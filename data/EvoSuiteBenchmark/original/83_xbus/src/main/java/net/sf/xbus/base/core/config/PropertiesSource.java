package net.sf.xbus.base.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;

/**
 * This is the implementation of a {@link ConfigSource} wich uses the Java-<code>Properties</code>.
 * <p>
 * 
 * To map the hierarchy of the
 * {@link net.sf.xbus.base.core.config.Configuration}, the keys of the
 * properties must consist of three parts divided by "_"
 * (e.g.System_MQ_Timeout).
 */
public class PropertiesSource implements ConfigSource
{
	private static final String DELIMITER = "_";
	private static final String POSTFIX = ".conf";
	private String mPrefix = null;

	/**
	 * The constructor builds the name of the properties-file:
	 * <code>%XBUS_HOME%/etc/<i>source</i>.conf</code>
	 * <p>
	 * 
	 * Program which uses this class must be started with:
	 * <code>java -Dxbus.home="%XBUS_HOME%"</code>
	 * 
	 * @param source the property source
	 * @exception XException if any error occurs
	 */
	public PropertiesSource(String source)
	{
		if (source == null)
		{
			System.out.println("I_00_001_2 Source must not be <null>");
			System.exit(1);
		}

		mPrefix = source;
	}

	/**
	 * Reads the properties and fills a three-level hierarchie hashtable
	 * suitable for the {@link net.sf.xbus.base.core.config.Configuration}.
	 * 
	 * @return Hashtable - three-level hierarchie hashtable
	 *         <ol>
	 *         <li>level consists of the chapters as the keys and the hashtable
	 *         as value</li>
	 *         <li>level consists of the names of sections as the keys and a
	 *         hashtable as value</li>
	 *         <li>level consists of the name of keys and their values as
	 *         Strings</li>
	 *         </ol>
	 * @exception XException if any error occurs
	 */
	public Hashtable readCache()
	{
		String key, value;
		Vector cacheKeys;
		Hashtable cache = new Hashtable();

		Properties props = new Properties();

		addProperties(props, mPrefix, Constants.XBUS_ETC);
		addProperties(props, mPrefix, Constants.XBUS_PLUGIN_ETC);

		for (Enumeration e = props.propertyNames(); e.hasMoreElements();)
		{
			key = (String) e.nextElement();
			value = props.getProperty(key);
			cacheKeys = splitKey(key);
			cache = putCache(cache, cacheKeys, value);
		}

		return cache;
	}

	/**
	 * Load the properties from a file.
	 * 
	 * @param filename the name of the properties file
	 * @exception XException if any error occurs
	 */
	private void addProperties(Properties props, String prefix, String directory)
	{
		File etcDir = new File(directory);
		String[] configFiles = etcDir.list(new PropertiesFilter());

		/*
		 * Check wether at least one config file in XBUS/etc could be found.
		 */
		if ((Constants.XBUS_ETC.equals(directory))
				&& (Configuration.STANDARD_CONFIG.equals(mPrefix))
				&& ((configFiles == null) || (configFiles.length == 0)))
		{
			System.out
					.println("I_00_001_2 No configuration file " + prefix
							+ "*.conf"
							+ " exists, maybe XBUS_HOME is not set properly");
			System.exit(1);
		}

		for (int i = 0; (configFiles != null) && (i < configFiles.length); i++)
		{
			FileInputStream instream;
			Properties newProps = new Properties();

			try
			{
				instream = new FileInputStream(directory + configFiles[i]);
				newProps.load(instream);
				instream.close();
			}
			catch (java.io.FileNotFoundException e)
			{
				System.out.println("I_00_001_2 File " + directory
						+ configFiles[i] + " doesn't exist");
				System.exit(1);
			}
			catch (java.io.IOException e)
			{
				System.out
						.println("I_00_001_2 IOException while reading file  "
								+ directory + configFiles[i]);
				e.printStackTrace();
				System.exit(1);
			}

			/*
			 * Move all elements of currently loaded properties to the complete
			 * set
			 */
			String key = null;
			for (Enumeration keys = newProps.keys(); keys.hasMoreElements();)
			{
				key = (String) keys.nextElement();

				/*
				 * Detection of collisions
				 */
				if (props.containsKey(key))
				{
					System.out.println("I_00_001_5 Key " + key
							+ " has already been inserted");
					System.exit(1);
				}

				props.put(key, newProps.get(key));
			}
		}
	}

	/**
	 * Converts the key of the property suitable for the Configuration, i.e.
	 * removes separators from it and saves three elements in the vector.
	 * 
	 * @param key the key of the properties (must consist of three parts
	 *            separated be "_")
	 * @return Vector - vector with 3 elements: chapter,section,key
	 * @exception XException if any error occurs
	 */
	private Vector splitKey(String key)
	{
		StringTokenizer st = new StringTokenizer(key,
				PropertiesSource.DELIMITER);
		String token;
		Vector tmp = new Vector();

		while (st.hasMoreTokens())
		{
			token = st.nextToken();
			tmp.add(token);
		}

		if (tmp.size() != 3)
		{
			System.out.println("I_00_001_4 Wrong format of key " + key);
			System.exit(1);
		}

		return tmp;
	}

	/**
	 * Adds new entry to the hashtable.
	 * 
	 * @param cache the old three-level hierarchie hashtable
	 * @param cacheKeys the vector with new elements (chapter,section,key)
	 * @param value the new value
	 * @return Hashtable - three-level hierarchie hashtable
	 *         <p>
	 *         <ol>
	 *         <li>level consists of the chapters as the keys and the hashtable
	 *         as value</li>
	 *         <li>level consists of the names of sections as the keys and a
	 *         hashtable as value</li>
	 *         <li>level consists of the name of keys and their values as
	 *         Strings</li>
	 *         </ol>
	 */
	private Hashtable putCache(Hashtable cache, Vector cacheKeys, String value)
	{
		Hashtable sectionTable = null;
		boolean newChapter = false;
		Hashtable keyTable = null;
		boolean newSection = false;

		String chapter = ((String) cacheKeys.elementAt(0));
		String section = ((String) cacheKeys.elementAt(1));
		String cacheKey = ((String) cacheKeys.elementAt(2));

		sectionTable = (Hashtable) cache.get(chapter);
		if (sectionTable == null)
		{
			sectionTable = new Hashtable();
			newChapter = true;
		}
		keyTable = (Hashtable) sectionTable.get(section);
		if (keyTable == null)
		{
			keyTable = new Hashtable();
			newSection = true;
		}

		keyTable.put(cacheKey, value.trim());

		if (newSection)
		{
			sectionTable.put(section, keyTable);
		}
		if (newChapter)
		{
			cache.put(chapter, sectionTable);
		}

		return cache;
	}

	/**
	 * The internal class <code>PropertiesFilter</code> checks wether files
	 * are wanted Properties or not.
	 */
	private class PropertiesFilter implements FilenameFilter
	{
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
		 */
		public boolean accept(File dir, String filename)
		{
			if ((filename.startsWith(mPrefix)) && (filename.endsWith(POSTFIX)))
			{
				return true;
			}
			else
			{
				return false;
			}
		}

	}
}
