package macaw.businessLayer;


/**
 * describes the types of variables in the system.  A {@link macaw.businessLayer.RawVariable} 
 * refers to a variable whose data come from a specific question on a specific questionnaire 
 * from the NSHD study.  A {@link macaw.businessLayer.DerivedVariable} refers to a variable whose 
 * data are derived from one or more variables that can be either raw or derived variables.
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

public enum VariableTypeFilter {
	RAW, DERIVED, RAW_AND_DERIVED
}

