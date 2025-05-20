package net.sf.xbus.technical.ftp;

import java.util.Iterator;
import java.util.List;

import net.sf.xbus.application.Adapter;
import net.sf.xbus.application.PostProcessor;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.TAManager;
import net.sf.xbus.base.core.TAResource;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.trace.Trace;
import net.sf.xbus.base.deletedMessageStore.DeletedMessageStore;
import net.sf.xbus.base.notifyerror.NotifyError;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.Receiver;
import net.sf.xbus.technical.ReceiverSingleInterface;
import net.sf.xbus.technical.file.FileReceiverConfiguration;

/**
 * The class <code>FTPReceiver</code> receives data by reading a file on a FTP
 * server.
 * <p />
 * It creates a <code>Message</code> object and calls the application layer.
 */
public class FTPReceiver
		implements
			Receiver,
			ReceiverSingleInterface,
			TAResource
{
	protected FileReceiverConfiguration mConfiguration = null;
	private FTPConnection mFTPConnection = null;
	private String mSystem = null;
	private String mFileName = null;
	private String mWorkDir = null;

	/**
	 * Reads data from files on a FTP server specified by the given system. If
	 * the file name contains variables, they will be filled with their actual
	 * values before.
	 */
	public void receive(String system)
	{
		mSystem = system;

		try
		{
			mConfiguration = new FileReceiverConfiguration(system);

			// FileReceiver will be registered by TAManager
			TAManager taManager = TAManager.getInstance();
			taManager.clearManager();
			taManager.registerResource(this);
			taManager.begin();

			// determine the list of XBUSSystem for the system
			List systems = XBUSSystem.getSystems(system, mConfiguration
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
				PostProcessor.start(new XBUSSystem(system), null,
						Constants.POSTPROCESSING_FINAL);
			}
		}
		catch (Throwable e)
		{
			try
			{
				NotifyError.notifyError(this, new XBUSSystem(system), e
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
	 * Receives data for one system.
	 */
	private boolean doReceive(XBUSSystem source)
	{
		String request = null;

		try
		{
			/*
			 * Get the transaction manager
			 */
			TAManager taManager = TAManager.getInstance();
			taManager.begin();

			mFileName = source.replaceAllMarkers(mConfiguration.getFileName())[0];

			/*
			 * The FileName may contain a directory. It is split up into the
			 * name of the directory and the name of the file itself.
			 */
			mWorkDir = FTPConnection.getWorkingDirectory(mFileName);
			mFileName = FTPConnection.getFileName(mFileName);

			if (!mFTPConnection.existsFile(mWorkDir, mFileName))
			{
				return true;
			}
			else
			{
				Trace.info("Receiving data from "
						+ mFTPConnection.getFTPName(mWorkDir, mFileName));

				/*
				 * Retrieve data from FTP connection
				 */
				request = getRequestContent();

				/*
				 * Process data
				 */
				Adapter adapter = new Adapter();
				adapter.callApplication(source, request, getType());
				Object responseObject = adapter.getResponse();
				if (Constants.RC_OK.equals(adapter.getReturncode()))
				{
					taManager.commit();
					PostProcessor.start(source, responseObject,
							Constants.POSTPROCESSING_PERSYSTEM);
					Trace.info("End processing " + source.getCompleteName());
					Trace.info("----------------------------");
					return true;
				}
				else
				{
					taManager.rollback();

					NotifyError.notifyError(this, source, adapter
							.getErrormessage(), request, null);

					Trace.info("Error while processing "
							+ source.getCompleteName());
					Trace.info("----------------------------");
					return false;
				}
			}
		}
		catch (Throwable t)
		{
			Trace.error(t);
			Trace.info("Error while processing " + source.getCompleteName());
			Trace.info("----------------------------");
			return false;
		}
	}

	/**
	 * @see net.sf.xbus.technical.Receiver#getType()
	 * 
	 * @return <code>Constants.TYPE_TEXT</code>
	 */
	public String getType()
	{
		return Constants.TYPE_TEXT;
	}

	/**
	 * Opens the connection to the FTP server.
	 */
	public void open() throws XException
	{
		String ftpName = Configuration.getInstance().getValue(
				Constants.CHAPTER_SYSTEM, mSystem, "FTPConnection");
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
	 * Depending on the parameter <code>FinalResolution</code>, the file
	 * previously read will either be renamed, deleted or will not be touched.
	 */
	public void commit() throws XException
	{
		if (mConfiguration.getResolution().equals(Constants.READ_RENAME))
		{
			String newFileName = mFileName + Constants.getDateAsString();
			mFTPConnection.rename(mWorkDir, mFileName, newFileName);
		}
		else if (mConfiguration.getResolution().equals(Constants.READ_DELETE))
		{
			mFTPConnection.delete(mWorkDir, mFileName);
		}
	}

	/**
	 * Depending on the parameter <code>OnError</code>, the file previously
	 * read will either be renamed, deleted or will not be touched.
	 */
	public void rollback() throws XException
	{
		String onError = mConfiguration.getOnError();

		if (onError.equals(Constants.READ_DELETE))
		{
			if (DeletedMessageStore.getInstance().writeMessage())
			{
				mFTPConnection.delete(mWorkDir, mFileName);
			}
			else
			{
				Trace.warn("Renaming "
						+ mFTPConnection.getFTPName(mWorkDir, mFileName));
				onError = Constants.READ_RENAME;
			}
		}
		if (onError.equals(Constants.READ_RENAME))
		{
			String newFileName = mFileName + Constants.getDateAsString();
			mFTPConnection.rename(mWorkDir, mFileName, newFileName);
		}
	}
	
	protected String getRequestContent() throws XException
	{
		return mFTPConnection.retrieveFile(FTPConnection.getWorkingDirectory(mFileName), 
				FTPConnection.getFileName(mFileName),
				mConfiguration.getEncoding());

	}
	
	protected String getAddress()
	{
		return new StringBuffer(mWorkDir).append(mFileName).toString();
	}
}
