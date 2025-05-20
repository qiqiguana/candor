/*
 * @(#)NoteListsPane.java
 * Created on 2005-8-26
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
package com.allenstudio.ir.ui;

import javax.swing.*;
import java.awt.*;

import java.util.*;

import com.allenstudio.ir.core.*;

/**
 * A pane with several tabs of different
 * types of notes.
 * A tab named "All" is always displayed.
 * Other tabs depend on the registered types
 * of notes. The registration is stored in
 * "config/config.xml". The program will
 * make use of this by the reflection technology
 * in Java. A plugin, that is a note-type,
 * corresponds to a tab in this pane. By the
 * name, the "All" tab shows all notes. For
 * other tabs, corresponding notes are displayed.
 * Each tab contains a <code>NoteList</code> in
 * it.
 * 
 * @author Allen Chue
 */
public class NoteListsTabPane extends JTabbedPane implements InspirentoWidget {
    
    private NoteList allList;
    
    private HashMap<String, NoteList> listMap = new HashMap<String, NoteList>();

    public static void main(String[] args) {
        JFrame f = new JFrame("Test");
        f.setLayout(new BorderLayout());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        NoteListsTabPane pane = new NoteListsTabPane();
        pane.removeItem("Birthday");
        f.add(pane, BorderLayout.CENTER);
        f.pack();
        f.setVisible(true);
    }
    
    public NoteListsTabPane() {
        addAllTab();//Adds the "All" button
        
        initTabs();
    }
    
    /**
     * Adds the tab titled "All" to the pane.
     * This tab is always displayed, and shows
     * all notes of various types. If no note
     * types are registered, this tab shows nothing.
     */
    private void addAllTab() {
        allList = new NoteList();
        JScrollPane scroller = new JScrollPane(allList);
        addTab(UIResources.getString("allTabTitle"), scroller);//$NON-NLS-1$
    }
    
    private void initTabs() {
        String[] pluginNames = PluginManager.getInstance().getAllPluginNames();
        
        for (String name : pluginNames) {
            NoteList list = new NoteList();
            JScrollPane scroller = new JScrollPane(list);
            addTab(name, scroller);
            listMap.put(name, list);
        }
    }
    
    public void removeItem(String tab) {
        NoteList list = listMap.get(tab);
        list.removeAll();
    }

    public void changed() {
        
    }
}
