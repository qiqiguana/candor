package net.sf.xbus.protocol.soap;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.base.xml.XMLHelper;
import net.sf.xbus.protocol.ObjectMessage;
import net.sf.xbus.protocol.TextMessage;
import net.sf.xbus.protocol.xml.XMLMessageAbstract;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <code>SOAPMessage</code> represents messages for the Simple Object Access
 * Protocol.
 */
public class SOAPMessage extends XMLMessageAbstract implements TextMessage,
		ObjectMessage
{
	public static final String SOAP_DETAIL = "detail";

	private static final String SOAP_FAULTSTRING = "faultstring";

	public static final String SOAP_FAULTCODE = "faultcode";

	public static final String SOAP_FAULT = "Fault";

	public static final String SOAP_BODY = "Body";

	public static final String SOAP_NAMESPACE = "http://schemas.xmlsoap.org/soap/envelope/";

	public static final String SOAPENV_QUALIFIER = "soapenv";

	private boolean hasFault = false;

	/**
	 * This constructor stores the <code>source</code>, creates an unique
	 * identifier for the message and initializes the requestTimestamp. It is
	 * used when constructing a new <code>SOAPMessage</code> from the data of
	 * a receiver.
	 */
	public SOAPMessage(XBUSSystem source)
	{
		super(source);
		setShortname("SOAPMessage");
	}

	/**
	 * This constructor initializes the new <code>SOAPMessage</code> with the
	 * given parameters. It is used when constructing a new
	 * <code>SOAPMessage</code> by converting it from another
	 * <code>Message</code>.
	 */
	public SOAPMessage(String function, XBUSSystem source, String id)
	{
		super(function, source, id);
		setShortname("SOAPMessage");
	}

	/**
	 * Sets the errortext with the given String. Additionally, a
	 * <code>Fault</code> element is added to the SOAP message, if there is
	 * not already one.
	 */
	public void setErrortext(String text)
	{
		super.setErrortext(text);

		if ((text != null) && (!hasFault))
		{
			try
			{
				Document response = getTemplateAsDocument();
				setResponseDocument(response, null);

				Node oldBody = getBody(response);

				Element newBody = response.createElementNS(SOAP_NAMESPACE,
						SOAPENV_QUALIFIER + ":" + SOAP_BODY);
				Element fault = response.createElementNS(SOAP_NAMESPACE,
						SOAPENV_QUALIFIER + ":" + SOAP_FAULT);
				newBody.appendChild(fault);
				Element faultcode = response.createElement(SOAP_FAULTCODE);
				faultcode.appendChild(response
						.createTextNode("Server.Exception"));
				fault.appendChild(faultcode);
				Element faultstring = response.createElement(SOAP_FAULTSTRING);
				faultstring.appendChild(response.createTextNode(text));
				fault.appendChild(faultstring);
				String stackTrace = XException.getExceptionInformation();
				if (stackTrace != null)
				{
					Element detail = response.createElement(SOAP_DETAIL);
					detail.appendChild(response.createTextNode(XException
							.getExceptionInformation()));
					fault.appendChild(detail);
				}
				Node father = oldBody.getParentNode();
				father.replaceChild(newBody, oldBody);

			}
			catch (XException e)
			{}
		}
	}

	/**
	 * After setting the request data, both <code>setRequestText</code> and
	 * <code>setRequestDocument</code>, the <code>function</code> of the
	 * <code>Message</code> must be set. The name of the first element of the
	 * SOAP body is used as the function.
	 */
	protected void synchronizeRequestFields(XBUSSystem system)
			throws XException
	{
		NodeList bodyNodes = getBodyNodes(getRequestDocument(system));
		Node function = null;
		/*
		 * The function of the SOAP message is the first element child of the
		 * body.
		 */
		for (int i = 0; i < bodyNodes.getLength(); i++)
		{
			function = bodyNodes.item(i);
			if (function.getNodeType() == Node.ELEMENT_NODE)
			{
				break;
			}
		}
		if (function == null)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_PROTOCOL, Constants.PACKAGE_PROTOCOL_SOAP,
					"1");
		}
		setFunction(function.getLocalName());
	}

	/**
	 * After setting the response data, both <code>setResponseText</code> and
	 * <code>setResponseDocument</code>, some fields of the
	 * <code>Message</code> must be synchronized with the response data. When
	 * an error has occured within the called system, a <code>Fault</code>
	 * element must be included in the response. In this case, the
	 * <code>returncode</code> will be set to <code>RC_NOK</code> and the
	 * <code>errortext</code> will be the concatination of
	 * <code>faultcode</code> and <code>faultstring</code>.
	 */
	protected void synchronizeResponseFields(XBUSSystem system)
			throws XException
	{
		if (getResponseDocument() != null)
		{
			NodeList bodyNodes = getBodyNodes(getResponseDocument());
			Node child = null;
			for (int i = 0; i < bodyNodes.getLength(); i++)
			{
				child = bodyNodes.item(i);
				if ((child.getNodeType() == Node.ELEMENT_NODE)
						&& (SOAP_NAMESPACE.equals(child.getNamespaceURI()))
						&& (SOAP_FAULT.equals(child.getLocalName())))
				{
					Node grandChild = null;
					hasFault = true;
					setReturncode(Constants.RC_NOK);
					NodeList grandChildren = child.getChildNodes();
					String faultcode = "";
					String faultstring = "";
					for (int j = 0; j < grandChildren.getLength(); j++)
					{
						grandChild = grandChildren.item(j);
						if ((grandChild.getNodeType() == Node.ELEMENT_NODE)
								&& (SOAP_FAULTCODE.equals(grandChild
										.getNodeName())))
						{
							faultcode = XMLHelper.getNodeText(grandChild);
						}
						if ((grandChild.getNodeType() == Node.ELEMENT_NODE)
								&& (SOAP_FAULTSTRING.equals(grandChild
										.getNodeName())))
						{
							faultstring = XMLHelper.getNodeText(grandChild);
						}
					}
					setErrortext(faultcode + "_" + faultstring);
					break;
				}
			}
		}
	}

	/**
	 * Returns a list of all nodes included in the SOAP body of the given
	 * <code>Document</code>.
	 */
	private NodeList getBodyNodes(Document doc) throws XException
	{
		return getBody(doc).getChildNodes();
	}

	/**
	 * Returns the SOAP body of the given <code>Document</code>.
	 */
	private Node getBody(Document doc) throws XException
	{
		NodeList bodyList = doc.getElementsByTagNameNS(SOAP_NAMESPACE,
				SOAP_BODY);

		if (bodyList.getLength() == 0)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_PROTOCOL, Constants.PACKAGE_PROTOCOL_SOAP,
					"2");
		}
		if (bodyList.getLength() > 1)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_PROTOCOL, Constants.PACKAGE_PROTOCOL_SOAP,
					"3");
		}

		return bodyList.item(0);
	}

	/**
	 * Returns a W3C document containing an empty SOAPMessage
	 */
	public Document getTemplateAsDocument() throws XException
	{
		return parseXML(getTemplateAsString(), null);
	}

	/**
	 * Returns a XML string containing an empty SOAPMessage
	 */
	static public String getTemplateAsString() throws XException
	{
		StringBuffer retString = new StringBuffer();
		retString.append("<?xml version=\"1.0\" encoding=\"");
		retString.append(Constants.getXMLEncoding());
		retString.append("\"?>");
		retString.append(Constants.LINE_SEPERATOR);
		retString.append("<").append(SOAPENV_QUALIFIER).append(":Envelope ")
				.append("xmlns:").append(SOAPENV_QUALIFIER).append("=\"")
				.append(SOAP_NAMESPACE).append("\"").append(">");
		retString.append(Constants.LINE_SEPERATOR);
		retString.append("<").append(SOAPENV_QUALIFIER).append(":Body").append(
				">");
		retString.append(Constants.LINE_SEPERATOR);
		retString.append("<").append("/").append(SOAPENV_QUALIFIER).append(
				":Body").append(">");
		retString.append(Constants.LINE_SEPERATOR);
		retString.append("<").append("/").append(SOAPENV_QUALIFIER).append(
				":Envelope").append(">");

		return retString.toString();
	}
}
