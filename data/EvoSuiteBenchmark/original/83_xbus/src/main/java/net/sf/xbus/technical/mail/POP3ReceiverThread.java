package net.sf.xbus.technical.mail;

import net.sf.xbus.base.xbussystem.XBUSSystem;

/**
 * 
 * The <code>POP3ReceiverThread</code> runs in the background and receives
 * emails.
 * 
 * @author Dominique Boivin
 */

public class POP3ReceiverThread extends POP3XMLReceiverThread
{
	/**
	 * Stores the interface name
	 * 
	 * @param source name of the interface definition
	 */
	public POP3ReceiverThread(XBUSSystem source)
	{
		super(source);
	}
	/**
	 * Creates an instance of {@link net.sf.xbus.technical.mail.POP3Receiver}
	 * used to read from a mailbox.
	 * 
	 * @return an instance of {@link net.sf.xbus.technical.mail.POP3Receiver}
	 */
	protected POP3XMLReceiver createReceiver()
	{
		return new POP3Receiver();
	}
}
