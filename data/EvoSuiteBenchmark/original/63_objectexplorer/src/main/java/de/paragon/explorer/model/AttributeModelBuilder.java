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
package de.paragon.explorer.model;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Vector;

import de.paragon.explorer.util.StandardEnumeration;

public final class AttributeModelBuilder {
	private static AttributeModelBuilder	singleton;

	public static AttributeModelBuilder getInstance() {
		return AttributeModelBuilder.getSingleton();
	}

	private static AttributeModelBuilder getSingleton() {
		if (AttributeModelBuilder.singleton == null) {
			AttributeModelBuilder.setSingleton(new AttributeModelBuilder());
		}
		return AttributeModelBuilder.singleton;
	}

	private static void setSingleton(AttributeModelBuilder builder) {
		AttributeModelBuilder.singleton = builder;
	}

	private AttributeModelBuilder() {
		super();
	}

	protected void createArrayAttributeModel(ObjectModel objModl) {
		AttributeModel attrModl = new ArrayAttributeModel();
		objModl.addAttributeModel(attrModl);
		attrModl.setObjectModel(objModl);
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass das ObjectModel bereits mit
	 * einem Object versehen ist. Fuer jedes Feld dieses Objects erzeugt sie ein
	 * neues AttributeModel und verknuepft es mit dem ObjectModel.
	 */
	protected void createAttributeModel(ObjectModel objModl) {
		AttributeModel attrModl = new AttributeModel();
		objModl.addAttributeModel(attrModl);
		attrModl.setObjectModel(objModl);
	}

	protected void createStandardAttributeModel(ObjectModel objModl) {
		AttributeModel attrModl = new StandardAttributeModel();
		objModl.addAttributeModel(attrModl);
		attrModl.setObjectModel(objModl);
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, das bereits fuer jedes Feld des
	 * Objects in objectModel ein AttributeModel er- zeugt wurde. Jedem dieser
	 * AttributeModels wird der jeweilige Wert des Feldes zugewiesen.
	 */
	public void setArrayAttributeData(ObjectModel objModl) {
		int i = 0;
		// Object aObject;
		StandardEnumeration attrModls = objModl.getAttributeModels();
		while (attrModls.hasMoreElements()) {
			ArrayAttributeModel attrModl = (ArrayAttributeModel) attrModls.nextElement();
			// aObject = Array.get(objModl.getObject(), i);
			attrModl.setPos(i);
			attrModl.setName("[" + (Integer.valueOf(i)).toString() + "]");
			attrModl.setType(objModl.getObject().getClass().getComponentType());
			// if (aObject != null) {
			// attrModl.setValue(aObject);
			// } else {
			// attrModl.setValue(NullObject.getNullObject());
			// }
			i = i + 1;
		}
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, das bereits fuer jedes Feld des
	 * Objects in objectModel ein AttributeModel erzeugt wurde. Jedem dieser
	 * AttributeModels wird der jeweilige Wert des Feldes zugewiesen.
	 */
	public void setStandardAttributeData(ObjectModel objModl) {
		int i = 0;
		// Object aObject;
		Field field;
		StandardEnumeration attrModls = objModl.getAttributeModels();
		Vector<Field> fields = objModl.getDeclaredFields();
		while (attrModls.hasMoreElements()) {
			StandardAttributeModel attrModl = (StandardAttributeModel) attrModls.nextElement();
			field = fields.elementAt(i);
			attrModl.setField(field);
			attrModl.setModifiers(field.getModifiers());
			attrModl.setType(field.getType());
			attrModl.setName(field.getName());
			i = i + 1;
		}
		Vector<AttributeModel> vector2set = new Vector<AttributeModel>();
		Vector<?> vector2transfer = attrModls.getVector();
		for (Object object2 : vector2transfer) {
			vector2set.add((AttributeModel) object2);
		}
		Vector<AttributeModel> vector = vector2set;
		Collections.sort(vector, new AttributeModelComparator());
	}
}
