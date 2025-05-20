package net.sf.xbus.tools;

import java.util.Enumeration;

import javax.jms.QueueBrowser;
import javax.jms.TextMessage;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.mq.MQConnection;

/**
 * The <code>QDump</code> uses a <code>QueueBrowser</code> interface to look
 * a messages on a queue without removing them and to print them one after
 * another on the console. The browse methods return a java.util.Enumeration
 * that is used to scan the queue's messages. The output of the messages are
 * separated from each other by following string:
 * 
 * <pre>
 * &quot;||||nextMessage||||&quot;
 * </pre>
 */

public class QDump
{
	/**
	 * The <code>main</code> method creates a JMS-QueueBrowser for the given
	 * queue and prints each message from the enumeration after other on the
	 * console. The output of the messages are separated from each other by
	 * following string:
	 * 
	 * <pre>
	 * &quot;||||nextMessage||||&quot;
	 * </pre>
	 */
	public static void main(String args[]) throws Exception
	{
		if (args.length < 1)
		{
			System.err.println("Usage: java QDump Queuename");
			System.exit(1);
		}
		String logQueuename = args[0];

		try
		{
			// Use the static MQConnection.newInstance() method to instantiate a
			// new
			// connection for messagequeues and creates a JMS-QueueBrowser for
			// the given queue.
			MQConnection mqCon = MQConnection.getInstance();
			QueueBrowser qb = mqCon.getBrowser(new XBUSSystem(logQueuename));

			TextMessage message = null;

			Enumeration e = qb.getEnumeration();

			while (e.hasMoreElements())
			{
				message = (TextMessage) e.nextElement();
				System.out.print(message.getText());
				System.out.print(Constants.QUEUE_DUMP_DELIMITER);
			}

			mqCon.commit();
			mqCon.close();
		}
		catch (Exception exc)
		{
			System.out.println("Exception gefangen");
			exc.printStackTrace();
			System.exit(1);
		}
		System.exit(0);
	}

}
