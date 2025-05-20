package net.sf.xbus.protocol;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;

import org.w3c.dom.Document;

/**
 * <code>XMLMessage</code> is the base interface for all messages in the XML
 * format.
 * <p>
 */
public interface XMLMessage
{
	/**
	 * Returns the request XML data as a <code>org.w3c.dom.Document</code>.
	 * 
	 * @param system reference to the system for which the message is meant
	 * @return the content of the request
	 */
	public Document getRequestDocument(XBUSSystem system);

	/**
	 * Sets the request XML data as a <code>org.w3c.dom.Document</code>.
	 * 
	 * @param doc the content of the request
	 * @param source system where the request is coming from
	 */
	public void setRequestDocument(Document doc, XBUSSystem source)
			throws XException;

	/**
	 * Returns the response XML data as a <code>org.w3c.dom.Document</code>.
	 * 
	 * @return the content of the response
	 */
	public Document getResponseDocument();

	/**
	 * Sets the response XML data as a <code>org.w3c.dom.Document</code>.
	 * 
	 * @param doc the content of the response
	 * @param destination system where the response is coming from
	 */
	public void setResponseDocument(Document doc, XBUSSystem destination)
			throws XException;

}
