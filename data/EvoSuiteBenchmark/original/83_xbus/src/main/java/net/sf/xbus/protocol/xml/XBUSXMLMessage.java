package net.sf.xbus.protocol.xml;

import java.util.Enumeration;
import java.util.Hashtable;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.base.xml.XMLHelper;
import net.sf.xbus.protocol.ObjectMessage;
import net.sf.xbus.protocol.TextMessage;
import net.sf.xbus.protocol.XMLMessage;

import org.w3c.dom.Document;

/**
 * <code>XBUSXMLMessage</code> represents messages in the xBus specific
 * XML-format.
 */
public class XBUSXMLMessage extends XMLMessageAbstract implements TextMessage,
		ObjectMessage, XMLMessage
{

	/**
	 * This constructor stores the <code>source</code>, creates an unique
	 * identifier for the call and initializes the requestTimestamp. It is used
	 * when constructing a new <code>XBUSXMLMessage</code> from the data of a
	 * receiver.
	 */
	public XBUSXMLMessage(XBUSSystem source)
	{
		super(source);
		setShortname("XBUSXMLMessage");
	}

	/**
	 * This constructor initializes the new <code>XBUSXMLMessage</code> with
	 * the given parameters. It is used when constructing a new
	 * <code>XBUSXMLMessage</code> by converting it from another
	 * <code>Message</code>.
	 */
	public XBUSXMLMessage(String function, XBUSSystem source, String id)
	{
		super(function, source, id);
		setShortname("XBUSXMLMessage");
	}

	/**
	 * Returns a XML string containing an empty XBUSXMLMessage
	 */
	static public String getTemplateAsString() throws XException
	{
		StringBuffer retString = new StringBuffer();

		retString.append("<?xml version=\"1.0\" encoding=\"");
		retString.append(Constants.getXMLEncoding());
		retString.append("\"?>");
		retString.append(Constants.LINE_SEPERATOR);
		retString.append("<").append(Constants.XBUSXMLMESSAGE_DOCUMENT).append(
				">");
		retString.append(Constants.LINE_SEPERATOR);
		retString.append("    <").append(Constants.XBUSXMLMESSAGE_CALL).append(
				">");
		retString.append(Constants.LINE_SEPERATOR);
		retString.append("        <").append(Constants.XBUSXMLMESSAGE_ID)
				.append(" />");
		retString.append(Constants.LINE_SEPERATOR);
		retString.append("        <").append(Constants.XBUSXMLMESSAGE_FUNCTION)
				.append(" />");
		retString.append(Constants.LINE_SEPERATOR);
		retString.append("        <").append(Constants.XBUSXMLMESSAGE_SOURCE)
				.append(" />");
		retString.append(Constants.LINE_SEPERATOR);
		retString.append("        <").append(Constants.XBUSXMLMESSAGE_ADDRESS)
				.append(" />");
		retString.append(Constants.LINE_SEPERATOR);
		retString.append("        <")
				.append(Constants.XBUSXMLMESSAGE_TIMESTAMP).append(" />");
		retString.append(Constants.LINE_SEPERATOR);
		retString.append("    </").append(Constants.XBUSXMLMESSAGE_CALL)
				.append(">");
		retString.append(Constants.LINE_SEPERATOR);
		retString.append("    <").append(Constants.XBUSXMLMESSAGE_DATA).append(
				" />");
		retString.append(Constants.LINE_SEPERATOR);
		retString.append("</").append(Constants.XBUSXMLMESSAGE_DOCUMENT)
				.append(">");

		return retString.toString();
	}

	/**
	 * Returns a W3C document containing an empty XBUSXMLMessage
	 */
	static public Document getTemplateAsDocument() throws XException
	{
		return XMLHelper
				.parseXML(getTemplateAsString(), "XBUSXMLMessage", null);
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
	protected void synchronizeRequestFields(XBUSSystem system)
			throws XException
	{
		String id = XMLHelper.getTagText(getRequestDocument(system),
				Constants.XBUSXMLMESSAGE_ID);
		if ((id != null) && (id.length() > 0))
		{
			setId(id);
		}
		else
		{
			XMLHelper.setTagText(getRequestDocument(system),
					Constants.XBUSXMLMESSAGE_ID, getId());
		}

		String newFunction = XMLHelper.getTagText(getRequestDocument(system),
				Constants.XBUSXMLMESSAGE_FUNCTION);
		if ((newFunction != null) && (newFunction.length() != 0))
		{
			setFunction(newFunction);
		}
		else
		{
			XMLHelper.setTagText(getRequestDocument(system),
					Constants.XBUSXMLMESSAGE_FUNCTION, getFunction());
		}

		XMLHelper.setTagText(getRequestDocument(system),
				Constants.XBUSXMLMESSAGE_SOURCE, getSource().getName());

		/*
		 * When an address information is contained in the request document,
		 * this address is set in the source of the message. Otherwise the
		 * address of the message will be set in request document.
		 */
		Hashtable addresses = XMLHelper.getTagTextList(
				getRequestDocument(system), Constants.XBUSXMLMESSAGE_ADDRESS);

		if ((addresses != null) && (!addresses.isEmpty()))
		{
			String key = null;
			String value = null;
			for (Enumeration e = addresses.keys(); e.hasMoreElements();)
			{
				key = (String) e.nextElement();
				value = (String) addresses.get(key);
				if (XBUSSystem.FILENAME_WILDCARD_XML.equals(key))
				{
					/*
					 * Switching the XML representation of the WILDCARD to the
					 * "normal" one.
					 */
					key = XBUSSystem.FILENAME_WILDCARD;
				}
				getSource().setAddress(key, value);
			}
		}
		else
		{
			if (getSource().getAddresses() != null)
			{
				/*
				 * Switching the "normal" representation of the WILDCARD to the
				 * XML representation.
				 */
				Hashtable copyAdresses = (Hashtable) getSource().getAddresses()
						.clone();
				String key = null;
				String value = null;
				for (Enumeration e = copyAdresses.keys(); e.hasMoreElements();)
				{
					key = (String) e.nextElement();
					value = (String) copyAdresses.get(key);
					if (XBUSSystem.FILENAME_WILDCARD.equals(key))
					{
						copyAdresses.remove(key);
						copyAdresses.put(XBUSSystem.FILENAME_WILDCARD_XML,
								value);
						break;
					}
				}

				/*
				 * Now setting the adresses in the XML document.
				 */
				XMLHelper.setTagTextList(getRequestDocument(system),
						Constants.XBUSXMLMESSAGE_ADDRESS, copyAdresses);
			}
		}
		XMLHelper.setTagText(getRequestDocument(system),
				Constants.XBUSXMLMESSAGE_TIMESTAMP, Constants.getDateFormat()
						.format(getRequestTimestamp()));
	}

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
	protected void synchronizeResponseFields(XBUSSystem system)
	{
		Document doc = getResponseDocument();

		if (doc != null)
		{
			setReturncode(XMLHelper.getTagText(getResponseDocument(),
					Constants.XBUSXMLMESSAGE_RETURNCODE));
			try
			{
				setErrorcode(Integer.parseInt(XMLHelper.getTagText(
						getResponseDocument(),
						Constants.XBUSXMLMESSAGE_ERRORCODE)));
			}
			catch (Throwable t)
			{
				/*
				 * The errorcode in the XML document is either <null> or not
				 * numeric
				 */
				setErrorcode(0);
			}
			setErrortext(XMLHelper.getTagText(getResponseDocument(),
					Constants.XBUSXMLMESSAGE_ERRORTEXT));
		}

		if ((getReturncode() == null) || (getReturncode().equals("")))
		{
			setReturncode(Constants.RC_OK);
		}
	}
}
