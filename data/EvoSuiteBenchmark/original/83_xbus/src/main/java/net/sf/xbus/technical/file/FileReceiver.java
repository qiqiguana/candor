package net.sf.xbus.technical.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import net.sf.xbus.application.Adapter;
import net.sf.xbus.application.PostProcessor;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.MessageHandler;
import net.sf.xbus.base.core.TAManager;
import net.sf.xbus.base.core.TAResource;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.trace.Trace;
import net.sf.xbus.base.deletedMessageStore.DeletedMessageStore;
import net.sf.xbus.base.notifyerror.NotifyError;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.Receiver;
import net.sf.xbus.technical.ReceiverSingleInterface;

/**
 * When the <code>FileReceiver</code> receives a request it reads the files
 * into a String and calls the application layer.
 */
public class FileReceiver extends FileBase
		implements
			Receiver,
			TAResource,
			ReceiverSingleInterface
{
	protected FileReceiverConfiguration mConfiguration = null;

	/**
	 * Full path name of the file to be read
	 */
	private String mFilename = null;

	/**
	 * Full path name of the copy from the original file to be read. <br>
	 * The filename is with current date expanded.
	 */
	protected String mCopyname = null;

	/**
	 * Full path name of the back up copy
	 */
	private String mBackupname = null;

	/**
	 * This method manages all processes of the receipt for one as parameter
	 * given name of the neighbor-system.
	 * <p>
	 * First, the list of {@link net.sf.xbus.base.xbussystem.XBUSSystem}for
	 * this system is determined by replacing the markers for additional
	 * adresses.
	 * <p>
	 * After that for each XBUSSystem the method {@link #doReceive(XBUSSystem)}
	 * is invoked to read the file and process its content.
	 * <p>
	 * 
	 * @see net.sf.xbus.base.xbussystem.XBUSSystem#getSystems(String, String)
	 * @param systemName name of the interface definition
	 * @exception XException if something goes wrong
	 */
	public void receive(String systemName)
	{
		try
		{
			readConfiguration(systemName);

			// FileReceiver will be registered by TAManager
			TAManager taManager = TAManager.getInstance();
			taManager.clearManager();
			taManager.registerResource(this);

			// determine the list of XBUSSystem for the system
			List systems = XBUSSystem.getSystems(systemName, mConfiguration
					.getFileName());

			boolean successful = true;
			for (Iterator it = systems.iterator(); it.hasNext();)
			{
				XBUSSystem xbusSystem = (XBUSSystem) it.next();
				Trace.info("Start processing " + xbusSystem.getCompleteName());
				if (!doReceive(xbusSystem))
				{
					successful = false;
				}
			}

			if (successful)
			{
				PostProcessor.start(new XBUSSystem(systemName), null,
						Constants.POSTPROCESSING_FINAL);
			}
		}
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
		}
		finally
		{
			// close resources and remove from the resources
			TAManager.getInstance().close();
			TAManager.getInstance().removeResource(this);
		}
	}

	/**
	 * Reads some entries from the configuration.
	 * 
	 * @param systemName
	 * @throws XException if anything goes wrong
	 */
	public void readConfiguration(String systemName) throws XException
	{
		// filename, encoding and resolution for this system
		// are read from the configuration and saved into class variables
		mConfiguration = new FileReceiverConfiguration(systemName);
	}

	/**
	 * Manages the process of receipt for several XBUSSystems. This method does
	 * the following:
	 * <ol>
	 * <li>replace placeholders in the filename with the dealer information
	 * <li>create copy and backup files, depending on the final resolution
	 * <li>read file into String
	 * <li>call application layer - Adapter
	 * <li>determine the resulting returncode
	 * </ol>
	 * 
	 * @param xbusSystem name of the interface definition
	 */
	protected boolean doReceive(XBUSSystem xbusSystem)
	{
		boolean successful = false;
		try
		{
			// The file path name may contain placeholders for informations on
			// the address.
			// In flw. method these placeholders will be replaced with their
			// actual values for the address
			// of the neighbor-system.
			// if the address has not been set, the text will be returned
			// without modifications.
			mFilename = xbusSystem.replaceAllMarkers(mConfiguration
					.getFileName())[0];
			// Trace.info("Receiving data from " + mFilename);
			// only if the file exist
			File file = new File(mFilename);
			if (file.exists())
			{
				if (xbusSystem.getMaxAge() > 0 || xbusSystem.getMinAge() > 0)
				{ // Restrictions to file age is specified.
					// Test the limits.
					long fileTimestamp = file.lastModified();
					long now = System.currentTimeMillis();
					if (now - fileTimestamp < xbusSystem.getMinAge())
						// The minimal age is not satisfied.
						successful = true;
					else if (xbusSystem.getMaxAge() > 0
							&& now - fileTimestamp > xbusSystem.getMaxAge())
						// The file is too old.
						successful = true;
				} // then (mMaxAge>0 || mMinAge>0)

				if (!successful)
				{
					Trace.info("Receiving data from " + mFilename);
					TAManager.getInstance().begin();
					// check if the file meets the requirements of Resolution
					// otherwise save xbusSystem with FileReceiver into Vector
					// to be
					// retry late
					if (!checkFilePermissions())
					{
						// wrong file permissions
						Vector params = new Vector(1);
						params.add(mFilename);

						throw new XException(Constants.LOCATION_EXTERN,
								Constants.LAYER_TECHNICAL,
								Constants.PACKAGE_TECHNICAL_FILE, "17", params);
					} // then (!checkFilePermissions())
					else
					{
						try
						{
							// make copy and use this copy for read
							mCopyname = mFilename + Constants.getDateAsString();
							copyFile(mFilename, mCopyname);
							if (!mConfiguration.getResolution().equals(
									Constants.READ_PRESERVE))
							{
								// hide file from other using wenn
								// resolution delete or rename
								mBackupname = mFilename
										+ Constants.BACKUP_SUFFIX;
								renameFile(mFilename, mBackupname);
							} // The content of the request.
							// Subclasses may define their own request content.
							Object requestContent = getRequestContent();

							// call application layer
							Adapter adapter = new Adapter();
							adapter.callApplication(xbusSystem, requestContent,
									getType());
							// check the Adapter return code
							// in case of "not success" throw new XException
							if (Constants.RC_OK.equals(adapter.getReturncode()))
							{
								TAManager.getInstance().commit();

								PostProcessor.start(xbusSystem, adapter
										.getResponse(),
										Constants.POSTPROCESSING_PERSYSTEM);

								Trace.info("End processing "
										+ xbusSystem.getCompleteName());
								Trace
										.info("----------------------------------------");
								successful = true;
							} // then
							// (Constants.RC_OK.equals(adapter.getReturncode()))
							else
							{
								TAManager.getInstance().rollback();

								NotifyError.notifyError(this, xbusSystem,
										adapter.getErrormessage(),
										requestContent, null);

								Trace.info("Error while processing "
										+ xbusSystem.getCompleteName());
								Trace
										.info("----------------------------------------");
								return false;
							} // else
							// (Constants.RC_OK.equals(adapter.getReturncode()))

						} // try
						catch (Throwable t)
						{
							// Something unexpected has happened.
							TAManager.getInstance().rollback();
							NotifyError.notifyError(this, xbusSystem, t
									.getMessage(), null, null);

							Trace.info("Error while processing "
									+ xbusSystem.getCompleteName());
							Trace
									.info("----------------------------------------");

							throw new XException(Constants.LOCATION_EXTERN,
									Constants.LAYER_TECHNICAL,
									Constants.PACKAGE_TECHNICAL_FILE, "0", t);
						} // catch (Throwable t)

					} // else (!checkFilePermissions())
				} // if (!successful)
			} // then (file.exists())
			else
			{
				// Trace.info("File does not exist");
				successful = true;
			} // else (file.exists())
		} // try
		catch (XException e)
		{
			successful = false;
		} // (XException e)
		return successful;
	} // doReceive

	/**
	 * <code>getRequestContent</code> delivers the content of the request.
	 * Subclasses may define their own request content.
	 * 
	 * @return the interface file content as <code>String</code> but casted to
	 *         <code>Object</code> for compliance reasons
	 */
	protected Object getRequestContent() throws XException
	{
		// read file
		return readFile(mCopyname);
	} // getRequestContent()

	/**
	 * Depending on the FinalResolution having read from the standard
	 * configuration, checks this method a file on an opportunity to meet the
	 * requirements.
	 * <p>
	 * <table border>
	 * <tr>
	 * <th>Resolution</th>
	 * <th>Requirements</th>
	 * </tr>
	 * <tr>
	 * <td>Preserve</td>
	 * <td>the file is a normal file (not a directory) <br>
	 * the application can read from this file</td>
	 * </tr>
	 * <tr>
	 * <td>Rename, Delete</td>
	 * <td>the file is a normal file (not a directory) <br>
	 * the application can read from this file <br>
	 * the application can modify this file</td>
	 * </tr>
	 * </table>
	 * 
	 * @return true if the file meets the requirements of Resolution, otherwise
	 *         false
	 * @exception XException if the file not exist or can not be read
	 */
	private boolean checkFilePermissions() throws XException
	{
		File readFile = new File(mFilename);
		// 1. check if it is one file ist
		if (!readFile.isFile())
		{
			List params = new Vector();
			params.add(mFilename);
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_FILE, "18", params);
		} // 2. check if file can be read
		if (!readFile.canRead())
		{
			List params = new Vector();
			params.add(mFilename);
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_FILE, "19", params);
		}

		// 3. resolution: Rename and Delete will take in account
		return mConfiguration.getResolution().equals(Constants.READ_PRESERVE)
				? true
				: readFile.canWrite();
	}

	/**
	 * Reads entire contents of a file into a String.
	 * <p>
	 * <b><i>Note: </i> </b> the filePermissions <b>(canRead) </b> must be
	 * determine prior to invoking this method!
	 * 
	 * @return file content as String
	 * @exception XException if file can not be read or any I/O error occurs
	 */
	private String readFile(String fileName) throws XException
	{
		StringBuffer retBuffer = new StringBuffer();
		File sourceFile = new File(fileName);
		String zeile;
		BufferedReader buffReader = null;
		try
		{

			buffReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(sourceFile), mConfiguration
							.getEncoding()));

			if ((zeile = buffReader.readLine()) == null)
			{
				buffReader.close();
				return "";
			}
			else
			{
				retBuffer.append(zeile);
			}

			while ((zeile = buffReader.readLine()) != null)
			{
				retBuffer.append(Constants.LINE_SEPERATOR);
				retBuffer.append(zeile);
			}
		}
		catch (IOException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_FILE, "0", e);
		}
		finally
		{
			if (buffReader != null)
			{
				try
				{
					buffReader.close();
				}
				catch (IOException e1)
				{
					// explicitly do nothing
				}
			}
		}

		return retBuffer.toString();
	}

	/**
	 * Implemented method <code>commit</code> from TAResource interface. The
	 * purpose of commit actions is to remove any backup information that had
	 * been created during process(tansaction).
	 * <p>
	 * Depending on the FinalResolution, the following acts commit all actions.
	 * <p>
	 * <table border>
	 * <tr>
	 * <th>Resolution</th>
	 * <th>Acts</th>
	 * </tr>
	 * <tr>
	 * <td>Preserve</td>
	 * <td>delete copy file</td>
	 * </tr>
	 * <tr>
	 * <td>Rename</td>
	 * <td>delete backup copy</td>
	 * </tr>
	 * <tr>
	 * <td>Delete</td>
	 * <td>&nbsp;1. delete copy file <br>
	 * &nbsp;2. delete backup copy</td>
	 * </tr>
	 * </table>
	 * 
	 * @see net.sf.xbus.base.core.TAResource#commit()
	 * @exception XException if any error occurs
	 */
	public void commit() throws XException
	{
		if (mConfiguration.getResolution().equals(Constants.READ_RENAME)
				|| mConfiguration.getResolution().equals(Constants.READ_DELETE))
		{

			deleteFile(mBackupname);
		}
		if (mConfiguration.getResolution().equals(Constants.READ_PRESERVE)
				|| mConfiguration.getResolution().equals(Constants.READ_DELETE))
		{

			deleteFile(mCopyname);
		}
	}

	/**
	 * Implemented method <code>rollback</code> from TAResource ignores all
	 * changes have made since the beginning of the process (transaction).
	 * <p>
	 * Depending on the FinalResolution, the following acts roll back all
	 * modifications that have been made in the file system:
	 * <p>
	 * <table border>
	 * <tr>
	 * <th>Resolution</th>
	 * <th>Acts</th>
	 * </tr>
	 * <tr>
	 * <td>Preserve</td>
	 * <td>delete copy file</td>
	 * </tr>
	 * <tr>
	 * <td>Rename</td>
	 * <td>&nbsp;1. rename backup copy to the original file <br>
	 * &nbsp;2. delete copy file</td>
	 * </tr>
	 * <tr>
	 * <td>Delete</td>
	 * <td>&nbsp;1. rename backup copy to the original file <br>
	 * &nbsp;2. delete copy file</td>
	 * </tr>
	 * </table>
	 * 
	 * @see net.sf.xbus.base.core.TAResource#rollback()
	 * @exception XException if any error occurs
	 */
	public void rollback() throws XException
	{
		boolean rename = mConfiguration.getOnError().equals(
				Constants.READ_RENAME);
		boolean delete = mConfiguration.getOnError().equals(
				Constants.READ_DELETE);
		boolean preserve = mConfiguration.getOnError().equals(
				Constants.READ_PRESERVE);

		if (delete)
		{
			if (DeletedMessageStore.getInstance().writeMessage())
			{
				deleteFile(mCopyname);
				if (mConfiguration.getResolution().equals(
						Constants.READ_PRESERVE))
				{
					deleteFile(mFilename);
				}
				else
				{
					deleteFile(mBackupname);
				}
			}
			else
			{
				delete = false;
				rename = true;
			}
		}

		if (preserve)
		{
			if (!mConfiguration.getResolution().equals(Constants.READ_PRESERVE))
			{
				File srcFile = new File(mBackupname);
				File destFile = new File(mFilename);
				if (srcFile.renameTo(destFile) == false)
				{
					Vector params = new Vector(2);
					params.add(mBackupname);
					params.add(mFilename);

					String key = Constants.LOCATION_INTERN + "_"
							+ Constants.LAYER_TECHNICAL + "_"
							+ Constants.PACKAGE_TECHNICAL_FILE + "_" + "1";

					MessageHandler msg = MessageHandler.getInstance("errors");
					String messageText = msg.getMessage(key, params);

					Trace.warn(messageText);
				}

			}
			deleteFile(mCopyname);
		}

		if (rename)
		{
			if (mConfiguration.getResolution().equals(Constants.READ_PRESERVE))
			{
				deleteFile(mFilename);
			}
			else
			{
				deleteFile(mBackupname);
			}
		}
	}

	/**
	 * Is not implemented for file system
	 */
	public void open()
	{}

	public String getConfigFilename()
	{
		return mConfiguration.getFileName();
	}

	public String getOnError()
	{
		return mConfiguration.getOnError();
	}

	/**
	 * Is not implemented for file system
	 */
	public void close()
	{}

	public String getType()
	{
		return Constants.TYPE_TEXT;
	}
}
