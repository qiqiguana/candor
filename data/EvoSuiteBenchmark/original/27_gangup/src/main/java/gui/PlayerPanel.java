/* $Id: PlayerPanel.java,v 1.13 2004/05/05 21:34:07 emill Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 *
 * @author: Emil Lundström <emill@kth.se>
 * @version: $Revision: 1.13 $
 *
 */

package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.ListModel;

import java.awt.event.*;
import javax.swing.event.*;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;

import java.util.ResourceBundle;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import module.GUIModule;
import state.*;

/**
 * The player view panel.
 */
public class PlayerPanel extends JPanel implements Observer {

    /** Localized strings. */
    protected ResourceBundle locale;
    
    JLabel name;
    JLabel image;
    JLabel strength;
    
    JList playerlist;
    JButton inviteButton;
    JButton msgButton;
    JButton kickButton;

    GUIModule module;
    
    //DefaultListModel listModel;
    
    PlayerButtonListener ml = new PlayerButtonListener();
    GUIFrame frame;

    /** Creates a new PlayerPanel. */
    public PlayerPanel(GUIModule mod, GUIFrame frm) {

	frame = frm;
	locale = ResourceBundle.getBundle("gangup", Locale.getDefault());
	module = mod;
	
	JLabel pinfotop = new JLabel(locale.getString("_GUI_PLAYERINFO_TOPIC"));
	JLabel ginfotop = new JLabel(locale.getString("_GUI_GROUPINFO_TOPIC"));
	name = new JLabel(locale.getString("_GUI_NAME_LABEL") + ": ");
	strength = new JLabel(locale.getString("_GUI_STRENGTH_LABEL") + ": ");
	image = new JLabel(createImageIcon("dat/gfx/player/blank.jpg",
					   "Player picture"));

	msgButton = new JButton(locale.getString("_GUI_MESSAGE_BUTTON"));
	msgButton.setActionCommand("message");
	msgButton.addActionListener(ml);

	inviteButton = new JButton(locale.getString("_GUI_INVITE_BUTTON"));
	inviteButton.setActionCommand("invite");
	inviteButton.addActionListener(ml);

	kickButton = new JButton(locale.getString("_GUI_KICK_BUTTON"));
	kickButton.setActionCommand("kick");
	kickButton.addActionListener(ml);
	
	playerlist = new JList();
	JScrollPane playerscroll = new JScrollPane(playerlist);
	playerlist.setEnabled(false);

	GridBagLayout layout = new GridBagLayout();
	GridBagConstraints constraints = new GridBagConstraints();
	
	setLayout(layout);

	constraints.anchor = GridBagConstraints.NORTHWEST;
	constraints.weightx = 1.0;
	constraints.weighty = 0.0;
	constraints.insets = new Insets(6,6,0,6);
	constraints.fill = GridBagConstraints.NONE;
	constraints.gridwidth = GridBagConstraints.REMAINDER;

	layout.setConstraints(pinfotop,constraints);
	add(pinfotop);
	layout.setConstraints(image,constraints);
	add(image);
	layout.setConstraints(name,constraints);
	add(name);
	layout.setConstraints(strength,constraints);
	add(strength);
	

	layout.setConstraints(ginfotop,constraints);
	add(ginfotop);
	constraints.weighty = 1.0;
	constraints.fill = GridBagConstraints.BOTH;
	layout.setConstraints(playerscroll,constraints);
	playerscroll.setPreferredSize(new Dimension(100,180));
	add(playerscroll);

	constraints.weighty = 0.0;
	constraints.fill = GridBagConstraints.HORIZONTAL;
	layout.setConstraints(msgButton,constraints);
	add(msgButton);
	layout.setConstraints(inviteButton,constraints);
	add(inviteButton);
	constraints.insets = new Insets(6,6,6,6);
	layout.setConstraints(kickButton,constraints);
	add(kickButton);
	
	inviteButton.setEnabled(false);
	msgButton.setEnabled(false);
	kickButton.setEnabled(false);
    }
    
   
    /**
     * Called whenever an object this object observes calls its
     * notifyObservers. For this frame, this will be called whenever a
     * new played is selected, or if the currently selected player
     * dies.
     */
    public void update(Observable o, Object arg) {
	if (o instanceof GUIState) {
	    String newname = "";
	    int newstr = 0;
	    ImageIcon newpic = null;

	    if (arg != null && arg instanceof Player) {
		Player p = (Player) arg;
		newname = p.getName();
		newstr = p.getStrength();
		newpic = new
		    ImageIcon(module.getPicturePath(p.getPictureId()));

		if (frame.state.getGameState() == GameState.STATE_WAITING ||
		    frame.state.getGameState() == GameState.STATE_ENDED) {
		    msgButton.setEnabled(false);
		    inviteButton.setEnabled(false);
		    kickButton.setEnabled(false);
		}
		else if (p == frame.state.getMe()) {
		    msgButton.setEnabled(false);
		    inviteButton.setEnabled(false);
		    kickButton.setEnabled(false);
		}
		else {
		    if (frame.state.getMe().getSubparty().contains(p)) {
			kickButton.setEnabled(true);
		    } else {
			kickButton.setEnabled(false);
		    }
		    msgButton.setEnabled(true);
		    inviteButton.setEnabled(true);
		}
	    }
	    name.setText(locale.getString("_GUI_NAME_LABEL")
			 + ": " + newname);
	    strength.setText(locale.getString("_GUI_STRENGTH_LABEL")
			     + ": " + newstr);
	    if (newpic != null)
		image.setIcon(newpic);
	    
	}
    }

    /**
     * As described in Java API Tutorial: How to Use Icons.
     */
    protected static ImageIcon createImageIcon(String path,
					       String description) {
	java.net.URL imgURL = ClassLoader.getSystemResource(path);
	if (imgURL != null) {
	    return new ImageIcon(imgURL, description);
	} else {
	    System.err.println("Player picture not found.");
	    return null;
	}
}


    /**
     * Listener for the two buttons beneath the player info.
     */
    private class PlayerButtonListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    if (e.getActionCommand().equals("message")) {
		frame.chat.addTab(frame.guistate.getSelected());
	    } else if (e.getActionCommand().equals("invite")) {
		module.sendAction(e.getActionCommand(),
				  frame.guistate.getSelected());
	    }
	    else if (e.getActionCommand().equals("kick")) {
		module.sendAction(e.getActionCommand(),
				  frame.guistate.getSelected());
	    }
	}
    }
}
