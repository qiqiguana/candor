package ipac;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;

/**
 *  Title: WhoIS Output Printer
 *  <br>Description: Displays the results in a Text Area.
 *  <br>Copyright (c) 2006-7 Jason Wang
 *
 *  <br>This program is free software; you can redistribute it and/or
 *  <br>modify it under the terms of the GNU General Public License
 *  <br>as published by the Free Software Foundation; either version 2
 *  <br>of the License, or (at your option) any later version.
 *
 *  <br>This program is distributed in the hope that it will be useful,
 *  <br>but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  <br>MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  <br>GNU General Public License for more details.
 *
 *  <br>You should have received a copy of the GNU General Public License
 *  <br>along with this program; if not, write to the Free Software
 *  <br>Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 *                                                              02110-1301, USA.
 *
 *  @author      Jason Wang
 *  @version     v0.18
 */

public class WhoISOutput extends JFrame{
    
    /**
     *  Prints the given string from the <code><b>WhoIS.java</b></code> panel
     *  onto a text area within a scrollable panel.
     *
     *  @param   WhoISOutput     The <code>string</code> to be printed by this
     *                           method.
     */
    public WhoISOutput(final String WhoISOutput) {
        super("Whois Results");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setResizable(true);
        setBackground(Color.WHITE);
        JFrame.setDefaultLookAndFeelDecorated(false);
        
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenuBar = new JMenu("File");
        fileMenuBar.setMnemonic(KeyEvent.VK_F);
        
        JMenuItem saveMenuItem = new JMenuItem("Save As");
        saveMenuItem.setMnemonic(KeyEvent.VK_S);
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, Event.CTRL_MASK));
        saveMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                save(WhoISOutput);
            }
        });
        fileMenuBar.add(saveMenuItem);
        
        JMenuItem printMenuItem = new JMenuItem("Print");
        printMenuItem.setMnemonic(KeyEvent.VK_P);
        printMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_P, Event.CTRL_MASK));
        printMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    new JTextArea(WhoISOutput).print();
                } catch (PrinterException ex) {
                    ex.printStackTrace();
                }
            }
        });
        fileMenuBar.add(printMenuItem);
        
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setMnemonic(KeyEvent.VK_X);
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_X, Event.ALT_MASK));
        exitMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });
        fileMenuBar.add(exitMenuItem);
        
        menuBar.add(fileMenuBar);
        setJMenuBar(menuBar);
        
        getContentPane().add(new JScrollPane(new JTextArea(WhoISOutput)));
        
        /** Change default program icon look to the APNIC Logo */
        java.net.URL imgURL = getClass().getResource("APNIC_Logo.JPG");
        setIconImage(new ImageIcon(imgURL).getImage());
        
        /** Re-pack all components to prevent display errors */
        pack();
        setSize(500, 300);
        setVisible(true);
    }
        
    public void save(String WhoISOutput) {
        FileDialog file = new FileDialog(this, "Save As", FileDialog.SAVE);
        file.setFile("*.txt");  // set initial filename filter
        file.setVisible(true);
        String curFile;
        if ((curFile = file.getFile()) != null) {
            String filename = file.getDirectory() + curFile;
            // curFile ends in .*.* if file does not exist
            byte[] data;
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            // Remove trailing ".*.*" if present - signifies file does not exist
            if (filename.indexOf(".*.*") != -1) {
                filename = filename.substring(0, filename.length()-4);
            }
            File f = new File(filename);
            try {
                FileOutputStream fon = new FileOutputStream(f);
                data = WhoISOutput.getBytes();
                fon.write(data);
                fon.close();
            } catch (IOException exc) {
                String errorString = "IOException: " + filename;
                data = new byte[errorString.length()];
                errorString.getBytes();
            }
        }
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
}