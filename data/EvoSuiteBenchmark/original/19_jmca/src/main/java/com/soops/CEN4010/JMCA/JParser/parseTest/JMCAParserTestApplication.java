package com.soops.CEN4010.JMCA.JParser.parseTest;

import java.awt.Toolkit;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Dimension;
import com.soops.CEN4010.JMCA.ASTNode;
import com.soops.CEN4010.JMCA.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.*;


public class JMCAParserTestApplication {
    boolean packFrame = false;

    /**
     * Construct and show the application.
     */
    public JMCAParserTestApplication( String input) {
        Reader rdr = null;
        Parser jparser = new JMCAParser();
        try {
            rdr = new FileReader(new File(input));
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
            return;
        }
        String outfile = input.substring(0, input.indexOf("."));
        ASTNode tmp = jparser.parse(rdr);
        tmp.dump(outfile + ".node");
        DefaultMutableTreeNode rootNode = tmp.createTree();

        JMCAParserTestFrame frame = new JMCAParserTestFrame(rootNode, outfile);


        if (packFrame) {
            frame.pack();
         } else {
            frame.validate();
        }

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

    /**
     * Application entry point.
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        String input = "test.txt";
        if (args.length == 1) {
            input = args[0];
        }
        final String finalString = input;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.
                                             getSystemLookAndFeelClassName());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                new JMCAParserTestApplication(finalString);
            }
        });
    }
}
