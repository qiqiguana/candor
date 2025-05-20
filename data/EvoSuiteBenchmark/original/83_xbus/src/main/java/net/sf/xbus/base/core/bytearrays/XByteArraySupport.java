package net.sf.xbus.base.core.bytearrays;

import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;

/**
 * <code>XByteArraySupport</code> gives some support in manipulating byte
 * arrays.
 * 
 * @author Stephan Düwel
 */
public class XByteArraySupport
{

	/**
	 * <code>subArray</code> extracts a byte array from another.
	 * 
	 * @param array the complete byte array
	 * @param pos the starting position for extracting
	 * @param length the length of extracted sub-array
	 * @return the extracted sub-array
	 * @throws XException in case of unpossible <code>pos</code> or
	 *             <code>length</code> values
	 */
	public static byte[] subArray(byte[] array, int pos, int length)
			throws XException
	{
		byte[] retArray = new byte[length];
		try
		{
			for (int i = 0; i < length; i++)
				retArray[i] = array[pos + i];
		} // try
		catch (IndexOutOfBoundsException e)
		{
			Vector params = new Vector(3);
			params.add(new Integer(pos));
			params.add(new Integer(length));
			params.add(new Integer(array.length));
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_BASE,
					Constants.PACKAGE_COREBASE_BYTEARRAYS, "1");
		} // catch (IndexOutOfBoundsException e)
		return retArray;
	} // subArray(byte[] array, int pos, int length)

	/**
	 * @param inObject must be an <code>InputStream</code>
	 * @return the content of the InputStread as a byte array
	 */
	static public byte[] transformToByteArray(Object inObject)
			throws XException
	{
		InputStream is = (InputStream) inObject;

		// The stream content is read in block of size:
		final int blocksize = 1024;
		// Number of bytes read in one block.
		int bytesRead = 0;
		// Total number of bytes read.
		int byteArrayLength = 0;

		// The list of read blocks - content and length for each block.
		Vector blocks = new Vector();
		// Content and length for a single block.
		Object[] block = null;

		// Byte butter for reading a block.
		byte[] ba = new byte[blocksize];

		try
		// For casting to XException.
		{ // Reading first block
			bytesRead = is.read(ba);
			while (bytesRead != -1)
			{ // Loop until end of stream.
				// The read block.
				block = new Object[2];
				block[0] = ba;
				block[1] = new Integer(bytesRead);
				byteArrayLength += bytesRead;
				blocks.add(block);
				// Read next block.
				ba = new byte[blocksize];
				bytesRead = is.read(ba);
			} // while (bytesRead!=-1)
		} // try
		catch (IOException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_BASE,
					Constants.PACKAGE_COREBASE_BYTEARRAYS, "0", e);
		} // catch

		// Put everything together into one byte array.
		ba = new byte[byteArrayLength];
		int offset = 0;
		for (int i = 0; i < blocks.size(); i++)
		{
			bytesRead = ((Integer) ((Object[]) blocks.get(i))[1]).intValue();
			for (int j = 0; j < bytesRead; j++)
				ba[offset + j] = ((byte[]) ((Object[]) blocks.get(i))[0])[j];
			offset += bytesRead;
		} // for (int i=0; i<blocks.size(); i++)

		return ba;

	} // transformToByteArray(Object inObject)

} // XByteArraySupport
