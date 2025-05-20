package net.sf.xbus.protocol.records;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.base.xml.XDomSupport;
import net.sf.xbus.protocol.Message;
import net.sf.xbus.protocol.Transformer;
import net.sf.xbus.protocol.xml.XSLTTransformer;

import org.w3c.dom.Document;

/**
 * <code>RecordTypeTransformer</code> transforms one
 * {@link net.sf.xbus.protocol.records.RecordTypeMessage RecordTypeMessage} into
 * another one - according to a xsl transformation description. The class is
 * pretty like the
 * {@link net.sf.xbus.protocol.xml.XSLTTransformer XSLTTransformer}. The only
 * difference is a whitespace cleaning after the transformation.
 */
public class RecordTypeTransformer extends XSLTTransformer
		implements
			Transformer
{
	/**
	 * The <code>transform</code> method is automatically called by the xBus
	 * during the routing of messages.
	 * 
	 * @param inObject either the request or response that shall be transformed
	 *            to another format
	 * @param source the <code>XBUSSystem</code> of the message of the
	 *            <code>inObject</code>
	 * @param destination the <code>XBUSSystem</code> of the target message
	 * @param destinationMessage the target message eventually contains more
	 *            information necessary for transforming
	 * @return the transformed <code>Object</code>, written to either the
	 *         request or response of the target message
	 */
	public Object transform(Object inObject, XBUSSystem source,
			XBUSSystem destination, Message destinationMessage)
			throws XException
	{
		Document doc = (Document) super.transform(inObject, source,
				destination, destinationMessage);

		try
		// for casting to XException
		{
			XDomSupport.deleteWhitespaceTextInElementNodes(doc);
		} // try
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		} // catch

		return doc;
	}

	/**
	 * @param inDoc
	 * @param xsltStylesheet
	 * @return ???
	 */
	public Document transformSingleRecord(Document inDoc, String xsltStylesheet)
			throws XException
	{
		String xslFile = new StringBuffer(XSL_LOCATION).append(xsltStylesheet)
				.toString();
		return transformXML(inDoc, xslFile, null);
	}

} // RecordTypeTransformer
