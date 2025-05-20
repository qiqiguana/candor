package net.sf.xbus.technical.file;

import net.sf.xbus.base.xbussystem.XBUSSystem;

/**
 * The <code>FileByteArrayListReceiverThread</code> runs in the background and
 * receives data from files when they exist.
 */
public class FileByteArrayListReceiverThread extends FileReceiverThread
{
	/**
	 * The constructor stores the name of the system, which is processed by this
	 * thread.
	 */
	public FileByteArrayListReceiverThread(XBUSSystem source)
	{
		super(source);
	}

	/**
	 * Creates an instance of
	 * {@link net.sf.xbus.technical.file.FileByteArrayListReceiver} used to read
	 * from a file.
	 * 
	 * @return an instance of
	 *         {@link net.sf.xbus.technical.file.FileByteArrayListReceiver}
	 */
	protected FileReceiver createReceiver()
	{
		return new FileByteArrayListReceiver();
	}

}
