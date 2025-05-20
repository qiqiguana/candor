package net.sf.xbus.sample;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.bootstrap.JavaReceiverBootstrap;

/**
 * This is an example how to invoke the
 * {@link net.sf.xbus.bootstrap.JavaReceiverBootstrap}.
 */
public class JavaReceiverSample
{
	// private static final String REQUEST_STRING = "Hello World!";
	private static final String REQUEST_STRING = "Example 4";
	private static final String DESTINATION = "Example4InData";

	/**
	 * This is an example how to invoke the
	 * {@link net.sf.xbus.bootstrap.JavaReceiverBootstrap}.
	 */
	public static void main(String[] args)
	{
		try
		{
			System.out.println("Starting JavaReceiverSample");
			System.out.println("");
			System.out.println("Request:  " + REQUEST_STRING);
			System.out.println("");

			Object response = JavaReceiverBootstrap.receive(DESTINATION,
					REQUEST_STRING);

			System.out.println();
			if (response == null)
			{
				System.out.println("Response is <null>");
			}
			else
			{
				System.out.println("Response: " + response.toString());
			}
		}
		catch (XException e)
		{
			e.printStackTrace();
		}
	}
}
