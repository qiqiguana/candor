package net.sf.xbus.technical.as400;

import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.TAManager;
import net.sf.xbus.base.core.TAResource;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.Sender;
import net.sf.xbus.technical.TextSender;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400File;
import com.ibm.as400.access.AS400FileRecordDescription;
import com.ibm.as400.access.CharConverter;
import com.ibm.as400.access.IFSFile;
import com.ibm.as400.access.QSYSObjectPathName;
import com.ibm.as400.access.Record;
import com.ibm.as400.access.RecordFormat;
import com.ibm.as400.access.SequentialFile;

/**
 * <code>AS400AS400FileSender</code> manages writing a file on the iSeries
 * integrated file System.
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
public class AS400FileSender extends AS400FileBase
		implements
			Sender,
			TAResource,
			TextSender
{

	/**
	 * Current QSYSObjectPathName representes an object in the integrated file
	 * system which must be write
	 */
	private QSYSObjectPathName mQSYSobjPathname = null;

	/**
	 * Resolved action when the file already exists<br>
	 * <i>append, overwrite</i> or <i>error</i>
	 */
	private String mResolution = null;

	/**
	 * File encoding
	 */
	private String mEncoding = null;

	/**
	 * AS400 object that represents the iSeries system
	 */
	private AS400 mAS400System = null;

	/**
	 * Represents an AS/400 physical or logical file. Allows the user to use
	 * commitment control when accessing an AS/400 file and access the records
	 * in an AS/400 file sequentially or by record number.
	 */
	protected SequentialFile mOriginFile = null;

	/**
	 * Constructs a AS400FileSender object giving all necessary data from the
	 * standard configuration, connecting to the iSeries and registering current
	 * resource by the {@link net.sf.xbus.base.core.TAManager}.
	 * 
	 * @see AS400Connection
	 * @exception XException - If any error occurs
	 */
	public AS400FileSender(XBUSSystem system) throws XException
	{

		Configuration config = Configuration.getInstance();
		String as400name = config.getValue(Constants.CHAPTER_SYSTEM, system
				.getName(), "AS400");

		// create an AS400 object for the server that holds the files
		// and connection to the iSeries
		AS400Connection connection = AS400Connection.getInstance(as400name);
		mAS400System = connection.getSystem();

		// readConfiguration
		readConfiguration(system);

		// register resource by TAManager
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
	 * <td>path name that represents the object to be write in the QSYS library</td>
	 * </tr>
	 * <tr>
	 * <td><code>ConflictResolution</code></td>
	 * <td></td>
	 * <td>resolved action when the file already exists<br>
	 * <i>append, overwrite, error</i></td>
	 * </tr>
	 * <tr>
	 * <td><code>Encoding</code></td>
	 * <td></td>
	 * <td>specified character encoding of the interface</td>
	 * </tr>
	 * </table>
	 * </dl>
	 * 
	 * @param xbusSystem XBUSSystem object which consists of two informations:<br>
	 *            &nbsp;&nbsp;-the name of the system is used to identify the
	 *            system<br>
	 *            &nbsp;&nbsp;-additionally an adress might be available.
	 * @exception XException if any error occurs
	 */
	private void readConfiguration(XBUSSystem system) throws XException
	{
		try
		{
			Configuration config = Configuration.getInstance();
			mResolution = getResolution(config, system.getName());
			mEncoding = getEncoding(mAS400System, system.getName());
			String filename = config.getValue(Constants.CHAPTER_SYSTEM, system
					.getName(), Constants.KEY_FILENAME);
			filename = system.replaceAllMarkers(filename)[0];
			mQSYSobjPathname = new QSYSObjectPathName(filename);
		}
		catch (Exception ex)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "0", ex);
		}
	}

	/**
	 * Implemented method <code>execute</code> from TextSender sends the given
	 * string <i>callData</i> to the AS400-system. Three actions must be taking
	 * into account when a file already exists:
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
	public String execute(String function, String callData) throws XException
	{
		RecordFormat format = prepareWriting();

		if (callData == null)
			callData = "";

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
	private void writeData(String callData, SequentialFile file,
			RecordFormat format)
	{
		byte[] dataByte = null;

		try
		{
			// open original file for each resolution.
			mOriginFile.open(AS400File.READ_WRITE, 0,
					AS400File.COMMIT_LOCK_LEVEL_DEFAULT);

			CharConverter charConverter = new CharConverter(mEncoding);
			StringTokenizer tokenizer = new StringTokenizer(callData,
					Constants.LINE_SEPERATOR);

			while (tokenizer.hasMoreTokens())
			{
				String token = tokenizer.nextToken();
				// Characters written to it are translated into bytes array
				// according to a specified character encoding.
				dataByte = charConverter.stringToByteArray(token);
				// Save byteArray into Record based on the file format
				Record record = format.getNewRecord(dataByte);
				// write Record into file
				file.write(record);
			}
			mOriginFile.close();
		}
		catch (Exception ex)
		{}

	}

	public RecordFormat prepareWriting() throws XException
	{
		try
		{
			// Write file ONLY if file exist because the format of the file is
			// needed!
			if (new IFSFile(mAS400System, mQSYSobjPathname.getPath()).exists())
			{
				// initiate the SequentialFile object
				mOriginFile = new SequentialFile(mAS400System, mQSYSobjPathname
						.getPath());

				// Retreive and set an record format of the file.
				AS400FileRecordDescription recordDescription = new AS400FileRecordDescription(
						mAS400System, mQSYSobjPathname.getPath());
				RecordFormat[] formatArr = recordDescription
						.retrieveRecordFormat();
				RecordFormat format = formatArr[0];

				mOriginFile.setRecordFormat(format);

				if (mResolution.equals(Constants.WRITE_OVERWRITE))
				{
					deleteMember(mAS400System, mQSYSobjPathname.getPath());
					mOriginFile.addPhysicalFileMember(mQSYSobjPathname
							.getMemberName(), "");
				}
				return format;

			} // if (new IFSFile(mAS400System,
				// mQSYSobjPathname.getPath()).exists())
			else
			{
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_AS400, "28");
			}
		}
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "0", e);
		}
	}

	/**
	 * Reads conflict resolution (resolved action when the file already exists)
	 * for the given system name from the configuration and checks its
	 * conformity with the allowed ones:
	 * <dl>
	 * <dd>Append
	 * <dd>Overwrite
	 * <dd>Error
	 * </dl>
	 * 
	 * @param config Instanze of the configuration
	 * @param system Sytem name which resoltion must be read.
	 * @return conflict resolution as String (Append, Overwrite or Error)
	 * @exception XException if resolution is falsh or any errors occurs
	 */
	public String getResolution(Configuration config, String system)
			throws XException
	{
		String resolution = config.getValue(Constants.CHAPTER_SYSTEM, system,
				Constants.KEY_SEND_RESOL);
		if (!resolution.equals(Constants.WRITE_APPEND)
				&& !resolution.equals(Constants.WRITE_ERROR)
				&& !resolution.equals(Constants.WRITE_OVERWRITE))
		{
			List params = new Vector();
			params.add(mResolution);
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "30", params);
		}
		return resolution;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.xbus.base.core.TAResource#commit()
	 */
	public void commit()
	{
		// remove resource
		TAManager.getInstance().removeResource(this);

		// disconnect
		mAS400System.disconnectAllServices();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.xbus.base.core.TAResource#rollback()
	 */
	public void rollback()
	{
		// remove resource
		TAManager.getInstance().removeResource(this);

		// disconnect
		mAS400System.disconnectAllServices();
	}

	/**
	 * Is not implemented for iSeries integrated file system
	 */
	public void open()
	{}

	/**
	 * Is not implemented for iSeries integrated file system
	 */
	public void close()
	{}

	public String getType()
	{
		return Constants.TYPE_TEXT;
	}
}
