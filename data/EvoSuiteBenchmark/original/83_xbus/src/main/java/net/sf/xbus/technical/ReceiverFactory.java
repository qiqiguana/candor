package net.sf.xbus.technical;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.reflection.ReflectionSupport;
import net.sf.xbus.base.xbussystem.XBUSSystem;

/**
 * The class <code>ReceiverFactory</code> is used to dynamically create
 * receivers to receive messages from neighbor-systems. Both
 * <code>ReceiverThreads</code> and <code>ReceiverSingles</code> can be
 * created.
 * <p>
 * It implements the <b>Factory</b>-Design-Pattern.
 */
public class ReceiverFactory
{
	/**
	 * The appropriate <code>ReceiverThread</code> for the given system will
	 * be created.
	 */
	public static ReceiverThreadBase createReceiverThread(String receiverName,
			String system) throws XException
	{
		String receiverClass = Configuration.getClass("Receiver", receiverName);

		Class[] conArgsClass = new Class[]
		{ReflectionSupport
				.classForName("net.sf.xbus.base.xbussystem.XBUSSystem")};
		Object[] conArgs = new Object[]
		{new XBUSSystem(system)};

		return (ReceiverThreadBase) ReflectionSupport.createObject(
				receiverClass, conArgsClass, conArgs);
	}

	/**
	 * The appropriate <code>ReceiverSingle</code> for the given system will
	 * be created.
	 */
	public static ReceiverSingleInterface createReceiverSingle(
			String receiverName) throws XException
	{
		String receiverClassName = Configuration.getClass("Receiver",
				receiverName);

		return (ReceiverSingleInterface) ReflectionSupport
				.createObject(receiverClassName);
	}
}
