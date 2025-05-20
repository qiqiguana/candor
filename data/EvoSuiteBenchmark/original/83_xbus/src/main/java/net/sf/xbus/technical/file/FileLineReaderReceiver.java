package net.sf.xbus.technical.file;

import java.io.File;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.technical.Receiver;
import net.sf.xbus.technical.ReceiverSingleInterface;

/**
 * <code>FileLineReaderReceiver</code> is a receiver class which only opens
 * the input file and send a reference to it through the integration layer.
 */
public class FileLineReaderReceiver extends FileReceiver
		implements
			Receiver,
			ReceiverSingleInterface
{
	protected Object getRequestContent() throws XException
	{
		Object reader;
		try
		{
			reader = new FileLineReader(new File(mCopyname), mConfiguration
					.getEncoding());
		}
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_FILE, "0", e);
		}
		return reader;
	}

	public String getType()
	{
		return Constants.TYPE_OBJECT;
	}
}
