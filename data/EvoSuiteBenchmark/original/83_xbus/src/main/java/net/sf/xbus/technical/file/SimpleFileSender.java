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
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.Sender;
import net.sf.xbus.technical.TextSender;

public class SimpleFileSender extends FileBase implements Sender, TextSender
{
	protected FileSenderConfiguration mConfiguration = null;

	/**
	 * Constructs a FileSender object giving all necessary data from the
	 * standard configuration, checking file permissions and registering current
	 * resource by the {@link net.sf.xbus.base.core.TAManager}.
	 * 
	 * @exception XException if any error occurs
	 */
	public SimpleFileSender(XBUSSystem system) throws XException
	{
		mConfiguration = new FileSenderConfiguration(system);
		checkFilePermissions();
	}

	public String execute(String function, String callData) throws XException
	{
		BufferedWriter buffOut = prepareWriter();

		if (callData == null)
		{
			callData = "";
		}

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
		return null;
	}

	protected BufferedWriter prepareWriter() throws XException
	{
		prepareWriteFile();
		BufferedWriter buffOut;
		try
		{
			buffOut = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(mConfiguration
							.getFileNames()[0], true), mConfiguration
							.getEncoding()));
		}
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_FILE, "0", e);
		}
		return buffOut;
	}

	protected void prepareWriteFile() throws XException
	{
		if (new File(mConfiguration.getFileNames()[0]).exists())
		{
			if (mConfiguration.getResolution()
					.equals(Constants.WRITE_OVERWRITE))
			{
				deleteFile(mConfiguration.getFileNames()[0]);
			}

			if (mConfiguration.getResolution().equals(Constants.WRITE_APPEND))
			{
				if (getFileLength(mConfiguration.getFileNames()[0]) > 0
						&& getLastByteOfFile(mConfiguration.getFileNames()[0]) != 10)
				{
					try
					{
						BufferedOutputStream buffOut = new BufferedOutputStream(
								new FileOutputStream(mConfiguration
										.getFileNames()[0], true));
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
	 * <td>the physical file can not be exist</td>
	 * </tr>
	 * <tr>
	 * <td>Overwrite Append</td>
	 * <td>1. the file is a normal file (not a directory) <br>
	 * 2. the application can modify this file</td>
	 * </tr>
	 * </table border>
	 * 
	 * @param config Instanze of the configuration
	 * @param system Sytem name which file resoltion must be read.
	 * @return true if the file meets the requirements of ConflictResolution,
	 *         otherwise false
	 * @exception XException if any errors occurs
	 */

	private void checkFilePermissions() throws XException
	{
		String fileName = mConfiguration.getFileNames()[0];
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

	public String getType()
	{
		return Constants.TYPE_TEXT;
	}
}
