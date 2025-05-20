package net.sf.xbus.base.core.trace;

import net.sf.xbus.base.core.XException;

/**
 * This implementation of <code>TraceTarget</code> writes the trace to the
 * console (<code>stdout</code>).
 */
public class ConsoleTrace implements TraceTarget
{
	/**
	 * Writes the <code>message</code>, the <code>priority</code> and if
	 * given the backtrace of the <code>Throwable</code> to the console.
	 * <p>
	 * It uses the class {@link net.sf.xbus.base.core.trace.Formatter}.
	 * 
	 * @param priority the priority after which the messages should be traced
	 * @param message the message to be traced
	 * @param t the stack trace of the Throwable
	 */
	public void trace(int priority, Object message, Throwable t)
	{
		if (message == null)
		{
			message = "<null>";
		}
		try
		{
			System.out.println(Formatter.format(message.toString(), priority));
			if (t != null)
			{
				t.printStackTrace();
			}
		}
		catch (Exception e)
		{
			System.out.println("Exception caught in Tracing");
		}

	}

	/**
	 * Not implemented for <code>ConsoleTrace</code>!
	 * <p>
	 * Returns all trace messages as one large string.
	 * 
	 * @return String - "ConsoleTrace can't return trace messages."
	 * @exception XException if any error occurs
	 */
	public String getTrace()
	{
		return "ConsoleTrace can't return trace messages.";
	}
}
