package net.sf.xbus.admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import net.sf.xbus.base.core.ASCIITokenizer;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.MessageHandler;
import net.sf.xbus.base.core.TAManager;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.trace.Trace;
import net.sf.xbus.base.deletedMessageStore.DeletedMessageStore;
import net.sf.xbus.base.journal.Journal;
import net.sf.xbus.technical.ReceiverService;
import net.sf.xbus.technical.ReceiverThreadManager;
import net.sf.xbus.technical.as400.AS400Connection;
import net.sf.xbus.technical.database.DBConnection;
import net.sf.xbus.technical.http.HTTPReceiver;
import net.sf.xbus.technical.mq.MQConnection;

/**
 * Provides several services to administrate the xBus.
 */
public class Administrator
{
	/**
	 * Reads a file from <code>XBUS_HOME/etc</code>.
	 * 
	 * @param filename the name of the file to read
	 * @return the content of the file or an error message
	 */
	public String readEtc(String filename)
	{
		if (filename == null)
		{
			return ("Filename must not be null.");
		} // if (source == null)
		else if (filename.startsWith("..") || (filename.indexOf("..") > 0))
		{
			return (".. is not allowed in filename.");
		} // if (source.startsWith("..") || (source.indexOf("..") > 0))
		else if (filename.startsWith("/") || (filename.startsWith("\\")))
		{
			return ("Filename must not start with / or \\.");
		} // if (source.startsWith("..") || (source.indexOf("..") > 0))

		return readFile(Constants.XBUS_ETC + filename, 0);
	}

	/**
	 * Reads a file from <code>XBUS_HOME/log</code>.
	 * 
	 * @param filename the name of the file
	 * @param maxLength maximum length to read in kbyte.
	 * @return the content of the file or an error message
	 */
	public String readLog(String filename, int maxLength)
	{
		if (filename == null)
		{
			return ("Filename must not be null");
		} // if (source == null)
		else if (filename.startsWith("..") || (filename.indexOf("..") > 0))
		{
			return (".. is not allowed in filename");
		} // if (source.startsWith("..") || (source.indexOf("..") > 0))
		else if (filename.startsWith("/") || (filename.startsWith("\\")))
		{
			return ("filename must not start with / or \\");
		} // if (source.startsWith("..") || (source.indexOf("..") > 0))

		return readFile(Constants.XBUS_LOG + filename, maxLength);
	}

	/**
	 * Explains an errorcode.
	 * 
	 * @param key the errorcode to explain
	 * @return the message of the errorcode
	 */
	public String explainErrorcode(String key)
	{
		if (key == null)
		{
			return ("Key must not be null.");
		}

		String basename = "errors";

		// get the decompose key
		ASCIITokenizer test = new ASCIITokenizer(key, "_");
		String Location = test.nextToken();
		String Layer = test.nextToken();
		String Package = test.nextToken();
		String Number = test.nextToken();

		// get the Message
		MessageHandler msg = MessageHandler.getInstance(basename);
		String messageText = msg.getMessageOptional(key, null);

		// get the Location
		String LocationInformation = null;
		if (Location.equals("I"))
		{
			LocationInformation = "Intern";
		} // if (Location.equals("I"))
		else if (Location.equals("E"))
		{
			LocationInformation = "Extern";
		} // if (Location.equals("E"))
		else
		{
			return "Wrong Location";
		}
		// get the Layer
		String LayerInformation = msg.getMessageOptional(Layer, null);

		// get the Package
		String PackageInformation = msg.getMessageOptional(Layer + "_"
				+ Package, null);

		String informations = new StringBuffer(Location).append(" = ").append(
				LocationInformation).append(Constants.LINE_SEPERATOR).append(
				Layer).append(" = ").append(LayerInformation).append(
				Constants.LINE_SEPERATOR).append(Package).append(" = ").append(
				PackageInformation).append(Constants.LINE_SEPERATOR).append(
				"Number").append(" = ").append(Number).append(
				Constants.LINE_SEPERATOR).append("Message text").append(" = ")
				.append(messageText).toString();

		return informations;
	}

	/**
	 * Stopping all background services, refreshes the configuration and starts
	 * the background services again.
	 * 
	 * @return the confirmation or an error message
	 */
	public String restartReceiverService()
	{
		try
		{
			/*
			 * Stop background receivers
			 */
			ReceiverService.getInstance().stopAllSystems();

			/*
			 * Initialize all configurations
			 */
			Configuration.refresh("standard");
			Configuration.refresh("xbus");
			Configuration.refresh("mapping");

			/*
			 * Initialize XException, Trace and Journal
			 */
			XException.clearExceptionInformation();
			Trace.initialize();
			Journal.initialize();

			/*
			 * Clear all connections
			 */
			DBConnection.clear();
			AS400Connection.clear();
			MQConnection.clear();
			TAManager.clear();

			/*
			 * Start background receivers again
			 */
			ReceiverService.getInstance().startAllSystems();
			ReceiverThreadManager.getInstance().clearStoppedHTTPReceivers();
			HTTPReceiver.initializeAmountErrorsCompletely();

			return "ReceiverService has been restarted and configuration has been refreshed.";
		}
		catch (XException e)
		{
			return XException.getExceptionInformation();
		}
	}

	/**
	 * Stops the ReceiverService completely, including the JMX server.
	 * 
	 * @return the confirmation or an error message
	 */
	public String stopReceiverService()
	{
		try
		{
			if (!ReceiverService.getInstance().stop())
			{
				return "ReceiverService can not be stopped";
			}
		}
		catch (XException e)
		{
			return XException.getExceptionInformation();
		}

		return "ReceiverService has been stopped";
	}

	/**
	 * Returns lists of running, stopped and killed ReceiverThreads.
	 * 
	 * @return lists of running, stopped and killed ReceiverThreads
	 */
	public String getReceiverServiceStatus()
	{
		StringBuffer retBuffer = new StringBuffer();

		/*
		 * Retrieving state of ReveiverThreads
		 */
		ReceiverThreadManager manager = ReceiverThreadManager.getInstance();
		Set runningSystems;
		Set allSystems;
		try
		{
			allSystems = manager.getAllSystems();
			runningSystems = manager.getRunningSystems();
		}
		catch (XException e)
		{
			return e.getMessage();
		}
		Set stoppedSystems = manager.getStoppedSystems();

		StringBuffer tmpBuffer = new StringBuffer();
		retBuffer.append("Running Background Receivers:\n");
		for (Iterator it = runningSystems.iterator(); it.hasNext();)
		{
			tmpBuffer.append("\n").append(it.next());
		}
		if (tmpBuffer.length() == 0)
		{
			retBuffer.append("-");
		}
		else
		{
			retBuffer.append(tmpBuffer);
		}

		tmpBuffer = new StringBuffer();
		retBuffer.append("\n\nStopped Background Receivers:\n");
		for (Iterator it = stoppedSystems.iterator(); it.hasNext();)
		{
			tmpBuffer.append("\n").append(it.next());
		}
		if (tmpBuffer.length() == 0)
		{
			retBuffer.append("-");
		}
		else
		{
			retBuffer.append(tmpBuffer);
		}

		tmpBuffer = new StringBuffer();
		String system = null;
		retBuffer.append("\n\nKilled Background Receivers:\n");
		for (Iterator it = allSystems.iterator(); it.hasNext();)
		{
			system = (String) it.next();
			if ((!runningSystems.contains(system))
					&& (!stoppedSystems.contains(system)))
			{
				tmpBuffer.append("\n").append(it.next());
			}
		}
		if (tmpBuffer.length() == 0)
		{
			retBuffer.append("-");
		}
		else
		{
			retBuffer.append(tmpBuffer);
		}

		if (ReceiverService.getInstance().isWatchdogRunning())
		{
			retBuffer.append("\n\nWatchdog is running");
		}
		else
		{
			retBuffer.append("\n\nWatchdog is NOT running !!!");
		}

		return retBuffer.toString();
	}

	/**
	 * Returns some information about the state of the Java Virtual Machine,
	 * containing memory consumption and information about the environment.
	 * 
	 * @return some information about the state of the Java Virtual Machine
	 */
	public String getJVMStatus()
	{
		StringBuffer retBuffer = new StringBuffer();

		retBuffer.append("Resources:");
		Runtime runtime = Runtime.getRuntime();
		runtime.gc();
		retBuffer.append("\n\nMaximum memory available:        "
				+ NumberFormat.getInstance().format(runtime.maxMemory())
				+ " bytes");
		retBuffer.append("\nMemory currently used:           "
				+ NumberFormat.getInstance().format(runtime.totalMemory())
				+ " bytes");
		retBuffer.append("\nFree memory currently available: "
				+ NumberFormat.getInstance().format(runtime.freeMemory())
				+ " bytes");
		retBuffer.append("\nAvailable processors:            "
				+ runtime.availableProcessors());

		retBuffer.append("\n\nEnvironment:\n");
		TreeSet envKeys = new TreeSet(System.getProperties().keySet());
		String key = null;
		for (Iterator it = envKeys.iterator(); it.hasNext();)
		{
			key = (String) it.next();
			retBuffer.append("\n" + key + "=" + System.getProperty(key));
		}

		return retBuffer.toString();
	}

	/**
	 * Demands the stop of a background receiver.
	 * 
	 * @param system the name of the system
	 * @return a confirmation or an error message
	 */
	public String demandStopBackgroundReceiver(String system)
	{
		try
		{
			ReceiverThreadManager manager = ReceiverThreadManager.getInstance();
			if (!manager.getRunningSystems().contains(system))
			{
				return new StringBuffer(system).append(
						" is not a started background receiver").toString();
			}

			manager.demandStopReceiverThread(system);
			return "Stop for background receiver " + system + " demanded";
		}
		catch (XException e)
		{
			return e.getMessage();
		}
	}

	/**
	 * Starts a background receiver.
	 * 
	 * @param system the name of the system
	 * @return a confirmation or an error message
	 */
	public String startBackgroundReceiver(String system)
	{
		try
		{
			ReceiverThreadManager manager = ReceiverThreadManager.getInstance();
			if (!manager.getAllSystems().contains(system))
			{
				return new StringBuffer(system).append(
						" is not a configured configured background receiver")
						.toString();
			}
			if (manager.getRunningSystems().contains(system))
			{
				return new StringBuffer(system).append(" is already started")
						.toString();
			}

			manager.startReceiverThread(system);
		}
		catch (XException e)
		{
			return e.getMessage();
		}
		return "Background receiver " + system + " started";
	}

	/**
	 * Reads the filenames that are currently in the Deleted Message Store.
	 * 
	 * @return the filenames that are currently in the Deleted Message Store
	 */
	public String getDeletedMessageFilenames()
	{
		try
		{
			String[] filenames = DeletedMessageStore
					.getDeletedMessageFilenames();

			StringBuffer retBuffer = new StringBuffer();
			for (int i = 0; i < filenames.length; i++)
			{
				retBuffer.append(filenames[i]).append(Constants.LINE_SEPERATOR);
			}
			return retBuffer.toString();
		}
		catch (XException e)
		{
			return e.getMessage();
		}
	}

	/**
	 * Resends a message from the Deleted Message Store
	 * 
	 * @param filename the filename of the deleted message
	 * @return a message of success or failure
	 */
	public String resendDeletedMessage(String filename)
	{
		return DeletedMessageStore.resendDeletedMessage(filename);
	}

	/**
	 * Reads a file.
	 * 
	 * @param filename the name of the file including the directory
	 * @param maxLength maximum length to read in kbyte.
	 * @return the content of the file or an error message
	 */
	private String readFile(String filename, int maxLength)
	{
		StringBuffer retBuffer = new StringBuffer();
		File sourceFile = new File(filename);
		String zeile;

		try
		{
			BufferedReader buffReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(sourceFile)));

			if ((zeile = buffReader.readLine()) != null)
			{
				retBuffer.append(zeile);
			} // if((zeile = buffReader.readLine()) != null)

			while ((zeile = buffReader.readLine()) != null)
			{
				retBuffer.append(Constants.LINE_SEPERATOR);
				retBuffer.append(zeile);
			} // while ((zeile = buffReader.readLine()) != null)
			buffReader.close();
		}
		catch (IOException e)
		{
			return XException.getExceptionInformation();
		}

		if (maxLength > 0)
		{
			int bufferLength = retBuffer.length();
			int endLength = bufferLength - maxLength * 1024;
			retBuffer.delete(0, endLength);
		} // if (expectedLength > 0)
		return retBuffer.toString();
	}

}
