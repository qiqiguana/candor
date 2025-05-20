/**
 * HaSMETSProfile.java
 * 
 * $Revision: 866 $
 * 
 * $Date: 2009-09-04 17:57:38 +0100 (Fri, 04 Sep 2009) $
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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.SchemaProperty;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlDateTime;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlID;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlString;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.harvard.jhove.JhoveJhoveDocument;
import edu.nyu.textmd.TextMDDocument;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSChecksummer;
import gov.loc.amd.AmdAUDIOMDDocument;
import gov.loc.mets.MetsAreaType;
import gov.loc.mets.MetsBehaviorType;
import gov.loc.mets.MetsDivType;
import gov.loc.mets.MetsFileGrpType;
import gov.loc.mets.MetsFileType;
import gov.loc.mets.MetsMdSecType;
import gov.loc.mets.MetsMetsDocument;
import gov.loc.mets.MetsMetsType;
import gov.loc.mets.MetsStructMapType;
import gov.loc.mix.MixMixDocument;
import gov.loc.mods.ModsAccessConditionType;
import gov.loc.mods.ModsBaseDateType;
import gov.loc.mods.ModsBaseTitleInfoType;
import gov.loc.mods.ModsClassificationType;
import gov.loc.mods.ModsCodeOrText;
import gov.loc.mods.ModsDateOtherType;
import gov.loc.mods.ModsDateType;
import gov.loc.mods.ModsGenreType;
import gov.loc.mods.ModsIdentifierType;
import gov.loc.mods.ModsLanguageType;
import gov.loc.mods.ModsLocationType;
import gov.loc.mods.ModsModsDocument;
import gov.loc.mods.ModsModsType;
import gov.loc.mods.ModsNameType;
import gov.loc.mods.ModsOriginInfoType;
import gov.loc.mods.ModsPhysicalDescriptionType;
import gov.loc.mods.ModsPlaceAuthority;
import gov.loc.mods.ModsPlaceTermType;
import gov.loc.mods.ModsPlaceType;
import gov.loc.mods.ModsRecordInfoType;
import gov.loc.mods.ModsRelatedItemType;
import gov.loc.mods.ModsResourceType;
import gov.loc.mods.ModsSubjectType;
import gov.loc.mods.ModsTitleInfoType;
import gov.loc.mods.ModsTypeOfResourceType;
import gov.loc.mods.ModsUrlType;
import gov.loc.premis.PremisAgentDocument;
import gov.loc.premis.PremisEventDocument;
import gov.loc.premis.PremisObjectDocument;
import gov.loc.premis.PremisRightsDocument;
import gov.loc.vmd.VmdVIDEOMDDocument;

/**
 * This class represents a METS XML document that conforms to the UIUC Hub and
 * Spoke METS profile. This is basically a wrapper around various XMLBeans class
 * libraries for the different XML Schema used by the profile.
 * 
 * @author thabing
 * 
 */

public class HaSMETSProfile extends METSProfile {

	/**
	 * Class used to hold various Hashmaps which tie different attribute values
	 * to different kinds of metadata types. This class assumes that the
	 * MetsMdSecType.MdRef.MDTYPE.Enum, MetsMdSecType.MdWrap.MDTYPE.Enum values
	 * are synchronized (have the same values in the same order in the
	 * enumeration). This is true in the current schema, but if it ever changes
	 * this class will need reworked.
	 * 
	 * @author thabing
	 * 
	 */
	private static class ObjectTypes {
		private static HashMap<String, HashMap<QName, String[]>> allObjectTypes = new HashMap<String, HashMap<QName, String[]>>();

		private static HashMap<QName, String[]> digiprovObjectTypes = new HashMap<QName, String[]>();

		private static HashMap<QName, String[]> dmdObjectTypes = new HashMap<QName, String[]>();

		private static HashMap<QName, String[]> rightsObjectTypes = new HashMap<QName, String[]>();

		private static HashMap<QName, String[]> sourceObjectTypes = new HashMap<QName, String[]>();

		private static HashMap<QName, String[]> techObjectTypes = new HashMap<QName, String[]>();

		static {
			String[] s;
			techObjectTypes.put(new QName(PREMIS_NS, "object"),
					s = new String[2]);
			s[0] = "PREMIS";
			s[1] = null;
			techObjectTypes
					.put(new QName(AMD_NS, "AUDIOMD"), s = new String[2]);
			s[0] = "LC-AV";
			s[1] = null;
			techObjectTypes
					.put(new QName(VMD_NS, "VIDEOMD"), s = new String[2]);
			s[0] = "LC-AV";
			s[1] = null;
			techObjectTypes.put(new QName(MIX_NS, "mix"), s = new String[2]);
			s[0] = "NISOIMG";
			s[1] = null;
			techObjectTypes
					.put(new QName(JHOVE_NS, "jhove"), s = new String[2]);
			s[0] = "OTHER";
			s[1] = JHOVE_NS;
			techObjectTypes.put(new QName("textMD"), s = new String[2]);
			s[0] = "OTHER";
			s[1] = "http://dlib.nyu.edu/METS/textmd.xsd";

			rightsObjectTypes.put(new QName(PREMIS_NS, "rights"),
					s = new String[2]);
			s[0] = "PREMIS";
			s[1] = null;
			rightsObjectTypes.put(new QName(PREMIS_NS, "agent"),
					s = new String[2]);
			s[0] = "PREMIS";
			s[1] = null;

			digiprovObjectTypes.put(new QName(PREMIS_NS, "event"),
					s = new String[2]);
			s[0] = "PREMIS";
			s[1] = null;
			digiprovObjectTypes.put(new QName(PREMIS_NS, "agent"),
					s = new String[2]);
			s[0] = "PREMIS";
			s[1] = null;

			digiprovObjectTypes.put(new QName(MODS_NS, "mods"),
					s = new String[2]);
			s[0] = "MODS";
			s[1] = null;

			allObjectTypes.put("techMD", techObjectTypes);
			allObjectTypes.put("rightsMD", rightsObjectTypes);
			allObjectTypes.put("sourceMD", sourceObjectTypes);
			allObjectTypes.put("digiprovMD", digiprovObjectTypes);
			allObjectTypes.put("dmdSec", dmdObjectTypes);
		}

		/**
		 * Given the inputs, return a string which can be used as an MDTYPE
		 * attribute value
		 * 
		 * @param mdTypeNodeName
		 *            the name of the MdSecType, such as techMD
		 * @param qn
		 *            the QName of the xml contained in the MdSec
		 * @return String which can be used as the value of the MDTYPE attribute
		 */
		public static String getMDTYPE(String mdTypeNodeName, QName qn) {
			HashMap<QName, String[]> hm = allObjectTypes.get(mdTypeNodeName);
			if (hm != null) {
				String[] ot = hm.get(qn);
				if (ot != null) {
					return ot[0];
				} else {
					throw new IllegalArgumentException("The {"
							+ qn.getNamespaceURI() + "}" + qn.getLocalPart()
							+ " is not recognized");
				}
			} else {
				throw new IllegalArgumentException("The node name '"
						+ mdTypeNodeName + "' is not recognized");
			}
		}

		/**
		 * Given the inputs, return a string which can be used as an OTHERMDTYPE
		 * attribute value
		 * 
		 * @param mdTypeNodeName
		 *            the name of the MdSecType, such as techMD
		 * @param qn
		 *            the QName of the xml contained in the MdSec
		 */
		public static String getOTHERMDTYPE(String mdTypeNodeName, QName qn) {
			HashMap<QName, String[]> hm = allObjectTypes.get(mdTypeNodeName);
			if (hm != null) {
				String[] ot = hm.get(qn);
				if (ot != null) {
					return ot[1];
				} else {
					throw new IllegalArgumentException("The {"
							+ qn.getNamespaceURI() + "}" + qn.getLocalPart()
							+ " is not recognized");
				}
			} else {
				throw new IllegalArgumentException("The node name '"
						+ mdTypeNodeName + "' is not recognized");
			}
		}

		/**
		 * Determine whether the given combination of MetsMdSecType, namespace,
		 * nodename, and mdType are recognized by this profile
		 * 
		 * @param mdTypeNodeName
		 *            the name of the MDSecType node
		 * @param qn
		 *            the QName of the metadata contained or referenced by the
		 *            MDSecType
		 * @param type
		 *            the string value of the MDTYPE attribute
		 * @param otherType
		 *            the string value of the OTHERMDTYPE attributem or null if
		 *            there is none
		 * 
		 * @return true if all of the above values indicate a recognized
		 *         combination; else false
		 */
		public static boolean isRecognizedCombination(String mdTypeNodeName,
				QName qn, String type, String otherType) {
			HashMap<QName, String[]> hm = allObjectTypes.get(mdTypeNodeName);
			if (hm != null) {
				String[] ot = hm.get(qn);
				return (ot != null && ot[0].compareTo(type) == 0 && (ot[1]
						.compareTo(otherType) == 0 || ot[1] == otherType));
			} else {
				return false;
			}
		}

	}

	/**
	 * Return a ModsModsDocument which is minimally compliant to the Aquifer
	 * MODS profile
	 * 
	 * @param title
	 * @param resourceType
	 * @param digitalOrigin
	 * @param mimeType
	 * @param location
	 * @return ModsModsDocument
	 */
	public static ModsModsDocument createBasicAquiferModsDocument(String title,
			ModsResourceType.Enum resourceType,
			ModsPhysicalDescriptionType.DigitalOrigin.Enum digitalOrigin,
			String mimeType, String location) {
		ModsModsDocument modsDoc = ModsModsDocument.Factory.newInstance();
		ModsModsType mods = modsDoc.addNewMods();
		ModsTitleInfoType tinfo = mods.addNewTitleInfo();
		tinfo.addTitle(title);
		ModsTypeOfResourceType tRes = mods.addNewTypeOfResource();
		ModsOriginInfoType oInfo = mods.addNewOriginInfo();
		ModsPhysicalDescriptionType phyD = mods.addNewPhysicalDescription();
		ModsLocationType mloc = mods.addNewLocation();
		ModsAccessConditionType access = mods.addNewAccessCondition();
		ModsRecordInfoType recInfo = mods.addNewRecordInfo();
		tRes.set(resourceType);
		ModsDateType dcreated = oInfo.addNewDateCreated();
		SimpleDateFormat dform = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		dcreated.setStringValue(dform.format(Calendar.getInstance().getTime()));
		dcreated.setKeyDate(ModsDateType.KeyDate.YES);
		phyD.addDigitalOrigin(digitalOrigin);
		phyD.addInternetMediaType(mimeType);
		ModsLanguageType langT = recInfo.addNewLanguageOfCataloging();
		ModsLanguageType.LanguageTerm langTerm = langT.addNewLanguageTerm();
		langTerm.setAuthority(ModsLanguageType.LanguageTerm.Authority.RFC_3066);
        langTerm.setType(ModsCodeOrText.CODE);
		langTerm.setStringValue("en");
		ModsUrlType murl = mloc.addNewUrl();
		murl.setStringValue(location);
		murl.setUsage(ModsUrlType.Usage.PRIMARY_DISPLAY);
		access.setType2("useAndReproduction");
		access
				.setStringValue("There are no restrictions on use or reproduction.");

		return modsDoc;
	}

	/**
	 * Returns a basic PremisEventDocument with the given params The
	 * eventIdentifierValue is just the current dateTime and the
	 * eventIdentifierType is 'LOCAL'
	 * 
	 * @param eventType
	 * @param eventDetail
	 * @return PremisEventDocument
	 */
	public static PremisEventDocument createBasicPremisEvent(
			HaSMETSProfile.PREMISEventType eventType, String eventDetail) {
		// TODO
		PremisEventDocument evDoc = PremisEventDocument.Factory.newInstance();
		PremisEventDocument.Event ev = evDoc.addNewEvent();
		ev.setVersion(PremisEventDocument.Event.Version.X_1_1);

		PremisEventDocument.Event.EventIdentifier evid = ev
				.addNewEventIdentifier();
		evid.setEventIdentifierType(HaSMETSProfile.PREMISIdentifierType.LOCAL
				.toString());
		ev.setEventType(eventType.toString());
		XmlDateTime dt = XmlDateTime.Factory.newInstance();
		dt.setCalendarValue(Calendar.getInstance());
		PremisEventDocument.Event.EventDateTime edt = PremisEventDocument.Event.EventDateTime.Factory
				.newValue(dt);
		ev.xsetEventDateTime(edt);
		evid.setEventIdentifierValue(ev.xgetEventDateTime().getStringValue());
		if (eventDetail != null)
			ev.setEventDetail(eventDetail);
		return evDoc;

	}

	/**
	 * Returns a basic PremisEventDocument with the given params
	 * 
	 * @param id
	 * @param eventType
	 * @param eventDetail
	 * @return PremisEventDocument
	 */
	public static PremisEventDocument createBasicPremisEvent(String id,
			HaSMETSProfile.PREMISEventType eventType, String eventDetail) {
		// TODO
		PremisEventDocument evDoc = PremisEventDocument.Factory.newInstance();
		PremisEventDocument.Event ev = evDoc.addNewEvent();
		ev.setVersion(PremisEventDocument.Event.Version.X_1_1);

		PremisEventDocument.Event.EventIdentifier evid = ev
				.addNewEventIdentifier();
		evid.setEventIdentifierType(HaSMETSProfile.PREMISIdentifierType.LOCAL
				.toString());
		evid.setEventIdentifierValue(id);
		ev.setEventType(eventType.toString());
		XmlDateTime dt = XmlDateTime.Factory.newInstance();
		dt.setCalendarValue(Calendar.getInstance());
		PremisEventDocument.Event.EventDateTime edt = PremisEventDocument.Event.EventDateTime.Factory
				.newValue(dt);
		ev.xsetEventDateTime(edt);
		if (eventDetail != null)
			ev.setEventDetail(eventDetail);
		return evDoc;

	}

	// The PremisAgentDocument representing the current human operator
	private PremisAgentDocument humanAgentDoc = null;

	// the type OAIS package represented by this document
	private HaSMETSProfile.PackageType packageType = HaSMETSProfile.PackageType.AIP;

	// The PremisAgentDocument representing this software agent
	private PremisAgentDocument softAgentDoc = null;

	// Initialize some stuff
	{
		PROFILE_URI = "http://www.loc.gov/mets/profiles/00000015.xml";

		// System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
		// "net.sf.saxon.dom.DocumentBuilderFactoryImpl");

		initDefaultAgents();
	}

	/**
	 * Generate a new stub METS document using defaults values appropriate for
	 * an AIP
	 */
	protected HaSMETSProfile() {

		super();
		MetsMetsType mets = metsDoc.getMets();
		mets.setLABEL("[LABEL]");
		mets.setOBJID("[IDENTIFIER]");
		
		MetsMetsType.MetsHdr hdr = mets.getMetsHdr();
		MetsMetsType.MetsHdr.AltRecordID altID = hdr.addNewAltRecordID();
		altID.setTYPE(HaSMETSProfile.PREMISIdentifierType.LOCAL.toString());
		altID.setStringValue("[IDENTIFIER]");
		
		MetsDivType div = mets.getStructMapArray(0).getDiv();
		MetsMdSecType primRep = null;
		try {
			primRep = this.newPREMISObjectTechMD(div,
					METSProfile.PREMISIdentifierType.LOCAL, "[IDENTIFIER]");
		} catch (HaSMETSProfileException e) {
			throw new RuntimeException(
					"Unable to create new techMD with Premis object", e);
		}
		primRep.setSTATUS(METSProfile.TechMDStatus.PRIMARY_REPRESENTATION
				.toString());

		mets.setPROFILE(this.PROFILE_URI);

		MetsMdSecType dmdSec = mets.addNewDmdSec();
		dmdSec.setSTATUS(HaSMETSProfile.DMDSecStatus.PRIMARY_DMDSEC.toString());
		XmlID dmdID = this.getNewID(null, dmdSec);
		dmdSec.setCREATED(Calendar.getInstance());
		ArrayList<String> lst = new ArrayList<String>();
		lst.add(dmdID.getStringValue());

		//MetsDivType div = mets.getStructMapArray(0).getDiv();
		div.setDMDID(lst);
		MetsMdSecType.MdWrap wrap = dmdSec.addNewMdWrap();
		wrap.setMDTYPE(MetsMdSecType.MdWrap.MDTYPE.MODS);
		wrap.setMIMETYPE("text/xml");
		MetsMdSecType.MdWrap.XmlData xml = wrap.addNewXmlData();

		ModsModsDocument modsDoc = HaSMETSProfile
				.createBasicAquiferModsDocument("[Title goes here]",
						ModsTypeOfResourceType.TEXT,
						ModsPhysicalDescriptionType.DigitalOrigin.BORN_DIGITAL,
						"text/plain", "http://example.org/");

		this.setXmlDataAny(xml, modsDoc);

		PremisEventDocument event = HaSMETSProfile.createBasicPremisEvent(
				HaSMETSProfile.PREMISEventType.METADATA_CREATION,
				"Basic Aquifer MODS Initialized by Program");
		PremisAgentDocument agents[] = { softAgentDoc, humanAgentDoc };
		HaSMETSProfile.PREMISLinkingAgentRole roles[] = {
				HaSMETSProfile.PREMISLinkingAgentRole.SOFTWARE_USED,
				HaSMETSProfile.PREMISLinkingAgentRole.EVENT_INITIATOR };
		this.addEventToXmlObject(dmdSec, event, agents, roles);

		initIDs();
	}

	/**
	 * Create a new METS object from the given MetsMetsDocument with the given
	 * baseURI
	 * 
	 * @param mets
	 *            MetsMetsDocument
	 * @param uri
	 *            URI
	 */
	protected HaSMETSProfile(MetsMetsDocument mets, URI uri) {
		metsDoc = mets;
		baseURI = uri;
		initIDs();
	}

	/**
	 * Create a new METS object from the XML file at the given filepath
	 * 
	 * @param filepath
	 *            the file path to the XML METS file to load
	 */
	protected HaSMETSProfile(String filepath) throws HaSMETSProfileException {

		File f = new File(filepath);
		try {
			FileInputStream fis = new FileInputStream(f);
			//HaSChecksummer summer = new HaSChecksummer();
			//CheckedInputStream chkStrm = new CheckedInputStream(fis, summer);
		
		
			baseURI = f.toURI();
			metsDoc = MetsMetsDocument.Factory.parse(baseURI.toURL(),
					defaultLoadOptions);
			//metsDoc = MetsMetsDocument.Factory.parse(chkStrm);
			
		} catch (XmlException e) {
			throw new HaSMETSProfileException("Invalid XML from this file '"
					+ f.getAbsolutePath() + "'", e);
		} catch (IOException e) {
			throw new HaSMETSProfileException("Unable to access file '"
					+ f.getAbsolutePath() + "'", e);
		}
		initIDs();
	}

	/**
	 * Create a new METS object from the XML file at the given URL
	 * 
	 * @param url
	 *            the location of the XML METS file to load
	 */
	protected HaSMETSProfile(URL url) throws HaSMETSProfileException {

		try {
			baseURI = url.toURI();
			metsDoc = MetsMetsDocument.Factory.parse(url, defaultLoadOptions);
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

		initIDs();
	}

	/**
	 * Associate the given PremisAgentDocument with the given
	 * PremisEventDocument using the given
	 * HaSMETSProfile.PREMISLinkingAgentRole. If there is already a PREMIS agent
	 * with the same identifier as the given agent then use the pre-exisitng
	 * agent; otherwise, create a new digiprovMD conatining the new agent
	 * 
	 * @param event
	 *            PremisEventDocument
	 * @param agent
	 *            PremisAgentDocument
	 * @param agentRole
	 *            HaSMETSProfile.PREMISLinkingAgentRole
	 * @return the MetsMdSecType containing the agent
	 */
	public MetsMdSecType addAgentToEvent(PremisEventDocument event,
			PremisAgentDocument agent,
			HaSMETSProfile.PREMISLinkingAgentRole agentRole) {

		MetsMdSecType agentMdSec = this.getMdSecWith(agent);

		String id = null;
		if (agentMdSec == null) {
			// we need to create an mdSec for the agent
			agentMdSec = this.addAmdSecXmlWrapper(
					HaSMETSProfile.AMD_SECTION.DIGIPROVMD,
					MetsMdSecType.MdWrap.MDTYPE.PREMIS, null);
			this.setXmlDataAny(agentMdSec.getMdWrap().getXmlData(), agent);
		}

		id = agentMdSec.getID();
		// add linkingAgentIdentifier to the event if needed

		// first look for agent ids in the event
		PremisAgentDocument.Agent.AgentIdentifier aids[] = agent.getAgent()
				.getAgentIdentifierArray();
		PremisEventDocument.Event.LinkingAgentIdentifier laids[] = event
				.getEvent().getLinkingAgentIdentifierArray();

		boolean foundMatch = false;
		for (int i = 0; i < aids.length; i++) {
			for (int j = 0; j < laids.length; j++) {
				if (aids[i].getAgentIdentifierType().compareToIgnoreCase(
						laids[j].getLinkingAgentIdentifierType()) == 0
						&& aids[i]
								.getAgentIdentifierValue()
								.compareToIgnoreCase(
										laids[j]
												.getLinkingAgentIdentifierValue()) == 0) {
					laids[j].setLinkAgentXmlID(id);
					laids[j].addLinkingAgentRole("SOME ROLE"); // TODO roles
					foundMatch = true;
				}
			}
		}

		if (!foundMatch) {
			// use the first agentIdentifier to create a link in the event
			PremisEventDocument.Event.LinkingAgentIdentifier newId = event
					.getEvent().addNewLinkingAgentIdentifier();
			newId.setLinkAgentXmlID(id);
			newId.setLinkingAgentIdentifierType(aids[0]
					.getAgentIdentifierType());
			newId.setLinkingAgentIdentifierValue(aids[0]
					.getAgentIdentifierValue());
			newId.addLinkingAgentRole(agentRole.toString());
		}

		this.setLastModifiedToCurrent();
		return null;
	}

	/**
	 * Add a new alternate dmdSec to the document. The dmdSec will reference the
	 * given file (METS mdRef). A reference to the new dmdSec will be added to
	 * the root div element of any structMaps
	 * 
	 * @param type
	 *            the MetsMdSecType.MdRef.MDTYPE for newly added dmdSec
	 * @param otherType
	 *            if type is OTHER then set this to the other type
	 * @param file
	 *            java.io.File representing MD to be referenced from a mdRef
	 *            element
	 * @return the newly added dmdSec
	 */
	public MetsMdSecType addAlternateDmdSec(
			MetsMdSecType.MdRef.MDTYPE.Enum type, String otherType, File file) {
		return addAlternateDmdSec(null, type, otherType, null, file);
	}

	/**
	 * Add a new alternate dmdSec to the document. The dmdSec will wrap given
	 * XML DOM Node (METS mdWrap), or reference (METS mdRef) a given file. A
	 * reference to the new dmdSec will be added to the root div element of any
	 * structMaps
	 * 
	 * @param type
	 *            the MetsMdSecType.MdWrap.MDTYPE for newly added dmdSec
	 * @param type
	 *            the MetsMdSecType.MdRef.MDTYPE for newly added dmdSec
	 * @param otherType
	 *            if type is OTHER then set this to the other type
	 * @param xmlAny
	 *            a DOM Node containing the XML for the newly added dmdSec
	 * @param file
	 *            java.io.File representing MD to be referenced from a mdRef
	 *            element
	 * @return the newly added dmdSec
	 */
	private MetsMdSecType addAlternateDmdSec(
			MetsMdSecType.MdWrap.MDTYPE.Enum wraptype,
			MetsMdSecType.MdRef.MDTYPE.Enum reftype, String otherType,
			Node xmlAny, File file) {

		MetsMdSecType dmdSec = metsDoc.getMets().addNewDmdSec();
		dmdSec.setSTATUS(HaSMETSProfile.DMDSecStatus.ALTERNATE_DMDSEC
				.toString());
		XmlID dmdID = this.getNewID(null, dmdSec);
		dmdSec.setCREATED(Calendar.getInstance());

		// reference the new dmd from all the structMaps
		MetsStructMapType smaps[] = metsDoc.getMets().getStructMapArray();
		for (int i = 0; i < smaps.length; i++) {
			if (smaps[i].getDiv().isSetDMDID()) {
				List dmdid2 = smaps[i].getDiv().getDMDID();
				ArrayList<String> lst = new ArrayList<String>(
						(Collection<? extends String>) dmdid2);
				lst.add(dmdID.getStringValue());
				smaps[i].getDiv().setDMDID(lst);
			} else {
				ArrayList<String> lst = new ArrayList<String>();
				lst.add(dmdID.getStringValue());
				smaps[i].getDiv().setDMDID(lst);
			}
		}

		// if there is no file given then add the XML node
		if (file == null) {
			MetsMdSecType.MdWrap wrap = dmdSec.addNewMdWrap();
			wrap.setMDTYPE(wraptype);
			if (otherType != null && otherType.length() > 0)
				wrap.setOTHERMDTYPE(otherType);
			wrap.setMIMETYPE("text/xml");
			MetsMdSecType.MdWrap.XmlData xml = wrap.addNewXmlData();
			this.setXmlDataAny(xml, xmlAny);
			this.setLastModifiedToCurrent();
		} else { // add a reference
			MetsMdSecType.MdRef ref = dmdSec.addNewMdRef();
			ref.setMDTYPE(reftype);
			if (otherType != null && otherType.length() > 0)
				ref.setOTHERMDTYPE(otherType);
			ref.setMIMETYPE("text/xml");
			ref.setHref(file.getPath());
		}
		return dmdSec;
	}

	/**
	 * Add a new alternate dmdSec to the document. The dmdSec will wrap the
	 * given XML DOM Node (METS mdWrap). A reference to the new dmdSec will be
	 * added to the root div element of any structMaps
	 * 
	 * @param type
	 *            the MetsMdSecType.MdWrap.MDTYPE for newly added dmdSec
	 * @param otherType
	 *            if type is OTHER then set this to the other type
	 * @param xmlAny
	 *            a DOM Node containing the XML for the newly added dmdSec
	 * @return the newly added dmdSec
	 */
	public MetsMdSecType addAlternateDmdSec(
			MetsMdSecType.MdWrap.MDTYPE.Enum type, String otherType, Node xmlAny) {
		return addAlternateDmdSec(type, null, otherType, xmlAny, null);
	}

	/**
	 * Add the given PREMIS event as a new amdSec/digiprovMD element, also
	 * associate the given agents to the event
	 * 
	 * @param mdSec
	 *            the MetsMdSecType element to associate with the event
	 * @param event
	 *            the PremisEventDocument to use
	 * @return the MetsMdSecType for the newly added digiprovMD
	 */
	public MetsMdSecType addEventToXmlObject(XmlObject mdSec,
			PremisEventDocument event) {

		MetsMdSecType digiprov = addAmdSecXmlWrapperToXmlObject(
				HaSMETSProfile.AMD_SECTION.DIGIPROVMD, mdSec,
				MetsMdSecType.MdWrap.MDTYPE.PREMIS);
		setXmlDataAny(digiprov.getMdWrap().getXmlData(), event);

		this.setLastModifiedToCurrent();

		return digiprov;
	}

	/**
	 * Add the given PREMIS event as a new amdSec/digiprovMD element, also
	 * associate the given agents to the event
	 * 
	 * @param mdSec
	 *            the MetsMdSecType element to associate with the event
	 * @param event
	 *            the PremisEventDocument to use
	 * @param agents
	 *            an array of PremisAgentDocument elements associated to the
	 *            event
	 * @param roles
	 *            an array of HaSMETSProfile.PREMISLinkingAgentRole which are
	 *            associated with each agent
	 * @return the MetsMdSecType for the newly added digiprovMD
	 */
	public MetsMdSecType addEventToXmlObject(XmlObject mdSec,
			PremisEventDocument event, PremisAgentDocument agents[],
			HaSMETSProfile.PREMISLinkingAgentRole roles[]) {

		for (int i = 0; i < agents.length; i++) {
			this.addAgentToEvent(event, agents[i], roles[i]);
		}

		MetsMdSecType digiprov = addAmdSecXmlWrapperToXmlObject(
				HaSMETSProfile.AMD_SECTION.DIGIPROVMD, mdSec,
				MetsMdSecType.MdWrap.MDTYPE.PREMIS);
		setXmlDataAny(digiprov.getMdWrap().getXmlData(), event);

		this.setLastModifiedToCurrent();

		return digiprov;
	}

	/**
	 * Add a new file and associated technical metadata to the METS document
	 * 
	 * @param file
	 * @return MetsFileType
	 * @throws HaSMETSProfileException
	 */
	public MetsFileType addFile(File file) throws HaSMETSProfileException {

		MetsFileType ft = null;
		if (!metsDoc.getMets().isSetFileSec()) {
			ft = metsDoc.getMets().addNewFileSec().addNewFileGrp().addNewFile();
		} else if (metsDoc.getMets().getFileSec().sizeOfFileGrpArray() == 0) {
			ft = metsDoc.getMets().getFileSec().addNewFileGrp().addNewFile();
		} else {
			ft = metsDoc.getMets().getFileSec().getFileGrpArray(0).addNewFile();
		}

		XmlID id = this.getNewID("FILE", ft);

		MetsFileType.FLocat floc = ft.addNewFLocat();
		floc.setLOCTYPE(MetsFileType.FLocat.LOCTYPE.URL);

		URI furi = file.toURI();
		URI buri = this.getBaseURIMinusFile();
		URI relative = buri.relativize(furi);
		floc.setHref(relative.toASCIIString());

		MetsMdSecType mdsec = this.newPREMISObjectTechMD(ft,
				HaSMETSProfile.PREMISIdentifierType.URL, relative.toASCIIString());

		// set checksum/mimetype/size/date for ft
		try {
			PremisObjectDocument.Object pObj = PremisObjectDocument.Factory
					.parse(this.getXmlDataAny(mdsec.getMdWrap().getXmlData()))
					.getObject();

			ft.setCREATED(ft.getCREATED());

			PremisObjectDocument.Object.ObjectCharacteristics pObjChr = pObj
					.getObjectCharacteristicsArray(0);

			for (int i = 0; i < pObjChr.getFixityArray(0)
					.getMessageDigestAlgorithm().length(); i++) {
				if (MetsFileType.CHECKSUMTYPE.SHA_1.toString().compareTo(
						pObjChr.getFixityArray(0).getMessageDigestAlgorithm()) == 0) {
					ft
							.setCHECKSUM(pObjChr.getFixityArray(0)
									.getMessageDigest());
					ft.setCHECKSUMTYPE(MetsFileType.CHECKSUMTYPE.SHA_1);
				}
			}
			ft.setMIMETYPE(pObjChr.getFormat().getFormatDesignation()
					.getFormatName());
			ft.setSIZE(pObjChr.getSize());

		} catch (XmlException e) {
			throw new HaSMETSProfileException(e.getMessage());
		}

		return ft;
	}

	/**
	 * Add a new checksum to the file. This checksum will replace any CHECKSUM
	 * attributes on the file element, and it will also be added to the PREMIS
	 * object techMD, if any
	 * 
	 * @param file
	 *            the file of interest
	 * @param cType
	 *            the checksum type
	 * @param cValue
	 *            the checksum value
	 * @throws HaSMETSProfileException
	 */
	public void addNewChecksum(MetsFileType file,
			MetsFileType.CHECKSUMTYPE.Enum cType, String cValue)
			throws HaSMETSProfileException {
		file.setCHECKSUMTYPE(cType);
		file.setCHECKSUM(cValue);

		MetsMdSecType md[] = this.getSpecificAmdSecXmlData(file,
				HaSMETSProfile.AMD_SECTION.TECHMD, new QName(
						HaSMETSProfile.PREMIS_NS, "object"));

		if (md.length != 0) {
			try {
				PremisObjectDocument pobj = (PremisObjectDocument) this
						.getMDSecXmlObject(md[0]);
				if (!this.hasChecksum(pobj, cType, cValue)) {
					PremisObjectDocument.Object.ObjectCharacteristics oChar = pobj
							.getObject().getObjectCharacteristicsArray(0);
					PremisObjectDocument.Object.ObjectCharacteristics.Fixity oFix = oChar
							.addNewFixity();
					oFix.setMessageDigestAlgorithm(cType.toString());
					oFix.setMessageDigest(cValue);
					this.setMDSecXmlObject(md[0], pobj);
					this.setLastModifiedToCurrent();
				}
			} catch (HaSMETSProfileException e) {
				throw new HaSMETSProfileException(
						"Unable to add the checksum to the file ID='"
								+ file.getID() + "'", e);
			}
		}
	}

	/**
	 * Add Premis object techMD to each file in the METS document, unless it
	 * already has one
	 * 
	 * @throws HaSMETSProfileException
	 */
	public void addNewPREMISObjectTechMDToAllFile()
			throws HaSMETSProfileException {
		MetsFileType ft[] = this.getAllFiles();

		for (int i = 0; i < ft.length; i++) {
			if (!this.hasSpecificAmdSecXmlData(ft[i],
					HaSMETSProfile.AMD_SECTION.TECHMD, new QName(
							HaSMETSProfile.PREMIS_NS, "object"))) {
				this.newPREMISObjectTechMD(ft[i],
						HaSMETSProfile.PREMISIdentifierType.LOCAL, ft[i]
								.getID());
			}
		}

		this.setLastModifiedToCurrent();

	}

	/**
	 * Add a new PRIMARY_REPRESENTATION Premis Object techMD section to the
	 * document
	 * 
	 * @param idType
	 *            the type of id to use for the object
	 * @param id
	 *            the id to use for the object
	 * @return the newly added MetsMdSecType techMD
	 * @throws HaSMETSProfileException
	 *             if the document already has a PRIMARY_REPRESENTATION
	 */
	public MetsMdSecType addNewPrimaryRepresentation(
			HaSMETSProfile.PREMISIdentifierType idType, String id)
			throws HaSMETSProfileException {
		String xql = "declare namespace m='" + METS_NS
				+ "'; .//m:techMD[@STATUS='PRIMARY_REPRESENTATION']";

		XmlObject o[] = metsDoc.selectPath(xql);

		if (o.length != 0) {
			throw new HaSMETSProfileException(
					"This METS document already has a PRIMARY_REPRESENTATION");
		}

		MetsDivType pDiv = this.getPrimaryStructMap().getDiv();

		MetsMdSecType pRep = this.addAmdSecXmlWrapperToXmlObject(
				HaSMETSProfile.AMD_SECTION.TECHMD, pDiv,
				MetsMdSecType.MdWrap.MDTYPE.PREMIS);

		PremisObjectDocument pObjDoc = HaSMETSProfile.createBasicPremisObject(
				idType, id, HaSMETSProfile.PREMISObjectCategory.REPRESENTATION);

		// if there are altRecordIDs add them to the PREMIS Object
		if (metsDoc.getMets().isSetMetsHdr()) {
			MetsMetsType.MetsHdr.AltRecordID altid[] = metsDoc.getMets()
					.getMetsHdr().getAltRecordIDArray();
			for (int i = 0; i < altid.length; i++) {
				if (altid[i].getStringValue().compareTo(id) != 0) {
					PremisObjectDocument.Object.ObjectIdentifier oid = pObjDoc
							.getObject().addNewObjectIdentifier();
					oid.setObjectIdentifierValue(altid[i].getStringValue());
					oid.setObjectIdentifierType(altid[i].getTYPE());
				}
			}
		}

		this.setXmlDataAny(pRep.getMdWrap().getXmlData(), pObjDoc);
		this.setLastModifiedToCurrent();

		pRep.setSTATUS(HaSMETSProfile.TechMDStatus.PRIMARY_REPRESENTATION
				.toString());
		return pRep;

	}

	/**
	 * Add an objectIdentifier to the files associated PREMIS object technical
	 * metadata
	 * 
	 * @param file
	 *            the file of interest
	 * @param idType
	 *            the type of the new identifier
	 * @param idVal
	 *            the value of the new identifier
	 * @throws HaSMETSProfileException
	 *             if the file does not have an associated premis techMD
	 */
	public void addPremisObjectIdentifierToFile(MetsFileType file,
			HaSMETSProfile.PREMISIdentifierType idType, String idVal,
			String xlTitle) throws HaSMETSProfileException {
		MetsMdSecType md[] = this.getSpecificAmdSecXmlData(file,
				HaSMETSProfile.AMD_SECTION.TECHMD, new QName(
						HaSMETSProfile.PREMIS_NS, "object"));

		if (md.length == 0) {
			throw new HaSMETSProfileException(
					"This file does not have associated PREMIS object technical metadata");
		}

		PremisObjectDocument pdoc = (PremisObjectDocument) this
				.getMDSecXmlObject(md[0]);
		PremisObjectDocument.Object.ObjectIdentifier oid = pdoc.getObject()
				.addNewObjectIdentifier();
		if (xlTitle != null && xlTitle.length() > 0) {
			Element oNode = (Element) oid.getDomNode();
			oNode.setAttributeNS(HaSMETSProfile.XLINK_NS, "title", xlTitle);
		}
		oid.setObjectIdentifierType(idType.toString());
		oid.setObjectIdentifierValue(idVal);

		this.setMDSecXmlObject(md[0], pdoc);

		this.setLastModifiedToCurrent();

	}

	/**
	 * Add a techMD/mdWrap/xmlData/any section of the appropriate type and link
	 * it to the given file element via the file elements ADMID attribute.
	 * 
	 * @param ft
	 *            the FileType element to attach the new techMD to
	 * @param xobj
	 *            the XmlObject to add under the xmlData section
	 * @return the newly added techMD section
	 * 
	 * @throws HaSMETSProfileException
	 *             if an unexpected type of XmlObject is passed
	 */
	public MetsMdSecType addTechMDXmlObjectToFile(MetsFileType ft,
			XmlObject xobj) throws HaSMETSProfileException {

		Node root = getRootElementFromXmlData(xobj);
		QName qn = getQName(root);

		MetsMdSecType.MdWrap.MDTYPE.Enum xmlType = null;
		String otherType = null;

		try {
			String type = HaSMETSProfile.ObjectTypes.getMDTYPE("techMD", qn);
			xmlType = MetsMdSecType.MdWrap.MDTYPE.Enum.forString(type);
			otherType = HaSMETSProfile.ObjectTypes.getOTHERMDTYPE("techMD", qn);
		} catch (IllegalArgumentException e) {
			throw new HaSMETSProfileException("Unexpected xobj '"
					+ qn.toString() + "'", e);
		}

		MetsMdSecType techmd = addAmdSecXmlWrapperToXmlObject(
				HaSMETSProfile.AMD_SECTION.TECHMD, ft, xmlType, otherType);
		setXmlDataAny(techmd.getMdWrap().getXmlData(), xobj);

		this.setLastModifiedToCurrent();
		return techmd;
	}

	/**
	 * Associate the given MetsMdSecType with the given XmlObject via the ADMID
	 * attribute of the XmlObject
	 * 
	 * @param mdSec
	 * @param xobj
	 */
	public void associateAmdSecToXmlObject(MetsMdSecType mdSec, XmlObject xobj) {
		SchemaType st = xobj.schemaType();
		SchemaProperty sp = st.getAttributeProperty(new QName("ADMID"));
		if (sp == null) {
			throw new RuntimeException(
					"The XmlObject cannot have an ADMID attribute");
		}

		XmlID xid = mdSec.xgetID();

		// cast the XmlObject to the appropriate type and set the ADMID
		// attribute
		if (xobj instanceof MetsMetsType.MetsHdr) {
			if (((MetsMetsType.MetsHdr) xobj).isSetADMID()) {
				List admid = ((MetsMetsType.MetsHdr) xobj).getADMID();
				ArrayList<String> lst = new ArrayList<String>(
						(Collection<? extends String>) admid);
				lst.add(xid.getStringValue());
				((MetsMetsType.MetsHdr) xobj).setADMID(lst);
			} else {
				ArrayList<String> lst = new ArrayList<String>();
				lst.add(xid.getStringValue());
				((MetsMetsType.MetsHdr) xobj).setADMID(lst);
			}
		} else if (xobj instanceof MetsFileGrpType) {
			if (((MetsFileGrpType) xobj).isSetADMID()) {
				List admid = ((MetsFileGrpType) xobj).getADMID();
				ArrayList<String> lst = new ArrayList<String>(
						(Collection<? extends String>) admid);
				lst.add(xid.getStringValue());
				((MetsFileGrpType) xobj).setADMID(lst);
			} else {
				ArrayList<String> lst = new ArrayList<String>();
				lst.add(xid.getStringValue());
				((MetsFileGrpType) xobj).setADMID(lst);
			}
		} else if (xobj instanceof MetsDivType) {
			if (((MetsDivType) xobj).isSetADMID()) {
				List admid = ((MetsDivType) xobj).getADMID();
				ArrayList<String> lst = new ArrayList<String>(
						(Collection<? extends String>) admid);
				lst.add(xid.getStringValue());
				((MetsDivType) xobj).setADMID(lst);
			} else {
				ArrayList<String> lst = new ArrayList<String>();
				lst.add(xid.getStringValue());
				((MetsDivType) xobj).setADMID(lst);
			}
		} else if (xobj instanceof MetsAreaType) {
			if (((MetsAreaType) xobj).isSetADMID()) {
				List admid = ((MetsAreaType) xobj).getADMID();
				ArrayList<String> lst = new ArrayList<String>(
						(Collection<? extends String>) admid);
				lst.add(xid.getStringValue());
				((MetsAreaType) xobj).setADMID(lst);
			} else {
				ArrayList<String> lst = new ArrayList<String>();
				lst.add(xid.getStringValue());
				((MetsAreaType) xobj).setADMID(lst);
			}
		} else if (xobj instanceof MetsBehaviorType) {
			if (((MetsBehaviorType) xobj).isSetADMID()) {
				List admid = ((MetsBehaviorType) xobj).getADMID();
				ArrayList<String> lst = new ArrayList<String>(
						(Collection<? extends String>) admid);
				lst.add(xid.getStringValue());
				((MetsBehaviorType) xobj).setADMID(lst);
			} else {
				ArrayList<String> lst = new ArrayList<String>();
				lst.add(xid.getStringValue());
				((MetsBehaviorType) xobj).setADMID(lst);
			}
		} else if (xobj instanceof MetsMdSecType) {
			if (((MetsMdSecType) xobj).isSetADMID()) {
				List admid = ((MetsMdSecType) xobj).getADMID();
				ArrayList<String> lst = new ArrayList<String>(
						(Collection<? extends String>) admid);
				lst.add(xid.getStringValue());
				((MetsMdSecType) xobj).setADMID(lst);
			} else {
				ArrayList<String> lst = new ArrayList<String>();
				lst.add(xid.getStringValue());
				((MetsMdSecType) xobj).setADMID(lst);
			}
		} else if (xobj instanceof MetsFileType.Stream) {
			if (((MetsFileType.Stream) xobj).isSetADMID()) {
				List admid = ((MetsFileType.Stream) xobj).getADMID();
				ArrayList<String> lst = new ArrayList<String>(
						(Collection<? extends String>) admid);
				lst.add(xid.getStringValue());
				((MetsFileType.Stream) xobj).setADMID(lst);
			} else {
				ArrayList<String> lst = new ArrayList<String>();
				lst.add(xid.getStringValue());
				((MetsFileType.Stream) xobj).setADMID(lst);
			}
		} else if (xobj instanceof MetsFileType) {
			if (((MetsFileType) xobj).isSetADMID()) {
				List admid = ((MetsFileType) xobj).getADMID();
				ArrayList<String> lst = new ArrayList<String>(
						(Collection<? extends String>) admid);
				lst.add(xid.getStringValue());
				((MetsFileType) xobj).setADMID(lst);
			} else {
				ArrayList<String> lst = new ArrayList<String>();
				lst.add(xid.getStringValue());
				((MetsFileType) xobj).setADMID(lst);
			}
		} else {
			throw new RuntimeException("The XmlObject '"
					+ xobj.getDomNode().getNodeName() + "' was not expected");

		}
	}

	/**
	 * Augment the ModsModsDocument so that it conforms to the Aquifer profile.
	 * Missing values will be derived from existing data in the profile when
	 * possible. If not possible default or boilerplate values will be supplied.
	 * All changes will be logged using log4j.
	 * 
	 * @param modsDoc
	 *            the ModsModsDocument to augment
	 * @return the augmented ModsModsDocument
	 */
	public ModsModsDocument augmentAquiferModsDocument(ModsModsDocument modsDoc) {

		ModsModsType mods = modsDoc.getMods();
		ModsBaseTitleInfoType mtitlei[] = mods.getTitleInfoArray();
		if (mtitlei == null || mtitlei.length == 0) {
			logger
					.warn(this.getBaseURI().toASCIIString()
							+ " PRIMARY_DMDSEC: Missing mods/titleInfo was copied from the /mets/@LABEL attribute");
			ModsTitleInfoType t = mods.addNewTitleInfo();
			XmlString tt = t.insertNewTitle(0);
			tt.setStringValue(metsDoc.getMets().getLABEL());
			mtitlei = mods.getTitleInfoArray();
		}
		boolean foundTitle = false;
		for (int i = 0; i < mtitlei.length; i++) {
			if (mtitlei[i].getTitleArray().length != 0) {
				foundTitle = true;
			}
		}
		if (!foundTitle) {
			logger
					.warn(this.getBaseURI().toASCIIString()
							+ " PRIMARY_DMDSEC: Missing title was added to the first mods/titleInfo with the content copied from the /mets/@LABEL attribute");
			XmlString tt = mtitlei[0].insertNewTitle(0);
			tt.setStringValue(metsDoc.getMets().getLABEL());
			mtitlei = mods.getTitleInfoArray();
		}

		ModsNameType mname[] = mods.getNameArray();
		for (int i = 0; i < mname.length; i++) {
			if (mname[i].getNamePartArray().length == 0) {
				logger
						.warn(this.getBaseURI().toASCIIString()
								+ " PRIMARY_DMDSEC: Missing mods/name/namePart was set to a value of '*********'.");
				XmlString nn = mname[i].insertNewNamePart(0);
				nn.setStringValue("*********");
			}
		}

		if (mods.getTypeOfResourceArray().length == 0) {
			// TODO: write method to guess the type of resource based on the
			// mime types of the files
			logger
					.warn(this.getBaseURI().toASCIIString()
							+ " PRIMARY_DMDSEC: Missing mods/typeOfResource was added based on the aggregate MIMETYPE of all the files.");
			ModsTypeOfResourceType rt = mods.addNewTypeOfResource();
			rt.setStringValue(guessTypeOfResource().toString());
		}

		ModsGenreType mgen[] = mods.getGenreArray();
		for (int i = 0; i < mgen.length; i++) {
			if (!mgen[i].isSetAuthority()) {
				logger
						.warn(this.getBaseURI().toASCIIString()
								+ " PRIMARY_DMDSEC: Missing mods/genre/@authority was set to 'unknown'.");
				mgen[i].setAuthority("unknown");
			}
		}

		ModsOriginInfoType morigin[] = mods.getOriginInfoArray();
		if (morigin == null || morigin.length == 0) {
			logger
					.warn(this.getBaseURI().toASCIIString()
							+ " PRIMARY_DMDSEC: Missing mods/originInfo was added with the key date copied from the mets/metsHdr/@CREATEDATE attribute");
			ModsOriginInfoType moi = mods.addNewOriginInfo();
			ModsDateOtherType mdo = moi.addNewDateOther();
			mdo.setKeyDate(ModsDateType.KeyDate.YES);
			mdo.setEncoding(ModsBaseDateType.Encoding.W_3_CDTF);
			mdo.setStringValue(metsDoc.getMets().getMetsHdr().xgetCREATEDATE()
					.getStringValue());
			morigin = mods.getOriginInfoArray();
		}

		ModsDateType mdate[] = null;
		ArrayList<ModsDateType> keyDates = new ArrayList<ModsDateType>();
		for (int i = 0; i < morigin.length; i++) {
			// make sure that place elements have the required children
			ModsPlaceType mplace[] = morigin[i].getPlaceArray();
			for (int k = 0; k < mplace.length; k++) {
				ModsPlaceTermType mplaceterm[] = mplace[k].getPlaceTermArray();
				if (mplaceterm.length == 0) {
					logger
							.warn(this.getBaseURI().toASCIIString()
									+ " PRIMARY_DMDSEC: Missing mods/originInfo/place/placeTerm was set to a value of '*********'.");
					ModsPlaceTermType pt = mplace[k].addNewPlaceTerm();
					pt.setStringValue("*********");
					pt.setType(ModsCodeOrText.TEXT);
					mplaceterm = mplace[k].getPlaceTermArray();
				}
				for (int l = 0; l < mplaceterm.length; l++) {
					if (!mplaceterm[l].isSetType()) {
						if (mplaceterm[l].getStringValue().trim().length() > 3) {
							logger
									.warn(this.getBaseURI().toASCIIString()
											+ " PRIMARY_DMDSEC: Missing mods/originInfo/place/placeTerm/@type was set to 'text'.");
							mplaceterm[l].setType(ModsCodeOrText.TEXT);
						} else {
							logger
									.warn(this.getBaseURI().toASCIIString()
											+ " PRIMARY_DMDSEC: Missing mods/originInfo/place/placeTerm/@type was set to 'code'.");
							mplaceterm[l].setType(ModsCodeOrText.CODE);
						}
					}
					if (mplaceterm[l].getType() == ModsCodeOrText.CODE
							&& !mplaceterm[l].isSetAuthority()) {
						// TODO: Explore guessing the authority based on the
						// code value -- may not be possible
						if (mplaceterm[l].getStringValue().length() == 2
								|| mplaceterm[l].getStringValue().length() == 3) {
							logger
									.warn(this.getBaseURI().toASCIIString()
											+ " PRIMARY_DMDSEC: Missing mods/originInfo/place/placeTerm/@authority was set to 'ISO 3166'.");
							mplaceterm[l]
									.setAuthority(ModsPlaceAuthority.ISO_3166);
						} else {
							logger
									.warn(this.getBaseURI().toASCIIString()
											+ " PRIMARY_DMDSEC: Missing mods/originInfo/place/placeTerm/@authority was set to 'MARCGAC'.");
							mplaceterm[l]
									.setAuthority(ModsPlaceAuthority.MARCGAC);
						}
					}
				}
			}

			// make sure there is at least one date type element
			if (morigin[i].getCopyrightDateArray().length == 0
					&& morigin[i].getDateCapturedArray().length == 0
					&& morigin[i].getDateCreatedArray().length == 0
					&& morigin[i].getDateIssuedArray().length == 0
					&& morigin[i].getDateModifiedArray().length == 0
					&& morigin[i].getDateOtherArray().length == 0
					&& morigin[i].getDateValidArray().length == 0) {
				logger
						.warn(this.getBaseURI().toASCIIString()
								+ " PRIMARY_DMDSEC: Missing mods/originInfo/dateOther was copied from the mets/metsHdr/@CREATEDATE attribute.");
				ModsDateOtherType mdo = morigin[i].addNewDateOther();
				mdo.setKeyDate(ModsDateType.KeyDate.YES);
				mdo.setEncoding(ModsBaseDateType.Encoding.W_3_CDTF);
				mdo.setStringValue(metsDoc.getMets().getMetsHdr()
						.xgetCREATEDATE().getStringValue());

			}
			// make sure that exactly one of them has a keyDate='yes' attribute
			mdate = morigin[i].getCopyrightDateArray();
			for (int j = 0; j < mdate.length; j++) {
				if (mdate[j].isSetKeyDate()) {
					keyDates.add(mdate[j]);
				}
			}
			mdate = morigin[i].getDateCreatedArray();
			for (int j = 0; j < mdate.length; j++) {
				if (mdate[j].isSetKeyDate()) {
					keyDates.add(mdate[j]);
				}
			}
			mdate = morigin[i].getDateIssuedArray();
			for (int j = 0; j < mdate.length; j++) {
				if (mdate[j].isSetKeyDate()) {
					keyDates.add(mdate[j]);
				}
			}
			mdate = morigin[i].getDateCapturedArray();
			for (int j = 0; j < mdate.length; j++) {
				if (mdate[j].isSetKeyDate()) {
					keyDates.add(mdate[j]);
				}
			}
			mdate = morigin[i].getDateValidArray();
			for (int j = 0; j < mdate.length; j++) {
				if (mdate[j].isSetKeyDate()) {
					keyDates.add(mdate[j]);
				}
			}
			mdate = morigin[i].getDateModifiedArray();
			for (int j = 0; j < mdate.length; j++) {
				if (mdate[j].isSetKeyDate()) {
					keyDates.add(mdate[j]);
				}
			}
			mdate = morigin[i].getDateOtherArray();
			for (int j = 0; j < mdate.length; j++) {
				if (mdate[j].isSetKeyDate()) {
					keyDates.add(mdate[j]);
				}
			}
			if (keyDates.isEmpty()) {
				ModsDateType mdo = null;
				if (morigin[i].sizeOfDateIssuedArray() > 0
						&& (mdo = morigin[i].getDateIssuedArray(0)) != null) {
					logger
							.warn(this.getBaseURI().toASCIIString()
									+ " PRIMARY_DMDSEC: No originInfo date had @keyDate='yes' so it was set on the dateIssued");
					mdo.setKeyDate(ModsDateType.KeyDate.YES);
				} else if (morigin[i].sizeOfDateCreatedArray() > 0
						&& (mdo = morigin[i].getDateCreatedArray(0)) != null) {
					logger
							.warn(this.getBaseURI().toASCIIString()
									+ " PRIMARY_DMDSEC: No originInfo date had @keyDate='yes' so it was set on the dateCreated");
					mdo.setKeyDate(ModsDateType.KeyDate.YES);
				} else if (morigin[i].sizeOfCopyrightDateArray() > 0
						&& (mdo = morigin[i].getCopyrightDateArray(0)) != null) {
					logger
							.warn(this.getBaseURI().toASCIIString()
									+ " PRIMARY_DMDSEC: No originInfo date had @keyDate='yes' so it was set on the copyrightDate");
					mdo.setKeyDate(ModsDateType.KeyDate.YES);
				} else if (morigin[i].sizeOfDateOtherArray() > 0
						&& (mdo = morigin[i].getDateOtherArray(0)) != null) {
					logger
							.warn(this.getBaseURI().toASCIIString()
									+ " PRIMARY_DMDSEC: No originInfo date had @keyDate='yes' so it was set on the dateOther");
					mdo.setKeyDate(ModsDateType.KeyDate.YES);
				} else if (morigin[i].sizeOfDateCapturedArray() > 0
						&& (mdo = morigin[i].getDateCapturedArray(0)) != null) {
					logger
							.warn(this.getBaseURI().toASCIIString()
									+ " PRIMARY_DMDSEC: No originInfo date had @keyDate='yes' so it was set on the dateCaptured");
					mdo.setKeyDate(ModsDateType.KeyDate.YES);
				} else if (morigin[i].sizeOfDateValidArray() > 0
						&& (mdo = morigin[i].getDateValidArray(0)) != null) {
					logger
							.warn(this.getBaseURI().toASCIIString()
									+ " PRIMARY_DMDSEC: No originInfo date had @keyDate='yes' so it was set on the dateValid");
					mdo.setKeyDate(ModsDateType.KeyDate.YES);
				} else if (morigin[i].sizeOfDateModifiedArray() > 0
						&& (mdo = morigin[i].getDateModifiedArray(0)) != null) {
					logger
							.warn(this.getBaseURI().toASCIIString()
									+ " PRIMARY_DMDSEC: No originInfo date had @keyDate='yes' so it was set on the dateModified");
					mdo.setKeyDate(ModsDateType.KeyDate.YES);
				}
			} else if (keyDates.size() > 1) {
				logger
						.warn(this.getBaseURI().toASCIIString()
								+ " PRIMARY_DMDSEC: There are multiple originInfo dates with a @keyDate='yes'; all but "
								+ keyDates.get(0).getDomNode().getLocalName()
								+ " will have this attribute removed.");
				for (int q = 1; q < keyDates.size(); q++) {
					keyDates.get(q).unsetKeyDate();
				}
			}
		}

		ModsLanguageType mlang[] = mods.getLanguageArray();
		for (int i = 0; i < mlang.length; i++) {
			if (mlang[i].getLanguageTermArray().length == 0) {
				logger
						.warn(this.getBaseURI().toASCIIString()
								+ " PRIMARY_DMDSEC: Missing mods/language/languageTerm set to 'Undetermined'.");
				ModsLanguageType.LanguageTerm ml = mlang[i]
						.addNewLanguageTerm();
				ml.setStringValue("Undetermined");
				ml.setType(ModsCodeOrText.TEXT);
				ml = mlang[i].addNewLanguageTerm();
				ml.setStringValue("und");
				ml.setType(ModsCodeOrText.CODE);
				ml
						.setAuthority(ModsLanguageType.LanguageTerm.Authority.ISO_639_2_B);
			}
			ModsLanguageType.LanguageTerm mlangterm[] = mlang[i]
					.getLanguageTermArray();
			for (int l = 0; l < mlangterm.length; l++) {
				if (!mlangterm[l].isSetType()) {
					if (mlangterm[l].getStringValue().length() > 3) {
						logger
								.warn(this.getBaseURI().toASCIIString()
										+ " PRIMARY_DMDSEC: Missing mods/language/languageTerm/@type set to 'text'.");
						mlangterm[l].setType(ModsCodeOrText.TEXT);
					} else {
						logger
								.warn(this.getBaseURI().toASCIIString()
										+ " PRIMARY_DMDSEC: Missing mods/language/languageTerm/@type set to 'code'.");
						mlangterm[l].setType(ModsCodeOrText.CODE);
					}
				}
				if (mlangterm[l].getType() == ModsCodeOrText.CODE
						&& !mlangterm[l].isSetAuthority()) {
					if (mlangterm[l].getStringValue().length() == 3) {
						logger
								.warn(this.getBaseURI().toASCIIString()
										+ " PRIMARY_DMDSEC: Missing mods/language/languageTerm/@authority set to 'iso639-2b'");
						mlangterm[l]
								.setAuthority(ModsLanguageType.LanguageTerm.Authority.ISO_639_2_B);
					} else {
						logger
								.warn(this.getBaseURI().toASCIIString()
										+ " PRIMARY_DMDSEC: Missing mods/language/languageTerm/@authority set to 'rfc3066'");
						mlangterm[l]
								.setAuthority(ModsLanguageType.LanguageTerm.Authority.RFC_3066);
					}
				}
			}
		}

		ModsPhysicalDescriptionType mphys[] = mods
				.getPhysicalDescriptionArray();
		if (mphys.length == 0) {
			logger
					.warn(this.getBaseURI().toASCIIString()
							+ " PRIMARY_DMDSEC: Missing mods/physicalDescription added.  The digitalOrigin was set to 'born digital' and the internetMediaType was set based on the mets//file/@MIMETYPE attribute values.");
			ModsPhysicalDescriptionType mphy = mods.addNewPhysicalDescription();
			mphy
					.addDigitalOrigin(ModsPhysicalDescriptionType.DigitalOrigin.BORN_DIGITAL);
			String types[] = this.getAllMIMETypes();
			for (int k = 0; k < types.length; k++) {
				mphy.addInternetMediaType(types[k]);
			}

		} else if (mphys.length > 1) {
			logger
					.warn(this.getBaseURI().toASCIIString()
							+ " PRIMARY_DMDSEC: There are too many mods/physicalDescription elements.  All but the first were removed.");
			for (int j = mphys.length - 1; j > 0; j--) {
				mods.removePhysicalDescription(j);
			}
		} else {
			if (mphys[0].getDigitalOriginArray().length != 1) {
				logger
						.warn(this.getBaseURI().toASCIIString()
								+ " PRIMARY_DMDSEC: Missing mods/physicalDescription/digitalOrigin was set to 'born digital'.");
				mphys[0]
						.addDigitalOrigin(ModsPhysicalDescriptionType.DigitalOrigin.BORN_DIGITAL);
			}
			if (mphys[0].getInternetMediaTypeArray().length == 0) {
				logger
						.warn(this.getBaseURI().toASCIIString()
								+ " PRIMARY_DMDSEC: Missing mods/physicalDescription/internetMediaType were set based on the mets//file/@MIMETYPE attribute values.");
				String types[] = this.getAllMIMETypes();
				for (int k = 0; k < types.length; k++) {
					mphys[0].addInternetMediaType(types[k]);
				}
			}
		}

		ModsSubjectType msubj[] = mods.getSubjectArray();
		for (int i = 0; i < msubj.length; i++) {
			XmlObject xobj[] = msubj[i].selectPath("./*");
			if (xobj.length == 0) {
				logger
						.warn(this.getBaseURI().toASCIIString()
								+ " PRIMARY_DMDSEC: The mods/subject had no children so it was deleted.");
				mods.removeSubject(i);
			}
		}

		ModsClassificationType mclass[] = mods.getClassificationArray();
		for (int i = 0; i < mclass.length; i++) {
			if (!mclass[i].isSetAuthority()) {
				logger
						.warn(this.getBaseURI().toASCIIString()
								+ " PRIMARY_DMDSEC: Missing mods/classification/@authority was set to 'unknown'.");
				mclass[i].setAuthority("unknown");
			}
		}

		ModsRelatedItemType mrelat[] = mods.getRelatedItemArray();
		for (int i = 0; i < mrelat.length; i++) {
			if (!mrelat[i].isSetType()) {
				logger
						.warn(this.getBaseURI().toASCIIString()
								+ " PRIMARY_DMDSEC: Missing relatedItem/@type was set to 'otherVersion'.");
				mrelat[i].setType(ModsRelatedItemType.Type.OTHER_VERSION);
			}
		}

		ModsIdentifierType mident[] = mods.getIdentifierArray();
		for (int i = 0; i < mident.length; i++) {
			if (!mident[i].isSetType()) {
				logger
						.warn(this.getBaseURI().toASCIIString()
								+ " PRIMARY_DMDSEC: Missing mods/identifier/@type was set to '"
								+ this.guessIDType(mident[i].getStringValue())
										.toString() + "'.");
				mident[i].addNewType()
						.setStringValue(
								this.guessIDType(mident[i].getStringValue())
										.toString());
			}
		}
		int primDisplayCnt = 0;
		ArrayList<ModsUrlType> primaryURL = new ArrayList<ModsUrlType>();
		ModsLocationType mloc[] = mods.getLocationArray();
		if (mloc.length == 0) {
			logger
					.warn(this.getBaseURI().toASCIIString()
							+ " PRIMARY_DMDSEC: Missing mods/location was set to the base URL for this mets document.");
			ModsLocationType ml = mods.addNewLocation();
			ModsUrlType mu = ml.addNewUrl();
			mu.setStringValue(this.getBaseURI().toASCIIString());
			mu.setUsage(ModsUrlType.Usage.PRIMARY_DISPLAY);
			mloc = mods.getLocationArray();
		}
		for (int i = 0; i < mloc.length; i++) {
			ModsUrlType murl[] = mloc[i].getUrlArray();
			if (murl.length == 0) {
				logger
						.warn(this.getBaseURI().toASCIIString()
								+ " PRIMARY_DMDSEC: Missing mods/location/url was set to the base URL for this mets document.");
				ModsUrlType mu = mloc[i].addNewUrl();
				mu.setStringValue(this.getBaseURI().toASCIIString());
				murl = mloc[i].getUrlArray();
			}
			for (int j = 0; j < murl.length; j++) {
				if (murl[j].isSetUsage()
						&& murl[j].getUsage() == ModsUrlType.Usage.PRIMARY_DISPLAY) {
					primDisplayCnt++;
					primaryURL.add(murl[j]);
				}
			}
		}
		if (primaryURL.size() == 0) {
			logger
					.warn(this.getBaseURI().toASCIIString()
							+ " PRIMARY_DMDSEC: There was no location/url/@usage atribute set to 'primary display', so the first url was set to 'primary display'.");
			mods.getLocationArray(0).getUrlArray(0).setUsage(
					ModsUrlType.Usage.PRIMARY_DISPLAY);
		} else if (primaryURL.size() > 1) {
			logger
					.error(this.getBaseURI().toASCIIString()
							+ " PRIMARY_DMDSEC: There are multiple mods/location/url elements with a usage attribute of 'primary display'.  All but the first will have this attribute removed.");
			for (int j = primaryURL.size() - 1; j > 0; j--) {
				primaryURL.get(j).unsetUsage();
			}
		}

		ModsAccessConditionType macc[] = mods.getAccessConditionArray();
		boolean useAndRepoAttr = false;
		if (macc.length == 0) {
			logger
					.warn(this.getBaseURI().toASCIIString()
							+ " PRIMARY_DMDSEC: Missing mods/accessCondition was added.");
			ModsAccessConditionType mac = mods.addNewAccessCondition();
			mac.setType2("useAndReproduction");
			mac
					.setStringValue("The access conditions for this object are unknown.");
			macc = mods.getAccessConditionArray();
		}
		for (int i = 0; i < macc.length; i++) {
			if (macc[i].isSetType2()
					&& macc[i].getType2().compareTo("useAndReproduction") == 0) {
				useAndRepoAttr = true;
				break;
			}
		}
		if (!useAndRepoAttr) {
			logger
					.warn(this.getBaseURI().toASCIIString()
							+ " PRIMARY_DMDSEC: There was no mods/accessCondition/@type with a value of 'useAndReproduction' so the first one was set to this value.");
			mods.getAccessConditionArray(0).setType2("useAndReproduction");
		}

		ModsRecordInfoType minfo[] = mods.getRecordInfoArray();
		if (minfo.length == 0) {
			logger
					.warn(this.getBaseURI().toASCIIString()
							+ " PRIMARY_DMDSEC: Missing mods/recordInfo was added.  The languageOfCataloging was set to 'Undetermined'.");
			ModsRecordInfoType mi = mods.addNewRecordInfo();
			ModsLanguageType mlt = mi.addNewLanguageOfCataloging();
			ModsLanguageType.LanguageTerm lt = mlt.addNewLanguageTerm();
			lt.setType(ModsCodeOrText.CODE);
			lt.setStringValue("und");
			lt
					.setAuthority(ModsLanguageType.LanguageTerm.Authority.ISO_639_2_B);
			ModsLanguageType.LanguageTerm lt2 = mlt.addNewLanguageTerm();
			lt2.setType(ModsCodeOrText.TEXT);
			lt2.setStringValue("Undetermined");
			minfo = mods.getRecordInfoArray();
		} else {
			if (minfo.length > 1) {
				logger
						.warn(this.getBaseURI().toASCIIString()
								+ " PRIMARY_DMDSEC: There are too many mods/recordInfo elements.  All but the first have been deleted.");
				for (int j = minfo.length - 1; j > 0; j--) {
					mods.removeRecordInfo(j);
				}
			}
			ModsLanguageType minfolang[] = minfo[0]
					.getLanguageOfCatalogingArray();
			if (minfolang.length == 0) {
				logger
						.warn(this.getBaseURI().toASCIIString()
								+ " PRIMARY_DMDSEC: Missing mods/recordInfo/languageOfCataloging was set to 'Undetermined'.");
				ModsLanguageType mlt = minfo[0].addNewLanguageOfCataloging();
				ModsLanguageType.LanguageTerm lt = mlt.addNewLanguageTerm();
				lt.setStringValue("und");
				lt.setType(ModsCodeOrText.CODE);
				lt
						.setAuthority(ModsLanguageType.LanguageTerm.Authority.ISO_639_2_B);
				ModsLanguageType.LanguageTerm lt2 = mlt.addNewLanguageTerm();
				lt2.setType(ModsCodeOrText.TEXT);
				lt2.setStringValue("Undetermined");
			} else {
				ModsLanguageType.LanguageTerm minfolangterm[] = minfolang[0]
						.getLanguageTermArray();
				if (minfolangterm.length == 0) {
					logger
							.warn(this.getBaseURI().toASCIIString()
									+ " PRIMARY_DMDSEC: Missing mods/recordInfo/languageOfCataloging/languageTerm was set to 'Undetermined'.");
					ModsLanguageType.LanguageTerm lt = minfolang[0]
							.addNewLanguageTerm();
					lt.setStringValue("und");
					lt.setType(ModsCodeOrText.CODE);
					lt
							.setAuthority(ModsLanguageType.LanguageTerm.Authority.ISO_639_2_B);
					ModsLanguageType.LanguageTerm lt2 = minfolang[0]
							.addNewLanguageTerm();
					lt2.setType(ModsCodeOrText.TEXT);
					lt2.setStringValue("Undetermined");
				} else {
					if (!minfolangterm[0].isSetType()) {
						if (minfolangterm[0].getStringValue().length() <= 4) {
							logger
									.warn(this.getBaseURI().toASCIIString()
											+ " PRIMARY_DMDSEC: Missing mods/recordInfo/languageOfCataloging/languageTerm/@type was set to 'code'.");
							minfolangterm[0].setType(ModsCodeOrText.CODE);
						} else {
							logger
									.warn(this.getBaseURI().toASCIIString()
											+ " PRIMARY_DMDSEC: Missing mods/recordInfo/languageOfCataloging/languageTerm/@type was set to 'text'.");
							minfolangterm[0].setType(ModsCodeOrText.TEXT);
						}
					}
					if (!minfolangterm[0].isSetAuthority()) {
						if (minfolangterm[0].getStringValue().length() == 3
								&& minfolangterm[0].getType().equals(
										ModsCodeOrText.CODE)) {
							logger
									.warn(this.getBaseURI().toASCIIString()
											+ " PRIMARY_DMDSEC: Missing mods/recordInfo/languageOfCataloging/languageTerm/@authority was set to 'iso639-2b'.");
							minfolangterm[0]
									.setAuthority(ModsLanguageType.LanguageTerm.Authority.ISO_639_2_B);
						} else if (minfolangterm[0].getType().equals(
								ModsCodeOrText.CODE)) {
							logger
									.warn(this.getBaseURI().toASCIIString()
											+ " PRIMARY_DMDSEC: Missing mods/recordInfo/languageOfCataloging/languageTerm/@authority was set to 'rfc3066'.");
							minfolangterm[0]
									.setAuthority(ModsLanguageType.LanguageTerm.Authority.RFC_3066);
						}
					}
				}
			}
		}
		return modsDoc;
	}

	/**
	 * Compares files with those from another METS documents, checking for
	 * differences in file name, size, and mime type.
	 * 
	 * @param mets
	 *            an EchoDep METS file
	 * @throws HaSMETSProfileException
	 */
	public void compareFiles(HaSMETSProfile mets)
			throws HaSMETSProfileException {
		MetsFileType[] files = mets.getAllFiles();

		// log4j logger
		Logger logger = Logger.getLogger(HaSMETSProfile.class);
		HaSMETSAppender app = new HaSMETSAppender("compareFiles");

		ArrayList<MetsFileType> missingfiles = new ArrayList<MetsFileType>();
		for (int i = 0; i < files.length; i++) {
			logger.addAppender(app);
			app.clearEvents();
			app.setThreshold(Level.INFO);
			Boolean event = false;

			MetsFileType[] f = this.getFiles(files[i].getCHECKSUMTYPE(),
					files[i].getCHECKSUM());

			if (f.length <= 0) {
				missingfiles.add(files[i]);

			} else {
				if (f[0].getSIZE() != files[i].getSIZE()) {
					event = true;
					logger
							.warn("File size discrepancy: File from previous METS document \""
									+ files[i].getFLocatArray(0).getHref()
									+ "\" has size = "
									+ files[i].getSIZE()
									+ ". File from this METS document \""
									+ f[0].getFLocatArray(0).getHref()
									+ "\" has size = " + f[0].getSIZE() + ".");
				}
				if (!f[0].getMIMETYPE()
						.equalsIgnoreCase(files[i].getMIMETYPE())) {
					event = true;
					logger
							.warn("MIME type discrepancy: File from previous METS document \""
									+ files[i].getFLocatArray(0).getHref()
									+ "\" has MIME type = "
									+ files[i].getMIMETYPE()
									+ ". File from this METS document \""
									+ f[0].getFLocatArray(0).getHref()
									+ "\" has MIME type = "
									+ f[0].getMIMETYPE() + ".");
				}
				if (!f[0].getFLocatArray(0).getHref().equals(
						files[i].getFLocatArray(0).getHref())) {
					event = true;
					logger
							.warn("File name discrepancy: File from previous METS document is \""
									+ files[i].getFLocatArray(0).getHref()
									+ "\". File from this METS document is \""
									+ f[0].getFLocatArray(0).getHref() + "\".");
				}
			}

			logger.removeAppender(app);
			if (event) {
				PremisEventDocument premis = HaSMETSProfile
						.createBasicPremisEvent(
								HaSMETSProfile.PREMISEventType.VALIDATION,
								"Comparing files with previous METS document.");
				PremisEventDocument.Event.EventOutcomeInformation.EventOutcomeDetail det = premis
						.getEvent().addNewEventOutcomeInformation()
						.addNewEventOutcomeDetail();
				XmlObject events;
				try {
					events = XmlObject.Factory.parse(app
							.getAllEventsAsXmlText());
				} catch (XmlException e) {
					throw new RuntimeException("The log4j XML is not valid", e);
				}
				Node n = det.getDomNode().getOwnerDocument().importNode(
						events.getDomNode().getFirstChild(), true);
				det.getDomNode().appendChild(n);

				// attach the event
				PremisAgentDocument agents[] = { this.getDefaultHumanAgent(),
						this.getDefaultSoftwareAgent() };
				HaSMETSProfile.PREMISLinkingAgentRole roles[] = {
						HaSMETSProfile.PREMISLinkingAgentRole.EVENT_INITIATOR,
						HaSMETSProfile.PREMISLinkingAgentRole.SOFTWARE_USED };

				addEventToXmlObject(f[0], premis, agents, roles);
			}
		}

		files = this.getAllFiles();
		for (int i = 0; i < files.length; i++) {

			MetsFileType[] f = mets.getFiles(files[i].getCHECKSUMTYPE(),
					files[i].getCHECKSUM());
			if (f.length <= 0) {

				Boolean found = false;
				for (int j = 0; j < missingfiles.size(); j++) {
					if (files[i].getFLocatArray(0).getHref().equals(
							missingfiles.get(j).getFLocatArray(0).getHref())) {
						logger.addAppender(app);
						app.clearEvents();
						app.setThreshold(Level.INFO);

						found = true;

						MetsFileType mf = this.metsDoc.getMets().getFileSec()
								.getFileGrpArray(0).addNewFile();
						XmlID id = this.getNewID("FILE", mf);
						mf.setCREATED(missingfiles.get(i).getCREATED());
						mf.setCHECKSUM(missingfiles.get(i).getCHECKSUM());
						mf.setCHECKSUMTYPE(missingfiles.get(i)
								.getCHECKSUMTYPE());
						mf.setMIMETYPE(missingfiles.get(i).getMIMETYPE());
						mf.setSIZE(missingfiles.get(i).getSIZE());
						mf.setOWNERID(missingfiles.get(i).getOWNERID());
						missingfiles.remove(j);

						logger
								.warn("Possible file corruption: \""
										+ missingfiles.get(j).getFLocatArray(0)
												.getHref()
										+ "\" from the previous METS document and \""
										+ files[i].getFLocatArray(0).getHref()
										+ "\" from this one have the same file name but"
										+ "different CHECKSUM values.");

						logger.removeAppender(app);
						PremisEventDocument premis = HaSMETSProfile
								.createBasicPremisEvent(
										HaSMETSProfile.PREMISEventType.VALIDATION,
										"Comparing files with previous METS document.");
						PremisEventDocument.Event.EventOutcomeInformation.EventOutcomeDetail det = premis
								.getEvent().addNewEventOutcomeInformation()
								.addNewEventOutcomeDetail();
						XmlObject events;
						try {
							events = XmlObject.Factory.parse(app
									.getAllEventsAsXmlText());
						} catch (XmlException e) {
							throw new RuntimeException(
									"The log4j XML is not valid", e);
						}
						Node n = det.getDomNode().getOwnerDocument()
								.importNode(
										events.getDomNode().getFirstChild(),
										true);
						det.getDomNode().appendChild(n);

						// attach the event
						PremisAgentDocument agents[] = {
								this.getDefaultHumanAgent(),
								this.getDefaultSoftwareAgent() };
						HaSMETSProfile.PREMISLinkingAgentRole roles[] = {
								HaSMETSProfile.PREMISLinkingAgentRole.EVENT_INITIATOR,
								HaSMETSProfile.PREMISLinkingAgentRole.SOFTWARE_USED };

						addEventToXmlObject(files[i], premis, agents, roles);
						addEventToXmlObject(mf, premis, agents, roles);
						break;
					}
				}
				if (!found) {
					logger.addAppender(app);
					app.clearEvents();
					app.setThreshold(Level.INFO);

					logger
							.warn("New file: \""
									+ files[i].getFLocatArray(0).getHref()
									+ "\" cannot be found in the previous METS document but is present here");

					logger.removeAppender(app);
					PremisEventDocument premis = HaSMETSProfile
							.createBasicPremisEvent(
									HaSMETSProfile.PREMISEventType.ADDITION,
									"Comparing files with previous METS document.");
					PremisEventDocument.Event.EventOutcomeInformation.EventOutcomeDetail det = premis
							.getEvent().addNewEventOutcomeInformation()
							.addNewEventOutcomeDetail();
					XmlObject events;
					try {
						events = XmlObject.Factory.parse(app
								.getAllEventsAsXmlText());
					} catch (XmlException e) {
						throw new RuntimeException(
								"The log4j XML is not valid", e);
					}
					Node n = det.getDomNode().getOwnerDocument().importNode(
							events.getDomNode().getFirstChild(), true);
					det.getDomNode().appendChild(n);

					// attach the event
					PremisAgentDocument agents[] = {
							this.getDefaultHumanAgent(),
							this.getDefaultSoftwareAgent() };
					HaSMETSProfile.PREMISLinkingAgentRole roles[] = {
							HaSMETSProfile.PREMISLinkingAgentRole.EVENT_INITIATOR,
							HaSMETSProfile.PREMISLinkingAgentRole.SOFTWARE_USED };

					addEventToXmlObject(files[i], premis, agents, roles);
				}
			}
		}

		for (int i = 0; i < missingfiles.size(); i++) {
			logger.addAppender(app);
			app.clearEvents();
			app.setThreshold(Level.INFO);
			logger
					.warn("Missing file: \""
							+ missingfiles.get(i).getFLocatArray(0).getHref()
							+ "\" from the previous METS document is missing from this one");

			MetsFileType mf = this.metsDoc.getMets().getFileSec()
					.getFileGrpArray(0).addNewFile();
			XmlID id = this.getNewID("FILE", mf);
			mf.setCREATED(missingfiles.get(i).getCREATED());
			mf.setCHECKSUM(missingfiles.get(i).getCHECKSUM());
			mf.setCHECKSUMTYPE(missingfiles.get(i).getCHECKSUMTYPE());
			mf.setMIMETYPE(missingfiles.get(i).getMIMETYPE());
			mf.setSIZE(missingfiles.get(i).getSIZE());
			mf.setOWNERID(missingfiles.get(i).getOWNERID());

			logger.removeAppender(app);
			PremisEventDocument premis = HaSMETSProfile.createBasicPremisEvent(
					HaSMETSProfile.PREMISEventType.DELETION,
					"Comparing files with previous METS document.");
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
			PremisAgentDocument agents[] = { this.getDefaultHumanAgent(),
					this.getDefaultSoftwareAgent() };
			HaSMETSProfile.PREMISLinkingAgentRole roles[] = {
					HaSMETSProfile.PREMISLinkingAgentRole.EVENT_INITIATOR,
					HaSMETSProfile.PREMISLinkingAgentRole.SOFTWARE_USED };

			addEventToXmlObject(mf, premis, agents, roles);
		}
	}

	public void convertPrimaryDmdSecToAquifer() throws HaSMETSProfileException {
		MetsMdSecType priDMD = null;
		ModsModsDocument modsDoc = null;
		priDMD = this.getPrimaryDmdSec();
		modsDoc = (ModsModsDocument) this.getMDSecXmlObject(priDMD);

		// set up a logger to collect events from the augmenter
		HaSMETSAppender app = new HaSMETSAppender();
		logger.addAppender(app);

		modsDoc = this.augmentAquiferModsDocument(modsDoc);

		if (app.hasEvents()) {
			// create an event describing the conversion and containing events
			// from the augmenter
			PremisEventDocument pEvDoc = HaSMETSProfile
					.createBasicPremisEvent(
							HaSMETSProfile.PREMISEventType.METADATA_MODIFICATION,
							"The previous PRIMARY_DMDSEC was augmented so that it conforms to the Aquifer MODS profile.");
			pEvDoc
					.getEvent()
					.addNewEventOutcomeInformation()
					.setEventOutcome(
							"This was an automated conversion.  Some boilerplate or incorrect values may have been assigned.  The results should be reviewed by a human operator.  Refer to the eventOutcomeDetail.");
			PremisEventDocument.Event.EventOutcomeInformation.EventOutcomeDetail det = pEvDoc
					.getEvent().getEventOutcomeInformationArray(0)
					.addNewEventOutcomeDetail();
			XmlObject events;
			try {
				events = XmlObject.Factory.parse(app.getAllEventsAsXmlText(),
						HaSMETSProfile.defaultLoadOptions);
			} catch (XmlException e) {
				throw new RuntimeException("The log4j XML is not valid", e);
			}
			Node n = det.getDomNode().getOwnerDocument().importNode(
					events.getDomNode().getFirstChild(), true);
			det.getDomNode().appendChild(n);

			// add the modified Mods as an alternate DMDSEC
			MetsMdSecType newDMD = this.addAlternateDmdSec(
					MetsMdSecType.MdWrap.MDTYPE.MODS, "", modsDoc.getDomNode());

			// set the original primary dmd to alternate and set the new
			// alternate to primary
			priDMD.setSTATUS(HaSMETSProfile.DMDSecStatus.ALTERNATE_DMDSEC
					.toString());
			newDMD.setSTATUS(HaSMETSProfile.DMDSecStatus.PRIMARY_DMDSEC
					.toString());

			// attach the event to the new DMD
			PremisAgentDocument agents[] = { this.getDefaultHumanAgent(),
					this.getDefaultSoftwareAgent() };
			HaSMETSProfile.PREMISLinkingAgentRole roles[] = {
					HaSMETSProfile.PREMISLinkingAgentRole.EVENT_INITIATOR,
					HaSMETSProfile.PREMISLinkingAgentRole.SOFTWARE_USED };
			this.addEventToXmlObject(newDMD, pEvDoc, agents, roles);

			this.setLastModifiedToCurrent();
		}

		// stop collection log events
		app.clearEvents();
		logger.removeAppender(app);
	}

	/**
	 * If the given xobj has an ADMID attribute return all the MetsMdSecType
	 * elements that are referenced by that ADMID
	 * 
	 * @param xobj
	 *            XmlObject
	 * @return an array of MetsMdSecType elements, may be empty
	 */
	public MetsMdSecType[] getAllAmdSecMDs(XmlObject xobj) {
		// check if the xobj has an ADMID attribute
		SchemaType st = xobj.schemaType();
		SchemaProperty sp = st.getAttributeProperty(new QName("ADMID"));
		if (sp == null) {
			return new MetsMdSecType[0];
		} else {
			Element n = (Element) xobj.getDomNode();
			String admID = n.getAttribute("ADMID");
			if (admID == null) {
				return new MetsMdSecType[0];
			} else {
				String i[] = admID.split("\\s+");

				String orID = "";
				for (int j = 0; j < i.length; j++) {
					if (orID.length() > 0) {
						orID = orID + " or @ID='" + i[j] + "'";
					} else {
						orID = "@ID='" + i[j] + "'";
					}
				}

				String xql = "declare namespace m='" + METS_NS
						+ "'; .//m:amdSec/m:*[" + orID + "]";

				XmlObject ft[] = metsDoc.selectPath(xql);
				if (ft == null || ft.length == 0)
					return new MetsMdSecType[0];
				else
					return (MetsMdSecType[]) ft;

			}
		}
	}

	private String[] getAllMIMETypes() {
		MetsFileType ft[] = this.getAllFiles();
		ArrayList<String> types = new ArrayList<String>();

		for (int i = 0; i < ft.length; i++) {
			if (ft[i].isSetMIMETYPE()
					&& !types.contains(ft[i].getMIMETYPE().toLowerCase())) {
				types.add(ft[i].getMIMETYPE().toLowerCase());
			}
		}
		return types.toArray(new String[0]);
	}

	/**
	 * Returns the alternate dmdSec elements which has a
	 * STATUS='ALTERNATE_DMDSEC' attribute
	 * 
	 * @return MetsMdSecType[] object representing all the alternate dmdSec
	 *         elements
	 */
	public MetsMdSecType[] getAlternateDmdSecArray() {
		String xql = "declare namespace m='" + METS_NS
				+ "'; .//m:dmdSec[@STATUS='ALTERNATE_DMDSEC']";

		XmlObject o[] = metsDoc.selectPath(xql);

		if (o == null || o.length == 0)
			return new MetsMdSecType[0];
		else
			return (MetsMdSecType[]) o;
	}

	/**
	 * Returns a specific alternate dmdSec element which has a
	 * STATUS='ALTERNATE_DMDSEC' attribute
	 * 
	 * @param i
	 *            the ordinal of the alternate dmdSec element to return
	 * @return MetsMdSecType object representing the ith alternate dmdSec
	 *         element, or null
	 */
	public MetsMdSecType getAlternateDmdSecArray(int i) {
		MetsMdSecType mdSec[] = getAlternateDmdSecArray();
		if (i < mdSec.length) {
			return mdSec[i];
		} else {
			return null;
		}
	}

	/**
	 * Return the default PREMIS agent which represent the current human
	 * operator
	 * 
	 * @return the default human PremisAgentDocument
	 */
	public PremisAgentDocument getDefaultHumanAgent() {
		return humanAgentDoc;
	}

	/**
	 * Return the default PREMIS agent which represent this software
	 * 
	 * @return the default software PremisAgentDocument
	 */
	public PremisAgentDocument getDefaultSoftwareAgent() {
		return softAgentDoc;
	}

	/**
	 * Return an InputStream for the MetsFileType object, can accomodate either
	 * the FContent or FLocat sub-elements
	 * 
	 * @param file
	 *            the MetsFileType object for the file of interest
	 * @return the InputStream
	 * @throws HaSMETSProfileException
	 *             if the stream is not accessible
	 */
	public InputStream getFileInputStream(MetsFileType file)
			throws HaSMETSProfileException {
		InputStream strm = null;

		if (file.isSetFContent()) {
			if (file.getFContent().isSetBinData()) {
				strm = new ByteArrayInputStream(file.getFContent().getBinData());
			} else if (file.getFContent().isSetXmlData()) {
				// TODO: Need to get the exact text stream out of the xmlData
				// element. This is required if
				// we want the size and checksum attributes to match correctly,
				// or we
				// could require some sort of normalized form as in digital
				// signatures
				// or maybe just do not require checksums at all for embedded
				// xmlData
				strm = file.getFContent().getXmlData().newInputStream(
						new XmlOptions().setSaveInner().setSaveNoXmlDecl()
								.setSaveAggressiveNamespaces());
			} else {
				throw new HaSMETSProfileException(
						"The FContent element does not contain an xmlData or binData sub-element");
			}
		} else if (file.getFLocatArray().length == 1) {
			URI uri = resolveURIAgainstBaseURI(file.getFLocatArray(0).getHref());
			URL url = null;
			try {
				url = uri.toURL();
			} catch (MalformedURLException e) {
				throw new HaSMETSProfileException("The href '"
						+ file.getFLocatArray(0).getHref()
						+ "' is not a valid URL.", e);
			}
			// may need to do some URL encoding
			// TODO: Say something in the profile about this
			// if (url.toExternalForm().indexOf("%") > -1) {
			// String urlStr = url.toExternalForm();
			// urlStr = urlStr.replaceAll("%", "%25");
			// try {
			// url = new URL(urlStr);
			// } catch (MalformedURLException e) {
			// throw new HaSMETSProfileException("The href '"
			// + file.getFLocatArray(0).getHref()
			// + "' is not a valid URL.", e);
			// }
			// }
			try {
				strm = url.openStream();
			} catch (IOException e) {
				throw new HaSMETSProfileException(
						"Unable to open the file located at '"
								+ url.toExternalForm() + "'", e);
			}
		} else {
			throw new HaSMETSProfileException(
					"The file element does not contain exactly one FContent or exactly one FLocat sub-element");
		}

		return strm;
	}

	/**
	 * 
	 * Given the CHECKSUM of a file return the XMLBean FileType object(s) that
	 * reference that file
	 * 
	 * @param algorithm
	 *            CHECKSUM type
	 * @param digest
	 *            CHECKSUM digest
	 * @return the MetsFileType[] for that file or null
	 */
	public MetsFileType[] getFiles(MetsFileType.CHECKSUMTYPE.Enum algorithm,
			String digest) {
		ArrayList<MetsFileType> ret = new ArrayList<MetsFileType>();

		String xql = "declare namespace m='" + METS_NS
				+ "'; declare namespace xl='" + XLINK_NS
				+ "'; .//m:file[@CHECKSUMTYPE='" + algorithm
				+ "' and @CHECKSUM='" + digest + "']";

		XmlObject o[] = metsDoc.selectPath(xql);

		for (int i = 0; i < o.length; i++) {
			ret.add((MetsFileType) o[i]);
		}

		return ret.toArray(new MetsFileType[0]);
	}

	/**
	 * Return an array of XMLBean FileType objects with a given ID(s)
	 * 
	 * @param ids
	 *            one or more ID values separated by space
	 * @return an array of XMLBean FileType objects
	 */
	public MetsFileType[] getFiles(String ids) {
		XmlObject ft[] = null;

		String i[] = ids.split("\\s+");

		String orID = "";
		for (int j = 0; j < i.length; j++) {
			if (orID.length() > 0) {
				orID = orID + " or @ID='" + i[j] + "'";
			} else {
				orID = "@ID='" + i[j] + "'";
			}
		}

		String xql = "declare namespace m='" + METS_NS + "'; .//m:file[" + orID
				+ "]";

		ft = metsDoc.selectPath(xql);

		if (ft == null || ft.length == 0)
			return new MetsFileType[0];
		else
			return (MetsFileType[]) ft;
	}

	/**
	 * Given the URL of a file return the MetsFileType.FLocat(s) that reference
	 * that file in the href
	 * 
	 * @param url
	 *            String for the URL, either relative or absolute
	 * @return the MetsFileType.FLocat[] for that file or null
	 */
	public MetsFileType.FLocat[] getFLocat(String url) {
		ArrayList<MetsFileType.FLocat> ret = new ArrayList<MetsFileType.FLocat>();

		URI uri = this.resolveURIAgainstBaseURI(url);

		String xql = "declare namespace m='" + METS_NS
				+ "'; declare namespace xl='" + XLINK_NS
				+ "'; .//m:FLocat[@LOCTYPE='URL' and @xl:href]";

		XmlObject o[] = metsDoc.selectPath(xql);

		MetsFileType.FLocat flocat = null;
		for (int i = 0; i < o.length; i++) {
			MetsFileType.FLocat fl = (MetsFileType.FLocat) o[i];
			URI href = this.resolveURIAgainstBaseURI(fl.getHref());
			if (uri.equals(href)) {
				flocat = fl;
				ret.add(flocat);
			}
		}

		return ret.toArray(new MetsFileType.FLocat[0]);

	}

	/**
	 * Given a PremisAgentDocument return the MetsMdSecType object that contains
	 * that agent as determined by matching agentIdentifierValue and
	 * agentIdentifierType. This assumes that these are unique within a single
	 * METS document.
	 * 
	 * @param agentIdentifierValue
	 *            String containing a Premis agent identifier
	 * 
	 * @return the MetsMdSecType element that contains the agent or null, if the
	 *         agent cannot be found.
	 * 
	 * @throws RuntimeException
	 *             if more than one matching MetsMdSecType is found
	 */
	private MetsMdSecType getMdSecWith(PremisAgentDocument agent) {

		PremisAgentDocument.Agent.AgentIdentifier ids[] = agent.getAgent()
				.getAgentIdentifierArray();

		ArrayList<XmlObject> mds = new ArrayList<XmlObject>();

		for (int i = 0; i < ids.length; i++) {
			String val = ids[i].getAgentIdentifierValue();
			String typ = ids[i].getAgentIdentifierType();

			String xql = "declare namespace m='" + METS_NS
					+ "';declare namespace p='" + PREMIS_NS + "'";
			xql = xql
					+ ".//m:*[normalize-space(m:mdWrap/m:xmlData/p:agent/p:agentIdentifier/p:agentIdentifierValue)='"
					+ val + "'";
			xql = xql
					+ " and normalize-space(m:mdWrap/m:xmlData/p:agent/p:agentIdentifier/p:agentIdentifierType)='"
					+ typ + "']";

			XmlObject xobj[] = metsDoc.selectPath(xql);

			for (int j = 0; j < xobj.length; j++) {
				mds.add(xobj[j]);
			}
		}

		if (mds.size() > 1) {
			throw new RuntimeException(
					"Multiple matching MetsMdSecType elements where found which contain this agent");
		} else if (mds.size() == 0) {
			return null;
		} else {
			return (MetsMdSecType) mds.get(0);
		}
	}

	/**
	 * Return an HaSMETSProfileException if the xmlData contained in the
	 * MdSecType is not valid or is not of the correct type as indicated by the
	 * namespace and root element name
	 * 
	 * @param md
	 *            the MdSecType of the metadata section to check
	 * @param xobj
	 *            the XmlObject to be added as a child of the md
	 * @param namespaceURI
	 *            the expected namespace URI of the first child element of the
	 *            xmlData
	 * @param nodeName
	 *            the expected root element name of the first child element of
	 *            the xmlData
	 * @return a HaSMETSProfileException or null if there are no problems with
	 *         the xmlData
	 */
	private HaSMETSProfileException getMDSecXmlDataException(MetsMdSecType md,
			XmlObject xobj, QName qn) {

		// makes sure the md can contain the xobj
		if (!this.isAmdSecComboOK(md, xobj)) {
			return new HaSMETSProfileException("This XmlObject '"
					+ xobj.getDomNode().getLocalName()
					+ "' is not recognized in this MetsMdSecType '"
					+ md.getDomNode().getLocalName() + "'");
		}

		// make sure there is not both a mdWrap and a mdRef which indicates
		// something strange for this profile
		if (md.isSetMdWrap() && md.isSetMdRef()) {
			return new HaSMETSProfileException(
					"This MdSecType contains both an mdRef and a mdWrap which is not supported by this profile.");
		}

		// if the md contains a mdWrap/xmlData then check that it is the right
		// kind of xmlData
		if (md.isSetMdWrap() && md.getMdWrap().isSetXmlData()) {
			Node nx = md.getMdWrap().getXmlData().getDomNode();
			// if the xmlData contains multiple children, it does not conform to
			// our
			// profile
			if (nx.getChildNodes().getLength() > 1) {
				return new HaSMETSProfileException(
						"The xmlData element contains multiple children.");
			}
			// check whether the xml has the expected namespace and root element
			// name
			else if (nx.hasChildNodes()
					&& nx.getFirstChild().getLocalName().compareTo(
							qn.getLocalPart()) != 0
					&& (nx.getFirstChild().getNamespaceURI().compareTo(
							qn.getNamespaceURI()) != 0 || nx.getFirstChild()
							.getNamespaceURI() == qn.getNamespaceURI())) {
				return new HaSMETSProfileException(
						"The xmlData element contains an unexpected child element '"
								+ nx.getNodeName() + "'");
			} else {
				return null;
			}

			// if the mdSec contains an mdRef check that it is the right kind of
			// referenced data
			// we don't actually look at the referenced file, but only the
			// MDTYPE attributes
		} else if (md.isSetMdRef()) {
			if (!ObjectTypes.isRecognizedCombination("techMD", qn, md
					.getMdRef().xgetMDTYPE().getStringValue(), md.getMdRef()
					.getOTHERMDTYPE())) {
				return new HaSMETSProfileException(
						"This techMD references a different kind of metadata '"
								+ md.getMdRef().xgetMDTYPE().getStringValue()
								+ "'");
			} else {
				return null;
			}
		}
		return null;
	}

	/**
	 * Get the next unassigned ID value that can be used in the METS file and
	 * assign it to an XmlObject
	 * 
	 * @param prefix
	 *            string that will be prepended to all ID values, if this is
	 *            null or empty string then the value "ID" will be used
	 * 
	 * @param xobj
	 *            the XmlObject to which the ID will be assigned
	 * @return an XmlID object representing the new ID attribute
	 */
	synchronized public XmlID getNewID(String prefix, XmlObject xobj) {
		if (prefix == null || prefix.length() == 0)
			prefix = "ID";

		// get the next unused ID
		String id;
		do {
			idCounter++;
			id = prefix + String.valueOf(idCounter);
		} while (allIDs.containsKey(id));

		// add a new ID attribuite to the xobj with the new value
		Node n = xobj.getDomNode();
		n.getAttributes().setNamedItem(
				n.getOwnerDocument().createAttribute("ID"));
		n.getAttributes().getNamedItem("ID").setNodeValue(id);

		// add the entry to the allIDS Hashtable
		allIDs.put(id, xobj);

		// return the XmlID object
		return (XmlID) xobj.selectAttribute(new QName("ID"));
	}

	/**
	 * Return the OAIS package type represented by the current METS document
	 * 
	 * @return HaSMETSProfile.PackageType: SIP, DIP, AIP
	 */
	public HaSMETSProfile.PackageType getPackageType() {
		return packageType;
	}

	/**
	 * Return the structMap which contains a given div
	 * 
	 * @param div
	 *            the XmlObject for the div
	 * @return MetsStructMapType which is the parent of the given div
	 */
	protected MetsStructMapType getParentStructMap(XmlObject div) {
		XmlCursor curs = div.newCursor();
		while (curs.getDomNode().getLocalName().compareTo("structMap") != 0) {
			curs.toParent();
		}
		return (MetsStructMapType) curs.getObject();
	}

	/**
	 * Returns the primary dmdSec element which has a STATUS='PRIMARY_DMDSEC'
	 * attribute
	 * 
	 * @return MetsMdSecType object represetning the primary dmdSec element
	 * @throws HaSMETSProfileException
	 *             if there is not exactly one primary dmdSec element
	 */
	public MetsMdSecType getPrimaryDmdSec() throws HaSMETSProfileException {
		String xql = "declare namespace m='" + METS_NS
				+ "'; .//m:dmdSec[@STATUS='PRIMARY_DMDSEC']";

		XmlObject o[] = metsDoc.selectPath(xql);

		if (o.length != 1) {
			throw new HaSMETSProfileException(
					"There must be exactly one Primary dmdSec; this document contains "
							+ o.length);
		}
		return (MetsMdSecType) o[0];
	}

	/**
	 * Returns the primary representation techMD element which has a
	 * STATUS='PRIMARY_REPRESENTATION' attribute
	 * 
	 * @return MetsMdSecType object representing the primary representation
	 *         techMD element
	 * @throws HaSMETSProfileException
	 *             if there is not exactly one primary representation techMD
	 *             element
	 */
	public MetsMdSecType getPrimaryRepresentation()
			throws HaSMETSProfileException {
		String xql = "declare namespace m='" + METS_NS
				+ "'; .//m:techMD[@STATUS='PRIMARY_REPRESENTATION']";

		XmlObject o[] = metsDoc.selectPath(xql);

		if (o.length > 1) {
			throw new HaSMETSProfileException(
					"There is more than one Primary Representation techMD");
		}
		if (o.length == 0
				&& this.getPackageType() != HaSMETSProfile.PackageType.SIP) {
			throw new HaSMETSProfileException(
					"There is no Primary Representation techMD");
		} else if (o.length == 0
				&& this.getPackageType() == HaSMETSProfile.PackageType.SIP) {
			return null;
		}

		return (MetsMdSecType) o[0];
	}

	/**
	 * Returns the primary structMap element which has a
	 * TYPE='PRIMARY_STRUCTMAP' attribute
	 * 
	 * @return MetsMdSecType object representing the primary representation
	 *         techMD element
	 * @throws HaSMETSProfileException
	 *             if there is not exactly one primary structMap techMD element
	 */
	public MetsStructMapType getPrimaryStructMap()
			throws HaSMETSProfileException {
		String xql = "declare namespace m='" + METS_NS
				+ "'; .//m:structMap[@TYPE='PRIMARY_STRUCTMAP']";

		XmlObject o[] = metsDoc.selectPath(xql);

		if (o.length != 1) {
			throw new HaSMETSProfileException(
					"There must be exactly one Primary structMap; this document contains "
							+ o.length);
		}

		return (MetsStructMapType) o[0];
	}

	/**
	 * For the given DOM Node return a QName
	 * 
	 * @param n
	 *            Node
	 * @return QName
	 */
	protected QName getQName(Node n) {
		return new QName(n.getNamespaceURI(), n.getLocalName());
	}

	/**
	 * Given an XmlObject which could be either Document Node or an xmlData node
	 * return the firstChild which is an element
	 * 
	 * @param xobj
	 *            the XmlObject of interest
	 * @return a Node which is the first element child of the XmlObject
	 */
	protected Node getRootElementFromXmlData(XmlObject xobj) {
		Node xmlData = xobj.getDomNode();

		if (xmlData.getNodeType() != Node.DOCUMENT_NODE
				&& xmlData.getLocalName().compareTo("xmlData") != 0) {
			throw new IllegalArgumentException(
					"xobj must be an xmlData element or a Document node");
		}

		Node root = xmlData.getFirstChild();

		while (root.getNodeType() != Node.ELEMENT_NODE) {
			root = root.getNextSibling();
		}

		return root;
	}

	/**
	 * Return the specific administrative metadata MetsMdSec which is referenced
	 * from the XmlObject and is of the appropriate type
	 * 
	 * @param xobj
	 *            the XmlObject to test
	 * @param amdSec
	 *            the type of AMD_SECTION being looked for
	 * @param qn
	 *            the QName of the root element in the xmlData being looked for
	 * @return MetsMDSecType[], may be empty of nothing matches
	 */
	public MetsMdSecType[] getSpecificAmdSecXmlData(XmlObject xobj,
			HaSMETSProfile.AMD_SECTION amdSec, QName qn) {
		MetsMdSecType mds[] = this.getAllAmdSecMDs(xobj);
		ArrayList<MetsMdSecType> lst = new ArrayList<MetsMdSecType>();

		for (int i = 0; i < mds.length; i++) {
			if (mds[i].getDomNode().getLocalName().compareTo(amdSec.toString()) == 0) {
				if (mds[i].isSetMdWrap() && mds[i].getMdWrap().isSetXmlData()) {
					Node n = this
							.getXmlDataAny(mds[i].getMdWrap().getXmlData());
					QName q2 = this.getQName(n);
					if (q2.equals(qn)) {
						lst.add(mds[i]);
					}
				}
			}
		}
		return lst.toArray(new MetsMdSecType[0]);
	}

	/**
	 * Return all the techMD sections associated with the given File
	 * 
	 * @param ft
	 *            the XMLBean FileType object for the file object
	 * @return an array of XMLBean MdSecType objects (these will all be techMD
	 *         elements)
	 */
	public MetsMdSecType[] getTechMDsForFile(MetsFileType ft) {
		MetsMdSecType mdSecs[];
		String ids = ft.xgetADMID().getStringValue();

		String i[] = ids.split("\\s+");

		String orID = "";
		for (int j = 0; j < i.length; j++) {
			if (orID.length() > 0) {
				orID = orID + " or @ID='" + i[j] + "'";
			} else {
				orID = "@ID='" + i[j] + "'";
			}
		}

		String xql = "declare namespace m='" + METS_NS + "'; .//m:techMD["
				+ orID + "]";

		mdSecs = (MetsMdSecType[]) metsDoc.selectPath(xql);

		return mdSecs;
	}

	// NESTED CLASSES

	/**
	 * Return an XmlObject for a given ID attribute value; you should cast this
	 * to an XMLBean object of the appropriate type
	 * 
	 * @param id
	 *            the id string
	 * @return an XmlObjector null if none is found for the given id
	 */
	public XmlObject getXmlObject(String id) {
		return allIDs.get(id);
	}

	/**
	 * Determine if the package is an AIP, SIP, or DIP by trying to validate it
	 * as if it was each type. Currently, an AIP and a DIP are equivalent and
	 * both are also a valid SIP, so if the package passes the AIP validation,
	 * AIP is returned. If the package passes the SIP validation, but not the
	 * AIP (or DIP) validation SIP is returned. If none of the validations are
	 * passes, UNKNOWN is returned. This method will not aattempt to open each
	 * file as part of the validation, so it might report a valid AIP or SIP,
	 * but there still might be problems with the sizes or checksums of the
	 * actual files
	 * 
	 * @param app
	 *            an HaSMETSAppender to which error messages will be added, may
	 *            be null
	 * 
	 * @return HaSMETSProfile.PackageType which may be UNKNOWN
	 */
	public HaSMETSProfile.PackageType guessPackageType(HaSMETSAppender app) {
		// TODO: Try to modify the
		// the validate functions so they can return immediately upon an error
		// so this function can work faster

		boolean valid = this.validateProfile(HaSMETSProfile.PackageType.AIP,
				app);
		if (valid) {
			return HaSMETSProfile.PackageType.AIP;
		} else {
			valid = this.validateProfile(HaSMETSProfile.PackageType.DIP, app);
			if (valid) {
				return HaSMETSProfile.PackageType.DIP;
			} else {
				valid = this.validateProfile(HaSMETSProfile.PackageType.SIP,
						app);
				if (valid) {
					return HaSMETSProfile.PackageType.SIP;
				} else {
					return HaSMETSProfile.PackageType.UNKNOWN;
				}
			}
		}

	}
	
	/**
	 * Simple guess package type
	 * @return
	 */
	public HaSMETSProfile.PackageType guessPackageType() {
		if(getMetsDocument().getMets().getOBJID() == null) {
			return PackageType.SIP;
		} else if (getMetsDocument().selectPath(
					"declare namespace m='" + METSProfile.METS_NS
							+ "'; //m:techMD[@STATUS='PRIMARY_REPRESENTATION']").length == 1) {
			return PackageType.AIP;
		} else {
			return PackageType.SIP;
		}
	}

	private ModsResourceType.Enum guessTypeOfResource() {
		// TODO: Look at all the file MIMETYPE attributes to determine the best
		// answer to this
		// Possibly do a frequency analysis of the top-level types and guess
		// based on the most
		// frequently used values
		MetsFileType ft[] = this.getAllFiles();
		Hashtable<String, Integer> mimeTypes = new Hashtable<String, Integer>();
		Hashtable<String, ModsResourceType.Enum> mimeToResource = new Hashtable<String, ModsResourceType.Enum>();
		mimeToResource.put("application", ModsResourceType.SOFTWARE_MULTIMEDIA);
		mimeToResource.put("audio", ModsResourceType.SOUND_RECORDING);
		mimeToResource.put("example", ModsResourceType.MIXED_MATERIAL);
		mimeToResource.put("image", ModsResourceType.STILL_IMAGE);
		mimeToResource.put("message", ModsResourceType.MIXED_MATERIAL);
		mimeToResource.put("model", ModsResourceType.SOFTWARE_MULTIMEDIA);
		mimeToResource.put("multipart", ModsResourceType.MIXED_MATERIAL);
		mimeToResource.put("text", ModsResourceType.TEXT);
		mimeToResource.put("video", ModsResourceType.MOVING_IMAGE);
		int cnt = 0;
		for (int i = 0; i < ft.length; i++) {
			String mime = ft[i].getMIMETYPE();
			if (mime != null) {
				cnt++;
				mime = mime.substring(0, mime.indexOf("/")).toLowerCase();
				if (mimeTypes.containsKey(mime)) {
					mimeTypes.put(mime, mimeTypes.get(mime) + 1);
				} else {
					mimeTypes.put(mime, 1);
				}
			}
		}
		ModsResourceType.Enum ret = ModsResourceType.MIXED_MATERIAL;
		;

		for (Enumeration<String> en = mimeTypes.keys(); en.hasMoreElements();) {
			String mime = en.nextElement();
			if (mimeTypes.get(mime) / cnt * 100 >= MIME_FREQ_THRESHOLD) {
				ret = mimeToResource.get(mime);
				break;
			}
		}

		return ret;
	}

	/**
	 * Determine whether the given premis object contains the given checksum or
	 * not
	 * 
	 * @param pobj
	 * @param cType
	 * @param cValue
	 * @return
	 */
	private boolean hasChecksum(PremisObjectDocument pobj,
			MetsFileType.CHECKSUMTYPE.Enum cType, String cValue) {
		boolean ret = false;

		PremisObjectDocument.Object.ObjectCharacteristics ochar[] = pobj
				.getObject().getObjectCharacteristicsArray();
		for (int i = 0; i < ochar.length; i++) {
			PremisObjectDocument.Object.ObjectCharacteristics.Fixity ofix[] = ochar[i]
					.getFixityArray();
			for (int j = 0; j < ofix.length; j++) {
				if (ofix[j].getMessageDigestAlgorithm().compareTo(
						cType.toString()) == 0
						&& ofix[j].getMessageDigest().compareToIgnoreCase(
								cValue) == 0) {
					ret = true;
					break;
				}
			}
			if (ret)
				break;
		}

		return ret;
	}

	/**
	 * Test whether the given XmlObject references an administrative metadata
	 * section of the given type which wraps metadata of a given type
	 * 
	 * @param xobj
	 *            the XmlObject to test
	 * @param amdSec
	 *            the type of AMD_SECTION being looked for
	 * @param qn
	 *            the QName of the root element in the xmlData being looked for
	 * @return true or false
	 */
	public boolean hasSpecificAmdSecXmlData(XmlObject xobj,
			HaSMETSProfile.AMD_SECTION amdSec, QName qn) {
		MetsMdSecType mds[] = this.getAllAmdSecMDs(xobj);
		boolean ret = false;
		for (int i = 0; i < mds.length; i++) {
			if (mds[i].getDomNode().getLocalName().compareTo(amdSec.toString()) == 0) {
				if (mds[i].isSetMdWrap() && mds[i].getMdWrap().isSetXmlData()) {
					Node n = this
							.getXmlDataAny(mds[i].getMdWrap().getXmlData());
					QName q2 = this.getQName(n);
					if (q2.equals(qn)) {
						ret = true;
						break;
					}
				}
			}
		}
		return ret;
	}

	/**
	 * There are two default agents associated with each instance of this class:
	 * the software agent which is this software and the human agent which is
	 * the logged on user (might actually be some sort of default system
	 * account)
	 */
	void initDefaultAgents() {
		// softAgent represent this software program
		String rev = "$LastChangedRevision: 866 $";
		rev = rev.substring(22, rev.length() - 2);
		softAgentDoc = PremisAgentDocument.Factory.newInstance();
		PremisAgentDocument.Agent sagent = softAgentDoc.addNewAgent();
		PremisAgentDocument.Agent.AgentIdentifier sid = sagent
				.addNewAgentIdentifier();
		sid.setAgentIdentifierType(HaSMETSProfile.PREMISIdentifierType.LOCAL
				.toString());
		sid.setAgentIdentifierValue(this.toString().split("@")[0] + "/" + rev);
		sagent.setAgentType(HaSMETSProfile.PREMISAgentType.SOFTWARE.toString());
		sagent
				.addAgentName("UIUC Hub and Spoke METS Profile Java Implementation, Revision "
						+ rev);

		// coin an id for the user
		String domain = System.getenv("USERDOMAIN");
		String hostname = null;
		String humanID;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			hostname = addr.getHostName();
		} catch (UnknownHostException e) {
			hostname = null;
		}
		if (domain != null)
			humanID = domain + "\\" + System.getProperty("user.name");
		else if (hostname != null)
			humanID = hostname + "\\" + System.getProperty("user.name");
		else
			humanID = System.getProperty("user.name");

		// humanAgent is the currently logged in user
		humanAgentDoc = PremisAgentDocument.Factory.newInstance();
		PremisAgentDocument.Agent hagent = humanAgentDoc.addNewAgent();
		PremisAgentDocument.Agent.AgentIdentifier hid = hagent
				.addNewAgentIdentifier();
		hid.setAgentIdentifierType(HaSMETSProfile.PREMISIdentifierType.LOCAL
				.toString());
		hid.setAgentIdentifierValue(humanID);
		hagent.setAgentType(HaSMETSProfile.PREMISAgentType.PERSON.toString());

	}

	/**
	 * Determine whether the given XmlObject can be contained in the given
	 * MetsMdSecType according to the rules of this profile. Note the profile
	 * does not necissarily forbid the combination, but it does not recognize
	 * it.
	 * 
	 * @param mdsec
	 *            the MetsMdSecType
	 * @param xobj
	 *            the XmlObject
	 * @return true if the combination is OK, else false
	 */
	private boolean isAmdSecComboOK(MetsMdSecType mdsec, XmlObject xobj) {
		String nm1 = mdsec.getDomNode().getLocalName();
		String nm2 = getRootElementFromXmlData(xobj).getLocalName();
		String uri2 = getRootElementFromXmlData(xobj).getNamespaceURI();
		if (uri2 == null)
			uri2 = "";
		if (nm1.compareTo("techMD") == 0) {
			if ((uri2.compareTo(PREMIS_NS) == 0 && nm2.compareTo("object") == 0)
					|| (uri2.compareTo(AMD_NS) == 0 && nm2.compareTo("AUDIOMD") == 0)
					|| (uri2.compareTo(VMD_NS) == 0 && nm2.compareTo("VIDEOMD") == 0)
					|| (uri2.compareTo(JHOVE_NS) == 0 && nm2.compareTo("jhove") == 0)
					|| (uri2.compareTo(MIX_NS) == 0 && nm2.compareTo("mix") == 0)
					|| (uri2.compareTo(TEXTMD_NS) == 0 && nm2
							.compareTo("textMD") == 0)) {
				return true;
			}
		} else if (nm1.compareTo("sourceMD") == 0) {
			return false;
		} else if (nm1.compareTo("rightsMD") == 0) {
			if ((uri2.compareTo(PREMIS_NS) == 0 && nm2.compareTo("rights") == 0)
					|| (uri2.compareTo(PREMIS_NS) == 0 && nm2
							.compareTo("agent") == 0)) {
				return true;
			}
		} else if (nm1.compareTo("digiprovMD") == 0) {
			if ((uri2.compareTo(PREMIS_NS) == 0 && nm2.compareTo("event") == 0)
					|| (uri2.compareTo(PREMIS_NS) == 0 && nm2
							.compareTo("agent") == 0)) {
				return true;
			}

		} else {
			throw new RuntimeException("Unexpected mdSecType '" + nm1 + "'");
		}
		return false;
	}

	protected boolean isDateAfterDate(Calendar dt1, Calendar dt2) {
		if (!dt1.isSet(Calendar.HOUR_OF_DAY)) {
			dt1.set(Calendar.HOUR_OF_DAY, 0);
			dt1.set(Calendar.MINUTE, 0);
			dt1.set(Calendar.SECOND, 0);
			dt1.set(Calendar.MILLISECOND, 0);
		}
		if (!dt2.isSet(Calendar.HOUR_OF_DAY)) {
			dt2.set(Calendar.HOUR_OF_DAY, 23);
			dt2.set(Calendar.MINUTE, 59);
			dt2.set(Calendar.SECOND, 59);
			dt2.set(Calendar.MILLISECOND, 999);
		}

		dt1.add(Calendar.SECOND, -HaSMETSProfile.DATESTAMP_TOLERANCE_SECONDS);
		return dt1.after(dt2);
	}

	protected boolean isDateAfterLastModDate(Calendar dt) {
		Calendar lastMod = this.getMetsDocument().getMets().getMetsHdr()
				.getLASTMODDATE();
		lastMod
				.add(Calendar.SECOND,
						HaSMETSProfile.DATESTAMP_TOLERANCE_SECONDS);

		if (!dt.isSet(Calendar.HOUR_OF_DAY)) {
			dt.set(Calendar.HOUR_OF_DAY, 0);
			dt.set(Calendar.MINUTE, 0);
			dt.set(Calendar.SECOND, 0);
			dt.set(Calendar.MILLISECOND, 0);
		}

		return dt.after(lastMod);
	}

	protected boolean isDateBeforeCreateDate(Calendar dt) {
		Calendar create = this.getMetsDocument().getMets().getMetsHdr()
				.getCREATEDATE();
		create
				.add(Calendar.SECOND,
						-HaSMETSProfile.DATESTAMP_TOLERANCE_SECONDS);

		if (!dt.isSet(Calendar.HOUR_OF_DAY)) {
			dt.set(Calendar.HOUR_OF_DAY, 23);
			dt.set(Calendar.MINUTE, 59);
			dt.set(Calendar.SECOND, 59);
			dt.set(Calendar.MILLISECOND, 999);
		}

		return dt.before(create);
	}

	protected boolean isDateBeforeDate(Calendar dt1, Calendar dt2) {

		if (!dt1.isSet(Calendar.HOUR_OF_DAY)) {
			dt1.set(Calendar.HOUR_OF_DAY, 23);
			dt1.set(Calendar.MINUTE, 59);
			dt1.set(Calendar.SECOND, 59);
			dt1.set(Calendar.MILLISECOND, 999);
		}
		if (!dt2.isSet(Calendar.HOUR_OF_DAY)) {
			dt2.set(Calendar.HOUR_OF_DAY, 0);
			dt2.set(Calendar.MINUTE, 0);
			dt2.set(Calendar.SECOND, 0);
			dt2.set(Calendar.MILLISECOND, 0);
		}

		dt1.add(Calendar.SECOND, HaSMETSProfile.DATESTAMP_TOLERANCE_SECONDS);
		return dt1.before(dt2);
	}

	/**
	 * Determine whether the given MetsMdSecType is empty, meaning that it does
	 * not wrap or reference any metadata
	 * 
	 * @param mdsec
	 *            the MetsMdSecType of interests
	 * 
	 * @return true if it is empty, else false
	 */
	private boolean isMetsMdSecTypeEmpty(MetsMdSecType mdsec) {
		if (!mdsec.isSetMdRef() && !mdsec.isSetMdWrap()) {
			// there is no mdRef and no mdWrap
			return true;
		} else if (mdsec.isSetMdWrap() && !mdsec.getMdWrap().isSetXmlData()) {
			// there is an mdWrap, but no xmlData
			return true;
		} else if (mdsec.isSetMdWrap() && mdsec.getMdWrap().isSetXmlData()
				&& getXmlDataAny(mdsec.getMdWrap().getXmlData()) == null) {
			// there is an mdWrap and a xmlData, but the xmlData is empty
			return true;
		} else if (mdsec.isSetMdRef() && !mdsec.getMdRef().isSetHref()) {
			// there is an mdRef, but it doesn't have an xlink:href attribute
			return true;
		}
		return false;
	}

	/**
	 * Determine whether the given profile is a subclass of the profile for this
	 * class
	 * 
	 * @param profile
	 *            the profile string of the current document
	 * @return true or false
	 */
	protected boolean isSubclassOfThisProfile(String profile) {
		if (profile.compareTo("http://www.loc.gov/mets/profiles/00000016.xml") == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Save the MetsFileType file to the given location
	 * 
	 * @param file
	 *            the MetsFileType to save
	 * @param path
	 *            the location to save
	 */
	public void saveFile(MetsFileType file, String path)
			throws HaSMETSProfileException {
		try {
			InputStream inStrm = this.getFileInputStream(file);
			FileOutputStream outStrm = new FileOutputStream(path);
			int c;
			while ((c = inStrm.read()) != -1) {
				outStrm.write(c);
			}
			inStrm.close();
			outStrm.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new HaSMETSProfileException("File '" + path + "' not found",
					e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new HaSMETSProfileException(
					"Unable to read or write from the file", e);
		}
	}

	/**
	 * Set the base URI of the current METS file. Note this may invalidate any
	 * relative URLs contained in this file.
	 * 
	 * @param baseURI
	 *            the URI to set
	 */
	public void setBaseURI(URI baseURI) {
		this.baseURI = baseURI;
	}

	/**
	 * Extracts the value of the MODS title element and sets it as the LABEL of
	 * the HaSMETSProfile
	 * 
	 * @throws HaSMETSProfileException
	 */
	public void setLabelFromPrimaryDmdSec() throws HaSMETSProfileException {

		MetsMdSecType dmd = this.getPrimaryDmdSec();

		// get the value from the title element
		String title = dmd.selectPath("declare namespace m='"
				+ HaSMETSProfile.MODS_NS + "'; //m:title")[0].getDomNode()
				.getFirstChild().getNodeValue();

		if (title == null) {
			title = "";
		}

		this.getMetsDocument().getMets().setLABEL(title);
	}

	/**
	 * Set the xmlData of the given MetsMdSecType element to the given
	 * XmlObject. If the given mdsec already has xml metadata of the same type
	 * as that being set, either referenced or wrapped, it will be replaced.
	 * Referenced metadata of the same type will be replaced by wrapped
	 * metadata. If the existing metadata is of a different type that that being
	 * set an exception will be thrown.
	 * 
	 * @param mdsec
	 *            the MetsMdSecType object to which to add the XmlObject
	 * @param xobj
	 *            the XmlObject to set
	 * @throws HaSMETSProfileException
	 *             if the techMD//xmlData section contains multiple children or
	 *             if the techMD//xmlData already contains an element which is
	 *             of a different type than that being set
	 * @return the XML Node object representing the live DOM Node of the newly
	 *         added XmlObject metadata
	 */
	public Node setMDSecXmlObject(MetsMdSecType mdsec, XmlObject xobj)
			throws HaSMETSProfileException {

		// see if the xobj has a HaSSourceID PI, if so get the ID, this PI
		// should always be the last child
		String srcID = null;
		Node pi = xobj.getDomNode().getLastChild();
		if (pi.getNodeType() == Node.PROCESSING_INSTRUCTION_NODE
				&& pi.getNodeName().compareTo("HaSSourceID") == 0) {
			srcID = pi.getNodeValue();
		}

		if (isMetsMdSecTypeEmpty(mdsec)
				|| (srcID != null && mdsec.getID() != null && mdsec.getID()
						.compareTo(srcID) == 0)) {
			// determine what type the xobj is
			Node root = this.getRootElementFromXmlData(xobj);
			QName qn = this.getQName(root);

			// check whether there is pre-existing xmlData and whether it is
			// valid
			// this is mostly a sanity check since the above 'if' should have
			// eliminated most
			// disallowed set cases
			HaSMETSProfileException ex = getMDSecXmlDataException(mdsec, xobj,
					qn);
			if (ex != null)
				throw ex;

			// get the MDTYPE to use for the mdWrap
			MetsMdSecType.MdWrap.MDTYPE.Enum xmlType = null;
			String otherType = null;

			try {
				String type = ObjectTypes.getMDTYPE("techMD", qn);
				xmlType = MetsMdSecType.MdWrap.MDTYPE.Enum.forString(type);
				otherType = ObjectTypes.getOTHERMDTYPE("techMD", qn);
			} catch (IllegalArgumentException e) {
				throw new HaSMETSProfileException("Unexpected xobj '"
						+ qn.toString() + "'", e);
			}

			// if the mdWrap or xmlData elements are missing then add them, or
			// just
			// return the existing one
			MetsMdSecType.MdWrap.XmlData xml = addXmlDataIfMissing(mdsec,
					xmlType, otherType, "text/xml");

			// insert the xml from the pObj as a child of the XmlData element
			Node n = setXmlDataAny(xml, xobj);

			this.setLastModifiedToCurrent();

			return n;
		} else {
			throw new HaSMETSProfileException(
					"This xobj may only be set on the techmd element from which it came or on an empty techmd element.");
		}

	}

	/**
	 * Set the underlying XMLBean MetsDocument object
	 * 
	 * @param metsDocument
	 *            the XMLBean MetsDocument object
	 */
	protected void setMetsDocument(MetsMetsDocument metsDocument) {
		this.metsDoc = metsDocument;
	}

	/**
	 * Use the given values to give the METS document a new identifier
	 * 
	 * @param idValue
	 *            String for the new id
	 * @param idType
	 *            HaSMETSProfile.PREMISIdentifierType
	 * @throws HaSMETSProfileException
	 *             if there is no Primary Representation
	 */
	public void setNewPrimaryID(String idValue,
			HaSMETSProfile.PREMISIdentifierType idType)
			throws HaSMETSProfileException {
		if (idValue == null) return;
		if (metsDoc.getMets().isSetOBJID()) {
			String oldID = metsDoc.getMets().getOBJID();
			if (oldID.compareTo(idValue) != 0) {
				// this is a new id, so copy old id to the altRecordID
				// since this has an OBJID already we can assume it also has a
				// metsHdr
				// and Primary Representation with the original OBJID in it

				// first make sure the original id isn't already in the
				// altRecordIDs
				MetsMetsType.MetsHdr.AltRecordID altids[] = metsDoc.getMets()
						.getMetsHdr().getAltRecordIDArray();
				boolean foundID = false;
				for (int i = 0; i < altids.length; i++) {
					if (altids[i].getStringValue().compareTo(oldID) == 0) {
						foundID = true;
						break;
					}
				}
				if (!foundID) {
					MetsMetsType.MetsHdr.AltRecordID altid = metsDoc.getMets()
							.getMetsHdr().addNewAltRecordID();
					altid.setStringValue(oldID);
					altid.setTYPE(this.guessIDType(oldID).toString());
				}

				MetsMdSecType primRep = this.getPrimaryRepresentation();
				if (primRep == null) {
					// create a new primary rep
					primRep = this.addNewPrimaryRepresentation(idType, idValue);
				}
				PremisObjectDocument primRepObj = (PremisObjectDocument) this
						.getMDSecXmlObject(primRep);
				PremisObjectDocument.Object.ObjectIdentifier primID = primRepObj
						.getObject().addNewObjectIdentifier();
				primID.setObjectIdentifierValue(idValue);
				primID.setObjectIdentifierType(idType.toString());
				this.setMDSecXmlObject(primRep, primRepObj);

				metsDoc.getMets().setOBJID(idValue);

				// set the modified date to now
				this.setLastModifiedToCurrent();

			}

		} else {

			// there isn't an id yet so set it
			metsDoc.getMets().setOBJID(idValue);
			// also set it in the Primary Representation
			MetsMdSecType primRep = this.getPrimaryRepresentation();
			if (primRep == null) {
				// create a new primary rep
				primRep = this.addNewPrimaryRepresentation(idType, idValue);
			} else {
				PremisObjectDocument primRepObj = (PremisObjectDocument) this
						.getMDSecXmlObject(primRep);
				PremisObjectDocument.Object.ObjectIdentifier primID = primRepObj
						.getObject().addNewObjectIdentifier();
				primID.setObjectIdentifierValue(idValue);
				primID.setObjectIdentifierType(idType.toString());
				this.setMDSecXmlObject(primRep, primRepObj);
			}
			// the DATECREATED in the metsHdr also reflects the assignment of a
			// first id, so set that also
			MetsMetsType.MetsHdr hdr = null;
			if (!metsDoc.getMets().isSetMetsHdr()) {
				hdr = metsDoc.getMets().addNewMetsHdr();
			} else {
				hdr = metsDoc.getMets().getMetsHdr();
			}
			this.setLastModifiedToCurrent();
			hdr.setCREATEDATE(hdr.getLASTMODDATE());
		}

	}

	/**
	 * Set the OAI package type of the current METS document
	 * 
	 * @param packageType
	 *            HaSMETSProfile.PackageType: SIP, DIP, AIP
	 */
	public void setPackageType(HaSMETSProfile.PackageType packageType) {
		this.packageType = packageType;
	}

	/**
	 * Add an arbitrary Node as a child of a xmlData element
	 * 
	 * @param xml
	 *            the XmlData object to add the XmlObject to
	 * @param np
	 *            the XmlObject to add, this must be a Document node
	 * @return returns the xml Node object for the newly added Node
	 */
	private Node setXmlDataAny(MetsMdSecType.MdWrap.XmlData xml, Node np) {
		Node nx = xml.getDomNode();
		// if the obj has a trailing HasSourceID PI delete it before proceeding
		Node pi = np.getLastChild();
		if (pi.getNodeType() == Node.PROCESSING_INSTRUCTION_NODE
				&& pi.getNodeName().compareTo("HaSSourceID") == 0) {
			np.removeChild(pi);
		}

		Node nn = nx.getOwnerDocument().importNode(np.getFirstChild(), true);

		// delete the previous child before adding the new one
		if (nx.hasChildNodes()) {
			NodeList nl = nx.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++)
				nx.removeChild(nl.item(i));
		}

		nx.appendChild(nn);

		return nn;
	}

	/**
	 * Validate the METS document against the requirements of the METS HaS
	 * Profile
	 * 
	 * @param pt
	 *            the PackageType to assume for validation purposes
	 */
	public boolean validateProfile(PackageType pt) {
		return this.validateProfile(pt, null);
	}

	/**
	 * Validate the METS document against the requirements of the METS HaS
	 * Profile
	 * 
	 * @param pt
	 *            the PackageType to assume for validation purposes
	 * @param app
	 *            HaSMETSAppender which will accumulate the log events, may be
	 *            null
	 */
	public boolean validateProfile(PackageType pt, HaSMETSAppender app) {
		HaSMETSValidator val = new HaSMETSValidator();
		if (app != null)
			HaSMETSValidator.logger.addAppender(app);
		boolean ret = val.validateProfile(this, pt, true);
		if (app != null)
			HaSMETSValidator.logger.removeAppender(app);
		return ret;
	}

}
