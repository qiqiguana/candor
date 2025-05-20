/*
 * @(#)ListDataEvent.java
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
package com.allenstudio.ir.event;

import java.util.EventObject;

/**
 * Defines an event that encapsulates the
 * changes to a <code>NoteList</code>.
 * 
 * @author Allen Chue
 */
public class NoteListDataEvent extends EventObject {
    /**Identifies the change of content in the list*/
    public static final int CONTENT_CHANGED = 0;
    
    /**Identifies the addition of a new item*/
    public static final int ITEM_ADDED = 1;
    
    /**Identifies the removal of an item*/
    public static final int ITEM_REMOVED = 2;
    
    private int index;
    private int type;
    
    public NoteListDataEvent(Object source, int index) {
        super(source);
        this.index = index;
    }
    
    /**
     * Returns the event type. The possible values are:
     * <ul>
     * <li> {@link #CONTENTS_CHANGED}
     * <li> {@link #ITEM_ADDED}
     * <li> {@link #ITEM_REMOVED}
     * </ul>
     *
     * @return an int representing the type value
     */
    public int getType() {
        return type;
    }   
    
    /**
     * Returns the index of event source.
     *
     * @return an int representing the index value
     */
    public int getIndex() {
        return index;
    }
}