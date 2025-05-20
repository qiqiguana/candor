package macaw.persistenceLayer.production;

import macaw.businessLayer.*;
import macaw.system.*;
import macaw.util.ValidationUtility;

import java.util.ArrayList;
import java.sql.*;

/**
 * <p></p>
 * <hr>
 * (c) 2009 Medical Research Council of the United Kingdom.
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

public class SQLFilterQueries {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private Log log;
	private SQLListChoiceManager listChoiceManager;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public SQLFilterQueries(Log log,
							SQLListChoiceManager listChoiceManager) {
		this.log = log;
		this.listChoiceManager = listChoiceManager;
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	/**
	 * we don't need to create a derivedVariables table because there are no 
	 * concepts beyond the ones in Variables we need to retain.  The exception is
	 * the list of source variables for each derived variable but that is handled in 
	 * a separate table.
	 * @param connection
	 * @throws SQLException
	 */

	//now focusing on methods demanded by the MacawDatabase API
	

	public ArrayList<VariableSummary> filterVariableSummaries(Connection connection,
			   												  String searchText,
			   												  String year,
			   												  String categoryName,
			   												  VariableTypeFilter variableTypeFilter) throws MacawException {

		ResultSet resultSet = null;
		Statement statement = null;

		try {

			StringBuilder query = new StringBuilder();
			query.append("SELECT identifier,");
			query.append("name,");
			query.append("year,");
			query.append("label,");
			query.append("is_derived_variable ");
			query.append("FROM variables");

			ArrayList<String> conditions = new ArrayList<String>();
		
			if (ValidationUtility.isBlank(searchText) == false) {
				StringBuilder condition = new StringBuilder();
				condition.append(" (label LIKE '%");
				condition.append(searchText);
				condition.append("%' OR name LIKE '%");
				condition.append(searchText);
				condition.append("%')");
				conditions.add(condition.toString());
			}
		
			if ((ValidationUtility.isBlank(year) == false) &&
				(year.equals("All") == false) ) {	
				StringBuilder condition = new StringBuilder();
				condition.append("year='");
				condition.append(year);
				condition.append("'");
				conditions.add(condition.toString());
			}
		
			if ((ValidationUtility.isBlank(categoryName) == false) &&
					(categoryName.equals("All") == false) ) {	
					StringBuilder condition = new StringBuilder();
					condition.append("category_id=");
		
					Category category = new Category(categoryName);
					int categoryId
						= listChoiceManager.getCategoryIdentifier(connection, null, category);
					condition.append(String.valueOf(categoryId));

					conditions.add(condition.toString());
			}
		
		
			if (variableTypeFilter == VariableTypeFilter.DERIVED) {
				StringBuilder condition = new StringBuilder();
				condition.append("is_derived_variable=TRUE");			
				conditions.add(condition.toString());
			}
			else if (variableTypeFilter == VariableTypeFilter.RAW) {
				StringBuilder condition = new StringBuilder();
				condition.append("is_derived_variable=FALSE");
				conditions.add(condition.toString());
			}
		
			query.append(" WHERE deleted_at IS NULL");			
		
			int numberOfConditions = conditions.size();					
			for (int i = 0; i < numberOfConditions; i++) {
				query.append(" AND ");
				query.append(conditions.get(i));
			}
		
			query.append(" ORDER BY year DESC;");

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query.toString());
			
			ArrayList<VariableSummary> variableSummaries = new ArrayList<VariableSummary>();
			while (resultSet.next() == true) {
				VariableSummary variableSummary = new VariableSummary();

				variableSummary.setIdentifier(resultSet.getInt(1));
				variableSummary.setName(resultSet.getString(2));
				String currentYear = resultSet.getString(3);			
				variableSummary.setYear(currentYear);
				
				variableSummary.setLabel(resultSet.getString(4));

				variableSummary.setDerived(resultSet.getBoolean(5));				
				variableSummaries.add(variableSummary);
			}
			
			return variableSummaries;
		}
		catch(SQLException exception) {
			exception.printStackTrace(System.out);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToFilterVariableSummaries");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_FILTER_SUMMARIES,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}
	

	public ArrayList<SupportingDocument> filterSupportingDocuments(Connection connection,
				  												   String documentTitleFilter,
				  												   String documentCodeFilter) throws MacawException {


		if ( (documentTitleFilter.equals("") == true) &&
			 (documentCodeFilter.equals("") == true)) {
			String errorMessage
				= MacawMessages.getMessage("supportingDocumentFilter.error");
			MacawException exception
				= new MacawException(MacawErrorType.NO_SUPPORTING_DOCUMENT_FILTER,
									errorMessage);
			throw exception;
		}
				
		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier,");
		query.append("title,");
		query.append("document_code,");
		query.append("description,");
		query.append("file_name,");
		query.append("file_path ");
		query.append("FROM supporting_documents ");
		query.append("WHERE deleted_at IS NULL AND ");

		ArrayList<String> conditions = new ArrayList<String>();

		if (ValidationUtility.isBlank(documentTitleFilter) == false) {
			StringBuilder condition = new StringBuilder();
			condition.append("title LIKE '%");
			condition.append(documentTitleFilter);
			condition.append("%'");
			conditions.add(condition.toString());
		}	
		
		if (ValidationUtility.isBlank(documentCodeFilter) == false) {
			StringBuilder condition = new StringBuilder();
			condition.append("document_code LIKE '%");
			condition.append(documentCodeFilter);
			condition.append("%'");
			conditions.add(condition.toString());
		}
		
		int numberOfConditions = conditions.size();		
		for (int i = 0; i < numberOfConditions; i++) {
			if (i != 0) {
				query.append(" AND ");
			}
			query.append(conditions.get(i));
		}

		query.append(" ORDER BY title ASC;");
		
		ResultSet resultSet = null;
		Statement statement = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query.toString());

			ArrayList<SupportingDocument> searchResults = new ArrayList<SupportingDocument>();
			while (resultSet.next() == true) {
				SupportingDocument supportingDocument
					= new SupportingDocument();
				supportingDocument.setIdentifier(resultSet.getInt(1));
				supportingDocument.setTitle(resultSet.getString(2));
				supportingDocument.setDocumentCode(resultSet.getString(3));
				supportingDocument.setDescription(resultSet.getString(4));
				supportingDocument.setFileName(resultSet.getString(5));
				supportingDocument.setFilePath(resultSet.getString(6));
				searchResults.add(supportingDocument);
			}

			return searchResults;
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToFilterSupportingDocuments");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_FILTER_SUPPORTING_DOCUMENTS,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
		}
	}

	public ArrayList<OntologyTerm> filterOntologyTerms(Connection connection,
				  									   String term,
				  									   String description) throws MacawException {

		
		if ((term.equals("") == true) &&
			(description.equals("") == true) ) {
			String errorMessage
				= MacawMessages.getMessage("ontologyTermFilter.error");
			MacawException exception
				= new MacawException(MacawErrorType.NO_ONTOLOGY_TERM_FILTER,
									 errorMessage);
			throw exception;
		}

		StringBuilder query = new StringBuilder();
		query.append("SELECT identifier,");
		query.append("term,");
		query.append("ontology_name,");
		query.append("description,");
		query.append("name_space");
		query.append(" FROM ontology_terms ");
		query.append("WHERE deleted_at IS NULL AND ");

		ArrayList<String> conditions = new ArrayList<String>();

		if (ValidationUtility.isBlank(term) == false) {
			StringBuilder condition = new StringBuilder();
			condition.append("term LIKE '%");
			condition.append(term);
			condition.append("%'");
			conditions.add(condition.toString());
		}	
		
		if (ValidationUtility.isBlank(description) == false) {
			StringBuilder condition = new StringBuilder();
			condition.append("description LIKE '%");
			condition.append(description);
			condition.append("%'");
			conditions.add(condition.toString());
		}
		
		int numberOfConditions = conditions.size();		
		for (int i = 0; i < numberOfConditions; i++) {
			if (i != 0) {
				query.append(" AND ");
			}
			query.append(conditions.get(i));
		}

		query.append(" ORDER BY term ASC;");
		
		ResultSet resultSet = null;
		Statement statement = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query.toString());

			ArrayList<OntologyTerm> searchResults = new ArrayList<OntologyTerm>();
			while (resultSet.next() == true) {
				OntologyTerm ontologyTerm
					= new OntologyTerm();
				ontologyTerm.setIdentifier(resultSet.getInt(1));
				ontologyTerm.setTerm(resultSet.getString(2));
				ontologyTerm.setOntologyName(resultSet.getString(3));
				ontologyTerm.setDescription(resultSet.getString(4));
				ontologyTerm.setNameSpace(resultSet.getString(5));
				searchResults.add(ontologyTerm);
			}

			return searchResults;
		}
		catch(SQLException exception) {
			log.logException(exception);
			String errorMessage
				= MacawMessages.getMessage("sql.error.unableToFilterOntologyTerms");
			MacawException macawException 
				= new MacawException(MacawErrorType.UNABLE_TO_FILTER_ONTOLOGY_TERMS,
									 errorMessage);
			throw macawException;
		}	
		finally {
			SQLUtilities.closeStatementsWithCatch(statement, resultSet);
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

