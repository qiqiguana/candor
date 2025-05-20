package net.sf.xbus.technical.java;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.timeoutcall.TimedCallable;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.ObjectSender;
import net.sf.xbus.technical.Sender;

/**
 * <code>JavaSender</code> sends data by calling a method of an object. Both
 * method and object are read out of the configuration.
 * <p>
 * 
 * The method must have one of these two signatures:
 * <p>
 * <code>    public String methodname(String callData)</code>
 * <p>
 * or
 * <p>
 * <code>    public Object methodname(Object callData)</code>
 * <p>
 * 
 */
public class JavaSender implements Sender, ObjectSender
{
	private XBUSSystem mDestination = null;
	private boolean mIsText = false;

	/**
	 * 
	 */
	public JavaSender(XBUSSystem destination)
	{
		mDestination = destination;
	}

	/**
	 * The given object will be sent to the neighbor-system. The answer of the
	 * neighbor-system is the return-value of this method.
	 * <p>
	 * 
	 * The function is used for calling different methods of the
	 * neighbor-system.
	 */
	public Object execute(String function, Object callData) throws XException
	{
		Object retObject = null;

		Configuration config = Configuration.getInstance();
		int timeout = config.getValueAsIntOptional(Constants.CHAPTER_SYSTEM,
				mDestination.getName(), "Timeout") * 1000;
		if (timeout == 0)
		{
			timeout = Integer.MAX_VALUE;
		}

		JavaCaller caller = new JavaCaller(mDestination, function, callData,
				mIsText);
		TimedCallable tc = new TimedCallable(caller, timeout);
		try
		{
			retObject = tc.call();
		}
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_JAVA, "0", e);
		}

		return retObject;
	}

	public String getType()
	{
		return Constants.TYPE_OBJECT;
	}
}
