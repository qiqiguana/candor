package net.sf.xbus.base.core.config;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.strings.XStringSupport;

/**
 * This class reads program parameters from different sources and makes them
 * accessible. The entries of the configuration are organized in a three-level
 * hierarchie:
 * <p>
 * <ul>
 * <li>The <b>chapter </b> is the top level. Often you will find names of
 * components or classes at this level.</li>
 * <li>Every chapter contains one or more <b>sections </b></li>
 * <li><b>Keys </b> are at the bottom of the hierarchie, beyond the sections.
 * </ul>
 * 
 * The <code>Configuration</code> implements the <b>Singleton </b>
 * Design-Pattern.
 * <p>
 * 
 * Up to now, only the Java- <code>Properties</code> can be used as a source
 * for the configuration. Other sources like XML-files or a database are
 * prepared.
 */
public class Configuration
{
	public static final String STANDARD_CONFIG = "standard";
	private static final String XBUS_HOME = "$XBUS_HOME$";

	public static final String VARIABLE_PREFIX = "$VARIABLE_";
	public static final String VARIABLE_END = "$";
	public static final String MAPPING_DEFAULT = "Default";

	// private static Configuration mSingleInstance = null;
	private static Hashtable mInstances = new Hashtable();

	private static final Object classLock = Configuration.class;

	private Hashtable mCache = null;
	private ConfigSource mSource = null;

	/**
	 * The constructor is private. Instances of <code>Configuration</code> can
	 * only be accessed via the method <code>getInstance()</code>.
	 * 
	 * @param source the source of the properties.
	 * @exception XException if any error occurs
	 */
	private Configuration(String source)
	{
		/*
		 * Check if XBUS_HOME is set. This is done here, because loading the
		 * configuration will be the first point, where XBUS_HOME is used.
		 */
		if (Constants.XBUS_HOME == null)
		{
			System.out.println("I_00_000_2 XBUS_HOME has not been set!");
			System.exit(1);
		}

		/*
		 * Initialize the source of the configuration entries.
		 */
		mSource = new PropertiesSource(source);
	} // Configuration(String source)

	/**
	 * Returns an instance of the standard <code>Configuration</code>.
	 * <p>
	 * The first call creates a new <code>Configuration</code> and reads all
	 * entries. Following calls will return the object, that has been created by
	 * the first call.
	 * 
	 * @return Configuration - instance of the standard
	 *         <code>Configuration</code>
	 * @exception XException if any error occurs
	 */
	public static Configuration getInstance() throws XException
	{
		return getInstance(STANDARD_CONFIG);
	} // getInstance()

	/**
	 * Returns a named instance of the <code>Configuration</code>.
	 * <p>
	 * The first call creates a new <code>Configuration</code> and reads all
	 * entries. Following calls will return the object, that has been created by
	 * the first call.
	 * 
	 * @param source the source of the properties.
	 * @return Configuration - instance of the standard
	 *         <code>Configuration</code>
	 * @exception XException if any error occurs
	 */
	public static Configuration getInstance(String source) throws XException
	{
		synchronized (classLock)
		{
			Configuration instance = (Configuration) mInstances.get(source);
			if (instance == null)
			{
				instance = new Configuration(source);
				instance.readCache();
				mInstances.put(source, instance);
			} // if (instance == null)
			return instance;
		}
	} // getInstance(String source)

	/**
	 * Creates an updated instance of the standard <code>Configuration</code>.
	 * 
	 * @exception XException if any error occurs
	 */
	public static void refresh() throws XException
	{
		refresh(STANDARD_CONFIG);
	} // refresh()

	/**
	 * Creates an updated instance of the named <code>Configuration</code>.
	 * 
	 * @param source the source of the properties
	 * @exception XException if any error occurs
	 */
	public static void refresh(String source) throws XException
	{
		mInstances.remove(source);
        getInstance(source);
	} // refresh(String source)

	/**
	 * Returns the value of an entry. All three parameters have to contain
	 * values.
	 * <p>
	 * If the desired entry can not be found, a <code>XException</code> is
	 * thrown.
	 * 
	 * @param chapter The first level from the three-level hierarchie hashtable
	 * @param section The second level from the three-level hierarchie hashtable
	 *            that belongs to this chapter
	 * @param key The bottom level from the three-level hierarchie hashtable
	 *            that belongs to these chapter and section
	 * @return Value as String for the given chapter, section and key
	 * @exception XException if the entry in configuration is not exist or any
	 *                error occurs
	 */
	public String getValue(String chapter, String section, String key)
			throws XException
	{
		String returnString = getValueInternal(chapter, section, key);
		if (returnString == null)
		{
			List params = new Vector();
			params.add(chapter);
			params.add(section);
			params.add(key);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_COREBASE,
					Constants.PACKAGE_COREBASE_COREBASE, "1", params);
		} // if (returnString == null)

		return returnString;
	} // getValue(String chapter, String section, String key)

	/**
	 * Returns the value of an entry as an integer. All three parameters have to
	 * contain values.
	 * <p>
	 * If the desired entry can not be found or the value is not an integer, a
	 * <code>XException</code> is thrown.
	 * 
	 * @param chapter The first level from the three-level hierarchie hashtable
	 * @param section The second level from the three-level hierarchie hashtable
	 *            that belongs to this chapter
	 * @param key The bottom level from the three-level hierarchie hashtable
	 *            that belongs to these chapter and section
	 * @return Value as <i>int </i> for the given chapter, section and key *
	 * @exception XException if the entry in configuratiuon is not exist or it
	 *                is not an integer
	 */
	public int getValueAsInt(String chapter, String section, String key)
			throws XException
	{
		String returnString = getValueInternal(chapter, section, key);
		if (returnString == null)
		{
			List params = new Vector();
			params.add(chapter);
			params.add(section);
			params.add(key);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_COREBASE,
					Constants.PACKAGE_COREBASE_COREBASE, "1", params);
		} // if (returnString == null)

		try
		// for casting to XException
		{
			int retInt = Integer.parseInt(returnString);
			return retInt;
		}
		catch (NumberFormatException e)
		{
			List params = new Vector();
			params.add(chapter);
			params.add(section);
			params.add(key);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_COREBASE,
					Constants.PACKAGE_COREBASE_COREBASE, "3", params);
		} // catch (NumberFormatException e)
	} // getValueAsInt(String chapter, String section, String key)

	/**
	 * Returns the optional value of an entry as an integer. All three
	 * parameters have to contain values.
	 * <p>
	 * If the desired entry is not an integer, a <code>XException</code> is
	 * thrown.
	 * 
	 * @param chapter The first level from the three-level hierarchie hashtable
	 * @param section The second level from the three-level hierarchie hashtable
	 *            that belongs to this chapter
	 * @param key The bottom level from the three-level hierarchie hashtable
	 *            that belongs to these chapter and section
	 * @return Value as <i>int </i> for the given chapter, section and key <br>
	 *         If the entry in the configuration is not found, <code>0</code>
	 *         is returned.
	 * @exception XException if the entry is not an integer
	 */
	public int getValueAsIntOptional(String chapter, String section, String key)
			throws XException
	{
		String returnString = getValueInternal(chapter, section, key);
		if (returnString == null)
		{
			return 0;
		} // if (returnString == null)

		try
		{
			int retInt = Integer.parseInt(returnString);
			return retInt;
		}
		catch (NumberFormatException e)
		{
			List params = new Vector();
			params.add(chapter);
			params.add(section);
			params.add(key);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_COREBASE,
					Constants.PACKAGE_COREBASE_COREBASE, "3", params);
		} // catch (NumberFormatException e)
	} // getValueAsIntOptional(String chapter, String section, String key)

	/**
	 * Returns the optional value of an entry as an long value. All three
	 * parameters have to contain values.
	 * <p>
	 * If the desired entry is not an long value, a <code>XException</code> is
	 * thrown.
	 * 
	 * @param chapter The first level from the three-level hierarchie hashtable
	 * @param section The second level from the three-level hierarchie hashtable
	 *            that belongs to this chapter
	 * @param key The bottom level from the three-level hierarchie hashtable
	 *            that belongs to these chapter and section
	 * @return Value as <i>int </i> for the given chapter, section and key <br>
	 *         If the entry in the configuration is not found, <code>0</code>
	 *         is returned.
	 * @exception XException if the entry is not an integer
	 */
	public long getValueAsLongOptional(String chapter, String section,
			String key) throws XException
	{
		String returnString = getValueInternal(chapter, section, key);
		if (returnString == null)
		{
			return 0;
		} // if (returnString == null)

		try
		{
			long retLong = Long.parseLong(returnString);
			return retLong;
		}
		catch (NumberFormatException e)
		{
			List params = new Vector();
			params.add(chapter);
			params.add(section);
			params.add(key);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_COREBASE,
					Constants.PACKAGE_COREBASE_COREBASE, "5", params);
		} // catch (NumberFormatException e)
	} // getValueAsLongOptional(String chapter, String section, String key)

	/**
	 * Returns the value of an entry as a boolean. All three parameters have to
	 * contain values.
	 * <p>
	 * If the desired entry can not be found or the value is not a boolean, a
	 * <code>XException</code> is thrown.
	 * 
	 * @param chapter The first level from the three-level hierarchie hashtable
	 * @param section The second level from the three-level hierarchie hashtable
	 *            that belongs to this chapter
	 * @param key The bottom level from the three-level hierarchie hashtable
	 *            that belongs to these chapter and section
	 * @return Value as <i>boolean </i> for the given chapter, section and key
	 * @exception XException if the entry in the configuration is not exist or
	 *                it is not a boolean value
	 */
	public boolean getValueAsBoolean(String chapter, String section, String key)
			throws XException
	{
		String returnString = getValueInternal(chapter, section, key);
		if (returnString == null)
		{
			List params = new Vector();
			params.add(chapter);
			params.add(section);
			params.add(key);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_COREBASE,
					Constants.PACKAGE_COREBASE_COREBASE, "1", params);
		}

		if (Constants.CONFIGURATION_TRUE.toUpperCase().equals(
				returnString.toUpperCase()))
		{
			return true;
		} // then
		// (Constants.CONFIGURATION_TRUE.toUpperCase().equals(returnString.toUpperCase()))
		else if (Constants.CONFIGURATION_FALSE.toUpperCase().equals(
				returnString.toUpperCase()))
		{
			return false;
		} // else then
		// (Constants.CONFIGURATION_FALSE.toUpperCase().equals(returnString.toUpperCase()))
		else
		{
			List params = new Vector();
			params.add(chapter);
			params.add(section);
			params.add(key);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_COREBASE,
					Constants.PACKAGE_COREBASE_COREBASE, "7", params);
		} // else
		// (Constants.CONFIGURATION_FALSE.toUpperCase().equals(returnString.toUpperCase()))
	} // getValueAsBoolean(String chapter,String section,String key)

	/**
	 * Returns the optional value of an entry as a boolean. All three parameters
	 * have to contain values.
	 * <p>
	 * If the desired entry can not be found, false is returned. If the value is
	 * not a boolean, a <code>XException</code> is thrown.
	 * 
	 * @param chapter The first level from the three-level hierarchie hashtable
	 * @param section The second level from the three-level hierarchie hashtable
	 *            that belongs to this chapter
	 * @param key The bottom level from the three-level hierarchie hashtable
	 *            that belongs to these chapter and section
	 * @return Value as boolean for the given chapter, section and key. <br>
	 *         If the entry in the configuration is not found, false is
	 *         returned.
	 * @exception XException if the entry is not a boolean value or any error
	 *                occurs
	 */
	public boolean getValueAsBooleanOptional(String chapter, String section,
			String key) throws XException
	{
		String returnString = getValueInternal(chapter, section, key);
		if (returnString == null)
		{
			return false;
		}

		if (Constants.CONFIGURATION_TRUE.toUpperCase().equals(
				returnString.toUpperCase()))
		{
			return true;
		} // then
		// (Constants.CONFIGURATION_TRUE.toUpperCase().equals(returnString.toUpperCase()))
		else if (Constants.CONFIGURATION_FALSE.toUpperCase().equals(
				returnString.toUpperCase()))
		{
			return false;
		} // else then
		// (Constants.CONFIGURATION_FALSE.toUpperCase().equals(returnString.toUpperCase()))
		else
		{
			List params = new Vector();
			params.add(chapter);
			params.add(section);
			params.add(key);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_COREBASE,
					Constants.PACKAGE_COREBASE_COREBASE, "7", params);
		} // else
		// (Constants.CONFIGURATION_FALSE.toUpperCase().equals(returnString.toUpperCase()))
	} // getValueAsBooleanOptional(String chapter,String section,String key)

	/**
	 * Returns the value of an entry. All three parameters have to contain
	 * values.
	 * <p>
	 * If the desired entry is not in the configuration, <code>null</code>
	 * will be returned.
	 * 
	 * @param chapter The first level from the three-level hierarchie hashtable
	 * @param section The second level from the three-level hierarchie hashtable
	 *            that belongs to this chapter
	 * @param key The bottom level from the three-level hierarchie hashtable
	 *            that belongs to these chapter and section
	 * @return Value as string for the given chapter,section and key or null if
	 *         the desired value is not found in the configuration
	 */
	public String getValueOptional(String chapter, String section, String key)
	{
		return getValueInternal(chapter, section, key);
	} // getValueOptional(String chapter, String section, String key)

	/**
	 * Returns a list of all chapters.
	 * 
	 * @return List with chapters
	 * @exception XException if any error occurs
	 */
	public List getChapters()
	{
		TreeSet chapters = new TreeSet(mCache.keySet());
		return new Vector(chapters);
	} // getChapters()

	/**
	 * Returns a list of all sections for the given chapter
	 * 
	 * @param chapter The first level from the three-level hierarchie hashtable
	 * @return List of all sections for the given chapter or null wenn no
	 *         sections are found
	 * @exception XException if any error occurs
	 */
	public List getSections(String chapter)
	{
		Hashtable sections = (Hashtable) mCache.get(chapter);
		if (sections == null)
		{
			return null;
		} // if (sections == null)

		TreeSet sectionSet = new TreeSet(sections.keySet());
		return new Vector(sectionSet);
	} // getSections(String chapter)

	/**
	 * Returns a map of all keys and their values for the given chapter and
	 * section.
	 * 
	 * @param chapter The first level from the three-level hierarchie hashtable
	 * @param section The second level from the three-level hierarchie hashtable
	 *            that belongs to this chapter
	 * @return Map of all keys and their values for the given chapter and
	 *         section or <i>null </i> wenn no keys are found
	 * @exception XException if any error occurs
	 */
	public Map getKeysAndValues(String chapter, String section)
	{
		Hashtable sections = (Hashtable) mCache.get(chapter);
		if (sections == null)
		{
			return null;
		} // if (sections == null)

		Hashtable keys = (Hashtable) sections.get(section);

		if (keys != null)
		{
			return new TreeMap(keys);
		}
		else
		{
			return null;
		}
	} // getKeysAndValues(String chapter, String section)

	/**
	 * Returns a value for the given chapter, section and key.
	 * 
	 * @param chapter The first level from the three-level hierarchie hashtable
	 * @param section The second level from the three-level hierarchie hashtable
	 *            that belongs to this chapter
	 * @param key The bottom level from the three-level hierarchie hashtable
	 *            that belongs to these chapter and section
	 * @return Value as String for the given chapter, section and key
	 * @exception XException if any error occurs
	 */
	private String getValueInternal(String chapter, String section, String key)
	{
		Hashtable sectTable = (Hashtable) mCache.get(chapter);
		if (sectTable == null)
		{
			return null;
		} // if (sectTable == null)

		Hashtable keyTable = (Hashtable) sectTable.get(section);
		if (keyTable == null)
		{
			return null;
		} // if (keyTable == null)

		return (String) keyTable.get(key);
	} // getValueInternal(String chapter, String section, String key)

	/**
	 * This method is used by {@link #getInstance()}and by
	 * {@link #getInstance(String)}for reading all entries from a source and
	 * filling a three-level hierarchie hashtable.
	 * 
	 * @see net.sf.xbus.base.core.config.PropertiesSource#readCache()
	 * @exception XException if any error occurs
	 */
	private void readCache() throws XException
	{
		mCache = mSource.readCache();
		replaceVariables(mCache);
	} // readCache()

	/**
	 * Replaces XBUS_HOME and variables with the key
	 * <code>Base_Variable_xxx</code> in all entries of the current
	 * configuration.
	 */
	private void replaceVariables(Hashtable cache) throws XException
	{
		Map variables = getVariables(cache);

		for (Enumeration chapters = cache.elements(); chapters
				.hasMoreElements();)
		{
			Hashtable chapter = (Hashtable) chapters.nextElement();
			for (Enumeration sections = chapter.elements(); sections
					.hasMoreElements();)
			{
				Hashtable section = (Hashtable) sections.nextElement();
				for (Enumeration keys = section.keys(); keys.hasMoreElements();)
				{
					String key = (String) keys.nextElement();
					String value = (String) section.get(key);

					/*
					 * Replace further variables
					 */
					int variablePosNew = value.indexOf(VARIABLE_PREFIX);
					if (variablePosNew >= 0)
					{
						int variablePosOld = -99999;
						String variable = null;
						Set variablesKeySet = null;
						while ((variablePosNew >= 0)
								&& (variablePosOld != variablePosNew))
						{
							variablePosOld = variablePosNew;
							if (variables == null)
							{
								throw new XException(Constants.LOCATION_INTERN,
										Constants.LAYER_BASE,
										Constants.PACKAGE_BASE_XBUSSYSTEM, "4");
							}
							variablesKeySet = variables.keySet();
							if (variablesKeySet == null)
							{
								throw new XException(Constants.LOCATION_INTERN,
										Constants.LAYER_BASE,
										Constants.PACKAGE_BASE_XBUSSYSTEM, "4");
							}
							String variablesKey = null;
							for (Iterator it = variablesKeySet.iterator(); it
									.hasNext();)
							{
								variablesKey = (String) it.next();
								variable = (String) variables.get(variablesKey);
								if (value.indexOf(variablesKey) >= 0)
								{
									value = XStringSupport.replaceAll(value,
											variablesKey, variable);
								}
							}
							variablePosNew = value.indexOf(VARIABLE_PREFIX);
						}
						if (variablePosOld == variablePosNew)
						{
							/*
							 * Found same variable again => variable cannot be
							 * replaced
							 */
							List params = new Vector();
							params.add(new Integer(variablePosOld));
							params.add(value);
							throw new XException(Constants.LOCATION_INTERN,
									Constants.LAYER_BASE,
									Constants.PACKAGE_BASE_XBUSSYSTEM, "3",
									params);
						}
					}

					/*
					 * Replace XBUS_HOME
					 */
					if (value.indexOf(XBUS_HOME) >= 0)
					{
						value = XStringSupport.replaceAll(value, XBUS_HOME,
								Constants.XBUS_HOME);
					}

					section.put(key, value);
				}
			}
		}

	}

	private Map getVariables(Hashtable cache)
	{
		Hashtable sections = (Hashtable) cache.get(Constants.CHAPTER_BASE);

		Map variablesConf = null;
		if (sections == null)
		{
			return null;
		} // if (sections == null)

		Hashtable keys = (Hashtable) sections.get("Variable");

		if (keys != null)
		{
			variablesConf = new TreeMap(keys);
		}
		else
		{
			return null;
		}

		Map variablesNew = new Hashtable();

		if (variablesConf != null)
		{
			String key = null;
			String variable = null;
			for (Iterator it = variablesConf.keySet().iterator(); it.hasNext();)
			{
				key = (String) it.next();
				variable = (String) variablesConf.get(key);
				key = new StringBuffer(VARIABLE_PREFIX).append(key).append(
						VARIABLE_END).toString();
				variablesNew.put(key, variable);
			}
		}

		return variablesNew;
	}
	/**
	 * Returns an entry of the class table.
	 * 
	 * @see #getValue(String, String, String)
	 * @param type The second level from the three-level hierarchie hashtable
	 *            that belongs to the chapter: <b><i>Class </i> </b>
	 * @param name The bottom level from the three-level hierarchie hashtable
	 *            that belongs to the chapter: <b><i>Class </i> </b> and this
	 *            section
	 * @return Value as String for the given type and name
	 * @exception XException if the entry is not found or any error occurs
	 */
	public static String getClass(String type, String name) throws XException
	{
		return Configuration.getInstance("xbus").getValue("Class", type, name);
	} // getClass(String type, String name)

	/**
	 * Returns an entry of the mapping table.
	 * 
	 * @see #getValue(String, String, String)
	 * @param section The second level from the three-level hierarchie hashtable
	 *            that belongs to the chapter: <b><i>Mapping </i> </b>
	 * @param key The bottom level from the three-level hierarchie hashtable
	 *            that belongs to the chapter: <b><i>Mapping </i> </b> and this
	 *            section
	 * @return Value for the given section and key
	 * @exception XException if the entry is not found or any error occurs
	 */
	public static String getMapping(String section, String key)
			throws XException
	{
		return Configuration.getInstance("mapping").getValue("Mapping",
				section, key);
	} // getMapping(String section, String key)

	/**
	 * Returns an entry of the mapping table or <code>null</code> if it
	 * doesn't exist.
	 * 
	 * @param section The second level from the three-level hierarchie hashtable
	 *            that belongs to the chapter: <b><i>Mapping </i> </b>
	 * @param key The bottom level from the three-level hierarchie hashtable
	 *            that belongs to the chapter: <b><i>Mapping </i> </b> and this
	 *            section
	 * @return value for the given section and key
	 * @exception XException if the entry is not found or any error occurs
	 */
	public static String getMappingOptional(String section, String key)
			throws XException
	{
		return Configuration.getInstance("mapping").getValueOptional("Mapping",
				section, key);
	}

	/**
	 * @param section
	 */
	public static String getMappingDefault(String section) throws XException
	{
		return getMapping(section, Configuration.MAPPING_DEFAULT);
	} // getMapping(String section, String key)

} // Configuration
