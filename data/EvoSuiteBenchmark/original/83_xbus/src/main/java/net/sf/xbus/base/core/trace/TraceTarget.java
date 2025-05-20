package net.sf.xbus.base.core.trace;

/**
 * If a class should be used by the {@link net.sf.xbus.base.core.trace.Trace} to
 * write informations, it has to implement the interface
 * <code>TraceTarget</code>.
 */
public interface TraceTarget
{
	/**
	 * Writes the <code>message</code> to the sink. <code>trace</code> is
	 * called from {@link net.sf.xbus.base.core.trace.Trace}.
	 * 
	 * @param priority the priority after which the messages should be traced
	 * @param message the message to be traced
	 * @param t the stack trace of the Throwable
	 */
	public void trace(int priority, Object message, Throwable t);

	/**
	 * Returns all trace messages as one large string.
	 */
	public String getTrace();

}
