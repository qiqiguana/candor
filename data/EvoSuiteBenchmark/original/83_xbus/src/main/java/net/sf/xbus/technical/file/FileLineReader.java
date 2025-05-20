package net.sf.xbus.technical.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.reflection.ReflectionSupport;
import net.sf.xbus.base.linereader.LineReader;
import net.sf.xbus.base.linereader.LineTransformer;
import net.sf.xbus.base.xbussystem.XBUSSystem;

/**
 * <code>LineReader</code> to read/send a character stream from/to a file.
 */
public class FileLineReader extends BufferedReader implements LineReader
{
	private LineTransformer mTransformer = null;

	public FileLineReader(File sourceFile, String encoding)
			throws UnsupportedEncodingException, FileNotFoundException
	{
		super(new InputStreamReader(new FileInputStream(sourceFile), encoding));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.xbus.technical.LineReader#prepareReading()
	 */
	public void prepareReading(XBUSSystem destination) throws XException
	{
		/*
		 * Check if lines shall be transformed before writing
		 */
		String stringTransformer = Configuration.getInstance()
				.getValueOptional("System", destination.getName(),
						"StringTransformer");
		if (stringTransformer != null)
		{
			String transformerClassName = Configuration.getClass(
					"StringTransformer", stringTransformer);
			mTransformer = (LineTransformer) ReflectionSupport
					.createObject(transformerClassName);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.xbus.technical.LineReader#readRecord()
	 */
	public String readRecord() throws XException
	{
		String line = null;

		try
		{
			line = readLine();
		}
		catch (IOException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_FILE, "0", e);
		}

		if ((mTransformer != null) && (line != null))
		{
			line = mTransformer.transform(line);
		}

		return line;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.xbus.technical.LineReader#terminateReading()
	 */
	public void terminateReading() throws XException
	{
		try
		{
			close();
		}
		catch (IOException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_FILE, "0", e);
		}
	}

}
