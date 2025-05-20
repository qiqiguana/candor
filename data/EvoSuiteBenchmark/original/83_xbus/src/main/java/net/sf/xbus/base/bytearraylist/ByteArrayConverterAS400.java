package net.sf.xbus.base.bytearraylist;

import java.io.UnsupportedEncodingException;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.technical.as400.AS400Connection;
import net.sf.xbus.technical.as400.AS400FileBase;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.CharConverter;

/**
 * @author Fleckenstein
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class ByteArrayConverterAS400 implements ByteArrayConverter
{
	private CharConverter conv = null;
	private byte mSpace;

	public ByteArrayConverterAS400(String system) throws XException
	{
		String as400name = Configuration.getInstance().getValue(
				Constants.CHAPTER_SYSTEM, system, "AS400");
		AS400 as400 = AS400Connection.getInstance(as400name).getSystem();
		String encoding = AS400FileBase.getEncoding(as400, system);

		try
		{
			conv = new CharConverter(encoding);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_BYTEARRAYLIST, "0", e);
		}

		byte[] spaceArray = null;
		spaceArray = conv.stringToByteArray(" ");
		if (spaceArray.length != 1)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_BYTEARRAYLIST, "2");
		}
		mSpace = spaceArray[0];
	}

	/**
	 * @see net.sf.xbus.base.bytearraylist.ByteArrayConverter#byteArrayToString(byte[])
	 */
	public String byteArrayToString(byte[] array)
	{
		return conv.byteArrayToString(array);
	}

	/**
	 */
	public byte[] stringToByteArray(String string, int length)
	{
		byte[] stringArray = null;

		boolean end = false;
		while (!end)
		{
			stringArray = conv.stringToByteArray(string);
			if (stringArray.length <= length)
			{
				end = true;
			}
			else
			{
				string = string.substring(0, string.length() - 1);
			}

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

	public byte[] stringToByteArray(String string)
	{
		return conv.stringToByteArray(string);
	}
}
