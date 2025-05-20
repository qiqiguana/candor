package net.sf.xbus.technical;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.reflection.ReflectionSupport;
import net.sf.xbus.base.xbussystem.XBUSSystem;

/**
 * The class <code>SenderFactory</code> is used to dynamically create senders
 * to neighbor systems.
 * <p />
 * It implements the <b>Factory</b>-Design-Pattern.
 */
public class SenderFactory
{
	/**
	 * The appropriate {@link Sender} for the given destination will be created.
	 * The name of the class is read from the
	 * {@link net.sf.xbus.base.core.config.Configuration}.
	 * 
	 * @param destination name of the interface definition
	 * @throws XException if something goes wrong
	 */
	public static Sender createSender(XBUSSystem destination) throws XException
	{
		Configuration config = Configuration.getInstance();
		String senderClassShortname = config.getValue(Constants.CHAPTER_SYSTEM,
				destination.getName(), "Sender");
		String senderClass = Configuration.getClass("Sender",
				senderClassShortname);

		Class[] conArgsClass = new Class[]
		{destination.getClass()};
		Object[] conArgs = new Object[]
		{destination};
		return (Sender) ReflectionSupport.createObject(senderClass,
				conArgsClass, conArgs);
	}
}
