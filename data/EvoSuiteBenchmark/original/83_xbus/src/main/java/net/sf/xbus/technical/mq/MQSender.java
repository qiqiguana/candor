package net.sf.xbus.technical.mq;

import javax.jms.JMSException;
import javax.jms.QueueSender;
import javax.jms.TextMessage;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.Sender;
import net.sf.xbus.technical.TextSender;

/**
 * The <code>MQSender</code> sends a message to a message-queue.
 */
public class MQSender implements Sender, TextSender
{
	private QueueSender mSender;
	private MQConnection mMQConnection;

	/**
	 * Opens the connection to the queue-manager. It uses the
	 * {@link net.sf.xbus.technical.mq.MQConnection}.
	 */
	public MQSender(XBUSSystem destination) throws XException
	{
		openConnection(destination);
	}

	/**
	 * Sends the <code>callData</code> to the message-queue.
	 * <code>function</code> is ignored.
	 * 
	 * @return <code>null</code>
	 */
	public String execute(String function, String callData) throws XException
	{
		if (callData == null)
			callData = "";

		try
		{
			mSender.setPriority(4);

			TextMessage message = mMQConnection.createTextMessage();
			message.setText(callData);
			mSender.send(message);
		}
		catch (JMSException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL, Constants.PACKAGE_TECHNICAL_MQ,
					"0", e);
		}
		return null;
	}

	private void openConnection(XBUSSystem destination) throws XException
	{
		mMQConnection = MQConnection.getInstance();
		mSender = mMQConnection.getSender(destination);
	}

	public String getType()
	{
		return Constants.TYPE_TEXT;
	}
}
