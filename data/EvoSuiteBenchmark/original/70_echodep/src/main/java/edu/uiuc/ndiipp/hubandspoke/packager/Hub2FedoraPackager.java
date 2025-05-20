/**
 * Hub2FedoraPackager.java
 * 
 * $Revision: 872 $
 * 
 * $Date: 2009-11-17 20:37:38 +0000 (Tue, 17 Nov 2009) $
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

import edu.uiuc.ndiipp.hubandspoke.lrcrud.client.LrcrudClientException;
import edu.uiuc.ndiipp.hubandspoke.lrcrud.client.LrcrudClient.CreateResponse;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSAppender;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfile;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfileException;
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
 * @author Bill Ingram
 * @author Matt Cordial
 *
 */
public class Hub2FedoraPackager extends FromHubPackager {
	
	private static Logger log = Logger.getLogger(Hub2DspacePackager.class
			.getName());
	
	// Set accordingly
	private static String tempURL = "http://localhost:8080/temp";

	/**
	 * 
	 */
	public Hub2FedoraPackager() {
		// TODO Auto-generated constructor stub
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

		String hubbase = new File(echodepmets.getBaseURIMinusFile()).getPath();
		//hubbase = hubbase.substring(0, hubbase.lastIndexOf("/"));

		File dest = null;
		ZipOutputStream zip = null;

		try {
			echodepmets.setNewPrimaryID(id, PREMISIdentifierType.HANDLE);
			echodepmets.save();
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
		
		if (System.getenv("HS_HOME") == null) {
		    throw new PackagerException("The HS_HOME environment variable is not set.");
		}
		try {
			// FIXME pulling the location of the HaS installation from the
			// environmental variable for now
			// FIXME may want to use config files in the future
			File xslt = new File(System.getenv("HS_HOME") + File.separatorChar
					+ "xslt" + File.separatorChar + "mods2simpledc.xsl");
	
			StreamSource xsltsource = new StreamSource(xslt);
	
			// get the MODS to transform into Dspace Dublin Core
			MetsMdSecType dmd = echodepmets.getPrimaryDmdSec();
			ModsModsDocument mods = (ModsModsDocument) echodepmets
					.getMDSecXmlObject(dmd);
			DOMSource xmlSource = new DOMSource(mods.getDomNode());

			TransformerFactory tfact = TransformerFactory.newInstance();
			Transformer tform = tfact.newTransformer(xsltsource);

			// we will write the transform to a file because JAXP does not want
			// to allow
			// multiple transforms on a single source
			File dc = new File(hubbase + File.separator + "dublin_core.xml");
			FileOutputStream fout = new FileOutputStream(dc);
			Result res = new StreamResult(fout);
			tform.transform(xmlSource, res);
			
			File modsfile = new File(hubbase + File.separator + "mods.xml");
			tform = tfact.newTransformer();
			fout = new FileOutputStream(modsfile);
			res = new StreamResult(fout);
			tform.transform(xmlSource, res);

			// add transformed files to the zip
			HaSZipUtils.addFilesToZip(dc, zip);
			HaSZipUtils.addFilesToZip(modsfile, zip);
			

			// add transformed file as an ALTERNATE_DMDSEC to the METS
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(dc);

			MetsMdSecType altdmd = echodepmets.addAlternateDmdSec(
					MetsMdSecType.MdWrap.MDTYPE.DC, null, doc);

			// now create PREMIS events for the transformation
			PremisEventDocument event = HaSMETSProfile.createBasicPremisEvent(
					PREMISEventType.METADATA_TRANSFORMATION,
					"Transformed to simple Dublin Core");
			PremisAgentDocument[] agents = {
					echodepmets.getDefaultHumanAgent(),
					echodepmets.getDefaultSoftwareAgent() };
			PREMISLinkingAgentRole[] roles = {
					PREMISLinkingAgentRole.EVENT_INITIATOR,
					PREMISLinkingAgentRole.SOFTWARE_USED };
			echodepmets.addEventToXmlObject(altdmd, event, agents, roles);
			echodepmets.save();
		} catch (Exception e) {
			throw new PackagerException("Error transforming MODS metadata.", e);
		}

		try {
			// FIXME pulling the location of the HaS installation from the
			// environmental variable for now
			// FIXME may want to use config files in the future
			File xslt = new File(System.getenv("HS_HOME") + File.separatorChar
					+ "xslt" + File.separatorChar + "echodep-mets2foxml.xsl");

			File foxml = new File(hubbase + File.separatorChar + id.replace(":", ".") + ".xml");
	
			StreamSource xsltsource = new StreamSource(xslt);
			DOMSource xmlSource = new DOMSource(mastermets.getMetsDocument().getDomNode());
			
			String temp_url = tempURL + "/" + id.replace(":", "_") + "/";

			TransformerFactory tfact = TransformerFactory.newInstance();
			Transformer tform = tfact.newTransformer(xsltsource);
			tform.setParameter("pid", id);
			tform.setParameter("temp_url", temp_url);
			tform.setParameter("dc_file", new File(hubbase + File.separatorChar + "dublin_core.xml").toURI().toURL().toString());
			tform.setParameter("mods_file", new File(hubbase + File.separatorChar + "mods.xml").toURI().toURL().toString());
			tform.setParameter("mm_file", HaSConstants.MASTER_METS_FILE_NAME);
			tform.setParameter("mets_file", echodepmets.getBaseURI().toURL().toString());
			tform.setParameter("fox_file", foxml.toURI().toURL().toString());
			tform.transform(xmlSource, new StreamResult());
			
			HaSZipUtils.addFilesToZip(foxml, zip);
		} catch (Exception e) {
			throw new PackagerException("Error transforming FOXML.", e);
		}

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

}
