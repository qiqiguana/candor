/* $Id: PlayerTree.java,v 1.3 2004/04/01 10:34:09 emill Exp $
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

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * The graphical component that contains the player list.
 */
public class PlayerTree extends JTree {

    public PlayerTree(TreeCellRenderer renderer) {
        getSelectionModel().setSelectionMode(
                TreeSelectionModel.SINGLE_TREE_SELECTION);
        setCellRenderer(renderer);
	setRootVisible(false);
	putClientProperty("JTree.lineStyle", "None");
	setToggleClickCount(1);
	setExpandsSelectedPaths(true);
    }
}
