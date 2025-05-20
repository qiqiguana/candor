/*
 * Created on 31.08.2006
 * Created by Richard Doerfler, Thomas Halm
 * Copyright (C) 2006  Richard Doerfler, Thomas Halm
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package visu.handball.moves.views;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import visu.handball.moves.Main;
import visu.handball.moves.model.HandballModel;
import visu.handball.moves.model.HandballModelListener;
import visu.handball.moves.model.MoveEvent;
import visu.handball.moves.model.PassEvent;
import visu.handball.moves.model.HandballModel.State;
import visu.handball.moves.model.player.Defender;
import visu.handball.moves.model.player.HighlightableItem;
import visu.handball.moves.model.player.Offender;
import visu.handball.moves.model.player.Player;

public class StatusBar extends JPanel implements MouseMotionListener,
		HandballModelListener {

	private HandballModel handballModel;

	private static final String COORDINATES_TEXT = "Position: ";

	private static final String INFO_TEXT = "Info: ";

	private static final String PLAYER_TEXT = "Spieler: ";

	private JLabel coordinates;

	private JLabel info;

	private JLabel player;

	public StatusBar(HandballModel handballModel) {
		this.handballModel = handballModel;
		handballModel.addListener(this);

		info = new JLabel("", JLabel.LEFT);
		coordinates = new JLabel("0,0", JLabel.LEFT);
		coordinates
				.setPreferredSize(new Dimension(100, coordinates.getHeight()));
		player = new JLabel("", JLabel.LEFT);
		player.setPreferredSize(new Dimension(80, player.getHeight()));
		this.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 5, 0, 0);
		;
		c.gridx = 0;
		c.gridy = 0;
		// c.anchor = GridBagConstraints.WEST;
		c.weightx = 0.5;
		this.add(info, c);
		c.weightx = 0;
		c.gridx = 1;
		this.add(player, c);
		c.gridx = 2;
		this.add(coordinates, c);
	}

	private void setCoordinates(int x, int y) {
		coordinates.setText(COORDINATES_TEXT + x + "," + y);
	}

	private void setInfo(String newInfo) {
		info.setText(INFO_TEXT + newInfo);
	}

	private void setPlayer(int number) {
		player.setText(PLAYER_TEXT + number + "");
	}

	private void setPlayer() {
		player.setText(PLAYER_TEXT);
	}

	public void modelChanged() {
		State state = handballModel.getState();
		HighlightableItem item = handballModel.getHightligtedItem();

		switch (state) {
		case INITIAL:
			setInfo("Modus auswählen...");
			break;
		case PLACE_OFFENDERS:
			if (item != null && item instanceof Offender) {
				setInfo(item + " mit rechter Maustaste löschen");
			} else {
				setInfo("Neuen Angreifer mit linker Maustaste positionieren...");
			}
			break;
		case PLACE_DEFENDERS:
			if (item != null && item instanceof Defender) {
				setInfo(item + " mit rechter Maustaste löschen");
			} else {
				setInfo("Neuen Verteidiger mit linker Maustaste positionieren...");
			}
			break;
		case PLACE_BALL:
			if (item != null && item instanceof Offender) {
				setInfo("Klicken um Ball " + item + " zuzuweisen");
			} else {
				setInfo("Angreifer anklicken, um Ball zu positionieren...");
			}
			break;
		case EDIT:
			if (item != null && item instanceof Player
					&& handballModel.getMarkedPlayer() != item) {
				setInfo("Klicken um " + item + " zu markieren");
			} else if (handballModel.getMarkedPlayer() != null) {
				setInfo(handballModel.getMarkedPlayer() + " ist markiert");
			} else {
				setInfo("Spieler markieren um für diesen ein Ereignis anzulegen...");
			}
			break;
		case EDIT_EVENT:
			MoveEvent actual = handballModel.getActualMoveEvent();
			if (actual != null && actual instanceof PassEvent) {
				PassEvent pass = (PassEvent) actual;
				if (!pass.isDestinationPointSet()
						&& pass.getDestinationPoint() != null) {
					Player near = handballModel.getNearestPlayer(pass
							.getDestinationX(), pass.getDestinationY());
					if (near != null && near instanceof Offender
							&& near != actual.getPlayer()) {
						setInfo(actual.getPlayer() + ": Pass zu " + near
								+ " definieren");
						break;
					} else if (Main.getField().insideGoal(
							pass.getDestinationX(), pass.getDestinationY())) {
						setInfo(actual.getPlayer() + ": Torwurf definieren");
						break;
					}
				}
				setInfo(actual.getPlayer() + ": Pass definieren");
			} else if (actual != null) {
				if (!actual.isDestinationPointSet()) {
					setInfo(actual.getPlayer()
							+ ": Zielpunkt des Laufwegs festlegen...");
				} else if (!actual.isControlPointSet()) {
					setInfo(actual.getPlayer()
							+ ": Krümmung des Laufwegs festlegen...");
				}
			} else {
				setInfo("Laufwege zeichnen...");
			}
			break;
		case ANIMATION:
			int seq = handballModel.getAcutalSequenceNr();
			setInfo("Spielzug-Animation: Sequenz " + seq + " ausgewählt");
			break;

		default:
			setInfo("");
			break;
		}

		// Spielernummer des markierten Spielers ausgeben
		Player player = handballModel.getMarkedPlayer();
		if (player != null) {
			setPlayer(player.getPlayerNumber());
		} else
			setPlayer();
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		setCoordinates(e.getX(), e.getY());
	}

}
