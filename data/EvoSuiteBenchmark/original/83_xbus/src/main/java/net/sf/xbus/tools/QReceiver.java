package net.sf.xbus.tools;

import javax.jms.QueueReceiver;
import javax.jms.TextMessage;

import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.mq.MQConnection;

/**
 * The <code>QReceiver</code> uses a <code>QueueReceiver</code> interface to
 * receive messages that have been delivered to a queue and to print them
 * together with their ordinal number on the console.
 */

public class QReceiver
{
	static int mCommitRate = 1;

	/**
	 * The <code>main</code> method creates a JMS QueueReceiver for the given
	 * queue, receives messages that have been delivered to this queue within
	 * the specified timeout interval and prints them together with their
	 * ordinal number on the console.
	 */
	public static void main(String args[]) throws Exception
	{
		if (args.length != 2)
		{
			System.err.println("Usage: java QReceiver Queuename #Messages");
			System.exit(1);
		}
		String logQueuename = args[0];

		int numMessages = 0;
		try
		{
			numMessages = Integer.parseInt(args[1]);
		}
		catch (NumberFormatException e)
		{
			System.err.println("Usage: java QReceiver Queuename _#Messages_");
			System.exit(1);
		}

		try
		{

			// Use the static MQConnection.newInstance() method to instantiate a
			// new connection
			// for messagequeues and creates a JMS QueueReceiver for the given
			// queue.
			MQConnection mqCon = MQConnection.getInstance();
			QueueReceiver qr = mqCon.getReceiver(new XBUSSystem(logQueuename));

			int commitCounter = 0;
			int messageCounter = 0;

			// Use a TextMessage object to receive a message containing a
			// java.lang.String
			// in read-only mode.
			TextMessage message = null;

			boolean messageFound = false;
			while (!messageFound && (messageCounter < numMessages))
			{
				try
				{
					// Use this method to receive the next message that arrives
					// within the specified timeout interval.
					// This call blocks until a message arrives, the timeout
					// expires,
					// or this message consumer is closed.
					message = (TextMessage) qr.receive(1000);

					if (message == null)
					{
						System.out.println("Keine Nachricht vorhanden!");
						messageFound = true;
					}
					else
					{
						messageCounter++;
						commitCounter++;
						System.out.println("Nachricht-Nr. " + messageCounter
								+ " empfangen: " + message.getText());

						if (commitCounter == mCommitRate)
						{
							commitCounter = 0;
							mqCon.commit();
						}
					}
				}
				catch (Exception exc)
				{
					System.out.println("Exception gefangen!");
					exc.printStackTrace();
					mqCon.rollback();
					continue;
				}
			}

			mqCon.commit();
			mqCon.close();
		}
		catch (Exception exc)
		{
			System.out.println("Exception gefangen!");
			exc.printStackTrace();
		}
		System.exit(0);
	}
}
