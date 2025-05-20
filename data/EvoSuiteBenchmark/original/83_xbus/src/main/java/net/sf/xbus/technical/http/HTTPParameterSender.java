package net.sf.xbus.technical.http;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.base.xml.XDomSupport;
import net.sf.xbus.base.xml.XMLHelper;
import net.sf.xbus.technical.ObjectSender;
import net.sf.xbus.technical.Sender;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <code>HTTPParameterSender</code> gets a XML document containing parameter
 * and values and sends them as a query string to an URL.
 */
public class HTTPParameterSender extends HTTPSender implements Sender,
		ObjectSender
{
	/**
	 * Stores the given destination.
	 * 
	 * @param destination
	 *            name of the interface definition
	 * @throws XException
	 *             never
	 */
	public HTTPParameterSender(XBUSSystem destination)
	{
		super(destination);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.xbus.technical.ObjectSender#execute(java.lang.String,
	 *      java.lang.Object)
	 */
	public Object execute(String function, Object callData) throws XException
	{
		Document doc = (Document) callData;
		XDomSupport.deleteWhitespaceTextInElementNodes(doc);
		NodeList nodes = doc.getChildNodes();
		if ((nodes == null) || (nodes.getLength() == 0))
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_HTTP, "4");
		}
		Node rootNode = nodes.item(0);

		String url = XMLHelper.getAttribute(rootNode, "url");

		PostMethod method = initialize(function, url);

		nodes = rootNode.getChildNodes();
		String name = null;
		String value = null;
		Vector params = new Vector();
		for (int i = 0; i < nodes.getLength(); i++)
		{
			name = nodes.item(i).getNodeName();
			value = XMLHelper.getNodeText(nodes.item(i));
			if (value == null)
			{
				value = "";
			}
			params.add(new NameValuePair(name, value));
		}

		/*
		 * Copy WILDCARDS from XBUSSystem into parameters
		 */
		Hashtable info = mDestination.getAddresses();
		String key = null;
		for (Enumeration en = info.keys(); en.hasMoreElements();)
		{
			key = (String) en.nextElement();
			if (XBUSSystem.FILENAME_WILDCARD.equals(key))
			{
				value = (String) info.get(key);
				params.add(new NameValuePair(key, value));
			}
		}

		NameValuePair[] paramArray = new NameValuePair[params.size()];
		params.copyInto(paramArray);
		method.setQueryString(paramArray);

		String response = sendMessage(method);
		return XMLHelper.parseXML(response, "Default", mDestination.getName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.xbus.technical.Sender#getType()
	 */
	public String getType()
	{
		return Constants.TYPE_OBJECT;
	}
}
