/**
 * Dspace2HubPackager.java
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
 * Converts the output of a DSpace export into a Hub compatible package.
 * 
 * If a previous echodepmets.xml file exists it attempts to combine and
 * reconcile the metadata otherwise it creates a HaSProfile from scratch.
 * 
 * @author Matt Cordial
 * 
 */
public class Dspace2HubPackager extends ToHubPackager {

	private static Logger log = Logger.getLogger(Dspace2HubPackager.class
			.getName());
	public static final String DS_PREMIS_NS = "http://www.loc.gov/standards/premis";
	public static final String DSPACE_METS_FILE_NAME = "mets.xml";

	private File exportdir;
	private MetsMetsDocument dspacemets;
	private HaSMETSProfile echodepmets;
	private HaSMasterMETSProfile mastermets;

	public Dspace2HubPackager() {
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

	/**
	 * Creates a Hub Package from a DSpace dissemination by locating the Master
	 * METS file for the package or creating a new one if it cannot be found. A
	 * new EchoDep METS document is generated from the DSpace export METS. It is
	 * compared to the most-recent EchoDep METS and any changes to the package
	 * are noted (i.e., file names, sizes, and mime types).
	 * 
	 * @param dir
	 *            - the directory where the package will live
	 * @param hubpath
	 *            -
	 * @return - true if the Hub Package creation was successful
	 * @throws PackagerException
	 * @throws FileNotFoundException
	 */
	public boolean createHubPackage(File dir, String hubpath)
			throws PackagerException, FileNotFoundException {
		try {
			exportdir = dir;
			dspacemets = findDspaceMETS(dir);
			renameToOriginalFileNames();
			mastermets = findMasterMETS();

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

		} catch (HaSMETSProfileException e) {
			throw new PackagerException(e.getMessage());
		} catch (IOException e) {
			throw new PackagerException(e.getMessage());
		}

		return true;
	}

	/**
	 * Searches the directory that contains the unzipped DSpace export for the
	 * file "mets.xml"
	 * 
	 * @param dir
	 *            - directory to search
	 * @return MetsMetsDocument -- XMLBeans METS document
	 * @throws FileNotFoundException
	 * @throws PackagerException
	 */
	private MetsMetsDocument findDspaceMETS(File dir)
			throws FileNotFoundException, PackagerException {
		File f = new File(dir.getAbsolutePath() + File.separatorChar
				+ DSPACE_METS_FILE_NAME);
		if (!f.exists()) {
			log.error("Can't find the file we need");
			throw new FileNotFoundException("The DSpace export file ("
					+ DSPACE_METS_FILE_NAME + ") was not found at "
					+ dir.getAbsolutePath());
		} else {
			MetsMetsDocument mets = null;
			try {
				mets = MetsMetsDocument.Factory.parse(f);
			} catch (Exception e) {
				throw new PackagerException(
						"Problem opening the Dspace export "
								+ DSPACE_METS_FILE_NAME);
			}
			return mets;
		}
	}

	/**
	 * Instantiates and returns a HaSMETSProfile if echodepmets.xml is found,
	 * null otherwise
	 * 
	 * @return HaSMETSProfile - HaSMETSProfile if echodepmets.xml is found, null
	 *         otherwise
	 * @throws HaSMETSProfileException
	 */
	private HaSMETSProfile findEchoDepMETS() throws HaSMETSProfileException {
		HaSMETSProfile echodep = null;
		File newfile = new File(exportdir.getAbsolutePath() + File.separator
				+ HaSConstants.METS_FILE_NAME);
		if (newfile.exists()) {
			echodep = HaSMETSProfileFactory.newInstance(newfile);
		}
		// XmlObject xobj[] = dspacemets.selectPath("declare namespace p='" +
		// DS_PREMIS_NS +
		// "'; declare namespace m='" + HaSMETSProfile.METS_NS +
		// "';
		// //m:amdSec[.//p:originalName[.='"+HaSConstants.METS_FILE_NAME+"']]");
		// if (xobj.length > 0){
		// MetsAmdSecType amd = (MetsAmdSecType)xobj[0];
		//			
		// String xql = "declare namespace m='" + HaSMETSProfile.METS_NS + "';
		// //m:file[@ADMID='"+amd.getID()+"']";
		// XmlObject xobj2[] = dspacemets.selectPath(xql);
		// if (xobj2.length > 0){
		// MetsFileType file = (MetsFileType) xobj2[0];
		// FLocat floc = file.getFLocatArray(0);
		// echodep =
		//HaSMETSProfileFactory.newInstance(exportdir+File.separator+floc.getHref
		// ());
		// }
		//			
		// }
		return echodep;
	}

	/**
	 * Instantiates and returns a HaSMasterMETSProfile if mastermets.xml is
	 * found, null otherwise
	 * 
	 * @return HaSMasterMETSProfile - HaSMETSProfile if master.xml is found,
	 *         null otherwise
	 * @throws HaSMETSProfileException
	 */
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
	 * Pulls out the MODS that is embedded in the DSpace METS export.
	 * 
	 * @return ModsModsDocument -- the parsed MODS as an XMLBeans object
	 * @throws PackagerException
	 */
	private ModsModsDocument extractDSMODS() throws PackagerException {
		ModsModsDocument mods = null;
		XmlObject xobj[] = dspacemets.selectPath("declare namespace mods='"
				+ METSProfile.MODS_NS + "'; //mods:mods");
		if (xobj.length > 0) {
			try {
				mods = ModsModsDocument.Factory.parse(xobj[0].getDomNode());
			} catch (XmlException e) {
				throw new PackagerException(
						"Problem parsing MODS document from DSpace "
								+ e.getMessage());
			}
		}
		return mods;
	}

	/**
	 * Finds the original names of the files in the PREMIS and renames them in
	 * both the DSpace METS file and on the file system. This is because DSpace
	 * renames them during export and we would prefer to see the orig. filename
	 * in the Hub.
	 * 
	 */
	private void renameToOriginalFileNames() {

		XmlObject files[] = dspacemets.selectPath("declare namespace mets='"
				+ METSProfile.METS_NS + "'; //mets:file");
		for (int i = 0; i < files.length; i++) {
			MetsFileType file = (MetsFileType) files[i];
			String filename = file.getFLocatArray(0).getHref();
			String admid = (String) file.getADMID().get(0);
			XmlObject ons[] = dspacemets.selectPath("declare namespace m='"
					+ METSProfile.METS_NS + "'; declare namespace p='"
					+ DS_PREMIS_NS + "'; //m:amdSec[@ID='" + admid
					+ "']//p:originalName");
			String origname = ons[0].getDomNode().getFirstChild()
					.getNodeValue();
			origname = origname.replace("\\", "/");
			origname = origname.substring(origname.lastIndexOf("/") + 1);
			File newfile = new File(exportdir.getAbsolutePath()
					+ File.separator + origname);

			int next = 0;
			while (newfile.exists()) {
				origname = (++next) + "_"
						+ ons[0].getDomNode().getFirstChild().getNodeValue();
				newfile = new File(exportdir.getAbsolutePath() + File.separator
						+ origname);
			}

			File refile = new File(exportdir.getAbsolutePath() + File.separator
					+ filename);
			refile.renameTo(newfile);

			// update the flocat href to the new name
			file.getFLocatArray(0).setHref(origname);
		}
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

		// set the base url of this to be the location where the DSpace export
		// was unzipped
		echodepmets.setBaseURI(new File(exportdir.toString() + File.separator
				+ HaSConstants.METS_FILE_NAME).toURI());

		// get the Mods supplied by DSpace
		ModsModsDocument dsmods = extractDSMODS();

		// remove the xmlData section and replace it with the extracted dspace
		// MODS
		Node xmlData = echodepmets.getMetsDocument().getMets().getDmdSecArray()[0]
				.getMdWrap().getXmlData().getDomNode();
		xmlData.removeChild(xmlData.getFirstChild());
		Node mod = xmlData.getOwnerDocument().importNode(
				dsmods.getMods().getDomNode(), true);
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

	@SuppressWarnings("unchecked")
	private void createNewFileSec() throws HaSMETSProfileException,
			PackagerException {
		log.info("Creating a new FileSec");

		XmlObject files[] = dspacemets.selectPath("declare namespace mets='"
				+ METSProfile.METS_NS + "'; //mets:file");

		for (int i = 0; i < files.length; i++) {
			MetsFileType dsfile = (MetsFileType) files[i];
			String filename = dsfile.getFLocatArray(0).getHref();
			filename = filename.replace("\\", "/");
			filename = filename.substring(filename.lastIndexOf("/") + 1);
			String admid = (String) dsfile.getADMID().get(0);

			// add the file to the HaSProfile
			MetsFileType echodepfile = echodepmets.addFile(new File(exportdir
					+ File.separator + filename));
			echodepmets.getPrimaryStructMap().getDiv().addNewFptr().setFILEID(
					echodepfile.getID());

			// get the DSpace PREMIS for the file
			XmlObject dspremisobjs[] = dspacemets
					.selectPath("declare namespace m='" + METSProfile.METS_NS
							+ "'; declare namespace p='" + DS_PREMIS_NS
							+ "'; //m:amdSec[@ID='" + admid + "']//p:object");

			// associate all of the related PREMIS objs to the file....
			for (int j = 0; j < dspremisobjs.length; j++) {
				try {
					// need to tell the parser that there will be 2 different
					// namespaces
					// one supplied by DSpace and one compiled into the PREMIS
					// XMLBeans
					Map namemap = new HashMap<String, String>();
					namemap.put(DS_PREMIS_NS, METSProfile.PREMIS_NS);
					XmlOptions xopts = new XmlOptions();
					xopts.setLoadSubstituteNamespaces(namemap);
					PremisObjectDocument dspobj = PremisObjectDocument.Factory
							.parse(dspremisobjs[j].getDomNode(), xopts);

					// //merge the DSpace PREMIS objects for the file into the
					// new HaSProfile
					//					
					// //get the techmds for the file
					// MetsMdSecType techmds[] =
					// echodepmets.getTechMDsForFile(echodepfile);
					//					
					// //for each techmd get the PREMIS object -- should only be
					// one
					// for (int k = 0; k < techmds.length; k++) {
					// HaSXMLMergeUtils.mergePremisTechMd(techmds[k], dspobj);
					//						
					// }

					echodepmets.addTechMDXmlObjectToFile(echodepfile, dspobj);
				} catch (XmlException e) {
					throw new PackagerException(
							"Problem parsing the PREMIS from DSpace"
									+ e.getMessage());
					// } catch (HaSXMLMergeException e) {
					// throw new PackagerException("Problem merging the PREMIS
					// from DSpace for file: " + filename + ": " +
					// e.getMessage());
				}
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
			String id = dspacemets.getMets().getOBJID();
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
