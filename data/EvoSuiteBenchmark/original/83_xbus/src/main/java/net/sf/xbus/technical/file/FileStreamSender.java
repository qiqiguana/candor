package net.sf.xbus.technical.file;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.ObjectSender;
import net.sf.xbus.technical.Sender;

/**
 * <code>FileStreamSender</code> gets an <code>InputStream</code> and writes
 * it into a file.
 */
public class FileStreamSender extends FileSender
		implements
			Sender,
			ObjectSender
{
	/**
	 * Stores the given system.
	 * 
	 * @param system where the data shall be send to
	 * @throws XException
	 */
	public FileStreamSender(XBUSSystem system) throws XException
	{
		super(system);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.xbus.technical.ObjectSender#execute(java.lang.String,
	 *      java.lang.Object)
	 */
	public Object execute(String function, Object source) throws XException
	{
		senderExecuted = true;

		InputStream inStream = (InputStream) source;
		BufferedOutputStream buffOut = prepareOutputStream();

		try
		{

			int character = inStream.read();
			int lastChar = -1;
			while (character >= 0)
			{
				buffOut.write(character);
				lastChar = character;
				character = inStream.read();
			} // while (character >= 0)
			// Writing an end of file sign on Unix systems
			if (Constants.LINE_SEPERATOR.equals("\n") && lastChar != '\n')
			{
				buffOut.write('\n');
			}
		}
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_FILE, "0", e);
		}
		finally
		{
			try
			{
				if (buffOut != null)
				{
					buffOut.close();
				}
				if (inStream != null)
				{
					inStream.close();
				}
			}
			catch (IOException e1)
			{
				/*
				 * do nothing
				 */
			}
		}
		return null;
	}

	/**
	 * Opens a stream later used to write the incoming data into the file.
	 * 
	 * @return a <code>BufferedOutputStream</code> for the temporary file
	 * @throws XException if something goes wrong
	 */
	protected BufferedOutputStream prepareOutputStream() throws XException
	{
		prepareWriteFile(mConfiguration.getFileNames()[0], 0);
		BufferedOutputStream buffOut;
		try
		{
			buffOut = new BufferedOutputStream(new FileOutputStream(
					mTempFilename[0], true));
		}
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_FILE, "0", e);
		}
		return buffOut;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.xbus.technical.Sender#getType()
	 */
	public String getType()
	{
		return Constants.TYPE_OBJECT;
	}
}
