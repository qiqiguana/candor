/* $Id: PlayerTreeRenderer.java,v 1.4 2004/04/22 11:26:42 emill Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Emil Lundström <emill@kth.se>
 * @version: $Revision: 1.4 $
 *
 */

package gui;

import javax.swing.ListModel;
import java.util.Observer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.tree.*;
import javax.swing.JTree;
import java.awt.Color;
import state.*;


/**
 * Provides a tree renderer to correctly set player icons in the gang
 * tree.
 */
public class PlayerTreeRenderer extends DefaultTreeCellRenderer {
    private Icon player_icon;
    private Icon player_bossicon;
    private Icon player_deadicon;

    private GameState state;
    
    /**
     * Creates a new PlayerTreeRenderer.
     */
    public PlayerTreeRenderer() {
	player_icon = new ImageIcon("dat/gfx/player_icon.png");
	player_bossicon = new ImageIcon("dat/gfx/player_bossicon.png");
	player_deadicon = new ImageIcon("dat/gfx/player_deadicon.png");
    }

    public void setState(GameState s) {
	state = s;
    }

    /**
     * Called by the JTree whenever a cell is to be rendered.
     * Modified to set icons correctly.
     */
    public Component getTreeCellRendererComponent(
			JTree tree,
			Object value,
			boolean sel,
			boolean expanded,
			boolean leaf,
                        int row,
                        boolean hasFocus) {

	super.getTreeCellRendererComponent(
		tree, value, sel,
		expanded, leaf, row,
		hasFocus);
	if (value instanceof Player) {
	    if (((Player)value).isDead()) {
		setIcon(player_deadicon);
	    } else if (((Player)value).isBoss()) {
		setIcon(player_bossicon);
	    } else {
		setIcon(player_icon);
	    }
	    if (state.getMe() == value) {
		setForeground(Color.BLUE);
	    }
	} else {
	    setIcon(null);
	}
	return this;
    }
}
