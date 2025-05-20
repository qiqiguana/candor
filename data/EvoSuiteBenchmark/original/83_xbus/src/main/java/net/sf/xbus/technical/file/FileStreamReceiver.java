package net.sf.xbus.technical.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.technical.Receiver;
import net.sf.xbus.technical.ReceiverSingleInterface;

/**
 * <code>FileStreamReceiver</code> is a receiver class which opens an
 * <code>InputStream</code> of the input file and sends a reference to it
 * through the xBus.
 */
public class FileStreamReceiver extends FileReceiver
		implements
			Receiver,
			ReceiverSingleInterface
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.xbus.technical.file.FileReceiver#getRequestContent()
	 */
	protected Object getRequestContent() throws XException
	{
		BufferedInputStream inStream;
		try
		{
			inStream = new BufferedInputStream(new FileInputStream(new File(
					mCopyname)));
		}
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_FILE, "0", e);
		}
		return inStream;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.xbus.technical.Receiver#getType()
	 */
	public String getType()
	{
		return Constants.TYPE_OBJECT;
	}
}
