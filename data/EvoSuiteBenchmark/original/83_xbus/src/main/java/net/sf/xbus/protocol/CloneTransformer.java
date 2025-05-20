package net.sf.xbus.protocol;

import net.sf.xbus.base.xbussystem.XBUSSystem;

/**
 * The <code>CloneTransformer</code> simply returns the given
 * <code>Object</code>. It is used when no transformation is necessary.
 */
public class CloneTransformer implements Transformer
{
	/**
	 * No transformation is done, the given <code>inObject</code> is returned.
	 * 
	 * @param inObject either the request or response of the source message
	 * @param source not used
	 * @param destination not used
	 * @param destinationMessage not used
	 * @return reference to the <code>inObject</code>
	 */
	public Object transform(Object inObject, XBUSSystem source,
			XBUSSystem destination, Message destinationMessage)
	{
		return inObject;
	}

}
