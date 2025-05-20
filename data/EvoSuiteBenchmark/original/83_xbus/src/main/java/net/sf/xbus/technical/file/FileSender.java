package net.sf.xbus.technical.file;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.TAManager;
import net.sf.xbus.base.core.TAResource;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.Sender;
import net.sf.xbus.technical.TextSender;

/**
 * <code>FileSender</code> manages writing a text file on a mounted file
 * system.
 * <p>
 * <b>Configuration: </b>
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
 * <td>File path name on the mounted-system</td>
 * </tr>
 * <tr>
 * <td>System</td>
 * <td>Interface</td>
 * <td>ConflictResolution</td>
 * <td>Three actions must be possible when a file already exists <br>
 * <b><i>append, overwrite <i><b>or <b><i>error </i> </b></td>
 * </tr>
 * <tr>
 * <td>System</td>
 * <td>Interface</td>
 * <td>Encoding</td>
 * <td>Specified character encoding of the interface <i>(Optional) </i></td>
 * </tr>
 * </table border>
 */
public class FileSender extends FileBase
		implements
			Sender,
			TAResource,
			TextSender
{
	protected FileSenderConfiguration mConfiguration = null;

	/**
	 * Path name of the temporary file to be write Array for broadcasting
	 */
	protected String[] mTempFilename = null;

	/**
	 * Path name for the backup copy Array for broadcasting
	 */
	protected String[] mBackupFilename = null;

	/**
	 * Indicates wether execute has been called, used in commit and rollback
	 */
	protected boolean senderExecuted = false;

	/**
	 * Constructs a FileSender object giving all necessary data from the
	 * standard configuration, checking file permissions and registering current
	 * resource by the {@link net.sf.xbus.base.core.TAManager}.
	 * 
	 * @param system destination of the message
	 * @exception XException if an error occurs
	 */
	public FileSender(XBUSSystem system) throws XException
	{
		readConfiguration(system);
		TAManager taManager = TAManager.getInstance();
		taManager.registerResource(this);
	}

	/**
	 * Reads follow data for the given XBUSSystem object from the standard
	 * configuration and stores it in the class variables.
	 * <p>
	 * <dl>
	 * <dd><table>
	 * <tr>
	 * <td><code>Filename</code></td>
	 * <td></td>
	 * <td>path name of the file to be write</td>
	 * </tr>
	 * <tr>
	 * <td><code>ConflictResolution</code></td>
	 * <td></td>
	 * <td>resolved action when the file already exists <br>
	 * <i>(append, overwrite, error) </i></td>
	 * </tr>
	 * <tr>
	 * <td><code>Encoding</code></td>
	 * <td></td>
	 * <td>specified character encoding of the interface</td>
	 * </tr>
	 * </table>
	 * </dl>
	 * 
	 * @param xbusSystem XBUSSystem object which consists of two informations:
	 *            <br>
	 *            &nbsp;&nbsp;-the name of the system is used to identify the
	 *            system <br>
	 *            &nbsp;&nbsp;-additionally an adress might be available.
	 * @exception XException if any error occurs
	 */
	private void readConfiguration(XBUSSystem xbusSystem) throws XException
	{
		try
		{
			mConfiguration = new FileSenderConfiguration(xbusSystem);
			// The original file names.
			String[] fileNames = mConfiguration.getFileNames();
			// The temporay file names used during file creation.
			mTempFilename = new String[fileNames.length];
			// The backup file names.
			mBackupFilename = new String[fileNames.length];
			for (int fileNo = 0; fileNo < fileNames.length; fileNo++)
			{
				mTempFilename[fileNo] = fileNames[fileNo]
						+ Constants.TEMP_SUFFIX;
				mBackupFilename[fileNo] = fileNames[fileNo]
						+ Constants.getDateAsString();
			} // for (int fileNo=0; fileNo<fileNames.length; fileNo++)
		}
		catch (Exception ex)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_FILE, "0", ex);
		}
	}

	/**
	 * Implemented method <code>execute</code> from TextSender sends the given
	 * string <i>callData </i> to the neighbor-system. Three actions must be
	 * taking into account when a file already exists:
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
	public String execute(String function, String callData) throws XException
	{
		senderExecuted = true;

		if (callData == null)
			callData = "";

		BufferedWriter buffOut = null;

		// The file name to write to finnally.
		// Only several ones for broadcasting.
		// Otherwise only the first array element is filled.
		String[] fileNames = mConfiguration.getFileNames();

		for (int fileNo = 0; fileNo < fileNames.length; fileNo++)
		{ // Loop over destination files.
			checkFilePermissions(fileNames[fileNo]);

			buffOut = prepareWriter(fileNames[fileNo], fileNo);

			// write callData into the file.
			// Characters written to it are translated into bytes
			// according to a specified character encoding.
			try
			{
				buffOut.write(callData);
				// Writing an end of file sign on Unix systems
				if (Constants.LINE_SEPERATOR.equals("\n")
						&& (callData.length() == 0 || callData.charAt(callData
								.length() - 1) != '\n'))
					buffOut.newLine();
				buffOut.close();
			}
			catch (IOException e)
			{
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_FILE, "0", e);
			}
		}
		return null;
	}

	protected BufferedWriter prepareWriter(String fileName, int fileNo)
			throws XException
	{
		prepareWriteFile(fileName, fileNo);
		BufferedWriter buffOut;
		try
		{
			buffOut = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(mTempFilename[fileNo], true),
					mConfiguration.getEncoding()));
		}
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_FILE, "0", e);
		}
		return buffOut;
	}

	protected void prepareWriteFile(String fileName, int fileNo)
			throws XException
	{
		if (new File(mTempFilename[fileNo]).exists())
		{
			deleteFile(mTempFilename[fileNo]);
		}

		if (new File(fileName).exists())
		{
			renameFile(fileName, mBackupFilename[fileNo]);

			// copy existing file if it exist and append is allowed
			if (mConfiguration.getResolution().equals(Constants.WRITE_APPEND))
			{
				copyFile(mBackupFilename[fileNo], mTempFilename[fileNo]);
				if (getFileLength(mTempFilename[fileNo]) > 0
						&& getLastByteOfFile(mTempFilename[fileNo]) != 10)
				{
					try
					{
						BufferedOutputStream buffOut = new BufferedOutputStream(
								new FileOutputStream(mTempFilename[fileNo],
										true));
						buffOut.write(Constants.LINE_SEPERATOR
								.getBytes(mConfiguration.getEncoding()));
						buffOut.close();
					}
					catch (Exception e)
					{
						throw new XException(Constants.LOCATION_EXTERN,
								Constants.LAYER_TECHNICAL,
								Constants.PACKAGE_TECHNICAL_FILE, "0", e);
					}
				}
			}
		}
		else
		{
			mBackupFilename[fileNo] = null;
		}
	}

	/**
	 * On the basis of the ConflictResolution (resolved action when the file
	 * already exists) having read from the standard configuration, checks this
	 * method a file on an opportunity to meet the requirements
	 * <p>
	 * <table border>
	 * <tr>
	 * <th>Resolution</th>
	 * <th>Requirements</th>
	 * </tr>
	 * <tr>
	 * <td>Error</td>
	 * <td>the physical file may not exist</td>
	 * </tr>
	 * <tr>
	 * <td>Overwrite, Append</td>
	 * <td>1. the file is a normal file (not a directory)<br>
	 * 2. the application can modify this file</td>
	 * </tr>
	 * </table border>
	 * 
	 * @param fileName the file path
	 * @return true if the file meets the requirements of ConflictResolution,
	 *         otherwise false
	 * @exception XException if any error occurs
	 */
	private void checkFilePermissions(String fileName) throws XException
	{
		File srcFile = new File(fileName);
		// constructing the file object doesn't create a file on disk!

		if (srcFile.exists())
		{
			// 1.check if resolution = error
			if (mConfiguration.getResolution().equals(Constants.WRITE_ERROR))
			{
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_FILE, "29");
			}

			// 2. check if it is one file
			if (!srcFile.isFile())
			{
				List params = new Vector();
				params.add(mConfiguration.getFileNames());
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_FILE, "30", params);
			}

			// 3. check write permissions
			if (!srcFile.canWrite())
			{
				List params = new Vector();
				params.add(fileName);
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_FILE, "31", params);
			}
		}
	}

	/**
	 * Implemented method <code>commit</code> from TAResource interface. The
	 * purpose of commit actions is to remove any backup information that had
	 * been created during process (tansaction).
	 * <p>
	 * Undepending on the ConflictResolution, the following acts commit all
	 * actions.
	 * <ol>
	 * <li>remove resorce from the TAManager
	 * <li>rename temporary file to the current file
	 * <li>delete backup copy if exist
	 * </ol>
	 * 
	 * @see net.sf.xbus.base.core.TAResource#commit()
	 * @exception XException if any error occurs
	 */
	public void commit() throws XException
	{
		TAManager.getInstance().removeResource(this);

		if (senderExecuted)
		{
			// The destination files.
			// Up to now the transferred data is still in the tempoirary files.
			String[] fileNames = mConfiguration.getFileNames();
			for (int fileNo = 0; fileNo < fileNames.length; fileNo++)
			{
				// rename temp file to original
				renameFile(mTempFilename[fileNo], fileNames[fileNo]);

				// delete backup file if it exists
				if ((mBackupFilename[fileNo] != null)
						&& (!mConfiguration.getResolution().equals(
								Constants.WRITE_RENAME)))
				{
					deleteFile(mBackupFilename[fileNo]);
				} // if ((mBackupFilename[fileNo] != null) &&
					// (!mConfiguration.getResolution().equals(Constants.WRITE_RENAME)))
			} // for (int fileNo=0; fileNo<fileNames.length;fileNo++)
		} // if (senderExecuted)
	} // commit()

	/**
	 * Implemented method <code>rollback</code> from TAResource ignores all
	 * changes have made since the beginning of the process (transaction).
	 * <p>
	 * Undepending on the ConflictResolution, the following acts roll back all
	 * modifications that have been made in the file system associated with this
	 * <code>FileSender</code>:
	 * <ol>
	 * <li>remove resorce from the TAManager
	 * <li>delete temp file
	 * <li>if backup copy exist, then rename it to the original file
	 * </ol>
	 * 
	 * @see net.sf.xbus.base.core.TAResource#rollback()
	 * @exception XException if any error occurs
	 */
	public void rollback() throws XException
	{
		TAManager.getInstance().removeResource(this);

		if (senderExecuted)
		{
			// The destination files.
			// The transferred data is still in the tempoirary files.
			String[] fileNames = mConfiguration.getFileNames();
			for (int fileNo = 0; fileNo < fileNames.length; fileNo++)
			{
				deleteFile(mTempFilename[fileNo]);
				if (mBackupFilename[fileNo] != null
						&& new File(mBackupFilename[fileNo]).exists())
				{
					renameFile(mBackupFilename[fileNo], fileNames[fileNo]);
				} // if (mBackupFilename[fileNo] != null && new
					// File(mBackupFilename[fileNo]).exists())
			} // for (int fileNo=0; fileNo<fileNames.length;fileNo++)
		} // if (senderExecuted)
	} // rollback()

	/**
	 * Is not implemented for file system
	 */
	public void open()
	{} // open()

	/**
	 * Is not implemented for file system
	 */
	public void close()
	{} // close()

	public String getType()
	{
		return Constants.TYPE_TEXT;
	} // getType()

} // FileSender
