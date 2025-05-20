package net.sf.xbus.technical.misc;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.timeoutcall.Callable;
import net.sf.xbus.base.xbussystem.XBUSSystem;

/**
 * Used by {@link ProgramSender#execute(String, String)}together with the
 * {@link net.sf.xbus.base.core.timeoutcall.TimedCallable}to be able to stop
 * the execution of the program after a timeout.
 */
public class ProgramCaller implements Callable
{
	private XBUSSystem mDestination = null;
	private Process mProcess = null;

	/**
	 * Stores the destination.
	 * 
	 * @param destination name of the interface definition
	 * @param callData ignored
	 */
	public ProgramCaller(XBUSSystem destination, String callData)
	{
		mDestination = destination;
	}

	/**
	 * Calls an external program. Program name and parameters are read out of
	 * the configuration.
	 */
	public Object call() throws XException
	{
		String programName = getProgramName();
		List parameters = getParameters();

		String[] cmdarray = new String[parameters.size() + 1];
		cmdarray[0] = programName;
		for (int i = 0; i < parameters.size(); i++)
		{
			cmdarray[i + 1] = (String) parameters.get(i);
		}

		StringBuffer message = new StringBuffer();
		message.append("Calling:");
		for (int i = 0; i < cmdarray.length; i++)
		{
			message.append(" ");
			message.append(cmdarray[i]);
		}

		try
		{
			mProcess = Runtime.getRuntime().exec(cmdarray);
		}
		catch (Exception e)
		{
			List params = new Vector(1);
			params.add(cmdarray[0]);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_MISC, "6", e, params);
		}

		// Input piped to the called process
		List internalCommands = getProcessInputs();
		if (internalCommands != null && internalCommands.size() > 0)
		{
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					mProcess.getOutputStream()));
			try
			{
				for (int i = 0; i < internalCommands.size(); i++)
				{
					writer.write((String) internalCommands.get(i));
					writer.newLine();
				}
				writer.flush();
			}
			catch (IOException e)
			{
				List params = new Vector(1);
				params.add(cmdarray[0]);
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_MISC, "7", e, params);
			}
			finally
			{
				try
				{
					writer.close();
				}
				catch (IOException e)
				{
					// do nothing
				}
			}
		}

		InputStreamReader reader = new InputStreamReader(mProcess
				.getInputStream());
		int character = 0;
		byte[] ba = new byte[1];
		String oneChar = null;
		StringBuffer buffer = new StringBuffer();

		try
		{
			while ((character = reader.read()) >= 0)
			{
				ba[0] = new Integer(character).byteValue();
				oneChar = new String(ba);
				buffer.append(oneChar);
				System.out.print(oneChar);
			}
		}
		catch (IOException e)
		{
			List params = new Vector(1);
			params.add(cmdarray[0]);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_MISC, "7", e, params);
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch (IOException e)
			{
				// do nothing
			}
		}

		try
		{
			mProcess.waitFor();
		}
		catch (InterruptedException e)
		{
			List params = new Vector(1);
			params.add(cmdarray[0]);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_MISC, "7", e, params);
		}

		return buffer.toString();
	}

	/**
	 * Stopping the execution of the program by destroying the program.
	 */
	public void stop()
	{
		if (mProcess != null)
		{
			mProcess.destroy();
		}
	}

	/**
	 * Reading the name of the program out of the configuration.The program name
	 * may contain markers for additional address information which will be
	 * replaced by their actual values.
	 * 
	 * @return name ot the program to be called
	 * @throws XException if something goes wrong
	 */
	private String getProgramName() throws XException
	{
		Configuration config = Configuration.getInstance();

		String programName = config.getValue(Constants.CHAPTER_SYSTEM,
				mDestination.getName(), "ProgramName");
		programName = mDestination.replaceAllMarkers(programName)[0];

		return programName;
	}

	/**
	 * Reading the the list of parameters out of the configuration. Parameters
	 * may contain markers for additional address information which will be
	 * replaced by their actual values.
	 * 
	 * @return list of parameters
	 * @throws XException if something something goes wrong
	 */
	private List getParameters() throws XException
	{
		Vector parameters = new Vector();

		Configuration config = Configuration.getInstance();

		String parameter = config.getValueOptional(Constants.CHAPTER_SYSTEM,
				mDestination.getName(), "Parameter1");
		int i = 1;
		while (parameter != null)
		{
			i++;
			parameter = mDestination.replaceAllMarkers(parameter)[0];
			parameters.add(parameter);
			parameter = config.getValueOptional(Constants.CHAPTER_SYSTEM,
					mDestination.getName(), "Parameter" + i);
		}
		return parameters;
	}

	/**
	 * @return
	 */
	private List getProcessInputs() throws XException
	{
		Vector processInputs = new Vector();

		Configuration config = Configuration.getInstance();

		String processInput = config.getValueOptional(Constants.CHAPTER_SYSTEM,
				mDestination.getName(), "ProcessInput1");
		int i = 1;
		while (processInput != null)
		{
			i++;
			String[] pInput = mDestination.replaceAllMarkers(processInput);
			// Array for broadcast
			for (int j = 0; j < pInput.length; j++)
				processInputs.add(pInput[j]);
			processInput = config.getValueOptional(Constants.CHAPTER_SYSTEM,
					mDestination.getName(), "ProcessInput" + i);
		}
		return processInputs;
	}

}
