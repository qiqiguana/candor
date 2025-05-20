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

package visu.handball.moves.controller;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputAdapter;

import visu.handball.moves.Main;
import visu.handball.moves.model.HandballModel;
import visu.handball.moves.model.MoveEvent;
import visu.handball.moves.model.PassEvent;
import visu.handball.moves.model.HandballModel.State;
import visu.handball.moves.model.player.Defender;
import visu.handball.moves.model.player.MovePoint;
import visu.handball.moves.model.player.Offender;
import visu.handball.moves.model.player.Player;

/**
 * Controller für Maus-Input auf dem Spielfeld
 * 
 * @author tommy
 */
public class MouseController extends MouseInputAdapter {

	private final static String playerInSixMeter = "Spieler dürfen nicht im Sechsmeter-Raum positioniert werden";

	private final static String moveInSixMeter = "Laufwege dürfen nicht im Sechsmeter-Raum enden";

	private HandballModel model;

	private Player actualPlayer = null;

	private MovePoint acutalMovePoint = null;

	private JInternalFrame frame;

	/**
	 * Konstruktor
	 * 
	 * @param model Model das auf dem Spielfeld angezeigt wird
	 * @param frame InternalFrame des Spielfelds
	 */
	public MouseController(HandballModel model, JInternalFrame frame) {
		this.model = model;
		this.frame = frame;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		State state = model.getState();
		switch (state) {
		case INITIAL:
			break;
		case PLACE_BALL:
			if (e.getButton() == MouseEvent.BUTTON1) {
				Offender ballOwner = model.getNearestOffender(e.getX(), e
						.getY());
				model.setFirstBallOwner(ballOwner);
			}
			break;
		case PLACE_OFFENDERS:
			if (e.getButton() == MouseEvent.BUTTON1) {
				// linker Mausknopf
				if (isInsideSixMeter(e)) {
					insideSixmeterMessage(playerInSixMeter);
					return;
				} else
					model.addOffender(e.getX(), e.getY());
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				// rechter Mausknopf
				Player player = model.getNearestPlayer(e.getX(), e.getY());
				if (player instanceof Offender) {
					deletePlayer(player);
				}
			}
			break;
		case PLACE_DEFENDERS:
			if (e.getButton() == MouseEvent.BUTTON1) {
				// linker Mausknopf
				if (isInsideSixMeter(e)) {
					insideSixmeterMessage(playerInSixMeter);
					return;
				} else
					model.addDefender(e.getX(), e.getY());
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				// rechter Mausknopf
				Player player = model.getNearestPlayer(e.getX(), e.getY());
				if (player instanceof Defender) {
					deletePlayer(player);
				}
			}
			break;
		case EDIT:
			model.setMarkedPlayer(model.getNearestPlayer(e.getX(), e.getY()));
			break;
		case EDIT_EVENT:
			MoveEvent event = model.getActualMoveEvent();
			// PassEvent
			if (event instanceof PassEvent) {
				PassEvent pass = (PassEvent) event;
				if (!event.isDestinationPointSet()) {
					if (Main.getField().insideGoal(e.getX(), e.getY())) {
						pass.setGoalPass(true);
					} else {
						Player player = model.getNearestPlayer(e.getX(), e
								.getY());
						if (player != null && player instanceof Offender) {
							pass.setDestinationPlayer((Offender) player, false);
						} else {
							pass.setDestinationPlayer(null, false);
						}
						pass.setGoalPass(false);
					}
					model.setState(State.EDIT);
				}
			}
			// MoveEvent
			else {
				if (isInsideSixMeter(e) && !event.isDestinationPointSet()) {
					insideSixmeterMessage(moveInSixMeter);
					return;
				}
				if (!event.isDestinationPointSet()) {
					model.setDestinationPointForEvent(event, e.getX(),
							e.getY(), false);
				} else if (!event.isControlPointSet()) {
					model.setControlPointForEvent(event, e.getX(), e.getY(),
							false);
					model.setState(State.EDIT);
				}
			}
			break;
		case ANIMATION:
			break;

		default:
			break;
		}
	}

	private void deletePlayer(Player player) {
		if (model.eventDefinedWithPlayer(player)) {

			int returncode = JOptionPane
					.showConfirmDialog(
							Main.getWindow(),
							"Der Spieler hat noch Events.\nWollen Sie Ihn und die zugeh�rigen/abh�ngigen Events l�schen?",
							"Frage:", JOptionPane.YES_NO_OPTION);
			switch (returncode) {
			case JOptionPane.NO_OPTION:
			case JOptionPane.CLOSED_OPTION:
				// Benutzer will nicht l�schen. Daher nix zu tun.
				return;
			}
		}
		model.removePlayer(player);
	}

	private static void insideSixmeterMessage(String message) {
		JOptionPane.showMessageDialog(Main.getWindow(), message, "Fehler",
				JOptionPane.ERROR_MESSAGE);

	}

	private boolean isInsideSixMeter(MouseEvent e) {
		return Main.getField().insideSixMeter(e.getX(), e.getY());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (!Main.getField().contains(e.getX(), e.getY())) {
			return;
		}
		if (acutalMovePoint != null) {
			MoveEvent actual = model.getActualMoveEvent();
			if (actual instanceof PassEvent) {
				// Torwurf?
				if (Main.getField().insideGoal(e.getX(), e.getY())) {
					Point middle = Main.getField().getGoalMiddle();
					((PassEvent) actual).setGoalPass(true);
					model.setMovePointTo(acutalMovePoint, middle.x, middle.y);
				} else {
					// Abfragen, ob weitere P�sse vorhanden, dann darf Punkt
					// nicht verschoben werden.
					if (model.passEventDefinedAfter(actual.getSequenceNr())) {
						return;
					}
					// versuchen Spieler zu finden
					Player player = model.getNearestPlayer(e.getX(), e.getY());
					if (player != null && player instanceof Offender
							&& !player.equals(actual.getPlayer())) {
						((PassEvent) actual).setDestinationPlayer(
								(Offender) player, false);
						model.setMovePointTo(acutalMovePoint, player
								.getCurrent_x(), player.getCurrent_y());
					} else {
						((PassEvent) actual).setDestinationPlayer(null, false);
						model.setMovePointTo(acutalMovePoint, e.getX(), e
								.getY());
					}
					((PassEvent) actual).setGoalPass(false);
				}
			} else {
				if (isInsideSixMeter(e)
						&& actual.getControlPoint() != acutalMovePoint) {
					return;
				}
				model.setMovePointTo(acutalMovePoint, e.getX(), e.getY());
			}
		} else if (actualPlayer != null
				&& !Main.getField().insideSixMeter(e.getX(), e.getY())) {
			model.movePlayerTo(actualPlayer, e.getX(), e.getY());
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		checkSelectionOfFrame();

		switch (model.getState()) {
		case EDIT_EVENT:
			MoveEvent event = model.getActualMoveEvent();
			if (event instanceof PassEvent) {
				// Passereignis
				if (!event.isDestinationPointSet()) {
					// innerhalb des Tores
					if (Main.getField().insideGoal(e.getX(), e.getY())) {
						Point middle = Main.getField().getGoalMiddle();
						model.setDestinationPointForEvent(event, middle.x,
								middle.y, true);
					} else {
						Player player = model.getNearestPlayer(e.getX(), e
								.getY());
						if (player != null && player instanceof Offender) {
							model.setDestinationPointForEvent(event, player
									.getCurrent_x(), player.getCurrent_y(),
									true);
						} else {
							model.setDestinationPointForEvent(event, e.getX(),
									e.getY(), true);
						}
					}
				}
			} else if (isInsideSixMeter(e) && !event.isDestinationPointSet()) {
				return;
			} else if (!event.isDestinationPointSet()) {
				model.setDestinationPointForEvent(event, e.getX(), e.getY(),
						true);
			} else if (!event.isControlPointSet()) {
				model.setControlPointForEvent(event, e.getX(), e.getY(), true);
			}
			break;

		default:
			model.setHighLightedItem(model.getNearestHighlightableItem(
					e.getX(), e.getY()));
			break;
		}

	}

	private void checkSelectionOfFrame() {
		if (!frame.isSelected()) {
			frame.moveToFront();
			try {
				frame.setSelected(true);
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		acutalMovePoint = model.getNearestMovePoint(e.getX(), e.getY());
		if (acutalMovePoint == null) {
			actualPlayer = model.getNearestPlayer(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		actualPlayer = null;
		acutalMovePoint = null;
	}

}
