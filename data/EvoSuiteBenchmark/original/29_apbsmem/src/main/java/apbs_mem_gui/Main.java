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
 *
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
    protected JFormattedTextField pqrFile1, pqrFile2,
            gridDimx, gridDimy, gridDimz,
            gridLen1a, gridLen2a, gridLen3a,
            gridLen1b, gridLen2b, gridLen3b,
            gridLen1c, gridLen2c, gridLen3c,
            countIon1Charge, countIon1Con, countIon1Sz,
            countIon2Charge, countIon2Con, countIon2Sz,
            proteinDi, solventDi, membraneDi,
            srad, sdens, temp,
            zmem, Lmem, idie, geoFactor1, geoFactor2, geoFactor3, potential, potcontour;
    protected javax.swing.JProgressBar pBar;
    protected javax.swing.JComboBox solMethodCombo, boundaryCondCombo,
            calcTypeCombo, centerCombo, contourCombo;
    InFile inFile;
    Exec exec = new Exec();
    FileEditor file = new FileEditor();
    boolean hasbeenchanged = true;		//keeps track of whether or not file has been changed
    boolean file_loaded = false;		//is file already loaded/saved?

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
      
        inFile = new InFile();

        theWindow = new JFrame("APBSmem " + version);
        theWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPane = theWindow.getContentPane();

        Run = new JButton("Run");
        PQRBrowse1 = new JButton("Browse");
        PQRBrowse2 = new JButton("Browse");
        PQRBrowse2.setEnabled(false);
        Preview = new JButton("Preview");
        mainBar = new JMenuBar();
        theWindow.setJMenuBar(mainBar);

        RedrawPot = new JButton("Redraw");
        RedrawPot.setEnabled(false);

        fileMenu = new JMenu("File");
        open = new JMenuItem("Open");
        save = new JMenuItem("Save");
        exit = new JMenuItem("Exit");
        
        helpMenu = new JMenu("Help");
        readme = new JMenuItem("Readme");
        about = new JMenuItem("About");
        
        //helpMenu.add(readme);
        helpMenu.add(about);
        

        fileMenu.add(open);
        fileMenu.add(save);
        fileMenu.add(exit);
        focusMenu = new JMenu("Focus");
        setFocus0 = new JRadioButtonMenuItem("No focus");
        setFocus1 = new JRadioButtonMenuItem("1 focus level");
        setFocus2 = new JRadioButtonMenuItem("2 focus levels");
        setFocus0.setSelected(true);
        focusbuttons = new ButtonGroup();
        focusbuttons.add(setFocus0);
        focusbuttons.add(setFocus1);
        focusbuttons.add(setFocus2);
        focusMenu.add(setFocus0);
        focusMenu.add(setFocus1);
        focusMenu.add(setFocus2);

        mainBar.add(fileMenu);
        mainBar.add(focusMenu);
        mainBar.add(helpMenu);

        ctypes = new String[] {"Protein solvation", "Ion solvation", "Gating charge"};
        
        pBar = new javax.swing.JProgressBar();
        pBar.setStringPainted(true);
        pb = 0;

        gridDimx = new JFormattedTextField(new Integer(97));
        gridDimx.setToolTipText("Grid dimension X");
        gridDimy = new JFormattedTextField(new Integer(97));
        gridDimy.setToolTipText("Grid dimension Y");
        gridDimz = new JFormattedTextField(new Integer(97));
        gridDimz.setToolTipText("Grid dimension Z");
        gridLen1a = new JFormattedTextField(new Double(300));
        gridLen1a.setToolTipText("Grid length X");
        gridLen2a = new JFormattedTextField(new Double(300));
        gridLen2a.setToolTipText("Grid length Y");
        gridLen3a = new JFormattedTextField(new Double(300));
        gridLen3a.setToolTipText("Grid length Z");
        gridLen1b = new JFormattedTextField(new Double(200));
        gridLen1b.setToolTipText("Grid length X");
        gridLen2b = new JFormattedTextField(new Double(200));
        gridLen2b.setToolTipText("Grid length Y");
        gridLen3b = new JFormattedTextField(new Double(200));
        gridLen3b.setToolTipText("Grid length Z");
        gridLen1c = new JFormattedTextField(new Double(100));
        gridLen1c.setToolTipText("Grid length X");
        gridLen2c = new JFormattedTextField(new Double(100));
        gridLen2c.setToolTipText("Grid length Y");
        gridLen3c = new JFormattedTextField(new Double(100));
        gridLen3c.setToolTipText("Grid length Z");
        
        setFocus1Enable(false);
        setFocus2Enable(false);

        countIon1Charge = new JFormattedTextField(new Double(1.0));
        countIon1Charge.setToolTipText("Ion 1 CHARGE");
        countIon1Con = new JFormattedTextField(new Double(0.1));
        countIon1Con.setToolTipText("Ion 1 CONCENTRATION");
        countIon1Sz = new JFormattedTextField(new Double(2.0));
        countIon1Sz.setToolTipText("Ion 1 RADIUS");
        countIon2Charge = new JFormattedTextField(new Double(-1.0));
        countIon2Charge.setToolTipText("Ion 2 CHARGE");
        countIon2Con = new JFormattedTextField(new Double(0.1));
        countIon2Con.setToolTipText("Ion 2 CONCENTRATION");
        countIon2Sz = new JFormattedTextField(new Double(2.0));
        countIon2Sz.setToolTipText("Ion 2 RADIUS");
        proteinDi = new JFormattedTextField(new Double(2.0));
        solventDi = new JFormattedTextField(new Double(80.0));
        membraneDi = new JFormattedTextField(new Double(2.0));
        pqrFile1 = new JFormattedTextField(new String(""));
        pqrFile1.setColumns(16);
        pqrFile2 = new JFormattedTextField(new String(""));
        pqrFile2.setColumns(16);
        pqrFile2.setEnabled(false);

        srad = new JFormattedTextField(new Double(1.4));
        sdens = new JFormattedTextField(new Double(10.0));
        temp = new JFormattedTextField(new Double(298.15));
        temp.setToolTipText("Temperature in Kelvin");
        zmem = new JFormattedTextField(new Double(-20.0));
        zmem.setToolTipText("Protein position in membrane in Angstroms");
        Lmem = new JFormattedTextField(new Double(40));
        Lmem.setToolTipText("Membrane length in Angstroms");
        idie = new JFormattedTextField(new Double(80));

        geoFactor1 = new JFormattedTextField(new Double(0.0));
        geoFactor1.setToolTipText("Exclusion radius in Angstroms");
        geoFactor2 = new JFormattedTextField(new Double(0.0));
        geoFactor2.setToolTipText("Exclusion radius in Angstroms");
        geoFactor3 = new JFormattedTextField(new Double(0.0));
        geoFactor3.setToolTipText("Thickness in Angstroms");

        drawPot = new JCheckBox("Draw potential");
        potcontour = new JFormattedTextField("2");
        potcontour.setToolTipText("Isocontour to draw electrostatic potential (+/-)");
        potcontour.setEnabled(false);

        potential = new JFormattedTextField("50");
        potential.setColumns(5);
        potential.setToolTipText("Membrane potential in mV");
        potential.setEnabled(false);

        solMethodCombo = new javax.swing.JComboBox();
        solMethodCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "lpbe", "npbe" }));
        solMethodCombo.setToolTipText("Linearized or Non-linearized Poisson Boltzmann");

        calcTypeCombo = new javax.swing.JComboBox();
        calcTypeCombo.setModel(new javax.swing.DefaultComboBoxModel(ctypes));

        boundaryCondCombo = new javax.swing.JComboBox();
        boundaryCondCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Zero", "SDH", "MDH", "Focus", "Membrane potential" }));

        centerCombo = new javax.swing.JComboBox();
        centerCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Origin", "Molecule 1"}));

        contourCombo = new javax.swing.JComboBox();
        contourCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Membrane-Protein", "Protein-Solvent"}));

        fileInHandler fhandler = new fileInHandler();
        menuHandler menuhandler = new menuHandler();
        boundaryCondCombo.addActionListener(fhandler);
        centerCombo.addActionListener(fhandler);
        calcTypeCombo.addActionListener(fhandler);
        pqrFile1.addActionListener(fhandler);
        pqrFile2.addActionListener(fhandler);
        gridDimx.addActionListener(fhandler);
        gridDimy.addActionListener(fhandler);
        gridDimz.addActionListener(fhandler);
        gridLen1a.addActionListener(fhandler);
        gridLen2a.addActionListener(fhandler);
        gridLen3a.addActionListener(fhandler);
        countIon1Charge.addActionListener(fhandler);
        countIon1Con.addActionListener(fhandler);
        countIon1Sz.addActionListener(fhandler);
        countIon2Charge.addActionListener(fhandler);
        countIon2Con.addActionListener(fhandler);
        countIon2Sz.addActionListener(fhandler);
        proteinDi.addActionListener(fhandler);
        solventDi.addActionListener(fhandler);
        membraneDi.addActionListener(fhandler);
        Run.addActionListener(fhandler);
        PQRBrowse1.addActionListener(fhandler);
        PQRBrowse2.addActionListener(fhandler);
        Preview.addActionListener(fhandler);
        drawPot.addActionListener(fhandler);
        RedrawPot.addActionListener(fhandler);
        setFocus0.addActionListener(menuhandler);
        setFocus1.addActionListener(menuhandler);
        setFocus2.addActionListener(menuhandler);
        open.addActionListener(menuhandler);
        save.addActionListener(menuhandler);
        exit.addActionListener(menuhandler);
        about.addActionListener(menuhandler);
        readme.addActionListener(menuhandler);

        lpane = new JPanel();

        JmolPanel jmolPanel = new JmolPanel();
        
        aboutdialog = new SimpleAboutDialog(new JFrame());
        helpdialog = new SimpleHelpDialog(new JFrame());

        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref", //columns
                "pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu," +
                "pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu," +
                "pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu," +
                "pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu," +
                "pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref"); //rows

        PanelBuilder builder = new PanelBuilder(layout);
        builder.setDefaultDialogBorder();

        lpane.setLayout(layout);

        CellConstraints cc = new CellConstraints();
        builder.add(new JLabel("Calculation type"),     cc.xy (1, 1));
        builder.add(calcTypeCombo,                      cc.xyw(3, 1, 5));

        builder.add(new JLabel("PQR File 1"),           cc.xy (1, 3));
        builder.add(pqrFile1,                           cc.xyw(3, 3, 3));
        builder.add(PQRBrowse1,                         cc.xy (7, 3));

        builder.add(new JLabel("PQR File 2"),           cc.xy (1, 5));
        builder.add(pqrFile2,                           cc.xyw (3, 5, 3));
        builder.add(PQRBrowse2,                         cc.xy (7, 5));

        builder.add(new JLabel("Grid dimensions"),      cc.xy (1, 7));
        builder.add(gridDimx,                           cc.xy (3, 7));
        builder.add(gridDimy,                           cc.xy (5, 7));
        builder.add(gridDimz,                           cc.xy (7, 7));

        builder.add(new JLabel("Grid lengths"),         cc.xy (1, 9));
        builder.add(gridLen1a,                          cc.xy (3, 9));
        builder.add(gridLen2a,                          cc.xy (5, 9));
        builder.add(gridLen3a,                          cc.xy (7, 9));

        builder.add(new JLabel(""),                     cc.xy (1, 11));
        builder.add(gridLen1b,                          cc.xy (3, 11));
        builder.add(gridLen2b,                          cc.xy (5, 11));
        builder.add(gridLen3b,                          cc.xy (7, 11));

        builder.add(new JLabel(""),                     cc.xy (1, 13));
        builder.add(gridLen1c,                          cc.xy (3, 13));
        builder.add(gridLen2c,                          cc.xy (5, 13));
        builder.add(gridLen3c,                          cc.xy (7, 13));

        builder.add(new JLabel("Counter-ions"),         cc.xy (1, 15));
        builder.add(countIon1Charge,                    cc.xy (3, 15));
        builder.add(countIon1Con,                       cc.xy (5, 15));
        builder.add(countIon1Sz,                        cc.xy (7, 15));

        builder.add(countIon2Charge,                    cc.xy (3, 17));
        builder.add(countIon2Con,                       cc.xy (5, 17));
        builder.add(countIon2Sz,                        cc.xy (7, 17));

        builder.addSeparator("Dielectric constants",    cc.xyw(1, 21, 7));
        builder.add(new JLabel("Protein"),              cc.xy (1, 23));
        builder.add(proteinDi,                          cc.xy (3, 23));
        builder.add(new JLabel("Solvent"),              cc.xy (5, 23));
        builder.add(solventDi,                          cc.xy (7, 23));
        builder.add(new JLabel("Membrane"),             cc.xy (1, 25));
        builder.add(membraneDi,                         cc.xy (3, 25));
        builder.add(new JLabel("Head group"),           cc.xy (5, 25));
        builder.add(idie,                               cc.xy (7, 25));

        builder.addSeparator("Geometry settings",       cc.xyw(1, 27, 7));
        builder.add(new JLabel("Membrane thickness"),   cc.xy (1, 29));
        builder.add(Lmem,                               cc.xy(3, 29));
        builder.add(new JLabel("Upper exclusion"),      cc.xy (5, 29));
        builder.add(geoFactor1,                         cc.xy(7, 29));
        builder.add(new JLabel("Head group thickness"), cc.xy (1, 31));
        builder.add(geoFactor3,                         cc.xy(3, 31));
        builder.add(new JLabel("Lower exclusion"),      cc.xy (5, 31));
        builder.add(geoFactor2,                         cc.xy(7, 31));
        builder.add(new JLabel("Membrane bottom z-pos"),cc.xy (1, 33));
        builder.add(zmem,                               cc.xy(3, 33));

        builder.addSeparator("Other parameters",        cc.xyw(1, 35, 7));
        builder.add(new JLabel("Solution method"),      cc.xy(1, 37));
        builder.add(solMethodCombo,                     cc.xy(3, 37));
        builder.add(new JLabel("Temperature"),          cc.xy(5, 37));
        builder.add(temp,                               cc.xy(7, 37));

        builder.add(new JLabel("Boundary conditions"),  cc.xy(1, 39));
        builder.add(boundaryCondCombo,                  cc.xy(3, 39));
        builder.add(new JLabel("Membrane potential"),   cc.xy(5,39));
        builder.add(potential,                          cc.xy(7, 39));

        builder.add(new JLabel("Solvent radius"),       cc.xy(1,41));
        builder.add(srad,                               cc.xy(3,41));
        builder.add(new JLabel("Sphere density"),       cc.xy(5,41));
        builder.add(sdens,                              cc.xy(7,41));

        builder.add(new JLabel("Isosurface contour"),   cc.xy(1,43));
        builder.add(contourCombo,                       cc.xy(3,43));
        builder.add(new JLabel("Grid center"),          cc.xy(5,43));
        builder.add(centerCombo,                        cc.xy(7,43));
        
        builder.add(drawPot,                            cc.xy(3,45));
        builder.add(potcontour,                         cc.xy(5, 45));
        builder.add(RedrawPot,                          cc.xy(7,45));

        builder.add(new JLabel(""),                     cc.xy(1, 47));
        builder.add(Preview,                            cc.xy(3, 47));
        builder.add(Run,                                cc.xy(5, 47));

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, builder.getPanel(), null);
        sp.setOneTouchExpandable(true);
        sp.setBorder(null);
        mainPane.add(sp, BorderLayout.CENTER);
        JPanel bottom = new JPanel();
        bottom.setLayout(new BorderLayout());
        bottom.add(pBar, BorderLayout.WEST);
        mainPane.add(bottom, BorderLayout.SOUTH);
        viewer = jmolPanel.getViewer();
        viewer.evalString("zap;frank off;");
        File logo = new File("apbsmem.jvxl");
        if(logo.exists())
            viewer.evalString("isoSurface \"apbsmem.jvxl\";zoom 200;");

       theWindow.pack();
       jmolPanel.setPreferredSize(new Dimension(300,300));
       sp.setRightComponent(jmolPanel);
       theWindow.pack();
       theWindow.setVisible(true);

        dependencyCheck();
        
    }

    /**
     * Driver method
     * @param args No command-line arguments are necessary
     */
    public static void main(String[] args) {
        m = new Main();
    }

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
            }

            else if(e.getSource() == about) {
                aboutdialog.show();
            }

            else if(e.getSource() == readme) {
                helpdialog.show();
            }

            //opens a *.in file that has between 0 and 2 focus
            else if (e.getSource() == open) {
                
                if (OpenFile()) {
                    //if got the file successfully and (dont save)
                    try {
                        fScan = new Scanner(new FileInputStream(ofile));
                    } catch (Exception e1) {
                        System.out.println(e1.getMessage());
                    }

                    calcTypeCombo.setSelectedIndex(0); //assume protein solvation unless we see >1 pqr or memv parameters
                    
                    StringBuffer theData = new StringBuffer();
                    int loadfocus = -1;
                    String nextline;
                    while (fScan.hasNextLine()) {
                        nextline = fScan.nextLine();
                        if (nextline.contains("elec name")) {
                            if (loadfocus > -1 && nextline.endsWith("0"))
                                break; // we only want to load one section so we stop if we see a new section
                            else
                                loadfocus++;
                        } else if (nextline.contains("end")) {	//if done with loading a focus level
                            SaveData();						//save current data regardless. first focus it wont really do anything
                            maxfocus = loadfocus;
                            String temp = theData.toString();
                            String[] data = temp.split("\n");
                            LoadData(data,loadfocus);						//then load up data to the new focus
                            theData = new StringBuffer();		//finally, reset the data for the next focus
                        } else {
                            theData.append(nextline + "\n");
                        }
                    }

                    fScan.close();

                    setMaxFocus(maxfocus);

                }

            }

            else if (e.getSource() == save) {
                if (SaveData() && SaveDialog("Save settings to file")) {
                    //if it was able to save the data
                    SaveToFile(true);		//save all data to file (true = first run)
                    hasbeenchanged = false;	//file has not been changed since
                }
            }

            else if (e.getSource() == exit) {
                if (hasbeenchanged) {
                    int answer;
                    if (file_loaded) {
                        answer = JOptionPane.showConfirmDialog(null, "Do you want to save changes to " + ofile.getName() + "?", "", JOptionPane.YES_NO_OPTION);
                    }
                    else {
                        answer = JOptionPane.showConfirmDialog(null, "Do you want to save your settings?", "", JOptionPane.YES_NO_OPTION);
                    }
                    if(answer == JOptionPane.YES_OPTION) {
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
                if(pqrFile1.getText() == "") {
                    JOptionPane.showMessageDialog(null, "Please select the PQR file", "Error", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                else {
                    File tfile = new File(pqrFile1.getText());
                    if(!tfile.exists()) {
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

                if(calcTypeCombo.getSelectedIndex() == 1) {
                        //ION SOLVATION
                        if (!pqrFile2.getText().equals("")) {
                            viewer.evalString("load APPEND \"" + pqrFile2.getText() + "\""); //Load the ION too!
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

            }
            else if (e.getSource() == Run) {

                if(pqrFile1.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Please select the PQR file for the protein.", "Error", JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                    else {
                        File tfile1 = new File(pqrFile1.getText());
                        if(!tfile1.exists()) {
                            System.out.println("Protein PQR file not found.");
                            return;
                        }
                }
                System.out.println("Focus: " + maxfocus + " " + inFile.getMaxfocus());
                //clean up files from a previous run - this should not be needed at some point
                File curdir = new File(".");
                deleteFiles(curdir.getPath(), ".dx");

                if (SaveData() && SaveDialog("Enter a name for the output directory before running")) {

                     if(calcTypeCombo.getSelectedIndex() == 1) {
                        //ION SOLVATION - create concatenation file of ion + protein
                        viewer.evalString("load FILES \"" + pqrFile1.getText() + "\" \"" + pqrFile2.getText() + "\""); //Load the ION too!
                        try {
                            BufferedWriter prot1 = new BufferedWriter(new FileWriter(pqrFile1.getText() + ".withion.pqr", false));
                            BufferedReader in1 = new BufferedReader(new FileReader(pqrFile1.getText()));
                            BufferedReader in2 = new BufferedReader(new FileReader(pqrFile2.getText()));
                            String inputline;

                            while((inputline = in1.readLine()) != null) {
                                prot1.write(inputline);
                                prot1.newLine();
                            }
                            while((inputline = in2.readLine()) != null) {
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

                        viewer.evalString("load APPEND \"" + pqrFile2.getText() + "\""); //Load the ION too!
                        viewer.evalString("frame *;cpk off;wireframe off;cartoons on;color green;select 2.0;color purple;spacefill 100%");

                    }
                    else if(calcTypeCombo.getSelectedIndex() == 2) {
                        //GATING CHARGE - turn charges off for dummy calculation
                        exec.callChargeOff(pqrFile1.getText());
                        exec.callChargeOff(pqrFile2.getText());
                    }


                    Thread t1 = new Thread(new Run(m, inFile, ofile, viewer, pBar, drawPot.isSelected(), potcontour.getText(), false));
                    t1.start();
                    
                    if(drawPot.isEnabled()) {
                        RedrawPot.setEnabled(true);
                    }
                }
                pBar.setValue(0);

            }
            else if (e.getSource() == PQRBrowse1) {
                
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
            }
            else if (e.getSource() == PQRBrowse2) {
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
            }
            else if (e.getSource() == boundaryCondCombo) {

                if(calcTypeCombo.getSelectedIndex() == 2) {
                    boundaryCondCombo.setSelectedIndex(4);
                    potential.setEnabled(true);
                }
                else {
                    if(boundaryCondCombo.getSelectedIndex() == 4) {
                        boundaryCondCombo.setSelectedIndex(0);
                        potential.setEnabled(false);
                    }
                    if(boundaryCondCombo.getSelectedIndex() == 3 && maxfocus == 0) {
                        String temp;

                        Object[] choices = {"0","1","2"};
                        temp = (String) JOptionPane.showInputDialog(null, "How many focus levels do you want to set?", "Focus levels", JOptionPane.QUESTION_MESSAGE, null, choices,choices[0]);

                        setMaxFocus(Integer.parseInt(temp));
                    }
                }

            }
            
            else if (e.getSource() == calcTypeCombo) {

                if(calcTypeCombo.getSelectedIndex() == 0) {
                    //PROTEIN SOLVATION
                    pqrFile2.setEnabled(false);
                    PQRBrowse2.setEnabled(false);
                    pqrFile1.setToolTipText("PQR file of protein in membrane");
                    if(boundaryCondCombo.getSelectedIndex() == 4) {
                        boundaryCondCombo.setSelectedIndex(0);
                    }
                }
                else if(calcTypeCombo.getSelectedIndex() == 1) {
                    //ION SOLVATION
                    pqrFile2.setEnabled(true);
                    PQRBrowse2.setEnabled(true);
                    pqrFile1.setToolTipText("PQR file of protein");
                    pqrFile2.setToolTipText("PQR file of ion only");
                    if(boundaryCondCombo.getSelectedIndex() == 4) {
                        boundaryCondCombo.setSelectedIndex(0);
                    }
                }
                else {
                    //GATING CHARGE
                    pqrFile2.setEnabled(true);
                    PQRBrowse2.setEnabled(true);
                    pqrFile1.setToolTipText("PQR file of gating conformation 1");
                    pqrFile2.setToolTipText("PQR file of gating conformation 2");
                    boundaryCondCombo.setSelectedIndex(4);
                    potential.setEnabled(true);
                }

              
            }

            else if (e.getSource() == drawPot) {
                potcontour.setEnabled(drawPot.isSelected());
            }

            else if(e.getSource() == RedrawPot) {
                viewer.evalString("isosurface delete");
                if (drawPot.isSelected()) {
                    viewer.evalString("isosurface " + getIsocontour() + " \"dielx_" + (maxfocus + 1) + "m.dx\"; color isosurface white translucent");
                    viewer.evalString("isosurface potpos " + potcontour.getText() + " \"pot_1.dx\"; color isoSurface red translucent");
                    viewer.evalString("isosurface potneg -" + potcontour.getText() + " \"pot_1.dx\"; color isoSurface blue translucent");
                }
            }
        }
    }	///end fileInHandler

    public Double getIsocontour() {
        //protein-membrane dielectric
        if (contourCombo.getSelectedIndex() == 0) {
            if (Double.valueOf(inFile.getMdie()) < Double.valueOf(inFile.getProteinDi())) {
                return Double.valueOf(inFile.getMdie()) + 0.001;
            }
            else {
                return Double.valueOf(inFile.getProteinDi()) + 0.001;
            }
        }
        //protein-solvent boundary
        else {
            if (Double.valueOf(inFile.getProteinDi()) < Double.valueOf(inFile.getSolventDi())) {
                return Double.valueOf(inFile.getProteinDi()) + 0.001;
            }
            else {
                return Double.valueOf(inFile.getSolventDi()) + 0.001;
            }
        }
    }
    /**
     * Change the number of focus levels to use in the calculation
     * @param mf The new number of focus levels. Current options are 0, 1, or 2.
     */
    public void setMaxFocus(int mf) {
        maxfocus = mf;
        inFile.setMaxFocus(mf);
        if (maxfocus == 0) {
            if(boundaryCondCombo.getSelectedIndex() == 3)
                boundaryCondCombo.setSelectedIndex(0);

            setFocus1Enable(false);
            setFocus2Enable(false);
            setFocus0.setSelected(true);
        }
        else if(maxfocus == 1) {
            boundaryCondCombo.setSelectedIndex(3);
            setFocus1Enable(true);
            setFocus2Enable(false);
            setFocus1.setSelected(true);
        }
        else {
            boundaryCondCombo.setSelectedIndex(3);
            setFocus1Enable(true);
            setFocus2Enable(true);
            setFocus2.setSelected(true);
        }
    }

    /**
     * Enable or disable text input fields for the level 1 focusing.
     * @param v flag to enable (if true) or disable (if false) the focus level 1.
     */
    public void setFocus1Enable(boolean v) {
        gridLen1b.setEnabled(v);
        gridLen2b.setEnabled(v);
        gridLen3b.setEnabled(v);
    }

    /**
     * Enable or disable text input fields for the level 2 focusing.
     * @param v flag to enable (if true) or disable (if false) the focus level 2.
     */
    public void setFocus2Enable(boolean v) {
        gridLen1c.setEnabled(v);
        gridLen2c.setEnabled(v);
        gridLen3c.setEnabled(v);
    }

    /**
     * Save the parameters stored in the inFile object to a file.
     * @param firstcall True if the file to be written is the "dummy" file
     * before the real calculation, otherwise false.
     */
    public void SaveToFile(boolean firstcall)
    {
        if (ofile != null) {
            
            try {
                if(firstcall) {
                    File outFile2 = new File(ofile.getPath() + ".dummy.in");
                    outFile2.delete();
                    outFile = new PrintWriter(new FileOutputStream(outFile2));
                    System.out.println("HEY: " + outFile2.getPath());
                }
		else {
                    File outFile2 = new File(ofile.getPath() + ".solv.in");
                    outFile2.delete();
                    outFile = new PrintWriter(new FileOutputStream(outFile2));
                }
            } catch (Exception e1) {
                System.out.println("SaveToFile exception: " + e1.toString());
            }
                                

            String tempIn = inFile.toString(firstcall);         //store data in temporary string
            String[] tempInSplit = tempIn.split("\n");		//split data by each new line
            for (int i = 0; i < tempInSplit.length; i++) {
                outFile.println(tempInSplit[i]);		//print data one line at a time
            }
             
            outFile.close();

        }
    }

    /**
     * Store the current GUI text field values in the inFile object.
     * @return True if the operation was succesful, false otherwise.
     */
    public boolean SaveData()
    {
        if (pqrFile1.getText().equals("") || pqrFile1.getText() == null ||
                gridDimx.getText().equals("") || gridDimx.getText() == null || gridDimy.getText().equals("") || gridDimy.getText() == null || gridDimz.getText().equals("") || gridDimz.getText() == null ||
                gridLen1a.getText().equals("") || gridLen1a.getText() == null || gridLen2a.getText().equals("") || gridLen2a.getText() == null || gridLen3a.getText().equals("") || gridLen3a.getText() == null ||
                countIon1Charge.getText().equals("") || countIon1Charge.getText() == null || countIon1Con.getText().equals("") || countIon1Con.getText() == null || countIon1Sz.getText().equals("") || countIon1Sz.getText() == null ||
                countIon2Charge.getText().equals("") || countIon2Charge.getText() == null || countIon2Con.getText().equals("") || countIon2Con.getText() == null || countIon2Sz.getText().equals("") || countIon2Sz.getText() == null ||
                proteinDi.getText().equals("") || proteinDi.getText() == null || solventDi.getText().equals("") || solventDi.getText() == null ||
                srad.getText().equals("") || srad.getText() == null || sdens.getText().equals("") || sdens.getText() == null || temp.getText().equals("") || temp.getText() == null) {
            if(maxfocus > 0 && (gridLen1b.getText().equals("") || gridLen1b.getText() == null || gridLen2b.getText().equals("") || gridLen2b.getText() == null || gridLen3b.getText().equals("") || gridLen3b.getText() == null)) {
                if(maxfocus > 1 && (gridLen1c.getText().equals("") || gridLen1c.getText() == null || gridLen2c.getText().equals("") || gridLen2c.getText() == null || gridLen3c.getText().equals("") || gridLen3c.getText() == null)) {
                    if (file_loaded) {		//if you arent opening it and it fails then fail
                        JOptionPane.showMessageDialog(null, "Fill in all values first", "", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
            }
        }
        //otherwise save data
        inFile.setMol1(pqrFile1.getText());
        inFile.setMol2(pqrFile2.getText());        
        inFile.setDime(new String(gridDimx.getText() + " " + gridDimy.getText() + " " + gridDimz.getText()));
        inFile.setGlen(new String(gridLen1a.getText() + " " + gridLen2a.getText() + " " + gridLen3a.getText()),0);
        if(maxfocus > 0) {
            inFile.setGlen(new String(gridLen1b.getText() + " " + gridLen2b.getText() + " " + gridLen3b.getText()),1);
        }
        if(maxfocus == 2) {
            inFile.setGlen(new String(gridLen1c.getText() + " " + gridLen2c.getText() + " " + gridLen3c.getText()),2);
        }
        inFile.setIon1Charge(countIon1Charge.getText());
        inFile.setIon1Conc(countIon1Con.getText());
        inFile.setIon1Radius(countIon1Sz.getText());
        inFile.setIon2Charge(countIon2Charge.getText());
        inFile.setIon2Conc(countIon2Con.getText());
        inFile.setIon2Radius(countIon2Sz.getText());
        
        inFile.setProteinDi(new String(proteinDi.getText()));
        inFile.setSolventDi(new String(solventDi.getText()));
        inFile.setSrad(srad.getText());
        inFile.setSdens(sdens.getText());
        inFile.setTemp(temp.getText());
        inFile.setType(calcTypeCombo.getSelectedIndex());
        inFile.setPotential(potential.getText());
        inFile.setLmem(Lmem.getText());
        inFile.setZmem(zmem.getText());
        inFile.setMdie(membraneDi.getText());
        inFile.setIdie(idie.getText());
        inFile.setGeo1(geoFactor1.getText());
        inFile.setGeo2(geoFactor2.getText());
        inFile.setGeo3(geoFactor3.getText());
        inFile.setDrawPot(drawPot.isSelected());
        if(boundaryCondCombo.getSelectedIndex() == 2) {
            inFile.setBoundCond(new String("mdh"));
        } else if (boundaryCondCombo.getSelectedIndex() == 1) {
            inFile.setBoundCond(new String("sdh"));
        } else if (boundaryCondCombo.getSelectedIndex() == 0) {
            inFile.setBoundCond(new String("zero"));
        }
        if (solMethodCombo.getSelectedIndex() == 1) {
            inFile.setSolMethod(new String("npbe"));
        } else if (solMethodCombo.getSelectedIndex() == 0) {
            inFile.setSolMethod(new String("lpbe"));
        }

        if (centerCombo.getSelectedIndex() == 0) {
            inFile.setCenter(new String("0 0 0"));
        } else if (centerCombo.getSelectedIndex() == 1) {
            inFile.setCenter(new String("mol 1"));
        }

        return true;
    }

    /**
     * Load parameters from an APBS input file into the GUI.
     * @param thedata APBS input file read into a string array by lines.
     * @param loadfocus the focus level for which to load the parameters.
     */
    private void LoadData(String[] thedata, int loadfocus) {
        boolean countion1 = true;	//take in the first ion as #1
        
        for (int i = 0; i < thedata.length; i++) {

            if (thedata[i].contains("mol pqr ")) {

                if(thedata[i-1].contains("mol pqr ")) {
                    if(thedata[i-2].contains("mol pqr ")) {
                        // there are 3 files so it must be ion solvation
                        calcTypeCombo.setSelectedIndex(1);
                    }
                    else {
                        // there are 2 files so it must be gating charge
                        String f_temp = thedata[i].substring((thedata[i].indexOf("mol pqr ") + 8), thedata[i].length());
                        if (f_temp.startsWith("\""))
                            f_temp = f_temp.substring(1,f_temp.length()-1);
                        pqrFile2.setText(f_temp);
                        calcTypeCombo.setSelectedIndex(2);
                    }
                }
                else {
                    String f_temp = thedata[i].substring((thedata[i].indexOf("mol pqr ") + 8), thedata[i].length());
                        if (f_temp.startsWith("\""))
                            f_temp = f_temp.substring(1,f_temp.length()-1);
                    pqrFile1.setText(f_temp);
                    viewer.openFile(pqrFile1.getText());
                }
            } else if (thedata[i].contains("dime ")) {
                String tempdime[] = thedata[i].substring((thedata[i].indexOf("dime ") + 5), thedata[i].length()).split(" ");
                //if data isnt empty, set the data, else set it to blank
                    if (tempdime.length > 0) {
                        gridDimx.setText(tempdime[0]);
                    } else {
                        gridDimx.setText("");
                    }
                    if (tempdime.length > 1) {
                        gridDimy.setText(tempdime[1]);
                    } else {
                        gridDimy.setText("");
                    }
                    if (tempdime.length > 2) {
                        gridDimz.setText(tempdime[2]);
                    } else {
                        gridDimz.setText("");
                    }

            } else if (thedata[i].contains("glen ")) {
                String tempglen[] = thedata[i].substring((thedata[i].indexOf("glen ") + 5), thedata[i].length()).split(" ");
                //if data isnt empty     set the data                  else  set it to blank
                if(loadfocus==0) {
                    if (tempglen.length > 0) {
                        gridLen1a.setText(tempglen[0]);
                    } else {
                        gridLen1a.setText("");
                    }
                    if (tempglen.length > 1) {
                        gridLen2a.setText(tempglen[1]);
                    } else {
                        gridLen2a.setText("");
                    }
                    if (tempglen.length > 2) {
                        gridLen3a.setText(tempglen[2]);
                    } else {
                        gridLen3a.setText("");
                    }
                } else if (loadfocus==1) {
                    if (tempglen.length > 0) {
                        gridLen1b.setText(tempglen[0]);
                    } else {
                        gridLen1b.setText("");
                    }
                    if (tempglen.length > 1) {
                        gridLen2b.setText(tempglen[1]);
                    } else {
                        gridLen2b.setText("");
                    }
                    if (tempglen.length > 2) {
                        gridLen3b.setText(tempglen[2]);
                    } else {
                        gridLen3b.setText("");
                    }
                }
                else {
                    if (tempglen.length > 0) {
                        gridLen1c.setText(tempglen[0]);
                    } else {
                        gridLen1c.setText("");
                    }
                    if (tempglen.length > 1) {
                        gridLen2c.setText(tempglen[1]);
                    } else {
                        gridLen2c.setText("");
                    }
                    if (tempglen.length > 2) {
                        gridLen3c.setText(tempglen[2]);
                    } else {
                        gridLen3c.setText("");
                    }
                }
            } else if (thedata[i].contains("ion ")) {
                String tempion[] = thedata[i].substring((thedata[i].indexOf("ion ") + 4), thedata[i].length()).split(" ");
                if (countion1) {
                    if (tempion.length > 0) {
                        countIon1Charge.setText(tempion[0]);
                    } else {
                        countIon1Charge.setText("");
                    }
                    if (tempion.length > 1) {
                        countIon1Con.setText(tempion[1]);
                    } else {
                        countIon1Con.setText("");
                    }
                    if (tempion.length > 2) {
                        countIon1Sz.setText(tempion[2]);
                    } else {
                        countIon1Sz.setText("");
                    }
                    countion1 = false;
                } else {
                    if (tempion.length > 0) {
                        countIon2Charge.setText(tempion[0]);
                    } else {
                        countIon2Charge.setText("");
                    }
                    if (tempion.length > 1) {
                        countIon2Con.setText(tempion[1]);
                    } else {
                        countIon2Con.setText("");
                    }
                    if (tempion.length > 2) {
                        countIon2Sz.setText(tempion[2]);
                    } else {
                        countIon2Sz.setText("");
                    }
                }
            } else if (thedata[i].contains("pdie ")) {
                proteinDi.setText(thedata[i].substring((thedata[i].indexOf("pdie ") + 5), thedata[i].length()));
            } else if (thedata[i].contains("sdie ")) {
                solventDi.setText(thedata[i].substring((thedata[i].indexOf("sdie ") + 5), thedata[i].length()));
            } else if (thedata[i].contains("srad ")) {
                srad.setText(thedata[i].substring((thedata[i].indexOf("srad ") + 5), thedata[i].length()));
            } else if (thedata[i].contains("sdens ")) {
                sdens.setText(thedata[i].substring((thedata[i].indexOf("sdens ") + 6), thedata[i].length()));
            } else if (thedata[i].contains("temp ")) {
                temp.setText(thedata[i].substring((thedata[i].indexOf("temp ") + 5), thedata[i].length()));
            } else if (thedata[i].contains("mdie ")) {
                membraneDi.setText(thedata[i].substring((thedata[i].indexOf("mdie ") + 5), thedata[i].length()));
            } else if (thedata[i].contains("zmem ")) {
                zmem.setText(thedata[i].substring((thedata[i].indexOf("zmem ") + 5), thedata[i].length()));
            } else if (thedata[i].contains("lmem ")) {
                Lmem.setText(thedata[i].substring((thedata[i].indexOf("lmem ") + 5), thedata[i].length()));
            } else if (thedata[i].contains("memv ")) {
                potential.setText(thedata[i].substring((thedata[i].indexOf("memv ") + 5), thedata[i].length()));
            } else if (thedata[i].contains("idie ")) {
                idie.setText(thedata[i].substring((thedata[i].indexOf("idie ") + 5), thedata[i].length()));
            } else if (thedata[i].contains("geo1 ")) {
                System.out.println(thedata[i]);
                geoFactor1.setText(thedata[i].substring((thedata[i].indexOf("geo1 ") + 5), thedata[i].length()));
            } else if (thedata[i].contains("geo2 ")) {
                geoFactor2.setText(thedata[i].substring((thedata[i].indexOf("geo2 ") + 5), thedata[i].length()));
            } else if (thedata[i].contains("geo3 ")) {
                geoFactor3.setText(thedata[i].substring((thedata[i].indexOf("geo3 ") + 5), thedata[i].length()));
            } else if (thedata[i].contains("gcent ")) {
                String loadctr = (thedata[i].substring((thedata[i].indexOf("gcent ") + 6), thedata[i].length()));
                if (loadctr.equals("0 0 0")) {
                   centerCombo.setSelectedIndex(0);
                } else if (loadctr.equals("mol 1")) {
                   centerCombo.setSelectedIndex(1);
                }
            }

            else if (thedata[i].contains("lpbe")) {
                solMethodCombo.setSelectedIndex(0);
            } else if (thedata[i].contains("npbe")) {
                solMethodCombo.setSelectedIndex(1);
            } else if (thedata[i].contains("bcfl ")) {
                String loadbcfl = thedata[i].substring((thedata[i].indexOf("bcfl ") + 5), thedata[i].length());
                if (loadbcfl.equals("zero")) {
                   boundaryCondCombo.setSelectedIndex(0);
                } else if (loadbcfl.equals("sdh")) {
                   boundaryCondCombo.setSelectedIndex(1);
                } else if (loadbcfl.equals("mdh")) {
                   boundaryCondCombo.setSelectedIndex(2);
                } else if (loadbcfl.equals("focus")) {
                   boundaryCondCombo.setSelectedIndex(3);
                } else if (loadbcfl.equals("memv")) {
                   boundaryCondCombo.setSelectedIndex(4);
                }

            }
        }
    }	

    /**
     * Simple method for logging debug output to file.
     * @param msg String to write to file for debug purposes.
     */
    protected void log(String msg) {
        if(true) {
            try {
                outFile = new PrintWriter(new FileOutputStream(new File(ofile.getPath() + ".log"), true));
            } catch (Exception e1) {
                System.out.print(e1.toString());
            }
            outFile.println(msg);
            outFile.close();

        }
    }

    /**
     * Locate a file from the filesystem via the JFileChooser.
     * @return True if the file was successfully selected, otherwise false.
     */
    private boolean OpenFile() {

        File currentDir = new File(".");
        final JFileChooser fc = new JFileChooser(currentDir);

        int returnVal = fc.showOpenDialog(theWindow);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            ofile = fc.getSelectedFile();
            file_loaded = true;	//we now have a familiar file
            return true;
        } else {
            return false;
        }
    }

    /**
     * Select a location and name for saving a file.
     * @param savetitle title for the JFileChooser window.
     * @return True if a file path was successfully chosen, otherwise false.
     */
    private boolean SaveDialog(String savetitle) {

        File currentDir = new File(".");
        final JFileChooser fc = new JFileChooser(currentDir);
        fc.setDialogTitle(savetitle);

        int returnVal = fc.showSaveDialog(theWindow);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            ofile = fc.getSelectedFile();
            ofile.mkdir();
            log(ofile.getParent() + "/" + ofile.getName() + "/" + ofile.getName());
            ofile = new File(ofile.getParent() + "/" + ofile.getName() + "/" + ofile.getName());
            file_loaded = true;

            return true;
        } else {
            return false;
        }
    }

    /**
     * Delete a list of temporary files based on extension.
     * @param directory Path to the directory that contains the files.
     * @param extension File extension of the files that will be deleted.
     */
    private void deleteFiles(String directory, String extension) {
        ExtensionFilter filter = new ExtensionFilter(extension);
        File dir = new File(directory);

        String[] list = dir.list(filter);
        File todelete;
        if (list.length == 0) return;
        System.out.println("Cleaning up current working directory...");
        for (int i = 0; i < list.length; i++) {
          todelete = new File(directory, list[i]);
          System.out.println(todelete + "  deleted: " + todelete.delete());
        }
   }

    /**
     * Copy files based on extension
     * @param src Path to source directory.
     * @param dst Path to destination directory.
     * @param extension Extension of files to be copied.
     */
    public void copyFiles(String src, String dst, String extension) {
        ExtensionFilter filter = new ExtensionFilter(extension);
        File dir = new File(src);

        File[] list = dir.listFiles(filter);
        if (list.length == 0) return;
        try {
        for (int i = 0; i < list.length; i++) {
            InputStream in = new FileInputStream(list[i]);
            OutputStream out = new FileOutputStream(new File(dst + "/" + list[i].getName()));

            byte[] buf = new byte[1024];
            int len = 0;

            while ((len = in.read(buf)) > 0)
            {
            out.write(buf, 0, len);

            }

            in.close();
            out.close();
            
            System.out.print(list[i].getPath() + " copied to " + dst);
        }
        } catch (Exception fe) {
            System.out.println(fe);
        }

   }

    private void dependencyCheck() {
        String dependencyError = "";

        //First check to make sure the commandline utilities are available
        File[] requiredFiles = new File[3];
        String osExt = "";
        
        //Add the .exe extension when running on windows
        if(System.getProperty("os.name").startsWith("Windows")) {
            osExt = ".exe";
        }

        requiredFiles[0] = new File("draw_membrane4" + osExt);
        requiredFiles[1] = new File("pull_comps" + osExt);
        requiredFiles[2] = new File("total_charge_off_2" + osExt);

        //make sure the commandline util files exist
        for (File i : requiredFiles) {
            if(!i.exists()) {
                dependencyError += i.getName() + " was not found in your APBSmem directory.\n";
            }
        }

        //Now check to make sure APBS is installed and available
        try {
            ProcessBuilder pb = new ProcessBuilder();
            if(System.getProperty("os.name").startsWith("Windows")) {

            pb = new ProcessBuilder( "apbs.exe", "--version");
            }
            else {
                 pb = new ProcessBuilder( "apbs", "--version");
            }

            Process pp = pb.start();

            int exitVal = pp.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            dependencyError += "Could not run APBS. Make sure it is installed and located on your path.\n";
        }

        if (!dependencyError.equals("")) {
            JOptionPane.showMessageDialog(null, "One or more errors were found during startup:\n" + dependencyError + "\nFor more information please see the APBSmem manual.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

   /**
    *
    */
   public class ExtensionFilter implements FilenameFilter {
          private String extension;
          /**
           *
           * @param extension
           */
          public ExtensionFilter( String extension ) {
            this.extension = extension;
          }

          public boolean accept(File dir, String name) {
            return (name.endsWith(extension));
          }
    }


   /**
    *
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
                jsp.getViewport().add( jep, BorderLayout.CENTER );

            }
            catch (Exception e) {
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

