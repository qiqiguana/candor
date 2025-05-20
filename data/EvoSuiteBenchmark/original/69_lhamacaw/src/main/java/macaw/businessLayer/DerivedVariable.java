package macaw.businessLayer;

import java.util.ArrayList;

import macaw.system.*;

/**
 * a Variable that has been created using data from one or more {@link macaw.businessLayer.Variable}.
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

public class DerivedVariable extends Variable {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private ArrayList<Variable> sourceVariables;

	
	// ==========================================
	// Section Construction
	// ==========================================
	public DerivedVariable() {
		sourceVariables = new ArrayList<Variable>();	
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	
	public ArrayList<Variable> getSourceVariables() {
		return sourceVariables;
	}
	
	
	public void addSourceVariables(ArrayList<Variable> variables) {
		sourceVariables.addAll(variables);
	}
	
	public void setSourceVariables(ArrayList<Variable> sourceVariables) {
		this.sourceVariables = sourceVariables;
	}
	
	public void addSourceVariable(Variable variable) {
		sourceVariables.add(variable);
	}
	
	public void removeSourceVariable(Variable variable) {
		sourceVariables.remove(variable);		
	}
	
	public void removeSourceVariables(ArrayList<Variable> variablesToDelete) {
		for (Variable targetVariable : variablesToDelete) {
			sourceVariables.remove(targetVariable);
		}
	}

	public boolean containsSourceVariable(Variable targetVariable) {
		int targetIdentifier = targetVariable.getIdentifier();

		for (Variable sourceVariable : sourceVariables) {
			int currentIdentifier
				= sourceVariable.getIdentifier();
			if (targetIdentifier == currentIdentifier) {
				return true;
			}
		}
		return false;		
	}
	
	static public ArrayList<MacawChangeEvent> detectFieldChanges(User user,
			 													 DerivedVariable originalDerivedVariable,
			 													 DerivedVariable revisedDerivedVariable) {
		return Variable.detectFieldChanges(user, 
				   						   originalDerivedVariable, 
				   						   revisedDerivedVariable);		
	}
	
	public boolean hasSameDisplayName(Variable variable) {
		if ((getName().equals(variable.getName()) == true) &&
			(variable instanceof DerivedVariable == true)) {
			return true;
		}
		return false;
	}

	// ==========================================
	// Section Errors and Validation
	// ==========================================
	public static void validateFields(DerivedVariable derivedVariable) throws MacawException {
		ArrayList<String> errorMessages
			= Variable.validateFields(derivedVariable);
		
		if (errorMessages.size() > 0) {
			MacawException exception
				= new MacawException();
			for (String errorMessage: errorMessages) {
				exception.addErrorMessage(MacawErrorType.INVALID_DERIVED_VARIABLE,
										  errorMessage);
			}
			exception.printErrors();
			throw exception;
		}
	}
	// ==========================================
	// Section Interfaces
	// ==========================================

	// ==========================================
	// Section Overload
	// ==========================================
	public Object clone() {
		DerivedVariable cloneDerivedVariable = new DerivedVariable();
	
		ArrayList<Variable> clonedVariableList
			= new ArrayList<Variable>();
		clonedVariableList.addAll(sourceVariables);
		
		cloneDerivedVariable.setSourceVariables(clonedVariableList);
		super.cloneAttributes(cloneDerivedVariable);
		
		return cloneDerivedVariable;
	}
	
}

