package net.sf.xbus.base.core.trace;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;

/**
 * <code>Trace</code> writes program-internal informations to a sink. The most
 * important use of <code>Trace</code> is writing informations, when an error
 * occurs, e.g. the queue-server is not available.
 * <p>
 */
public class Trace
{
	public static final int DEBUG = 4;
	public static final int INFO = 3;
	public static final int WARN = 2;
	public static final int ERROR = 1;
	public static final int ALWAYS = 0;

	private static final int NOT_INITIALIZED = 9999;
	private static int mTracelevel = NOT_INITIALIZED;
	private static TraceTarget mTarget = null;

	/**
	 * Traces a message object with the ALWAYS priority.
	 * 
	 * @param message the message to be traced
	 */
	public static void always(Object message)
	{
		Trace.trace(Trace.ALWAYS, message, null);
	}

	/**
	 * Traces a message object with the ERROR priority.
	 * 
	 * @param message the message to be traced
	 */
	public static void error(Object message)
	{
		if (Trace.mTracelevel < Trace.ERROR)
		{
			return;
		}
		Trace.trace(Trace.ERROR, message, null);
	}

	/**
	 * Traces a message object with the ERROR priority including the message and
	 * the stack trace of the Throwable t passed as parameter.
	 * <p>
	 * When the <code>Throwable</code> is an <code>XException</code> nothing
	 * happens, because the <code>XException</code> has been traced before.
	 * 
	 * @param t the Throwable which shall be traced.
	 */
	public static void error(Throwable t)
	{
		if (Trace.mTracelevel < Trace.ERROR)
		{
			return;
		}
		if (t != null)
		{
			/*
			 * When the exception is an XException, it has been traced before
			 */
			if (!(t instanceof XException))
			{
				Trace.trace(Trace.ERROR, t.getMessage(), t);
			}
		}
		else
		{
			Trace.trace(Trace.ERROR, "Exception <null> ???", null);
		}
	}

	/**
	 * Traces a message object with the ERROR priority including the stack trace
	 * of the Throwable t passed as parameter.
	 * 
	 * @param message the message to be traced
	 * @param t the stack trace of the Throwable
	 */
	public static void error(Object message, Throwable t)
	{
		if (Trace.mTracelevel < Trace.ERROR)
		{
			return;
		}
		Trace.trace(Trace.ERROR, message, t);
	}

	/**
	 * Traces a message object with the WARN priority.
	 * 
	 * @param message the message to be traced
	 */
	public static void warn(Object message)
	{
		if (Trace.mTracelevel < Trace.WARN)
		{
			return;
		}
		Trace.trace(Trace.WARN, message, null);
	}

	/**
	 * Traces a message object with the INFO priority.
	 * 
	 * @param message the message to be traced
	 */
	public static void info(Object message)
	{
		if (Trace.mTracelevel < Trace.INFO)
		{
			return;
		}
		Trace.trace(Trace.INFO, message, null);
	}

	/**
	 * Traces a message object with the DEBUG priority.
	 * 
	 * @param message the message to be traced
	 */
	// public static void debug(Object message)
	// {
	// if (Trace.mTracelevel < Trace.DEBUG)
	// {
	// return;
	// }
	// Trace.trace(Trace.DEBUG, message, null);
	// }
	/**
	 * The <code>initialize()</code> method reads the name and the level of
	 * the trace from the <code>Configuration</code> and initializes the
	 * Tracing
	 * 
	 * @see Configuration#getValue(String,String,String)
	 */
	public static void initialize()
	{
		try
		{
			Integer traceLevel;
			Configuration config = Configuration.getInstance();
			traceLevel = new Integer(config.getValue("Base", "Trace", "Level"));
			mTracelevel = traceLevel.intValue();

			String tracerShort = config.getValue("Base", "Trace", "Tracer");
			String tracerName = Configuration.getClass("Trace", tracerShort);

			mTarget = (TraceTarget) Class.forName(tracerName).newInstance();
			System.out.println("Tracing with " + tracerName);
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Returns all trace messages as one large string.
	 * 
	 * @return String - all trace message
	 */
	public static String getTrace()
	{
		if (!isInitialized())
		{
			Trace.initialize();
		}
		String retString = mTarget.getTrace();
		if (retString == null)
		{
			return "No trace messages found.";
		}
		else
		{
			return retString;
		}
	}

	/**
	 * Tests if the Tracer is initialized.
	 * 
	 * @return boolean - true: Trace is initialized
	 */
	public static boolean isInitialized()
	{
		return (mTracelevel != NOT_INITIALIZED);
	}

	/**
	 * Writes the <code>message</code> to the sink.
	 * 
	 * @param priority the priority after which the messages should be traced
	 * @param message the message to be traced
	 * @param t the stack trace of the Throwable
	 */
	private static void trace(int priority, Object message, Throwable t)
	{
		if (mTracelevel == NOT_INITIALIZED)
		{
			Trace.initialize();
		}
		if (Trace.mTracelevel >= priority)
		{
			mTarget.trace(priority, message, t);
		}
	}

	/**
	 * @return current tracelevel
	 */
	public static int getTracelevel()
	{
		return mTracelevel;
	}

}
