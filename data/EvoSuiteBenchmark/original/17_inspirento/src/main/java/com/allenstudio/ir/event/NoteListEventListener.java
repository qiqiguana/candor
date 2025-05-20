/*
 * @(#)NoteListEventListener.java
 * Created on 2005-8-9
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

import java.util.EventListener;
/**
 * 
 * @author Allen Chue
 */
public interface NoteListEventListener extends EventListener {
    /**
     * Invoked when the user makes a
     * single left click on a <code>NoteList</code>
     * cell.
     * @param e a <code>NoteListEvent</code> object
     */
    public void leftClickOnCell(NoteListEvent e);
    
    /**
     * Invoked when the user right-clicks
     * on a <code>NoteList</code> cell.
     * @param e a <code>NoteListEvent</code> object
     */
    public void rightClickOnCell(NoteListEvent e);

    /**
     * Invoked when the user double-clicks
     * on a <code>NoteList</code> cell.
     * @param e a <code>NoteListEvent</code> object
     */
    public void doubleClickOnCell(NoteListEvent e);
    
    /**
     * Invoked when the cusor moves above a cell.
     * @param e a <code>NoteListEvent</code> object
     */
    public void cursorEnterCell(NoteListEvent e);
    
    /**
     * Invoked when the cusor moves out of a cell.
     * @param e a <code>NoteListEvent</code> object
     */
    public void cursorExitCell(NoteListEvent e);
}
