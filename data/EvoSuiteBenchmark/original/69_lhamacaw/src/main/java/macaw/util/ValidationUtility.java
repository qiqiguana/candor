package macaw.util;

import macaw.system.MacawMessages;

import java.util.regex.*;


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

public class ValidationUtility {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================

	// ==========================================
	// Section Construction
	// ==========================================
	private ValidationUtility() {
		
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	static public String convertEmptyValueToBlank(String value) {
		if (value.equals("") == true) {
			return MacawMessages.getMessage("general.fieldValue.blank");
		}
		else {
			return value;
		}
	}
	
	static public boolean isBlank(String value) {
		return (value.equals(""));
	}
	
	static public boolean isUnknown(String value) {
		String unknownMessage
			= MacawMessages.getMessage("general.fieldValue.unknown");
		return unknownMessage.equals(value);
	}
	
	static public boolean duplicateNameExists(String candidateName,
											  String[] duplicateNames) {
		for (int i = 0; i < duplicateNames.length; i++) {
			if (candidateName.equals(duplicateNames[i]) == true) {
				return true;
			}
		}
		
		return false;
	}
	
	static public String removeNullStrings(String value) {
		if (value == null) {
			return "";
		}
		else {
			return value;
		}
	}

	static public boolean promotesSecurityRisk(String value) {
		//allows whitespace, a-zA-Z_0-9, and the characters - and ,

		 Pattern pattern = Pattern.compile("[\\s\\w-,.]*");
		 Matcher matcher = pattern.matcher(value);
		 return !matcher.matches();
	}

	static public boolean isValidEmail(String value) {
		//allows whitespace, a-zA-Z_0-9, and the characters - and ,
		 String emailExpression
		 	= "^[\\w\\-\\+\\&\\*]+(?:\\.[\\w\\-\\_\\+\\&\\*]+)*@(?:[\\w-]+\\.)+[a-zA-Z]{2,7}$";
		 Pattern pattern = Pattern.compile(emailExpression);
		 Matcher matcher = pattern.matcher(value);
		 return matcher.matches();
	}

	
	static public boolean isEmptyValue(String value) {
		if (value == null) {
			return true;
		}
		
		if (value.equals("") == true) {
			return true;
		}
		
		return false;
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

