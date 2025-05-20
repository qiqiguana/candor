/**
 * WorkflowManagerGUI.java
 * 
 * $Revision: 864 $
 * 
 * $Date: 2009-07-16 18:51:47 +0100 (Thu, 16 Jul 2009) $
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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.swing.AbstractListModel;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import org.purl.sword.base.Collection;
import org.purl.sword.base.Service;
import org.purl.sword.base.Workspace;
import org.purl.sword.client.Client;

import edu.uiuc.ndiipp.hubandspoke.lrcrud.client.LrcrudClient;
import edu.uiuc.ndiipp.hubandspoke.lrcrud.client.LrcrudClientException;
import edu.uiuc.ndiipp.hubandspoke.packager.PackagerException;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMETSProfileException;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMasterMETSProfile;
import edu.uiuc.ndiipp.hubandspoke.profile.HaSMasterMETSProfileFactory;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSConstants;
import edu.uiuc.ndiipp.hubandspoke.utils.HaSFileUtils;
import java.awt.Cursor;
import java.awt.Point;
import javax.swing.SwingWorker;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import org.purl.sword.base.SWORDEntry;
import org.purl.sword.base.ServiceDocument;
import org.purl.sword.client.PostMessage;
import org.purl.sword.client.SWORDClientException;
import org.purl.sword.client.Status;

/**
 *
 * @author  Bill Ingram
 */
public class WorkflowManagerGUI extends javax.swing.JFrame implements
	TreeSelectionListener {

    private static final long serialVersionUID = 1L;
    static Logger logger = Logger.getLogger(WorkflowManagerGUI.class);
    private String logconfig = "/home/wingram2/NetBeansProjects/hands-source/hubandspoke" + File.separator +
	    "config" + File.separator + "log4j.properties";
    private File tempdir;
    private Client sword = null;
    private Service service = null;
    private DefaultMutableTreeNode swordServicesTreeRoot = null;
    private DefaultTreeModel swordServicesTreeModel = null;
    private SWORDUtils su = null;

    /** Creates new form WorkflowManagerGUI */
    public WorkflowManagerGUI() {
	PropertyConfigurator.configure(logconfig);

	tempdir = HaSFileUtils.createTempDirectory();
	logger.info("Building GUI");

	swordServicesTreeRoot = new DefaultMutableTreeNode("Services");
	swordServicesTreeModel = new DefaultTreeModel(swordServicesTreeRoot);
	su = new SWORDUtils();

	initComponents();

	putKeepCopyTextField.setEnabled(false);
	putKeepCopyBrowseButton.setEnabled(false);

	getListLocationLabel.setEnabled(false);
	getListLocationTextField.setEnabled(false);
	getListLocationtBrowseButton.setEnabled(false);

	migrateToKeepCopyTextField.setEnabled(false);
	migrateToKeepCopyBrowseButton.setEnabled(false);

	migrateFromListLocationLabel.setEnabled(false);
	migrateFromListLocationTextField.setEnabled(false);
	migrateFromListLocationBrowseButton.setEnabled(false);

	swordKeepCopyTextField.setEnabled(false);
	swordKeepCopyBrowseButton.setEnabled(false);
    }

    /**
     * Action is triggered by the Submit button on the PUT tab.
     *
     * @param evt
     */
    private void putSubmitButtonActionPerformed(ActionEvent evt) {

	SwingWorker worker = new SwingWorker<Void, Void>() {

	    @Override
	    protected void done() {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		putSubmitButton.setEnabled(true);
	    }

	    @Override
	    protected Void doInBackground()  {
		JTextPaneAppender putAppender = new JTextPaneAppender(
			putLogTextPane);
		logger.addAppender(putAppender);

		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		putSubmitButton.setEnabled(false);

		String crud_url;
		LrcrudClient client = null;
		String coll_id;
		String source_type;
		String zip_dest = tempdir.getPath();
		String packager_class_name;
		boolean keep_zip = false;

		Stack<File> fileStack = new Stack<File>();
		Stack<File> dirStack = new Stack<File>();
		Stack<HaSMasterMETSProfile> mets_stack =
			new Stack<HaSMasterMETSProfile>();

		/* LRCRUD Client */

		if (putCrudTextField.getText().compareTo("") != 0) {
		    crud_url = putCrudTextField.getText();
		} else {
		    JOptionPane.showMessageDialog(null,
			    "You must supply an LRCRUD URL");
		    logger.removeAppender(putAppender);
		    return null;
		}

		try { // check to see if it's a valid url
		    new URL(crud_url);
		} catch (MalformedURLException e) {
		    JOptionPane.showMessageDialog(null,
			    "LRCRUD URL is not well formed");
		    logger.removeAppender(putAppender);
		    return null;
		}

		try {
		    client = new LrcrudClient(crud_url, new GUIAuthenticator());

		} catch (LrcrudClientException e) {
		    logger.error(e);
		// e.printStackTrace();
		}

		/* Collection ID */

		if (putHandleTextField.getText().compareTo("") != 0) {
		    coll_id = putHandleTextField.getText();
		} else {
		    JOptionPane.showMessageDialog(null,
			    "You must supply a collection ID");
		    logger.removeAppender(putAppender);
		    return null;
		}

		/* Source Type */

		if (putPackageSourceTypeDirRadioButton.isSelected()) {
		    source_type = "DIR";
		} else if (putPackageSourceTypeListRadioButton.isSelected()) {
		    source_type = "LIST";
		} else if (putPackageSourceTypeZipRadioButton.isSelected()) {
		    source_type = "ZIP";
		} else {
		    source_type = "METS";
		}

		/* Package Source */

		if (putPackageSourceTextField.getText().compareTo("") != 0) {
		    File start_loc = new File(
			    putPackageSourceTextField.getText());
		    int count = 0;

		    if (source_type.compareToIgnoreCase("METS") == 0) {
			if (!(start_loc.getName().compareToIgnoreCase(
				HaSConstants.MASTER_METS_FILE_NAME) == 0 || start_loc.getName().
				compareToIgnoreCase(
				HaSConstants.METS_FILE_NAME) == 0)) {
			    JOptionPane.showMessageDialog(null,
				    "Source Location cannot be a directory if Source Type is METS");
			    logger.removeAppender(putAppender);
			    return null;
			} else {
			    // push it on the file stack
			    fileStack.push(start_loc);
			}
		    }
		    if (source_type.compareToIgnoreCase("ZIP") == 0) {
			if (!start_loc.getName().endsWith(".zip")) {
			    JOptionPane.showMessageDialog(null,
				    "Source Location must be a zip file if Source Type is Zip file");
			    logger.removeAppender(putAppender);
			    return null;
			} else {
			    // push it on the file stack
			    fileStack.push(start_loc);
			}
		    }
		    if (source_type.compareToIgnoreCase("LIST") == 0) {
			if (!start_loc.getName().endsWith(".zip")) {
			    JOptionPane.showMessageDialog(null,
				    "Source Location cannot be a directory if Source Type is List");
			    logger.removeAppender(putAppender);
			    return null;
			} else {
			    BufferedReader input = null;
			    try {
				input = new BufferedReader(new FileReader(
					start_loc));
				String line = null;

				logger.info("Reading text file...");

				// read the file line-by-line
				while ((line = input.readLine()) != null) {

				    if (line.charAt(0) != '\'') {
					// skip lines beginning with a single
					// quote

					File nextFile =
						new File(line.toString());

					if (!nextFile.exists()) {
					    logger.error(
						    "File processing error: " +
						    line.toString() +
						    " does not exist.");
					} else if (!nextFile.isAbsolute()) {
					    logger.error(
						    "File processing error: " +
						    line.toString() +
						    " is not an absolute filename.");
					} else if (nextFile.getName().
						compareToIgnoreCase(
						HaSConstants.MASTER_METS_FILE_NAME) == 0 || nextFile.getName().
						compareToIgnoreCase(
						HaSConstants.METS_FILE_NAME) ==
						0 ||
						nextFile.getName().endsWith(
						".zip")) {

					    // push it on the file stack
					    fileStack.push(nextFile);
					} else {
					    logger.error(
						    "File processing error: " +
						    "cannot create a Hub package at " + nextFile.getPath() + ".");
					}
				    }
				}
			    } catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null,
					"File not found: " + start_loc.
					getAbsolutePath());
				logger.removeAppender(putAppender);
				return null;
			    } catch (IOException e) {
				JOptionPane.showMessageDialog(null,
					"Error opening file: " + start_loc.
					getAbsolutePath());
				logger.removeAppender(putAppender);
				return null;
			    }
			    try {
				if (input != null) {
				    input.close();
				}
			    } catch (IOException e) {
				e.printStackTrace();
			    }
			}
		    }
		    if (source_type.compareToIgnoreCase("DIR") == 0) {
			if (!start_loc.isDirectory()) {
			    JOptionPane.showMessageDialog(null,
				    "Source Location must be a directory if Source Type is Directory Crawl");
			    logger.removeAppender(putAppender);
			    return null;
			} else {
			    dirStack.push(start_loc);

			    logger.info("Crawling directory....");

			    while (!dirStack.isEmpty()) {
				File[] filesAndDirs = dirStack.pop().listFiles();
				for (File file : filesAndDirs) {
				    if (file.isDirectory()) {
					dirStack.push(file);
				    }
				    if (file.getName().compareToIgnoreCase(
					    HaSConstants.MASTER_METS_FILE_NAME) == 0 || file.getName().
					    compareToIgnoreCase(
					    HaSConstants.METS_FILE_NAME) == 0 || file.getName().
					    endsWith(".zip")) {

					// push it on the file stack
					fileStack.push(file);
				    }
				}
			    }
			}
		    }

		    // go through the file stack, create Hub packages,
		    // push mastermets onto the mets stack
		    while (!fileStack.isEmpty()) {
			File f = fileStack.pop();

			if (f.getName().compareToIgnoreCase(
				HaSConstants.MASTER_METS_FILE_NAME) == 0 || f.
				getName().compareToIgnoreCase(
				HaSConstants.METS_FILE_NAME) == 0) {

			    // it's a mets file, so create hub package and push
			    // the
			    // master mets
			    try {
				// first, move the package to a temp directory
				File working_dir = new File(tempdir, f.
					getParentFile().getName());
				HaSFileUtils.copyDirectory(f.getParentFile(),
					working_dir);

				HaSMasterMETSProfile mm =
					HaSMasterMETSProfileFactory.newInstance(
					working_dir.getPath());
				if (mm != null) {
				    mets_stack.push(mm);
				    count++;
				}
			    } catch (IOException e) {
				logger.error(
					"Error creating Hub package for: " + f.
					getName());
			    }
			} else if (f.getName().endsWith(".zip")) {

			    // it's a zip file, so create hub package and push
			    // the
			    // master mets
			    try {
				HaSMasterMETSProfile mm =
					HaSMasterMETSProfileFactory.newInstance(
					new ZipFile(f), tempdir, true);
				if (mm != null) {
				    mets_stack.push(mm);
				    count++;
				}
			    } catch (ZipException e) {
				logger.warn("Error creating Hub package for: " +
					f.getName() + " (" + e.getMessage() + ")\n");
			    } catch (HaSMETSProfileException e) {
				logger.warn("Error creating Hub package for: " +
					f.getName() + " (" + e.getMessage() + ")\n");
			    } catch (IOException e) {
				logger.warn("Error creating Hub package for: " +
					f.getName() + " (" + e.getMessage() + ")\n");
			    }
			}
		    }
		    logger.info("Found " + count + " items to submit");
		} else {
		    JOptionPane.showMessageDialog(null,
			    "You must supply a package to submit");
		    logger.removeAppender(putAppender);
		    return null;
		}

		/* Packager Class */

		if (putPackagerClassComboBox.getSelectedItem() != null) {
		    packager_class_name = putPackagerClassComboBox.
			    getSelectedItem().toString();
		} else {
		    JOptionPane.showMessageDialog(null,
			    "You must select a packager class");
		    logger.removeAppender(putAppender);
		    return null;
		}

		/* Keep Zips? */

		if (putKeepCopyYesRadioButton.isSelected()) {
		    keep_zip = true;
		}

		if (keep_zip) {
		    if (putKeepCopyTextField.getText().compareTo("") != 0) {
			zip_dest = putKeepCopyTextField.getText();
			File zip_loc = new File(zip_dest);
			if (!zip_loc.isDirectory()) {
			    JOptionPane.showMessageDialog(null,
				    zip_loc.getAbsolutePath() +
				    " is not a directory. Please supply a directory");
			    logger.removeAppender(putAppender);
			    return null;
			}
		    }
		}

		/* Submit Package */

		PackageSubmission ps = new PackageSubmission(logger);

		while (!mets_stack.isEmpty()) {
		    logger.info("Processing " + mets_stack.peek().getBaseURI().
			    toString() + "...");

		    try {
			String handle = ps.postPackage(client, coll_id);

			ps.submitPackage(mets_stack.pop(), client, handle,
				zip_dest, packager_class_name, keep_zip);

		    } catch (PackagerException e) {
			logger.error(e);
			logger.removeAppender(putAppender);
			return null;
		    } catch (HaSMETSProfileException e) {
			logger.error(e);
			logger.removeAppender(putAppender);
			return null;
		    } catch (LrcrudClientException e) {
			JOptionPane.showMessageDialog(null, "Error connecting to LRCRUD service at " + client.getBaseURL().
				toString());
			logger.removeAppender(putAppender);
			return null;
		    }
		}

		logger.removeAppender(putAppender);
		return null;
	    }
	};
	worker.execute();
    }

    /**
     *
     * @param evt
     */
    private void putPackageSourceBrowseButtonActionPerformed(
	    ActionEvent evt) {
	JFileChooser chooser = new JFileChooser();
	chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	chooser.showDialog(this, "Select");
	File file = chooser.getSelectedFile();
	if (file != null) {
	    putPackageSourceTextField.setText(file.getPath());
	}
    }

    /**
     *
     * @param evt
     */
    private void putKeepCopyBrowseButtonActionPerformed(
	    ActionEvent evt) {
	JFileChooser chooser = new JFileChooser();
	chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	chooser.showDialog(this, "Select");
	File file = chooser.getSelectedFile();
	if (file != null) {
	    putKeepCopyTextField.setText(file.getPath());
	}
    }

    /**
     *
     * @param evt
     */
    private void putCancelButtonActionPerformed(ActionEvent evt) {
	//this.dispose();
	System.exit(DISPOSE_ON_CLOSE);
    }

    /**
     *
     * @param evt
     */
    private void putKeepCopyYesRadioButtonActionPerformed(
	    ActionEvent evt) {
	putKeepCopyTextField.setEnabled(true);
	putKeepCopyBrowseButton.setEnabled(true);
    }

    /**
     *
     * @param evt
     */
    private void putKeepCopyNoRadioButtonActionPerformed(
	    ActionEvent evt) {
	putKeepCopyTextField.setEnabled(false);
	putKeepCopyBrowseButton.setEnabled(false);
    }
	

    /**
     *
     * @param evt
     */
    private void getSubmitButtonActionPerformed(ActionEvent evt) {

	SwingWorker worker = new SwingWorker<Void, Void>() {

	    @Override
	    protected void done() {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		getSubmitButton.setEnabled(true);
	    }

	    @Override
	    protected Void doInBackground() {
		JTextPaneAppender getAppender = new JTextPaneAppender(
			getLogTextPane);
		logger.addAppender(getAppender);

		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		getSubmitButton.setEnabled(false);

		String crud_url;
		LrcrudClient client = null;
		String diss_type;
		String packager_class_name;
		String dest_path;
		boolean bagit = false;

		Stack<String> id_stack = new Stack<String>();


		/* LRCRUD Client */

		if (getCrudTextField.getText().compareTo("") != 0) {
		    crud_url = getCrudTextField.getText();
		} else {
		    JOptionPane.showMessageDialog(null,
			    "You must supply an LRCRUD URL");
		    logger.removeAppender(getAppender);
		    return null;
		}


		try { // check to see if it's a valid url
		    new URL(crud_url);
		} catch (MalformedURLException e) {
		    JOptionPane.showMessageDialog(null,
			    "LRCRUD URL is not well formed");
		    logger.removeAppender(getAppender);
		    return null;
		}

		try {
		    logger.info("Instantiating LRCRUD client");
		    client = new LrcrudClient(crud_url, new GUIAuthenticator());

		} catch (LrcrudClientException e) {
		    logger.error(e);
		    logger.removeAppender(getAppender);
		    return null;
		}


		/* Export Source Type (Item/List) */

		if (getExportTypeListRadioButton.isSelected()) {
		    diss_type = "LIST";
		} else {
		    diss_type = "ITEM";
		}


		/* Item ID(s) */

		if (diss_type.compareToIgnoreCase("ITEM") == 0) {
		    if (getHandleTextField.getText().compareTo("") != 0) {
			id_stack.push(getHandleTextField.getText());
		    } else {
			JOptionPane.showMessageDialog(null,
				"You must supply an Item ID for Item Export");
			logger.removeAppender(getAppender);
			return null;
		    }
		} else if (diss_type.compareToIgnoreCase("LIST") == 0) {
		    if (getListLocationTextField.getText().compareTo("") != 0) {
			File item_list = new File(getListLocationTextField.
				getText());
			BufferedReader input = null;
			try {
			    input =
				    new BufferedReader(new FileReader(item_list));
			    String line = null;
			    while ((line = input.readLine()) != null) {
				if (line.charAt(0) != '\'') {
				    // comment out a line with single quote
				    logger.debug("Adding file " + line);
				    id_stack.push(line);
				}
			    }
			} catch (FileNotFoundException e) {
			    JOptionPane.showMessageDialog(null, "File not found");
			    logger.removeAppender(getAppender);
			    return null;
			} catch (IOException e) {
			    JOptionPane.showMessageDialog(null,
				    "Error opening list file");
			    logger.removeAppender(getAppender);
			    return null;
			}
			try {
			    if (input != null) {
				input.close();
			    }
			} catch (IOException e) {
			    e.printStackTrace();
			}
		    } else {
			JOptionPane.showMessageDialog(null,
				"You must supply a List Location for List Export");
			logger.removeAppender(getAppender);
			return null;
		    }
		}



		/* Packager Class */

		if (getPackagerClassComboBox.getSelectedItem() != null) {
		    packager_class_name = getPackagerClassComboBox.
			    getSelectedItem().
			    toString();
		} else {
		    JOptionPane.showMessageDialog(null,
			    "You must select a packager class");
		    logger.removeAppender(getAppender);
		    return null;
		}


		/* Export destination */

		if (getPackageDestTextField.getText().compareTo("") != 0) {
		    dest_path = getPackageDestTextField.getText();
		    File dest_loc = new File(dest_path);
		    if (!dest_loc.isDirectory()) {
			JOptionPane.showMessageDialog(null, dest_loc.
				getAbsolutePath() +
				" is not a directory. Please supply a directory");
			logger.removeAppender(getAppender);
			return null;
		    }
		} else {
		    JOptionPane.showMessageDialog(null,
			    "You must select an export destination");
		    logger.removeAppender(getAppender);
		    return null;
		}


		/* Export to Bagit */

		if (getBagitYesRadioButton.isSelected()) {
		    bagit = true;
		} else {
		    bagit = false;
		}


		/* Disseminate Package */

		boolean replace_dups = true;
		PackageDissemination pd = new PackageDissemination(logger);

		while (!id_stack.isEmpty()) {
		    logger.info("Processing item " + id_stack.peek() + "...");

		    try {
			pd.disseminatePackage(client, id_stack.pop(), dest_path,
				packager_class_name, replace_dups, bagit);
		    } catch (LrcrudClientException e) {
			JOptionPane.showMessageDialog(null, "Error connecting to LRCRUD service at " + client.getBaseURL().
				toString());
			logger.removeAppender(getAppender);
			return null;
		    } catch (IOException e) {
			logger.error(e);
			logger.removeAppender(getAppender);
			return null;
		    } catch (PackagerException e) {
			logger.error(e);
			logger.removeAppender(getAppender);
			return null;
		    } catch (HaSMETSProfileException e) {
			logger.error(e);
			logger.removeAppender(getAppender);
			return null;
		    }
		}

		logger.removeAppender(getAppender);
		return null;
	    }
	};

	worker.execute();
    }

    /**
     *
     * @param evt
     */
    private void getPackageDestBrowseButtonActionPerformed(
	    ActionEvent evt) {
	JFileChooser chooser = new JFileChooser();
	chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	chooser.showDialog(this, "Select");
	File file = chooser.getSelectedFile();
	if (file != null) {
	    getPackageDestTextField.setText(file.getPath());
	}
    }

    /**
     *
     * @param evt
     */
    private void getListLocationtBrowseButtonActionPerformed(
	    ActionEvent evt) {
	JFileChooser chooser = new JFileChooser();
	chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	chooser.showDialog(this, "Select");
	File file = chooser.getSelectedFile();
	if (file != null) {
	    getListLocationTextField.setText(file.getPath());
	}
    }

    /**
     *
     * @param evt
     */
    private void getCancelButtonActionPerformed(ActionEvent evt) {
	//this.dispose();
	System.exit(DISPOSE_ON_CLOSE);
    }

    /**
     *
     * @param evt
     */
    private void getExportTypeItemRadioButtonActionPerformed(
	    ActionEvent evt) {
	getHandleLabel.setEnabled(true);
	getHandleTextField.setEnabled(true);

	getListLocationLabel.setEnabled(false);
	getListLocationTextField.setEnabled(false);
	getListLocationtBrowseButton.setEnabled(false);
    }

    /**
     *
     * @param evt
     */
    private void getExportTypeListRadioButtonActionPerformed(
	    ActionEvent evt) {
	getListLocationLabel.setEnabled(true);
	getListLocationTextField.setEnabled(true);
	getListLocationtBrowseButton.setEnabled(true);

	getHandleLabel.setEnabled(false);
	getHandleTextField.setEnabled(false);
    }

    /**
     *
     * @param evt
     */
    private void migrateSubmitButtonActionPerformed(
	    ActionEvent evt) {

	SwingWorker worker = new SwingWorker<Void, Void>() {

	    @Override
	    protected void done() {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		migrateSubmitButton.setEnabled(true);
	    }

	    @Override
	    protected Void doInBackground() {
		JTextPaneAppender migrateAppender = new JTextPaneAppender(
			migrateLogTextPane);
		logger.addAppender(migrateAppender);

		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		migrateSubmitButton.setEnabled(false);

		/**
		 * GET section
		 */
		String get_crud_url;
		LrcrudClient get_client = null;
		String get_diss_type;
		String get_packager_class_name;
		String get_dest_path;

		Stack<String> id_stack = new Stack<String>();

		get_dest_path = tempdir.getPath();

		GUIAuthenticator gauth = new GUIAuthenticator();


		/* LRCRUD Client */

		if (migrateFromCrudTextField.getText().compareTo("") != 0) {
		    get_crud_url = migrateFromCrudTextField.getText();
		} else {
		    JOptionPane.showMessageDialog(null,
			    "You must supply an LRCRUD URL");
		    logger.removeAppender(migrateAppender);
		    return null;
		}


		try { // check to see if it's a valid url
		    new URL(get_crud_url);
		} catch (MalformedURLException e) {
		    JOptionPane.showMessageDialog(null,
			    "LRCRUD URL is not well formed");
		    logger.removeAppender(migrateAppender);
		    return null;
		}

		try {
		    get_client = new LrcrudClient(get_crud_url, gauth);

		} catch (LrcrudClientException e) {
		    logger.error(e);
		// e.printStackTrace();
		}


		/* Export Source Type (Item/List) */

		if (migrateFromExportTypeListRadioButton.isSelected()) {
		    get_diss_type = "LIST";
		} else {
		    get_diss_type = "ITEM";
		}


		/* Item ID(s) */

		if (get_diss_type.compareToIgnoreCase("ITEM") == 0) {
		    if (migrateFromHandleTextField.getText().compareTo("") != 0) {
			id_stack.push(migrateFromHandleTextField.getText());
		    } else {
			JOptionPane.showMessageDialog(null,
				"You must supply an Item ID for Item Export");
			logger.removeAppender(migrateAppender);
			return null;
		    }
		} else if (get_diss_type.compareToIgnoreCase("LIST") == 0) {
		    if (migrateFromListLocationTextField.getText().compareTo("") !=
			    0) {
			File item_list = new File(
				migrateFromListLocationTextField.getText());
			BufferedReader input = null;
			try {
			    input =
				    new BufferedReader(new FileReader(item_list));
			    String line = null;
			    while ((line = input.readLine()) != null) {
				if (line.charAt(0) != '\'') {
				    // comment out a line with single quote
				    logger.debug("Adding file " + line);
				    id_stack.push(line);
				}
			    }
			} catch (FileNotFoundException e) {
			    JOptionPane.showMessageDialog(null, "File not found");
			    logger.removeAppender(migrateAppender);
			    return null;
			} catch (IOException e) {
			    JOptionPane.showMessageDialog(null,
				    "Error opening list file");
			    logger.removeAppender(migrateAppender);
			    return null;
			}
			try {
			    if (input != null) {
				input.close();
			    }
			} catch (IOException e) {
			    e.printStackTrace();
			}
		    } else {
			JOptionPane.showMessageDialog(null,
				"You must supply a List Location for List Export");
			logger.removeAppender(migrateAppender);
			return null;
		    }
		}



		/* Packager Class */

		if (migrateFromPackagerClassComboBox.getSelectedItem() != null) {
		    get_packager_class_name = migrateFromPackagerClassComboBox.
			    getSelectedItem().toString();
		} else {
		    JOptionPane.showMessageDialog(null,
			    "You must select a packager class");
		    logger.removeAppender(migrateAppender);
		    return null;
		}


		/* Disseminate Package */

		Stack<HaSMasterMETSProfile> mets_stack =
			new Stack<HaSMasterMETSProfile>();

		boolean replace_dups = true;
		PackageDissemination pd = new PackageDissemination(logger);

		int count = 0;
		while (!id_stack.isEmpty()) {
		    logger.info("Processing item " + id_stack.peek() + "...");

		    String mm_path = "";

		    try {
			mm_path =
				pd.disseminatePackage(get_client,
				id_stack.pop(), get_dest_path,
				get_packager_class_name, replace_dups, false);
		    } catch (IOException e) {
			logger.error(e);
			logger.removeAppender(migrateAppender);
			return null;
		    } catch (PackagerException e) {
			logger.error(e);
			logger.removeAppender(migrateAppender);
			return null;
		    } catch (HaSMETSProfileException e) {
			logger.error(e);
			logger.removeAppender(migrateAppender);
			return null;
		    } catch (LrcrudClientException e) {
			JOptionPane.showMessageDialog(null, "Error connecting to LRCRUD service at " + get_client.getBaseURL().
				toString());
			logger.removeAppender(migrateAppender);
			return null;
		    }


		    HaSMasterMETSProfile mm = null;
		    try {
			mm = HaSMasterMETSProfileFactory.newInstance(new File(
				mm_path));
		    } catch (HaSMETSProfileException e) {
			e.printStackTrace();
		    }
		    if (mm != null) {
			mets_stack.push(mm);
			count++;
		    }
		}
		logger.info("Retrieved " + count + " items from the repository.");


		/**
		 * PUT section
		 */
		String put_crud_url;
		LrcrudClient put_client = null;
		String put_coll_id;
		String put_zip_dest = tempdir.getPath();
		String put_packager_class_name;
		boolean put_keep_zip = false;


		/* LRCRUD Client */

		if (migrateToCrudTextField.getText().compareTo("") != 0) {
		    put_crud_url = migrateToCrudTextField.getText();
		} else {
		    JOptionPane.showMessageDialog(null,
			    "You must supply an LRCRUD URL");
		    logger.removeAppender(migrateAppender);
		    return null;
		}


		try { // check to see if it's a valid url
		    new URL(put_crud_url);
		} catch (MalformedURLException e) {
		    JOptionPane.showMessageDialog(null,
			    "LRCRUD URL is not well formed");
		    logger.removeAppender(migrateAppender);
		    return null;
		}

		try {
		    put_client = new LrcrudClient(put_crud_url, gauth);

		} catch (LrcrudClientException e) {
		    logger.error(e);
		// e.printStackTrace();
		}


		/* Collection ID */

		if (migrateToHandleTextField.getText().compareTo("") != 0) {
		    put_coll_id = migrateToHandleTextField.getText();
		} else {
		    JOptionPane.showMessageDialog(null,
			    "You must supply a collection ID");
		    logger.removeAppender(migrateAppender);
		    return null;
		}



		/* Packager Class */

		if (migrateToPackagerClassComboBox.getSelectedItem() != null) {
		    put_packager_class_name = migrateToPackagerClassComboBox.
			    getSelectedItem().toString();
		} else {
		    JOptionPane.showMessageDialog(null,
			    "You must select a packager class");
		    logger.removeAppender(migrateAppender);
		    return null;
		}



		/* Keep Zips? */

		if (migrateToKeepCopyYesRadioButton.isSelected()) {
		    put_keep_zip = true;
		}

		if (put_keep_zip) {
		    if (migrateToKeepCopyTextField.getText().compareTo("") != 0) {
			put_zip_dest = migrateToKeepCopyTextField.getText();
			File zip_loc = new File(put_zip_dest);
			if (!zip_loc.isDirectory()) {
			    JOptionPane.showMessageDialog(null,
				    zip_loc.getAbsolutePath() +
				    " is not a directory. Please supply a directory");
			    logger.removeAppender(migrateAppender);
			    return null;
			}
		    }
		}



		/* Submit Package */

		PackageSubmission ps = new PackageSubmission(logger);

		while (!mets_stack.isEmpty()) {
		    logger.info("Processing " + mets_stack.peek().getBaseURI().
			    toString() + "...");

		    try {
			String handle = ps.postPackage(put_client, put_coll_id);

			ps.submitPackage(mets_stack.pop(), put_client, handle,
				put_zip_dest, put_packager_class_name,
				put_keep_zip);

		    } catch (PackagerException e) {
			logger.error(e);
			logger.removeAppender(migrateAppender);
			return null;
		    } catch (HaSMETSProfileException e) {
			logger.error(e);
			logger.removeAppender(migrateAppender);
			return null;
		    } catch (LrcrudClientException e) {
			JOptionPane.showMessageDialog(null, "Error connecting to LRCRUD service at " + put_client.getBaseURL().
				toString());
			logger.removeAppender(migrateAppender);
			return null;
		    }
		}

		logger.removeAppender(migrateAppender);
		return null;
	    }
	};
	worker.execute();

    }

    /**
     *
     * @param evt
     */
    private void migrateFromExportTypeItemRadioButtonActionPerformed(
	    ActionEvent evt) {
	migrateFromHandleLabel.setEnabled(true);
	migrateFromHandleTextField.setEnabled(true);

	migrateFromListLocationLabel.setEnabled(false);
	migrateFromListLocationTextField.setEnabled(false);
	migrateFromListLocationBrowseButton.setEnabled(false);
    }

    /**
     *
     * @param evt
     */
    private void migrateFromExportTypeListRadioButtonActionPerformed(
	    ActionEvent evt) {
	migrateFromListLocationLabel.setEnabled(true);
	migrateFromListLocationTextField.setEnabled(true);
	migrateFromListLocationBrowseButton.setEnabled(true);

	migrateFromHandleLabel.setEnabled(false);
	migrateFromHandleTextField.setEnabled(false);
    }

    /**
     *
     * @param evt
     */
    private void migrateFromListLocationBrowseButtonActionPerformed(
	    ActionEvent evt) {
	JFileChooser chooser = new JFileChooser();
	chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	chooser.showDialog(this, "Select");
	File file = chooser.getSelectedFile();
	if (file != null) {
	    migrateFromListLocationTextField.setText(file.getPath());
	}
    }

    /**
     *
     * @param evt
     */
    private void migrateToKeepCopyYesRadioButtonActionPerformed(
	    ActionEvent evt) {
	migrateToKeepCopyTextField.setEnabled(true);
	migrateToKeepCopyBrowseButton.setEnabled(true);
    }

    /**
     *
     * @param evt
     */
    private void migrateToKeepCopyNoRadioButtonActionPerformed(
	    ActionEvent evt) {
	migrateToKeepCopyTextField.setEnabled(false);
	migrateToKeepCopyBrowseButton.setEnabled(false);
    }

    /**
     *
     * @param evt
     */
    private void migrateToKeepCopyBrowseButtonActionPerformed(
	    ActionEvent evt) {
	JFileChooser chooser = new JFileChooser();
	chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	chooser.showDialog(this, "Select");
	File file = chooser.getSelectedFile();
	if (file != null) {
	    migrateToKeepCopyTextField.setText(file.getPath());
	}
    }

    /**
     *
     * @param evt
     */
    private void migrateCancelButtonActionPerformed(
	    ActionEvent evt) {
	System.exit(DISPOSE_ON_CLOSE);
    }

    private ComboBoxModel swordCollectionLocationModel() {
	ArrayList<String> services = new ArrayList<String>();
	if (service != null) {

	    Iterator<Workspace> workspaces = service.getWorkspaces();
	    while (workspaces.hasNext()) {
		Iterator<Collection> collections = workspaces.next().
			collectionIterator();
		while (collections.hasNext()) {
		    services.add(collections.next().getLocation());
		}
	    }
	}

	return new ArrayListComboBoxModel(services);
    }

    public void valueChanged(TreeSelectionEvent evt) {
	TreePath[] paths = evt.getPaths();

	for (int i = 0; i < paths.length; i++) {
	    if (evt.isAddedPath(i)) {
		DefaultMutableTreeNode node;
		node =
			(DefaultMutableTreeNode) (paths[i].getLastPathComponent());

		Object o = node.getUserObject();
		if (o instanceof TreeNodeWrapper) {
		    try {
			TreeNodeWrapper treeNode = (TreeNodeWrapper) o;
			Object data = treeNode.getData();
			if (data instanceof Service) {
			    su.showService((Service) data,
				    swordServiceDetailsEditorPane);
			} else if (data instanceof Workspace) {
			    su.showWorkspace((Workspace) data,
				    swordServiceDetailsEditorPane);
			} else if (data instanceof Collection) {
			    Collection c = (Collection) data;
			    su.showCollection(c, swordServiceDetailsEditorPane);
			    swordCollectionLocationComboBox.setSelectedItem(c.
				    getLocation());
			} else if (data instanceof SWORDEntry) {
			    su.showEntry((SWORDEntry) data,
				    swordServiceDetailsEditorPane);
			} else {
			    swordServiceDetailsEditorPane.setText(
				    "<html><body>unknown</body></html>");
			}
		    } catch (Exception e) {
			swordServiceDetailsEditorPane.setText("<html><body>An error occurred. The message was: " +
				e.getMessage() + "</body></html>");
			e.printStackTrace();
		    }
		} else {
		    swordServiceDetailsEditorPane.setText(
			    "<html><body>please select one of the other nodes</body></html>");
		}
	    }
	}
	swordServiceDetailsEditorPane.setCaretPosition(0);
    }

    /**
     * Representation of a Swing ComboBoxModel as an ArrayList
     * @author Bill Ingram
     *
     */
    private class ArrayListComboBoxModel extends AbstractListModel implements
	    ComboBoxModel {

	private static final long serialVersionUID = 1L;
	private Object selectedItem;
	private ArrayList<String> anArrayList;

	public ArrayListComboBoxModel(ArrayList<String> arrayList) {
	    anArrayList = arrayList;
	}

	public Object getSelectedItem() {
	    return selectedItem;
	}

	public void setSelectedItem(Object newValue) {
	    if (anArrayList.contains(newValue)) {
		selectedItem = newValue;
		fireContentsChanged(this, anArrayList.indexOf(newValue),
			anArrayList.indexOf(newValue));
	    }
	}

	public int getSize() {
	    return anArrayList.size();
	}

	public Object getElementAt(int i) {
	    return anArrayList.get(i);
	}
    }

    /**
     *
     * @return
     */
    private ComboBoxModel putFromHubPackagerTargets() {
	try {
	    ClassLoader classLoader = Thread.currentThread().
		    getContextClassLoader();
	    assert classLoader != null;
	    String p_dir = "edu.uiuc.ndiipp.hubandspoke.packager";
	    String p_path = p_dir.replace('.', '/');
	    URL resource = classLoader.getResource(p_path);
	    File directory = new File(resource.getFile());
	    String[] files = directory.list();

	    ArrayList<String> packages = new ArrayList<String>();
	    for (int i = 0; i < files.length; i++) {
		// get rid of the ".class" at the end
		String sans_class = files[i].substring(0, files[i].length() - 6);
		String full_name = p_dir + '.' + sans_class;
		if (sans_class.compareToIgnoreCase("FromHubPackager") == 0) {
		    continue;
		}

		Class packager_class = getClass().getClassLoader().loadClass(
			full_name);
		if (edu.uiuc.ndiipp.hubandspoke.packager.FromHubPackager.class.
			isAssignableFrom(packager_class)) {
		    packages.add(full_name);
		}
	    }
	    return new ArrayListComboBoxModel(packages);
	} catch (ClassNotFoundException ex) {
	    logger.error(ex.getMessage());
	}
	return null;
    }

    /**
     *
     * @return
     */
    private ComboBoxModel getToHubPackagerTargets() {
	try {
	    ClassLoader classLoader = Thread.currentThread().
		    getContextClassLoader();
	    assert classLoader != null;
	    String p_dir = "edu.uiuc.ndiipp.hubandspoke.packager";
	    String p_path = p_dir.replace('.', '/');
	    URL resource = classLoader.getResource(p_path);
	    File directory = new File(resource.getFile());
	    String[] files = directory.list();

	    ArrayList<String> packages = new ArrayList<String>();
	    for (int i = 0; i < files.length; i++) {
		// get rid of the ".class" at the end
		String sans_class = files[i].substring(0, files[i].length() - 6);
		String full_name = p_dir + '.' + sans_class;
		if (sans_class.compareToIgnoreCase("ToHubPackager") == 0 ||
			sans_class.compareToIgnoreCase("DirectoryPackager") == 0 ||
			sans_class.compareToIgnoreCase("OCAPackager") == 0) {
		    continue;
		}

		Class packager_class = getClass().getClassLoader().loadClass(
			full_name);
		if (edu.uiuc.ndiipp.hubandspoke.packager.ToHubPackager.class.
			isAssignableFrom(packager_class)) {
		    packages.add(full_name);
		}
	    }
	    return new ArrayListComboBoxModel(packages);
	} catch (ClassNotFoundException ex) {
	    logger.error(ex.getMessage());
	}
	return null;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                putKeepZipsButtonGroup = new ButtonGroup();
                getBagitButtonGroup = new ButtonGroup();
                getExportTypeButtonGroup = new ButtonGroup();
                putSourceTypeButtonGroup = new ButtonGroup();
                migrateExportTypeButtonGroup = new ButtonGroup();
                migrateKeepZipsButtonGroup = new ButtonGroup();
                swordServiceDocDialog = new JDialog();
                swordUsernameLabel = new JLabel();
                swordUsernameTextField = new JTextField();
                swordPasswordLabel = new JLabel();
                swordPasswordField = new JPasswordField();
                swordLocationLabel = new JLabel();
                swordLocationTextField = new JTextField();
                swordOnBehalfOfLabel = new JLabel();
                swordOnBehalfOfTextField = new JTextField();
                getServiceDocButton = new JButton();
                getServiceDocCancelButton = new JButton();
                swordSourceTypeButtonGroup = new ButtonGroup();
                swordKeepZipsButtonGroup = new ButtonGroup();
                mainTabbedPane = new JTabbedPane();
                getSplitPane = new JSplitPane();
                getPanel = new JPanel();
                getCancelButton = new JButton();
                getSubmitButton = new JButton();
                getCrudLabel = new JLabel();
                getCrudTextField = new JTextField();
                getHandleLabel = new JLabel();
                getHandleTextField = new JTextField();
                getPackageDestLabel = new JLabel();
                getPackageDestTextField = new JTextField();
                getPackageDestBrowseButton = new JButton();
                getToHubClassLabel = new JLabel();
                getPackagerClassComboBox = new JComboBox();
                getBagitLabel = new JLabel();
                getBagitYesRadioButton = new JRadioButton();
                getBagitNoRadioButton = new JRadioButton();
                getExportTypeLabel = new JLabel();
                getExportTypeItemRadioButton = new JRadioButton();
                getExportTypeListRadioButton = new JRadioButton();
                getListLocationLabel = new JLabel();
                getListLocationTextField = new JTextField();
                getListLocationtBrowseButton = new JButton();
                getLogPanel = new JPanel();
                getLogLabel = new JLabel();
                jScrollPane2 = new JScrollPane();
                getLogTextPane = new JTextPane();
                putSplitPane = new JSplitPane();
                putPanel = new JPanel();
                putPackagerClassComboBox = new JComboBox();
                putFromHubClassLabel = new JLabel();
                putPackageSourceLabel = new JLabel();
                putPackageSourceTextField = new JTextField();
                putPackageSourceBrowseButton = new JButton();
                putCrudLabel = new JLabel();
                putCrudTextField = new JTextField();
                putHandleLabel = new JLabel();
                putHandleTextField = new JTextField();
                putSubmitButton = new JButton();
                putKeepCopyLabel = new JLabel();
                putKeepCopyYesRadioButton = new JRadioButton();
                putKeepCopyNoRadioButton = new JRadioButton();
                putKeepCopyTextField = new JTextField();
                putKeepCopyBrowseButton = new JButton();
                putCancelButton = new JButton();
                putPackageSourceTypeLabel = new JLabel();
                putPackageSourceTypeMETSRadioButton = new JRadioButton();
                putPackageSourceTypeListRadioButton = new JRadioButton();
                putPackageSourceTypeDirRadioButton = new JRadioButton();
                putPackageSourceTypeZipRadioButton = new JRadioButton();
                putLogPanel = new JPanel();
                putLogLabel = new JLabel();
                jScrollPane1 = new JScrollPane();
                putLogTextPane = new JTextPane();
                migrateSplitPane = new JSplitPane();
                migratePanel = new JPanel();
                migrateFromLabel = new JLabel();
                migrateFromCrudLabel = new JLabel();
                migrateFromCrudTextField = new JTextField();
                migrateFromExportTypeLabel = new JLabel();
                migrateFromExportTypeItemRadioButton = new JRadioButton();
                migrateFromExportTypeListRadioButton = new JRadioButton();
                migrateFromHandleLabel = new JLabel();
                migrateFromHandleTextField = new JTextField();
                migrateFromListLocationLabel = new JLabel();
                migrateFromListLocationTextField = new JTextField();
                migrateFromListLocationBrowseButton = new JButton();
                migrateFromToHubClassLabel = new JLabel();
                migrateFromPackagerClassComboBox = new JComboBox();
                migrateSeparator = new JSeparator();
                migrateToLabel = new JLabel();
                migrateToCrudLabel = new JLabel();
                migrateToCrudTextField = new JTextField();
                migrateToHandleLabel = new JLabel();
                migrateToHandleTextField = new JTextField();
                migrateToFromHubClassLabel = new JLabel();
                migrateToPackagerClassComboBox = new JComboBox();
                migrateToKeepCopyLabel = new JLabel();
                migrateToKeepCopyYesRadioButton = new JRadioButton();
                migrateToKeepCopyNoRadioButton = new JRadioButton();
                migrateToKeepCopyTextField = new JTextField();
                migrateToKeepCopyBrowseButton = new JButton();
                migrateCancelButton = new JButton();
                migrateSubmitButton = new JButton();
                migrateLogPanel = new JPanel();
                migrateLogLabel = new JLabel();
                jScrollPane3 = new JScrollPane();
                migrateLogTextPane = new JTextPane();
                swordSplitPane = new JSplitPane();
                swordServicePane = new JSplitPane();
                swordServiceTreePanel = new JPanel();
                swordGetServiceDocButton = new JButton();
                swordServiceTreeScrollPane = new JScrollPane();
                swordServicesTree = new JTree();
                swordServiceEditorPanel = new JPanel();
                swordServiceEditorScrollPane = new JScrollPane();
                swordServiceDetailsEditorPane = new JEditorPane("text/html", "<html><body><h1>Details</h1><p>This panel will show the details for the currently selected item in the tree.</p></body></html>");
                swordPostPane = new JSplitPane();
                swordPostPanel = new JPanel();
                swordCollectionLocationLabel = new JLabel();
                swordCollectionLocationComboBox = new JComboBox();
                swordPackageSourceTypeLabel = new JLabel();
                swordPackageSourceTypeMETSRadioButton = new JRadioButton();
                swordPackageSourceTypeZipRadioButton = new JRadioButton();
                swordPackageSourceTypeListRadioButton = new JRadioButton();
                swordPackageSourceTypeDirRadioButton = new JRadioButton();
                swordPackageSourceLabel = new JLabel();
                swordPackageSourceTextField = new JTextField();
                swordPackageSourceBrowseButton = new JButton();
                swordKeepCopyLabel = new JLabel();
                swordKeepCopyYesRadioButton = new JRadioButton();
                swordKeepCopyNoRadioButton = new JRadioButton();
                swordKeepCopyTextField = new JTextField();
                swordKeepCopyBrowseButton = new JButton();
                swordCancelButton = new JButton();
                swordSubmitButton = new JButton();
                swordMD5CheckBox = new JCheckBox();
                swordCorruptMD5CheckBox = new JCheckBox();
                swordNoOpCheckBox = new JCheckBox();
                swordVerboseCheckBox = new JCheckBox();
                jLabel2 = new JLabel();
                swordSlugHeaderTextField = new JTextField();
                swordPostLogPanel = new JPanel();
                swordLogLabel = new JLabel();
                jScrollPane4 = new JScrollPane();
                swordLogTextPane = new JTextPane();

                swordServiceDocDialog.setTitle("Get Service Document");
                swordServiceDocDialog.setMinimumSize(new Dimension(549, 208));
                swordServiceDocDialog.setModal(true);
                swordServiceDocDialog.setResizable(false);

                swordUsernameLabel.setFont(new Font("Tahoma", 1, 11));
                swordUsernameLabel.setText("Username: ");

                swordPasswordLabel.setFont(new Font("Tahoma", 1, 11));
                swordPasswordLabel.setText("Password: ");

                swordLocationLabel.setFont(new Font("Tahoma", 1, 11));
                swordLocationLabel.setText("Location: ");

                swordOnBehalfOfLabel.setFont(new Font("Tahoma", 1, 11));
                swordOnBehalfOfLabel.setText("On Behalf Of: ");

                getServiceDocButton.setText("Get Service Document");
                getServiceDocButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                getServiceDocButtonActionPerformed(evt);
                        }
                });

                getServiceDocCancelButton.setText("Cancel");
                getServiceDocCancelButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                getServiceDocCancelButtonActionPerformed(evt);
                        }
                });

                GroupLayout swordServiceDocDialogLayout = new GroupLayout(swordServiceDocDialog.getContentPane());
                swordServiceDocDialog.getContentPane().setLayout(swordServiceDocDialogLayout);
                swordServiceDocDialogLayout.setHorizontalGroup(
                        swordServiceDocDialogLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(swordServiceDocDialogLayout.createSequentialGroup()
                                .addGroup(swordServiceDocDialogLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(swordServiceDocDialogLayout.createSequentialGroup()
                                                .addGap(129, 129, 129)
                                                .addComponent(getServiceDocButton)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(getServiceDocCancelButton))
                                        .addGroup(swordServiceDocDialogLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(swordServiceDocDialogLayout.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(swordOnBehalfOfLabel, Alignment.TRAILING)
                                                        .addComponent(swordLocationLabel, Alignment.TRAILING)
                                                        .addComponent(swordPasswordLabel, Alignment.TRAILING)
                                                        .addComponent(swordUsernameLabel, Alignment.TRAILING))
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addGroup(swordServiceDocDialogLayout.createParallelGroup(Alignment.LEADING, false)
                                                        .addComponent(swordOnBehalfOfTextField)
                                                        .addComponent(swordLocationTextField)
                                                        .addComponent(swordPasswordField)
                                                        .addComponent(swordUsernameTextField, GroupLayout.PREFERRED_SIZE, 385, GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(76, Short.MAX_VALUE))
                );

                swordServiceDocDialogLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {getServiceDocButton, getServiceDocCancelButton});

                swordServiceDocDialogLayout.setVerticalGroup(
                        swordServiceDocDialogLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(swordServiceDocDialogLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(swordServiceDocDialogLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(swordUsernameLabel)
                                        .addComponent(swordUsernameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(swordServiceDocDialogLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(swordPasswordLabel)
                                        .addComponent(swordPasswordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(swordServiceDocDialogLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(swordLocationLabel)
                                        .addComponent(swordLocationTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(swordServiceDocDialogLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(swordOnBehalfOfLabel)
                                        .addComponent(swordOnBehalfOfTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                                .addGroup(swordServiceDocDialogLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(getServiceDocButton)
                                        .addComponent(getServiceDocCancelButton))
                                .addContainerGap())
                );

                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                setTitle("Hub and Spoke Workflow Manager");
                setMinimumSize(new Dimension(800, 600));

                mainTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
                mainTabbedPane.setPreferredSize(new Dimension(800, 600));

                getSplitPane.setDividerLocation(400);
                getSplitPane.setMaximumSize(null);
                getSplitPane.setMinimumSize(new Dimension(800, 600));
                getSplitPane.setPreferredSize(new Dimension(800, 600));

                getPanel.setMaximumSize(null);
                getPanel.setMinimumSize(new Dimension(400, 600));
                getPanel.setPreferredSize(new Dimension(400, 600));

                getCancelButton.setText("Cancel");
                getCancelButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                getCancelButtonActionPerformed(evt);
                        }
                });

                getSubmitButton.setText("Submit");
                getSubmitButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                getSubmitButtonActionPerformed(evt);
                        }
                });

                getCrudLabel.setFont(new Font("Tahoma", 1, 11));
                getCrudLabel.setText("URL of LRCRUD Service");

                getHandleLabel.setFont(new Font("Tahoma", 1, 11));
                getHandleLabel.setText("Package ID or Handle (Item)");

                getPackageDestLabel.setFont(new Font("Tahoma", 1, 11));
                getPackageDestLabel.setText("Package Export Destination");

                getPackageDestBrowseButton.setText("Browse");
                getPackageDestBrowseButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                getPackageDestBrowseButtonActionPerformed(evt);
                        }
                });

                getToHubClassLabel.setFont(new Font("Tahoma", 1, 11));
                getToHubClassLabel.setText("To-Hub Packager Class");

                getPackagerClassComboBox.setModel(getToHubPackagerTargets());

                getBagitLabel.setFont(new Font("Tahoma", 1, 11));
                getBagitLabel.setText("Save in Bagit format?");

                getBagitButtonGroup.add(getBagitYesRadioButton);
                getBagitYesRadioButton.setText("Yes");

                getBagitButtonGroup.add(getBagitNoRadioButton);
                getBagitNoRadioButton.setSelected(true);
                getBagitNoRadioButton.setText("No");

                getExportTypeLabel.setFont(new Font("Tahoma", 1, 11));
                getExportTypeLabel.setText("Export type - Item or List?");

                getExportTypeButtonGroup.add(getExportTypeItemRadioButton);
                getExportTypeItemRadioButton.setSelected(true);
                getExportTypeItemRadioButton.setText("Item");
                getExportTypeItemRadioButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                getExportTypeItemRadioButtonActionPerformed(evt);
                        }
                });

                getExportTypeButtonGroup.add(getExportTypeListRadioButton);
                getExportTypeListRadioButton.setText("List");
                getExportTypeListRadioButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                getExportTypeListRadioButtonActionPerformed(evt);
                        }
                });

                getListLocationLabel.setFont(new Font("Tahoma", 1, 11));
                getListLocationLabel.setText("List Location");

                getListLocationtBrowseButton.setText("Browse");
                getListLocationtBrowseButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                getListLocationtBrowseButtonActionPerformed(evt);
                        }
                });

                GroupLayout getPanelLayout = new GroupLayout(getPanel);
                getPanel.setLayout(getPanelLayout);
                getPanelLayout.setHorizontalGroup(
                        getPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(getPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(getPanelLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(getPanelLayout.createSequentialGroup()
                                                .addComponent(getExportTypeItemRadioButton)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(getExportTypeListRadioButton))
                                        .addComponent(getCrudLabel)
                                        .addComponent(getExportTypeLabel)
                                        .addComponent(getHandleLabel)
                                        .addComponent(getListLocationLabel)
                                        .addComponent(getToHubClassLabel)
                                        .addComponent(getPackageDestLabel)
                                        .addGroup(getPanelLayout.createSequentialGroup()
                                                .addGroup(getPanelLayout.createParallelGroup(Alignment.TRAILING, false)
                                                        .addComponent(getListLocationTextField, Alignment.LEADING)
                                                        .addComponent(getPackageDestTextField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addGroup(getPanelLayout.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(getListLocationtBrowseButton)
                                                        .addComponent(getPackageDestBrowseButton)))
                                        .addComponent(getCrudTextField, GroupLayout.PREFERRED_SIZE, 360, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(getPanelLayout.createParallelGroup(Alignment.TRAILING)
                                                .addGroup(getPanelLayout.createSequentialGroup()
                                                        .addComponent(getCancelButton)
                                                        .addPreferredGap(ComponentPlacement.RELATED)
                                                        .addComponent(getSubmitButton))
                                                .addComponent(getPackagerClassComboBox, GroupLayout.PREFERRED_SIZE, 361, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(getPanelLayout.createSequentialGroup()
                                                .addComponent(getBagitYesRadioButton)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(getBagitNoRadioButton))
                                        .addComponent(getBagitLabel)
                                        .addComponent(getHandleTextField, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(28, Short.MAX_VALUE))
                );

                getPanelLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {getCrudTextField, getPackagerClassComboBox});

                getPanelLayout.setVerticalGroup(
                        getPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(getPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(getCrudLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(getCrudTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(getExportTypeLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(getPanelLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(getExportTypeItemRadioButton)
                                        .addComponent(getExportTypeListRadioButton))
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(getHandleLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(getHandleTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(getListLocationLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(getPanelLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(getListLocationtBrowseButton)
                                        .addComponent(getListLocationTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(getPackageDestLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(getPanelLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(getPackageDestTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(getPackageDestBrowseButton))
                                .addGap(18, 18, 18)
                                .addComponent(getToHubClassLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(getPackagerClassComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(getBagitLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(getPanelLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(getBagitYesRadioButton)
                                        .addComponent(getBagitNoRadioButton))
                                .addGap(35, 35, 35)
                                .addGroup(getPanelLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(getCancelButton)
                                        .addComponent(getSubmitButton))
                                .addContainerGap(132, Short.MAX_VALUE))
                );

                getSplitPane.setLeftComponent(getPanel);

                getLogPanel.setPreferredSize(new Dimension(400, 600));

                getLogLabel.setFont(new Font("Tahoma", 1, 11));
                getLogLabel.setText("Log");

                jScrollPane2.setViewportView(getLogTextPane);

                GroupLayout getLogPanelLayout = new GroupLayout(getLogPanel);
                getLogPanel.setLayout(getLogPanelLayout);
                getLogPanelLayout.setHorizontalGroup(
                        getLogPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(getLogPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(getLogPanelLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                                        .addComponent(getLogLabel))
                                .addContainerGap())
                );
                getLogPanelLayout.setVerticalGroup(
                        getLogPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(getLogPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(getLogLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
                                .addContainerGap())
                );

                getSplitPane.setRightComponent(getLogPanel);

                mainTabbedPane.addTab("GET", getSplitPane);

                putSplitPane.setBorder(null);
                putSplitPane.setDividerLocation(400);
                putSplitPane.setMaximumSize(null);
                putSplitPane.setMinimumSize(new Dimension(800, 600));
                putSplitPane.setPreferredSize(new Dimension(800, 600));

                putPanel.setMaximumSize(null);
                putPanel.setMinimumSize(new Dimension(400, 600));
                putPanel.setPreferredSize(new Dimension(400, 600));

                putPackagerClassComboBox.setModel(putFromHubPackagerTargets());

                putFromHubClassLabel.setFont(new Font("Tahoma", 1, 11));
                putFromHubClassLabel.setText("From-Hub Packager Class");

                putPackageSourceLabel.setFont(new Font("Tahoma", 1, 11));
                putPackageSourceLabel.setText("Source Location");

                putPackageSourceBrowseButton.setText("Browse");
                putPackageSourceBrowseButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                putPackageSourceBrowseButtonActionPerformed(evt);
                        }
                });

                putCrudLabel.setFont(new Font("Tahoma", 1, 11));
                putCrudLabel.setText("URL of LRCRUD Service");

                putHandleLabel.setFont(new Font("Tahoma", 1, 11));
                putHandleLabel.setText("Collection ID or Handle");

                putSubmitButton.setText("Submit");
                putSubmitButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                putSubmitButtonActionPerformed(evt);
                        }
                });

                putKeepCopyLabel.setFont(new Font("Tahoma", 1, 11));
                putKeepCopyLabel.setText("Keep a Local Copy of Repository Packages?");

                putKeepZipsButtonGroup.add(putKeepCopyYesRadioButton);
                putKeepCopyYesRadioButton.setText("Yes");
                putKeepCopyYesRadioButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                putKeepCopyYesRadioButtonActionPerformed(evt);
                        }
                });

                putKeepZipsButtonGroup.add(putKeepCopyNoRadioButton);
                putKeepCopyNoRadioButton.setSelected(true);
                putKeepCopyNoRadioButton.setText("No");
                putKeepCopyNoRadioButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                putKeepCopyNoRadioButtonActionPerformed(evt);
                        }
                });

                putKeepCopyBrowseButton.setText("Browse");
                putKeepCopyBrowseButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                putKeepCopyBrowseButtonActionPerformed(evt);
                        }
                });

                putCancelButton.setText("Cancel");
                putCancelButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                putCancelButtonActionPerformed(evt);
                        }
                });

                putPackageSourceTypeLabel.setFont(new Font("Tahoma", 1, 11));
                putPackageSourceTypeLabel.setText("Source Type");

                putSourceTypeButtonGroup.add(putPackageSourceTypeMETSRadioButton);
                putPackageSourceTypeMETSRadioButton.setSelected(true);
                putPackageSourceTypeMETSRadioButton.setText("METS file");

                putSourceTypeButtonGroup.add(putPackageSourceTypeListRadioButton);
                putPackageSourceTypeListRadioButton.setText("List (text file)");

                putSourceTypeButtonGroup.add(putPackageSourceTypeDirRadioButton);
                putPackageSourceTypeDirRadioButton.setText("Directory Crawl");

                putSourceTypeButtonGroup.add(putPackageSourceTypeZipRadioButton);
                putPackageSourceTypeZipRadioButton.setText("Zip file");

                GroupLayout putPanelLayout = new GroupLayout(putPanel);
                putPanel.setLayout(putPanelLayout);
                putPanelLayout.setHorizontalGroup(
                        putPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(putPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(putPanelLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(putCrudLabel)
                                        .addComponent(putHandleLabel)
                                        .addComponent(putHandleTextField, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(putPanelLayout.createSequentialGroup()
                                                .addComponent(putPackageSourceTypeMETSRadioButton)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(putPackageSourceTypeZipRadioButton)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(putPackageSourceTypeListRadioButton)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(putPackageSourceTypeDirRadioButton))
                                        .addComponent(putPackageSourceTypeLabel)
                                        .addComponent(putPackageSourceLabel)
                                        .addComponent(putFromHubClassLabel)
                                        .addGroup(putPanelLayout.createSequentialGroup()
                                                .addComponent(putKeepCopyYesRadioButton)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(putKeepCopyNoRadioButton))
                                        .addComponent(putKeepCopyLabel)
                                        .addComponent(putCrudTextField, GroupLayout.PREFERRED_SIZE, 368, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(putPanelLayout.createSequentialGroup()
                                                .addComponent(putKeepCopyTextField, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(putKeepCopyBrowseButton))
                                        .addGroup(putPanelLayout.createSequentialGroup()
                                                .addGap(227, 227, 227)
                                                .addComponent(putCancelButton)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(putSubmitButton))
                                        .addGroup(putPanelLayout.createSequentialGroup()
                                                .addComponent(putPackageSourceTextField, GroupLayout.PREFERRED_SIZE, 286, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(putPackageSourceBrowseButton))
                                        .addComponent(putPackagerClassComboBox, GroupLayout.PREFERRED_SIZE, 346, GroupLayout.PREFERRED_SIZE))
                                .addGap(22, 22, 22))
                );
                putPanelLayout.setVerticalGroup(
                        putPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(putPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(putCrudLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(putCrudTextField)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(putHandleLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(putHandleTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(putPackageSourceTypeLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(putPanelLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(putPackageSourceTypeMETSRadioButton)
                                        .addComponent(putPackageSourceTypeListRadioButton)
                                        .addComponent(putPackageSourceTypeDirRadioButton)
                                        .addComponent(putPackageSourceTypeZipRadioButton))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(putPackageSourceLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(putPanelLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(putPackageSourceTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(putPackageSourceBrowseButton))
                                .addGap(18, 18, 18)
                                .addComponent(putFromHubClassLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(putPackagerClassComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(putKeepCopyLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(putPanelLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(putKeepCopyYesRadioButton)
                                        .addComponent(putKeepCopyNoRadioButton))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(putPanelLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(putKeepCopyTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(putKeepCopyBrowseButton))
                                .addGap(53, 53, 53)
                                .addGroup(putPanelLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(putCancelButton)
                                        .addComponent(putSubmitButton))
                                .addGap(185, 185, 185))
                );

                putSplitPane.setLeftComponent(putPanel);

                putLogPanel.setPreferredSize(new Dimension(400, 600));

                putLogLabel.setFont(new Font("Tahoma", 1, 11));
                putLogLabel.setText("Log");

                jScrollPane1.setViewportView(putLogTextPane);

                GroupLayout putLogPanelLayout = new GroupLayout(putLogPanel);
                putLogPanel.setLayout(putLogPanelLayout);
                putLogPanelLayout.setHorizontalGroup(
                        putLogPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(putLogPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(putLogPanelLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                                        .addComponent(putLogLabel))
                                .addContainerGap())
                );
                putLogPanelLayout.setVerticalGroup(
                        putLogPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(putLogPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(putLogLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                                .addContainerGap())
                );

                putSplitPane.setRightComponent(putLogPanel);

                mainTabbedPane.addTab("PUT", putSplitPane);

                migrateSplitPane.setDividerLocation(400);
                migrateSplitPane.setMaximumSize(null);
                migrateSplitPane.setMinimumSize(new Dimension(800, 600));
                migrateSplitPane.setPreferredSize(new Dimension(800, 600));

                migratePanel.setMaximumSize(null);
                migratePanel.setMinimumSize(new Dimension(400, 600));
                migratePanel.setPreferredSize(new Dimension(400, 600));

                migrateFromLabel.setFont(new Font("Tahoma", 3, 12));
                migrateFromLabel.setText("From");

                migrateFromCrudLabel.setFont(new Font("Tahoma", 1, 11));
                migrateFromCrudLabel.setText("URL of LRCRUD Service");

                migrateFromExportTypeLabel.setFont(new Font("Tahoma", 1, 11));
                migrateFromExportTypeLabel.setText("Export type - Item or List?");

                migrateExportTypeButtonGroup.add(migrateFromExportTypeItemRadioButton);
                migrateFromExportTypeItemRadioButton.setSelected(true);
                migrateFromExportTypeItemRadioButton.setText("Item");
                migrateFromExportTypeItemRadioButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                migrateFromExportTypeItemRadioButtonActionPerformed(evt);
                        }
                });

                migrateExportTypeButtonGroup.add(migrateFromExportTypeListRadioButton);
                migrateFromExportTypeListRadioButton.setText("List");
                migrateFromExportTypeListRadioButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                migrateFromExportTypeListRadioButtonActionPerformed(evt);
                        }
                });

                migrateFromHandleLabel.setFont(new Font("Tahoma", 1, 11));
                migrateFromHandleLabel.setText("Package ID or Handle (Item)");

                migrateFromListLocationLabel.setFont(new Font("Tahoma", 1, 11));
                migrateFromListLocationLabel.setText("List Location");

                migrateFromListLocationBrowseButton.setText("Browse");
                migrateFromListLocationBrowseButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                migrateFromListLocationBrowseButtonActionPerformed(evt);
                        }
                });

                migrateFromToHubClassLabel.setFont(new Font("Tahoma", 1, 11));
                migrateFromToHubClassLabel.setText("To-Hub Packager Class");

                migrateFromPackagerClassComboBox.setModel(getToHubPackagerTargets());

                migrateToLabel.setFont(new Font("Tahoma", 3, 12));
                migrateToLabel.setText("To");

                migrateToCrudLabel.setFont(new Font("Tahoma", 1, 11));
                migrateToCrudLabel.setText("URL of LRCRUD Service");

                migrateToHandleLabel.setFont(new Font("Tahoma", 1, 11));
                migrateToHandleLabel.setText("Collection ID or Handle");

                migrateToFromHubClassLabel.setFont(new Font("Tahoma", 1, 11));
                migrateToFromHubClassLabel.setText("From-Hub Packager Class");

                migrateToPackagerClassComboBox.setModel(putFromHubPackagerTargets());

                migrateToKeepCopyLabel.setFont(new Font("Tahoma", 1, 11));
                migrateToKeepCopyLabel.setText("Keep a Local Copy of Repository Packages?");

                migrateKeepZipsButtonGroup.add(migrateToKeepCopyYesRadioButton);
                migrateToKeepCopyYesRadioButton.setText("Yes");
                migrateToKeepCopyYesRadioButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                migrateToKeepCopyYesRadioButtonActionPerformed(evt);
                        }
                });

                migrateKeepZipsButtonGroup.add(migrateToKeepCopyNoRadioButton);
                migrateToKeepCopyNoRadioButton.setSelected(true);
                migrateToKeepCopyNoRadioButton.setText("No");
                migrateToKeepCopyNoRadioButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                migrateToKeepCopyNoRadioButtonActionPerformed(evt);
                        }
                });

                migrateToKeepCopyBrowseButton.setText("Browse");
                migrateToKeepCopyBrowseButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                migrateToKeepCopyBrowseButtonActionPerformed(evt);
                        }
                });

                migrateCancelButton.setText("Cancel");
                migrateCancelButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                migrateCancelButtonActionPerformed(evt);
                        }
                });

                migrateSubmitButton.setText("Submit");
                migrateSubmitButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                migrateSubmitButtonActionPerformed(evt);
                        }
                });

                GroupLayout migratePanelLayout = new GroupLayout(migratePanel);
                migratePanel.setLayout(migratePanelLayout);
                migratePanelLayout.setHorizontalGroup(
                        migratePanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(migratePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(migratePanelLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(migrateFromLabel)
                                        .addComponent(migrateFromCrudLabel)
                                        .addComponent(migrateFromListLocationLabel)
                                        .addComponent(migrateFromToHubClassLabel)
                                        .addComponent(migrateFromCrudTextField, GroupLayout.PREFERRED_SIZE, 354, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(migratePanelLayout.createSequentialGroup()
                                                .addComponent(migrateFromListLocationTextField, GroupLayout.PREFERRED_SIZE, 298, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(migrateFromListLocationBrowseButton))
                                        .addComponent(migrateFromPackagerClassComboBox, GroupLayout.PREFERRED_SIZE, 366, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(migratePanelLayout.createParallelGroup(Alignment.LEADING)
                                                .addComponent(migrateToLabel)
                                                .addComponent(migrateToCrudLabel)
                                                .addComponent(migrateToKeepCopyLabel)
                                                .addComponent(migrateToPackagerClassComboBox, GroupLayout.PREFERRED_SIZE, 339, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(migrateToCrudTextField, GroupLayout.PREFERRED_SIZE, 376, GroupLayout.PREFERRED_SIZE)
                                                .addGroup(migratePanelLayout.createSequentialGroup()
                                                        .addComponent(migrateToKeepCopyYesRadioButton)
                                                        .addPreferredGap(ComponentPlacement.RELATED)
                                                        .addComponent(migrateToKeepCopyNoRadioButton))
                                                .addComponent(migrateToHandleLabel)
                                                .addComponent(migrateToFromHubClassLabel)
                                                .addComponent(migrateToHandleTextField, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
                                                .addGroup(Alignment.TRAILING, migratePanelLayout.createSequentialGroup()
                                                        .addComponent(migrateToKeepCopyTextField, GroupLayout.PREFERRED_SIZE, 299, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(ComponentPlacement.RELATED)
                                                        .addComponent(migrateToKeepCopyBrowseButton)
                                                        .addGap(107, 107, 107)))
                                        .addComponent(migrateSeparator, GroupLayout.PREFERRED_SIZE, 342, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(migratePanelLayout.createSequentialGroup()
                                                .addGroup(migratePanelLayout.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(migrateFromHandleTextField, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(migrateFromHandleLabel))
                                                .addGap(18, 18, 18)
                                                .addGroup(migratePanelLayout.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(migratePanelLayout.createSequentialGroup()
                                                                .addComponent(migrateFromExportTypeItemRadioButton)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(migrateFromExportTypeListRadioButton))
                                                        .addComponent(migrateFromExportTypeLabel)))
                                        .addGroup(migratePanelLayout.createSequentialGroup()
                                                .addGap(208, 208, 208)
                                                .addComponent(migrateCancelButton)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(migrateSubmitButton)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                migratePanelLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {migrateFromCrudTextField, migrateFromPackagerClassComboBox, migrateSeparator, migrateToCrudTextField, migrateToPackagerClassComboBox});

                migratePanelLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {migrateFromListLocationTextField, migrateToKeepCopyTextField});

                migratePanelLayout.setVerticalGroup(
                        migratePanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(migratePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(migrateFromLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(migrateFromCrudLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(migrateFromCrudTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(migratePanelLayout.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(migratePanelLayout.createSequentialGroup()
                                                .addComponent(migrateFromHandleLabel)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(migrateFromHandleTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(migratePanelLayout.createSequentialGroup()
                                                .addComponent(migrateFromExportTypeLabel)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addGroup(migratePanelLayout.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(migrateFromExportTypeItemRadioButton)
                                                        .addComponent(migrateFromExportTypeListRadioButton))))
                                .addGap(3, 3, 3)
                                .addComponent(migrateFromListLocationLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(migratePanelLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(migrateFromListLocationTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(migrateFromListLocationBrowseButton))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(migrateFromToHubClassLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(migrateFromPackagerClassComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23)
                                .addComponent(migrateSeparator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(migrateToLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(migrateToCrudLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(migrateToCrudTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(migrateToHandleLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(migrateToHandleTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(migrateToFromHubClassLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(migrateToPackagerClassComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(migrateToKeepCopyLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(migratePanelLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(migrateToKeepCopyYesRadioButton)
                                        .addComponent(migrateToKeepCopyNoRadioButton))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(migratePanelLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(migrateToKeepCopyTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(migrateToKeepCopyBrowseButton))
                                .addGap(28, 28, 28)
                                .addGroup(migratePanelLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(migrateCancelButton)
                                        .addComponent(migrateSubmitButton))
                                .addContainerGap(35, Short.MAX_VALUE))
                );

                migrateSplitPane.setLeftComponent(migratePanel);

                migrateLogPanel.setPreferredSize(new Dimension(400, 600));

                migrateLogLabel.setFont(new Font("Tahoma", 1, 11));
                migrateLogLabel.setText("Log");

                jScrollPane3.setViewportView(migrateLogTextPane);

                GroupLayout migrateLogPanelLayout = new GroupLayout(migrateLogPanel);
                migrateLogPanel.setLayout(migrateLogPanelLayout);
                migrateLogPanelLayout.setHorizontalGroup(
                        migrateLogPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(migrateLogPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(migrateLogPanelLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                                        .addComponent(migrateLogLabel))
                                .addContainerGap())
                );
                migrateLogPanelLayout.setVerticalGroup(
                        migrateLogPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(migrateLogPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(migrateLogLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
                                .addContainerGap())
                );

                migrateSplitPane.setRightComponent(migrateLogPanel);

                mainTabbedPane.addTab("MIGRATE", migrateSplitPane);

                swordSplitPane.setDividerLocation(200);
                swordSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
                swordSplitPane.setMaximumSize(null);
                swordSplitPane.setMinimumSize(new Dimension(800, 600));
                swordSplitPane.setPreferredSize(new Dimension(800, 600));

                swordServicePane.setDividerLocation(300);

                swordGetServiceDocButton.setText("Get Service Document");
                swordGetServiceDocButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                swordGetServiceDocButtonActionPerformed(evt);
                        }
                });

                swordServicesTree.setModel(swordServicesTreeModel);
                swordServicesTree.addTreeSelectionListener(this);
                swordServiceTreeScrollPane.setViewportView(swordServicesTree);

                GroupLayout swordServiceTreePanelLayout = new GroupLayout(swordServiceTreePanel);
                swordServiceTreePanel.setLayout(swordServiceTreePanelLayout);
                swordServiceTreePanelLayout.setHorizontalGroup(
                        swordServiceTreePanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(swordServiceTreePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(swordServiceTreePanelLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(swordServiceTreeScrollPane, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                                        .addComponent(swordGetServiceDocButton))
                                .addContainerGap())
                );
                swordServiceTreePanelLayout.setVerticalGroup(
                        swordServiceTreePanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(swordServiceTreePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(swordGetServiceDocButton)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(swordServiceTreeScrollPane, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                                .addContainerGap())
                );

                swordServicePane.setLeftComponent(swordServiceTreePanel);

                swordServiceEditorScrollPane.setViewportView(swordServiceDetailsEditorPane);

                GroupLayout swordServiceEditorPanelLayout = new GroupLayout(swordServiceEditorPanel);
                swordServiceEditorPanel.setLayout(swordServiceEditorPanelLayout);
                swordServiceEditorPanelLayout.setHorizontalGroup(
                        swordServiceEditorPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(swordServiceEditorPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(swordServiceEditorScrollPane)
                                .addContainerGap())
                );
                swordServiceEditorPanelLayout.setVerticalGroup(
                        swordServiceEditorPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(swordServiceEditorPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(swordServiceEditorScrollPane, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                                .addContainerGap())
                );

                swordServicePane.setRightComponent(swordServiceEditorPanel);

                swordSplitPane.setTopComponent(swordServicePane);

                swordPostPanel.setMaximumSize(null);
                swordPostPanel.setMinimumSize(new Dimension(400, 300));
                swordPostPanel.setPreferredSize(new Dimension(400, 300));

                swordCollectionLocationLabel.setFont(new Font("Tahoma", 1, 11));
                swordCollectionLocationLabel.setText("Collection Location URL");

                swordCollectionLocationComboBox.setFont(new Font("Tahoma", 0, 10));

                swordPackageSourceTypeLabel.setFont(new Font("Tahoma", 1, 11));
                swordPackageSourceTypeLabel.setText("Source Type");

                swordSourceTypeButtonGroup.add(swordPackageSourceTypeMETSRadioButton);
                swordPackageSourceTypeMETSRadioButton.setSelected(true);
                swordPackageSourceTypeMETSRadioButton.setText("METS file");

                swordSourceTypeButtonGroup.add(swordPackageSourceTypeZipRadioButton);
                swordPackageSourceTypeZipRadioButton.setText("Zip file");

                swordSourceTypeButtonGroup.add(swordPackageSourceTypeListRadioButton);
                swordPackageSourceTypeListRadioButton.setText("List (text file)");

                swordSourceTypeButtonGroup.add(swordPackageSourceTypeDirRadioButton);
                swordPackageSourceTypeDirRadioButton.setText("Directory Crawl");

                swordPackageSourceLabel.setFont(new Font("Tahoma", 1, 11));
                swordPackageSourceLabel.setText("Source Location");

                swordPackageSourceBrowseButton.setText("Browse");
                swordPackageSourceBrowseButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                swordPackageSourceBrowseButtonActionPerformed(evt);
                        }
                });

                swordKeepCopyLabel.setFont(new Font("Tahoma", 1, 11));
                swordKeepCopyLabel.setText("Keep a Local Copy of Repository Packages?");

                swordKeepZipsButtonGroup.add(swordKeepCopyYesRadioButton);
                swordKeepCopyYesRadioButton.setText("Yes");
                swordKeepCopyYesRadioButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                swordKeepCopyYesRadioButtonActionPerformed(evt);
                        }
                });

                swordKeepZipsButtonGroup.add(swordKeepCopyNoRadioButton);
                swordKeepCopyNoRadioButton.setSelected(true);
                swordKeepCopyNoRadioButton.setText("No");
                swordKeepCopyNoRadioButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                swordKeepCopyNoRadioButtonActionPerformed(evt);
                        }
                });

                swordKeepCopyBrowseButton.setText("Browse");
                swordKeepCopyBrowseButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                swordKeepCopyBrowseButtonActionPerformed(evt);
                        }
                });

                swordCancelButton.setText("Cancel");
                swordCancelButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                swordCancelButtonActionPerformed(evt);
                        }
                });

                swordSubmitButton.setText("Submit");
                swordSubmitButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                                swordSubmitButtonActionPerformed(evt);
                        }
                });

                swordMD5CheckBox.setText("Use MD5");

                swordCorruptMD5CheckBox.setText("Corrupt MD5");

                swordNoOpCheckBox.setText("Use noOp");

                swordVerboseCheckBox.setText("Use verbose");

                jLabel2.setFont(new Font("Tahoma", 1, 11));
                jLabel2.setText("Slug Header");

                GroupLayout swordPostPanelLayout = new GroupLayout(swordPostPanel);
                swordPostPanel.setLayout(swordPostPanelLayout);
                swordPostPanelLayout.setHorizontalGroup(
                        swordPostPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(swordPostPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(swordPostPanelLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(swordPostPanelLayout.createSequentialGroup()
                                                .addComponent(swordMD5CheckBox)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(swordCorruptMD5CheckBox)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(swordNoOpCheckBox)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(swordVerboseCheckBox))
                                        .addComponent(swordCollectionLocationLabel)
                                        .addGroup(swordPostPanelLayout.createSequentialGroup()
                                                .addComponent(swordPackageSourceTypeMETSRadioButton)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(swordPackageSourceTypeZipRadioButton)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(swordPackageSourceTypeListRadioButton)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(swordPackageSourceTypeDirRadioButton))
                                        .addComponent(swordPackageSourceTypeLabel)
                                        .addGroup(swordPostPanelLayout.createParallelGroup(Alignment.TRAILING, false)
                                                .addGroup(Alignment.LEADING, swordPostPanelLayout.createSequentialGroup()
                                                        .addComponent(jLabel2)
                                                        .addPreferredGap(ComponentPlacement.RELATED)
                                                        .addComponent(swordSlugHeaderTextField))
                                                .addGroup(Alignment.LEADING, swordPostPanelLayout.createSequentialGroup()
                                                        .addComponent(swordKeepCopyYesRadioButton)
                                                        .addPreferredGap(ComponentPlacement.RELATED)
                                                        .addComponent(swordKeepCopyNoRadioButton))
                                                .addComponent(swordKeepCopyLabel, Alignment.LEADING)
                                                .addGroup(Alignment.LEADING, swordPostPanelLayout.createSequentialGroup()
                                                        .addComponent(swordKeepCopyTextField, GroupLayout.PREFERRED_SIZE, 293, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(ComponentPlacement.RELATED)
                                                        .addComponent(swordKeepCopyBrowseButton))
                                                .addGroup(swordPostPanelLayout.createSequentialGroup()
                                                        .addComponent(swordCancelButton)
                                                        .addPreferredGap(ComponentPlacement.RELATED)
                                                        .addComponent(swordSubmitButton))
                                                .addGroup(swordPostPanelLayout.createSequentialGroup()
                                                        .addGroup(swordPostPanelLayout.createParallelGroup(Alignment.TRAILING)
                                                                .addComponent(swordCollectionLocationComboBox, Alignment.LEADING, 0, 366, Short.MAX_VALUE)
                                                                .addGroup(swordPostPanelLayout.createSequentialGroup()
                                                                        .addGroup(swordPostPanelLayout.createParallelGroup(Alignment.LEADING)
                                                                                .addComponent(swordPackageSourceLabel)
                                                                                .addComponent(swordPackageSourceTextField, GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE))
                                                                        .addPreferredGap(ComponentPlacement.RELATED)
                                                                        .addComponent(swordPackageSourceBrowseButton)))
                                                        .addPreferredGap(ComponentPlacement.RELATED))))
                                .addContainerGap(24, Short.MAX_VALUE))
                );
                swordPostPanelLayout.setVerticalGroup(
                        swordPostPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(swordPostPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(swordCollectionLocationLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(swordCollectionLocationComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(swordPackageSourceTypeLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(swordPostPanelLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(swordPackageSourceTypeMETSRadioButton)
                                        .addComponent(swordPackageSourceTypeListRadioButton)
                                        .addComponent(swordPackageSourceTypeDirRadioButton)
                                        .addComponent(swordPackageSourceTypeZipRadioButton))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(swordPackageSourceLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(swordPostPanelLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(swordPackageSourceTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(swordPackageSourceBrowseButton))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(swordKeepCopyLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(swordPostPanelLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(swordKeepCopyYesRadioButton)
                                        .addComponent(swordKeepCopyNoRadioButton))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(swordPostPanelLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(swordKeepCopyTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(swordKeepCopyBrowseButton))
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(swordPostPanelLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(swordMD5CheckBox)
                                        .addComponent(swordNoOpCheckBox)
                                        .addComponent(swordVerboseCheckBox)
                                        .addComponent(swordCorruptMD5CheckBox))
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(swordPostPanelLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(swordSlugHeaderTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(swordPostPanelLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(swordCancelButton)
                                        .addComponent(swordSubmitButton))
                                .addContainerGap(56, Short.MAX_VALUE))
                );

                swordPostPane.setLeftComponent(swordPostPanel);

                swordLogLabel.setFont(new Font("Tahoma", 1, 11));
                swordLogLabel.setText("Log");

                jScrollPane4.setViewportView(swordLogTextPane);

                GroupLayout swordPostLogPanelLayout = new GroupLayout(swordPostLogPanel);
                swordPostLogPanel.setLayout(swordPostLogPanelLayout);
                swordPostLogPanelLayout.setHorizontalGroup(
                        swordPostLogPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(swordPostLogPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(swordPostLogPanelLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(jScrollPane4, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                                        .addComponent(swordLogLabel))
                                .addContainerGap())
                );
                swordPostLogPanelLayout.setVerticalGroup(
                        swordPostLogPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(swordPostLogPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(swordLogLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                                .addContainerGap())
                );

                swordPostPane.setRightComponent(swordPostLogPanel);

                swordSplitPane.setRightComponent(swordPostPane);

                mainTabbedPane.addTab("SWORD", swordSplitPane);

                GroupLayout layout = new GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(Alignment.LEADING)
                        .addComponent(mainTabbedPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(Alignment.LEADING)
                        .addComponent(mainTabbedPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                );

                mainTabbedPane.getAccessibleContext().setAccessibleName("");

                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                setBounds((screenSize.width-808)/2, (screenSize.height-634)/2, 808, 634);
        }// </editor-fold>//GEN-END:initComponents

private void swordSubmitButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_swordSubmitButtonActionPerformed


    SwingWorker worker = new SwingWorker<Void, Void>() {

	@Override
	protected void done() {
	    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    swordSubmitButton.setEnabled(true);
	}

	@Override
	protected Void doInBackground() {
	    JTextPaneAppender swordAppender = new JTextPaneAppender(
		    swordLogTextPane);
	    logger.addAppender(swordAppender);

	    setCursor(new Cursor(Cursor.WAIT_CURSOR));
	    swordSubmitButton.setEnabled(false);

	    String location;
	    String source_type;
	    String zip_dest = tempdir.getPath();
	    String packager_class_name;
	    boolean keep_zip = false;

	    Stack<File> fileStack = new Stack<File>();
	    Stack<File> dirStack = new Stack<File>();
	    Stack<HaSMasterMETSProfile> mets_stack =
		    new Stack<HaSMasterMETSProfile>();


	    /* LRCRUD Client */

	    if (swordCollectionLocationComboBox.getSelectedItem() != null) {
		location = swordCollectionLocationComboBox.getSelectedItem().
			toString();
	    } else {
		JOptionPane.showMessageDialog(null,
			"No collection location selected.");
		logger.removeAppender(swordAppender);
		return null;
	    }


	    /* Source Type */

	    if (swordPackageSourceTypeDirRadioButton.isSelected()) {
		source_type = "DIR";
	    } else if (swordPackageSourceTypeListRadioButton.isSelected()) {
		source_type = "LIST";
	    } else if (swordPackageSourceTypeZipRadioButton.isSelected()) {
		source_type = "ZIP";
	    } else {
		source_type = "METS";
	    }


	    /* Package Source */

	    if (!swordPackageSourceTextField.getText().trim().equals("")) {
		File start_loc = new File(swordPackageSourceTextField.getText());
		int count = 0;

		if (source_type.compareToIgnoreCase("METS") == 0) {
		    if (!(start_loc.getName().compareToIgnoreCase(
			    HaSConstants.MASTER_METS_FILE_NAME) == 0 ||
			    start_loc.getName().compareToIgnoreCase(
			    HaSConstants.METS_FILE_NAME) == 0)) {
			JOptionPane.showMessageDialog(null,
				"Source Location cannot be a directory if Source Type is METS");
			logger.removeAppender(swordAppender);
			return null;
		    } else {
			// push it on the file stack
			fileStack.push(start_loc);
		    }
		}
		if (source_type.compareToIgnoreCase("ZIP") == 0) {
		    if (!start_loc.getName().endsWith(".zip")) {
			JOptionPane.showMessageDialog(null,
				"Source Location must be a zip file if Source Type is Zip file");
			logger.removeAppender(swordAppender);
			return null;
		    } else {
			// push it on the file stack
			fileStack.push(start_loc);
		    }
		}
		if (source_type.compareToIgnoreCase("LIST") == 0) {
		    if (!start_loc.getName().endsWith(".zip")) {
			JOptionPane.showMessageDialog(null,
				"Source Location cannot be a directory if Source Type is List");
			logger.removeAppender(swordAppender);
			return null;
		    } else {
			BufferedReader input = null;
			try {
			    input = new BufferedReader(
				    new FileReader(start_loc));
			    String line = null;

			    // read the file line-by-line
			    while ((line = input.readLine()) != null) {

				if (line.charAt(0) != '\'') {
				    // skip lines beginning with a single quote

				    File nextFile =
					    new File(line.toString());

				    if (!nextFile.exists()) {
					logger.error(
						"File processing error: " +
						line.toString() +
						" does not exist.");
				    } else if (!nextFile.isAbsolute()) {
					logger.error(
						"File processing error: " +
						line.toString() +
						" is not an absolute filename.");
				    } else if (nextFile.getName().
					    compareToIgnoreCase(
					    HaSConstants.MASTER_METS_FILE_NAME) ==
					    0 ||
					    nextFile.getName().
					    compareToIgnoreCase(
					    HaSConstants.METS_FILE_NAME) ==
					    0 || nextFile.getName().
					    endsWith(".zip")) {

					// push it on the file stack
					fileStack.push(
						nextFile);
				    } else {
					logger.error("File processing error: " +
						"cannot create a Hub package at " +
						nextFile.getPath() +
						".");
				    }
				}
			    }
			} catch (FileNotFoundException e) {
			    JOptionPane.showMessageDialog(null,
				    "File not found: " + start_loc.
				    getAbsolutePath());
			    logger.removeAppender(swordAppender);
			    return null;
			} catch (IOException e) {
			    JOptionPane.showMessageDialog(null,
				    "Error opening file: " +
				    start_loc.getAbsolutePath());
			    logger.removeAppender(swordAppender);
			    return null;
			}
			try {
			    if (input != null) {
				input.close();
			    }
			} catch (IOException e) {
			    e.printStackTrace();
			}
		    }
		}
		if (source_type.compareToIgnoreCase("DIR") == 0) {
		    if (!start_loc.isDirectory()) {
			JOptionPane.showMessageDialog(null,
				"Source Location must be a directory if Source Type is Directory Crawl");
			logger.removeAppender(swordAppender);
			return null;
		    } else {
			dirStack.push(start_loc);

			while (!dirStack.isEmpty()) {
			    File[] filesAndDirs = dirStack.pop().
				    listFiles();
			    for (File file : filesAndDirs) {
				if (file.isDirectory()) {
				    dirStack.push(file);
				}
				if (file.getName().
					compareToIgnoreCase(
					HaSConstants.MASTER_METS_FILE_NAME) ==
					0 ||
					file.getName().
					compareToIgnoreCase(
					HaSConstants.METS_FILE_NAME) ==
					0 || file.getName().
					endsWith(".zip")) {

				    // push it on the file stack
				    fileStack.push(file);
				}
			    }
			}
		    }
		}


		// go through the file stack, create Hub packages,
		// push mastermets onto the mets stack
		while (!fileStack.isEmpty()) {
		    File f = fileStack.pop();

		    if (f.getName().compareToIgnoreCase(
			    HaSConstants.MASTER_METS_FILE_NAME) == 0 || f.
			    getName().
			    compareToIgnoreCase(
			    HaSConstants.METS_FILE_NAME) == 0) {

			// it's a mets file, so create hub package and push the
			// master mets
			try {
			    // first, move the package to a temp directory
			    File working_dir = new File(tempdir, f.getParentFile().
				    getName());
			    HaSFileUtils.copyDirectory(f.getParentFile(),
				    working_dir);

			    HaSMasterMETSProfile mm =
				    HaSMasterMETSProfileFactory.newInstance(
				    working_dir.getPath());
			    if (mm != null) {
				mets_stack.push(mm);
				count++;
			    }
			} catch (IOException e) {
			    logger.error("Error creating Hub package for: " +
				    f.getName());
			}
		    } else if (f.getName().endsWith(".zip")) {

			// it's a zip file, so create hub package and push the
			// master mets
			try {
			    HaSMasterMETSProfile mm =
				    HaSMasterMETSProfileFactory.newInstance(new ZipFile(
				    f),
				    tempdir, true);
			    if (mm != null) {
				mets_stack.push(mm);
				count++;
			    }
			} catch (ZipException e) {
			    logger.error("Error creating Hub package for: " +
				    f.getName());
			} catch (HaSMETSProfileException e) {
			    logger.error("Error creating Hub package for: " +
				    f.getName());
			} catch (IOException e) {
			    logger.error("Error creating Hub package for: " +
				    f.getName());
			}
		    }
		}
		logger.info("Found " + count + " items to submit");
	    } else {
		JOptionPane.showMessageDialog(null,
			"You must supply a package to submit");
		logger.removeAppender(swordAppender);
		return null;
	    }



	    /* Packager Class */

//		if (swordPackagerClassComboBox.getSelectedItem() != null) {
//			packager_class_name = swordPackagerClassComboBox.getSelectedItem().toString();
//		} else {
//			JOptionPane.showMessageDialog(null, "You must select a packager class");
//			return;
//		}
	    packager_class_name =
		    "edu.uiuc.ndiipp.hubandspoke.packager.Hub2SwordPackager";


	    /* Keep Zips? */

	    if (swordKeepCopyYesRadioButton.isSelected()) {
		keep_zip = true;
	    }

	    if (keep_zip) {
		if (!swordKeepCopyTextField.getText().trim().equals("")) {
		    zip_dest = swordKeepCopyTextField.getText();
		    File zip_loc = new File(zip_dest);
		    if (!zip_loc.isDirectory()) {
			JOptionPane.showMessageDialog(null,
				zip_loc.getAbsolutePath() +
				" is not a directory. Please supply a directory");
			logger.removeAppender(swordAppender);
			return null;
		    }
		}
	    }

	    /* Set POST options */

	    PostMessage pm = new PostMessage();
	    pm.setDestination(location);
	    pm.setFiletype("application/zip");
	    pm.setFormatNamespace("http://purl.org/net/sword-types/METSDSpaceSIP");

	    if (swordMD5CheckBox.isSelected()) {
		pm.setUseMD5(true);
	    }

	    if (swordCorruptMD5CheckBox.isSelected()) {
		pm.setChecksumError(true);
	    }

	    if (swordNoOpCheckBox.isSelected()) {
		pm.setNoOp(true);
	    }

	    if (swordVerboseCheckBox.isSelected()) {
		pm.isVerbose();
	    }

	    if (!swordSlugHeaderTextField.getText().trim().equals("")) {
		pm.setSlug(swordSlugHeaderTextField.getText());
	    }

	    /* Submit Package */

	    PackageSubmission ps = new PackageSubmission(logger);

	    while (!mets_stack.isEmpty()) {
		logger.info("Processing " + mets_stack.peek().getBaseURI().
			toString() + "...");
		try {
		    ps.submitPackage(mets_stack.pop(), sword, pm, zip_dest,
			    packager_class_name, keep_zip);
		} catch (PackagerException e) {
		    logger.error(e);
		    logger.removeAppender(swordAppender);
		    return null;
		} catch (HaSMETSProfileException e) {
		    logger.error(e);
		    logger.removeAppender(swordAppender);
		    return null;
		} catch (SWORDClientException e) {
		    logger.error(e);
		    logger.removeAppender(swordAppender);
		    return null;
		}

	    }

	    logger.removeAppender(swordAppender);
	    return null;

	}
    };
    worker.execute();

}//GEN-LAST:event_swordSubmitButtonActionPerformed

private void swordCancelButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_swordCancelButtonActionPerformed
    System.exit(DISPOSE_ON_CLOSE);
}//GEN-LAST:event_swordCancelButtonActionPerformed

private void swordKeepCopyBrowseButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_swordKeepCopyBrowseButtonActionPerformed
    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    chooser.showDialog(this, "Select");
    File file = chooser.getSelectedFile();
    if (file != null) {
	swordKeepCopyTextField.setText(file.getPath());
    }
}//GEN-LAST:event_swordKeepCopyBrowseButtonActionPerformed

private void swordKeepCopyNoRadioButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_swordKeepCopyNoRadioButtonActionPerformed
    swordKeepCopyTextField.setEnabled(false);
    swordKeepCopyBrowseButton.setEnabled(false);
}//GEN-LAST:event_swordKeepCopyNoRadioButtonActionPerformed

private void swordKeepCopyYesRadioButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_swordKeepCopyYesRadioButtonActionPerformed
    swordKeepCopyTextField.setEnabled(true);
    swordKeepCopyBrowseButton.setEnabled(true);
}//GEN-LAST:event_swordKeepCopyYesRadioButtonActionPerformed

private void swordPackageSourceBrowseButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_swordPackageSourceBrowseButtonActionPerformed

    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    chooser.showDialog(this, "Select");
    File file = chooser.getSelectedFile();
    if (file != null) {
	swordPackageSourceTextField.setText(file.getPath());
    }
}//GEN-LAST:event_swordPackageSourceBrowseButtonActionPerformed

private void swordGetServiceDocButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_swordGetServiceDocButtonActionPerformed
    swordServiceDocDialog.setLocationRelativeTo(mainTabbedPane);
    swordServiceDocDialog.setVisible(true);
}//GEN-LAST:event_swordGetServiceDocButtonActionPerformed

private void getServiceDocCancelButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_getServiceDocCancelButtonActionPerformed
    swordServiceDocDialog.setVisible(false);
}//GEN-LAST:event_getServiceDocCancelButtonActionPerformed

private void getServiceDocButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_getServiceDocButtonActionPerformed
    String username;
    String password;
    String locText = "";
    URL location = null;
    String onBehalfOf;

    if (swordUsernameTextField.getText().compareTo("") != 0) {
	username = swordUsernameTextField.getText();
    } else {
	JOptionPane.showMessageDialog(null, "No username was specified.");
	return;
    }

    if (swordPasswordField.getPassword() != null) {
	password = new String(swordPasswordField.getPassword());
    } else {
	JOptionPane.showMessageDialog(null, "No password was specified.");
	return;
    }

    if (swordLocationTextField.getText().compareTo("") != 0) {
	locText = swordLocationTextField.getText();
	try {
	    location = new URL(locText);
	} catch (MalformedURLException e) {
	    JOptionPane.showMessageDialog(null, e.getMessage());
	    return;
	}
    } else {
	JOptionPane.showMessageDialog(null,
		"No service document location was specified.");
	return;
    }

    if (swordOnBehalfOfTextField.getText().compareTo("") != 0) {
	onBehalfOf = swordOnBehalfOfTextField.getText();
    } else {
	onBehalfOf = null;
    }

    try {
	sword = new Client();
	sword.setServer(location.getHost(), location.getPort());
	sword.setCredentials(username, password);
	ServiceDocument serviceDoc = null;
	serviceDoc = sword.getServiceDocument(location.toString(),
		onBehalfOf);
	Status status = sword.getStatus();
	if (status.getCode() != 200) {
	    JOptionPane.showMessageDialog(null, status.getCode() + ": " +
		    status.getMessage());
	    return;
	}
	service = serviceDoc.getService();
	su.processServiceDocument(locText, serviceDoc,
		swordServicesTreeModel, swordServicesTreeRoot,
		swordServicesTree);
	swordCollectionLocationComboBox.setModel(
		swordCollectionLocationModel());
	swordServiceDocDialog.setVisible(false);
	swordServiceTreeScrollPane.getViewport().setViewPosition(new Point(0, 0));
    } catch (SWORDClientException e) {
	JOptionPane.showMessageDialog(null, e.getMessage());
	e.printStackTrace();
	return;
    }
}//GEN-LAST:event_getServiceDocButtonActionPerformed

    protected void swordGetServiceDocCancelButtonActionPerformed(
	    ActionEvent evt) {
	service = null;
	swordCollectionLocationComboBox.setModel(
		swordCollectionLocationModel());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
	java.awt.EventQueue.invokeLater(new Runnable() {

	    public void run() {
		new WorkflowManagerGUI().setVisible(true);
	    }
	});
    }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private ButtonGroup getBagitButtonGroup;
        private JLabel getBagitLabel;
        private JRadioButton getBagitNoRadioButton;
        private JRadioButton getBagitYesRadioButton;
        private JButton getCancelButton;
        private JLabel getCrudLabel;
        private JTextField getCrudTextField;
        private ButtonGroup getExportTypeButtonGroup;
        private JRadioButton getExportTypeItemRadioButton;
        private JLabel getExportTypeLabel;
        private JRadioButton getExportTypeListRadioButton;
        private JLabel getHandleLabel;
        private JTextField getHandleTextField;
        private JLabel getListLocationLabel;
        private JTextField getListLocationTextField;
        private JButton getListLocationtBrowseButton;
        private JLabel getLogLabel;
        private JPanel getLogPanel;
        private JTextPane getLogTextPane;
        private JButton getPackageDestBrowseButton;
        private JLabel getPackageDestLabel;
        private JTextField getPackageDestTextField;
        private JComboBox getPackagerClassComboBox;
        private JPanel getPanel;
        private JButton getServiceDocButton;
        private JButton getServiceDocCancelButton;
        private JSplitPane getSplitPane;
        private JButton getSubmitButton;
        private JLabel getToHubClassLabel;
        private JLabel jLabel2;
        private JScrollPane jScrollPane1;
        private JScrollPane jScrollPane2;
        private JScrollPane jScrollPane3;
        private JScrollPane jScrollPane4;
        private JTabbedPane mainTabbedPane;
        private JButton migrateCancelButton;
        private ButtonGroup migrateExportTypeButtonGroup;
        private JLabel migrateFromCrudLabel;
        private JTextField migrateFromCrudTextField;
        private JRadioButton migrateFromExportTypeItemRadioButton;
        private JLabel migrateFromExportTypeLabel;
        private JRadioButton migrateFromExportTypeListRadioButton;
        private JLabel migrateFromHandleLabel;
        private JTextField migrateFromHandleTextField;
        private JLabel migrateFromLabel;
        private JButton migrateFromListLocationBrowseButton;
        private JLabel migrateFromListLocationLabel;
        private JTextField migrateFromListLocationTextField;
        private JComboBox migrateFromPackagerClassComboBox;
        private JLabel migrateFromToHubClassLabel;
        private ButtonGroup migrateKeepZipsButtonGroup;
        private JLabel migrateLogLabel;
        private JPanel migrateLogPanel;
        private JTextPane migrateLogTextPane;
        private JPanel migratePanel;
        private JSeparator migrateSeparator;
        private JSplitPane migrateSplitPane;
        private JButton migrateSubmitButton;
        private JLabel migrateToCrudLabel;
        private JTextField migrateToCrudTextField;
        private JLabel migrateToFromHubClassLabel;
        private JLabel migrateToHandleLabel;
        private JTextField migrateToHandleTextField;
        private JButton migrateToKeepCopyBrowseButton;
        private JLabel migrateToKeepCopyLabel;
        private JRadioButton migrateToKeepCopyNoRadioButton;
        private JTextField migrateToKeepCopyTextField;
        private JRadioButton migrateToKeepCopyYesRadioButton;
        private JLabel migrateToLabel;
        private JComboBox migrateToPackagerClassComboBox;
        private JButton putCancelButton;
        private JLabel putCrudLabel;
        private JTextField putCrudTextField;
        private JLabel putFromHubClassLabel;
        private JLabel putHandleLabel;
        private JTextField putHandleTextField;
        private JButton putKeepCopyBrowseButton;
        private JLabel putKeepCopyLabel;
        private JRadioButton putKeepCopyNoRadioButton;
        private JTextField putKeepCopyTextField;
        private JRadioButton putKeepCopyYesRadioButton;
        private ButtonGroup putKeepZipsButtonGroup;
        private JLabel putLogLabel;
        private JPanel putLogPanel;
        private JTextPane putLogTextPane;
        private JButton putPackageSourceBrowseButton;
        private JLabel putPackageSourceLabel;
        private JTextField putPackageSourceTextField;
        private JRadioButton putPackageSourceTypeDirRadioButton;
        private JLabel putPackageSourceTypeLabel;
        private JRadioButton putPackageSourceTypeListRadioButton;
        private JRadioButton putPackageSourceTypeMETSRadioButton;
        private JRadioButton putPackageSourceTypeZipRadioButton;
        private JComboBox putPackagerClassComboBox;
        private JPanel putPanel;
        private ButtonGroup putSourceTypeButtonGroup;
        private JSplitPane putSplitPane;
        private JButton putSubmitButton;
        private JButton swordCancelButton;
        private JComboBox swordCollectionLocationComboBox;
        private JLabel swordCollectionLocationLabel;
        private JCheckBox swordCorruptMD5CheckBox;
        private JButton swordGetServiceDocButton;
        private JButton swordKeepCopyBrowseButton;
        private JLabel swordKeepCopyLabel;
        private JRadioButton swordKeepCopyNoRadioButton;
        private JTextField swordKeepCopyTextField;
        private JRadioButton swordKeepCopyYesRadioButton;
        private ButtonGroup swordKeepZipsButtonGroup;
        private JLabel swordLocationLabel;
        private JTextField swordLocationTextField;
        private JLabel swordLogLabel;
        private JTextPane swordLogTextPane;
        private JCheckBox swordMD5CheckBox;
        private JCheckBox swordNoOpCheckBox;
        private JLabel swordOnBehalfOfLabel;
        private JTextField swordOnBehalfOfTextField;
        private JButton swordPackageSourceBrowseButton;
        private JLabel swordPackageSourceLabel;
        private JTextField swordPackageSourceTextField;
        private JRadioButton swordPackageSourceTypeDirRadioButton;
        private JLabel swordPackageSourceTypeLabel;
        private JRadioButton swordPackageSourceTypeListRadioButton;
        private JRadioButton swordPackageSourceTypeMETSRadioButton;
        private JRadioButton swordPackageSourceTypeZipRadioButton;
        private JPasswordField swordPasswordField;
        private JLabel swordPasswordLabel;
        private JPanel swordPostLogPanel;
        private JSplitPane swordPostPane;
        private JPanel swordPostPanel;
        private JEditorPane swordServiceDetailsEditorPane;
        private JDialog swordServiceDocDialog;
        private JPanel swordServiceEditorPanel;
        private JScrollPane swordServiceEditorScrollPane;
        private JSplitPane swordServicePane;
        private JPanel swordServiceTreePanel;
        private JScrollPane swordServiceTreeScrollPane;
        private JTree swordServicesTree;
        private JTextField swordSlugHeaderTextField;
        private ButtonGroup swordSourceTypeButtonGroup;
        private JSplitPane swordSplitPane;
        private JButton swordSubmitButton;
        private JLabel swordUsernameLabel;
        private JTextField swordUsernameTextField;
        private JCheckBox swordVerboseCheckBox;
        // End of variables declaration//GEN-END:variables
}
