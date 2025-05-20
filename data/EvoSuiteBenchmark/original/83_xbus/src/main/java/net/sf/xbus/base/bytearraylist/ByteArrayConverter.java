package net.sf.xbus.base.bytearraylist;

import net.sf.xbus.base.core.XException;

/**
 * @author Fleckenstein
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public interface ByteArrayConverter
{
	public String byteArrayToString(byte[] array) throws XException;

	public byte[] stringToByteArray(String string, int length)
			throws XException;

	public byte[] stringToByteArray(String string) throws XException;
}
