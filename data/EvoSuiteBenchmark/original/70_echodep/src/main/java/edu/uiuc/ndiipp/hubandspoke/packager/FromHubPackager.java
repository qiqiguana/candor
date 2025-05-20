/**
 * FromHubPackager.java
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

package edu.uiuc.ndiipp.hubandspoke.packager;

import java.io.File;
import java.io.InputStream;
import java.util.zip.ZipOutputStream;

import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfile;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfileException;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMasterMETSProfile;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSZipUtils;
import gov.loc.mets.MetsFileType;

public abstract class FromHubPackager {

	public abstract File createRepositoryPackage(HaSMasterMETSProfile mastermets, String output_dir, String id) 
		throws PackagerException, HaSMETSProfileException;
	
	protected static void addFilesToZip(HaSMasterMETSProfile mastermets, ZipOutputStream zip) 
	throws PackagerException{
		HaSMETSProfile echodepmets;
		try {
			echodepmets = mastermets.getMostRecentEchoDepMETS();
		} catch (HaSMETSProfileException e1) {
			e1.printStackTrace();
			throw new PackagerException("Cannot add files to zip. Error retrieving most recent echodep mets from mastermets.");
		}
		
		MetsFileType[] files = echodepmets.getAllFiles();
		for(int i=0; i < files.length; i++){
			MetsFileType file = files[i];
			String fname;
			//determine if the files are referenced or embedded
			if (file.isSetFContent()){
				/*
				 * FIXME: This is bad! Need to devise a way to get a file 
				 * extension. 
				 */
				fname = "embeddedfile"+i;
			} else {
				//here we are assuming that the hubpackage passed validation
				//which means that there is only one <flocat> per <file> 
				//so the first entry in the resulting array is the one we want
				fname = files[i].getFLocatArray()[0].getHref();
			}
			fname = fname.replace("\\", "/");
			fname = fname.substring(fname.lastIndexOf("/")+1);
			try {		
				InputStream in = echodepmets.getFileInputStream(file);
				HaSZipUtils.addFilesToZip(in, zip, fname);
			} catch (Exception e) {
				throw new PackagerException("Error adding files to the zip package", e);
			}
		}
	}
}
