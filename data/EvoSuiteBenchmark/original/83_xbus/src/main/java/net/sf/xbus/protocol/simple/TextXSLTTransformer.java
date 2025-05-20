package net.sf.xbus.protocol.simple;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Hashtable;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.base.xml.XMLHelper;
import net.sf.xbus.protocol.Message;

/**
 * TODO: Kommentierung
 */
public class TextXSLTTransformer implements net.sf.xbus.protocol.Transformer
{
	protected static final String XSL_LOCATION = new StringBuffer(
			Constants.XBUS_ETC).append("xsl").append(Constants.FILE_SEPERATOR)
			.toString();

	private static Hashtable mTransformers = new Hashtable();

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
		Configuration config = Configuration.getInstance();

		String xslFile = config.getValue("XSLTStylesheet", source.getName(),
				destination.getName());

		xslFile = new StringBuffer(XSL_LOCATION).append(xslFile).toString();

		return transformXML((String) inObject, xslFile, destinationMessage);
	}

	/**
	 * Transforms the given XML-data with the given XSL-stylesheet. After the
	 * XSL-transformation the so-called XBUS_Stylets are processed.
	 * <p>
	 * 
	 * @param xmlOld the source XML-data
	 * @param xslFile the name of the XSL-stylesheet
	 * @param message the destination message
	 * @throws XException if something goes wrong
	 */
	protected String transformXML(String xmlOld, String xslFile, Message message)
			throws XException
	{
		String xmlNew = null;
		StringReader source = new StringReader(xmlOld);
		StreamSource streamSource = new StreamSource(source);
		StringWriter result = new StringWriter();
		StreamResult streamResult = new StreamResult(result);

		try
		{
			Transformer transformer = getXSLTransformer(xslFile);
			transformer.transform(streamSource, streamResult);
			xmlNew = result.toString();
			source.close();
			result.close();
		}
		catch (IOException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL, Constants.PACKAGE_PROTOCOL_XML,
					"0", e);
		}
		catch (TransformerConfigurationException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL, Constants.PACKAGE_PROTOCOL_XML,
					"0", e);
		}
		catch (TransformerException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL, Constants.PACKAGE_PROTOCOL_XML,
					"0", e);
		}
		return xmlNew;
	}

	/**
	 * Returns a <code>Transformer</code> object needed for the XSLT
	 * processing. The <code>Transformers</code> are cached for every thread
	 * and every XSLT stylesheet.
	 */
	protected Transformer getXSLTransformer(String xslFile) throws XException
	{
		Transformer transformer = null;

		try
		{
			transformer = (Transformer) mTransformers.get(Thread
					.currentThread().getName()
					+ xslFile);

			if (transformer == null)
			{
				XMLHelper.setTransformerProperties();

				TransformerFactory tFactory = TransformerFactory.newInstance();
				transformer = tFactory
						.newTransformer(new StreamSource(xslFile));
				mTransformers.put(Thread.currentThread().getName() + xslFile,
						transformer);
			}
		}
		catch (TransformerConfigurationException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL, Constants.PACKAGE_PROTOCOL_XML,
					"0", e);
		}
		return transformer;
	}
}
