package net.sf.xbus.protocol.bytearraylist;

import java.io.ByteArrayInputStream;

import net.sf.xbus.base.bytearraylist.ByteArrayList;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.protocol.Message;
import net.sf.xbus.protocol.Transformer;

/**
 * The <code>ByteArrayList2StreamTransformer</code> returns a
 * <code>ByteArrayInputStream</code> to read its content.
 */
public class ByteArrayList2StreamTransformer implements Transformer
{
	/**
	 * The <code>ByteArrayList2StreamTransformer</code> returns a
	 * <code>ByteArrayInputStream</code> to read its content.
	 * 
	 * @param inObject either the request or response of the source message
	 * @param source not used
	 * @param destination not used
	 * @param destinationMessage not used
	 * @return <code>ByteArrayInputStream</code>
	 */
	public Object transform(Object inObject, XBUSSystem source,
			XBUSSystem destination, Message destinationMessage)
	{
		ByteArrayList bal = (ByteArrayList) inObject;

		return new ByteArrayInputStream(bal.toByteArray());
	}

}
