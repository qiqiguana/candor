package net.sf.xbus.tools;

import java.util.Date;
import java.util.Enumeration;

import javax.jms.QueueBrowser;
import javax.jms.TextMessage;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.mq.MQConnection;

/**
 * The <code>QBrowser</code> uses a <code>QueueBrowser</code> interface to
 * look a messages on a queue without removing them and to print them in the
 * specially format on the console. The browse methods return a
 * java.util.Enumeration that is used to scan the queue's messages. Messages may
 * be arriving and expiring while the scan is done. It uses following format for
 * messages output:
 * 
 * <pre>
 * &quot;yyyy-MM-dd HH:mm:ss,SSS message&quot;
 * </pre>
 */

public class QBrowser
{
	/**
	 * The <code>main</code> method creates a JMS-QueueBrowser for the given
	 * queue and prints each message from the enumeration on the console in the
	 * following format:
	 * 
	 * <pre>
	 * &quot;yyyy-MM-dd HH:mm:ss,SSS message&quot;
	 * </pre>
	 */
	public static void main(String args[]) throws Exception
	{
		if (args.length < 1)
		{
			System.err.println("Usage: java QBrowser Queuename");
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
			Date timestamp = new Date();

			Enumeration e = qb.getEnumeration();

			while (e.hasMoreElements())
			{
				message = (TextMessage) e.nextElement();
				timestamp.setTime(message.getJMSTimestamp());
				System.out.println(Constants.getDateFormat().format(timestamp)
						+ " " + message.getText());
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
