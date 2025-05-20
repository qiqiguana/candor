package net.sf.xbus.protocol.simple;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.protocol.Message;
import net.sf.xbus.protocol.Transformer;

/**
 * The <code>Text2StreamTransformer</code> returns a
 * <code>ByteArrayInputStream</code> to read a string content. The class
 * <code>StringBufferInputStream</code> is deprecated: "This class does not
 * properly convert characters into bytes. As of JDK 1.1, the preferred way to
 * create a stream from a string is via the StringReader class."
 */
public class Text2StreamTransformer implements Transformer
{
	/**
	 * The <code>Text2StreamTransformer</code> returns a
	 * <code>ByteArrayInputStream</code> to read a string content.
	 * 
	 * @param inObject either the request or response of the source message
	 * @param source not used
	 * @param destination not used
	 * @param destinationMessage not used
	 * @return <code>ByteArrayInputStream</code>
	 */
	public Object transform(Object inObject, XBUSSystem source,
			XBUSSystem destination, Message destinationMessage)
			throws XException
	{
		String text = (String) inObject;

		String configEncoding = Configuration.getInstance().getValueOptional(
				Constants.CHAPTER_SYSTEM, source.getName(),
				Constants.KEY_ENCODING);

		byte[] ba = null;

		if (configEncoding == null)
			ba = text.getBytes();
		else
		{
			try
			{
				ba = text.getBytes(configEncoding);
			} // try
			catch (UnsupportedEncodingException e)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_SIMPLE, "0", e);
			} // catch
		} // (configEncoding == null)

		return new ByteArrayInputStream(ba);
	} // transform(Object inObject,XBUSSystem source,XBUSSystem
		// destination,Message destinationMessage)

} // Text2StreamTransformer
