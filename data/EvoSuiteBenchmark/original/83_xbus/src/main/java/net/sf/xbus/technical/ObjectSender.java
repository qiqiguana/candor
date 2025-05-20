package net.sf.xbus.technical;

import net.sf.xbus.base.core.XException;

/**
 * Senders which send objects as messages to neighbor-systems have to implement
 * the <code>ObjectSender</code>-interface.
 */
public interface ObjectSender
{
	/**
	 * The given object will be send to the neighbor system. The response of the
	 * neighbor system is the return value of this method.
	 * <p>
	 * 
	 * Optionally a function can be specified. It can be used for calling
	 * different methods of the neighbor-system.
	 * 
	 * @param function used by some senders
	 * @param callData the data that shall be send to the neighbor system
	 * @return the response of the neighbor system
	 * @throws XException if something goes wrong
	 */
	public Object execute(String function, Object callData) throws XException;
}
