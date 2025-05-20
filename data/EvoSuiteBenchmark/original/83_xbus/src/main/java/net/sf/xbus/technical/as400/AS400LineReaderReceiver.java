package net.sf.xbus.technical.as400;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.technical.Receiver;
import net.sf.xbus.technical.ReceiverSingleInterface;

/**
 * <code>AS400LineReaderReceiver</code> receives data from a AS400 file but
 * instead of reading them in passing the data itself through the xBus it only
 * opens the file and passes a refernce to it to the application layer. The data
 * will be read by a sender record by record. This is done with the help of the
 * {@link net.sf.xbus.technical.as400.AS400LineReader} class. Exactly an object
 * of this class is passed through the xBus. A respective sender access it in
 * terms of the operations declared in the
 * {@link net.sf.xbus.base.linereader.LineReader} interface. This way of data
 * transport is suitable for large interface files with data which does not need
 * any transformation.
 * 
 * @author Stephan Düwel
 */
public class AS400LineReaderReceiver extends AS400FileReceiver
		implements
			Receiver,
			ReceiverSingleInterface
{
	/**
	 * Opens the interface file on the AS400 and returns an
	 * {@link net.sf.xbus.technical.as400.AS400LineReader} object as reference
	 * to the opened file. Object attributes should have been initialized by
	 * reading them from the configuration.
	 * 
	 * @return reference to the opened AS400 file
	 * @throws XException if something goes wrong
	 */
	protected Object getRequestContent() throws XException
	{
		return new AS400LineReader(mAS400System, mQSYSObject, mOriginFile,
				mEncoding);
	} // getRequestContent()

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.xbus.technical.Receiver#getType()
	 */
	public String getType()
	{
		return Constants.TYPE_OBJECT;
	}
} // AS400LineReaderReceiver
