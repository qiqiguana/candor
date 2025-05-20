/*
 * @(#)NoteList.java
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
package com.allenstudio.ir.ui;

import java.awt.*;

import javax.swing.*;

import com.allenstudio.ir.core.plugins.*;
import com.allenstudio.ir.event.*;

/**
 * Designed to show a list of notes.
 * The user is allowed to select one of
 * the elements in this list.<br>
 * As its super class <code>JComponent</code>
 * is already is a container, I didn't use
 * a separate model class to save the contents
 * of this lis. Instead, I use methods derived
 * from <code>JComponent</code>, such as getComponentCount()
 * to get the total number of list items.<br>
 * <em>
 * For normal assumption, only the inner type <code>
 * NoteListCell</code> is allowed to be added to the list.
 * Efforts will be made to ensure this demand.
 * </em>
 * 
 * @see javax.swing.JPanel
 * @see NoteListModel
 * @version 1.0 Aug. 6, 2005
 * @author Allen Chue
 */
//TODO support multiple choice and mutliple modification
//FIXME this list should only accept the inner type NoteListCell
public class NoteList extends JPanel implements Scrollable {

    //Tests and demonstrates this class
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
            Font f = new Font("Tahoma, Dialog", Font.PLAIN, 12);
            UIManager.put("Button.font", f);
            UIManager.put("Label.font", f);
            // UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JFrame f = new JFrame("Test NoteList");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        f.setLayout(new BorderLayout());
        NoteList list = new NoteList();
        list.addNoteListEventListener(new NoteListEventListener() {

            public void leftClickOnCell(NoteListEvent e) {
            }

            public void rightClickOnCell(NoteListEvent e) {
            }

            public void doubleClickOnCell(NoteListEvent e) {
            }

            public void cursorEnterCell(NoteListEvent e) {
                NoteListCell cell = (NoteListCell)e.getSource();
                cell.setBorder(BorderFactory.createLineBorder(Color.gray));
            }

            public void cursorExitCell(NoteListEvent e) {
                NoteListCell cell = (NoteListCell)e.getSource();
                cell.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
            }
            
        });
        
        f.add(new JScrollPane(list), BorderLayout.CENTER);
        
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    
        
    
    private int selectedIndex = -1;//The first cell is selected by default
    
    private int cellCount = 0;
    
    private NoteListEventListener listListener = null;

    public NoteList() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        addCell(new NoteListCell(new CommonNote()));
        addCell(new NoteListCell(new CommonNote()));
        addCell(new NoteListCell(new CommonNote()));
        addCell(new NoteListCell(new CommonNote()));
        addCell(new NoteListCell(new CommonNote()));
    }
    
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    public int getScrollableUnitIncrement(Rectangle visibleRect,
            int orientation, int direction) {
        if (getComponentCount() > 0) {
            Component c = getComponent(0);
            return c.getHeight();
        }
        else {
            return 0;
        }
    }

    public int getScrollableBlockIncrement(Rectangle visibleRect,
            int orientation, int direction) {
        return orientation == SwingConstants.HORIZONTAL ? visibleRect.width
                : visibleRect.height;
    }

    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    public boolean getScrollableTracksViewportHeight() {
        if (getComponentCount() == 0) {
            return true;
        }
        if (getParent() instanceof JViewport) {
            return (((JViewport)getParent()).getHeight() > getPreferredSize().height);
        }
        return false;
    }
        
    /**
     * Returns the selected cell's index
     * @return an <code>int</code> num representing the selected cell's index
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }
    
    /**
     * Returns the total number of cells
     * in this list
     * @return the total number of cells
     */
    public int getCellCount() {
        return cellCount;
    }

    public void addCell(NoteListCell cell) {
        super.add(cell);
        cell.setIndexInList(getCellCount());
        cellCount++;
        if (getCellCount() == 1) {
            cell.setSelected(true);
            selectedIndex = 0;
        }
    }
    
    //XXX should I throw a TooManyListenersException?
    public void addNoteListEventListener(NoteListEventListener l) {
        if (listListener == null) {
            this.listListener = l;
        }
    }
    
    public void removeListEventListener() {
        this.listenerList = null;
    }
    
    /**
     * Selects or deselects a cell in this
     * list according to the <code>boolean</code>
     * value <code>b</code>.
     * @param index the index of the cell to be operated
     * @param b true for selecting, while false for deselecting
     */
    public void select(int index, boolean b) {
        if (index != getSelectedIndex()) {
            ((NoteListCell)getComponent(getSelectedIndex())).setSelected(false);
            ((NoteListCell)getComponent(index)).setSelected(b);
            selectedIndex = index;
        }
    }

    protected void fireLeftClick(Object source, Point p) {
        if (listListener != null) {
            listListener.leftClickOnCell(new NoteListEvent(
                    source, p));
        }
    }

    protected void fireRightClick(Object source, Point p) {
        if (listListener != null) {
            listListener.rightClickOnCell(new NoteListEvent(
                    source, p));
        }
    }

    protected void fireDoubleClick(Object source, Point p) {
        if (listListener != null) {
            listListener.doubleClickOnCell(new NoteListEvent(
                    source, p));
        }
    }
    
    protected void fireCursorEnter(Object source, Point p) {
        if (listListener != null) {
            listListener.cursorEnterCell(new NoteListEvent(
                    source, p));
        }
    }

    protected void fireCursorExit(Object source, Point p) {
        if (listListener != null) {
            listListener.cursorExitCell(new NoteListEvent(
                    source, p));
        }
    }
}
