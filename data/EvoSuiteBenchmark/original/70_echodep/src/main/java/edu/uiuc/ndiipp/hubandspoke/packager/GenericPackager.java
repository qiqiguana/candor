/**
 * GenericPackager.java
 *
 * $Revision: 869 $
 *
 * $Date: 2009-09-04 19:37:28 +0100 (Fri, 04 Sep 2009) $
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
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.zip.ZipFile;

import org.apache.commons.cli.*;
import org.apache.log4j.*;
import org.apache.xmlbeans.XmlObject;
import org.safehaus.uuid.UUID;
import org.safehaus.uuid.UUIDGenerator;
import org.w3c.dom.Node;

import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfile;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfileException;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfileFactory;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMasterMETSProfile;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMasterMETSProfileFactory;
import edu.uiuc.ndiipp.hubandspoke.profile.METSProfile.PackageType;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSConstants;
import edu.uiuc.ndiipp.hubandspoke.utils.jhove.TechMDAugmenter;
import edu.uiuc.ndiipp.hubandspoke.packager.Hub2BagitPackager;
import gov.loc.mets.MetsFileType;
import gov.loc.mets.MetsMdSecType;
import gov.loc.mets.MetsDivType.Mptr;
import gov.loc.mods.ModsModsDocument;
import gov.loc.mods.ModsTitleInfoType;
import org.apache.xmlbeans.XmlString;

/**
 * Walks a supplied directory and creates an EchoDep Hub compliant information
 * package from the contents.
 *
 * @author Bill Ingram
 */
public class GenericPackager extends ToHubPackager {

	private static Logger log = Logger.getLogger(GenericPackager.class);
	private String logconfig = System.getenv("HS_HOME") + File.separator
			+ "config" + File.separator + "log4j.properties";
	protected static Options opts = new Options();

	public GenericPackager() {
		super();
		PropertyConfigurator.configure(logconfig);
	}

	/**
	 * Command line interface...
	 *
	 */
	public static void main(String[] args) {
		try {
			GenericPackager gp = new GenericPackager();
			CommandLine cmd = gp.buildCmdLine(args);
			// automatically generate the help statement
			HelpFormatter formatter = new HelpFormatter();

			File itemdir;
			File metadata;

            HaSMasterMETSProfile mm = null;

			// if the help flag is set or no parameters are supplied print help
			if (cmd.getOptions().length == 0 || cmd.hasOption("h")) {
				usage(formatter, opts, null);
				return;
			}

			if (cmd.hasOption("d")) {
				itemdir = new File(cmd.getOptionValue("d"));
				if (!itemdir.isDirectory()) {
					usage(formatter, opts, itemdir.getAbsolutePath()
							+ " is not a directory. Please supply a directory");
					return;
				}
			} else {
				usage(formatter, opts, "Directory(-d) is a required parameter.");
				return;
			}

			if (cmd.hasOption("m")) {
				System.out.println();
				metadata = new File(cmd.getOptionValue("m"));
				if (!metadata.isFile()) {
					usage(
							formatter,
							opts,
							metadata.getAbsolutePath()
									+ " is not a file. Please supply the path to the MODS metadata.");
					return;
				}
			} else {
				metadata = null;
			}

			if (cmd.hasOption("i")) {
				HaSMETSProfile.PREMISIdentifierType type;
				if (cmd.hasOption("t")) {
					String t = cmd.getOptionValue("t");
					if (t.equalsIgnoreCase("ark")) {
						type = HaSMETSProfile.PREMISIdentifierType.ARK;
					} else if (t.equalsIgnoreCase("urn")) {
						type = HaSMETSProfile.PREMISIdentifierType.URN;
					} else if (t.equalsIgnoreCase("url")) {
						type = HaSMETSProfile.PREMISIdentifierType.URL;
					} else if (t.equalsIgnoreCase("purl")) {
						type = HaSMETSProfile.PREMISIdentifierType.PURL;
					} else if (t.equalsIgnoreCase("handle")) {
						type = HaSMETSProfile.PREMISIdentifierType.HANDLE;
					} else if (t.equalsIgnoreCase("doi")) {
						type = HaSMETSProfile.PREMISIdentifierType.DOI;
					} else {
						type = HaSMETSProfile.PREMISIdentifierType.LOCAL;
					}
				} else {
					type = HaSMETSProfile.PREMISIdentifierType.LOCAL;
				}

				mm = gp.createHubPackage(itemdir, metadata, cmd.getOptionValue("i"), type);

			} else {
				mm = gp.createHubPackage(itemdir, metadata);
			}

			if (cmd.hasOption("b")) {
				System.out.println();
                
                Hub2BagitPackager.createBagitPackage(mm, itemdir.getAbsolutePath(),
                    itemdir.getName());
			}


		} catch (ParseException e) {
			System.err
					.println("Whoops! There was a problem interpereting your input: "
							+ e.getMessage());
		} catch (PackagerException e) {
			System.err
					.println("Whoops! There was a problem creating the Hub Package: "
							+ e.getMessage());
		}
	}

	@SuppressWarnings("static-access")
	protected CommandLine buildCmdLine(String[] args) throws ParseException {

		Option dir = OptionBuilder
				.withArgName("directory")
				.hasArg()
				.withDescription(
						"REQUIRED - directory containing the files from which to create Hub package")
				.create("d");

		Option meta = OptionBuilder
				.withArgName("file")
				.hasArg()
				.withDescription(
						"OPTIONAL - file containing the Aquifer MODS metadata describing the item being packaged. If not supplied, a minimal MODS will be created.")
				.create("m");

		Option id = OptionBuilder
				.withArgName("id")
				.hasArg()
				.withDescription(
						"OPTIONAL - Identifier of the item. If not supplied, a UUID will be created for the item.")
				.create("i");

		Option idtype = OptionBuilder
				.withArgName("idtype")
				.hasArg()
				.withDescription(
						"OPTIONAL - Indicator of which identifier scheme is used. Recognized values are: LOCAL, ARK, URN, URL, PURL, HANDLE, DOI.")
				.create("t");

		Option bagit = OptionBuilder
				.withArgName("bagit")
				.hasArg(false)
				.withDescription(
						"OPTIONAL - Save package in Bagit format?")
				.create("b");

		opts.addOption(dir);
		opts.addOption(meta);
		opts.addOption(id);
		opts.addOption(idtype);
		opts.addOption(bagit);
		opts.addOption("h", "help", false, "prints this help list");

		CommandLineParser parser = new PosixParser();
		return parser.parse(opts, args);
	}

	/**
	 * Creates the Hub package from scratch
	 *
	 * @param itemdir
	 *            -- File representing the path to the items
	 * @param metadata
	 *            -- File representing the path to the MODS metadata for the
	 *            item
	 * @param id
	 *            -- Identifier of the item. If not supplied, a UUID will be
	 *            created for the item.
	 * @param idtype
	 *            -- Indicator of which identifier scheme is used.
	 * @return HaSMasterMETSProfile object
	 * @throws PackagerException
	 */
	public HaSMasterMETSProfile createHubPackage(File itemdir, File metadata,
			String id, HaSMETSProfile.PREMISIdentifierType idtype)
			throws PackagerException {
		try {

			log.info("Creating a Hub package from scratch...");
            
			log.info("Creating METS files...");
            
            // since we're starting from scratch, clean up any old echodepmets files
            File m_file = new File(itemdir.getAbsolutePath() +
                             File.separator +
                             HaSConstants.METS_FILE_NAME_PREFIX + "0.xml");
            
            File mm_file = new File(itemdir.getAbsolutePath() + 
                                    File.separator + 
                                    HaSConstants.MASTER_METS_FILE_NAME);

            if (m_file.exists()) m_file.delete();
            if (mm_file.exists()) mm_file.delete();

			// create a new HaSMasterMETSProfile instance
			HaSMasterMETSProfile mm = HaSMasterMETSProfileFactory.newInstance();

            // create a new HaSMETSProfile instance
            HaSMETSProfile echodepmets = HaSMETSProfileFactory
					.newHaSMETSProfile(HaSMETSProfile.PackageType.SIP);

            // set the base URI on the objects
            echodepmets.setBaseURI(m_file.toURI());

			// set the id
			setOBJID(id, idtype, echodepmets);

			// add the MODS as the primary DMD
            if (metadata != null) {
                addPrimaryMetadata(metadata, echodepmets);
            } else {
                setTitle("Generic Hub and Spoke Package", echodepmets);
            }

			// make the metadata conform
			echodepmets.convertPrimaryDmdSecToAquifer();

			// create the new FileSec
			createFileSec(itemdir, echodepmets);

			// extract a Label for the METS file from the MODS title element
			echodepmets.setLabelFromPrimaryDmdSec();

			// add techMD
            log.info("Adding technical metadata...");
			TechMDAugmenter.addJhoveToProfile(echodepmets);

            // save echodepmets
            echodepmets.save();

            // save and add to mastermets
            mm.addMptr(echodepmets.getBaseURI());

            // save mastermets
            mm.save(mm_file.getAbsolutePath());

            log.info("Hub Package created!");

            return mm;

		} catch (HaSMETSProfileException e) {
			throw new PackagerException("Problem creating Hub package: "
					+ e.getMessage());
		}
	}

	/**
	 * Overload to allow internal generation of an identifier
	 *
	 * @param itemdir
	 *            -- File representing the path to the items
	 * @param metadata
	 *            -- File representing the path to the MODS metadata for the
	 *            item
	 * @return - HaSMasterMETSProfile object
	 * @throws PackagerException
	 */
	public HaSMasterMETSProfile createHubPackage(File itemdir, File metadata)
			throws PackagerException {
		return createHubPackage(itemdir, metadata, null, null);
	}

	/**
	 * Builds the FileSec from the supplied directry.
	 *
	 * @param dir
	 *            -- directory containing the items
	 * @param echodepmets
	 *            -- HaSMETSProfile to put the FileSec into
	 * @throws HaSMETSProfileException
	 */
	protected void createFileSec(File dir, HaSMETSProfile echodepmets)
			throws HaSMETSProfileException {
		log.info("Creating a new FileSec");

		File[] contents = dir.listFiles();

		for (int i = 0; i < contents.length; i++) {
			File file = contents[i];
			if (file.isDirectory()) {
				if (!containsMETS(file, HaSConstants.METS_FILE_NAME)) {
					createFileSec(file, echodepmets);
				}
			} else {
				// add the file to the HaSProfile
                log.info("Adding " + file.getName() + " to METS.");
				MetsFileType echodepfile = echodepmets.addFile(file);
				echodepmets.getPrimaryStructMap().getDiv().addNewFptr()
						.setFILEID(echodepfile.getID());
			}

		}
	}

	/**
	 * Prints hints on how to use the program from the command line.
	 *
	 * @param formatter
	 *            -- CommonsCLI help formatter
	 * @param opts
	 *            -- CommonsCLI options
	 * @param message
	 *            -- what you want to tell the user
	 */
	protected static void usage(HelpFormatter formatter, Options opts,
			String message) {
		if (message != null) {
			System.out.println(message);
		}
		formatter.printHelp("packager", opts);
	}
    
	/**
	 * Replaces the boilerplate MODS created by the constructor with the
	 * supplied MODS and sets it as the primary metadata.
	 *
	 * @param metadata
	 *            -- Aquifer MODS metadata for the item
	 * @param echodepmets
	 * @throws PackagerException
	 */
	protected static void addPrimaryMetadata(File metadata,
			HaSMETSProfile echodepmets) throws PackagerException {

        log.info("Adding primary metadata...");
		ModsModsDocument mods;
		try {
			// load the MODS
			mods = ModsModsDocument.Factory.parse(metadata);
			// remove the xmlData section and replace it with the extracted
			// dspace MODS
			Node xmlData = echodepmets.getMetsDocument().getMets()
					.getDmdSecArray()[0].getMdWrap().getXmlData().getDomNode();
			xmlData.removeChild(xmlData.getFirstChild());
			Node mod = xmlData.getOwnerDocument().importNode(
					mods.getMods().getDomNode(), true);
			xmlData.appendChild(mod);
		} catch (Exception e) {
			throw new PackagerException("Problem creating Hub package"
					+ e.getMessage());
		}

	}


    /**
     * Sets the title of the HaSMETSProfile - to be used *only* when there
     * is no metadata.
     *
     * @param title
     * @param echodepmets
     * @throws PackagerException
     *
     */
    protected void setTitle(String title, HaSMETSProfile echodepmets)
            throws PackagerException{
        if (title == null) return;
        try {
            MetsMdSecType priDMD = echodepmets.getPrimaryDmdSec();
            ModsModsDocument mods = (ModsModsDocument) echodepmets.getMDSecXmlObject(priDMD);

            mods.getMods().getTitleInfoArray(0).removeTitle(0);
            XmlString tt = mods.getMods().getTitleInfoArray(0).insertNewTitle(0);
			tt.setStringValue(title);

			// remove the xmlData section and replace it with the fixed MODS
			Node xmlData = echodepmets.getMetsDocument().getMets()
					.getDmdSecArray()[0].getMdWrap().getXmlData().getDomNode();
			xmlData.removeChild(xmlData.getFirstChild());
			Node mod = xmlData.getOwnerDocument().importNode(
					mods.getMods().getDomNode(), true);
			xmlData.appendChild(mod);
            
        } catch (HaSMETSProfileException e) {
			throw new PackagerException("Problem creating Hub package"
					+ e.getMessage());
        }

    }

	/**
	 * Sets the OBJID of the HaSMETSProfile and cleans up some of the
	 * boilerplate created by the constructor.
	 *
	 * @param id
	 *            -- Identifier of the item. If not supplied, a UUID will be
	 *            created for the item.
	 * @param type
	 *            -- Indicator of which identifier scheme is used.
	 * @param echodepmets
	 * @throws PackagerException
	 */
	protected String setOBJID(String id,
			HaSMETSProfile.PREMISIdentifierType type, HaSMETSProfile echodepmets)
			throws PackagerException {
		try {

			if (id == null) {
				UUID uuid = UUIDGenerator.getInstance()
						.generateRandomBasedUUID();
				id = uuid.toString();
				type = HaSMETSProfile.PREMISIdentifierType.LOCAL;
			}

			echodepmets.setNewPrimaryID(id, type);

			// remove the boilerplate ID values
			XmlObject objs[] = echodepmets.getMetsDocument().selectPath(
					"declare namespace m='" + HaSMETSProfile.METS_NS
							+ "'; //m:altRecordID[.='[IDENTIFIER]']");

			for (int i = 0; i < objs.length; i++) {
				XmlObject object = objs[i];
				object.getDomNode().getParentNode().removeChild(
						object.getDomNode());
			}

			MetsMdSecType prim = echodepmets.getPrimaryRepresentation();
			XmlObject idents[] = prim
					.selectPath("declare namespace p='"
							+ HaSMETSProfile.PREMIS_NS
							+ "'; //p:objectIdentifier[p:objectIdentifierValue[.='[IDENTIFIER]']]");
			for (int i = 0; i < idents.length; i++) {
				XmlObject ident = idents[i];
				ident.getDomNode().getParentNode().removeChild(
						ident.getDomNode());
			}
			return id;

		} catch (Exception e) {
			throw new PackagerException(
					"Problem setting the OBJID of the echodepmets.xml file"
							+ e.getMessage());
		}

	}

	/**
	 * Checks to see if the named METS file exists in a particular location.
	 *
	 * @param dir
	 * @param metsname
	 * @return true if found false otherwise
	 */
	protected static boolean containsMETS(File dir, String metsname) {
		File mets = new File(dir.getAbsolutePath() + File.separatorChar
				+ metsname);
		if (mets.exists()) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean createHubPackage(ZipFile zip, String unzippath,
			String hubpath) throws PackagerException, HaSMETSProfileException {
		// for now this is not implemented...because this class should go away.
		return false;
	}

}
