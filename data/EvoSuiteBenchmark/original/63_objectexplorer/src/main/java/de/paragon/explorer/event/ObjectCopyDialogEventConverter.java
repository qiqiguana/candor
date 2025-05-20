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
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.paragon.explorer.figure.ExplorerFigure;
import de.paragon.explorer.figure.ExplorerFigureBuilder;
import de.paragon.explorer.figure.ListBoxFigure;
import de.paragon.explorer.gui.ObjectCopyDialog;
import de.paragon.explorer.model.ExplorerModel;
import de.paragon.explorer.model.ExplorerModelBuilder;
import de.paragon.explorer.model.ObjectModel;
import de.paragon.explorer.util.ExplorerColorManager;
import de.paragon.explorer.util.ExplorerManager;
import de.paragon.explorer.util.StandardEnumeration;

/**
 * Diese Klasse erzeugt den Object Copy Dialog und verarbeitet die zugehoerigen
 * Events
 */
public class ObjectCopyDialogEventConverter implements ActionListener, ListSelectionListener {
	private static final String	CANCEL	= "cancel";
	private static final String	OK		= "ok";
	private Object				selectedObject;
	private ExplorerFigure		explorerFigure;
	private ListBoxFigure		objectToCopy;
	private ObjectCopyDialog	copyDialog;

	/**
	 * ObjectMoveFrameEventConverter constructor comment.
	 */
	public ObjectCopyDialogEventConverter(ExplorerFigure expl) {
		super();
		this.setExplorerFigure(expl);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals(ObjectCopyDialogEventConverter.OK)) {
			this.ok();
		}
		else if (event.getActionCommand().equals(ObjectCopyDialogEventConverter.CANCEL)) {
			this.cancel();
		}
	}

	private void addObjectToModel() {
		ListBoxFigure liBoFiToShow = this.getListBoxFigureToShow();
		// Liefert mir die darzustellende ListBoxFigure zurueck
		if (liBoFiToShow == null) {
			this.getExplorerModelBuilder().addModel((ExplorerModel) this.getSelectedObject(), this.getObjectToCopy().getModel().getObject());
		}
		else {
			// info bereits vorhanden aktiv schalten !
			this.getExplorerFigureBuilder().drawInForeground(liBoFiToShow);
		}
	}

	private void cancel() {
		this.getCopyDialog().setVisible(false);
		this.setAllOtherFramesEnabled(true);
	}

	public final void copyObject(Point point, ListBoxFigure param) {
		this.setObjectToCopy(param);
		this.getCopyDialog().setListData(this.getAllOtherExplorerNames());
		this.setAllOtherFramesEnabled(false);
		javax.swing.SwingUtilities.convertPointToScreen(point, ((ExplorerFigure) this.getObjectToCopy().getParent()).getFrame());
		this.getCopyDialog().setLocation(point);
		this.getCopyDialog().setOKButtonEnabled(false);
		this.getCopyDialog().setVisible(true);
	}

	private Vector<String> getAllOtherExplorerNames() {
		Vector<String> names = new Vector<String>();
		Vector<ExplorerModel> aVector = this.getAllOtherExplorers();
		for (int i = 0; i < aVector.size(); i++) {
			names.add((aVector.elementAt(i)).getName());
		}
		return names;
	}

	@SuppressWarnings("unchecked")
	private Vector<ExplorerModel> getAllOtherExplorers() {
		Vector<ExplorerModel> aVector = (Vector<ExplorerModel>) ((Vector<ExplorerModel>) this.getExplorerManager().getExistingExplorerModels()).clone();
		aVector.remove(((ObjectModel) this.getObjectToCopy().getModel()).getExplorerModel());
		return aVector;
	}

	private ExplorerColorManager getColorManager(ListBoxFigure libofi) {
		return ((ExplorerModel) this.getExplorerFigure(libofi).getModel()).getColorManager();
	}

	private ObjectCopyDialog getCopyDialog() {
		if (this.copyDialog == null) {
			this.setCopyDialog(new ObjectCopyDialog(this.getExplorerFigure().getFrame()));
			this.initialize();
		}
		return this.copyDialog;
	}

	private ExplorerFigure getExplorerFigure() {
		return this.explorerFigure;
	}

	private ExplorerFigure getExplorerFigure(ListBoxFigure libofi) {
		return ((ExplorerFigure) libofi.getParent());
	}

	private ExplorerFigureBuilder getExplorerFigureBuilder() {
		return ExplorerFigureBuilder.getInstance();
	}

	private ExplorerManager getExplorerManager() {
		return ExplorerManager.INSTANCE;
	}

	private ExplorerModelBuilder getExplorerModelBuilder() {
		return ExplorerModelBuilder.getInstance();
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass die ueberge- bene
	 * TextBoxFigure mit einem AttributeModel verknuepft ist. Anhand der
	 * TextBoxFigure werden zunaechst alle existierenden ListBoxFigures
	 * ausfindig gemacht. Alle diese werden durchsucht nach dem Object. Dieses
	 * wird dann der Rueckgabevariablen zugewiesen. Wird keines gefunden, wird
	 * also null zurueck- gegeben.
	 */
	private ListBoxFigure getListBoxFigureToShow() {
		StandardEnumeration enumeration;
		ListBoxFigure liBoFiToShow = null;
		ObjectModel tempObjModl;
		enumeration = ((ExplorerModel) this.getSelectedObject()).getObjectModels();
		while (enumeration.hasMoreElements()) {
			tempObjModl = (ObjectModel) enumeration.nextElement();
			if (tempObjModl.getObject() == this.getObjectToCopy().getModel().getObject()) {
				liBoFiToShow = (ListBoxFigure) tempObjModl.getFigure();
			}
		}
		return liBoFiToShow;
	}

	private ListBoxFigure getObjectToCopy() {
		return this.objectToCopy;
	}

	private Object getSelectedObject() {
		return this.selectedObject;
	}

	private void initialize() {
		this.getCopyDialog().addListSelectionListener(this);
		this.getCopyDialog().addOKButtonActionListener(this);
		this.getCopyDialog().addCancelButtonActionListener(this);
		// this.getCopyDialog().addWindowListener(this);
	}

	private void ok() {
		this.addObjectToModel();
		this.setCopyColor(this.getListBoxFigureToShow());
		this.getCopyDialog().setVisible(false);
		this.setAllOtherFramesEnabled(true);
		this.setDestinationFrameFocus();
	}

	private void setAllOtherFramesEnabled(boolean value) {
		Object[] objects = this.getExplorerManager().getExistingExplorerModels().toArray();
		for (int i = 0; i < objects.length; i++) {
			((de.paragon.explorer.figure.ExplorerFigure) ((ExplorerModel) objects[i]).getFigure()).getFrame().getGlassPane().setVisible(!value);
		}
	}

	private void setCopyColor(ListBoxFigure libofi) {
		((de.paragon.explorer.figure.TextBoxFigure) ((ObjectModel) libofi.getModel()).getHeaderModel().getFigure()).setBackground(this.getColorManager(libofi).getCopyBackground());
	}

	private void setCopyDialog(ObjectCopyDialog newCopyDialog) {
		this.copyDialog = newCopyDialog;
	}

	private void setDestinationFrameFocus() {
		((ExplorerFigure) ((ExplorerModel) this.getSelectedObject()).getFigure()).getFrame().requestFocus();
	}

	private void setExplorerFigure(ExplorerFigure newExplorerFigure) {
		this.explorerFigure = newExplorerFigure;
	}

	private void setObjectToCopy(ListBoxFigure newObjectToCopy) {
		this.objectToCopy = newObjectToCopy;
	}

	private void setSelectedObject(Object newSelectedObject) {
		this.selectedObject = newSelectedObject;
	}

	public final void valueChanged(ListSelectionEvent event) {
		if (event.getValueIsAdjusting()) {
			for (int i = 0; i < this.getAllOtherExplorers().size(); i++) {
				if (this.getCopyDialog().getSelectionModel().isSelectedIndex(i)) {
					this.setSelectedObject(this.getAllOtherExplorers().elementAt(i));
					this.getCopyDialog().setOKButtonEnabled(true);
				}
			}
		}
	}

	public void windowClosing(WindowEvent e) {
		this.setAllOtherFramesEnabled(true);
	}
}
