package net.sf.xbus.technical.misc;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.ObjectSender;
import net.sf.xbus.technical.Sender;

/**
 * <code>PingSender</code> simply returns the data that was sent.
 * <p>
 */
public class PingSender implements Sender, ObjectSender
{
	XBUSSystem mDestination = null;

	/**
	 * 
	 */
	public PingSender(XBUSSystem destination)
	{
		mDestination = destination;
	}

	/**
	 * The given object <code>callData</code> will be returned.
	 */
	public Object execute(String function, Object callData)
	{
		return callData;
	}

	public String getType()
	{
		return Constants.TYPE_OBJECT;
	}
}
