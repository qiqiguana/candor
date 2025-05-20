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
package de.paragon.explorer.util;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Vector;

import de.paragon.explorer.model.AttributeModel;
import de.paragon.explorer.model.NullObject;
import de.paragon.explorer.model.ObjectModel;

public class ObjectViewManager {
	private boolean						allAttributesVisible;
	private boolean						nullAttributesVisible	= PropertyManager.getInstance().getHideNullAttribut();
	private boolean						staticAttributesVisible	= PropertyManager.getInstance().getHideStaticAttribut();
	private boolean						attributesToDisplay;
	private boolean						removeDependencies		= PropertyManager.getInstance().getHideUnexploredAttribut();
	private Collection<AttributeModel>	attributeModels;
	private ObjectModel					objectModel;
	private boolean						unexploredAttributesVisible;

	/**
	 * ObjectViewManager constructor comment.
	 */
	public ObjectViewManager() {
		super();
	}

	/**
	 * ObjectViewManager constructor comment.
	 */
	public ObjectViewManager(ObjectModel newObjectModel) {
		super();
		this.setObjectModel(newObjectModel);
	}

	public boolean isAllAttributesVisible() {
		return this.allAttributesVisible;
	}

	public Collection<AttributeModel> getAttributeModels() {
		if (this.attributeModels == null) {
			this.setAttributeModels(new Vector<AttributeModel>());
		}
		return this.attributeModels;
	}

	public Collection<AttributeModel> getInvisibleAttributes() {
		AttributeModel attrModl;
		Vector<AttributeModel> vector = new Vector<AttributeModel>();
		StandardEnumeration attrModls = this.getObjectModel().getAttributeModels();
		while (attrModls.hasMoreElements()) {
			attrModl = (AttributeModel) attrModls.nextElement();
			if (!(attrModl.isVisible())) {
				vector.addElement(attrModl);
			}
		}
		return vector;
	}

	public ObjectModel getObjectModel() {
		return this.objectModel;
	}

	public boolean isStaticAttributesVisible() {
		return this.staticAttributesVisible;
	}

	public boolean isUnexploredAttributesVisible() {
		return this.unexploredAttributesVisible;
	}

	public Collection<AttributeModel> getVisibleAttributes() {
		AttributeModel attrModl;
		Vector<AttributeModel> vector = new Vector<AttributeModel>();
		StandardEnumeration attrModls = this.getObjectModel().getAttributeModels();
		while (attrModls.hasMoreElements()) {
			attrModl = (AttributeModel) attrModls.nextElement();
			if (attrModl.isVisible()) {
				vector.addElement(attrModl);
			}
		}
		return vector;
	}

	public boolean hasAttributesToDisplay() {
		return this.attributesToDisplay;
	}

	/**
	 * @return Returns the removeDependencies.
	 */
	public boolean isRemoveDependencies() {
		return this.removeDependencies;
	}

	public void setAllAttributesVisible(boolean newAllAttributesVisible) {
		this.allAttributesVisible = newAllAttributesVisible;
	}

	private void setAttributeModels(Collection<AttributeModel> newAttributeModels) {
		this.attributeModels = newAttributeModels;
	}

	public void setAttributesToDisplay(boolean newValue) {
		this.attributesToDisplay = newValue;
	}

	public void setNullAttributesVisible(boolean newNullAttributesVisible) {
		this.nullAttributesVisible = newNullAttributesVisible;
	}

	private void setObjectModel(ObjectModel newObjectModel) {
		this.objectModel = newObjectModel;
	}

	/**
	 * @param removeDependencies
	 *            The removeDependencies to set.
	 */
	public void setRemoveDependencies(boolean newRemoveDependencies) {
		this.removeDependencies = newRemoveDependencies;
	}

	public void setStaticAttributesVisible(boolean newStaticAttributesVisible) {
		this.staticAttributesVisible = newStaticAttributesVisible;
	}

	public void setUnexploredAttributesVisible(boolean newUnexploredAttributesVisible) {
		this.unexploredAttributesVisible = newUnexploredAttributesVisible;
	}

	public boolean shouldBeVisible(AttributeModel attrModl) {
		if (!(this.isNullAttributesVisible()) && NullObject.isNullObject(attrModl.getValue())) {
			return false;
		}
		if (!(this.isStaticAttributesVisible()) && (Modifier.isStatic(attrModl.getModifiers()))) {
			return false;
		}
		if (!(this.isUnexploredAttributesVisible()) && (attrModl.getConnectionModel() == null)) {
			return false;
		}
		if (this.isAllAttributesVisible()) {
			return true;
		}
		return false;
	}

	public boolean isNullAttributesVisible() {
		return this.nullAttributesVisible;
	}
}
