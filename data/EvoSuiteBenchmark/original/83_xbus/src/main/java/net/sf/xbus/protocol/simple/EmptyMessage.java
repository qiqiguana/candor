/*
 * Created on 21.10.2004
 */
package net.sf.xbus.protocol.simple;

import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.protocol.Message;
import net.sf.xbus.protocol.ObjectMessage;
import net.sf.xbus.protocol.TextMessage;
import net.sf.xbus.protocol.XMLMessage;

import org.w3c.dom.Document;

/**
 * <code>EmptyMessage</code> is a message with content <code>null</code>,
 * which is compatible with all message types (text, object, XML).
 * 
 * @author Stephan Düwel
 */
public class EmptyMessage extends Message
		implements
			TextMessage,
			ObjectMessage,
			XMLMessage
{

	/**
	 * @see Message
	 */
	public EmptyMessage(XBUSSystem source)
	{
		super(source);
	}

	/**
	 * @see Message
	 */
	public EmptyMessage(String function, XBUSSystem source, String id)
	{
		super(function, source, id);
	}

	/**
	 * Setting the request text just does nothing. The <code>EmptyMessage</code>
	 * is not intended to have any content.
	 * 
	 * @see net.sf.xbus.protocol.TextMessage#setRequestText(java.lang.String,
	 *      net.sf.xbus.base.xbussystem.XBUSSystem)
	 */
	public void setRequestText(String text, XBUSSystem source)
	{}

	/**
	 * Setting the response text just does nothing. The
	 * <code>EmptyMessage</code> is not intended to have any content.
	 * 
	 * @see net.sf.xbus.protocol.TextMessage#setResponseText(java.lang.String,
	 *      net.sf.xbus.base.xbussystem.XBUSSystem)
	 */
	public void setResponseText(String text, XBUSSystem destination)
	{}

	/**
	 * Getting the request text always returns <code>null</code>. The
	 * <code>EmptyMessage</code> is not intended to have any content.
	 * 
	 * @see net.sf.xbus.protocol.TextMessage#getRequestText(net.sf.xbus.base.xbussystem.XBUSSystem)
	 */
	public String getRequestText(XBUSSystem system)
	{
		return null;
	}

	/**
	 * Getting the response text always returns <code>null</code>. The
	 * <code>EmptyMessage</code> is not intended to have any content.
	 * 
	 * @see net.sf.xbus.protocol.TextMessage#getResponseText()
	 */
	public String getResponseText()
	{
		return null;
	}

	/**
	 * Setting the request object just does nothing. The
	 * <code>EmptyMessage</code> is not intended to have any content.
	 * 
	 * @see net.sf.xbus.protocol.ObjectMessage#setRequestObject(java.lang.Object,
	 *      net.sf.xbus.base.xbussystem.XBUSSystem)
	 */
	public void setRequestObject(Object obj, XBUSSystem source)
	{}

	/**
	 * Setting the response object just does nothing. The
	 * <code>EmptyMessage</code> is not intended to have any content.
	 * 
	 * @see net.sf.xbus.protocol.ObjectMessage#setResponseObject(java.lang.Object,
	 *      net.sf.xbus.base.xbussystem.XBUSSystem)
	 */
	public void setResponseObject(Object obj, XBUSSystem destination)
	{}

	/**
	 * Getting the response object always returns <code>null</code>. The
	 * <code>EmptyMessage</code> is not intended to have any content.
	 * 
	 * @see net.sf.xbus.protocol.ObjectMessage#getRequestObject(net.sf.xbus.base.xbussystem.XBUSSystem)
	 */
	public Object getRequestObject(XBUSSystem system)
	{
		return null;
	}

	/**
	 * Getting the response object always returns <code>null</code>. The
	 * <code>EmptyMessage</code> is not intended to have any content.
	 * 
	 * @see net.sf.xbus.protocol.ObjectMessage#getResponseObject()
	 */
	public Object getResponseObject()
	{
		return null;
	}

	/**
	 * Getting the resquest document always returns <code>null</code>. The
	 * <code>EmptyMessage</code> is not intended to have any content.
	 * 
	 * @see net.sf.xbus.protocol.XMLMessage#getRequestDocument(
	 *      net.sf.xbus.base.xbussystem.XBUSSystem)
	 */
	public Document getRequestDocument(XBUSSystem system)
	{
		return null;
	}

	/**
	 * Setting the request document just does nothing. The
	 * <code>EmptyMessage</code> is not intended to have any content.
	 * 
	 * @see net.sf.xbus.protocol.XMLMessage#setRequestDocument(org.w3c.dom.Document,
	 *      net.sf.xbus.base.xbussystem.XBUSSystem)
	 */
	public void setRequestDocument(Document doc, XBUSSystem source)
	{}

	/**
	 * Getting the response document always returns <code>null</code>. The
	 * <code>EmptyMessage</code> is not intended to have any content.
	 * 
	 * @see net.sf.xbus.protocol.XMLMessage#getResponseDocument()
	 */
	public Document getResponseDocument()
	{
		return null;
	}

	/**
	 * Setting the response document just does nothing. The
	 * <code>EmptyMessage</code> is not intended to have any content.
	 * 
	 * @see net.sf.xbus.protocol.XMLMessage#setResponseDocument(org.w3c.dom.Document,
	 *      net.sf.xbus.base.xbussystem.XBUSSystem)
	 */
	public void setResponseDocument(Document doc, XBUSSystem destination)
	{}

} // EmptyMessage
