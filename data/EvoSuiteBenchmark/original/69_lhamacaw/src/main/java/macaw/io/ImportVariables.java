package macaw.io;

import macaw.businessLayer.*;

import java.util.*;
import java.io.*;

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

public class ImportVariables {
	static public void main(String[] variables) {
		try {
			ImportVariables importVariables = new ImportVariables();
			importVariables.readFile(null);
			
		}
		catch(Exception exception) {
			exception.printStackTrace(System.out);
		}
	}
	
	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private ImportCategoryStates importCategoryStates;
	private ImportValueLabels importValueLabels;
	// ==========================================
	// Section Construction
	// ==========================================
	public ImportVariables() {
		importCategoryStates = new ImportCategoryStates();
		importValueLabels = new ImportValueLabels(); 
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	public void readFile(File file) throws Exception {
		readCategories();
		readVariableLabels();
		
		readVariables();		
	}
	
	private void readCategories() {
		File categoryFile = new File("C:\\macaw_import\\Categories.txt");
		importCategoryStates.readCategoryFile(categoryFile);
		ArrayList<Category> categories 
			= importCategoryStates.getCategories();
	}
	
	private void readVariableLabels() {
		File valueLabelsFile = new File("C:\\macaw_import\\ValueLabels.txt");
		importValueLabels.readVariableLabelsFile(valueLabelsFile);
		
	}
	
	private void readVariables() throws Exception {
		File variablesFile = new File("C:\\macaw_import\\Variables.txt");

		FileReader fileReader = new FileReader(variablesFile);
		BufferedReader reader = new BufferedReader(fileReader);
		String currentLine
			= reader.readLine();
		while (currentLine != null) {
			Scanner lineScanner = new Scanner(currentLine);
			lineScanner.useDelimiter("\t");

			boolean isDerived = parseBooleanValue(lineScanner.next());
			String name = stripQuotes(lineScanner.next());			
			String label = stripQuotes(lineScanner.next());
			String year = stripQuotes(lineScanner.next());			
			String category = stripQuotes(lineScanner.next());
			boolean isCleaned = parseBooleanValue(lineScanner.next());			
			String alias = stripQuotes(lineScanner.next());			
			String columnStart = stripQuotes(lineScanner.next());
			String columnEnd = stripQuotes(lineScanner.next());
			String form = stripQuotes(lineScanner.next());
			String questionNumber = stripQuotes(lineScanner.next());
			String codeBookNumber = stripQuotes(lineScanner.next());
			boolean isCoded = !parseBooleanValue(lineScanner.next());

			if (isDerived == true) {
				DerivedVariable derivedVariable = new DerivedVariable();
				derivedVariable.setCategory(category);

				derivedVariable.setName(name);
				derivedVariable.setLabel(label);
				derivedVariable.setCleaned(isCleaned);
				derivedVariable.setCoded(isCoded);
				derivedVariable.setYear(year);
			}
			else {
				RawVariable rawVariable = new RawVariable();
				
			}
			
		}
	}
	
	private String stripQuotes(String phrase) {
		if (phrase.equals("") == true) {
			return phrase;
		}
		int length = phrase.length();
		return phrase.substring(1, length - 1);
	}

	private boolean parseBooleanValue(String number) {
		if (number.equals("0") == true) {
			return false;
		}
		else {
			return true;
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

