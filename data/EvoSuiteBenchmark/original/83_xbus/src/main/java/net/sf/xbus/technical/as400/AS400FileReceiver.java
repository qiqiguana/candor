package net.sf.xbus.technical.as400;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import net.sf.xbus.application.Adapter;
import net.sf.xbus.application.PostProcessor;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.TAManager;
import net.sf.xbus.base.core.TAResource;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.trace.Trace;
import net.sf.xbus.base.notifyerror.NotifyError;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.Receiver;
import net.sf.xbus.technical.ReceiverSingleInterface;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400File;
import com.ibm.as400.access.AS400FileRecordDescription;
import com.ibm.as400.access.CharConverter;
import com.ibm.as400.access.IFSFile;
import com.ibm.as400.access.ProgramParameter;
import com.ibm.as400.access.QSYSObjectPathName;
import com.ibm.as400.access.Record;
import com.ibm.as400.access.RecordFormat;
import com.ibm.as400.access.SequentialFile;

/**
 * The <code>AS400FileReceiver</code> receives a request, reads the files on
 * the AS400 machines into tstrings and calls the application layer.
 * 
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
 * QSYS library file system on the AS400 machine</td>
 * </tr>
 * <tr>
 * <td>System</td>
 * <td>Interface</td>
 * <td>FinalResolution</td>
 * <td>Three actions are possible with the file after processing: <br>
 * <b><i>preserve, rename</i></b> or<b><i> delete</i></b></td>
 * </tr>
 * <tr>
 * <td>System</td>
 * <td>Interface</td>
 * <td>OnError</td>
 * <td>Three actions are be possible with the file when an unrecoverable error
 * has occured: <br>
 * <b><i>preserve, rename</i></b> or<b><i> delete</i></b></td>
 * </tr>
 * <tr>
 * <td>System</td>
 * <td>Interface</td>
 * <td>Encoding</td>
 * <td>Specified character encoding of the interface<i> (Optional)</i></td>
 * </tr>
 * <tr>
 * <td>Base</td>
 * <td>ReceiverTimeout</td>
 * <td>Seconds</td>
 * <td>Global timeout value for AS400FileReceiver.<br>
 * If any error occurs the AS400FileReceiver tries again in this time.</td>
 * </tr>
 * <tr>
 * <td>Base</td>
 * <td>Retry</td>
 * <td>Count</td>
 * <td>How many additional attempts are started after an error occured?</td>
 * </tr>
 * </table>
 */
public class AS400FileReceiver extends AS400FileBase
		implements
			Receiver,
			TAResource,
			ReceiverSingleInterface
{

	/**
	 * AS400 object that represents the iSeries system that contains the file
	 */
	protected AS400 mAS400System = null;

	/**
	 * Path name that represents an object in the QSYS library file system from
	 * the standard configuration.<br>
	 * May contain placeholders for informations on the address
	 */
	private String mConfigFilename = null;

	/**
	 * Path name that represents the copy from the original object in the QSYS
	 * library file system<br>
	 * which Member is renamed to the current date.
	 */
	private String mRenamename = null;

	/**
	 * Represents an AS/400 physical or logical file. Allows the user to use
	 * commitment control when accessing an AS/400 file and access the records
	 * in an AS/400 file sequentially or by record number.
	 */
	protected SequentialFile mOriginFile = null;

	/**
	 * QSYSObjectPathName represents an object in the integrated file system
	 */
	protected QSYSObjectPathName mQSYSObject = null;

	/**
	 * File encoding
	 */
	protected String mEncoding = null;

	/**
	 * Resolved action with the file after that it was read<br>
	 * <i>preserve, rename</i> or <i>delete</i>
	 */
	private String mResolution = null;

	/**
	 * In case of <code>mResolution=CallProgram</code> this path will be used
	 * for the program call.
	 */
	private String mAS400TerminatorProgram;

	/**
	 * Type of action after an error occured.
	 */
	private String mOnError = null;

	/**
	 * Number of retries after an error in first attempt.
	 */
	private int mRetryCount = 0;

	/**
	 * Delay between retries in seconds.
	 */
	private int mTimeout = 0;

	/**
	 * Timeout for stopping program call
	 */
	private int mProgramTimeout = 0;

	/**
	 * This method manages the receiving of message for one neighbor system.
	 * <p>
	 * The system is specified by its name. Due to wildcards in this name it may
	 * really describe serveral interfaces. All these will be processed. Thus,
	 * first the list of {@link net.sf.xbus.base.xbussystem.XBUSSystem}s for
	 * the given system name is determined by replacing the wildcards.
	 * <p>
	 * After that for each XBUSSystem the {@link #doReceive(XBUSSystem)} method
	 * is invoked, where requested interface file(s) is (are) read out into a
	 * character string. The XBUSSystem and the string are passed to the
	 * application layer.
	 * <p>
	 * <b><i>Note:</i></b><br>
	 * The retry mechanism in this method first attempts processing <b><u>each</u></b>
	 * XBUSSystem. Those which cause errors are put into a list and this list is
	 * the base for the next retry.
	 * 
	 * @param systemName name of the system to receive data from
	 * @throws XException if something goes wrong
	 */
	public void receive(String systemName)
	{
		try
		{
			// file name, encoding and resolution for this system
			// are read from the configuration and saved into class variables
			readConfiguration(systemName);

			mAS400System.connectService(AS400.FILE);

			// AS400Receiver will be registered by TAManager
			TAManager taManager = TAManager.getInstance();
			taManager.clearManager();
			taManager.registerResource(this);
			taManager.begin();

			// determine the list of XBUSSystems for the system name
			List systems = XBUSSystem.getSystems(systemName, mConfigFilename);
			// for failed transmissions
			List errorSystems = null;

			// for counting the attempts
			int i = 0;
			// while loop for the attemps to process the interfaces
			while ((!systems.isEmpty()) && (i < mRetryCount + 1))
			{ // still some systems to process and number of retires nor yet
				// reached
				// time out before retries
				if (i > 0)
					Thread.sleep(mTimeout * 1000);

				// empty list for failed interfaces
				errorSystems = new Vector();

				// for loop iterates over the all XBUSSystems and tries to
				// receive the interface data
				for (Iterator it = systems.iterator(); it.hasNext();)
				{ // iterating operation in first body instruction:
					XBUSSystem xbusSystem = (XBUSSystem) it.next();
					if (i == 0)
					{ // first attempt
						Trace.info("Start processing "
								+ xbusSystem.getCompleteName());
					} // then (i == 0)
					else
					{ // a retry
						Trace.info("Retry number " + i + ": "
								+ xbusSystem.getCompleteName());
					} // else (i == 0)
					if (doReceive(xbusSystem))
					{ // successfully received
						taManager.commit();

						PostProcessor.start(xbusSystem, null,
								Constants.POSTPROCESSING_PERSYSTEM);

						Trace.info("End processing "
								+ xbusSystem.getCompleteName());
						Trace.info("----------------------------------------");
					} // then (receive(xbusSystem))
					else
					{ // An error occured during data transmission
						// but the resulting exception was already catched.
						taManager.rollback();
						Trace.info("Error while processing "
								+ xbusSystem.getCompleteName());
						Trace.info("----------------------------------------");
						errorSystems.add(xbusSystem);
					} // else (receive(xbusSystem))
				} // for (Iterator it = systems.iterator(); it.hasNext();)
				// In next attempt only try the failed interfaces.
				systems = errorSystems;
				// One attempt more done.
				i++;
			} // while ((!systems.isEmpty()) && (i < mRetryCount + 1))

			if (!systems.isEmpty())
			{ // Even after retrying some systems were not successfully
				// processed.
				Trace.error("Some systems couldn't be processed.");

				// Falied interfaces list for error message.
				Hashtable additionalInfo = new Hashtable();
				// Counting the failed interfaces.
				i = 1;
				XBUSSystem system;
				// loop over al failed interfaces
				for (Iterator it = systems.iterator(); it.hasNext();)
				{ // iterating operation in first body instruction:
					system = (XBUSSystem) it.next();

					additionalInfo.put("System" + i, system.getCompleteName());

					// Perform cleaning operations according to configuration.
					try
					{
						if (mOnError.equals(Constants.READ_DELETE)
								|| mOnError.equals(Constants.READ_DELETEFILE))
						{
							deleteFile(mAS400System, system
									.replaceAllMarkers(mConfigFilename)[0]);
						} // delete
						else if (mOnError.equals(Constants.READ_DELETEMEMBER))
						{
							deleteMember(mAS400System, system
									.replaceAllMarkers(mConfigFilename)[0]);
						} // delete member
						else if (mOnError.equals(Constants.READ_RENAME))
						{
							renameFile(
									mAS400System,
									system.replaceAllMarkers(mConfigFilename)[0],
									system.replaceAllMarkers(mConfigFilename)[0]
											+ Constants.getDateAsString());
						} // rename
						// Nothing to do for "Preserve".
					} // try
					catch (XException e)
					{
						Trace.error(e);
					} // catch
					// Next system.
					i++;
				} // for (Iterator it = systems.iterator(); it.hasNext();)
				NotifyError.notifyError(this, new XBUSSystem(systemName),
						"Some interfaces couldn't be processed", null,
						additionalInfo);
			} // if (!systems.isEmpty())
			else
				PostProcessor.start(new XBUSSystem(systemName), null,
						Constants.POSTPROCESSING_FINAL);
		} // try
		catch (Throwable e)
		{
			try
			{
				NotifyError.notifyError(this, new XBUSSystem(systemName), e
						.getMessage(), null, null);
			}
			catch (XException e1)
			{
				// do nothing
			}
		} // catch
		finally
		{
			TAManager.getInstance().close();
			// remove from the resources
			TAManager.getInstance().removeResource(this);
			// disconnect from the all services at the iSeries
			mAS400System.disconnectAllServices();
		} // finally
	} // doReceive(String systemName)

	/**
	 * Manages the process of receipt for a specifc XBUSSystem. This method is
	 * provided to do the following:
	 * <ol>
	 * <li> replace wildcards in the filename with the dealer information
	 * <li> create copy and backup files, depending on the resolution
	 * <li> read file into String
	 * <li> call application layer
	 * <li> determine the result in sense of success/no success
	 * </ol>
	 * 
	 * @param xbusSystem XBUSSystem object with information of this
	 *            neighbor-system to be read
	 * @exception XException if any errors is occurs which is not treated in the
	 *                application layer
	 */
	protected boolean doReceive(XBUSSystem xbusSystem)
	{
		boolean successful = false;

		try
		{
			// The name of the interface file may contain placeholders for
			// informations
			// on the address.
			// In replaceMarker() method these placeholders will be replaced
			// with their
			// actual values for the address of the neighbor-system.
			// If no address address information is available, the text will be
			// returned
			// without modifications.
			// Create an object in the integrated file system for the file name.
			// It is used to parse an integrated file system name into its
			// components.
			mQSYSObject = new QSYSObjectPathName(xbusSystem
					.replaceAllMarkers(mConfigFilename)[0]);
			IFSFile ifsFile = new IFSFile(mAS400System, mQSYSObject.getPath());

			// only if the file exist and it is accessible.
			if (ifsFile.exists())
			{
				Trace.info("Receiving data from "
						+ xbusSystem.replaceAllMarkers(mConfigFilename)[0]);

				// initiate the SequentialFile object
				mOriginFile = new SequentialFile(mAS400System, mQSYSObject
						.getPath());

				// lock original file with the WRITE_EXCLUSIVE_LOCK lock type
				mOriginFile.lock(SequentialFile.WRITE_EXCLUSIVE_LOCK);

				Object requestContent = getRequestContent();

				// if OnEmpty is Ignore end this method
				if (requestContent == null)
				{
					rollback();
					return true;
				}
				else
				{
					// call application layer
					Adapter adapter = new Adapter();
					adapter.callApplication(xbusSystem, requestContent,
							getType());

					// check the Adapter return code
					// in case of not success, throw new XException
					if (Constants.RC_OK.equals(adapter.getReturncode()))
					{
						successful = true;
					}
				}
			}
			else
			{

				successful = true;
			}

		}
		catch (Throwable t)
		{
			// Something unexpected has happened.
			Trace.error(t);
			successful = false;
		}

		return successful;

	}

	/**
	 * <code>getRequestContent</code> delivers the content of the request.
	 * Subclasses may define their own request content.
	 * 
	 * @return the interface file content as <code>String</code> but casted to
	 *         <code>Object</code> for compliance reasons
	 * @exception XException if file can not be read or any I/O error occurs
	 */
	protected Object getRequestContent() throws XException
	{
		// read original file
		return readFile();
	} // getRequestContent()

	/**
	 * Reads the content of the integrated file system object specified by its
	 * path name into a string.
	 * <p>
	 * <b><i>Note: </i></b> We use the record-level access classes for
	 * reading.<br>
	 * Contents of each record first are read out in a byte array of AS400 data
	 * and then converted then to a character string with the specified
	 * character encoding.
	 * 
	 * @return file contents as String (with line breaks as recordSeparators)
	 * @exception XException if file can not be read or any I/O error occurs
	 */
	private String readFile() throws XException
	{
		StringBuffer retBuffer = new StringBuffer();

		AS400FileRecordDescription recordDescription = new AS400FileRecordDescription(
				mAS400System, mQSYSObject.getPath());

		try
		{
			// Set record format of the file.
			RecordFormat[] format = recordDescription.retrieveRecordFormat();
			mOriginFile.setRecordFormat(format[0]);

			// Open the file.
			mOriginFile.open(AS400File.READ_WRITE, 0,
					AS400File.COMMIT_LOCK_LEVEL_NONE);

			// Read all records in the file into
			// a string - each one separated by the Constants.LINE_SEPARATOR
			Record record = mOriginFile.readNext();

			if (record != null)
			{ // at least one record
				byte[] inData = null;

				// save record contents to the byte array
				inData = record.getContents();

				// convert this content to the String
				mConverter = new CharConverter(mEncoding);
				retBuffer.append(mConverter.byteArrayToString(inData));

				while ((record = mOriginFile.readNext()) != null)
				{
					// read records while they exist
					retBuffer.append(Constants.LINE_SEPERATOR);
					inData = record.getContents();
					retBuffer.append(mConverter.byteArrayToString(inData));
				} // while ((record = mOriginFile.readNext()) != null)
			} // if (record != null)
			// close the file since I am done
			mOriginFile.close();
		} // try
		catch (Exception thr)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "0", thr);
		} // catch
		return retBuffer.toString();
	} // readFile()

	/**
	 * Reads standard configuration and stores following data in the class
	 * variables.
	 * <p>
	 * <dl>
	 * <dd><table>
	 * <tr>
	 * <td><code>Filename</code></td>
	 * <td></td>
	 * <td>path name that represents an object in the QSYS library file system</td>
	 * </tr>
	 * <tr>
	 * <td><code>Resolution actions</code></td>
	 * <td></td>
	 * <td>actions to be taken in case of successfull transmission, error and
	 * empty source file</td>
	 * </tr>
	 * <tr>
	 * <td><code>Encoding</code></td>
	 * <td></td>
	 * <td>specified character encoding of the interface</td>
	 * </tr>
	 * </table>
	 * </dl>
	 * 
	 * @see #getResolution(Configuration, String)
	 * @see AS400FileBase#getEncoding(AS400, String)
	 * @param system System name to be read
	 * @exception XException if the configuartion is not available or a
	 *                mandatory key cannot be read
	 */
	public void readConfiguration(String system) throws XException
	{
		Configuration config = Configuration.getInstance();

		// create an AS400 object for the server that holds the files
		// and connection to the iSeries
		AS400Connection connection = AS400Connection.getInstance(config
				.getValue(Constants.CHAPTER_SYSTEM, system, "AS400"));
		mAS400System = connection.getSystem();

		mResolution = getResolution(config, system);
		mOnError = getOnError(config, system);
		mConfigFilename = config.getValue(Constants.CHAPTER_SYSTEM, system,
				Constants.KEY_FILENAME);
		mEncoding = getEncoding(mAS400System, system);
		mAS400TerminatorProgram = config.getValueOptional(
				Constants.CHAPTER_SYSTEM, system, "AS400Program.Terminator");
		mRetryCount = Configuration.getInstance().getValueAsInt(
				Constants.CHAPTER_BASE, "Retry", "Count");
		if (mRetryCount > 0)
			mTimeout = config.getValueAsInt(Constants.CHAPTER_BASE,
					"ReceiverTimeout", "Seconds");

		mProgramTimeout = config.getValueAsIntOptional(
				Constants.CHAPTER_SYSTEM, system, "ProgramTimeout") * 1000;
		if (mProgramTimeout == 0)
		{
			mProgramTimeout = Integer.MAX_VALUE;
		}
	} // readConfiguration(String system)

	/**
	 * Determines the final resolution (action after successful file processing)
	 * for the given system name from the standard configuration and checks its
	 * conformity with the allowed ones:
	 * <p>
	 * <dl>
	 * <dd><code>Preserve</code>&nbsp;-the source file remains without
	 * modifications
	 * <dd><code>Rename</code>&nbsp;&nbsp;&nbsp;- the source file member is
	 * renamed
	 * <dd><code>Delete</code> or <code>DeleteFile</code>&nbsp;&nbsp;&nbsp;-
	 * the source file is deleted
	 * <dd><code>DeleteMember</code>&nbsp;&nbsp;&nbsp;- the source file
	 * member is deleted
	 * <dd><code>CallProgram</code>&nbsp;&nbsp;&nbsp;- a parameterless
	 * program is called on the AS400, its path is given in the configuration
	 * </dl>
	 * 
	 * @param config Instance of the
	 *            {@link net.sf.xbus.base.core.config.Configuration}
	 * @param system Sytem name for which resolution must be read.
	 * @return final resolution as String (Preserve, Rename, Delete, DeleteFile,
	 *         DeleteMember or CallProgram)
	 * @exception XException if resolution value is not configured or not known
	 */
	public String getResolution(Configuration config, String system)
			throws XException
	{
		String resolution = config.getValue(Constants.CHAPTER_SYSTEM, system,
				Constants.KEY_RECEIVE_RESOL);
		if (!resolution.equals(Constants.READ_PRESERVE)
				&& !resolution.equals(Constants.READ_RENAME)
				&& !resolution.equals(Constants.READ_DELETE)
				&& !resolution.equals(Constants.READ_DELETEFILE)
				&& !resolution.equals(Constants.READ_DELETEMEMBER)
				&& !resolution.equals(Constants.READ_CALLPROGRAM))
		{
			List params = new Vector();
			params.add(mResolution);
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "20", params);
		}
		return resolution;
	} // getResolution(Configuration config, String system)

	/**
	 * Determines the action which should happen in case of an error from the
	 * standard configuration and checks its conformity with the allowed ones:
	 * <p>
	 * <dl>
	 * <dd><code>Preserve</code>&nbsp;-the source file remains without
	 * modifications
	 * <dd><code>Rename</code>&nbsp;&nbsp;&nbsp;- the source file member is
	 * renamed
	 * <dd><code>Delete</code> or <code>DeleteFile</code>&nbsp;&nbsp;&nbsp;-
	 * the source file is deleted
	 * <dd><code>DeleteMember</code>&nbsp;&nbsp;&nbsp;- the source file
	 * member is deleted
	 * </dl>
	 * 
	 * @param config Instance of the
	 *            {@link net.sf.xbus.base.core.config.Configuration}
	 * @param system Sytem name which resoltion must be read.
	 * @return error case action as String (Preserve, Rename, Delete,
	 *         DeleteFile, DeleteMember)
	 * @exception XException if the onError key is missing or unknown
	 */
	public String getOnError(Configuration config, String system)
			throws XException
	{
		String onError = config.getValue(Constants.CHAPTER_SYSTEM, system,
				"OnError");
		if (!onError.equals(Constants.READ_PRESERVE)
				&& !onError.equals(Constants.READ_RENAME)
				&& !onError.equals(Constants.READ_DELETE)
				&& !onError.equals(Constants.READ_DELETEMEMBER))
		{
			List params = new Vector();
			params.add(onError);
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "21", params);
		}
		return onError;
	} // getOnError(Configuration config, String system)

	/**
	 * Sets the aS400System in AS400FileReceiver object.
	 * 
	 * @param aS400System The aS400System to set
	 */
	public void setAS400System(AS400 aS400System)
	{
		mAS400System = aS400System;
	} // setAS400System(AS400 aS400System)

	/**
	 * Implemented method <code>commit</code> from the TAResource interface.
	 * The purpose of commit actions is to remove any backup information that
	 * had been created during processing (transaction) in the iSeries
	 * integrated file system. Depending on the Resolution, the following
	 * actions are carried out:
	 * <p>
	 * <table border>
	 * <tr>
	 * <th>Resolution</th>
	 * <th>Action</th>
	 * </tr>
	 * <tr>
	 * <td>Preserve</td>
	 * <td>&nbsp;1. release file lock</td>
	 * </tr>
	 * <tr>
	 * <td>Rename</td>
	 * <td>&nbsp;1. release file lock<br>
	 * &nbsp;2. rename file</td>
	 * </tr>
	 * <tr>
	 * <td>Delete/DeleteFile</td>
	 * <td>&nbsp;1. release file lock<br>
	 * &nbsp;2. delete file</td>
	 * </tr>
	 * <tr>
	 * <td>DeleteMember</td>
	 * <td>&nbsp;1. release file lock<br>
	 * &nbsp;2. delete member</td>
	 * </tr>
	 * <tr>
	 * <td>CallProgram</td>
	 * <td>&nbsp;1. release file lock<br>
	 * &nbsp;2. call the specified terminator program</td>
	 * </tr>
	 * </table>
	 * 
	 * @see net.sf.xbus.base.core.TAResource#commit()
	 * @exception XException if accessing the interface file or perfoming the
	 *                configured action fails
	 */
	public void commit() throws XException
	{
		// release all Locks
		if (mOriginFile.getExplicitLocks().length > 0)
		{
			try
			{
				mOriginFile.releaseExplicitLocks();
			} // try
			catch (Exception ex)
			{
				List params = new Vector();
				params.add(mQSYSObject.getPath());
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_AS400, "6", params);
			} // catch
		} // if (mOriginFile.getExplicitLocks().length > 0)

		// delete file if resolution = Delete/DeleteFile
		if (mResolution.equals(Constants.READ_DELETE)
				|| mResolution.equals(Constants.READ_DELETEFILE))
		{
			try
			{
				mOriginFile.delete();
			} // try
			catch (Exception ex)
			{
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_AS400, "0", ex);
			} // catch
		} // Delete/DeleteFile

		// Delete member if resolution = DelteMember
		if (mResolution.equals(Constants.READ_DELETEMEMBER))
		{
			try
			{
				mOriginFile.deleteMember();
			} // try
			catch (Exception ex)
			{
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_AS400, "0", ex);
			} // catch
		} // DeleteMember

		// Rename member if resolution = rename
		else if (mResolution.equals(Constants.READ_RENAME))
		{
			// Get new path with date as member name.
			mRenamename = getRenamedMember(new QSYSObjectPathName(mQSYSObject
					.getPath()), "SV" + Constants.getAS400DateFormat());
			// Execute renaming
			renameFile(mAS400System, mQSYSObject.getPath(), mRenamename);
		} // Rename

		// Call "Terminator" program if resolution = CallProgram
		else if (mResolution.equals(Constants.READ_CALLPROGRAM))
		{
			// Check for program path read from the configuration before.
			if (mAS400TerminatorProgram == null
					|| mAS400TerminatorProgram.length() == 0)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_AS400, "25");
			}
			// Empty parameter list
			ProgramParameter[] param = new ProgramParameter[0];
			// Call
			callAS400Program(mAS400System, mAS400TerminatorProgram, param,
					mProgramTimeout);
		} // CallProgram
	} // commit()

	/**
	 * Implemented method <code>rollback</code> from TAResource neglects all
	 * changes made so far (file locking).
	 * <p>
	 * 
	 * @see net.sf.xbus.base.core.TAResource#rollback()
	 * @exception XException if locks cannot be released
	 */
	public void rollback() throws XException
	{
		if (mOriginFile.getExplicitLocks().length > 0)
		{
			try
			{
				mOriginFile.releaseExplicitLocks();
			} // try
			catch (Exception ex)
			{
				List params = new Vector();
				params.add(mQSYSObject.getPath());
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_AS400, "6", params);
			} // catch
		} // if (mOriginFile.getExplicitLocks().length > 0)
	} // rollback()

	/**
	 * Not implemented for the iSeries integrated file system
	 * 
	 * @see net.sf.xbus.base.core.TAResource#open()
	 */
	public void open()
	{}

	/**
	 * Not implemented for the iSeries integrated file system
	 * 
	 * @see net.sf.xbus.base.core.TAResource#close()
	 */
	public void close()
	{}

	public String getType()
	{
		return Constants.TYPE_TEXT;
	}
} // AS400FileReceiver
