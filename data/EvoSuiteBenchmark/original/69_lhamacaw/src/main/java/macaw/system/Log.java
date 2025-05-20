package macaw.system;

import javax.swing.JOptionPane;
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

public class Log {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	
	// ==========================================
	// Section Construction
	// ==========================================
	public Log() {
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public void logException(Exception exception) {
		if (exception instanceof MacawException) {
			MacawException macawException = (MacawException) exception;
			macawException.printErrors();
		}
		else {
			exception.printStackTrace(System.out);			
		}
	}
	
	public void displayInformationDialog(String message) {
		JOptionPane.showMessageDialog(null,
									  message,
									  null,
									  JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	
	public void displayErrorDialog(String errorMessage) {
		String dialogTitle
			= MacawMessages.getMessage("errorDialog.title");

		JOptionPane.showMessageDialog(null,
									  errorMessage,
									  dialogTitle,
									  JOptionPane.ERROR_MESSAGE);
	}

	public void displayErrorDialog(MacawException exception) {
		exception.printStackTrace(System.out);
		ArrayList<MacawError> errors = exception.getErrors();
		StringBuilder buffer = new StringBuilder();
		buffer.append("\n");
		for (MacawError currentError : errors) {
			buffer.append("* ");
			buffer.append(currentError.getMessage());
			buffer.append("\n");
		}

		String errorsWereEncounteredMessage
			= MacawMessages.getMessage("errorDialog.multipleErrorsOccurred",
									   buffer.toString());

		
		String dialogTitle
			= MacawMessages.getMessage("errorDialog.title");

		JOptionPane.showMessageDialog(null,
									  errorsWereEncounteredMessage,
									  dialogTitle,
									  JOptionPane.ERROR_MESSAGE);		
	}
	
	/**
	public void displayErrorDialog(ArrayList<String> errorMessages) {
		
		StringBuilder buffer = new StringBuilder();
		buffer.append("\n");
		for (String currentErrorMessage : errorMessages) {
			buffer.append("* ");
			buffer.append(currentErrorMessage);
			buffer.append("\n");
		}

		String errorsWereEncounteredMessage
			= MacawMessages.getMessage("errorDialog.multipleErrorsOccurred",
									   buffer.toString());

		
		String dialogTitle
			= MacawMessages.getMessage("errorDialog.title");

		JOptionPane.showMessageDialog(null,
									  errorsWereEncounteredMessage,
									  dialogTitle,
									  JOptionPane.ERROR_MESSAGE);
	}
	*/
	
	public void displayErrorDialog(Exception exception) {
		displayErrorDialog(exception.getMessage());
		exception.printStackTrace(System.out);
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
