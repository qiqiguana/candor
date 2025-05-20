/**
 * Fedora2HubPackager.java
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

package edu.uiuc.ndiipp.hubandspoke.packager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.w3c.dom.Node;

import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfile;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfileException;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfileFactory;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMasterMETSProfile;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMasterMETSProfileFactory;
import edu.uiuc.ndiipp.hubandspoke.profile.METSProfile;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSConstants;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSZipUtils;
import gov.loc.mets.MetsFileType;
import gov.loc.mets.MetsMdSecType;
import gov.loc.mets.MetsMetsDocument;
import gov.loc.mods.ModsModsDocument;
import gov.loc.premis.PremisObjectDocument;

/**
 * @author Bill Ingram
 * 
 */
public class Fedora2HubPackager extends ToHubPackager {

	private static Logger log = Logger.getLogger(Fedora2HubPackager.class
			.getName());

	private File exportdir;
	private MetsMetsDocument fedoramets;
	private HaSMETSProfile echodepmets;
	private HaSMasterMETSProfile mastermets;

	/**
	 * 
	 */
	public Fedora2HubPackager() {
		super();
	}

	public boolean createHubPackage(ZipInputStream zip, String unzippath,
			String hubpath) throws PackagerException {
		try {
			return createHubPackage(HaSZipUtils.unzip(zip, unzippath), hubpath);
		} catch (Exception e) {
			throw new PackagerException("Error creating hub package.", e);
		}
	}

	@Override
	public boolean createHubPackage(ZipFile zip, String unzippath,
			String hubpath) throws PackagerException {
		try {
			return createHubPackage(HaSZipUtils.unzip(zip, unzippath), hubpath);
		} catch (Exception e) {
			throw new PackagerException("Error creating hub package.", e);
		}
	}

	public boolean createHubPackage(String zippath, String unzippath,
			String hubpath) throws PackagerException, IOException {
		return createHubPackage(new ZipFile(zippath), unzippath, hubpath);
	}

	public boolean createHubPackage(File dir, String hubpath)
			throws PackagerException, FileNotFoundException {
		try {
			exportdir = dir;
			mastermets = findMasterMETS();

			// FIXME: Get the objid -- 
			// you need this to figure out what the METS file will be named
//			String pid = mastermets.getMostRecentEchoDepMETS()
//					.getMetsDocument().getMets().getOBJID();
			
			String pid = dir.getName();
			
			fedoramets = findFedoraMETS(dir, pid);

			if (mastermets != null) {
				log.info("Found Echodep Master METS");

			} else {
				File foundmets = new File(exportdir.getAbsolutePath()
						+ File.separator + HaSConstants.METS_FILE_NAME);

				if (foundmets.exists()) {
					log.info("Found Echodep METS");
					log.info("Creating new Echodep Master METS");
					mastermets = HaSMasterMETSProfileFactory.newInstance();
					mastermets.addMptr(foundmets.toURI());

				} else {
					log.info("Creating new Echodep Master METS");
					mastermets = HaSMasterMETSProfileFactory.newInstance();
				}
			}
			log.info("Creating new Echodep METS from scratch");
			echodepmets = createFromScratch();
			log.info("Saving new Echodep METS to "
					+ exportdir.getAbsolutePath() + File.separator
					+ HaSConstants.METS_FILE_NAME);
			echodepmets.save(exportdir.getAbsolutePath() + File.separator
					+ HaSConstants.METS_FILE_NAME);

			log.info("Adding new Echodep METS to Master METS");
			mastermets.addMptr(new File(exportdir.getAbsolutePath()
					+ File.separator + HaSConstants.METS_FILE_NAME).toURI());

			log.info("Saving Master METS to " + exportdir.getAbsolutePath()
					+ File.separator + HaSConstants.MASTER_METS_FILE_NAME);
			File file = new File(exportdir.getAbsolutePath() + File.separator
					+ HaSConstants.MASTER_METS_FILE_NAME);
			mastermets.getMetsDocument().save(file);
			
			// remove Fedora METS
//			boolean clean = new File(dir.getAbsolutePath() + File.separatorChar
//					+ pid.replace(":", "_") + ".xml").delete();
//			
//			if (!clean) {
//				log.warn("Cannot delete Fedora METS: " +
//							dir.getAbsolutePath() + 
//							File.separatorChar	+ 
//							pid.replace(":", "_") + 
//							".xml");
//			}

		} catch (HaSMETSProfileException e) {
			throw new PackagerException(e.getMessage());
		} catch (IOException e) {
			throw new PackagerException(e.getMessage());
		}

		return true;
	}

	private MetsMetsDocument findFedoraMETS(File dir, String pid)
			throws FileNotFoundException, PackagerException {
		File f = new File(dir.getAbsolutePath() + File.separatorChar
				+ pid.replace(":", "_") + ".xml");
		if (!f.exists()) {
			log.error("Can't find the file we need");
			throw new FileNotFoundException("The Fedora export file ("
					+ pid.replace(":", "_") + ") was not found at "
					+ dir.getAbsolutePath());
		} else {
			MetsMetsDocument mets = null;
			try {
				mets = MetsMetsDocument.Factory.parse(f);
			} catch (Exception e) {
				throw new PackagerException(
						"Problem opening the Dspace export "
								+ pid.replace(":", "_"));
			}
			return mets;
		}
	}

	private HaSMasterMETSProfile findMasterMETS()
			throws HaSMETSProfileException {
		HaSMasterMETSProfile mastermets = null;
		File newfile = new File(exportdir.getAbsolutePath() + File.separator
				+ HaSConstants.MASTER_METS_FILE_NAME);
		if (newfile.exists()) {
			mastermets = HaSMasterMETSProfileFactory.newInstance(newfile);
		}
		return mastermets;
	}
	
	/**
	 * Creates a new HaSMETSProfile from the supplied files and adds the
	 * pertinent metadata from the DSpace METS
	 * 
	 * @return HasMETSProfile
	 * @throws PackagerException
	 * @throws HaSMETSProfileException
	 */
	private HaSMETSProfile createFromScratch() throws PackagerException,
			HaSMETSProfileException {
		log.info("Creating a Hub package from scratch");

		// create a new HaSMETSProfile instance
		echodepmets = HaSMETSProfileFactory
				.newHaSMETSProfile(HaSMETSProfile.PackageType.AIP);

		// set the base url of this to be the location where the Fedora export
		// was unzipped
		echodepmets.setBaseURI(new File(exportdir.toString() + File.separator
				+ HaSConstants.METS_FILE_NAME).toURI());

		// get the Mods supplied by DSpace
		ModsModsDocument mods = extractMODS();

		// remove the xmlData section and replace it with the extracted Fedora
		// MODS
		Node xmlData = echodepmets.getMetsDocument().getMets().getDmdSecArray()[0]
				.getMdWrap().getXmlData().getDomNode();
		xmlData.removeChild(xmlData.getFirstChild());
		Node mod = xmlData.getOwnerDocument().importNode(
				mods.getMods().getDomNode(), true);
		xmlData.appendChild(mod);

		// make the metadata conform
		echodepmets.convertPrimaryDmdSecToAquifer();

		// create the new FileSec and merge PREMIS from DSpace
		createNewFileSec();

		// extract a Label for the METS file from the MODS title element
		echodepmets.setLabelFromPrimaryDmdSec();

		// set the handle as an alt. record ID
		// recordHandleAsAltRecordID();

		// set the OBJID of the HaSMETSProfile
		setOBJID();

		// add techMD
		// skip this for now, only run JHOVE if there are
		// checksum or filesize conflicts later in the process
		// TechMDAugmenter.addJhoveToProfile(echodepmets);

		return echodepmets;
	}


	/**
	 * Pulls out the MODS that is embedded in the Fedora METS export.
	 * 
	 * @return ModsModsDocument -- the parsed MODS as an XMLBeans object
	 * @throws PackagerException
	 */
	private ModsModsDocument extractMODS() throws PackagerException {
		ModsModsDocument mods = null;
		XmlObject xobj[] = fedoramets.selectPath("declare namespace mods='"
				+ METSProfile.MODS_NS + "'; //mods:mods");
		if (xobj.length > 0) {
			try {
				mods = ModsModsDocument.Factory.parse(xobj[0].getDomNode());
			} catch (XmlException e) {
				throw new PackagerException(
						"Problem parsing MODS document from Fedora "
								+ e.getMessage());
			}
		}
		return mods;
	}
	
	
	@SuppressWarnings("unchecked")
	private void createNewFileSec() throws HaSMETSProfileException,
			PackagerException {
		log.info("Creating a new FileSec");

		XmlObject files[] = fedoramets.selectPath("declare namespace mets='"
				+ METSProfile.METS_NS + "'; //mets:file");

		for (int i = 0; i < files.length; i++) {
			MetsFileType mft = (MetsFileType) files[i];
			String filename = mft.getFLocatArray(0).getTitle();
			filename = filename.replace("\\", "/");
			filename = filename.substring(filename.lastIndexOf("/") + 1);

			if (!(filename.compareToIgnoreCase(HaSConstants.MASTER_METS_FILE_NAME) == 0
					|| filename.contains(HaSConstants.METS_FILE_NAME_PREFIX))) {
				// add the file to the HaSProfile
				MetsFileType echodepfile = echodepmets.addFile(new File(exportdir
						+ File.separator + filename));
				echodepmets.getPrimaryStructMap().getDiv().addNewFptr().setFILEID(
						echodepfile.getID());
			}
		}

	}

	/**
	 * Sets the OBJID of the HaSMETSProfile and cleans up some of the
	 * boilerplate created by the constructor
	 * 
	 * @throws HaSMETSPackagerException
	 */
	private void setOBJID() throws PackagerException {
		try {

			// String id =
			// echodepmets.getBaseURI().toURL().toString()+METS_FILE_NAME;
			String id = fedoramets.getMets().getOBJID();
			echodepmets.setNewPrimaryID(id,
					HaSMETSProfile.PREMISIdentifierType.HANDLE);

			// remove the boilerplate ID values
			XmlObject objs[] = echodepmets.getMetsDocument().selectPath(
					"declare namespace m='" + METSProfile.METS_NS
							+ "'; //m:altRecordID[.='[IDENTIFIER]']");

			for (int i = 0; i < objs.length; i++) {
				XmlObject object = objs[i];
				object.getDomNode().getParentNode().removeChild(
						object.getDomNode());
			}

			MetsMdSecType prim = echodepmets.getPrimaryRepresentation();
			XmlObject idents[] = prim
					.selectPath("declare namespace p='"
							+ METSProfile.PREMIS_NS
							+ "'; //p:objectIdentifier[p:objectIdentifierValue[.='[IDENTIFIER]']]");
			for (int i = 0; i < idents.length; i++) {
				XmlObject ident = idents[i];
				ident.getDomNode().getParentNode().removeChild(
						ident.getDomNode());
			}

		} catch (Exception e) {
			throw new PackagerException(
					"Problem setting the OBJID of the echodepmets.xml file"
							+ e.getMessage());
		}

	}
}
