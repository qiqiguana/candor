package net.sf.xbus.protocol.xml;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.base.xml.XMLHelper;
import net.sf.xbus.protocol.Message;
import net.sf.xbus.protocol.Transformer;

import org.w3c.dom.Document;

/**
 * The <code>XMLUnwrapperTransformer</code> does the inverse transformation of
 * the <code>XMLWrapperTransformer</code>. It awaits a XBUSXML document
 * containing a CDATA section. The content of this CDATA section is given back
 * as a <code>String</code>.
 */
public class XMLUnwrapperTransformer implements Transformer
{
	/**
	 * The <code>transform</code> method unwraps a <code>String</code> from
	 * the given <code>Object</code>.
	 * 
	 * @param inObject either the request or response. It must be an
	 *            <code>org.w3c.Document</code> containing a
	 *            <code>XBUSXMLMessage</code>.
	 * @param source not used
	 * @param destination not used
	 * @param destinationMessage not used
	 * @return an <code>String</code> unwrapped from the CDATA section of the
	 *         given document.
	 */
	public Object transform(Object inObject, XBUSSystem source,
			XBUSSystem destination, Message destinationMessage)
	{
		return XMLHelper.getTagCDATA((Document) inObject,
				Constants.XBUSXMLMESSAGE_DATA);
	}

}
