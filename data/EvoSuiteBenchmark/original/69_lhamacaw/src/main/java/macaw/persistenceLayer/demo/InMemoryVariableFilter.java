package macaw.persistenceLayer.demo;

import macaw.businessLayer.*;
import macaw.system.*;
import macaw.util.SearchUtility;
import macaw.util.ValidationUtility;

import java.util.ArrayList;

/**
 * Contains code to support searching for variables in the Macaw forms found
 * in the package <code>macaw.view</code>
 * 
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

public class InMemoryVariableFilter {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private ArrayList<Variable> variables;
	private String allText;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public InMemoryVariableFilter() {
		variables = new ArrayList<Variable>();
		allText = MacawMessages.getMessage("general.fieldValue.allChoices");
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public void setVariables(ArrayList<Variable> variables) {
		this.variables = variables;
	}
	
	
	public ArrayList<VariableSummary> filterVariableSummaries(User user,
			   								   		  		  String searchPhrase,
			   								   		  		  String year,
			   								   		  		  String category,
			   								   		  		  VariableTypeFilter variableTypeFilter) throws MacawException {

		ArrayList<Variable> variableTypeFilterResults
			= filterByVariableType(variableTypeFilter, variables);
		ArrayList<Variable> categoryFilterResults
			= filterByCategory(category, variableTypeFilterResults);
		ArrayList<Variable> yearFilterResults
			= filterByYear(year, categoryFilterResults);
		ArrayList<Variable> searchPhraseFilterResults
			= filterBySearchPhrase(searchPhrase, yearFilterResults);
	
		ArrayList<VariableSummary> finalResults
			= new ArrayList<VariableSummary>();
		for (Variable currentVariable : searchPhraseFilterResults) {
			finalResults.add(currentVariable.createVariableSummary());
		}
		
		return finalResults;
	}
	
	private ArrayList<Variable> filterByVariableType(VariableTypeFilter variableTypeFilter,
													 ArrayList<Variable> results) {
		ArrayList<Variable> refinedResults = new ArrayList<Variable>();
		
		if (variableTypeFilter == VariableTypeFilter.RAW_AND_DERIVED) {
			//return everything so just return the original result list
			return results;
		}
		else if (variableTypeFilter == VariableTypeFilter.RAW) {
			for (Variable currentResult : results) {
				if (currentResult instanceof RawVariable) {
					refinedResults.add(currentResult);
				}
			}
		}
		else {
			for (Variable currentResult : results) {
				if (currentResult instanceof DerivedVariable) {
					refinedResults.add(currentResult);
				}
			}
		}

		return refinedResults;
	}

	private ArrayList<Variable> filterByCategory(String categoryFilter, 
											 	 ArrayList<Variable> results) {

		if (categoryFilter.equals(allText) == true) {
			return results;
		}

		SearchUtility searchUtility = new SearchUtility();
		searchUtility.setDefaultSearchPattern(categoryFilter);
		
		ArrayList<Variable> refinedResults = new ArrayList<Variable>();
		for (Variable currentResult : results) {
			String currentCategory 
				= currentResult.getCategory();
			if (searchUtility.valueContainsPattern(currentCategory) == true) {
				refinedResults.add(currentResult);
			}
		}

		return refinedResults;
	}
	
	private ArrayList<Variable> filterByYear(String yearFilter, 
											 ArrayList<Variable> results) {

		if (yearFilter.equals(allText) == true) {
			return results;
		}

		SearchUtility searchUtility = new SearchUtility();
		searchUtility.setDefaultSearchPattern(yearFilter);
		
		ArrayList<Variable> refinedResults = new ArrayList<Variable>();
		for (Variable currentResult : results) {
			String currentYear = currentResult.getYear();
			if (searchUtility.valueContainsPattern(currentYear) == true) {
				refinedResults.add(currentResult);
			}
		}
		
		return refinedResults;
	}
	
	private ArrayList<Variable> filterBySearchPhrase(String searchPhraseFilter,
													 ArrayList<Variable> results) {
		
		//if no search string specified, assume they don't want to filter by
		//that.
		if (ValidationUtility.isEmptyValue(searchPhraseFilter) == true) {
			return results;
		}

		SearchUtility searchUtility = new SearchUtility();
		searchUtility.setDefaultSearchPattern(searchPhraseFilter);
		
		ArrayList<Variable> refinedResults = new ArrayList<Variable>();
		for (Variable currentResult : results) {
			String currentName = currentResult.getName();
			String currentLabel = currentResult.getLabel();

			if ((searchUtility.valueContainsPattern(currentName) == true) ||
			    (searchUtility.valueContainsPattern(currentLabel) == true) ) {
				refinedResults.add(currentResult);
			}
		}
		
		return refinedResults;
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

