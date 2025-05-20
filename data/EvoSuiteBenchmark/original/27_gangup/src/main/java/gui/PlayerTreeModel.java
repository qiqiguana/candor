/* $Id: PlayerTreeModel.java,v 1.3 2004/05/02 01:52:45 emill Exp $
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

import javax.swing.tree.*;
import javax.swing.event.*;
import state.*;
import java.util.Vector;
import java.util.LinkedList;
import java.util.Observer;
import java.util.Observable;


/**
 * Provides a TreeModel representingen the GameState, to used in
 * the GroupPanel. 
 */
public class PlayerTreeModel implements TreeModel, Observer {
    
    private GameState state;
    private Party root = new Party(); // basically a place holder
    private Vector<TreeModelListener> treeModelListeners;
    
    /**
     * Creates a new TreeModel for the underlying GameState.
     *
     * @param state The underlying state.
     * @param root The initial root.
     */
    public PlayerTreeModel() {
	treeModelListeners = new Vector<TreeModelListener>();
    }

    /**
     * Sets the state for this treemodel.
     *
     * @param state the game state.
     * @see GameState
     */
    public void setState(GameState s) {
	state = s;
    }
    
    
    /**
     * Update defined in Observer interface.
     *
     * @param o The observable object.
     * @param arg The argument passed to notifyObservers.
     */
    public void update(Observable o, Object arg) {
	System.err.println("reloading grouptree");
	reload();
	if (state != o) 
	    System.err.println("ALERT! state != o in PTM.update()");
    }
    
    /**
     * Call this whenever the tree structure of GameState has been
     * changed.
     *
     * @param changed The Player from which the change
     * started. To be sure, use the root :).
     */
    protected void fireTreeStructureChanged(Party changed) {
	TreeModelEvent e =
	    new TreeModelEvent(this, new Object[] {changed});
	for (TreeModelListener l : treeModelListeners) {
	    l.treeStructureChanged(e);
	}
    }
    
    /**
	 * Call this when the tree structure has been changed.
	 */ 
    public void reload() {
	fireTreeStructureChanged(root);
    }
    
    
    
    // --- TreeModel interface ---
    public void addTreeModelListener(TreeModelListener l) {
	treeModelListeners.addElement(l);
    }
    
    public void removeTreeModelListener(TreeModelListener l) {
	treeModelListeners.removeElement(l);
    }
    
    public Object getChild(Object parent, int index) {
	// give the root special treatment
	if (state == null)
	    return null;
	if (parent == root) {
	    int i = 0;
	    for (Player player : state.players()) {
		if (player != null && player.isBoss() && i++ == index) {
		    return player;
		}
	    }
	} else {
	    LinkedList<Party> l = ((Party)parent).getSubparty();
	    l.remove(parent);
	    return l.get(index);
	}
	return null;
    }

    public int getChildCount(Object parent) {
	if (state == null)
	    return 0;
	// give the root special treatment
	if (parent == root) {
	    int c = 0;
	    for (Player player : state.players()) {
		if (player != null && player.isBoss()) {
		    c++;
		}
	    }
	    return c;
	} else {
	    LinkedList<Party> l = ((Party)parent).getSubparty();
	    l.remove(parent);
	    return l.size();
	}
    }
    
    /**
     * Note: Does not follow standard. Should return -1 if either
     * child or parent is not part of this model, which is never
     * checked.
     */
    public int getIndexOfChild(Object parent, Object child) {
	if (state == null || parent == null || child == null) return -1;
	if (parent == root) {
	    int i = 0;
	    for (Player player : state.players()) {
		if (player != null && player.isBoss()) {
		    if (player == child) return i;
		    i++;
		}
	    }
	} else {
	    LinkedList<Party> l = ((Party)parent).getSubparty();
	    l.remove(parent);
	    return l.indexOf(child);
	}
	return -1;
    }
    
    public Object getRoot() {
	return root;
    }
    
    public boolean isLeaf(Object node) {
	Party p = (Party) node;
	    return (!p.isBoss() || (p.head == null && p != root));
    }
    
    /**
     * Messaged when the user has altered the value for the item
     * identified by path to newValue.  Not used by this model.
     *
     * FIX: Ripped from JAVA API EXAMPLE.
     */
    public void valueForPathChanged(TreePath path, Object newValue) {
	    System.out.println("*** valueForPathChanged : "
			       + path + " --> " + newValue);
    }
}
