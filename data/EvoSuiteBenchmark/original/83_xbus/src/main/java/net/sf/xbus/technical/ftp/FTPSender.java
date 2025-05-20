package net.sf.xbus.technical.ftp;

import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.TAManager;
import net.sf.xbus.base.core.TAResource;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.Sender;
import net.sf.xbus.technical.TextSender;
import net.sf.xbus.technical.file.FileSenderConfiguration;

/**
 * <code>FTPSender</code> sends data to neighbor systems by accessing a FTP
 * server and writing the data onto this server.
 */
public class FTPSender implements Sender, TextSender, TAResource
{
	private XBUSSystem mDestination = null;
	private FTPConnection mFTPConnection = null;
	private FileSenderConfiguration mConfiguration = null;

	private String mWorkDir = null;
	private String mFileName = null;
	private String mTmpFileName = null;
	private String mRenameFileName = null;

	/**
	 * Indicates wether execute has been called, used in commit and rollback
	 */
	private boolean senderExecuted = false;

	/**
	 * The constructor stores the destination, registers the
	 * <code>FTPSender</code> at the {@link net.sf.xbus.base.core.TAManager}
	 * and opens the connection to the FTP server.
	 * 
	 * @param destination where the data shall be send to
	 * @throws XException if something goes wrong
	 */
	public FTPSender(XBUSSystem destination) throws XException
	{
		mDestination = destination;
		TAManager.getInstance().registerResource(this);
		open();
	}

	/**
	 * Writes the data to the FTP server. A simple transaction logic is
	 * implemented: The data will be written to a temporary file. It will be
	 * moved to the original file name during {@link #commit}.
	 * 
	 * @param function ignored
	 * @param callData data to be written
	 * @return <code>null</code>
	 * @throws XException if something goes wrong
	 */
	public String execute(String function, String callData) throws XException
	{
		senderExecuted = true;
		/*
		 * Read configuration and initialize variables
		 */
		mConfiguration = new FileSenderConfiguration(mDestination);

		/*
		 * The FileName may contain a directory. It is split up into the name of
		 * the directory and the name of the file itself.
		 */
		mWorkDir = FTPConnection.getWorkingDirectory(mConfiguration
				.getFileNames()[0]);
		mFileName = FTPConnection.getFileName(mConfiguration.getFileNames()[0]);

		mTmpFileName = mFileName + "." + Constants.TEMP_SUFFIX;
		mRenameFileName = mFileName + Constants.getDateAsString();

		/*
		 * if resolution is WRITE_ERROR then stop if the file is already there
		 */
		if ((mConfiguration.getResolution().equals(Constants.WRITE_ERROR))
				&& (mFTPConnection.existsFile(mWorkDir, mFileName)))
		{
			Vector params = new Vector(1);
			params.add(mConfiguration.getFileNames());
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL, Constants.PACKAGE_TECHNICAL_FTP,
					"10", params);
		}

		/*
		 * Delete tmpFile if it exists
		 */
		if (mFTPConnection.existsFile(mWorkDir, mTmpFileName))
		{
			mFTPConnection.delete(mWorkDir, mTmpFileName);
		}

		/*
		 * if resolution is WRITE_APPEND and file exists, data will be appended
		 */
		if ((mConfiguration.getResolution().equals(Constants.WRITE_APPEND))
				&& (mFTPConnection.existsFile(mWorkDir, mFileName)))
		{
			// get current data
			StringBuffer currentData = new StringBuffer(mFTPConnection
					.retrieveFile(mWorkDir, mFileName, mConfiguration
							.getEncoding()));

			// append linebreak if necessary
			if ((currentData.length() > 0)
					&& (currentData.substring(currentData.length() - 1)
							.getBytes()[0] != Constants.NEWLINE))
			{
				currentData.append(Constants.LINE_SEPERATOR);
			}

			// append new data to current data and write to server as tmpFile
			currentData.append(callData);
			mFTPConnection.storeFile(currentData.toString(), mWorkDir,
					mTmpFileName, mConfiguration.getEncoding());
		}
		else
		{
			// write callData to server as tmpFile
			mFTPConnection.storeFile(callData, mWorkDir, mTmpFileName,
					mConfiguration.getEncoding());

		}

		return null;
	}

	/**
	 * Opens the connection to a FTP server, using the
	 * {@link net.sf.xbus.technical.ftp.FTPConnection}.
	 */
	public void open() throws XException
	{
		String ftpName = Configuration.getInstance().getValue(
				Constants.CHAPTER_SYSTEM, mDestination.getName(),
				"FTPConnection");
		mFTPConnection = FTPConnection.getInstance(ftpName);
	}

	/**
	 * Closes the connection to the FTP server.
	 */
	public void close()
	{
		if (mFTPConnection != null)
		{
			mFTPConnection.close();
		}
	}

	/**
	 * Renames the temporary file to the original file name. If the file already
	 * exists, it will be either deleted or renamed, depending on the
	 * <code>ConflictResolution</code>.
	 * 
	 * @throws XException if something goes wrong
	 */
	public void commit() throws XException
	{
		TAManager.getInstance().removeResource(this);
		if (senderExecuted)
		{
			/*
			 * Rename the file if it exists
			 */
			if (mFTPConnection.existsFile(mWorkDir, mFileName))
			{
				mFTPConnection.rename(mWorkDir, mFileName, mRenameFileName);
			}

			/*
			 * Rename tmpFile to original file
			 */
			mFTPConnection.rename(mWorkDir, mTmpFileName, mFileName);

			/*
			 * Remove renamed file if resolution is not WRITE_RENAME
			 */
			if ((!mConfiguration.getResolution().equals(Constants.WRITE_RENAME))
					&& (mFTPConnection.existsFile(mWorkDir, mRenameFileName)))
			{
				mFTPConnection.delete(mWorkDir, mRenameFileName);
			}
		}
	}

	/**
	 * Deletes the temporary file.
	 * 
	 * @throws XException if something goes wrong
	 */
	public void rollback() throws XException
	{
		TAManager.getInstance().removeResource(this);
		if (senderExecuted)
		{
			/*
			 * delete tmpFile
			 */
			mFTPConnection.delete(mWorkDir, mTmpFileName);
		}
	}

	/**
	 * @see net.sf.xbus.technical.Sender#getType()
	 * @return <code>Constants.TYPE_TEXT</code>
	 */
	public String getType()
	{
		return Constants.TYPE_TEXT;
	}
}
