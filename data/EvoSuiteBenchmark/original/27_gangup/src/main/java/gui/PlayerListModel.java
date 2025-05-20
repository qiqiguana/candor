/* $Id: PlayerListModel.java,v 1.3 2004/04/01 11:42:45 emill Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Emil Lundström <emill@kth.se>
 * @version: $Revision: 1.3 $
 *
 */

package gui;

import javax.swing.ListModel;
import javax.swing.event.*;
import java.util.Observer;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Observer;
import java.util.Observable;
import state.*;

/**
 * Provides a ListModel representingen the GameState, to used in
 * the PlayerPanel.
 */
public class PlayerListModel implements ListModel, Observer {
    
    /** The underlying GameState.*/
    private GameState state;
    
    private LinkedList<Party> selectedlist;
    
    private Vector<ListDataListener> listDataListeners;

    /**
     * Creates a new PlayerListModel.
     */
    public PlayerListModel() {
	listDataListeners = new Vector<ListDataListener>();
    }


    /**
     * Sets the gamestate for this treemodel.
     */
    public void setState(GameState s) {
	state = s;
	reload();
    }
    
    /**
     * Update defined in Observer interface.
     *
     * @param o The observable object.
     * @param arg The argument passed to notifyObservers.
     */
    public void update(Observable o, Object arg) {
	if (o instanceof GUIState) {
	    reload( ((GUIState)o).getSelected() );
	}
	if (!(arg instanceof Player)) {
	    reload();
	}
    }
    
    
    /**
     * Call this when the tree structure has been changed.
     */
    protected void reload() {
	ListDataEvent e =
	    new ListDataEvent(this,
			      ListDataEvent.CONTENTS_CHANGED,
			      0,
			      getSize());
	for (ListDataListener l : listDataListeners) {
	    l.contentsChanged(e);
	}
    }
    
    /**
     * Called whenever structure has been completely changed,
     * usually happens when a new player has been selected.
     */
    protected void reload(Player selected) {
	if (selected == null) {
	    selectedlist = null;
	} else {
	    selectedlist = selected.getSubparty();
	}
	reload();
    }
    
    public void addListDataListener(ListDataListener l) {
	listDataListeners.addElement(l);
    }
    
    public void removeListDataListener(ListDataListener l) {
	listDataListeners.removeElement(l);
    }
    
    public int getSize() {
	if (selectedlist == null) {
	    return 0;
	}
	return selectedlist.size();
    }
    
    public Object getElementAt(int index) {
	if (selectedlist == null) {
	    return null;
	}
	return selectedlist.get(index);
    }
}


