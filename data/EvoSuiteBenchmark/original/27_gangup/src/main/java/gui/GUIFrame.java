/* $Id: GUIFrame.java,v 1.16 2004/05/05 12:04:40 emill Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Emil Lundström <emill@kth.se>
 * @version: $Revision: 1.16 $
 *
 */


package gui;


//import gui.gl.GLWorldPanel;
import module.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.border.*;

import java.util.ResourceBundle;
import java.util.Locale;
import state.*;
import gui.gl.*;



/**
 * The GUI frame.
 */
public class GUIFrame extends JFrame {
    
    
    /** The parent GUI module. */
    GUIModule parent;
	
    /** The audiomanager **/
    AudioManager am;
    

    /** The chat panel. */
    ChatPanel chat;

    /** The world view panel. */
    WorldGLPanel world;

    /** The Player view panel. */
    PlayerPanel player;

    /** The Group view panel. */
    GroupPanel group;

    /** Client copy of the game state */
    GameState state;

    /** The guistate information, contains selected player info. */
    GUIState guistate;

    private MenuBarListener menubarlistener = new MenuBarListener();

    protected ResourceBundle locale = ResourceBundle.
	getBundle("gangup", Locale.getDefault());
    
    /**
     * Initiates a new GUIFrame.
     */
    public GUIFrame(GUIModule mod) {
	super("Gangup or: How I learned to stop worrying and love the bomb");
	Container c = getContentPane();
	parent = mod;

	/* initialize the different panels */
	chat   = new ChatPanel(mod);
	player = new PlayerPanel(mod,this);
	group  = new GroupPanel(mod,this);
	world  = new WorldGLPanel(mod,this);
	
	player.setPreferredSize(new Dimension(120,0));
	group.setPreferredSize(new Dimension(120,0));
	
	Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
	chat.setBorder(border);
	world.setBorder(border);
	player.setBorder(border);
	group.setBorder(border);

	c.setLayout(new BorderLayout());
	JSplitPane split = 
	    new JSplitPane(JSplitPane.VERTICAL_SPLIT,world,chat);
	// give most extra space to the world view
	//split.setResizeWeight(0.8);
	c.add(split,BorderLayout.CENTER);
	c.add(player,BorderLayout.EAST);
	c.add(group,BorderLayout.WEST);

	JMenuBar menubar = new JMenuBar();
	JMenu mainmenu = new JMenu(locale.getString("_GUI_MENU_MAIN"));
	JMenuItem menuConnect = 
	    new JMenuItem(locale.getString("_GUI_MENU_CONNECT"));
	JMenuItem menuQuit = 
	    new JMenuItem(locale.getString("_GUI_MENU_QUIT"));
	JMenuItem menuDisconnect =
	    new JMenuItem(locale.getString("_GUI_MENU_DISCONNECT"));
	

	menuConnect.setActionCommand("connect");
	menuConnect.addActionListener(menubarlistener);
	mainmenu.add(menuConnect);

	menuDisconnect.setActionCommand("disconnect");
	menuDisconnect.addActionListener(menubarlistener);
	mainmenu.add(menuDisconnect);
	mainmenu.addSeparator();

	menuQuit.setActionCommand("quit");
	menuQuit.addActionListener(menubarlistener);
	mainmenu.add(menuQuit);

	menubar.add(mainmenu);
	setJMenuBar(menubar);

	guistate = new GUIGLState();
	guistate.addObserver(player);
	//guistate.addObserver(world);
	guistate.addObserver(group);

	
	//setDefaultCloseOperation(EXIT_ON_CLOSE);
	addWindowListener(new GUIFrameListener());
	setBounds(100,100,800,600);
	setResizable(true);
	setVisible(true);

	((GUIGLState)guistate).setXMR(world.startXithRenderer());

    }


    private void initAudio() {
	am = new AudioManager(true);
	if(!am.loadSample("dat/sfx/event.wav", "event", false)) {
	    System.out.println("Couldn't load event.wav");
	    am.setEnabled(false);
	}
	if(!am.loadSample("dat/sfx/adminmessage.wav", "admin", false)) {
	    System.out.println("Couldn't load adminmessage.wav");
	    am.setEnabled(false);
	}
	if(!am.loadSample("dat/sfx/error.wav", "error", false)) {
	    System.out.println("Couldn't load error.wav");
	    am.setEnabled(false);
	}
	if(!am.loadSample("dat/sfx/win.wav", "win", false)) {
	    System.out.println("Couldn't load win.wav");
	    am.setEnabled(false);
	}
	if(!am.loadSample("dat/sfx/fight.wav", "fight", false)) {
	    System.out.println("Couldn't load fight.wav");
	    am.setEnabled(false);
	}
	if(!am.loadSample("dat/sfx/gamestart.wav", "gamestart", false)) {
	    System.out.println("Couldn't load gamestart.wav");
	    am.setEnabled(false);
	}
    }


    
    /**
     * Displays a warning for the user. 
     *
     * @param msg The warning message.
     */
    public void displayWarning(String msg) {
	// FIXME: how should this message be displayed? showdialog?
	// FIXME: option to turn off warnings?
	am.play("event");
	JOptionPane.showMessageDialog(this,
				      msg,
				      locale.getString("_WARNING_TITLE"),
				      JOptionPane.WARNING_MESSAGE);
    }


    /**
     * Opens up a dialog asking the user if he wants to join another
     * user.
     */
    public void displayInvite(Player actor) {
	Object[] options = {locale.getString("_GUI_INVITE_BTN_ACCEPT"),
			    locale.getString("_GUI_INVITE_BTN_REJECT")};
	am.play("event");
	int n = JOptionPane.showOptionDialog(this,
	   String.format(locale.getString("_GUI_INVITE_MSG"),actor.getName()),
			 locale.getString("_GUI_INVITE_TOPIC"),
			 JOptionPane.YES_NO_OPTION,    
			 JOptionPane.QUESTION_MESSAGE,
			 null,
		         options,
			 options[1]);
	if (n == JOptionPane.YES_OPTION) {
	    parent.sendAction("join_agree",actor);
	}
    }

    /**
     * Opens up a dialog asking the user if he wants to allow another
     * user to join him.
     */
    public void displayApply(Player actor) {
	am.play("event");
	Object[] options = {locale.getString("_GUI_APPLY_BTN_ACCEPT"),
			    locale.getString("_GUI_APPLY_BTN_REJECT")};
	int n = JOptionPane.showOptionDialog(this,
	   String.format(locale.getString("_GUI_APPLY_MSG"),actor.getName()),
			 locale.getString("_GUI_APPLY_TOPIC"),
			 JOptionPane.YES_NO_OPTION,
			 JOptionPane.QUESTION_MESSAGE,
			 null,
			 options,
			 options[1]);
	if (n == JOptionPane.YES_OPTION) {
	    parent.sendAction("join_allow",actor);
	}
    }
    
    /**
     * Displays an error for the user. 
     *
     * @param msg The warning message.
     */
    public void displayError(String msg) {
	// FIXME: how should this message be displayed? showdialog?
	am.play("error");
	JOptionPane.showMessageDialog(this,
			msg,
			locale.getString("_ERROR_TITLE"),
			JOptionPane.ERROR_MESSAGE);
    }

    
    /**
     * Displays a fatal error for the user.
     *
     * @param msg The warning message.
     */
    public void displayFatal(String msg) {
	am.play("error");
	// FIXME: how should this message be displayed? showdialog?
	// FIXME: quit the gui here or let the underlying module do that?
	JOptionPane.showMessageDialog(this,
			 msg + "\n\n" +
			 locale.getString("_FATAL_ERROR_CLOSE_NOTICE"),
			 locale.getString("_FATAL_ERROR_TITLE"),
			 JOptionPane.ERROR_MESSAGE);
	world.getXMR().dispose();
	am.killAllData();
	System.exit(0);
    }


    /**
     * Sets the gamestate for this GUI to use.
     */
    public void setGameState(GameState state) {
	this.state = state;
	PlayerTreeModel ptm = new PlayerTreeModel();
	PlayerListModel plm = new PlayerListModel();
	ptm.setState(state);
	plm.setState(state);
	group.ptr.setState(state);
	group.tree.setModel(ptm);
	player.playerlist.setModel(plm);
	state.addObserver(ptm);
	state.addObserver(plm);
	state.addObserver(guistate);
	state.addObserver(group);
	guistate.addObserver(plm);
	//guistate.addObserver(world);
	state.reload();
    }

    /**
     * Adds a chat message to the the appropriate chat tab in the chat
     * panel.
     */
    public void addChatMessage(Object id, String message) {
	String[] tmp = new String[2]; 
	tmp = message.split(" ",2); 
	if (tmp[0].equals("<"+locale.getString("_ADMIN_NAME")+">")) {  
	    am.play("admin");
	}
	tmp = message.split(" ",1); 
	if (message.contains(locale.getString("_GAMESTART_MESSAGE"))) {  
	    am.play("gamestart");
	}
	chat.writeTo(id,message);
    }

    public void startFight(Player actor, Player target) {
	am.play("fight");
	world.getXMR().startFight(actor,target);
    }

    public void disableButton(int index) {
	group.buttons[index].setEnabled(false);
    }

    public void enableButton(int index) {
	group.buttons[index].setEnabled(true);
    }



    private class MenuBarListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    if (e.getActionCommand().equals("connect")) {
		parent.sendConnect();
	    } else if (e.getActionCommand().equals("quit")) {
		world.getXMR().dispose();
		am.killAllData();
		System.exit(0);
	    } else if (e.getActionCommand().equals("disconnect")) {
		parent.sendDisconnect();
	    }
	}
    }

    private class GUIFrameListener extends WindowAdapter {
	public void windowClosing(WindowEvent e) {
	    world.getXMR().dispose();
	    am.killAllData();
	    System.exit(0);
	}

	public void windowOpened(WindowEvent e) {
	    initAudio();
	}
    }
}
