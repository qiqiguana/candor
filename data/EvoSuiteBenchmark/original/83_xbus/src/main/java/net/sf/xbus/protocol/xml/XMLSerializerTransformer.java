package net.sf.xbus.protocol.xml;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.base.xml.XMLHelper;
import net.sf.xbus.protocol.Message;
import net.sf.xbus.protocol.Transformer;

import org.w3c.dom.Document;

/**
 * The <code>XMLSerializerTransformer</code> serializes the given
 * <code>org.w3c.Document</code> into a <code>String</code>.
 */
public class XMLSerializerTransformer implements Transformer
{
	/**
	 * The <code>transform</code> method serializes the given
	 * <code>org.w3c.Document</code> into a <code>String</code>.
	 * 
	 * @param inObject either the request or response that shall be wrapped into
	 *            a string. This <code>Object</code> must be of type
	 *            <code>org.w3c.Document</code>.
	 * @param source not used
	 * @param destination not used
	 * @param destinationMessage not used
	 * @return a <code>String</code> containing the serialized
	 *         <code>inObject</code>
	 */
	public Object transform(Object inObject, XBUSSystem source,
			XBUSSystem destination, Message destinationMessage)
			throws XException
	{
		return XMLHelper.serializeXML((Document) inObject, null);
	}
}
