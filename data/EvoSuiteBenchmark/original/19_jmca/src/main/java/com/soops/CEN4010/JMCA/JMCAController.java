/*
 JMCAController.java

 Contains class JMCAController
 JMCAController accepts a filename as input, then calls other classes to
 1) parse the file and get the Abstract Syntax Tree (AST)
 2) pass the AST to the cohesion analyzer class
 3) get the report output from the analyzer


 CEN4010
 FALL 2005
 GROUP2

 Curt Hayashida, Scott Springer, Ian Cole


 Revision History:
 10/15/2005 - IC - Quick & Dirty Class Creation for prototype
 10/24/2005 - IC - First Integration pass with GUI code
           1) Added to package
           2) Added comments to the analyze() method
             based on Scott's early design
 10/30/2005 - IC - Convert to true MVC (Model-View-Controller) per the grading notes
      1) Changing from JMCAFrame has a JMCAController to
       JMCAController (Controller) has a JMCAFrame (View)
      2) Removed constructor that took filename - unneeded with MVC
      3) Removed get / set filename - unneeded
      4) Removed getOutput - unneeded
      5) Main function now target for application execution
      6) Main function updated to MVC pattern
      7) Converted to use JMCAView

 11/13/2005 - SS - Change parser to JMCAParser, analyzer to JMCAAnalyzer

 Compilation (from project directory)
 javac -d . *.java

 Execution (from top-level project directory)
 java com.soops.CEN4010.JMCA.JMCAController


 */


package com.soops.CEN4010.JMCA;

import java.io.Reader;
import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.awt.event.*;
import java.awt.Toolkit;
import com.soops.CEN4010.JMCA.JParser.parseTest.JMCAParserTestFrame;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.Dimension;


/**
 *
 * <p>Title: Group 2 Project </p>
 *
 * <p>Class: CEN4010 Fall 2005</p>
 *
 * <p>Description: Controller manages subsystems of JMCA </p>
 *
 * <p>Instructor Dr. Stoeklin</p>
 *
 * @author Group 2
 */

public class JMCAController {
    private static final boolean DEVELOPMENT = true;

    private static JMCAView view;

    /**
     * default constructor
     */
    JMCAController() {
    }


    /**
     *   controls Parser and Analyzer
     *     analyze will return String with analysis or error text
     * @return String
     */
    public static String analyze(String filename) {

        /**
         * implement placeholder implementations of Parser and Analyzer
         *       Reader rdr = new FileReader (new File (filename));
         *       ASTNode rootNode = new JMCAParser().parse(rdr);
         *       output = new JMCAAnalyzer().analyze(rootNode);
         */
        if (filename != null && !filename.equals("")) {
            Reader rdr = null;
            try {
                rdr = new FileReader(new File(filename));
            } catch (IOException ie) {
                System.err.println(ie.getMessage());
            }
            /**
             * call parser
             */
            // ASTNode rootNode = new DummyParser().parse(rdr);
            ASTNode rootNode = new JMCAParser().parse(rdr);
            if (DEVELOPMENT && rootNode != null)  showTree(rootNode, filename);
            /**
             * call analyzer
             */
            //  Analyzer analyzer = new UILayoutDisplayAnalyzer();
            Analyzer analyzer = new JMCAAnalyzer();
            analyzer.setFileName(filename);
            return analyzer.analyze(rootNode);
        }

        return new String("ERROR: Filename not entered.");

    } //end analyze()


    private static void showTree(ASTNode tmp, String filename) {
        DefaultMutableTreeNode rootNode = tmp.createTree();

        JMCAParserTestFrame frame = new JMCAParserTestFrame(rootNode, filename);

        frame.validate();

// Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2,
                          (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);

    }


    //main function for JMCA Application
    public static void main(String[] args) {

        view = new JMCAView();
        // in this case our model is the Parser / Analyzer

        //set action listeners here....
        view.setAnalyzeListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                view.updateDisplay(analyze(view.getFilename()));
            }
        }); //end setAnalyzeListener

        view.setSelectFileListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.chooseFile();
            }
        }); //end setSelectFileListener

    } //end main


} //end class
