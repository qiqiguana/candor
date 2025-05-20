package net.sf.xbus.protocol.simple;

import java.io.UnsupportedEncodingException;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.bytearrays.XByteArraySupport;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.protocol.Message;
import net.sf.xbus.protocol.Transformer;

/**
 * The <code>Stream2TextTransformer</code> read the content of an
 * <code>InputStream<code> into string.
 */
public class Stream2TextTransformer implements Transformer
{
	/**
	 * The <code>Stream2TextTransformer</code> read the content of an
	 * <code>InputStream<code> into string.
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
		// First get stream content as byte array
		byte[] ba = XByteArraySupport.transformToByteArray(inObject);
		String text = null;

		// Individual encoding?
		String configEncoding = Configuration.getInstance().getValueOptional(
				Constants.CHAPTER_SYSTEM, source.getName(),
				Constants.KEY_ENCODING);
		if (configEncoding == null)
			text = new String(ba);
		else
		{
			try
			{
				text = new String(ba, configEncoding);
			} // try
			catch (UnsupportedEncodingException e)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_SIMPLE, "0", e);
			} // catch
		} // (configEncoding == null)

		return text;
	} // transform(Object inObject,XBUSSystem source,XBUSSystem
		// destination,Message destinationMessage)

} // Stream2ByteArrayListTransformer
