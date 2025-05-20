package net.sf.xbus.sample;

import java.util.List;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.strings.XStringSupport;
import net.sf.xbus.base.xbussystem.AdditionalAddress;

/**
 * Specific data about dealers is stored in the standard configuration. This
 * convience class enables an easy access.
 */
public class Dealer implements AdditionalAddress
{
	private static final String DEALERNUMBER = "$DEALERNUMBER$";
	private static final String DEALERNAME = "$DEALERNAME$";

	private static final String CHAPTER = "Dealer";

	/**
	 * Returns a list of all dealernumbers.
	 * 
	 * @return list of dealernumbers
	 * @exception XException if the entry is not found or any error occurs
	 */
	public List getAddresses() throws XException
	{
		List dealers = Configuration.getInstance().getSections(CHAPTER);
		if (dealers != null)
		{
			return dealers;
		}
		else
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_SAMPLE, Constants.PACKAGE_SAMPLE_SAMPLE,
					"1");
		}
	}

	/**
	 * Returns if the given text contains placeholders for informations on the
	 * address.
	 * 
	 * @param text text eventually containing markers
	 * @return true if text contains markers
	 * @exception XException if any error occurs
	 */
	public boolean hasMarker(String text)
	{
		return ((text != null) && ((text.indexOf(DEALERNUMBER) >= 0) || (text
				.indexOf(DEALERNAME) >= 0)));
	}

	/**
	 * Replaces all occurences of special markers. The following markers are
	 * defined for dealers:
	 * <p>
	 * <ul>
	 * <li>DEALERNUMBER
	 * <li>DEALERNAME
	 * </ul>
	 * 
	 * @param text text containing markers
	 * @param address dealernumber
	 * @return text with replacements for markers
	 * @exception XException if the entry is not found or any error occurs
	 */
	public String replaceMarker(String text, String address) throws XException
	{
		if (text.indexOf(DEALERNUMBER) >= 0)
		{
			text = XStringSupport.replaceAll(text, DEALERNUMBER, address);
		}
		if (text.indexOf(DEALERNAME) >= 0)
		{
			text = XStringSupport.replaceAll(text, DEALERNAME,
					getDealerName(address));
		}

		return text;
	}

	/**
	 * Returns the name of the dealer.
	 * 
	 * @param dealernumber
	 * @return name of the dealer
	 * @exception XException if the entry is not found or any error occurs
	 */
	public String getDealerName(String dealernumber) throws XException
	{
		return Configuration.getInstance().getValue(CHAPTER, dealernumber,
				"Name");
	}

	/**
	 * @see net.sf.xbus.base.xbussystem.AdditionalAddress#getValue(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public String getValue(String inValue, String outValue, String key)
	{
		return null;
	}

}
