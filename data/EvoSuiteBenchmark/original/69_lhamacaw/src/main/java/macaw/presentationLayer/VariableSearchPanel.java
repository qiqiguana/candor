package macaw.presentationLayer;

import macaw.system.*;
import macaw.businessLayer.*;


import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.event.*;

import java.awt.*;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

public class VariableSearchPanel implements ActionListener, 
									ListSelectionListener,
									TableModelListener {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private SessionProperties sessionProperties;
	private MacawCurationAPI database;
	private User currentUser;
	private UserInterfaceFactory userInterfaceFactory;
	
	private JPanel panel;

	private String allChoicesText;
	private VariableSearchTableModel variableSearchTableModel;
	private JTable searchTable;
	
	private JTextField searchField;
	private JRadioButton filterByRawVariables;
	private JRadioButton filterByDerivedVariables;
	private JRadioButton showRawAndDerivedVariables;
	
	private JComboBox filterByCategory;
	private JComboBox filterByYear;
	
	private JButton applySearch;
	private JButton reset;
	
	private ChangeListener changeListener;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public VariableSearchPanel(SessionProperties sessionProperties) {
		this.sessionProperties = sessionProperties;
		userInterfaceFactory = sessionProperties.getUserInterfaceFactory();
		database = (MacawCurationAPI) sessionProperties.getProperty(SessionProperties.DATABASE);
		currentUser = (User) sessionProperties.getProperty(SessionProperties.CURRENT_USER);
		
		allChoicesText
			= MacawMessages.getMessage("general.fieldValue.allChoices");
		panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC = userInterfaceFactory.createGridBagConstraints();
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		
		panelGC.insets = new Insets(2, 2, 2, 2);
		JPanel filterCriteriaPanel 
			= createFilterCriteriaPanel();
		filterCriteriaPanel.setBorder(LineBorder.createGrayLineBorder());
		panel.add(filterCriteriaPanel, panelGC);

		panelGC.gridy++;		
		panelGC.fill = GridBagConstraints.BOTH;
		panelGC.weightx = 1.0;
		panelGC.weighty = 1.0;
		panel.add(createResultPanel(), panelGC);		
	}
	
	private JPanel createResultPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.insets = new Insets(2, 2, 2, 2);		

		String searchResultsTitleLabelText
			= MacawMessages.getMessage("general.labels.searchResults");
		JLabel searchResultsTitleLabel
			= userInterfaceFactory.createLabel(searchResultsTitleLabelText);
		panel.add(searchResultsTitleLabel, panelGC);
		
		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.BOTH;
		panelGC.weightx = 100;
		panelGC.weighty = 100;

		variableSearchTableModel
			= new VariableSearchTableModel(database);
		searchTable = userInterfaceFactory.createTable(variableSearchTableModel);		
		JTableHeader header = searchTable.getTableHeader();
		header.setReorderingAllowed(false);				
		JScrollPane filterPane 
			= userInterfaceFactory.createScrollPane(searchTable);
		
		ListSelectionModel listSelectionModel
			= searchTable.getSelectionModel();
		listSelectionModel.addListSelectionListener(this);
		variableSearchTableModel.addTableModelListener(this);
		
		panel.add(filterPane, panelGC);
				
		try {
			variableSearchTableModel.showAllVariableSummaries(currentUser);			
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}		
		
		return panel;
	}
	
	private JPanel createFilterCriteriaPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.insets = new Insets(2, 2, 2, 2);		
		panelGC.ipadx = 5;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		
		panel.add(createSearchFieldFilterPanel(), panelGC);
		panelGC.gridy++;
		panel.add(createYearAndCategoryFieldFilterPanel(), panelGC);
		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 0.0;

		panel.add(createVariableTypeFilterPanel(), panelGC);
		
		panelGC.gridy++;
		panelGC.fill = GridBagConstraints.NONE;
		panelGC.weightx = 0;
		panelGC.weighty = 0;
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
		panel.add(createButtonPanel(), panelGC);
		return panel;
	}

	private JPanel createButtonPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.insets = new Insets(2, 2, 2, 2);
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
		
		String searchLabelText
			= MacawMessages.getMessage("general.labels.search");
		applySearch
			= userInterfaceFactory.createButton(searchLabelText);
		applySearch.addActionListener(this);
		panel.add(applySearch, panelGC);
		
		panelGC.gridx++;
		String resetLabelText
			= MacawMessages.getMessage("general.buttons.reset");		
		reset
			= userInterfaceFactory.createButton(resetLabelText);
		reset.addActionListener(this);
		panel.add(reset, panelGC);
		
		return panel;
	}

	private JPanel createSearchFieldFilterPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.ipadx = 5;

		String searchFieldLabelText
			= MacawMessages.getMessage("general.labels.searchPhrase");
		JLabel searchFieldLabel
			= userInterfaceFactory.createLabel(searchFieldLabelText);
		panel.add(searchFieldLabel, panelGC);		

		panelGC.gridx++;
		panelGC.weightx = 1.0;
		searchField = userInterfaceFactory.createTextField(40);		
		panel.add(searchField, panelGC);

		return panel;
	}
	
	private JPanel createVariableTypeFilterPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.ipadx = 5;

		ButtonGroup buttonGroup = new ButtonGroup();
		String filterbyTypeLabelText
			= MacawMessages.getMessage("variableSearchPanel.variableType");
		JLabel filterByTypeLabel
			= userInterfaceFactory.createLabel(filterbyTypeLabelText);
		panel.add(filterByTypeLabel, panelGC);
		panelGC.gridx++;
		String filterByRawVariablesText
			= MacawMessages.getMessage("variableSearchPanel.rawVariables");
		filterByRawVariables 
			= userInterfaceFactory.createRadioButton(filterByRawVariablesText);
		buttonGroup.add(filterByRawVariables);
		panel.add(filterByRawVariables, panelGC);
		panelGC.gridx++;
		String filterByDerivedVariablesText
			= MacawMessages.getMessage("variableSearchPanel.derivedVariables");
		filterByDerivedVariables 
			= userInterfaceFactory.createRadioButton(filterByDerivedVariablesText);
		buttonGroup.add(filterByDerivedVariables);
		panel.add(filterByDerivedVariables, panelGC);	
		
		panelGC.gridx++;
		String showRawAndDerivedVariablesText
			= MacawMessages.getMessage("variableSearchPanel.rawAndDerivedVariables");
		showRawAndDerivedVariables
			= userInterfaceFactory.createRadioButton(showRawAndDerivedVariablesText);
		buttonGroup.add(showRawAndDerivedVariables);
		panel.add(showRawAndDerivedVariables, panelGC);	
		
		showRawAndDerivedVariables.setSelected(true);
		
		filterByRawVariables.addActionListener(this);
		filterByDerivedVariables.addActionListener(this);
		showRawAndDerivedVariables.addActionListener(this);		
		
		return panel;
	}

	private JPanel createYearAndCategoryFieldFilterPanel() {
		JPanel panel = userInterfaceFactory.createPanel();
		GridBagConstraints panelGC
			= userInterfaceFactory.createGridBagConstraints();	
		panelGC.ipadx = 5;
		
		panelGC.gridy++;
		panelGC.gridx = 0;
		panelGC.weightx = 0;
		String yearFieldLabelText
			= MacawMessages.getMessage("general.fields.year");
		JLabel yearFieldLabel = userInterfaceFactory.createLabel(yearFieldLabelText);
		panel.add(yearFieldLabel, panelGC);
		panelGC.gridx++;
		String[] availableYears = getYearChoices();
		filterByYear = userInterfaceFactory.createComboBox(availableYears);
		panel.add(filterByYear, panelGC);
		
		//add the filter by category criteria
		panelGC.gridx++;
		String categoryLabelText
			= MacawMessages.getMessage("general.fields.category");
		JLabel categoryLabel
			= userInterfaceFactory.createLabel(categoryLabelText);
		panel.add(categoryLabel, panelGC);
		panelGC.gridx++;
		panelGC.fill = GridBagConstraints.HORIZONTAL;
		panelGC.weightx = 1.0;
		String[] categoryChoices = new String[0];
		try {
			ArrayList<Category> categories 
				= database.getCategories(currentUser);
			//here, insert the category key word "All"
			String allText
				= MacawMessages.getMessage("general.fieldValue.allChoices");
			Category allCategories = new Category(allText);
			categories.add(0, allCategories);
			categoryChoices 
				= ConstantConverter.getCategoryChoices(categories);
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}	
		filterByCategory
			= userInterfaceFactory.createComboBox(categoryChoices);
		panel.add(filterByCategory, panelGC);
		
		return panel;
	}

	private String[] getYearChoices() {
		try {
			String[] years 
				= database.getStudyYears(currentUser);
			String[] results = new String[years.length + 1];
	
			//add the "All" choice to allow all choices to be selected
			results[0] = allChoicesText;
			for (int i = 1; i < results.length; i++) {
				results[i] = years[i - 1];
			}
			return results;		
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}

		return(new String[0]);
	}
			
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	
	public void deleteSelectedItems() throws MacawException {
		ArrayList<RawVariable> selectedRawVariables 
			= new ArrayList<RawVariable>();
		ArrayList<DerivedVariable> selectedDerivedVariables 
			= new ArrayList<DerivedVariable>();
		int[] selectedRowIndices
			= searchTable.getSelectedRows();
		for (int i = 0; i < selectedRowIndices.length; i++) {
			VariableSummary variableSummary
				= variableSearchTableModel.getVariableSummary(selectedRowIndices[i]);
			Variable selectedVariable
				= database.getCompleteVariableData(currentUser,
												   variableSummary);
			if (selectedVariable instanceof RawVariable) {
				selectedRawVariables.add((RawVariable) selectedVariable);
			}
			else {
				selectedDerivedVariables.add((DerivedVariable) selectedVariable);				
			}
		}	
		
		database.deleteRawVariables(currentUser, selectedRawVariables);
		database.deleteDerivedVariables(currentUser, selectedDerivedVariables);	
		searchTable.clearSelection();
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	public ArrayList<Variable> getSelectedVariables() throws MacawException {
		ArrayList<Variable> selectedVariables = new ArrayList<Variable>();
		int[] selectedRowIndices
			= searchTable.getSelectedRows();
		for (int i = 0; i < selectedRowIndices.length; i++) {
			VariableSummary variableSummary
				= variableSearchTableModel.getVariableSummary(selectedRowIndices[i]);
			Variable variable 
				= database.getCompleteVariableData(currentUser,
												   variableSummary);
			selectedVariables.add(variable);
		}
		
		//XXXX convert summaries into real values
		
		return selectedVariables;
	}
	
	
	
	public Variable getSelectedVariable() throws MacawException {
		int selectedIndex
			= searchTable.getSelectedRow();
		if (selectedIndex == -1) {
			return null;
		}
		else {
			VariableSummary variableSummary
				= variableSearchTableModel.getVariableSummary(selectedIndex);
			Variable variable 
				= database.getCompleteVariableData(currentUser,
												   variableSummary);
			return variable;
		}
	}
	
	public void applySearch() {
		String searchPhrase
			= searchField.getText().trim();
		String selectedCategory
			= (String) filterByCategory.getSelectedItem();
		String selectedYear
			= (String) filterByYear.getSelectedItem();
		
		try {
			if (showRawAndDerivedVariables.isSelected() == true) {
				variableSearchTableModel.filterVariables(currentUser, 
										 		 		searchPhrase, 
										 		 		selectedYear, 
										 		 		selectedCategory);
			}
			else if (filterByDerivedVariables.isSelected() == true) {
				variableSearchTableModel.filterVariables(currentUser, 
														searchPhrase, 
														selectedYear, 
														selectedCategory,
														true);
			}
			else {
				variableSearchTableModel.filterVariables(currentUser, 
														searchPhrase, 
														selectedYear, 
														selectedCategory,
														false);			
			}			
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}
	}
	
	public void refreshList() {
		try {
			variableSearchTableModel.refreshList();
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);			
		}
	}
	
	public void reset() {
		//reset the search field
		searchField.setText("");
		filterByYear.setSelectedItem(allChoicesText);
		filterByCategory.setSelectedItem(allChoicesText);
		showRawAndDerivedVariables.setSelected(true);

		try {
			variableSearchTableModel.showAllVariableSummaries(currentUser);			
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}
	}
	
	public void setChangeListener(ChangeListener changeListener) {
		this.changeListener = changeListener;
	}
	
	public void setSelectionMode(int selectionMode) {
		ListSelectionModel listSelectionModel
			= searchTable.getSelectionModel();
		listSelectionModel.setSelectionMode(selectionMode);
	}
	
	public boolean showRawVariables() {
		if (filterByRawVariables.isSelected() == true) {
			return true;
		}
		else if (showRawAndDerivedVariables.isSelected() == true) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean showDerivedVariables() {
		if (filterByDerivedVariables.isSelected() == true) {
			return true;
		}
		else if (showRawAndDerivedVariables.isSelected() == true) {
			return true;
		}
		else {
			return false;
		}
	}

	public void selectVariable(Variable variable) {
		int selectionRow = variableSearchTableModel.getRow(variable.createVariableSummary());
		if (selectionRow != -1) {
			Rectangle selectionArea 
				= searchTable.getCellRect(selectionRow, 
										  0, 
										  true);
			searchTable.scrollRectToVisible(selectionArea);			
		}
	}
	
	public boolean hasSearchResults() {
		if (variableSearchTableModel.getRowCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	// ==========================================
	// Section Errors and Validation
	// ==========================================

	// ==========================================
	// Section Interfaces
	// ==========================================

	//Interface: ActionListener
	public void actionPerformed(ActionEvent event) {
		Object button = event.getSource();
		
		if (button == applySearch) {
			applySearch();
		}
		else if (button == reset) {
			reset();
		}		
	}

	//Interface: List Selection Listener
	 public void valueChanged(ListSelectionEvent event) {
		 if ((event.getValueIsAdjusting() == false) &&
			 (changeListener != null) ){
			 ChangeEvent changeEvent = new ChangeEvent(this);
			 changeListener.stateChanged(changeEvent);
		 }
	 }
	
	 //Interface: Table Model Listener
	 public void tableChanged(TableModelEvent event) {
		 if (changeListener != null){
			 ChangeEvent changeEvent = new ChangeEvent(this);
			 changeListener.stateChanged(changeEvent);
		 }		 
	 }
	 
	 // ==========================================
	// Section Overload
	// ==========================================

}

