package net.sf.xbus.technical.http;

import java.io.IOException;
import java.io.InputStream;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.ObjectSender;
import net.sf.xbus.technical.Sender;

import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * <code>HTTPStreamSender</code> gets an <code>InputStream</code> and
 * streams it to an URL.
 */
public class HTTPStreamSender extends HTTPSender
		implements
			Sender,
			ObjectSender
{
	/**
	 * Stores the given destination.
	 * 
	 * @param destination name of the interface definition
	 * @throws XException never
	 */
	public HTTPStreamSender(XBUSSystem destination)
	{
		super(destination);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.xbus.technical.ObjectSender#execute(java.lang.String,
	 *      java.lang.Object)
	 */
	public Object execute(String function, Object callData) throws XException
	{
		PostMethod method = initialize(function, null);

		InputStream inStream = (InputStream) callData;
		method.setRequestEntity(new InputStreamRequestEntity(inStream));
		sendMessage(method);

		try
		{
			inStream.close();
		}
		catch (IOException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_HTTP, "0", e);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.xbus.technical.Sender#getType()
	 */
	public String getType()
	{
		return Constants.TYPE_OBJECT;
	}
}
