package net.sf.xbus.technical;

import net.sf.xbus.base.core.XException;

/**
 * <code>RecordByteArrayReader</code> are used by some Senders and Receivers
 * to send a byte stream through the xBus.
 */
public interface RecordByteArrayReader
{
	/**
	 * Initialization to be done before reading from the
	 * <code>RecordByteArrayReader</code>.
	 * 
	 * @throws XException if something goes wrong
	 */
	public void prepareReading() throws XException;

	/**
	 * <code>readRecord</code> is called by senders to read the incoming data
	 * line by line.
	 * 
	 * @return <code>null</code> if there is no more record
	 * @throws XException if something goes wrong
	 */
	public byte[] readRecord() throws XException;

	/**
	 * Cleanup to be done after reading from the
	 * <code>RecordByteArrayReader</code>, e.g. closing an InputStream.
	 * 
	 * @throws XException if something goes wrong
	 */
	public void terminateReading() throws XException;
}
