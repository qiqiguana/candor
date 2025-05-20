/*
 * @(#)NoteListModel.java
 * Created on 2005-8-3
 * Inspirento, Copyright AllenStudio, All Rights Reserved
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

/**
 * This interface defines several methods
 * that a <code>NoteList</code> uses to
 * get the value of each cell in the list.<br>
 * Any changes of the data should be reported
 * to the <code>ListDataListener</code>.
 * 
 * @author Allen Chue
 */
package com.allenstudio.ir.ui;

import com.allenstudio.ir.core.plugins.*;
import com.allenstudio.ir.event.*;

/**
 * This interface offers some methods
 * that a <code>NoteList</code> would
 * use to get the value of each cell
 * in the list and the length of list.
 * 
 * @see NoteList
 * @see com.allenstudio.ir.event.NoteListDataListener
 * @author Allen Chue
 */
public interface NoteListModel {
    /**
     * Returns the length of the list
     * @return the length of the list
     */
    int getSize();
    
    /**
     * Returns the value at the specified index.  
     * @param index the requested index
     * @return the value at <code>index</code>
     */
    AbstractNote getElementAt(int index);
    
    /**
     * Adds a listener to the list that's notified each time a change
     * to the data model occurs.
     * @param l the <code>NoteListDataListener</code> to be added
     */  
    void addNoteListDataListener(NoteListDataListener l);

    /**
     * Removes the listener that's notified each time a 
     * change to the data model occurs.
     */  
    void removeNoteListDataListener();
}
