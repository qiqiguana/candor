package client.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JTable;

import org.apache.log4j.Logger;

import messages.session.SessionStateMsg;
import client.BomberClient;
import client.ClientGameLoop;
import client.gui.components.MyButton;
import client.gui.components.MyPanel;

/**
 * @author tobias
 * lobby between two rounds
 *
 */
public class HighscoreLobby extends MyPanel implements MouseListener,ActionListener {

	String[] columnNames = { "Player", "Score" };
	String[][] rowData;
	String[][] rowSessionData;
	private static final Logger logger = Logger.getLogger(HighscoreLobby.class);

	public MyButton jButtonNextRound = new MyButton("Ready for next round");
	Chat chat;
	JLabel sessionScoreLabel;
	JTable jTableScore;
	JTable jTableSessionScore;
	public MyButton jButtonLogout = new MyButton("quit session");
	public JLabel jLabelGameOver = new JLabel("Game over!");

	public HighscoreLobby() {
		this.setSize(800, 600);
		setLayout(null);
		jButtonNextRound.setBounds(20, 240, 250, 25);
		jButtonNextRound.addActionListener(this);
		jButtonLogout.setBounds(10, 570, 150, 25);
		jLabelGameOver.setBounds(120, 480, 150, 80); //380
		jLabelGameOver.setVisible(false);
		jButtonLogout.addActionListener(this);
		add(jButtonNextRound);
		add(jButtonLogout);
		chat = new Chat(400, 600);

		chat.setType(chat.SESSION);
		chat.setBounds(400, 0, 400, 600);
		add(chat);
		setFont(new Font("Arial", 0, 24));
		addLabel(jLabelGameOver);
	}

	private void setTable(String[][] nameForScore) {
		
		logger.info("setting table");
		if(jTableScore!=null){
			remove(jTableScore);
		}
		rowData = nameForScore;
		JLabel label2 = new JLabel("Round Scores");
		label2.setBounds(20, 10, 300, 30);
		jTableScore = new JTable(rowData, columnNames);
		jTableScore.setBounds(20, 50, 300, 150);
		add(jTableScore);
		addLabel(label2);
		this.repaint();
	}

	public void setTable(HashMap<String, Integer> rndscore){
		outScores(rndscore);
		String[][] rndscorearr = new String[rndscore.size()][2];
		int index = 0;
		for (String key : rndscore.keySet()) {
			rndscorearr[index][0]=key;
			rndscorearr[index][1]=rndscore.get(key).toString();
			index++;
			
		}
		setTable(rndscorearr);
	}
	
	private void outScores(HashMap<String, Integer> rndscore) {
		logger.info("GAMEROUND SCORES");
		for (Entry entry : rndscore.entrySet()) {
			logger.info(entry.getKey() + " " + entry.getValue());
		}
		logger.info("/GAMEROUND SCORES");
	}
	public void setSessionTable(HashMap<String, Integer> rndscore){
		outScores(rndscore);
		String[][] rndscorearr = new String[rndscore.size()][2];
		int index = 0;
		for (String key : rndscore.keySet()) {
			rndscorearr[index][0]=key;
			rndscorearr[index][1]=rndscore.get(key).toString();
			index++;
			
		}
		setSessionTable(rndscorearr);
	}
	

	private void setSessionTable(String[][] nameForScore) {
		removeSessionTable();
		logger.info("setting session table");
		logger.info("setting sessionscore");
		sessionScoreLabel = new JLabel("Session Scores");
		sessionScoreLabel.setBounds(20, 210, 300, 20);
		rowSessionData = nameForScore;
		jTableSessionScore = new JTable(rowSessionData, columnNames);
		jTableSessionScore.setBounds(20, 240, 300, 150);
		add(jTableSessionScore);
		addLabel(sessionScoreLabel);
		this.repaint();
	}

	public void removeSessionTable() {
		if (jTableSessionScore != null) {
			remove(jTableSessionScore);
			this.repaint();
		}
		if(sessionScoreLabel!=null){
			remove(sessionScoreLabel);
			this.repaint();
			
		}
	}

	public void removeTable() {
		if (jTableScore != null) {
			remove(jTableScore);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(jButtonNextRound)) {
			BomberClient.getInstance().sendMsg(
					new SessionStateMsg(SessionStateMsg.READY_FOR_NEXT_ROUND));
			jButtonNextRound.setVisible(false);
			repaint();
		} else if (e.getSource().equals(jButtonLogout)) {
			// TODO Quit Session
			logger.info("leave --> HighScoreLobby");
			BomberClient.getInstance().leaveSession();
			StartFrame.getInstance().changePanel(
					StartFrame.getInstance().jPanelGlobalLobby);
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
		if (e.getSource().equals(jButtonNextRound)) {
			BomberClient.getInstance().sendMsg(
					new SessionStateMsg(SessionStateMsg.READY_FOR_NEXT_ROUND));
			jButtonNextRound.setVisible(false);
			repaint();
		} else if (e.getSource().equals(jButtonLogout)) {
			// TODO Quit Session
			logger.info("leave --> HighScoreLobby");
			BomberClient.getInstance().leaveSession();
			StartFrame.getInstance().changePanel(
					StartFrame.getInstance().jPanelGlobalLobby);
		}
		
	}

}
