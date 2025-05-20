package net.sf.xbus.technical.as400;

import java.util.Iterator;

import net.sf.xbus.base.bytearraylist.ByteArrayList;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.TAResource;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.ObjectSender;
import net.sf.xbus.technical.Sender;

import com.ibm.as400.access.AS400File;
import com.ibm.as400.access.Record;
import com.ibm.as400.access.RecordFormat;
import com.ibm.as400.access.SequentialFile;

/**
 * <code>AS400AS400ByteArrayListSender</code> manages writing a file on the
 * iSeries integrated file System.
 * <p>
 * <b>Configuration:</b>
 * <p>
 * <table border>
 * <tr>
 * <th>Chapter</th>
 * <th>Section</th>
 * <th>Key</th>
 * <th>Content</th>
 * </tr>
 * <tr>
 * <td>System</td>
 * <td>Interface</td>
 * <td>Filename</td>
 * <td>Path name that represents an object in the <br>
 * QSYS library file system on the AS400 machine<</td>
 * </tr>
 * <tr>
 * <td>System</td>
 * <td>Interface</td>
 * <td>ConflictResolution</td>
 * <td>Three actions must be possible when a file already exists <br>
 * <b><i>apend, overwrite</i></b> or<b><i> error</i></b></td>
 * </tr>
 * <tr>
 * <td>System</td>
 * <td>Interface</td>
 * <td>Encoding</td>
 * <td>Specified character encoding of the interface <i>(Optional)</i></td>
 * </tr>
 * </table border>
 */
public class AS400ByteArrayListSender extends AS400FileSender
		implements
			Sender,
			TAResource,
			ObjectSender
{
	/**
	 * Constructs a AS400ByteArrayListSender object giving all necessary data
	 * from the standard configuration, connecting to the iSeries and
	 * registering current resource by the
	 * {@link net.sf.xbus.base.core.TAManager}.
	 * 
	 * @see AS400Connection
	 * @exception XException - If any error occurs
	 */
	public AS400ByteArrayListSender(XBUSSystem system) throws XException
	{
		super(system);
	}

	/**
	 * Implemented method <code>execute</code> from ObjectSender sends the
	 * given object <i>callData</i> to the AS400-system. Three actions must be
	 * taking into account if a file already exists:
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
	 * @param function - name of the interface
	 * @param callData - String to be written
	 * @exception XException if any error occurs
	 */
	public Object execute(String function, Object callData) throws XException
	{
		RecordFormat format = prepareWriting();

		if (callData == null)
			callData = new ByteArrayList();

		writeData(callData, mOriginFile, format);
		return null;
	}

	/**
	 * Writes data to the write to the integrated file system object represented
	 * by this path name.<br>
	 * Follow actions are implemented to do it:
	 * <ol>
	 * <li>break a callData into tokens
	 * <li>convert each tokens in to byte[] according to a specified character
	 * encoding.
	 * <li>create from this array new record based on the record format
	 * <li>write this record into temp file object in the iSeries integrated
	 * fiel system.
	 * </ol>
	 * <i>Note:</i> The integrated file system object must be opened prior to
	 * invoking this method
	 * 
	 * @param callData Data to be wrote to the file object in the iSeries
	 *            integrated file system
	 * @param file AS/400 physical file to be written
	 * @param format Record format which was retreived from the original file
	 * @exception XException if any errors occurs
	 */
	private void writeData(Object callData, SequentialFile file,
			RecordFormat format)
	{
		try
		{
			// open original file for each resolution.
			mOriginFile.open(AS400File.READ_WRITE, 0,
					AS400File.COMMIT_LOCK_LEVEL_DEFAULT);

			for (Iterator it = ((ByteArrayList) callData).iterator(); it
					.hasNext();)
			{
				Record record = format.getNewRecord((byte[]) it.next());
				// write Record into file
				file.write(record);

			}
			mOriginFile.close();
		}
		catch (Exception ex)
		{}

	}

	public String getType()
	{
		return Constants.TYPE_OBJECT;
	}
}
