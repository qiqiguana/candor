package net.sf.xbus.base.bytearraylist;

import java.io.UnsupportedEncodingException;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.technical.file.FileBase;

/**
 * @author Fleckenstein
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class ByteArrayConverterFile implements ByteArrayConverter
{
	private String mEncoding;
	private byte mSpace;

	public ByteArrayConverterFile(String system) throws XException
	{
		String encoding = FileBase.getEncoding(system);

		byte[] spaceArray = null;
		try
		{
			spaceArray = " ".getBytes(encoding);
			if (spaceArray.length != 1)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_BYTEARRAYLIST, "2");
			}
			mSpace = spaceArray[0];
		}
		catch (UnsupportedEncodingException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_BYTEARRAYLIST, "0", e);
		}

		mEncoding = encoding;
	}

	/**
	 * @see net.sf.xbus.base.bytearraylist.ByteArrayConverter#byteArrayToString(byte[])
	 */
	public String byteArrayToString(byte[] array) throws XException
	{
		try
		{
			return new String(array, mEncoding);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_BYTEARRAYLIST, "0", e);
		}
	}

	/**
	 */
	public byte[] stringToByteArray(String string, int length)
			throws XException
	{
		byte[] stringArray = null;
		try
		{
			boolean end = false;
			while (!end)
			{
				stringArray = string.getBytes(mEncoding);
				if (stringArray.length <= length)
				{
					end = true;
				}
				else
				{
					string = string.substring(0, string.length() - 1);
				}
			}
		}
		catch (UnsupportedEncodingException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_BYTEARRAYLIST, "0", e);
		}

		if (stringArray.length == length)
		{
			return stringArray;
		}
		else
		{
			byte[] retArray = new byte[length];
			for (int i = 0; i < length; i++)
			{
				if (i < stringArray.length)
				{
					retArray[i] = stringArray[i];
				}
				else
				{
					retArray[i] = mSpace;
				}
			}
			return retArray;
		}
	}

	public byte[] stringToByteArray(String string) throws XException
	{
		try
		{
			return string.getBytes(mEncoding);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_BYTEARRAYLIST, "0", e);
		}

	}
}
