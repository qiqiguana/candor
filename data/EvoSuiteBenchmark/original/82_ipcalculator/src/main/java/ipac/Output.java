package ipac;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;

/**
 *  Title: Output Printer
 *  <br>Description: Displays the results in a Table format inside a scrollable
 *                  panel.
 *  <br>Copyright (c) 2006-7 Jason Wang
 *
 *  <p>This program is free software; you can redistribute it and/or
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

public class Output extends JFrame{
    final JTable resultTable;
    /**
     *  Prints the given data onto the table inside the scrollable panel.
     *
     *  @param   data    The data that will be printed onto the table inside the
     *                   scrollable panel.
     */
    public Output(Object [] [] data) {
        super("Results");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setResizable(true);
        setBackground(Color.WHITE);
        JFrame.setDefaultLookAndFeelDecorated(false);
        String [] columnHeader = {"Prefix", "IP Address Range"};
        resultTable = new JTable(data, columnHeader);
        
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenuBar = new JMenu("File");
        fileMenuBar.setMnemonic(KeyEvent.VK_F);
        
        JMenuItem saveMenuItem = new JMenuItem("Save As");
        saveMenuItem.setMnemonic(KeyEvent.VK_S);
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, Event.CTRL_MASK));
        saveMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                save();
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
                    resultTable.print();
                } catch (PrinterException ex) {
                    new IPv4().displayError("Printer not found");
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
        
        getContentPane().add(new JScrollPane(resultTable));
        
        /** Change default program icon look to the APNIC Logo */
        java.net.URL imgURL = getClass().getResource("APNIC_Logo.JPG");
        setIconImage(new ImageIcon(imgURL).getImage());
        
        
        
        /** Re-pack all components to prevent display errors */
        pack();
        setSize(400, 300);
        setVisible(true);
    }
    
    public void save() {
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
                String text = "";
                for (int i = 0; i < resultTable.getRowCount(); i++){
                    text = text + resultTable.getValueAt(i, 0) + "\t" + 
                            resultTable.getValueAt(i, 1) + "\n";
                }
                data = text.getBytes();
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