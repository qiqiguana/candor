package macaw.system;

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

public class MacawException extends Exception {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private ArrayList<MacawError> errors;

	// ==========================================
	// Section Construction
	// ==========================================
	
	public MacawException() {
		super();
		errors = new ArrayList<MacawError>();
	}
	
	public MacawException(MacawErrorType errorType,
						  String errorMessage) {
		super(errorMessage);
		MacawError error = new MacawError(errorType, errorMessage);
		errors = new ArrayList<MacawError>();
		errors.add(error);
	}
	
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	public void addErrorMessage(MacawErrorType errorType,
								String errorMessage) {
		MacawError error = new MacawError(errorType, errorMessage);
		errors.add(error);
	}

	public ArrayList<String> getErrorMessages() {
		ArrayList<String> results = new ArrayList<String>();
		for (MacawError error : errors) {
			results.add(error.getMessage());			
		}
		return results;
	}
	
	public ArrayList<MacawError> getErrors() {
		return errors;
	}
	
	public int getNumberOfErrors() {
		return errors.size();
	}

	public int getNumberOfErrors(MacawErrorType errorType) {
		int numberOfMatchingErrors = 0;
		for (MacawError error : errors) {
			if (error.getErrorType() == errorType) {
				numberOfMatchingErrors++;
			}
		}
		return numberOfMatchingErrors;
	}

	public void printErrors() {
		int i = 0;
		for (MacawError error : errors) {
			i++;
			System.out.println("ERROR #"+i+" type="+error.getErrorType()+"=message=="+error.getMessage()+"==");
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

