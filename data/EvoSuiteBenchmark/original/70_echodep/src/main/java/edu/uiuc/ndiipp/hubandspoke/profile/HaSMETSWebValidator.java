/**
 * HaSMETSWebValidator.java
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

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.zip.CheckedInputStream;

import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlHexBinary;
import org.archive.io.arc.ARCReader;
import org.archive.io.arc.ARCRecord;

import edu.uiuc.ndiipp.hubandspoke.profile.METSProfile.PackageType;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSChecksummer;
import gov.loc.mets.MetsAreaType;
import gov.loc.mets.MetsDivType;
import gov.loc.mets.MetsFileType;
import gov.loc.mets.MetsStructMapType;

public class HaSMETSWebValidator extends HaSMETSValidator {

	/* (non-Javadoc)
	 * @see edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfile#validateProfile(edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfile.PackageType, java.util.ArrayList)
	 */
	public boolean validateProfile(HaSMETSProfile metsP,PackageType pt, boolean openEachFile)  {
		HaSMETSAppender app = new HaSMETSAppender("ValidateProfile");
		logger.addAppender(app);

		super.validateProfile(metsP, pt, openEachFile);
		
		this.validatePrimaryStructMap(metsP,pt);
		
		logger.removeAppender(app);
		return (!app.hasErrors());
	}
	
	public boolean validateProfile(HaSMETSProfile metsP,PackageType pt)  {
		return this.validateProfile(metsP,pt, true);
	}
	
	/**
	 * Validate that the div of the PRIMARY_STRUCTMAP contains a valid fptr
	 * @param metsP HaSMETSProfile
	 * @param pt PackageType
	 * @param div	the div whose fptr is neing validated
	 * @return true if it is valid, else false
	 */
	public boolean validatePrimaryStructMapFptr(HaSMETSProfile metsP, PackageType pt, MetsDivType div)  {
		HaSMETSAppender app = new HaSMETSAppender("ValidatePrimaryStructMapFptr");
		logger.addAppender(app);

		boolean arcFiles = ((HaSMETSWebProfile)metsP).usesArcFiles();
		
		//each div of the PRIMARY_STRUCTMAP must have one fptr
		MetsDivType.Fptr fptrs[] = div.getFptrArray();
		if(fptrs.length==0){
			logger.error(XmlError.forObject("The div element of the PRIMARY_STRUCTMAP does not have an fptr sub-element",div));
		} else if(fptrs.length>1) {
			logger.error(XmlError.forObject("The div element of the PRIMARY_STRUCTMAP has more than one fptr sub-element",div));
		} else {
			if(arcFiles){
				//the fptr must have an area
				MetsAreaType area = fptrs[0].getArea();
				if(area==null){
					logger.error(XmlError.forObject("The fptr element of the PRIMARY_STRUCTMAP for a METS document using ARC files does not have an area sub-element",fptrs[0]));
				} else {
					//check that the area points to a ARC-URL-RECORD file
					String fid = area.getFILEID();
					if (fid==null){
						logger.error(XmlError.forObject("The area element of the PRIMARY_STRUCTMAP for a METS document using ARC files does not have a FILEID attribute",area));
					} else {
						MetsFileType fts[]= metsP.getFiles(fid);
						if(fts.length==0){
							logger.error(XmlError.forObject("The area element of the PRIMARY_STRUCTMAP for a METS document using ARC files does not reference an exiting file element",area));
						} else if (fts.length>1){
							logger.error(XmlError.forObject("The METS document contains multiple file elements with this same ID",area));
						} else {
							if(fts[0].getUSE().compareToIgnoreCase(HaSMETSWebProfile.ArcFileDecomposition.ARC.toString())!=0){
								logger.error(XmlError.forObject("The file element referenced from this area element is not an ARC file",area));
							} else {
								//check that the BEGIN and EXTENT atributes are correct
								if (!area.isSetBEGIN() || !area.isSetEXTENT() || !area.isSetBETYPE() || !area.isSetEXTTYPE()){
									logger.error(XmlError.forObject("The area element is missing one or more of the BEGIN, EXTENT, BETYPE, or EXTTYPE attributes",area));
								} else {
									//check that the attributes have valid values
									if(area.getBETYPE()!=MetsAreaType.BETYPE.BYTE || area.getEXTTYPE()!=MetsAreaType.EXTTYPE.BYTE){
										logger.error(XmlError.forObject("The area BETYPE or EXTTYPE attribute does not have a value of BYTE",area));										
									}
									long be=0;
									long ex=0;
									try {
										be = Long.parseLong(area.getBEGIN());
										ex = Long.parseLong(area.getEXTENT());
									} catch (NumberFormatException e) {
										logger.error(XmlError.forObject("The area BEGIN or EXTENT attribute does not contain a valid numeric value",area));										
									}
									if (be<=0 || ex<=0){
										logger.error(XmlError.forObject("The area BEGIN or EXTENT attribute does not contain a positive numeric value",area));										
									} else {
										//check the ARC file itself
										ARCReader arcRdr=null;
										ARCRecord arcRec=null;
										try {
											arcRdr = ((HaSMETSWebProfile)metsP).getARCReader(fts[0]);
											arcRec = (ARCRecord)arcRdr.get(be);
											if (arcRec.getMetaData().getLength()!=ex){
												logger.error(XmlError.forObject("The EXTENT attribute does not match the actual length of the record:" + arcRec.getMetaData().getLength(),area));										
											}
										} catch (HaSMETSProfileException e) {
											logger.error(XmlError.forObject("Unable to open the ARC file",fts[0]));										
											logger.error(XmlError.forObject(e.getMessage(),fts[0]));
										} catch (IOException e) {
											logger.error(XmlError.forObject("Unable to read ARC record from given offset",fts[0]));										
											logger.error(XmlError.forObject(e.getMessage(),fts[0]));
										}
										
									}
								}
							}
						}
					}
				}
			} else {
				//there is not really anything to very for non-ARCs
			}
			
		}
		
		logger.removeAppender(app);
		return (!app.hasErrors());
	}
	
	/**
	 * Validate the primary structMap against the requirements of the Web METS HaS Profile
	 * @param metsP HaSMETSProfile
	 * @param pt PackageType
	 * @return true if it is valid, else false
	 */
	public boolean validatePrimaryStructMap(HaSMETSProfile metsP,PackageType pt)  {
		HaSMETSAppender app = new HaSMETSAppender("ValidatePrimaryStructMap");
		logger.addAppender(app);
		
		MetsStructMapType psmap=null;
		try {
			 psmap= metsP.getPrimaryStructMap();
		} catch (HaSMETSProfileException e) {
			logger.error(XmlError.forObject("Unable to validate the structMap: "+ e.getMessage(),metsP.getMetsDocument()));
		}

		if (psmap!=null) {
			//if this METS document uses ARC files check that the ARC file sections are appropriately
			//referenced in the structMap
			
			
			
			//check that the structure of the structMap is OK
			
			MetsDivType rootDiv = psmap.getDiv();
			
			if (!rootDiv.isSetTYPE() || rootDiv.getTYPE().compareTo(HaSMETSWebProfile.WebCaptureDivision.WEB_CAPTURE.toString())!=0){
				logger.error(XmlError.forObject("The root div element of the PRIMARY_STRUCTMAP does not have a TYPE='WEB_CAPTURE' attribute",rootDiv));
			}
			
			MetsDivType lvl2Divs[] = rootDiv.getDivArray();
			
			for(int i = 0; i<lvl2Divs.length;i++){
				if (!lvl2Divs[i].isSetTYPE() || lvl2Divs[i].getTYPE().compareTo(HaSMETSWebProfile.WebCaptureDivision.WEB_RESOURCE.toString())!=0){
					logger.error(XmlError.forObject("The second-level div element of the PRIMARY_STRUCTMAP does not have a TYPE='WEB_RESOURCE' attribute",lvl2Divs[i]));
				}
				this.validatePrimaryStructMapFptr(metsP,pt,lvl2Divs[i]);
				this.validateDependentWebResourceDivs(metsP,pt,lvl2Divs[i]);
			}
			
			
			//TODO: in the base class also check for orphaned files
		}
		
		logger.removeAppender(app);
		return (!app.hasErrors());
	}
	
	
	/**
	 * Validate any DEPENDENT_WEB_RESOURCE divs against the rules in METS profile for Web captures
	 * This method will recursively descend through all subsequent divs and valiate them as well
	 * @param metsP HaSMETSProfile
	 * @param pt PackageType
	 * @param div	the parent div which may contain DEPENDENT_WEB_RESOURCE divs
	 * @return true if it is valid, else false
	 */
	protected boolean validateDependentWebResourceDivs(HaSMETSProfile metsP,PackageType pt, MetsDivType div)  {
		
		HaSMETSAppender app = new HaSMETSAppender("ValidateDependentWebResourceDivs");
		logger.addAppender(app);

		MetsDivType divs[] = div.getDivArray();
		
		for(int i = 0; i<divs.length;i++){
			if (!divs[i].isSetTYPE() || divs[i].getTYPE().compareTo(HaSMETSWebProfile.WebCaptureDivision.DEPENDENT_WEB_RESOURCE.toString())!=0){
				logger.error(XmlError.forObject("The div element of the PRIMARY_STRUCTMAP does not have a TYPE='DEPENDENT_WEB_RESOURCE' attribute",divs[i]));
			}
			this.validatePrimaryStructMapFptr(metsP,pt,divs[i]);
			this.validateDependentWebResourceDivs(metsP,pt,divs[i]);
		}
		
		logger.removeAppender(app);
		return (!app.hasErrors());
	}

	/**
	 * Validate the file elements against the requirements of the Web METS HaS Profile
	 * @param metsP HaSMETSProfile
	 * @param pt PackageType
	 * @return true if it is valid, else false
	 */
	public boolean validateFiles(HaSMETSProfile metsP,PackageType pt, boolean openEachFile)  {
		HaSMETSAppender app = new HaSMETSAppender("ValidateFiles");
		logger.addAppender(app);

		//check all the files
		MetsFileType fls[] = metsP.getAllFiles();

		//test whether this is using the rules from the basic profile or the arc file rules
		boolean arcFiles=((HaSMETSWebProfile)metsP).usesArcFiles();
		
		for(int i =0;i<fls.length;i++){
			//every file must either use the rules for ARC or not, you can't mix them
			if(arcFiles){
				//make sure that every file is using the ARC rules
				if(!fls[i].isSetUSE() || (fls[i].getUSE().compareTo(HaSMETSWebProfile.ArcFileDecomposition.ARC.toString())!=0
					&& fls[i].getUSE().compareTo(HaSMETSWebProfile.ArcFileDecomposition.ARC_URL_RECORD.toString())!=0)){
						logger.error(XmlError.forObject("The file element does not have a USE='ARC' or USE='ARC-URL-RECORD' attribute.  You cannot mix ARC and non-ARC files in a single METS document that conforms to this profile.",fls[i]));
				}
			} else {
				//make sure that none of the files use the ARC rules
				if(fls[i].isSetUSE() && (fls[i].getUSE().compareTo(HaSMETSWebProfile.ArcFileDecomposition.ARC.toString())==0
						|| fls[i].getUSE().compareTo(HaSMETSWebProfile.ArcFileDecomposition.ARC_URL_RECORD.toString())==0)){
					logger.error(XmlError.forObject("The file element has a USE='ARC' or USE='ARC-URL-RECORD' attribute.  You cannot mix ARC and non-ARC files in a single METS document that conforms to this profile.",fls[i]));
				}
			}
			if(fls[i].getFLocatArray().length>0 && fls[i].isSetFContent()){
				logger.error(XmlError.forObject("The file element has both FLocat and FContent sub-elements",fls[i]));
			}
			if(fls[i].getFLocatArray().length>1){
				logger.error(XmlError.forObject("The file element has mutliple FLocat sub-elements",fls[i]));
			}
			//check that the file element has the appropriate children
			MetsFileType.FLocat flocat[]=fls[i].getFLocatArray();
			if ((!fls[i].isSetUSE()
					|| (fls[i].getUSE().compareTo(HaSMETSWebProfile.ArcFileDecomposition.ARC.toString())!=0 
					&& fls[i].getUSE().compareTo(HaSMETSWebProfile.ArcFileDecomposition.ARC_URL_RECORD.toString())!=0)) 
					&& flocat.length!=1 && !fls[i].isSetFContent()){
				logger.error(XmlError.forObject("The file does not have exactly one FLocat or exactly one FContent sub-element",fls[i]));
			} else if (fls[i].isSetUSE() 
					&& fls[i].getUSE().compareTo(HaSMETSWebProfile.ArcFileDecomposition.ARC.toString())==0
					&& flocat.length!=1) {
				logger.error(XmlError.forObject("The ARC file does not have exactly one FLocat sub-element",fls[i]));
			} else if (fls[i].isSetUSE() 
					&& fls[i].getUSE().compareTo(HaSMETSWebProfile.ArcFileDecomposition.ARC.toString())==0
					&& fls[i].isSetFContent()) {
				logger.error(XmlError.forObject("The ARC file contains an FContent sub-element",fls[i]));
			} else if (fls[i].isSetUSE() 
					&& fls[i].getUSE().compareTo(HaSMETSWebProfile.ArcFileDecomposition.ARC_URL_RECORD.toString())==0
					&& (fls[i].isSetFContent() || flocat.length!=0)) {
				logger.error(XmlError.forObject("The ARC-URL-RECORD file contains an FLocat or FContent sub-element",fls[i]));
			} else if (flocat.length==1 ){
				//if there is an FLocat check that it is relative to the METS document
				if (flocat[0].getLOCTYPE()!=MetsFileType.FLocat.LOCTYPE.URL){
					logger.error(XmlError.forObject("The FLocat does not have a LOCTYPE attribute with a value 'URL'",flocat[0]));
				}
				if (!flocat[0].isSetHref()){
					logger.error(XmlError.forObject("The FLocat is missing an xlink:href attribute",flocat[0]));
				} else {
					URI href =null;
					try {
						href= new URI(flocat[0].getHref());
					} catch (URISyntaxException e) {
						logger.error(XmlError.forObject("The xlink:href is not a valid URI",flocat[0].xgetHref()));
					}
					href=href.normalize();
					URI lnk=metsP.getBaseURI().resolve(href).normalize();
					String commonBase = metsP.getBaseURI().normalize().toString();
					commonBase = commonBase.substring(0, commonBase.lastIndexOf("/"));
					if (!lnk.toString().toLowerCase().startsWith(commonBase.toLowerCase())){
						logger.error(XmlError.forObject("This xlink:href value is not relative to the base location of the METS document",flocat[0].xgetHref()));
					}
				}
			}
			//get the input stream of each file and calculate its size and checksum
			long actualSize=0;
			String actualChecksum="";
			boolean fileAccessed=false;
			if(openEachFile){
				try {
					InputStream inpStrm = metsP.getFileInputStream(fls[i]);
					if (inpStrm==null) throw new HaSMETSProfileException("InputStream is null");
					HaSChecksummer summer = new HaSChecksummer();
					CheckedInputStream chkStrm = new CheckedInputStream(inpStrm, summer);
					while(chkStrm.read()!=-1) actualSize++;
					chkStrm.close();
					actualChecksum = summer.getHexEncodedSHA1();
					fileAccessed=true;
				} catch (HaSMETSProfileException e) {
					logger.error(XmlError.forObject("The InputStream associated with the file with ID='" + fls[i].getID() + "' could not be opened",fls[i]));
					logger.error(XmlError.forObject(e.getMessage(),fls[i]));
				} catch (IOException e) {
					logger.error(XmlError.forObject("The InputStream associated with the file with ID='" + fls[i].getID() + "' could not be read",fls[i]));
					logger.error(XmlError.forObject(e.getMessage(),fls[i]));
				}
			}
			
			//check for MIMETYPE
			if (!fls[i].isSetMIMETYPE()) {
				logger.error(XmlError.forObject("The file is missing a MIMETYPE attribute",fls[i]));
			} else if (!fls[i].getMIMETYPE().toLowerCase().startsWith("application") 
					&& !fls[i].getMIMETYPE().toLowerCase().startsWith("audio")
					&& !fls[i].getMIMETYPE().toLowerCase().startsWith("example")
					&& !fls[i].getMIMETYPE().toLowerCase().startsWith("image")
					&& !fls[i].getMIMETYPE().toLowerCase().startsWith("message")
					&& !fls[i].getMIMETYPE().toLowerCase().startsWith("model")
					&& !fls[i].getMIMETYPE().toLowerCase().startsWith("multipart")
					&& !fls[i].getMIMETYPE().toLowerCase().startsWith("text")
					&& !fls[i].getMIMETYPE().toLowerCase().startsWith("video")
					) {
				logger.error(XmlError.forObject("The MIMETYPE attribute does not start with a recognized value",fls[i].xgetMIMETYPE()));
			} else if (fls[i].isSetUSE() 
					&& fls[i].getUSE().compareTo(HaSMETSWebProfile.ArcFileDecomposition.ARC.toString())==0
					&& fls[i].getMIMETYPE().compareToIgnoreCase("application/octet-stream")!=0){
				logger.error(XmlError.forObject("The ARC file does not have a MIMETYPE='application/octet-stream'",fls[i].xgetMIMETYPE()));
			}
			
		
			//check for SIZE
			//TODO: Clarify the profile so that it is clear that the CHECKSUM and SIZE attributes are just
			//for the body of an HTTP response and do not include the HTTP headers.  This is particlarly
			//important for records contained in an ARC file because ARC files treat the size as the whole
			//response and not just the body
			if (!fls[i].isSetSIZE()) {
				logger.error(XmlError.forObject("The file is missing a SIZE attribute",fls[i]));
			} else if (fls[i].getSIZE()<=0) {
				logger.error(XmlError.forObject("This SIZE attribute has a negative or zero value",fls[i].xgetSIZE()));
			} else {
				if(fls[i].isSetFContent() && fls[i].getFContent().isSetXmlData()){
					logger.warn(XmlError.forObject("The SIZE attribute for FContent embedded as xmlData cannot currently be validated",XmlError.SEVERITY_WARNING,fls[i].xgetCHECKSUM()));
				} else if(fileAccessed && fls[i].getSIZE()!=actualSize){
					logger.error(XmlError.forObject("This SIZE attribute does not match the actual size of the file which is " + String.valueOf(actualSize),fls[i].xgetSIZE()));
				}
			}
			//check for CREATED
			if (!fls[i].isSetCREATED()) {
				logger.error(XmlError.forObject("The file is missing a CREATED attribute",fls[i]));
			} else {
				if (metsP.isDateAfterLastModDate(fls[i].getCREATED())) {
					logger.warn(XmlError.forObject("The CREATED date is later than the LASTMODDATE date for this METS document", XmlError.SEVERITY_WARNING,fls[i].xgetCREATED()));
				}
				if (metsP.isDateAfterDate(fls[i].getCREATED(),Calendar.getInstance())) {
					logger.warn(XmlError.forObject("The CREATED date is later than the current date", XmlError.SEVERITY_WARNING,fls[i].xgetCREATED()));
				}
			}
			//check for checksum	
			if (!fls[i].isSetCHECKSUM()) {
				if(pt!=HaSMETSProfile.PackageType.SIP){
					//TODO:  This may become required for all types
					logger.error(XmlError.forObject("The file is missing a CHECKSUM attribute",fls[i]));
				}
			} else if (!fls[i].isSetCHECKSUMTYPE() || fls[i].getCHECKSUMTYPE()!=MetsFileType.CHECKSUMTYPE.SHA_1) {
				logger.error(XmlError.forObject("The CHECKSUMTYPE attribute does not have a value of 'SHA-1'",fls[i].xgetCHECKSUMTYPE()));
			} else if (fls[i].getCHECKSUM().trim().length()!=40) {
				logger.error(XmlError.forObject("The CHECKSUM attribute is not a 40-digit hexadecimal number",fls[i].xgetCHECKSUM()));
			} else {
				try {
					XmlHexBinary hex = XmlHexBinary.Factory.parse("<xml-fragment>" + fls[i].getCHECKSUM() + "</xml-fragment>");
					if(!hex.validate()){
						logger.error(XmlError.forObject("The CHECKSUM attribute is not a 40-digit hexadecimal number",fls[i].xgetCHECKSUM()));
					}
				} catch (XmlException e) {
					logger.error(XmlError.forObject("The CHECKSUM attribute is not a 40-digit hexadecimal number",fls[i].xgetCHECKSUM()));
				}
				if (fls[i].isSetFContent() && fls[i].getFContent().isSetXmlData()){
					logger.warn(XmlError.forObject("The CHECKSUM attribute for FContent embedded as xmlData cannot currently be validated",XmlError.SEVERITY_WARNING,fls[i].xgetCHECKSUM()));
				}else if (fileAccessed && fls[i].getCHECKSUM().compareToIgnoreCase(actualChecksum)!=0){
					logger.error(XmlError.forObject("This CHECKSUM attribute does not match the actual checksum of the file which is " + actualChecksum,fls[i].xgetCHECKSUM()));
				}
			}
			
			//check that each file references a techMD premis object
			if (!fls[i].isSetADMID()){
				if(pt!=HaSMETSProfile.PackageType.SIP){
					logger.error(XmlError.forObject("The file is missing an ADMID attribute",fls[i]));
				}
			} else {
				this.validateTechMDPremisObjectForFile(metsP,pt,fls[i]);
				this.validateDigiprovMDPremisEventForFile(metsP,pt,fls[i]);
			}
			
		}
		
		logger.removeAppender(app);
		return (!app.hasErrors());
	}
}
