/* $Id: GUIModule.java,v 1.16 2004/05/05 20:07:00 emill Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Emil Lundström <emill@kth.se>
 * @version: $Revision: 1.16 $
 *
 */

package module;

// Skin Look and Feel support - http://www.l2fprod.com/
import com.l2fprod.gui.plaf.skin.SkinLookAndFeel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JTree;
import javax.swing.JFrame;
import javax.swing.ListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.util.Vector;
import java.util.LinkedList;
import java.util.Observer;

import java.util.ResourceBundle;
import java.util.Locale;

import java.io.*;

import gui.GUIFrame;
import state.*;

@cvs(file     = "$RCSfile: GUIModule.java,v $",
     revision = "$Revision: 1.16 $",
     date     = "$Date: 2004/05/05 20:07:00 $",
     author   = "$Author: emill $",
     tag      = "$Name:  $",
     build    = "")

@mod(name     = "GUIModule",
     topics   = "FROM_NETWORK ERROR WARNING FATAL CONNECTED DROPPED", 
     cmds     = "DUMP",
     desc     = "This is the client GUI module for the game.")

/**
 * This is an example of a simple module. It's purpose is to demonstrate
 * the basic structure of a module.
 */
public class GUIModule extends AbstractModule {

    /** The JFrame in which the GUI is drawn. */
    private GUIFrame frame;

    private GameState state;

    private String host;
    private String port;

    private String[] picturepaths;

    /** Get a reference to the ActionFactory. */
    private ActionFactory af = ActionFactory.getInstance();

    public final String GENERAL_MESSAGE =
	locale.getString("_GUI_CHAT_GENERALTAB");
    public final String GROUP_MESSAGE =
	locale.getString("_GUI_CHAT_GANGTAB");
    
    /**
     * Creates a new instance of this module. This constructor is usually
     * called from the kernel when it wishes to load a module dynamically.
     *
     * @throws ModuleRegisterException If you have a broken Java interpreter.
     */
    public GUIModule() throws ModuleRegisterException {}

    /**
     * Initialization method for modules. Using init() is recommended
     * instead of overloading the constructor.
     */
    protected void init() {
	try {
	    host = (String)request
		("CONFIG","GET ClientNetwork host").body;
	    port = (String)request
		("CONFIG","GET ClientNetwork port").body;
	} catch (MessageTimeoutException e) {
	    sendMessage(new Message("WARNING", locale.
			    getString("_CLIENT_CON_CFG_FAIL")));
	    e.printStackTrace(System.err);
	}
	
       	try {
	    String s = (String) request("CONFIG","GET GUI skinlf_enable").body;
	    if (s.equals("1")) {
		s = (String) request("CONFIG","GET GUI skinlf_themepack").body;
		SkinLookAndFeel.setSkin(SkinLookAndFeel.loadThemePack(s));
		UIManager.setLookAndFeel(new SkinLookAndFeel());
	    }

	} catch (NoClassDefFoundError e) {
	    sendMessage(new Message("WARNING", locale.
			  getString("_GUI_WARNING_SKINLF_NOT_FOUND")));
	} catch (MessageTimeoutException e) {
	    sendMessage(new Message("WARNING", locale.
			  getString("_GUI_WARNING_CFG_TIMEOUT")));
	} catch (UnsupportedLookAndFeelException e) {
	    sendMessage(new Message("WARNING", locale.
			  getString("_GUI_WARNING_UNSUPPORTED_LF")));
	} catch (Exception e) {
	    e.printStackTrace();
	    System.err.print(e.toString());
	    sendMessage(new Message("WARNING", locale.
			   getString("_GUI_WARNING_SKINLF_EXCEPTION")
			   + e.toString()));
	}

	try {

	    String datafile = (String)request("CONFIG",
			  "GET PlayerData playerdatafile").body;
	    BufferedReader br = new BufferedReader(new FileReader(datafile));

	    int c = Integer.parseInt(br.readLine());
	    for (int i=0; i < c; i++) {
		// throw away player names, we dont care
		br.readLine();
	    }
	    c = Integer.parseInt(br.readLine());
	    
	    picturepaths = new String[c];
	    
	    for (int i=0; i < c; i++) {
		// read picture paths
		picturepaths[i] = br.readLine();
		System.err.println(picturepaths[i]);
	    }
	    
	} catch (Exception e) {
	    e.printStackTrace(System.err);
	}

	frame = new GUIFrame(this);
	state = new GameState();
	frame.setGameState(state);
    }

    /**
     * Method to run recurrently in the loop of run(). This method
     * should never be blocking, lest the module will not be able to
     * process any messages.
     */
    protected void step() {}

    /**
     * Exit method for modules. This method is invoked after a call to
     * the @see exit() method. Modules should overload this method with
     * code to do a proper exit.
     */
    protected void free() {}

    /**
     * This method is invoked once for every Message in the input queue.
     * Normally this is where module specific message handling is performed.
     * 
     * @param msg The Message that is to be processed.
     */
    protected void processMessage(Message msg) {

	System.err.println(msg);
	
	if (msg.header.equals("WARNING")) {
	    frame.displayWarning((String)msg.body);
	}

	else if (msg.header.equals("ERROR")) {
	    frame.displayError((String)msg.body);
	}

	else if (msg.header.equals("FATAL")) {
	    frame.displayFatal((String)msg.body);
	}

	else if (msg.header.equals("FROM_NETWORK")) {
	    decodeNetworkMessage((Packable)msg.body);
	}

	else if (msg.header.equals("CONNECTED")) {
	    frame.addChatMessage(GENERAL_MESSAGE,
				 locale.getString("_CONNECTED_MESSAGE"));
	}

	else if (msg.header.equals("DROPPED")) {
	    frame.addChatMessage(GENERAL_MESSAGE,
				 locale.getString("_DROPPED_MESSAGE"));
	}
	
	/* process module specific messages */

	if (msg.body.equals("DUMP")) {
	    System.err.println(modinfo.revision);
	}
    }

    /**
     * Decodes a network message and tries to apply its meaning to the
     * GUI.
     *
     * @param p The packable from the network.
     */
    protected void decodeNetworkMessage(Packable p) {
	try {
	if (p.type() == Packable.GAME_STATE) {

	    state = (GameState) p;

	    System.err.println("state received");
	    try {
		int id = (Integer)request("CLIENT","ID").body;
		state.setMe(state.player(id));
	    } catch (MessageTimeoutException e) {
		sendMessage(new Message("WARNING",
		       locale.getString("_ID_NOT_AVAILABLE")));
	    }
	    frame.setGameState(state);

	    // to receive players pre-game
	} else if (p.type() == Packable.PLAYER) {
	    if (state == null) return;
	    Player s = (Player)p;
	    // means this player disconnected, ugly way
	    if (s.getName() == "DROPPED") {
		
	    } else {
		state.addPlayer(s);
		frame.addChatMessage(GENERAL_MESSAGE,
		   String.format(locale.getString("_PLAYER_JOINED_MESSAGE"),
				 s.getName()));
	    }
	    
	} else if (p.type() == Packable.GAME_EVENT) {

	    if (state == null) return;
	    
	    GameEvent g = (GameEvent)p;
	    switch (g.getEventType()) {

	    case GameEvent.WARMUP:
		state.setGameState(GameState.STATE_WARMUP);
		state.setGamestart(System.currentTimeMillis());
		frame.addChatMessage(GENERAL_MESSAGE,
		       locale.getString("_WARMUP_MESSAGE"));
		break; 
		
	    case GameEvent.START:
		state.setGameState(GameState.STATE_PLAYING);
		state.setGamestart(System.currentTimeMillis());
		frame.addChatMessage(GENERAL_MESSAGE,
		       locale.getString("_GAMESTART_MESSAGE"));
		break;
		
	    case GameEvent.JOIN:
		state.join(state.player(g.getTarget()),
			   state.player(g.getActor()));
		frame.addChatMessage(GENERAL_MESSAGE,
			  String.format(locale.getString("_JOIN_MESSAGE"),
					state.player(g.getActor()),
					state.player(g.getTarget())));

		break;
	    case GameEvent.PART:
		state.part(state.player(g.getTarget()),
			   state.player(g.getActor()));

		frame.addChatMessage(GENERAL_MESSAGE,
			  String.format(locale.getString("_PART_MESSAGE"),
					state.player(g.getActor()),
					state.player(g.getTarget())));
		break;
	    case GameEvent.KICK:
		state.part(state.player(g.getActor()),
			   state.player(g.getTarget()));

		frame.addChatMessage(GENERAL_MESSAGE,
			  String.format(locale.getString("_KICK_MESSAGE"),
					state.player(g.getTarget()),
					state.player(g.getActor())));
		break;
	    case GameEvent.FIGHT:
		LinkedList<Party> losers =
		    state.player(g.getTarget()).getSubparty();
		for (Party loser : losers) {
		    state.setDead((Player)loser);
		}
		frame.addChatMessage(GENERAL_MESSAGE,
			  String.format(locale.getString("_BATTLE_MESSAGE"),
					state.player(g.getActor()),
					state.player(g.getTarget())));
		frame.startFight(state.player(g.getActor()),
				 state.player(g.getTarget()));
		break;
		
	    case GameEvent.INVITE:
		// redudant check if gamemodule works correctly
		if (state.player(g.getTarget()) == state.getMe()) {
		    frame.displayInvite(state.player(g.getActor()));
		}
		break;

	    case GameEvent.APPLY:
		// redudant check if gamemodule works correctly
		if (state.player(g.getTarget()) == state.getMe()) {
		    frame.displayApply(state.player(g.getActor()));
		}
		break;
		
		//case GameEvent.MOVE:
		//state.move(state.player(g.getActor()),g.getTarget());
		//break;

	    case GameEvent.DROP:
		frame.addChatMessage(GENERAL_MESSAGE,
		   String.format(locale.getString("_PLAYER_LEFT_MESSAGE"),
				 state.player(g.getActor()).getName()));
		state.removePlayer(state.player(g.getActor()));
		break;

	    case GameEvent.END:
		state.setGamestart(0);
		state.setGameState(GameState.STATE_ENDED);
		frame.addChatMessage(GENERAL_MESSAGE,
		       locale.getString("_GAMEEND_MESSAGE"));
		break;
	    }

	    
	} else if (p.type() == Packable.TEXT_MESSAGE) {

	    if (state == null) return;
	    
	    TextMessage t = (TextMessage) p;
	    String text;
	    if (t.getActor() == -1) {
		text = "<" + locale.getString("_ADMIN_NAME")
		    + "> " +t.getText(); 
	    } else {
		text = "<" + state.player(t.getActor())
		    + "> " + t.getText();
	    }
	    
	    switch (t.getMessageType()) {

	    case TextMessage.GENERAL:
		frame.addChatMessage(GENERAL_MESSAGE,text);
		break;
	    case TextMessage.GROUP:
		frame.addChatMessage(GROUP_MESSAGE,text);
		break;
	    case TextMessage.PRIVATE:
		if (state.player(t.getActor()) == state.getMe()) {
		    frame.addChatMessage(state.player(t.getTarget()),text);
		} else {
		    frame.addChatMessage(state.player(t.getActor()),text);
		}
		break;
	    }
	}
	} catch (Exception e) {
	    e.printStackTrace(System.err);
	}
    }
    

    public void sendConnect() {
	sendMessage(new Message("CONNECT",host + " " + port));
    }

    public void sendDisconnect() {
	sendMessage(new Message("DISCONNECT",null));
    }

    public void sendMoveAction(int direction) {
	Action a = af.createAction(Action.ACTION_MOVE,-1,direction);
	sendMessage(new Message("NETWORK", a));
    }

    /**
     * Sends 
     * @param x
     * @param y
     * @param z
     */
    public void sendMoveAction(byte x, byte y, byte z) {
	sendMessage(
	    new Message("NETWORK", 
			af.createMoveAction(-1,x,y,z)));
    }

    public void sendAction(String action, Object target) {
	if (state == null ||
	    state.getGameState() == GameState.STATE_WAITING)
	    return;
	Player targ = (Player)target;
	if (action.equals("join")) {
	    sendMessage(new Message("NETWORK", af.createAction(Action.ACTION_JOIN_APPLY,-1,targ.getId())));
	}
	else if (action.equals("part")) {
	    sendMessage(new Message("NETWORK", new
		Action(Action.ACTION_PART,-1,-1)));
	}
	else if (action.equals("fight")) {
	    sendMessage(new Message("NETWORK", new
		Action(Action.ACTION_ATTACK,-1,targ.getId())));
	}
	else if (action.equals("invite")) {
	    sendMessage(new Message("NETWORK", new
		Action(Action.ACTION_JOIN_INVITE,-1,
			     targ.getId())));
	}
	else if (action.equals("join_agree")) {
	    sendMessage(new Message("NETWORK", new
		Action(Action.ACTION_JOIN_AGREE,-1,
		       targ.getId())));
	}
	else if (action.equals("join_allow")) {
	    sendMessage(new Message("NETWORK", new
		Action(Action.ACTION_JOIN_ALLOW,-1,
		       targ.getId())));
	}
	else if (action.equals("kick")) {
	    sendMessage(new Message("NETWORK", new
		Action(Action.ACTION_KICK,-1,
		       targ.getId())));
	}
    }

    /**
     * Returns the picture path correspoding to a picture id.
     */
    public String getPicturePath(int id) {
	return picturepaths[id];
    }

    /**
     * Sends a message to the network module.
     * 
     * @param message The message to be sent.
     * @param target The target for this textmessage.
     */
    public void sendTextMessage(String message, Object target) {
       
	
	if (target == GENERAL_MESSAGE) {
	    
	    sendMessage(new Message("NETWORK", new
		TextMessage(0,-1,TextMessage.GENERAL,message)));
	    
	} else if (target == GROUP_MESSAGE) {

	    sendMessage(new Message("NETWORK", new
		TextMessage(0,-1,TextMessage.GROUP,message)));
	    
	} else {

	    Player targ = (Player)target;
	    sendMessage(new Message("NETWORK", new
		TextMessage(0,targ.getId(),TextMessage.PRIVATE,message)));
	}
    }
}
