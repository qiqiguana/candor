/* $Id: GroupPanel.java,v 1.11 2004/05/05 20:07:00 emill Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 *
 * @author: Emil Lundström <emill@kth.se>
 * @version: $Revision: 1.11 $
 *
 */

package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JButton;

import javax.swing.tree.*;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.event.*;
import javax.swing.event.*;

import java.util.ResourceBundle;
import java.util.Locale;
import java.util.Observer;
import java.util.Observable;

import state.*;

import module.GUIModule;

/**
 * The chat panel.
 */
public class GroupPanel extends JPanel implements Observer {

    /** Localized strings. */
    protected ResourceBundle locale;
    
    /** The tree component. */
    PlayerTree tree;
    
    /** Underlying GUI module. */
    GUIModule module;

    /** Parent GUI frame. */
    GUIFrame frame;

    /** The ActionListener for the Popup menu in the gang tree. */
    GroupButtonListener ml = new GroupButtonListener();

    GroupPanel gp  = this;

    PlayerTreeRenderer ptr;
    
    JButton[] buttons = new JButton[4];
    
    /** Creates a new GroupPanel. */
    public GroupPanel(GUIModule mod, GUIFrame frm) {
	frame = frm;
	module = mod;
	locale = ResourceBundle.getBundle("gangup", Locale.getDefault());
	JLabel grouptext = new JLabel(locale.getString("_GUI_GANGINFO_TOPIC"));
	tree = new PlayerTree(ptr = new PlayerTreeRenderer());
	JScrollPane groupscroll = new JScrollPane(tree);
	groupscroll.setPreferredSize(new Dimension(100,180));
	JLabel filler = new JLabel("");

	buttons[0]  = new JButton(locale.getString("_GUI_POPUP_JOIN"));
	buttons[1]  = new JButton(locale.getString("_GUI_POPUP_FIGHT"));
	buttons[2]  = new JButton(locale.getString("_GUI_POPUP_PART"));
	
	buttons[0].setEnabled(false);
	buttons[1].setEnabled(false);
	buttons[2].setEnabled(false);

	buttons[0].addActionListener(ml);
	buttons[1].addActionListener(ml);
	buttons[2].addActionListener(ml);
	
	buttons[0].setActionCommand("action join");
	buttons[1].setActionCommand("action fight");	
	buttons[2].setActionCommand("action part");
	
	
	GridBagLayout layout = new GridBagLayout();
	GridBagConstraints constraints = new GridBagConstraints();
	setLayout(layout);
	
	constraints.anchor = GridBagConstraints.NORTHWEST;
	constraints.weightx = 1.0;
	constraints.weighty = 0.0;
	constraints.insets = new Insets(6,6,0,6);
	constraints.fill = GridBagConstraints.BOTH;
	constraints.gridwidth = GridBagConstraints.REMAINDER;

	layout.setConstraints(grouptext,constraints);
	add(grouptext);
	constraints.weighty = 1.0;
	layout.setConstraints(groupscroll,constraints);
	add(groupscroll);
	constraints.weighty = 0.0;
	layout.setConstraints(buttons[0],constraints);
	add(buttons[0]);
	layout.setConstraints(buttons[2],constraints);
	add(buttons[2]);
	constraints.insets = new Insets(6,6,6,6);
	layout.setConstraints(buttons[1],constraints);
	add(buttons[1]);

	tree.addTreeSelectionListener(new PlayerTreeListener());
	tree.setModel(new PlayerTreeModel());
    }

    /**
     * Part of observer interface. When the structure of GameState is
     * altered, update which buttons are enabled/disabled.
     */
    public void update(Observable o, Object arg) {
	if (o instanceof GameState) {
	    
	    if (frame.state.getMe() != null) {
		
		if (frame.state.getMe().isDead() ||
		    frame.state.getGameState() == GameState.STATE_WAITING) {
		    buttons[1].setEnabled(false);
		    buttons[0].setEnabled(false);
		    buttons[2].setEnabled(false);
		}
		else if (frame.state.getMe().isBoss()) {
		    buttons[0].setEnabled(true);
		    
		    if (frame.state.getGameState() ==
			GameState.STATE_WARMUP) {
			buttons[1].setEnabled(false);
		    }
		    else if (frame.state.getGameState() ==
			GameState.STATE_PLAYING) {
			buttons[1].setEnabled(true);
		    }
		    buttons[2].setEnabled(false);
		}
		else {
		    buttons[1].setEnabled(false);
		    buttons[0].setEnabled(false);
		    buttons[2].setEnabled(true);
		}
	    }
	}
	if (o instanceof GUIState) {
	    //if (tree.getLastSelectedPathComponent() !=
	    //frame.guistate.getSelected()) {
		// FIXME: expand entire path - this doesn't work correctly
		//tree.setSelectionPath(new TreePath(frame.guistate.getSelected()));
	    //}
	}
    }
    
    
    private class PlayerTreeListener implements TreeSelectionListener {
	public void valueChanged(TreeSelectionEvent e) {
	    Player p = (Player)tree.getLastSelectedPathComponent();
	    frame.guistate.setSelected(p);
	    if (p != null)
		System.err.println(p + " " + p.isBoss());
	}
    }

    private class GroupButtonListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    String actcmd[] = e.getActionCommand().split(" ");
	    if (frame.guistate.getSelected() != null ||
		actcmd[1].equals("part")) {
		if (actcmd[0].equals("action")) {
		    module.sendAction(actcmd[1],
				      frame.guistate.getSelected());
		}
	    }
	}
    }
}
