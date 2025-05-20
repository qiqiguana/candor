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

public class OntologyTermsPanel {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private Variable variable;
	private MacawCurationAPI database;
	private Log log;
	private User currentUser;
	private DisplayableListPanel displayableListPanel;

	private OntologyTermSelectionDialog ontologyTermSelectionDialog;
	private OntologyTermEditor ontologyTermEditor;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public OntologyTermsPanel(SessionProperties sessionProperties,
							  DisplayableListParentForm parentForm,
							  boolean allowWriteAccess) {

		
		database 
			= (MacawCurationAPI) sessionProperties.getProperty(SessionProperties.DATABASE);
		currentUser
			= (User) sessionProperties.getProperty(SessionProperties.CURRENT_USER);
		log = sessionProperties.getLog();
		
		String title
			= MacawMessages.getMessage("ontologyTermsPanel.title");
		
		ontologyTermEditor
			= new OntologyTermEditor(sessionProperties, allowWriteAccess);
		ontologyTermSelectionDialog 
			= new OntologyTermSelectionDialog(sessionProperties);
		
		displayableListPanel 
			= new DisplayableListPanel(sessionProperties,
									   parentForm,
									   title,
									   allowWriteAccess);
				
		//unlike other examples such as the SourceVariablesPanel, this class
		//uses the same class for all three Add, Edit and View functions.
		
		displayableListPanel.setDisplayableListItemSelector(ontologyTermSelectionDialog);
		displayableListPanel.setDisplayableListItemAdder(ontologyTermEditor);
		displayableListPanel.setDisplayableListItemEditor(ontologyTermEditor);
		//displayableListPanel.setDisplayableListItemViewer(ontologyTermEditor);
		displayableListPanel.setDisplayableListItemDeleter(ontologyTermEditor);
	}
		
	public void setNewRecord(Variable variable) {
		displayableListPanel.setNewRecord(variable.isNewRecord());
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public void setData(Variable variable) {
		this.variable = variable;
		setNewRecord(variable);
		
		ontologyTermSelectionDialog.setVariable(variable);
		ontologyTermEditor.setParentVariable(variable);
		//ontologyTermSelectionDialog.setVariable(variable);
		displayableListPanel.setListOwnerName(variable.getDisplayName());
		
		//ask the database to obtain to the list of associated ontology terms
		try {
			ArrayList<OntologyTerm> ontologyTerms 
				= database.getOntologyTerms(currentUser, variable);
				
			ArrayList<Displayable> masterOntologyTermCollection 
				= new ArrayList<Displayable>();
			for (OntologyTerm ontologyTerm : ontologyTerms) {
				Displayable cloneDocument = (Displayable) ontologyTerm.clone();
				masterOntologyTermCollection.add(cloneDocument);
			}			
			
			displayableListPanel.setDisplayableItems(masterOntologyTermCollection);		
		}
		catch(MacawException exception) {
			log.logException(exception);
		}
		
	}
	
	public ArrayList<OntologyTerm> getOntologyTerms() {
		ArrayList<Displayable> currentListItems
			= displayableListPanel.getAllListItems();
		
		ArrayList<OntologyTerm> results = new ArrayList<OntologyTerm>();
		for (Displayable currentOntologyTerm : currentListItems) {
			results.add((OntologyTerm) currentOntologyTerm);
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

