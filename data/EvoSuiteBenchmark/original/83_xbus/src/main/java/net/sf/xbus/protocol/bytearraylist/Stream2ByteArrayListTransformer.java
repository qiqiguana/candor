package net.sf.xbus.protocol.bytearraylist;

import net.sf.xbus.base.bytearraylist.ByteArrayList;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.bytearrays.XByteArraySupport;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.protocol.Message;
import net.sf.xbus.protocol.Transformer;

/**
 * The <code>Stream2ByteArrayListTransformer</code> read the content of an
 * <code>InputStream<code> into a <code>ByteArrayList<code>.
 */
public class Stream2ByteArrayListTransformer implements Transformer
{
	/**
	 * The <code>Stream2ByteArrayListTransformer</code> read the content of an
	 * <code>InputStream<code> into a <code>ByteArrayList<code>.
	 * 
	 * @param inObject either the request or response of the source message
	 * @param source not used - from the Transformer interface
	 * @param destination not used - from the Transformer interface
	 * @param destinationMessage not used - from the Transformer interface
	 * @return <code>ByteArrayList</code>
	 */
	public Object transform(Object inObject, XBUSSystem source,
			XBUSSystem destination, Message destinationMessage)
			throws XException
	{
		byte[] ba = XByteArraySupport.transformToByteArray(inObject);
		// convert the byte array into a ByteArrayList.
		return ByteArrayList.createByteArrayList(ba, 0);
	} // transform(Object inObject,XBUSSystem source,XBUSSystem
		// destination,Message destinationMessage)

} // Stream2ByteArrayListTransformer
