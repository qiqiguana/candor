package net.sf.xbus.protocol;

import java.util.Date;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.xbussystem.XBUSSystem;

/**
 * <code>Message</code> is the base for all classes that handle the data of
 * requests and responses. It contains all methods and attributes which are
 * independent of the application-protocol.
 */
public class Message
{
	/**
	 * Each message that has been initially received has an unique identifier.
	 * If there is a chain, in which this message is processed (eg. with storing
	 * in a message-queue), the identifier is preserved in all steps.
	 * <p>
	 * This means, if the identifier can be read from an incoming call, it is
	 * used, otherwise a new identifier will be created.
	 * <p>
	 * It is allowed to create <code>Message</code>-Classes, which always
	 * creates a new identifier for incoming calls. But these
	 * <code>Message's</code> shouldn't be used in the middle of a chain.
	 */
	private String mId = null;

	/**
	 * The name of a function, that a neighbor-system should execute, when it
	 * receives the message.
	 */
	private String mFunction = null;

	/**
	 * Each neighbor-system of the xBus has a name. The message stores the name
	 * of the neighbor-system which has sent the call.
	 * <p>
	 * The name of the neighbor-system is under control of the xBus. It is part
	 * of the configuration of the receiver and cannot be changed.
	 */
	private XBUSSystem mSource = null;

	/**
	 * The timestamp when this <code>Message</code> has been received.
	 */
	private Date mRequestTimestamp = null;

	/**
	 * The timestamp when the response has been given to the neighbor-system,
	 * which has sent the request.
	 */
	private Date mResponseTimestamp = null;

	/**
	 * The returncode indicates, wether an request has been processed
	 * successfully (<code>RC_OK</code>) or not (<code>RC_NOK</code>).
	 */
	private String mReturncode = Constants.RC_OK;

	/**
	 * If an error has occured an numerical errorcode can be set by the method
	 * or neighbor-system that generates the error.
	 */
	private int mErrorcode = 0;

	/**
	 * If an error has occured an errortext can be set by the method or
	 * neighbor-system that generates the error.
	 */
	private String mErrortext = null;

	/**
	 * The shortname for the call is used many times in the configuration
	 */
	private String mShortname = null;

	/**
	 * This constructor stores the <code>source</code>, creates an unique
	 * identifier for the call and initializes the requestTimestamp. It is used
	 * when constructing a new <code>Message</code> from the data of a
	 * receiver.
	 */
	public Message(XBUSSystem source)
	{
		mSource = source;
		mId = createUniqueId();
		mRequestTimestamp = new Date();
		mFunction = "Default";
	}

	/**
	 * This constructor initializes the new <code>Message</code> with the
	 * given parameters. It is used when constructing a new <code>Message</code>
	 * by converting it from another <code>Message</code>.
	 */
	public Message(String function, XBUSSystem source, String id)
	{
		mRequestTimestamp = new Date();
		mId = id;
		mFunction = function;
		mSource = source;
	}

	/**
	 * Gets the unique id.
	 */
	public String getId()
	{
		return mId;
	}

	/**
	 * Sets the unique id.
	 */
	protected void setId(String id)
	{
		mId = id;
	}

	/**
	 * Gets the name of the function.
	 */
	public String getFunction()
	{
		return mFunction;
	}

	/**
	 * Sets the name of the function.
	 */
	protected void setFunction(String function)
	{
		mFunction = function;
	}

	/**
	 * Gets the name of the neighbor-system that has send the message.
	 */
	public XBUSSystem getSource()
	{
		return mSource;
	}

	/**
	 * Gets the timestamp when this <code>Message</code> has been received.
	 */
	public Date getRequestTimestamp()
	{
		return mRequestTimestamp;
	}

	/**
	 * Gets the timestamp when the response has been given to the
	 * neighbor-system, which has sent the request.
	 */
	public Date getResponseTimestamp()
	{
		return mResponseTimestamp;
	}

	/**
	 * Sets the timestamp when the response has been given to the
	 * neighbor-system, which has sent the request.
	 */
	public void setResponseTimestamp(Date timestamp)
	{
		mResponseTimestamp = timestamp;
	}

	/**
	 * Gets the returncode.
	 */
	public String getReturncode()
	{
		return mReturncode;
	}

	/**
	 * Sets the returncode.
	 */
	public void setReturncode(String rc)
	{
		mReturncode = rc;
	}

	/**
	 * Gets the errorcode.
	 */
	public int getErrorcode()
	{
		return mErrorcode;
	}

	/**
	 * Sets the errorcode.
	 */
	public void setErrorcode(int errorcode)
	{
		mErrorcode = errorcode;
	}

	/**
	 * Gets the errortext.
	 */
	public String getErrortext()
	{
		return mErrortext;
	}

	/**
	 * Sets the errortext.
	 */
	public void setErrortext(String errortext)
	{
		mErrortext = errortext;
	}

	/**
	 * Sets the shortname.
	 */
	public void setShortname(String shortname)
	{
		mShortname = shortname;
	}

	/**
	 * Gets the shortname.
	 */
	public String getShortname()
	{
		return mShortname;
	}

	private String createUniqueId()
	{
		return new StringBuffer().append(Thread.currentThread().getName())
				.append("_").append((new Date()).getTime()).toString();
	}

}
