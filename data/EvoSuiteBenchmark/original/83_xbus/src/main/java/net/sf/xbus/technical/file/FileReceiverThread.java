package net.sf.xbus.technical.file;

import java.util.Iterator;
import java.util.List;

import net.sf.xbus.application.PostProcessor;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.TAManager;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.trace.Trace;
import net.sf.xbus.base.notifyerror.NotifyError;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.ReceiverThreadBase;

/**
 * The <code>FileReceiverThread</code> runs in the background and receives
 * data from files when they exist.
 */
public class FileReceiverThread extends ReceiverThreadBase
{
	FileReceiver mReceiver = null;

	/**
	 * Stores the interface name
	 * 
	 * @param source name of the interface definition
	 */
	public FileReceiverThread(XBUSSystem source)
	{
		super(source);
	}

	/**
	 * Receives and processes messages for one system. It is running until
	 * either the thread shall be stopped on demand or until a configurable
	 * amount of errors has occured, without a successful processed message
	 * between it.
	 */
	public void run()
	{
		Trace
				.always("Starting FileReceiverThread for "
						+ getSource().getName());

		mReceiver = createReceiver();

		TAManager taManager = null;
		List systems = null;

		// initialize and register all resources
		try
		{
			taManager = TAManager.getInstance();
			mReceiver.readConfiguration(getSource().getName());
			// register resource
			taManager.registerResource(mReceiver);
		}
		catch (XException xe)
		{
			Trace.always("Couldn't start " + getSource().getName());
			NotifyError.notifyError(this, getSource(), "Couldn't start "
					+ getSource().getName(), null, null);
			return;
		}

		// main loop
		while (checkProceed())
		{
			try
			{
				try
				{
					// determine the list of XBUSSystem for the system
					systems = XBUSSystem.getSystems(getSource().getName(),
							mReceiver.getConfigFilename());
				}
				catch (XException xex)
				{
					NotifyError.notifyError(this, getSource(),
							xex.getMessage(), null, null);
					return;
				}

				// for loop iterates over the all XBUSSystems
				// and proceed the receipt for each ones
				boolean errorFree = true;
				for (Iterator it = systems.iterator(); it.hasNext();)
				{
					if (!mReceiver.doReceive((XBUSSystem) it.next()))
					{
						errorFree = false;
					}
				}
				if (errorFree)
				{
					initializeErrorCounter();
					PostProcessor.start(new XBUSSystem(getSource().getName()),
							null, Constants.POSTPROCESSING_FINAL);
					// wait and try once again
					try
					{
						Thread.sleep(getTimeout());
					}
					catch (InterruptedException ie)
					{}
				}
				else
				{
					incrementErrorCounter();
					// wait and try once again
					try
					{
						Thread.sleep(getErrorTimeout());
					}
					catch (InterruptedException ie)
					{}
				}

			}
			catch (Throwable t)
			{
				/*
				 * Last safety belt, if anything happens, at least the error
				 * should be reported.
				 */
				NotifyError.notifyError(this, getSource(), t.getMessage(),
						null, null);
				interruptThread();

			}
		}

		Trace.always("FileReceiverThread for " + getSource().getName()
				+ " stopped");

		// close all resources and remove Resource from the TAManager
		taManager.close();
		taManager.removeResource(mReceiver);
	}

	/**
	 * Creates an instance of {@link net.sf.xbus.technical.file.FileReceiver}
	 * used to read from a file.
	 * 
	 * @return an instance of {@link net.sf.xbus.technical.file.FileReceiver}
	 */
	protected FileReceiver createReceiver()
	{
		return new FileReceiver();
	}

	/**
	 * Not implemented because the <code>run</code> method is not the standard
	 * implementation.
	 */
	protected void initializeThread()
	{}

	/**
	 * Not implemented because the <code>run</code> method is not the standard
	 * implementation.
	 */
	protected void registerResources(TAManager taManager)
	{}

	/**
	 * Not implemented because the <code>run</code> method is not the standard
	 * implementation.
	 */
	protected Object receive()
	{
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * @see net.sf.xbus.technical.Receiver#getType()
	 */
	public String getType()
	{
		return mReceiver.getType();
	}
	/**
	 * @see net.sf.xbus.technical.ReceiverThreadBase#getReceiverClassName()
	 */
	protected String getReceiverClassName()
	{
		return "FileReceiverThread";
	}
	/**
	 * Returns the file name of the received message.
	 * 
	 * @see net.sf.xbus.technical.ReceiverThreadBase#getAddress()
	 */
	protected String getAddress()
	{
		return mReceiver.getConfigFilename();
	}
}
