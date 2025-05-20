package net.sf.xbus.technical;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import net.sf.xbus.application.PreProcessor;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.trace.Trace;

/**
 * <code>ReceiverSingle</code> is called to receive messages from systems one
 * time. After all receivers has been invoked the program ends.
 */
public class ReceiverSingle
{
	/**
	 * 
	 * @param args list of systems from which messages shall be received.
	 */
	public void start(Vector args)
	{
		// ensure that a systems was given in args[]
		if ((args == null) || (args.size() == 0))
		{
			Trace.error("At least one system must be specified.");
			System.exit(1);
		}

		Hashtable receivers = null;
		try
		{
			receivers = createReceiverTable(args);
			callReceivers(receivers);
		}
		catch (Exception e)
		{
			Trace.error(e);
			System.exit(1);
		}
	}

	/**
	 * The given list of systems will be sorted after the appropriate receivers,
	 * read out of the <code>Configuration</code>. The keys of the returned
	 * <code>Hashtable</code> are the short names of the receivers. The value
	 * for every receiver is a list of the belonging systems.
	 * 
	 * @param args list of systems from which messages shall be received.
	 * @return receivers and their belonging systems
	 * @throws XException if the systems contain a <code>ReceiverThread</code>
	 */
	private Hashtable createReceiverTable(Vector args) throws XException
	{
		Hashtable receivers = new Hashtable();
		String systemName = null;
		String receiver = null;
		Vector systems = null;

		Configuration config = Configuration.getInstance();

		// nested 'for'loop traverses for searching all systems
		// be read (terates over the elements of an array)
		for (int i = 0; i < args.size(); i++)
		{
			systemName = (String) args.elementAt(i);
			receiver = config.getValue(Constants.CHAPTER_SYSTEM, systemName,
					"Receiver");
			if (receiver.endsWith("Thread"))
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_TECHNICAL, "11");
			}
			if (receivers.containsKey(receiver))
			{
				systems = (Vector) receivers.get(receiver);
			}
			else
			{
				systems = new Vector();
			}
			systems.add(systemName);
			receivers.put(receiver, systems);
		}
		return receivers;
	}

	/**
	 * For every receiver of the given <code>Hashtable</code> the
	 * <code>receive</code> method is called with the list of belonging
	 * systems as parameter.
	 * 
	 * @param receivers <code>Hashtable</code> with receivers and their
	 *            belonging systems
	 * @throws XException if a receiver cannot be instanciated or messages
	 *             cannot be received
	 */
	private void callReceivers(Hashtable receivers) throws XException
	{
		String receiverName = null;
		Vector systems = null;
		String systemName = null;
		ReceiverSingleInterface receiver = null;

		for (Enumeration e = receivers.keys(); e.hasMoreElements();)
		{
			receiverName = (String) e.nextElement();
			systems = (Vector) (receivers.get(receiverName));

			for (Iterator it = systems.iterator(); it.hasNext();)
			{
				systemName = (String) it.next();
				PreProcessor.process(systemName);
				receiver = ReceiverFactory.createReceiverSingle(receiverName);
				receiver.receive(systemName);
			}
		}
	}
}
