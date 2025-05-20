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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import de.paragon.explorer.model.Model;

/**
 * Klassenbeschreibung:
 * 
 * Instanzvariablen: text: Der String, der innerhalb der TextBoxFigure darge-
 * stellt werden soll, model: Das Model, das inhaltliche Aspekte der Figure
 * haelt. Im Falle des Explorers z.Bsp. sind dies: a) ObjectHeaderModel, falls
 * die TextBoxFigure den Namen des Objektes darstellt, b) AttributeModel, falls
 * die TextBoxFigure den Namen ei- nes Feldes des Objektes darstellt. visible:
 * Eine Variable des Typs boolean, die angibt, ob die Figur bei Zeichnung
 * sichtbar oder unsichtbar ist.
 * 
 * Ein Objekt dieser Klasse wird referenziert von: StandardConnectionFigure (als
 * startFigure): Die Connection- figur, die auf eine andere Figur zeigt. In
 * diesem Falle ist das Model dieser Figur ein AttributeModel,
 * StandardConnectionFigure (als endFigure): Die Connection- figure, die von
 * einer anderen Figur auf diese zeigt. In diesem Falle ist das Model ein
 * ObjectHeaderModel.
 */
public class TextBoxFigure extends ColorRectangleFigure {
	private static final int	NUMBER_3	= 3;
	private String				text		= "";
	private Model				model;
	private boolean				visible		= false;
	private java.awt.Font		font;
	private java.awt.Color		foreground;

	public TextBoxFigure() {
		super();
	}

	/**
	 * provisorische Einbettung des Textes in das umgebende Rechteck Kommentar:
	 * Das Zeichnen einer Figur wird abhaengig gemacht von "visible". Falls
	 * "visible", wird zunaechst der Rahmen und dann der Text gezeichnet.
	 */
	@Override
	public void draw(Graphics g) {
		if (this.isVisible()) {
			super.draw(g);
			int leftMargin = TextBoxFigure.NUMBER_3;
			int bottomHeight = 1;
			String localtext = this.getText();
			int descent = g.getFontMetrics(this.getFont()).getMaxDescent();
			Rectangle rect = this.getDisplayBox().getRectangle();
			int x = rect.x + leftMargin;
			int y = rect.y + rect.height - descent - bottomHeight;
			Font localFont = g.getFont();
			g.setFont(this.getFont());
			Color color = g.getColor();
			g.setColor(this.getForeground());
			g.drawString(localtext, x, y);
			g.setFont(localFont);
			g.setColor(color);
		}
	}

	public Font getFont() {
		return this.font;
	}

	public Color getForeground() {
		return this.foreground;
	}

	public Model getModel() {
		return this.model;
	}

	public String getText() {
		return this.text;
	}

	@Override
	public boolean isVisible() {
		return this.visible;
	}

	public void setFont(Font newFont) {
		this.font = newFont;
	}

	public void setForeground(Color newForeground) {
		this.foreground = newForeground;
	}

	public void setModel(Model newModel) {
		this.model = newModel;
	}

	public void setText(String newText) {
		this.text = newText;
	}

	public void setUnvisible() {
		this.visible = false;
	}

	public void setVisible() {
		this.visible = true;
	}
}
