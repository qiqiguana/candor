package net.sf.xbus.protocol.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.strings.XStringSupport;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.base.xml.XMLHelper;
import net.sf.xbus.protocol.Message;
import net.sf.xbus.protocol.ObjectMessage;
import net.sf.xbus.protocol.TextMessage;
import net.sf.xbus.protocol.XMLMessage;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;

/**
 * <code>XMLMessage</code> is the base class for all messages in the XML
 * format.
 * <p>
 * 
 * It holds the XML data internally as an <code>org.w3c.dom.Document</code>.
 * Therefore it is more efficient to work with the <code>Document</code>
 * representation than with the <code>String</code> representation. When
 * working with <code>String</code> representation, parsing and serializing is
 * done internally.
 */
public abstract class XMLMessageAbstract extends Message implements
		TextMessage, ObjectMessage, XMLMessage
{
	private Document mXMLRequestDocument = null;

	private Document mXMLResponseDocument = null;

	private String mRequestDTDSystemID = null;

	private String mResponseDTDSystemID = null;

	/**
	 * This constructor stores the <code>source</code>, creates an unique
	 * identifier for the message and initializes the requestTimestamp. It is
	 * used when constructing a new <code>XMLMessage</code> from the data of a
	 * receiver.
	 * 
	 * @param source
	 *            the system where the message is coming from
	 */
	public XMLMessageAbstract(XBUSSystem source)
	{
		super(source);
	}

	/**
	 * This constructor initializes the new <code>XMLMessage</code> with the
	 * given parameters. It is used when constructing a new
	 * <code>XMLMessage</code> by converting it from another
	 * <code>Message</code>.
	 * 
	 * @param function
	 *            the function of the other <code>Message</code>
	 * @param source
	 *            the source of the other <code>Message</code>
	 * @param id
	 *            the id of the other <code>Message</code>
	 */
	public XMLMessageAbstract(String function, XBUSSystem source, String id)
	{
		super(function, source, id);
	}

	/**
	 * Gets the text of the incoming message. It is the result of the
	 * serialization of the internally used <code>org.w3c.dom.Document</code>.
	 * 
	 * @param source
	 *            ignored
	 * @return the request XML document as a <code>String</code>
	 */
	public String getRequestText(XBUSSystem source) throws XException
	{
		return serializeXML(mXMLRequestDocument, mRequestDTDSystemID);
	}

	/**
	 * Sets the text of the incoming message. After parsing the XML string, the
	 * information is internally stored in a <code>org.w3c.dom.Document</code>.
	 * 
	 * @param text
	 *            a XML document from the request
	 */
	public void setRequestText(String text, XBUSSystem source)
			throws XException
	{
		setRequestDocument(parseXML(text, source), source);
	}

	/**
	 * Gets the text of the outgoing message. It is the result of the
	 * serialization of the internally used <code>org.w3c.dom.Document</code>.
	 * 
	 * @return the response XML document as a <code>String</code>
	 */
	public String getResponseText() throws XException
	{
		return serializeXML(mXMLResponseDocument, mResponseDTDSystemID);
	}

	/**
	 * Sets the text of the outgoing message. After parsing the XML string, the
	 * information is internally stored as a <code>org.w3c.dom.Document</code>.
	 * 
	 * @param text
	 *            a XML document from the response
	 */
	public void setResponseText(String text, XBUSSystem destination)
			throws XException
	{
		setResponseDocument(parseXML(text, destination), destination);
	}

	/**
	 * Returns the request XML data as a <code>org.w3c.dom.Document</code>.
	 */
	public Document getRequestDocument(XBUSSystem system)
	{
		return mXMLRequestDocument;
	}

	/**
	 * Sets the request XML data as a <code>org.w3c.dom.Document</code>.
	 */
	public void setRequestDocument(Document doc, XBUSSystem source)
			throws XException
	{
		mXMLRequestDocument = doc;

		if (mXMLRequestDocument != null)
		{
			/*
			 * Set the SystemId with the DTD given in the document
			 */
			DocumentType docType = mXMLRequestDocument.getDoctype();
			if (docType != null)
			{
				mRequestDTDSystemID = docType.getSystemId();
			}

			/*
			 * Set the function, when a XPath expression is given
			 */
			String xPathExpr = Configuration.getInstance().getValueOptional(
					Constants.CHAPTER_SYSTEM, source.getName(),
					"XPathExpression");
			try
			{
				if (xPathExpr != null)
				{
					XPath xPath = XPathFactory.newInstance().newXPath();
					String function = xPath.evaluate(xPathExpr, doc
							.getDocumentElement());
					function = XStringSupport.replaceAll(function, " ", "");
					setFunction(function);
				}
			}
			catch (XPathExpressionException e)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_XML, "0", e);
			}

		}

		synchronizeRequestFields(source);
	}

	/**
	 * Returns the response XML data as a <code>org.w3c.dom.Document</code>.
	 */
	public Document getResponseDocument()
	{
		return mXMLResponseDocument;
	}

	/**
	 * Sets the response XML data as a <code>org.w3c.dom.Document</code>.
	 */
	public void setResponseDocument(Document doc, XBUSSystem destination)
			throws XException
	{
		mXMLResponseDocument = doc;

		if (mXMLResponseDocument != null)
		{
			DocumentType docType = mXMLResponseDocument.getDoctype();
			if (docType != null)
			{
				mResponseDTDSystemID = docType.getSystemId();
			}
		}

		synchronizeResponseFields(destination);
	}

	/**
	 * @see net.sf.xbus.protocol.ObjectMessage#getRequestObject(net.sf.xbus.base.xbussystem.XBUSSystem)
	 */
	public Object getRequestObject(XBUSSystem system) throws XException
	{
		return getRequestDocument(system);
	}

	/**
	 * @see net.sf.xbus.protocol.ObjectMessage#setRequestObject(java.lang.Object,
	 *      net.sf.xbus.base.xbussystem.XBUSSystem)
	 */
	public void setRequestObject(Object object, XBUSSystem source)
			throws XException
	{
		setRequestDocument((Document) object, source);
	}

	/**
	 * @see net.sf.xbus.protocol.ObjectMessage#getResponseObject()
	 */
	public Object getResponseObject() throws XException
	{
		return getResponseDocument();
	}

	/**
	 * @see net.sf.xbus.protocol.ObjectMessage#setResponseObject(java.lang.Object,
	 *      net.sf.xbus.base.xbussystem.XBUSSystem)
	 */
	public void setResponseObject(Object object, XBUSSystem destination)
			throws XException
	{
		setResponseDocument((Document) object, destination);
	}

	/**
	 * After setting the request data, both <code>setRequestText</code> and
	 * <code>setRequestDocument</code>, some fields of the
	 * <code>Message</code> must be synchronized with the request data.
	 * <p>
	 * <ul>
	 * <li><b>Id: </b>When the Id is found in the request data, it must be
	 * copied to the <code>Message</code>. When it is not found, the value
	 * from the <code>Message</code> must be copied to the request data.
	 * <li><b>Function: </b>The value for the function must be read from the
	 * request data and must be set in the <code>Message</code>.
	 * <li><b>Source: </b>Must be copied from the <code>Message</code> object
	 * to the request data, to be sure that it is set correct.
	 * <li><b>Request Timestamp: </b>Must be copied from the
	 * <code>Message</code> object to the request data.
	 * </ul>
	 */
	abstract protected void synchronizeRequestFields(XBUSSystem system)
			throws XException;

	/**
	 * After setting the response data, both <code>setResponseText</code> and
	 * <code>setResponseDocument</code>, some fields of the
	 * <code>Message</code> must be synchronized with the response data.
	 * <p>
	 * <ul>
	 * <li><b>Returncode: </b>The value of the returncode must be extracted out
	 * of the response data.
	 * <li><b>Errorcode: </b>When an error has occured, the value for the
	 * errorcode can be read out of the response data, if it is included there.
	 * <li><b>Errortext: </b>When an error has occured, the value for the
	 * errortext can be read out of the response data, if it is included there.
	 * </ul>
	 */
	abstract protected void synchronizeResponseFields(XBUSSystem system)
			throws XException;

	/**
	 * Parses the given XML string.
	 */
	protected Document parseXML(String xml, XBUSSystem system)
			throws XException
	{
		return XMLHelper.parseXML(xml, getShortname(), system.getName());
	}

	/**
	 * Returns an adequate <code>DocumentBuilder</code> for this message
	 */
	protected DocumentBuilder getDocumentBuilder(XBUSSystem system)
			throws XException
	{
		return XMLHelper.getDocumentBuilder(getShortname(), system.getName());
	}

	/**
	 * Serializes the given <code>org.w3c.dom.Document</code>.
	 */
	static protected String serializeXML(Document doc, String systemID)
			throws XException
	{
		return XMLHelper.serializeXML(doc, systemID);
	}
}
