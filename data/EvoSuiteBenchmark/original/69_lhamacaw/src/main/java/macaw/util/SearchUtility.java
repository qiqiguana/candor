package macaw.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

public class SearchUtility {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private Pattern pattern;
	private String filterValue;
	
	// ==========================================
	// Section Construction
	// ==========================================

	public SearchUtility(String filterValue) {		
		setDefaultSearchPattern(filterValue);
	}
	
	public SearchUtility() {
		setDefaultSearchPattern("");
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	public void setDefaultSearchPattern(String filter) {
		setDefaultSearchPattern(filter, false);
	}
	
	public void setDefaultSearchPattern(String filterValue, boolean isCaseSensitive) {
		StringBuilder regularExpression
			= new StringBuilder();
		regularExpression.append(".*");
		regularExpression.append(filterValue.toUpperCase());
		regularExpression.append(".*");		
		pattern = Pattern.compile(regularExpression.toString().toUpperCase());
		this.filterValue = filterValue.toUpperCase();
	}

	public boolean valueExactlyMatches(String value) {
		if (value == null) return false;
	
		return filterValue.equals(value.toUpperCase());
	}
	
	public boolean valueContainsPattern(String value) {
		if (value == null) return false;			
		Matcher matcher = pattern.matcher(value.toUpperCase());
		return matcher.matches();
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

