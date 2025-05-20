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

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Rectangle;

import org.apache.log4j.Logger;

import de.paragon.explorer.Explorer;
import de.paragon.explorer.event.DrawEvent;
import de.paragon.explorer.event.DrawListener;
import de.paragon.explorer.event.FocusManager;
import de.paragon.explorer.gui.ExplorerDrawingPanel;
import de.paragon.explorer.gui.ExplorerFrame;
import de.paragon.explorer.gui.Warning;
import de.paragon.explorer.model.ExplorerModel;
import de.paragon.explorer.model.Model;
import de.paragon.explorer.util.LoggerFactory;
import de.paragon.explorer.util.ResourceBundlePurchaser;
import de.paragon.explorer.util.StandardEnumeration;

/**
 * Klassenbeschreibung
 * 
 * Instanzvariablen: frame: Beziehung zum Frame, auf dem die Figur dargestellt
 * wird. model: Verknuepfung mit dem ExplorerModel, das fuer den Inhalt der
 * ExplorerFigure zustaendig ist.
 */
public class ExplorerFigure extends StandardCompositeFigure implements DrawListener {
	private static final int		NUMBER_15										= 15;
	private static final String		EXPLORERFIGURE_ERROR_FINDING_HIT_FIGURE			= "explorerfigure.error_finding_hit_figure ";
	private static final String		EXPLORERFIGURE_ERROR_DRAWING_EXPLORER_FIGURE	= "explorerfigure.error_drawing_explorer_figure";
	private static final String		EXPLORERFIGURE_ERROR_CALCULATING_GRAPHIC_SIZE	= "explorerfigure.error_calculating_graphic_size";
	private static final Logger		logger											= LoggerFactory.make();
	private ExplorerDrawingPanel	panel;
	private Model					model;
	private ExplorerFrame			frame;
	private Dimension				graphicSize;
	private FocusManager			focusManager;

	public ExplorerFigure() {
		super();
	}

	private void calculateGraphicSize() {
		StandardEnumeration figures = null;
		this.getGraphicSize().setSize(0, 0);
		try {
			figures = this.getFigures();
		}
		catch (Exception ex) {
			ExplorerFigure.logger.error(ResourceBundlePurchaser.getMessage(ExplorerFigure.EXPLORERFIGURE_ERROR_CALCULATING_GRAPHIC_SIZE), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ExplorerFigure.EXPLORERFIGURE_ERROR_CALCULATING_GRAPHIC_SIZE));
		}
		if (figures != null) {
			while (figures.hasMoreElements()) {
				try {
					this.calculateGraphicSize(((Figure) figures.nextElement()).getBounds());
				}
				catch (Exception ex) {
					ExplorerFigure.logger.error(ResourceBundlePurchaser.getMessage(ExplorerFigure.EXPLORERFIGURE_ERROR_CALCULATING_GRAPHIC_SIZE), ex);
					Warning.showWarning(ResourceBundlePurchaser.getMessage(ExplorerFigure.EXPLORERFIGURE_ERROR_CALCULATING_GRAPHIC_SIZE));
				}
			}
		}
	}

	private void calculateGraphicSize(Rectangle rec) {
		double xpos;
		double ypos;
		xpos = rec.getX() + rec.getWidth() + ExplorerFigure.NUMBER_15;
		ypos = rec.getY() + rec.getHeight() + ExplorerFigure.NUMBER_15;
		if (this.getGraphicSize().getWidth() > xpos) {
			xpos = this.getGraphicSize().getWidth();
		}
		if (this.getGraphicSize().getHeight() > ypos) {
			ypos = this.getGraphicSize().getHeight();
		}
		this.getGraphicSize().setSize((int) xpos, (int) ypos);
	}

	/**
	 * Kommentar: Das Loeschen einer ExplorerFigure funktioniert so: Zunaechst
	 * wird die Groesse des StandardDrawingFrames bestimmt. Dieses Rechteck wird
	 * dann geloescht.
	 */
	public void clear() {
		this.setDisplayBoxIsValid(false);
		// Dimension size = this.getPanel().getSize();
		// this.getGraphics().clearRect(0, 0, size.width, size.height);
	}

	public void draw(DrawEvent event) {
		try {
			super.draw(event.getGraphics());
		}
		catch (Exception ex) {
			ExplorerFigure.logger.error(ResourceBundlePurchaser.getMessage(ExplorerFigure.EXPLORERFIGURE_ERROR_DRAWING_EXPLORER_FIGURE), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ExplorerFigure.EXPLORERFIGURE_ERROR_DRAWING_EXPLORER_FIGURE));
		}
	}

	private Dimension getActualSize() {
		this.calculateGraphicSize();
		return this.getGraphicSize();
	}

	public FocusManager getFocusManager() {
		if (this.focusManager == null) {
			this.setFocusManager(new FocusManager(this));
		}
		return this.focusManager;
	}

	public FontMetrics getFontMetrics() {
		return this.getFrame().getGraphics().getFontMetrics();
	}

	public ExplorerFrame getFrame() {
		if (this.frame == null) {
			this.setFrame(new ExplorerFrame());
			this.getFrame().setFocusable(true);
			this.getFrame().setName(((ExplorerModel) this.getModel()).getName());
		}
		return this.frame;
	}

	private Dimension getGraphicSize() {
		if (this.graphicSize == null) {
			this.setGraphicSize(new Dimension(0, 0));
		}
		return this.graphicSize;
	}

	/**
	 * Kommentar: Das Loeschen einer ExplorerFigure funktioniert so: Zunaechst
	 * wird die Groesse des StandardDrawingFrames bestimmt. Dieses Rechteck wird
	 * dann geloescht.
	 * 
	 * @return Figure
	 */
	public Figure getListBoxFigureForPoint(Point pt) {
		StandardEnumeration explFigParts = null;
		try {
			explFigParts = this.getFigures();
		}
		catch (Exception ex) {
			ExplorerFigure.logger.error(ResourceBundlePurchaser.getMessage(ExplorerFigure.EXPLORERFIGURE_ERROR_FINDING_HIT_FIGURE), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ExplorerFigure.EXPLORERFIGURE_ERROR_FINDING_HIT_FIGURE));
		}
		Figure tempFig = null;
		Figure hitFig = null;
		if (explFigParts != null) {
			while (explFigParts.hasMoreElements()) {
				tempFig = (Figure) explFigParts.nextElement();
				if (tempFig.containsPoint(pt.x, pt.y)) {
					if (tempFig instanceof ListBoxFigure) {
						hitFig = tempFig;
					}
				}
			}
		}
		return hitFig;
	}

	public Model getModel() {
		return this.model;
	}

	public ExplorerDrawingPanel getPanel() {
		if (this.panel == null) {
			this.setPanel(Explorer.getPanel());
			this.panel.addDrawListener(this);
			this.getFrame().getScrollPane().setViewportView(this.getPanel());
		}
		return this.panel;
	}

	/**
	 * Kommentar: Diese Methode geht folgendermassen vor: Sie sucht sich alle
	 * getroffenen ListBoxFigures raus. Sie durchsucht jede diese Figuren nach
	 * einer getroffenen Teilfigur. Jedes Mal, wenn sie fuendig geworden ist,
	 * ueberschreibt sie die Variable fuer die Figur, die sie am Ende
	 * zurueckgibt. Da die Figuren in derselben Figur durchsucht werden, wie sie
	 * auch gezeichnet werden, gibt sie die oberste, getroffene Figur zurueck.
	 */
	public Figure getTextBoxFigureForPoint(Point pt) {
		StandardEnumeration explFigParts = null;
		try {
			explFigParts = this.getFigures();
		}
		catch (Exception ex) {
			ExplorerFigure.logger.error(ResourceBundlePurchaser.getMessage(ExplorerFigure.EXPLORERFIGURE_ERROR_FINDING_HIT_FIGURE), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ExplorerFigure.EXPLORERFIGURE_ERROR_FINDING_HIT_FIGURE));
		}
		Figure tempFig = null;
		Figure hitFig = null;
		if (explFigParts != null) {
			while (explFigParts.hasMoreElements()) {
				tempFig = (Figure) explFigParts.nextElement();
				if (tempFig.containsPoint(pt.x, pt.y)) {
					if (tempFig instanceof ListBoxFigure) {
						hitFig = ((ListBoxFigure) tempFig).getTextBoxFigureForPoint(pt);
					}
				}
			}
		}
		return hitFig;
	}

	public void repaint() {
		this.getPanel().setPreferredSize(this.getActualSize());
		this.getPanel().revalidate();
		this.getPanel().repaint();
	}

	private void setFocusManager(de.paragon.explorer.event.FocusManager newFocusManager) {
		this.focusManager = newFocusManager;
	}

	public void setFrame(ExplorerFrame newFrame) {
		this.frame = newFrame;
	}

	private void setGraphicSize(Dimension newGraphicSize) {
		this.graphicSize = newGraphicSize;
	}

	public void setModel(Model modl) {
		this.model = modl;
	}

	public void setPanel(ExplorerDrawingPanel newPanel) {
		this.panel = newPanel;
	}
}
