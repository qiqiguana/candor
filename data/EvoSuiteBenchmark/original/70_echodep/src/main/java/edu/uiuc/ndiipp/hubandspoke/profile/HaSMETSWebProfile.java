/**
 * HaSMETSWebProfile.java
 * 
 * $Revision: 846 $
 * 
 * $Date: 2009-04-22 18:45:17 +0100 (Wed, 22 Apr 2009) $
 * 
 * Copyright (c) 2009, University Library, University of Illinois at 
 * Urbana-Champaign. All rights reserved. 
 * 
 * Developed by: The Hub and Spoke Project Group 
 *               University of Illinois Urbana-Champaign Library
 *               http://dli.grainger.uiuc.edu/echodep/hands/ 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the 
 * "Software"), to deal with the Software without restriction, including 
 * without limitation the rights to use, copy, modify, merge, publish, 
 * distribute, sublicense, and/or sell copies of the Software, and to 
 * permit persons to whom the Software is furnished to do so, subject to 
 * the following conditions: 
 * 
 * - Redistributions of source code must retain the above copyright notice, 
 * this list of conditions and the following disclaimers. 
 * 
 * - Redistributions in binary form must reproduce the above copyright 
 * notice, this list of conditions and the following disclaimers in the 
 * documentation and/or other materials provided with the distribution. 
 * 
 * - Neither the names of The Hub and Spoke Project Group, University of 
 * Illinois Urbana-Champaign Library, nor the names of its contributors may 
 * be used to endorse or promote products derived from this Software 
 * without specific prior written permission. 
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS 
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE CONTRIBUTORS OR COPYRIGHT HOLDERS BE LIABLE FOR 
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 * SOFTWARE OR THE USE OR OTHER DEALINGS WITH THE SOFTWARE. 
 */

package edu.uiuc.ndiipp.hubandspoke.profile;


import gov.loc.mets.*;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.archive.io.*;
import org.archive.io.arc.*;
import org.archive.util.InetAddressUtil;
import org.archive.util.TextUtils;

/**
 * This class represents a METS XML document that conforms to the UIUC Hub and
 * Spoke METS Web Profile. This is basically a wrapper around various XMLBeans class
 * libraries for the different XML Schema used by the profile.
 *
 * @author thabing
 *
 */
public class HaSMETSWebProfile extends HaSMETSProfile {

	//map file ids to ARCReaders
	private Hashtable<String,ARCReader> arcFileList=
		new Hashtable<String,ARCReader>();
	//map file ids to Hashtable keyed by RecordIdentifier, containing ARCRecordMetaData
	private Hashtable<String,Hashtable<String,Long>> arcFileRecordList= 
		new Hashtable<String,Hashtable<String,Long>>();
	
	{
		PROFILE_URI ="http://www.loc.gov/mets/profiles/00000016.xml";
	}

	/**
	 * Dump to stdout a list of all records in the each referenced ARC along with their offsets
	 * Mostly for debugging purposes
	 */
	public void dumpArcFileRecordList(){
		for(Enumeration<String> fileIDS = arcFileRecordList.keys();fileIDS.hasMoreElements();){
			String fileID = fileIDS.nextElement();
			System.out.println("ARC File ID: " + fileID);
			for(Enumeration<String> recIDS = arcFileRecordList.get(fileID).keys();recIDS.hasMoreElements();){
				String recID=recIDS.nextElement();
				System.out.println(recID + " offset:" + arcFileRecordList.get(fileID).get(recID).toString());
			}
		}
	}

    protected void finalize() throws Throwable {
    	//close all cached ARCReaders
    	for(Iterator it=arcFileList.values().iterator();it.hasNext();){
    		ARCReader arcRdr =(ARCReader)it.next();
    		String path= arcRdr.getReaderIdentifier();
    		arcRdr.close();
    		
    		//make sure temp files are cleaned up
    		//TODO:  This doesn't seem to be working on Windows in the dev environment
    		//make sure to test thoroughly because a bunch of arc files sitting in temp 
    		//can eat up much space
    		if(path.contains("HaSTemp")){
    			new File(path).delete();
    		}
    	}
        super.finalize();
}

	/**
	 * Generate  a new stub METS object using the default template
	 */
	protected HaSMETSWebProfile() {
		super();
		this.getMetsDocument().getMets().setPROFILE(PROFILE_URI);
		MetsStructMapType smap;
		try {
			smap = this.getPrimaryStructMap();
		} catch (HaSMETSProfileException e) {
			throw new RuntimeException(e);
		}
		smap.getDiv().setTYPE(HaSMETSWebProfile.WebCaptureDivision.WEB_CAPTURE.toString());
	}
	
	/**
	 * Create a new METS object from the XML file at the given filepath
	 * 
	 * @param filepath
	 *            the file path to the XML METS file to load
	 */
	protected HaSMETSWebProfile(String filepath) throws HaSMETSProfileException {
		super(filepath);
	}
	
	/**
	 * Create a new METS object from the XML file at the given URL
	 * 
	 * @param url
	 *            the location of the XML METS file to load
	 */
	protected HaSMETSWebProfile(URL url) throws HaSMETSProfileException {
		super(url);
	}	

	/**
	 * Create a new METS object from the given MetsMetsDocument with the given baseURI
	 * 
	 * @param mets MetsMetsDocument
	 * @param uri URI
	 */
	protected HaSMETSWebProfile(MetsMetsDocument mets, URI uri){
		this.setMetsDocument(mets);
		this.setBaseURI(uri);
		this.initIDs();
	}

	/**
	 * Return an InputStream for the MetsFileType object, can accomodat either the FContent or FLocat
	 * sub-elements or sections from an ARC file
	 * @param file the MetsFileType object for the file of interest
	 * @return	the InputStream
	 * @throws HaSMETSProfileException if the stream is not accessible
	 */
	public InputStream getFileInputStream(MetsFileType file)
		throws HaSMETSProfileException {
		InputStream strm =null;
		
		if(!file.isSetUSE() || file.getUSE().compareTo(HaSMETSWebProfile.ArcFileDecomposition.ARC_URL_RECORD.toString())!=0){
			strm=super.getFileInputStream(file);
		} else {
			//this is a ARC-URL-RECORD, so the parent file is the ARC file
			XmlCursor c = file.newCursor();
			c.toParent();
			if(c.getName().getLocalPart().compareTo("file")==0 && c.getName().getNamespaceURI().compareTo(METS_NS)==0){
				MetsFileType arcFile = (MetsFileType)c.getObject();
				if (arcFile.isSetUSE() 
						&& arcFile.getUSE().compareTo(HaSMETSWebProfile.ArcFileDecomposition.ARC.toString())==0){
					ARCReader arcRdr = this.getARCReader(arcFile);
					
					String ownerid=file.getOWNERID();
					if (ownerid==null){
						throw new HaSMETSProfileException("ARC-URL-RECORD file does not have an OWNERID attribute");
					}
					ARCRecordMetaData arcMeta=this.parseARCRecordOWNERID(arcFile.getFLocatArray(0).getHref(), ownerid);
					String recordID = arcMeta.getRecordIdentifier();
					
					Hashtable<String,Long> arcRecords = 
						arcFileRecordList.get(arcFile.getID());
					Long offset = arcRecords.get(recordID);

					if (offset!=null){
						ARCRecord arcRec=null;
						try {
							arcRec = (ARCRecord)arcRdr.get(offset);
							arcRec.skipHttpHeader();
							strm=arcRec;
						} catch (IOException e) {
							e.printStackTrace();
							throw new RuntimeException("Cannot get ARCRecord",e);
						}
					} else {
						throw new HaSMETSProfileException("The ARC-URL-RECORD could not be found in the ARC file");
					}
						
				} else {
					throw new HaSMETSProfileException("The parent of this ARC-URL-RECORD file element is not an ARC file element");
				}
				
			} else {
				throw new HaSMETSProfileException("The parent of this ARC-URL-RECORD file element is not a file element");
			}
			
		}
		
		
		return strm;
	}	
	
	/**
	 * Determine whether this document is using ARC files or not
	 * All it does is look in the first file element 
	 * @return true if this document is using arc files, else false
	 */
	protected boolean usesArcFiles(){
		boolean arcFiles=false;
		MetsFileType fls[] = this.getAllFiles();
		if (fls.length>0 
				 && (fls[0].isSetUSE() && (fls[0].getUSE().compareTo(HaSMETSWebProfile.ArcFileDecomposition.ARC.toString())==0
						 || fls[0].getUSE().compareTo(HaSMETSWebProfile.ArcFileDecomposition.ARC_URL_RECORD.toString())==0))){
			arcFiles=true;
		}
		return arcFiles;
	}
	
	/**
	 * If this METS document uses ARC files, decompose them and convert the METS file appropriately
	 * including generation of digiprov.  If the fileSec lists the ARC and subordinate ARC-URL-RECORD
	 * then only the listed ARC-URL-RECORD will be extracted.  If the fileSec lists only the ARC files,
	 * all records in the ARC files will be extracted
	 * 
	 * @param destinationPath
	 * @throws HaSMETSProfileException
	 */
	public void decomposeARCFiles(String destinationPath) throws HaSMETSProfileException {
		
		boolean valid = this.validateProfile(HaSMETSProfile.PackageType.SIP);
		
		if(!valid){
			throw new HaSMETSProfileException("This METS document does not conform to the profile and cannot be decomposed.");
		} else if(!usesArcFiles()){
			throw new HaSMETSProfileException("This METS document does not use ARC files and cannot be decomposed.");
		} else {
			MetsFileType fts[] = this.getAllFiles(HaSMETSWebProfile.ArcFileDecomposition.ARC);

			
			if (fts.length>0){
				this.getMetsDocument().getMets().getFileSec().addNewFileGrp();
				for(int i = 0;i<fts.length;i++){
					MetsFileType arcRecords[] =fts[i].getFileArray();
					if (arcRecords.length>0){
						//only extract the explicitly listed files
						for(int j = 0; j<arcRecords.length;j++){
							//TODO
						}
					} else {
						//TODO: extract all files in the ARC
					}
				}
			}
		}
	}
	
	/**
	 * Return an array of XMLBean FileType objects for all files of the specific type
	 * 
	 * @param use
	 *            one of the ArcFileDecomposition values ARC or ARC-URL-RECORD
	 * @return an array of XMLBean FileType objects
	 */
	private MetsFileType[] getAllFiles(HaSMETSWebProfile.ArcFileDecomposition use) {
		MetsFileType ft[] = null;

		String xql = "declare namespace m='" + METS_NS + "'; .//m:file[@USE='" + use.toString() + "']";
		
		XmlObject xobj[] = this.getMetsDocument().selectPath(xql);

		if (xobj.length>0)
			ft = (MetsFileType[])xobj ;
		else
			ft = new MetsFileType[0];
		
		return ft;

	}	
	
	/* (non-Javadoc)
	 * @see edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfile#validateProfile(edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfile.PackageType, java.util.ArrayList)
	 */
	public boolean validateProfile(PackageType pt)  {
		return this.validateProfile(pt, null);
	}

	/* (non-Javadoc)
	 * @see edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfile#validateProfile(edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfile.PackageType, java.util.ArrayList)
	 */
	public boolean validateProfile(PackageType pt, HaSMETSAppender app)  {
		HaSMETSWebValidator valid = new HaSMETSWebValidator();
		if(app!=null) HaSMETSWebValidator.logger.addAppender(app);
		boolean ret= valid.validateProfile(this, pt);
		if(app!=null) HaSMETSWebValidator.logger.removeAppender(app);
		return ret;
	}
	
	public static enum WebCaptureDivision {
		//Types of div elements in the PRIMARY_STRUCTMAP
		WEB_CAPTURE,
		WEB_RESOURCE,
		DEPENDENT_WEB_RESOURCE
	}
	
	public static enum ArcFileDecomposition {
		ARC,
		ARC_URL_RECORD;
		
		public String toString(){
			if(this.compareTo(HaSMETSWebProfile.ArcFileDecomposition.ARC)==0) 
				return this.name();
			else
				return "ARC-URL-RECORD";
		}
	}
	
	/**
	 * Get the ARCReader for the given arcFile.  The ARCReader may be newly created or
	 * retrieved from a Hashtable of perviously opened ARCReaders.  This method also creates a
	 * Hashtable of all the files contained in the ARC.
	 * @param fl
	 * @return an ARCReader
	 * @throws HaSMETSProfileException
	 */
	protected ARCReader getARCReader(MetsFileType fl) throws HaSMETSProfileException{
		File file = null;
		ARCReader arcRdr=null;
		MetsFileType arcFile=null;
		
		//check if this file is an ARC-URL-RECORD, if so get the parent ARC file
		if(fl.getUSE().compareTo(HaSMETSWebProfile.ArcFileDecomposition.ARC_URL_RECORD.toString())==0){
			XmlCursor c = fl.newCursor();
			c.toParent();
			if(c.getName().getLocalPart().compareTo("file")==0 
					&& c.getName().getNamespaceURI().compareTo(METS_NS)==0){
				arcFile = (MetsFileType)c.getObject();
				if (!arcFile.isSetUSE() 
						|| arcFile.getUSE().compareTo(HaSMETSWebProfile.ArcFileDecomposition.ARC.toString())!=0){
					throw new HaSMETSProfileException("The ARC-URL-RECORD file does not have a parent file element with a USE='ARC' attribute");
				}
			} else {
				throw new HaSMETSProfileException("The ARC-URL-RECORD file does not have a parent file element");
			}
		} else{
			arcFile=fl;
		}
		
		//first look in the arcFileList
		if(arcFileList.containsKey(arcFile.getID())){
			return arcFileList.get(arcFile.getID());
		}
		
		MetsFileType.FLocat flocat=arcFile.getFLocatArray(0);
		URL url;
		try {
			url = this.resolveURIAgainstBaseURI(flocat.getHref()).toURL();
		} catch (MalformedURLException e1) {
			throw new HaSMETSProfileException("The href '" + flocat.getHref()+ "' is not a valid URL.", e1);
		}
		if (url.getProtocol().compareToIgnoreCase("file")==0){
			file = new File(url.getPath());
		} else if (url.getProtocol().startsWith("http")){
			//copy the arc to a temporary file location
			InputStream arcIn = this.getFileInputStream(arcFile);
			try {
				file = File.createTempFile("HaSTemp", ".arc");
				file.deleteOnExit(); //TODO: this has bugs under Windows
				FileOutputStream arcOut = new FileOutputStream(file);
				int b;
				while((b = arcIn.read())!=-1){
					arcOut.write(b);
				}
				arcOut.close();
				arcIn.close();
			} catch (FileNotFoundException e) {
				throw new RuntimeException("Unable to find temp file '" + file.getAbsolutePath() + "'",e);
			} catch (IOException e) {
				throw new RuntimeException("Unable to read temp file '" + file.getAbsolutePath() + "'",e);
			}
		} else {
			throw new RuntimeException("The URL '" + url.getProtocol() + "' scheme is not supported.");
		}
		
		
		try {
			arcRdr = ARCReaderFactory.get(file);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to read ARC file",e);
		}
		
		//add to arc file list
		arcFileList.put(arcFile.getID(), arcRdr);
		//add to arc file record list
		arcFileRecordList.put(arcFile.getID(),createArcHashes(arcRdr));

		return arcRdr;
	}
	
	/**
	 * Create a Hashtable of all the files contained in the ARC.  The key is the RecordIdentifier
	 * and the value is the offset
	 * @param arcRdr
	 * @return
	 */
	private Hashtable<String,Long> createArcHashes(ARCReader arcRdr){
		Hashtable<String,Long> arcRecordList = 
			new Hashtable<String,Long>();

		for(Iterator<ArchiveRecord> it = arcRdr.iterator();it.hasNext();){
			ARCRecord arcRec = (ARCRecord)it.next();
			arcRecordList.put(arcRec.getHeader().getRecordIdentifier(), arcRec.getHeader().getOffset());
		}
		
		return arcRecordList;
	}
	
	/**
	 * Parse a string which is a ARC file section header into an ARCRecordMetaData class
	 * @param arc  the path or url of the arc file containing the header
	 * @param ownerid the String with the header string
	 * @return  ARCRecordMetaData
	 * @throws HaSMETSProfileException
	 */
	private ARCRecordMetaData parseARCRecordOWNERID(String arc,
			String ownerid) throws HaSMETSProfileException{
		
		//pad the ownerid with version and offset values
		ownerid = ownerid + " 1.1 0";
		String fields[] = ownerid.split(" +");
		String names[] = (String[])ARCConstants.REQUIRED_VERSION_1_HEADER_FIELDS.toArray();
		
		ARCRecordMetaData meta=null;
		try {
			meta= computeMetaData(Arrays.asList(names),Arrays.asList(fields),"1.1",0,arc);
		} catch (IOException e) {
			e.printStackTrace();
			throw new HaSMETSProfileException("The ARC file OWNERID '" + ownerid + "' is not valid",e);
		}
		return meta;
	}
	
	
	//THE FOLLOWING WERE COPIED FROM VARIOUS HERITRIX ARC CLASSES
    /**
     * Compute metadata fields.
     *
     * Here we check the meta field has right number of items in it.
     *
     * @param keys Keys to use composing headerFields map.
     * @param values Values to set into the headerFields map.
     * @param v The version of this ARC file.
     * @param offset Offset into arc file.
     *
     * @return Metadata structure for this record.
     *
     * @exception IOException  If no. of keys doesn't match no. of values.
     */
    private ARCRecordMetaData computeMetaData(List<String> keys,
    		List<String> values, String v, long offset, String arc)
    throws IOException {
        if (keys.size() != values.size()) {
            List<String> originalValues = values;
            if (!isStrict()) {
                values = fixSpaceInURL(values, keys.size());
                // If values still doesn't match key size, try and do
                // further repair.
	            if (keys.size() != values.size()) {
	            	// Early ARCs had a space in mimetype.
	            	if (values.size() == (keys.size() + 1) &&
	            			values.get(4).toLowerCase().startsWith("charset=")) {
	            		List<String> nuvalues =
	            			new ArrayList<String>(keys.size());
	            		nuvalues.add(0, values.get(0));
	            		nuvalues.add(1, values.get(1));
	            		nuvalues.add(2, values.get(2));
	            		nuvalues.add(3, values.get(3) + values.get(4));
	            		nuvalues.add(4, values.get(5));
	            		values = nuvalues;
	            	} else if((values.size() + 1) == keys.size() &&
                            isLegitimateIPValue(values.get(1)) &&
                            isDate(values.get(2)) && isNumber(values.get(3))) {
                        // Mimetype is empty.
                        List<String> nuvalues =
                            new ArrayList<String>(keys.size());
                        nuvalues.add(0, values.get(0));
                        nuvalues.add(1, values.get(1));
                        nuvalues.add(2, values.get(2));
                        nuvalues.add(3, "-");
                        nuvalues.add(4, values.get(3));
                        values = nuvalues;
                    }
	            }
        	}
            if (keys.size() != values.size()) {
                throw new IOException("Size of field name keys does" +
                    " not match count of field values: " + values);
            }
            // Note that field was fixed on stderr.
            logStdErr(Level.WARNING, "Fixed spaces in metadata line at " +
            	"offset " + offset +
                " Original: " + originalValues + ", New: " + values);
        }
        
        Map<Object, Object> headerFields =
        	new HashMap<Object, Object>(keys.size() + 2);
        for (int i = 0; i < keys.size(); i++) {
            headerFields.put(keys.get(i), values.get(i));
        }
        
        // Add a check for tabs in URLs.  If any, replace with '%09'.
        // See https://sourceforge.net/tracker/?group_id=73833&atid=539099&func=detail&aid=1010966,
        // [ 1010966 ] crawl.log has URIs with spaces in them.
        String url = (String)headerFields.get(ARCConstants.URL_FIELD_KEY);
        if (url != null && url.indexOf('\t') >= 0) {
            headerFields.put(ARCConstants.URL_FIELD_KEY,
                TextUtils.replaceAll("\t", url, "%09"));
        }

        headerFields.put(ARCConstants.VERSION_FIELD_KEY, v);
        headerFields.put(ARCConstants.ABSOLUTE_OFFSET_KEY, new  Long(offset));

        return new ARCRecordMetaData(arc, headerFields);
    }
    private boolean isStrict() {return false;}
    
    /**
     * Fix space in URLs.
     * The ARCWriter used to write into the ARC URLs with spaces in them.
     * See <a
     * href="https://sourceforge.net/tracker/?group_id=73833&atid=539099&func=detail&aid=1010966">[ 1010966 ]
     * crawl.log has URIs with spaces in them</a>.
     * This method does fix up on such headers converting all spaces found
     * to '%20'.
     * @param values List of metadata values.
     * @param requiredSize Expected size of resultant values list.
     * @return New list if we successfully fixed up values or original if
     * fixup failed.
     */
    private List<String> fixSpaceInURL(List<String> values, int requiredSize) {
        // Do validity check. 3rd from last is a date of 14 numeric
        // characters. The 4th from last is IP, all before the IP
        // should be concatenated together with a '%20' joiner.
        // In the below, '4' is 4th field from end which has the IP.
        if (!(values.size() > requiredSize) || values.size() < 4) {
            return values;
        }
        // Test 3rd field is valid date.
        if (!isDate((String) values.get(values.size() - 3))) {
            return values;
        }

        // Test 4th field is valid IP.
        if (!isLegitimateIPValue((String) values.get(values.size() - 4))) {
            return values;
        }

        List<String> newValues = new ArrayList<String>(requiredSize);
        StringBuffer url = new StringBuffer();
        for (int i = 0; i < (values.size() - 4); i++) {
            if (i > 0) {
                url.append("%20");
            }
            url.append(values.get(i));
        }
        newValues.add(url.toString());
        for (int i = values.size() - 4; i < values.size(); i++) {
            newValues.add(values.get(i));
        }
        return newValues;
    }
    private boolean isLegitimateIPValue(final String ip) {
        if (ip == "-") {
            return true;
        }
        Matcher m = InetAddressUtil.IPV4_QUADS.matcher(ip);
        return m != null && m.matches();
    }
    private boolean isDate(final String date) {
        if (date.length() != 14) {
            return false;
        }
        return isNumber(date);
    }
    
    private boolean isNumber(final String n) {
        for (int i = 0; i < n.length(); i++) {
            if (!Character.isDigit(n.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    private void logStdErr(Level level, String message) {
        System.err.println(level.toString() + " " + message);
    }

}
