/**
 * HaSMETSProfileFactory.java
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;

import edu.uiuc.ndiipp.hubandspoke.utils.HaSChecksummer;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSConstants;

import gov.loc.mets.MetsFileType;
import gov.loc.mets.MetsMdSecType;
import gov.loc.mets.MetsMetsDocument;
import gov.loc.premis.PremisAgentDocument;
import gov.loc.premis.PremisEventDocument;

/**
 * 
 * This class contains static methods for instantiating through various means HaSMETSProfile instances 
 * or subclasses.
 * 
 * @author thabing
 *
 */
public class HaSMETSProfileFactory {
	//log4j logger
	static Logger logger = Logger.getLogger(HaSMETSProfileFactory.class);

	private static final String BASE_PROFILE_URI = "http://www.loc.gov/mets/profiles/00000015.xml";

	private static final String WEB_PROFILE_URI = "http://www.loc.gov/mets/profiles/00000016.xml";
	
	private static final String ZIP_METS_FILENAME = HaSConstants.METS_FILE_NAME;

	private static final String ALT_ZIP_METS_FILENAME = "mets.xml";
	
	/**
	 * Perform basic functions from the command line like unzip or validate
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		Options options = new Options();
		options.addOption("u","unzip",true,"Unzip the given ZIP METS package");
		options.addOption("v","validate",false,"Validate the METS file");
		CommandLineParser parser = new PosixParser();
		CommandLine cmd;
		HelpFormatter formatter = new HelpFormatter();
		try {
			cmd = parser.parse( options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp( "HaSMETSProfileFactory", options );
			return;
		}
		
		if(cmd.hasOption("u")){
			String path = cmd.getOptionValue("u");
			HaSMETSProfile hmp;
			try {
				hmp = HaSMETSProfileFactory.newInstance(new ZipFile(path), true,HaSMETSProfile.PackageType.SIP);
			} catch (HaSMETSProfileException e) {
				System.out.println("METS Profile Error: " + e.getMessage());
				return;
			} catch (IOException e) {
				System.out.println("ZIP File Error: " + e.getMessage());
				return;
			}
			if(cmd.hasOption("v")){
				HaSMETSAppender app = new HaSMETSAppender("HaSMETSProfileFactory");
				logger.addAppender(app);
				hmp.validateProfile(HaSMETSProfile.PackageType.SIP);
				if(app.hasEvents()) {
					System.out.println("PROFILE VALIDATION ERRORS:");
					for(Iterator it = app.getEvents().iterator();it.hasNext();){
						System.out.println(it.next().toString());
					}
				}
			}
			System.out.println("Finished unzipping: " + path);
			return;
		} else {
			System.out.println("Missing command-line parameters");
			formatter.printHelp( "HaSMETSProfileFactory", options );
			return;
		}
	}

	/**
	 * Create a new instance of a HaSMETSProfile object
	 * 
	 * @param pt	the PackageType of the newly created package 
	 * 
	 * @return a new HaSMETSProfile instance
	 */
	public static HaSMETSProfile newHaSMETSProfile(HaSMETSProfile.PackageType pt) {
		HaSMETSProfile ret =new HaSMETSProfile();
		ret.setPackageType(pt);
		return ret;
	}
	
	/**
	 * Create a new instance of a HaSMETSProfile object with a PackageType of AIP
	 * 
	 * @return a new HaSMETSProfile instance
	 */
	public static HaSMETSProfile newHaSMETSProfile(){
		return HaSMETSProfileFactory.newHaSMETSProfile(HaSMETSProfile.PackageType.AIP);
	}

	/**
	 * Create a new instance of a HaSMETSWebProfile object
	 * 
	 * @param pt	the package type of the newly created package
	 * 
	 * @return a new HaSMETSWebProfile instance
	 */
	public static HaSMETSWebProfile newHaSMETSWebProfile(HaSMETSProfile.PackageType pt) {
		
		HaSMETSWebProfile ret = new HaSMETSWebProfile();
		ret.setPackageType(pt);
		
		return ret;
	}
	
	/**
	 * Create a new instance of a HaSMETSWebProfile object with a package type of AIP
	 * 
	 * @return a new HaSMETSWebProfile instance
	 */
	public static HaSMETSWebProfile newHaSMETSWebProfile() {
		return HaSMETSProfileFactory.newHaSMETSWebProfile(HaSMETSProfile.PackageType.AIP);
	}

	
	/**
	 * Create a new HaSMETSProfile object or one of its subclasses from the XML
	 * file at the given URL. The class returned depends on the PROFILE
	 * attribute of the METS file
	 * 
	 * @param url
	 *            the URL to the XML METS file to load
	 *            
	 * @param pt	PackageType
	 */
	public static HaSMETSProfile newInstance(URL url, HaSMETSProfile.PackageType pt)
			throws HaSMETSProfileException {
		URI baseURI = null;
		MetsMetsDocument metsDoc = null;
		HaSMETSProfile ret=null;
		try {
			baseURI = url.toURI();
			metsDoc = MetsMetsDocument.Factory.parse(url,
					HaSMETSProfile.defaultLoadOptions);
		} catch (XmlException e) {
			throw new HaSMETSProfileException("Invalid XML from this URL '"
					+ url.toExternalForm() + "'", e);
		} catch (IOException e) {
			throw new HaSMETSProfileException("Unable to access URL '"
					+ url.toExternalForm() + "'", e);
		} catch (URISyntaxException e) {
			throw new HaSMETSProfileException("The URL '"
					+ url.toExternalForm() + "' syntax is not valid", e);
		}

		if (metsDoc.getMets().isSetPROFILE()
				&& metsDoc.getMets().getPROFILE().compareTo(BASE_PROFILE_URI) == 0) {
			ret= new HaSMETSProfile(metsDoc, baseURI);
		} else if (metsDoc.getMets().isSetPROFILE()
				&& metsDoc.getMets().getPROFILE().compareTo(WEB_PROFILE_URI) == 0) {
			ret= new HaSMETSWebProfile(metsDoc, baseURI);
		} else {
			throw new HaSMETSProfileException(
					"The document at this URL '"
							+ url.toExternalForm()
							+ "' does not have a PROFILE attribute matching one of the Hub and Spoke profiles");
		}
		ret.setPackageType(pt);
		return ret;
	}
	
	/**
	 * Create a new HaSMETSProfile object or one of its subclasses from the XML
	 * file at the given URL. The class returned depends on the PROFILE
	 * attribute of the METS file
	 * 
	 * @param url
	 *            the URL to the XML METS file to load
	 * @throws HaSMETSProfileException 
	 *            
	 */
	public static HaSMETSProfile newInstance(URL url) throws HaSMETSProfileException{
		return HaSMETSProfileFactory.newInstance(url, HaSMETSProfile.PackageType.AIP);
	}


	/**
	 * Create a new HaSMETSProfile object or one of its subclasses from the XML
	 * file at the given file path. The class returned depends on the PROFILE
	 * attribute of the METS file
	 * 
	 * @param filepath
	 *            the file path to the XML METS file to load
	 * @param pt	PackageType
	 */
	public static HaSMETSProfile newInstance(String filepath, HaSMETSProfile.PackageType pt)
			throws HaSMETSProfileException {
		File f = new File(filepath);
		return newInstance(f,pt);
	}
	
	/**
	 * Create a new HaSMETSProfile object or one of its subclasses from the XML
	 * file at the given file path. The class returned depends on the PROFILE
	 * attribute of the METS file
	 * 
	 * @param filepath
	 *            the file path to the XML METS file to load
	 * @throws HaSMETSProfileException 
	 */
	public static HaSMETSProfile newInstance(String filepath) throws HaSMETSProfileException{
		return HaSMETSProfileFactory.newInstance(filepath, HaSMETSProfile.PackageType.AIP);
	}


	/**
	 * Create a new HaSMETSProfile object or one of its subclasses from the XML
	 * file . The class returned depends on the PROFILE attribute of the METS
	 * file
	 * 
	 * @param f
	 *            the file containing XML METS file to load
	 * @param pt	the PackageType of the new package
	 */
	public static HaSMETSProfile newInstance(File f, HaSMETSProfile.PackageType pt)
			throws HaSMETSProfileException {
		// TODO: update checksums here
		URI baseURI = null;
		MetsMetsDocument metsDoc = null;
		HaSMETSProfile ret=null;
		try {
			baseURI = f.toURI();
			metsDoc = MetsMetsDocument.Factory.parse(baseURI.toURL(),
					HaSMETSProfile.defaultLoadOptions);
		} catch (XmlException e) {
			throw new HaSMETSProfileException("Invalid XML from this file '"
					+ f.getAbsolutePath() + "'", e);
		} catch (IOException e) {
			throw new HaSMETSProfileException("Unable to access file '"
					+ f.getAbsolutePath() + "'", e);
		}

		if (metsDoc.getMets().isSetPROFILE()
				&& metsDoc.getMets().getPROFILE().compareTo(BASE_PROFILE_URI) == 0) {
			ret= new HaSMETSProfile(metsDoc, baseURI);
		} else if (metsDoc.getMets().isSetPROFILE()
				&& metsDoc.getMets().getPROFILE().compareTo(WEB_PROFILE_URI) == 0) {
			ret= new HaSMETSWebProfile(metsDoc, baseURI);
		} else {
			throw new HaSMETSProfileException(
					"This file '"
							+ f.getAbsolutePath()
							+ "' does not have a PROFILE attribute matching one of the Hub and Spoke profiles");
		}
		ret.setPackageType(pt);
		return ret;

	}
	
	/**
	 * Create a new HaSMETSProfile object or one of its subclasses from the XML
	 * file . The class returned depends on the PROFILE attribute of the METS
	 * file
	 * 
	 * @param f
	 *            the file containing XML METS file to load
	 */
	public static HaSMETSProfile newInstance(File f) throws HaSMETSProfileException{
		return HaSMETSProfileFactory.newInstance(f, HaSMETSProfile.PackageType.AIP);
	}
	

	/**
	 * Create a new HaSMETSProfile object or one of its subclasses from the
	 * files contained in the given ZipFile. The zip file is unzipped into the
	 * same directory as the zipfile itself. If there already files in this
	 * directory they will not be overwritten, and n error will result. The
	 * class returned depends on the PROFILE attribute of the METS file. There
	 * is assumed to be a file in the root directory of the zip file called
	 * echodepmets.xml or just mets.xml. This method looks for filenames which
	 * would be duplicates on a system which treats filenames as
	 * case-insensitive (Windows), and it will rename these files as
	 * appropriate. Note that directory names are not renamed, so for example if
	 * there are files Test/file.txt and test/file.txt they will end up in the
	 * same directory, but with different file names, like Test/file.txt and
	 * Test/file_1.txt
	 * 
	 * @param zip
	 * @param overWrite
	 * @param pt
	 * @throws HaSMETSProfileException
	 */
	public static HaSMETSProfile newInstance(ZipFile zip, boolean overWrite, HaSMETSProfile.PackageType pt) 
		throws HaSMETSProfileException {
		/**
		 * Inner class for keeping track of the files in the zip
		 */
		class ZIPFileList {
			Hashtable<String,File> files = new Hashtable<String,File>();
			Hashtable<String,File> renamedFiles = new Hashtable<String,File>();
			Hashtable<String,String> sha1 = new Hashtable<String,String>();
			
			File getMetsFile(){
				String metsPath=null;
				for(Enumeration<String> en = files.keys();en.hasMoreElements();){
					String path = en.nextElement();
					if(path.toLowerCase().endsWith(ZIP_METS_FILENAME)){
						if(metsPath==null || path.length()<metsPath.length()){
							metsPath=path;
						}
					}
				}
				if(metsPath==null){
					for(Enumeration<String> en = files.keys();en.hasMoreElements();){
						String path = en.nextElement();
						if(path.toLowerCase().endsWith(ALT_ZIP_METS_FILENAME)){
							if(metsPath==null || path.length()<metsPath.length()){
								metsPath=path;
							}
						}
					}
				}
				
				if (metsPath!=null)
					return files.get(metsPath);
				else
					return null;
			}
			
			void deleteFiles(){
				for(Enumeration<File> en = files.elements();en.hasMoreElements();){
					File file = en.nextElement();
					if(file.exists() && file.isFile()){
						file.delete();
					}
				}
				
			}
		}

		ZIPFileList zz = new ZIPFileList();
		
		File zipfolder = new File(zip.getName()).getParentFile().getAbsoluteFile();
		
		for(Enumeration<? extends ZipEntry> ents = zip.entries();ents.hasMoreElements();){
			ZipEntry zent=ents.nextElement();
			
			if(!zent.isDirectory()){
				// create unique lowercase names for the files to prevent
				// collisions on case-insensitive systems
				File zentPath = new File(zipfolder,zent.getName());
				String origName = zentPath.getName();
				String pre ="";
				String ext ="";
				int i = origName.lastIndexOf(".");
				if(i==-1) {
					pre=origName;
					ext="";
				} else {
					pre=origName.substring(0,i);
					ext=origName.substring(i);
				}
				boolean renamed=false;
				int cnt=1;
				while(zz.files.contains(zentPath)){
					String fname = pre + "__" + String.valueOf(cnt++) + ext;
					zentPath = new File(zentPath.getParentFile(),fname);
					renamed=true;
				}
				if(renamed){
					zz.renamedFiles.put(zent.getName(), zentPath);
				}
				zz.files.put(zent.getName(), zentPath);
				
				if(zentPath.getParentFile().exists() || zentPath.getParentFile().mkdirs()){
					boolean ok=false;
					try {
						ok = zentPath.createNewFile();
					} catch (IOException e) {
						zz.deleteFiles();
						throw new HaSMETSProfileException("Unable to create file '" + zentPath.getAbsolutePath() + "'",e);
					}
					if(!ok && !overWrite){
						zz.deleteFiles();
						throw new HaSMETSProfileException("The file already exists '" + zentPath.getAbsolutePath() + "' and will not be overwritten");
					} else {
						long actualSize=0;
						long actualCRC=0;
						
						long reportedCRC = zent.getCrc();
						long reportedSize = zent.getSize();
						HaSChecksummer summer;
						try {
							InputStream inpStrm = zip.getInputStream(zent);
							summer = new HaSChecksummer();
							CheckedInputStream chkStrm = new CheckedInputStream(inpStrm, summer);
							FileOutputStream outfile = new FileOutputStream(zentPath);
							int b;
							while((b=chkStrm.read())!=-1){
								outfile.write(b);
								actualSize++;
							}
							chkStrm.close();
							outfile.close();
						} catch (IOException e) {
							zz.deleteFiles();
							throw new HaSMETSProfileException("Unable to access zip entry '" + zent.getName() + "'",e);
						}
						zentPath.setLastModified(zent.getTime());
						actualCRC = summer.getLongCRC32();
						zz.sha1.put(zent.getName(), summer.getHexEncodedSHA1());
						if(reportedCRC != actualCRC){
							zz.deleteFiles();
							throw new HaSMETSProfileException("The reported CRC does not match the actual CRC of the file '" + zentPath.getAbsolutePath() + "'");
						}
						if(reportedSize != actualSize){
							zz.deleteFiles();
							throw new HaSMETSProfileException("The reported size does not match the actual size of the file '" + zentPath.getAbsolutePath() + "'");
						}
					}
				} else {
					zz.deleteFiles();
					throw new HaSMETSProfileException("Unable to create directories '" + zentPath.getParentFile().getAbsolutePath() + "'");
				}
			}
		}
		
		File metsfile = zz.getMetsFile();
		
		if (metsfile==null){
			zz.deleteFiles();
			throw new HaSMETSProfileException("The Zip file does not contain an 'echodepmets.xml' or 'mets.xml' file");
		}
		
		HaSMETSProfile mets=HaSMETSProfileFactory.newInstance(metsfile,pt);
		
		//add a premis object techMD to all files, if needed
		mets.addNewPREMISObjectTechMDToAllFile();

		//if the file element has a checksum validate it against the calculated one;
		//else add the calculated one to the file
		for(Enumeration<String> en = zz.sha1.keys();en.hasMoreElements();){
			String key = en.nextElement();
			String sha1 = zz.sha1.get(key);
			File url = new File(zipfolder,key);
			MetsFileType.FLocat fl[] = mets.getFLocat(url.toURI().toASCIIString());
			for(int i=0;i<fl.length;i++){
				XmlCursor xcur = fl[i].newCursor();
				xcur.toParent();
				MetsFileType ft = (MetsFileType)xcur.getObject();
				if(ft.isSetCHECKSUM() && ft.isSetCHECKSUMTYPE() && ft.getCHECKSUMTYPE().equals(MetsFileType.CHECKSUMTYPE.SHA_1)){
					//compare checksums
					if(sha1.compareToIgnoreCase(ft.getCHECKSUM())!=0){
						logger.warn("The CHECKSUM attribute of the file '" + key + "' does not match the actual checksum.");
						//throw new HaSMETSProfileException("The CHECKSUM attribute of the file '" + key + "' does not match the actual checksum.");
					}
				} else {
					mets.addNewChecksum(ft,MetsFileType.CHECKSUMTYPE.SHA_1,sha1);
				}
			}
		}

		
		// if any files needed to be renamed modify the mets file accordingly
		for(Enumeration<String> en = zz.renamedFiles.keys();en.hasMoreElements();){
			String key = en.nextElement();
			File url = new File(zipfolder,key);
			MetsFileType.FLocat fl[] = mets.getFLocat(url.toURI().toASCIIString());
			URI metsBaseURI = mets.getBaseURIMinusFile();
			URI renamedURI = zz.renamedFiles.get(key).toURI();
			URI newURI = metsBaseURI.relativize(renamedURI);
			// TODO: replace the existing URL with the new one and add some
			// techmd
			if(fl.length>0){
				for(int i=0;i<fl.length;i++){
					XmlCursor xcur = fl[i].newCursor();
					xcur.toParent();
					MetsFileType ft = (MetsFileType)xcur.getObject();
					String origName = fl[i].getHref();
					fl[i].setHref("./" + newURI.toASCIIString());
					
					mets.addPremisObjectIdentifierToFile(ft, HaSMETSProfile.PREMISIdentifierType.URL, fl[i].getHref(), 
							"Name was changed from '" + origName + "' to prevent collisions on case-insensitive file systems" );
				}
			} else {
				// if the renamed files are not found just log it for now;
				// otherwise uncomment these lines:
				// zz.deleteFiles();
				// throw new HaSMETSProfileException("The renamed file '" + key
				// + "' was not found in the METS document");
				logger.warn("The renamed file '" + key + "' was not found in the METS document");
			}
		}
		
		PremisEventDocument pevent = HaSMETSProfile.createBasicPremisEvent(
				HaSMETSProfile.PREMISEventType.DECOMPRESSION, 
				"This METS document and all associated files were extracted from zip file '" + zip.getName() + "'");

		PremisAgentDocument agents[] = {mets.getDefaultHumanAgent(),mets.getDefaultSoftwareAgent()};
		HaSMETSProfile.PREMISLinkingAgentRole roles[] = {HaSMETSProfile.PREMISLinkingAgentRole.EVENT_INITIATOR,
				HaSMETSProfile.PREMISLinkingAgentRole.SOFTWARE_USED};
		
		//add the event to each file
		MetsFileType allfiles[] = mets.getAllFiles();
		if(allfiles.length>0){
			MetsMdSecType ev = mets.addEventToXmlObject(allfiles[0], pevent, agents,roles);
			for(int i=1;i<allfiles.length;i++){
				mets.associateAmdSecToXmlObject(ev,allfiles[i]);
			}
		}
		
		return mets;
	}
	
	/**
	 * Create a new HaSMETSProfile object or one of its subclasses from the
	 * files contained in the given ZipFile. 
	 * 
	 * @param zip
	 * @param overWrite
	 * @throws HaSMETSProfileException 
	 */
	public static HaSMETSProfile newInstance(ZipFile zip, boolean overWrite) throws HaSMETSProfileException {
		return HaSMETSProfileFactory.newInstance(zip, overWrite, HaSMETSProfile.PackageType.AIP);
	}


}
