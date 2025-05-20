package net.sf.xbus.technical.file;

import net.sf.xbus.base.xbussystem.XBUSSystem;

/**
 * The <code>FileStreamReceiverThread</code> runs in the background and
 * receives data from files when they exist.
 */
public class FileStreamReceiverThread extends FileReceiverThread
{
	/**
	 * The constructor stores the name of the system, which is processed by this
	 * thread.
	 */
	public FileStreamReceiverThread(XBUSSystem source)
	{
		super(source);
	}

	/**
	 * Creates an instance of
	 * {@link net.sf.xbus.technical.file.FileStreamReceiver} used to read from a
	 * file.
	 * 
	 * @return an instance of
	 *         {@link net.sf.xbus.technical.file.FileStreamReceiver}
	 */
	protected FileReceiver createReceiver()
	{
		return new FileStreamReceiver();
	}

}
