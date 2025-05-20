package net.sf.xbus.protocol.xml;

import java.util.Hashtable;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.reflection.ReflectionSupport;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.base.xml.XDomSupport;
import net.sf.xbus.base.xml.XMLHelper;
import net.sf.xbus.protocol.Message;

import org.w3c.dom.Document;

/**
 * This is the base class for all transformation between
 * <code>XMLMessages</code> with XSLT stylesheets.
 * <p>
 * 
 * The transformation is done in two steps:
 * <ol>
 * <li>An XSLT stylesheet which name is read from the configuration does the
 * first part of the transformation.
 * <li>The so-called XBUS_Stylets are processed to fill in extra data into the
 * XML data.
 * </ol>
 * <p>
 * 
 * <b>XBUS_Stylets:</b>
 * <p>
 * XSLT stylesheets lack the possibilty to fill in data to the transformed XML
 * data which cannot be extracted out of the source XML. To fill this gap some
 * defined tags can be included in the transformed XML when the XSLT
 * transformation is done. These tags are replaced in the second step with
 * defined data. The format for XBUS_Stylets is:
 * 
 * <pre>
 *      &lt;XBUS_Stylet Name=&quot;xxx&quot; Tag=&quot;yyy&quot; Key=&quot;zzz&quot;&gt;
 * </pre>
 * 
 * The XSLT code to achieve this looks like:
 * 
 * <pre>
 *      &lt;xsl:element name=&quot;XBUS_Stylet&quot;&gt;
 *          &lt;xsl:attribute name=&quot;Name&quot;&gt;xxx&lt;/xsl:attribute&gt;
 *          &lt;xsl:attribute name=&quot;Tag&quot;&gt;yyy&lt;/xsl:attribute&gt;
 *          &lt;xsl:attribute name=&quot;Key&quot;&gt;zzz&lt;/xsl:attribute&gt;
 *      &lt;/xsl:element&gt;
 * </pre>
 * 
 * The meaning of the attributes is:
 * <ul>
 * <li><b>Name:</b> The name defines what data is inserted. There is a defined
 * set of names which will be explained in the following list.</li>
 * <li><b>Tag:</b> The name of the tag under which the data is inserted.<br>
 * May be omitted if <code>Name="Value"</code> or
 * <code>Name="FormatDate"</code>. In this case only a text node for the
 * value is inserted.</li>
 * <li><b>Section:</b> Optional, only used with the name <code>Value</code>.
 * Specifies the name of the mapping to get a value from.</li>
 * <li><b>Key:</b> Optional, only used with the names <code>Value</code>,
 * <code>CDATA</code> and <code>FormatDate</code>. Values come from two
 * different sources depending on the section attribute.<br>
 * If no section is specified: A key/value pair can be stored before the
 * transformation (see method
 * {@link #putStyletValue(Object, Object) putStyletValue}). The value for the
 * given key is inserted.<br>
 * For a given section: The value is read by the
 * {@link net.sf.xbus.base.core.config.Configuration#getMapping(String, String) getMapping}
 * operation of the Configuration.</li>
 * <li><b>SourceFormat:</b> Optional, only used with the name
 * <code>FormatDate</code>. Describes the actual format of the date given in
 * <code>Key</code>.</li>
 * <li><b>DestinationFormat:</b> Optional, only used with the name
 * <code>FormatDate</code>. Describes the format to apply to the date given
 * in <code>Key</code>.</li>
 * </ul>
 * 
 * The values for the attribute <code>Name</code> are defined as follows:
 * <ul>
 * <li><b>Id:</b> The identifier of the new <code>Message</code> is
 * inserted.</li>
 * <li><b>Source:</b> The logical name of the source of the
 * <code>Message</code> is inserted.</li>
 * <li><b>RequestTimestamp:</b> The request timestamp is inserted.</li>
 * <li><b>Value:</b> The value for the given <code>key</code> (see above) is
 * inserted.</li>
 * <li><b>CDATA:</b> The value for the given <code>key</code> (see above) is
 * inserted as a CDATA section.</li>
 * <li><b>FormatDate:</b> The format for the given date in <code>key</code>
 * (see above) is changed. <code>SourceFormat</code> describes the original
 * and <code>DestinationFormat</code> the new format.</li>
 * </ul>
 * 
 * If a transformation between <code>XMLMessages</code> can be done directly
 * via a XSLT stylesheet and XBUS_Stylet processing, this class can be used.
 * When extra code is needed for the transformation, it has to be subclassed.
 */
public class XSLTTransformer implements net.sf.xbus.protocol.Transformer
{
	protected static final String XSL_LOCATION = new StringBuffer(
			Constants.XBUS_ETC).append("xsl").append(Constants.FILE_SEPERATOR)
			.toString();

	private Hashtable mStyletValues = new Hashtable();
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

		// After restructuring the xBus transformation calls, the old
		// configuration
		// key "XslRequestFromRequest" was no longer meaningful because the
		// source
		// and destination systems determine, which style sheet should be used.
		// The differentiation between transformation from reponse or from
		// request is
		// done in net.sf.xbus.application.Router.distributeInternal when
		// looking
		// at the incoming response.
		// The new configuration key is "XSLTStylesheet";
		// The old configuration key "XslRequestFromRequest" still works for
		// upgrade compatibility.
		String xslFile = config.getValueOptional("XslRequestFromRequest",
				source.getName(), destination.getName());
		if (xslFile == null)
		{
			xslFile = config.getValue("XSLTStylesheet", source.getName(),
					destination.getName());
		}
		xslFile = new StringBuffer(XSL_LOCATION).append(xslFile).toString();

		return transformXML((Document) inObject, xslFile, destinationMessage);
	}

	/**
	 * Stores a key/value pair which is used later in the processing of the
	 * XBUS_Stylets.
	 */
	public void putStyletValue(Object key, Object value)
	{
		mStyletValues.put(key, value);
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
	protected Document transformXML(Document xmlOld, String xslFile,
			Message message) throws XException
	{
		Document xmlNew = null;
		DOMResult domResult = new DOMResult();

		try
		{
			Transformer transformer = getXSLTransformer(xslFile);
			transformer.transform(new DOMSource(xmlOld), domResult);
			xmlNew = (Document) domResult.getNode();
			applyStylets(xmlNew, message);
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

	/**
	 * Processes the so-called XBUS-Stylets. See also the JavaDoc for the class.
	 */
	private void applyStylets(Document xmlNew, Message message)
			throws XException
	{
		IteratedStyletApplication itop = new IteratedStyletApplication(xmlNew,
				message, mStyletValues);
		try
		{
			XDomSupport.traversePostOrder(xmlNew, itop);
		}
		catch (XException e)
		{
			throw e;
		} // catch (XException e)
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL, Constants.PACKAGE_PROTOCOL_XML,
					"0", e);
		}
	} // applyStylets(XMLMessage newMessage, Document xmlNew)

	/**
	 * Creates an object which is suitable for sending data to the given
	 * <code>system</code>. The name of the class is read in the <@link
	 * net.sf.xbus.base.core.Configuration>. The returned class must be a
	 * subclass of <code>XMLMessage</code>.
	 */
	protected XMLMessageAbstract createMessage(XBUSSystem destination,
			XMLMessageAbstract source) throws XException
	{
		Configuration config = Configuration.getInstance();
		String newMessageClassShortname = config.getValue(
				Constants.CHAPTER_SYSTEM, destination.getName(), "Message");
		String newMessageClass = Configuration.getClass("Message",
				newMessageClassShortname);

		Class[] conArgsClass = new Class[]
		{
				ReflectionSupport.classForName("java.lang.String"),
				ReflectionSupport
						.classForName("net.sf.xbus.base.xbussystem.XBUSSystem"),
				ReflectionSupport.classForName("java.lang.String")};

		Object[] conArgs = new Object[]
		{source.getFunction(), source.getSource(), source.getId()};

		return (XMLMessageAbstract) ReflectionSupport.createObject(
				newMessageClass, conArgsClass, conArgs);
	}
}
