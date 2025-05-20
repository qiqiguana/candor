package macaw.presentationLayer;

import macaw.system.*;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

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

public class ChangeHistoryTableModel extends AbstractTableModel {

	// ==========================================
	// Section Constants
	// ==========================================
	private static final int DATE_COLUMN = 0;
	private static final int CHANGE_MESSAGE = 1;
	private static final int USER_ID = 2;
	private SimpleDateFormat simpleDateFormat;
	
	// ==========================================
	// Section Properties
	// ==========================================
	private ArrayList<MacawChangeEvent> changeEvents;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public ChangeHistoryTableModel() {
		changeEvents = new ArrayList<MacawChangeEvent>();
		simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public void setChangeEvents(ArrayList<MacawChangeEvent> changeEvents) {
		this.changeEvents = changeEvents;
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
		String columnName = null;
		if (column == DATE_COLUMN) {
			columnName
				= MacawMessages.getMessage("changeHistory.date");						
		}
		else if (column == CHANGE_MESSAGE) {
			columnName
				= MacawMessages.getMessage("changeHistory.changeMessage");			
		}
		else if (column == USER_ID) {
			columnName
				= MacawMessages.getMessage("changeHistory.userID");
		}
		return columnName;
	}
	
	public int getColumnCount() {		
		return 3;
	}

	public int getRowCount() {
		return changeEvents.size();
	}

	public Object getValueAt(int row, int column) {
		MacawChangeEvent changeEvent = changeEvents.get(row);
		
		if (column == DATE_COLUMN) {
			java.util.Date date = changeEvent.getDate();
			return simpleDateFormat.format(date);
		}
		else if (column == CHANGE_MESSAGE) {
			return changeEvent.getChangeMessage();
		}
		else if (column == USER_ID) {
			return changeEvent.getUserID();
		}
		else {
			return null;
		}
	}
}
