package net.sf.xbus.bootstrap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.reflection.ReflectionSupport;

/**
 * The <code>ReceiverServiceServletListener</code> receives notifications on
 * startup and shutdown of the servlet container. Its task is to start and stop
 * the <code>ReceiverService</code>.
 */
public class ReceiverServiceServletListener implements ServletContextListener
{

	/**
	 * Starts the <code>ReceiverService</code> on startup of the servlet
	 * container.
	 */
	public void contextInitialized(ServletContextEvent arg0)
	{
		ReceiverServiceBootstrap.start(true);
	}

	/**
	 * Stops the <code>ReceiverService</code> on shutdown of the servlet
	 * container.
	 */
	public void contextDestroyed(ServletContextEvent arg0)
	{
		try
		{
			Object rs = ReceiverServiceBootstrap.getInstance();
			ReflectionSupport.callMethod("stop", rs, null, null);
		}
		catch (XException e)
		{
			/*
			 * do nothing
			 */
		}
	}

}
