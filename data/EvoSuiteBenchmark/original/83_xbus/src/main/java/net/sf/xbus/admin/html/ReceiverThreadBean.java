package net.sf.xbus.admin.html;

import java.util.Iterator;
import java.util.Set;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.technical.ReceiverThreadManager;

/**
 * TODO Kommentierung
 */
public class ReceiverThreadBean
{
	public String getRunningReceiverThreads()
	{
		StringBuffer buf = new StringBuffer(
				"<option selected>--------------------");

		try
		{
			Set runningSystems = ReceiverThreadManager.getInstance()
					.getRunningSystems();

			for (Iterator it = runningSystems.iterator(); it.hasNext();)
			{
				buf.append("<option>");
				buf.append((String) it.next());
			}
		}
		catch (XException e)
		{
			// do nothing
		}
		return buf.toString();
	}

	public String getNotRunningReceiverThreads()
	{
		StringBuffer buf = new StringBuffer(
				"<option selected>--------------------");

		try
		{
			ReceiverThreadManager manager = ReceiverThreadManager.getInstance();
			Set allSystems = manager.getAllSystems();
			Set runningSystems = manager.getRunningSystems();

			String name = null;
			for (Iterator it = allSystems.iterator(); it.hasNext();)
			{
				name = (String) it.next();
				if (!runningSystems.contains(name))
				{
					buf.append("<option>");
					buf.append(name);
				}
			}
		}
		catch (XException e)
		{
			// do nothing
		}

		return buf.toString();
	}

}
