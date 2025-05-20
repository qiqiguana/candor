package macaw.presentationLayer;

import macaw.system.*;
import macaw.businessLayer.MacawCurationAPI;
import macaw.businessLayer.SupportingDocument;

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

public class SupportingDocumentSearchTableModel extends AbstractTableModel {


	// ==========================================
	// Section Constants
	// ==========================================
	private static final int DOCUMENT_CODE = 0;
	private static final int DOCUMENT_TITLE = 1;
	private static final int DOCUMENT_DESCRIPTION = 2;
	
	// ==========================================
	// Section Properties
	// ==========================================
	private MacawCurationAPI database;
	private ArrayList<SupportingDocument> supportingDocuments;

	// ==========================================
	// Section Construction
	// ==========================================
	public SupportingDocumentSearchTableModel(MacawCurationAPI database) {
		this.database = database;
		supportingDocuments = new ArrayList<SupportingDocument>();		
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	
	public void setData(ArrayList<SupportingDocument> supportingDocuments) {
		this.supportingDocuments = supportingDocuments;		
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

	public SupportingDocument getRow(int row) {
		return supportingDocuments.get(row);
	}
	
	public String getColumnName(int column) {
		String columnName = null;
		
		if (column == DOCUMENT_CODE) {
			columnName
				= MacawMessages.getMessage("supportingDocument.documentCode.label");
		}
		else if (column == DOCUMENT_TITLE) {
			columnName
				= MacawMessages.getMessage("supportingDocument.title.label");
		}
		else if (column == DOCUMENT_DESCRIPTION) {
			columnName
				= MacawMessages.getMessage("supportingDocument.description.label");
		}
		
		return columnName;
	}
	
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public int getColumnCount() {
		return 3;
	}

	public int getRowCount() {
		return supportingDocuments.size();
	}

	public Object getValueAt(int row, int column) {
		SupportingDocument supportingDocument
			= supportingDocuments.get(row);
		
		if (column == DOCUMENT_CODE) {
			return supportingDocument.getDocumentCode();
		}
		else if (column == DOCUMENT_TITLE) {
			return supportingDocument.getTitle();
		}
		else {
			//assume it is document description
			return supportingDocument.getDescription();
		}
	}
}
