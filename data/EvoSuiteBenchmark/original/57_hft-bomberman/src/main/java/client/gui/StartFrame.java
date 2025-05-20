package client.gui;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import messages.global.MapInfo;
import messages.global.SessionDetailsMsg;

import org.apache.log4j.Logger;

import sound.SoundPlayer;
import client.BomberClient;
import client.gui.components.MyPanel;
import client.view.GameCanvas;
import db.DBGameUser;

public class StartFrame extends JFrame implements KeyListener,
		ComponentListener, WindowFocusListener {

	private static final Logger logger = Logger.getLogger(StartFrame.class);
	/**
	 * @param args
	 */
	/**
	 * for fullscreen mode
	 */

	private static String fullscreen = " ";

	private Component actualPanel;

	MyPanel jPanelFirst = new MyPanel();

	MyPanel jPanelSelectGame = new MyPanel();

	public ServerSelectionDialog jPanelSelectServer = new ServerSelectionDialog();

	MyPanel jPanelConfig = new MyPanel();

	MyPanel jPanelJoinGame = new MyPanel();

	Game jPanelGame = new Game();

	// TODO
	GameCanvas gameCanvas = jPanelGame.gameCanvas;
	// GameCanvas gameCanvas = new GameCanvas(800, 600);

	GlobalLobby jPanelGlobalLobby = new GlobalLobby();

	CreateGameDialog jPanelNewGame = new CreateGameDialog();

	LoginDialog jPanelLogin = LoginDialog.init();

	SessionLobby jPanelWaitForGame = new SessionLobby();

	public HighscoreLobby jPanelHighscoreLobby = new HighscoreLobby();

	GlobalHighscoreLobby jPanelGlobalHighscoreLobby = new GlobalHighscoreLobby();

	JButton jButtonSelectServer = new JButton("SelectServer");

	JButton jButtonConfig = new JButton("Config");

	// JButton jButtonSelectServer

	JTextField jTextFieldSelectedServerIP = new JTextField();

	JTextField jTextFieldSelectedServerName = new JTextField();

	JComboBox jComboBoxSelectedServerLoad = new JComboBox();

	JButton jButtonSelectedServerLoad = new JButton("Load");

	JButton jButtonSelectedServerStore = new JButton("Store");

	JButton jButtonSelectedServerCancel = new JButton("Cancel");

	JButton jButtonSelectedServerOkay = new JButton("Okay");

	JButton jButtonSelectedServerConfig = new JButton("Config");

	JTextField jTextFieldLoginUsername = new JTextField();

	JTextField jTextFieldLoginPassword = new JTextField();

	JButton jButtonLoginCancel = new JButton("Cancel");

	JButton jButtonLoginLogin = new JButton("Login");

	public static StartFrame startFrame = null;

	ActionListenerChangePanel actionListenerChangePanel = new ActionListenerChangePanel();

	// TODO
	Cash myCash = new Cash();

	public static void main(String[] args) {

		if (args.length > 0) {
			fullscreen = args[0];
		}
		System.out.println(fullscreen);

		SplashThread splash = new SplashThread();
		SoundPlayer.getInstance().intro();
		splash.start();

		startFrame = new StartFrame();

		startFrame.setActualPanel(startFrame.jPanelSelectServer);

		splash.setRun(false);
		synchronized (splash) {
			splash.notify();
		}
		// SoundPlayer.getInstance().hintergrund();
	}

	public static StartFrame getInstance() {
		if (startFrame == null) {
			startFrame = new StartFrame();
		}
		return startFrame;

	}

	private StartFrame() {
		super("Fubarman");
		startFrame = this;
		initFrame();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/*
	 * Spiel in Fullscreen @Ghazwan
	 * 
	 * 
	 */

	int width = 0;
	int height = 0;
	private Cursor theCursor;

	public void setFullScreen() {

		/*
		 * Rectangle virtualBounds = new Rectangle(); GraphicsDevice GDEVICE =
		 * GraphicsEnvironment.getLocalGraphicsEnvironment()
		 * .getDefaultScreenDevice(); GraphicsConfiguration[] gc =
		 * GDEVICE.getConfigurations();
		 * 
		 * setResizable(false); setUndecorated(true); virtualBounds =
		 * virtualBounds.union(gc[0].getBounds()); width = virtualBounds.width;
		 * height = virtualBounds.height; setVisible(true);
		 * GDEVICE.setFullScreenWindow(this);
		 */

		setUndecorated(true);
		GraphicsDevice device = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		// setSize(800, 600);
		// setVisible(true);
		// setResizable(false);
		device.setFullScreenWindow(this);
		if (device.isDisplayChangeSupported()) {
			DisplayMode dm = new DisplayMode(800, 600, 32,
					DisplayMode.REFRESH_RATE_UNKNOWN);
			device.setDisplayMode(dm);

		}
		// device.setDisplayMode(new DisplayMode(800 , 600, 16, 85));

	}

	// private JFrame hackFrame;

	public void initFrame() {
		// Add the bomb as cursor
		// Image i = ResourceService.getImage("/images/mousecursor.png");
		// Cursor c = getToolkit().createCustomCursor(i, new Point(10, 10),
		// "CUSTOM_CURSOR");
		// setCursor(c);

		// Calculate the Size of the screen for fullscreen

		Rectangle virtualBounds = new Rectangle();
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		this.addKeyListener(this);
		try {
			GraphicsDevice gd = gs[0];
			GraphicsConfiguration[] gc = gd.getConfigurations(); // System.out.println("GraphicsConfig:
			// " +
			gd.getConfigurations();
			virtualBounds = virtualBounds.union(gc[0].getBounds());
			width = virtualBounds.width;
			height = virtualBounds.height;
		} catch (Exception e) {
		}

		Dimension dimm = new Dimension(width, height);
		startFrame.setSize(dimm);
		// startFrame.setResizable(false);
		// startFrame.setUndecorated(true);
		startFrame.getContentPane().setLayout(null);
		// startFrame.setForeground(new Color(255,255,255));
		// JButton but = new JButton("Test");
		// getContentPane().add (but);

		if (fullscreen.equals("-fullscreen")) {
			logger.info("Changing to fullscreen mode");
			setFullScreen();
		}

		else {
			setMiddel(jPanelSelectServer);
			setMiddel(jPanelLogin);
			setMiddel(jPanelGame);
			setMiddel(jPanelGlobalLobby);
			setMiddel(jPanelHighscoreLobby);
			setMiddel(jPanelWaitForGame);
			setMiddel(jPanelNewGame);
			setMiddel(jPanelGlobalHighscoreLobby);
		}

		getContentPane().add(jPanelSelectServer);
		actualPanel = jPanelSelectServer;
		initjPanelConfig();
//		initjPanelLogin();
//		getContentPane().add(jPanelGlobalLobby);	
//		getContentPane().add(jPanelLogin);
//		getContentPane().add(jPanelNewGame);
//		getContentPane().add(jPanelGame);
//		getContentPane().add(jPanelWaitForGame);
//		getContentPane().add(jPanelHighscoreLobby);
//		getContentPane().add(jPanelGlobalHighscoreLobby);
//		getContentPane().add(jPanelLogin);
//		jPanelGlobalLobby.setVisible(false);
//		jPanelLogin.setVisible(false);
//		jPanelNewGame.setVisible(false);
//		jPanelGame.setVisible(false);
//		jPanelWaitForGame.setVisible(false);
//		jPanelHighscoreLobby.setVisible(false);
//		jPanelGlobalHighscoreLobby.setVisible(false);
//		jPanelLogin.setVisible(false);
		jButtonSelectServer.addActionListener(actionListenerChangePanel);
		jButtonConfig.addActionListener(actionListenerChangePanel);
		jButtonSelectedServerOkay.addActionListener(actionListenerChangePanel);
		jButtonSelectedServerConfig
				.addActionListener(actionListenerChangePanel);

		// getContentPane().add(myCash);
		// gameCanvas.getCanvas().setBackground(Color.white);
		// changePanel(gameCanvas);
		// this.setResizable(false);
		// startFrame.getContentPane().setBackground(new Color(0, 0, 0));
		// but.requestFocus();
		startFrame.addKeyListener(this);
	}

	private void setMiddel(MyPanel panel) {
		int pwidth = (int) panel.getBounds().getWidth();
		int pheight = (int) panel.getBounds().getHeight();

		int offsetX = (width / 2) - (pwidth / 2);
		int offsetY = (height / 2) - (pheight / 2);

		panel.setBounds(offsetX, offsetY, pwidth, pheight);

	}

	private void initjPanelConfig() {
		jPanelConfig.setSize(new Dimension(800, 600));
		jPanelConfig.add(new JLabel("Config"));
	}

	public class ActionListenerChangePanel implements ActionListener {

		public void actionPerformed(ActionEvent evt) {
			jPanelFirst.setVisible(false);
			jPanelSelectServer.setVisible(false);
			jPanelConfig.setVisible(false);
			// startFrame.removeAll();
			if (evt.getSource().equals(jButtonSelectServer)) {
				// System.out.println("SelectServer");
				jPanelSelectServer.setVisible(true);
			} else if (evt.getSource().equals(jButtonConfig)) {
				// System.out.println("Config");
				jPanelConfig.setVisible(true);

			}
			// else if (evt.getSource().equals(jButtonSelectedServerConfig)) {
			// System.out.println("Config");
			// jPanelConfig.setVisible(true);
			//
			// }else if (evt.getSource().equals(jButtonSelectedServerOkay)) {
			// System.out.println("Config");
			// jPanelLogin.setVisible(true);
			//
			// }
		}

	}

	public GlobalLobby getJPanelGlobalLobby() {
		return jPanelGlobalLobby;
	}

	public void changePanel(Component panel) {
		getContentPane().remove(actualPanel);
		panel.setVisible(false);
		getContentPane().add(panel);
		panel.setVisible(true);
		actualPanel = panel;
//		jPanelFirst.setVisible(false);
//		jPanelSelectServer.setVisible(false);
//		jPanelConfig.setVisible(false);
//		jPanelGlobalLobby.setVisible(false);
//		jPanelNewGame.setVisible(false);
//		jPanelWaitForGame.setVisible(false);
//		jPanelGame.setVisible(false);
//		jPanelLogin.setVisible(false);
//		jPanelHighscoreLobby.setVisible(false);
//		jPanelGlobalHighscoreLobby.setVisible(false);
	}

	public void keyPressed(KeyEvent evt) {
		System.out.println("Key pressed");
		if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.out.println("Escape pressedS");
			System.out.println(evt.getKeyCode());
		}
	}

	/*
	 * Sollte das gesamte Spiel beenden durch "Esc" funktioniert aber noch
	 * nicht!
	 * 
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */

	public void keyReleased(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
			// System.out.println("Exit");

			System.exit(0);

		}
	}

	/**
	 * wird nicht verwendet
	 */
	public void keyTyped(KeyEvent arg0) {
	}

	/**
	 * liefert die GameCanvas
	 * 
	 * @return
	 */

	public GameCanvas getGameCanvas() {
		return gameCanvas;
	}

	/**
	 * Das GameCanvas wird auf dem Bilschirm angezeigt
	 */

	public void showGameCanvas() {
		logger
				.debug("Showing GameCanvas, isVisible: "
						+ gameCanvas.isVisible());
		// gameCanvas.getCanvas().setBackground(Color.white);
		changePanel(jPanelGame);
		theCursor = getCursor();
		// Add the bomb as cursor
		Image i = new ImageIcon("").getImage();
		Cursor c = getToolkit().createCustomCursor(i, new Point(10, 10),
				"CUSTOM_CURSOR");
		setCursor(c);
		jPanelGame.gameCanvas.requestFocus();

	}

	/**
	 * Game wird vom Bildschirm genommen und der Screen "Join_New_Game" zur
	 * auswahl eines Spieles oder erstellen eines neuen Spieles
	 */
	// TODO
	public void showHighscoreLobby() {
		jPanelHighscoreLobby.jButtonNextRound.setVisible(true);
		jPanelHighscoreLobby.repaint();
		this.changePanel(jPanelHighscoreLobby);
		// // Add the bomb as cursor
		// Image i = ResourceService.getImage("/images/mousecursor.png");
		// Cursor c = getToolkit().createCustomCursor(i, new Point(10, 10),
		// "CUSTOM_CURSOR");
		setCursor(theCursor);

	}

	public void updateRoundScoreData(HashMap<String, Integer> rndscore) {
		jPanelHighscoreLobby.setTable(rndscore);
	}

	public void updateSessionScoreData(HashMap<String, Integer> rndscore) {
		jPanelHighscoreLobby.setSessionTable(rndscore);
	}

	public void removeHighScoreData() {
		jPanelHighscoreLobby.removeTable();
	}

	public void removeSessionHighScoreData() {
		jPanelHighscoreLobby.removeSessionTable();
	}

	public void setAvailableMaps(Vector<MapInfo> maps) {
		jPanelNewGame.jListMap.setListData(maps);
		jPanelNewGame.jListMap.requestFocus();
	}

	public void setAvailableSessions(Vector<SessionDetailsMsg> sessions) {
		jPanelGlobalLobby.jListGames.setListData(sessions);
	}

	/**
	 * @param sender
	 * @param msg
	 */
	public void addChatMessageSession(String sender, String msg) {
		if (jPanelWaitForGame.isVisible()) {
			jPanelWaitForGame.getChat().addChatMessage(sender, msg);
		} else {
			jPanelHighscoreLobby.chat.addChatMessage(sender, msg);
		}

	}

	/**
	 * 
	 */
	public void removeChatMessageSession() {

		jPanelWaitForGame.getChat().clearChat();
		jPanelHighscoreLobby.chat.clearChat();

	}

	public void addChatMessageGlobal(String sender, String msg) {
		getJPanelGlobalLobby().getChat().addChatMessage(sender, msg);
		// jPanelHighscoreLobby.chat.addChatMessage(sender, msg);

	}

	public void showGlobalLobby() {
		BomberClient.getInstance().requestSessionList();
		jPanelGlobalLobby.hideInfos();
		jPanelGlobalLobby.jListGames.setSelectedIndex(-1);
		changePanel(jPanelGlobalLobby);
		// Image i = ResourceService.getImage("/images/mousecursor.png");
		// Cursor c = getToolkit().createCustomCursor(i, new Point(10, 10),
		// "CUSTOM_CURSOR");
		if (theCursor != null)
			setCursor(theCursor);
	}

	public void setGlobalScore(ArrayList<DBGameUser> list) {
		jPanelGlobalHighscoreLobby.fillScore(list);
	}

	public void logout() {
		StartFrame.getInstance().changePanel(
				StartFrame.getInstance().jPanelSelectServer);
		BomberClient.getInstance().closeConnection();
	}

	public SessionLobby getSessionLobby() {
		return jPanelWaitForGame;
	}

	public void windowGainedFocus(WindowEvent e) {
		repaint();

	}

	public void windowLostFocus(WindowEvent e) {
		repaint();

	}

	public void componentHidden(ComponentEvent e) {
		repaint();

	}

	public void componentMoved(ComponentEvent e) {
		repaint();

	}

	public void componentResized(ComponentEvent e) {
		repaint();

	}

	public void componentShown(ComponentEvent e) {
		repaint();

	}

	/**
	 * 
	 */
	public void showNewGamePanel() {
		changePanel(jPanelNewGame);
	}

	/**
	 * 
	 */
	public void showSessionLobby() {
		changePanel(jPanelWaitForGame);
		StartFrame.getInstance().jPanelWaitForGame.updateInfo();
	}

	public Component getActualPanel() {
		return actualPanel;
	}

	public void setActualPanel(Component actualPanel) {
		this.actualPanel = actualPanel;
	}

	public void setCursorWait() {
		setCursor(new Cursor(Cursor.WAIT_CURSOR));
	}

	public void setCursorNormal() {
		setCursor(theCursor);
	}
}
