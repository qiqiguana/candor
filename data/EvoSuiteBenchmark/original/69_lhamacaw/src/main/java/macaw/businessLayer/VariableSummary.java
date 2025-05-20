package macaw.businessLayer;

import macaw.util.Displayable;

/**
 * Describes the minimum amount of information in a search result for a Variable.  It is 
 * used to provide a summary of a variable record that would appear in a list of results.
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

public class VariableSummary implements Displayable {

	// ==========================================
	// Section Constants
	// ==========================================
	
	// ==========================================
	// Section Properties
	// ==========================================
	private int identifier;
	private String name;
	private String label;
	private String year;
	private boolean isDerived;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public VariableSummary() {
		identifier = 0;
		name = "";
		label = "";
		year = "";
		isDerived = false;
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================

	/**
	 * @return the identifier
	 */
	public int getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
		
	public boolean isDerived() {
		return isDerived;
	}
	
	public void setDerived(boolean isDerived) {
		this.isDerived = isDerived;
	}
	
	public boolean hasSameDisplayName(VariableSummary variableSummary) {
		if (getDisplayName().equals(variableSummary.getDisplayName()) == true) {
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

	//Interface: Displayable
	public String getDisplayName() {
		return name;
	}
	
	public String getDisplayItemIdentifier() {
		return String.valueOf(identifier);
	}
	
	public Object clone() {
		VariableSummary cloneSummary = new VariableSummary();
		cloneSummary.setIdentifier(identifier);
		cloneSummary.setLabel(label);
		cloneSummary.setName(name);
		cloneSummary.setYear(year);
		
		return cloneSummary;		
	}
	// ==========================================
	// Section Overload
	// ==========================================

}

