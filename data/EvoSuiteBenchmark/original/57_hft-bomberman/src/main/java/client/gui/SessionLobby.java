package client.gui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.apache.log4j.Logger;

import client.BomberClient;
import client.ClientGameSession;
import client.ClientPlayer;
import client.gui.components.MyButton;
import client.gui.components.MyPanel;

public class SessionLobby extends MyPanel implements MouseListener, ActionListener {

	/**
	 * @param args
	 */

	private static final Logger logger = Logger.getLogger(HighscoreLobby.class);

	private Chat chat;

	public MyButton leave = new MyButton("back");

	JLabel jLabelMap = new JLabel("Map:");
	JLabel jLabelPlayers = new JLabel("Players:");
	JLabel jLabelPlayersShow = new JLabel("-0/-0");
	JLabel jLabelRounds = new JLabel("Rounds:");
	JLabel jLabelRoundsShow = new JLabel("-1");

	ImageCache preview = new ImageCache(null, 120, 180, 120, 90);

	public SessionLobby() {
		super();

		leave.addActionListener(this);
		setLayout(null);
		setBounds(0, 0, 800, 600);
		chat = new Chat(400, 600);
		chat.setType(chat.SESSION);
		chat.setBounds(400, 0, 400, 600);
		leave.setBounds(20, 20, 150, 25);
		add(chat);
		add(leave);
		this.setTitel("Wait for game to start!");

		jLabelMap.setBounds(20, 170, 50, 25);
		jLabelPlayers.setBounds(20, 310, 50, 25);
		jLabelPlayersShow.setBounds(120, 310, 150, 150);
		jLabelRounds.setBounds(20, 440, 50, 25);
		jLabelRoundsShow.setBounds(120, 440, 50, 25);

		updateInfo();

		addLabel(jLabelMap);
		addLabel(jLabelPlayers);
		addLabel(jLabelPlayersShow);
		addLabel(jLabelRounds);
		addLabel(jLabelRoundsShow);
		addImage(preview);
	}

	public void updateInfo() {
		ClientGameSession session = BomberClient.getInstance()
				.getCurrentSession();
		if (session != null) {
			Map<Integer, String> playerNames = session.getPlayerNames();
			jLabelRoundsShow.setText("" + session.getTotalRounds());
			Iterator<Entry<Integer, String>>iter = playerNames.entrySet().iterator();
			String playerString = new String("<HTML>");
			int num = 0;
			while (iter.hasNext()) {
				Entry<Integer, String> entry = (Entry<Integer, String>) iter.next();
				playerString += entry.getValue() + "<br>";
				num++;
			}

			jLabelPlayersShow.setText(playerString + "<br>" + num + " / "
					+ session.getTotalNrOfPlayers() + "");
			repaint();
		}
		// show preview
		if (session != null) {
			preview.setImg(session.getPreview().getImage());
		}
	}

	/**
	 * @return
	 */
	public Chat getChat() {
		return chat;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(leave)) {
			logger.info("leave --> SessionLobby");
			
			BomberClient.getInstance().leaveSession();
			StartFrame.getInstance().showGlobalLobby();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(leave)) {
			logger.info("leave --> SessionLobby");
			
			BomberClient.getInstance().leaveSession();
			StartFrame.getInstance().showGlobalLobby();
		}
		
	}

}
