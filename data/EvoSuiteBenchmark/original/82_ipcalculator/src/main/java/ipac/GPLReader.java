package ipac;
import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 *  Title: GNU GPL Reader
 *  <br>Description: Displays the GNU General Public License (GPL) inside a
 *                   scrollable panel.
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
 *  @version     Beta v0.17
 */

public class GPLReader extends JFrame{
    
    /** Creates a new instance of GNU GPL Reader */
    public GPLReader() {
        super("The GNU General Public License(GPL)");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 550);
        setResizable(true);
        setVisible(true);
        setBackground(Color.WHITE);
        JFrame.setDefaultLookAndFeelDecorated(false);
        
        /** Point the program to where the license is stored */
        InputStream is = getClass().getResourceAsStream("/ipac/License.txt");
        StringBuffer sb = new StringBuffer();
        int chr;
        
        /** Read each character and append to the output string */
        try {
            while ((chr = is.read()) != -1)
                sb.append((char) chr);
            is.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        /**
         *  Add output string into a text area which is then added into a
         *  scrollable panel to be placed onto the window
         */
        getContentPane().add(new JScrollPane(new JTextArea(sb.toString())));
        
        /** Change default program icon look to the APNIC Logo */
        java.net.URL imgURL = getClass().getResource("APNIC_Logo.JPG");
        setIconImage(new ImageIcon(imgURL).getImage());
        
        /** Re-pack all components to prevent display errors */
        pack();
        setSize(550, 550);
    }
}