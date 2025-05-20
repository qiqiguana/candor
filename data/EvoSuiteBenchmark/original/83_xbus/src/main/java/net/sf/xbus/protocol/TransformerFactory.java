package net.sf.xbus.protocol;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.reflection.ReflectionSupport;
import net.sf.xbus.base.xbussystem.XBUSSystem;

public class TransformerFactory
{
	/**
	 * The appropriate {@link net.sf.xbus.protocol.Transformer} object which is
	 * needed to transform the request or response of one {@link Message} to the
	 * request or response of another {@link Message} will be created. First it
	 * is checked whether there is a special transformer configured for the two
	 * systems, if not, the standard transformer from <code>xbus.conf</code>
	 * is used.
	 * 
	 * @param source the name of the interface definition of the source message
	 * @param destination the name of the interface definition of the
	 *            destination message
	 * @param sourceMessage the message holding the data to be transformed
	 * @param destinationMessage the message receiving the transformed data
	 * @return the appropriate <code>Transformer</code> object
	 * @throws XException if something goes wrong
	 */
	public static Transformer createTransformer(XBUSSystem source,
			XBUSSystem destination, Message sourceMessage,
			Message destinationMessage) throws XException
	{
		/*
		 * First search if there is a special transformer for the two systems.
		 */
		Configuration config = Configuration.getInstance();
		String transformerClassShortname = config.getValueOptional(
				"Transformer", source.getName(), destination
						.getName());
		if (transformerClassShortname == null)
		{
		transformerClassShortname = config.getValueOptional(
				"Transformer", getSection(source, sourceMessage), destination
						.getName());
		}

		/*
		 * If not, take the standard transformer from xbus.conf
		 */
		config = Configuration.getInstance("xbus");
		if (transformerClassShortname == null)
		{
			transformerClassShortname = config.getValue("Transformer",
					sourceMessage.getShortname(), destinationMessage
							.getShortname());
		}

		String transformerClass = Configuration.getClass("Transformer",
				transformerClassShortname);

		Object transformerObject = ReflectionSupport
				.createObject(transformerClass);
		return (Transformer) transformerObject;
	}

	private static String getSection(XBUSSystem system, Message message)
	{
		return new StringBuffer().append(system.getName()).append(".").append(
				message.getFunction()).toString();
	}

}
