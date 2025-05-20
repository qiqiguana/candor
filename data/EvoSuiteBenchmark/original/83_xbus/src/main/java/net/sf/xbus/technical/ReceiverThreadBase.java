package net.sf.xbus.technical;

import net.sf.xbus.application.Adapter;
import net.sf.xbus.application.PostProcessor;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.TAManager;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.trace.Trace;
import net.sf.xbus.base.notifyerror.NotifyError;
import net.sf.xbus.base.xbussystem.XBUSSystem;

/**
 * Classes implementing the <code>ReceiverThreadBase</code> are running as
 * background threads to process messages when they arrive. These classes are
 * executed by the {@link ReceiverService}.
 */
abstract public class ReceiverThreadBase implements Runnable, Receiver
{
	/**
	 * Default for the time to wait after an error has occured.
	 */
	static protected final int DEFAULT_ERROR_TIMEOUT = 10; // seconds

	/**
	 * Default for the time to wait after the successful processing of a
	 * message.
	 */
	static protected final int DEFAULT_TIMEOUT = 5; // seconds

	private XBUSSystem mSource = null;
	private int mErrorCounter = 0;
	private long mErrorTimeout = 0;
	private int mStopAfterErrors = 0;
	private long mTimeout = 0;
	private boolean mProceed = true;

	/**
	 * Stores the interface name
	 * 
	 * @param source name of the interface definition
	 */
	public ReceiverThreadBase(XBUSSystem source)
	{
		mSource = source;
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
				.always("Starting ReceiverThread for "
						+ mSource.getCompleteName());

		/*
		 * Initialize the ReceiverThread, e.g. opening a connection
		 */
		TAManager taManager = TAManager.getInstance();
		boolean initializeSuccessful = false;
		while (!initializeSuccessful && checkProceed())
		{
			try
			{
				initializeThread();
				initializeSuccessful = true;
				initializeErrorCounter();
			}
			catch (XException e)
			{
				incrementErrorCounter();
				Trace.error("Problem while starting "
						+ mSource.getCompleteName());
				Trace.error("Retry after " + getErrorTimeout() / 1000
						+ " seconds");
				Trace.error("------------------------------");
				try
				{
					Thread.sleep(getErrorTimeout());
				}
				catch (InterruptedException ie)
				{
					// do nothing
				}
			}
		}

		/*
		 * Process messages
		 */
		Object message = null;
		while (checkProceed())
		{
			try
			{
				taManager.clearManager();
				registerResources(taManager);
				taManager.begin();
			}
			catch (XException e)
			{
				taManager.close();
				incrementErrorCounter();
				Trace.error("Problem while starting "
						+ mSource.getCompleteName());
				Trace.error("Retry after " + getErrorTimeout() / 1000
						+ " seconds");
				Trace.error("------------------------------");
				try
				{
					Thread.sleep(getErrorTimeout());
				}
				catch (InterruptedException e1)
				{
					// do nothing
				}
			}

			try
			{
				message = receive();

				if (message != null)
				{
					Trace.info("Receiving data from " + getAddress());

					Adapter adapter = new Adapter();
					adapter.callApplication(getSource(), message, getType());

					if (Constants.RC_OK.equals(adapter.getReturncode()))
					{
						initializeErrorCounter();
						taManager.commit();
						PostProcessor.start(getSource(), adapter.getResponse(),
								Constants.POSTPROCESSING_PERSYSTEM);
						Trace.info("End processing "
								+ getSource().getCompleteName());
						Trace.info("------------------------------");
					}
					else
					{
						handleError(taManager, message, adapter
								.getErrormessage());
					}
				}
				else
				{
					/*
					 * All receiver threads except the MQReceiver shall wait
					 * some seconds if there is currently no message available.
					 */

					if (!"MQReceiverThread".equals(getReceiverClassName()))
					{
						try
						{
							Thread.sleep(getTimeout());
						}
						catch (InterruptedException e1)
						{
							// do nothing
						}
					}
				}
			}
			catch (XException e)
			{
				handleError(taManager, message, e.getMessage());
			}
		}

		taManager.close();

		Trace
				.always("ReceiverThread for " + getSource().getName()
						+ " stopped");
	}

	/**
	 * Initialization of the the thread, called in the beginning, before the
	 * loop for processing messages starts. Used e.g. for opening a connection.
	 * 
	 * @throws XException if something goes wrong
	 */
	abstract protected void initializeThread() throws XException;

	/**
	 * Because the thread clears the list of transactional resources each time
	 * after processing a message, this method is called before reading the next
	 * message to register a receiver resource in the transaction manager.
	 * 
	 * @param taManager the transaction manager in which the resources shall be
	 *            registered
	 * @throws XException if something goes wrong
	 */
	abstract protected void registerResources(TAManager taManager)
			throws XException;

	/**
	 * Receives one message.
	 * 
	 * @return the received message or <code>null</code> when no message has
	 *         been available
	 * @throws XException if something goes wrong
	 */
	abstract protected Object receive() throws XException;

	/**
	 * Returns the name of the receiver class, used for example to determine
	 * values in the configuration.
	 * 
	 * @return the name of the receiver class
	 */
	abstract protected String getReceiverClassName();

	/**
	 * Returns the address of a received message, used for example in the
	 * tracing. The content of the address depends on the type of receiver.
	 * FileReceivers will return filenames, the POP3Receiver will return an
	 * email address.
	 * 
	 * @return the address of a received message
	 */
	abstract protected String getAddress();

	/**
	 * Handles all actions to be taken, when a message cannot be processed, e.g.
	 * rollback of transactional resources and notifying an administrator.
	 * 
	 * @param taManager the transactional manager
	 * @param message the content of the message that cannot be processed.
	 */
	private void handleError(TAManager taManager, Object message,
			String errorMessage)
	{
		taManager.rollback();
		taManager.close();
		incrementErrorCounter();
		NotifyError.notifyError(this, getSource(), errorMessage, message, null);
		Trace.error("Error while processing " + getSource().getCompleteName());
		Trace.error("Retry after " + getErrorTimeout() / 1000 + " seconds");
		Trace.error("------------------------------");
		try
		{
			Thread.sleep(getErrorTimeout());
		}
		catch (InterruptedException e1)
		{
			// do nothing
		}
	}

	/**
	 * <code>interruptThread</code> shall interrupt the loop of receiving and
	 * processing messages and cause the thread to stop. It is invoked by the
	 * {@link ReceiverService}on shutdown or restart.
	 */
	public void interruptThread()
	{
		mProceed = false;
	}

	/**
	 * Checks wether the amount of errors have been reached, to stop the
	 * receiver thread.
	 */
	private void checkErrorCounter()
	{
		if (mStopAfterErrors == 0)
		{
			mStopAfterErrors = ReceiverThreadManager.getStopAfterErrors(mSource
					.getName(), getReceiverClassName());
		}

		if (mProceed && (mStopAfterErrors > 0)
				&& (mErrorCounter >= mStopAfterErrors))
		{
			String message = "Stopping ReceiverThread "
					+ mSource.getCompleteName()
					+ " because of maximum amount of errors!";
			Trace.always(message);
			NotifyError.notifyError(this, mSource, message, null, null);
			try
			{
				ReceiverThreadManager.getInstance().demandStopReceiverThread(
						mSource.getName());
			}
			catch (XException e)
			{
				// do nothing, error has been traced
				Trace.error("Cannot stop ReceiverThread " + mSource.getName());
			}
		}
	}

	/**
	 * Returns a boolean value that indicates whether the thread shall continue
	 * to process messages.
	 * 
	 * @return a boolean value that indicates whether the thread shall continue
	 *         to process messages
	 */
	protected boolean checkProceed()
	{
		checkErrorCounter();
		return mProceed;
	}

	/**
	 * Increments the error counter.
	 */
	protected void incrementErrorCounter()
	{
		mErrorCounter++;
	}

	/**
	 * Initializes the error counter to 0.
	 */
	protected void initializeErrorCounter()
	{
		mErrorCounter = 0;
	}

	/**
	 * Returns the source of the messages.
	 * 
	 * @return the source of the messages
	 */
	protected XBUSSystem getSource()
	{
		return mSource;
	}

	/**
	 * Returns the time to wait after an error has occured. The value is read
	 * out of the configuration, either a default for all receivers or a value
	 * specific for the current source. If no value is specified in the
	 * configuration, a hard coded default is used.
	 * 
	 * @return the time to wait after an error has occured
	 */
	protected long getErrorTimeout()
	{
		if (mErrorTimeout == 0)
		{
			try
			{
				Configuration config = Configuration.getInstance();
				mErrorTimeout = config.getValueAsIntOptional("System", mSource
						.getName(), "WaitAfterError") * 1000;
				if (mErrorTimeout == 0)
				{
					mErrorTimeout = config.getValueAsInt("Base", "Receiver",
							"WaitAfterError") * 1000;
				}
			}
			catch (XException e)
			{
				mErrorTimeout = DEFAULT_ERROR_TIMEOUT * 1000;
				Trace.warn("Using default for WaitAfterError: "
						+ DEFAULT_ERROR_TIMEOUT + " seconds");
			}
		}
		return mErrorTimeout;
	}

	/**
	 * Returns the time to wait after the successful processing of a message.
	 * The value is read out of the configuration, either a default for the
	 * receiver class or a value specific for the current source. If no value is
	 * specified in the configuration, a hard coded default is used.
	 * 
	 * @return the time to wait after the successful processing of a message
	 */
	protected long getTimeout()
	{
		if (mTimeout == 0)
		{
			try
			{
				Configuration config = Configuration.getInstance();
				mTimeout = config.getValueAsIntOptional("System", mSource
						.getName(), "Timeout") * 1000;
				if (mTimeout == 0)
				{
					mTimeout = config.getValueAsInt("Base",
							getReceiverClassName(), "Timeout") * 1000;
				}
			}
			catch (XException e)
			{
				mTimeout = DEFAULT_TIMEOUT * 1000;
				Trace.warn("Using default for Timeout: " + DEFAULT_TIMEOUT
						+ " seconds");
			}
		}
		return mTimeout;
	}
}
