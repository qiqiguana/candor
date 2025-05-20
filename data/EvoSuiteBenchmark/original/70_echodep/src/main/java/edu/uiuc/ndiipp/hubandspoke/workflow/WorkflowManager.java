/**
 * WorkflowManager.java
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

package edu.uiuc.ndiipp.hubandspoke.workflow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Stack;
import java.util.logging.Level;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import edu.uiuc.ndiipp.hubandspoke.lrcrud.client.LrcrudClient;
import edu.uiuc.ndiipp.hubandspoke.lrcrud.client.LrcrudClientException;
import edu.uiuc.ndiipp.hubandspoke.lrcrud.client.LrcrudClient.CreateResponse;
import edu.uiuc.ndiipp.hubandspoke.lrcrud.client.LrcrudClient.RetrieveResponse;
import edu.uiuc.ndiipp.hubandspoke.packager.DirectoryPackager;
import edu.uiuc.ndiipp.hubandspoke.packager.FromHubPackager;
import edu.uiuc.ndiipp.hubandspoke.packager.FromHubPackagerFactory;
import edu.uiuc.ndiipp.hubandspoke.packager.Hub2BagitPackager;
import edu.uiuc.ndiipp.hubandspoke.packager.PackagerException;
import edu.uiuc.ndiipp.hubandspoke.packager.ToHubPackager;
import edu.uiuc.ndiipp.hubandspoke.packager.ToHubPackagerFactory;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfile;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfileException;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMasterMETSProfile;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMasterMETSProfileFactory;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSConstants;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSFileUtils;
import edu.uiuc.ndiipp.hubandspoke.utils.jhove.TechMDAugmenter;

/**
 * Main entry point for the Hub and Spoke
 * 
 * @author Bill Ingram
 * 
 */
public class WorkflowManager {

	// log4j logger
	static Logger logger = Logger.getLogger("console");

	private File tempdir;

	private String logconfig = System.getenv("HS_HOME") + File.separator
			+ "config" + File.separator + "log4j.properties";

	/**
	 * Constructor -- initializes logger, creates temporary directory
	 * 
	 */
	public WorkflowManager() {
		PropertyConfigurator.configure(logconfig);
		tempdir = HaSFileUtils.createTempDirectory();
	}

	/**
	 * 
	 */
	protected void finalize() throws Throwable {
		try {
			HaSFileUtils.deleteDirectory(tempdir);
		} finally {
			super.finalize();
		}
	}

	/**
	 * Main program entrance
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		WorkflowManager wfm = new WorkflowManager();

		if (args.length > 0 && args[0].compareToIgnoreCase("PUT") == 0) {
			wfm.process_submission(args);
		} else if (args.length > 0 && args[0].compareToIgnoreCase("GET") == 0) {
			wfm.process_dissemination(args);
		} else {
			System.out
					.println("\nusage: WorkflowManager <subcommand> [-option1] argument1 [-option2] argument2...");
			System.out
					.println("\nType \"WorkflowManager <subcommand> --help\" for help on a specific subcommand");
			System.out.println("\nAvailable subcommands:");
			System.out.println("\tPUT");
			System.out.println("\tGET");
			// System.out.println("\tmigrate");
		}

	}

	/**
	 * Prints hints on how to use the program from the command line.
	 * 
	 * @param formatter
	 *            -- CommonsCLI help formatter
	 * @param opts
	 *            -- CommonsCLI options
	 * @param cmd
	 *            -- the command to which the hint pertains
	 * @param message
	 *            -- what you want to tell the user
	 */
	private static void usage(HelpFormatter formatter, Options opts,
			String cmd, String message) {
		if (message != null) {
			System.out.println(message);
		}
		formatter.printHelp("WorkflowManager " + cmd
				+ " [-option1] argument1 [-option2] argument2...\n", opts);
		if (cmd.compareToIgnoreCase("PUT") == 0) {
			System.out
					.println("\nsubmitting items: WorkflowManager PUT -s source -u url -i ID -p packager -k  -z zipfile destination");
		}
		if (cmd.compareToIgnoreCase("GET") == 0) {
			System.out
					.println("\ndisseminating items: WorkflowManager GET -t type -i ID -u url -p packager -d destination");
		}
		// if (cmd.compareToIgnoreCase("MIGRATE")==0)
		// {
		// System.out
		// .println("\nsubmitting items: WorkflowManager MIGRATE ... ");
		// }
		System.exit(0);
	}

    /**
     * Processes command-line arguments for dissemination
     * 
     * @param args
     */
    private void process_dissemination(String[] args) {
	String crud_url;
	String diss_type;
	String dest_path;
	String packager_class_name;
	boolean bagit = false;

	Stack<String> id_stack = new Stack<String>();

	try {

	    /* Set up CLI options */

	    Options opts = new Options();

	    opts.addOption("t", "type", true, "with -i, type: ITEM or LIST");
	    opts.addOption(
		    "i",
		    "ID",
		    true,
		    "with -t, ID or handle of the package to export, or name of a text file listing IDs to export");
	    opts.addOption("u", "URL", true, "URL of the LRCRUD service");
	    opts.addOption(
		    "p",
		    "packager",
		    true,
		    "class name of the Packager to use " +
		    "(must implement edu.uiuc.ndiipp.hubandspoke.packager.ToHubPackager)");
	    opts.addOption("d", "dest", true,
		    "destination path to where you want packages disseminated");

	    opts.addOption("b", "bagit", false, "export in bagit format");

	    opts.addOption("h", "help", false, "prints this help list");

	    CommandLineParser parser = new PosixParser();
	    CommandLine cmd = parser.parse(opts, args);



	    /* Help Formatter */

	    // automatically generate the help statement
	    HelpFormatter formatter = new HelpFormatter();

	    // if the help flag is set or no parameters are supplied print help
	    if (cmd.getOptions().length == 0 || cmd.hasOption("h")) {
		usage(formatter, opts, "GET", null);
		return;
	    }



	    /* LRCRUD Client */

	    if (cmd.hasOption("u") && cmd.getOptionValue("u").length() > 0) {
		crud_url = cmd.getOptionValue("u");
		try {
		    URL crud = new URL(crud_url);
		} catch (MalformedURLException e) {
		    usage(formatter, opts, "GET", cmd.getOptionValue("u") +
			    " is not a valid URL. Please supply a URL");
		    return;
		}
	    } else {
		usage(formatter, opts, "GET",
			"\"crudURL\" (-u) is a REQUIRED parameter");
		return;
	    }



	    /* Export Source Type (Item/List) */

	    if (cmd.hasOption("t") && cmd.getOptionValue("t").length() > 0) {
		if (cmd.getOptionValue("t").compareToIgnoreCase("LIST") == 0) {
		    diss_type = "LIST";
		} else if (cmd.getOptionValue("t").compareToIgnoreCase("ITEM") ==
			0) {
		    diss_type = "ITEM";
		} else {
		    usage(formatter, opts, "GET",
			    "\"type\" (-t) must be either ITEM or LIST");
		    return;
		}
	    } else {
		usage(formatter, opts, "GET",
			"\"type\" (-t) is a REQUIRED parameter");
		return;
	    }



	    /* Item ID(s) */

	    if (cmd.hasOption("i") && cmd.getOptionValue("i").length() > 0) {
		if (diss_type.compareToIgnoreCase("ITEM") == 0) {
		    id_stack.push(cmd.getOptionValue("i"));
		} else if (diss_type.compareToIgnoreCase("LIST") == 0) {
		    File item_list = new File(cmd.getOptionValue("i"));
		    BufferedReader input = null;
		    try {
			input = new BufferedReader(new FileReader(item_list));
			String line = null;
			while ((line = input.readLine()) != null) {
			    if (line.charAt(0) != '\'') {
				// comment out a line with single quote
				logger.debug("Adding file " + line);
				id_stack.push(line);
			    }
			}
		    } catch (FileNotFoundException e) {
			usage(formatter, opts, "GET", "File not found: " +
				item_list.getAbsolutePath());
			return;
		    } catch (IOException e) {
			usage(formatter, opts, "GET", "Error opening file: " +
				item_list.getAbsolutePath());
			return;
		    }
		    try {
			if (input != null) {
			    input.close();
			}
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		}
	    } else {
		usage(formatter, opts, "GET",
			"\"ID\" (-i) is a REQUIRED parameter.");
		return;
	    }



	    /* Packager Class */

	    if (cmd.hasOption("p")) {
		packager_class_name = cmd.getOptionValue("p");
	    } else {
		usage(formatter, opts, "GET",
			"\"packager\" (-p) is a required parameter.");
		return;
	    }



	    /* Export destination */

	    if (cmd.hasOption("d")) {
		dest_path = cmd.getOptionValue("d");
		File dest_loc = new File(dest_path);
		if (!dest_loc.isDirectory()) {
		    usage(formatter, opts, "GET", dest_loc.getAbsolutePath() +
			    " is not a directory. Please supply a directory");
		    return;
		}
	    } else {
		usage(formatter, opts, "GET",
			"\"dest\" (-d) is a REQUIRED parameter.");
		return;
	    }


	    /* Export to Bagit */

	    if (cmd.hasOption("b")) {
		bagit = true;
	    }


	    /* Disseminate Package */

	    ConsoleAuthenticator auth = new ConsoleAuthenticator();
	    LrcrudClient client = null;
	    try {
		client =
			new LrcrudClient(crud_url, auth);
	    } catch (LrcrudClientException ex) {
		java.util.logging.Logger.getLogger(
			WorkflowManager.class.getName()).
			log(Level.SEVERE, null, ex);
	    }
	    boolean replace_dups = false;

	    PackageDissemination pd = new PackageDissemination(logger);

	    try {
		while (!id_stack.isEmpty()) {
		    logger.info("Processing item " + id_stack.peek() + "...");

		    pd.disseminatePackage(client, id_stack.pop(), dest_path,
			    packager_class_name, replace_dups, bagit);
		}
	    } catch (IOException e) {
		logger.error(e);
	    } catch (PackagerException e) {
		logger.error(e);
	    } catch (HaSMETSProfileException e) {
		logger.error(e);
	    } catch (LrcrudClientException e) {
		logger.error(e);
	    // e.printStackTrace();
	    }

	} catch (IllegalArgumentException e) {
	    logger.error(e);
	// e.printStackTrace();
	} catch (ParseException e) {
	    logger.error(e);
	// e.printStackTrace();
	}
	logger.removeAllAppenders();
    }

    /**
     * Processes command-line arguments for submission
     *
     * @param args
     */
    private void process_submission(String[] args) {
	String crud_url;
	LrcrudClient client = null;
	String coll_id;
	String zip_dest;
	String packager_class_name;
	boolean keep_zip = false;

	Stack<File> fileStack = new Stack<File>();
	Stack<File> dirStack = new Stack<File>();
	Stack<HaSMasterMETSProfile> mets_stack =
		new Stack<HaSMasterMETSProfile>();

	try {


	    /* Set up CLI options */

	    Options opts = new Options();

	    opts.addOption(
		    "s",
		    "source",
		    true,
		    "source of item(s) to submit: " +
		    "(source may be a single METS file, a text file listing METS file locations, or " +
		    "a directory to begin recursively searching for METS files)");
	    opts.addOption("u", "URL", true, "URL of the LRCRUD service");
	    opts.addOption("i", "ID", true,
		    "destination collection ID or handle");
	    opts.addOption(
		    "p",
		    "packager",
		    true,
		    "class name of the Packager to use " +
		    "(must implement edu.uiuc.ndiipp.hubandspoke.packager.FromHubPackager)");
	    opts.addOption("k", "keep", false,
		    "keep a local copy of the packaged SIP(s)");
	    opts.addOption("z", "zip", true,
		    "with -k, local destination path to store the SIP zip file(s)");

	    opts.addOption("h", "help", false, "prints this help list");

	    CommandLineParser parser = new PosixParser();
	    CommandLine cmd = parser.parse(opts, args);



	    /* Help Formatter */

	    // automatically generate the help statement
	    HelpFormatter formatter = new HelpFormatter();

	    // if the help flag is set or no parameters are supplied print help
	    if (cmd.getOptions().length == 0 || cmd.hasOption("h")) {
		usage(formatter, opts, "PUT", null);
		return;
	    }



	    /* LRCRUD Client */

	    if (cmd.hasOption("u") && cmd.getOptionValue("u").length() > 0) {
		crud_url = cmd.getOptionValue("u");
		try {
		    URL crud = new URL(crud_url);
		} catch (MalformedURLException e) {
		    usage(formatter, opts, "PUT", cmd.getOptionValue("u") +
			    " is not a valid URL. Please supply a URL");
		    return;
		}

		try {
		    client = new LrcrudClient(crud_url,
			    new ConsoleAuthenticator());

		} catch (LrcrudClientException e) {
		    logger.error(e);
		// e.printStackTrace();
		}

	    } else {
		usage(formatter, opts, "PUT",
			"\"url\" (-u) is a REQUIRED parameter");
		return;
	    }



	    /* Collection ID */

	    if (cmd.hasOption("i") && cmd.getOptionValue("i").length() > 0) {
		coll_id = cmd.getOptionValue("i");
	    } else {
		usage(formatter, opts, "PUT",
			"\"ID\" (-i) is a REQUIRED parameter.");
		return;
	    }



	    /* Package Source */

	    if (cmd.hasOption("s") && cmd.getOptionValue("s").length() > 0) {

		File start_loc = new File(cmd.getOptionValue("s"));
		int count = 0;

		if (!start_loc.isDirectory()) {
		    if (start_loc.getName().compareToIgnoreCase(
			    HaSConstants.MASTER_METS_FILE_NAME) == 0 || start_loc.getName().
			    compareToIgnoreCase(HaSConstants.METS_FILE_NAME) ==
			    0 || start_loc.getName().endsWith(".zip")) {

			// push it on the file stack
			fileStack.push(start_loc);

		    } else {
			// it must be a text file
			BufferedReader input = null;
			try {
			    input =
				    new BufferedReader(new FileReader(start_loc));
			    String line = null;

			    // read the file line-by-line
			    while ((line = input.readLine()) != null) {

				if (line.charAt(0) != '\'') { // skip lines beginning with a single quote

				    File nextFile = new File(line.toString());

				    if (!nextFile.exists()) {
					logger.error("File processing error: " +
						line.toString() +
						" does not exist.");
				    } else if (!nextFile.isAbsolute()) {
					logger.error("File processing error: " +
						line.toString() +
						" is not an absolute filename.");
				    } else if (nextFile.getName().
					    compareToIgnoreCase(
					    HaSConstants.MASTER_METS_FILE_NAME) ==
					    0 || nextFile.getName().
					    compareToIgnoreCase(
					    HaSConstants.METS_FILE_NAME) == 0 || nextFile.getName().
					    endsWith(".zip")) {

					// push it on the file stack
					fileStack.push(nextFile);
				    } else {
					logger.error("File processing error: " +
						"cannot create a Hub package at " +
						nextFile.getPath() + ".");
				    }
				}
			    }
			} catch (FileNotFoundException e) {
			    usage(formatter, opts, "PUT", "File not found: " +
				    start_loc.getAbsolutePath());
			    return;
			} catch (IOException e) {
			    usage(formatter, opts, "PUT",
				    "Error opening file: " + start_loc.
				    getAbsolutePath());
			    return;
			}
			try {
			    if (input != null) {
				input.close();
			    }
			} catch (IOException e) {
			    e.printStackTrace();
			}

		    }
		} else {
		    // it's a directory to crawl
		    dirStack.push(start_loc);

		    while (!dirStack.isEmpty()) {
			File[] filesAndDirs = dirStack.pop().listFiles();
			for (File file : filesAndDirs) {
			    if (file.isDirectory()) {
				dirStack.push(file);
			    }
			    if (file.getName().compareToIgnoreCase(
				    HaSConstants.MASTER_METS_FILE_NAME) == 0 || file.getName().
				    compareToIgnoreCase(
				    HaSConstants.METS_FILE_NAME) == 0 || file.
				    getName().endsWith(".zip")) {

				// push it on the file stack
				fileStack.push(file);
			    }
			}
		    }
		}

		// go through the file stack, create Hub packages, push mastermets onto the mets stack
		while (!fileStack.isEmpty()) {
		    File f = fileStack.pop();

		    if (f.getName().compareToIgnoreCase(
			    HaSConstants.MASTER_METS_FILE_NAME) == 0 || f.
			    getName().compareToIgnoreCase(
			    HaSConstants.METS_FILE_NAME) == 0) {

			// it's a mets file, so create hub package and push the master mets
			try {
			    // first, move the package to a temp directory
			    File working_dir = new File(tempdir, f.getParentFile().
				    getName());
			    HaSFileUtils.copyDirectory(f.getParentFile(),
				    working_dir);

			    HaSMasterMETSProfile mm =
				    HaSMasterMETSProfileFactory.newInstance(
				    working_dir);
			    if (mm != null) {
				mets_stack.push(mm);
				count++;
			    }
			} catch (HaSMETSProfileException e) {
			    logger.error("Error creating Hub package for: " + f.
				    getName());
			} catch (IOException e) {
			    logger.error("Error creating Hub package for: " + f.
				    getName());
			}
		    } else if (f.getName().endsWith(".zip")) {

			// it's a zip file, so create hub package and push the master mets
			try {
			    HaSMasterMETSProfile mm = HaSMasterMETSProfileFactory.newInstance(
				    new ZipFile(f), tempdir, true);
			    if (mm != null) {
				mets_stack.push(mm);
				count++;
			    }
			} catch (ZipException e) {
			    logger.error("Error creating Hub package for: " + f.
				    getName());
			} catch (HaSMETSProfileException e) {
			    logger.error("Error creating Hub package for: " + f.
				    getName());
			} catch (IOException e) {
			    logger.error("Error creating Hub package for: " + f.
				    getName());
			}
		    }
		}
		logger.info("Found " + count + " items to submit");

	    } else {
		usage(formatter, opts, "PUT",
			"\"source\" (-s) is a REQUIRED parameter.");
		return;
	    }



	    /* Packager Class */

	    if (cmd.hasOption("p") && cmd.getOptionValue("p").length() > 0) {
		packager_class_name = cmd.getOptionValue("p");
	    } else {
		usage(formatter, opts, "PUT",
			"\"packager\" (-p) is a required parameter.");
		return;
	    }



	    /* Keep Zips? */

	    if (cmd.hasOption("k")) {
		keep_zip = true;
	    }

	    if (cmd.hasOption("z") && cmd.getOptionValue("z").length() > 0) {
		zip_dest = cmd.getOptionValue("z");
		File zip_loc = new File(zip_dest);
		if (!zip_loc.isDirectory()) {
		    usage(formatter, opts, "PUT", zip_loc.getAbsolutePath() +
			    " is not a directory. Please supply a directory");
		    return;
		}
	    } else {
		zip_dest = null;
	    }



	    /* Submit Package */

	    PackageSubmission ps = new PackageSubmission(logger);

	    while (!mets_stack.isEmpty()) {
		logger.info("Processing " + mets_stack.peek() + "...");

		try {
		    String handle = ps.postPackage(client, coll_id);

		    ps.submitPackage(mets_stack.pop(), client, handle,
			    zip_dest, packager_class_name, keep_zip);

		} catch (PackagerException e) {
		    logger.error(e);
		} catch (HaSMETSProfileException e) {
		    logger.error(e);
		} catch (LrcrudClientException e) {
		    logger.error(e);
		}
	    }

	} catch (IllegalArgumentException e) {
	    logger.error(e);
	// e.printStackTrace();
	} catch (ParseException e) {
	    logger.error(e);
	// e.printStackTrace();
	}
	logger.removeAllAppenders();
    }

}
