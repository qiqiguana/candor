package macaw.presentationLayer;

import macaw.businessLayer.*;
import macaw.system.*;
import macaw.util.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.*;

/**
 * <p></p>
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

abstract public class VariableEditor implements ActionListener,
												DisplayableListParentForm {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	protected SessionProperties sessionProperties;
	protected UserInterfaceFactory userInterfaceFactory;
	protected User currentUser;
	protected String currentUserID;
	protected Log log;

	protected MacawCurationAPI database;
	protected JDialog dialog;
	
	protected JTextArea instructionsTextArea;
	
	protected String isCodedText;
	protected JCheckBox isCodedField;
	
	protected String nameLabelText;
	protected JTextField nameField;

	protected String labelLabelText;
	protected JTextField labelField;	

	protected String categoryLabelText;
	protected JComboBox categoryChoicesField;

	protected CleaningStatePanel cleaningStatePanel;

	protected String yearLabelText;
	protected JTextField yearField;

	private String formLabelText;
	private JTextField formField;

	private String questionNumberLabelText;
	private JTextField questionNumberField;	

	protected OntologyTermsPanel ontologyTermsPanel;
	protected SupportingDocumentsPanel supportingDocumentsPanel;

	protected String availabilityLabelText;
	protected JComboBox availabilityChoicesField;

	protected String aliasFilePathLabelText;
	protected JComboBox aliasFilePathChoicesField;

	private JTextField codeBookNumberField;
	private JTextField columnStartField;
	private JTextField columnEndField;

	protected String filePathLabelText;
	protected JTextField filePathField;

	protected JTextArea notesField;

	protected AlternativeVariableSelectionPanel alternativeVariableSelectionPanel;
	
	protected JButton showChangeHistory;
	protected JButton editLabelValues;
	
	protected OKClosePanel okCancelPanel;
	protected JButton save;
	protected JButton close;
	
	protected boolean committedChanges;
	
	private Variable variable;

	protected boolean allowWriteAccess;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public VariableEditor(SessionProperties sessionProperties,
						  boolean allowWriteAccess) {
		this.sessionProperties = sessionProperties;
		this.allowWriteAccess = allowWriteAccess;
		userInterfaceFactory
			= sessionProperties.getUserInterfaceFactory();
		database
			= (MacawCurationAPI) sessionProperties.getProperty(SessionProperties.DATABASE);
		currentUser
			= (User) sessionProperties.getProperty(SessionProperties.CURRENT_USER);
		currentUserID = currentUser.getUserID();
		log
			= (Log) sessionProperties.getProperty(SessionProperties.LOG);
		
		
		instructionsTextArea
			= userInterfaceFactory.createImmutableTextArea(3, 30);
		String instructionsAreaText
			= MacawMessages.getMessage("variableLabelEditor.instructions");
		instructionsTextArea.setText(instructionsAreaText);
		instructionsTextArea.setBorder(LineBorder.createGrayLineBorder());
		
		nameLabelText
			= MacawMessages.getMessage("variable.name.label");
		nameField 
			= userInterfaceFactory.createTextField(10);
		nameField.setEditable(allowWriteAccess);
		
		labelLabelText
			= MacawMessages.getMessage("variable.label.label"); 
		labelField 
			= userInterfaceFactory.createTextField(30);
		labelField.setEditable(allowWriteAccess);
		
		categoryLabelText
			= MacawMessages.getMessage("variable.category.label"); 
		
		String[] categoryChoices = new String[0];
		try {
			ArrayList<Category> categories
				= database.getCategories(currentUser);		
			categoryChoices
				= ConstantConverter.getCategoryChoices(categories);
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}
		
		categoryChoicesField 
			= userInterfaceFactory.createComboBox(categoryChoices);
		categoryChoicesField.setEnabled(allowWriteAccess);
		
		yearLabelText
			= MacawMessages.getMessage("variable.year.label"); 
		yearField = userInterfaceFactory.createTextField(5);
		yearField.setEditable(allowWriteAccess);

		isCodedText
			= MacawMessages.getMessage("variable.isCoded.label");
		isCodedField = userInterfaceFactory.createCheckBox(isCodedText);
		
		ontologyTermsPanel
			= new OntologyTermsPanel(sessionProperties,
									 this,
								     allowWriteAccess);
		
		supportingDocumentsPanel 
			= new SupportingDocumentsPanel(sessionProperties, 
										   this,
										   allowWriteAccess);
		
		alternativeVariableSelectionPanel
			= new AlternativeVariableSelectionPanel(sessionProperties);

		availabilityLabelText
			= MacawMessages.getMessage("variable.availability.label");
		String[] availabilityChoices = new String[0];
		try {
			ArrayList<AvailabilityState> availabilityStates
				= database.getAvailabilityStates(currentUser);		
			availabilityChoices
				= ConstantConverter.getAvailabilityStatusChoices(availabilityStates);
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}
		
		availabilityChoicesField 
			= userInterfaceFactory.createComboBox(availabilityChoices);
		availabilityChoicesField.setEnabled(allowWriteAccess);
		
		aliasFilePathLabelText
			= MacawMessages.getMessage("variable.alias.label");
		
		String[] aliasFilePathChoices = new String[0];
		try {
			ArrayList<AliasFilePath> aliasFilePaths
				= database.getAliasFilePaths(currentUser);		
			aliasFilePathChoices
				= ConstantConverter.getAliasFilePathChoices(aliasFilePaths);
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}		
		aliasFilePathChoicesField 
			= userInterfaceFactory.createComboBox(aliasFilePathChoices);
		aliasFilePathChoicesField.setEnabled(allowWriteAccess);

		filePathLabelText
			= MacawMessages.getMessage("variable.filePath.label");
		filePathField = userInterfaceFactory.createTextField(5);
		filePathField.setEditable(false);
		

		String showChangeHistoryText
			= MacawMessages.getMessage("general.buttons.showChangeHistory");
		showChangeHistory
			= userInterfaceFactory.createButton(showChangeHistoryText);
		editLabelValues
			= userInterfaceFactory.createButton("");

		okCancelPanel
			= new OKClosePanel(userInterfaceFactory,
										 this);
		okCancelPanel.addButton(showChangeHistory);
		okCancelPanel.addButton(editLabelValues);		
		okCancelPanel.setAllowWriteAccess(allowWriteAccess);

		save = okCancelPanel.getSaveButton();
		close = okCancelPanel.getCloseButton();
		
		committedChanges = false;
				
		dialog = userInterfaceFactory.createDialog();
	
		WindowSizeListener windowSizeListener
			= new WindowSizeListener();
		dialog.addComponentListener(windowSizeListener);	

		aliasFilePathChoicesField.addActionListener(this);
	}
	
	protected JPanel createYearFormQuestionNumberPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 0.0;
		panelGC.ipadx = 10;
		JLabel yearLabel = userInterfaceFactory.createLabel(yearLabelText);		
		panel.add(yearLabel, panelGC);
		panelGC.gridx++;
		panelGC.weightx = 0.3;
		panel.add(yearField, panelGC);
		panelGC.gridx++;
		formLabelText
			= MacawMessages.getMessage("rawVariableLabelEditor.form");
		JLabel formLabel = userInterfaceFactory.createLabel(formLabelText);
		panelGC.weightx = 0.0;
		panel.add(formLabel, panelGC);
		panelGC.gridx++;
		panelGC.weightx = 0.3;
		formField = userInterfaceFactory.createTextField(10);
		formField.setEditable(allowWriteAccess);
		panel.add(formField, panelGC);
		panelGC.gridx++;
		
		questionNumberLabelText
			= MacawMessages.getMessage("rawVariableLabelEditor.questionNumber");
		JLabel questionNumberLabel
			= userInterfaceFactory.createLabel(questionNumberLabelText);
		panelGC.weightx = 0.0;
		panel.add(questionNumberLabel, panelGC);
		panelGC.gridx++;
		questionNumberField = userInterfaceFactory.createTextField(10);
		questionNumberField.setEditable(allowWriteAccess);
		panelGC.weightx = 0.3;
		panel.add(questionNumberField, panelGC);
		
		return panel;
	}	

	
	protected JPanel createNameLabelPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC	
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.ipadx = 5;

		JLabel nameFieldLabel
			= userInterfaceFactory.createLabel(nameLabelText);
		panel.add(nameFieldLabel, panelGC);
		panelGC.gridx++;
		panel.add(nameField, panelGC);
		panelGC.gridx++;
		JLabel labelLabel
			= userInterfaceFactory.createLabel(labelLabelText);
		panel.add(labelLabel, panelGC);
		panelGC.gridx++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		panel.add(labelField, panelGC);
		
		return panel;
	}

	protected JPanel createRubricCardPanel() {
		JPanel panel
			= userInterfaceFactory.createPanel();
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.insets = new Insets(5, 0, 5, 0);
		
		String panelTitleText
			= MacawMessages.getMessage("rawVariableLabelEditor.rubricCardData.title");
		JLabel panelTitleLabel 
			= userInterfaceFactory.createLabel(panelTitleText);
		panel.add(panelTitleLabel, panelGC);
		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		panel.add(createRubricCardFields(), panelGC);
		panel.setBorder(LineBorder.createGrayLineBorder());
		return panel;
	}
	
	protected JPanel createRubricCardFields() {
		JPanel panel
			= userInterfaceFactory.createPanel();
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();

		//adding code book number field
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 0;
		panelGC.gridx++;		
		String codeBookNumberLabelText
			= MacawMessages.getMessage("variable.codeBookNumber.label");
		JLabel codeBookNumberLabel
			= userInterfaceFactory.createLabel(codeBookNumberLabelText);
		panel.add(codeBookNumberLabel, panelGC);
		panelGC.gridx++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		codeBookNumberField = userInterfaceFactory.createTextField(20);
		codeBookNumberField.setEditable(allowWriteAccess);
		panel.add(codeBookNumberField, panelGC);
		
		//adding column start field
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 0;
		panelGC.gridx++;
		String columnStartLabelText
			= MacawMessages.getMessage("variable.columnStart.label");
		JLabel columnStartLabel
			= userInterfaceFactory.createLabel(columnStartLabelText);
		panel.add(columnStartLabel, panelGC);
		panelGC.gridx++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		columnStartField 
			= userInterfaceFactory.createTextField(20);
		columnStartField.setEditable(allowWriteAccess);
		panel.add(columnStartField, panelGC);
		
		//adding column end field
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 0;
		panelGC.gridx++;
		String columnEndLabelText
			= MacawMessages.getMessage("variable.columnEnd.label");
		JLabel columnEndLabel
			= userInterfaceFactory.createLabel(columnEndLabelText);
		panel.add(columnEndLabel, panelGC);
		panelGC.gridx++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		columnEndField
			= userInterfaceFactory.createTextField(20);
		columnEndField.setEditable(allowWriteAccess);
		panel.add(columnEndField, panelGC);
		
		return panel;
	}

	protected JPanel createDataLibraryAndFilePanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC	
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.ipadx = 5;
		
		JLabel dataLibraryLabel
			= userInterfaceFactory.createLabel(aliasFilePathLabelText);
		panel.add(dataLibraryLabel, panelGC);
		panelGC.gridx++;
		panel.add(aliasFilePathChoicesField, panelGC);
		panelGC.gridx++;

		panelGC.gridy++;
		panelGC.gridx = 0;
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 0;
		JLabel filePathLabel
			= userInterfaceFactory.createLabel(filePathLabelText);
		panel.add(filePathLabel, panelGC);
		panelGC.gridx++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		panel.add(filePathField, panelGC);
		
		return panel;
	}

	protected JPanel createNotesFieldPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC	
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.ipadx = 5;
		
		String notesFieldText
			= MacawMessages.getMessage("variable.notes.label");
		JLabel notesFieldLabel
			= userInterfaceFactory.createLabel(notesFieldText);
		panel.add(notesFieldLabel, panelGC);
		
		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.BOTH;
		panelGC.weightx = 1.0;
		panelGC.weighty = 1.0;
		notesField = userInterfaceFactory.createTextArea(2, 10);
		JScrollPane notesScrollPane 
			= userInterfaceFactory.createScrollPane(notesField);
		panel.add(notesScrollPane, panelGC);
	
		return panel;
	}
	
	protected void addField(String fieldName,
						  	JComponent editingComponent,
						  	JPanel parentPanel,
						  	GridBagConstraints parentPanelGC) {

		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.ipadx = 5;
		panelGC.ipady = 5;

		JLabel label = userInterfaceFactory.createLabel(fieldName);
		panel.add(label, panelGC);
		panelGC.gridx++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 100;
		panel.add(editingComponent, panelGC);

		parentPanel.add(panel, parentPanelGC);	
	}
	
	protected JPanel createIsCleanedPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC	
			= userInterfaceFactory.createGridBagConstraints();

		String[] cleaningStateChoices = new String[0];
		try {
			ArrayList<CleaningState> cleaningStates
				= database.getCleaningStates(currentUser);		
			cleaningStateChoices
				= ConstantConverter.getCleaningStatusChoices(cleaningStates);
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}
		
		cleaningStatePanel 
			= new CleaningStatePanel(sessionProperties,
									 cleaningStateChoices,
									 allowWriteAccess);
		panel.add(cleaningStatePanel.getCheckBox(), panelGC);

		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.BOTH;
		panelGC.weightx = 100;
		panelGC.weighty = 100;
	
		panel.add(cleaningStatePanel.getPanel(), panelGC);

		return panel;
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public boolean isCancelled() {
		return committedChanges;
	}
	
	public void show() {
		dialog.setVisible(true);
	}
	
	private void updateEditLabelValuesButton() {
		try {			
			int numberOfValues = 0;
			if (variable.isNewRecord() == false) {				
				ArrayList<ValueLabel> valueLabels 
					= database.getValueLabels(currentUser, variable);
				numberOfValues = valueLabels.size();
			}

			if (allowWriteAccess == true) {
				String editLabelValuesText
					= MacawMessages.getMessage("rawVariableLabelEditor.editLabelValues",
												String.valueOf(numberOfValues));
				editLabelValues.setText(editLabelValuesText);
			}
			else {
				String viewLabelValuesText
					= MacawMessages.getMessage("rawVariableLabelEditor.viewLabelValues",
												String.valueOf(numberOfValues));
				editLabelValues.setText(viewLabelValuesText);
			}		
		}
		catch(MacawException exception) {
			log.displayErrorDialog(exception);
		}
	}
	
	protected void setData(Variable variable, boolean isNewRecord) {
		this.variable = variable;
		ontologyTermsPanel.setData(variable);		
		supportingDocumentsPanel.setData(variable);
		updateEditLabelValuesButton();
		alternativeVariableSelectionPanel.setData(variable);	
		cleaningStatePanel.initialise(variable);
	}
		
	protected void saveVariableFields(Variable variable) {
		this.variable = variable;
		
		if (allowWriteAccess == false) {
			return;
		}
		
		variable.setName(nameField.getText().trim());
		variable.setLabel(labelField.getText().trim());
		variable.setYear(yearField.getText().trim());
		
		variable.setForm(formField.getText().trim());
		
		variable.setQuestionNumber(questionNumberField.getText().trim());
		variable.setCategory((String) categoryChoicesField.getSelectedItem());
		variable.setCoded(isCodedField.isSelected());

		//save state managed by cleaning state panel
		cleaningStatePanel.save();
		boolean isCleaned = cleaningStatePanel.isCleaned();
		variable.setCleaned(isCleaned);
		if (isCleaned == false) {
			variable.setCleaningStatus("");
			variable.setCleaningDescription("");
		} 
		else {
			variable.setCleaningStatus(cleaningStatePanel.getCleaningStatus());
			variable.setCleaningDescription(cleaningStatePanel.getCleaningDescription());			
		}
		
		ArrayList<SupportingDocument> supportingDocuments
			= supportingDocumentsPanel.getSupportingDocuments();
		variable.setSupportingDocuments(supportingDocuments);

		String selectedAvailability
			= (String) availabilityChoicesField.getSelectedItem();

		variable.setAvailability((String) availabilityChoicesField.getSelectedItem());
		
		variable.setCodeBookNumber(codeBookNumberField.getText().trim());
		variable.setColumnStart(columnStartField.getText().trim());
		variable.setColumnEnd(columnEndField.getText().trim());
		
		variable.setAlias((String) aliasFilePathChoicesField.getSelectedItem());
		variable.setFilePath(filePathField.getText().trim());
		variable.setNotes(notesField.getText().trim());
		
		Variable alternativeVariable
			= alternativeVariableSelectionPanel.getAlternativeVariable();
		variable.setAlternativeVariable(alternativeVariable);
		alternativeVariableSelectionPanel.setData(variable);
		
		//assumes that change events have already been recorded
		cleaningStatePanel.clearChanges();

	}
	
	protected void restoreVariableFields(Variable variable) {
		nameField.setText(variable.getName());
		labelField.setText(variable.getLabel());
		yearField.setText(variable.getYear());
		formField.setText(variable.getForm());
		
		questionNumberField.setText(variable.getQuestionNumber());

		isCodedField.setSelected(variable.isCoded());
		String category = variable.getCategory();
		if (category.equals("") == true) {
			category 
				= MacawMessages.getMessage("general.fieldValue.unknown");
		}
		categoryChoicesField.setSelectedItem(category);
		
		cleaningStatePanel.restore();
	
		supportingDocumentsPanel.setData(variable);

		String availability = variable.getAvailability();

		if (availability.equals("") == true) {
			availability 
				= MacawMessages.getMessage("general.fieldValue.unknown");
		}
		availabilityChoicesField.setSelectedItem(availability);

		String alias
			= variable.getAlias();
		if (alias.equals("") == true) {
			alias
				= MacawMessages.getMessage("general.fieldValue.unknown");
		}		
		aliasFilePathChoicesField.setSelectedItem(alias);

		updateFilePath();
		
		codeBookNumberField.setText(variable.getCodeBookNumber());
		columnStartField.setText(variable.getColumnStart());
		columnEndField.setText(variable.getColumnEnd());	
		
		notesField.setText(variable.getNotes());
		alternativeVariableSelectionPanel.setData(variable);
	}
	
	protected void close() {
		dialog.setVisible(false);
	}
	
	protected void editLabelValues() {
		try {			
			VariableLabelValueEditor variableLabelValueEditor
				= new VariableLabelValueEditor(sessionProperties,
											   allowWriteAccess);
			variableLabelValueEditor.setData(variable);
			variableLabelValueEditor.show();
	
			updateEditLabelValuesButton();	
		}
		catch(MacawException exception) {
			log.displayErrorDialog(exception);
		}

	}
		
	protected void showChangeHistory() {
		try {
			ChangeHistoryViewer changeHistoryViewer
				= new ChangeHistoryViewer(sessionProperties);
			changeHistoryViewer.showHistoryForVariable(variable);
		}
		catch(MacawException exception) {
			log.displayErrorDialog(exception);
		}
	}
	
	abstract public Displayable getData();
	
	
	protected void updateFilePath() {
		try {
			String currentAlias
				= (String) aliasFilePathChoicesField.getSelectedItem();

			String filePath
				= database.getFilePathFromAlias(currentUser, currentAlias);
			filePathField.setText(filePath);
		}
		catch(MacawException exception) {
			log.logException(exception);
		}
	}
	
	protected void updateValueLabelsButtonState(Variable rawVariable) {
		if (rawVariable.isNewRecord() == true) {
			editLabelValues.setEnabled(false);
		}
		else {
			editLabelValues.setEnabled(true);
		}
	}
	
	// ==========================================
	// Section Errors and Validation
	// ==========================================
	
	// ==========================================
	// Section Interfaces
	// ==========================================
	abstract public void actionPerformed(ActionEvent event);
	abstract public void commitChanges() throws MacawException;
	
	// ==========================================
	// Section Overload
	// ==========================================

}

