/*
 * @(#)ListDataListener.java
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
 * 
 * 
 * @author Allen Chue
 */
package com.allenstudio.ir.event;

import java.util.EventListener;

/**
 * NoteListDataListener
 * 
 * @author Allen Chue
 */
public interface NoteListDataListener extends EventListener {
    /**
     * Sent after value of an item in the list
     * changes.
     * 
     * @param e a <code>NoteListDataEvent</code> object
     *          encapsulating the event information
     */
    void contentChanged(NoteListDataEvent e);
    
    /**
     * Sent after a new item is added to the list.
     * 
     * @param e a <code>NoteListDataEvent</code> object
     *          encapsulating the event information
     */
    void itemAdded(NoteListDataEvent e);
    
    /**
     * Sent after an item in the list is removed.
     * 
     * @param e a <code>NoteListDataEvent</code> object
     *          encapsulating the event information
     */
    void itemRemoved(NoteListDataEvent e);
}