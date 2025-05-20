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
import java.util.Vector;

import org.apache.log4j.Logger;

import de.paragon.explorer.ProjectConstants4ObjectExplorer;
import de.paragon.explorer.model.AttributeModel;
import de.paragon.explorer.model.NullObject;

/**
 * In dieser Klasse sind die Reglen hinterlegt, wie der Title eines Attributes
 * aussieht, abhaengig vom Typ.
 */
public final class AttributeTitleManager {
	private static final Logger				logger	= LoggerFactory.make();
	private static AttributeTitleManager	singleton;

	public static AttributeTitleManager getSingleton() {
		if (AttributeTitleManager.singleton == null) {
			AttributeTitleManager.setSingleton(new AttributeTitleManager());
		}
		return AttributeTitleManager.singleton;
	}

	private static void setSingleton(AttributeTitleManager newSingleton) {
		AttributeTitleManager.singleton = newSingleton;
	}

	private AttributeTitleManager() {
		super();
	}

	public String getCompleteTitle(AttributeModel attrModl) {
		return Modifier.toString(attrModl.getModifiers()) + " " + this.getTypeName(attrModl.getType()) + "  " + attrModl.getName() + "     "
				+ this.getValueAsString(attrModl.getValue());
	}

	public String getTitle(AttributeModel attrModl) {
		if (attrModl.isAttributePrimitive()) {
			return attrModl.getName() + "     " + this.getValueAsString(attrModl.getValue());
		}
		else {
			return attrModl.getName();
		}
	}

	private String getTypeName(Class<?> type) {
		if (type.isArray()) {
			try {
				Class<?> cl = type;
				int dimensions = 0;
				while (cl.isArray()) {
					dimensions++;
					cl = cl.getComponentType();
				}
				StringBuffer sb = new StringBuffer();
				sb.append(cl.getName());
				for (int i = 0; i < dimensions; i++) {
					sb.append("[]");
				}
				return sb.toString();
			}
			catch (Throwable t) {
				AttributeTitleManager.logger.error("-->> Fehler bei getTypeName", t);
			}
		}
		return type.getName();
	}

	private String getValueAsString(Object object) {
		if (object instanceof NullObject) {
			return ProjectConstants4ObjectExplorer.getSTRING_NULL();
		}
		else if (object instanceof Vector) {
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
}
