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

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import de.paragon.explorer.ProjectConstants4ObjectExplorer;
import de.paragon.explorer.figure.ExplorerFigure;
import de.paragon.explorer.figure.ExplorerFigureBuilder;
import de.paragon.explorer.figure.ListBoxFigure;
import de.paragon.explorer.figure.ListBoxFigureBuilder;
import de.paragon.explorer.figure.TextBoxFigure;
import de.paragon.explorer.gui.ObjectViewDialog;
import de.paragon.explorer.model.AttributeModel;
import de.paragon.explorer.model.AttributeModelComparator;
import de.paragon.explorer.model.ExplorerModel;
import de.paragon.explorer.model.ObjectModel;
import de.paragon.explorer.model.StandardAttributeModel;
import de.paragon.explorer.util.LoggerFactory;
import de.paragon.explorer.util.ObjectViewManager;

/**
 * Diese Klasse erzeugt den Object View Dialog und verarbeitet die zugehoerigen
 * Events
 */
public class ObjectViewDialogEventConverter extends WindowAdapter implements ActionListener, ItemListener, ListSelectionListener, KeyListener {
	private static final Logger		logger		= LoggerFactory.make();
	private static final int		NUMBER_100	= 100;
	private static final int		NUMBER_400	= 400;
	private ExplorerFigure			explorerFigure;
	private ObjectViewDialog		objectViewDialog;
	private ListBoxFigure			objectToView;
	private Object					selectedObject;
	private Vector<AttributeModel>	visibleAttributes;
	private Vector<AttributeModel>	invisibleAttributes;

	/**
	 * AboutDialogEventConverter constructor comment.
	 */
	public ObjectViewDialogEventConverter(ExplorerFigure expl) {
		super();
		this.setExplorerFigure(expl);
	}

	public void actionPerformed(ActionEvent event) {
		String actionCommand = event.getActionCommand();
		if (actionCommand.equals(ProjectConstants4ObjectExplorer.getOBJECT_VIEW_DIALOG_GO_TO())) {
			this.gotoObject();
		}
		else if (actionCommand.equals(ProjectConstants4ObjectExplorer.getOBJECT_VIEW_DIALOG_CLOSE())) {
			this.close();
		}
		else if (actionCommand.equals(ProjectConstants4ObjectExplorer.getOBJECT_VIEW_DIALOG_EXPLORE())) {
			this.explore();
		}
		else if (actionCommand.equals(ProjectConstants4ObjectExplorer.getOBJECT_VIEW_DIALOG_ADD())) {
			this.add();
		}
		else if (actionCommand.equals(ProjectConstants4ObjectExplorer.getOBJECT_VIEW_DIALOG_REMOVE())) {
			this.remove();
		}
	}

	private void add() {
		this.getInvisibleAttributes().remove(this.getSelectedObject());
		this.getVisibleAttributes().addElement((AttributeModel) this.getSelectedObject());
		Collections.sort(this.getVisibleAttributes(), new AttributeModelComparator());
		this.getObjectViewDialog().getVisibleList().setListData(this.getVisibleAttributesNames());
		this.getObjectViewDialog().getInvisibleList().setListData(this.getInvisibleAttributesNames());
		this.selectionChangedVisibleList();
		this.setAttributeVisible();
	}

	private void close() {
		this.getObjectViewDialog().setVisible(false);
		this.getExplorerFigure().getFrame().getWindowMenu().getItem(0).setEnabled(true);
		((ExplorerModel) this.getExplorerFigure().getModel()).getDrawingFrameEventConverter().getHeaderPopupMenu().enable("object view", true);
		this.reset();
	}

	private final void displayDialog() {
		// this.getExplorerFigure().getFrame().getGlassPane().setVisible(true);
		// javax.swing.SwingUtilities.convertPointToScreen(point,((ExplorerFigure)this.getObjectToView().getParent()).getFrame());
		this.getObjectViewDialog().setLocation(new Point(ObjectViewDialogEventConverter.NUMBER_400, ObjectViewDialogEventConverter.NUMBER_100));
		this.getObjectViewDialog().setVisible(true);
	}

	private void explore() {
		AttributeModel attrModl = (AttributeModel) this.getSelectedObject();
		TextBoxFigure teBoFi = (TextBoxFigure) attrModl.getFigure();
		this.getExplorerActionConverter().showInForeground(teBoFi);
		this.setExploreButtonEnabled(false);
		this.getObjectViewDialog().setGoToButtonEnabled(true);
	}

	private int findIndex(Vector<String> aVector) {
		int selectedIndex = -1;
		if ((aVector != null) && (this.getSelectedObject() != null)) {
			for (int i = 0; i < aVector.size(); i++) {
				if (aVector.elementAt(i).equals(((AttributeModel) this.getSelectedObject()).getName())) {
					selectedIndex = i;
				}
			}
		}
		return selectedIndex;
	}

	private ExplorerActionConverter getExplorerActionConverter() {
		return ExplorerActionConverter.getInstance();
	}

	private ExplorerFigure getExplorerFigure() {
		return this.explorerFigure;
	}

	private ExplorerFigureBuilder getExplorerFigureBuilder() {
		return ExplorerFigureBuilder.getInstance();
	}

	private Vector<AttributeModel> getInvisibleAttributes() {
		if (this.invisibleAttributes == null) {
			this.setInvisibleAttributes((Vector<AttributeModel>) this.getObjectViewManager().getInvisibleAttributes());
		}
		return this.invisibleAttributes;
	}

	private Vector<String> getInvisibleAttributesNames() {
		Vector<String> names = new Vector<String>();
		for (int i = 0; i < this.getInvisibleAttributes().size(); i++) {
			names.add((this.getInvisibleAttributes().elementAt(i)).getName());
		}
		// Collections.sort(names);
		return names;
	}

	private ListBoxFigureBuilder getListBoxFigureBuilder() {
		return ListBoxFigureBuilder.getInstance();
	}

	private ListBoxFigure getObjectToView() {
		return this.objectToView;
	}

	private ObjectViewDialog getObjectViewDialog() {
		if (this.objectViewDialog == null) {
			this.setObjectViewDialog(new ObjectViewDialog(this.getExplorerFigure().getFrame(), false));
			this.initialize();
		}
		return this.objectViewDialog;
	}

	private ObjectViewManager getObjectViewManager() {
		return ((ObjectModel) this.getObjectToView().getModel()).getObjectViewManager();
	}

	private Object getSelectedObject() {
		return this.selectedObject;
	}

	private Vector<AttributeModel> getVisibleAttributes() {
		if (this.visibleAttributes == null) {
			this.setVisibleAttributes((Vector<AttributeModel>) this.getObjectViewManager().getVisibleAttributes());
		}
		return this.visibleAttributes;
	}

	private Vector<String> getVisibleAttributesNames() {
		Vector<String> names = new Vector<String>();
		for (int i = 0; i < this.getVisibleAttributes().size(); i++) {
			names.add((this.getVisibleAttributes().elementAt(i)).getName());
		}
		// Collections.sort(names);
		return names;
	}

	private void gotoObject() {
		AttributeModel attrModl = (AttributeModel) this.getSelectedObject();
		if (attrModl != null) {
			de.paragon.explorer.figure.TextBoxFigure teBoFi = (TextBoxFigure) attrModl.getConnectionModel().getHeaderModel().getFigure();
			this.getExplorerActionConverter().setInForeground((ListBoxFigure) teBoFi.getParent());
			this.getExplorerFigure().getFocusManager().handleFocus(teBoFi, false);
			this.updateObjectFocus((ListBoxFigure) teBoFi.getParent());
		}
	}

	private void handleAttribute() {
		AttributeModel attrModl;
		attrModl = (AttributeModel) this.getSelectedObject();
		if (attrModl != null) {
			this.getObjectViewDialog().getInfoText().setText(attrModl.getCompleteTitle());
			this.getObjectViewDialog().getInfoText().setCaretPosition(0);
			if ((!attrModl.isAttributePrimitive() && (attrModl.getConnectionModel() == null))) {
				this.setExploreButtonEnabled(true);
				this.getObjectViewDialog().setGoToButtonEnabled(false);
			}
			else {
				this.setExploreButtonEnabled(false);
				if (attrModl.isAttributePrimitive()) {
					this.getObjectViewDialog().setGoToButtonEnabled(false);
				}
				else {
					this.getObjectViewDialog().setGoToButtonEnabled(true);
				}
			}
		}
	}

	private void handleFocus(boolean mousePressed) {
		int selectedIndex;
		selectedIndex = this.findIndex(this.getVisibleAttributesNames());
		if (selectedIndex >= 0) {
			this.setSelectedObject(this.getVisibleAttributes().elementAt(selectedIndex));
			this.selectionChangedVisibleList();
		}
		else {
			selectedIndex = this.findIndex(this.getInvisibleAttributesNames());
			if ((selectedIndex >= 0)) {
				this.setSelectedObject(this.getInvisibleAttributes().elementAt(selectedIndex));
				this.selectionChangedInvisibleList();
			}
			else {
				this.getObjectViewDialog().getInvisibleList().clearSelection();
				// this.getObjectViewDialog().getInvisibleList().ensureIndexIsVisible(0);
				// this.getObjectViewDialog().getVisibleList().clearSelection();
				// this.getObjectViewDialog().getVisibleList().ensureIndexIsVisible(0);
				this.setSelectedObject(null);
				this.getExplorerFigure().getFocusManager()
						.handleFocus((TextBoxFigure) ((ObjectModel) this.getObjectToView().getModel()).getHeaderModel().getFigure(), mousePressed);
			}
		}
	}

	private void initialize() {
		final ObjectViewDialog objectViewDialog4Init = this.getObjectViewDialog();
		objectViewDialog4Init.addListSelectionListener(this);
		objectViewDialog4Init.addKeyListener(this);
		objectViewDialog4Init.addButtonActionListener(this);
		objectViewDialog4Init.addWindowListener(this);
		objectViewDialog4Init.addItemListener(this);
		objectViewDialog4Init.setTitle(((ExplorerModel) this.getExplorerFigure().getModel()).getName() + " Object View");
	}

	public final void initializeDialog(ListBoxFigure param, boolean mousePressed) {
		this.setObjectToView(param);
		final ObjectViewDialog objectViewDialog4Init = this.getObjectViewDialog();
		objectViewDialog4Init.setRemoveButtonEnabled(false);
		objectViewDialog4Init.setAddButtonEnabled(false);
		objectViewDialog4Init.setExploreButtonEnabled(false);
		objectViewDialog4Init.setGoToButtonEnabled(false);
		objectViewDialog4Init.getVisibleList().setListData(this.getVisibleAttributesNames());
		objectViewDialog4Init.getInvisibleList().setListData(this.getInvisibleAttributesNames());
		this.handleFocus(mousePressed);
	}

	public final void itemStateChanged(ItemEvent event) {
		// if (event.getStateChange() == ItemEvent.SELECTED) {
		// nicht implementiert
		// this.getObjectTitleManager().getValuesToShow().add(this.getObjectToView().getModel().getObject().getClass());
		// }
	}

	public void keyPressed(KeyEvent e) {
		if (e.getSource() == this.getObjectViewDialog().getInvisibleList()) {
			this.keyPressedInvisibleList(e);
		}
		if (e.getSource() == this.getObjectViewDialog().getVisibleList()) {
			this.keyPressedVisibleList(e);
		}
	}

	public void keyPressedInvisibleList(KeyEvent e) {
		Character charKey = Character.valueOf(e.getKeyChar());
		String key = charKey.toString();
		int selectedIndex = this.getObjectViewDialog().getInvisibleList().getSelectedIndex();
		int j;
		Vector<String> invisibleAttributesNames = this.getInvisibleAttributesNames();
		String elementAtAsString = null;
		for (int i = 0; i < invisibleAttributesNames.size(); i++) {
			elementAtAsString = invisibleAttributesNames.elementAt(i);
			if (elementAtAsString.startsWith(key) || elementAtAsString.startsWith(key.toUpperCase())) {
				selectedIndex = i;
				if (this.getSelectedObject() != null) {
					if (((AttributeModel) this.getSelectedObject()).getName().startsWith(key)
							|| ((AttributeModel) this.getSelectedObject()).getName().startsWith(key.toUpperCase())) {
						j = this.getObjectViewDialog().getInvisibleList().getSelectedIndex();
						if (invisibleAttributesNames.size() > (j + 1)) {
							if ((invisibleAttributesNames.elementAt(j + 1)).startsWith(key) || (invisibleAttributesNames.elementAt(j + 1)).startsWith(key.toUpperCase())) {
								selectedIndex = j + 1;
							}
						}
					}
				}
				this.setSelectedObject(this.getInvisibleAttributes().elementAt(selectedIndex));
				this.selectionChangedInvisibleList();
				break;
			}
		}
	}

	public void keyPressedVisibleList(KeyEvent e) {
		Character charKey = Character.valueOf(e.getKeyChar());
		String key = charKey.toString();
		int selectedIndex = this.getObjectViewDialog().getVisibleList().getSelectedIndex();
		int j;
		for (int i = 0; i < this.getVisibleAttributesNames().size(); i++) {
			if ((this.getVisibleAttributesNames().elementAt(i)).startsWith(key) || (this.getVisibleAttributesNames().elementAt(i)).startsWith(key.toUpperCase())) {
				selectedIndex = i;
				if (this.getSelectedObject() != null) {
					if (((AttributeModel) this.getSelectedObject()).getName().startsWith(key)
							|| ((AttributeModel) this.getSelectedObject()).getName().startsWith(key.toUpperCase())) {
						j = this.getObjectViewDialog().getVisibleList().getSelectedIndex();
						if ((this.getVisibleAttributesNames().size() > (j + 1))) {
							if ((this.getVisibleAttributesNames().elementAt(j + 1)).startsWith(key)
									|| (this.getVisibleAttributesNames().elementAt(j + 1)).startsWith(key.toUpperCase())) {
								selectedIndex = j + 1;
							}
						}
					}
					else {
						if (ObjectViewDialogEventConverter.logger.isDebugEnabled()) {
							ObjectViewDialogEventConverter.logger.debug("kein Object mit diesem Buchstaben selektiert: " + key.toString() + " "
									+ ((AttributeModel) this.getSelectedObject()).getName());
						}
					}
				}
				else {
					if (ObjectViewDialogEventConverter.logger.isDebugEnabled()) {
						ObjectViewDialogEventConverter.logger.debug("selectedObject == null");
					}
				}
				this.setSelectedObject(this.getVisibleAttributes().elementAt(selectedIndex));
				this.selectionChangedVisibleList();
				break;
			}
		}
		// System.out.println(key.toString()+ " " +((AttributeModel)
		// this.getSelectedObject()).getName() + " "+ selectedIndex);
	}

	public void keyRealsed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	private void remove() {
		this.getVisibleAttributes().remove(this.getSelectedObject());
		if (this.getSelectedObject() != null) {
			this.getInvisibleAttributes().addElement((AttributeModel) this.getSelectedObject());
		}
		// sortieren der liste
		Collections.sort(this.getInvisibleAttributes(), new AttributeModelComparator());
		// update der listen
		this.getObjectViewDialog().getVisibleList().setListData(this.getVisibleAttributesNames());
		this.getObjectViewDialog().getInvisibleList().setListData(this.getInvisibleAttributesNames());
		// aktualisieren der oberflaeche
		this.selectionChangedInvisibleList();
		this.setAttributeInvisible();
	}

	private void reset() {
		this.setVisibleAttributes(null);
		this.setInvisibleAttributes(null);
		this.getObjectViewDialog().getInfoText().setText("");
		// this.setSelectedObject(null);
		// this.getExplorerFigure().getFrame().getGlassPane().setVisible(false);
	}

	private void selectionChangedInvisibleList() {
		final ObjectViewDialog objectViewDialog2 = this.getObjectViewDialog();
		objectViewDialog2.setRemoveButtonEnabled(false);
		objectViewDialog2.setAddButtonEnabled(true);
		objectViewDialog2.getVisibleList().clearSelection();
		objectViewDialog2.getInvisibleList().setSelectedIndex(this.getInvisibleAttributes().indexOf(this.getSelectedObject()));
		objectViewDialog2.getInvisibleList().ensureIndexIsVisible(this.getInvisibleAttributes().indexOf(this.getSelectedObject()));
		this.handleAttribute();
		de.paragon.explorer.figure.ListBoxFigure liBoFi = this.getObjectToView();
		this.getExplorerFigure().getFocusManager().handleFocus((TextBoxFigure) ((ObjectModel) liBoFi.getModel()).getHeaderModel().getFigure(), false);
	}

	private void selectionChangedVisibleList() {
		final ObjectViewDialog objectViewDialog2 = this.getObjectViewDialog();
		objectViewDialog2.setRemoveButtonEnabled(true);
		objectViewDialog2.setAddButtonEnabled(false);
		objectViewDialog2.getInvisibleList().clearSelection();
		objectViewDialog2.getVisibleList().setSelectedIndex(this.getVisibleAttributes().indexOf(this.getSelectedObject()));
		objectViewDialog2.getVisibleList().ensureIndexIsVisible(this.getVisibleAttributes().indexOf(this.getSelectedObject()));
		this.handleAttribute();
		if (this.getSelectedObject() != null) {
			TextBoxFigure teBoFi = (TextBoxFigure) ((AttributeModel) this.getSelectedObject()).getFigure();
			this.getExplorerFigure().getFocusManager().handleFocus(teBoFi, false);
		}
	}

	private void setAttributeInvisible() {
		AttributeModel attrModl = (AttributeModel) this.getSelectedObject();
		this.getListBoxFigureBuilder().setSingleAttributeUnvisible((TextBoxFigure) attrModl.getFigure());
		ListBoxFigure liBoFi = (ListBoxFigure) ((TextBoxFigure) attrModl.getFigure()).getParent();
		this.getListBoxFigureBuilder().initializeListBoxFigure(liBoFi);
		this.getExplorerFigureBuilder().update((ExplorerFigure) liBoFi.getParent());
	}

	private void setAttributeVisible() {
		AttributeModel attrModl = (AttributeModel) this.getSelectedObject();
		this.getListBoxFigureBuilder().setSingleAttributeVisible((TextBoxFigure) attrModl.getFigure());
		ListBoxFigure liBoFi = (ListBoxFigure) ((TextBoxFigure) attrModl.getFigure()).getParent();
		this.getListBoxFigureBuilder().initializeListBoxFigure(liBoFi);
		this.getExplorerFigureBuilder().update((ExplorerFigure) liBoFi.getParent());
	}

	public void setExploreButtonEnabled(boolean value) {
		this.getObjectViewDialog().setExploreButtonEnabled(value);
	}

	private void setExplorerFigure(ExplorerFigure newExplorerFigure) {
		this.explorerFigure = newExplorerFigure;
	}

	private void setInvisibleAttributes(Vector<AttributeModel> newInvisibleAttributes) {
		this.invisibleAttributes = newInvisibleAttributes;
	}

	private void setObjectToView(ListBoxFigure newObjectToView) {
		this.objectToView = newObjectToView;
	}

	private void setObjectViewDialog(ObjectViewDialog newObjectViewDialog) {
		this.objectViewDialog = newObjectViewDialog;
	}

	private void setSelectedObject(Object newSelectedObject) {
		this.selectedObject = newSelectedObject;
		this.getObjectViewDialog().getGoToButton().setEnabled(newSelectedObject != null);
		this.getObjectViewDialog().getAddButton().setEnabled(newSelectedObject != null);
		this.getObjectViewDialog().getRemoveButton().setEnabled(newSelectedObject != null);
	}

	private void setVisibleAttributes(Vector<AttributeModel> newVisibleAttributes) {
		this.visibleAttributes = newVisibleAttributes;
	}

	public final void showDialog() {
		if (!(this.getObjectViewDialog().isShowing())) {
			this.displayDialog();
			this.getExplorerFigure().getFrame().getWindowMenu().getItem(0).setEnabled(false);
			((ExplorerModel) this.getExplorerFigure().getModel()).getDrawingFrameEventConverter().getHeaderPopupMenu().enable("object view", false);
		}
	}

	public final void updateAttributeFocus(TextBoxFigure param) {
		this.setSelectedObject(param.getModel());
		this.selectionChangedVisibleList();
	}

	public final void updateObjectFocus(ListBoxFigure param) {
		if (this.getObjectViewDialog().isVisible()) {
			this.reset();
			this.initializeDialog(param, false);
		}
	}

	public final void updateObjectFocusMousePressed(ListBoxFigure param) {
		boolean found = false;
		Object foundElement = null;
		Object oldSelection = this.getSelectedObject();
		Enumeration<?> enumeration = ((ObjectModel) param.getModel()).getAttributeModels().getVector().elements();
		while (enumeration.hasMoreElements() && !found) {
			foundElement = enumeration.nextElement();
			if ((foundElement instanceof StandardAttributeModel) && (oldSelection instanceof StandardAttributeModel)) {
				if (((StandardAttributeModel) foundElement).getName().equals(((StandardAttributeModel) oldSelection).getName())) {
					found = true;
				}
			}
		}
		if (found) {
			this.setSelectedObject(foundElement);
		}
		else {
			this.setSelectedObject(null);
		}
		this.reset();
		this.initializeDialog(param, true);
	}

	public final void valueChanged(ListSelectionEvent event) {
		// if (event.getValueIsAdjusting()) {
		if (event.getSource() == this.getObjectViewDialog().getVisibleList().getSelectionModel()) {
			// System.out.println("valueChanged ");
			for (int i = 0; i < this.getVisibleAttributes().size(); i++) {
				if (this.getObjectViewDialog().getVisibleList().getSelectionModel().isSelectedIndex(i)) {
					this.setSelectedObject((this.getVisibleAttributes()).elementAt(i));
				}
			}
			this.selectionChangedVisibleList();
		}
		if (event.getSource() == this.getObjectViewDialog().getInvisibleList().getSelectionModel()) {
			for (int i = 0; i < this.getInvisibleAttributes().size(); i++) {
				if (this.getObjectViewDialog().getInvisibleList().getSelectionModel().isSelectedIndex(i)) {
					this.setSelectedObject((this.getInvisibleAttributes()).elementAt(i));
				}
			}
			this.selectionChangedInvisibleList();
		}
		// }
	}

	@Override
	public void windowClosing(WindowEvent e) {
		this.reset();
		this.getExplorerFigure().getFrame().getWindowMenu().getItem(0).setEnabled(true);
		((ExplorerModel) this.getExplorerFigure().getModel()).getDrawingFrameEventConverter().getHeaderPopupMenu().enable("object view", true);
	}
}
