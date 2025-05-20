package net.sf.xbus.technical.java;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.reflection.ReflectionSupport;
import net.sf.xbus.base.core.timeoutcall.Callable;
import net.sf.xbus.base.xbussystem.XBUSSystem;

/**
 * Used by {@link JavaSender#execute(String, Object)} together with the
 * {@link net.sf.xbus.base.core.timeoutcall.TimedCallable} to be able to stop
 * the execution of the program after a timeout.
 */
public class JavaCaller implements Callable
{
	private String mFunction = null;
	private Object mCallData = null;
	private XBUSSystem mDestination = null;
	private boolean mIsText = false;

	/**
	 * Stores the given data.
	 * 
	 * @param destination name of the interface definition
	 * @param function used to determine the method
	 * @param callData data to be send
	 * @param isText indicates whether the method accepts a String (<code>true</code>)
	 *            or an Object (<code>false</code>)
	 */
	public JavaCaller(XBUSSystem destination, String function, Object callData,
			boolean isText)
	{
		mDestination = destination;
		mFunction = function;
		mCallData = callData;
		mIsText = isText;
	}

	/**
	 * Calling the Java method. The name of the class and the name of the method
	 * are read out of the configuration.
	 * 
	 * @return the response of the called method
	 */
	public Object call() throws XException
	{
		Configuration config = Configuration.getInstance();
		String classname = config.getValue(Constants.CHAPTER_SYSTEM,
				mDestination.getName(), "Class");

		Object callObject = ReflectionSupport.createObject(classname);

		String methodname = getMethodName(mFunction);

		Class[] parameterTypes = null;
		if (mIsText)
		{
			parameterTypes = new Class[]
			{String.class};
		}
		else
		{
			parameterTypes = new Class[]
			{Object.class};
		}
		Object[] arguments = new Object[]
		{mCallData};

		return ReflectionSupport.callMethod(methodname, callObject,
				parameterTypes, arguments);
	}

	/**
	 * Stopping the execution of the program by doing nothing.
	 */
	public void stop()
	{}

	/**
	 * Reads the name of the method out of the configuration.
	 * 
	 * @param function which method is called may depend on the given function
	 * @return name of the method
	 */
	private String getMethodName(String function) throws XException
	{
		Configuration config = Configuration.getInstance();
		String retString = config.getValueOptional(Constants.CHAPTER_SYSTEM,
				mDestination.getName(), "Method");
		if (retString == null)
		{
			retString = config.getValue(Constants.CHAPTER_SYSTEM,
				mDestination.getName(), "Method." + function);
		}
		return retString;
	}
}
