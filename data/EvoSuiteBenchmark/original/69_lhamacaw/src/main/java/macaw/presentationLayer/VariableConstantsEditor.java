package macaw.presentationLayer;

import macaw.util.*;
import macaw.system.*;
import macaw.businessLayer.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
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

public class VariableConstantsEditor implements ActionListener,
												DisplayableListParentForm {

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
	
	private JDialog dialog;
	
	private DisplayableListPanel categoryListPanel;
	private DisplayableListPanel cleaningStatusListPanel;
	private DisplayableListPanel availabilityStatusListPanel;
	private DisplayableListPanel aliasFilePathListPanel;

	private JButton close;

	private Log log;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public VariableConstantsEditor(SessionProperties sessionProperties,
								   boolean allowWriteAccess) {
		String title
			= MacawMessages.getMessage("variableConstantsEditor.title");
		userInterfaceFactory = sessionProperties.getUserInterfaceFactory();
		log = sessionProperties.getLog();
		
		database 
			= (MacawCurationAPI) sessionProperties.getProperty(SessionProperties.DATABASE);
		currentUser
			= (User) sessionProperties.getProperty(SessionProperties.CURRENT_USER);
		dialog
			= userInterfaceFactory.createDialog();
		dialog.setTitle("Macaw List Choice Editor");
		
		JPanel panel
			= userInterfaceFactory.createPanel();
		GridBagConstraints panelGC 
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.insets = new Insets(2, 2, 2, 2);
		panelGC.fill = GridBagConstraints.BOTH;
		panelGC.weightx = 1.0;
		panelGC.weighty = 1.0;
		
		//category list
		panelGC.gridy++;
		String categoryListPanelTitle
			= MacawMessages.getMessage("variableConstantsEditor.categoryChoicesListTitle");
		categoryListPanel 
			= new DisplayableListPanel(sessionProperties,
									   this,
									   categoryListPanelTitle,
									   true);
		ArrayList<Displayable> categories = getCategoryChoices();
		categoryListPanel.setDisplayableItems(categories);
		CategoryStateEditor categoryChoiceEditor = new CategoryStateEditor(sessionProperties);
		categoryListPanel.setListOwnerName(categoryListPanelTitle);
		categoryListPanel.setDisplayableListItemAdder(categoryChoiceEditor);
		categoryListPanel.setDisplayableListItemEditor(categoryChoiceEditor);
		categoryListPanel.setDisplayableListItemViewer(categoryChoiceEditor);		
		categoryListPanel.setDisplayableListItemDeleter(categoryChoiceEditor);	
		panel.add(categoryListPanel.getPanel(), panelGC);
		
		//cleaning status list
		panelGC.gridy++;
		String cleaningStatusListPanelTitle
			= MacawMessages.getMessage("variableConstantsEditor.cleaningChoicesListTitle");
		cleaningStatusListPanel 
			= new DisplayableListPanel(sessionProperties,
									   this,
									   cleaningStatusListPanelTitle,
									   true);
		ArrayList<Displayable> cleaningStatusChoices 
			= getCleaningStatusChoices();
		cleaningStatusListPanel.setDisplayableItems(cleaningStatusChoices);
		CleaningStateEditor cleaningStateEditor 
			= new CleaningStateEditor(sessionProperties);
		cleaningStatusListPanel.setListOwnerName(cleaningStatusListPanelTitle);
		cleaningStatusListPanel.setDisplayableListItemAdder(cleaningStateEditor);
		cleaningStatusListPanel.setDisplayableListItemEditor(cleaningStateEditor);
		cleaningStatusListPanel.setDisplayableListItemViewer(cleaningStateEditor);			
		cleaningStatusListPanel.setDisplayableListItemDeleter(cleaningStateEditor);	
		panel.add(cleaningStatusListPanel.getPanel(), panelGC);

		//availability status list
		panelGC.gridy++;
		String availabilityStatusListPanelTitle
			= MacawMessages.getMessage("variableConstantsEditor.availabilityChoicesListTitle");
		availabilityStatusListPanel 
			= new DisplayableListPanel(sessionProperties,
									   this,
									   availabilityStatusListPanelTitle,
									   true);
		ArrayList<Displayable> availabilityStatusChoices 
			= getAvailabilityStatusChoices();
		availabilityStatusListPanel.setDisplayableItems(availabilityStatusChoices);
		AvailabilityStateEditor availabilityChoiceEditor 
			= new AvailabilityStateEditor(sessionProperties);
		availabilityStatusListPanel.setListOwnerName(availabilityStatusListPanelTitle);
		availabilityStatusListPanel.setDisplayableListItemAdder(availabilityChoiceEditor);
		availabilityStatusListPanel.setDisplayableListItemEditor(availabilityChoiceEditor);
		availabilityStatusListPanel.setDisplayableListItemViewer(availabilityChoiceEditor);		
		availabilityStatusListPanel.setDisplayableListItemDeleter(availabilityChoiceEditor);	
		panel.add(availabilityStatusListPanel.getPanel(), panelGC);
		
		//add in the alias file path
		panelGC.gridy++;
		String aliasFilePathListPanelTitle
			= MacawMessages.getMessage("variableConstantsEditor.aliasFilePathChoicesListTitle");
		aliasFilePathListPanel 
			= new DisplayableListPanel(sessionProperties,
									   this,
									   aliasFilePathListPanelTitle,
									   true);

		ArrayList<Displayable> aliasFilePaths 
			= getAliasFilePaths();
		aliasFilePathListPanel.setDisplayableItems(aliasFilePaths);
		AliasFilePathEditor aliasFilePathEditor 
			= new AliasFilePathEditor(sessionProperties);
		aliasFilePathListPanel.setListOwnerName(availabilityStatusListPanelTitle);
		aliasFilePathListPanel.setDisplayableListItemAdder(aliasFilePathEditor);
		aliasFilePathListPanel.setDisplayableListItemEditor(aliasFilePathEditor);
		aliasFilePathListPanel.setDisplayableListItemViewer(aliasFilePathEditor);		
		aliasFilePathListPanel.setDisplayableListItemDeleter(aliasFilePathEditor);	
		panel.add(aliasFilePathListPanel.getPanel(), panelGC);
		
		panelGC.gridy++;
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
		panelGC.fill = GridBagConstraints.NONE;
		panel.add(createCloseButtonPanel(), panelGC);
		
		dialog.getContentPane().add(panel);
		dialog.setModal(true);
		dialog.setSize(550, 690);
		dialog.setResizable(false);
		WindowSizeListener windowSizeListener
			= new WindowSizeListener();
		dialog.addComponentListener(windowSizeListener);
	}

	private JPanel createCloseButtonPanel() {
		JPanel panel
			= userInterfaceFactory.createPanel();
		GridBagConstraints panelGC 
			= userInterfaceFactory.createGridBagConstraints();
		panelGC.anchor = GridBagConstraints.SOUTHEAST;
		panelGC.fill = GridBagConstraints.NONE;

		String closeText = MacawMessages.getMessage("general.buttons.close");
		close = userInterfaceFactory.createButton(closeText);
		close.addActionListener(this);
		panel.add(close, panelGC);
		
		return panel;
	}
	
	private ArrayList<Displayable>getCategoryChoices() {		

		ArrayList<Displayable> results
			= new ArrayList<Displayable>();

		try {			
			ArrayList<Category> categories
				= database.getCategories(currentUser);
		
			for (Category currentCategory : categories) {
				results.add((Displayable) currentCategory);			
			}
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}
		
		return results;
	}

	
	private ArrayList<Displayable>getCleaningStatusChoices() {
		ArrayList<Displayable> results
			= new ArrayList<Displayable>();

		try {			
			ArrayList<CleaningState> cleaningStates
				= database.getCleaningStates(currentUser);
	
			for (CleaningState currentCleaningState : cleaningStates) {
				results.add((Displayable) currentCleaningState);			
			}
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}
	
		return results;
	}
	
	private ArrayList<Displayable> getAvailabilityStatusChoices() {
		ArrayList<Displayable> results
			= new ArrayList<Displayable>();

		try {			
			ArrayList<AvailabilityState> availabilityStates
				= database.getAvailabilityStates(currentUser);
	
			for (AvailabilityState currentCleaningState : availabilityStates) {
				results.add((Displayable) currentCleaningState);			
			}
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}
		
		return results;
	}
	
	private ArrayList<Displayable>getAliasFilePaths() {
		ArrayList<Displayable> results
			= new ArrayList<Displayable>();

		try {			
			ArrayList<AliasFilePath> aliasFilePaths
				= database.getAliasFilePaths(currentUser);
	
			for (AliasFilePath currentAliasFilePath : aliasFilePaths) {
				results.add((Displayable) currentAliasFilePath);			
			}
		}
		catch(MacawException exception) {
			Log log = sessionProperties.getLog();
			log.displayErrorDialog(exception);
		}

		return results;
	}
		
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public void show() {
		dialog.setVisible(true);
	}
	
	private void close() {
		dialog.setVisible(false);
	}
	
	// ==========================================
	// Section Errors and Validation
	// ==========================================

	// ==========================================
	// Section Interfaces
	// ==========================================

	//Interface: Action Listener
	public void actionPerformed(ActionEvent event) {
		Object button = event.getSource();

		if (button == close) {
			close();
		}
	}

	//Interface: DisplayableListParentForm
	public void commitChanges() throws MacawException {
		
	}
	
	// ==========================================
	// Section Overload
	// ==========================================

}

