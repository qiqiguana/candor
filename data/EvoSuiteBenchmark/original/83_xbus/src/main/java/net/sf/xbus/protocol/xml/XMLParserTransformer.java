package net.sf.xbus.protocol.xml;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.base.xml.XMLHelper;
import net.sf.xbus.protocol.Message;
import net.sf.xbus.protocol.Transformer;

/**
 * The <code>XMLParserTransformer</code> parses the given <code>String</code>
 * into an <code>org.w3c.Document</code>.
 */
public class XMLParserTransformer implements Transformer
{
	/**
	 * The <code>transform</code> method parses the given <code>String</code>
	 * into an <code>org.w3c.Document</code>.
	 * 
	 * @param inObject either the request or response that shall be parsed into
	 *            an <code>org.w3c.Document</code>. This <code>Object</code>
	 *            must be of type <code>String</code>.
	 * @param source not used
	 * @param destination where the message shall be sent to
	 * @param destinationMessage the message to send
	 * @return an <code>org.w3c.Document</code> containing the parsed
	 *         <code>inObject</code>
	 */
	public Object transform(Object inObject, XBUSSystem source,
			XBUSSystem destination, Message destinationMessage)
			throws XException
	{
		return XMLHelper.parseXML((String) inObject, destinationMessage
				.getShortname(), destination.getName());
	}
}
