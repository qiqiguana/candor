package net.sf.xbus.protocol.simple;

import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.protocol.Message;
import net.sf.xbus.protocol.ObjectMessage;
import net.sf.xbus.protocol.TextMessage;

/**
 * <code>SimpleTextMessage</code> is the most simple implementation of
 * {@link net.sf.xbus.protocol.TextMessage}.
 */
public class SimpleTextMessage extends Message
		implements
			TextMessage,
			ObjectMessage
{

	String mRequestText = null;
	String mResponseText = null;

	/**
	 * Creates the <code>SimpleTextMessage</code> with the given source.
	 */
	public SimpleTextMessage(XBUSSystem source)
	{
		super(source);
		setShortname("SimpleTextMessage");
	}

	/**
	 * This constructor initializes the new <code>SimpleTextMessage</code>
	 * with the given parameters. It is used when constructing a new
	 * <code>SimpleTextMessage</code> by converting it from another
	 * <code>Message</code>.
	 */
	public SimpleTextMessage(String function, XBUSSystem source, String id)
	{
		super(function, source, id);
		setShortname("SimpleTextMessage");
	}

	/*
	 * @see TextMessage#setRequestText(String)
	 */
	public void setRequestText(String text, XBUSSystem source)
	{
		mRequestText = text;
		setFunction("Default");
	}

	/*
	 * @see TextMessage#setResponseText(String)
	 */
	public void setResponseText(String text, XBUSSystem destination)
	{
		mResponseText = text;
	}

	/*
	 * @see TextMessage#getRequestText()
	 */
	public String getRequestText(XBUSSystem system)
	{
		return mRequestText;
	}

	/*
	 * @see TextMessage#getResponseText()
	 */
	public String getResponseText()
	{
		return mResponseText;
	}

	/*
	 * @see ObjectMessage#setRequestObject(Object)
	 */
	public void setRequestObject(Object text, XBUSSystem source)
	{
		mRequestText = (String) text;
		setFunction("Default");
	}

	/*
	 * @see ObjectMessage#setResponseObject(Object)
	 */
	public void setResponseObject(Object text, XBUSSystem destination)
	{
		mResponseText = (String) text;
	}

	/*
	 * @see ObjectMessage#getRequestObject()
	 */
	public Object getRequestObject(XBUSSystem system)
	{
		return mRequestText;
	}

	/*
	 * @see ObjectMessage#getResponseText()
	 */
	public Object getResponseObject()
	{
		return mResponseText;
	}
}
