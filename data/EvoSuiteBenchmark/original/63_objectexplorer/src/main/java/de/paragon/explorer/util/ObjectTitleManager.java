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

import org.apache.log4j.Logger;

import de.paragon.explorer.ProjectConstants4ObjectExplorer;
import de.paragon.explorer.model.ObjectModel;

/**
 * In dieser Klasse sind die Reglen hinterlegt, wie der Title eines Objekts
 * aussieht. Bei Integer und String wird der Wert direkt angezeigt.
 * 
 */
public final class ObjectTitleManager {
	private static final Logger			logger	= LoggerFactory.make();
	private static ObjectTitleManager	objectTitleManager;

	public static ObjectTitleManager getSingleton() {
		if (ObjectTitleManager.objectTitleManager == null) {
			ObjectTitleManager.setSingleton(new ObjectTitleManager());
		}
		return ObjectTitleManager.objectTitleManager;
	}

	private static void setSingleton(ObjectTitleManager newObjectTitleManager) {
		ObjectTitleManager.objectTitleManager = newObjectTitleManager;
	}

	private Collection<Class<?>>	valuesToShow;

	/**
	 * ObjectViewManager constructor comment.
	 */
	private ObjectTitleManager() {
		super();
		this.getValuesToShow().add(String.class);
		this.getValuesToShow().add(Integer.class);
	}

	public String getCompleteTitle(ObjectModel objModl) {
		if (objModl.isNullObject()) {
			return "";
		}
		else {
			return Modifier.toString(objModl.getObject().getClass().getModifiers()) + " " + this.getTypeName(objModl.getObject().getClass()) + "  "
					+ this.getValueAsString(objModl.getObject());
		}
	}

	private String getShortTypeName(Class<?> type) {
		if (this.isArrayOfPrimitiveType(type)) {
			this.getTypeName(type);
		}
		return this.getTypeName(type).substring(this.getTypeName(type).lastIndexOf(".") + 1);
	}

	public String getTitle(ObjectModel objModl) {
		Object object = objModl.getObject();
		Class<? extends Object> objectClass = object.getClass();
		if (this.getValuesToShow().contains(objectClass)) {
			StringBuilder sb = new StringBuilder();
			sb.append(this.getShortTypeName(object.getClass()));
			sb.append("    ");
			sb.append(object.toString());
			return sb.toString();
		}
		if (objModl.isNullObject()) {
			return ProjectConstants4ObjectExplorer.getSTRING_NULL();
		}
		return this.getShortTypeName(object.getClass());
	}

	private String getTypeName(Class<?> type) {
		if (type.isArray()) {
			try {
				Class<?> clazz = type;
				int dimensions = 0;
				while (clazz.isArray()) {
					dimensions++;
					clazz = clazz.getComponentType();
				}
				StringBuilder sb = new StringBuilder();
				sb.append(clazz.getName());
				for (int i = 0; i < dimensions; i++) {
					sb.append("[]");
				}
				return sb.toString();
			}
			catch (Throwable t) {
				ObjectTitleManager.logger.error("-->> Fehler bei getTypeName", t);
			}
		}
		return type.getName();
	}

	private String getValueAsString(Object object) {
		if (object instanceof Vector) {
			Vector<Object> vector2return = new Vector<Object>();
			Vector<?> vector2transfer = (Vector<?>) object;
			for (Object object2 : vector2transfer) {
				vector2return.add(object2);
			}
			return ToStringConverter.convertVectorToString(vector2return);
		}
		else {
			return object.toString();
		}
	}

	private Collection<Class<?>> getValuesToShow() {
		if (this.valuesToShow == null) {
			this.setValuesToShow(new Vector<Class<?>>());
		}
		return this.valuesToShow;
	}

	private boolean isArrayOfPrimitiveType(Class<?> type) {
		return type.getName().startsWith("[") && !(type.getName().startsWith("[L"));
	}

	private void setValuesToShow(Collection<Class<?>> newValuesToShow) {
		this.valuesToShow = newValuesToShow;
	}
}
