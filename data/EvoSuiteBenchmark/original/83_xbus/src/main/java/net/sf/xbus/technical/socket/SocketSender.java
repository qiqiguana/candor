package net.sf.xbus.technical.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.Sender;
import net.sf.xbus.technical.TextSender;

/**
 * The <code>SocketSender</code> sends a message to a socket. No response is
 * given back.
 */
public class SocketSender implements Sender, TextSender
{

	XBUSSystem mDestination = null;

	String mHost = null;
	int mPort;

	/**
	 * The constructor stores the destination.
	 * 
	 * @param destination name of the interface definition
	 */
	public SocketSender(XBUSSystem destination)
	{
		mDestination = destination;
	}

	/**
	 * @see net.sf.xbus.technical.TextSender#execute(java.lang.String,
	 *      java.lang.String)
	 * 
	 * @param function ignored
	 */
	public String execute(String function, String callData) throws XException
	{
		Socket outSocket = null;
		PrintWriter out = null;

		if (callData == null)
			callData = "";

		try
		{
			Configuration config = Configuration.getInstance();
			mHost = config.getValue(Constants.CHAPTER_SYSTEM, mDestination
					.getName(), "Host");
			mPort = config.getValueAsInt(Constants.CHAPTER_SYSTEM, mDestination
					.getName(), "Port");

			outSocket = new Socket(mHost, mPort);
			out = new PrintWriter(outSocket.getOutputStream(), true);
			out.print(callData);
			out.close();
			outSocket.close();
		}
		catch (UnknownHostException e)
		{
			List params = new Vector(2);
			params.add(mHost);
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_SOCKET, "1", e, params);
		}
		catch (IOException e)
		{
			List params = new Vector(2);
			params.add(mHost);
			params.add(String.valueOf(mPort));
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_SOCKET, "2", e, params);
		}
		return null;
	}

	public String getType()
	{
		return Constants.TYPE_TEXT;
	}
}
