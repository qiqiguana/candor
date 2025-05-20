package ipac;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

/**
 *  Title: Internet Protocol Address Calculator (IPAC) Applet
 *  <br>Description: Internet Protocol Address Calculator (IPAC) Main Graphical
 *                   User Interface (GUI).
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

public class IPAC_Applet extends JApplet implements ChangeListener {
    /** USER EDITABLE OPTIONS */
        /** URL of online help files */
        String HELPTOPICURL = "http://www.apnic.net/temp/help.html";
    
    /** Global Variables -=- DO NOT EDIT */
    JTabbedPane IPAddressCalculator;
    IPv4 IPv4 = new IPv4();
    IPv6 IPv6 = new IPv6();
    WhoIS Whois = new WhoIS();
    
    /** Creates a new instance of IPAC Applet */
    public void init() {
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    initComponents();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void initComponents() {
        setSize(800, 600);
        setVisible(true);
        JFrame.setDefaultLookAndFeelDecorated(false);
        
        setJMenuBar(addMenuBar());
        
        try {
            getContentPane().setLayout(new BorderLayout());
            getContentPane().add(addTopPanel(), BorderLayout.NORTH);
            createTabbedPane();
            getContentPane().add(IPAddressCalculator, BorderLayout.CENTER);
            getContentPane().setBackground(Color.WHITE);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     *  Copies the IP Address from IPv4 and IPv6 textfields when
     *  <code>JTabbedPane</code> changes its state to WhoIs tab.
     *
     *  <p>Overrides abstract method stateChanged(javax.swing.event.ChangeEvent)
     *  in javax.swing.event.ChangeListener.
     *
     *  @param  e    a ChangeEvent object
     */
    public void stateChanged(ChangeEvent e){
        String tabName = IPAddressCalculator.getTitleAt(
                ((DefaultSingleSelectionModel)e.getSource()).getSelectedIndex());
        String query = "";
        if (tabName == "Whois"){
            if (IPv4.IPAddressTextField.getText().trim().length() != 0)
                query = IPv4.IPAddressTextField.getText();
            else if (IPv6.IPAddressTextField.getText().trim().length() != 0)
                query = IPv6.IPAddressTextField.getText();
        }
        StringTokenizer token = new StringTokenizer (query, " ");
        if (token.hasMoreTokens())
            Whois.queryTextField.setText(token.nextToken() +  token.nextToken());
        else 
            Whois.queryTextField.setText(query);
    }
    
    /**
     *  Creates the top section of this program.
     *
     *  <p>This includes:
     *  <ul>
     *      <li>The APNIC logo
     *      <li>Full APNIC Name
     *      <li>Name of this program
     *  </ul>
     *
     *  @return     A <code>JPanel</code> containing all components for the top
     *              section of this program.
     */
    private JPanel addTopPanel(){
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(Color.WHITE);
        
        JPanel topBar = new JPanel(new BorderLayout());
        
        JLabel apnicLogoLabel = new JLabel(new ImageIcon(getClass().getResource(
                "logo.jpg")));
        topBar.add(apnicLogoLabel, BorderLayout.WEST);
        
        JLabel apnicLabel =
                new JLabel("Asia Pacific Network Information Centre");
        apnicLabel.setFont(new Font("Arial", Font.BOLD, 15));
        apnicLabel.setForeground(new Color(2, 69, 169));
        apnicLabel.setBorder(BorderFactory.createMatteBorder(
                0, 0, 30, 10, Color.WHITE));
        topBar.add(apnicLabel, BorderLayout.EAST);
        
        topBar.setBackground(Color.WHITE);
        topPanel.add(topBar);
        
        JPanel bottomBar = new JPanel(new FlowLayout());
        
        JLabel programNameLabel = new JLabel(
                "  IP Address Calculator  ", JLabel.LEFT);
        programNameLabel.setOpaque(true);
        programNameLabel.setForeground(new Color(2, 69, 169));
        programNameLabel.setFont(new Font("Verdana", Font.BOLD, 30));
        programNameLabel.setBackground(new Color(255, 204, 102));
        
        bottomBar.add(programNameLabel);
        bottomBar.setBackground(new Color(255, 204, 102));
        bottomBar.setBorder(BorderFactory.createMatteBorder(
                10, 10, 10, 10, Color.WHITE));
        topPanel.add(bottomBar);
        
        return topPanel;
    }
    
    /**
     *  Adds three panels to the <code>JTabbedPane</code>.
     */
    private void createTabbedPane(){
        IPAddressCalculator = new JTabbedPane(JTabbedPane.TOP);
        IPAddressCalculator.addTab("IPv4", IPv4);
        IPAddressCalculator.addTab("IPv6", IPv6);
        IPAddressCalculator.addTab("Whois", Whois);
        IPAddressCalculator.getModel().addChangeListener(this);
        
        IPAddressCalculator.setForeground(new Color(2, 69, 169));
        IPAddressCalculator.setBackground(Color.WHITE);
        IPAddressCalculator.setOpaque(false);
    }
    
    /**
     *  Creates a <code>menuBar</code> where the user can view the license and
     *  the online help files.
     *
     *  @return      A <code>menuBar</code> containing these components.
     */
    private JMenuBar addMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenuBar = new JMenu("File");
        fileMenuBar.setMnemonic(KeyEvent.VK_F);
        
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setMnemonic(KeyEvent.VK_X);
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_X, Event.ALT_MASK));
        exitMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        fileMenuBar.add(exitMenuItem);
        
        menuBar.add(fileMenuBar);
        
        JMenu helpMenuBar = new JMenu("Help");
        helpMenuBar.setMnemonic(KeyEvent.VK_H);
        
        JMenuItem helpTopicsMenuItem = new JMenuItem("Help Topics");
        helpTopicsMenuItem.setMnemonic(KeyEvent.VK_T);
        helpTopicsMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new URLOpener(HELPTOPICURL);
            }
        });
        helpMenuBar.add(helpTopicsMenuItem);
        
        JMenuItem GPLReaderMenuItem = new JMenuItem("GNU GPL Reader");
        GPLReaderMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new GPLReader();
            }
        });
        helpMenuBar.add(GPLReaderMenuItem);
        
        menuBar.add(helpMenuBar);
        
        return menuBar;
    }
}