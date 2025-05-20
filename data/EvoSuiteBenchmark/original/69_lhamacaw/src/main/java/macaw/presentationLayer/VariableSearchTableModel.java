package macaw.presentationLayer;

import macaw.system.*;
import macaw.businessLayer.MacawCurationAPI;
import macaw.businessLayer.User;
import macaw.businessLayer.VariableSummary;
import macaw.businessLayer.VariableTypeFilter;

import javax.swing.table.AbstractTableModel;
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

public class VariableSearchTableModel extends AbstractTableModel {

	// ==========================================
	// Section Constants
	// ==========================================

	private static final int NAME_FIELD = 0;
	private static final int LABEL_FIELD = 1;
	private static final int YEAR_FIELD = 2;
	
	// ==========================================
	// Section Properties
	// ==========================================
	private MacawCurationAPI database;
	private ArrayList<VariableSummary> variableSummaries;

	private User currentUser;
	private String searchText;
	private String year;
	private String category;
	private VariableTypeFilter variableTypeFilter;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public VariableSearchTableModel(MacawCurationAPI database) {
		this.database = database;
		variableSummaries = new ArrayList<VariableSummary>();
		searchText = "";
		year = "";
		category = "";
		variableTypeFilter = VariableTypeFilter.RAW_AND_DERIVED;
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	
	public int getRow(VariableSummary targetVariable) {
		int numberOfVariables = variableSummaries.size();
		for (int i = 0; i < numberOfVariables; i++) {
			if (targetVariable.equals(variableSummaries.get(i)) == true) {
				return i;
			}
		}
		return -1;
	}
	
	public VariableSummary getVariableSummary(int row) {
		return variableSummaries.get(row);
	}
	
	public void showAllVariableSummaries(User currentUser) throws MacawException {
		variableSummaries = database.getSummaryDataForAllVariables(currentUser);
		fireTableDataChanged();	
	}
	
	public void filterVariables(User currentUser,
								String searchText,
								String year,
								String category) throws MacawException {


		this.currentUser = currentUser;
		this.searchText = searchText;
		this.year = year;
		this.category = category;
		this.variableTypeFilter = VariableTypeFilter.RAW_AND_DERIVED;
		
		variableSummaries
			= database.filterVariableSummaries(currentUser,
									   		   searchText,
									   		   year,
									   		   category,
									   		   variableTypeFilter);
		fireTableDataChanged();
	}

	public void filterVariables(User currentUser,
								String searchText,
								String year,
								String category,
								boolean isDerivedVariable) throws MacawException {


		this.currentUser = currentUser;
		this.searchText = searchText;
		this.year = year;
		this.category = category;
		if (isDerivedVariable == true) {
			variableTypeFilter = VariableTypeFilter.DERIVED;			
		}
		else {
			variableTypeFilter = VariableTypeFilter.RAW;			
		}
		
		variableSummaries
			= database.filterVariableSummaries(currentUser,
									   		   searchText,
									   		   year,
									   		   category,
									   		   variableTypeFilter);
		fireTableDataChanged();
	}
	
	/**
	 * updates the last search.   This method is used when users have added, edited
	 * or deleted items from the list.
	 */
	public void refreshList() throws MacawException {
		variableSummaries
			= database.filterVariableSummaries(currentUser,
									   		   searchText,
									   		   year,
									   		   category,
									   		   variableTypeFilter);
		
		fireTableDataChanged();		
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

	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public String getColumnName(int column) {
		if (column == NAME_FIELD) {
			String nameLabelText
				= MacawMessages.getMessage("general.fields.name");
			return nameLabelText;
		}
		else if (column == LABEL_FIELD) {
			String labelLabelText
				= MacawMessages.getMessage("general.fields.label");
			return labelLabelText;
		}
		else {
			String yearLabelText
				= MacawMessages.getMessage("general.fields.year");
			return yearLabelText;
		}		
	}
	
	public int getColumnCount() {
		return 3;
	}

	public int getRowCount() {
		return variableSummaries.size();
	}

	public Object getValueAt(int row, int column) {
		VariableSummary variableSummary = variableSummaries.get(row);
		
		if (column == NAME_FIELD) {
			return variableSummary.getName();
		}
		else if (column == LABEL_FIELD) {
			return variableSummary.getLabel();
		}
		else if (column == YEAR_FIELD) {
			return variableSummary.getYear();
		}
		else {
			//should never happen but should most certainly cause an exception if it does.
			return null;
		}		
	}

}

