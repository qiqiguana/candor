package client.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;

import messages.global.InfoRequestMsg;

import client.BomberClient;
import client.gui.components.MyButton;
import client.gui.components.MyPanel;
import db.DBGameUser;
import db.Top10Highscore;

public class GlobalHighscoreLobby extends MyPanel implements MouseListener, ActionListener {

	MyButton jButtonBack = new MyButton("Back");

	JLabel user_01 = new JLabel("");
	JLabel user_02 = new JLabel("");
	JLabel user_03 = new JLabel("");
	JLabel user_04 = new JLabel("");
	JLabel user_05 = new JLabel("");
	JLabel user_06 = new JLabel("");
	JLabel user_07 = new JLabel("");
	JLabel user_08 = new JLabel("");
	JLabel user_09 = new JLabel("");
	JLabel user_10 = new JLabel("");

	JLabel score_01 = new JLabel("");
	JLabel score_02 = new JLabel("");
	JLabel score_03 = new JLabel("");
	JLabel score_04 = new JLabel("");
	JLabel score_05 = new JLabel("");
	JLabel score_06 = new JLabel("");
	JLabel score_07 = new JLabel("");
	JLabel score_08 = new JLabel("");
	JLabel score_09 = new JLabel("");
	JLabel score_10 = new JLabel("");

	JLabel titel = new JLabel("Top 10");
	
	public GlobalHighscoreLobby() {
		super();

//		setTitel("GlobalHighscore");
		setLayout(null);
		setSize(800, 600);
		setFont(new Font("Arial", 0, 24));
		jButtonBack.setBounds(20, 570, 80, 25);
		jButtonBack.addActionListener(this);
		add(jButtonBack);

		titel.setBounds(350, 70, 200, 50);
		
		addLabel(titel);
		
		int offsetx = 200;
		int offsety = 130;

		user_01.setBounds(20 + offsetx, 20 + offsety, 100, 25);
		user_02.setBounds(20 + offsetx, 50 + offsety, 100, 25);
		user_03.setBounds(20 + offsetx, 80 + offsety, 100, 25);
		user_04.setBounds(20 + offsetx, 110 + offsety, 100, 25);
		user_05.setBounds(20 + offsetx, 140 + offsety, 100, 25);
		user_06.setBounds(20 + offsetx, 170 + offsety, 100, 25);
		user_07.setBounds(20 + offsetx, 200 + offsety, 100, 25);
		user_08.setBounds(20 + offsetx, 230 + offsety, 100, 25);
		user_09.setBounds(20 + offsetx, 260 + offsety, 100, 25);
		user_10.setBounds(20 + offsetx, 290 + offsety, 100, 25);

		score_01.setBounds(350 + offsetx, 20 + offsety, 100, 25);
		score_02.setBounds(350 + offsetx, 50 + offsety, 100, 25);
		score_03.setBounds(350 + offsetx, 80 + offsety, 100, 25);
		score_04.setBounds(350 + offsetx, 110 + offsety, 100, 25);
		score_05.setBounds(350 + offsetx, 140 + offsety, 100, 25);
		score_06.setBounds(350 + offsetx, 170 + offsety, 100, 25);
		score_07.setBounds(350 + offsetx, 200 + offsety, 100, 25);
		score_08.setBounds(350 + offsetx, 230 + offsety, 100, 25);
		score_09.setBounds(350 + offsetx, 260 + offsety, 100, 25);
		score_10.setBounds(350 + offsetx, 290 + offsety, 100, 25);

		addLabel(user_01);
		addLabel(user_02);
		addLabel(user_03);
		addLabel(user_04);
		addLabel(user_05);
		addLabel(user_06);
		addLabel(user_07);
		addLabel(user_08);
		addLabel(user_09);
		addLabel(user_10);

		addLabel(score_01);
		addLabel(score_02);
		addLabel(score_03);
		addLabel(score_04);
		addLabel(score_05);
		addLabel(score_06);
		addLabel(score_07);
		addLabel(score_08);
		addLabel(score_09);
		addLabel(score_10);

		// updateHighscore();

	}

	public void fillScore(ArrayList<DBGameUser> user) {

		int index = user.size();
		index = 10 - index;
		if (index < 10) {
			DBGameUser myUser;
			//System.out.println("User: " + user.get(0).getName());
			switch (index) {
			case 0: {
				myUser = user.get(9);
				user_10.setText(myUser.getName());
				score_10.setText("" + myUser.getScore());
			}
			case 1: {
				myUser = user.get(8);
				user_09.setText(myUser.getName());
				score_09.setText("" + myUser.getScore());
			}
			case 2: {
				myUser = user.get(7);
				user_08.setText(myUser.getName());
				score_08.setText("" + myUser.getScore());
			}
			case 3: {
				myUser = user.get(6);
				user_07.setText(myUser.getName());
				score_07.setText("" + myUser.getScore());
			}
			case 4: {
				myUser = user.get(5);
				user_06.setText(myUser.getName());
				score_06.setText("" + myUser.getScore());
			}
			case 5: {
				myUser = user.get(4);
				user_05.setText(myUser.getName());
				score_05.setText("" + myUser.getScore());
			}
			case 6: {
				myUser = user.get(3);
				user_04.setText(myUser.getName());
				score_04.setText("" + myUser.getScore());
			}
			case 7: {
				myUser = user.get(2);
				user_03.setText( myUser.getName());
				score_03.setText("" + myUser.getScore());
			}
			case 8: {
				myUser = user.get(1);
				user_02.setText( myUser.getName());
				score_02.setText("" + myUser.getScore());
			}
			case 9: {
				myUser = user.get(0);
				user_01.setText( myUser.getName());
				score_01.setText("" + myUser.getScore());
			}

			}

		}
		repaint();
	}

	public void updateHighscore() {
		// Top10Highscore hs = new Top10Highscore();
		// fillScore(hs.getPlayerList());
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource().equals(jButtonBack)) {
			StartFrame.getInstance().changePanel(
					StartFrame.getInstance().jPanelGlobalLobby);
		}

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(jButtonBack)) {
			StartFrame.getInstance().changePanel(
					StartFrame.getInstance().jPanelGlobalLobby);
	}

}}