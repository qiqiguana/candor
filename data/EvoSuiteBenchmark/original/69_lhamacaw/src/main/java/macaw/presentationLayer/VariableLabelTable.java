package macaw.presentationLayer;

import macaw.system.MacawException;

import macaw.businessLayer.MacawCurationAPI;
import macaw.businessLayer.User;
import macaw.businessLayer.ValueLabel;
import macaw.businessLayer.Variable;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.ListSelectionModel;

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

public class VariableLabelTable extends JTable {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private VariableLabelTableModel tableModel;
	private User currentUser;
	private Variable variable;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public VariableLabelTable(MacawCurationAPI database,
							  boolean allowWriteAccess) {
		tableModel 
			= new VariableLabelTableModel(database,
										  allowWriteAccess);
		setModel(tableModel);
		
		JTableHeader header = getTableHeader();
		header.setReorderingAllowed(false);
		
		//for simplicity, make table support single item selection
		ListSelectionModel listSelectionModel
			= getSelectionModel();
		listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		setRowSelectionAllowed(true);
		
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	public void addValueLabel() {
		finishEditingCurrentCell();

		ValueLabel valueLabel = new ValueLabel();
		
		ListSelectionModel listSelectionModel
			= getSelectionModel();
		int rowCount = tableModel.getRowCount();
		
		int selectedIndex
			= this.getSelectedRow();
		if (selectedIndex == -1) {
			selectedIndex = rowCount;
			tableModel.addValueLabel(selectedIndex, valueLabel);
		}
		else {
			selectedIndex = selectedIndex + 1;
			tableModel.addValueLabel(selectedIndex, valueLabel);			
		}
		listSelectionModel.setSelectionInterval(selectedIndex, rowCount);

	}
	
	public void deleteValueLabel() {
		finishEditingCurrentCell();

		int selectionIndex 
			= this.getSelectedRow();
		if (selectionIndex != -1) {
			tableModel.deleteValueLabel(selectionIndex);
		}
	}
	
	public void setData(User currentUser,
						Variable variable) throws MacawException {
		this.currentUser = currentUser;
		this.variable = variable;

		finishEditingCurrentCell();
		tableModel.setData(currentUser,
						   variable);	
	}
	
	public void save() throws MacawException {
		finishEditingCurrentCell();
		tableModel.save();
		ArrayList<ValueLabel> valueLabels
			= tableModel.getData();
		variable.setValueLabels(valueLabels);
	}
		
	public void cancel() {
		finishEditingCurrentCell();
	}
	
	private void finishEditingCurrentCell() {
		if (isEditing() == true) {
			DefaultCellEditor defaultEditor
				= (DefaultCellEditor) getCellEditor();
			defaultEditor.stopCellEditing();			
		}
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

