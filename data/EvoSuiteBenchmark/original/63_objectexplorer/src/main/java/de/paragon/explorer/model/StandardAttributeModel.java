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

import org.apache.log4j.Logger;

import de.paragon.explorer.gui.Warning;
import de.paragon.explorer.util.LoggerFactory;
import de.paragon.explorer.util.ResourceBundlePurchaser;

public class StandardAttributeModel extends AttributeModel {
	private static final Logger	logger			= LoggerFactory.make();
	private static final String	ERROR_EXCEPTION	= "standardattributemodel.error_exception";
	private Field				field;

	/**
	 * StandardAttributeModel constructor comment.
	 */
	public StandardAttributeModel() {
		super();
	}

	private Object getAttributeValue(Field field2Change, Object aObject) {
		Object value = null;
		try {
			field2Change.setAccessible(true);
			value = field2Change.get(aObject);
			field2Change.setAccessible(false);
		}
		catch (IllegalAccessException ex1) {
			StandardAttributeModel.logger.error(ResourceBundlePurchaser.getMessage(StandardAttributeModel.ERROR_EXCEPTION), ex1);
			Warning.showWarning(ResourceBundlePurchaser.getSubstitutedMessage(StandardAttributeModel.ERROR_EXCEPTION, ex1.toString()));
		}
		catch (IllegalArgumentException ex2) {
			StandardAttributeModel.logger.error(ResourceBundlePurchaser.getMessage(StandardAttributeModel.ERROR_EXCEPTION), ex2);
			Warning.showWarning(ResourceBundlePurchaser.getSubstitutedMessage(StandardAttributeModel.ERROR_EXCEPTION, ex2.toString()));
		}
		catch (NullPointerException ex3) {
			StandardAttributeModel.logger.error(ResourceBundlePurchaser.getMessage(StandardAttributeModel.ERROR_EXCEPTION), ex3);
			Warning.showWarning(ResourceBundlePurchaser.getSubstitutedMessage(StandardAttributeModel.ERROR_EXCEPTION, ex3.toString()));
		}
		return value;
	}

	private Field getField() {
		return this.field;
	}

	@Override
	public Object getValue() {
		Object value = this.getAttributeValue(this.getField(), this.getObjectModel().getObject());
		if (value == null) {
			value = NullObject.getNullObject();
		}
		return value;
	}

	public void setField(Field newField) {
		this.field = newField;
	}
}
