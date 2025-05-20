package net.sf.xbus.technical.as400;

import net.sf.xbus.base.bytearraylist.ByteArrayList;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.technical.Receiver;
import net.sf.xbus.technical.ReceiverSingleInterface;

import com.ibm.as400.access.AS400File;
import com.ibm.as400.access.AS400FileRecordDescription;
import com.ibm.as400.access.Record;
import com.ibm.as400.access.RecordFormat;

/**
 * <code>AS400ByteArrayListReceiver</code> serves for reading files from an
 * AS400 and putting the file content into a byte array list. Each byte array in
 * this list corresponds to a record in the file. Byte array are used instead of
 * strings if the character encoding does not allow predicting the the string
 * length of free text fields in sense of number of characters by knowing the
 * number of bytes. In this case bytes instaed of characters have to be counted
 * to determine the contents of single record fileds.
 * 
 * @author Stefan Fleckenstein
 */
public class AS400ByteArrayListReceiver extends AS400FileReceiver
		implements
			Receiver,
			ReceiverSingleInterface
{
	/**
	 * Reads the integrated file system object specified by the path name into a
	 * ByteArrayList.
	 * <p>
	 * <b><i>Note: </i></b> We use the record-level access classes for
	 * reading.<br>
	 * Contents of each record are read into a byte array of AS400 data.
	 * 
	 * @return file contents as
	 *         {@link net.sf.xbus.base.bytearraylist.ByteArrayList}.
	 * @exception XException if file can not be read or any I/O error occurs
	 */
	protected Object getRequestContent() throws XException
	{
		ByteArrayList retBuffer = new ByteArrayList();

		AS400FileRecordDescription recordDescription = new AS400FileRecordDescription(
				mAS400System, mQSYSObject.getPath());

		try
		{
			// Set record format of the file.
			RecordFormat[] format = recordDescription.retrieveRecordFormat();
			mOriginFile.setRecordFormat(format[0]);

			// Open the file.
			mOriginFile.open(AS400File.READ_WRITE, 0,
					AS400File.COMMIT_LOCK_LEVEL_NONE);

			// Read records
			byte[] inData = null;
			Record record;
			while ((record = mOriginFile.readNext()) != null)
			{
				inData = record.getContents();
				retBuffer.add(inData);
			}
			// close the file since I am done using it
			mOriginFile.close();

		}
		catch (Exception thr)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "0", thr);
		}
		return retBuffer;
	}

	public String getType()
	{
		return Constants.TYPE_OBJECT;
	}
}
