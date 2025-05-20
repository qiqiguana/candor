/**
 * Hub2DspacePackager.java
 * 
 * $Revision: 863 $
 * 
 * $Date: 2009-06-17 19:10:23 +0100 (Wed, 17 Jun 2009) $
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSAppender;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfile;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfileException;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfileFactory;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMasterMETSProfile;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMasterMETSProfileFactory;
import edu.uiuc.ndiipp.hubandspoke.profile.METSProfile.PREMISEventType;
import edu.uiuc.ndiipp.hubandspoke.profile.METSProfile.PREMISIdentifierType;
import edu.uiuc.ndiipp.hubandspoke.profile.METSProfile.PREMISLinkingAgentRole;
import edu.uiuc.ndiipp.hubandspoke.profile.METSProfile.PackageType;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSConstants;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSZipUtils;
import gov.loc.mets.MetsMdSecType;
import gov.loc.mets.MetsDivType.Mptr;
import gov.loc.mods.ModsModsDocument;
import gov.loc.premis.PremisAgentDocument;
import gov.loc.premis.PremisEventDocument;

/**
 * <p>
 * Class responsible for transforming a hub package into a package ingestable by
 * Dspace. Expects to be fed a METS file conformant to the EchoDep profile
 * (http://www.loc.gov/standards/mets/profiles/00000015.xml)
 * </p>
 * 
 * <p>
 * Any files not referenced in the METS file will not be packaged for DSpace
 * ingestion.
 * </p>
 * 
 * @author Matt Cordial
 * @version $LastChangedRevision: 863 $
 * @see <a
 *      href="http://www.loc.gov/standards/mets/profiles/00000015.xml">ECHODep *
 *      Generic METS Profile for Preservation and Digital Repository
 *      Interoperability< /a>
 * 
 */
public class Hub2DspacePackager extends FromHubPackager {

	// TODO do something with this!
	private static Logger log = Logger.getLogger(Hub2DspacePackager.class
			.getName());

	/**
	 * Command line interface to transform a hub package into a package
	 * ingestable by Dspace
	 * 
	 * USAGE: Hub2DspacePackager /path/to/hub/package /output/dir
	 * dspaceAssignedHandle
	 * 
	 * @param args
	 *            - path to the hub package, path to the output directory,
	 *            dspace handle
	 */
	public static void main(String[] args) {

		if (args.length != 3) {
			System.out
					.println("Please supply the correct number of arguments.");
			usage();
			return;
		}

		try {
			Hub2DspacePackager p = new Hub2DspacePackager();
			if (p.createRepositoryPackage(args[0], args[1], args[2]) != null) {
				System.out.println("Packaging complete.");
			} else {
				System.out.println("Errors occurred!");
			}
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			usage();
			return;
		}
	}

	/**
	 * Carry out the transformation on the given package. If output directory is
	 * not given it creates the package in the same directory as the METS file.
	 * 
	 * @param pathtomets
	 *            - location of the METS file
	 * @param id
	 *            - handle retrieved from the POST method of LRCRUD
	 * @throws PackagerException
	 * @throws HaSMETSProfileException
	 */
	public File createRepositoryPackage(String pathtomets, String id)
			throws HaSMETSProfileException, PackagerException {
		return createRepositoryPackage(pathtomets, null, id);
	}

	/**
	 * Carry out the transformation on the given package. If passed a string,
	 * instantiates a MaSMETSProfile object and validates it before continuing.
	 * 
	 * @param pathtomets
	 *            - location of the METS file
	 * @param id
	 *            - handle retrieved from the POST method of LRCRUD
	 * @throws HaSMETSProfileException
	 * @throws PackagerException
	 */
	public File createRepositoryPackage(String pathtomets, String output_path,
			String id) throws HaSMETSProfileException, PackagerException {
		// HaSMETSProfile hs = HaSMETSProfileFactory.newInstance(pathtomets);
		HaSMasterMETSProfile mastermets = HaSMasterMETSProfileFactory
				.newInstance(new File(pathtomets));
		HaSMETSProfile echodepmets = mastermets.getMostRecentEchoDepMETS();
		HaSMETSAppender app = new HaSMETSAppender("ProfileValidation");

		if (echodepmets.validateProfile(PackageType.SIP, app)) {
			return createRepositoryPackage(mastermets, output_path, id);
		} else {
			String events = app.getAllEventsAsXmlText();
			// TODO Write this to a log file and not the system
			System.out.println(events);
			log.error(events);
			return null;
		}
	}

	/**
	 * Carry out the transformation on the given package. If passed a string,
	 * instantiates a MaSMETSProfile object and validates it before continuing.
	 * 
	 * @param hubpackage
	 *            - HaSMETSProfile object
	 * @param id
	 *            - handle retrieved from the POST method of LRCRUD
	 * @throws HaSMETSProfileException
	 * @throws PackagerException
	 */
	public File createRepositoryPackage(HaSMasterMETSProfile mastermets,
			String id) throws HaSMETSProfileException, PackagerException {
		return createRepositoryPackage(mastermets, null, id);
	}

	/**
	 * Carry out the transformation on the given package
	 * 
	 * @param hubpackage
	 *            - HaSMETSProfile object
	 * @param output_dir
	 *            - Place to put the zip
	 * @param id
	 *            - handle retrieved from the POST method of LRCRUD
	 * @throws HaSMETSProfileException
	 * @throws PackagerException
	 */
	public File createRepositoryPackage(HaSMasterMETSProfile mastermets,
			String output_dir, String id) throws PackagerException,
			HaSMETSProfileException {

		HaSMETSProfile echodepmets = mastermets.getMostRecentEchoDepMETS();
		echodepmets.setPackageType(echodepmets.guessPackageType());

		String hubbase = echodepmets.getBaseURI().getPath();
		hubbase = hubbase.substring(0, hubbase.lastIndexOf("/"));

		echodepmets.setNewPrimaryID(id, PREMISIdentifierType.HANDLE);

		// System.setProperty("javax.xml.transform.TransformerFactory",
		// "net.sf.saxon.TransformerFactoryImpl");

		File dest = null;
		ZipOutputStream zip = null;
		try {
			if (output_dir != null) { // put it where specified
				dest = File.createTempFile("hubandspoke", ".zip", new File(
						output_dir));
				// dest = new
				// File(output_dir+File.separatorChar+formatHandleForFileName
				// (id)+".zip");
				zip = new ZipOutputStream(new FileOutputStream(dest));
			} else { // put the zip in the same directory as the HS METS file
				dest = new File(hubbase + File.separatorChar
						+ formatHandleForFileName(id) + ".zip");
				zip = new ZipOutputStream(new FileOutputStream(dest));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace(System.out);
			log.error("Output directory not found.");
			throw new PackagerException("Output directory not found.", e);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("IO Error in create repository package.");
			throw new PackagerException("IO Error in create repository package.", e);
		}

		addFilesToZip(mastermets, zip);

		// FIXME pulling the location of the HaS installation from the
		// environmental variable for now
		// FIXME may want to use config files in the future
		File xslt = new File(System.getenv("HS_HOME") + File.separatorChar
				+ "xslt" + File.separatorChar + "mods2dsdc.xsl");

		StreamSource xsltsource = new StreamSource(xslt);

		// get the MODS to transform into Dspace Dublin Core
		MetsMdSecType dmd = echodepmets.getPrimaryDmdSec();
		ModsModsDocument mods = (ModsModsDocument) echodepmets
				.getMDSecXmlObject(dmd);
		DOMSource xmlSource = new DOMSource(mods.getDomNode());

		try {
			TransformerFactory tfact = TransformerFactory.newInstance();
			Transformer tform = tfact.newTransformer(xsltsource);

			// we will write the transform to a file because JAXP does not want
			// to allow
			// multiple transforms on a single source
			File dc = new File(hubbase + File.separatorChar + "dublin_core.xml");
			FileOutputStream fout = new FileOutputStream(dc);
			Result res = new StreamResult(fout);
			tform.transform(xmlSource, res);

			// add transformed file to the zip
			HaSZipUtils.addFilesToZip(dc, zip);

			// add transformed file as an ALTERNATE_DMDSEC to the METS

			// String prevFactory =
			// System.getProperty("javax.xml.parsers.DocumentBuilderFactory");
			// System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
			// "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl"
			// );

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(dc);

			// System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
			// prevFactory);

			MetsMdSecType altdmd = echodepmets.addAlternateDmdSec(
					MetsMdSecType.MdWrap.MDTYPE.DC, null, doc);

			// now create PREMIS events for the transformation
			PremisEventDocument event = HaSMETSProfile.createBasicPremisEvent(
					PREMISEventType.METADATA_TRANSFORMATION,
					"Transformed to Dspace " + "compliant Dublin Core");
			PremisAgentDocument[] agents = {
					echodepmets.getDefaultHumanAgent(),
					echodepmets.getDefaultSoftwareAgent() };
			PREMISLinkingAgentRole[] roles = {
					PREMISLinkingAgentRole.EVENT_INITIATOR,
					PREMISLinkingAgentRole.SOFTWARE_USED };
			echodepmets.addEventToXmlObject(altdmd, event, agents, roles);

		} catch (Exception e) {
			throw new PackagerException("Error transforming MODS metadata.", e);
		}

		// try { //to save the Mets file to the zip stream
		// ZipEntry ze = new ZipEntry(HaSConstants.METS_FILE_NAME);
		// zip.putNextEntry(ze);
		// echodepmets.getMetsDocument().save(zip);
		// zip.closeEntry();
		// zip.close();
		// } catch (IOException e) {
		// throw new
		// PackagerException("Problem saving the METS file to the zip.", e);
		// }

		Mptr mptrs[] = mastermets.getAllMptrs();
		try {
			for (Mptr mptr : mptrs) {
				HaSZipUtils.addFilesToZip(new File(mastermets.getBaseURI()
						.resolve(mptr.getHref())), zip);
			}
			HaSZipUtils.addFilesToZip(new File(mastermets.getBaseURI()), zip);
		} catch (IOException e) {
			e.printStackTrace();
			throw new PackagerException(
					"Problem saving the METS file to the zip.", e);
		}
		try {
			zip.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new PackagerException(e.getMessage());
		}
		return dest;
	}

	private static String formatHandleForFileName(String handle) {
		if (handle.contains("/")) {
			handle = handle.replaceAll("/", "_");
		}
		return handle;
	}

	/**
	 * Prints hints on how to use the program from the command line.
	 */
	private static void usage() {
		System.out.println("");
		System.out
				.println("====================================================");
		System.out
				.println("USAGE: Hub2DspaceTransformer /path/to/hub/package /output/dir dspace-handle");
		System.out
				.println("Example: Hub2DspaceTransformer d:\\staging\\hubandspokemets.xml d:\\out 00101/3343");
		System.out
				.println("====================================================");
	}

}
