package net.sf.xbus.base.notifyerror;

import java.util.Hashtable;

import net.sf.xbus.application.Adapter;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.trace.Trace;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.protocol.Message;
import net.sf.xbus.protocol.MessageFactory;

/**
 * Sends a message with detailled information to one or more destinations, when
 * the xBus has detected a problem.
 */
public class NotifyError
{
	/**
	 * Sends a message with detailled information to one or more destinations,
	 * when the xBus has detected a problem.
	 * 
	 * @param className the object where the error has been detected (must not
	 *            be <code>null</code>)
	 * @param source the system from where the message has been received or
	 *            should be sent to (may be <code>null</code>)
	 * @param message an error message (may be <code>null</code>)
	 * @param request the request that has caused the error (may be
	 *            <code>null</code>)
	 * @param additionalInfo additional address information (may be
	 *            <code>null</code>)
	 */
	static public synchronized void notifyError(Object className, XBUSSystem source,
			String message, Object request, Hashtable additionalInfo)
	{
		try
		{
			Configuration config = Configuration.getInstance();

			String notifySystemName = config.getValueOptional("Base",
					"NotifyError", Constants.CHAPTER_SYSTEM + "1");

			if (notifySystemName != null)
			{
				NotifyErrorMessage errorMessage = new NotifyErrorMessage(source);
				errorMessage.setData(className.getClass().getName(), message,
						request, additionalInfo);

				if (errorMessage.getRequestText(source).indexOf("file:") == -1
						|| errorMessage.getRequestText(source).indexOf(
								"is empty") == -1)
				{
					/**
					 * Send error message to various systems
					 */
					Message sendMessage = null;
					XBUSSystem notifySystem = null;

					notifySystemName = config.getValueOptional("Base",
							"NotifyError", Constants.CHAPTER_SYSTEM + "1");
					if (notifySystemName != null)
					{
						notifySystem = new XBUSSystem(notifySystemName);
					}
					else
					{
						notifySystem = null;
					}
					int i = 1;
					while (notifySystem != null)
					{
						sendMessage = MessageFactory.createSenderMessage(
								notifySystem, errorMessage,
								MessageFactory.TRANSFORM_FROM_REQUEST);

						Adapter.callSender(sendMessage, notifySystem);

						Trace.error("Notified system: "
								+ notifySystem.getCompleteName());

						i++;
						notifySystemName = config.getValueOptional("Base",
								"NotifyError", Constants.CHAPTER_SYSTEM + i);
						if (notifySystemName != null)
						{
							notifySystem = new XBUSSystem(notifySystemName);
						}
						else
						{
							notifySystem = null;
						}
					}
				}
			}
		}
		catch (Throwable e)
		{
			Trace.error(e);
			Trace.error("Error while notfiying error");
		}
	}
}
