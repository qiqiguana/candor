package net.sf.xbus.bootstrap;

import java.util.Vector;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.reflection.ReflectionSupport;
import net.sf.xbus.base.core.reflection.XBUSClassLoader;

/**
 * <code>ReceiverSingleBootstrap</code> starts the
 * {@link net.sf.xbus.technical.ReceiverSingle}, using the
 * {@link net.sf.xbus.base.core.reflection.XBUSClassLoader}.
 */
public class ReceiverSingleBootstrap
{
	/**
	 * Starts the {@link net.sf.xbus.technical.ReceiverSingle}
	 * 
	 * @param args list of systems which receivers shall be started
	 */
	public static void main(String[] args)
	{
		Vector argVector = new Vector();
		for (int i = 0; i < args.length; i++)
		{
			argVector.add(args[i]);
		}

		Thread.currentThread().setContextClassLoader(
				XBUSClassLoader.getInstance(Thread.currentThread()
						.getContextClassLoader()));

		try
		{
			Object obj = ReflectionSupport
					.createObject("net.sf.xbus.technical.ReceiverSingle");

			Class[] parameterTypes = new Class[]
			{Vector.class};
			Object[] arguments = new Object[]
			{argVector};

			ReflectionSupport.callMethod("start", obj, parameterTypes,
					arguments);
		}
		catch (XException e)
		{
			// System.exit(1);
		}
	}
}
