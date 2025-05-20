package net.sf.xbus.technical;

import net.sf.xbus.admin.jmx.AdministratorJMXConnector;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.trace.Trace;

/**
 * TODO Kommentierung
 */
public class ReceiverServiceAdministrator
{
	public static void shutdown() throws XException
	{
		Trace.always("Entering ReceiverServiceAdministrator.shutdown");

		String msg = AdministratorJMXConnector.stopReceiverService();

		Trace.always(msg);
	}

	public static void restart() throws XException
	{
		Trace.always("Entering ReceiverServiceAdministrator.restart");

		String msg = AdministratorJMXConnector.restartReceiverService();

		Trace.always(msg);
	}

}
