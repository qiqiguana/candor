/**
 * TestHaSMETSWebProfile.java
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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.zip.ZipFile;

import org.junit.Test;

import gov.loc.mets.MetsDivType;
import gov.loc.mets.MetsFileType;
import gov.loc.mets.MetsMdSecType;
import gov.loc.mets.MetsStructMapType;
	
/**
 * JUnit tests for the HasMETSWebProfile class
 * 
 * @author thabing
 *
 */
public class TestHaSMETSWebProfile {
		static String testURL = "http://dli.grainger.uiuc.edu/echodep/METS/JUnit/p2a1.xml";
		static String testURL1 = "http://dli.grainger.uiuc.edu/echodep/METS/JUnit/p2a1-1.xml";
		static String testURL2 = "http://dli.grainger.uiuc.edu/echodep/METS/JUnit/00000015.xml";
		static String testURL3 = "http://dli.grainger.uiuc.edu/echodep/METS/JUnit/00000016.xml";
		static String testURL4 = "http://dli.grainger.uiuc.edu/echodep/METS/JUnit/00000016-2.xml";
		static String testURL5 = "http://dli.grainger.uiuc.edu/echodep/METS/JUnit/p1a1.xml";
		static String testPath = "testfiles/METSProfile.xml";
		
		static String testZip4  = "testfiles/HS208291564ocl.ready.zip";
		static String testZip6  = "testfiles/HS993207292ocl.ready.zip";
		
		static String testNeg1  = "testfiles/NegativeTestCases/ChecksumMismatch/HS499272100ocl/echodepmets.xml";
		
		static String wawURL1 = "http://dli.grainger.uiuc.edu/echodep/hnS/WAWSamples/HS993207292ocl/echodepmets.xml";
		static String wawPath1 = "r:/inetpub/echodep/hnS/WAWSamples/HS239551045ocl/echodepmets.xml";

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

		// Unit Testing Functions below here

		/**
		 * Function main used for unit testing only
		 * 
		 * @param args
		 *          command line params the url to the METS file to load
		 */
		public static void main(String[] args) {
			org.junit.runner.JUnitCore
					.main("edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSWebProfile");
		}

		@Test
		public void textNegative(){
			HaSMETSProfile hmp = null;
			hmp = this.loadMETS(testNeg1);
			
			HaSMETSAppender app = new HaSMETSAppender("Validation");
			boolean valid=hmp.validateProfile(HaSMETSProfile.PackageType.SIP,app);
			if(app.hasEvents()){
				System.out.println("VALIDATION EVENTS:");
				System.out.println(app.getAllEventsAsXmlText());
			}
			assertFalse(valid);
		}
		
		@Test
		public void testEmptyConstructor() {
			HaSMETSWebProfile hmp = null;
			hmp = HaSMETSProfileFactory.newHaSMETSWebProfile();
			assertTrue(hmp != null
					&& hmp.getMetsDocument().getMets().getDomNode().getLocalName()
							.compareTo("mets") == 0);
			System.out.println("URL: " + hmp.getBaseURI());

		
			boolean valid=hmp.validateProfile(HaSMETSProfile.PackageType.AIP);
			assertTrue(valid);
			
			MetsStructMapType smap=null;
			try {
				smap = hmp.getPrimaryStructMap();
			} catch (HaSMETSProfileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("Should not be here");
			}
			MetsDivType div = smap.getDiv();
			assertTrue(div.isSetTYPE() && 
					div.getTYPE().compareTo(HaSMETSWebProfile.WebCaptureDivision.WEB_CAPTURE.name())==0);
		}

		@Test
		public void testURLConstructor() {
			HaSMETSProfile hmp = null;
			hmp = loadMETS(testURL1);

			assertTrue(hmp != null
					&& hmp.getMetsDocument().getMets().getDomNode().getLocalName()
							.compareTo("mets") == 0);
			try {
				assertTrue(hmp.getBaseURI().compareTo(new URI(testURL1)) == 0);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			System.out.println("URL: " + hmp.getBaseURI());
			boolean valid=hmp.validateProfile(HaSMETSProfile.PackageType.SIP);
			assertTrue(valid);
		}
		
		@Test
		public void testSaveFile() {
			HaSMETSProfile hmp = null;
			hmp = loadMETS(testURL);
			
			MetsFileType ft[] = hmp.getFiles("APP2_FID7");
			
			assertTrue(ft.length==1);
			
			try {
				File tempFile = File.createTempFile("HaSTemp", ".jpg");
				tempFile.deleteOnExit();
				hmp.saveFile(ft[0], tempFile.getAbsolutePath());
				System.out.println("SAVED TO:" + tempFile.getAbsolutePath());
				
			} catch (IOException e) {
				e.printStackTrace();
				fail("You should not be here");
			} catch (HaSMETSProfileException e) {
				e.printStackTrace();
				fail("You should not be here");
			}
			
			//hmp.dumpArcFileRecordList();
		}
		
		@Test
		public void testWAW1(){
			HaSMETSProfile hmp = null;
			hmp = loadMETS(wawURL1);
			
			
			boolean valid=hmp.validateProfile(HaSMETSProfile.PackageType.SIP);
			assertTrue(valid);
			
			hmp.setPackageType(HaSMETSProfile.PackageType.SIP);
			try {
				hmp.setNewPrimaryID("http://example.org/test", HaSMETSProfile.PREMISIdentifierType.URL);
			} catch (HaSMETSProfileException e) {
				e.printStackTrace();
				fail("Should not be here either.");
			}
			
			assertTrue(hmp.getMetsDocument().getMets().getOBJID().compareTo("http://example.org/test")==0);
			
			MetsMdSecType pRep=null;
			try {
				pRep = hmp.getPrimaryRepresentation();
			} catch (HaSMETSProfileException e) {
				e.printStackTrace();
				fail("Should not be here neither.");
			}
			System.out.println(pRep.xmlText(HaSMETSProfile.defaultSaveOptions));
			
			
		}
		
		@Test
		public void testFactoryNewInstanceURL(){
			HaSMETSProfile hmp=null;
			try {
				hmp = HaSMETSProfileFactory.newInstance(new URL(testURL1));
			} catch (HaSMETSProfileException e) {
				e.printStackTrace();
				fail("What!!!");
			} catch (MalformedURLException e) {
				e.printStackTrace();
				fail("Oh man!!!");
			}
			
			assertTrue(hmp instanceof HaSMETSWebProfile);
			
			boolean valid=hmp.validateProfile(HaSMETSProfile.PackageType.SIP);
			assertTrue(valid);
		}

		@Test
		public void testFactoryNewInstanceZip(){
			HaSMETSProfile hmp4 = null;
			HaSMETSProfile hmp6 = null;
			try {

				hmp4 = HaSMETSProfileFactory.newInstance(new ZipFile(testZip4),true);
				
				MetsFileType.FLocat flocat[] = hmp4.getFLocat("./Object1/AzModel.pdf");
				assertTrue(flocat.length==1);

				hmp4.save("echodepmets_out4.xml");
				URI base4 = hmp4.getBaseURI();
				hmp4 = HaSMETSProfileFactory.newInstance("echodepmets_out4.xml");
				hmp4.setBaseURI(base4);
				


				hmp6 = HaSMETSProfileFactory.newInstance(new ZipFile(testZip6),true);
				hmp6.save("echodepmets_out6.xml");
				URI base6 = hmp6.getBaseURI();
				hmp6 = HaSMETSProfileFactory.newInstance("echodepmets_out6.xml");
				hmp6.setBaseURI(base6);

			} catch (IOException e) {
				e.printStackTrace();
				fail("Should not be here!");
			} catch (HaSMETSProfileException e) {
				e.printStackTrace();
				fail("Should not be here either.");
			}
			
			boolean valid=hmp4.validateProfile(HaSMETSProfile.PackageType.SIP);
			assertTrue(valid);
			
			valid=hmp6.validateProfile(HaSMETSProfile.PackageType.SIP);
			assertTrue(valid);
			}

		}


