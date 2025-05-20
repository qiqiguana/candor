/**
 * TechMDAugmenter.java
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

package edu.uiuc.ndiipp.hubandspoke.utils.jhove;

import edu.harvard.hul.ois.jhove.JhoveException;
import edu.uiuc.ndiipp.hubandspoke.profile.*;

import java.io.*;

import gov.loc.mets.*;
import gov.loc.premis.*;
import gov.loc.amd.*;
import gov.loc.mix.*;
import edu.nyu.textmd.*;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.w3c.dom.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.OutputKeys;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.namespace.*;

/**
 * Inserts JHOVE generated technical metadata into the METS profile. This class
 * will read in a METS file then to use JHOVE to create and insert technical
 * metadata for all the content files.
 * 
 * The main procedure, addJhoveToProfile, takes as a parameter the
 * HaSMETSProfile.
 * 
 * @author Bill Ingram
 * @author Howard Ding
 * 
 */

public class TechMDAugmenter {

	// log4j logger
	static Logger logger = Logger.getLogger(TechMDAugmenter.class);
	static HaSMETSAppender app = new HaSMETSAppender("JHOVE");

	/**
	 * Cycles through the files, uses JHOVE to generate techMD, and inserts the
	 * MIME-specific techMD into the profile. If the MIME-type detected by JHOVE
	 * conflicts with the MIME type given by the profile, an warning is logged
	 * and inserted into the profile as a PREMIS event. If the MIME-type is one
	 * not supported (e.g., application/octet-stream), a warning is generated
	 * and inserted as a PREMIS event as well.
	 * 
	 * @param profile
	 *            - HaSMETSProfile
	 */
	public static boolean addJhoveToProfile(HaSMETSProfile profile) {
		logger.addAppender(app);
		app.clearEvents();
		app.setThreshold(Level.INFO);
		MetsFileType[] fileArray = profile.getAllFiles();
		for (int i = 0; i < fileArray.length; i++) {
			try {
				String mime = fileArray[i].getMIMETYPE();
				logger.debug("Loading file: "
						+ fileArray[i].getFLocatArray(0).getHref());

				if (new File(profile.resolveURIAgainstBaseURI(
						fileArray[i].getFLocatArray(0).getHref()).getPath())
						.exists()) {
					if (mime.compareTo("application/pdf") != 0
							&& mime.compareTo("application/octet-stream") != 0) {
						JhoveResult result = JhoveGenerator.analyzeFileNamed(
								new File(profile
										.resolveURIAgainstBaseURI(fileArray[i]
												.getFLocatArray(0).getHref()))
										.getPath(), "File Name", fileArray[i]
										.getFLocatArray(0).getHref());
						// XMLToStream(result.jhoveMetadata, System.out);
						String jhoveMime = determineMIMEType(result
								.getJhoveMetadata());
						if ((mime != null)
								&& (jhoveMime.compareToIgnoreCase(mime) != 0)) {
							logger.info("MIME type discrepancy for file: "
									+ fileArray[i].getFLocatArray(0).getHref()
									+ ". Expected MIME: " + mime
									+ ". MIME detected by JHOVE: " + jhoveMime);
						}
						long size = determineFileSize(result.getJhoveMetadata());
						if (size != fileArray[i].getSIZE()) {
							logger.info("File size discrepancy for file: "
									+ fileArray[i].getFLocatArray(0).getHref()
									+ ". Expected file size: "
									+ fileArray[i].getSIZE()
									+ ". File size detected by JHOVE: " + size);
						}
						insertTechMD(profile, result, jhoveMime, fileArray[i]);
					} else {
						logger.info("Unhandled MIME type for file: "
								+ fileArray[i].getFLocatArray(0).getHref()
								+ ". MIME type: " + mime + "");
					}
				} else {
					logger.warn("File not found: "
							+ fileArray[i].getFLocatArray(0).getHref());
					logger.removeAppender(app);
					return false;
				}

			} catch (JhoveException e) {
				logger.error(e);
				// e.printStackTrace();
			} catch (Exception e) {
				logger.error(e);
				// e.printStackTrace();
			}
		}
		logger.removeAppender(app);
		PremisEventDocument premis = HaSMETSProfile.createBasicPremisEvent(
				HaSMETSProfile.PREMISEventType.VALIDATION,
				"Generating Technical Metadata Using JHOVE");
		PremisEventDocument.Event.EventOutcomeInformation.EventOutcomeDetail det = premis
				.getEvent().addNewEventOutcomeInformation()
				.addNewEventOutcomeDetail();
		XmlObject events;
		try {
			events = XmlObject.Factory.parse(app.getAllEventsAsXmlText());
		} catch (XmlException e) {
			throw new RuntimeException("The log4j XML is not valid", e);
		}
		Node n = det.getDomNode().getOwnerDocument().importNode(
				events.getDomNode().getFirstChild(), true);
		det.getDomNode().appendChild(n);

		// attach the event
		PremisAgentDocument jhoveAgent = PremisAgentDocument.Factory
				.newInstance();
		PremisAgentDocument.Agent agent = jhoveAgent.addNewAgent();
		PremisAgentDocument.Agent.AgentIdentifier agentID = agent
				.addNewAgentIdentifier();
		agentID.setAgentIdentifierValue("http://hul.harvard.edu/jhove/");
		agentID.setAgentIdentifierType(HaSMETSProfile.PREMISIdentifierType.URL
				.toString());
		agent.addAgentName("JHOVE Version 1.0");
		agent.setAgentType(HaSMETSProfile.PREMISAgentType.SOFTWARE.toString());

		PremisAgentDocument agents[] = { profile.getDefaultHumanAgent(),
				profile.getDefaultSoftwareAgent(), jhoveAgent };
		HaSMETSProfile.PREMISLinkingAgentRole roles[] = {
				HaSMETSProfile.PREMISLinkingAgentRole.EVENT_INITIATOR,
				HaSMETSProfile.PREMISLinkingAgentRole.SOFTWARE_USED,
				HaSMETSProfile.PREMISLinkingAgentRole.SOFTWARE_USED };
		try {
			profile.addEventToXmlObject(profile.getPrimaryStructMap().getDiv(),
					premis, agents, roles);
		} catch (HaSMETSProfileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		profile.save();
		return true;

	}

	/**
	 * If there is no techMD section associated with the file already in the
	 * profile, add it; otherwise, do nothing
	 * 
	 * @param pro
	 *            - Hub and Spoke METS profile
	 * @param res
	 *            - JhoveResult for the file
	 * @param mime
	 *            - MIME type of the file, as per Jhove
	 * @param ft
	 *            - the file
	 */

	private static void insertTechMD(HaSMETSProfile pro, JhoveResult res,
			String mime, MetsFileType ft) {
		try {
			if (mime.compareTo("text/html") == 0
					|| mime.compareTo("text/xml") == 0
					|| mime.compareTo("text/plain") == 0
					|| mime.compareTo("text/plain; charset=UTF-8") == 0
					|| mime.compareTo("text/plain; charset=US-ASCII") == 0) {
				if (!pro.hasSpecificAmdSecXmlData(ft,
						HaSMETSProfile.AMD_SECTION.TECHMD, new QName(
								HaSMETSProfile.PREMIS_NS, "textMD"))) {
					TextMDDocument textObj = TextMDDocument.Factory.parse(res
							.getSpecificMetadata());
					pro.addTechMDXmlObjectToFile(ft, textObj);
				}
			} else if (mime.compareTo("image/gif") == 0
					|| mime.compareTo("image/jpeg") == 0
					|| mime.compareTo("image/jp2") == 0
					|| mime.compareTo("image/jpx") == 0
					|| mime.compareTo("image/tiff") == 0
					|| mime.compareTo("image/tiff-fx") == 0
					|| mime.compareTo("image/iex") == 0) {
				if (!pro.hasSpecificAmdSecXmlData(ft,
						HaSMETSProfile.AMD_SECTION.TECHMD, new QName(
								HaSMETSProfile.PREMIS_NS, "textMD"))) {

					NodeList nl = res.getSpecificMetadata().getFirstChild()
							.getChildNodes();

					for (int k = 0; k < nl.getLength(); k++) {
						MixMixDocument mixObj = MixMixDocument.Factory.parse(nl
								.item(k));
						pro.addTechMDXmlObjectToFile(ft, mixObj);
					}
				}
			} else if (mime.compareTo("audio/x-aiff") == 0
					|| mime.compareTo("audio/x-wave") == 0) {
				if (!pro.hasSpecificAmdSecXmlData(ft,
						HaSMETSProfile.AMD_SECTION.TECHMD, new QName(
								HaSMETSProfile.PREMIS_NS, "textMD"))) {
					AmdAUDIOMDDocument audioObj = AmdAUDIOMDDocument.Factory
							.parse(res.getSpecificMetadata());
					pro.addTechMDXmlObjectToFile(ft, audioObj);
				}
			}
			// TODO: how to handle pdf files
			else if (mime == "application/pdf"
					|| mime == "application/octet-stream") {
				logger.info("Unhandled MIME type for file: "
						+ ft.getFLocatArray(0).getHref() + ". MIME type: "
						+ mime + "");
			} else {
				logger.info("Unhandled MIME type for file: "
						+ ft.getFLocatArray(0).getHref() + ". MIME type: "
						+ mime + "");
			}
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HaSMETSProfileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Transform XML to stream
	 * 
	 * @param n
	 * @param stream
	 * @throws Exception
	 */
	public static void XMLToStream(Node n, PrintStream stream) throws Exception {
		if (n != null) {
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			Source s = new DOMSource(n);
			t.transform(s, new StreamResult(stream));
			stream.println("\n");
		}
	}

	/**
	 * Extracts the MIME type from Jhove metadata
	 * 
	 * @param jhoveInput
	 *            The raw Jhove metadata
	 * @return String that encodes the MIME type given in the Jhove metadata
	 * @throws XPathExpressionException
	 */
	protected static String determineMIMEType(Node jhoveInput)
			throws XPathExpressionException {
		XPath xpath = XPathFactory.newInstance().newXPath();
		xpath.setNamespaceContext(JhoveNamespaceContext.getInstance());
		String mime = xpath.evaluate(
				"/jhove:jhove/jhove:repInfo/jhove:mimeType", jhoveInput);
		return mime;
	}

	/**
	 * Extracts the file size from Jhove metadata
	 * 
	 * @param jhoveInput
	 *            The raw Jhove metadata
	 * @return long file size given in the Jhove metadata
	 * @throws XPathExpressionException
	 */
	protected static long determineFileSize(Node jhoveInput)
			throws XPathExpressionException {
		XPath xpath = XPathFactory.newInstance().newXPath();
		xpath.setNamespaceContext(JhoveNamespaceContext.getInstance());
		String size = xpath.evaluate("/jhove:jhove/jhove:repInfo/jhove:size",
				jhoveInput);
		return Long.parseLong(size);
	}
}
