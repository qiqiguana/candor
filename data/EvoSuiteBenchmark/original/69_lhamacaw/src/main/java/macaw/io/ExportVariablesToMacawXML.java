package macaw.io;

import macaw.persistenceLayer.demo.DemonstrationRetrievalService;
import macaw.system.Log;
import macaw.system.SessionProperties;
import macaw.system.StartupOptions;
import macaw.util.Displayable;

import macaw.businessLayer.MacawRetrievalAPI;
import macaw.businessLayer.OntologyTerm;
import macaw.businessLayer.SupportingDocument;
import macaw.businessLayer.User;
import macaw.businessLayer.ValueLabel;
import macaw.businessLayer.Variable;

import java.util.ArrayList;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

/**
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

public class ExportVariablesToMacawXML {
	
	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private SessionProperties sessionProperties;
	private User currentUser;
	
	private File selectedExportFile;
	private BufferedWriter writer;

	private MacawRetrievalAPI macawRetrievalAPI;
	
	// ==========================================
	// Section Construction
	// ==========================================

	public ExportVariablesToMacawXML(SessionProperties sessionProperties) {
		this.sessionProperties = sessionProperties;
		currentUser 
			= (User) sessionProperties.getProperty(SessionProperties.CURRENT_USER);
		macawRetrievalAPI 
			= (MacawRetrievalAPI) sessionProperties.getProperty(SessionProperties.RETRIEVAL_SERVICE);
	}
		
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public void export() {
		Log log = sessionProperties.getLog();
		try {
			
			selectExportFile();

			writeBeginXMLDocument();
			String[] variableNames
				= macawRetrievalAPI.getVariableNames(currentUser);
			for (String variableName : variableNames) {
				Variable variable
					= macawRetrievalAPI.getVariable(currentUser, variableName);
				writeVariableToMacawXML(currentUser, variable);
			}			
			writeEndXMLDocument();
			writer.close();
		}
		catch(Exception exception) {
			log.logException(exception);			
		}	
	}

	private void selectExportFile() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		int choice = fileChooser.showSaveDialog(null);
		if (choice != JFileChooser.APPROVE_OPTION) {
			StringBuilder message = new StringBuilder();
			message.append("No destination file for export provided.");
			message.append("Terminating export program.");
			System.out.println(message.toString());
		}
		selectedExportFile 
			= fileChooser.getSelectedFile();
		
		String currentFilePath = selectedExportFile.getAbsolutePath();
		if (currentFilePath.toUpperCase().endsWith(".XML") == false) {
			String revisedFilePath = currentFilePath + ".xml";
			selectedExportFile = new File(revisedFilePath);
		}
		
		FileWriter fileWriter = new FileWriter(selectedExportFile);		
		writer = new BufferedWriter(fileWriter);		
	}
	
	private void writeVariableToMacawXML(User user,
			 							 Variable variable) throws IOException {

		writeXMLBeginTagWithIdentifier("raw_variable", variable);

		
		
		
		//write out the main attributes of a variable		
		writeXMLTag("name", variable.getName());
		writeXMLTag("label", variable.getLabel());
		writeXMLTag("year", variable.getYear());
		writeXMLTag("category", variable.getCategory());
		writeXMLTag("is_cleaned", variable.isCleaned());	
		writeXMLTag("cleaning_status", variable.getCleaningStatus());
		writeXMLTag("cleaning_description", variable.getCleaningDescription());
		writeXMLTag("availability", variable.getAvailability());
		writeXMLTag("alias", variable.getAlias());
		writeXMLTag("file_path", variable.getFilePath());
		writeXMLTag("is_coded", variable.isCoded());
		writeXMLTag("form", variable.getForm());
		writeXMLTag("question_number", variable.getQuestionNumber());
		writeXMLTag("code_book_number", variable.getCodeBookNumber());
		writeXMLTag("notes", variable.getNotes());		
		writeXMLTag("column_start", variable.getColumnStart());
		writeXMLTag("column_end", variable.getColumnEnd());
		writeXMLTag("notes", variable.getNotes());

		//write out associations
		writeValueLabelsToMacawXML(user, variable);
		writeOntologyTermsToMacawXML(user, variable);
		writeSupportingDocumentsToMacawXML(user, variable);

		writeEndXMLTag("raw_variable");		
		writer.flush();
	}

	private void writeValueLabelsToMacawXML(User user, Variable variable) throws IOException {
		ArrayList<ValueLabel> valueLabels
			= macawRetrievalAPI.getValueLabels(user, variable.getName());
		if (valueLabels.size() == 0) {
			return;
		}

		writeBeginXMLTag("value_labels");

		for (ValueLabel valueLabel : valueLabels) {
			writeXMLBeginTagWithIdentifier("value_label", valueLabel);
			writeXMLTag("value", valueLabel.getValue());
			writeXMLTag("label", valueLabel.getLabel());
			writeXMLTag("is_missing", valueLabel.isMissingValue());			
			writeEndXMLTag("value_label");			
		}

		writeEndXMLTag("value_labels");	
		writer.flush();
	}

	private void writeSupportingDocumentsToMacawXML(User user,
													Variable variable) throws IOException {

		ArrayList<SupportingDocument> supportingDocuments
			= macawRetrievalAPI.getSupportingDocuments(user, variable.getName());
			
		if (supportingDocuments.size() == 0) {
			return;
		}

		writeBeginXMLTag("supporting_documents");

		for (SupportingDocument supportingDocument : supportingDocuments) {
			writeXMLBeginTagWithIdentifier("supporting_document", supportingDocument);
			writeXMLTag("title", supportingDocument.getTitle());
			writeXMLTag("description", supportingDocument.getDescription());
			writeXMLTag("document_code", supportingDocument.getDocumentCode());
			writeXMLTag("file_path", supportingDocument.getFilePath());
			writeEndXMLTag("supporting_document");			
		}

		writeEndXMLTag("supporting_documents");		
	}


	private void writeOntologyTermsToMacawXML(User user,
											  Variable variable) throws IOException {

		ArrayList<OntologyTerm> ontologyTerms
			= macawRetrievalAPI.getOntologyTerms(user, variable.getName());
		if (ontologyTerms.size() == 0) {
			return;
		}
		
		writeBeginXMLTag("ontology_terms");

		for (OntologyTerm ontologyTerm : ontologyTerms) {
			writeXMLBeginTagWithIdentifier("ontology_term", ontologyTerm);
			writeXMLTag("term", ontologyTerm.getTerm());
			writeXMLTag("description", ontologyTerm.getDescription());
			writeXMLTag("ontology_name", ontologyTerm.getOntologyName());
			writeXMLTag("name_space", ontologyTerm.getNameSpace());
			writeEndXMLTag("ontology_term");			
		}

		writeEndXMLTag("ontology_terms");
		writer.flush();
	}
	
	private void writeBeginXMLDocument() throws IOException {
		writer.write("<?xml version = \"1.0\" encoding = \"UTF-8\"?>");
		writer.write("<dss_variables>");
	}

	private void writeEndXMLDocument() throws IOException {
		writer.write("</dss_variables>");
		writer.flush();
	}


	private void writeBeginXMLTag(String tagName) throws IOException {
		writer.write("<");
		writer.write(tagName);
		writer.write(">");		
	}
	
	private void writeEndXMLTag(String tagName) throws IOException {
		writer.write("</");
		writer.write(tagName);
		writer.write(">");		
	}

	private void writeXMLBeginTagWithIdentifier(String tagName, Displayable tagItem) throws IOException {
		writer.write("<");
		writer.write(tagName);
		writer.write(" id=");
		writer.write("\"");
		writer.write(tagItem.getDisplayItemIdentifier());
		writer.write("\"");		
		writer.write(">");		
	}

	private void writeXMLTag(String tagName, String tagValue) throws IOException {
		if (tagValue.equals("") == true) {
			return;
		}
		
		writeBeginXMLTag(tagName);		
		writer.write(tagValue);
		writeEndXMLTag(tagName);
	}

	private void writeXMLTag(String tagName, boolean tagValue) throws IOException {
		writeBeginXMLTag(tagName);		
		writer.write(String.valueOf(tagValue));
		writeEndXMLTag(tagName);
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

