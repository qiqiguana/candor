package net.sf.xbus.technical.file;

import java.io.BufferedWriter;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.linereader.LineReader;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.ObjectSender;
import net.sf.xbus.technical.Sender;

/**
 * <code></code>
 * 
 * @author düwel
 */
public class FileLineWriterSender extends FileSender
		implements
			Sender,
			ObjectSender
{
	private XBUSSystem mDestination = null;

	/**
	 * Constructor for FileLineWriterSender.
	 * 
	 * @param system
	 * @throws XException
	 */
	public FileLineWriterSender(XBUSSystem system) throws XException
	{
		super(system);
		mDestination = system;
	}

	public Object execute(String function, Object source) throws XException
	{
		senderExecuted = true;

		LineReader reader = null;
		BufferedWriter buffOut = null;
		try
		{
			reader = (LineReader) source;
			reader.prepareReading(mDestination);
			buffOut = prepareWriter(mConfiguration.getFileNames()[0], 0);

			String record = reader.readRecord();
			String lastRecord = "";

			if (record != null)
			{
				buffOut.write(record);
				lastRecord = record;
				record = reader.readRecord();
			} // while (record != null)
			while (record != null)
			{
				buffOut.write(Constants.LINE_SEPERATOR);
				buffOut.write(record);
				lastRecord = record;
				record = reader.readRecord();
			} // while (record != null)

			// Writing an end of file sign on Unix systems
			if (Constants.LINE_SEPERATOR.equals("\n")
					&& (lastRecord.length() == 0 || lastRecord
							.charAt(lastRecord.length() - 1) != '\n'))
				buffOut.newLine();
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
				if (reader != null)
				{
					reader.terminateReading();
				}
				if (buffOut != null)
				{
					buffOut.close();
				}
			}
			catch (Exception e)
			{
				/*
				 * do nothing
				 */
			}
		}
		return null;
	}

	public String getType()
	{
		return Constants.TYPE_OBJECT;
	}
}
