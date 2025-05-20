package net.sf.xbus.base.core.arithmetic;

/**
 * <code>XIntSupport</code> is a collection of helpful methods concerning
 * integer handling.
 * 
 * @author Stephan Düwel
 */
public class XIntSupport
{

	/**
	 * <code>calculateLength</code> computes the length of an integer in
	 * respect to a given radix. The implementation is simple: dividing in loop.
	 * 
	 * @param value the integer for which to compute the length
	 * @param radix the radix for length computation
	 * @return minimum 1
	 */
	public static int calculateLength(long value, int radix)
	{
		int length = 1;
		if (value < 0)
			value *= -1;
		while (value / radix > 0)
		{
			value /= radix;
			length++;
		} // while (value/radix>0)
		return length;
	} // calculateLength(int value, int radix)

} // XIntSupport
