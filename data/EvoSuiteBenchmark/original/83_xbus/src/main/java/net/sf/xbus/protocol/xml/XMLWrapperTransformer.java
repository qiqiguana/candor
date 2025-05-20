package net.sf.xbus.protocol.xml;

import net.sf.xbus.base.bytearraylist.ByteArrayList;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.base.xml.XMLHelper;
import net.sf.xbus.protocol.Message;
import net.sf.xbus.protocol.Transformer;

import org.w3c.dom.Document;

/**
 * The <code>XMLWrapperTransformer</code> wraps the given <code>String</code>
 * into a <code>XBUSXMLMessage</code>. The XBUS_Data section of the outcoming
 * XBUSXML document contains the given <code>String</code> as a CDATA element.
 * This makes sure the XML parser will not try to evaluate the
 * <code>String</code>.
 */
public class XMLWrapperTransformer implements Transformer
{
	/**
	 * The <code>transform</code> method wraps the given <code>Object</code>
	 * into a <code>XBUSXMLMessage</code>.
	 * 
	 * @param inObject either the request or response that shall be wrapped into
	 *            a XBUSXMLMessage. This <code>Object</code> must be of type
	 *            <code>String</code>.
	 * @param source not used
	 * @param destination not used
	 * @param destinationMessage not used
	 * @return an <code>org.w3c.Document</code> containing a
	 *         <code>XBUSXMLMessage</code> containing the
	 *         <code>inObject</code> as a CDATA section.
	 */
	public Object transform(Object inObject, XBUSSystem source,
			XBUSSystem destination, Message destinationMessage)
			throws XException
	{
		Document doc = XBUSXMLMessage.getTemplateAsDocument();

		String inString = inObject.toString();

		if (inObject instanceof Document)
		{
			inString = XMLHelper.serializeXML((Document) inObject, null);
		}
		else if (inObject instanceof ByteArrayList)
		{ // ByteArrayLists must be treated separately to serialise the
			// content
			// and not the byte representation as sequence of numbers
			inString = ((ByteArrayList) inObject)
					.getContentAsString(destination.getName());
		} // if
		// (inObject.getClass().getName().equals("net.sf.xbus.protocol.bytearraylist.ByteArrayList"))

		XMLHelper.setTagCDATA(doc, Constants.XBUSXMLMESSAGE_DATA, inString);

		return doc;
	}
}
