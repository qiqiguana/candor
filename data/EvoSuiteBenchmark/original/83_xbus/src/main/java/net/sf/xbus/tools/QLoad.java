package net.sf.xbus.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Vector;

import javax.jms.JMSException;
import javax.jms.QueueSender;
import javax.jms.TextMessage;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.mq.MQConnection;

/**
 * The <code>QLoad</code> removes all separators between the messages and,
 * using a <code>QueueSender</code> interface, sends these messages in their
 * initial form to a queue.
 */
public class QLoad
{
	/**
	 * The <code>main</code> method reads, using BufferedReader, text from a
	 * character-input stream, writes it to the StringBuffer, removes all
	 * separators between the messages and sends these messages in their initial
	 * form to a queue.
	 */
	public static void main(String args[]) throws Exception
	{
		if (args.length != 1)
		{
			System.err.println("Usage: java QLoad Queuename");
			System.exit(1);
		}
		String logQueuename = args[0];

		StringBuffer messages = new StringBuffer();
		String zeile;

		try
		{
			BufferedReader instream = new BufferedReader(new InputStreamReader(
					System.in));
			while ((zeile = instream.readLine()) != null)
			{
				messages.append(zeile);
			}
			instream.close();
		}
		catch (IOException e)
		{
			System.out.println("Exception gefangen!");
			e.printStackTrace();
			System.exit(1);
		}

		Vector messageVec = new Vector();

		String tmp = new String(messages);
		int i;
		while ((i = tmp.indexOf(Constants.QUEUE_DUMP_DELIMITER)) >= 0)
		{
			messageVec.add(tmp.substring(0, i));
			messages.delete(0, i + Constants.QUEUE_DUMP_DELIMITER.length());
			tmp = new String(messages);
		}
		/*
		 * StringTokenizer st = new StringTokenizer(new String(messages),
		 * DELIMITER); while (st.hasMoreTokens()) { String mes = st.nextToken();
		 * System.out.println(mes);
		 * System.out.println("------------------------------------------------------------");
		 * messageVec.add(mes); }
		 */
		try
		{
			// Use the static MQConnection.newInstance() method to instantiate a
			// new connection
			// for messagequeues and creates a JMS QueueSender for the given
			// queue.
			MQConnection mqCon = MQConnection.getInstance();
			QueueSender qs = mqCon.getSender(new XBUSSystem(logQueuename));

			// Create a JMS-TextMessage for each element of the vector and send
			// this message to the queue.
			// Use the QueueSender's default delivery mode, timeToLive and
			// priority.
			for (Enumeration e = messageVec.elements(); e.hasMoreElements();)
			{
				try
				{
					TextMessage message = mqCon.createTextMessage();
					message.setText((String) e.nextElement());
					qs.send(message);
				}
				catch (JMSException ex)
				{
					System.out.println("Exception gefangen!");
					ex.printStackTrace();
					System.exit(1);
				}
			}

			mqCon.commit();
			mqCon.close();
		}
		catch (Exception exc)
		{
			System.out.println("Exception gefangen!");
			exc.printStackTrace();
			System.exit(1);
		}

		System.exit(0);
	}
}
