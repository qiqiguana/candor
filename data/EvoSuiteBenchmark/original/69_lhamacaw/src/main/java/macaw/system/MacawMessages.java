package macaw.system;

import java.text.MessageFormat;
import java.util.ResourceBundle;


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

public class MacawMessages {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private static ResourceBundle resourceBundle 
		= ResourceBundle.getBundle("MacawProperties");
	
	// ==========================================
	// Section Construction
	// ==========================================
	private MacawMessages() {
		
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	public static String getMessage(String key) {
		if (resourceBundle != null) {
			return (resourceBundle.getString(key));
	    }
	    return key;
	}

	public static String getMessage(String key,
	                             	String parameter0) {

		String[] parameters = new String[1];
	    parameters[0] = parameter0;

	    return fillInTheBlanks(key,
	                		   parameters);
	}

	public static String getMessage(String key,
									String parameter0,
									String parameter1) {

		String[] parameters = new String[2];
	    parameters[0] = parameter0;
	    parameters[1] = parameter1;

	    return fillInTheBlanks(key,
	       parameters);
	    }

	public static String getMessage(String key,
	                                String parameter0,
	                             	String parameter1,
	                           		String parameter2) {

	        String[] parameters = new String[3];
	        parameters[0] = parameter0;
	        parameters[1] = parameter1;
	        parameters[2] = parameter2;

	        return fillInTheBlanks(key,
	                parameters);
	    }

	    public static String getMessage(String key,
	    						 		String parameter0,
	    						 		String parameter1,
	    						 		String parameter2,
	    						 		String parameter3) {

	        String[] parameters = new String[4];
	        parameters[0] = parameter0;
	        parameters[1] = parameter1;
	        parameters[2] = parameter2;
	        parameters[3] = parameter3;
	        return fillInTheBlanks(key,
	                parameters);

	    }

	    private static String fillInTheBlanks(String key,
	    							   		  String[] parameters) {

	    	String messageWithBlanks = resourceBundle.getString(key);

	    	MessageFormat messageFormat
	    		= new MessageFormat(messageWithBlanks);
	    	String messageWithoutBlanks
	    		= messageFormat.format(parameters);
	    	return messageWithoutBlanks;
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

