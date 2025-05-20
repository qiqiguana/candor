package net.sf.xbus.protocol.as400;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.protocol.TextMessage;
import net.sf.xbus.protocol.xml.XBUSXMLMessage;

/**
 * <code>AS400ProgramMessage</code> represents a message for calling an AS400
 * program.
 */
public class AS400ProgramMessage extends XBUSXMLMessage implements TextMessage
{
	/**
	 * This constructor stores the <code>source</code>, creates an unique
	 * identifier for the message and initializes the requestTimestamp. It is
	 * used when constructing a new <code>AS400ProgramMessage</code> from the
	 * data of a receiver.
	 * 
	 * @param source XBUSSystem object with information of this neighbor-system
	 */
	public AS400ProgramMessage(XBUSSystem source)
	{
		super(source);
		setShortname("AS400ProgramMessage");
	}

	/**
	 * Initializes the new <code>AS400ProgramMessage</code> with the given
	 * parameters. It is used when constructing a new
	 * <code>AS400ProgramMessage</code> by converting it from another
	 * <code>Message</code>.
	 * 
	 * @param function
	 * @param source
	 * @param id
	 * @throws XException if any error occurs
	 */
	public AS400ProgramMessage(String function, XBUSSystem source, String id)
	{
		super(function, source, id);
		setShortname("AS400ProgramMessage");
	}

}
