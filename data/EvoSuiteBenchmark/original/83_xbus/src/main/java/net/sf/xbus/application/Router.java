package net.sf.xbus.application;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.trace.Trace;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.protocol.Message;
import net.sf.xbus.protocol.MessageFactory;

/**
 * The <code>Router</code> routes a message to one or more systems. Three
 * different strategies are implemented:
 * <ul>
 * <li>{@link #distribute(Message) distribute} sends the data to one or more
 * systems and awaits no response data.
 * <li>{@link #invoke(Message) invoke} implements a chain of senders. This
 * means the response data of one sender is the request data of the following
 * sender.
 * <li>{@link #invokeAndDistribute(Message) invokeAndDistribute} is the
 * combination of the both. First the data goes through the chain of sender
 * invocations, then the response data of the last sender is distributed to one
 * or more systems.
 * </ul>
 * <p>
 * 
 * <b>Configuration:</b>
 * <p>
 * <table border>
 * <tr>
 * <th>Chapter</th>
 * <th>Section</th>
 * <th>Key</th>
 * <th>Content</th>
 * </tr>
 * <tr>
 * <td>Router</td>
 * <td><code>message.getSource() + "." + message.getFunction()</code></td>
 * <td>Invoke<i>n</i></td>
 * <td>System where a message is sent to and from which a response is returned.</td>
 * </tr>
 * <tr>
 * <td>Router</td>
 * <td><code>message.getSource() + "." + message.getFunction()</code></td>
 * <td>Distribute<i>n</i></td>
 * <td>System where a message is sent to and from which no response is
 * returned.</td>
 * </tr>
 * </table>
 */
public class Router
{
	public static final String CHAPTER = "Router";

	private boolean mResponseFilled = false;

	private class InvokeInternalResult
	{
		Message resultMessage;
		XBUSSystem lastSystem;
	}

	/**
	 * The message will be routed depending on the entries of the configuration.
	 * 
	 * @param message the request data of this <code>Message</code> is sent to
	 *            the first system. It's response data is empty afterwards
	 *            because the last step is the distribution without responses.
	 */
	public void route(Message message) throws XException
	{
		if (getRouterEntryOptional(message, "Invoke1") != null)
		{
			if (getRouterEntryOptional(message,
					"Distribute1") != null)
			{
				invokeAndDistribute(message);
			}
			else
			{
				invoke(message);
			}
		}
		else
		{
			distribute(message);
		}
	}

	/**
	 * The <code>Message</code> is distributed to one or more systens. This
	 * means, that the given <code>Message</code> is sent to the systems (read
	 * from the <code>Configuration</code>) one after the other. Because
	 * there is no response data from the systems awaited, the response data in
	 * the <code>Message</code> is empty after the distribution.
	 * <p>
	 * 
	 * If an error occurs when calling one sender, the processing stops, a
	 * rollback is done and the returncode of the <code>Message</code> is set
	 * to <code>RC_NOK</code>.
	 * 
	 * @param message the request data of this <code>Message</code> is sent to
	 *            the systems
	 */
	public void distribute(Message message) throws XException
	{
		distributeInternal(message);
	}

	/**
	 * A combination of {@link #invoke(Message) invoke} and
	 * {@link #distribute(Message) distribute}. First the data goes through a
	 * chain of sender invocations, then the response data of the last sender is
	 * distributed to one or more systems.
	 * <p>
	 * 
	 * If an error occurs when calling one sender, the processing stops, a
	 * rollback is done and the returncode of the <code>Message</code> is set
	 * to <code>RC_NOK</code>.
	 * 
	 * @param message the request data of this <code>Message</code> is sent to
	 *            the first system. It's response data is empty afterwards
	 *            because the last step is the distribution without responses.
	 */
	public void invokeAndDistribute(Message message) throws XException
	{
		InvokeInternalResult invokeResult = invokeInternal(message);

		Message newMessage = invokeResult.resultMessage;

		if (newMessage.getReturncode() == Constants.RC_OK)
		{
			distributeInternal(newMessage);
		}

		message.setReturncode(newMessage.getReturncode());
		message.setErrorcode(newMessage.getErrorcode());
		message.setErrortext(newMessage.getErrortext());
	}

	/**
	 * A chain of invocations of one or more senders. After a sender has been
	 * called, the <code>Message</code> for the next sender is created with
	 * the response data of the previous sender. After the invocation of the
	 * last sender, the response data of the initial <code>Message</code> is
	 * filled with the response data of the last sender.
	 * <p>
	 * 
	 * If an error occurs when calling one sender, the processing stops, a
	 * rollback is done and the returncode of the initial <code>Message</code>
	 * is set to <code>RC_NOK</code>.
	 * 
	 * @param message the initial <code>Message</code>
	 */
	public void invoke(Message message) throws XException
	{
		InvokeInternalResult invokeResult = invokeInternal(message);

		Message newMessage = invokeResult.resultMessage;
		XBUSSystem lastSystem = invokeResult.lastSystem;

		MessageFactory.transformResponse2Response(lastSystem, message
				.getSource(), newMessage, message);
	}

	/**
	 * Routes the incoming <code>Message</code>-object to the systems, which
	 * are read from the <code>Configuration</code>. For every system, a new
	 * <code>Message</code>-object is created via the
	 * {@link net.sf.xbus.protocol.MessageFactory} and is sent by the
	 * {@link net.sf.xbus.technical.Adapter}.
	 * <p>
	 * 
	 * When an error occurs, the returncode of the initial <code>Message</code>-object
	 * will be set to <code>RC_NOK</code>.
	 */
	private void distributeInternal(Message message) throws XException
	{
		String errortext = null;

		String destString = getRouterEntry(message, "Distribute1");
		XBUSSystem destination;

		int countSystems = 1;
		boolean rollback = false;
		Message sendMessage = null;

		while ((destString != null) && (!rollback))
		{
			sendMessage = null;
			Configuration config = Configuration.getInstance();
			destination = new XBUSSystem(destString, message.getSource()
					.getAddresses(), config.getValueAsBooleanOptional("System",
					destString, "Broadcast"));
			// The last parameter instructs the sender to try a broadcast for
			// all configured addtional addresses. This will only be active if
			// the destination address really contains a reference to the
			// addtional address information.
			try
			{
				if (mResponseFilled)
				{
					sendMessage = MessageFactory.createSenderMessage(
							destination, message,
							MessageFactory.TRANSFORM_FROM_RESPONSE);
				}
				else
				{
					sendMessage = MessageFactory.createSenderMessage(
							destination, message,
							MessageFactory.TRANSFORM_FROM_REQUEST);
				}

				Adapter.callSender(sendMessage, destination);

				if (!Constants.RC_OK.equals(sendMessage.getReturncode()))
				{
					rollback = true;
				}
			}
			catch (XException e)
			{
				rollback = true;
				errortext = e.getMessage();
				Trace.error("Exception while routing messages");
			}
			countSystems++;
			destString = getRouterEntryOptional(message,
					new StringBuffer().append("Distribute")
							.append(countSystems).toString());
		}

		if (rollback)
		{
			if (sendMessage != null)
			{
				message.setReturncode(sendMessage.getReturncode());
				message.setErrorcode(sendMessage.getErrorcode());
				message.setErrortext(sendMessage.getErrortext());
			}
			else
			{
				message.setReturncode(Constants.RC_NOK);
				if (errortext != null)
				{
					message.setErrortext(errortext);
				}
				else
				{
					message.setErrortext(XException.getExceptionInformation());
				}
			}
		}
		else
		{
			message.setReturncode(Constants.RC_OK);
		}
	}

	/**
	 * Calls <code>Metaphase</code> first and then calls the
	 * <code>route()</code>-method of its superclass with the response from
	 * <code>Metaphase</code>.
	 */
	private InvokeInternalResult invokeInternal(Message message)
			throws XException
	{
		InvokeInternalResult result = new InvokeInternalResult();

		String destString = getRouterEntry(message,	"Invoke1");

		int countSystems = 1;
		boolean rollback = false;

		Message sendMessage = message;
		Message sendMessageNew = null;

		while ((destString != null) && (!rollback))
		{
			result.lastSystem = new XBUSSystem(destString, message.getSource()
					.getAddresses());
			try
			{
				if (countSystems == 1)
				{
					sendMessageNew = MessageFactory.createSenderMessage(
							result.lastSystem, sendMessage,
							MessageFactory.TRANSFORM_FROM_REQUEST);
				}
				else
				{
					sendMessageNew = MessageFactory.createSenderMessage(
							result.lastSystem, sendMessage,
							MessageFactory.TRANSFORM_FROM_RESPONSE);
				}

				Adapter.callSender(sendMessageNew, result.lastSystem);

				if (!sendMessageNew.getReturncode().equals(Constants.RC_OK))
				{
					rollback = true;
				}

				sendMessage = sendMessageNew;
			}
			catch (XException e)
			{
				rollback = true;
				sendMessage.setReturncode(Constants.RC_NOK);
				sendMessage.setErrortext(e.getMessage());
				Trace.error("Exception while routing messages");
			}
			countSystems++;
			destString = getRouterEntryOptional(message,
					new StringBuffer().append("Invoke").append(countSystems)
							.toString());
		}

		mResponseFilled = true;
		result.resultMessage = sendMessage;
		return result;
	}

	private String getRouterEntry(Message message, String key) throws XException
	{
		Configuration config = Configuration.getInstance();
		String retString = config.getValueOptional(CHAPTER, message.getSource().getName(), key);
		if (retString == null)
		{
			retString = config.getValue(CHAPTER, getSection(message), key);
		}
		
		return retString;
	}
	
	private String getRouterEntryOptional(Message message ,String key) throws XException
	{
		Configuration config = Configuration.getInstance();
		String retString = config.getValueOptional(CHAPTER, message.getSource().getName(), key);
		if (retString == null)
		{
			retString = config.getValueOptional(CHAPTER, getSection(message), key);
		}
		
		return retString;
	}
	
	private String getSection(Message message)
	{
		
		return new StringBuffer().append(message.getSource().getName()).append(
				".").append(message.getFunction()).toString();
	}

}
