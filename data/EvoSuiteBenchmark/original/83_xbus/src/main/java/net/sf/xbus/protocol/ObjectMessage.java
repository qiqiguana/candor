package net.sf.xbus.protocol;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;

/**
 * <code>ObjectMessage</code> is the interface for all <code>Messages</code>
 * which deals with messages that are representated as objects.
 */
public interface ObjectMessage
{
	/**
	 * Sets the object from an incomimg message.
	 * 
	 * @param obj the content of the request
	 * @param source system where the request is coming from
	 */
	public void setRequestObject(Object obj, XBUSSystem source)
			throws XException;

	/**
	 * Sets the object of the outgoing message.
	 * 
	 * @param obj the content of the response
	 * @param destination system where the response is coming from
	 */
	public void setResponseObject(Object obj, XBUSSystem destination)
			throws XException;

	/**
	 * Gets the object from an incomimg message.
	 * 
	 * @param system reference to the system for which the message is meant
	 * @return the content of the request
	 */
	public Object getRequestObject(XBUSSystem system) throws XException;

	/**
	 * Gets the object of the outgoing message.
	 * 
	 * @return the content of the response
	 */
	public Object getResponseObject() throws XException;
}
