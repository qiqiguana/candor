package net.sf.xbus.technical.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import net.sf.xbus.base.bytearraylist.ByteArrayList;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.technical.Receiver;
import net.sf.xbus.technical.ReceiverSingleInterface;

public class FileByteArrayListReceiver extends FileReceiver
		implements
			Receiver,
			ReceiverSingleInterface
{
	protected Object getRequestContent() throws XException
	{
		File sourceFile = new File(mCopyname);

		/**
		 * 1. Determine the file size
		 */
		int fileLength;
		long fileLengthLong = sourceFile.length();
		if (fileLengthLong > Integer.MAX_VALUE)
		{
			List params = new Vector();
			params.add(mCopyname);
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_FILE, "5", params);
		}
		else
		{
			fileLength = new Long(fileLengthLong).intValue();
		}

		if (fileLength < 1)
		{
			return new ByteArrayList();
		}

		/**
		 * 2. Read the complete file
		 */
		byte[] byteBuffer = new byte[fileLength];

		try
		{
			BufferedInputStream buffStream = new BufferedInputStream(
					new FileInputStream(sourceFile));
			if (buffStream.read(byteBuffer) == -1)
			{
				List params = new Vector();
				params.add(mCopyname);
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_FILE, "7", params);
			}
			buffStream.close();
		}
		catch (IOException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_FILE, "0", e);
		}

		/**
		 * 3. Convert the byte buffer into a ByteArrayList
		 */
		return ByteArrayList.createByteArrayList(byteBuffer, mConfiguration
				.getLineLength());
	}

	public String getType()
	{
		return Constants.TYPE_OBJECT;
	}
}
