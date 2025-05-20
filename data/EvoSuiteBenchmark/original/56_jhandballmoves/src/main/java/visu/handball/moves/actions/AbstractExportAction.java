/*
 * Created on 24.10.2006
 * Created by Thomas Halm
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
package visu.handball.moves.actions;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Icon;

import visu.handball.moves.Main;
import visu.handball.moves.model.HandballModel;
import visu.handball.moves.views.DefenderDrawer;
import visu.handball.moves.views.EventDrawer;
import visu.handball.moves.views.FieldDrawer;
import visu.handball.moves.views.OffenderDrawer;

public abstract class AbstractExportAction extends AbstractAction {

	protected HandballModel model;

	protected HandballModel copy;

	public AbstractExportAction(String name, Icon icon, HandballModel model) {
		super(name, icon);
		this.model = model;
	}

	abstract protected void doExport();

	public void actionPerformed(ActionEvent e) {
		createCopy();

		doExport();

	}

	protected void createCopy() {
		try {
			// Kopie des Models benutzen um so Refresh durch setzen des
			// aktuellen Ereignisses zu vermeiden
			copy = new HandballModel();
			copy.initWithLoadedModel((HandballModel) Main.deepCopy(model));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected Graphics2D drawHeader(Graphics graphics, double startX,
			double startY, double pageWidth) {
		Graphics2D g2d = (Graphics2D) graphics;

		g2d.translate(startX, startY);

		BufferedImage logo = null;
		try {
			logo = ImageIO
					.read(Main.class.getResource("images/logo_small.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		g2d.drawImage(logo, null, (int) pageWidth - logo.getWidth(null), 0);

		// Zeichenobjekt in freien Bereich unter dem Logo verschieben
		g2d.translate(0, Math.round(logo.getHeight(null)) + 50);
		return g2d;
	}

	protected void drawSequence(Graphics2D g2d, int sequenzNr) {
		if (copy.getMoveEvents(sequenzNr).size() == 0) {
			copy.setActualMoveEvent(null);
			copy.computePlayerPositions(sequenzNr);
		} else {
			copy.setActualMoveEvent(copy.getMoveEvents(sequenzNr).get(0));
		}
		String sequenz = "Sequenz: " + sequenzNr;
		Font titleFont = new Font("helvetica", Font.BOLD, 24);
		g2d.setFont(titleFont);
		g2d.drawString(sequenz, 0, g2d.getFontMetrics().getHeight());

		g2d.translate(0, 40);

		// Feld zeichnen
		g2d.scale(0.7, 0.7);

		RoundRectangle2D rect = new RoundRectangle2D.Double(0, 0,
				FieldDrawer.PANEL_WIDTH + 20, FieldDrawer.PANEL_HEIGHT + 20,
				10, 10);
		g2d.draw(rect);
		g2d.translate(10, 10);

		FieldDrawer fieldDrawer = new FieldDrawer(Main.getColorModel());
		fieldDrawer.draw(g2d);

		OffenderDrawer offenderDrawer = new OffenderDrawer(copy, Main.getColorModel());
		offenderDrawer.draw(g2d);
		DefenderDrawer defenderDrawer = new DefenderDrawer(copy, Main.getColorModel());
		defenderDrawer.draw(g2d);

		EventDrawer eventDrawer = new EventDrawer(copy, Main.getColorModel(), true);
		eventDrawer.draw(g2d);

		// Kommentar auf erster Seite ausgeben
		if (sequenzNr == 0) {
			g2d.translate(-10, 600);
			g2d.scale(1.0, 1.0);
			g2d.setColor(Color.BLACK);
			
			//Kommentar-Titel
			Font commentTitleFont = new Font("helvetica", Font.BOLD, 12);
			g2d.setFont(commentTitleFont);
			g2d.drawString("Kommentar:", 0, 0);
			g2d.translate(0, g2d.getFontMetrics().getHeight() + 4);
			
			//Kommentar selbst
			String lineSep = System.getProperty("line.separator");
			StringTokenizer st = new StringTokenizer(copy.getComment(), lineSep);
			Font commentFont = new Font("helvetica", Font.TRUETYPE_FONT, 12);
			g2d.setFont(commentFont);
			g2d.setColor(Color.BLACK);
			while (st.hasMoreTokens()) {
				String tmp = st.nextToken();
				g2d.setFont(commentFont);
				g2d.drawString(tmp, 0, 0);
				g2d.translate(0, g2d.getFontMetrics().getHeight() + 4);
			}
		}
	}
}