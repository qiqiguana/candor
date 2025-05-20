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

public class SourceVariablesPanel {


	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================

	private Log log;
	private MacawCurationAPI database;
	private User currentUser;
	
	private SourceVariableSelectionDialog sourceVariableSelectionDialog;
	private DisplayableListPanel displayableListPanel;

	private SourceVariableEditor sourceVariableEditor;
	
	private DerivedVariable derivedVariable;
	private ArrayList<Displayable> masterSourceVariableCollection;
	private ArrayList<Displayable> currentSourceVariableCollection;

	// ==========================================
	// Section Construction
	// ==========================================
	public SourceVariablesPanel(SessionProperties sessionProperties,
								DisplayableListParentForm parentForm,
								boolean allowWriteAccess) {
		
		log = sessionProperties.getLog();
		database
			 = (MacawCurationAPI) sessionProperties.getProperty(SessionProperties.DATABASE);
		currentUser 
			= (User) sessionProperties.getProperty(SessionProperties.CURRENT_USER);
		
		String title
			= MacawMessages.getMessage("derivedVariable.sourceVariables.title");
		
		displayableListPanel 
			= new DisplayableListPanel(sessionProperties,
									   parentForm,
									   title,
									   allowWriteAccess,
									   false);
		
		sourceVariableSelectionDialog
			= new SourceVariableSelectionDialog(sessionProperties, this);
		displayableListPanel.setDisplayableListItemSelector(sourceVariableSelectionDialog);
		sourceVariableEditor = new SourceVariableEditor(sessionProperties, allowWriteAccess);
		displayableListPanel.setDisplayableListItemViewer(sourceVariableEditor);
		displayableListPanel.setDisplayableListItemDeleter(sourceVariableSelectionDialog);
		
		masterSourceVariableCollection = new ArrayList<Displayable>();
		currentSourceVariableCollection = new ArrayList<Displayable>();
		
		//displayableListPanel.setDisplayableListItemEditor(supportingDocumentEditor);
	}
		
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	public void setNewRecord(Variable variable) {
		displayableListPanel.setNewRecord(variable.isNewRecord());
	}

	public void setData(DerivedVariable derivedVariable) {
		this.derivedVariable = derivedVariable;
		setNewRecord(derivedVariable);

		ArrayList<Variable> sourceVariables = new ArrayList<Variable>();
		
		try {
			sourceVariables
				= database.getSourceVariables(currentUser, derivedVariable);			
		}
		catch(MacawException exception) {
			log.logException(exception);
		}

		masterSourceVariableCollection = new ArrayList<Displayable>();
		for (Variable sourceVariable : sourceVariables) {
			Displayable cloneVariable = (Displayable) sourceVariable.clone();
			masterSourceVariableCollection.add(cloneVariable);
		}
	
		currentSourceVariableCollection.clear();
		currentSourceVariableCollection.addAll(masterSourceVariableCollection);
		
		displayableListPanel.setDisplayableItems(currentSourceVariableCollection);
	}
	
	public DerivedVariable getData() {
		return derivedVariable;
	}
	
	public ArrayList<Variable> getSourceVariables() {
		ArrayList<Displayable> currentListItems
			= displayableListPanel.getAllListItems();
		
		ArrayList<Variable> results = new ArrayList<Variable>();
		for (Displayable currentVariable : currentListItems) {
			results.add((Variable) currentVariable);
		}
		
		return results;
	}

	public JPanel getPanel() {
		return displayableListPanel.getPanel();
	}
	
	public void save() {
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

class SourceVariableEditor implements DisplayableListItemEditor,
									  DisplayableListItemViewer {
	
	private SessionProperties sessionProperties;
	private boolean allowWriteAccess;
	private VariableEditor variableEditor;
	private Displayable listItem;
	
	public SourceVariableEditor(SessionProperties sessionProperties,
								boolean allowWriteAccess) {
		this.sessionProperties = sessionProperties;
		this.allowWriteAccess = allowWriteAccess;
	}
	
	public void editListItem(String listItemOwner,
			 				 Displayable listItem, 
			 				 DisplayableList displayableList) throws MacawException {
		this.listItem = listItem;
		setEditor(listItem);
		variableEditor.show();
	}
	
	public boolean isEditingCancelled() {
		return variableEditor.isCancelled();
	}
	
	public Displayable getItem() {
		return listItem;		
	}
	
	public void viewListItem(Displayable listItem) throws MacawException {
		this.listItem = listItem;
		setEditor(listItem);
		variableEditor.show();
	}
	
	private void setEditor(Displayable listItem) throws MacawException {
		if (listItem instanceof DerivedVariable) {
			DerivedVariable derivedVariable = (DerivedVariable) listItem;
			DerivedVariableEditor editor
				= new DerivedVariableEditor(sessionProperties,
											false);
			editor.setData(derivedVariable, false);
			variableEditor = editor;
		}
		else {
			RawVariableEditor editor
				= new RawVariableEditor(sessionProperties,
										false);
			RawVariable rawVariable = (RawVariable) listItem;
			editor.setData(rawVariable, false);
			variableEditor = editor;
		}
		
	}
}

