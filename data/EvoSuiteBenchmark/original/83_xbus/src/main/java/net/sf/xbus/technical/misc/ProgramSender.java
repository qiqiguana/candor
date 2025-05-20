package net.sf.xbus.technical.misc;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.timeoutcall.TimedCallable;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.Sender;
import net.sf.xbus.technical.TextSender;

/**
 * <code>ProgramSender</code> calls an external program. Program name and
 * parameters are read out of the configuration.
 */
public class ProgramSender implements Sender, TextSender
{
	protected XBUSSystem mDestination = null;

	/**
	 * Stores the destination.
	 * 
	 * @param destination name of the interface definition
	 * @throws XException never
	 */
	public ProgramSender(XBUSSystem destination)
	{
		mDestination = destination;
	}

	/**
	 * Calls an external program. Program name and parameters are read out of
	 * the configuration.
	 * 
	 * @param function ignored
	 * @param callData ignored
	 */
	public String execute(String function, String callData) throws XException
	{
		String retString = null;

		Configuration config = Configuration.getInstance();
		int timeout = config.getValueAsIntOptional(Constants.CHAPTER_SYSTEM,
				mDestination.getName(), "Timeout") * 1000;
		if (timeout == 0)
		{
			timeout = Integer.MAX_VALUE;
		}

		ProgramCaller caller = new ProgramCaller(mDestination, callData);
		TimedCallable tc = new TimedCallable(caller, timeout);
		try
		{
			retString = (String) tc.call();
		}
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_JAVA, "0", e);
		}
		return retString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.xbus.technical.Sender#getType()
	 */
	public String getType()
	{
		return Constants.TYPE_TEXT;
	}
}
