package net.sf.xbus.technical.mq;

import java.util.List;
import java.util.Vector;

import javax.jms.JMSException;
import javax.jms.QueueReceiver;
import javax.jms.TextMessage;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.TAManager;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.technical.ReceiverThreadBase;

/**
 * TODO Kommentierung
 */
public class MQReceiverThread extends ReceiverThreadBase
{
	private MQConnection mMQConnection = null;
	private String mPhysicalQueuename = null;

	private String mOnError = null;

	/**
	 * Stores the interface name
	 * 
	 * @param source name of the interface definition
	 */
	public MQReceiverThread(XBUSSystem source)
	{
		super(source);
	}

	/**
	 * Opens a {@link MQConnection}.
	 */
	protected void initializeThread() throws XException
	{
		mMQConnection = MQConnection.getInstance();
		mMQConnection.open();

		mOnError = getOnError(getSource().getName());

		mPhysicalQueuename = mMQConnection.getPhysQueuename(getSource());
	}

	/**
	 * Registers the {@link MQConnection}in the
	 * {@link net.sf.xbus.base.core.TAManager}.
	 */
	protected void registerResources(TAManager taManager) throws XException
	{
		mMQConnection.open();
		taManager.registerResource(mMQConnection);
	}

	/**
	 * @see net.sf.xbus.technical.ReceiverThreadBase#receive()
	 */
	protected Object receive() throws XException
	{
		try
		{
			QueueReceiver receiver = mMQConnection.getReceiver(getSource());
			TextMessage message = (TextMessage) receiver.receive(getTimeout());
			if (message != null)
			{
				if ((mOnError != null)
						&& mOnError.equals(Constants.READ_DELETE))
				{
					mMQConnection.setDeleteInformation(getSource(), message
							.getJMSMessageID());
				}
				return message.getText();
			}
			else
			{
				return null;
			}
		}
		catch (JMSException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL, Constants.PACKAGE_TECHNICAL_MQ,
					"0", e);
		}
	}

	/**
	 * Returns {@link net.sf.xbus.base.core.Constants#TYPE_TEXT}
	 * 
	 * @return {@link net.sf.xbus.base.core.Constants#TYPE_TEXT}
	 */
	public String getType()
	{
		return Constants.TYPE_TEXT;
	}

	/**
	 * @see net.sf.xbus.technical.ReceiverThreadBase#getReceiverClassName()
	 */
	protected String getReceiverClassName()
	{
		return "MQReceiverThread";
	}

	/**
	 * Returns the name of the message queue.
	 * 
	 * @return the name of the message queue
	 */
	protected String getAddress()
	{
		return mPhysicalQueuename;
	}

	/**
	 * Reads the action which should happen with the message after an error for
	 * the for the given system name from the standard configuration and checks
	 * its conformity with the allowed ones:
	 * <p>
	 * <dl>
	 * <dd><code>Preserve</code> &nbsp;-message is remained without
	 * modifications
	 * <dd><code>Delete</code> &nbsp;&nbsp;&nbsp;- message is deleted
	 * </dl>
	 * 
	 * @param system Sytem name which resoltion must be read.
	 * @return exist resolution (Preserve or Delete) as String
	 * @exception XException if resolution is false or any errors occurs
	 */
	private String getOnError(String system) throws XException
	{
		Configuration config = Configuration.getInstance();
		String onError = config.getValueOptional(Constants.CHAPTER_SYSTEM,
				system, "OnError");
		if (onError == null)
		{
			onError = Constants.READ_PRESERVE;
		}

		if (!onError.equals(Constants.READ_PRESERVE)
				&& !onError.equals(Constants.READ_DELETE))
		{
			List params = new Vector();
			params.add(onError);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_TECHNICAL, Constants.PACKAGE_TECHNICAL_MQ,
					"1", params);
		}
		return onError;
	}

}
