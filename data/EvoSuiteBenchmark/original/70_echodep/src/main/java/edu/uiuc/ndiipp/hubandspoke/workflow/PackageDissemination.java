/**
 * PackageDissemination.java
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;

import edu.uiuc.ndiipp.hubandspoke.lrcrud.client.LrcrudClient;
import edu.uiuc.ndiipp.hubandspoke.lrcrud.client.LrcrudClientException;
import edu.uiuc.ndiipp.hubandspoke.lrcrud.client.LrcrudClient.RetrieveResponse;
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
 * @author Bill Ingram
 *
 */
public class PackageDissemination {

    // log4j logger
    static Logger logger;

    /**
     *
     */
    public PackageDissemination() {
	logger = Logger.getLogger("console");
    }

    /**
     *
     */
    public PackageDissemination(Logger log) {
	logger = log;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	// TODO Auto-generated method stub
    }

    /**
     * Retrieves package from repository; creates a directory and extracts the
     * contents of the retrieved zip file; and updates or builds an EchoDep METS
     * file for the exported package.
     *
     * @param crud_client
     *            -- LRCRUD client
     * @param item_id
     *            -- Repository ID or Handle of the package to be disseminated
     * @param diss_dest
     *            -- Destination directory for the disseminated files
     * @param package_class_name
     *            -- ToHubPackager class
     * @param overwrite
     *            -- Overwrite existing export directories with the same name
     *
     * @return path to the disseminated package's MasterMETS file
     */
    public String disseminatePackage(LrcrudClient crud_client, String item_id,
	    String diss_dest, String packager_class_name, boolean overwrite,
	    boolean bagit) throws LrcrudClientException, IOException,
	    PackagerException, HaSMETSProfileException {

	item_id = item_id.trim();
	logger.info("Retrieving package " + item_id);
	RetrieveResponse rr = crud_client.retrieve(item_id);

	logger.info("Saving export zip file");
	String fixed_item_id = item_id;
	fixed_item_id = fixed_item_id.replaceAll("/", "_");
	fixed_item_id = fixed_item_id.replaceAll(":", "_");

	File dest_dir = new File(diss_dest + File.separator + fixed_item_id);
	logger.info("Creating destination directory: " + dest_dir.
		getAbsolutePath());

	if (dest_dir.exists() && !overwrite) {
	    boolean good_answer = false;
	    while (!good_answer) {
		logger.warn(
			"Directory already exists. Replace it? yes / no / yes for all (y/n/a)");
		BufferedReader cin = new BufferedReader(
			new InputStreamReader(System.in));
		String ans = cin.readLine().toLowerCase();
		if (ans.startsWith("n")) {
		    good_answer = true;
		    logger.info("Package '" + item_id + "' was skipped\r\n");
		    return null;
		}
		if (ans.startsWith("a")) {
		    good_answer = true;
		    overwrite = true;
		    HaSFileUtils.deleteDirectory(dest_dir);
		}
		if (ans.startsWith("y")) {
		    good_answer = true;
		    if (!HaSFileUtils.deleteDirectory(dest_dir)) {
			logger.error("Delete failed!");
		    }
		}
	    }
	}
	if (dest_dir.exists() && overwrite) {
	    if (!HaSFileUtils.deleteDirectory(dest_dir)) {
		logger.error("Directory overwrite failed for " + dest_dir.
			getAbsolutePath());
	    }
	}
	boolean made = dest_dir.mkdirs();
	if (!made) {
	    logger.error("Error creating directory at " + dest_dir.
		    getAbsolutePath());
	    logger.info("Package '" + item_id + "' was skipped\r\n");
	    return null;
	}

	File zip_loc =
		new File(dest_dir.getAbsolutePath() + File.separator +
		fixed_item_id + ".zip");
	rr.save(zip_loc.getAbsolutePath());
	ZipFile zip = new ZipFile(zip_loc.getAbsolutePath());

	logger.info("Creating Packager");
	ToHubPackagerFactory tpf = new ToHubPackagerFactory();
	ToHubPackager packager =
		tpf.createToHubPackager(packager_class_name);

	logger.info("Packaging files");
	boolean ok = packager.createHubPackage(zip,
		dest_dir.getAbsolutePath(), null);

	logger.info("Deleting temporary files");
	zip.close();
	boolean success = zip_loc.delete();
	if (!success) {
	    logger.warn("Delete failed!");
	}

	if (ok) {
	    if (bagit) {
		logger.info("Creating Bagit archive");
		HaSMasterMETSProfile mastermets =
			HaSMasterMETSProfileFactory.newInstance(new File(dest_dir.getPath() + File.separator +
			HaSConstants.MASTER_METS_FILE_NAME));

		logger.info("Generating JHOVE metadata");
		HaSMETSProfile echodepmets =
			mastermets.getMostRecentEchoDepMETS();
		TechMDAugmenter.addJhoveToProfile(echodepmets);
		Hub2BagitPackager.createBagitPackage(mastermets, dest_dir.
			getAbsolutePath(),
			dest_dir.getName());
	    }
	}
	logger.info("Done!\r\n");

	return dest_dir.getPath() + File.separator +
		HaSConstants.MASTER_METS_FILE_NAME;


    }
}
