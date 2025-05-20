/**
 * [ObjectExplorer4J - Tool zur grafischen Darstellung von Objekten und ihren
 * Referenzen]
 * 
 * Copyright (C) [2009] [PARAGON Systemhaus GmbH]
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, see <http://www.gnu.org/licenses/>.
 **/
package de.paragon.explorer.figure;

/**
 * Klassenbeschreibung:
 * 
 * Instanzvariablen: displayBox: referenziert auf die Ausdehnung der Figur,
 * startFigure: referenziert auf die Zielfigur, endFigure: referenziert auf die
 * EndFigur, Model: referenziert auf das ConnectionModel.
 * 
 * Ein Object dieser Klasse wird referenziert von: ExplorerFigure, zusammen mit
 * allen anderen StandardConnectionFigures und allen List- BoxFigures in der
 * vererbten Instanzvariable figureStore.
 */
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;

import de.paragon.explorer.excp.FigureException;
import de.paragon.explorer.gui.DisplayBox;
import de.paragon.explorer.gui.NullDisplayBox;
import de.paragon.explorer.gui.StandardRectangularDisplayBox;
import de.paragon.explorer.model.ConnectionModel;
import de.paragon.explorer.model.ObjectModel;

public class StandardConnectionFigure extends AbstractFigure implements ConnectionFigure {
	private static final int	NUMBER_3	= 3;
	private static final int	NUMBER_5	= 5;
	private static final int	NUMBER_10	= 10;
	private DisplayBox			displayBox	= new NullDisplayBox();
	private TextBoxFigure		startFigure;
	private TextBoxFigure		endFigure;
	private ConnectionModel		model;

	public StandardConnectionFigure() {
		super();
	}

	// Folgt dem Beispielcode von E.Gamma, allerdings wird nicht direkt auf
	// eine Instanzvariable zugegriffen
	@Override
	protected void basicMoveBy(int x, int y) {
		this.getDisplayBox().translate(x, y);
	}

	@Override
	public void draw(Graphics g) throws FigureException {
		if ((this.getStartFigure() == null) || (this.getEndFigure() == null)) {
			this.setDisplayBox();
			return;
		}
		this.setDisplayBox();
		this.drawStartMarker(g);
		this.drawEndMarker(g);
		this.drawLine(g);
	}

	/** draws triangle pointing to endFigure */
	protected void drawEndMarker(Graphics g) throws FigureException {
		if (this.getEndFigure() == null) {
			return;
		}
		java.awt.Rectangle rect = this.getEndFigure().getDisplayBox().getRectangle();
		int x1 = rect.x;
		int y1 = rect.y + rect.height / 2;
		int x2 = x1 - StandardConnectionFigure.NUMBER_10;
		int y2 = y1 - StandardConnectionFigure.NUMBER_5;
		int x3 = x2;
		int y3 = y1 + StandardConnectionFigure.NUMBER_5;
		java.awt.Polygon triangle = new Polygon();
		triangle.addPoint(x1, y1);
		triangle.addPoint(x2, y2);
		triangle.addPoint(x3, y3);
		g.fillPolygon(triangle);
	}

	protected void drawLine(Graphics g) throws FigureException {
		if ((this.getStartFigure() == null) || (this.getEndFigure() == null)) {
			return;
		}
		java.awt.Rectangle rectStart = this.getDisplayBox().getRectangle();
		java.awt.Rectangle rectEnd = this.getEndFigure().getDisplayBox().getRectangle();
		int x1 = rectStart.x + this.getBubbleRadius();
		int y1 = rectStart.y + this.getBubbleRadius();
		int x2 = rectEnd.x - StandardConnectionFigure.NUMBER_10;
		int y2 = rectEnd.y + rectEnd.height / 2;
		g.drawLine(x1, y1, x2, y2);
	}

	protected void drawStartMarker(Graphics g) {
		if (this.getStartFigure() == null) {
			return;
		}
		int x = this.getDisplayBox().getRectangle().x;
		int y = this.getDisplayBox().getRectangle().y;
		int width = this.getDisplayBox().getRectangle().width;
		int height = this.getDisplayBox().getRectangle().height;
		int diameter = Math.min(width, height);
		g.fillOval(x, y, diameter, diameter);
	}

	/**
	 * In dieser Methode kann der Radius des Kreises der Connection bestimmt und
	 * abgefragt werden. Dieser Wert wird in den Methoden drawLine und
	 * setDisplayBox abgefragt.
	 * 
	 * @return int
	 */
	private int getBubbleRadius() {
		return StandardConnectionFigure.NUMBER_3;
	}

	@Override
	public DisplayBox getDisplayBox() {
		return this.displayBox;
	}

	public TextBoxFigure getEndFigure() {
		return this.endFigure;
	}

	public ConnectionModel getModel() {
		return this.model;
	}

	public TextBoxFigure getStartFigure() {
		return this.startFigure;
	}

	protected void setDisplayBox() throws FigureException {
		if (this.getStartFigure() != null) {
			Rectangle rect;
			if (this.getStartFigure().isVisible()) {
				rect = this.getStartFigure().getDisplayBox().getRectangle();
			}
			else {
				rect = ((ObjectModel) ((ListBoxFigure) this.getStartFigure().getParent()).getModel()).getHeaderModel().getFigure().getDisplayBox().getRectangle();
			}
			int xConn = rect.x + rect.width;
			int yConn = rect.y + rect.height / 2 - this.getBubbleRadius();
			int widthConn = 2 * this.getBubbleRadius();
			int heightConn = 2 * this.getBubbleRadius();
			this.displayBox = new StandardRectangularDisplayBox(xConn, yConn, widthConn, heightConn);
		}
		else {
			this.displayBox = new NullDisplayBox();
		}
	}

	public void setEndFigure(TextBoxFigure newEndFigure) {
		this.endFigure = newEndFigure;
	}

	public void setModel(ConnectionModel newModel) {
		this.model = newModel;
	}

	public void setStartFigure(TextBoxFigure newStartFigure) {
		this.startFigure = newStartFigure;
	}
}
