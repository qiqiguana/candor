package net.sf.xbus.base.notifyerror;

import java.util.Enumeration;
import java.util.Hashtable;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.protocol.Message;
import net.sf.xbus.protocol.TextMessage;

/**
 * @author Fleckenstein
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class NotifyErrorMessage extends Message implements TextMessage
{
	private String mRequestText;
	private String mResponseText;
	private String mErrorMessage;
	private Object mErrorRequest;
	private String mClassName;
	private Hashtable mAdditionalInfo;

	/**
	 * Creates the <code>NotifyErrorMessage</code> with the given source.
	 */
	public NotifyErrorMessage(XBUSSystem source)
	{
		super(source);
		setShortname("NotifyErrorMessage");
	}

	/**
	 * This constructor initializes the new <code>NotifyErrorMessage</code>
	 * with the given parameters. It is used when constructing a new
	 * <code>NotifyErrorMessage</code> by converting it from another
	 * <code>Message</code>.
	 */
	public NotifyErrorMessage(String function, XBUSSystem source, String id)
	{
		super(function, source, id);
		setShortname("NotifyErrorMessage");
	}

	public void setData(String className, String errorMessage,
			Object errorRequest, Hashtable additionalInfo) throws XException
	{
		/**
		 * Get the exception information first, before other errors may occur
		 */
		String stackTrace = XException.getExceptionInformation();

		/*
		 * Set some internal fields
		 */
		mErrorMessage = errorMessage;
		mErrorRequest = errorRequest;
		mAdditionalInfo = additionalInfo;
		mClassName = className;

		Configuration config = Configuration.getInstance();

		String sourceName = "<null>";
		if (getSource() != null)
		{
			sourceName = getSource().getCompleteName();
		}

		/**
		 * Build up error message
		 */
		StringBuffer errorText = new StringBuffer()
				.append("Unrecoverable error while receiving message!")
				.append(Constants.LINE_SEPERATOR)
				.append(Constants.LINE_SEPERATOR)
				.append("Class          : ")
				.append(mClassName)
				.append(Constants.LINE_SEPERATOR)
				.append(Constants.LINE_SEPERATOR)
				.append("Date            : ")
				.append(Constants.getDateFormat().format(getRequestTimestamp()))
				.append(Constants.LINE_SEPERATOR).append(
						Constants.LINE_SEPERATOR).append("Receiving system: ")
				.append(sourceName).append(Constants.LINE_SEPERATOR).append(
						Constants.LINE_SEPERATOR).append("Error message:    ")
				.append(mErrorMessage).append(Constants.LINE_SEPERATOR);

		if (mErrorRequest != null)
		{
			int length = config.getValueAsInt("Base", "NotifyError",
					"MessageLength");
			if (length > 0)
			{
				String errorRequestString = mErrorRequest.toString();
				if (errorRequestString.length() > length)
				{
					errorRequestString = mErrorRequest.toString().substring(
							length);
				}

				errorText.append(Constants.LINE_SEPERATOR).append(
						"Request message:  ").append(Constants.LINE_SEPERATOR)
						.append(Constants.LINE_SEPERATOR).append(
								errorRequestString).append(
								Constants.LINE_SEPERATOR);
			}
			else if (length < 0)
			{
				errorText.append(Constants.LINE_SEPERATOR).append(
						"Request message:  ").append(Constants.LINE_SEPERATOR)
						.append(Constants.LINE_SEPERATOR).append(
								mErrorRequest.toString()).append(
								Constants.LINE_SEPERATOR);
			}
		}

		Object key = null;
		Object value = null;
		if (additionalInfo != null)
		{
			errorText.append(Constants.LINE_SEPERATOR).append(
					"Additional information:").append(Constants.LINE_SEPERATOR)
					.append(Constants.LINE_SEPERATOR);

			for (Enumeration e = additionalInfo.keys(); e.hasMoreElements();)
			{
				key = e.nextElement();
				value = additionalInfo.get(key);
				errorText.append(key.toString()).append(": ").append(
						value.toString()).append(Constants.LINE_SEPERATOR);
			}
		}

		if (stackTrace != null)
		{
			errorText.append(Constants.LINE_SEPERATOR).append(
					"Exception caught:").append(Constants.LINE_SEPERATOR)
					.append(Constants.LINE_SEPERATOR).append(stackTrace)
					.append(Constants.LINE_SEPERATOR);
		}

		setRequestText(errorText.toString(), null);
	}

	/*
	 * @see TextMessage#setRequestText(String)
	 */
	public void setRequestText(String text, XBUSSystem source)
	{
		mRequestText = text;
		setFunction("Error");
	}

	/*
	 * @see TextMessage#setResponseText(String)
	 */
	public void setResponseText(String text, XBUSSystem destination)
	{
		mResponseText = text;
		setReturncode(Constants.RC_OK);
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

	/**
	 * Returns the additionalInfo.
	 * 
	 * @return Hashtable
	 */
	public Hashtable getAdditionalInfo()
	{
		return mAdditionalInfo;
	}

	/**
	 * Returns the errorMessage.
	 * 
	 * @return String
	 */
	public String getErrorMessage()
	{
		return mErrorMessage;
	}

	/**
	 * Returns the errorRequest.
	 * 
	 * @return Object
	 */
	public Object getErrorRequest()
	{
		return mErrorRequest;
	}

	/**
	 * Returns the className.
	 * 
	 * @return String
	 */
	public String getClassName()
	{
		return mClassName;
	}

}
