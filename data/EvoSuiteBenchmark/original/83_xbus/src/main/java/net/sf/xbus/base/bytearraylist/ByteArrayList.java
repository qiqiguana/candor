package net.sf.xbus.base.bytearraylist;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.bytearrays.XByteArraySupport;
import net.sf.xbus.base.core.config.Configuration;

/**
 * Manages a list of byte arrays.
 */
public class ByteArrayList
{
	private List mByteArrays;

	private int mLength;

	public ByteArrayList()
	{
		mByteArrays = new Vector();
		mLength = 0;
	}

	/**
	 * <code>getContentAsString</code> returns the content of the
	 * <code>ByteArrayList</code> as string. Each single byte array builds up
	 * one line in the string.
	 * 
	 * @param system used to determine the ByteArrayConverter and linebreaks
	 * @return A string containing the byte arrays in lines.
	 * @throws XException in case that the conversion cannot be performed
	 */
	public String getContentAsString(String system) throws XException
	{
		/*
		 * Create converter and linebreak
		 */
		ByteArrayConverter byteArrayConverter = ByteArrayConverterFactory
				.getConverter(system);
		String platform = Configuration.getInstance().getValueOptional(
				Constants.CHAPTER_SYSTEM, system, "Platform");
		String lineBreak = Constants.LINE_SEPERATOR;
		if (platform != null)
		{
			lineBreak = Constants.getLineSeperator(platform);
		}

		// for the result
		StringBuffer buffer = new StringBuffer(mLength + mByteArrays.size()
				* lineBreak.length());
		// how many characters alreday stored into the string buffer?
		int charsRead = 0;
		// A single line
		String line = null;
		if (mByteArrays.size() > 0)
		{ // There are some lines in the byte array, here process the first
			// one.
			line = byteArrayConverter.byteArrayToString((byte[]) mByteArrays
					.get(0));
			buffer.append(line);
			charsRead += line.length();
		} // if (mByteArrays.size()>0)
		for (int i = 1; i < mByteArrays.size(); i++)
		{ // for the following lines
			// first finish the previous line with a line break
			buffer.append(lineBreak);
			charsRead += lineBreak.length();
			// now the current line
			line = byteArrayConverter.byteArrayToString((byte[]) mByteArrays
					.get(i));
			buffer.append(line);
			charsRead += line.length();
		} // for (int i=1; i<mByteArrays.size(); i++)
		return buffer.substring(0, charsRead);
	} // getContentAsString(ByteArrayConverter byteArrayConverter, String
		// lineBreak)

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		int outputLength = Integer.MAX_VALUE;
		if (mLength * 3 < outputLength)
			outputLength = mLength * 3;
		StringBuffer buffer = new StringBuffer(outputLength);
		Iterator it = mByteArrays.iterator();
		int charCount = 0;
		String byteHex = null;
		while (it.hasNext() && charCount + 2 < outputLength)
		{
			byte[] line = (byte[]) it.next();
			for (int i = 0; i < line.length && charCount + 2 < outputLength; i++)
			{
				if (line[i] < 0)
					byteHex = Integer.toHexString(line[i] + 256).toUpperCase();
				else
					byteHex = Integer.toHexString(line[i]).toUpperCase();
				if (byteHex.length() == 1)
					buffer.append('0');
				buffer.append(byteHex);
				buffer.append(' ');
				charCount += 3;
			}
		}

		return buffer.toString();
	}

	/**
	 * Appends the specified byte array to the end of this list.
	 * 
	 * @param byteArray byte array to be appended to this list
	 */
	public void add(byte[] byteArray)
	{
		mByteArrays.add(byteArray);
		mLength += byteArray.length;
	}

	/**
	 * Returns an iterator over the elements in this list in proper sequence.
	 * 
	 * @return an iterator over the elements in this list in proper sequence
	 */
	public Iterator iterator()
	{
		return mByteArrays.iterator();
	}

	/**
	 * Method length.
	 * 
	 * @return int
	 */
	public int length()
	{
		return mLength;
	}

	public byte[] get(int index)
	{
		return (byte[]) mByteArrays.get(index);
	}

	public void addAll(ByteArrayList list)
	{
		if (list != null)
		{
			Iterator it = list.iterator();
			while (it.hasNext())
				add((byte[]) it.next());
		}
	}

	/**
	 * @return the content of the ByteArrayList as an array of bytes with system
	 *         specific line breaks.
	 */
	public byte[] toByteArray()
	{
		byte[] retArray = null;
		byte[] lineBreak = Constants.LINE_SEPERATOR.getBytes();

		for (Iterator it = iterator(); it.hasNext();)
		{
			byte[] line = (byte[]) it.next();
			retArray = concatenate(retArray, line);
			retArray = concatenate(retArray, lineBreak);
		}

		return retArray;
	}

	/**
	 * Concatenates two byte arrays
	 * 
	 * @param b1 the byte array at the beginning
	 * @param b2 the byte array at the end
	 * @return the concatenated byte array
	 */
	private byte[] concatenate(byte[] b1, byte[] b2)
	{
		if (b1 == null)
		{
			return b2;
		}

		if (b2 == null)
		{
			return b1;
		}

		byte[] retArray = new byte[b1.length + b2.length];

		System.arraycopy(b1, 0, retArray, 0, b1.length);
		System.arraycopy(b2, 0, retArray, b1.length, b2.length);

		return retArray;
	}

	static public ByteArrayList createByteArrayList(byte[] byteBuffer,
			int lineLength) throws XException
	{
		ByteArrayList retArray = new ByteArrayList();

		if (lineLength > 0)
		{
			byte[] byteArray;
			int i = 0;
			while (i + lineLength <= byteBuffer.length)
			{
				byteArray = XByteArraySupport.subArray(byteBuffer, i,
						lineLength);
				retArray.add(byteArray);
				i += lineLength;
				while (i < byteBuffer.length
						&& (byteBuffer[i] == Constants.NEWLINE || byteBuffer[i] == Constants.CARRIAGE_RETURN))
				{
					i++;
				}
			}
		}
		else
		{
			byte[] byteArray;
			int i = 0;
			int lastPos = 0;
			while (i < byteBuffer.length)
			{
				if ((byteBuffer[i] == Constants.NEWLINE)
						|| (byteBuffer[i] == Constants.CARRIAGE_RETURN))
				{
					byteArray = XByteArraySupport.subArray(byteBuffer, lastPos,
							i - lastPos);
					retArray.add(byteArray);
					do
					{
						i++;
					}
					while ((i < byteBuffer.length)
							&& ((byteBuffer[i] == Constants.NEWLINE) || (byteBuffer[i] == Constants.CARRIAGE_RETURN)));
					lastPos = i;
				}
				else
				{
					i++;
				}
			}
			if (lastPos < i)
			{
				byteArray = XByteArraySupport.subArray(byteBuffer, lastPos, i
						- lastPos);
				retArray.add(byteArray);
			}
		}

		return retArray;
	}

}
