/**
 * PackageSubmission.java
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

import java.io.File;

import org.apache.log4j.Logger;

import edu.uiuc.ndiipp.hubandspoke.lrcrud.client.LrcrudClient;
import edu.uiuc.ndiipp.hubandspoke.lrcrud.client.LrcrudClientException;
import edu.uiuc.ndiipp.hubandspoke.lrcrud.client.LrcrudClient.CreateResponse;
import edu.uiuc.ndiipp.hubandspoke.packager.FromHubPackager;
import edu.uiuc.ndiipp.hubandspoke.packager.FromHubPackagerFactory;
import edu.uiuc.ndiipp.hubandspoke.packager.PackagerException;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfile;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfileException;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMasterMETSProfile;
import edu.uiuc.ndiipp.hubandspoke.utils.jhove.TechMDAugmenter;
import org.purl.sword.base.DepositResponse;
import org.purl.sword.client.Client;
import org.purl.sword.client.PostMessage;
import org.purl.sword.client.SWORDClientException;
import org.purl.sword.client.Status;

/**
 * 
 * @author Bill Ingram
 *
 */
public class PackageSubmission {

    // log4j logger
    static Logger logger = null;

    public PackageSubmission() {
	logger = Logger.getLogger("console");
    }

    public PackageSubmission(Logger log) {
	logger = log;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	// TODO Auto-generated method stub
    }

    public String postPackage(LrcrudClient crud_client, String coll_id)
	    throws LrcrudClientException {

	logger.info("Creating new repository package");

	CreateResponse cr = crud_client.create(coll_id);
	String responseID = cr.getIdentifier();

	logger.info("ID = " + responseID);

	return responseID;
    }

    /**
     * Augments profile with JHOVE generated TechMD, creates repository package
     * zip file, and submits package to the repository.
     *
     * @param mastermets
     *            -- absolute path to an EchoDep Master METS file
     * @param crud_client
     *            -- Lrcrud client
     * @param handle
     *            -- location within the repository
     * @param zip_dest
     *            -- local destination directory for zipfile
     * @param packager_class_name
     * @param keep_zips
     * @throws PackagerException
     * @throws HaSMETSProfileException
     * @throws LrcrudClientException 
     */
    public void submitPackage(HaSMasterMETSProfile mastermets,
	    LrcrudClient crud_client,
	    String handle, String zip_dest, String packager_class_name,
	    boolean keep_zips) throws PackagerException, HaSMETSProfileException,
	    LrcrudClientException {

	// logger.info("Creating repository package");
	FromHubPackagerFactory fpf = new FromHubPackagerFactory();
	FromHubPackager packager = fpf.createFromHubPackager(
		packager_class_name);

	logger.info("Generating JHOVE metadata");
	HaSMETSProfile echodepmets = mastermets.getMostRecentEchoDepMETS();
	if (TechMDAugmenter.addJhoveToProfile(echodepmets)) {

	    logger.info("Packaging files");
	    File pack = packager.createRepositoryPackage(mastermets,
		    zip_dest, handle);

	    logger.info("Updating repository package");
	    // skip packages over 100MB
	    if ((pack.length() / 1048576) < 100) {
		LrcrudClient.UpdateResponse ur = crud_client.update(
			handle, pack);
		if (ur.getStatusCode() == 404) {
		    logger.warn(
			    "Error sending package; will try again in three seconds");
		    try {
			Thread.sleep(3000);
		    } catch (InterruptedException e) {
			logger.error(e.toString());
		    }
		    ur = crud_client.update(handle, pack);
		}
		if (ur.getStatusCode() >= 200 && ur.getStatusCode() <= 204) {
		    logger.info(ur.getStatusCode() + ": " +
			    ur.getStatusMessage());
		    logger.info("ID: " + ur.getUrl());
		} else {
		    logger.error(ur.getStatusCode() + " " +
			    ur.getStatusMessage());
		}

	    } else {
		logger.warn("Package " + mastermets.getBaseURI().toString() +
			" is too large and will not be sent.\r\n");

	    // delete the stub
	    // TODO: Make sure delete is working
	    //crud_client.delete(responseID);
	    }

	    if (keep_zips) {
		logger.info("Local SIP stored at " + pack.getAbsolutePath() +
			"\r\n");
	    } else {
		boolean got_it = pack.delete();
		if (!got_it) {
		    logger.warn("Delete failed!");
		} else {
		    logger.info("Done!\r\n");
		}
	    }

	} else {
	    logger.warn("Profile " + mastermets.getBaseURI().toString() +
		    " contains missing files. Package skipped.\r\n");
	}
    }

    public void submitPackage(HaSMasterMETSProfile mastermets,
	    Client sword_client,
	    PostMessage message, String zip_dest, String packager_class_name,
	    boolean keep_zips) throws PackagerException, HaSMETSProfileException,
	    SWORDClientException {

	// logger.info("Creating repository package");
	FromHubPackagerFactory fpf = new FromHubPackagerFactory();
	FromHubPackager packager = fpf.createFromHubPackager(
		packager_class_name);

	logger.info("Generating JHOVE metadata");
	HaSMETSProfile echodepmets = mastermets.getMostRecentEchoDepMETS();
	if (TechMDAugmenter.addJhoveToProfile(echodepmets)) {

	    logger.info("Packaging files");
	    String handle = echodepmets.getMetsDocument().getMets().getID();
	    File pack = packager.createRepositoryPackage(mastermets,
		    zip_dest, handle);

	    logger.info("Updating repository package");
	    // skip packages over 100MB
	    if ((pack.length() / 1048576) < 100) {
		message.setFilepath(pack.getAbsolutePath());
		message.setFiletype("application/zip");

		DepositResponse dr = sword_client.postFile(message);
		Status status = sword_client.getStatus();
		if (status.getCode() >= 200 && status.getCode() <= 204) {
		    logger.info(dr.marshall());
		    logger.info(status.getCode() + ": " +
			    status.getMessage());

		    logger.info("ID: " + dr.getEntry().getId());
		} else {
		    logger.error(status.getCode() + " " +
			    status.getMessage());
		}

	    } else {
		logger.warn("Package " + mastermets.getBaseURI().toString() +
			" is too large and will not be sent.\r\n");
	    }

	    if (keep_zips) {
		logger.info("Local SIP stored at " + pack.getAbsolutePath() +
			"\r\n");
	    } else {
		boolean got_it = pack.delete();
		if (!got_it) {
		    logger.warn("Delete failed!");
		} else {
		    logger.info("Done!\r\n");
		}
	    }

	} else {
	    logger.warn("Profile " + mastermets.getBaseURI().toString() +
		    " contains missing files. Package skipped.\r\n");
	}
    }
}
