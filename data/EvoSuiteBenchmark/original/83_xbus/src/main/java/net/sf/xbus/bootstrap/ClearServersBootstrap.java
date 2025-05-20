package net.sf.xbus.bootstrap;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.reflection.ReflectionSupport;
import net.sf.xbus.base.core.reflection.XBUSClassLoader;

/**
 * Deletes all messages on MQ and mail servers. Needed to initialize the
 * automated tests.
 */
public class ClearServersBootstrap
{
	/**
	 * Deletes all messages on MQ and mail servers. Needed to initialize the
	 * automated tests.
	 */
	public static void main(String[] args)
	{
		Thread.currentThread().setContextClassLoader(
				XBUSClassLoader.getInstance(null));

		try
		{
			Object obj = ReflectionSupport
					.createObject("net.sf.xbus.test.ClearServers");
			ReflectionSupport.callMethod("deleteMessages", obj, null, null);
		}
		catch (XException e)
		{
			// do nothing
		}
	}
}
