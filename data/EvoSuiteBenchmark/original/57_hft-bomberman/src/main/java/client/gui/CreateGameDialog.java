package client.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

import messages.global.MapInfo;
import client.BomberClient;
import client.gui.components.MyButton;
import client.gui.components.MyPanel;

public class CreateGameDialog extends MyPanel implements ActionListener,
		ItemListener, MouseListener {

	JList jListMap = new JList();
	JTextField jTextFieldRounds = new JTextField();
	JTextField jTextFieldPlayerNumber = new JTextField();
	JTextField jTextFieldName = new JTextField("Game name");

	JLabel jLabelRounds = new JLabel("Rounds:");
	JLabel jLabelPlayerNumber = new JLabel("Playernumber: ");
	JLabel jLabelPrev = new JLabel("Preview");
	ImageCache preview = new ImageCache(null, 400, 270, 300, 225);
	JLabel jLabelName = new JLabel("Game Name:");
	JLabel jLabelMessage = new JLabel(" ");
	
	MyButton jButtonStart = new MyButton("Create Game");
	MyButton jButtonCancel = new MyButton("Cancel");
	MyButton jButtonReturn = new MyButton("Return");

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource().equals(jButtonStart)) {

		}

	}

	public CreateGameDialog() {
		super();

		this.setLayout(null);
		this.setBounds(0, 0, 800, 600);
		// setBackground(new Color(100, 100, 100));

		jLabelRounds.setBounds(20, 20, 130, 25);
		jLabelPlayerNumber.setBounds(20, 100, 130, 25);
		jLabelName.setBounds(20, 200, 130, 25);

		jTextFieldRounds.setBounds(140, 20, 50, 25);
		jTextFieldPlayerNumber.setBounds(140, 100, 50, 25);
		jTextFieldName.setBounds(140, 200, 200, 25);

		jListMap.setBounds(400, 20, 300, 225);
		jLabelPrev.setBounds(400, 250, 170, 110);

		jButtonStart.setBounds(20, 250, 160, 25);
		jButtonStart.setEnabled(false);
		jButtonStart.setVisible(false);
		// jButtonCancel.setBounds(190,195,160,25);
		jButtonReturn.setBounds(190, 250, 160, 25);
		jLabelMessage.setBounds(20, 300, 160, 25);

		// ImageIcon icon = new ImageIcon("C:\\Bombermann\\PrevGruen.png");

		// jLabelPrev.setIcon(icon);
		// jLabelPrev.setBackground(new Color(0, 0, 0));
		// jLabelPrev.setForeground(new Color(255, 255, 255));

		jTextFieldRounds.setText("3");
		jTextFieldPlayerNumber.setText("2");
		jTextFieldName.setText("Game Name");

		jListMap.addMouseListener(this);

		jButtonStart.addMouseListener(this);
		jButtonReturn.addMouseListener(this);

		add(jButtonStart);
		add(jButtonReturn);
		addLabel(jLabelRounds);
		addLabel(jLabelPlayerNumber);
		add(jTextFieldRounds);
		add(jTextFieldPlayerNumber);
		add(jListMap);
		add(jTextFieldName);
		addLabel(jLabelName);
		addLabel(jLabelPrev);
		addLabel(jLabelMessage);
		addImage(preview);
	}

	// Nobody uses this shit
	public Vector<MapInfo> loadMaps() {
		return BomberClient.getInstance().getAvailableMaps();
	}

	// Nobody uses this shit
	public void loadAndDisplayMaps() {
		jListMap.setListData(loadMaps());
		jListMap.setFocusable(true);
		jListMap.setEnabled(true);

	}

	public void itemStateChanged(ItemEvent arg0) {

	}

	public void mouseClicked(MouseEvent evt) {
		if (evt.getSource().equals(jListMap)) {
			if (jListMap.getSelectedIndex() != -1) {
				jLabelMessage.setVisible(false);
				boolean isOk = true;
				int number = -1;
				try {
					number = Integer.parseInt(jTextFieldPlayerNumber.getText());
				} catch (Exception e) {

				}
				Object[] items = jListMap.getSelectedValues();
				for (int i = 0; i < items.length; i++) {
					MapInfo cash = (MapInfo) items[i];
					int aktuellNumber = cash.getMaxPlayers();
					if (aktuellNumber < number) {
						isOk = false;
					}
				}
				if (isOk) {
					jButtonStart.setEnabled(true);
					jButtonStart.setVisible(true);
				}else {
					jLabelMessage.setText("<HTML>At least one of the chosen maps <br>doesn't support " + number + " player");
					jLabelMessage.setVisible(true);

					jButtonStart.setEnabled(false);
					jButtonStart.setVisible(false);
				}

			} else {
				jButtonStart.setEnabled(false);
				jButtonStart.setVisible(false);
			}
			Object o = jListMap.getSelectedValue();
			ImageIcon icon = new ImageIcon();
			icon = (((MapInfo) o).getPreview());
			// draw preview direct to panel
			preview.setImg(icon.getImage());
			super.repaint();
		} else if (evt.getSource().equals(jButtonStart)
				&& jListMap.getSelectedIndex() != -1) {
			int number = -1;
			int rounds = -1;
			try {
				number = Integer.parseInt(jTextFieldPlayerNumber.getText());
				rounds = Integer.parseInt(jTextFieldRounds.getText());
				if (number < 2) {
					number = 2;
				}
				StartFrame frame = StartFrame.getInstance();
				Config.playerNumber = number;
				Config.rounds = rounds;
				Config.gameName = jTextFieldName.getText();

				ArrayList<String> selection = new ArrayList<String>();
				ImageIcon previewIcon = null;
				for (Object o : jListMap.getSelectedValues()) {
					selection.add(((MapInfo) o).getFile());
					// set preview of first map
					if (previewIcon == null) {
						previewIcon = ((MapInfo) o).getPreview();
					}
				}

				BomberClient.getInstance().createSession(Config.gameName,
						selection, previewIcon, Config.playerNumber,
						Config.rounds);
			} catch (Exception e) {
				// TODO Hinweis wegen geht nicht

			}
		} else if (evt.getSource().equals(jButtonReturn)) {
			StartFrame.getInstance().showGlobalLobby();
		}
		repaint();
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
