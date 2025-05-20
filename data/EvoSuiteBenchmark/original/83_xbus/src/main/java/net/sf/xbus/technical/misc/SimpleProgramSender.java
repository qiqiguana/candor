/*
 * Created on 26.10.2004
 */
package net.sf.xbus.technical.misc;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.TextSender;

/**
 * <code>SimpleProgramSender</code> calls an external program. Program name
 * and parameters are read out of the configuration. It is simple in that sense
 * that it does not support specific timeout requirements - in contrast to the
 * {@link ProgramSender}.
 * 
 * @author Stephan Düwel
 */
public class SimpleProgramSender extends ProgramSender implements TextSender
{

	/**
	 * @see ProgramSender#ProgramSender(XBUSSystem)
	 */
	public SimpleProgramSender(XBUSSystem destination)
	{
		super(destination);
	}

	/**
	 * @see ProgramSender#execute(String, String)
	 * @see ProgramCaller#call()
	 */
	public String execute(String function, String callData) throws XException
	{
		String programName = getProgramName();
		List parameters = getParameters();

		String[] cmdarray = new String[parameters.size() + 1];
		cmdarray[0] = programName;
		for (int i = 0; i < parameters.size(); i++)
		{
			cmdarray[i + 1] = (String) parameters.get(i);
		} // for (int i = 0; i < parameters.size(); i++)

		StringBuffer message = new StringBuffer();
		message.append("Calling:");
		for (int i = 0; i < cmdarray.length; i++)
		{
			message.append(" ");
			message.append(cmdarray[i]);
		} // for (int i=0; i<cmdarray.length; i++)

		Process proc = null;
		try
		{
			proc = Runtime.getRuntime().exec(cmdarray);
		} // try
		catch (Exception e)
		{
			List params = new Vector(1);
			params.add(cmdarray[0]);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_MISC, "6", e, params);
		} // catch (Exception e)

		InputStreamReader reader = new InputStreamReader(proc.getInputStream());
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
			} // while ((character = reader.read()) >= 0)
		} // try
		catch (IOException e)
		{
			List params = new Vector(1);
			params.add(cmdarray[0]);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_MISC, "7", e, params);
		} // catch (IOException e)
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (IOException e1)
				{
					// do nothing
				}
			}
		}

		try
		{
			proc.waitFor();
		} // try
		catch (InterruptedException e)
		{
			List params = new Vector(1);
			params.add(cmdarray[0]);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_MISC, "7", e, params);
		} // catch (InterruptedException e)

		return buffer.toString();
	} // execute(String function, String callData)

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
	} // getProgramName()

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
		} // while (parameter != null)
		return parameters;
	} // getParameters()

} // SimpleProgramSender
