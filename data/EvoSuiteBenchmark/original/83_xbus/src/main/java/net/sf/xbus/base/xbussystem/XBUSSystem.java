package net.sf.xbus.base.xbussystem;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.reflection.ReflectionSupport;
import net.sf.xbus.base.core.strings.XStringSupport;

/**
 * <code>XBUSSystem</code> is the representation of any neighbor-systems
 * respectively their interfaces. It consists of two informations:
 * <p>
 * <ul>
 * <li>The name of the system is used to identify the system.
 * <li>Additionally an adress might be available.
 * </ul>
 */
public class XBUSSystem
{
	public static final String FILENAME_WILDCARD = "$WILDCARD$";
	public static final String FILENAME_WILDCARD_XML = "WILDCARD";
	private static final String ADDITIONAL_ADDRESS = "AddressImplementation";
	private static final String TIMESTAMP = "$TIMESTAMP$";

	/**
	 * Holds all address informations of the neighbor-system. Each address
	 * stored as value against its typ.
	 * <p>
	 * <i>Possible addresses: </i> <table border>
	 * <tr>
	 * <th>Key</th>
	 * <th>Value(Example)</th>
	 * </tr>
	 * <tr>
	 * <td>AddressImplementation</td>
	 * <td>Test/Wholesale/std$DEALERNUMBER$.cnf</td>
	 * </tr>
	 * <tr>
	 * <td>WILDCARD</td>
	 * <td>parts</td>
	 * </tr>
	 * <tr>
	 * <td><i>example: </i>user</td>
	 * <td>KS</td>
	 * </tr>
	 * </table>
	 */
	private Hashtable mAddresses = new Hashtable();

	/**
	 * Is a braodcast requested for all recorded addtional addresses when
	 * sending data to this system?
	 */
	private boolean mBroadcast;

	/**
	 * Name of the neighbor-system
	 */
	private String mName = null;

	/**
	 * Holds all AdditionalAddress objects of this neighbor-system. Each object
	 * stored as value against the name of this neigbor-system.
	 * <p>
	 * <i>Example: </i> <table border>
	 * <tr>
	 * <th>Key</th>
	 * <th>Value</th>
	 * </tr>
	 * <tr>
	 * <td>PartsOrderAutoline</td>
	 * <td>new Dealer()</td>
	 * </tr>
	 * </table
	 */
	private static Hashtable mAdditionalAddressImplementations = new Hashtable();

	/**
	 * <code>mMinAge</code> can be used to filter data sources due to their
	 * age to ignore all those, which are too fresh. This can be used for access
	 * synchronisation: Putting in the maximum time for constructing a file
	 * ensures that the file transfer does not start while during writing the
	 * file. The value is in milliseconds.
	 */
	private int mMinAge;

	/**
	 * <code>mMaxAge</code> can be used to filter data sources due to their
	 * age to ignore all those, which are too old. This can be used for ignoring
	 * outdated data sources from former failed data transfers. The value is in
	 * milliseconds.
	 */
	private int mMaxAge;

	/**
	 * Constructs the <code>XBUSSystem</code> without an address.
	 * 
	 * @param name name of the neighbor-system
	 */
	public XBUSSystem(String name) throws XException
	{
		mName = name;

		// Look for the (optional) restrictions to data source age.
		Configuration config = Configuration.getInstance();
		mMinAge = config.getValueAsIntOptional(Constants.CHAPTER_SYSTEM, name,
				"MinAge") * 1000;
		mMaxAge = config.getValueAsIntOptional(Constants.CHAPTER_SYSTEM, name,
				"MaxAge") * 1000;
	} // XBUSSystem(String name)

	/**
	 * Constructs the <code>XBUSSystem</code> from the name fo the
	 * neighbor-system and all address informations for this system
	 * 
	 * @param name name of the neighbor-system
	 * @param addresses name of the address
	 */
	public XBUSSystem(String name, Hashtable addresses) throws XException
	{
		this(name);

		mAddresses = addresses;
	} // XBUSSystem(String name, Hashtable addresses)

	/**
	 * Constructs the <code>XBUSSystem</code> from the name fo the
	 * neighbor-system and all address informations for this system and the flag
	 * if a broadcast is requested to this system. The addresses do not
	 * necessarily contain the reference to additional address information
	 * necessary for a broadcast.
	 * 
	 * @param name name of the neighbor-system
	 * @param addresses name of the address
	 * @param broadcast is broadcast to be done when sending?
	 */
	public XBUSSystem(String name, Hashtable addresses, boolean broadcast)
			throws XException
	{
		this(name, addresses);

		mBroadcast = broadcast;
	} // XBUSSystem(String name, Hashtable addresses, boolean broadcast)

	/**
	 * Returns the name of the neighbor-system.
	 * 
	 * @return name of the neighbor-system
	 */
	public String getName()
	{
		return mName;
	} // getName()

	/**
	 * Returns all address informations of the neighbor-system. Each address
	 * stored as value against its typ.
	 * <p>
	 * <i>Example: </i>
	 * <p>
	 * <table border>
	 * <tr>
	 * <th>Key</th>
	 * <th>Value</th>
	 * </tr>
	 * <tr>
	 * <td>AddressImplementation</td>
	 * <td>Test/Wholesale/std$DEALERNUMBER$.cnf</td>
	 * </tr>
	 * <tr>
	 * <td>WILDCARD</td>
	 * <td>parts</td>
	 * </tr>
	 * <tr>
	 * <td>user</td>
	 * <td>KS</td>
	 * </tr>
	 * </table>
	 * 
	 * @return all addresses of the neighbor-system
	 */
	public Hashtable getAddresses()
	{
		return mAddresses;
	} // getAddresses()

	/**
	 * Sets all address informations for the neighbor-system.
	 * 
	 * @param addresses Hashtable of the addresses
	 */
	/*
	 * private void setAddresses(Hashtable addresses) { mAddresses = addresses; } //
	 * setAddresses(Hashtable addresses)
	 */
	/**
	 * Sets one address information of the neighbor-system.
	 * 
	 * @param name typ of additional information
	 * @param address additional inrformation eventually containing markers with
	 *            the format <code>$key$</code>.
	 */
	public void setAddress(String name, String address)
	{
		mAddresses.put(name, address);
	} // setAddress(String name, String address)

	/**
	 * <dl>
	 * <dt>Returns the concatenation of name and all addresses of the
	 * neighbor-system by <b>"_" </b> separated.
	 * <dd><i>Example: </i>&nbsp;&nbsp;&nbsp;PartsOrderAutoline_12345
	 * <p>
	 * <dt>If no address information is found, the name of the neighbor-system
	 * will be returned. <br>
	 * <dd><i>Example: </i>&nbsp;&nbsp;&nbsp;PartsOrderAutoline
	 * <p>
	 * </dl>
	 * 
	 * @return concatenation of name and all addresses of the neighbor-system or
	 *         only name if addresses are not exist
	 */
	public String getCompleteName()
	{
		String retString;

		if (mAddresses.isEmpty())
		{
			retString = mName;
		}
		else
		{
			StringBuffer retStringBuffer = new StringBuffer(mName);
			for (Enumeration e = mAddresses.keys(); e.hasMoreElements();)
			{
				retStringBuffer.append("_");
				retStringBuffer.append(mAddresses.get(e.nextElement()));
			}
			retString = retStringBuffer.toString();
		}
		return retString;
	} // getCompleteName()

	/**
	 * Is a braodcast requested for all recorded addtional addresses when
	 * sending data to this system?
	 */
	public boolean getBroadcast()
	{
		return mBroadcast;
	} // getBroadcast()

	/**
	 * Get the maximal age of the data source. The value is in milliseconds.
	 */
	public int getMaxAge()
	{
		return mMaxAge;
	} // getMaxAge()

	/**
	 * Get the minimal age of the data source. The value is in milliseconds.
	 */
	public int getMinAge()
	{
		return mMinAge;
	} // getMinAge()

	/**
	 * The given text may contain place markers which will be replaced with
	 * their actual values. Three types of markers are possible:
	 * <ul>
	 * <li>Additional informations which have been set with the
	 * {@link #setAddress(String, String)}method. The key set there is
	 * interpreted as a marker with the format <code>$key$</code>.
	 * <li>WILDCARD separator
	 * <li>Markers for informations on the address. These markers will be
	 * replaced with their actual values for the address of the neighbor-system.
	 * <br>
	 * The implementation of this functionality is realized in a dedicated
	 * class, implementing the interface {@link AdditionalAddress}.<br/>A
	 * broadcast is possible in sending using the addtional address information.
	 * That is the reason for the array return type. Withoout braoadcast only
	 * the first array element is filled.
	 * </ul>
	 * <p>
	 * <b><i>Note: </i> </b>If no marker is found, the text will be returned
	 * without modifications.
	 * 
	 * @param text text containing markers
	 * @return text array with replacements for markers or without modifications
	 *         if no marker is found.
	 * @exception XException if the entry is not found or any error occurs
	 */
	public String[] replaceAllMarkers(String text) throws XException
	{
		text = XStringSupport.replaceAll(text, TIMESTAMP, Constants
				.getDateAsString());

		/*
		 * 1. Search if a marker in the table of additional values is found. If
		 * yes, replace it.
		 */
		String key = null;
		String marker = null;
		for (Enumeration e = mAddresses.keys(); e.hasMoreElements();)
		{
			key = (String) e.nextElement();
			if (!key.equals(ADDITIONAL_ADDRESS)
					&& !key.equals(FILENAME_WILDCARD))
			{
				marker = "$" + key + "$";
				if (text.indexOf(marker) >= 0)
				{
					text = text
							.replaceAll(marker, (String) mAddresses.get(key));
				}
			}
		}

		/*
		 * 2. Search if a WILDCARD separator in the table is found. If yes,
		 * replace it
		 */
		if (mAddresses.containsKey(FILENAME_WILDCARD))
		{
			if (text.indexOf(FILENAME_WILDCARD) >= 0)
			{
				text = XStringSupport.replaceAll(text, FILENAME_WILDCARD,
						(String) mAddresses.get(FILENAME_WILDCARD));
			}
		}

		/*
		 * 3. If the XBUSSystem contains an address information, replace markers
		 * for this address.
		 */
		if (mAddresses.containsKey(ADDITIONAL_ADDRESS))
		{

			AdditionalAddress addAddress = getAdditionalAddressImplementation(getName());

			if (addAddress.hasMarker(text))
			{ // The string contains a marker for additional address
				// information.
				if (mBroadcast)
				{ // For broadcast construct one string for each addiotinal
					// address.
					List addAddresses = addAddress.getAddresses();
					int quantAddresses = addAddresses.size();
					if (quantAddresses < 1)
					{ // No additional addresses specified in configuration.
						List params = new Vector();
						params.add(mName);
						params.add(text);
						throw new XException(Constants.LOCATION_INTERN,
								Constants.LAYER_BASE,
								Constants.PACKAGE_BASE_XBUSSYSTEM, "5", params);
					} // if (quantAddresses<1)
					String[] result = new String[quantAddresses];
					for (int i = 0; i < quantAddresses; i++)
						result[i] = addAddress.replaceMarker(text,
								(String) addAddresses.get(i));
					return result;
				} // then (mBroadcast)
				else
				{ // Simple addtional address without broadcast.
					String[] result =
					{addAddress.replaceMarker(text, (String) mAddresses
							.get(ADDITIONAL_ADDRESS))};
					return result;
				} // else (mBroadcast)
			} // then addAddress.hasMarker(text)
			else
			{ // No addtional address marker.
				String[] result =
				{text};
				return result;
			} // else (addAddress.hasMarker(text))
		} // then (mAddresses.containsKey(ADDITIONAL_ADDRESS))
		else
		{ // No addtional address at all.
			String[] result =
			{text};
			return result;
		} // else (mAddresses.containsKey(ADDITIONAL_ADDRESS))

	} // replaceAllMarkers(String text)

	/**
	 * Returns the concatenation of name and address of the neighbor-system.
	 * 
	 * @see #getCompleteName()
	 * @return concatenation of name and address of the neighbor-system or only
	 *         name if address is not exist
	 */
	public String toString()
	{
		return getCompleteName();
	} // toString()

	/**
	 * Returns a list of <code>XBUSSystems</code>, generated through the
	 * replacing of all possible place markers.
	 * <p>
	 * <b><i>Note: </i> </b>If there are no place markers in the given text,
	 * only one system with an empty address is in that list.
	 * 
	 * @param name the name of the system
	 * @param text eventually containing place markers
	 * @return list of <code>XBUSSystems</code>
	 * @exception XException if any error occurs
	 */
	static public List getSystems(String name, String text) throws XException
	{
		/*
		 * 1. check if the given text contains place markers for informations on
		 * the address, then store list of all configured addresses
		 */

		Vector additionalAddresses = null;
		if (hasAdditionalAddressMarker(name, text))
		{
			additionalAddresses = new Vector();

			List addresses = getAdditionalAddresses(name);
			for (Iterator it = addresses.iterator(); it.hasNext();)
			{
				additionalAddresses.add(it.next());
			}
		}

		/*
		 * 2. check if the given text contains WILDCARD separator
		 * 
		 */
		List addressVector = null;
		Hashtable addressTable = null;

		if (text.indexOf(XBUSSystem.FILENAME_WILDCARD) >= 0)
		{
			// there is no additional informations yet
			if ((additionalAddresses == null)
					|| (additionalAddresses.isEmpty()))
			{
				FileAcceptor acceptor = new FileAcceptor(text);
				// get an array of strings naming the files and directories in
				// the directory
				// denoted by this abstract pathname that were accepted by the
				// FileAcceptor filter.
				String[] dummy = new File(text).getParentFile().list(acceptor);
				String message = "Found files";
				if (dummy != null)
					for (int i = 0; i < dummy.length; i++)
						message = message + " " + dummy[i];
				// save all replacements (wildcards) in vector
				addressVector = acceptor.getReplacements();
			}
			else
			{
				addressTable = new Hashtable();
				// get additional address implementation for this system
				AdditionalAddress additionalAddressImpl = getAdditionalAddressImplementation(name);
				// replace place markers in each address
				for (Iterator it = additionalAddresses.iterator(); it.hasNext();)
				{
					String additionalAddress = (String) it.next();
					String filename = additionalAddressImpl.replaceMarker(text,
							additionalAddress);

					// initializte filename filter for this filename
					FileAcceptor acceptor = new FileAcceptor(filename);
					// get an array of strings naming the files and directories
					// in the directory denoted by this abstract pathname that
					// were accepted by the FileAcceptor filter.
					String[] dummy = new File(filename).getParentFile().list(
							acceptor);
					String message = "Found files";
					for (int i = 0; dummy != null && i < dummy.length; i++)
						message = message + " " + dummy[i];
					addressTable.put(additionalAddress, acceptor
							.getReplacements());
				}
			}
		}

		/*
		 * 3. construct a system vector a: put each address with its type in
		 * hashtable b: encapsulate this table with name of the system in
		 * XBUSSystem object c: and store this object in vector
		 */
		Vector retSystems = new Vector();
		// there are only wildcards
		if (addressVector != null)
		{
			for (Iterator it = addressVector.iterator(); it.hasNext();)
			{
				Hashtable addresses = new Hashtable();
				addresses.put(FILENAME_WILDCARD, it.next());
				retSystems.add(new XBUSSystem(name, addresses));
			}
		}
		// there are also address implementations
		else if (addressTable != null)
		{
			for (Enumeration e = addressTable.keys(); e.hasMoreElements();)
			{
				String additionalAddress = (String) e.nextElement();
				List wildcardReplacements = (List) addressTable
						.get(additionalAddress);
				for (Iterator it = wildcardReplacements.iterator(); it
						.hasNext();)
				{
					Hashtable addresses = new Hashtable();
					addresses.put(ADDITIONAL_ADDRESS, additionalAddress);
					addresses.put(FILENAME_WILDCARD, it.next());
					retSystems.add(new XBUSSystem(name, addresses));
				}

			}
		}
		// if only additional address implementation exist
		else if (additionalAddresses != null)
		{
			for (Iterator it = additionalAddresses.iterator(); it.hasNext();)
			{
				Hashtable addresses = new Hashtable();
				addresses.put(ADDITIONAL_ADDRESS, it.next());
				retSystems.add(new XBUSSystem(name, addresses));
			}
		}
		// there is no addresses exist
		else
		{
			retSystems.add(new XBUSSystem(name));
		}
		return retSystems;
	}

	/**
	 * Tests if the given text contains place markers for informations on the
	 * address.
	 * <p>
	 * The implementation of this functionality is realized in a dedicated
	 * class, implementing the {@link net.sf.xbus.base.AdditionalAddress}
	 * interface.
	 * 
	 * @see #getAdditionalAddressImplementation(String)
	 * @param systemNAme name of the system
	 * @param text text eventually containing markers
	 * @return true if text contains markers; false otherwise
	 * @exception XException if any error occurs
	 */
	static private boolean hasAdditionalAddressMarker(String systemName,
			String text) throws XException
	{
		AdditionalAddress additionalAddressImplementation = getAdditionalAddressImplementation(systemName);
		if (additionalAddressImplementation != null)
		{
			return additionalAddressImplementation.hasMarker(text);
		}
		else
		{
			return false;
		}
	} // hasAdditionalAddressMarker(String systemName,String text)

	/**
	 * Returns a list of all configured addresses for the given system name.
	 * <p>
	 * The implementation of this functionality is realized in a dedicated
	 * class, implementing the {@link net.sf.xbus.base.AdditionalAddress}
	 * interface.
	 * 
	 * @see #getAdditionalAddressImplementation(String)
	 * @param systemName the name of the system
	 * @return list of addresses
	 * @exception XException if any error occurs
	 */
	static private List getAdditionalAddresses(String systemName)
			throws XException
	{
		return getAdditionalAddressImplementation(systemName).getAddresses();
	} // getAdditionalAddresses(String systemName)

	/**
	 * Gets the address implementation ({@link AdditionalAddress}) for the
	 * given system name.
	 * <p>
	 * <b><i>Note: </i> </b> If there is no address implementation for the
	 * given system created yet, then creates this method one first. <br>
	 * Address implementation name is read from the configuration: <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <b>System_ </b> <i>SystemName
	 * </i> <b>_AddressImplementation </b> <br>
	 * If there is no implementation for the address is provided with this
	 * system, there must be a global entry used.: <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 * <b>Base_System_AddressImplementation </b>
	 * 
	 * @param systemName the name of the system
	 * @return address implementation ({@link AdditionalAddress}) for the
	 *         given system name
	 * @exception XException if any error occurs
	 */
	public static AdditionalAddress getAdditionalAddressImplementation(
			String systemName) throws XException
	{
		if (!mAdditionalAddressImplementations.contains(systemName))
		{
			/*
			 * If there is no address implementation for the current system
			 * created yet, we will create it and put it in the Hashtable.
			 */

			Configuration config = Configuration.getInstance();
			/*
			 * If an implementation for the address is provided with the system,
			 * than this implementation is getting used.
			 */

			String addressImplementationNameShort = config.getValueOptional(
					Constants.CHAPTER_SYSTEM, systemName, ADDITIONAL_ADDRESS);

			if (addressImplementationNameShort == null)
			{
				/*
				 * If there is no system specific implementation, there maybe is
				 * a global entry.
				 */
				addressImplementationNameShort = config.getValueOptional(
						"Base", Constants.CHAPTER_SYSTEM, ADDITIONAL_ADDRESS);

			}

			if (addressImplementationNameShort != null)
			{
				String addressImplementationName = Configuration.getClass(
						"AdressImplementation", addressImplementationNameShort);

				mAdditionalAddressImplementations.put(systemName,
						ReflectionSupport
								.createObject(addressImplementationName));
			}
		}

		return (AdditionalAddress) mAdditionalAddressImplementations
				.get(systemName);
	} // getAdditionalAddressImplementation(String systemName)

	/**
	 * Check if the given address string does contain a reference to addtional
	 * address information. Update the list of addresses and broadcast flag
	 * accordingly.
	 * 
	 * @param text the address text (e.g. file path)
	 */
	public void getBroadcastData(String text) throws XException
	{
		if (hasAdditionalAddressMarker(mName, text))
			// Reference to additional addresses given.
			mAddresses.put(ADDITIONAL_ADDRESS, "broadcast");
		else
			// No reference to additional addresses given.
			mBroadcast = false;
	} // getBroadcastData(String text)

} // XBUSSystem

