/**
 * HaSMasterMETSProfileFactory.java
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;

import edu.uiuc.ndiipp.hubandspoke.packager.DirectoryPackager;
import edu.uiuc.ndiipp.hubandspoke.packager.PackagerException;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSChecksummer;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSConstants;

import gov.loc.mets.MetsFileType;
import gov.loc.mets.MetsMdSecType;
import gov.loc.mets.MetsMetsDocument;
import gov.loc.premis.PremisAgentDocument;
import gov.loc.premis.PremisEventDocument;

/**
 * 
 * @author Bill Ingram
 * @author thabing
 *
 */
public class HaSMasterMETSProfileFactory {
	static Logger logger = Logger.getLogger(HaSMETSProfileFactory.class);

	/**
	 * Create a new instance of a HaSMasterMETSProfile object
	 * 
	 * @return a new HaSMasterMETSProfile instance
	 */
	protected static HaSMasterMETSProfile newHaSMasterMETSProfile() {

		HaSMasterMETSProfile ret = new HaSMasterMETSProfile();
		return ret;
	}

	public static HaSMasterMETSProfile newInstance() {
		return newHaSMasterMETSProfile();
	}

	// public static HaSMasterMETSProfile newInstance(HaSMETSProfile mets)
	// throws HaSMETSProfileException {
	// HaSMasterMETSProfile ret = newHaSMasterMETSProfile();
	// ret.addMptr(mets);
	// return ret;
	// }

	/**
	 * Create a new HaSMasterMETSProfile object from the XML file.
	 * 
	 * @param f
	 *            the file containing XML METS file to load
	 */
	public static HaSMasterMETSProfile newInstance(File f)
			throws HaSMETSProfileException {
		URI baseURI = null;
		MetsMetsDocument metsDoc = null;
		HaSMETSProfile ret = null;
		try {
			baseURI = f.toURI();
			metsDoc = MetsMetsDocument.Factory.parse(baseURI.toURL(),
					HaSMETSProfile.defaultLoadOptions);
		} catch (XmlException e) {
			throw new HaSMETSProfileException("Invalid XML from this file '"
					+ f.getAbsolutePath() + "'", e);
		} catch (IOException e) {
			throw new HaSMETSProfileException("Unable to access file '"
					+ f.getAbsolutePath() + "'", e);
		}
		return new HaSMasterMETSProfile(metsDoc, baseURI);

	}

	public static HaSMasterMETSProfile newInstance(HaSMETSProfile echodepmets)
			throws HaSMETSProfileException {
		HaSMasterMETSProfile mm = new HaSMasterMETSProfile();
		mm.addMptr(echodepmets.getBaseURI());

		return mm;
	}
	
	/**
	 * Creates a new HaSMasterMETSProfile object from the files contained in the 
	 * directory at the given path. 
	 * 
	 * @param path -- path to a directory
	 * 
	 * @return -- a new HaSMasterMETSProfile object if one can be created from the 
	 * 			  directory of files; otherwise, return null.
	 */
	public static HaSMasterMETSProfile newInstance(String path){
		File dir = new File(path);
		if(dir.exists() && dir.isDirectory()){
			try {
				return DirectoryPackager.createHubPackage(dir, HaSMETSProfile.PackageType.SIP);
			} catch (PackagerException e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * Create a new HaSMasterMETSProfile object from the files contained in the
	 * given zip file. The zip file is unzipped into the same directory as the
	 * zip file itself. If there already files in this directory they will not
	 * be overwritten, but an error will result. The class returned depends on
	 * the PROFILE attribute of the METS file. There is assumed to be a file in
	 * the root directory of the zip file called mastermets.xml, or
	 * echodepmets.xml. If mastermets.xml is found, it is valided to ensure all
	 * of its mptrs resolve. If mastermets.xml cannot be found, this method
	 * looks for echodepmets.xml or just mets.xml, and creates a Hub Package
	 * with a mastermets.xml for the package. The method then looks for
	 * filenames that would be duplicates on a system which treats filenames as
	 * case-insensitive (Windows), and it will rename these files as
	 * appropriate. Note that directory names are not renamed, so for example if
	 * there are files Test/file.txt and test/file.txt they will end up in the
	 * same directory, but with different file names, like Test/file.txt and
	 * Test/file_1.txt
	 * 
	 * @param zip
	 * @param overWrite
	 * @param pt
	 * @throws HaSMasterMETSProfileException
	 */
	public static HaSMasterMETSProfile newInstance(ZipFile zip, File tempdir,
			boolean overWrite) throws HaSMETSProfileException {
		/**
		 * Inner class for keeping track of the files in the zip
		 */
		class ZIPFileList {
			Hashtable<String, File> files = new Hashtable<String, File>();
			Hashtable<String, File> renamedFiles = new Hashtable<String, File>();
			Hashtable<String, String> sha1 = new Hashtable<String, String>();

			File getMasterMetsFile() {
				String metsPath = null;
				for (Enumeration<String> en = files.keys(); en
						.hasMoreElements();) {
					String path = en.nextElement();
					if (path.toLowerCase().endsWith(
							HaSConstants.MASTER_METS_FILE_NAME)) {
						if (metsPath == null
								|| path.length() < metsPath.length()) {
							metsPath = path;
						}
					}
				}

				if (metsPath != null)
					return files.get(metsPath);
				else
					return null;
			}

			File getOldMetsFile() {
				String metsPath = null;
				for (Enumeration<String> en = files.keys(); en
						.hasMoreElements();) {
					String path = en.nextElement();
					if (path.toLowerCase().endsWith(
							HaSConstants.METS_FILE_NAME)) {
						if (metsPath == null
								|| path.length() < metsPath.length()) {
							metsPath = path;
						}
					}
				}
				if (metsPath == null) {
					for (Enumeration<String> en = files.keys(); en
							.hasMoreElements();) {
						String path = en.nextElement();
						if (path.toLowerCase().endsWith("mets.xml")) {
							if (metsPath == null
									|| path.length() < metsPath.length()) {
								metsPath = path;
							}
						}
					}
				}

				if (metsPath != null)
					return files.get(metsPath);
				else
					return null;
			}

			void deleteFiles() {
				for (Enumeration<File> en = files.elements(); en
						.hasMoreElements();) {
					File file = en.nextElement();
					if (file.exists() && file.isFile()) {
						file.delete();
					}
				}

			}
		}

		ZIPFileList zz = new ZIPFileList();

//		File zipfolder = new File(zip.getName()).getParentFile()
//				.getAbsoluteFile();
		
		for (Enumeration<? extends ZipEntry> ents = zip.entries(); ents
				.hasMoreElements();) {
			ZipEntry zent = ents.nextElement();

			if (!zent.isDirectory()) {
				// create unique lowercase names for the files to prevent
				// collisions on case-insensitive systems
				File zentPath = new File(tempdir, zent.getName());
				String origName = zentPath.getName();
				String pre = "";
				String ext = "";
				int i = origName.lastIndexOf(".");
				if (i == -1) {
					pre = origName;
					ext = "";
				} else {
					pre = origName.substring(0, i);
					ext = origName.substring(i);
				}
				boolean renamed = false;
				int cnt = 1;
				while (zz.files.contains(zentPath)) {
					String fname = pre + "__" + String.valueOf(cnt++) + ext;
					zentPath = new File(zentPath.getParentFile(), fname);
					renamed = true;
				}
				if (renamed) {
					zz.renamedFiles.put(zent.getName(), zentPath);
				}
				zz.files.put(zent.getName(), zentPath);

				if (zentPath.getParentFile().exists()
						|| zentPath.getParentFile().mkdirs()) {
					boolean ok = false;
					try {
						ok = zentPath.createNewFile();
					} catch (IOException e) {
						zz.deleteFiles();
						throw new HaSMETSProfileException(
								"Unable to create file '"
										+ zentPath.getAbsolutePath() + "'", e);
					}
					if (!ok && !overWrite) {
						zz.deleteFiles();
						throw new HaSMETSProfileException(
								"The file already exists '"
										+ zentPath.getAbsolutePath()
										+ "' and will not be overwritten");
					} else {
						long actualSize = 0;
						long actualCRC = 0;

						long reportedCRC = zent.getCrc();
						long reportedSize = zent.getSize();
						HaSChecksummer summer;
						try {
							InputStream inpStrm = zip.getInputStream(zent);
							summer = new HaSChecksummer();
							CheckedInputStream chkStrm = new CheckedInputStream(
									inpStrm, summer);
							FileOutputStream outfile = new FileOutputStream(
									zentPath);
							int b;
							while ((b = chkStrm.read()) != -1) {
								outfile.write(b);
								actualSize++;
							}
							chkStrm.close();
							outfile.close();
						} catch (IOException e) {
							zz.deleteFiles();
							throw new HaSMETSProfileException(
									"Unable to access zip entry '"
											+ zent.getName() + "'", e);
						}
						zentPath.setLastModified(zent.getTime());
						actualCRC = summer.getLongCRC32();
						zz.sha1.put(zent.getName(), summer.getHexEncodedSHA1());
						if (reportedCRC != actualCRC) {
							zz.deleteFiles();
							throw new HaSMETSProfileException(
									"The reported CRC does not match the actual CRC of the file '"
											+ zentPath.getAbsolutePath() + "'");
						}
						if (reportedSize != actualSize) {
							zz.deleteFiles();
							throw new HaSMETSProfileException(
									"The reported size does not match the actual size of the file '"
											+ zentPath.getAbsolutePath() + "'");
						}
					}
				} else {
					zz.deleteFiles();
					throw new HaSMETSProfileException(
							"Unable to create directories '"
									+ zentPath.getParentFile()
											.getAbsolutePath() + "'");
				}
			}
		}

		File mastermetsfile = zz.getMasterMetsFile();

		HaSMasterMETSProfile mastermets = null;
		HaSMETSProfile mets = null;

		if (mastermetsfile == null) {
			File metsfile = zz.getOldMetsFile();
			if (metsfile == null) {
				zz.deleteFiles();
				// throw new HaSMETSProfileException(
				// "The Zip file does not contain an 'echodepmets.xml' or 'mets.xml' file"
				// );
				throw new HaSMETSProfileException(
						"The Zip file does not contain a valid METS file");
			} else {
				try {
					//mets = HaSMETSProfileFactory.newInstance(metsfile);
					mastermets = DirectoryPackager.createHubPackage(new File(
							metsfile.getParent()),
							HaSMETSProfile.PackageType.SIP);
					mets = mastermets.getMostRecentEchoDepMETS();
					if (!metsfile.delete()) {
						//logger.warn("Could not delete old echodep mets.");
					}
				} catch (PackagerException e) {
					// e.printStackTrace();
					throw new HaSMETSProfileException(
							"Error creating Hub package for: "
									+ metsfile.getParent());
				}
				// mets=HaSMETSProfileFactory.newInstance(metsfile,pt);
			}
		} else {
			mastermets = HaSMasterMETSProfileFactory
					.newInstance(mastermetsfile);
			mets = mastermets.getMostRecentEchoDepMETS();
		}

		// add a premis object techMD to all files, if needed
		mets.addNewPREMISObjectTechMDToAllFile();

		// if the file element has a checksum validate it against the calculated
		// one;
		// else add the calculated one to the file
		for (Enumeration<String> en = zz.sha1.keys(); en.hasMoreElements();) {
			String key = en.nextElement();
			String sha1 = zz.sha1.get(key);
			File url = new File(tempdir, key);
			MetsFileType.FLocat fl[] = mets.getFLocat(url.toURI()
					.toASCIIString());
			for (int i = 0; i < fl.length; i++) {
				XmlCursor xcur = fl[i].newCursor();
				xcur.toParent();
				MetsFileType ft = (MetsFileType) xcur.getObject();
				if (ft.isSetCHECKSUM()
						&& ft.isSetCHECKSUMTYPE()
						&& ft.getCHECKSUMTYPE().equals(
								MetsFileType.CHECKSUMTYPE.SHA_1)) {
					// compare checksums
					if (sha1.compareToIgnoreCase(ft.getCHECKSUM()) != 0) {
						logger
								.warn("The CHECKSUM attribute of the file '"
										+ key
										+ "' does not match the actual checksum.");
						// throw new HaSMETSProfileException(
						// "The CHECKSUM attribute of the file '" + key +
						// "' does not match the actual checksum.");
					}
				} else {
					mets.addNewChecksum(ft, MetsFileType.CHECKSUMTYPE.SHA_1,
							sha1);
				}
			}
		}

		// if any files needed to be renamed modify the mets file accordingly
		for (Enumeration<String> en = zz.renamedFiles.keys(); en
				.hasMoreElements();) {
			String key = en.nextElement();
			File url = new File(tempdir, key);
			MetsFileType.FLocat fl[] = mets.getFLocat(url.toURI()
					.toASCIIString());
			URI metsBaseURI = mets.getBaseURIMinusFile();
			URI renamedURI = zz.renamedFiles.get(key).toURI();
			URI newURI = metsBaseURI.relativize(renamedURI);
			// TODO: replace the existing URL with the new one and add some
			// techmd
			if (fl.length > 0) {
				for (int i = 0; i < fl.length; i++) {
					XmlCursor xcur = fl[i].newCursor();
					xcur.toParent();
					MetsFileType ft = (MetsFileType) xcur.getObject();
					String origName = fl[i].getHref();
					fl[i].setHref("./" + newURI.toASCIIString());

					mets
							.addPremisObjectIdentifierToFile(
									ft,
									HaSMETSProfile.PREMISIdentifierType.URL,
									fl[i].getHref(),
									"Name was changed from '"
											+ origName
											+ "' to prevent collisions on case-insensitive file systems");
				}
			} else {
				// if the renamed files are not found just log it for now;
				// otherwise uncomment these lines:
				// zz.deleteFiles();
				// throw new HaSMETSProfileException("The renamed file '" + key
				// + "' was not found in the METS document");
				logger.warn("The renamed file '" + key
						+ "' was not found in the METS document");
			}
		}

		PremisEventDocument pevent = HaSMETSProfile.createBasicPremisEvent(
				HaSMETSProfile.PREMISEventType.DECOMPRESSION,
				"This METS document and all associated files were extracted from zip file '"
						+ zip.getName() + "'");

		PremisAgentDocument agents[] = { mets.getDefaultHumanAgent(),
				mets.getDefaultSoftwareAgent() };
		HaSMETSProfile.PREMISLinkingAgentRole roles[] = {
				HaSMETSProfile.PREMISLinkingAgentRole.EVENT_INITIATOR,
				HaSMETSProfile.PREMISLinkingAgentRole.SOFTWARE_USED };

		// add the event to each file
		MetsFileType allfiles[] = mets.getAllFiles();
		if (allfiles.length > 0) {
			MetsMdSecType ev = mets.addEventToXmlObject(allfiles[0], pevent,
					agents, roles);
			for (int i = 1; i < allfiles.length; i++) {
				mets.associateAmdSecToXmlObject(ev, allfiles[i]);
			}
		}
		mets.save();
		return mastermets;
	}
}
