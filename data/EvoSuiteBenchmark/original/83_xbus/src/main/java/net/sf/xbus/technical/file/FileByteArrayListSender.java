package net.sf.xbus.technical.file;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

import net.sf.xbus.base.bytearraylist.ByteArrayList;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.ObjectSender;
import net.sf.xbus.technical.Sender;

/**
 * <code>FileByteArrayListSender</code> manages writing a
 * <code>ByteArrayList</code> on a mounted file system.
 */
public class FileByteArrayListSender extends FileSender
		implements
			Sender,
			ObjectSender
{
	/**
	 * Constructs a FileByteArrayListSender object giving all necessary data
	 * from the standard configuration, checking file permissions and
	 * registering current resource by the
	 * {@link net.sf.xbus.base.core.TAManager}.
	 * 
	 * @exception XException if any error occurs
	 */
	public FileByteArrayListSender(XBUSSystem system) throws XException
	{
		super(system);
	}

	/**
	 * <code>execute</code> sends the given <code>ByteArrayList</code>
	 * <i>callData </i> to the neighbor-system. Three actions are possible when
	 * a file already exists:
	 * <p>
	 * <dl>
	 * <dd><table>
	 * <tr>
	 * <td><code>Append</code></td>
	 * <td></td>
	 * <td>append new data to the existing file</td>
	 * </tr>
	 * <tr>
	 * <td><code>Overwrite</code></td>
	 * <td></td>
	 * <td>overwrite existing file with the new data</td>
	 * </tr>
	 * <tr>
	 * <td><code>Error</code></td>
	 * <td></td>
	 * <td>throw XException</td>
	 * </tr>
	 * </table>
	 * </dl>
	 * 
	 * @param function Name of the interface
	 * @param callData String to be written
	 * @exception XException if any error occurs
	 */
	public Object execute(String function, Object callData) throws XException
	{
		senderExecuted = true;

		try
		{
			prepareWriteFile(mConfiguration.getFileNames()[0], 0);

			BufferedOutputStream buffOut = new BufferedOutputStream(
					new FileOutputStream(mTempFilename[0], true));

			if (callData == null)
				callData = new ByteArrayList();

			byte[] record = null;
			Iterator it = ((ByteArrayList) callData).iterator();
			if (it.hasNext())
			{
				record = (byte[]) it.next();
				buffOut.write(record);
			}
			while (it.hasNext())
			{
				buffOut.write(Constants.LINE_SEPERATOR.getBytes(mConfiguration
						.getEncoding()));
				record = (byte[]) it.next();
				buffOut.write(record);
			}
			if (Constants.LINE_SEPERATOR.equals("\n"))
				buffOut.write('\n');

			buffOut.close();
		}
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_FILE, "0", e);
		}
		return null;
	}

	public String getType()
	{
		return Constants.TYPE_OBJECT;
	}
}
