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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import visu.handball.moves.Main;
import visu.handball.moves.model.HandballModel;
import visu.handball.moves.model.MoveEvent;
import visu.handball.moves.model.PassEvent;
import visu.handball.moves.model.player.Player;

public class HandballModelWriter {

	private static HandballModelWriter instance = null;
	
	private File outputFile;
	private HandballModel model;
	
	private HandballModelWriter() {
		
	}
	
	
	private void setModel(HandballModel model) {
		this.model = model;
	}


	private void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}
	
	public void writeToXml() {
		if (outputFile == null || model == null) {
			return;
		}
		
		if (outputFile.exists()) {
			outputFile.delete();
		}
		
		Element root = new Element("jHandballMoves");
		root.setAttribute("version", Main.getVersion());
		Element modelElement = new Element("moveModel");
		modelElement.setAttribute("name",model.getMoveName());
		root.addContent(modelElement);
		
		//defenders mit id speichern
        savePlayers(modelElement, "defenders", "defender",model.getDefenders());
		
		//attackers mit id speichern
		savePlayers(modelElement, "attackers", "attacker", model.getOffenders());
		
		//events speichern
		List<MoveEvent> events = model.getEvents();
		Element eventsElement = new Element("events");
		for (Iterator<MoveEvent> iter = events.iterator(); iter.hasNext();) {
			MoveEvent event = iter.next();
			Element eventElement = null;
			if (event instanceof PassEvent) {
				PassEvent pass = (PassEvent) event;
				eventElement = new Element("pass");
				String receiver = null;
				if (!pass.isGoalPass()) {
					receiver = String.valueOf(pass.getDestinationPlayer().hashCode());
				} else {
					receiver = "goal";
				}
				eventElement.setAttribute("receiverId", receiver);
				
			} else {
				eventElement = new Element("move");
				eventElement.setAttribute("controllX",String.valueOf(event.getControlPointX()));
				eventElement.setAttribute("controllY",String.valueOf(event.getControlPointY()));
			}
			eventElement.setAttribute("destX",String.valueOf(event.getDestinationX()));
			eventElement.setAttribute("destY",String.valueOf(event.getDestinationY()));
			eventElement.setAttribute("sequence",String.valueOf(event.getSequenceNr()));
			eventElement.setAttribute("delay", String.valueOf(event.getDelay()));
			eventElement.setAttribute("playerId",String.valueOf(event.getPlayer().hashCode()));
			
			eventsElement.addContent(eventElement);
		}
		modelElement.addContent(eventsElement);
		//firstballowner
		Element ballOwnerElement = new Element("ballOwner");
		ballOwnerElement.setAttribute("playerId",String.valueOf(model.getFirstBallOwner() != null ? model.getFirstBallOwner().hashCode() : "none"));
		modelElement.addContent(ballOwnerElement);
		//state, actualevent, ballOwner, ball brauchen nicht gespeichert werden, da initial das erste
		//Event geladen werden soll (Zustand "Bearbeiten")
		//comment speichern
		Element comment = new Element("comment");
		comment.addContent(model.getComment());
		modelElement.addContent(comment);
		
		Document document = new Document(root);
		DocType type = new DocType("jHandballMoves", "jhandballmoves.sourceforge.net", "jhandballmoves.dtd");
		document.setDocType(type);
		
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		try {
			outputter.output(document, new FileOutputStream(outputFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}


	private void savePlayers(Element parent, String listElementName, String playerElementName, List<? extends Player> players) {
		Element playersElement = new Element(listElementName);
		for (Iterator iter = players.iterator(); iter.hasNext();) {
			Player player = (Player) iter.next();
			Element playerElement = new Element(playerElementName);
			playerElement.setAttribute("id", String.valueOf(player.hashCode()));
			playerElement.setAttribute("startX",String.valueOf(player.getStart_x()));
			playerElement.setAttribute("startY",String.valueOf(player.getStart_y()));
			//current muss nicht gespeichert werden da beim Laden Ausgangsposition angezeigt werden soll
			playersElement.addContent(playerElement);
		}
		parent.addContent(playersElement);
	}


	public static HandballModelWriter getInstance(File outputFile, HandballModel model) {
		if (instance == null) {
			instance = new HandballModelWriter();
		}
		instance.setOutputFile(outputFile);
		instance.setModel(model);
		return instance;
	}
	
	
}
