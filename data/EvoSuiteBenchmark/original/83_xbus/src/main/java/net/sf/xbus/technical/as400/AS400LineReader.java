package net.sf.xbus.technical.as400;

import java.io.UnsupportedEncodingException;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.reflection.ReflectionSupport;
import net.sf.xbus.base.linereader.LineReader;
import net.sf.xbus.base.linereader.LineTransformer;
import net.sf.xbus.base.xbussystem.XBUSSystem;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400File;
import com.ibm.as400.access.AS400FileRecordDescription;
import com.ibm.as400.access.CharConverter;
import com.ibm.as400.access.QSYSObjectPathName;
import com.ibm.as400.access.Record;
import com.ibm.as400.access.RecordFormat;
import com.ibm.as400.access.SequentialFile;

/**
 * <code>LineReader</code> to read/send a character stream from/to an AS/400.
 */
public class AS400LineReader implements LineReader
{
	private AS400 mAS400System;
	private QSYSObjectPathName mQSYSObject;
	private SequentialFile mOriginFile;
	private CharConverter mCharConverter;
	private LineTransformer mTransformer = null;

	/**
	 * Constructor an AS400LineReader.
	 * 
	 * @param aS400System reference to the AS400
	 * @param qSYSObject ???
	 * @param originFile
	 * @param encoding
	 * @throws XException if something goes wrong
	 */
	public AS400LineReader(AS400 aS400System, QSYSObjectPathName qSYSObject,
			SequentialFile originFile, String encoding) throws XException
	{
		mAS400System = aS400System;
		mQSYSObject = qSYSObject;
		mOriginFile = originFile;
		try
		{
			mCharConverter = new CharConverter(encoding);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "0", e);
		}
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

		AS400FileRecordDescription recordDescription = new AS400FileRecordDescription(
				mAS400System, mQSYSObject.getPath());
		// Set record format of the file.
		RecordFormat[] format;
		try
		{
			format = recordDescription.retrieveRecordFormat();
			mOriginFile.setRecordFormat(format[0]);
			// Open the file.
			mOriginFile.open(AS400File.READ_WRITE, 0,
					AS400File.COMMIT_LOCK_LEVEL_NONE);
		}
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "0", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.xbus.technical.LineReader#readRecord()
	 */
	public String readRecord() throws XException
	{
		String result = null;
		Record record;
		try
		{
			record = mOriginFile.readNext();
		}
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "0", e);
		}
		if (record != null)
		{
			byte[] inData = null;
			// save record contents to the byte array
			try
			{
				inData = record.getContents();
			}
			catch (Exception e)
			{
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_AS400, "0", e);
			}
			// convert this content to the String
			result = mCharConverter.byteArrayToString(inData);

			if ((mTransformer != null) && (result != null))
			{
				result = mTransformer.transform(result);
			}

		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.xbus.technical.LineReader#terminateReading()
	 */
	public void terminateReading() throws XException
	{
		// close the file since I am done using it
		try
		{
			mOriginFile.close();
		}
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "0", e);
		}
	}

}
