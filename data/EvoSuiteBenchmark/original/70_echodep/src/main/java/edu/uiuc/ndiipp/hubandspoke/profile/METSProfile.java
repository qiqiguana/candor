/**
 * METSProfile.java
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

package edu.uiuc.ndiipp.hubandspoke.profile;

import edu.harvard.jhove.JhoveJhoveDocument;
import edu.nyu.textmd.TextMDDocument;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSChecksummer;
import gov.loc.amd.AmdAUDIOMDDocument;
import gov.loc.mets.MetsAmdSecType;
import gov.loc.mets.MetsAreaType;
import gov.loc.mets.MetsBehaviorType;
import gov.loc.mets.MetsDivType;
import gov.loc.mets.MetsFileGrpType;
import gov.loc.mets.MetsFileType;
import gov.loc.mets.MetsMdSecType;
import gov.loc.mets.MetsMetsDocument;
import gov.loc.mets.MetsMetsType;
import gov.loc.mets.MetsStructMapType;
import gov.loc.mets.MetsDivType.Mptr;
import gov.loc.mix.MixMixDocument;
import gov.loc.mods.ModsModsDocument;
import gov.loc.premis.PremisAgentDocument;
import gov.loc.premis.PremisEventDocument;
import gov.loc.premis.PremisObjectDocument;
import gov.loc.premis.PremisRightsDocument;
import gov.loc.vmd.VmdVIDEOMDDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.FileNameMap;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.TimeZone;
import java.util.zip.CheckedInputStream;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.SchemaProperty;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlID;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Generic METS profile class
 * @author thabing
 * @author Bill Ingram
 *
 */
public abstract class METSProfile {

	public static enum AMD_SECTION {
		DIGIPROVMD, RIGHTSMD, SOURCEMD, // kinds of admin sections
		TECHMD;

		@Override
		public String toString() {
			if (this.compareTo(METSProfile.AMD_SECTION.TECHMD) == 0)
				return "techMD";
			else if (this.compareTo(METSProfile.AMD_SECTION.DIGIPROVMD) == 0)
				return "digiprovMD";
			else if (this.compareTo(METSProfile.AMD_SECTION.RIGHTSMD) == 0)
				return "rightsMD";
			else if (this.compareTo(METSProfile.AMD_SECTION.SOURCEMD) == 0)
				return "sourceMD";
			else
				return this.name();
		}
	}

	public static enum DMDSecStatus {
		ALTERNATE_DMDSEC, PRIMARY_DMDSEC
	}

	public static enum PackageType {
		// OAIS package types
		AIP, DIP, SIP, UNKNOWN
	}

	public static enum PREMISAgentType {
		ORGANIZATION, PERSON, SOFTWARE
	}

	public static enum PREMISEventType {
		// suggested types from PREMIS data dictionary
		ADDITION, // NEWLY ADDED - PROFILE SHOULD BE UPDATED
		CALCULATION, 
		CAPTURE, 
		COMPRESSION, 
		DEACCESSION, 
		DECOMPRESSION, 
		DECRYPTION, 
		DELETION, 
		DIGITAL_SIGNATURE_VALIDATION, 
		DISSEMINATION, 
		FIXITY_CHECK, 
		INGESTION, 
		MESSAGE_DIGEST, 
		METADATA_CREATION, 
		METADATA_DELETION, 
		METADATA_MODIFICATION, 																																																									// used
		METADATA_TRANSFORMATION, 
		MIGRATION, 
		NORMALIZATION, 
		REPLICATION, 
		STRUCTMAP_CREATION, 
		STRUCTMAP_DELETION, 
		STRUCTMAP_MODIFICATION,																																		// used
		STRUCTMAP_TRANSFORMATION, 
		VALIDATION, 
		VIRUS_CHECK
	}

	public static enum PREMISIdentifierType {
		ARK, DOI, HANDLE, LOCAL, PURL, URL, URN
	}

	public static enum PREMISLinkingAgentRole {
		DATA_DESTINATION, DATA_SOURCE, EVENT_INITIATOR, SOFTWARE_USED
	}

	public static enum PREMISObjectCategory {
		BITSTREAM, FILE, REPRESENTATION

	}

	public static enum PREMISSwType {
		ANCILLARY, DRIVER, OPERATING_SYSTEM, // suggested types from PREMIS
												// data dictionary
		RENDERER
	}

	public static enum StructMapType {
		PRIMARY_STRUCTMAP
	}

	public static enum TechMDStatus {
		PRIMARY_REPRESENTATION
	}

	public static final String AMD_NS = "http://www.loc.gov/AMD/";

	protected static final int DATESTAMP_TOLERANCE_SECONDS = 120;

	// Options for loading or Saving XML into XMLBeans
	static XmlOptions defaultLoadOptions = new XmlOptions()
			.setLoadStripWhitespace().setLoadLineNumbers();

	static XmlOptions defaultSaveOptions = new XmlOptions()
			.setSavePrettyPrint().setSavePrettyPrintIndent(2);

	public static final String JHOVE_NS = "http://hul.harvard.edu/ois/xml/ns/jhove";

	// log4j logger
	static Logger logger = Logger.getLogger(HaSMETSProfile.class);

	public static final String METS_NS = "http://www.loc.gov/METS/";

	protected static final int MIME_FREQ_THRESHOLD = 75;
	public static final String MIX_NS = "http://www.loc.gov/mix/";

	public static final String MODS_NS = "http://www.loc.gov/mods/v3";

	// static properties
	public static final String PREMIS_NS = "http://www.loc.gov/standards/premis/v1";

	public static final String TEXTMD_NS = "";

	public static final String VMD_NS = "http://www.loc.gov/VMD/";

	public static final String XLINK_NS = "http://www.w3.org/1999/xlink";

	/**
	 * Return a basic PremisObjectDocument with the given parameters
	 * 
	 * @param idType
	 *            HaSMETSProfile.PREMISIdentifierType
	 * @param id
	 *            String with the identifier value
	 * @param objCat
	 *            HaSMETSProfile.PREMISObjectCategory
	 * @return PremisObjectDocument
	 */
	public static PremisObjectDocument createBasicPremisObject(
			HaSMETSProfile.PREMISIdentifierType idType, String id,
			HaSMETSProfile.PREMISObjectCategory objCat) {

		PremisObjectDocument objDoc = PremisObjectDocument.Factory
				.newInstance();
		PremisObjectDocument.Object obj = objDoc.addNewObject();
		PremisObjectDocument.Object.ObjectIdentifier objId = obj
				.addNewObjectIdentifier();
		objId.setObjectIdentifierType(idType.toString());
		objId.setObjectIdentifierValue(id);
		obj.setObjectCategory(objCat.toString());

		obj.setVersion(PremisObjectDocument.Object.Version.X_1_1);
		PremisObjectDocument.Object.ObjectCharacteristics objChar = null;

		switch (objCat) {
		case REPRESENTATION:
			obj.setType(PremisObjectDocument.Object.Type.REPRESENTATION);
			break;
		case FILE:
			obj.setType(PremisObjectDocument.Object.Type.FILE);
			objChar = obj.addNewObjectCharacteristics();
			objChar.setCompositionLevel(BigInteger.ZERO);
			break;
		case BITSTREAM:
			obj.setType(PremisObjectDocument.Object.Type.BITSTREAM);
			objChar = obj.addNewObjectCharacteristics();
			objChar.setCompositionLevel(BigInteger.ZERO);
			break;
		}

		return objDoc;
	}
	
	/**
	 * 
	 * @param f
	 * @param idType
	 * @param idVal
	 * @return
	 */
	public static PremisObjectDocument createPremisObject(File f,
			PREMISIdentifierType idType, String idVal) {

		// Open the file and analyze its contents
		int size = 0;
		Calendar lastModified = Calendar.getInstance();
		lastModified.setTimeInMillis(f.lastModified());
		HaSChecksummer summer = null;
		try {
			FileInputStream inpStrm = new FileInputStream(f);
			summer = new HaSChecksummer();
			CheckedInputStream chkStrm = new CheckedInputStream(inpStrm, summer);
			while (chkStrm.read() != -1)
				size++;
			chkStrm.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found '" + f.getPath()
					+ "'", e);
		} catch (IOException e) {
			throw new RuntimeException("Unable to read file '"
					+ f.getAbsolutePath() + "'", e);
		}
		String sha1 = summer.getHexEncodedSHA1();
		String md5 = summer.getHexEncodedMD5();

		// set property so URLConnection.getFileNameMap() returns a map based on
		// our properties file
		System.setProperty("content.types.user.table", System.getenv("HS_HOME")
				+ File.separator + "config" + File.separator
				+ "content-types.properties");
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		String mimeType = fileNameMap.getContentTypeFor(f.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}

		// create the PREMIS object with the required sub elements and
		// attributes
		PremisObjectDocument pDocObj = PremisObjectDocument.Factory
				.newInstance(defaultLoadOptions);
		PremisObjectDocument.Object pObj = pDocObj.addNewObject();
		pObj.setType(PremisObjectDocument.Object.Type.FILE);
		pObj.setVersion(PremisObjectDocument.Object.Version.X_1_1);
		PremisObjectDocument.Object.ObjectIdentifier pObjId = pObj
				.addNewObjectIdentifier();
		pObjId.setObjectIdentifierType(idType.toString());
		pObjId.setObjectIdentifierValue(idVal);

		pObj.setObjectCategory("FILE");

		PremisObjectDocument.Object.ObjectCharacteristics pObjChar = pObj
				.addNewObjectCharacteristics();
		pObjChar.setCompositionLevel(BigInteger.ZERO);

		// synchronize values from the file element with elements in the PREMIS
		// object
		PremisObjectDocument.Object.ObjectCharacteristics.Fixity pObjCharFix = pObjChar
				.addNewFixity();
		pObjCharFix.setMessageDigestAlgorithm(MetsFileType.CHECKSUMTYPE.SHA_1
				.toString());
		pObjCharFix.setMessageDigest(sha1);

		pObjCharFix = pObjChar.addNewFixity();
		pObjCharFix.setMessageDigestAlgorithm(MetsFileType.CHECKSUMTYPE.MD_5
				.toString());
		pObjCharFix.setMessageDigest(md5);

		pObjChar.setSize(size);

		PremisObjectDocument.Object.ObjectCharacteristics.Format pObjCharForm = pObjChar
				.addNewFormat();
		PremisObjectDocument.Object.ObjectCharacteristics.Format.FormatDesignation pObjCharFormDes = pObjCharForm
				.addNewFormatDesignation();
		pObjCharFormDes.setFormatName(mimeType);

		if (mimeType.toLowerCase().startsWith("application/")) {
			// TODO: Look at using some sort of lookup to guess best values for
			// these
			// TODO: Mets profile needs to be relaxed for these, since it will
			// often be
			// impossible to determine
			pObj.addNewCreatingApplication().setCreatingApplicationName(
					"unknown");
			pObj.getCreatingApplicationArray(0).setDateCreatedByApplication(
					lastModified);

			PremisObjectDocument.Object.Environment.Software sw = pObj
					.addNewEnvironment().addNewSoftware();
			String swname = mimeType.substring(mimeType.indexOf("/") + 1);
			if (swname.toLowerCase().startsWith("x-")) {
				swname = swname.substring(2);
			} else if (swname.toLowerCase().startsWith("vnd.")) {
				swname = swname.substring(4);
			}
			sw.setSwName(swname);
			sw.setSwType(HaSMETSProfile.PREMISSwType.RENDERER.toString());
		}
		return pDocObj;
	}

	// Manages all ID attributes and associated XmlObjects
	protected Hashtable<String, XmlObject> allIDs = new Hashtable<String, XmlObject>();

	// the base URI of the mets document, needed to dereference the
	// relative URIs of the FLocat/@hrefs
	protected URI baseURI = null;

	protected long idCounter = 0;

	// The MetsMetsDocument which is wrapped by this class
	protected MetsMetsDocument metsDoc = null;

	// The URI assigned to documents that conform to this profile
	protected String PROFILE_URI;
	
	// TODO: Use these
	protected String sha1;
	protected String md5;
	protected int size;

	protected METSProfile() {

		// construct a minimally-conforming METS document (AIP)
		metsDoc = MetsMetsDocument.Factory.newInstance(defaultLoadOptions);
		MetsMetsType mets = metsDoc.addNewMets();

		MetsMetsType.MetsHdr hdr = mets.addNewMetsHdr();
		this.setLastModifiedToCurrent();
		hdr.setCREATEDATE(hdr.getLASTMODDATE());

		MetsStructMapType smap = mets.addNewStructMap();
		MetsDivType div = smap.addNewDiv();
		smap.setTYPE(METSProfile.StructMapType.PRIMARY_STRUCTMAP.toString());

		baseURI = (new File(System.getProperty("user.dir"))).toURI();
	}

	/**
	 * Add a new amdSec sub-element of the given type. This also creates mdWrap
	 * and xmlData sub-elements
	 * 
	 * @param amdType
	 *            the type of sub-element HaSMETSProfile.TECHMD,
	 *            HaSMETSProfile.DIGIPROVMD, etc.
	 * @param xmlType
	 *            the MetsMdSecType.MdWrap.MDTYPE of the wrapped xml
	 * @param otherType
	 *            if the xmlType is OTHER, then put the other type here
	 * @return the MetsMdSecType for the added amdSec sub-element
	 */
	protected MetsMdSecType addAmdSecXmlWrapper(
			HaSMETSProfile.AMD_SECTION amdType,
			MetsMdSecType.MdWrap.MDTYPE.Enum xmlType, String otherType) {
		// check if there is an amdSec, if not create one, else
		// just use the first one since our profile doesn't care
		// if there is more than one or not
		MetsAmdSecType amd;

		if (metsDoc.getMets().sizeOfAmdSecArray() > 0)
			amd = metsDoc.getMets().getAmdSecArray(0);
		else
			amd = metsDoc.getMets().addNewAmdSec();

		// add the section to the amdSec
		MetsMdSecType techmd = null;
		switch (amdType) {
		case TECHMD:
			techmd = amd.addNewTechMD();
			break;
		case DIGIPROVMD:
			techmd = amd.addNewDigiprovMD();
			break;
		case RIGHTSMD:
			techmd = amd.addNewRightsMD();
			break;
		case SOURCEMD:
			techmd = amd.addNewSourceMD();
			break;
		default:
			throw new IllegalArgumentException("Unexpected amdType attribute");
		}
		// assign it a new ID and set the created date using GMT
		getNewID(null, techmd);
		techmd.setCREATED(new GregorianCalendar(TimeZone.getTimeZone("GMT")));

		// add the mdWrap and xmlData children and set appropriate attributes
		addXmlDataIfMissing(techmd, xmlType, otherType, "text/xml");

		return techmd;
	}

	/**
	 * Add a techMD/mdWrap/xmlData section of the appropriate type and link it
	 * to the given XmlObject element via the XmlObject element's ADMID
	 * attribute
	 * 
	 * @param amdType
	 *            an int constant indicating which type of MetsMdSecType to use:
	 *            TECHM, RIGHTSMD, DIGIPROVMD, or SOURCEMD
	 * @param xobj
	 *            the XmlObject element to attach the new techMD to
	 * @param xmlType
	 *            an MetsMdSecType.MdWrap.MDTYPE for the type of metadata to be
	 *            contained in the xmlData section
	 * 
	 * @return the newly added techMD section
	 */
	protected MetsMdSecType addAmdSecXmlWrapperToXmlObject(
			HaSMETSProfile.AMD_SECTION amdType, XmlObject xobj,
			MetsMdSecType.MdWrap.MDTYPE.Enum xmlType) {
		return addAmdSecXmlWrapperToXmlObject(amdType, xobj, xmlType, null);
	}

	/**
	 * Add a techMD/mdWrap/xmlData section of the appropriate type and link it
	 * to the given XmlObject element via the XmlObject element's ADMID
	 * attribute
	 * 
	 * @param amdType
	 *            an int constant indicating which type of MetsMdSecType to use:
	 *            TECHM, RIGHTSMD, DIGIPROVMD, or SOURCEMD
	 * @param xobj
	 *            the XmlObject element to attach the new techMD to
	 * @param xmlType
	 *            an MetsMdSecType.MdWrap.MDTYPE for the type of metadata to be
	 *            contained in the xmlData section
	 * @param otherType
	 *            the String value for the type if the xmlType is OTHER
	 * 
	 * @return the newly added techMD section
	 */
	@SuppressWarnings("unchecked")
	protected MetsMdSecType addAmdSecXmlWrapperToXmlObject(
			HaSMETSProfile.AMD_SECTION amdType, XmlObject xobj,
			MetsMdSecType.MdWrap.MDTYPE.Enum xmlType, String otherType) {
		// check if the xobj can accept an ADMID attribute
		SchemaType st = xobj.schemaType();
		SchemaProperty sp = st.getAttributeProperty(new QName("ADMID"));
		if (sp == null) {
			throw new RuntimeException(
					"The XmlObject cannot have an ADMID attribute");
		}

		MetsMdSecType techmd = this.addAmdSecXmlWrapper(amdType, xmlType,
				otherType);
		XmlID xid = techmd.xgetID();

		// cast the XmlObject to the appropriate type and set the ADMID
		// attribute
		if (xobj instanceof MetsMetsType.MetsHdr) {
			if (((MetsMetsType.MetsHdr) xobj).isSetADMID()) {
				ArrayList lst = new ArrayList(((MetsMetsType.MetsHdr) xobj)
						.getADMID());
				lst.add(xid.getStringValue());
				((MetsMetsType.MetsHdr) xobj).setADMID(lst);
			} else {
				ArrayList<String> lst = new ArrayList<String>();
				lst.add(xid.getStringValue());
				((MetsMetsType.MetsHdr) xobj).setADMID(lst);
			}
		} else if (xobj instanceof MetsFileGrpType) {
			if (((MetsFileGrpType) xobj).isSetADMID()) {
				ArrayList lst = new ArrayList(((MetsFileGrpType) xobj)
						.getADMID());
				lst.add(xid.getStringValue());
				((MetsFileGrpType) xobj).setADMID(lst);
			} else {
				ArrayList<String> lst = new ArrayList<String>();
				lst.add(xid.getStringValue());
				((MetsFileGrpType) xobj).setADMID(lst);
			}
		} else if (xobj instanceof MetsDivType) {
			if (((MetsDivType) xobj).isSetADMID()) {
				ArrayList lst = new ArrayList(((MetsDivType) xobj).getADMID());
				lst.add(xid.getStringValue());
				((MetsDivType) xobj).setADMID(lst);
			} else {
				ArrayList<String> lst = new ArrayList<String>();
				lst.add(xid.getStringValue());
				((MetsDivType) xobj).setADMID(lst);
			}
		} else if (xobj instanceof MetsAreaType) {
			if (((MetsAreaType) xobj).isSetADMID()) {
				ArrayList lst = new ArrayList(((MetsAreaType) xobj).getADMID());
				lst.add(xid.getStringValue());
				((MetsAreaType) xobj).setADMID(lst);
			} else {
				ArrayList<String> lst = new ArrayList<String>();
				lst.add(xid.getStringValue());
				((MetsAreaType) xobj).setADMID(lst);
			}
		} else if (xobj instanceof MetsBehaviorType) {
			if (((MetsBehaviorType) xobj).isSetADMID()) {
				ArrayList lst = new ArrayList(((MetsBehaviorType) xobj)
						.getADMID());
				lst.add(xid.getStringValue());
				((MetsBehaviorType) xobj).setADMID(lst);
			} else {
				ArrayList<String> lst = new ArrayList<String>();
				lst.add(xid.getStringValue());
				((MetsBehaviorType) xobj).setADMID(lst);
			}
		} else if (xobj instanceof MetsMdSecType) {
			if (((MetsMdSecType) xobj).isSetADMID()) {
				ArrayList lst = new ArrayList(((MetsMdSecType) xobj).getADMID());
				lst.add(xid.getStringValue());
				((MetsMdSecType) xobj).setADMID(lst);
			} else {
				ArrayList<String> lst = new ArrayList<String>();
				lst.add(xid.getStringValue());
				((MetsMdSecType) xobj).setADMID(lst);
			}
		} else if (xobj instanceof MetsFileType.Stream) {
			if (((MetsFileType.Stream) xobj).isSetADMID()) {
				ArrayList lst = new ArrayList(((MetsFileType.Stream) xobj)
						.getADMID());
				lst.add(xid.getStringValue());
				((MetsFileType.Stream) xobj).setADMID(lst);
			} else {
				ArrayList<String> lst = new ArrayList<String>();
				lst.add(xid.getStringValue());
				((MetsFileType.Stream) xobj).setADMID(lst);
			}
		} else if (xobj instanceof MetsFileType) {
			if (((MetsFileType) xobj).isSetADMID()) {
				ArrayList lst = new ArrayList(((MetsFileType) xobj).getADMID());
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
		// add the premis techMD ID to the AMDIDs of the XmlObject

		return techmd;
	}

	/**
	 * If the mdSecType does not contain an xmlData child then add one and
	 * return it; otherwise, just return the existing xmlData. The mdWrap
	 * attributes MDTYPE, OTHERMDTYPE, and MIMETYPE are only set if a brand new
	 * mdWrap element is created; otherwise they are left alone.
	 * <p>
	 * If the mdSec already has an mdRef element it will be totally replaced by
	 * the mdWrap element
	 * 
	 * @param md
	 *            the MdSecType to which to add the xmlData
	 * @param mdType
	 *            the MDTYPE attribute value to use for the mdWrap element
	 * @param otherMDType
	 *            the OTHERMDTYPE attribute value to use for the mdWrap element
	 * @param mimeType
	 *            the MIMETYPE attribute value to use for the mdWrap element
	 * @return an XmlData element
	 */
	protected MetsMdSecType.MdWrap.XmlData addXmlDataIfMissing(
			MetsMdSecType md, MetsMdSecType.MdWrap.MDTYPE.Enum mdType,
			String otherMDType, String mimeType) {
		// if the mdWrap or xmlData elements are missing then add them
		if (!md.isSetMdWrap()) {
			md.addNewMdWrap();
			md.getMdWrap().setMDTYPE(mdType);
			if (mdType == MetsMdSecType.MdWrap.MDTYPE.OTHER
					&& otherMDType != null)
				md.getMdWrap().setOTHERMDTYPE(otherMDType);
			md.getMdWrap().setMIMETYPE(mimeType);
			md.getMdWrap().addNewXmlData();
		}
		if (!md.getMdWrap().isSetXmlData())
			md.getMdWrap().addNewXmlData();

		// if there is an mdRef, replace delete it, the assumptiion is that it
		// has already been checked
		// whether this is safe or not.
		if (md.isSetMdRef())
			md.unsetMdRef();

		return md.getMdWrap().getXmlData();

	}

	/**
	 * Return an array of XMLBean FileType objects for all files
	 * 
	 * @return an array of XMLBean FileType objects
	 */
	public MetsFileType[] getAllFiles() {
		MetsFileType ft[] = null;

		String xql = "declare namespace m='" + METS_NS + "'; .//m:file";

		XmlObject xobj[] = metsDoc.selectPath(xql);

		if (xobj.length > 0)
			ft = (MetsFileType[]) xobj;
		else
			ft = new MetsFileType[0];

		return ft;

	}


	/**
	 * Return an array of XMLBean Mptr objects
	 * 
	 * @return an array of XMLBean Mptr objects
	 */
	public Mptr[] getAllMptrs() {
		Mptr mptr[] = null;

		String xql = "declare namespace m='" + METS_NS + "'; .//m:mptr";

		XmlObject xobj[] = metsDoc.selectPath(xql);

		if (xobj.length > 0)
			mptr = (Mptr[]) xobj;
		else
			mptr = new Mptr[0];
		
		return mptr;
	}

	/**
	 * Given an xml Node, return the appropriate XmlBeans XmlObject type
	 * 
	 * @param xml
	 *            the xml Node of interest
	 * @return an XmlObject which may be cast to a specific XmlBeans data type
	 * @throws HaSMETSProfileException
	 *             if the xml Node cannot be parsed as on of the known XmlBean
	 *             types
	 */
	protected XmlObject getAnyXmlObject(Node xml)
			throws HaSMETSProfileException {
		XmlObject xobj = null;
		String rootName = xml.getLocalName();
		String rootNS = xml.getNamespaceURI();
		if (rootNS == null)
			rootNS = "";

		if (rootName.compareTo("object") == 0
				&& rootNS.compareTo(PREMIS_NS) == 0) {
			try {
				xobj = PremisObjectDocument.Factory.parse(xml,
						defaultLoadOptions);
			} catch (XmlException e) {
				throw new HaSMETSProfileException(
						"Unable to parse the Premis Object document", e);
			}
		} else if (rootName.compareTo("event") == 0
				&& rootNS.compareTo(PREMIS_NS) == 0) {
			try {
				xobj = PremisEventDocument.Factory.parse(xml,
						defaultLoadOptions);
			} catch (XmlException e) {
				throw new HaSMETSProfileException(
						"Unable to parse the Premis Event document", e);
			}
		} else if (rootName.compareTo("agent") == 0
				&& rootNS.compareTo(PREMIS_NS) == 0) {
			try {
				xobj = PremisAgentDocument.Factory.parse(xml,
						defaultLoadOptions);
			} catch (XmlException e) {
				throw new HaSMETSProfileException(
						"Unable to parse the Premis Agent document", e);
			}
		} else if (rootName.compareTo("rights") == 0
				&& rootNS.compareTo(PREMIS_NS) == 0) {
			try {
				xobj = PremisRightsDocument.Factory.parse(xml,
						defaultLoadOptions);
			} catch (XmlException e) {
				throw new HaSMETSProfileException(
						"Unable to parse the Premis Rights document", e);
			}
		} else if (rootName.compareTo("AUDIOMD") == 0
				&& rootNS.compareTo(AMD_NS) == 0) {
			try {
				xobj = AmdAUDIOMDDocument.Factory
						.parse(xml, defaultLoadOptions);
			} catch (XmlException e) {
				throw new HaSMETSProfileException(
						"Unable to parse the AUDIOMD document", e);
			}
		} else if (rootName.compareTo("VIDEOMD") == 0
				&& rootNS.compareTo(VMD_NS) == 0) {
			try {
				xobj = VmdVIDEOMDDocument.Factory
						.parse(xml, defaultLoadOptions);
			} catch (XmlException e) {
				throw new HaSMETSProfileException(
						"Unable to parse the VIDEOMD document", e);
			}
		} else if (rootName.compareTo("mix") == 0
				&& rootNS.compareTo(MIX_NS) == 0) {
			try {
				xobj = MixMixDocument.Factory.parse(xml, defaultLoadOptions);
			} catch (XmlException e) {
				throw new HaSMETSProfileException(
						"Unable to parse the Mix document", e);
			}
		} else if (rootName.compareTo("textMD") == 0
				&& rootNS.compareTo(TEXTMD_NS) == 0) {
			try {
				xobj = TextMDDocument.Factory.parse(xml, defaultLoadOptions);
			} catch (XmlException e) {
				throw new HaSMETSProfileException(
						"Unable to parse the TextMD document", e);
			}
		} else if (rootName.compareTo("jhove") == 0
				&& rootNS.compareTo(JHOVE_NS) == 0) {
			try {
				xobj = JhoveJhoveDocument.Factory
						.parse(xml, defaultLoadOptions);
			} catch (XmlException e) {
				throw new HaSMETSProfileException(
						"Unable to parse the JHOVE document", e);
			}
		} else if (rootName.compareTo("mods") == 0
				&& rootNS.compareTo(MODS_NS) == 0) {
			try {
				xobj = ModsModsDocument.Factory.parse(xml, defaultLoadOptions);
			} catch (XmlException e) {
				throw new HaSMETSProfileException(
						"Unable to parse the Mods document", e);
			}
		} else {
			throw new HaSMETSProfileException("The xml '{" + rootNS + "}"
					+ rootName + "' is not recognized by the profile.");
		}
		return xobj;
	}

	/**
	 * Return the base URI of the current METS file
	 * 
	 * @return the base URI of the current METS file
	 */
	public URI getBaseURI() {
		return baseURI;
	}

	/**
	 * Return the base URI of the current METS file, minus the filename
	 * 
	 * @return the base URI of the current METS file
	 */
	public URI getBaseURIMinusFile() {
		String uri = baseURI.toASCIIString();
		int i = uri.lastIndexOf("/");
		uri = uri.substring(0, i + 1);
		URI newURI;
		try {
			newURI = new URI(uri);
		} catch (URISyntaxException e) {
			throw new RuntimeException("Unable to shorten the URI", e);
		}
		return newURI;
	}

	/**
	 * Return the xml which is either referenced or wrapped in the given
	 * mdSecType section. Since the HaS profile is assumed, this method assumes
	 * just one child which is either wrapped or referenced. The XmlObject which
	 * will be returned will be one of these XMLBeans types depending on the
	 * xml:
	 * <ul>
	 * <li>PremisObjectDocument
	 * <li>PremisAgentDocument
	 * <li>PremisEventDocument
	 * <li>PremisRightsDocument
	 * <li>ModsModsDocument
	 * <li>JhoveJhoveDocument
	 * <li>TextMDDocument
	 * <li>AmdAUDIOMDDocument
	 * <li>VmdVIDEOMDDocument
	 * <li>MixMixDocument
	 * <li>XmlObject
	 * </ul>
	 * If the xml is not one of the recognized types a generic XmlObject will be
	 * returned
	 * <p>
	 * NOTE: The returned object is a copy; therefore, if changes are made which
	 * must be reflected back in the METS document the setMDSecXmlObject method
	 * must be called.
	 * 
	 * @param mdsec
	 *            an MdSecType object representing the MetsMdSecType element
	 * @return a XmlObject which may be cast into one of the appropriate XMLBean
	 *         types list above
	 * @throws HaSMETSProfileException
	 *             if the xmlData cannot be parsed into a recognized type or if
	 *             the xml is not accessible
	 */
	public XmlObject getMDSecXmlObject(MetsMdSecType mdsec)
			throws HaSMETSProfileException {
		Node xml = null;
		if (mdsec != null && mdsec.isSetMdWrap()
				&& mdsec.getMdWrap().isSetXmlData()
				&& mdsec.getMdWrap().getXmlData().getDomNode() != null) {
			// get the xml from the wrapper
			xml = this.getXmlDataAny(mdsec.getMdWrap().getXmlData());
		} else if (mdsec != null && mdsec.isSetMdRef()
				&& mdsec.getMdRef().isSetHref()) {
			// load the xml from the referenced location
			URI uri = resolveURIAgainstBaseURI(mdsec.getMdRef().getHref());
			URL url = null;
			try {
				url = uri.toURL();
			} catch (MalformedURLException e) {
				throw new HaSMETSProfileException("The href '"
						+ mdsec.getMdRef().getHref() + "' is not a valid URL.",
						e);
			}
			DocumentBuilderFactory docf = DocumentBuilderFactory.newInstance();
			try {
				DocumentBuilder docb = docf.newDocumentBuilder();
				InputStream is = url.openStream();
				xml = docb.parse(is);
				is.close();
				xml = ((Document) xml).getDocumentElement();
			} catch (SAXException e) {
				throw new HaSMETSProfileException(
						"Unable to open the referenced metadata '"
								+ url.toExternalForm() + "'", e);
			} catch (IOException e) {
				throw new HaSMETSProfileException(
						"Unable to open the referenced metadata '"
								+ url.toExternalForm() + "'", e);
			} catch (ParserConfigurationException e) {
				throw new HaSMETSProfileException(
						"Unable to open the referenced metadata '"
								+ url.toExternalForm() + "'", e);
			}
		} else {
			throw new HaSMETSProfileException(
					"The techMD does not wrap or reference any XML metadata.");
		}

		// Get the appropriate type of XmlObject
		XmlObject xobj = this.getAnyXmlObject(xml);

		// add a processing instruction to indicate from where the object
		// originated this will be used later to ensure that this object is only
		// set back
		// to the same mdsec or to a brand new mdsec
		Node pi = ((Document) xobj.getDomNode()).createProcessingInstruction(
				"HaSSourceID", mdsec.getID());
		xobj.getDomNode().appendChild(pi);

		return xobj;
	}

	/**
	 * Return the underlying XMLBean MetsDocument object
	 * 
	 * @return the underlying XMLBean MetsDocument object
	 */
	public MetsMetsDocument getMetsDocument() {
		return metsDoc;
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
	 * Return the root child element of the given xmlData element
	 * 
	 * @param xmldata
	 *            the xmlData of interest
	 * @return returns the Node which is the first child element of the xmlData,
	 *         or null if there is none
	 */
	protected Node getXmlDataAny(MetsMdSecType.MdWrap.XmlData xmldata) {
		XmlObject[] ns = xmldata.selectPath("*");
		if (ns == null || ns.length == 0)
			return null;
		else
			return ns[0].getDomNode();
	}

	/**
	 * Guess the type of an identifier string
	 * 
	 * @param id
	 *            the identifier String
	 * @return a PREMISIdentifierType Enum
	 */
	protected PREMISIdentifierType guessIDType(String id) {
		// TODO: Add some more rules for different types
		if (id.startsWith("http") || id.startsWith("HTTP"))
			return PREMISIdentifierType.URL;
		else
			return PREMISIdentifierType.LOCAL;
	}

	/**
	 * Initilaize the allIDs Hashtable whose keys are all of the ID attributes
	 * contained in the document and whose elements are the corresponding
	 * XmlObjects
	 */
	protected void initIDs() {
		allIDs.clear();

		String xql = "//@ID";

		XmlObject ids[] = metsDoc.selectPath(xql);

		for (int i = 0; i < ids.length; i++) {
			String xql2 = "//*[@ID='" + ((XmlID) ids[i]).getStringValue()
					+ "']";
			XmlObject[] ob = metsDoc.selectPath(xql2);
			if (ob.length > 0)
				allIDs.put(((XmlID) ids[i]).getStringValue(), ob[0]);
		}

	}

	/**
	 * Add a new PREMIS Object techMD section that is associated with the given
	 * div element, and return the newly added techMD section that contains the
	 * PREMIS object.
	 * <p>
	 * NOTE: The idType and idVal parameters are not the same as the XML ID
	 * attributes used to associate the new techMD section to the file.
	 * 
	 * @param div
	 *            the div element to which the techMD is associated
	 * @param idType
	 *            the type of the ID to be used with the PREMIS object
	 * @param idVal
	 *            the value of the identifier
	 * 
	 * @return returns the new techMD section that was created
	 * 
	 * @throws HaSMETSProfileException
	 */
	public MetsMdSecType newPREMISObjectTechMD(MetsDivType div,
			PREMISIdentifierType idType, String idVal)
			throws HaSMETSProfileException {

		MetsMdSecType techmd = addAmdSecXmlWrapperToXmlObject(
				HaSMETSProfile.AMD_SECTION.TECHMD, div,
				MetsMdSecType.MdWrap.MDTYPE.PREMIS);

		// create the PREMIS object with the required sub elements and
		// attributes
		PremisObjectDocument pDocObj = METSProfile.createBasicPremisObject(
				idType, idVal,
				HaSMETSProfile.PREMISObjectCategory.REPRESENTATION);

		// add the PREMIS to the xmlData element; must use DOM methods because
		// of xs:any
		setXmlDataAny(techmd.getMdWrap().getXmlData(), pDocObj);

		this.setLastModifiedToCurrent();
		return techmd;
	}

	/**
	 * Add a new PREMIS Object techMD section that is associated with the given
	 * file, and return the newly added techMD section that contains the PREMIS
	 * object. Attributes of the file element such as CHECKSUM, MIMETYPE, and
	 * SIZE are copied into the PREMIS object.
	 * <p>
	 * NOTE: The idType and idVal parameters are not the same as the XML ID
	 * attributes used to associate the new techMD section to the file.
	 * 
	 * @param ft
	 *            the file to which the techMD is associated
	 * @param idType
	 *            the type of the ID to be used with the PREMIS object
	 * @param idVal
	 *            the value of the identifier
	 * 
	 * @return returns the new techMD section that was created
	 * 
	 * @throws HaSMETSProfileException
	 */
	public MetsMdSecType newPREMISObjectTechMD(MetsFileType ft,
			PREMISIdentifierType idType, String idVal)
			throws HaSMETSProfileException {

		MetsMdSecType techmd = addAmdSecXmlWrapperToXmlObject(
				HaSMETSProfile.AMD_SECTION.TECHMD, ft,
				MetsMdSecType.MdWrap.MDTYPE.PREMIS);

		File f = new File(this.baseURI.resolve(ft.getFLocatArray(0).getHref()));
		if(!f.exists()){
			throw new HaSMETSProfileException("File not found: " 
				+ ft.getFLocatArray(0).getHref());
		}
		PremisObjectDocument pDocObj = createPremisObject(f, idType, idVal);
		PremisObjectDocument.Object pObj = pDocObj.getObject();

		// if the file has an OWNERID then add it as an alternate identifier,
		// unless that
		// already is the identifier that is being added
		if (ft.isSetOWNERID()
				&& ft.getOWNERID().compareToIgnoreCase(idVal) != 0) {
			PremisObjectDocument.Object.ObjectIdentifier pObjId2 = pObj
					.addNewObjectIdentifier();
			pObjId2.setObjectIdentifierType(guessIDType(ft.getOWNERID())
					.toString());
			pObjId2.setObjectIdentifierValue(ft.getOWNERID());
		}

		// if file has FLocat children, add their href attributes as identifiers
		MetsFileType.FLocat flocat[] = ft.getFLocatArray();
		for (int k = 1; k < flocat.length; k++) {
			PremisObjectDocument.Object.ObjectIdentifier pObjId2 = pObj
					.addNewObjectIdentifier();
			pObjId2.setObjectIdentifierValue(flocat[k].getHref());
			if (flocat[k].getLOCTYPE()
					.equals(MetsFileType.FLocat.LOCTYPE.OTHER)
					&& flocat[k].isSetOTHERLOCTYPE()) {
				pObjId2.setObjectIdentifierType(flocat[k].getOTHERLOCTYPE());
			} else if (flocat[k].getLOCTYPE().equals(
					MetsFileType.FLocat.LOCTYPE.OTHER)) {
				pObjId2
						.setObjectIdentifierType(HaSMETSProfile.PREMISIdentifierType.LOCAL
								.toString());
			} else {
				pObjId2.setObjectIdentifierType(flocat[k].getLOCTYPE()
						.toString());
			}
		}

		// add the PREMIS to the xmlData element; must use DOM methods because
		// of xs:any
		setXmlDataAny(techmd.getMdWrap().getXmlData(), pDocObj);

		setLastModifiedToCurrent();
		return techmd;
	}

	/**
	 * Add a new PREMIS Object techMD section that is associated with the given
	 * mptr div, and return the newly added techMD section that contains the
	 * PREMIS object. Attributes of the mptr target file such as CHECKSUM,
	 * MIMETYPE, and SIZE are copied into the PREMIS object.
	 * <p>
	 * NOTE: The idType and idVal parameters are not the same as the XML ID
	 * attributes used to associate the new techMD section to the file.
	 * 
	 * @param mptr
	 *            the mptr to which the techMD will be associated
	 * @param idType
	 *            the type of the ID to be used with the PREMIS object
	 * @param idVal
	 *            the value of the identifier
	 * 
	 * @return returns the new techMD section that was created
	 * 
	 * @throws HaSMETSProfileException
	 */
	public MetsMdSecType newPREMISObjectTechMD(Mptr mptr,
			PREMISIdentifierType idType, String idVal)
			throws HaSMETSProfileException {
		
		XmlCursor curs = mptr.newCursor();
		curs.toParent();
		MetsDivType div = (MetsDivType) curs.getObject();

		MetsMdSecType techmd = addAmdSecXmlWrapperToXmlObject(
				HaSMETSProfile.AMD_SECTION.TECHMD, div,
				MetsMdSecType.MdWrap.MDTYPE.PREMIS);

		File f = new File(this.baseURI.resolve(div.getMptrArray(0).getHref()));
		PremisObjectDocument pDocObj = createPremisObject(f, idType, idVal);
		PremisObjectDocument.Object pObj = pDocObj.getObject();

		if (pObj.getCreatingApplicationArray().length > 0) {
			pObj.getCreatingApplicationArray(0).setCreatingApplicationName(
					"EchoDep Hub and Spoke Tool Suite");
			pObj.getCreatingApplicationArray(0).setDateCreatedByApplication(
					Calendar.getInstance());
		}

		// add the PREMIS to the xmlData element; must use DOM methods because
		// of xs:any
		setXmlDataAny(techmd.getMdWrap().getXmlData(), pDocObj);

		setLastModifiedToCurrent();
		return techmd;
	}

	/**
	 * Return a new URI which is the given relative URI string resolved against
	 * base URI for the METS document
	 * 
	 * @see <a
	 *      href='http://java.sun.com/javase/6/docs/api/java/net/URI.html#resolve(java.lang.String)'>resolve</a>
	 * 
	 * @param relativeURI
	 *            the relative URI to be resolved
	 * @return the new URI
	 */
	public URI resolveURIAgainstBaseURI(String relativeURI) {
		return baseURI.resolve(relativeURI);
	}

	/**
	 * Return a new URI which is the given relative URI resolved against the
	 * base URI for the METS document
	 * 
	 * @see <a
	 *      href='http://java.sun.com/javase/6/docs/api/java/net/URI.html#resolve(java.net.URI)'>resolve</a>
	 * 
	 * @param relativeURI
	 *            the relative URI to be resolved
	 * @return the new URI
	 */
	public URI resolveURIAgainstBaseURI(URI relativeURI) {
		return baseURI.resolve(relativeURI);
	}
	
	/**
	 * 
	 */
	public void save() {
		// TODO: update checksums here
		try {
			metsDoc.save(new File(this.baseURI), METSProfile.defaultSaveOptions);
		} catch (IOException e) {
			throw new RuntimeException("Unable to save to file '" + this.baseURI + "'",
					e);
		}
		// update checksums here
		// convert mets object to file
		File f = new File(this.baseURI) ;
  
		// Open the file and analyze its contents
		size = 0;
		Calendar lastModified = Calendar.getInstance();
		lastModified.setTimeInMillis(f.lastModified());
		HaSChecksummer summer = null;
		try {
			FileInputStream inpStrm = new FileInputStream(f);
			summer = new HaSChecksummer();
			CheckedInputStream chkStrm = new CheckedInputStream(inpStrm, summer);
			while (chkStrm.read() != -1)
				size++;
			chkStrm.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found '" + f.getPath()
					+ "'", e);
		} catch (IOException e) {
			throw new RuntimeException("Unable to read file '"
					+ f.getAbsolutePath() + "'", e);
		}
		sha1 = summer.getHexEncodedSHA1();
		md5 = summer.getHexEncodedMD5();
	}
	
	/**
	 * Save the METS document to the given destination file
	 * 
	 * @param dest
	 */
	public void save(String dest) {
		try {
			metsDoc.save(new File(dest), METSProfile.defaultSaveOptions);
		} catch (IOException e) {
			throw new RuntimeException("Unable to save to file '" + dest + "'",
					e);
		}
		// update checksums here
		// convert mets object to file
		File f = new File(dest) ;
  
		// Open the file and analyze its contents
		size = 0;
		Calendar lastModified = Calendar.getInstance();
		lastModified.setTimeInMillis(f.lastModified());
		HaSChecksummer summer = null;
		try {
			FileInputStream inpStrm = new FileInputStream(f);
			summer = new HaSChecksummer();
			CheckedInputStream chkStrm = new CheckedInputStream(inpStrm, summer);
			while (chkStrm.read() != -1)
				size++;
			chkStrm.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found '" + f.getPath()
					+ "'", e);
		} catch (IOException e) {
			throw new RuntimeException("Unable to read file '"
					+ f.getAbsolutePath() + "'", e);
		}
		sha1 = summer.getHexEncodedSHA1();
		md5 = summer.getHexEncodedMD5();
        baseURI = f.toURI();
	}

	/**
	 * Set the LASTMODDATE of the METS document to the current date time This
	 * should be called after any changes to the METS file
	 */
	protected void setLastModifiedToCurrent() {
		if (metsDoc.getMets().isSetMetsHdr()) {
			metsDoc.getMets().getMetsHdr().setLASTMODDATE(
					Calendar.getInstance());
		}
	}

	/**
	 * Add an arbitrary XmlObject as a child of a xmlData element
	 * 
	 * @param xml
	 *            the XmlData object to add the XmlObject to
	 * @param obj
	 *            the XmlObject to add, this must be a Document node
	 * @return returns the xml Node object for the newly added XmlObject
	 */
	protected Node setXmlDataAny(MetsMdSecType.MdWrap.XmlData xml, XmlObject obj) {
		Node nx = xml.getDomNode();
		Node np = obj.getDomNode();
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
}
