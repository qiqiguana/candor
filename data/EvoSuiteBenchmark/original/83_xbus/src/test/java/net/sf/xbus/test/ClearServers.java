package net.sf.xbus.test;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.jms.QueueReceiver;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.trace.Trace;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.mq.MQConnection;

/**
 * Deletes all messages from MQ and mail servers.
 */
public class ClearServers
{
	/**
	 * Cleaning some servers
	 */
	public void deleteMessages()
	{
		try
		{
			Configuration config = Configuration.getInstance();
			List sections = config.getSections(Constants.CHAPTER_SYSTEM);
			String system = null;
			String receiver = null;
			for (Iterator it = sections.iterator(); it.hasNext();)
			{
				system = (String) it.next();
				receiver = config.getValueOptional(Constants.CHAPTER_SYSTEM,
						system, "Receiver");
				if ((receiver != null) && (receiver.startsWith("MQ")))
				{
					deleteMQMessages(system);
				}
				else if ((receiver != null) && (receiver.startsWith("POP3")))
				{
					deleteMailMessages(system);
				}
			}
		}
		catch (XException e)
		{
			// do nothing
		}
	}

	private void deleteMQMessages(String system)
	{
		try
		{
			Thread.sleep(500);
			Trace.info("Checking " + system + " for messages");
			MQConnection mqCon = MQConnection.getInstance();
			mqCon.open();
			QueueReceiver mqRec = mqCon.getReceiver(new XBUSSystem(system));
			while (mqRec.receiveNoWait() != null)
			{
				Trace.info("Deleting message from " + system);
			}
			mqCon.commit();
			mqCon.close();
		}
		catch (Exception e)
		{
			Trace.error(e);
		}
	}

	private void deleteMailMessages(String system)
	{
		try
		{
			Trace.info("Checking " + system + " for messages");
			Configuration config = Configuration.getInstance();
			Session session = Session.getInstance(new Properties());
			Store store = session.getStore("pop3");
			store.connect(config.getValue(Constants.CHAPTER_SYSTEM, system,
					"Host"), config.getValue(Constants.CHAPTER_SYSTEM, system,
					"User"), config.getValue(Constants.CHAPTER_SYSTEM, system,
					"Password"));
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_WRITE);
			javax.mail.Message[] messages = folder.getMessages();
			for (int i = 0; i < messages.length; i++)
			{
				messages[i].setFlag(Flags.Flag.DELETED, true);
				Trace.info("Deleting message from " + system);
			}
			folder.close(true);
			store.close();
		}
		catch (Exception e)
		{
			Trace.error(e);
		}
	}
}
