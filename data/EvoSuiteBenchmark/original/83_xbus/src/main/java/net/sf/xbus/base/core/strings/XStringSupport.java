package net.sf.xbus.base.core.strings;

/**
 * <code>XStringSupport</code>is a collection of helpful methods concerning
 * string handling.
 * 
 * @author Stephan Düwel
 */
public class XStringSupport
{

	/**
	 * <code>getNumberString</code> constructs a string out of an integer
	 * value. The number representation is decimal. The string is filled with
	 * leading zeros to reach a specified length. In case of negative values,
	 * the zeros are inserted between the minus sign and the number.
	 * 
	 * @param number the integer value to be represented as a string
	 * @param length the desired length of the string representation
	 * @return the string representation of <code>number</code>
	 */
	public static String getNumberString(int number, int length)
	{
		// ordinary conversion
		StringBuffer numString = new StringBuffer();
		numString.append(number);
		// length check
		if (numString.length() > length)
			throw new IllegalArgumentException("Number too long");
		// filling in zeros
		StringBuffer buffer = null;
		if (length > numString.length())
		{
			buffer = new StringBuffer(length - numString.length());
			// space for zeros
			if (number < 0)
			{ // negative number
				buffer.append('-');
				numString.deleteCharAt(0);
				// sign already handled
			} // if (number<0)
			// only zeros left to fill in
			for (int i = 0; i < length - numString.length(); i++)
				buffer.append('0');
			// number behind all zeros
			buffer.append(numString);
		} // then (length>numString.length())
		else
			buffer = numString;
		return buffer.toString();
	} // getNumberString(int number, int length)

	/**
	 * <code>successorUsualChars</code> computes the successor string in
	 * lexicographic order. The method set only characters with ASCII codes
	 * between 32 and 126 to ensure that they can be properly displayed. The
	 * successor of other characters is ' ' (ASCII 32).
	 * 
	 * @param s the predecessor string
	 * @param lengthFix if set to <code>true</code>, the string length is
	 *            never augmented, in case of an overflow the blank string will
	 *            be returned
	 * @return the successor string (obeying the described constraints)
	 */
	public static String successorUsualChars(String s, boolean lengthFix)
	{
		char[] content = s.toCharArray();
		boolean overflow = true;
		// overflow in the actually examined string position
		for (int i = content.length - 1; overflow && i > -1; i--)
		{ // loop from the string end until the successor is found or
			// the string begin is reached
			if (content[i] < 32)
			{ // control character - jum to ' '
				content[i] = ' ';
				overflow = false;
				// successor found
			} // if (content[i]<32)
			else if (content[i] > 125)
				content[i] = ' ';
			else
			{ // ASCII code between 32 and 125 - augment it by 1
				content[i]++;
				overflow = false;
				// successor found
			} // else (content[i]>125)
		} // for (int i=content.length-1; overflow&&i>-1; i--)
		s = new String(content);
		if (!lengthFix && overflow)
			s = ' ' + s;
		return s;
	} // successorUsualChars(String s, boolean lengthFix)

	/**
	 * Replaces all occurences of <code>marker</code> in <code>text</code>
	 * with <code>replacement</code>.
	 * <p>
	 * <dl>
	 * <dt><i>Example:</i><br>
	 * <dd><i>before:</i>&nbsp;&nbsp;&nbsp;Input_<b>DEALERNUMBER</b>_<b>WILDCARD</b>.xml<br>
	 * <dd><i>after:</i>&nbsp;&nbsp;&nbsp;&nbsp;Input_<b>12345</b>_<b>parts</b>.xml
	 * </dl>
	 * 
	 * @param text text which all occurences of marker must be replaced
	 * @param marker text marker to be replaced
	 * @param replacement replaced text
	 */
	public static String replaceAll(String text, String marker,
			String replacement)
	{
		int pos;
		StringBuffer work = new StringBuffer(text);
		while ((pos = text.indexOf(marker)) >= 0)
		{
			work.replace(pos, pos + marker.length(), replacement);
			text = new String(work);
		}
		return new String(work);
	}

	public static String replaceFirst(String text, String marker,
			String replacement, int fromPosition)
	{
		int pos;
		StringBuffer work = new StringBuffer(text);
		if ((pos = text.indexOf(marker, fromPosition)) >= 0)
		{
			work.replace(pos, pos + marker.length(), replacement);
			text = new String(work);
		}
		return new String(work);
	}
} // XStringSupport
