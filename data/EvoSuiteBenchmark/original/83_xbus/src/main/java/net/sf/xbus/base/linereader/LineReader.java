package net.sf.xbus.base.linereader;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;

/**
 * LineReaders are used by some Senders and Receivers to send a character stream
 * through the xBus.
 */
public interface LineReader
{
	/**
	 * Initialization to be done before reading from the <code>LineReader</code>.
	 * 
	 * @throws XException if something goes wrong
	 */
	public void prepareReading(XBUSSystem system) throws XException;

	/**
	 * <code>readRecord</code> is called by senders to read the incoming data
	 * line by line.
	 * 
	 * @return <code>null</code> if there is no more line
	 * @throws XException if something goes wrong
	 */
	public String readRecord() throws XException;

	/**
	 * Cleanup to be done after reading from the <code>LineReader</code>,
	 * e.g. closing an InputStream.
	 * 
	 * @throws XException if something goes wrong
	 */
	public void terminateReading() throws XException;
}
