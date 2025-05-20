package net.sf.xbus.base.journal;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.protocol.Message;

/**
 * Interface for classes which write information about the activities of the
 * middleware.
 * <p>
 */
public interface JournalInterface
{
	/**
	 * Writes a middleware-activity.
	 * <ul>
	 * <li>A message has been received and processed.</li>
	 * <li>A message has been send to a neighbor-system.</li>
	 * </ul>
	 * <p>
	 * The {@link net.sf.xbus.base.core.config.Configuration} stores, which
	 * activities are written. All other activities are ignored.
	 * <p>
	 * 
	 * @param type <b>R</b>: message has been received, <b>S</b>: message has
	 *            been send
	 * @param system The name of the system, from which the message has been
	 *            received or where the message has been sent to.
	 * @param message The {@link Message}-object that represents the data of
	 *            the message.
	 * 
	 */
	public void log(char type, XBUSSystem system, Message message)
			throws XException;

	/**
	 * Commits the entry.
	 */
	public void commit() throws XException;
}
