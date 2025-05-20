/*
 * Copyright (C) 2000-2003 Greg Merrill (greghmerrill@yahoo.com)
 * 
 * This file is part of Follow (http://follow.sf.net).
 * 
 * Follow is free software; you can redistribute it and/or modify it under the
 * terms of version 2 of the GNU General Public License as published by the Free
 * Software Foundation.
 * 
 * Follow is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Follow; if not, write to the Free Software Foundation, Inc., 59 Temple Place,
 * Suite 330, Boston, MA 02111-1307 USA
 */

package ghm.follow.config;

import ghm.follow.FollowApp;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import java.util.logging.Logger;

/**
 * @author <a href="mailto:greghmerrill@yahoo.com">Greg Merrill</a>
 */
public class FollowAppAttributes
{
	private static Logger log = Logger.getLogger(FollowAppAttributes.class.getName());
	EnumeratedProperties properties;
	private EnumeratedProperties defaultProperties;
	private FollowAppAttributes defaultAttributes;
	static final String userHome = System.getProperty("user.home");
	public static final String PROPERTY_FILE_NAME = ".followApp.properties";
	static final String defaultPropertyFileName = userHome + FollowApp.FILE_SEPARATOR
			+ PROPERTY_FILE_NAME;
	private File propertyFile;
	public static final String PROPERTY_PROTOTYPE_FILE_NAME = "followApp.properties.prototype";
	public static final int BUFFER_SIZE = 32768;

	public static final String HEIGHT_KEY = "height";
	public static final String WIDTH_KEY = "width";
	public static final String X_KEY = "x";
	public static final String Y_KEY = "y";
	public static final String FOLLOWED_FILES_KEY = "followedFiles";
	public static final String TAB_PLACEMENT_KEY = "tabs.placement";
	public static final String SELECTED_TAB_INDEX_KEY = "tabs.selectedIndex";
	public static final String LAST_FILE_CHOOSER_DIR_KEY = "fileChooser.lastDir";
	public static final String BUFFER_SIZE_KEY = "bufferSize";
	public static final String LATENCY_KEY = "latency";
	public static final String ATTRIBUTES_VERSION_KEY = "attributesVersion";
	public static final String FONT_FAMILY_KEY = "fontFamily";
	public static final String FONT_STYLE_KEY = "fontStyle";
	public static final String FONT_SIZE_KEY = "fontSize";
	public static final String CONFIRM_DELETE_KEY = "confirmDelete";
	public static final String CONFIRM_DELETE_ALL_KEY = "confirmDeleteAll";
	public static final String AUTO_SCROLL_KEY = "autoScroll";
	public static final String EDITOR_KEY = "editor";
	public static final String TAB_SIZE_KEY = "tabSize";
	public static final String RECENT_FILES_MAX_KEY = "recentFilesMax";
	public static final String RECENT_FILES_KEY = "recentFiles";

	// Versions
	public static final int UNVERSIONED = 0;
	public static final int v1_1 = 1;
	public static final int v1_2 = 2;
	public static final int v1_3 = 3;
	public static final int v1_3_2 = 4;
	public static final int v1_4 = 5;
	public static final int v1_5_0 = 6;
	public static final int v1_6_0 = 7;

	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public FollowAppAttributes() throws IOException
	{
		// have to cast null so that the constructor call isn't ambiguous
		this((File) null);
	}

	public FollowAppAttributes(File exitingPropertyFile) throws IOException
	{
		if (exitingPropertyFile != null)
		{
			propertyFile = exitingPropertyFile;
		}
		else
		{
			propertyFile = new File(defaultPropertyFileName);
		}

		if (!(propertyFile.exists()))
		{
			// If the property file doesn't exist, we create a default property
			// file using a prototype property file stored somewhere on the
			// classpath
			log.info("No property file for the Follow application is present; creating "
					+ propertyFile.getAbsolutePath() + " (with default values) ...");
			properties = (EnumeratedProperties) getDefaultProperties().clone();
			log.info("... property file created successfully.");
		}
		else
		{
			properties = new EnumeratedProperties();
			FileInputStream fis = new FileInputStream(propertyFile);
			properties.load(fis);
			switch (getAttributesVersion())
			{
				case UNVERSIONED:
					// Migrate unversioned attributes to 1.1 attributes
					log.info("Migrating pre-v1.1 properties to v1.1.");
					setAttributesVersion(v1_1);
					setTabPlacement(getDefaultAttributes().getTabPlacement());
				case v1_1:
					// Migrate 1.1 attributes to 1.2 attributes
					log.info("Migrating v1.1 properties to v1.2.");
					setAttributesVersion(v1_2);
					setFont(getDefaultAttributes().getFont());
				case v1_2:
					// Migrate 1.2 attributes to 1.3 attributes
					log.info("Migrating v1.2 properties to v1.3.");
					setAttributesVersion(v1_3);
					setConfirmDelete(true);
					setConfirmDeleteAll(true);
					// Additionally, it is necessary to warn the user about the
					// changes to
					// Clear and ClearAll and the introduction of Delete and
					// DeleteAll
					JOptionPane.showMessageDialog(null, FollowApp
							.getResourceString("v1.3.warning.text"), FollowApp
							.getResourceString("v1.3.warning.title"), JOptionPane.WARNING_MESSAGE);
				case v1_3:
				case v1_3_2:
					// Migrate 1.3 attributes to 1.4 attributes
					log.info("Migrating v1.3 properties to v1.4.");
					setAttributesVersion(v1_4);
					setAutoScroll(true);
					// Inform the user of the new AutoScroll feature
					JOptionPane.showMessageDialog(null, FollowApp
							.getResourceString("v1.4.info.text"), FollowApp
							.getResourceString("v1.4.info.title"), JOptionPane.INFORMATION_MESSAGE);
				case v1_4:
					// Migrate 1.4 attributes to 1.5 attributes
					log.info("Migrating v1.4 properties to v.1.5.");
					setAttributesVersion(v1_5_0);
					setTabSize(4);
				case v1_5_0:
					// Migrate 1.5.0 attributes to 1.6.0 attributes
					log.info("Migrating v1.5 properties to 1.6.0.");
					setAttributesVersion(v1_6_0);
					setRecentFilesMax(5);
			}
			fis.close();
		}
	}

	private FollowAppAttributes(EnumeratedProperties props) throws IOException
	{
		properties = props;
	}

	public int getHeight()
	{
		return getInt(HEIGHT_KEY);
	}

	public void setHeight(int height)
	{
		setInt(HEIGHT_KEY, height);
	}

	public int getWidth()
	{
		return getInt(WIDTH_KEY);
	}

	public void setWidth(int width)
	{
		setInt(WIDTH_KEY, width);
	}

	public int getX()
	{
		return getInt(X_KEY);
	}

	public void setX(int x)
	{
		setInt(X_KEY, x);
	}

	public int getY()
	{
		return getInt(Y_KEY);
	}

	public void setY(int y)
	{
		setInt(Y_KEY, y);
	}

	/**
	 * Get an array files being followed
	 * 
	 * @return File[] File array of followed files
	 */
	public List<File> getFollowedFiles()
	{
		return getFiles(getFollowedFilesList());
	}

	/**
	 * Get a list of files being followed
	 * 
	 * @return List file names as Strings
	 */
	private List<String> getFollowedFilesList()
	{
		return getEnumeratedProperty(FOLLOWED_FILES_KEY);
	}

	protected List<File> getFiles(List<String> fileList)
	{
		ArrayList<File> files = new ArrayList<File>(fileList.size());
		for (String s : fileList)
		{
			files.add(new File(s));
		}
		return files;
	}

	/**
	 * Checks the existence of a file in the list of followed files
	 * 
	 * @return true iff any File in the List of followed Files (getFollowedFiles()) has the same
	 *         Canonical Path as the supplied File
	 */
	public boolean followedFileListContains(File file)
	{
		return fileListContains(getFollowedFilesList(), file);
	}

	/**
	 * Checks the existence of a file in the list of recent files
	 * 
	 * @return true iff any File in the List of recent Files (getFollowedFiles()) has the same
	 *         Canonical Path as the supplied File
	 */
	public boolean recentFileListContains(File file)
	{
		return fileListContains(getRecentFilesList(), file);
	}

	/**
	 * @return true iff any File in the List of Files (getFollowedFiles()) has the same Canonical
	 *         Path as the supplied File
	 */
	protected boolean fileListContains(List<String> fileList, File file)
	{
		boolean retval = false;
		if (fileList != null && file != null)
		{
			for (int i = 0; i < fileList.size(); i++)
			{
				String nextFile = (String) fileList.get(i);
				// be sure to check the same thing that is added in
				// addFollowedFile(File)
				if (nextFile.equals(file.getAbsolutePath()))
				{
					retval = true;
					break;
				}
			}
		}
		return retval;
	}

	/**
	 * Adds a file to the list of followed files
	 * 
	 * @param file
	 */
	public void addFollowedFile(File file)
	{
		List<String> fileNames = getEnumeratedProperty(FOLLOWED_FILES_KEY);
		if (!fileListContains(fileNames, file))
		{
			fileNames.add(file.getAbsolutePath());
			setEnumeratedProperty(FOLLOWED_FILES_KEY, fileNames);
		}
	}

	/**
	 * Removes a file from the list of followed files
	 * 
	 * @param file
	 */
	public void removeFollowedFile(File file)
	{
		List<String> fileNames = getEnumeratedProperty(FOLLOWED_FILES_KEY);
		fileNames.remove(file.getAbsolutePath());
		setEnumeratedProperty(FOLLOWED_FILES_KEY, fileNames);
	}

	public int getTabPlacement()
	{
		return getInt(TAB_PLACEMENT_KEY);
	}

	public void setTabPlacement(int tabPlacement)
	{
		setInt(TAB_PLACEMENT_KEY, tabPlacement);
	}

	public int getTabSize()
	{
		return getInt(TAB_SIZE_KEY);
	}

	public void setTabSize(int tabSize)
	{
		setInt(TAB_SIZE_KEY, tabSize);
	}

	public void setTabSize(String tabSize)
	{
		setTabSize(Integer.parseInt(tabSize));
	}

	public int getSelectedTabIndex()
	{
		try
		{
			return getInt(SELECTED_TAB_INDEX_KEY);
		}
		catch (NumberFormatException e)
		{
			setSelectedTabIndex(0);
			return 0;
		}
	}

	public void setSelectedTabIndex(int selectedTabIndex)
	{
		setInt(SELECTED_TAB_INDEX_KEY, selectedTabIndex);
	}

	public File getLastFileChooserDirectory()
	{
		return new File(properties.getProperty(LAST_FILE_CHOOSER_DIR_KEY, userHome));
	}

	public void setLastFileChooserDirectory(File file)
	{
		setString(LAST_FILE_CHOOSER_DIR_KEY, file.getAbsolutePath());
	}

	public int getBufferSize()
	{
		return getInt(BUFFER_SIZE_KEY);
	}

	public void setBufferSize(int bufferSize)
	{
		setInt(BUFFER_SIZE_KEY, bufferSize);
	}

	public void setBufferSize(String bufferSize)
	{
		setBufferSize(Integer.parseInt(bufferSize));
	}

	public int getLatency()
	{
		return getInt(LATENCY_KEY);
	}

	public void setLatency(int latency)
	{
		setInt(LATENCY_KEY, latency);
	}

	public void setLatency(String latency)
	{
		setLatency(Integer.parseInt(latency));
	}

	public int getAttributesVersion()
	{
		if (properties.get(ATTRIBUTES_VERSION_KEY) == null)
		{
			// Supporting v1.0 & v1.0.1, which had no notion of attributes
			// version
			return UNVERSIONED;
		}
		else
		{
			return getInt(ATTRIBUTES_VERSION_KEY);
		}
	}

	public void setAttributesVersion(int attributesVersion)
	{
		setInt(ATTRIBUTES_VERSION_KEY, attributesVersion);
	}

	public Font getFont()
	{
		Font font = new Font(getString(FONT_FAMILY_KEY), getInt(FONT_STYLE_KEY),
				getInt(FONT_SIZE_KEY));
		return font;
	}

	public void setFont(Font font)
	{
		setString(FONT_FAMILY_KEY, font.getFontName());
		setInt(FONT_STYLE_KEY, font.getStyle());
		setInt(FONT_SIZE_KEY, font.getSize());
	}

	public boolean confirmDelete()
	{
		return getBoolean(CONFIRM_DELETE_KEY);
	}

	public void setConfirmDelete(boolean value)
	{
		setBoolean(CONFIRM_DELETE_KEY, value);
	}

	public boolean confirmDeleteAll()
	{
		return getBoolean(CONFIRM_DELETE_ALL_KEY);
	}

	public void setConfirmDeleteAll(boolean value)
	{
		setBoolean(CONFIRM_DELETE_ALL_KEY, value);
	}

	public boolean autoScroll()
	{
		return getBoolean(AUTO_SCROLL_KEY);
	}

	public void setAutoScroll(boolean value)
	{
		setBoolean(AUTO_SCROLL_KEY, value);
	}

	public String getEditor()
	{
		String result = getString(EDITOR_KEY);
		if (result == null)
		{
			result = "";
		}

		return (result);
	}

	public void setEditor(String value)
	{
		setString(EDITOR_KEY, value);
	}

	/**
	 * Adds a file to the list of recent files
	 * 
	 * @param file
	 */
	public void addRecentFile(File file)
	{
		if (!recentFileListContains(file))
		{
			List<String> fileList = getRecentFilesList();
			// check size constraint and add accordingly
			if (fileList.size() == getRecentFilesMax())
			{
				for (int i = 0; i < fileList.size() - 1; i++)
				{
					fileList.set(i, fileList.get(i + 1));
				}
				fileList.set(fileList.size() - 1, file.getAbsolutePath());
			}
			else
			{
				fileList.add(file.getAbsolutePath());
			}
			setEnumeratedProperty(RECENT_FILES_KEY, fileList);
		}
	}

	/**
	 * Get an array of recently opened files
	 * 
	 * @return File[] File array of followed files
	 */
	public List<File> getRecentFiles()
	{
		return getFiles(getRecentFilesList());
	}

	public int getRecentFilesMax()
	{
		return getInt(RECENT_FILES_MAX_KEY);
	}

	public void setRecentFilesMax(String max)
	{
		setRecentFilesMax(Integer.parseInt(max));
	}

	public void setRecentFilesMax(int max)
	{
		List<String> files = getRecentFilesList();
		if (files.size() > max)
		{
			for (int i = files.size() - max; i > 0; i--)
			{
				files.remove(0);
			}
			setEnumeratedProperty(RECENT_FILES_KEY, files);
		}
		setInt(RECENT_FILES_MAX_KEY, max);
	}

	public File getPropertyFile()
	{
		return propertyFile;
	}

	public void store() throws IOException
	{
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(
				defaultPropertyFileName));
		properties.store(bos, null);
		// close this stream.  no need to flush it since Properties.store(..) does that
		bos.close();
	}

	// The listener list wrapper methods.
	public synchronized void addPropertyChangeListener(PropertyChangeListener listener)
	{
		pcs.addPropertyChangeListener(listener);
	}

	public synchronized void addPropertyChangeListener(String prop, PropertyChangeListener listener)
	{
		pcs.addPropertyChangeListener(prop, listener);
	}

	public synchronized void removePropertyChangeListener(PropertyChangeListener listener)
	{
		pcs.removePropertyChangeListener(listener);
	}

	public synchronized void removePropertyChangeListener(String prop, PropertyChangeListener listener)
	{
		pcs.removePropertyChangeListener(prop, listener);
	}

	public FollowAppAttributes getDefaultAttributes() throws IOException
	{
		if (defaultAttributes == null)
		{
			defaultAttributes = new FollowAppAttributes(getDefaultProperties());
			// Check for the unlikely possibility that the default font is
			// unavailable
			Font defaultFont = defaultAttributes.getFont();
			String[] availableFontFamilyNames = GraphicsEnvironment.getLocalGraphicsEnvironment()
					.getAvailableFontFamilyNames();
			boolean defaultFontIsAvailable = false;
			for (int i = 0; i < availableFontFamilyNames.length; i++)
			{
				if (defaultFont.getFamily().equals(availableFontFamilyNames[i]))
				{
					defaultFontIsAvailable = true;
					break;
				}
			}
			if (!defaultFontIsAvailable)
			{
				log.info("Font family " + defaultFont.getFamily() + " is unavailable; using "
						+ availableFontFamilyNames[0] + " instead.");
				defaultAttributes.setFont(new Font(availableFontFamilyNames[0], defaultFont
						.getStyle(), defaultFont.getSize()));
			}
		}
		return defaultAttributes;
	}

	/**
	 * Get a list of recently opened files
	 * 
	 * @return List recently opened files as Strings
	 */
	private List<String> getRecentFilesList()
	{
		return getEnumeratedProperty(RECENT_FILES_KEY);
	}

	private int getInt(String key)
	{
		int retval = 0;
		String s = getString(key);
		if (s != null)
			retval = Integer.parseInt(getString(key));
		return retval;
	}

	private void setInt(String key, int value)
	{
		int oldValue = getInt(key);
		properties.setProperty(key, String.valueOf(value));
		pcs.firePropertyChange(key, oldValue, value);
	}

	private boolean getBoolean(String key)
	{
		return "true".equals(getString(key));
	}

	private void setBoolean(String key, boolean value)
	{
		boolean oldValue = getBoolean(key);
		properties.setProperty(key, String.valueOf(value));
		pcs.firePropertyChange(key, oldValue, value);
	}

	private String getString(String key)
	{
		return properties.getProperty(key);
	}

	private void setString(String key, String value)
	{
		String oldValue = getString(key);
		properties.setProperty(key, value);
		pcs.firePropertyChange(key, oldValue, value);
	}

	private List<String> getEnumeratedProperty(String key)
	{
		return properties.getEnumeratedProperty(key);
	}

	private void setEnumeratedProperty(String key, List<String> values)
	{
		List<String> oldValue = getEnumeratedProperty(key);
		properties.setEnumeratedProperty(key, values);
		pcs.firePropertyChange(key, oldValue, values);
	}

	private EnumeratedProperties getDefaultProperties() throws IOException
	{
		if (defaultProperties == null)
		{
			InputStream in = this.getClass().getResourceAsStream(PROPERTY_PROTOTYPE_FILE_NAME);
			BufferedInputStream bis = new BufferedInputStream(in);
			FileOutputStream fos = new FileOutputStream(propertyFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			byte[] byteArray = new byte[BUFFER_SIZE];
			int len;
			while ((len = bis.read(byteArray, 0, BUFFER_SIZE)) > 0)
			{
				bos.write(byteArray, 0, len);
			}
			bos.flush();
			bos.close();
			bis.close();
			defaultProperties = new EnumeratedProperties();
			defaultProperties.load(new BufferedInputStream(new FileInputStream(propertyFile)));
		}
		return defaultProperties;
	}
}