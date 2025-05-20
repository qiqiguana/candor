package macaw.businessLayer;

import java.util.ArrayList;

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

public class ConstantConverter {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================

	// ==========================================
	// Section Construction
	// ==========================================
	private ConstantConverter() {
		
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	/**
	 * creates string lists from model objects.
	 */
	static public String[] getCategoryChoices(ArrayList<Category> categories) {
		String[] results = new String[categories.size()];
		for (int i = 0; i < results.length; i++) {
			results[i] = categories.get(i).getDisplayName();
		}
		return results;			
	}

	static public String[] getAvailabilityStatusChoices(ArrayList<AvailabilityState> availabilityStatusChoices) {
		String[] results = new String[availabilityStatusChoices.size()];
		for (int i = 0; i < results.length; i++) {
			results[i] = availabilityStatusChoices.get(i).getDisplayName();
		}
		return results;	
	}

	static public String[] getCleaningStatusChoices(ArrayList<CleaningState> cleaningStatusChoices) {
		String[] results = new String[cleaningStatusChoices.size()];
		for (int i = 0; i < results.length; i++) {
			results[i] = cleaningStatusChoices.get(i).getDisplayName();
		}
		return results;			
	}

	static public String[] getAliasFilePathChoices(ArrayList<AliasFilePath> aliasFilePathChoices) {
		String[] results = new String[aliasFilePathChoices.size()];
		for (int i = 0; i < results.length; i++) {
			results[i] = aliasFilePathChoices.get(i).getDisplayName();
		}
		return results;			
	}

	
	static public String[] getSupportingDocumentNames(ArrayList<SupportingDocument> supportingDocuments) {
		String[] results = new String[supportingDocuments.size()];
		for (int i = 0; i < results.length; i++) {
			results[i] = supportingDocuments.get(i).getDisplayName();
		}
		return results;			
	}

	static public String[] getVariableNames(ArrayList<Variable> variables) {
		String[] results = new String[variables.size()];
		for (int i = 0; i < results.length; i++) {
			results[i] = variables.get(i).getDisplayName();
		}
		return results;			
	}

	static public String[] getValueLabels(ArrayList<ValueLabel> valueLabels) {
		String[] results = new String[valueLabels.size()];
		for (int i = 0; i < results.length; i++) {
			results[i] = valueLabels.get(i).getDisplayName();
		}
		return results;			
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

