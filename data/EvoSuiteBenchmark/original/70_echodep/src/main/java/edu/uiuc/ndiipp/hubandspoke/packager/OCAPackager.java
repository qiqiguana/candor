/**
 * OCAPackager.java
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

package edu.uiuc.ndiipp.hubandspoke.packager;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfile;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfileException;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfileFactory;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSConstants;
import gov.loc.mets.MetsFileType;
import gov.loc.mets.MetsMdSecType;

/**
 * Walks a supplied directory and creates an EchoDep Hub compliant METS file from the contents.
 *  
 * @author Matt Cordial
 */
public class OCAPackager extends DirectoryPackager {
	
	private static Logger log = Logger.getLogger(OCAPackager.class.getName());
	
	public OCAPackager(){
		super();
	}
	
	/**
     * Command line interface...
     * 
     */
	public static void main ( String[] args ) {  
		System.out.println("Starting METS Generation.");
		try {
			OCAPackager ocap = new OCAPackager();
			CommandLine cmd = ocap.buildCmdLine(args);
			//automatically generate the help statement
			HelpFormatter formatter = new HelpFormatter();
		
			File itemdir;
			File metadata;
			
			//if the help flag is set or no parameters are supplied print help
			if (cmd.getOptions().length==0 || cmd.hasOption("h")){
				usage( formatter, opts, null);
				return;
			}
			
			if(cmd.hasOption("d")){
				itemdir = new File(cmd.getOptionValue("d"));
				if (!itemdir.isDirectory()){
					usage( formatter, opts, itemdir.getAbsolutePath()+" is not a directory. Please supply a directory");
					return;
				}
			}else{
				usage( formatter, opts, "Directory(-d) is a required parameter.");
				return;
			}
			
			if(cmd.hasOption("m")){
				System.out.println();
				metadata = new File(cmd.getOptionValue("m"));
				if (!metadata.isFile()){
					usage( formatter, opts, metadata.getAbsolutePath()+" is not a file. Please supply the path to the MODS metadata.");
					return;
				}
			}else{
				usage( formatter, opts, "Metadata(-m) is a required parameter.");
				return;
			}
			
			if (cmd.hasOption("i")){
				String id = cmd.getOptionValue("i");
				HaSMETSProfile.PREMISIdentifierType type;
				if (cmd.hasOption("t")){
					String t = cmd.getOptionValue("t");
					if (t.equalsIgnoreCase("ark")){
						type  = HaSMETSProfile.PREMISIdentifierType.ARK;
					} else if (t.equalsIgnoreCase("urn")){
						type  = HaSMETSProfile.PREMISIdentifierType.URN;
					} else if (t.equalsIgnoreCase("url")){
						type  = HaSMETSProfile.PREMISIdentifierType.URL;
					} else if (t.equalsIgnoreCase("purl")){
						type  = HaSMETSProfile.PREMISIdentifierType.PURL;
					} else if (t.equalsIgnoreCase("handle")){
						type  = HaSMETSProfile.PREMISIdentifierType.HANDLE;
					} else if (t.equalsIgnoreCase("doi")){
						type  = HaSMETSProfile.PREMISIdentifierType.DOI;
					} else {
						type  = HaSMETSProfile.PREMISIdentifierType.LOCAL;
					}
				}else{
					type  = HaSMETSProfile.PREMISIdentifierType.LOCAL;
				}
				HaSMETSProfile pro = ocap.createHubPackage(itemdir, metadata, id, type);
				String idFn = cleanIdForFileName(id)+".xml";
				//ocap.saveAsFile(pro, idFn);
				pro.save(idFn);
				log.info("Saving file as "+idFn);
				System.out.println("Done!");
			}else{
				ocap.createHubPackage(itemdir, metadata).save(HaSConstants.METS_FILE_NAME);
				return;
			}

		} catch (ParseException e) {	
			System.err.println("Whoops! There was a problem interpereting your input: "+e.getMessage());
		} catch (PackagerException e) {
			System.err.println("Whoops! There was a problem creating the Hub Package: "+e.getMessage());
		}
	}
	
	/**
	 * Creates the Hub package from scratch
	 * 
	 * @param itemdir -- File representing the path to the items
	 * @param metadata -- File representing the path to the MODS metadata for the item
	 * @param id -- Identifier of the item. If not supplied, a UUID will be created for the item.
	 * @param idtype -- Indicator of which identifier scheme is used.
	 * @return HasMETSProfile object
	 * @throws PackagerException
	 */
	public HaSMETSProfile createHubPackage(File itemdir, File metadata, String id, HaSMETSProfile.PREMISIdentifierType idtype) 
	throws PackagerException{
		try {
			
			log.info("Creating a Hub package from scratch");
			
			//create a new HaSMETSProfile instance
			HaSMETSProfile echodepmets = HaSMETSProfileFactory.newHaSMETSProfile(HaSMETSProfile.PackageType.AIP);
			
			//set the base url of this to be the location of the directory
			echodepmets.setBaseURI(itemdir.toURI());
			
			//set the id
			setOBJID(id, idtype, echodepmets);
			
			//add the MODS as the primary DMD
			addPrimaryMetadata(metadata, echodepmets);
			
			//make the metadata conform
			echodepmets.convertPrimaryDmdSecToAquifer();
			
			//create the new FileSec
			createFileSec(itemdir, echodepmets);
			
			//extract a Label for the METS file from the MODS title element
			echodepmets.setLabelFromPrimaryDmdSec();

			return echodepmets;
			
		} catch (HaSMETSProfileException e) {
			throw new PackagerException("Problem creating Hub package"+ e.getMessage());
		}
	}
	
	/**
	 * Overload to allow internal generation of an identifier
	 * 
	 * @param itemdir -- File representing the path to the items
	 * @param metadata -- File representing the path to the MODS metadata for the item
	 * @return - HasMETSProfile object
	 * @throws PackagerException
	 */
	public HaSMETSProfile createHubPackage(File itemdir, File metadata) 
	throws PackagerException{
			return createHubPackage(itemdir, metadata, null, null);
	}
	
	/**
	 * Builds the FileSec from the supplied directory.
	 * 
	 * @param dir -- directory containing the items
	 * @param echodepmets -- HaSMETSProfile to put the FileSec into
	 * @throws HaSMETSProfileException
	 */
	protected void createFileSec(File dir, HaSMETSProfile echodepmets) 
	throws HaSMETSProfileException {
		log.info("Creating a new FileSec");
		
		File[] contents = dir.listFiles();
		
		for (int i = 0; i < contents.length; i++) {
			File file = contents[i];
			if (file.isDirectory()){
				if (!containsMETS(file, HaSConstants.METS_FILE_NAME)){
					createFileSec(file, echodepmets);
				}
			}else{
				if (isMetadata(file.getName())){
					MetsMdSecType.MdRef.MDTYPE.Enum type = determineMDType(file.getName());
					if (type == MetsMdSecType.MdRef.MDTYPE.OTHER){
						echodepmets.addAlternateDmdSec(type, "UIUC-OCA", file);
					}else{
						echodepmets.addAlternateDmdSec(type, null, file);
					}
				}else {
					//if it is not skip-able add the file to the HaSProfile
					if (!isSkippable(file.getName())){
						MetsFileType echodepfile = echodepmets.addFile(file);
						echodepmets.getPrimaryStructMap().getDiv().addNewFptr().setFILEID(echodepfile.getID());
					}
				}
			}
		}
	}
	
	/**
	 * Checks to see if the file exists is metadata to be referenced.
	 * @param filename
	 * @return true if found false otherwise
	 */
	protected static boolean isMetadata(String filename){
		if(	filename.endsWith("_marc.xml") 
			|| filename.endsWith("_meta.xml")
			|| filename.endsWith("_dc.xml") ){
			return true;	
		} else {
			return false;
		}	
	}
	
	/**
	 * Checks to see if the file is the PDF or the ABBY xml file.
	 * @param filepath 
	 * @return true if found false otherwise
	 */
	protected static boolean isSkippable(String filepath){
//		int end = filepath.lastIndexOf(File.separator);
//		int start = filepath.lastIndexOf(File.separator, end-1);
//		String pdfFile = filepath.substring(start+1, end)+".pdf";
//		if(	filepath.endsWith("_abbyy.xml") || filepath.endsWith(pdfFile)) {
		if(	filepath.endsWith("_mods.xml")) {
			return true;	
		} else {
			return false;
		}	
	}
	
	/**
	 * Checks to see if the named METS file exists in a particular location.
	 * @param filename
	 * @return true if found false otherwise
	 */
	protected static MetsMdSecType.MdRef.MDTYPE.Enum determineMDType(String filename){
		if(	filename.endsWith("_marc.xml")){
			return MetsMdSecType.MdRef.MDTYPE.MARC;
		} else if (filename.endsWith("_meta.xml")){
			return MetsMdSecType.MdRef.MDTYPE.OTHER;
		} else if (filename.endsWith("_dc.xml")){
			return MetsMdSecType.MdRef.MDTYPE.DC;
		} else {
			return null;
		}	
	}
	
	/**
	 * Accepts the handle as a full URL, strips the prefix (http://handle.net/) and ensures the 
	 * handle contains no characters that are illegal in a filename.
	 * 
	 * @return true if found false otherwise
	 */
	protected static String cleanIdForFileName(String id){
		String[] peices = id.split("/");
		String prefix = peices[peices.length-2];
		String hdl = peices[peices.length-1].replace(":", ".");
		return prefix+"."+hdl;
	}

}
