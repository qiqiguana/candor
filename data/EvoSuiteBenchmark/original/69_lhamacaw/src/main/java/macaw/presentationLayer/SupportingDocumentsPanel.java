package macaw.presentationLayer;

import macaw.system.*;
import macaw.util.*;
import macaw.businessLayer.*;

import javax.swing.*;

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

public class SupportingDocumentsPanel {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private Variable variable;
	private MacawCurationAPI database;
	private User currentUser;
	private Log log;
	private boolean isNewRecord;
	private VariableEditor variableEditor;
	private DisplayableListPanel displayableListPanel;
	private SupportingDocumentSelectionDialog supportingDocumentSelectionDialog;
	private SupportingDocumentEditor supportingDocumentEditor;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public SupportingDocumentsPanel(SessionProperties sessionProperties,
									VariableEditor variableEditor,
									boolean allowWriteAccess) {
		
		
		this.variableEditor = variableEditor;
		
		database = (MacawCurationAPI) sessionProperties.getProperty(SessionProperties.DATABASE);
		log = sessionProperties.getLog();
		currentUser = (User) sessionProperties.getProperty(SessionProperties.CURRENT_USER);
		
		String title
			= MacawMessages.getMessage("supportingDocumentsPanel.title");
		
		supportingDocumentEditor
			= new SupportingDocumentEditor(sessionProperties,
										   allowWriteAccess);
		supportingDocumentSelectionDialog 
			= new SupportingDocumentSelectionDialog(sessionProperties);
		
		displayableListPanel 
			= new DisplayableListPanel(sessionProperties,
									   variableEditor,
									   title,
									   allowWriteAccess);
		

		//unlike other examples such as the SourceVariablesPanel, this class
		//uses the same class for all three Add, Edit and View functions.
		
		displayableListPanel.setDisplayableListItemSelector(supportingDocumentSelectionDialog);
		displayableListPanel.setDisplayableListItemAdder(supportingDocumentEditor);
		displayableListPanel.setDisplayableListItemEditor(supportingDocumentEditor);
		displayableListPanel.setDisplayableListItemViewer(supportingDocumentEditor);
		displayableListPanel.setDisplayableListItemDeleter(supportingDocumentEditor);
	}
		
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public void setNewRecord(Variable variable) {
		displayableListPanel.setNewRecord(variable.isNewRecord());
	}
	
	public void setData(Variable variable) {
		this.variable = variable;
		setNewRecord(variable);
			
		supportingDocumentEditor.setVariable(variable);
		supportingDocumentSelectionDialog.setVariable(variable);
		displayableListPanel.setListOwnerName(variable.getDisplayName());
		
		try {
			ArrayList<SupportingDocument> supportingDocuments
				= database.getSupportingDocuments(currentUser, variable);				
			ArrayList<Displayable> masterSupportingDocumentCollection 
				= new ArrayList<Displayable>();
			for (SupportingDocument supportingDocument : supportingDocuments) {
				Displayable cloneDocument = (Displayable) supportingDocument.clone();
				masterSupportingDocumentCollection.add(cloneDocument);
			}
		
			displayableListPanel.setDisplayableItems(masterSupportingDocumentCollection);			
		}
		catch(MacawException exception) {
			log.logException(exception);
		}
	}
	
	public ArrayList<SupportingDocument> getSupportingDocuments() {
		ArrayList<Displayable> currentListItems
			= displayableListPanel.getAllListItems();
		
		ArrayList<SupportingDocument> results = new ArrayList<SupportingDocument>();
		for (Displayable currentSupportingDocument : currentListItems) {
			results.add((SupportingDocument) currentSupportingDocument);
		}
		
		return results;
	}
	
	public JPanel getPanel() {
		return displayableListPanel.getPanel();
	}

	public void save() throws MacawException {
		//commit changes to the 
		variableEditor.commitChanges();
		displayableListPanel.save();
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

