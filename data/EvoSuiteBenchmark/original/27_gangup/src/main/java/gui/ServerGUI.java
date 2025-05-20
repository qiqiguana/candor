/* $Id: ServerGUI.java,v 1.18 2004/05/05 21:37:58 njursten Exp $
 *
 * This software is hereby placed in the public domain. You are free to do
 * whatever you want with this software. It is distributed in hope that
 * someone will find it useful but without any warranties. 
 * 
 * @author: Joel Andersson <bja@kth.se>
 * @version: $Revision: 1.18 $
 *
 */

package gui;

import state.*;
import module.*;

// import message utility methods.
import static module.MessageFactory.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.io.*;
import java.util.*;

import java.lang.reflect.*;
import java.lang.annotation.*;


/****************************************************************************/
/*                                                                          */
/*                                                                          */
/*                                                                          */
/*                                                                          */
/****************************************************************************/

/**
 * This class provides the ...
 * 
 *
 */
public class ServerGUI extends JFrame implements ActionListener {

    private final JFrame frame;
    private final JFileChooser chooser;
    private final ModuleBrowserDialog browser;

    private Kernel kernel;
    private SnoopyModule snoopy;
    private ConfigModule config;

    private DebugPanel debug;
    private GamePanel game;
    private StatsWindow statswindow;

    /**
     * This class subscribes to every message topic, so it will
     * receive all messages sent with null recipient.
     */
    @module.mod(name="SnoopyModule")
    private class SnoopyModule extends AbstractModule {
	SnoopyModule(Kernel krn) 
	    throws ModuleRegisterException {
	    super(krn);
	}

	public void sendTextMessage(String topic, String data) {
	    try {
		createMessage(topic, data).send(this);
	    } catch (MessageDeliveryException e) {
		e.printStackTrace(System.err);
	    }
	}

	protected void processKernelMessage(Message m) 
	    throws MessageProcessingException {
	    try {
		debug.append(m);
		if (m.getBody().equals("REGISTERED")) {
		    subscribe("*");
		}
	    } catch (Exception e) {
		throw new MessageProcessingException(this, m, e);
	    }
	}

	protected void processMessage(Message m) {
	    debug.append(m);

	    if (m.getHeader().equals("WARNING")) {
		displayWarning((String)m.getBody());
	    }

	    if (m.getHeader().equals("ERROR")) {
		displayWarning((String)m.getBody());
		System.exit(1);
	    }
	}

	/**
	 * Displays a warning for the user.
	 * @param msg The warning message.
	 */
	private void displayWarning(String msg) {
	    // FIXME: how should this message be displayed? showdialog?
	    // FIXME: option to turn off warnings?
	    JOptionPane.showMessageDialog(
		frame, msg, locale.getString("_WARNING_TITLE"),
		JOptionPane.WARNING_MESSAGE);
	}

    };

    /**
     * Creates a new instance of the ServerGUI class.
     * @param fileName the configuration file to use as default.
     */
    public ServerGUI(String fileName) {
	super(fileName);

	frame = this;

	initModules(fileName);

	browser = new ModuleBrowserDialog(this, kernel);
	chooser = new JFileChooser();

	initGUI();
	
	autoLoadModules();
    }

    /**
     * initialize standard modules.
     *
     */
    private void initModules(String fileName) {

	try {
	    
	    kernel = new Kernel();
	    config = new ConfigModule(kernel);

	    /* Start running the config module. */

	    config.start();

	    /* Load the default system configuration */

	    config.read(fileName);

      /*} catch (ModuleException e) {*/
	} catch (Exception e) {

	    System.err.print("ServerGUI(): *** ERROR *** [X] ");
	    System.err.print("caught unknown exception\n");
	    e.printStackTrace(System.err);
	    System.exit(1);

	}
    }

    private void autoLoadModules() {
	    
	try {
	    
	    /* Find list of additional modules to load */

	    String modules = config.getCVar("Kernel", "autoload");

	    if (modules != null) {
		String[] mod = modules.split(",");
		for (int i=0; i<mod.length; i++) {
		    kernel.loadModule(mod[i]);
		}
	    }
	} catch (Exception e) {

	    System.err.print("ServerGUI(): *** ERROR *** [X] ");
	    System.err.print("caught unknown exception\n");
	    e.printStackTrace(System.err);
	    System.exit(1);

	}

    }


    private void initGUI() {

	Container c = getContentPane();

	setTitle("gangupd ($Revision: 1.18 $)");
	setBounds(100,100,400,400);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	c.setLayout(new BorderLayout());

	/** */
	JTabbedPane tab = new JTabbedPane();
	//JList modlist = new JList(listModel);

	//c.add(BorderLayout.WEST, modlist);
	//c.add(BorderLayout.CENTER, panel0);

	debug = new DebugPanel(kernel);
	game = new GamePanel(kernel);

	tab.add(game, "Game");
	tab.add(debug, "Debug");

	try {
	    snoopy = new SnoopyModule(kernel);
	    snoopy.start();
	} catch (Exception e) {}

	c.add(tab);

	initMenu();

	setVisible(true);
    }

    /**
     * Create the menubar and menuentries.
     *
     */
    private void initMenu() {
	
	JMenuBar menu = new JMenuBar();
	JMenu fileMenu = new JMenu("File");
	JMenuItem fileNew  = new JMenuItem("New");
	JMenuItem fileOpen  = new JMenuItem("Open...");
	JMenuItem fileSave  = new JMenuItem("Save...");
	JMenuItem fileQuit  = new JMenuItem("Quit");
	JMenu modMenu = new JMenu("Modules");
	JMenuItem modBrowse = new JMenuItem("Browse...");
	JMenu msgMenu = new JMenu("Messages");
	JMenuItem msgTxt    = new JMenuItem("new TextMessage...");
	JMenuItem msgNet    = new JMenuItem("new NetworkMessage...");
	JMenuItem msgAct    = new JMenuItem("new ActionMessage...");
	JMenuItem msgSend   = new JMenuItem("Send");

	/* File */

	fileNew.addActionListener(this);
	fileQuit.addActionListener(this);
	fileSave.addActionListener(this);
	fileOpen.addActionListener(this);

	fileQuit.setActionCommand("quit");
	fileNew.setActionCommand("new");
	fileOpen.setActionCommand("open");
	fileSave.setActionCommand("save");	

	//fileMenu.add(fileNew);
	//fileMenu.add(fileOpen);
	//fileMenu.add(fileSave);
	//fileMenu.addSeparator();
	fileMenu.add(fileQuit);

	/* Modules */

	modBrowse.addActionListener(this);
	modBrowse.setActionCommand("browse");
	modMenu.add(modBrowse);

	/* Messages */

	msgTxt.addActionListener(this);
	msgTxt.setActionCommand("new_text_message");
	msgMenu.add(msgTxt);

	msgNet.addActionListener(this);
	msgNet.setActionCommand("new_network_message");
	msgMenu.add(msgNet);

	msgAct.addActionListener(this);
	msgAct.setActionCommand("new_action_message");
	msgMenu.add(msgAct);

	msgMenu.addSeparator();

	msgSend.addActionListener(this);
	msgSend.setActionCommand("send");
	msgMenu.add(msgSend);

	/* Main menubar */

	menu.add(fileMenu);
	//menu.add(modMenu);
	//menu.add(msgMenu);

	setJMenuBar(menu);
    }

    public void actionPerformed(ActionEvent ev) {

	String cmd = ev.getActionCommand();

	if (cmd.equals("open")) {

	    int returnVal = chooser.showOpenDialog(this);

	    if (returnVal == JFileChooser.APPROVE_OPTION) {
		System.out.println("You chose to open this file: " +
				   chooser.getSelectedFile().getName());
	    }
	} else if (cmd.equals("browse")) {
	    browser.setVisible(true);
	} else if (cmd.equals("send")) {
	} else if (cmd.equals("quit")) {
	    System.exit(0);
	}
    }

    public static final void main(String[] argv) {
	try {
	    ServerGUI loader = new ServerGUI(argv[0]);
	} catch (ArrayIndexOutOfBoundsException e) {
	    System.err.println("Usage: java ServerGUI CONFIG");
	}
    }
}

/****************************************************************************/
/*                                                                          */
/*                                                                          */
/*                                                                          */
/*                                                                          */
/****************************************************************************/


/**
 * This class provides info about the current game, such as which players
 * are connected and what they are up to. It also provides the gamemaster
 * to send messages to the players.
 *
 */
class GamePanel extends JPanel {

    private DefaultListModel playerListModel;
    private JList playerList;
    private Dimension prefSize;
    private Kernel kernel;
    private Module module;
    private String[] playermap;
    private ChatPanel chatPanel;
    private ServerPanel serverPanel;
    

    private JTextField chatInputField;
    private JTextArea chatTextArea;

    private final ResourceBundle locale = 
    ResourceBundle.getBundle("gangup", Locale.getDefault());

    /**
     * This class is used to handle user input in the chat panel.
     *
     */
    private class GameListener extends KeyAdapter implements ActionListener {

	private boolean gameOver = true;
	private JButton button;
	private JButton ssbutton;

	public GameListener() {	}

	public GameListener(JButton ssbutton) {
	    this.ssbutton = ssbutton;
	}

	public void gameStarted(boolean gameStarted) {
	    gameOver = !gameStarted;
	    if (gameOver) {
		ssbutton.setText("Start Game");
	    } else {
		ssbutton.setText("Stop Game");
	    }
	}

	public void keyTyped(KeyEvent ev) {
	    if (ev.getKeyChar() == KeyEvent.VK_ENTER) {
		submit();
	    }
	}
	
	public void actionPerformed(ActionEvent ev) {

	    if (ev.getActionCommand().equals("send")) {
		submit(); return;
	    }

	    if (ev.getActionCommand().equals("startstop")) {

		Message msg = new Message("GAME", null);

		button = (JButton) ev.getSource();
		button.setEnabled(false);

		msg.setSender("SnoopyModule");
		msg.setBody(gameOver ? "WARMUP" : "STOP");

		gameOver = !gameOver;

		try {
		    kernel.receiveMessage(msg);
		} catch (MessageDeliveryException e) {
		    e.printStackTrace();
		}
	    
		// enable button after a short period of time
		new DelayThread().start();
	    }
	}

	/* Sends the message to connected clients */

	private void submit() {

	    String mess = chatInputField.getText();
	    TextMessage data = new TextMessage(-1,-1,TextMessage.GENERAL,mess);
	    Message msg = new Message("NETWORK", new NetworkData(data));

	    // specify sender and send off message

	    try {
		msg.setSender("GameChatPanel");
		module.sendMessage(msg);
	    } catch (MessageDeliveryException e) {
		e.printStackTrace();
	    }

	    // clear input field.

	    chatPanel.write("GameMaster: " + mess + "\n");
	    chatPanel.setInputText("");
	}

	private class DelayThread extends Thread {
	    public void run() {
		try { 
		    Thread.sleep(2000); 
		    button.setEnabled(true); 
		    if (gameOver) {
			button.setText("Start Game");
		    } else {
			button.setText("Stop Game");
		    }
		}
		catch (InterruptedException e) {
		    System.err.println("GamePanel.submit(): *** WARNING *** " +
				       "[INTR] caught interrupted exception.");
		    e.printStackTrace(System.err);
		}
	    }
	}
    }

    /**
     * This class provides an interface to send text messages to
     * the the connected clients.
     */
    private class ChatPanel extends JPanel {

	private JButton sendButton;
	private JScrollPane textScroll;
	
	/**
	 * Create a new instance of the ChatPanel class. This class
	 * provides a mechanism for sending and receiving text messages.
	 */
	public ChatPanel() {

	    setBorder(BorderFactory.createTitledBorder("Chat"));	    
	    setLayout(new BorderLayout());
	    
	    chatTextArea = new JTextArea();
	    chatTextArea.setEditable(false);
	    chatTextArea.setRows(5);
	    textScroll = new JScrollPane(chatTextArea);
	    
	    JPanel inputpanel = new JPanel();
	    chatInputField = new JTextField();

	    JLabel inputtext = 
		new JLabel(" " + locale.getString("_SRVGUI_INPUTTEXT") + " ");

	    sendButton = 
		new JButton(locale.getString("_SRVGUI_GAME_SEND_BUTTON"));

	    inputpanel.setLayout(new BorderLayout());
	    inputpanel.add(inputtext,BorderLayout.WEST);
	    inputpanel.add(chatInputField,BorderLayout.CENTER);
	    inputpanel.add(sendButton,BorderLayout.EAST);
	    
	    add(textScroll,BorderLayout.CENTER);
	    add(inputpanel,BorderLayout.SOUTH);

	    GameListener listener = new GameListener();

	    chatInputField.addKeyListener(listener);
	    sendButton.setActionCommand("send");
	    sendButton.addActionListener(listener);
	}

	/** 
	 * Write the specified string to the chat text area.
	 * @param text the text to be written.
	 */
	public void write(String text) {
	    chatTextArea.append(text);
	    int endpos = textScroll.getVerticalScrollBar().getMaximum();
	    textScroll.getVerticalScrollBar().setValue(endpos);
	}

	/**
	 * Sets the input text field to the specified string;
	 * @param text the text to set.
	 */
	public void setInputText(String text) {
	    chatInputField.setText(text);
	}

	/**
	 * returns the text in the chat input field.
	 * @return the text in the chat input field.
	 */
	public String getInputText() {
	    return chatInputField.getText();
	}
    }

    /**
     * This class provides an interface for manipulating the game state.
     *
     */
    private class ServerPanel extends JPanel {
	GameListener listener;

	ServerPanel() {
	    setBorder(BorderFactory.createTitledBorder("Server"));
	    createComponents();
	}

	public void gameStarted(boolean gameStarted) {
	    listener.gameStarted(gameStarted);
	}

	private void createComponents() {
	    GridBagLayout layout = new GridBagLayout();
	    GridBagConstraints cons = new GridBagConstraints();
	    JButton startStopButton = new JButton("Start Game");
	    JButton kickbanButton = new JButton("Kick Player");
	    JButton placeHolderButton = new JButton("Place Holder");
	    JButton pauseButton = new JButton("Pause Game");
	    JButton infoButton = new JButton("Player Info");
	    JButton helpButton = new JButton("Get Help");
	    listener = new GameListener(startStopButton);

	    startStopButton.addActionListener(listener);
	    startStopButton.setActionCommand("startstop");
	    setLayout(layout);

	    cons.fill = GridBagConstraints.BOTH;
/*
	    cons.fill = GridBagConstraints.HORIZONTAL;

	    cons.weightx = 1;
	    cons.weighty = 1;
	    cons.anchor = GridBagConstraints.NORTH;
	    cons.gridwidth = GridBagConstraints.REMAINDER;
	    layout.setConstraints(helpButton, cons);
	    helpButton.setEnabled(false);
	    add(helpButton);

	    cons.weightx = 1;
	    cons.weighty = 0;
	    cons.anchor = GridBagConstraints.SOUTH;
	    cons.gridwidth = GridBagConstraints.REMAINDER;
	    layout.setConstraints(placeHolderButton, cons);
	    placeHolderButton.setEnabled(false);
	    add(placeHolderButton);

	    cons.weightx = 1;
	    cons.weighty = 0;
	    cons.anchor = GridBagConstraints.SOUTH;
	    cons.gridwidth = GridBagConstraints.REMAINDER;
	    layout.setConstraints(infoButton, cons);
	    infoButton.setEnabled(false);
	    add(infoButton);

	    cons.weightx = 1;
	    cons.weighty = 0;
	    cons.anchor = GridBagConstraints.SOUTH;
	    cons.gridwidth = GridBagConstraints.REMAINDER;
	    layout.setConstraints(kickbanButton, cons);
	    kickbanButton.setEnabled(false);
	    add(kickbanButton);

	    cons.weightx = 1;
	    cons.weighty = 0;
	    cons.anchor = GridBagConstraints.SOUTH;
	    cons.gridwidth = GridBagConstraints.REMAINDER;
	    layout.setConstraints(pauseButton, cons);
	    pauseButton.setEnabled(false);
	    add(pauseButton);
*/
	    cons.weightx = 1;
	    cons.weighty = 0;
	    cons.anchor = GridBagConstraints.SOUTH;
	    cons.gridwidth = GridBagConstraints.REMAINDER;
	    layout.setConstraints(startStopButton, cons);
	    add(startStopButton);

	}
    }

    /**
     * This class provides the interface for viewing and changing player
     * information.
     *
     */
    private class PlayerPanel extends JPanel {

	PlayerPanel() {
	    setBorder(BorderFactory.createTitledBorder("Players"));
	    createComponents();
	}

	public void createComponents() {

	    playerListModel = new DefaultListModel();
	    playerList = new JList(playerListModel);

	    JScrollPane scroll = new JScrollPane(playerList);
	    JLabel image = 
		new JLabel(new ImageIcon("dat/gfx/player_baby.png"));
	    JLabel strengthLabel = new JLabel("Strength: ?");
	    JButton changeButton = new JButton("Change...");
	    JButton placeHolderButton = new JButton(" ");

//	    image.setBorder(BorderFactory.createEmptyBorder());

	    GridBagLayout layout = new GridBagLayout();
	    GridBagConstraints cons = new GridBagConstraints();

	    setLayout(layout);
	    
	    cons.weightx = 1;
	    cons.weighty = 1;
	    cons.gridheight = GridBagConstraints.REMAINDER;
	    cons.fill = GridBagConstraints.BOTH;
	    cons.anchor = GridBagConstraints.NORTH;

	    layout.setConstraints(scroll, cons);
	    add(scroll);

/*
	    cons.weightx = 0;
	    cons.weighty = 0;
	    cons.gridheight = 1;
	    layout.setConstraints(image, cons);
	    image.setVerticalAlignment(SwingConstants.TOP);
	    add(image);

	    cons.weightx = 0;
	    cons.weighty = 1;
	    cons.gridheight = 1;
	    cons.anchor = GridBagConstraints.NORTH;
	    cons.ipadx = 5;
	    layout.setConstraints(strengthLabel, cons);
	    cons.ipadx = 0;
//	    strengthLabel.setVerticalAlignment(SwingConstants.TOP);
	    add(strengthLabel);

	    cons.weightx = 0;
	    cons.weighty = 0;
	    cons.gridheight = 1;
	    cons.anchor = GridBagConstraints.SOUTH;
	    layout.setConstraints(placeHolderButton, cons);
	    placeHolderButton.setEnabled(false);
	    add(placeHolderButton);

	    cons.weightx = 0;
	    cons.weighty = 0;
	    cons.gridheight = 1;
	    cons.anchor = GridBagConstraints.SOUTH;
	    layout.setConstraints(changeButton, cons);
	    add(changeButton);
*/
	}
    }


    /**
     * Creates a new instance of the GamePanel class.
     * @param kernel the associated Kernel.
     */
    public GamePanel(Kernel kernel) {
	this.kernel = kernel;
	this.playermap = new String[GameState.MAX_PLAYER_LIMIT];
	initModules();
	createComponents();
	prefSize = new Dimension(100,100);
    }


    /**
     * Adds the name of the player with the specified id to the playerlist.
     * @param id the id of the player.
     * @param name the name of the player
     */
    public void addPlayer(int id, String name) {
	try {
	    playerListModel.addElement(name);
	    playermap[id] = name;
	} catch (ArrayIndexOutOfBoundsException e) {
	    System.err.println("GamePanel.addPlayer(): *** WARNING *** " +
			       "[IDX] caught out of bounds exception");
	    e.printStackTrace();
	} catch (Exception e) {
	    System.err.println("GamePanel.addPlayer(): *** WARNING *** " +
			       "[X] caught unknown exception");
	    e.printStackTrace();
	}
    }

    /**
     * Removes the name of the player with the specified id from 
     * the playerlist.
     * @param id the id of the player.
     */
    public void removePlayer(int id) {
	try {
	    playerListModel.removeElement(playermap[id]);
	} catch (ArrayIndexOutOfBoundsException e) {
	    System.err.println("GamePanel.removePlayer(): *** WARNING *** " +
			       "[IDX] caught out of bounds exception");
	    e.printStackTrace();
	} catch (Exception e) {
	    System.err.println("GamePanel.removePlayer(): *** WARNING *** " +
			       "[X] caught unknown exception");
	    e.printStackTrace();
	}
    }

    /**
     * Handles all game events received by the interface module. This is 
     * used to update the player list with connected players.
     *
     * @param mod the interface module.
     * @param msg the message that is to be handled.
     */
    private void handleGameEvent(Module mod, Message msg) {

	Message res;
	String[] args = null;

	try {

	    /* intercept and display text general messages */

	    if (msg.getHeader().equals("FROM_NETWORK")) {

		if (msg.getBody() instanceof TextMessage) {
		    TextMessage txtmsg = (TextMessage) msg.getBody();
		    if (txtmsg.getMessageType() == TextMessage.GENERAL) {
			String text = txtmsg.getText();
			String sender = playermap[txtmsg.getActor()];
			chatPanel.write(sender+": " + text + "\n");
		    }
		}

		return; // end processing.
	    }
	    
	    /* process other kind of game events */

	    args = ((String) msg.getBody()).split(" ");

	    if (msg.getHeader().equals("GAME")) {
		if(msg.getBody().equals("FINISHED") ||
		   msg.getBody().equals("STOP")) {
		    serverPanel.gameStarted(false);
		}
		if(msg.getBody().equals("WARMUP")) {
		    serverPanel.gameStarted(true);
		}
	    } else if (msg.getHeader().equals("DROPPED")) {
		removePlayer(Integer.parseInt(args[1]));
	    } else if (msg.getHeader().equals("CONNECTED")) {
		res = mod.request(new Message("GAMEINFO", "INFO " + args[1]));
		addPlayer(Integer.parseInt(args[1]), (String) res.getBody());
	    }
	} catch (Exception e) {
	    System.err.print("GameListener.processMessage(): ");
	    System.err.print("[X] *** WARNING *** caught unknown exception\n");
	    e.printStackTrace(System.err);
	}
    }

    /**
     * Create and initialize the modules used by this class.
     *
     */
    private void initModules() {

	try {

	    /* Create an interface module that forwads game events to
	       the game event handler */

	    module = new AbstractModule() {
		    protected void processMessage(Message m) {
			handleGameEvent(this, m);
		    }
		};

	    /* initialize the module */

	    module.info().set("name","GameEventListener");

	    /* register this module with the kernel */

	    module.register(kernel);
	    kernel.subscribe(module, "GAME FROM_NETWORK CONNECTED DROPPED");
	    module.start();

	} catch (ModuleRegisterException e) {
	    System.err.println("GamePanel.initModules(): " +
			       "*** WARNING *** [MOD]");
	    e.printStackTrace(System.err);

	} catch (Exception e) {
	    System.err.print("GameListener.processMessage(): ");
	    System.err.print("*** WARNING *** [X] caught unknown exception\n");
	    e.printStackTrace(System.err); 
	}
    }
  
    /**
     * Create and layout the components used by this class.
     *
     */
    private void createComponents() {

	GridBagLayout layout = new GridBagLayout();
	GridBagConstraints cons = new GridBagConstraints();
	chatPanel = new ChatPanel();
	JPanel playerPanel = new PlayerPanel();
	serverPanel = new ServerPanel();
	setLayout(new BorderLayout());
	add(BorderLayout.SOUTH, chatPanel);
	add(BorderLayout.CENTER, playerPanel);
	add(BorderLayout.EAST, serverPanel);
    }
    
    public Dimension getPreferredSize() {
	return prefSize;
    }
}


/****************************************************************************/
/*                                                                          */
/*                                                                          */
/*                                                                          */
/*                                                                          */
/****************************************************************************/

/**
 * Provides a browser for finding modules.
 *
 */
class ModuleBrowserDialog extends JDialog implements MouseListener,
      ListSelectionListener, ActionListener {

    DefaultListModel model;
    ModuleInfo[] modv;
    JList list;

    private class ModuleFilter implements FilenameFilter {
	public boolean accept(File dir, String name) {
	    return name.matches(".*[.]class$");
	}
    };

    /** File filter to use when looking for modules. */
    private ModuleFilter filter;

    /** */
    private Kernel kernel;

    /** 
     * Creates a new instance of the ModuleBrowserDialog class.
     *
     * @param parent the JFrame that opened this dialog.
     * @param kern the Kernel associated with this browser.
     */
    ModuleBrowserDialog(JFrame parent, Kernel kern) {

	Container c;
	JScrollPane scroll;

	kernel = kern;

	setTitle("Module Browser");
	setSize(150,300);

	model = new DefaultListModel();
	filter = new ModuleFilter();

	c = getContentPane();
	c.setLayout(new BorderLayout());

	list = new JList(model);
	scroll = new JScrollPane(list);

	c.add(scroll);

	/* fixa */
	modv = findAvailableModules("obj/module");
	for (ModuleInfo m : modv) {
	    model.addElement(m.get("name"));
	}

	list.setSelectedIndex(0);
	list.addMouseListener(this);
	list.addListSelectionListener(this);

	initMenus();
    }

    private void initMenus() {
	JPopupMenu popup    = new JPopupMenu();
	JMenuItem mnuLoad   = new JMenuItem("Load");
	JMenuItem mnuUnload = new JMenuItem("Unload");
	JMenuItem mnuDump   = new JMenuItem("Dump");
	    
	popup.add(mnuLoad);
	popup.add(mnuUnload);
	popup.add(mnuDump);
	    
	mnuLoad.addActionListener(this);
	mnuUnload.addActionListener(this);
	mnuDump.addActionListener(this);

	mnuLoad.setActionCommand("load");
	mnuUnload.setActionCommand("unload");
	mnuDump.setActionCommand("dump");

	list.setComponentPopupMenu(popup);
    }

    /** 
     * Returns the modules found in the specified directory.
     * @param path the directory in which to search for modules.
     * @return the modules found in the specified directory.
     */
    private ModuleInfo[] findAvailableModules(String path) {

	Vector<ModuleInfo> r = new Vector<ModuleInfo>();

	try {

	    Class ref = Class.forName("module.Module");
	    File dir = new File(path);

	    /* check all .class files in the specified directory */

	    for (String f : dir.list(filter)) {

		/* remove trailing .class suffix and get class. */

		String name = f.substring(0, f.length()-6);
		Class cls = Class.forName("module." + name); // fixa!

		/* ignore interfaces */

		if (!cls.isInterface()) {

		    /* check if ref is super type of cls */

		    if (ref.isAssignableFrom(cls)) {
			ModuleInfo m = new ModuleInfo(cls.getAnnotations());
			if (m.get("name") == null) {
			    m.set("name", name);
			}
			r.add(m);
		    }
		}
	    }

	} catch (Exception e) { /* FileNotFoundException */
	    e.printStackTrace(System.err);
	}

	/*
  	   ModuleInfo[] mv = new ModuleInfo[r.size()];
	   for (int i=0; i<mv.length; i++) { mv[i] = r.get(i); }
	   r = null;
	*/

	return r.toArray(new ModuleInfo[r.size()]);
    }

    /* Listeners */

    public void actionPerformed(ActionEvent ev) {

	String cmd = ev.getActionCommand();

	try {
	    if (cmd.equals("load")) {

		kernel.loadModule("module." + 
				  modv[list.getSelectedIndex()].get("name"));

	    } else if (cmd.equals("unload")) {

		kernel.unloadModule("module." +
				    modv[list.getSelectedIndex()].get("name"));

	    } else if (cmd.equals("dump")) {
		System.err.println(modv[list.getSelectedIndex()]);
	    }
	} catch (Exception e) {
	    e.printStackTrace(System.err);
	}
    }

    public void mouseClicked(MouseEvent ev) {
	int button = ev.getButton();

	if ((button & MouseEvent.BUTTON1_MASK) > 0) {
	    if (ev.getClickCount() >= 2) {
		System.err.println("double-click");
		System.err.println(list.getSelectedValue());
	    }
	} else if ((button & MouseEvent.BUTTON2_MASK) > 0) {
	    
	}
    }

    public void valueChanged(ListSelectionEvent ev) {}

    /* unused */

    public void mouseEntered(MouseEvent ev) {}
    public void mouseExited(MouseEvent ev) {}
    public void mousePressed(MouseEvent ev) {}
    public void mouseReleased(MouseEvent ev) {}

}
