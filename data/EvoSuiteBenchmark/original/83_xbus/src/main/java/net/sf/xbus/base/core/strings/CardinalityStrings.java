package net.sf.xbus.base.core.strings;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;

/**
 * <code>CardinalityStrings</code> offers some methods for treating
 * cardinality strings like "1..*". Allowed cardinality string consist
 * <ul>
 * <li>only of a single strictly positive integer - fixed cardinality</li>
 * <li>of an interval with ".." - variable cardinality</li>
 * </ul>
 * In case of an interval the lower bound must be a positive integer (including
 * zero) and the upper bound must be a strictly positive integer or "*". "*"
 * means "arbitrary".
 * 
 * @author Stephan Düwel
 */
public class CardinalityStrings
{

	/**
	 * <code>isCardinalityValid</code> checks if a string is a well-formed
	 * cardinality.
	 * 
	 * @param card the string to check
	 * @return <code>true</code> for well-formed cardinality string, otherwise
	 *         <code>false</code>
	 */
	public static boolean isCardinalityValid(String card)
	{
		int dots = card.indexOf("..");
		// Search for interval dots
		if (dots == -1)
			// no interval
			dots = card.length();
		// to check everything till the end
		boolean result = true;
		if (dots + 2 == card.length())
			// Dots must be followed by a number or "*".
			result = false;
		else
		{ // No dots or something behind the dots.
			try
			// for conversions to numbers
			{
				// A single value or a lower bound must be positive integers.
				int low = Integer.parseInt(card.substring(0, dots));
				if (low < 0)
					result = false;
				else if (dots == card.length() && low == 0)
					// A single - not interval - value is zero but should be
					// strictly positive.
					result = false;
				else
				{ // Look if there is something behind dots.
					if (dots < card.length() - 3 || dots == card.length() - 3
							&& card.charAt(dots + 2) != '*')
					{ // Just "*" would be fine, but otherwise it must be an
						// integer
						// not smaller than the lower bound.
						int high = Integer.parseInt(card.substring(dots + 2));
						if (low > high)
							result = false;
					} // if (dots<card.length()-3 || card.charAt(dots+2)!='*')
					else if (dots == card.length() - 2)
						// The dots are the last characters in the string.
						result = false;
				} // else (low<0)
			} // try
			catch (NumberFormatException e)
			{ // Single value, lower or upper bound no numbers (upper bound
				// not "*")
				result = false;
			} // catch
		} // else (dots+2==card.length())
		return result;
	} // isCardinalityValid(String card)

	/**
	 * <code>isCardinalityInterval</code> checks if a given string is a
	 * cardinatily interval like "1..*".
	 * 
	 * @param card the string to check
	 * @param isKnownAsValidCardinality Is the string already known as a
	 *            well-formed cardinality specification? Otherwise this fact
	 *            will be checked.
	 * @return <code>true</code> for a well-formed cardinality interval,
	 *         otherwise <code>false</code>
	 */
	public static boolean isCardinalityInterval(String card,
			boolean isKnownAsValidCardinality)
	{
		if (!isKnownAsValidCardinality)
			isKnownAsValidCardinality = isCardinalityValid(card);
		// Ensure that the string is a well-formed cardinality string.
		if (isKnownAsValidCardinality)
			isKnownAsValidCardinality = (card.indexOf("..") > -1);
		// It is an interval iff it contains dots.
		return isKnownAsValidCardinality;
	} // isCardinalityInterval(String card, boolean isKnownAsValidCardinality)

	/**
	 * <code>getCardinalityLow</code> extracts the lower bound from a
	 * cardinality string like "1..*".
	 * 
	 * @param card the cardinality string
	 * @param isKnownAsCardinalityInterval Is the cardinality string already
	 *            known as a well-formed interval specification? Otherwise this
	 *            fact will be checked.
	 * @return the extracted lower bound which must be an positive integer
	 * @throws XException in case of a mal-formed cardinality string or a
	 *             negative lower bound
	 */
	public static int getCardinalityLow(String card,
			boolean isKnownAsCardinalityInterval) throws XException
	{
		if (!isKnownAsCardinalityInterval)
			isKnownAsCardinalityInterval = isCardinalityInterval(card, false);
		// Ensure that the string is a cardinality interval.
		int result = -1;
		if (isKnownAsCardinalityInterval)
			// NumberFormatException already checked
			result = Integer.parseInt(card.substring(0, card.indexOf("..")));
		else
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_COREBASE,
					Constants.PACKAGE_COREBASE_STRINGS, "1");
		}
		return result;
	} // getCardinalityLow(String card, boolean isKnownAsCardinalityInterval)

	/**
	 * <code>getCardinalityHigh</code> extracts the upper bound from a
	 * cardinality string like "1..*".
	 * 
	 * @param card the cardinality string
	 * @param isKnownAsCardinalityInterval Is the cardinality string already
	 *            known as a well-formed interval specification? Otherwise this
	 *            fact will be checked.
	 * @return the extracted upper bound which must be an positive integer,
	 *         <code>Integer.MAX_VALUE</code> in case of a "*"
	 * @throws XException in case of a mal-formed cardinality string or a
	 *             negative upper bound
	 */
	public static int getCardinalityHigh(String card,
			boolean isKnownAsCardinalityInterval) throws XException
	{
		if (!isKnownAsCardinalityInterval)
			isKnownAsCardinalityInterval = isCardinalityInterval(card, false);
		// Ensure that the string is a cardinality interval.
		int result = -1;
		if (isKnownAsCardinalityInterval)
		{
			int dots = card.indexOf("..");
			if (dots == card.length() - 3 && card.charAt(dots + 2) == '*')
				// no upper limit
				result = Integer.MAX_VALUE;
			else
				// NumberFormatException already checked
				result = Integer.parseInt(card.substring(dots + 2));
		} // then (isKnownAsCardinalityInterval)
		else
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_COREBASE,
					Constants.PACKAGE_COREBASE_STRINGS, "1");
		return result;
	} // getCardinalityHigh(String card, boolean isKnownAsCardinalityInterval)

} // CardinalityStrings
