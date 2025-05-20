package macaw.system;

import javax.swing.*;

import java.awt.*;
import java.util.*;
import javax.swing.table.*;

/**
 * centralised the creation of GUI components used to produce the Macaw data entry forms.
 * <hr>
 * Copyright 2010 Medical Research Council Unit for Lifelong Health and Ageing
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  
 * <hr>
 * @author Kevin Garwood (kgarwood@users.sourceforge.net)
 * @version 1.0	
 */

/*
 * Code Road Map:
 * --------------
 * Code is organised into the following sections.  Wherever possible, 
 * methods are classified based on an order of precedence described in 
 * parentheses (..).  For example, if you're trying to find a method 
 * 'getName(...)' that is both an interface method and an accessor 
 * method, the order tells you it should appear under interface.
 * 
 * Order of 
 * Precedence     Section
 * ==========     ======
 * (1)            Section Constants
 * (2)            Section Properties
 * (3)            Section Construction
 * (7)            Section Accessors and Mutators
 * (6)            Section Errors and Validation
 * (5)            Section Interfaces
 * (4)            Section Overload
 *
 */

public class UserInterfaceFactory {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private Font plainFont;
	private Font currentFont;
	
	private Color textColour;
	//private Color selectedItemBackground;
	private Color buttonBackground;
	private Color fieldBackground;
	private Color formBackground;	
	
	private Font smallFont;
	// ==========================================
	// Section Construction
	// ==========================================
	public UserInterfaceFactory() {
		JLabel sampleObject = new JLabel();
		Font normalFont = sampleObject.getFont();
		plainFont = normalFont.deriveFont(Font.PLAIN);	
		//currentFont = plainFont.deriveFont(72);
		
		smallFont = new Font("Arial",
							 Font.PLAIN,
							 10);
		
		currentFont = new Font("Arial",
							   Font.PLAIN,
							   16);
		
		textColour = Color.black;
		fieldBackground = Color.white;
		formBackground = sampleObject.getBackground();
		//formBackground = Color.orange;

		buttonBackground = sampleObject.getBackground();
		//buttonBackground = Color.orange;
		//updateFontSettings();
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	
	public JButton createButton(String buttonText) {
		JButton button = new JButton(buttonText);
		setComponentProperties(button);			
		return button;
	}
	
	public JTabbedPane createTabbedPane() {
		JTabbedPane tabbedPane = new JTabbedPane();
		setComponentProperties(tabbedPane);		
		return tabbedPane;
	}

	public JLabel createLabel() {
		JLabel label = new JLabel("");
		setComponentProperties(label);		
		return label;
	}	
	
	public JLabel createLabel(String labelText) {
		JLabel label = new JLabel(labelText);
		setComponentProperties(label);		
		return label;
	}
		
	public JSpinner createSpinner(SpinnerModel spinnerModel) {
		JSpinner spinner = new JSpinner(spinnerModel);
		setComponentProperties(spinner);		
		return spinner;
	}
	
	public GridBagConstraints createGridBagConstraints() {
		GridBagConstraints panelGC = new GridBagConstraints();
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.anchor = GridBagConstraints.NORTHWEST;
		panelGC.weightx = 0;
		panelGC.weighty = 0;
		panelGC.gridx = 0;
		panelGC.gridy = 0;

		return panelGC;
	}
	
	public JPanel createPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		setComponentProperties(panel);
		return panel;
	}

	public JPanel createBorderLayoutPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		setComponentProperties(panel);	
		return panel;
	}	
	
	public JPasswordField createPasswordField(int numberOfColumns) {
		JPasswordField passwordField = new JPasswordField(numberOfColumns);
		passwordField.setBackground(fieldBackground);		
		return passwordField;
	}
	
	public JTextField createTextField(int numberOfColumns) {
		JTextField textField = new JTextField(numberOfColumns);
		textField.setBackground(fieldBackground);
		return textField;		
	}
	
	public JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setComponentProperties(menuBar);
		return menuBar;
	}
	
	public JMenu createMenu(String menuText) {
		JMenu menu = new JMenu(menuText);
		setComponentProperties(menu);
		return menu;
	}

	public JRadioButtonMenuItem createRadioButtonMenuItem(String menuItemText) {
		JRadioButtonMenuItem menuItem
			= new JRadioButtonMenuItem(menuItemText);
		setComponentProperties(menuItem);		
		return menuItem;
	}
	
	public JRadioButton createRadioButton(String buttonText) {
		JRadioButton radioButton
			= new JRadioButton(buttonText);
		setComponentProperties(radioButton);
		return radioButton;		
	}
	
	public JMenuItem createMenuItem(String menuItemText) {
		JMenuItem menuItem = new JMenuItem(menuItemText);
		setComponentProperties(menuItem);
		return menuItem;
	}
	
	public void setContainerProperties(Container component) {		
		component.setBackground(formBackground);
		component.setFont(currentFont);
	}
	
	public void setComponentProperties(JComponent component) {		
		component.setBackground(formBackground);
		component.setFont(currentFont);
	}
	
	public JCheckBox createCheckBox(String text) {
		JCheckBox checkBox = new JCheckBox(text);
		setComponentProperties(checkBox);
		return checkBox;	
	}
	
	public JScrollPane createScrollPane(Component component) {
		JScrollPane scrollPane = new JScrollPane(component);
		setComponentProperties(scrollPane);
		return scrollPane;
	}
	
	public JTextField createImmutableTextField(int numberOfColumns) {
		JTextField textField = new JTextField(numberOfColumns);
		setComponentProperties(textField);
		textField.setEditable(false);
		textField.setBackground(formBackground);

		//setComponentProperties(textArea);
		return textField;
	}

	
	public JTextArea createImmutableTextArea(int numberOfRows, int numberOfColumns) {
		JTextArea textArea = new JTextArea(numberOfRows, numberOfColumns);
		setComponentProperties(textArea);
		textArea.setEditable(false);	
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBackground(formBackground);

		//setComponentProperties(textArea);
		return textArea;
	}
	
	public JTextArea createTextArea(int numberOfRows, int numberOfColumns) {
		JTextArea textArea = new JTextArea(numberOfRows, numberOfColumns);
		setComponentProperties(textArea);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBackground(fieldBackground);

		//setComponentProperties(textArea);
		return textArea;
	}
	
	public JComboBox createComboBox(String[] choices) {
		JComboBox comboBox = new JComboBox(choices);
		setComponentProperties(comboBox);
		return comboBox;		
	}
	
	public JFileChooser createFileChooser() {
		JFileChooser fileChooser = new JFileChooser(".");
		setComponentProperties(fileChooser);
		return fileChooser;
	}

	public JList createList() {
		JList list = new JList();
		setComponentProperties(list);
		list.getCellRenderer();
		return list;		
	}
	
	public JList createList(Vector<String> listItems) {
		JList list = new JList(listItems);
		setComponentProperties(list);		
		return list;
	}

	public JEditorPane createEditorPane() {
		JEditorPane editorPane = new JEditorPane();
		setComponentProperties(editorPane);	
		editorPane.setEditable(false);
		editorPane.setContentType("text/html");
		return editorPane;
	}

	public JTable createTable(TableModel tableModel) {
		JTable table = new JTable(tableModel);
		setComponentProperties(table);	
		JTableHeader header = table.getTableHeader();
		setComponentProperties(header);		
		return table;
	}
	
	public JSeparator createSeparator() {
		JSeparator separator = new JSeparator();
		setComponentProperties(separator);
		return separator;
	}
	
	public JPopupMenu createPopupMenu() {
		JPopupMenu popupMenu = new JPopupMenu();
		setComponentProperties(popupMenu);
		return popupMenu;
	}
	
	
	public void updateFontSettings() {
		UIDefaults definitions = UIManager.getLookAndFeelDefaults();
		Iterator iterator = definitions.keySet().iterator();

		while ( iterator.hasNext() == true) {
			String key = (String) iterator.next();
			String upperCaseKey = key.toUpperCase();

			Font font = definitions.getFont(key);
			if ( font != null) {
				UIManager.put(key, currentFont);
			} //end if ()
		 } //end while ()
	}

	public Color getDefaultApplicationThemeColour() {
		Color color = new Color(200,200,200);
		return color;
	}

	public void setSmallFont(JComponent component) {
		component.setFont(smallFont);
	}
	
	public JDialog createDialog() {
		JDialog dialog = new JDialog();
		return dialog;
	}

	// ==========================================
	// Section Errors and Validation
	// ==========================================

	// ==========================================
	// Section Interfaces
	// ==========================================

	// ==========================================
	// Section Overload
	// ==========================================
}
