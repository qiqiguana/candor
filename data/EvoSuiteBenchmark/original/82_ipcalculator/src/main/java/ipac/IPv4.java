package ipac;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.math.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 *  Title: IPv4 Panel
 *  <br>Description: The IPv4 Panel for the Graphical User Interface.
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
 *  @version     v0.19
 */

public class IPv4 extends JPanel implements ActionListener {
    /** USER EDITABLE OPTIONS */
    /** Maximum Prefix Size / Total bits in IP address */
    int MAXPREFIX = 32;
    /** Number of bits per group */
    int BITS_PER_GROUP = 8;
    /** Characters per group */
    int CHARS_PER_GROUP = 3;
    /** Number of groups */
    int NUMBER_OF_GROUPS = MAXPREFIX / BITS_PER_GROUP;
    /** Delimiter used to seperate groups */
    String DELIMITER = ".";
    /** Color of the left column */
    Color TABLE_HEADINGS_BACKGROUND = new Color(240, 248, 255);
    /** Color around each column */
    Color TABLE_CELL_COLOR = new Color(11, 196, 222);
    
    /** Global Variables -=- DO NOT EDIT */
    Border TABLE_CELL_BORDER = BorderFactory.createMatteBorder(1, 1, 0, 1,
            TABLE_CELL_COLOR);
    JTextField IPAddressTextField, numberOfIPTextField, netmaskTextField,
            rangeTextField;
    JTextArea outputTextArea;
    JCheckBox IPAddressCheckBox, rangeCheckBox;
    JRadioButton splittingPrefixRadioButton, numberOfIPRadioButton,
            netmaskRadioButton, rangeRadioButton;
    ButtonGroup radioButtonGroup = new ButtonGroup();
    JComboBox childPrefix;
    JButton calculateButton, resetButton;
    
    JPanel IPv4Panel;
    GridBagConstraints gridBagConstraints;
    
    /**
     *  Creates a new instance of the IPv4 Panel for the Main Graphical User
     *  Interface.
     */
    public IPv4() {
        setBackground(Color.WHITE);
        IPv4Panel = new JPanel(new GridBagLayout());
        IPv4Panel.setBackground(Color.WHITE);
        
        /**
         *  Components within this panel are left-aligned and stretched by
         *  default.
         */
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        
        /** Add the components onto this panel */
        addIPAddressRow();
        addRangeRow();
        addNetmaskRow();
        addSplittingPrefixRow();
        addNumberOfIPAddressRow();
        addButtonsRow();
        addOutputRow();
        
        add(IPv4Panel);
    }
    
    /**
     *  Creates the IP Address row by adding a <code>JLabel</code>, a
     *  <code>JTextField</code> and a <code>JCheckBox</code> to the
     *  <code>JPanel</code>
     */
    private void addIPAddressRow(){
        /** Internet Protocol Address Label */
        JLabel IPAddressLabel = new JLabel("  Start Address / Prefix  ");
        IPAddressLabel.setOpaque(true);
        IPAddressLabel.setBackground(TABLE_HEADINGS_BACKGROUND);
        IPAddressLabel.setBorder(TABLE_CELL_BORDER);
        IPAddressLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        /** Edit default global constraints for this component */
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        IPv4Panel.add(IPAddressLabel, gridBagConstraints);
        /**********************************************************************/
        /**
         *  Create the right-click drop-down menu for the
         *  <code>JTextField</code>
         */
        final JPopupMenu popupMenu = new JPopupMenu();
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
                IPAddressTextField.cut();
            }
        });
        
        copy.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                IPAddressTextField.copy();
            }
        });
        
        paste.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                IPAddressTextField.paste();
            }
        });
        
        selectAll.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                IPAddressTextField.selectAll();
            }
        });
        
        /** Internet Protocol Address Text Field */
        IPAddressTextField = new JTextField(25);
        
        /** Add drop-down menu */
        IPAddressTextField.addMouseListener(new MouseAdapter() {
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
        
        /** When "ENTER" key is pressed, it calls <code>calculate()</code> */
        IPAddressTextField.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e ){
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    calculate();
                }
            }
        });
        IPAddressTextField.setToolTipText("Enter a valid IPv4 address. " +
                "e.g. 202.198.1.0");
        
        /** Edit default global constraints for this component */
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridwidth = 3;
        IPv4Panel.add(IPAddressTextField, gridBagConstraints);
        gridBagConstraints.gridwidth = 1;
        IPAddressTextField.setRequestFocusEnabled(true);
        /**********************************************************************/
        /** Padding option */
        Action actionListener = new AbstractAction("  Padded  ") {
            public void actionPerformed(ActionEvent evt){
                boolean inputPadded = ((JCheckBox)evt.getSource()).isSelected();
                pad(IPAddressTextField, inputPadded);
            }
        };
        IPAddressCheckBox = new JCheckBox(actionListener);
        
        /** When "ENTER" key is pressed, it calls <code>calculate()</code> */
        IPAddressCheckBox.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e ){
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    calculate();
                }
            }
        });
        IPAddressCheckBox.setToolTipText("Zero pad decimal representation");
        IPAddressCheckBox.setBackground(Color.WHITE);
        
        /** Edit default global constraints for this component */
        gridBagConstraints.gridx = 5;
        IPv4Panel.add(IPAddressCheckBox, gridBagConstraints);
    }
    
    /**
     *  Creates the IP Range row by adding a <code>JLabel</code>, a
     *  <code>JRadioButton</code>, a <code>JTextField</code> and a
     *  <code>JCheckBox</code> to the <code>JPanel</code>
     */
    private void addRangeRow() {
        /** Range Label */
        JLabel rangeLabel = new JLabel("  End Address  ");
        rangeLabel.setBackground(TABLE_HEADINGS_BACKGROUND);
        rangeLabel.setOpaque(true);
        rangeLabel.setFont(new Font("Arial", Font.BOLD, 12));
        rangeLabel.setBorder(TABLE_CELL_BORDER);
        
        /** Edit default global constraints for this component */
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        IPv4Panel.add(rangeLabel, gridBagConstraints);
        /**********************************************************************/
        /** Netmask Radio Button */
        rangeRadioButton = new JRadioButton();
        rangeRadioButton.addActionListener(this);
        radioButtonGroup.add(rangeRadioButton);
        
        /** When "ENTER" key is pressed, it calls <code>calculate()</code> */
        rangeRadioButton.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e ){
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    calculate();
                }
            }
        });
        rangeRadioButton.setBackground(Color.WHITE);
        
        /** Edit default global constraints for this component */
        gridBagConstraints.gridx = 1;
        IPv4Panel.add(rangeRadioButton, gridBagConstraints);
        /**********************************************************************/
        /**
         *  Create the right-click drop-down menu for the
         *  <code>JTextField</code>
         */
        final JPopupMenu popupMenu = new JPopupMenu();
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
                rangeTextField.cut();
            }
        });
        
        copy.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                rangeTextField.copy();
            }
        });
        
        paste.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                rangeTextField.paste();
            }
        });
        
        selectAll.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                rangeTextField.selectAll();
            }
        });
        
        /** Netmask Text Field */
        rangeTextField = new JTextField();
        
        /** Add drop-down menu */
        rangeTextField.addMouseListener(new MouseAdapter() {
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
        
        /** When "ENTER" key is pressed, it calls <code>calculate()</code> */
        rangeTextField.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e ){
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    calculate();
                }
            }
        });
        rangeTextField.setToolTipText("Enter the end IP address range you " +
                "wish to calculate");
        rangeTextField.setEditable(false);
        
        /** Edit default global constraints for this component */
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridwidth = 3;
        IPv4Panel.add(rangeTextField, gridBagConstraints);
        gridBagConstraints.gridwidth = 1;
        /**********************************************************************/
        /** Padding option */
        Action actionListener = new AbstractAction("  Padded  ") {
            public void actionPerformed(ActionEvent evt){
                boolean rangePadded = ((JCheckBox)evt.getSource()).isSelected();
                pad(rangeTextField, rangePadded);
            }
        };
        rangeCheckBox = new JCheckBox(actionListener);
        
        /** When "ENTER" key is pressed, it calls <code>calculate()</code> */
        rangeCheckBox.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e ){
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    calculate();
                }
            }
        });
        rangeCheckBox.setToolTipText("Zero pad decimal representation");
        rangeCheckBox.setBackground(Color.WHITE);
        rangeCheckBox.setEnabled(false);
        
        /** Edit default global constraints for this component */
        gridBagConstraints.gridx = 5;
        IPv4Panel.add(rangeCheckBox, gridBagConstraints);
    }
    
    /**
     *  Creates the Netmask row by adding a <code>JLabel</code>, a
     *  <code>JRadioButton</code> and a <code>JTextField</code> to the
     *  <code>JPanel</code>
     */
    private void addNetmaskRow() {
        /** Netmask Label */
        JLabel netmaskLabel = new JLabel("  Netmask  ");
        netmaskLabel.setBackground(TABLE_HEADINGS_BACKGROUND);
        netmaskLabel.setOpaque(true);
        netmaskLabel.setFont(new Font("Arial", Font.BOLD, 12));
        netmaskLabel.setBorder(TABLE_CELL_BORDER);
        
        /** Edit default global constraints for this component */
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        IPv4Panel.add(netmaskLabel, gridBagConstraints);
        /**********************************************************************/
        /** Netmask Radio Button */
        netmaskRadioButton = new JRadioButton();
        netmaskRadioButton.addActionListener(this);
        radioButtonGroup.add(netmaskRadioButton);
        
        /** When "ENTER" key is pressed, starts calculations */
        netmaskRadioButton.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e ){
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    calculate();
                }
            }
        });
        netmaskRadioButton.setBackground(Color.WHITE);
        
        /** Edit default global constraints for this component */
        gridBagConstraints.gridx = 1;
        IPv4Panel.add(netmaskRadioButton, gridBagConstraints);
        /**********************************************************************/
        /**
         *  Create the right-click drop-down menu for the
         *  <code>JTextField</code>
         */
        final JPopupMenu popupMenu = new JPopupMenu();
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
                netmaskTextField.cut();
            }
        });
        
        copy.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                netmaskTextField.copy();
            }
        });
        
        paste.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                netmaskTextField.paste();
            }
        });
        
        selectAll.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                netmaskTextField.selectAll();
            }
        });
        
        /** Netmask Text Field */
        netmaskTextField = new JTextField();
        netmaskTextField.setEditable(false);
        
        /** Add drop-down menu */
        netmaskTextField.addMouseListener(new MouseAdapter() {
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
        
        /** When "ENTER" key is pressed, it calls <code>calculate()</code> */
        netmaskTextField.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e ){
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    calculate();
                }
            }
        });
        netmaskTextField.setToolTipText("Enter the netmask you wish to " +
                "calculate");
        
        /** Edit default global constraints for this component */
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridwidth = 3;
        IPv4Panel.add(netmaskTextField, gridBagConstraints);
        gridBagConstraints.gridwidth = 1;
    }
    
    /**
     *  Creates the Splitting Prefix row by adding three <code>JLabel</code>, a
     *  <code>JRadioButton</code> and two <code>JComboBox</code> to the
     *  <code>JPanel</code>
     */
    private void addSplittingPrefixRow(){
        /** Splitting Prefix Label */
        JLabel splittingPrefixLabel = new JLabel("  Split into  ");
        splittingPrefixLabel.setBackground(TABLE_HEADINGS_BACKGROUND);
        splittingPrefixLabel.setOpaque(true);
        splittingPrefixLabel.setBorder(TABLE_CELL_BORDER);
        splittingPrefixLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        /** Edit default global constraints for this component */
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        IPv4Panel.add(splittingPrefixLabel, gridBagConstraints);
        /**********************************************************************/
        /** Splitting Prefix Radio Button */
        splittingPrefixRadioButton = new JRadioButton("",true);
        splittingPrefixRadioButton.addActionListener(this);
        radioButtonGroup.add(splittingPrefixRadioButton);
        
        /** When "ENTER" key is pressed, it calls <code>calculate()</code> */
        splittingPrefixRadioButton.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e ){
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    calculate();
                }
            }
        });
        splittingPrefixRadioButton.setBackground(Color.WHITE);
        
        /** Edit default global constraints for this component */
        gridBagConstraints.gridx = 1;
        IPv4Panel.add(splittingPrefixRadioButton, gridBagConstraints);
        /**********************************************************************/
        /** Child Prefix drop-down menu */
        childPrefix = new JComboBox();
        childPrefix.addItem("");
        for (int i = MAXPREFIX - 2; i >= 1; i--){
            childPrefix.addItem("/" + i);
        }
        
        /** When "ENTER" key is pressed, it calls <code>calculate()</code> */
        childPrefix.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e ){
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    calculate();
                }
            }
        });
        childPrefix.setToolTipText("Select the child prefix size you require");
        childPrefix.setBackground(Color.WHITE);
        
        /** Edit default global constraints for this component */
        gridBagConstraints.gridx = 2;
        IPv4Panel.add(childPrefix, gridBagConstraints);
        /**********************************************************************/
        /** End label */
        JLabel endLabel = new JLabel("   prefixs  ");
        
        /** Edit default global constraints for this component */
        gridBagConstraints.gridx = 3;
        IPv4Panel.add(endLabel, gridBagConstraints);
    }
    
    /**
     *  Creates the Number of IP Addresses row by adding a <code>JLabel</code>,
     *  a <code>JRadioButton</code> and a <code>JTextField</code> to the
     *  <code>JPanel</code>
     */
    private void addNumberOfIPAddressRow() {
        /** Number of IP Label */
        JLabel numberOfIPLabel = new JLabel("  Number of IP Addresses  ");
        numberOfIPLabel.setBackground(TABLE_HEADINGS_BACKGROUND);
        numberOfIPLabel.setOpaque(true);
        numberOfIPLabel.setFont(new Font("Arial", Font.BOLD, 12));
        numberOfIPLabel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
                TABLE_CELL_COLOR));
        
        /** Edit default global constraints for this component */
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        IPv4Panel.add(numberOfIPLabel, gridBagConstraints);
        /**********************************************************************/
        /** Number of IP Radio Button */
        numberOfIPRadioButton = new JRadioButton();
        numberOfIPRadioButton.addActionListener(this);
        radioButtonGroup.add(numberOfIPRadioButton);
        
        /** When "ENTER" key is pressed, it calls <code>calculate()</code> */
        numberOfIPRadioButton.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e ){
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    calculate();
                }
            }
        });
        numberOfIPRadioButton.setBackground(Color.WHITE);
        
        /** Edit default global constraints for this component */
        gridBagConstraints.gridx = 1;
        IPv4Panel.add(numberOfIPRadioButton, gridBagConstraints);
        /**********************************************************************/
        /** Number of IP Text Field */
        numberOfIPTextField = new JTextField();
        numberOfIPTextField.setEditable(false);
        
        /** When "ENTER" key is pressed, it calls <code>calculate()</code> */
        numberOfIPTextField.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e ){
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    calculate();
                }
            }
        });
        numberOfIPTextField.setToolTipText("Enter the number of IPv4 " +
                "addresses you require");
        
        /** Edit default global constraints for this component */
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridwidth = 3;
        IPv4Panel.add(numberOfIPTextField, gridBagConstraints);
        gridBagConstraints.gridwidth = 1;
    }
    
    /**
     *  Creates the buttons row by adding two <code>JButtons</code> to the
     *  <code>JPanel</code>
     */
    private void addButtonsRow() {
        /** Calculate Button */
        calculateButton = new JButton("  Calculate  ");
        calculateButton.addActionListener(this);
        
        /** Edit default global constraints for this component */
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new Insets(10, 0, 0, 0);
        gridBagConstraints.fill = GridBagConstraints.NONE;
        IPv4Panel.add(calculateButton, gridBagConstraints);
        /**********************************************************************/
        /** Reset Button */
        resetButton = new JButton("  Reset  ");
        resetButton.addActionListener(this);
        
        /** Edit default global constraints for this component */
        gridBagConstraints.gridx = 4;
        IPv4Panel.add(resetButton, gridBagConstraints);
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
    }
    
    /**
     *  Creates the output row by adding a <code>JTextArea</code> to the
     *  <code>JPanel</code>
     */
    private void addOutputRow(){
        // Text Area where output would be printed
        outputTextArea = new JTextArea();
        outputTextArea.setLineWrap(true);
        outputTextArea.setEditable(false);
        
        // Edit default global constraints for this component
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 6;
        IPv4Panel.add(outputTextArea, gridBagConstraints);
        gridBagConstraints.gridwidth = 1;
    }
    
    /**
     *  Determines what to do when a button is pressed, when a radio button is
     *  selected, or when the value in the parent prefix <code>JComboBox</code>
     *  is changed.
     *
     *  <p>If the <b>Calculate</b> <code>JButton</code> is pressed, it triggers
     *  the <code>calculate()</code> method.
     *
     *  <p>If the <b>Reset</b> <code>JButton</code> is pressed, it triggers the
     *  <code>reset()</code> method to reset the current panel to its original
     *  state.
     *
     *  <p>If any of the five radio buttons are selected, other un-related rows
     *  are disabled to prevent the user from performing two calculations
     *  simontanously.
     *
     *  <p>When a value has been selected in the parent prefix drop-down menu,
     *  <code>checkChildPrefix()</code> is called to re-populate the child
     *  prefix drop-down menu to adjust to the changes.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculateButton)
            calculate();
        else if (e.getSource() == resetButton)
            reset();
        else if (rangeRadioButton.isSelected()) {
            rangeTextField.setEditable(true);
            rangeCheckBox.setEnabled(true);
            netmaskTextField.setEditable(false);
            childPrefix.setEnabled(false);
            numberOfIPTextField.setEditable(false);
            outputTextArea.setText("");
        } else if (netmaskRadioButton.isSelected()) {
            rangeTextField.setEditable(false);
            rangeCheckBox.setEnabled(false);
            netmaskTextField.setEditable(true);
            childPrefix.setEnabled(false);
            numberOfIPTextField.setEditable(false);
            outputTextArea.setText("");
        } else if (splittingPrefixRadioButton.isSelected()) {
            rangeTextField.setEditable(false);
            rangeCheckBox.setEnabled(false);
            netmaskTextField.setEditable(false);
            childPrefix.setEnabled(true);
            numberOfIPTextField.setEditable(false);
            outputTextArea.setText("");
        } else if (numberOfIPRadioButton.isSelected()){
            rangeTextField.setEditable(false);
            rangeCheckBox.setEnabled(false);
            netmaskTextField.setEditable(false);
            childPrefix.setEnabled(false);
            numberOfIPTextField.setEditable(true);
            outputTextArea.setText("");
        }
    }
    
    /**
     *  This method re-directs the calculation to the appropriate method
     *  depending on what <code>JRadioButton</code> is selected.
     */
    private void calculate(){
        outputTextArea.setText("");
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            if (numberOfIPRadioButton.isSelected()){
                if (checkIP(IPAddressTextField)){
                    calculateNumberOfAddresses();
                }
            } else if (splittingPrefixRadioButton.isSelected()){
                int prefix = getPrefix();
                if (checkIP(IPAddressTextField) && prefix != (MAXPREFIX + 1)){
                    if (childPrefix.getSelectedItem() == ""){
                        calculatePrefix(prefix);
                    } else {
                        int childPrefixInt = Integer.parseInt(childPrefix.
                                getSelectedItem().toString().substring(1,
                                childPrefix.getSelectedItem().toString().length()));
                        if (childPrefixInt > prefix){
                            calculateSplitting(prefix);
                        } else
                            displayError("Splitting Prefix incorrect");
                    }
                }
                if (prefix == (MAXPREFIX + 1))
                    prefix--;
                IPAddressTextField.setText(IPAddressTextField.getText().trim()
                + " /" + prefix);
            } else if (netmaskRadioButton.isSelected()){
                if (checkIP(IPAddressTextField)){
                    calculateNetmask();
                }
            } else if (rangeRadioButton.isSelected()){
                if (checkIP(IPAddressTextField)){
                    calculateRange();
                }
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }
    
    /**
     *  Gets the prefix from IPAddressTextField.
     *
     *  @return     Prefix from IPAddressTextField
     */
    private int getPrefix(){
        StringTokenizer prefixToken = new StringTokenizer(
                IPAddressTextField.getText().trim(), " /");
        int output = MAXPREFIX + 1;
        if (prefixToken.countTokens() == 2){
            IPAddressTextField.setText(prefixToken.nextToken());
            try {
                output = Integer.parseInt(prefixToken.nextToken());
                if (output > MAXPREFIX){
                    output = MAXPREFIX + 1;
                    displayError("Prefix too large.");
                }
            } catch (NumberFormatException ex) {
                displayError("Invalid input detected. Using /" + MAXPREFIX);
            }
        } else
            displayError("No Prefix Detected");
        
        return output;
    }
    
    /**
     *  Calculates the prefix size needed to accommodate the IP addresses from
     *  the <code>IPAddressTextField</code> and <code>rangeTextField</code>.
     */
    private void calculateRange(){
        if (checkIP(rangeTextField)){
            BigInteger sib = new BigInteger(IPToBinary(IPAddressTextField), 2);
            BigInteger eib = new BigInteger(IPToBinary(rangeTextField), 2);
            
            /**
             *  Check that the IP Address in rangeTextField is larger than the
             *  IP Address in IPAddressTextField.
             */
            if (sib.doubleValue() <= eib.doubleValue()){
                /** Find the difference (in binary) of the two IP Addresses */
                String difference = new BinaryCalculate().subBinary(IPToBinary(
                        IPAddressTextField), IPToBinary(rangeTextField));
                int totalIPAddresses = Integer.parseInt(difference, 2) + 1;
                int prefixInt = 0;
                if (difference.length() <= MAXPREFIX){
                    /** Convert back to prefix form */
                    prefixInt = MAXPREFIX - (int)Math.ceil(Math.log(
                            totalIPAddresses)/Math.log(2.0));
                }
                String prefix = "/" + prefixInt;
                /** Check for prefix guards */
                if (checkPrefix(prefixInt))
                    JOptionPane.showMessageDialog(this, "From the given IP " +
                            "addresses, the following prefix is \nrequired to" +
                            " accommodate the IP Addresses:    " +
                            IPAddressTextField.getText().trim().substring(0,
                            IPAddressTextField.getText().trim().length()) +
                            "   " + prefix,"Required Slash Notation",
                            JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(this, "No prefix can " +
                            "accomodate this range", "Error",
                            JOptionPane.INFORMATION_MESSAGE);
            } else
                displayError("Starting IP Address is smaller than End IP " +
                        "Addresss");
        }
    }
    
    /**
     *  Calculates the number of IP address starting from the IP address in
     *  <code>IPAddressTextField</code>. The results are displayed on a pop-up
     *  window.
     */
    private void calculateNumberOfAddresses(){
        if (numberOfIPTextField.getText().trim().length() == 0)
            displayError("Number of IP Addresses not detected. Please enter a" +
                    " number.");
        else {
            try {
                int prefixInt = MAXPREFIX - (int) Math.ceil(Math.log(
                        Integer.parseInt(numberOfIPTextField.getText().trim()))
                        / Math.log(2.0));
                if (checkPrefix(prefixInt))
                    outputTextArea.setText(new BinaryCalculate().IPCalculate(
                            IPToBinary(IPAddressTextField), getNumberOfIPs()));
                else
                    displayError("Not a valid IPv4 Address & number of " +
                            "IP addresses combination");
            } catch (NumberFormatException ex) {
                displayError("Invalid input detected");
            }
        }
    }
    
    /**
     *  Calculates the IP addresses needed to accomodate all the IP addresses
     *  from the selected slash prefix starting from the IP address in
     *  <code>IPAddressTextField</code>.
     */
    private void calculatePrefix(int prefixInt){
        if (prefixInt >= (MAXPREFIX - 1))
            displayError("Cannot have /" + prefixInt);
        else {
            if (checkPrefix(prefixInt))
                outputTextArea.setText(new BinaryCalculate().IPCalculate(
                        IPToBinary(IPAddressTextField), getSlashPrefix(
                        prefixInt)));
            else
                displayError("Not a valid IPv4 Address & slash prefix" +
                        " combination");
        }
    }
    
    /**
     *  Calculates the list of IP address ranges needed to accomodate all the IP
     *  address from the selected parent prefix splitted into the select child
     *  prefixs starting from the IP address in <code>IPAddressTextField</code>.
     */
    private void calculateSplitting(int prefixInt){
        if (checkPrefix(prefixInt))
            new BinaryCalculate().prefixInPrefixCalculate(IPToBinary(
                    IPAddressTextField),getChildPrefix(),
                    calculatePrefixInPrefix(prefixInt),
                    "/" + prefixInt);
        else
            displayError("Not a valid IPv4 Address & splitting " +
                    "prefix combination");
    }
    
    /**
     *  Calculates the IP addresses needed to accomodate the IP addresses from
     *  the selected netmask starting from the IP address in
     *  <code>IPAddressTextField</code>
     */
    private void calculateNetmask(){
        try {
            if (netmaskTextField.getText().trim().length() != 0){
                autoPad(netmaskTextField);
                StringTokenizer netmaskTokens = new StringTokenizer(
                        netmaskTextField.getText().trim(), DELIMITER);
                String netmaskBinary = "";
                while (netmaskTokens.hasMoreTokens()){
                    String binary = convertToBinary(Integer.parseInt(
                            netmaskTokens.nextToken()));
                    while (binary.length() < BITS_PER_GROUP)
                        binary = "0" + binary;
                    netmaskBinary += binary;
                }
                int prefix = 0;
                while (netmaskBinary.charAt(prefix) != '0'){
                    prefix++;
                    if (prefix == MAXPREFIX)
                        break;
                }
                if (checkPrefix(prefix))
                    outputTextArea.setText(new BinaryCalculate().IPCalculate(
                            IPToBinary(IPAddressTextField), getSlashPrefix(
                            prefix)));
                else
                    displayError("Not a valid IPv4 Address & netmask" +
                            " combination");
            } else
                displayError("Netmask field is empty. Please enter a number.");
        } catch (NumberFormatException ex) {
            displayError("Invalid input detected");
        }
    }
    
    /**
     *  Displays errors in a notification pop-up box.
     *
     *  @param      error       The error message to be displayed.
     */
    public void displayError(String error){
        JOptionPane.showMessageDialog(this, error, "Error",
                JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     *  Automatically adds trailing zeros to shorten IP address format
     *
     *  @param      inputTextField      The <code>JTextField</code> where
     *                                  trailing zeros will be added
     */
    private void autoPad(JTextField inputTextField){
        StringTokenizer octetToken = new StringTokenizer(
                inputTextField.getText().trim(), DELIMITER);
        if (octetToken.countTokens() < NUMBER_OF_GROUPS){
            String output = "";
            while (octetToken.hasMoreTokens())
                output = output + octetToken.nextToken() + ".";
            output = output + "0";
            inputTextField.setText(output);
            autoPad(inputTextField);
        }
    }
    
    /**
     *  Checks if the IP address from the <code>JTextField</code> is a valid
     *  IPv4 address.
     *
     *  <p>Displays an appropriate error if an incorrect IPv4 address is
     *  detected.
     *
     *  @param      inputTextField     The <code>JTextField</code> to be checked
     *  @return     <code>true</code> if the Internet Protocol Address is a
     *              valid IPv4 address, otherwise <code>false</code>.
     */
    private boolean checkIP(JTextField inputTextField){
        autoPad(inputTextField);
        boolean IPPassed = false;
        int currentToken = 1;
        
        StringTokenizer octetToken = new StringTokenizer(
                inputTextField.getText().trim(), DELIMITER);
        if (octetToken.countTokens()== NUMBER_OF_GROUPS){
            while (octetToken.hasMoreTokens()) {
                try{
                    int octet = Integer.parseInt(octetToken.nextToken());
                    
                    if (octet >= 0 && octet <= 255){
                        IPPassed = true;
                        currentToken++;
                    } else {
                        displayError("Octet " + currentToken + " is incorrect" +
                                " \n IP Range has to be 0 to 255");
                        IPPassed = false;
                        break;
                    }
                } catch (NumberFormatException nfe) {
                    displayError("Invalid input detected");
                    IPPassed = false;
                    break;
                }
            }
        } else {
            displayError("Too many octets");
            IPPassed = false;
        }
        
        return IPPassed;
    }
    
    /**
     *  Converts a <code>integer</code> into a binary string.
     *
     *  @parm       input       The <code>integer</code> value to be converted.
     *  @return     Binary representation of the <code>integer</code>.
     */
    private String convertToBinary(int input){
        return Integer.toBinaryString(input);
    }
    
    /**
     *  Calculates the padded binary representation of the IP Address from the
     *  <code>JTextField</code>.
     *
     *  @param      textField       The <code>JTextField</code> where the IP
     *                              address can be found
     *  @return     Padded binary representation of IP Address from the
     *              <code>JTextField</code>.
     */
    private String IPToBinary(JTextField textField){
        String paddedBinary = "";
        StringTokenizer octet = new StringTokenizer(textField.getText().trim(),
                DELIMITER);
        while (octet.hasMoreTokens()){
            String unpaddedBinary = convertToBinary(Integer.parseInt(
                    octet.nextToken()));
            while (unpaddedBinary.length() < BITS_PER_GROUP)
                unpaddedBinary = "0" + unpaddedBinary;
            paddedBinary += unpaddedBinary;
        }
        
        return paddedBinary;
    }
    
    /**
     *  Pads or Unpads the given <code>JTextField</code>
     *
     *  @parm   inputTextField      The <code>JTextField</code> to be padded
     *  @parm   padded              To pad or unpad the <code>JTextField</code>
     */
    private void pad(JTextField inputTextField, boolean padded){
        StringTokenizer tokens = new StringTokenizer(
                inputTextField.getText(), "/ ");
        String prefix = "";
        if (tokens.countTokens() == 2){
            inputTextField.setText(tokens.nextToken());
            prefix = tokens.nextToken();
        }
        if (checkIP(inputTextField)){
            StringTokenizer octetToken = new StringTokenizer(
                    inputTextField.getText(), DELIMITER);
            String output = "";
            int dotDecimal = 1;
            while (octetToken.hasMoreTokens()){
                String out = octetToken.nextToken();
                if (padded)
                    while (out.length() < CHARS_PER_GROUP)
                        out = "0" + out;
                output += out;
                if (dotDecimal < NUMBER_OF_GROUPS){
                    output += DELIMITER;
                    dotDecimal++;
                }
            }
            if (prefix.length() != 0)
                inputTextField.setText(output + "/" + prefix);
            else
                inputTextField.setText(output);
        }
    }
    
    /**
     *  Calculates the number of child prefixs that can fit inside the parent
     *  prefix.
     *
     *  @return     Number of child prefixs that can fit inside the parent
     *              prefix
     */
    private int calculatePrefixInPrefix(int parentPrefixInt){
        double parentTruePrefix = 0;
        
        if (parentPrefixInt == (MAXPREFIX - 1))
            displayError("Cannot have /" + (MAXPREFIX - 1));
        else {
            parentTruePrefix = MAXPREFIX - parentPrefixInt;
            parentTruePrefix = Math.pow(2, parentTruePrefix);
            
            if (parentTruePrefix < 0)
                parentTruePrefix *= -1;
        }
        
        String childPrefixString = childPrefix.getSelectedItem().toString();
        int childPrefixInt = Integer.parseInt(childPrefixString.substring(
                1, childPrefixString.length()));
        double childTruePrefix = MAXPREFIX - childPrefixInt;
        childTruePrefix = Math.pow(2, childTruePrefix);
        
        if (childTruePrefix < 0)
            childTruePrefix *= -1;
        
        double prefixDifference = parentTruePrefix / childTruePrefix;
        
        return (int)prefixDifference;
    }
    
    /**
     *  Calculates the binary representation of the selected child prefix.
     *
     *  @return      Binary representation of the selected child prefix.
     */
    private String getChildPrefix(){
        String childPrefixString = childPrefix.getSelectedItem().toString();
        int childPrefixInt = Integer.parseInt(childPrefixString.substring(
                1, childPrefixString.length()));
        double truePrefix= 0;
        
        if (childPrefixInt == (MAXPREFIX - 1))
            displayError("Cannot have /" + (MAXPREFIX - 1));
        else {
            truePrefix = MAXPREFIX - childPrefixInt;
            truePrefix = Math.pow(2, truePrefix);
            
            if (truePrefix < 0)
                truePrefix *= -1;
        }
        
        String childPrefixBinary = convertToBinary((int)truePrefix - 1);
        while (childPrefixBinary.length() < MAXPREFIX)
            childPrefixBinary = "0" + childPrefixBinary;
        return childPrefixBinary;
    }
    
    /**
     *  Calculates the binary representation of the given prefix.
     *
     *  @param      prefixInt       Prefix to be converted
     *  @return     Binary representation of the given prefix
     */
    /*private String getSlashPrefix(int prefixInt){
        double truePrefix= 0;
     
        truePrefix = MAXPREFIX - prefixInt;
        truePrefix = Math.pow(2, truePrefix);
        if (truePrefix < 0)
            truePrefix *= -1;
        System.out.println(truePrefix);
     
        String slashPrefix = convertToBinary((int)truePrefix - 1);
        while (slashPrefix.length() < MAXPREFIX)
            slashPrefix = "0" + slashPrefix;
        System.out.println(slashPrefix);
        return slashPrefix;
    }*/
    
    private String getSlashPrefix(int prefixInt){
        String output = "";
        int guard = MAXPREFIX - prefixInt;
        
        
        for (int i = MAXPREFIX; i > guard; i--){
            output += "0";
        }
        for (int j = prefixInt; j < MAXPREFIX; j++){
            output += "1";
        }
        return output;
    }
    
    /**
     *  Calculates the binary representation of the value in numberOfIPTextField
     *
     *  @return      Binary representation of the number of IP addresses
     */
    private String getNumberOfIPs(){
        /*String numberOfIPs = convertToBinary(Integer.parseInt(
                numberOfIPTextField.getText().trim()) + 1);
        while (numberOfIPs.length() < MAXPREFIX)
            numberOfIPs = "0" + numberOfIPs;*/
        
        /*BigInteger totalIPAddresses = new BigInteger(numberOfIPTextField.getText().trim());
        totalIPAddresses = totalIPAddresses.add(new BigInteger("2"));
        System.out.println(totalIPAddresses);
        int prefixInt = 0;
        if (totalIPAddresses.doubleValue() < 4)
            prefixInt = MAXPREFIX;
        else if (totalIPAddresses.doubleValue() == 4)
            prefixInt = MAXPREFIX - 2;
        else {
            prefixInt = MAXPREFIX - (int)Math.ceil(Math.log(
                        totalIPAddresses.doubleValue())/Math.log(2.0));
        }*/
        int prefixInt = MAXPREFIX - (int) Math.ceil(Math.log(
                Integer.parseInt(numberOfIPTextField.getText().trim()) + 2)
                / Math.log(2.0));
        return getSlashPrefix(prefixInt);
    }
    
    /** Resets this panel to its initial state on startup. */
    private void reset(){
        IPAddressTextField.setText("");
        IPAddressTextField.grabFocus();
        IPAddressCheckBox.setSelected(false);
        rangeTextField.setText("");
        rangeTextField.setEditable(false);
        rangeRadioButton.setSelected(false);
        rangeCheckBox.setSelected(false);
        rangeCheckBox.setEnabled(false);
        netmaskTextField.setEditable(false);
        netmaskTextField.setText("");
        splittingPrefixRadioButton.setSelected(true);
        childPrefix.setSelectedItem("");
        childPrefix.setEnabled(true);
        numberOfIPTextField.setEditable(false);
        numberOfIPTextField.setText("");
        outputTextArea.setText("");
    }
    
    /**
     *  Checks if the <code>prefixInt</code> and the inputed IP address are
     *  limited within the given prefix.
     *
     *  @param       prefixInt       Integer value of prefix to be tested
     *
     *  @return      <code>true</code> if <code>prefixInt</code> and IP address
     *               does not cross another prefix boundary. <code>false</code>
     *               if it crosses another prefix boundary.
     */
    private boolean checkPrefix(int prefixInt){
        String slashPrefixGuard = "";
        
        for (int i = MAXPREFIX - prefixInt; i > 0; i--)
            slashPrefixGuard += "0";
        String out = IPToBinary(IPAddressTextField);
        
        if (slashPrefixGuard.equals(out.substring(prefixInt, out.length())))
            return true;
        else
            return false;
    }
}