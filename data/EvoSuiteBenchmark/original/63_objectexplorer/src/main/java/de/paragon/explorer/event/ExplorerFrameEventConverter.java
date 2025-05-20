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
package de.paragon.explorer.event;

import java.awt.CheckboxMenuItem;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.KeyboardFocusManager;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JMenuItem;

import de.paragon.explorer.Explorer;
import de.paragon.explorer.ProjectConstants4ObjectExplorer;
import de.paragon.explorer.figure.ExplorerFieldListBoxFigure;
import de.paragon.explorer.figure.ExplorerFigure;
import de.paragon.explorer.figure.Figure;
import de.paragon.explorer.figure.ListBoxFigure;
import de.paragon.explorer.figure.TextBoxFigure;
import de.paragon.explorer.gui.ExplorerDrawingPanel;
import de.paragon.explorer.gui.ExplorerFrame;
import de.paragon.explorer.model.AttributeModel;
import de.paragon.explorer.model.ExplorerModel;
import de.paragon.explorer.model.ObjectHeaderModel;
import de.paragon.explorer.model.ObjectModel;
import de.paragon.explorer.popup.AttributePopupMenu;
import de.paragon.explorer.popup.HeaderPopupMenu;
import de.paragon.explorer.util.ExplorerManager;

/**
 * Kommentar: Diese Klasse empfaengt "rohe" Events wie Mausklicks oder
 * PopupMenu-Events vom Frame. Ihre Aufgabe besteht darin, alle noetigen
 * Informationen bei Eintreffen eines Events zu sammeln und damit ein neues
 * Explorer-Event zu basteln und an den ExplorerEventConverter weiterzusenden.
 * Sammeln heisst hierbei, sich den Punkt bei MousePressed zu merken, daraus die
 * getroffene Figur zu ermitteln und sich diese zu merken und gegebenen- falls
 * weitere Events (z.B. beim Frame im Falle des PopupMenus) anzufordern.
 * Kommentar zu den popups: Fuer die popups gibt es keine Zugriffsmethoden. Die
 * einzige Zuweisung erfolgt in der Variablendeklaration unten. Der einzige
 * Zugriff auf die Variablen erfolgt in der Methode getPopup.
 */
public class ExplorerFrameEventConverter extends WindowAdapter implements ActionListener, ItemListener, MouseListener, MouseMotionListener, KeyListener {
	private static final int				NUMBER_10	= 10;
	// private static final Logger logger =
	// Logger.getLogger(ExplorerFrameEventConverter.class);
	// private static boolean removeDependencies = true;
	// /**
	// * @return Returns the removeDependencies.
	// */
	// public static boolean removeDependencies() {
	// return ExplorerFrameEventConverter.removeDependencies;
	// }
	private ExplorerFigure					figure;
	private Point							mouseDownAt;
	private TextBoxFigure					actualTextBoxFigure;
	private ListBoxFigure					actualListBoxFigure;
	private HeaderPopupMenu					headerPopup;
	private AttributePopupMenu				attributePopup;
	// private boolean popupMenuRequested = false;
	private AboutDialogEventConverter		aboutDialogEventConverter;
	private ObjectCopyDialogEventConverter	objectCopyDialogEventConverter;
	private ObjectViewDialogEventConverter	objectViewDialogEventConverter;

	public ExplorerFrameEventConverter(ExplorerFigure explFig) {
		super();
		this.setFigure(explFig);
		this.initialize();
	}

	public void actionPerformed(ActionEvent event) {
		String actionCommandFromEvent = event.getActionCommand();
		ListBoxFigure actualListBoxFigureLocal = this.getActualListBoxFigure();
		if (actualListBoxFigureLocal != null) {
			if (ProjectConstants4ObjectExplorer.getHIDE_OBJECT().equals(actionCommandFromEvent)) {
				this.getExplorerFigure().getFocusManager().clearFocus();
				this.getExplorerPopupActionConverter().removeListBoxFigure(actualListBoxFigureLocal);
				this.setActualListBoxFigure(null);
			}
			else if (ProjectConstants4ObjectExplorer.getHIDE_OBJECT_WITH_DEPENDENCIES().equals(actionCommandFromEvent)) {
				this.getExplorerFigure().getFocusManager().clearFocus();
				if (actualListBoxFigureLocal instanceof ExplorerFieldListBoxFigure) {
					Iterator<ExplorerFieldListBoxFigure> it = ((ExplorerFieldListBoxFigure) actualListBoxFigureLocal).getChildren().iterator();
					while (it.hasNext()) {
						ExplorerFieldListBoxFigure aChild = (it.next());
						aChild.removeAllChildren(this.getExplorerPopupActionConverter());
						this.getExplorerPopupActionConverter().removeListBoxFigure(aChild);
					}
				}
				this.getExplorerPopupActionConverter().removeListBoxFigure(actualListBoxFigureLocal);
				this.setActualListBoxFigure(null);
			}
			else if (ProjectConstants4ObjectExplorer.getEXPLORE_OBJECT().equals(actionCommandFromEvent)) {
				this.getExplorerPopupActionConverter().exploreObjectOf(actualListBoxFigureLocal);
			}
			else if (ProjectConstants4ObjectExplorer.getCOPY_OBJECT().equals(actionCommandFromEvent)) {
				this.getObjectCopyDialogEventConverter().copyObject(this.getMouseDownAt(), actualListBoxFigureLocal);
			}
			else if (ProjectConstants4ObjectExplorer.getOBJECT_VIEW().equals(actionCommandFromEvent)) {
				if (actualListBoxFigureLocal != null) {
					this.getObjectViewDialogEventConverter().initializeDialog(actualListBoxFigureLocal, false);
				}
				this.getObjectViewDialogEventConverter().showDialog();
			}
			else if (ProjectConstants4ObjectExplorer.getUPDATE_OBJECT().equals(actionCommandFromEvent)) {
				this.updateObject();
			}
			else if (ProjectConstants4ObjectExplorer.getUPDATE_OBJECTS().equals(actionCommandFromEvent)) {
				this.handleUpdateObjects();
			}
			else if (ProjectConstants4ObjectExplorer.getHIDE_ATTRIBUTE().equals(actionCommandFromEvent)) {
				this.getExplorerPopupActionConverter().setSingleAttributeUnvisibleOf(this.getActualTextBoxFigure());
			}
			else if (ProjectConstants4ObjectExplorer.getUPDATE_ATTRIBUTES().equals(actionCommandFromEvent)) {
				this.getExplorerActionConverter().updateAttribute((AttributeModel) this.getActualTextBoxFigure().getModel());
				this.getExplorerActionConverter().updateObject(actualListBoxFigureLocal);
			}
			else if (ProjectConstants4ObjectExplorer.getSHOW_REFERENCE().equals(actionCommandFromEvent)) {
				this.getExplorerActionConverter().showInForeground(this.getActualTextBoxFigure());
			}
			else if (ProjectConstants4ObjectExplorer.getHIDE_REFERENCE().equals(actionCommandFromEvent)) {
				this.getExplorerPopupActionConverter().removeConnection(this.getActualTextBoxFigure());
			}
			else if (ProjectConstants4ObjectExplorer.getSAVE().equals(actionCommandFromEvent)) {
				this.getExplorerActionConverter().saveAttribute((ObjectHeaderModel) (this.getActualTextBoxFigure().getModel()));
			}
		}
		if (ProjectConstants4ObjectExplorer.getABOUT().equals(actionCommandFromEvent)) {
			this.getAboutDialogEventConverter().showDialog();
		}
		// this.setPopupMenuNotRequested();
	}

	private AboutDialogEventConverter getAboutDialogEventConverter() {
		if (this.aboutDialogEventConverter == null) {
			this.setAboutDialogEventConverter(new AboutDialogEventConverter(this.getExplorerFigure()));
		}
		return this.aboutDialogEventConverter;
	}

	private ListBoxFigure getActualListBoxFigure() {
		return this.actualListBoxFigure;
	}

	private TextBoxFigure getActualTextBoxFigure() {
		return this.actualTextBoxFigure;
	}

	private AttributePopupMenu getAttributePopupMenu() {
		if (this.attributePopup == null) {
			this.attributePopup = new AttributePopupMenu();
		}
		return this.attributePopup;
	}

	private ExplorerActionConverter getExplorerActionConverter() {
		return ExplorerActionConverter.getInstance();
	}

	public ExplorerFigure getExplorerFigure() {
		return this.figure;
	}

	private ExplorerManager getExplorerManager() {
		return ExplorerManager.INSTANCE;
	}

	private ExplorerPopupActionConverter getExplorerPopupActionConverter() {
		return ExplorerPopupActionConverter.getInstance();
	}

	public HeaderPopupMenu getHeaderPopupMenu() {
		if (this.headerPopup == null) {
			this.headerPopup = new HeaderPopupMenu();
		}
		return this.headerPopup;
	}

	private Point getMouseDownAt() {
		return this.mouseDownAt;
	}

	private ObjectCopyDialogEventConverter getObjectCopyDialogEventConverter() {
		if (this.objectCopyDialogEventConverter == null) {
			this.setObjectCopyDialogEventConverter(new ObjectCopyDialogEventConverter(this.getExplorerFigure()));
		}
		return this.objectCopyDialogEventConverter;
	}

	private ObjectViewDialogEventConverter getObjectViewDialogEventConverter() {
		if (this.objectViewDialogEventConverter == null) {
			this.setObjectViewDialogEventConverter(new ObjectViewDialogEventConverter(this.getExplorerFigure()));
		}
		return this.objectViewDialogEventConverter;
	}

	private PopupMenu getPopup() {
		if (!(this.getActualTextBoxFigure() == null)) {
			if ((this.getActualTextBoxFigure()).getModel().isObjectHeaderModel()) {
				HeaderPopupMenu hPM = this.getHeaderPopupMenu();
				hPM.initialize(((ObjectModel) this.getActualListBoxFigure().getModel()).getObjectViewManager());
				return hPM;
			}
			else if ((this.getActualTextBoxFigure()).getModel().isAttributeModel()) {
				AttributePopupMenu aPM = this.getAttributePopupMenu();
				aPM.initialize((AttributeModel) this.getActualTextBoxFigure().getModel());
				return aPM;
			}
		}
		return null;
	}

	private void handleUpdateObjects() {
		Vector<?> models = ((ExplorerModel) this.getExplorerFigure().getModel()).getObjectModels().getVector();
		for (int i = 0; i < models.size(); i++) {
			this.getExplorerActionConverter().updateAllAttributesOf((ListBoxFigure) ((ObjectModel) models.elementAt(i)).getFigure());
		}
	}

	private void initialize() {
		// HeaderPopup
		for (int i = 0; i < this.getHeaderPopupMenu().getItemCount(); i++) {
			MenuItem item = this.getHeaderPopupMenu().getItem(i);
			// ignore separators
			// a separator has a hyphen as its label
			if (!(item.getLabel().equals("-"))) {
				if (item instanceof CheckboxMenuItem) {
					((CheckboxMenuItem) item).addItemListener(this);
				}
				else {
					item.addActionListener(this);
				}
			}
		}
		boolean isActualLIstBoxFigureNotNull = false;
		if (this.getActualListBoxFigure() != null) {
			isActualLIstBoxFigureNotNull = true;
		}
		final ExplorerDrawingPanel explorerFigurePanel = this.getExplorerFigure().getPanel();
		explorerFigurePanel.add(this.getHeaderPopupMenu());
		explorerFigurePanel.add(this.getAttributePopupMenu());
		if (!Explorer.isEmbedded()) {
			final ExplorerFrame explorerFigureFrame = this.getExplorerFigure().getFrame();
			// AttributePopup
			for (int i = 0; i < this.getAttributePopupMenu().getItemCount(); i++) {
				this.getAttributePopupMenu().getItem(i).addActionListener(this);
			}
			// mouse listener
			explorerFigureFrame.addKeyListener(this);
			explorerFigureFrame.addWindowListener(this);
			// menubar add action listener for about
			((JMenuItem) explorerFigureFrame.getJMenuBar().getMenu(0).getMenuComponent(0)).addActionListener(this);
			((JMenuItem) explorerFigureFrame.getJMenuBar().getMenu(0).getMenuComponent(1)).addActionListener(this);
			((JMenuItem) explorerFigureFrame.getJMenuBar().getMenu(1).getMenuComponent(0)).addActionListener(this);
			((JMenuItem) explorerFigureFrame.getJMenuBar().getMenu(1).getMenuComponent(2)).addActionListener(this);
			explorerFigureFrame.getMenuItemSave().setEnabled(isActualLIstBoxFigureNotNull);
			explorerFigureFrame.getMenuItemObjectView().setEnabled(isActualLIstBoxFigureNotNull);
			explorerFigureFrame.getMenuItemUpdateObjects().setEnabled(isActualLIstBoxFigureNotNull);
		}
		explorerFigurePanel.addMouseMotionListener(this);
		explorerFigurePanel.addMouseListener(this);
		explorerFigurePanel.addKeyListener(this);
		// ((ExplorerFigure)
		// this.getExplorerFigure()).getPanel().addKeyListener(this);
		// ((ExplorerFigure)
		// this.getExplorerFigure()).getFrame().getScrollPane().addKeyListener(
		// this);
	}

	private boolean isObjectHeaderModelSelected() {
		if (this.getActualTextBoxFigure() != null) {
			return (this.getActualTextBoxFigure()).getModel().isObjectHeaderModel();
		}
		else {
			return false;
		}
	}

	public void itemStateChanged(ItemEvent event) {
		final String label = ((CheckboxMenuItem) event.getSource()).getLabel();
		if (ProjectConstants4ObjectExplorer.getSHOW_ATTRIBUTES().equals(label)) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				this.getExplorerPopupActionConverter().setAllAttributesVisibleOf(this.getActualListBoxFigure());
			}
			else {
				this.getExplorerPopupActionConverter().setAllAttributesUnvisibleOf(this.getActualListBoxFigure());
			}
		}
		if (ProjectConstants4ObjectExplorer.getHIDE_NULL_ATTRIBUTES().equals(label)) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				this.getExplorerPopupActionConverter().setNullAttributesUnvisibleOf(this.getActualListBoxFigure());
			}
			else {
				this.getExplorerPopupActionConverter().setNullAttributesVisibleOf(this.getActualListBoxFigure());
			}
		}
		// if
		// (ProjectConstants4ObjectExplorer.getREMOVE_DEPENDENCIES().equals(label))
		// {
		// if (event.getStateChange() == ItemEvent.SELECTED) {
		// this.setRemoveDependencies(true);
		// }
		// else {
		// this.setRemoveDependencies(false);
		// }
		// }
		if (ProjectConstants4ObjectExplorer.getHIDE_STATIC_ATTRIBUTES().equals(label)) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				this.getExplorerPopupActionConverter().setStaticAttributesUnvisibleOf(this.getActualListBoxFigure());
			}
			else {
				this.getExplorerPopupActionConverter().setStaticAttributesVisibleOf(this.getActualListBoxFigure());
			}
		}
		if (ProjectConstants4ObjectExplorer.getHIDE_UNEXPLORED_ATTRIBUTES().equals(label)) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				this.getExplorerPopupActionConverter().setAllUnexploredAttributesUnvisibleOf(this.getActualListBoxFigure());
			}
			else {
				this.getExplorerPopupActionConverter().setAllUnexploredAttributesVisibleOf(this.getActualListBoxFigure());
			}
		}
		this.getObjectViewDialogEventConverter().updateObjectFocusMousePressed(this.getActualListBoxFigure());
		// this.setPopupMenuNotRequested();
	}

	public void keyPressed(KeyEvent e) {
		Component c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		// Object receiver = null;
		if (this.getActualListBoxFigure() != null) {
			// this.getObjectViewDialogEventConverter().updateObjectFocus(this.
			// getActualListBoxFigure());
			if (c instanceof ExplorerDrawingPanel) {
				this.getObjectViewDialogEventConverter().keyPressedVisibleList(e);
			}
		}
		// if (KeyEvent.VK_SPACE == e.getKeyCode()) {
		// if (e.isControlDown()) {
		// // String prePattern = "";
		// if ((this.getActualTextBoxFigure() != null) &&
		// (this.getActualTextBoxFigure().getModel() instanceof
		// de.paragon.explorer.model.StandardAttributeModel)) {
		// // receiver = ((StandardAttributeModel)
		// // this.getActualTextBoxFigure().getModel()).getValue();
		// // prePattern = receiver.getClass().getName();
		// }
		// else {
		// if (this.getActualListBoxFigure() != null) {
		// // receiver =
		// // this.getActualListBoxFigure().getModel().getObject();
		// // prePattern = receiver.getClass().getName();
		// }
		// }
		// }
		// }
	}

	public void keyReleased(KeyEvent e) {
		// if (ExplorerFrameEventConverter.logger.isDebugEnabled()) {
		// ExplorerFrameEventConverter.logger.debug("-->> keyReleased");
		// }
	}

	public void keyTyped(KeyEvent e) {
		// if (ExplorerFrameEventConverter.logger.isDebugEnabled()) {
		// ExplorerFrameEventConverter.logger.debug("-->> keyTyped");
		// }
	}

	/*
	 * draw bedeutet, dass nur die im Argument angebene Figur gezeichnet wird.
	 * draw wird immer dann aufgerufen, wenn sich die Stelle der zu zeich-
	 * nenden Figure nicht geaendert hat.
	 */
	/**
	 * Kommentar: mouseClicked (d. h., wenn die Maus gedrueckt und wieder
	 * freigegeben wurde) bewirkt beim Explorer folgendes: Die ist die aktuell
	 * zwischengespeicherte TextFigur eine solche, die mit einem AttributeModel
	 * verknuepft ist, wird bei einmaligen Klick die gesamte ListBoxFigure neu
	 * in den Vordergrund gezeichnet. Bei einem zweimaligen Klick wird eine neue
	 * ListBoxFigure erstellt, die das Objekt darstellt, die das verknuepfte
	 * AttributeModel repraesentiert.
	 */
	public void mouseClicked(java.awt.event.MouseEvent event) {
		if ((this.getActualTextBoxFigure() != null) && this.getActualTextBoxFigure().getModel().isAttributeModel()) {
			if (event.getClickCount() == 1) {
				this.getExplorerActionConverter().draw(this.getActualListBoxFigure());
			}
			if (event.getClickCount() == 2) {
				if (((AttributeModel) this.getActualTextBoxFigure().getModel()).getConnectionModel() != null) {
					this.getExplorerPopupActionConverter().removeConnection(this.getActualTextBoxFigure());
				}
				else {
					this.getExplorerActionConverter().showInForeground(this.getActualTextBoxFigure());
				}
			}
		}
		if (this.isObjectHeaderModelSelected()) {
			if (event.getClickCount() == 2) {
				if (((ObjectModel) this.getActualListBoxFigure().getModel()).getObjectViewManager().isAllAttributesVisible()) {
					this.getExplorerPopupActionConverter().setAllAttributesUnvisibleOf(this.getActualListBoxFigure());
				}
				else {
					this.getExplorerPopupActionConverter().setAllAttributesVisibleOf(this.getActualListBoxFigure());
				}
				this.getObjectViewDialogEventConverter().updateObjectFocus(this.getActualListBoxFigure());
			}
		}
	}

	public void mouseDragged(MouseEvent e) {
		if (this.shouldMoveCursorAppear(e)) {
			ListBoxFigure liBoFi = this.getActualListBoxFigure();
			int x;
			int y;
			if (e.getX() >= 0) {
				x = e.getX() - this.getMouseDownAt().x;
			}
			else {
				// x = 10 - this.getMouseDownAt().x;
				x = 0;
			}
			if (e.getY() >= 0) {
				y = e.getY() - this.getMouseDownAt().y;
			}
			else {
				// y = 10 - this.getMouseDownAt().y;
				y = 0;
			}
			this.getExplorerActionConverter().moveFigureBy(liBoFi, x, y);
			this.getExplorerActionConverter().draw(liBoFi);
			if (this.getExplorerFigure().getFrame() != null) {
				this.getExplorerFigure().getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			}
			this.setMouseDownAt(e.getPoint());
		}
	}

	/**
	 * Kommentar:
	 * 
	 * Falls kein popupmenu geoeffnet wurde, geschieht folgendes:
	 * 
	 * Wenn der Mauszeiger das Fenster beruehrt, werden die aktuell
	 * zwischengespeicherte List- und TextBoxFigure sowie der Punkt, wo die Maus
	 * gedrueckt wurde, geloescht.
	 * 
	 * In jedem Fall wird das Object wieder so gesetzt, dass das popupmenu nicht
	 * mehr als geoeffnet gilt.
	 */
	public void mouseEntered(MouseEvent e) {
		// if (ExplorerFrameEventConverter.logger.isDebugEnabled()) {
		// ExplorerFrameEventConverter.logger.debug("-->> mouseEntered");
		// }
		/**
		 * if (!this.popupMenuIsRequested()) { this.setMouseDownAt(null);
		 * this.setActualTextBoxFigure(null); this.setActualListBoxFigure(null);
		 * } else { //
		 * System.out.println("mouse entered with popup menu requested " +
		 * this.getActualListBoxFigure()); } //this.setPopupMenuNotReqested();
		 * //System.out.println("mouse entered, Popup reset");
		 */
	}

	/**
	 * Kommentar: Wenn der Mauszeiger das Fenster verlaesst, werden die aktuell
	 * zwischengespeicherte List- und TextBoxFigure sowie der Punkt, wo die Maus
	 * gedrueckt wurde, geloescht
	 */
	/*
	 * Kommentar: zur Abfrage nach popupMenuIsRequested: In einigen
	 * Entwicklungsumgebungen ge- schieht beim Aufruf des Popup-Menus folgendes:
	 * Wenn ein MenuPunkt aus dem Popup-Menu ausgewaehlt wird und die Maustaste
	 * wied- der freigegeben wird, wird zuerst das Event "mouseExited" gefeuert,
	 * was ja dazu fuehrt, dass die zu bearbeitenden Text- und ListBoxFigures
	 * geloescht wer- den. Dies soll aber verhindert werden, wenn das Popup-Menu
	 * aufgerufen wurde. Wenn das Event MouseExited fertig ist, wird
	 * ActionPerformed, und danach MouseEntered aufgerufen.
	 */
	public void mouseExited(MouseEvent e) {
		// if (ExplorerFrameEventConverter.logger.isDebugEnabled()) {
		// ExplorerFrameEventConverter.logger.debug("-->> mouseExited");
		// }
		/**
		 * if (!this.popupMenuIsRequested()) { this.setMouseDownAt(null);
		 * this.setActualTextBoxFigure(null); this.setActualListBoxFigure(null);
		 * } else { //
		 * System.out.println("mouse exited with popup menu requested"); }
		 */
	}

	/**
	 * Kommentar: Sobald eine Maustaste gedrueckt wird, werden die Text- und die
	 * ListBoxFigure, sofern sie getroffen sind, sowie der Punkt in den
	 * Zwischenspeicher gespeichert. Die getroffene ListBoxFigure wird in den
	 * Vordergrund gesetzt, sodass sie bei der naechsten Komplettzeichnung vorne
	 * erscheint.
	 */
	public void mouseMoved(MouseEvent e) {
		// if (ExplorerFrameEventConverter.logger.isDebugEnabled()) {
		// ExplorerFrameEventConverter.logger.debug("-->> mouseMoved");
		// }
	}

	/**
	 * Kommentar: Sobald eine Maustaste gedrueckt wird, werden die Text- und die
	 * ListBoxFigure, sofern sie getroffen sind, sowie der Punkt in den
	 * Zwischenspeicher gespeichert. Die getroffene ListBoxFigure wird in den
	 * Vordergrund gesetzt, sodass sie bei der naechsten Komplettzeichnung vorne
	 * erscheint.
	 */
	public void mousePressed(MouseEvent e) {
		// if (ExplorerFrameEventConverter.logger.isDebugEnabled()) {
		// ExplorerFrameEventConverter.logger.debug("-->> mousePressed");
		// }
		this.setMouseDownAt(e.getPoint());
		if (e.getSource() instanceof ExplorerDrawingPanel) {
			((ExplorerDrawingPanel) e.getSource()).requestFocus();
			ListBoxFigure oldListBoxFigure = this.getActualListBoxFigure();
			// TextBoxFigure oldTextBoxFigure = this.getActualTextBoxFigure();
			this.setActualTextBoxFigure(this.getExplorerFigure().getTextBoxFigureForPoint(this.getMouseDownAt()));
			this.setActualListBoxFigure(this.getExplorerFigure().getListBoxFigureForPoint(this.getMouseDownAt()));
			// hier muss object view inaktiv bzw aktiv gesetzt werden.....
			if ((this.getActualListBoxFigure() != null) && (this.getActualTextBoxFigure() != null)) {
				// if (this.getActualListBoxFigure() instanceof ListBoxFigure) {
				this.getExplorerActionConverter().setInForeground(this.getActualListBoxFigure());
				// }
				this.getObjectViewDialogEventConverter().updateObjectFocusMousePressed(this.getActualListBoxFigure());
				if (!this.getActualTextBoxFigure().getModel().isObjectHeaderModel()) {
					this.getObjectViewDialogEventConverter().updateAttributeFocus(this.getActualTextBoxFigure());
				}
				else if (this.getActualListBoxFigure().equals(oldListBoxFigure)
						|| !((ObjectModel) this.getActualListBoxFigure().getModel()).getObjectViewManager().isAllAttributesVisible()) {
					this.getExplorerFigure().getFocusManager().handleFocus(this.getActualTextBoxFigure(), true);
				}
				/***************************************************************
				 * else if (oldTextBoxFigure != null) { if
				 * (oldTextBoxFigure.getModel().isObjectHeaderModel()) {
				 * this.getExplorerFigure ().getFocusManager().handleFocus(this.
				 * getActualTextBoxFigure ()); } }
				 **************************************************************/
			}
		}
	}

	/*
	 * Kommentar: draw bedeutet, dass nur die im Argument angebene Figur
	 * gezeichnet wird. draw wird immer dann aufgerufen, wenn sich die Stelle
	 * der zu zeich- nenden Figure nicht geaendert hat. Da sich bei moveFigureBy
	 * die Stelle aendert, wird in der Methode moveFigureBy explitit ein anderes
	 * draw aufgerufen. Das draw dort loescht die gesammte Zeichnung, bevor die
	 * kom- plette Zeichnung neu gezeichnet wird. Bemerkung: zur Abfrage nach
	 * popupMenuIsRequested: In einigen Entwicklungsumgebungen geschieht beim
	 * Aufruf des Popup-Menus folgendes: Wenn ein MenuPunkt aus dem Popup-Menu
	 * ausgewaehlt wird und die Maustaste wieder freigegeben wird, wird zuerst
	 * das Event "mouseExited" gefeuert, was ja dazu fuehrt, dass die zu
	 * bearbeitenden Text- und ListBoxFigures geloescht werden. Dies soll aber
	 * verhindert werden, wenn das Popup-Menu aufgerufen wurde. Wenn das Event
	 * MouseExited fertig ist, wird ActionPerformed, und danach MouseEntered
	 * aufgerufen.
	 */
	/**
	 * Kommentar: Wird die Maustaste wieder freigegeben, wird entweder ein von
	 * der getroffenen Figur abhaengiges Popupmenu geoeffnet, oder die
	 * ListBoxFigure wird um die Differenz der Punkte, wo die Maus gedrueckt und
	 * wieder freigegeben wurde, verschoben und im Vordergrund gezeichnet.
	 */
	public void mouseReleased(MouseEvent event) {
		// if (ExplorerFrameEventConverter.logger.isDebugEnabled()) {
		// ExplorerFrameEventConverter.logger.debug("-->> mouseReleased");
		// }
		if (event.isPopupTrigger()) {
			if (this.getActualTextBoxFigure() != null) {
				// if (this.getActualListBoxFigure() instanceof ListBoxFigure) {
				this.getExplorerActionConverter().draw(this.getActualListBoxFigure());
				// if (ExplorerFrameEventConverter.logger.isDebugEnabled()) {
				// ExplorerFrameEventConverter.logger.debug("-->> mouseReleased, Popup requested");
				// }
				// this.setPopupMenuRequested();
				// }
				this.getPopup().show(this.getExplorerFigure().getPanel(), (int) this.getMouseDownAt().getX(), (int) this.getMouseDownAt().getY());
			}
		}
		else {
			if (this.getExplorerFigure().getFrame() != null) {
				this.getExplorerFigure().getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
	}

	public void moveCursorShouldBeDisplayed(MouseEvent e) {
		if (this.getMouseDownAt() != e.getPoint()) {
			if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
				if (this.getActualTextBoxFigure() != null) {
					if ((this.getActualTextBoxFigure()).getModel().isObjectHeaderModel()) {
						ListBoxFigure liBoFi = this.getActualListBoxFigure();
						int x;
						int y;
						if (e.getX() >= 0) {
							x = e.getX() - this.getMouseDownAt().x;
						}
						else {
							x = ExplorerFrameEventConverter.NUMBER_10 - this.getMouseDownAt().x;
						}
						if (e.getY() >= 0) {
							y = e.getY() - this.getMouseDownAt().y;
						}
						else {
							y = ExplorerFrameEventConverter.NUMBER_10 - this.getMouseDownAt().y;
						}
						this.getExplorerActionConverter().moveFigureBy(liBoFi, x, y);
						this.getExplorerActionConverter().draw(liBoFi);
						if (this.getExplorerFigure().getFrame() != null) {
							this.getExplorerFigure().getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
						}
						this.setMouseDownAt(e.getPoint());
					}
				}
			}
		}
	}

	private void setAboutDialogEventConverter(AboutDialogEventConverter newAboutDialogEventConverter) {
		this.aboutDialogEventConverter = newAboutDialogEventConverter;
	}

	private void setActualListBoxFigure(Figure newFigure) {
		this.actualListBoxFigure = (ListBoxFigure) newFigure;
		boolean isActualLIstBoxFigureNotNull = false;
		if (newFigure != null) {
			isActualLIstBoxFigureNotNull = true;
		}
		if (!Explorer.isEmbedded()) {
			ExplorerFrame frame = this.getExplorerFigure().getFrame();
			frame.getMenuItemSave().setEnabled(isActualLIstBoxFigureNotNull);
			frame.getMenuItemObjectView().setEnabled(isActualLIstBoxFigureNotNull);
			frame.getMenuItemUpdateObjects().setEnabled(isActualLIstBoxFigureNotNull);
		}
	}

	private void setActualTextBoxFigure(Figure newFigure) {
		this.actualTextBoxFigure = (TextBoxFigure) newFigure;
	}

	public void setCursorPosition(int pos) {
	}

	public void setFigure(ExplorerFigure newFigure) {
		this.figure = newFigure;
	}

	private void setMouseDownAt(Point point) {
		this.mouseDownAt = point;
	}

	private void setObjectCopyDialogEventConverter(ObjectCopyDialogEventConverter newObjectCopyDialogEventConverter) {
		this.objectCopyDialogEventConverter = newObjectCopyDialogEventConverter;
	}

	private void setObjectViewDialogEventConverter(ObjectViewDialogEventConverter newObjectViewDialogEventConverter) {
		this.objectViewDialogEventConverter = newObjectViewDialogEventConverter;
	}

	private boolean shouldMoveCursorAppear(MouseEvent e) {
		return (this.getMouseDownAt() != e.getPoint()) && (e.getModifiers() == InputEvent.BUTTON1_MASK) && this.isObjectHeaderModelSelected();
	}

	private void updateObject() {
		this.getExplorerActionConverter().updateAllAttributesOf(this.getActualListBoxFigure());
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// this.getObjectMoveFrameEventConverter().activeFrame(e.getSource());
	}

	@Override
	public void windowClosing(WindowEvent e) {
		this.getExplorerManager().getExistingExplorerModels().remove(this.getExplorerFigure().getModel());
	}
}
