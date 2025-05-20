package net.sf.xbus.protocol;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;

/**
 * <code>TextMessage</code> is the interface for all <code>Messages</code>
 * which deals with textual messages, for example XML.
 */
public interface TextMessage
{
	/**
	 * Sets the text of the incoming message.
	 * 
	 * @param text the content of the request
	 * @param source system where the request is coming from
	 */
	public void setRequestText(String text, XBUSSystem source)
			throws XException;

	/**
	 * Sets the text of the outgoing message.
	 * 
	 * @param text the content of the response
	 * @param destination system where the response is coming from
	 */
	public void setResponseText(String text, XBUSSystem destination)
			throws XException;

	/**
	 * Gets the text from an incomimg message.
	 * 
	 * @param system reference to the system for which the message is meant
	 * @return the content of the request
	 */
	public String getRequestText(XBUSSystem system) throws XException;

	/**
	 * Gets the text of the outgoing message.
	 * 
	 * @return the content of the response
	 */
	public String getResponseText() throws XException;
}
