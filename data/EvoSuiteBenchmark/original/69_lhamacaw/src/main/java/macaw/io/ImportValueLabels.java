package macaw.io;


import macaw.businessLayer.*;

import java.io.*;
import java.util.*;

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

public class ImportValueLabels {

	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private HashMap<String, ArrayList<ValueLabel>> valueLabelsFromVariableName;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public ImportValueLabels() {
		valueLabelsFromVariableName = new HashMap<String, ArrayList<ValueLabel>>();
	}
	
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public void readVariableLabelsFile(File variableLabelsFile) {
		try {
			FileReader fileReader = new FileReader(variableLabelsFile);
			BufferedReader reader = new BufferedReader(fileReader);
			String currentLine
				= reader.readLine();
			while (currentLine != null) {
				Scanner lineScanner = new Scanner(currentLine);
				lineScanner.useDelimiter("\t");

				//prescan the line; it may have 3 or 4 phrases
				ArrayList<String> tokens = new ArrayList<String>();
				while (lineScanner.hasNext() == true) {
					tokens.add(lineScanner.next());
				}
					
				//assume all lines will have at least have the variable name
				String variableName = stripQuotes(tokens.get(0));
				ArrayList<ValueLabel> valueLabels
					= getValueLabels(variableName);

				ValueLabel valueLabel = new ValueLabel();
				String value = "";
				String label = "";
				boolean isMissingValue = false;

				if (tokens.size() == 2) {
					
				}
				else if (tokens.size() == 3) {
					value = stripQuotes(tokens.get(1));
					isMissingValue = parseMissingValue(tokens.get(2));					
				}
				else if (tokens.size() == 4) {
					value = stripQuotes(tokens.get(1));
					label = stripQuotes(tokens.get(2));
					isMissingValue = parseMissingValue(tokens.get(3));									
				}
				else {
					Exception exception = new Exception();
					throw exception;
				}

				valueLabel.setValue(value);
				valueLabel.setLabel(label);
				valueLabel.setMissingValue(isMissingValue);
				printValueLabel(valueLabel);

				valueLabels.add(valueLabel);
				currentLine = reader.readLine();
			}
		}
		catch(Exception exception) {
			exception.printStackTrace();			
		}		
	}
	
	public ArrayList getValueLabels(String variableName) {
		ArrayList<ValueLabel> valueLabels
			= (ArrayList<ValueLabel>) valueLabelsFromVariableName.get(variableName);
		if (valueLabels == null) {
			valueLabels = new ArrayList<ValueLabel>();
			valueLabelsFromVariableName.put(variableName, valueLabels);
		}
		
		return valueLabels;
	}

	private String stripQuotes(String phrase) {
		if (phrase.equals("") == true) {
			return phrase;
		}
		int length = phrase.length();
		return phrase.substring(1, length - 1);
	}

	private boolean parseMissingValue(String number) {
		if (number.equals("0") == true) {
			return false;
		}
		else {
			return true;
		}
	}

	private void printValueLabel(ValueLabel valueLabel) {
		/**
		StringBuilder buffer = new StringBuilder();
		buffer.append("Value Label==");
		buffer.append(valueLabel.getValue());
		buffer.append("==");
		buffer.append(valueLabel.getLabel());
		buffer.append("==");
		buffer.append(Boolean.toString(valueLabel.isMissingValue()));
		buffer.append("==");
		System.out.println(buffer.toString());
		*/
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

