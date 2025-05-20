package net.sf.xbus.protocol;

import java.util.List;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.reflection.ReflectionSupport;
import net.sf.xbus.base.xbussystem.XBUSSystem;

import org.w3c.dom.Document;

/**
 * The class <code>MessageFactory</code> is used to dynamically create
 * messages. It implements the <b>Factory </b> Design-Pattern.
 * <p>
 */
public class MessageFactory
{
	public static final int TRANSFORM_FROM_REQUEST = 0;

	public static final int TRANSFORM_FROM_RESPONSE = 1;

	/**
	 * The appropriate <code>Message</code> -object for the given source will
	 * be created. The name of the appropriate <code>Message</code> class is
	 * read from the {@link net.sf.xbus.base.core.config.Configuration}.
	 * 
	 * @param source the name of the system, from which the message was received
	 */
	public static Message createApplicationMessage(XBUSSystem source)
			throws XException
	{
		Configuration config = Configuration.getInstance();

		String messageClassShortname = config.getValue(
				Constants.CHAPTER_SYSTEM, source.getName(), "Message");

		String messageClass = Configuration.getClass("Message",
				messageClassShortname);

		Class[] conArgsClass = new Class[]
		{ReflectionSupport
				.classForName("net.sf.xbus.base.xbussystem.XBUSSystem")};
		Object[] conArgs = new Object[]
		{source};

		return (Message) ReflectionSupport.createObject(messageClass,
				conArgsClass, conArgs);
	}

	/**
	 * Transforms the response of a source message to the response of a
	 * destination message.
	 * 
	 * @param source the system name where the response of the source message is
	 *            coming from
	 * @param destination the system name where the response of the destination
	 *            message shall be sent to
	 * @param sourceMessage the <code>Message</code> from which the response
	 *            to transform is taken.
	 * @param destinationMessage the <code>Message</code> whose response is
	 *            filled
	 */
	static public void transformResponse2Response(XBUSSystem source,
			XBUSSystem destination, Message sourceMessage,
			Message destinationMessage) throws XException
	{
		String receiverType = Configuration.getInstance().getValueOptional(
				"TransformInput", source.getName(), destination.getName());

		if (receiverType == null)
		{
			if (sourceMessage instanceof XMLMessage)
			{
				receiverType = Constants.TYPE_XML;
			}
			else if (sourceMessage instanceof ObjectMessage)
			{
				receiverType = Constants.TYPE_OBJECT;
			}
			else if (sourceMessage instanceof TextMessage)
			{
				receiverType = Constants.TYPE_TEXT;
			}
			else
			{
				List params = new Vector(1);
				params.add(sourceMessage.getShortname());
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_PROTOCOL, "9", params);
			}
		} // if (receiverType==null) - no specific type declared for system

		Object input = null;
		Object output = null;

		if (receiverType.equals(Constants.TYPE_XML))
		{
			input = ((XMLMessage) sourceMessage).getResponseDocument();
		}
		else if (receiverType.equals(Constants.TYPE_OBJECT))
		{
			// the message content is serialised due to the structure
			// description attached to the source system
			input = ((ObjectMessage) sourceMessage).getResponseObject();
		}
		else
		{
			// the message content is serialised due to the structure
			// description attached to the source system
			input = ((TextMessage) sourceMessage).getResponseText();
		}

		Transformer transformer = TransformerFactory.createTransformer(source,
				destination, sourceMessage, destinationMessage);

		output = transformer.transform(input, source, destination,
				destinationMessage);

		// The transformation result is a string.
		if (output instanceof String)
		{
			((TextMessage) destinationMessage).setResponseText((String) output,
					destination);
		} // then (output.getClass().getName().equals("java.lang.String"))
		else if (output instanceof Document)
		{ // The transformation result is a DOM tree.
			((XMLMessage) destinationMessage).setResponseDocument(
					(Document) output, destination);
		} // then (doc.isInstance(output))
		else
		{ // The transformation result is anything else than a string or a
			// DOM tree.
			((ObjectMessage) destinationMessage).setResponseObject(output,
					destination);
		} // else (output.getClass().getName().equals("java.lang.String"))
	}

	/**
	 * The appropriate <code>Message</code> object which is needed to send a
	 * message to the given destination will be created. At this point either
	 * the request or the response of the incoming <code>Message</code> will
	 * be converted to the request of the outgoing <code>Message</code>.
	 * 
	 * @param destination the name of the system where the message will be send
	 *            to
	 * @param sourceMessage the <code>Message</code> from which the new
	 *            <code>Message</code> will be initialized
	 * @param convertFrom flag wether the request or the response of the
	 *            sourceMessage is used to fill the request of the new
	 *            <code>Message</code>
	 */
	public static Message createSenderMessage(XBUSSystem destination,
			Message sourceMessage, int convertFrom) throws XException
	{
		if ((convertFrom != TRANSFORM_FROM_REQUEST)
				&& (convertFrom != TRANSFORM_FROM_RESPONSE))
		{
			List params = new Vector();
			params.add(String.valueOf(convertFrom));
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_PROTOCOL, "7", params);
		}

		Message destinationMessage = createMessage(sourceMessage, destination);

		XBUSSystem source = sourceMessage.getSource();

		String receiverType = Configuration.getInstance().getValueOptional(
				"TransformInput", source.getName(), destination.getName());

		if (receiverType == null)
		{
			if (sourceMessage instanceof XMLMessage)
			{
				receiverType = Constants.TYPE_XML;
			}
			else if (sourceMessage instanceof ObjectMessage)
			{
				receiverType = Constants.TYPE_OBJECT;
			}
			else if (sourceMessage instanceof TextMessage)
			{
				receiverType = Constants.TYPE_TEXT;
			}
			else
			{
				List params = new Vector(1);
				params.add(sourceMessage.getShortname());
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_PROTOCOL, "9", params);
			}
		} // if (receiverType==null) - no specific type declared for system

		Object input = null;
		Object output = null;

		switch (convertFrom)
		{
			case TRANSFORM_FROM_REQUEST :
				if (receiverType.equals(Constants.TYPE_XML))
				{
					input = ((XMLMessage) sourceMessage)
							.getRequestDocument(source);
				}
				else if (receiverType.equals(Constants.TYPE_OBJECT))
				{
					// the message content is serialised due to the structure
					// description attached to the source system
					input = ((ObjectMessage) sourceMessage)
							.getRequestObject(source);
				}
				else if (receiverType.equals(Constants.TYPE_TEXT))
				{
					// the message content is serialised due to the structure
					// description attached to the source system
					input = ((TextMessage) sourceMessage)
							.getRequestText(source);
				}
				break;
			case TRANSFORM_FROM_RESPONSE :
				if (receiverType.equals(Constants.TYPE_XML))
				{
					input = ((XMLMessage) sourceMessage).getResponseDocument();
				}
				else if (receiverType.equals(Constants.TYPE_OBJECT))
				{
					// the message content is serialised due to the structure
					// description attached to the source system
					input = ((ObjectMessage) sourceMessage).getResponseObject();
				}
				else if (receiverType.equals(Constants.TYPE_TEXT))
				{
					// the message content is serialised due to the structure
					// description attached to the source system
					input = ((TextMessage) sourceMessage).getResponseText();
				}
				break;
		}

		Transformer transformer = TransformerFactory.createTransformer(source,
				destination, sourceMessage, destinationMessage);

		output = transformer.transform(input, source, destination,
				destinationMessage);

		if (output != null)
		{
			if (output instanceof String)
			{
				((TextMessage) destinationMessage).setRequestText(output
						.toString(), destination);
			}
			else if (output instanceof Document)
			{
				((XMLMessage) destinationMessage).setRequestDocument(
						(Document) output, destination);
			}
			else
			{
				((ObjectMessage) destinationMessage).setRequestObject(output,
						destination);
			}
		}
		return destinationMessage;
	}

	static private Message createMessage(Message source, XBUSSystem destination)
			throws XException
	{
		/*
		 * Constructing the parameters for the constructor.
		 */
		Class[] conArgsClass = new Class[]
		{
				ReflectionSupport.classForName("java.lang.String"),
				ReflectionSupport
						.classForName("net.sf.xbus.base.xbussystem.XBUSSystem"),
				ReflectionSupport.classForName("java.lang.String")};

		Object[] conArgs = new Object[]
		{source.getFunction(), source.getSource(), source.getId()};

		/*
		 * Getting the type of Message that must be created
		 */
		Configuration config = Configuration.getInstance();
		String newMessageShortname = config.getValue(Constants.CHAPTER_SYSTEM,
				destination.getName(), "Message");
		String newMessageName = Configuration.getClass("Message",
				newMessageShortname);

		/*
		 * Instanciating the new Message
		 */
		return (Message) ReflectionSupport.createObject(newMessageName,
				conArgsClass, conArgs);
	}
}
