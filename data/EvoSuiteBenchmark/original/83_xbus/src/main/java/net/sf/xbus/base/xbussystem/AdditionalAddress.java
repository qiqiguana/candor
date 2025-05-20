package net.sf.xbus.base.xbussystem;

import java.util.List;

import net.sf.xbus.base.core.XException;

/**
 * TODO Kommentierung
 */
public interface AdditionalAddress
{
	/**
	 * Returns a list of all configured addresses.
	 * <p>
	 * 
	 * @return list of addresses
	 * @exception XException if any error occurs
	 */
	public List getAddresses() throws XException;

	/**
	 * Returns if the given text contains placeholders for informations on the
	 * address.
	 * 
	 * @param text text eventually containing markers
	 * @return true if text contains markers
	 * @exception XException if any error occurs
	 */
	public boolean hasMarker(String text) throws XException;

	/**
	 * The given text may contain placeholders for informations on the address.
	 * These placeholders will be replaced with their actual values for the
	 * address of the neighbor system.
	 * <p>
	 * 
	 * If the address has not been set, the text will be returned without
	 * modifications.
	 * 
	 * @param text text containing markers
	 * @param address adress to search for the values
	 * @return text with replacements for markers
	 * @exception XException if the entry is not found or any error occurs
	 */
	public String replaceMarker(String text, String address) throws XException;

	public String getValue(String inValue, String outValue, String key)
			throws XException;

}
