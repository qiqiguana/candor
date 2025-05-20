/**
 * HaSMasterMETSProfile.java
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest ;
import java.util.Calendar;
import java.util.zip.CheckedInputStream;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

import edu.harvard.hul.ois.mets.AltRecordID;
import edu.uiuc.ndiipp.hubandspoke.lrcrud.client.LrcrudClient.DeleteResponse;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSChecksummer;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSConstants;

import gov.loc.mets.MetsAmdSecType;
import gov.loc.mets.MetsDivType;
import gov.loc.mets.MetsFileType;
import gov.loc.mets.MetsMdSecType;
import gov.loc.mets.MetsMetsDocument;
import gov.loc.mets.MetsMetsType;
import gov.loc.mets.MetsStructMapType;
import gov.loc.mets.MetsDivType.Mptr;
import gov.loc.mets.MetsDivType.Mptr.LOCTYPE;
import gov.loc.premis.PremisObjectDocument;
import gov.loc.premis.PremisObjectDocument.Object.ObjectCharacteristics.Fixity;

/**
 * This class represents a "Master" METS XML document that conforms to the
 * ECHODep Master METS profile. It wraps various XMLBeans class libraries for
 * the different XML Schema used by the profile.
 * 
 * @author Bill Ingram
 * 
 */
public class HaSMasterMETSProfile extends METSProfile {

	public static void main(String[] args) {
		HaSMasterMETSProfile hmp = null;
		hmp = HaSMasterMETSProfileFactory.newHaSMasterMETSProfile();
		System.out.println(hmp.getMetsDocument().xmlText(
				HaSMETSProfile.defaultSaveOptions));
	}

	// Initialize some stuff
	{
		// TODO: change this to the real url
		PROFILE_URI = "http://www.loc.gov/mets/profiles/000000.xml";
	}

	/**
	 * Construct a minimally-conforming METS document
	 */
	protected HaSMasterMETSProfile() {
		super();
		metsDoc.getMets().setPROFILE(this.PROFILE_URI);
		initIDs();
	}

	/**
	 * Create a new Master METS object from the given MetsMetsDocument with the
	 * given baseURI
	 * 
	 * @param mets
	 *            MetsMetsDocument
	 * @param uri
	 *            URI
	 */
	protected HaSMasterMETSProfile(MetsMetsDocument mets, URI uri) {
		metsDoc = mets;
		baseURI = uri;
		initIDs();
	}

	public MetsDivType addMptr(URI uri) throws HaSMETSProfileException {
		try {
			File mets = new File(uri);

			HaSMETSProfile newmets = HaSMETSProfileFactory.newInstance(mets);
			HaSMETSProfile oldmets = getMostRecentEchoDepMETS();
			if (oldmets != null) {
				newmets.compareFiles(oldmets);
				newmets.save();
			}

			int index = metsDoc.getMets().getStructMapArray(0).getDiv()
					.getDivArray().length;
			if (index == 0) {
				try {
					this.baseURI = new URI(newmets.getBaseURIMinusFile()
							+ "mastermets.xml");
				} catch (URISyntaxException e) {
					throw new HaSMETSProfileException(
							"Error extracting base URI from path. "
									+ e.getMessage());
				}
			} else {
				URI link = this.getBaseURI().resolve(uri.normalize())
						.normalize();
				String commonBase = this.getBaseURIMinusFile().toString();
				if (!link.toString().toLowerCase().startsWith(
						commonBase.toLowerCase())) {
					throw new HaSMETSProfileException(
							"The URI value of the file you are trying to add is not relative "
									+ "to the base location of the Master METS document: "
									+ this.getBaseURIMinusFile());
				}
			}

			File newname = new File(new URI(newmets.getBaseURIMinusFile()
					+ HaSConstants.METS_FILE_NAME_PREFIX + index + ".xml"));
			mets.renameTo(newname);
			String relpath = this.getBaseURIMinusFile().relativize(
					newname.toURI()).toString();
			MetsDivType div = metsDoc.getMets().getStructMapArray(0).getDiv()
					.addNewDiv();
			Mptr mptr = div.addNewMptr();
			mptr.setLOCTYPE(LOCTYPE.URL);
			mptr.setHref(relpath);

			MetsMdSecType mdsec = this.newPREMISObjectTechMD(mptr,
					HaSMETSProfile.PREMISIdentifierType.URL, relpath);
			div.setORDER(BigInteger.valueOf(metsDoc.getMets()
					.getStructMapArray(0).getDiv().getDivArray().length));
			setLastModifiedToCurrent();

			this.getMetsDocument().getMets().setLABEL(
					newmets.getMetsDocument().getMets().getLABEL());

			if (newmets.getMetsDocument().getMets().getOBJID() != null) {
				String objid = newmets.getMetsDocument().getMets().getOBJID();
				this.getMetsDocument().getMets().setOBJID(objid);
				this.getMetsDocument().getMets().getMetsHdr()
						.addNewAltRecordID();
				this.getMetsDocument().getMets().getMetsHdr()
						.getAltRecordIDArray(
								this.getMetsDocument().getMets().getMetsHdr()
										.getAltRecordIDArray().length - 1)
						.setStringValue(objid);
			}
			
			// XmlObject[] techmds = this
			// .getMetsDocument()
			// .selectPath(
			// "declare namespace m='"
			// + HaSMETSProfile.METS_NS
			// + "'; .//m:techMD[@STATUS='PRIMARY_REPRESENTATION']");
			// MetsMdSecType techmd = (MetsMdSecType) techmds[0];
			// PremisObjectDocument primRepObj = (PremisObjectDocument) this
			// .getMDSecXmlObject(techmd);
			// primRepObj.getObject().getObjectIdentifierArray(0).
			// setObjectIdentifierValue(objid);

			save();
			return div;

		} catch (URISyntaxException e) {
			e.printStackTrace();
			throw new HaSMETSProfileException(e.getMessage());
		}
	}

	// public void resetBaseURI(URI uri) throws HaSMETSProfileException {
	// try {
	// this.baseURI = uri;
	// MetsDivType[] divs =
	// this.getMetsDocument().getMets().getStructMapArray(0)
	// .getDiv().getDivArray();
	//			
	// }
	// }

	/**
	 * Returns the most recently-added echodepmets file, if one exists, or null
	 * 
	 */
	public HaSMETSProfile getMostRecentEchoDepMETS()
			throws HaSMETSProfileException {
		HaSMETSProfile echodepmets = null;
		MetsDivType[] divs = metsDoc.getMets().getStructMapArray(0).getDiv()
				.getDivArray();
		if (divs.length > 0) {
			try {
				echodepmets = HaSMETSProfileFactory
						.newInstance(baseURI
								.resolve(
										divs[divs.length - 1].getMptrArray(0)
												.getHref()).toURL());
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new HaSMETSProfileException(e.getMessage());
			}
		}
		return echodepmets;
	}

	/**
	 * Validates that all &lt;Mptr&gt;s are valid
	 * 
	 * @return
	 */
	public boolean validate() {
		for (Mptr mptr : this.getAllMptrs()) {
			String path = mptr.getHref();
			URI uri = baseURI.resolve(path);
			File f = new File(uri);
			if (!f.exists()) {
				logger.error("File not found: " + f.getPath());
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Update the checksum of latest MasterMets file
	 * 
	 * @return
	 */
	public boolean update_checksum_latest_mets() 
		throws HaSMETSProfileException {

		// first obtain the latest mets file
		// HaSMETSProfile echodepmets = getMostRecentEchoDepMETS() ;

		// convert mets object to file
		// File f = new File(echodepmets.getBaseURI()) ;
  
		// Open the file and analyze its contents
		// int size = 0;
		// Calendar lastModified = Calendar.getInstance();
		// lastModified.setTimeInMillis(f.lastModified());
		// HaSChecksummer summer = null;
		// try {
		// 	FileInputStream inpStrm = new FileInputStream(f);
		//	summer = new HaSChecksummer();
		//	CheckedInputStream chkStrm = new CheckedInputStream(inpStrm, summer);
		//	while (chkStrm.read() != -1)
		//		size++;
		//	chkStrm.close();
		// } catch (FileNotFoundException e) {
		//	throw new RuntimeException("File not found '" + f.getPath()
		//			+ "'", e);
		// } catch (IOException e) {
		//	throw new RuntimeException("Unable to read file '"
		//			+ f.getAbsolutePath() + "'", e);
		// }
		// String sha1 = summer.getHexEncodedSHA1();
		// String md5 = summer.getHexEncodedMD5();

		// Now, update size and sha1 & md5

		PremisObjectDocument.Object pObj;
		try {
			pObj = PremisObjectDocument.Factory.
					parse(this.getXmlDataAny(this.getMetsDocument().getMets().getAmdSecArray(0).
					getTechMDArray(0).getMdWrap().getXmlData())).getObject();

		    // update size
		    pObj.getObjectCharacteristicsArray(0).setSize(size) ;
				
		    Fixity baseObj ; 
            int i = 0 ;
            // only need to go through loop twice (for both digests)        
		    while (i < 1) {
                baseObj = pObj.getObjectCharacteristicsArray(0).getFixityArray(i) ;
		    	if ( baseObj.getMessageDigestAlgorithm().contentEquals("md5") ) {
	  	          baseObj.setMessageDigest(md5) ;
		       } else if ( baseObj.getMessageDigestAlgorithm().contentEquals("sha-1") ) {
	  	          baseObj.setMessageDigest(sha1) ;
	 	      } ;
              ++i ;
		   } 
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true ;
	}
}
