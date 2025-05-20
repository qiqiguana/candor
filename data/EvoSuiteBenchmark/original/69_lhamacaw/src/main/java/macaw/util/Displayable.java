package macaw.util;


/**
 * A general purpose interface for showing a name that appears in lists of items
 * shown to end-users.  This interface is part of a wider collection of convenience
 * classes developed to provide generic functionality for displaying curation records
 * that can be edited or viewed.  Each Displayable item has three feature:
 * <ul>
 * <li>retrieve an identifier value that is meant to be human-readable</li>
 * <li>retrieve an identifier value that is meant to be machine-readable</li>
 * <li>be able to clone itself</li>
 * </ul>
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

public interface Displayable extends Cloneable {
	public String getDisplayItemIdentifier();
	public String getDisplayName();
	//public Object clone();
}

