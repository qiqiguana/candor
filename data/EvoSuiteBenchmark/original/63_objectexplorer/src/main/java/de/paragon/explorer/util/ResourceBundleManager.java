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

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class ResourceBundleManager {
	private static final String				ERROR_KEY_NOT_FOUND									= "resourcebundlemanager.error_key_not_found";
	private static final String				INFO_RESOURCE_BUNDLE_WURDE_ERFOLGREICH_GELADEN		= "ResourceBundle wurde erfolgreich geladen.";
	private static final String				ERROR_RESOURCE_BUNDLE_KONNTE_NICHT_GEFUNDEN_WERDEN	= "ResourceBundle konnte nicht gefunden werden!";
	private static final String				INFO_RESOURCE_BUNDLE_WIRD_GELADEN_VON				= "ResourceBundle wird geladen von: ";
	private static final long				serialVersionUID									= 1L;
	private static final Logger				logger												= LoggerFactory.make();
	private static ResourceBundleManager	instance											= null;
	private static final String				RB_PATH												= "resources/logging_text";
	private static ResourceBundle			bundle;

	protected static ResourceBundleManager getInstance() {
		if ((ResourceBundleManager.instance == null)) {
			ResourceBundleManager.instance = new ResourceBundleManager();
		}
		return ResourceBundleManager.instance;
	}

	/**
	 * Protected Constructor
	 * 
	 */
	protected ResourceBundleManager() {
		try {
			if (ResourceBundleManager.logger.isDebugEnabled()) {
				ResourceBundleManager.logger.debug(ResourceBundleManager.INFO_RESOURCE_BUNDLE_WIRD_GELADEN_VON + ResourceBundleManager.RB_PATH);
			}
			ResourceBundleManager.bundle = ResourceBundle.getBundle(ResourceBundleManager.RB_PATH);
		}
		catch (Exception ex) {
			throw new RuntimeException(ResourceBundleManager.ERROR_RESOURCE_BUNDLE_KONNTE_NICHT_GEFUNDEN_WERDEN);
		}
		if (ResourceBundleManager.bundle == null) {
			throw new RuntimeException(ResourceBundleManager.ERROR_RESOURCE_BUNDLE_KONNTE_NICHT_GEFUNDEN_WERDEN);
		}
		if (ResourceBundleManager.logger.isDebugEnabled()) {
			ResourceBundleManager.logger.debug(ResourceBundleManager.INFO_RESOURCE_BUNDLE_WURDE_ERFOLGREICH_GELADEN);
		}
	}

	protected String getPropertyFromFromResourceBundle(String key) {
		String propertyValue = null;
		try {
			propertyValue = ResourceBundleManager.bundle.getString(key);
		}
		catch (Exception ex) {
			ResourceBundleManager.logger.error(MessageFormat.format(ResourceBundleManager.getInstance()
					.getPropertyFromFromResourceBundle(ResourceBundleManager.ERROR_KEY_NOT_FOUND), key));
		}
		return propertyValue;
	}

	protected String getSubstitutedMessageFromResourceBundle(String property, Object... value2Substitute) {
		String propertyFromResourceBundle = null;
		if ((property == null) || (value2Substitute == null)) {
			return null;
		}
		propertyFromResourceBundle = ResourceBundleManager.getInstance().getPropertyFromFromResourceBundle(property);
		if (propertyFromResourceBundle != null) {
			return MessageFormat.format(propertyFromResourceBundle, value2Substitute);
		}
		//
		return null;
	}
}
