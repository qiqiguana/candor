package net.sf.xbus.sample;

import net.sf.xbus.base.core.XException;

/**
 * This example of a class called by the <code>JavaTransformer</code> reverses
 * a string.
 */
public class JavaTransformerSample
{
	/**
	 * Reverses the given string
	 * 
	 * @param inObject must be of type <code>String</code>
	 * @return the reversed string
	 * @throws XException if something goes wrong
	 */
	public Object reverse(Object inObject)
	{
		return new JavaWorker().reverse(inObject);
	}

}
