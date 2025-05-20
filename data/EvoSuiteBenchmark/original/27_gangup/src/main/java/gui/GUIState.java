/* $Id: GUIState.java,v 1.5 2004/05/01 22:12:42 emill Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Emil Lundström <emill@kth.se>
 * @version: $Revision: 1.5 $
 */

package gui;

import java.util.Observable;
import java.util.Observer;
import state.*;

/**
 * The GUI state. Extends observable and fires a notifyObserver with
 * the selected object as arg when a new object is selected.
 */
public class GUIState extends Observable implements Observer {

    /** The currently selected player. */
    protected Player selected;

    /**
     * Sets a new player to be selected in the GUI.
     * @param p the player to select.
     */
    public void setSelected(Player p) {
	if (p != selected) {
	    selected = p;
	    setChanged();
	    notifyObservers(p);
	}
    }

    /**
     * Returns which player is selected in the GUI. 
     * @return which player is selected in the GUI. 
     */
    public Player getSelected() {
	return selected;
    }

    /**
     * If the selected player has died, reset it to null.
     * @param o the observable that triggered the change.
     * @param arg the optional argument passed to notifyObserver().
     */
    public void update(Observable o, Object arg) {
	if (selected != null) {
	    if (selected.isDead())
		setSelected(null);
	}
    }
}
