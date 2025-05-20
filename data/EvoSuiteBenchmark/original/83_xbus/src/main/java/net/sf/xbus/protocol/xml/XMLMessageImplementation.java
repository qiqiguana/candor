package net.sf.xbus.protocol.xml;

import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.protocol.ObjectMessage;
import net.sf.xbus.protocol.TextMessage;
import net.sf.xbus.protocol.XMLMessage;

/**
 * <code>XMLMessageImplementation</code> represents generic XML messages.
 */
public class XMLMessageImplementation extends XMLMessageAbstract
		implements
			TextMessage,
			ObjectMessage,
			XMLMessage
{

	/**
	 * This constructor stores the <code>source</code>, creates an unique
	 * identifier for the call and initializes the requestTimestamp. It is used
	 * when constructing a new <code>XBUSXMLMessage</code> from the data of a
	 * receiver.
	 */
	public XMLMessageImplementation(XBUSSystem source)
	{
		super(source);
		setShortname("XMLMessageImplementation");
	}

	/**
	 * This constructor initializes the new
	 * <code>XMLMessageImplementation</code> with the given parameters. It is
	 * used when constructing a new <code>XMLMessageImplementation</code> by
	 * converting it from another <code>Message</code>.
	 */
	public XMLMessageImplementation(String function, XBUSSystem source,
			String id)
	{
		super(function, source, id);
		setShortname("XMLMessageImplementation");
	}

	protected void synchronizeRequestFields(XBUSSystem system)
	{}

	protected void synchronizeResponseFields(XBUSSystem system)
	{}
}
