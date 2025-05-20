/**
 * Eprints2HubPackager.java
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSAppender;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfile;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfileException;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfileFactory;
import edu.uiuc.ndiipp.hubandspoke.profile.METSProfile.PREMISEventType; //import edu.uiuc.ndiipp.hubandspoke.profile.METSProfile.PREMISIdentifierType;
import edu.uiuc.ndiipp.hubandspoke.profile.METSProfile.PREMISLinkingAgentRole;
import edu.uiuc.ndiipp.hubandspoke.profile.METSProfile.PackageType;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMasterMETSProfile;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMasterMETSProfileFactory; // import edu.uiuc.ndiipp.hubandspoke.profile.METSProfile;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSConstants;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSZipUtils; // import edu.uiuc.ndiipp.hubandspoke.utils.jhove.TechMDAugmenter;
import gov.loc.mets.MetsFileType;
import gov.loc.mets.MetsMdSecType; // import gov.loc.mets.MetsMetsDocument;
import gov.loc.mods.ModsModsDocument;
import gov.loc.premis.PremisAgentDocument;
import gov.loc.premis.PremisEventDocument;

// import gov.loc.premis.PremisObjectDocument;

/**
 * Converts the output of an Eprints export into a Hub compatible package.
 * 
 * If a previous echodepmets.xml file exists it attempts to combine and
 * reconcile the metadata otherwise it creates a HaSProfile from scratch.
 * 
 * @author Matt Cordial
 * 
 */
public class Eprints2HubPackager extends ToHubPackager {

	private static Logger log = Logger.getLogger(Eprints2HubPackager.class
			.getName());
	public static final String EP_PREMIS_NS = "http://www.loc.gov/standards/premis";
	public static final String EPRINTS_FILE_NAME = "eprints.xml";

	private File exportdir;
	private HaSMETSProfile echodepmets;
	private HaSMasterMETSProfile mastermets;

	// Options for loading or Saving XML into XMLBeans
	static XmlOptions defaultLoadOptions = new XmlOptions()
			.setLoadStripWhitespace().setLoadLineNumbers();

	public Eprints2HubPackager() {
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
	 * Creates a Hub Package from an Eprints dissemination by locating the
	 * Master METS file for the package or creating a new one if it cannot be
	 * found. When a new EchoDep METS document is generated, it is compared to
	 * the most-recent EchoDep METS and any changes to the package are noted
	 * (i.e., file names, sizes, and mime types).
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

			echodepmets = createFromScratch(dir);
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
	 * Searches the directory that contains the unzipped EPrints export for the
	 * file "eprints.xml"
	 * 
	 * @param dir
	 *            - directory to search
	 * @return StreamSource -- XML Stream Source of eprints.xml file
	 * @throws FileNotFoundException
	 * @throws PackagerException
	 */
	private File findEprintsXml(File dir) throws FileNotFoundException,
			PackagerException {
		File f = new File(dir.getAbsolutePath() + File.separatorChar
				+ EPRINTS_FILE_NAME);
		if (!f.exists()) {
			log.error("Can't find the file we need");
			throw new FileNotFoundException("The EPrints export file ("
					+ EPRINTS_FILE_NAME + ") was not found at "
					+ dir.getAbsolutePath());
		} else {
			return f;
		}
	}

	/**
	 * Pulls out the MODS that is transformed from the Eprints export.
	 * 
	 * @param dir
	 *            - Eprints input file
	 * @return ModsModsDocument -- the parsed MODS as an XMLBeans object
	 * @throws PackagerException
	 */
	private ModsModsDocument extractEPMODS(File eprintsXmlFile)
			throws PackagerException {
		ModsModsDocument mods = null;

		// FIXME pulling the location of the HaS installation from the
		// environmental variable for now
		// FIXME may want to use config files in the future
		File xslt = new File(System.getenv("HS_HOME") + File.separatorChar
				+ "xslt" + File.separatorChar + "eprints2mods.xsl");

		/* For testing... */
		/*
		 * File xslt = new
		 * File(output_dir+File.separatorChar+"eprints2mods.xsl") ;
		 */

		StreamSource xsltsource = new StreamSource(xslt);
		// get the MODS to which Eprints will transform into
		Source xmlSource = new StreamSource(eprintsXmlFile);
		// DOMSource xmlSource = new DOMSource(mods.getDomNode());

		try {
			TransformerFactory tfact = TransformerFactory.newInstance();
			Transformer tform = tfact.newTransformer(xsltsource);

			// we will write the transform to a DOM Tree because JAXP does not
			// want to allow
			// multiple transforms on a single source
			// Write a Result to a DOM tree (inserted into the supplied
			// Document)

			DOMResult res = new DOMResult();

			// for testing... output the transformation result to screen
			// Result res = new StreamResult(System.out);
			// System.out.println("testing... Before transformation") ;

			// Do the transformation
			tform.transform(xmlSource, res);

			// transform the node of DOMResult to XML Beans ModsModsDocument
			mods = ModsModsDocument.Factory.parse(res.getNode(),
					defaultLoadOptions);

			// for testing...
			// System.out.println("testing... After transformation") ;
			// Node resultNode = res.getNode();

		} catch (TransformerFactoryConfigurationError e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TransformerConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TransformerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace(System.out);
		} catch (XmlException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace(System.out);
		}
		return mods;
	}

	/**
	 * Sets the OBJID of the HaSMETSProfile and cleans up some of the
	 * boilerplate created by the constructor
	 * 
	 * @throws HaSMETSPackagerException
	 */
	private void setOBJID(String eprintsId) throws PackagerException {
		try {
			echodepmets.setNewPrimaryID(eprintsId,
					HaSMETSProfile.PREMISIdentifierType.HANDLE);

			// remove the boilerplate ID values
			XmlObject objs[] = echodepmets.getMetsDocument().selectPath(
					"declare namespace m='" + HaSMETSProfile.METS_NS
							+ "'; //m:altRecordID[.='[IDENTIFIER]']");

			for (int i = 0; i < objs.length; i++) {
				XmlObject object = objs[i];
				object.getDomNode().getParentNode().removeChild(
						object.getDomNode());
			}

			MetsMdSecType prim = echodepmets.getPrimaryRepresentation();
			XmlObject idents[] = prim
					.selectPath("declare namespace p='"
							+ HaSMETSProfile.PREMIS_NS
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

	@SuppressWarnings("unchecked")
	private void createNewFileSec(ArrayList fileInfo)
			throws HaSMETSProfileException, PackagerException {
		log.info("Creating a new FileSec");

		for (int i = 0; i < fileInfo.size(); i = i + 3) {
			// MetsFileType epFile = (MetsFileType) fileInfo[i][2] ;
			String filename = fileInfo.get(i).toString();
			// String admid = (String) dsfile.getADMID().get(0);
			String fileSize = fileInfo.get(i + 1).toString();
			String fileType = fileInfo.get(i + 2).toString();

			// add the file to the HaSProfile
			MetsFileType echodepfile = echodepmets.addFile(new File(exportdir
					+ File.separator + filename));
			echodepmets.getPrimaryStructMap().getDiv().addNewFptr().setFILEID(
					echodepfile.getID());

			// determine if size and type match up what eprints has
			String fileType_calculated = echodepfile.getMIMETYPE()
					.toLowerCase().trim();
			if (!fileType.toLowerCase().trim().equals(fileType_calculated)) {
				// something wrong?
				if (!fileType.toLowerCase().startsWith("text")
						| !fileType_calculated.startsWith("text")) {
					// only throw error if both do not start with text...
					throw new PackagerException(
							"Problem: Created Mets file (MimeType="
									+ fileType_calculated
									+ ") does not match Eprints (MimeType="
									+ fileType.toLowerCase()
									+ ") input file-- " + filename + ")");
				}
			}
			Long fileSize_calculated = echodepfile.getSIZE();
			try {
				long fileSize_long = Long.parseLong(fileSize.trim());
				// System.out.println("long fileSize_long = " + fileSize_long);
				if (fileSize_long != fileSize_calculated) {
					throw new PackagerException(
							"Problem: Created Mets file (Size="
									+ fileSize_calculated
									+ ") does not match Eprints (Size="
									+ fileSize_long + ") input file--"
									+ filename + ")");
				}
			} catch (NumberFormatException nfe) {
				// System.out.println("NumberFormatException: " +
				// nfe.getMessage());
				throw new PackagerException(
						"Problem: Eprints SIZE NumberFormatException: "
								+ nfe.getMessage());
			}

			// now create PREMIS events for the transformation
			PremisEventDocument event = HaSMETSProfile.createBasicPremisEvent(
					PREMISEventType.METADATA_TRANSFORMATION,
					"Add file to our METS " + "compliant core");
			PremisAgentDocument[] agents = {
					echodepmets.getDefaultHumanAgent(),
					echodepmets.getDefaultSoftwareAgent() };
			PREMISLinkingAgentRole[] roles = {
					PREMISLinkingAgentRole.EVENT_INITIATOR,
					PREMISLinkingAgentRole.SOFTWARE_USED };
			echodepmets.addEventToXmlObject(echodepfile, event, agents, roles);
		}

	}

	/**
	 * Creates a new HaSMETSProfile from the supplied files and adds the
	 * pertinent metadata from the Eprints export file
	 * 
	 * @return HasMETSProfile
	 * @throws PackagerException
	 * @throws HaSMETSProfileException
	 */

	private HaSMETSProfile createFromScratch(File dir)
			throws PackagerException, HaSMETSProfileException {
		log.info("Creating a Hub package from scratch");

		try {
			exportdir = dir;

			// get eprints export file
			File eprintsXmlFile = findEprintsXml(dir);

			// get file nodes from xml DOM tree
			// parse the XML as a W3C Document
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			// it's important to set the namespace-aware value to true
			dbf.setNamespaceAware(true);

			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document document = builder.parse(eprintsXmlFile);

			// Map prefixes (in xpath expression) to URIs explicitly
			NamespaceContext ctx = new NamespaceContext() {
				public String getNamespaceURI(String prefix) {
					String uri;
					if (prefix.equals("ep"))
						uri = "http://eprints.org/ep2/data/2.0";
					else
						uri = null;
					return uri;
				}

				// Dummy implementation - not used!
				public Iterator getPrefixes(String val) {
					return null;
				}

				// Dummy implemenation - not used!
				public String getPrefix(String uri) {
					return null;
				}
			};
			XPath xpath = XPathFactory.newInstance().newXPath();
			xpath.setNamespaceContext(ctx);

			int f = 0;
			ArrayList<String> fileInfo = new ArrayList<String>();

			// Get Eprints Id
			Node eprintsIdNode = (Node) xpath.evaluate("//ep:eprintid",
					document, XPathConstants.NODE);
			Node firstnode = eprintsIdNode.getFirstChild();
			String eprintsId = firstnode.getNodeValue();

			// Get document
			String expression = "//ep:document";
			NodeList epDocumentNodes = (NodeList) xpath.evaluate(expression,
					document, XPathConstants.NODESET);

			// now go through the document nodes
			for (int d = 0; d < epDocumentNodes.getLength(); d++) {

				Node n = epDocumentNodes.item(d);
				// With a reference to the <document> element, a relative XPath
				// expression can now be written
				Node fileTypeNode = (Node) xpath.evaluate("ep:format", n,
						XPathConstants.NODE);
				firstnode = fileTypeNode.getFirstChild();
				String fileType = firstnode.getNodeValue();

				NodeList epFileNodes = (NodeList) xpath.evaluate(
						"ep:files/ep:file", n, XPathConstants.NODESET);

				// now go through the file nodes
				for (int i = 0; i < epFileNodes.getLength(); i++) {
					Node n1 = epFileNodes.item(i);
					// With a reference to the <file> element, a relative XPath
					// expression can now written to select the <filename> child
					// element:
					String fileExpression = "ep:filename";
					Node fileNameNode = (Node) xpath.evaluate(fileExpression,
							n1, XPathConstants.NODE);
					Node firstnode_name = fileNameNode.getFirstChild();
					String fileName = firstnode_name.getNodeValue();
					if ((fileName != HaSConstants.METS_FILE_NAME)
							&& (fileName != null) && (fileName != "")) {
						// process this file name
						// Possible information about file to save:
						// Save filename & filesize (need URL?)

						// listIndex = row * columnWidth + column
						int listIndex = f * 3 + 0;
						fileInfo.add(listIndex, fileName);

						fileNameNode = (Node) xpath.evaluate("ep:filesize", n1,
								XPathConstants.NODE);
						firstnode_name = fileNameNode.getFirstChild();
						String fileSize = firstnode_name.getNodeValue();
						++listIndex;
						fileInfo.add(listIndex, fileSize);
						++listIndex;
						fileInfo.add(listIndex, fileType);
						++f;
					} else {
						// this file is echodepmets.xml
						// Need to get Node echodepmets as node?
						// what to do if null?
					}
				}
			}

			// now, eprintsXmlFile, fileInfo, eprintsId

			// TODO: Change so that this returns the HaSMETSProfile that is
			// generated
			// boolean ret = true;

			// create a new HaSMETSProfile instance
			echodepmets = HaSMETSProfileFactory
					.newHaSMETSProfile(HaSMETSProfile.PackageType.AIP);

			// set the base url of this to be the location where the DSpace
			// export was unzipped
			echodepmets.setBaseURI(new File(exportdir.toString()
					+ File.separator + HaSConstants.METS_FILE_NAME).toURI());

			// get the transformed Mods supplied by EPrints
			ModsModsDocument epmods = extractEPMODS(eprintsXmlFile);

			// remove the xmlData section and replace it with the extracted
			// eprints MODS
			Node xmlData = echodepmets.getMetsDocument().getMets()
					.getDmdSecArray()[0].getMdWrap().getXmlData().getDomNode();
			xmlData.removeChild(xmlData.getFirstChild());
			Node mod = xmlData.getOwnerDocument().importNode(
					epmods.getMods().getDomNode(), true);
			xmlData.appendChild(mod);

			// make the metadata conform
			// Do we need this method?... for what? gives error in primary
			// dmdsec
			echodepmets.convertPrimaryDmdSecToAquifer();

			// create the new FileSec
			// Should this use the information I got from createHubPackage??
			// Filename & URL?
			createNewFileSec(fileInfo);

			// extract a Label for the METS file from the MODS title element
			echodepmets.setLabelFromPrimaryDmdSec();

			// set the handle as an alt. record ID
			// recordHandleAsAltRecordID();

			// set the OBJID of the HaSMETSProfile
			setOBJID(eprintsId);

			// add techMD
			// skip this for now, only run JHOVE if there are
			// checksum or filesize conflicts later in the process
			// TechMDAugmenter.addJhoveToProfile(echodepmets);

			// save it to the file system... for testing
			echodepmets.save();

			// HaSMETSAppender app = new HaSMETSAppender("ProfileValidation");
			// validate profile
			// try {
			// echodepmets.validateProfile(PackageType.SIP, app);
			// } catch (Exception s) {
			// throw new
			// PackagerException("Problem validating mets file: "+s.getMessage
			// ());
			// }

		} catch (Exception e) {
			log.error("Could not create package");
			e.printStackTrace();
			throw new PackagerException("Could not create package "
					+ e.getMessage());
		}
		return echodepmets;
	}

}
