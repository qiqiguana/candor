/**
 * Hub2BagitPackager.java
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

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.log4j.*;

import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfile;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfileException;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMasterMETSProfile;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSChecksummer;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSZipUtils;
import gov.loc.mets.MetsFileType;
import gov.loc.mets.MetsDivType.Mptr;
import java.net.URI;

/**
 * 
 * @author Bill Ingram
 *
 */
public class Hub2BagitPackager {

	private static Logger logger = Logger.getLogger(Hub2BagitPackager.class);

	/**
	 * Takes an HandS Hub Package and creates a bagit archive
	 * 
	 * @param mastermets 	-- echodep mastermets file
	 * @param zip_path 		-- destination of zipped bag
	 * @param name 			-- what to call the package
	 * @throws PackagerException
	 */
	public static void createBagitPackage(HaSMasterMETSProfile mastermets, String zip_path, String name) throws PackagerException {
		try {
            logger.info("Creating Bagit Archive...");
			File zipFile = new File(zip_path + ".zip");
			if (zipFile.exists()) {
				zipFile.delete();
			}
			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);

			HaSChecksummer summer = new HaSChecksummer();
			CheckedOutputStream chkStrm = new CheckedOutputStream(zos, summer);

			double packagesize = 0;

			String NL = System.getProperty("line.separator");

			// add bagit.txt to the zip
			StringBuffer bagit = new StringBuffer();
			bagit.append("BagIt-Version: 0.95" + NL);
			bagit.append("Tag-File-Character-Encoding: UTF-8" + NL);

			ByteArrayInputStream bais = new ByteArrayInputStream(bagit.toString().getBytes("UTF-8"));

			ZipEntry ze = new ZipEntry(name + File.separator + "bagit.txt");
			zos.putNextEntry(ze);

			byte[] buf = new byte[1024 * 4];
			int len = 0;
			while ((len = bais.read(buf)) > 0) {
				zos.write(buf, 0, len);
				packagesize += len;
			}
			zos.closeEntry();

			StringBuffer manifest = new StringBuffer();

			// add all the echodepmets files
			for (Mptr mptr : mastermets.getAllMptrs()) {
				File f = new File(mastermets.getBaseURI().resolve(mptr.getHref()));
				FileInputStream fis = new FileInputStream(f);
				logger.info("Reading file: " + f.getName());

				ze = new ZipEntry(name + File.separator + "data" + File.separator + "metadata" + File.separator + f.getName());
				zos.putNextEntry(ze);

				len = 0;
				while ((len = fis.read(buf)) > 0) {
					chkStrm.write(buf, 0, len);
					packagesize += len;
				}
				zos.closeEntry();

				manifest.append(summer.getHexEncodedSHA1() + "  " + "data" + File.separator + "metadata" + File.separator + f.getName() + NL);
			}

			// add all the content files
			HaSMETSProfile echodepmets = mastermets.getMostRecentEchoDepMETS();
			String title = echodepmets.getMetsDocument().getMets().getLABEL();

			for (MetsFileType mft : echodepmets.getAllFiles()) {
				File f = new File(echodepmets.getBaseURI().resolve(mft.getFLocatArray(0).getHref()));
				FileInputStream fis = new FileInputStream(f);
				logger.info("Reading file: " + f.getName());

				ze = new ZipEntry(name + File.separator + "data" + File.separator + mft.getFLocatArray(0).getHref());
				zos.putNextEntry(ze);

				len = 0;
				while ((len = fis.read(buf)) > 0) {
					chkStrm.write(buf, 0, len);
					packagesize += len;
				}
				zos.closeEntry();

				manifest.append(summer.getHexEncodedSHA1() + "  " + "data" + File.separator + mft.getFLocatArray(0).getHref() + NL);
			}

			// finally add the mastermets file
			File f = new File(mastermets.getBaseURI());
			FileInputStream fis = new FileInputStream(f);
			logger.info("Reading file: " + f.getName());

			ze = new ZipEntry(name + File.separator + "data" + File.separator + "metadata" + File.separator + f.getName());
			zos.putNextEntry(ze);

			len = 0;
			while ((len = fis.read(buf)) > 0) {
				chkStrm.write(buf, 0, len);
				packagesize += len;
			}
			zos.closeEntry();

			manifest.append(summer.getHexEncodedSHA1() + "  " + "data" + File.separator + "metadata" + File.separator + f.getName() + NL);

			// add manifest.txt to the zip
			bais = new ByteArrayInputStream(manifest.toString().getBytes("UTF-8"));

			ze = new ZipEntry(name + File.separator + "manifest-sha1.txt");
			zos.putNextEntry(ze);

			buf = new byte[1024 * 4];
			len = 0;
			while ((len = bais.read(buf)) > 0) {
				zos.write(buf, 0, len);
				packagesize += len;
			}
			zos.closeEntry();

			// clean up
			chkStrm.close();
			zos.close();
			fos.close();

            logger.info("Bagit creation complete! See " + zipFile.getAbsolutePath());

		} catch (IOException e) {
			e.printStackTrace();
			throw new PackagerException(e.getMessage());
		} catch (HaSMETSProfileException e) {
			e.printStackTrace();
			throw new PackagerException(e.getMessage());
		}
	}

	/**
	 * Creates a 'bag of bags'
	 * 
	 * @param directory -- directory that contains the bags; 
	 * 						everything in this directory will 
	 * 						get tagged and bagged
	 * @throws IOException
	 */
	public static void createMasterBag(String directory) throws IOException {

		// TODO: This method needs to be re-written so that it steams more and
		// serializes less

		Vector<File> files = new Vector<File>();

		File dir = new File(directory);

		for (File file : dir.listFiles()) {
			if (file.getName().endsWith(".zip")) {
				files.add(file);
			}
		}
		logger.info("Creating master Bag");
		File dest = new File(dir, "masterbag");
		if (!dest.mkdir()) {
			logger.error("Unable to create master bag directory.");
			System.exit(0);
		}

		BufferedWriter bagit = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(dest, "bagit.txt")), "UTF8"));
		BufferedWriter manifest = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(dest, "manifest-sha1.txt")), "UTF8"));
		File data = new File(dest, "data");
		if (!data.mkdir()) {
			logger.error("Unable to create data directory.");
			System.exit(0);
		}

		bagit.write("BagIt-Version: 0.95");
		bagit.newLine();
		bagit.write("Tag-File-Character-Encoding: UTF-8");
		bagit.close();

		for (File file : files) {
			logger.info("Processing file: " + file.getName());
			FileInputStream in = new FileInputStream(file);
			FileOutputStream out = new FileOutputStream(data.getPath() + File.separator + file.getName());
			HaSChecksummer summer = new HaSChecksummer();
			CheckedOutputStream chkStrm = new CheckedOutputStream(out, summer);

			int size = 0;
			byte[] buf = new byte[1024];
			while ((size = in.read(buf)) > 0) {
				chkStrm.write(buf, 0, size);
			}
			chkStrm.close();
			in.close();
			out.close();
			manifest.write(summer.getHexEncodedSHA1() + "  " + "data" + File.separator + file.getName());
			manifest.newLine();
		}
		manifest.close();

		ZipOutputStream zip = null;
		File d = new File(dir, dest.getName() + ".zip");
		zip = new ZipOutputStream(new FileOutputStream(d));

		logger.info("Creating archive: " + d.getName());
		zipAllFiles(dest, zip, dir.getPath());

		File pi = new File(dest, "package-info.txt");
		BufferedWriter packageinfo = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pi), "UTF8"));

		Calendar lastModified = Calendar.getInstance();
		lastModified.setTimeInMillis(d.lastModified());
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		logger.info("Getting file size");
		long size = d.length();

		packageinfo.write("Source-Organization: Grainger Engineering Library Information Center, University of Illinois, Urbana-Champaign");
		packageinfo.newLine();
		packageinfo.write("Organization-Address: 1301 W. Springfield Ave, Urbana, IL 61801");
		packageinfo.newLine();
		packageinfo.write("Contact-Name: Bill Ingram");
		packageinfo.newLine();
		packageinfo.write("Contact-Phone: +1 217-244-7809");
		packageinfo.newLine();
		packageinfo.write("Contact-Email: wingram2@uiuc.edu");
		packageinfo.newLine();
		packageinfo.write("External-Description: Master Bag of digital preservation packages created by the ECHO DEPository Project Web Archives Workbench ");
		packageinfo.newLine();
		packageinfo.write("Packing-Date: " + date.format(lastModified.getTime()));
		packageinfo.newLine();
		packageinfo.write("External-Identifier: " + dir.getName());
		packageinfo.newLine();
		packageinfo.write("Package-Size: " + size + " bytes");
		packageinfo.newLine();
		packageinfo.close();

		HaSZipUtils.addFilesToZip(new FileInputStream(pi), zip, dest.getPath());

		zip.close();
		logger.info("Cleaning up temporary files");
		// deleteDir(dest);
		logger.info("Done");

	}

	// /**
	// * @param args
	// * @throws IOException
	// */
	// public static void main(String[] args) {
	// // Hub2BagitPackager bagit = new Hub2BagitPackager();
	//
	// Appender cApp = new ConsoleAppender(new SimpleLayout());
	// Appender fApp = null;
	// try {
	// fApp = new FileAppender(new SimpleLayout(), "bagit.log");
	// } catch (IOException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// logger.addAppender(cApp);
	// logger.addAppender(fApp);
	// logger.setLevel(Level.INFO);
	//
	// Stack<File> dirStack = new Stack<File>();
	// Vector<File> files = new Vector<File>();
	// File dest = null;
	//
	// try {
	// Options opts = new Options();
	//
	// opts.addOption("s", "source", true,
	// "source of item(s) to convert to Bag(s): "
	// + "source may be a single zip file or a directory "
	// + "to begin recursively searching for zip files");
	// opts.addOption("d", "destination", true,
	// "destination path to store the Bag(s)");
	// opts.addOption("m", "master", false, "create a master Bag");
	//
	// opts.addOption("h", "help", false, "prints this help list");
	//
	// CommandLineParser parser = new PosixParser();
	// CommandLine cmd = parser.parse(opts, args);
	//
	// // automatically generate the help statement
	// HelpFormatter formatter = new HelpFormatter();
	//
	// // if the help flag is set or no parameters are supplied print help
	// if (cmd.getOptions().length == 0 || cmd.hasOption("h")) {
	// usage(formatter, opts, null);
	// return;
	// }
	// if (cmd.hasOption("s") && cmd.getOptionValue("s").length() > 0) {
	// File dir = new File(cmd.getOptionValue("s"));
	// logger.info("Looking for items to process");
	// if (dir.isDirectory()) {
	//
	// dirStack.push(dir);
	// while (!dirStack.isEmpty()) {
	// File[] filesAndDirs = dirStack.pop().listFiles();
	// for (File file : filesAndDirs) {
	// if (file.isDirectory()) {
	// dirStack.push(file);
	// } else {
	// logger.info("Found: " + file.getName());
	// files.add(file);
	// }
	// }
	// }
	// } else if (dir.isFile() && dir.getName().endsWith(".zip")) {
	// logger.info("Found: " + dir.getName());
	// files.add(dir);
	// } else {
	// usage(formatter, opts, "Error: source must be a zip file "
	// + "or a directory of zip files");
	// }
	// }
	// if (cmd.hasOption("d") && cmd.getOptionValue("d").length() > 0) {
	// dest = new File(cmd.getOptionValue("d"));
	// if (!dest.isDirectory()) {
	// usage(formatter, opts,
	// "Error: destination must be a directory.");
	// }
	// }
	// for (File file : files) {
	// logger.info("Creating Bag for " + file.getName());
	// try {
	// Hub2BagitPackager.createBag(new ZipFile(file), dest
	// .getPath());
	// } catch (ZipException e) {
	// logger.error("Package " + file.getName() + " was skipped: "
	// + e.getMessage());
	// } catch (IOException e) {
	// logger.error("Package " + file.getName() + " was skipped: "
	// + e.getMessage());
	// }
	// }
	//
	// if (cmd.hasOption("m")) {
	// try {
	// createMasterBag(cmd.getOptionValue("d"));
	// } catch (IOException e) {
	// logger
	// .error("Error creating master Bag: "
	// + e.getMessage());
	// }
	// }
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	//
	// }

	private static void usage(HelpFormatter formatter, Options opts, String message) {
		if (message != null) {
			System.out.println(message);
		}
		formatter.printHelp("BagIt -s source -d destination\n", opts);
		System.exit(0);
	}

	private static void zipAllFiles(File dir, ZipOutputStream zip, String basedir) throws IOException {
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				zipAllFiles(file, zip, basedir);
			} else {
				String path = file.getPath().substring(new Long(file.getPath().indexOf(basedir) + basedir.length() + 1).intValue());
				HaSZipUtils.addFilesToZip(new FileInputStream(file), zip, path);
			}
		}
	}

	/**
	 * Constructor -- initializes logger
	 * 
	 */
	public Hub2BagitPackager() {

		// logger.addAppender(app);
		// app.clearEvents();
		// app.setThreshold(Level.INFO);
	}

}
