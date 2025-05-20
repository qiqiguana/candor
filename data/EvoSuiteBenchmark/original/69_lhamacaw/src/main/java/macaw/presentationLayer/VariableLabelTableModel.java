package macaw.presentationLayer;

import macaw.system.*;
import macaw.businessLayer.MacawCurationAPI;
import macaw.businessLayer.User;
import macaw.businessLayer.ValueLabel;
import macaw.businessLayer.Variable;

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

public class VariableLabelTableModel extends AbstractTableModel {

	// ==========================================
	// Section Constants
	// ==========================================
	public static final int VALUE_COLUMN = 0;
	public static final int LABEL_COLUMN = 1;
	public static final int IS_MISSING_VALUE = 2;
	
	// ==========================================
	// Section Properties
	// ==========================================
	private User currentUser;
	private MacawCurationAPI database;
	private boolean allowWriteAccess;
	private Variable currentVariable;
	private ArrayList<ValueLabel> valueLabelsOriginalList;
	private ArrayList<ValueLabel> valueLabelsCurrentList;	
	
	// ==========================================
	// Section Construction
	// ==========================================
	public VariableLabelTableModel(MacawCurationAPI database,
								   boolean allowWriteAccess) {
		this.database = database;
		this.allowWriteAccess = allowWriteAccess;
		valueLabelsOriginalList = new ArrayList<ValueLabel>();
		valueLabelsCurrentList = new ArrayList<ValueLabel>();
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public void setData(User currentUser,
						Variable currentVariable) throws MacawException {
		this.currentUser = currentUser;
		
		this.currentVariable = currentVariable;
				
		this.valueLabelsOriginalList 
			= database.getValueLabels(currentUser, currentVariable);
				
		valueLabelsCurrentList.clear();
		for (ValueLabel valueLabel : valueLabelsOriginalList) {
			ValueLabel cloneValueLabel
				= (ValueLabel) valueLabel.clone();
			valueLabelsCurrentList.add(cloneValueLabel);
		}	
		fireTableDataChanged();
	}
	
	public ArrayList<ValueLabel> getData() {
		return valueLabelsOriginalList;
	}
		
	public void addValueLabel(int row, ValueLabel valueLabel) {
		valueLabelsCurrentList.add(row, valueLabel);
		fireTableRowsInserted(row, row);
	}
	
	public void deleteValueLabel(int row) {
		valueLabelsCurrentList.remove(row);
		fireTableRowsDeleted(row, row);
	}
	
	public void save() throws MacawException {
		//perform validation
		for (ValueLabel valueLabel : valueLabelsCurrentList) {
			ValueLabel.validateFields(valueLabel);
		}

		//note that some of these methods may not be terribly efficient
		//but we don't expect that a variable will have a lot of entries.
	
		ArrayList<ValueLabel> addedValueLabelItems = new ArrayList<ValueLabel>();
		ArrayList<ValueLabel> editedValueLabelItems = new ArrayList<ValueLabel>();
		ArrayList<ValueLabel> deletedValueLabelItems = new ArrayList<ValueLabel>();
		
		//identify items that have been added
		for (ValueLabel copyValueLabel : valueLabelsCurrentList) {
			if (copyValueLabel.isNewRecord() == true) {
				addedValueLabelItems.add(copyValueLabel);
			}
		}
		
		//Identify items that have been deleted or edited...
		for (ValueLabel originalValueLabel : valueLabelsOriginalList) {
			ValueLabel copyValueLabel = findCopy(originalValueLabel);
			if (copyValueLabel == null) {
				//this means we must have deleted it
				deletedValueLabelItems.add(originalValueLabel);
			}
			else {
				//don't worry if nobody has touched the values, 
				//the api implementation is intended to make a quick
				//return if there are no differences
				editedValueLabelItems.add(copyValueLabel);
			}
		}
		
		//make commits to database
		database.addValueLabels(currentUser, 
								currentVariable, 
								addedValueLabelItems);
		database.updateValueLabels(currentUser, 
								   currentVariable, 
								   editedValueLabelItems);
		database.deleteValueLabels(currentUser, 
								   currentVariable, 
								   deletedValueLabelItems);
	}
	
	private ValueLabel findCopy(ValueLabel original) {
		int originalIdentifier = original.getIdentifier();
		for (ValueLabel currentCopy : valueLabelsCurrentList) {
			if (currentCopy.getIdentifier() == originalIdentifier) {
				return currentCopy;
			}
		}		
		return null;
	}
		
	//methods used to record provenance
	
			
	// ==========================================
	// Section Errors and Validation
	// ==========================================

	// ==========================================
	// Section Interfaces
	// ==========================================

	// ==========================================
	// Section Overload
	// ==========================================
	
    public Class getColumnClass(int column) {
    	if (column == VALUE_COLUMN) {
    		return String.class;
    	}
    	else if (column == LABEL_COLUMN) {
    		return String.class;
    	}
    	else {
    		//helps ensure that JTable will render this column with a
    		//check box
    		return Boolean.class;
    	}
    }
	
	public String getColumnName(int column) {
		if (column == VALUE_COLUMN) {
			String valueLabelText
				= MacawMessages.getMessage("variableLabelValueEditor.value");
			return valueLabelText;
		}
		else if (column == LABEL_COLUMN) {
			String labelLabelText
				= MacawMessages.getMessage("variableLabelValueEditor.label");
			return labelLabelText;
		}
		else {
			String isMissingValueLabelText
				= MacawMessages.getMessage("variableLabelValueEditor.isMissingValue");
			return isMissingValueLabelText;
		}		
	}
	
	public boolean isCellEditable(int row, int column) {
		return allowWriteAccess;
	}
	public int getColumnCount() {
		return 3;
	}

	public int getRowCount() {
		return valueLabelsCurrentList.size();
	}

	public Object getValueAt(int row, int column) {
		
		ValueLabel valueLabel
			= valueLabelsCurrentList.get(row);
		
		if (column == VALUE_COLUMN) {
			return valueLabel.getValue();
		}
		else if (column == LABEL_COLUMN) {
			return valueLabel.getLabel();
		}
		else {
			return valueLabel.isMissingValue();
		}
	}

	public void setValueAt(Object value, int row, int column) {
		
		//we only want to set a value and record the change if
		//(1) the record has not just been created and
		//(2) the old and new field values are different.
		
		//this setValueAt(...) method is called whenever the user
		//attempts to edit a table cell.  If they jump away or if 
		//the table programatically finishes an editing operation,
		//then this method is triggered.  In some cases users may
		//attempt to edit a cell then not do anything.
		
		ValueLabel valueLabel
			= valueLabelsCurrentList.get(row);

		if (valueLabel.isNewRecord() == true) {
			if (column == IS_MISSING_VALUE) {
				Boolean newFieldValue = (Boolean) value;
				valueLabel.setMissingValue(newFieldValue);
			}
			else {
				String newFieldValue = (String) value;
				if (column == VALUE_COLUMN) {
					valueLabel.setValue(newFieldValue);
				}
				else {
					valueLabel.setLabel(newFieldValue);
				}			
			}
		}
		else {
			if (column == IS_MISSING_VALUE) {
				Boolean oldFieldValue = valueLabel.isMissingValue();
				Boolean newFieldValue = (Boolean) value;
				if (oldFieldValue.equals(newFieldValue) == false) {
					valueLabel.setMissingValue(newFieldValue);
				}	
			}
			else {
				String newFieldValue = (String) value;
				if (column == VALUE_COLUMN) {
					String oldFieldValue = valueLabel.getValue();
					if (oldFieldValue.equals(newFieldValue) == false) {					
						valueLabel.setValue(newFieldValue);
					}
				}
				else {
					String oldFieldValue = valueLabel.getLabel();
					
					if (oldFieldValue.equals(newFieldValue) == false) {						
						valueLabel.setLabel(newFieldValue);
					}				
				}			
			}		
		}		
	}
}

