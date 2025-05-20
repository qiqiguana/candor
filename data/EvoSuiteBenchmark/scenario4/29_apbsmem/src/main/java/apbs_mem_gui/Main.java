package apbs_mem_gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import com.jgoodies.forms.layout.*;
import com.jgoodies.forms.builder.*;
import org.jmol.api.JmolViewer;
import org.jmol.api.JmolAdapter;
import org.jmol.popup.JmolPopup;

/**
 * @author Keith Callenberg, Gabriel de Forest
 */
public class Main {

    private JMenuBar mainBar;

    private JMenu fileMenu, focusMenu, helpMenu;

    private JMenuItem save, open, exit, about, readme;

    private ButtonGroup focusbuttons;

    private JRadioButtonMenuItem setFocus0, setFocus1, setFocus2;

    protected int maxfocus = 0, pb = 0;

    private JPanel lpane, rpane;

    private Container mainPane;

    GridBagConstraints gbc = new GridBagConstraints();

    private JButton Run, PQRBrowse1, PQRBrowse2, Preview, RedrawPot;

    protected JCheckBox drawPot;

    protected JFormattedTextField pqrFile1, pqrFile2, gridDimx, gridDimy, gridDimz, gridLen1a, gridLen2a, gridLen3a, gridLen1b, gridLen2b, gridLen3b, gridLen1c, gridLen2c, gridLen3c, countIon1Charge, countIon1Con, countIon1Sz, countIon2Charge, countIon2Con, countIon2Sz, proteinDi, solventDi, membraneDi, srad, sdens, temp, zmem, Lmem, idie, geoFactor1, geoFactor2, geoFactor3, potential, potcontour;

    protected javax.swing.JProgressBar pBar;

    protected javax.swing.JComboBox solMethodCombo, boundaryCondCombo, calcTypeCombo, centerCombo, contourCombo;

    InFile inFile;

    Exec exec = new Exec();

    FileEditor file = new FileEditor();

    //keeps track of whether or not file has been changed
    boolean hasbeenchanged = true;

    //is file already loaded/saved?
    boolean file_loaded = false;

    double[] finalenergy, memv;

    File ofile;

    String outfilename;

    String[] theEnergy, ctypes;

    Scanner fScan;

    PrintWriter outFile;

    private JFrame theWindow = new JFrame();

    protected JmolViewer viewer;

    private JmolAdapter adapter;

    private JmolPopup jmolPopup;

    private static double version = 1.04;

    private JDialog aboutdialog, helpdialog;

    private static Main m;

    /**
     * Main class that includes the GUI components
     */
    public Main() {
    }

    /**
     * Driver method
     * @param args No command-line arguments are necessary
     */
    public static void main(String[] args);

    private class menuHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == setFocus0) {
                setMaxFocus(0);
            }
            if (e.getSource() == setFocus1) {
                setMaxFocus(1);
            }
            if (e.getSource() == setFocus2) {
                setMaxFocus(2);
            } else if (e.getSource() == about) {
                aboutdialog.show();
            } else if (e.getSource() == readme) {
                helpdialog.show();
            } else //opens a *.in file that has between 0 and 2 focus
            if (e.getSource() == open) {
                if (OpenFile()) {
                    //if got the file successfully and (dont save)
                    try {
                        fScan = new Scanner(new FileInputStream(ofile));
                    } catch (Exception e1) {
                        System.out.println(e1.getMessage());
                    }
                    //assume protein solvation unless we see >1 pqr or memv parameters
                    calcTypeCombo.setSelectedIndex(0);
                    StringBuffer theData = new StringBuffer();
                    int loadfocus = -1;
                    String nextline;
                    while (fScan.hasNextLine()) {
                        nextline = fScan.nextLine();
                        if (nextline.contains("elec name")) {
                            if (loadfocus > -1 && nextline.endsWith("0"))
                                // we only want to load one section so we stop if we see a new section
                                break;
                            else
                                loadfocus++;
                        } else if (nextline.contains("end")) {
                            //if done with loading a focus level
                            //save current data regardless. first focus it wont really do anything
                            SaveData();
                            maxfocus = loadfocus;
                            String temp = theData.toString();
                            String[] data = temp.split("\n");
                            //then load up data to the new focus
                            LoadData(data, loadfocus);
                            //finally, reset the data for the next focus
                            theData = new StringBuffer();
                        } else {
                            theData.append(nextline + "\n");
                        }
                    }
                    fScan.close();
                    setMaxFocus(maxfocus);
                }
            } else if (e.getSource() == save) {
                if (SaveData() && SaveDialog("Save settings to file")) {
                    //if it was able to save the data
                    //save all data to file (true = first run)
                    SaveToFile(true);
                    //file has not been changed since
                    hasbeenchanged = false;
                }
            } else if (e.getSource() == exit) {
                if (hasbeenchanged) {
                    int answer;
                    if (file_loaded) {
                        answer = JOptionPane.showConfirmDialog(null, "Do you want to save changes to " + ofile.getName() + "?", "", JOptionPane.YES_NO_OPTION);
                    } else {
                        answer = JOptionPane.showConfirmDialog(null, "Do you want to save your settings?", "", JOptionPane.YES_NO_OPTION);
                    }
                    if (answer == JOptionPane.YES_OPTION) {
                        if (!file_loaded) {
                            SaveDialog("Save settings to file");
                        }
                        SaveToFile(true);
                    }
                }
                File curdir = new File(".");
                deleteFiles(curdir.getPath(), ".dx");
                System.exit(0);
            }
        }
    }

    private class fileInHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == Preview) {
                if (pqrFile1.getText() == "") {
                    JOptionPane.showMessageDialog(null, "Please select the PQR file", "Error", JOptionPane.PLAIN_MESSAGE);
                    return;
                } else {
                    File tfile = new File(pqrFile1.getText());
                    if (!tfile.exists()) {
                        JOptionPane.showMessageDialog(null, "PQR file not found", "Error", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                }
                Integer t1 = Integer.parseInt(gridDimx.getText());
                Integer t2 = Integer.parseInt(gridDimy.getText());
                Integer t3 = Integer.parseInt(gridDimz.getText());
                gridDimx.setText("65");
                gridDimy.setText("65");
                gridDimz.setText("65");
                SaveData();
                ofile = new File("./preview_temp");
                if (calcTypeCombo.getSelectedIndex() == 1) {
                    //ION SOLVATION
                    if (!pqrFile2.getText().equals("")) {
                        //Load the ION too!
                        viewer.evalString("load APPEND \"" + pqrFile2.getText() + "\"");
                        viewer.evalString("frame *;cpk off;wireframe off;cartoon on;color green;select 2.0;color purple;spacefill 100%");
                    }
                }
                Thread pT = new Thread(new Run(m, inFile, ofile, viewer, pBar, drawPot.isSelected(), potcontour.getText(), true));
                pT.start();
                ofile.delete();
                gridDimx.setText(t1.toString());
                gridDimy.setText(t2.toString());
                gridDimz.setText(t3.toString());
                pBar.setValue(0);
            } else if (e.getSource() == Run) {
                if (pqrFile1.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please select the PQR file for the protein.", "Error", JOptionPane.PLAIN_MESSAGE);
                    return;
                } else {
                    File tfile1 = new File(pqrFile1.getText());
                    if (!tfile1.exists()) {
                        System.out.println("Protein PQR file not found.");
                        return;
                    }
                }
                System.out.println("Focus: " + maxfocus + " " + inFile.getMaxfocus());
                //clean up files from a previous run - this should not be needed at some point
                File curdir = new File(".");
                deleteFiles(curdir.getPath(), ".dx");
                if (SaveData() && SaveDialog("Enter a name for the output directory before running")) {
                    if (calcTypeCombo.getSelectedIndex() == 1) {
                        //ION SOLVATION - create concatenation file of ion + protein
                        //Load the ION too!
                        viewer.evalString("load FILES \"" + pqrFile1.getText() + "\" \"" + pqrFile2.getText() + "\"");
                        try {
                            BufferedWriter prot1 = new BufferedWriter(new FileWriter(pqrFile1.getText() + ".withion.pqr", false));
                            BufferedReader in1 = new BufferedReader(new FileReader(pqrFile1.getText()));
                            BufferedReader in2 = new BufferedReader(new FileReader(pqrFile2.getText()));
                            String inputline;
                            while ((inputline = in1.readLine()) != null) {
                                prot1.write(inputline);
                                prot1.newLine();
                            }
                            while ((inputline = in2.readLine()) != null) {
                                prot1.write(inputline);
                                prot1.newLine();
                            }
                            prot1.flush();
                            prot1.close();
                            in1.close();
                            in2.close();
                        } catch (Exception e1) {
                            System.out.println(e1);
                        }
                        inFile.setMol3(pqrFile1.getText() + ".withion.pqr");
                        //Load the ION too!
                        viewer.evalString("load APPEND \"" + pqrFile2.getText() + "\"");
                        viewer.evalString("frame *;cpk off;wireframe off;cartoons on;color green;select 2.0;color purple;spacefill 100%");
                    } else if (calcTypeCombo.getSelectedIndex() == 2) {
                        //GATING CHARGE - turn charges off for dummy calculation
                        exec.callChargeOff(pqrFile1.getText());
                        exec.callChargeOff(pqrFile2.getText());
                    }
                    Thread t1 = new Thread(new Run(m, inFile, ofile, viewer, pBar, drawPot.isSelected(), potcontour.getText(), false));
                    t1.start();
                    if (drawPot.isEnabled()) {
                        RedrawPot.setEnabled(true);
                    }
                }
                pBar.setValue(0);
            } else if (e.getSource() == PQRBrowse1) {
                final JFileChooser fc = new JFileChooser(new File("."));
                fc.setAcceptAllFileFilterUsed(false);
                EFileFilter filter = new EFileFilter("pqr");
                filter.setDescription("PQR files");
                fc.addChoosableFileFilter(filter);
                int returnVal = fc.showOpenDialog(theWindow);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    pqrFile1.setText(fc.getSelectedFile().getPath());
                    viewer.openFile(pqrFile1.getText());
                }
            } else if (e.getSource() == PQRBrowse2) {
                final JFileChooser fc = new JFileChooser(new File("."));
                fc.setAcceptAllFileFilterUsed(false);
                EFileFilter filter = new EFileFilter("pqr");
                filter.setDescription("PQR files");
                fc.addChoosableFileFilter(filter);
                int returnVal = fc.showOpenDialog(theWindow);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    pqrFile2.setText(fc.getSelectedFile().getPath());
                    // viewer.openFile(pqrFile2.getText());
                }
            } else if (e.getSource() == boundaryCondCombo) {
                if (calcTypeCombo.getSelectedIndex() == 2) {
                    boundaryCondCombo.setSelectedIndex(4);
                    potential.setEnabled(true);
                } else {
                    if (boundaryCondCombo.getSelectedIndex() == 4) {
                        boundaryCondCombo.setSelectedIndex(0);
                        potential.setEnabled(false);
                    }
                    if (boundaryCondCombo.getSelectedIndex() == 3 && maxfocus == 0) {
                        String temp;
                        Object[] choices = { "0", "1", "2" };
                        temp = (String) JOptionPane.showInputDialog(null, "How many focus levels do you want to set?", "Focus levels", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
                        setMaxFocus(Integer.parseInt(temp));
                    }
                }
            } else if (e.getSource() == calcTypeCombo) {
                if (calcTypeCombo.getSelectedIndex() == 0) {
                    //PROTEIN SOLVATION
                    pqrFile2.setEnabled(false);
                    PQRBrowse2.setEnabled(false);
                    pqrFile1.setToolTipText("PQR file of protein in membrane");
                    if (boundaryCondCombo.getSelectedIndex() == 4) {
                        boundaryCondCombo.setSelectedIndex(0);
                    }
                } else if (calcTypeCombo.getSelectedIndex() == 1) {
                    //ION SOLVATION
                    pqrFile2.setEnabled(true);
                    PQRBrowse2.setEnabled(true);
                    pqrFile1.setToolTipText("PQR file of protein");
                    pqrFile2.setToolTipText("PQR file of ion only");
                    if (boundaryCondCombo.getSelectedIndex() == 4) {
                        boundaryCondCombo.setSelectedIndex(0);
                    }
                } else {
                    //GATING CHARGE
                    pqrFile2.setEnabled(true);
                    PQRBrowse2.setEnabled(true);
                    pqrFile1.setToolTipText("PQR file of gating conformation 1");
                    pqrFile2.setToolTipText("PQR file of gating conformation 2");
                    boundaryCondCombo.setSelectedIndex(4);
                    potential.setEnabled(true);
                }
            } else if (e.getSource() == drawPot) {
                potcontour.setEnabled(drawPot.isSelected());
            } else if (e.getSource() == RedrawPot) {
                viewer.evalString("isosurface delete");
                if (drawPot.isSelected()) {
                    viewer.evalString("isosurface " + getIsocontour() + " \"dielx_" + (maxfocus + 1) + "m.dx\"; color isosurface white translucent");
                    viewer.evalString("isosurface potpos " + potcontour.getText() + " \"pot_1.dx\"; color isoSurface red translucent");
                    viewer.evalString("isosurface potneg -" + potcontour.getText() + " \"pot_1.dx\"; color isoSurface blue translucent");
                }
            }
        }
    }

    public Double getIsocontour();

    /**
     * Change the number of focus levels to use in the calculation
     * @param mf The new number of focus levels. Current options are 0, 1, or 2.
     */
    public void setMaxFocus(int mf);

    /**
     * Enable or disable text input fields for the level 1 focusing.
     * @param v flag to enable (if true) or disable (if false) the focus level 1.
     */
    public void setFocus1Enable(boolean v);

    /**
     * Enable or disable text input fields for the level 2 focusing.
     * @param v flag to enable (if true) or disable (if false) the focus level 2.
     */
    public void setFocus2Enable(boolean v);

    /**
     * Save the parameters stored in the inFile object to a file.
     * @param firstcall True if the file to be written is the "dummy" file
     * before the real calculation, otherwise false.
     */
    public void SaveToFile(boolean firstcall);

    /**
     * Store the current GUI text field values in the inFile object.
     * @return True if the operation was succesful, false otherwise.
     */
    public boolean SaveData();

    /**
     * Load parameters from an APBS input file into the GUI.
     * @param thedata APBS input file read into a string array by lines.
     * @param loadfocus the focus level for which to load the parameters.
     */
    private void LoadData(String[] thedata, int loadfocus);

    /**
     * Simple method for logging debug output to file.
     * @param msg String to write to file for debug purposes.
     */
    protected void log(String msg);

    /**
     * Locate a file from the filesystem via the JFileChooser.
     * @return True if the file was successfully selected, otherwise false.
     */
    private boolean OpenFile();

    /**
     * Select a location and name for saving a file.
     * @param savetitle title for the JFileChooser window.
     * @return True if a file path was successfully chosen, otherwise false.
     */
    private boolean SaveDialog(String savetitle);

    /**
     * Delete a list of temporary files based on extension.
     * @param directory Path to the directory that contains the files.
     * @param extension File extension of the files that will be deleted.
     */
    private void deleteFiles(String directory, String extension);

    /**
     * Copy files based on extension
     * @param src Path to source directory.
     * @param dst Path to destination directory.
     * @param extension Extension of files to be copied.
     */
    public void copyFiles(String src, String dst, String extension);

    private void dependencyCheck();

    /**
     */
    public class ExtensionFilter implements FilenameFilter {

        private String extension;

        /**
         * @param extension
         */
        public ExtensionFilter(String extension) {
            this.extension = extension;
        }

        public boolean accept(File dir, String name) {
            return (name.endsWith(extension));
        }
    }

    /**
     */
    public class SimpleAboutDialog extends JDialog {

        /**
         * A basic dialog for describing details of the software and providing contact information.
         * @param parent The APBSmem main JFrame window.
         */
        public SimpleAboutDialog(JFrame parent) {
            super(parent, "About APBSmem", true);
            Box b = Box.createVerticalBox();
            b.add(Box.createGlue());
            b.add(new JLabel("APBSmem v" + version));
            b.add(new JLabel("Grabe Lab"));
            b.add(new JLabel("University of Pittsburgh"));
            b.add(new JLabel("http://mgrabe1.bio.pitt.edu/apbsmem/"));
            b.add(Box.createGlue());
            getContentPane().add(b, "Center");
            JPanel p2 = new JPanel();
            JButton ok = new JButton("OK");
            p2.add(ok);
            getContentPane().add(p2, "South");
            ok.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    setVisible(false);
                }
            });
            setSize(300, 200);
        }
    }

    public class SimpleHelpDialog extends JDialog {

        /**
         * A basic dialog for describing details of the software and providing contact information.
         * @param parent The APBSmem main JFrame window.
         */
        public SimpleHelpDialog(JFrame parent) {
            super(parent, "APBSmem Help", true);
            JScrollPane jsp = new JScrollPane();
            try {
                JEditorPane jep = new JEditorPane("http://mgrabe1.bio.pitt.edu/apbsmem/");
                jep.setEditable(false);
                jsp.getViewport().add(jep, BorderLayout.CENTER);
            } catch (Exception e) {
                System.out.println("couldnt load editorpane");
            }
            JButton ok = new JButton("OK");
            jsp.validate();
            jsp.add(ok);
            getContentPane().add(jsp, "South");
            ok.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    setVisible(false);
                }
            });
            setSize(300, 200);
        }
    }
}
