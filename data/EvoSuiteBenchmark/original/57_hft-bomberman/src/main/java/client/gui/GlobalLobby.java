package client.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import messages.global.SessionDetailsMsg;
import client.BomberClient;
import client.gui.components.MyButton;
import client.gui.components.MyPanel;

public class GlobalLobby extends MyPanel implements ActionListener,
		MouseListener {

	/**
	 * @param args
	 */
	Chat chat;

	public Chat getChat() {
		return chat;
	}

	MyButton jButtonJoin = new MyButton("Join Game");
	MyButton jButtonStart = new MyButton("New Game");
	MyButton jButtonRefresh = new MyButton("Refresh");

	MyButton jButtonLogout = new MyButton("Logout");
	MyButton jButtonHightscore = new MyButton("Highscore");
	// JLabel jLabelJoin = new JLabel("Join Game:");

	JList jListGames = new JList();

	JLabel jLabelMap = new JLabel("Map:");
	JLabel jLabelPlayers = new JLabel("Players:");
	JLabel jLabelPlayersShow = new JLabel("-0/-0");
	JLabel jLabelRounds = new JLabel("Rounds:");
	JLabel jLabelRoundsShow = new JLabel("-1");

	ImageCache preview = new ImageCache(null, 20, 320, 120, 90);

	public GlobalLobby() {
		super();
		setLayout(null);
		setBounds(0, 0, 800, 600);
	//	this.setBackground(new Color(100, 0, 100));
		// jLabelJoin.setForeground(new Color(0, 255, 0));
		jButtonStart.addActionListener(this);
		jButtonJoin.addActionListener(this);
		// jLabelJoin.setBounds(20, 20, 150, 25);
		// hide join button at startup
		jButtonJoin.setBounds(20, 270, 115, 25);
		jButtonJoin.setVisible(false);
		jButtonJoin.setEnabled(false);
		jButtonStart.setBounds(145, 270, 115, 25);
		jButtonRefresh.setBounds(270, 270, 115, 25);
		jButtonLogout.setBounds(10, 570, 80, 25);
		jButtonHightscore.setBounds(100, 570, 150, 25);
		jListGames.setBounds(20, 60, 370, 200);

		jListGames.addMouseListener(this);
		jButtonRefresh.addActionListener(this);
		jButtonLogout.addActionListener(this);
		jButtonHightscore.addActionListener(this);

		jLabelMap.setBounds(20, 300, 50, 25);
		jLabelPlayers.setBounds(20, 410, 50, 25);
		jLabelPlayersShow.setBounds(120, 410, 50, 25);
		jLabelRounds.setBounds(20, 440, 50, 25);
		jLabelRoundsShow.setBounds(120, 440, 50, 25);

		// this.addLabel(jLabelJoin);
		this.add(jButtonStart);
		this.add(jButtonJoin);
		this.add(jListGames);
		this.add(jButtonRefresh);
		this.setVisible(true);
		this.add(jButtonLogout);
		this.add(jButtonHightscore);
		this.addLabel(jLabelMap);
		this.addImage(preview);
		this.addLabel(jLabelPlayers);
		this.addLabel(jLabelPlayersShow);
		this.addLabel(jLabelRounds);
		this.addLabel(jLabelRoundsShow);
		chat = new Chat(400, 600);
		chat.setType(chat.GLOBAL);
		chat.setBounds(400, 0, 400, 600);
		add(chat);
		this.setTitel("Select Game or create new Game");
		hideInfos();
		setPicture("/images/backgroundLeft.png");
	}

	// public void actionPerformed(ActionEvent evt) {
	// StartFrame frame = StartFrame.init();
	// if (evt.getSource().equals(jButtonStart)) {
	//			
	// System.out.println("Neues Spiel wird gestartet!");
	// frame.changePanel(frame.jPanelNewGame);
	// }else if (evt.getSource().equals(jButtonJoin)){
	// // TODO Das ausgewaehlte Spiel muss geladen werden
	// Config.gameName = "" + jListGames.getSelectedValue();
	// frame.changePanel(frame.jPanelGame);
	// System.out.println("Join Game");
	// }
	// }

	public void loadGames() {
		BomberClient.getInstance().requestSessionList();
		jListGames.setSelectedIndex(0);
	}

	/**
	 * load game/session details to display
	 * 
	 * @param index
	 */
	public void loadSessionInfos() {

		if (jListGames.getSelectedIndex() != -1) {
			SessionDetailsMsg cash = (SessionDetailsMsg) jListGames
					.getSelectedValue();
			jLabelPlayersShow.setText(cash.getTotalNrOfPlayers() + "/"
					+ cash.getNrOfPlayers());
			jLabelRoundsShow.setText("" + cash.getTotalRounds());
			jButtonJoin.setVisible(true);
			jButtonJoin.setEnabled(true);
			preview.setImg(cash.getPreview().getImage());
			super.repaint();
		} else {
			jButtonJoin.setVisible(false);
			jButtonJoin.setEnabled(false);
			super.repaint();
		}

	}

	public void mouseClicked(MouseEvent evt) {

		StartFrame frame = StartFrame.getInstance();
		if (evt.getSource().equals(jButtonStart)) {
			BomberClient.getInstance().requestMapList();
			// System.out.println("Neues Spiel wird gestartet!");
			frame.changePanel(frame.jPanelNewGame);
		} else if (evt.getSource().equals(jButtonJoin)) {
			// TODO Das ausgewaehlte Spiel muss geladen werden
			Config.gameName = "" + jListGames.getSelectedValue();
			SessionDetailsMsg session = (SessionDetailsMsg) jListGames
					.getSelectedValue();

			BomberClient.getInstance().createSession(session);

			frame.changePanel(frame.jPanelWaitForGame);
			// System.out.println("Join Game");
		} else if (evt.getSource().equals(jListGames)) {
			showInfos();
			actionPreformed();
		} else if (evt.getSource().equals((jButtonRefresh))) {
			loadGames();
			// System.out.println("Lade Spiele");
		} else if (evt.getSource().equals(jButtonLogout)) {
			StartFrame.getInstance().logout();
		} else if (evt.getSource().equals(jButtonHightscore)) {

			BomberClient.getInstance().requestGlobalScore();

			StartFrame.getInstance().changePanel(
					StartFrame.getInstance().jPanelGlobalHighscoreLobby);
			

		}
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void actionPreformed() {
		BomberClient.getInstance().requestMapList();
		// StartFrame.init().jPanelNewGame.loadAndDisplayMaps();
		loadSessionInfos();
	}

	public void actionPerformed(ActionEvent evt) {
		StartFrame frame = StartFrame.getInstance();
		if (evt.getSource().equals(jButtonStart)) {
			BomberClient.getInstance().requestMapList();
			// System.out.println("Neues Spiel wird gestartet!");
			frame.changePanel(frame.jPanelNewGame);
		} else if (evt.getSource().equals(jButtonJoin)) {
			// TODO Das ausgewaehlte Spiel muss geladen werden
			Config.gameName = "" + jListGames.getSelectedValue();
			SessionDetailsMsg session = (SessionDetailsMsg) jListGames
					.getSelectedValue();

			BomberClient.getInstance().createSession(session);

			frame.changePanel(frame.jPanelWaitForGame);
			// System.out.println("Join Game");
		} else if (evt.getSource().equals(jListGames)) {
			showInfos();
			actionPreformed();
		} else if (evt.getSource().equals((jButtonRefresh))) {
			loadGames();
			// System.out.println("Lade Spiele");
		} else if (evt.getSource().equals(jButtonLogout)) {
			StartFrame.getInstance().logout();
		} else if (evt.getSource().equals(jButtonHightscore)) {

			BomberClient.getInstance().requestGlobalScore();

			StartFrame.getInstance().changePanel(
					StartFrame.getInstance().jPanelGlobalHighscoreLobby);
			

		}

	}

	public void showInfos() {
		jLabelMap.setVisible(true);
		jLabelPlayers.setVisible(true);
		jLabelPlayersShow.setVisible(true);
		jLabelRounds.setVisible(true);
		jLabelRoundsShow.setVisible(true);
	}

	public void hideInfos() {

		jLabelMap.setVisible(false);
		jLabelPlayers.setVisible(false);
		jLabelPlayersShow.setVisible(false);
		jLabelRounds.setVisible(false);
		jLabelRoundsShow.setVisible(false);
	}

}
