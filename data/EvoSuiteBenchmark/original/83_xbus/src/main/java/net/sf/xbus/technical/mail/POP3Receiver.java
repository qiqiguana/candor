package net.sf.xbus.technical.mail;

import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.TAResource;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.technical.Receiver;
import net.sf.xbus.technical.ReceiverSingleInterface;

/**
 * TODO Kommentierung
 */
public class POP3Receiver extends POP3XMLReceiver
		implements
			TAResource,
			Receiver,
			ReceiverSingleInterface
{
	protected Object getRequestContent(String system) throws XException
	{
		String retString = null;

		try
		{
			if (1 <= mFolder.getMessageCount())
			{
				mMessage = mFolder.getMessage(1);

				Object o = mMessage.getContent();

				if (o instanceof String)
				{
					retString = o.toString();
				}
				else
				{
					Vector params = new Vector(1);
					params.add(o.getClass().getName());
					throw new XException(Constants.LOCATION_EXTERN,
							Constants.LAYER_TECHNICAL,
							Constants.PACKAGE_TECHNICAL_MAIL, "3", params);
				}

			}
			return retString;
		}
		catch (Exception e)
		{
			if (e instanceof XException)
			{
				throw (XException) e;
			}
			else
			{
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_MAIL, "0", e);
			}
		}
	}

	public String getType()
	{
		return Constants.TYPE_TEXT;
	}

}
