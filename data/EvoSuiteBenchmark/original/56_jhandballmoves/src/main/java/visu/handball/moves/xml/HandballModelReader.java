/*
 * Created on 03.05.2007
 * Created by Thomas Halm
 * Copyright (C) 2007 Richard Doerfler, Thomas Halm
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
package visu.handball.moves.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import visu.handball.moves.Main;
import visu.handball.moves.model.HandballModel;
import visu.handball.moves.model.MoveEvent;
import visu.handball.moves.model.PassEvent;
import visu.handball.moves.model.player.Defender;
import visu.handball.moves.model.player.Offender;
import visu.handball.moves.model.player.Player;

public class HandballModelReader {

	private static HandballModelReader instance = null;

	private InputStream inputStream;

	private File inputFile;

	private HandballModelReader() {

	}

	public static HandballModelReader getInstance(File inputFile) {
		if (instance == null) {
			instance = new HandballModelReader();
		}
		instance.setInputFile(inputFile);
		instance.setInputStream(null);
		return instance;
	}

	public static HandballModelReader getInstance(InputStream inputStream) {
		if (instance == null) {
			instance = new HandballModelReader();
		}
		instance.setInputStream(inputStream);
		instance.setInputFile(null);
		return instance;
	}

	public File getInputFile() {
		return inputFile;
	}

	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
	}

	public HandballModel readFromXml() {
		HandballModel model = new HandballModel();
		model.initModel();

		Document doc = null;

		SAXBuilder sb = new SAXBuilder();
		sb.setEntityResolver(new EntityResolver() {
			public InputSource resolveEntity(String publicId, String systemId)
					throws SAXException, IOException {
				if (publicId.equals("jhandballmoves.sourceforge.net")) {
					return new InputSource(Main.getResource(
							"xml/dtd/jhandballmoves.dtd").openStream());
				}
				return null;
			}
		});
		sb.setValidation(true);
		try {
			if (inputStream != null) {
				doc = sb.build(inputStream);
			} else {
				doc = sb.build(inputFile);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(Main.getWindow(),
					"Fehler beim Laden der XML-Datei\n"
							+ e.getLocalizedMessage(), "Laden fehlgeschlagen",
					JOptionPane.ERROR_MESSAGE);
		}
		if (doc != null) {
			Element root = doc.getRootElement();

			// TODO Versions-Test z.B. >= 1.0.5

			Element moveModel = root.getChild("moveModel");
			model.setMoveName(moveModel.getAttributeValue("name"));
			Map<String, Player> map = new HashMap<String, Player>();

			// Defenders laden
			Element defenders = moveModel.getChild("defenders");
			for (Iterator iter = defenders.getChildren("defender").iterator(); iter
					.hasNext();) {
				Element element = (Element) iter.next();
				int startX = Integer.valueOf(element
						.getAttributeValue("startX"));
				int startY = Integer.valueOf(element
						.getAttributeValue("startY"));
				Defender def = new Defender(startX, startY);
				map.put(element.getAttributeValue("id"), def);
				model.getDefenders().add(def);
			}
			// Attackers laden
			Element attackers = moveModel.getChild("attackers");
			for (Iterator iter = attackers.getChildren("attacker").iterator(); iter
					.hasNext();) {
				Element element = (Element) iter.next();
				int startX = Integer.valueOf(element
						.getAttributeValue("startX"));
				int startY = Integer.valueOf(element
						.getAttributeValue("startY"));
				Offender attacker = new Offender(startX, startY);
				map.put(element.getAttributeValue("id"), attacker);
				model.getOffenders().add(attacker);
			}
			// Events laden
			Element events = moveModel.getChild("events");
			for (Iterator iter = events.getChildren().iterator(); iter
					.hasNext();) {
				Element element = (Element) iter.next();
				Player player = map.get(element.getAttributeValue("playerId"));
				int seq = Integer
						.valueOf(element.getAttributeValue("sequence"));
				int delay = Integer.valueOf(element.getAttributeValue("delay"));
				MoveEvent event = null;
				if (element.getName().equals("pass")) {
					// Passevent laden
					event = new PassEvent(player, seq, delay);
					if (element.getAttributeValue("receiverId").equals("goal")) {
						((PassEvent) event).setGoalPass(true);
					} else {
						Offender off = (Offender) map.get(element
								.getAttributeValue("receiverId"));
						((PassEvent) event).setDestinationPlayer(off, false);
						event.setDestinationPoint(off.getCurrent_x(), off
								.getCurrent_y(), false);
					}
				} else if (element.getName().equals("move")) {
					// Laufevent laden
					event = new MoveEvent(player, seq, delay);
					int controlX = Integer.valueOf(element
							.getAttributeValue("controllX"));
					int controlY = Integer.valueOf(element
							.getAttributeValue("controllY"));

					event.setControlPoint(controlX, controlY, false);
				}
				int destX = Integer.valueOf(element.getAttributeValue("destX"));
				int destY = Integer.valueOf(element.getAttributeValue("destY"));
				event.setDestinationPoint(destX, destY, false);
				model.addMoveEvent(event);
			}
			Element firstOwner = moveModel.getChild("ballOwner");
			if (!firstOwner.getAttributeValue("playerId").equals("none")) {
				model.setFirstBallOwner((Offender) map.get(firstOwner
						.getAttributeValue("playerId")));
			}

			Element comment = moveModel.getChild("comment");
			model.setComment(comment.getText());
			model.setState(HandballModel.State.EDIT);
			if (model.getEvents().size() > 0) {
				model.setActualMoveEvent(model.getEvents().get(0));
			}
		}
		return model;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
