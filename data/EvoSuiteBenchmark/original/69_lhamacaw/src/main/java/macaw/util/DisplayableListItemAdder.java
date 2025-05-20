package macaw.util;

import macaw.system.MacawException;

/**
 * Manages functionality for adding an item to a {@link macaw.util.DisplayableListPanel}.
 * It is called when the user presses "Add" in the panel.  The DisplayableListItemAdder
 * is typically an editor that follows these steps when <code>addItems(...)</code> is
 * invoked:
 * <ul>
 * <li>make a note of the displayableList and pop up some kind of editor.</li>
 * <li>Let user create record and press some kind of button (usually an "OK" button).</li>
 * <li>validate the new record, typically by making a call to some implementation of 
 * {@link macaw.businessLayer.MacawCurationAPI}</li>.  The DB call will attempt to add the new 
 * record and fail if it encounters an exception, such as a duplicate record error.</li>
 * <li>update <code>displayableList</code> so that it refreshes the current list of items.  
 * The add operation will necessitate refreshing the collection of list items that appear
 * to an end-user.</li>
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

public interface DisplayableListItemAdder {
	public void addItems(String listOwnerName,
						 DisplayableList displayableList) throws MacawException;
}

