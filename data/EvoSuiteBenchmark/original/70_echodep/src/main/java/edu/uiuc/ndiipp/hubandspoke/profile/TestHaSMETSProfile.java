/**
 * TestHaSMETSProfile.java
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.xmlbeans.XmlID;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.junit.Test;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;

import gov.loc.mets.MetsFileType;
import gov.loc.mets.MetsMdSecType;
import gov.loc.mets.MetsMetsDocument;
import gov.loc.mods.ModsModsDocument;
import gov.loc.premis.PremisAgentDocument;
import gov.loc.premis.PremisObjectDocument;
import gov.loc.vmd.VmdVIDEOMDDocument;
import gov.loc.vmd.VmdVideoType;

/**
 * JUnit tests for the HaSMETSProfile class
 * 
 * @author thabing
 * 
 */
public class TestHaSMETSProfile {
	static String modsTestURL = "http://dli.grainger.uiuc.edu/echodep/METS/JUnit/modstest.xml";
	static String testPath = "testfiles/METSProfile.xml";
	static String testURL = "http://dli.grainger.uiuc.edu/echodep/METS/JUnit/p2a2.xml";
	static String testURL2 = "http://dli.grainger.uiuc.edu/echodep/METS/JUnit/00000015.xml";
	static String testURL3 = "http://dli.grainger.uiuc.edu/echodep/METS/JUnit/00000016.xml";
	static String testURL4 = "http://dli.grainger.uiuc.edu/echodep/METS/JUnit/00000016-2.xml";

	/**
	 * Function main used for unit testing only
	 * 
	 * @param args
	 *            command line params the url to the METS file to load
	 */
	public static void main(String[] args) {
		org.junit.runner.JUnitCore
				.main("edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfile");
	}

	// Unit Testing Functions below here

	private HaSMETSProfile loadMETS(String url) {
		HaSMETSProfile hmp = null;
		if (url.startsWith("http")) {
			try {
				hmp = HaSMETSProfileFactory.newInstance(new URL(url));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (HaSMETSProfileException e) {
				e.printStackTrace();
			}
		} else {
			try {
				hmp = HaSMETSProfileFactory.newInstance(url);
			} catch (HaSMETSProfileException e) {
				e.printStackTrace();
			}
		}

		return hmp;
	}

	@Test
	public void testAddAlternateDmdSec() {
		HaSMETSProfile hmp = null;
		hmp = HaSMETSProfileFactory.newHaSMETSProfile();

		DOMImplementationRegistry domImplReg = null;
		try {
			domImplReg = DOMImplementationRegistry.newInstance();
		} catch (ClassCastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DOMImplementation domImpl = domImplReg.getDOMImplementation("");
		Document doc = domImpl.createDocument("http://example.org/test",
				"test", null);
		doc.getDocumentElement().appendChild(doc.createElement("test2"));

		MetsMdSecType dmdSec = hmp.addAlternateDmdSec(
				MetsMdSecType.MdWrap.MDTYPE.OTHER, "SomeOtherTypeOfMetadata",
				doc);

		PremisAgentDocument agents[] = { hmp.getDefaultHumanAgent(),
				hmp.getDefaultSoftwareAgent() };
		HaSMETSProfile.PREMISLinkingAgentRole roles[] = {
				HaSMETSProfile.PREMISLinkingAgentRole.EVENT_INITIATOR,
				HaSMETSProfile.PREMISLinkingAgentRole.SOFTWARE_USED };

		hmp.addEventToXmlObject(dmdSec, HaSMETSProfile.createBasicPremisEvent(
				HaSMETSProfile.PREMISEventType.METADATA_CREATION,
				"This is just a test"), agents, roles);

		System.out.println(hmp.getMetsDocument().xmlText(
				HaSMETSProfile.defaultSaveOptions));

		boolean valid = hmp.validateProfile(HaSMETSProfile.PackageType.SIP);

		assertTrue(valid);

	}

	@Test
	public void testAddFile() {
		HaSMETSProfile hmp = null;
		hmp = loadMETS(testPath);

		MetsFileType ft = null;
		try {
			ft = hmp.addFile(new File("testfiles/logging.xml"));
		} catch (HaSMETSProfileException e) {
			e.printStackTrace();
			fail("What the ...?");
		}

		System.out.println(hmp.getMetsDocument().xmlText(
				HaSMETSProfile.defaultSaveOptions));
	}

	@Test
	public void testAdditionOfProcessingInstruction() {
		HaSMETSProfile hmp = null;
		hmp = loadMETS(testURL);

		MetsMdSecType techmd = (MetsMdSecType) hmp
				.getXmlObject("APP3_TMD2PREMIS");

		PremisObjectDocument p = null;
		try {
			p = (PremisObjectDocument) hmp.getMDSecXmlObject(techmd);
		} catch (HaSMETSProfileException e) {
			e.printStackTrace();
			fail("You should not be here");
		}

		System.out.println(p.xmlText());
		assertTrue(p.getDomNode().getLastChild().getNodeType() == Node.PROCESSING_INSTRUCTION_NODE);

	}

	@Test
	public void testAddNewPrimaryRep() {
		HaSMETSProfile hmp = null;
		hmp = loadMETS(testURL);

		try {
			hmp.addNewPrimaryRepresentation(
					HaSMETSProfile.PREMISIdentifierType.URL,
					"http://example.org/test");
			fail("should not be here");
		} catch (HaSMETSProfileException e) {
			assertTrue(e.getMessage().indexOf(
					"already has a PRIMARY_REPRESENTATION") != -1);
		}

	}

	@Test
	public void testAddNewVmd() {
		HaSMETSProfile hmp = null;
		hmp = loadMETS(testPath);

		MetsFileType ft[] = hmp.getFiles("APP3_FID1");
		assertTrue(ft.length == 1);

		VmdVIDEOMDDocument vmdoc = VmdVIDEOMDDocument.Factory
				.newInstance(new XmlOptions().setLoadStripWhitespace());
		VmdVideoType vmd = vmdoc.addNewVIDEOMD();
		vmd.addNewFileData();
		vmd.addNewPhysicalData();

		MetsMdSecType techmd = null;
		try {
			techmd = hmp.addTechMDXmlObjectToFile(ft[0], vmdoc);
		} catch (HaSMETSProfileException e) {
			e.printStackTrace();
		}

		assertTrue(techmd != null
				&& techmd.getMdWrap().getXmlData().getDomNode().getFirstChild()
						.getLocalName().compareTo("VIDEOMD") == 0);

		assertTrue(ft[0].getADMID().contains(techmd.getID()));
		System.out.println(techmd.xmlText());
		System.out.println(ft[0].xmlText());

		assertTrue(techmd.getMdWrap().getMDTYPE().intValue() == MetsMdSecType.MdWrap.MDTYPE.INT_LC_AV);
		assertTrue(techmd.getMdWrap().getOTHERMDTYPE() == null
				|| techmd.getMdWrap().getOTHERMDTYPE().length() == 0);
	}

	@Test
	public void testAddPREMISObjectTechMDToFile() {
		HaSMETSProfile hmp = null;
		hmp = loadMETS(testURL);
		MetsFileType[] ft = hmp.getAllFiles();
		assertTrue(ft.length > 0);
		MetsMdSecType md = null;
		try {
			md = hmp.newPREMISObjectTechMD(ft[0],
					HaSMETSProfile.PREMISIdentifierType.LOCAL, "TESTID");
		} catch (HaSMETSProfileException e) {
			e.printStackTrace();
		}
		assertTrue(md != null
				&& md.isSetMdWrap()
				&& md.getMdWrap().isSetXmlData()
				&& md.getMdWrap().getXmlData().getDomNode().getFirstChild()
						.getLocalName().compareTo("object") == 0);
		// make sure the file now points to the new md
		assertTrue(ft[0].getADMID().contains(md.getID()));
	}

	@Test
	public void testAddTechMDXmlObjectToFile() {
		HaSMETSProfile hmp = null;
		hmp = loadMETS(testURL);
		MetsFileType[] ft = hmp.getAllFiles();
		assertTrue(ft.length > 0);
		MetsMdSecType md = null;

		PremisObjectDocument pobj = PremisObjectDocument.Factory.newInstance();
		PremisObjectDocument.Object obj = pobj.addNewObject();
		PremisObjectDocument.Object.ObjectIdentifier oid = obj
				.addNewObjectIdentifier();
		oid.setObjectIdentifierType(HaSMETSProfile.PREMISIdentifierType.URL
				.name());
		oid.setObjectIdentifierValue("TEST");

		try {
			md = hmp.addTechMDXmlObjectToFile(ft[0], pobj);
		} catch (HaSMETSProfileException e1) {
			e1.printStackTrace();
		}

		assertTrue(md != null
				&& md.isSetMdWrap()
				&& md.getMdWrap().isSetXmlData()
				&& md.getMdWrap().getXmlData().getDomNode().getFirstChild()
						.getLocalName().compareTo("object") == 0);

		// make sure the file now points to the new md
		assertTrue(ft[0].getADMID().contains(md.getID()));

		PremisObjectDocument newPObj = null;
		try {
			newPObj = (PremisObjectDocument) hmp.getMDSecXmlObject(md);
		} catch (HaSMETSProfileException e) {
			e.printStackTrace();
		}

		assertTrue(newPObj != null
				&& newPObj.getObject().getObjectIdentifierArray()[0]
						.getObjectIdentifierValue().compareTo("TEST") == 0);
	}

	@Test
	public void testAddTechMDXmlObjectToFileWhenFileHasNoADMID() {
		HaSMETSProfile hmp = null;
		hmp = loadMETS(testURL);

		// add a new file element w/o an ADMID attr.
		MetsMetsDocument mdoc = hmp.getMetsDocument();
		MetsFileType mf = mdoc.getMets().getFileSec().getFileGrpArray()[0]
				.addNewFile();
		MetsFileType.FLocat floc = mf.addNewFLocat();
		floc.setHref("TEST");
		XmlID id = hmp.getNewID("ID", mf);

		assertTrue(mf.getID().compareTo(id.getStringValue()) == 0);

		assertFalse(mf.isSetADMID());

		PremisObjectDocument pdoc = PremisObjectDocument.Factory.newInstance();
		PremisObjectDocument.Object pobj = pdoc.addNewObject();
		PremisObjectDocument.Object.ObjectIdentifier pid = pobj
				.addNewObjectIdentifier();
		pid.setObjectIdentifierType("URI");
		pid.setObjectIdentifierValue("http://www.uiuc.edu/TEST");

		MetsMdSecType techmd = null;
		try {
			techmd = hmp.addTechMDXmlObjectToFile(mf, pdoc);
		} catch (HaSMETSProfileException e) {
			e.printStackTrace();
		}

		assertTrue(techmd != null && mf.isSetADMID()
				&& mf.getADMID().contains(techmd.getID()));

		System.out.println(techmd.xmlText());
		System.out.println(mf.xmlText());
	}

	@Test
	public void testConvertPrimaryDMDToAIP() {
		HaSMETSProfile hmp = null;
		hmp = loadMETS(modsTestURL);

		try {
			hmp.convertPrimaryDmdSecToAquifer();
		} catch (HaSMETSProfileException e) {
			e.printStackTrace();
			fail("What's going on!");
		}

		hmp.save("converttest.xml");
	}

	@Test
	public void testEmptyConstructor() {
		HaSMETSProfile hmp = null;
		hmp = HaSMETSProfileFactory.newHaSMETSProfile();
		assertTrue(hmp != null
				&& hmp.getMetsDocument().getMets().getDomNode().getLocalName()
						.compareTo("mets") == 0);
		System.out.println("URL: " + hmp.getBaseURI());

		System.out.println(hmp.getMetsDocument().xmlText(
				HaSMETSProfile.defaultSaveOptions));

		boolean valid = hmp.validateProfile(HaSMETSProfile.PackageType.AIP);
		assertTrue(valid);

		hmp = HaSMETSProfileFactory
				.newHaSMETSProfile(HaSMETSProfile.PackageType.SIP);
		assertTrue(hmp != null
				&& hmp.getMetsDocument().getMets().getDomNode().getLocalName()
						.compareTo("mets") == 0);
		System.out.println("URL: " + hmp.getBaseURI());

		System.out.println(hmp.getMetsDocument().xmlText(
				HaSMETSProfile.defaultSaveOptions));

		valid = hmp.validateProfile(HaSMETSProfile.PackageType.AIP);
		assertTrue(valid);
	}

	@Test
	public void testGetAllFiles() {
		HaSMETSProfile hmp = null;
		hmp = loadMETS(testURL);

		MetsFileType ft[] = hmp.getAllFiles();
		assertTrue(ft != null && ft.length > 0);
	}

	@Test
	public void testGetFileInputStream() {
		HaSMETSProfile hmp = null;
		hmp = loadMETS(testURL);

		MetsFileType files[] = hmp.getFiles("APP3_FID1");

		assertTrue(files.length == 1);

		InputStream s = null;
		try {
			s = hmp.getFileInputStream(files[0]);
		} catch (HaSMETSProfileException e) {
			e.printStackTrace();
			fail("You should not be here.");
		}

		InputStreamReader isr = new InputStreamReader(s);
		StringBuffer sb = new StringBuffer();
		int c;
		try {
			while ((c = isr.read()) != -1) {
				sb.appendCodePoint(c);
			}
			isr.close();
		} catch (IOException e) {
			e.printStackTrace();
			fail("Should not be here.");
		}

		System.out.println(sb);
		assertTrue(sb.toString().compareTo(
				"<r xmlns='http://example.org/'>THIS IS A TEST</r>") == 0);

		MetsFileType filesa[] = hmp.getFiles("APP3_FID1a");

		assertTrue(filesa.length == 1);

		InputStream sa = null;
		try {
			sa = hmp.getFileInputStream(filesa[0]);
		} catch (HaSMETSProfileException e) {
			e.printStackTrace();
			fail("You should not be here.");
		}

		InputStreamReader isra = new InputStreamReader(sa);
		StringBuffer sba = new StringBuffer();
		int ca;
		try {
			while ((ca = isra.read()) != -1) {
				sba.appendCodePoint(ca);
			}
			isra.close();
		} catch (IOException e) {
			e.printStackTrace();
			fail("Should not be here.");
		}

		System.out.println(sba);
		assertTrue(sba.toString().compareTo(
				"<r xmlns='http://example.org/'>THIS IS A TEST</r>") == 0);

		MetsFileType filesb[] = hmp.getFiles("APP3_FID1b");

		assertTrue(filesb.length == 1);

		InputStream sbb = null;
		try {
			sbb = hmp.getFileInputStream(filesb[0]);
		} catch (HaSMETSProfileException e) {
			e.printStackTrace();
			fail("You should not be here.");
		}

		InputStreamReader isrb = new InputStreamReader(sbb);
		StringBuffer sbbb = new StringBuffer();
		int cbb;
		try {
			while ((cbb = isrb.read()) != -1) {
				sbbb.appendCodePoint(cbb);
			}
			isrb.close();
		} catch (IOException e) {
			e.printStackTrace();
			fail("Should not be here.");
		}

		System.out.println(sbbb);
		assertTrue(sbbb.toString().startsWith("<r"));
	}

	@Test
	public void testGetModifySetPREMISObject() {
		HaSMETSProfile hmp = null;
		hmp = loadMETS(testURL);

		String xql = "declare namespace p='http://www.loc.gov/standards/premis/v1';declare namespace m='http://www.loc.gov/METS/'; .//m:techMD[m:mdWrap/m:xmlData/p:object]";
		XmlObject objs[] = hmp.getMetsDocument().selectPath(xql);
		MetsMdSecType techMD = null;
		if (objs.length > 0) {
			techMD = (MetsMdSecType) objs[0];
		}
		String id = techMD.getID();

		assertTrue(techMD != null);

		// Get the PREMIS object
		PremisObjectDocument obj = null;
		try {
			obj = (PremisObjectDocument) hmp.getMDSecXmlObject(techMD);
		} catch (HaSMETSProfileException e) {
			e.printStackTrace();
		}
		assert (obj.getDomNode().getLocalName().compareTo("object") == 0);

		// Modify the PREMIS object
		obj.getObject().setXmlID("TESTID");

		// Set the PREMIS object
		try {
			hmp.setMDSecXmlObject(techMD, obj);
		} catch (HaSMETSProfileException e) {
			e.printStackTrace();
		}

		assertTrue(((MetsMdSecType) hmp.getXmlObject(id)).getMdWrap()
				.getXmlData().getDomNode().getFirstChild().getAttributes()
				.getNamedItem("xmlID").getNodeValue().compareTo("TESTID") == 0);

	}

	@Test
	public void testGetPremisAndSetToWrongTechMD() {
		// this will fail unless an error is thrown
		HaSMETSProfile hmp = null;
		hmp = loadMETS(testURL);

		MetsMdSecType md = (MetsMdSecType) hmp.getXmlObject("APP3_TMD1PREMIS");
		MetsMdSecType md2 = (MetsMdSecType) hmp
				.getXmlObject("APP3_EXTERNAL_OTHER");

		assertTrue(md.getMdWrap().getMDTYPE() == MetsMdSecType.MdWrap.MDTYPE.PREMIS);
		assertTrue(md2.getMdRef().getMDTYPE() == MetsMdSecType.MdRef.MDTYPE.LC_AV);

		PremisObjectDocument pObjDoc = null;
		try {
			pObjDoc = (PremisObjectDocument) hmp.getMDSecXmlObject(md);
		} catch (HaSMETSProfileException e) {
			e.printStackTrace();
		}

		try {
			hmp.setMDSecXmlObject(md2, pObjDoc);
			fail("This should have thrown exception because of wrong destination for XmlObject");
		} catch (HaSMETSProfileException e) {
			assertTrue(e
					.getMessage()
					.compareTo(
							"This xobj may only be set on the techmd element from which it came or on an empty techmd element.") == 0);
		}
	}

	@Test
	public void testGetPrimaryDmdSec() {
		HaSMETSProfile hmp = null;
		hmp = loadMETS(testURL);

		MetsMdSecType dmd = null;
		try {
			dmd = hmp.getPrimaryDmdSec();
		} catch (HaSMETSProfileException e) {
			fail("You should not be here.");
			e.printStackTrace();
		}

		assertTrue(dmd != null && dmd.getID().compareTo("APP3_DM3") == 0);
	}

	@Test
	public void testGetPrimaryDmdSecFromExamples() {
		HaSMETSProfile hmp = null;
		hmp = loadMETS(testURL2);

		MetsMdSecType dmdSec = null;
		ModsModsDocument modsDoc = null;
		try {
			dmdSec = hmp.getPrimaryDmdSec();
			modsDoc = (ModsModsDocument) hmp.getMDSecXmlObject(dmdSec);
		} catch (HaSMETSProfileException e) {
			e.printStackTrace();
		}

		assertTrue(dmdSec.getID().compareTo("APP1_DM2") == 0);
		assertTrue(modsDoc.getMods().getTitleInfoArray(0).getTitleArray(0)
				.compareTo("Peoria County, Illinois") == 0);

		hmp = loadMETS(testURL3);

		try {
			dmdSec = hmp.getPrimaryDmdSec();
			modsDoc = (ModsModsDocument) hmp.getMDSecXmlObject(dmdSec);
		} catch (HaSMETSProfileException e) {
			e.printStackTrace();
		}

		assertTrue(dmdSec.getID().compareTo("APP2_DM1") == 0);
		assertTrue(modsDoc
				.getMods()
				.getTitleInfoArray(0)
				.getTitleArray(0)
				.compareTo(
						"The Richmond National Parks quarterly newsletter : Richmond\n\t\t\t\t\t\t\t\t\tNational Battlefield Park, Maggie L. Walker National Historic\n\t\t\t\t\t\t\t\t\tSite") == 0);

		hmp = loadMETS(testURL4);

		try {
			dmdSec = hmp.getPrimaryDmdSec();
			modsDoc = (ModsModsDocument) hmp.getMDSecXmlObject(dmdSec);
		} catch (HaSMETSProfileException e) {
			e.printStackTrace();
		}

		assertTrue(dmdSec.getID().compareTo("APP3_DM3") == 0);
		assertTrue(modsDoc.getMods().getNameArray(0).getNamePartArray(0)
				.getStringValue().compareTo("National Park Service") == 0);

	}

	@Test
	public void testGetTechMDXmlObjectWithMdRef() {
		HaSMETSProfile hmp = null;
		hmp = loadMETS(testURL);

		MetsMdSecType techmd = (MetsMdSecType) hmp
				.getXmlObject("APP3_EXTERNAL");

		assertTrue(techmd != null
				&& techmd.getID().compareTo("APP3_EXTERNAL") == 0);

		PremisObjectDocument pobj = null;
		try {
			pobj = (PremisObjectDocument) hmp.getMDSecXmlObject(techmd);
		} catch (HaSMETSProfileException e) {
			e.printStackTrace();
		}

		assertTrue(pobj != null);
		assertTrue(pobj.getObject().getObjectIdentifierArray(0)
				.getObjectIdentifierValue().compareTo(
						"http://www.nps.gov/rich/fall99.html") == 0);

		// test a case where the referenced xml is not a know type
		MetsMdSecType techmd2 = (MetsMdSecType) hmp
				.getXmlObject("APP3_EXTERNAL_JUNK");

		assertTrue(techmd2 != null
				&& techmd2.getID().compareTo("APP3_EXTERNAL_JUNK") == 0);

		try {
			PremisObjectDocument pobj2 = (PremisObjectDocument) hmp
					.getMDSecXmlObject(techmd2);
			System.out.println(pobj2.toString());
			fail("Should have thrown an exception because of unknown metadata type");
		} catch (HaSMETSProfileException e) {
			assertTrue(e.getMessage().contains(
					"is not recognized by the profile"));
		}

	}

	@Test
	public void testGetTechMDXmlObjectWithMdWrap() {
		HaSMETSProfile hmp = null;
		hmp = loadMETS(testURL);

		MetsMdSecType techmd = (MetsMdSecType) hmp
				.getXmlObject("APP3_TMD2PREMIS");

		assertTrue(techmd != null
				&& techmd.getID().compareTo("APP3_TMD2PREMIS") == 0);

		PremisObjectDocument pobj = null;
		try {
			pobj = (PremisObjectDocument) hmp.getMDSecXmlObject(techmd);
		} catch (HaSMETSProfileException e) {
			e.printStackTrace();
		}

		assertTrue(pobj != null);
		assertTrue(pobj.getObject().getObjectIdentifierArray(0)
				.getObjectIdentifierValue().compareTo(
						"http://www.nps.gov/rich/fall99p2.html") == 0);
	}

	@Test
	public void testLocalModify() {
		HaSMETSProfile hmp = null;
		hmp = loadMETS(testURL);

		MetsMdSecType techmd = (MetsMdSecType) hmp
				.getXmlObject("APP3_TMD2PREMIS");

		PremisObjectDocument p = null;
		try {
			p = (PremisObjectDocument) hmp.getMDSecXmlObject(techmd);
		} catch (HaSMETSProfileException e) {
			e.printStackTrace();
			fail("You should not be here");
		}

		p.getObject().setPreservationLevel("XXXYYYZZZ");

		// see if the update was live or not

		MetsMetsDocument m = hmp.getMetsDocument();

		XmlObject xs[] = null;

		String xql = "declare namespace p='http://www.loc.gov/standards/premis/v1'; .//p:preservationLevel[contains(.,'XXXYYYZZZ')]";

		xs = m.selectPath(xql);

		assertTrue(xs.length == 0);

		try {
			hmp.setMDSecXmlObject(techmd, p);
		} catch (HaSMETSProfileException e) {
			e.printStackTrace();
			fail("You should not be here.");
		}

		xs = m.selectPath(xql);

		assertTrue(xs.length == 1);

	}

	@Test
	public void testPathConstructor() {
		HaSMETSProfile hmp = null;
		hmp = loadMETS(testPath);

		assertTrue(hmp != null
				&& hmp.getMetsDocument().getMets().getDomNode().getLocalName()
						.compareTo("mets") == 0);
		System.out.println("PATH: " + hmp.getBaseURI());
	}

	@Test
	public void testResolveFileURI() {
		HaSMETSProfile hmp = null;
		hmp = loadMETS(testPath);

		MetsFileType ft[] = hmp.getFiles("APP3_FID1");
		assertTrue(ft != null && ft.length == 1);

		MetsFileType.FLocat fl[] = ft[0].getFLocatArray();
		assertTrue(fl != null && fl.length == 1);

		String href = fl[0].getHref();
		assertTrue(href.compareTo("./fall99.html") == 0);

		URI uri = hmp.resolveURIAgainstBaseURI(href);
		assertTrue(uri != null && uri.toString().endsWith("fall99.html"));
		System.out.println("URI: " + uri.toString());
	}

	@Test
	public void testResolveHTTPURI() {
		HaSMETSProfile hmp = null;
		hmp = loadMETS(testURL);

		MetsFileType ft[] = hmp.getAllFiles();
		assertTrue(ft != null && ft.length > 0);

		MetsFileType.FLocat fl[] = ft[0].getFLocatArray();
		assertTrue(fl != null && fl.length == 1);

		String href = fl[0].getHref();

		URI uri = hmp.resolveURIAgainstBaseURI(href);
		assertTrue(uri != null && uri.toString().startsWith("http"));
		System.out.println("URI: " + uri.toString());
	}

	@Test
	public void testSetNewPrimaryID() {

		String newID = "123456.99";
		HaSMETSProfile.PREMISIdentifierType newType = HaSMETSProfile.PREMISIdentifierType.HANDLE;
		HaSMETSProfile hmp = null;
		hmp = loadMETS(testURL);

		try {
			hmp.setNewPrimaryID(newID, newType);
		} catch (HaSMETSProfileException e) {
			e.printStackTrace();
			fail("Should not be here");
		}

		boolean valid = hmp.validateProfile(HaSMETSProfile.PackageType.AIP);

		assertTrue(valid);
		assertTrue(hmp.getMetsDocument().getMets().getOBJID().compareTo(newID) == 0);

	}

	@Test
	public void testSetNewPrimaryIDToSame() {

		String newID = "2135.85756";
		HaSMETSProfile.PREMISIdentifierType newType = HaSMETSProfile.PREMISIdentifierType.HANDLE;
		HaSMETSProfile hmp = null;
		hmp = loadMETS(testURL);

		try {
			hmp.setNewPrimaryID(newID, newType);
		} catch (HaSMETSProfileException e) {
			e.printStackTrace();
			fail("Should not be here");
		}

		boolean valid = hmp.validateProfile(HaSMETSProfile.PackageType.AIP);

		assertTrue(valid);
		assertTrue(hmp.getMetsDocument().getMets().getOBJID().compareTo(newID) == 0);

	}

	@Test
	public void testURLConstructor() {
		HaSMETSProfile hmp = null;
		hmp = loadMETS(testURL);

		assertTrue(hmp != null
				&& hmp.getMetsDocument().getMets().getDomNode().getLocalName()
						.compareTo("mets") == 0);
		try {
			assertTrue(hmp.getBaseURI().compareTo(new URI(testURL)) == 0);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		System.out.println("URL: " + hmp.getBaseURI());
	}

	@Test
	public void testValidateProfileWithValidDocument() {
		HaSMETSProfile hmp = null;
		hmp = loadMETS(testURL);

		boolean valid = hmp.validateProfile(HaSMETSProfile.PackageType.AIP);

		assertTrue(valid);

	}

}
