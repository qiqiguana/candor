package macaw.io;

import macaw.businessLayer.Category;

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

public class ImportCategoryStates {
	
	// ==========================================
	// Section Constants
	// ==========================================

	// ==========================================
	// Section Properties
	// ==========================================
	private ArrayList<Category> categories;
	
	// ==========================================
	// Section Construction
	// ==========================================
	public ImportCategoryStates() {
		categories = new ArrayList<Category>();
	}
	
	// ==========================================
	// Section Accessors and Mutators
	// ==========================================
	
	public ArrayList<Category> getCategories() {
		return categories;
	}
	
	public void readCategoryFile(File file) {
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			String currentLine
				= reader.readLine();
			while (currentLine != null) {
				Scanner lineScanner = new Scanner(currentLine);
				lineScanner.useDelimiter("\t");
				
				String identifier = lineScanner.next();
				String categoryName = lineScanner.next();
				Category category = new Category(categoryName);
				categories.add(category);
				currentLine = reader.readLine();
			}
		}
		catch(Exception exception) {
			exception.printStackTrace();			
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

