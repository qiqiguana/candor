package ipac;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

/**
 *  Title: WhoIS Panel
 *  <br>Description: The IP Range Panel for the Graphical User Interface.
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
 *  @version     Beta v0.17
 */

public class WhoIS extends JPanel implements ActionListener{
    /** USER EDITABLE OPTIONS */
        /** WhoIs server name */
        String WHOIS_NAME = "APNIC";
        /** WhoIs server address */
        String WHOIS_ADDRESS = "whois.apnic.net";
        /** WhoIs server port*/
        int WHOIS_PORT = 43;
    
    /** Global Variables -=- DO NOT EDIT */
    JTextField queryTextField;
    JButton queryButton, resetButton;
    JCheckBox lowercaseD;
    boolean booleanD;
    JRadioButton lowercaseL, uppercaseL, lowercaseM, uppercaseM, lowercaseX,
            resetRadioButton;
    ButtonGroup radioButtonGroup = new ButtonGroup();
    JPopupMenu popupMenu;
    JComboBox inverseQueries;
    JList miscList;
    
    /**
     *  Creates a new instance of the WhoIS Panel containing both sub-panels for
     *  use in IPAC (the Main Graphical User Interface).
     */
    public WhoIS() {
        JPanel WhoISPanel = new JPanel();
        WhoISPanel.setLayout(new BoxLayout(WhoISPanel, BoxLayout.Y_AXIS));
        
        createPopupMenu();
        
        WhoISPanel.add(queryPanel());
        WhoISPanel.add(additionalQueriesPanel());
        WhoISPanel.add(queryHintsPanel());
        
        setBackground(Color.WHITE);
        
        add(WhoISPanel);
    }
    
    /**
     *  Creates a sub-panel containing:
     *  <ul>
     *      <li>a <b>"IP Address"</b> label
     *      <li>a Text Field where the user can enter the desired Internet
     *          Protocol Address to be queried
     *      <li>a <b>"Query"</b> button
     *  </ul>
     *
     *  @return      Query sub-panel.
     */
    private JPanel queryPanel(){
        JPanel queryPanel = new JPanel();
        queryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JLabel queryLabel = new JLabel("Search for : ");
        queryPanel.add(queryLabel);
        
        queryTextField = new JTextField(40);
        queryTextField.addMouseListener(new MouseAdapter() {
            public void mousePressed( MouseEvent e ) {
                checkForTriggerEvent(e);
            }
            public void mouseReleased( MouseEvent e ) {
                checkForTriggerEvent(e);
            }
            private void checkForTriggerEvent( MouseEvent e ) {
                if ( e.isPopupTrigger() )
                    popupMenu.show( e.getComponent(), e.getX(), e.getY() );
            }
        });
        queryTextField.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e ){
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    startQuery();
                }
            }
        });
        queryTextField.setToolTipText("Enter Whois database object you would " +
                "like to query");
        queryPanel.add(queryTextField);
        
        queryButton = new JButton("Query");
        queryButton.addActionListener(this);
        queryPanel.add(queryButton);
        
        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        queryPanel.add(resetButton);
        
        queryTextField.setRequestFocusEnabled(true);
        
        queryPanel.setBackground(Color.WHITE);
        
        return queryPanel;
    }
    
    /**
     *  Creates a sub-panel containing two inner sub-panels:
     *  <ul>
     *      <li>IP Address Lookups Panel
     *      <li>Miscellaneous Queries Panel
     *  </ul>
     *
     *  @return      Additional Queries sub-panel.
     */
    private JPanel additionalQueriesPanel(){
        JPanel additionalQueriesPanel = new JPanel();
        additionalQueriesPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        additionalQueriesPanel.add(IPAddressLookups());
        additionalQueriesPanel.add(MiscellaneousQueries());
        
        additionalQueriesPanel.setBackground(Color.WHITE);
        
        return additionalQueriesPanel;
    }
    
    /**
     *  Creates a inner sub-panel containing:
     *  <ul>
     *      <li>a couple clickable radio button labels
     *      <li>a checkbox label for user to <b>"Associated reverse domain"</b>
     *  </ul>
     *
     *  @return      IP Address Lookups inner sub-panel.
     */
    private JPanel IPAddressLookups(){
        JPanel LookupPanel = new JPanel();
        LookupPanel.setLayout(new BoxLayout(LookupPanel, BoxLayout.Y_AXIS));
        
        lowercaseL = new JRadioButton("1st level less specific");
        radioButtonGroup.add(lowercaseL);
        LookupPanel.add(lowercaseL);
        
        uppercaseL = new JRadioButton("All less specific");
        radioButtonGroup.add(uppercaseL);
        LookupPanel.add(uppercaseL);
        
        lowercaseM = new JRadioButton("1st level more specific");
        radioButtonGroup.add(lowercaseM);
        LookupPanel.add(lowercaseM);
        
        uppercaseM = new JRadioButton("All more specific");
        radioButtonGroup.add(uppercaseM);
        LookupPanel.add(uppercaseM);
        
        lowercaseX = new JRadioButton("Exact match only");
        radioButtonGroup.add(lowercaseX);
        LookupPanel.add(lowercaseX);
        
        Action actionListener = new AbstractAction("Associated reverse domain"){
            public void actionPerformed(ActionEvent evt){
                booleanD = ((JCheckBox)evt.getSource()).isSelected();
            }
        };
        lowercaseD = new JCheckBox(actionListener);
        LookupPanel.add(lowercaseD);
        
        resetRadioButton = new JRadioButton("Reset Radio Button");
        radioButtonGroup.add(resetRadioButton);
        
        LookupPanel.setBackground(Color.WHITE);
        lowercaseL.setBackground(Color.WHITE);
        uppercaseL.setBackground(Color.WHITE);
        lowercaseM.setBackground(Color.WHITE);
        uppercaseM.setBackground(Color.WHITE);
        lowercaseX.setBackground(Color.WHITE);
        lowercaseD.setBackground(Color.WHITE);
        
        String toolTipText = "Use these options to view IP address blocks " +
                "that match or are larger than the IP address or range you " +
                "wish to query";
        lowercaseL.setToolTipText(toolTipText);
        uppercaseL.setToolTipText(toolTipText);
        lowercaseM.setToolTipText(toolTipText);
        uppercaseM.setToolTipText(toolTipText);
        lowercaseX.setToolTipText(toolTipText);
        lowercaseD.setToolTipText(toolTipText);
        
        return LookupPanel;
    }
    
    /**
     *  Creates a inner sub-panel containing:
     *  <ul>
     *      <li>a <b>"Inverse Queries"</b> label
     *      <li>a drop-down menu containing Inverse Queries
     *      <li>a <b>"Miscellaneous Queries"</b> label
     *      <li>a list containing Miscellaneous Queries inside a scrollable
     *          panel
     *  </ul>
     *
     *  @return      Miscellaneous Queries inner sub-panel.
     */
    private JPanel MiscellaneousQueries(){
        JPanel MiscPanel = new JPanel();
        MiscPanel.setLayout(new BoxLayout(MiscPanel, BoxLayout.Y_AXIS));
        
        JLabel inverseLabel = new JLabel("Inverse Queries");
        MiscPanel.add(inverseLabel);
        inverseQueries = new JComboBox();
        inverseQueries.addItem("None");
        inverseQueries.addItem("admin-c");
        inverseQueries.addItem("admin-c,tech-c,zone-c");
        inverseQueries.addItem("author");
        inverseQueries.addItem("cross-mnt");
        inverseQueries.addItem("cross-nfy");
        inverseQueries.addItem("local-as");
        inverseQueries.addItem("mbrs-by-ref");
        inverseQueries.addItem("member-of");
        inverseQueries.addItem("mnt-by");
        inverseQueries.addItem("mnt-lower");
        inverseQueries.addItem("mnt-nfy");
        inverseQueries.addItem("mnt-routes");
        inverseQueries.addItem("notify");
        inverseQueries.addItem("nserver");
        inverseQueries.addItem("origin");
        inverseQueries.addItem("person");
        inverseQueries.addItem("referral-by");
        inverseQueries.addItem("rev-srv");
        inverseQueries.addItem("sub-dom");
        inverseQueries.addItem("tech-c");
        inverseQueries.addItem("upd-to");
        inverseQueries.addItem("zone-c");
        inverseQueries.setToolTipText("Searching for objects in the "+
                WHOIS_NAME + " Whois Database that have an attribute matching" +
                " the attribute type chosen from the inverse lookup scroll " +
                "list and the query text given by the user");
        MiscPanel.add(inverseQueries);
        
        JLabel miscLabel = new JLabel("Object Types");
        MiscPanel.add(miscLabel);
        String[] items = {"All", "as-block", "as-set", "aut-num", "domain",
        "filter-set", "inet-rtr", "inet6num", "inetnum", "irt", "key-cert",
        "mntner", "peering-set", "person", "role", "route", "route-set",
        "rtr-set"};
        miscList = new JList(items);
        miscList.setSelectedIndex(0);
        miscList.setToolTipText("Limit your search to particular types of " +
                "objects");
        JScrollPane scrollingList = new JScrollPane(miscList);
        
        MiscPanel.add(scrollingList);
        
        MiscPanel.setBackground(Color.WHITE);
        inverseQueries.setBackground(Color.WHITE);
        
        return MiscPanel;
    }
    
    /**
     *  Creates a sub-panel placed on the bottom of the main panel.
     *
     *  @return     Query Hints sub-panel
     */
    private JPanel queryHintsPanel(){
        JPanel queryHintsPanel = new JPanel();
        queryHintsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        
        JLabel queryHintsText1 = new JLabel("Query hints ");
        JLabel emptyLabel = new JLabel("EMPTY LABEL");
        emptyLabel.setForeground(Color.WHITE);
        JLabel queryHintsText2 = new JLabel("* Include \"AS\" in front of an " +
                "AS number.");
        JLabel queryHintsText3 = new JLabel("Example: AS4808");
        JLabel queryHintsText4 = new JLabel("* Include \"-t\" (template only)" +
                " or \"-v\" (template and description) in front of an object " +
                "name to view the template.");
        JLabel queryHintsText5 = new JLabel("Example: -t inetnum");
        
        innerPanel.add(queryHintsText1);
        innerPanel.add(emptyLabel);
        innerPanel.add(queryHintsText2);
        innerPanel.add(queryHintsText3);
        innerPanel.add(queryHintsText4);
        innerPanel.add(queryHintsText5);
        
        queryHintsPanel.setBackground(Color.WHITE);
        innerPanel.setBackground(Color.WHITE);
        
        queryHintsPanel.add(innerPanel);
        return queryHintsPanel;
    }
    
    /**
     *  Create a right-click drop-down menu for the <b>queryTextField</b>
     *  <code>JTextField</code>
     */
    private void createPopupMenu(){
        popupMenu = new JPopupMenu();
        JMenuItem cut = new JMenuItem("Cut");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem paste = new JMenuItem("Paste");
        JMenuItem selectAll = new JMenuItem("Select All");
        
        popupMenu.add(cut);
        popupMenu.add(copy);
        popupMenu.add(paste);
        popupMenu.add(selectAll);
        
        cut.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                queryTextField.cut();
            }
        });
        
        copy.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                queryTextField.copy();
            }
        });
        
        paste.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                queryTextField.paste();
            }
        });
        
        selectAll.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                queryTextField.selectAll();
            }
        });
    }
    
    /**
     *  Determines what to do when a button is pressed.
     *
     *  <p>If <b>Calculate</b> button is pressed, it triggers the
     *  <code>startQuery()</code> method to query the WhoIS server address.
     *
     *  <p>If <b>Reset</b> button is pressed, it initialises the
     *  <code>reset()</code> method to reset this panel back to its initial
     *  state.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == queryButton)
            startQuery();
        else if (e.getSource() == resetButton)
            reset();
    }
    
    /**
     *  Starts querying the WhoIs server if the query length is greater than 1.
     */
    private void startQuery(){
        if (getQuery().length() < 1)
            displayError("No Query Detected");
        else
            query(getQuery());
    }
    
    /**
     *  Displays errors in a notification pop-up box.
     *
     *  @param      error       The error message to be displayed.
     */
    private void displayError(String error){
        JOptionPane.showMessageDialog(this, error, "Error",
                JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     *  Establishs a connection to the WhoIS server, sends the query to the
     *  server and reads the query's result then sends the output to
     *  <code><b>WhoISOutput.java</b></code>
     *
     *  <p>Displays appropriate errors if the WhoIS server address was declared
     *  incorrectly or the WhoIS server cannot be contacted.
     *
     *  @param      query       WhoIs Database Object to be queried.
     */
    private void query(String query) {
        try {
            Socket connection = new Socket(WHOIS_ADDRESS, WHOIS_PORT);
            PrintStream out = new PrintStream(connection.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line = "";
            
            out.println(query);
            
            String output = "";
            while ((line = in.readLine()) != null)
                output += line + "\n";
            
            new WhoISOutput(output);
            output = "";
        } catch (java.net.UnknownHostException e) {
            displayError("Unknown Whois host: " + WHOIS_ADDRESS);
        } catch (IOException e) {
            displayError("Whois server could not be contacted.\nPlease check" +
                    " your internet connection.");
        }
    }
    
    /**
     *  Retrieves the WhoIs Database object to be queried and any selections the
     *  user has made.
     *
     *  @return     Full query with WhoIs Database object and any additional
     *              queries.
     */
    private String getQuery(){
        String query = queryTextField.getText().trim();
        
        String iqs = inverseQueries.getSelectedItem().toString();
        if (!iqs.equals("None"))
            query = "-i " + iqs + " " + query;
        
        if (lowercaseL.isSelected())
            query = "-l " + query;
        else if (uppercaseL.isSelected())
            query = "-L " + query;
        else if (lowercaseM.isSelected())
            query = "-m " + query;
        else if (uppercaseM.isSelected())
            query = "-M " + query;
        else if (lowercaseX.isSelected())
            query = "-x " + query;
        
        if (booleanD)
            query = "-d " + query;
        
        String miscQueries = "";
        Object[] selected = miscList.getSelectedValues();
        for (int i = 0; i < selected.length; i++)
            if (selected[i] != "All")
                miscQueries += selected[i] + ",";
        
        if (miscQueries != ""){
            miscQueries = miscQueries.substring(0, (miscQueries.length() - 1));
            query = "-T " + miscQueries + " " + query;
        }
        
        return query;
    }
    
    /** Resets this panel to its initial state on startup. */
    public void reset(){
        queryTextField.setText("");
        resetRadioButton.setSelected(true);
        lowercaseD.setSelected(false);
        inverseQueries.setSelectedIndex(0);
        miscList.setSelectedIndex(0);
    }
}