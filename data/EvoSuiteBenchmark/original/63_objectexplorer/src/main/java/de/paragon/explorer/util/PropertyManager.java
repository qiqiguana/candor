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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * @author Stefan Jockenhövel
 * 
 */
public final class PropertyManager {
	private static final String		EXPLORER_INI								= "explorer.ini";
	private static PropertyManager	instance									= null;
	private static final Logger		logger										= LoggerFactory.make();
	private static Properties		properties									= null;
	private static final String		PROPERTY_HIDE_NULL_ATTRIBUTES				= "hideNullAttributes";
	private static final String		PROPERTY_HIDE_STATIC_ATTRIBUTES				= "hideStaticAttributes";
	private static final String		PROPERTY_HIDE_UNEXPLORED_ATTRIBUTES			= "hideUnexploredAttributes";
	private static final String		PROPERTYMANAGER_ERROR_FILE_NOT_FOUND		= "propertymanager.error_file_not_found";
	private static final String		PROPERTYMANAGER_ERROR_FILE_NOT_SAVED		= "propertymanager.error_file_not_saved";
	private static final String		PROPERTYMANAGER_ERROR_WHILE_CLOSE			= "propertymanager.error_while_close";
	private static final String		PROPERTYMANAGER_ERROR_WHILE_READING			= "propertymanager.error_while_reading";
	private static final String		PROPERTYMANAGER_ERROR_WITH_MEMORY_LOCATION	= "propertymanager.error_with_memory_location";
	private static final String		PROPERTYMANAGER_INFO_FILE_APPLIED			= "propertymanager.info_file_applied";
	private static final String		PROPERTYMANAGER_INFO_FILE_NOT_FOUND			= "propertymanager.info_file_not_found";
	private static final String		PROPERTYMANAGER_INFO_FILE_UPDATED			= "propertymanager.info_file_updated";
	private static final String		PROPERTYMANAGER_INFO_FILE_WAS_UP_TO_DATE	= "propertymanager.info_file_was_up_to_date";
	private static final String		PROPERTYMANAGER_INFO_SUCCESFUL_LOADING		= "propertymanager.info_succesful_loading";
	private static final String		TEXT_PROPERTIES_INITIALIZE					= "/* properties initialize */";
	private static final String		TEXT_PROPERTIES_UPDATET						= "/* properties updatet */";
	private static final String		TRUE_AS_STRING								= "true";
	private static final String		USER_HOME									= "user.home";
	private static final Integer	NUMBER_OF_PROPERTIES						= 3;

	public static PropertyManager getInstance() {
		if ((PropertyManager.instance == null)) {
			PropertyManager.instance = new PropertyManager();
		}
		return PropertyManager.instance;
	}

	private PropertyManager() {
		String dataDir = System.getProperty(PropertyManager.USER_HOME);
		String iniFile = null;
		//
		iniFile = this.getCompletePath4IniFile();
		if (iniFile != null) {
			PropertyManager.properties = new Properties();
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(iniFile);
				PropertyManager.properties.load(fis);
				if (PropertyManager.logger.isDebugEnabled()) {
					PropertyManager.properties.list(System.out);
					if (PropertyManager.properties.size() != PropertyManager.NUMBER_OF_PROPERTIES) {
						if (!PropertyManager.properties.containsKey(PropertyManager.PROPERTY_HIDE_NULL_ATTRIBUTES)) {
							PropertyManager.properties.put(PropertyManager.PROPERTY_HIDE_NULL_ATTRIBUTES, PropertyManager.TRUE_AS_STRING);
						}
						if (!PropertyManager.properties.containsKey(PropertyManager.PROPERTY_HIDE_STATIC_ATTRIBUTES)) {
							PropertyManager.properties.put(PropertyManager.PROPERTY_HIDE_STATIC_ATTRIBUTES, PropertyManager.TRUE_AS_STRING);
						}
						if (!PropertyManager.properties.containsKey(PropertyManager.PROPERTY_HIDE_UNEXPLORED_ATTRIBUTES)) {
							PropertyManager.properties.put(PropertyManager.PROPERTY_HIDE_UNEXPLORED_ATTRIBUTES, PropertyManager.TRUE_AS_STRING);
						}
						this.saveIniFile(iniFile, PropertyManager.TEXT_PROPERTIES_INITIALIZE);
						if (PropertyManager.logger.isDebugEnabled()) {
							PropertyManager.logger.debug(ResourceBundlePurchaser.getSubstitutedMessage(PropertyManager.PROPERTYMANAGER_INFO_FILE_UPDATED,
									PropertyManager.EXPLORER_INI));
						}
					}
					PropertyManager.logger.debug(ResourceBundlePurchaser.getSubstitutedMessage(PropertyManager.PROPERTYMANAGER_INFO_SUCCESFUL_LOADING, iniFile));
				}
			}
			catch (FileNotFoundException ex) {
				this.createAndSaveNewIniFile(iniFile, dataDir);
			}
			catch (IOException ex) {
				PropertyManager.logger.error(ResourceBundlePurchaser.getSubstitutedMessage(PropertyManager.PROPERTYMANAGER_ERROR_WHILE_READING, iniFile), ex);
			}
			try {
				if (fis != null) {
					fis.close();
				}
			}
			catch (IOException ex) {
				PropertyManager.logger.error(ResourceBundlePurchaser.getSubstitutedMessage(PropertyManager.PROPERTYMANAGER_ERROR_WHILE_CLOSE, iniFile), ex);
			}
		}
		else {
			PropertyManager.logger.error(ResourceBundlePurchaser.getSubstitutedMessage(PropertyManager.PROPERTYMANAGER_ERROR_WITH_MEMORY_LOCATION, PropertyManager.USER_HOME));
		}
	}

	private void createAndSaveNewIniFile(String iniFile, String dataDir) {
		if (PropertyManager.logger.isDebugEnabled()) {
			PropertyManager.logger.debug(ResourceBundlePurchaser.getSubstitutedMessage(PropertyManager.PROPERTYMANAGER_INFO_FILE_NOT_FOUND, PropertyManager.EXPLORER_INI, dataDir));
		}
		// neue Ini-Datei anlegen
		FileWriter fw = null;
		try {
			fw = new FileWriter(iniFile);
			if (PropertyManager.logger.isDebugEnabled()) {
				PropertyManager.logger.debug(ResourceBundlePurchaser
						.getSubstitutedMessage(PropertyManager.PROPERTYMANAGER_INFO_FILE_APPLIED, PropertyManager.EXPLORER_INI, dataDir));
			}
			// Default-Properties anlegen
			PropertyManager.properties = new Properties();
			PropertyManager.properties.put(PropertyManager.PROPERTY_HIDE_NULL_ATTRIBUTES, PropertyManager.TRUE_AS_STRING);
			PropertyManager.properties.put(PropertyManager.PROPERTY_HIDE_STATIC_ATTRIBUTES, PropertyManager.TRUE_AS_STRING);
			PropertyManager.properties.put(PropertyManager.PROPERTY_HIDE_UNEXPLORED_ATTRIBUTES, PropertyManager.TRUE_AS_STRING);
			this.saveIniFile(iniFile, PropertyManager.TEXT_PROPERTIES_INITIALIZE);
			if (PropertyManager.logger.isDebugEnabled()) {
				PropertyManager.logger.debug(ResourceBundlePurchaser.getSubstitutedMessage(PropertyManager.PROPERTYMANAGER_INFO_FILE_UPDATED, PropertyManager.EXPLORER_INI));
			}
		}
		catch (IOException ex) {
			PropertyManager.logger.error(ResourceBundlePurchaser.getSubstitutedMessage(PropertyManager.PROPERTYMANAGER_ERROR_FILE_NOT_SAVED, PropertyManager.EXPLORER_INI), ex);
		}
		try {
			if (fw != null) {
				fw.close();
			}
		}
		catch (IOException ex) {
			PropertyManager.logger.error(ResourceBundlePurchaser.getSubstitutedMessage(PropertyManager.PROPERTYMANAGER_ERROR_WHILE_CLOSE, PropertyManager.EXPLORER_INI), ex);
		}
	}

	private String getCompletePath4IniFile() {
		String dataDir = System.getProperty(PropertyManager.USER_HOME);
		String iniFile = PropertyManager.EXPLORER_INI;
		if (dataDir != null) {
			if (!dataDir.endsWith(File.separator)) {
				dataDir = dataDir + File.separator;
			}
			return dataDir + iniFile;
		}
		return null;
	}

	public boolean getHideNullAttribut() {
		String returnValue = PropertyManager.properties.getProperty(PropertyManager.PROPERTY_HIDE_NULL_ATTRIBUTES);
		if ((returnValue != null) && returnValue.equals(PropertyManager.TRUE_AS_STRING)) {
			return true;
		}
		return false;
	}

	public boolean getHideStaticAttribut() {
		String returnValue = PropertyManager.properties.getProperty(PropertyManager.PROPERTY_HIDE_STATIC_ATTRIBUTES);
		if ((returnValue != null) && returnValue.equals(PropertyManager.TRUE_AS_STRING)) {
			return true;
		}
		return false;
	}

	public boolean getHideUnexploredAttribut() {
		String returnValue = PropertyManager.properties.getProperty(PropertyManager.PROPERTY_HIDE_UNEXPLORED_ATTRIBUTES);
		if ((returnValue != null) && returnValue.equals(PropertyManager.TRUE_AS_STRING)) {
			return true;
		}
		return false;
	}

	private void saveIniFile(String iniFile, String comment) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(iniFile);
		}
		catch (FileNotFoundException ex) {
			PropertyManager.logger.error(ResourceBundlePurchaser.getSubstitutedMessage(PropertyManager.PROPERTYMANAGER_ERROR_FILE_NOT_FOUND, PropertyManager.EXPLORER_INI, ex));
		}
		try {
			PropertyManager.properties.store(out, comment);
		}
		catch (IOException ex) {
			PropertyManager.logger.error(ResourceBundlePurchaser.getSubstitutedMessage(PropertyManager.PROPERTYMANAGER_ERROR_FILE_NOT_SAVED, PropertyManager.EXPLORER_INI), ex);
		}
		try {
			if (out != null) {
				out.close();
			}
		}
		catch (IOException ex) {
			PropertyManager.logger.error(ResourceBundlePurchaser.getSubstitutedMessage(PropertyManager.PROPERTYMANAGER_ERROR_WHILE_CLOSE, PropertyManager.EXPLORER_INI), ex);
		}
	}

	public void setHideAttributes(boolean newValueHideNull, boolean newValueHideStatic, boolean newValueHideUnexplored) {
		boolean save = false;
		if (!Boolean.valueOf(this.getHideNullAttribut()).equals(newValueHideNull)) {
			PropertyManager.properties.setProperty(PropertyManager.PROPERTY_HIDE_NULL_ATTRIBUTES, Boolean.valueOf(newValueHideNull).toString());
			save = true;
		}
		if (!save && !Boolean.valueOf(this.getHideStaticAttribut()).equals(newValueHideStatic)) {
			PropertyManager.properties.setProperty(PropertyManager.PROPERTY_HIDE_STATIC_ATTRIBUTES, Boolean.valueOf(newValueHideStatic).toString());
			if (!save) {
				save = true;
			}
		}
		if (!save && !Boolean.valueOf(this.getHideUnexploredAttribut()).equals(newValueHideUnexplored)) {
			PropertyManager.properties.setProperty(PropertyManager.PROPERTY_HIDE_UNEXPLORED_ATTRIBUTES, Boolean.valueOf(newValueHideUnexplored).toString());
			if (!save) {
				save = true;
			}
		}
		if (save) {
			// in die Ini-Datei schreiben
			String iniFile = this.getCompletePath4IniFile();
			if (iniFile != null) {
				this.saveIniFile(iniFile, PropertyManager.TEXT_PROPERTIES_UPDATET);
				if (PropertyManager.logger.isDebugEnabled()) {
					PropertyManager.logger.debug(ResourceBundlePurchaser.getSubstitutedMessage(PropertyManager.PROPERTYMANAGER_INFO_FILE_UPDATED, PropertyManager.EXPLORER_INI));
				}
			}
			else {
				PropertyManager.logger.error(ResourceBundlePurchaser.getSubstitutedMessage(PropertyManager.PROPERTYMANAGER_ERROR_WITH_MEMORY_LOCATION, PropertyManager.USER_HOME));
			}
		}
		else {
			if (PropertyManager.logger.isDebugEnabled()) {
				PropertyManager.logger.debug(ResourceBundlePurchaser.getSubstitutedMessage(PropertyManager.PROPERTYMANAGER_INFO_FILE_WAS_UP_TO_DATE, PropertyManager.EXPLORER_INI));
			}
		}
	}
}
