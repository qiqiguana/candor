package net.sf.xbus.protocol.simple;

import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.protocol.Message;
import net.sf.xbus.protocol.ObjectMessage;

public class SimpleObjectMessage extends Message implements ObjectMessage
{

	Object mRequestObject = null;
	Object mResponseObject = null;

	/**
	 * Creates the <code>SimpleObjectMessage</code> with the given source.
	 */
	public SimpleObjectMessage(XBUSSystem source)
	{
		super(source);
		setShortname("SimpleObjectMessage");
	}

	/**
	 * This constructor initializes the new <code>SimpleObjectMessage</code>
	 * with the given parameters. It is used when constructing a new
	 * <code>SimpleObjectMessage</code> by converting it from another
	 * <code>Message</code>.
	 */
	public SimpleObjectMessage(String function, XBUSSystem source, String id)
	{
		super(function, source, id);
		setShortname("SimpleObjectMessage");
	}

	/**
	 * @see net.sf.xbus.protocol.ObjectMessage#setRequestObject(java.lang.Object,
	 *      net.sf.xbus.base.xbussystem.XBUSSystem)
	 */
	public void setRequestObject(Object obj, XBUSSystem source)
	{
		mRequestObject = obj;
		setFunction("Default");
	}

	/**
	 * @see net.sf.xbus.protocol.ObjectMessage#setResponseObject(java.lang.Object,
	 *      net.sf.xbus.base.xbussystem.XBUSSystem)
	 */
	public void setResponseObject(Object obj, XBUSSystem destination)
	{
		mResponseObject = obj;
	}

	/**
	 * @see net.sf.xbus.protocol.ObjectMessage#getRequestObject(net.sf.xbus.base.xbussystem.XBUSSystem)
	 */
	public Object getRequestObject(XBUSSystem source)
	{
		return mRequestObject;
	}

	/**
	 * @see net.sf.xbus.protocol.ObjectMessage#getResponseObject()
	 */
	public Object getResponseObject()
	{
		return mResponseObject;
	}

}
