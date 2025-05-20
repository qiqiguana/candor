package net.sf.xbus.base.core.tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Starts a Java application after a given amount of time.
 */
public class DelayedStart
{

	/**
	 * Starts a Java application after a given amount of time.
	 * 
	 * @param args at least 2 parameters must been given: delayTime
	 *            applicationName args*
	 */
	public static void main(String[] args)
	{
		new DelayedStart(args);
	}

	private DelayedStart(String[] args)
	{
		if (args.length < 2)
		{
			printUsage();
		}

		/*
		 * Wait some time
		 */
		long delaytime = new Long(args[0]).longValue();
		try
		{
			System.out.println("Waiting " + delaytime + " seconds ...");
			Thread.sleep(delaytime * 1000);
		}
		catch (InterruptedException e)
		{
			// do nothing
		}

		String applicationName = args[1];
		System.out.println("Starting " + applicationName + " ...");

		/*
		 * Build new array with parameters
		 */
		String[] newArgs = null;
		if (args.length > 2)
		{
			newArgs = new String[args.length - 2];
			for (int i = 2; i < args.length; i++)
			{
				newArgs[i - 2] = args[i];
			}
		}
		else
		{
			newArgs = new String[0];
		}

		/*
		 * Start main method of the application
		 */
		try
		{
			Class applicationClass = Class.forName(applicationName);
			Method mainMethod = null;
			Class[] parameters = new Class[]
			{String[].class};
			Object[] arguments = new Object[]
			{newArgs};
			mainMethod = applicationClass.getMethod("main", parameters);
			mainMethod.invoke(applicationClass, arguments);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}

	}

	private void printUsage()
	{
		System.out
				.println("At least 2 parameters must been given: delayTime applicationName args*");
		System.exit(1);
	}
}
