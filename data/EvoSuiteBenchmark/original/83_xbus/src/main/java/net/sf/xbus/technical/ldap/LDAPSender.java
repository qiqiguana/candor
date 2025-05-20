package net.sf.xbus.technical.ldap;

import java.util.Iterator;
import java.util.Vector;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.base.xml.XMLHelper;
import net.sf.xbus.technical.ObjectSender;
import net.sf.xbus.technical.Sender;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The <code>LDAPSender</code> sends requests to a LDAP server and returns the
 * results. The request and the response are XML documents.
 * <p />
 * Please refer to the documentation about the structure of the XML documents.
 */
public class LDAPSender implements Sender, ObjectSender
{
	private static final String SEARCH_STRING = "SearchString";
	private static final String SEARCH_ATTRIBUTES = "SearchAttributes";
	private static final String CONTEXT = "context";

	private XBUSSystem mDestination = null;

	/**
	 * The constructor stores the destination.
	 * 
	 * @param destination definition of the interface in
	 *            <code>standard.conf</code>
	 * @throws XException never thrown
	 */
	public LDAPSender(XBUSSystem destination)
	{
		mDestination = destination;
	}

	/**
	 * Sends requests to the LDAP server and return the results. Currently only
	 * searching for entries is implemented.
	 * 
	 * @param function not used
	 * @param callData XML document containing requests
	 * @return XML document with results
	 * @throws XException in case of errors
	 */
	public Object execute(String function, Object callData) throws XException
	{
		Document retDocument = XMLHelper.getDocumentBuilder("Default",
				mDestination.getName()).newDocument();
		retDocument.appendChild(retDocument.createElement(mDestination
				.getName()));

		/*
		 * Getting an instance of the LDAPConnection
		 */
		Configuration config = Configuration.getInstance();
		String connectionName = config.getValue(Constants.CHAPTER_SYSTEM,
				mDestination.getName(), "LDAPConnection");
		LDAPConnection connection = LDAPConnection.getInstance(connectionName);

		/*
		 * Execute the given LDAP requests
		 */
		Vector requests = extractRequests((Document) callData);
		NamingEnumeration results = null;
		DirContext context = connection.getContext();
		SearchControls controls = connection.getSearchControls();

		for (Iterator it = requests.iterator(); it.hasNext();)
		{
			RequestContainer request = (RequestContainer) it.next();
			String requestType = request.getType();
			try
			{
				if (requestType.equals(SEARCH_STRING))
				{
					results = context.search(request.getContext(),
							(String) request.getRequest(), controls);
					addResultSearch(results, retDocument, SEARCH_STRING,
							request.getContext(), request.toString());
				}
				else if (requestType.equals(SEARCH_ATTRIBUTES))
				{
					results = context.search(request.getContext(),
							(Attributes) request.getRequest());
					addResultSearch(results, retDocument, SEARCH_ATTRIBUTES,
							request.getContext(), request.toString());
				}
			}
			catch (NamingException e)
			{
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_LDAP, "0", e);
			}
		}
		return retDocument;
	}

	/**
	 * @see net.sf.xbus.technical.Sender#getType()
	 * 
	 * @return Constants.TYPE_OBJECT
	 */
	public String getType()
	{
		return Constants.TYPE_OBJECT;
	}

	private Vector extractRequests(Document doc) throws XException
	{
		Vector retVector = new Vector();

		Node parent = doc.getFirstChild();
		if (parent == null)
		{
			return retVector;
		}

		NodeList nodes = parent.getChildNodes();

		Node requestNode = null;
		String requestString = null;
		RequestContainer request = null;
		NodeList children = null;
		Node child = null;
		Attributes attrs;

		for (int i = 0; i < nodes.getLength(); i++)
		{
			requestNode = nodes.item(i);
			if (requestNode.getNodeType() == Element.ELEMENT_NODE)
			{
				if (requestNode.getNodeName().equals(SEARCH_STRING))
				{
					requestString = XMLHelper.getNodeText(requestNode);
					if (requestString == null)
					{
						throw new XException(Constants.LOCATION_EXTERN,
								Constants.LAYER_TECHNICAL,
								Constants.PACKAGE_TECHNICAL_LDAP, "1");
					}
					else
					{
						request = new RequestContainer(SEARCH_STRING,
								getContext(requestNode), requestString);
						retVector.add(request);
					}
				}
				else if (requestNode.getNodeName().equals(SEARCH_ATTRIBUTES))
				{
					children = requestNode.getChildNodes();
					attrs = new BasicAttributes();
					for (int j = 0; j < children.getLength(); j++)
					{
						child = children.item(j);
						if (child.getNodeType() == Element.ELEMENT_NODE)
						{
							String attributeName = child.getNodeName();
							String attributeValue = XMLHelper
									.getNodeText(child);
							attrs.put(attributeName, attributeValue);
						}
					}
					request = new RequestContainer(SEARCH_ATTRIBUTES,
							getContext(requestNode), attrs);
					retVector.add(request);
				}
				else
				{
					Vector params = new Vector(1);
					params.add(requestNode.getNodeName());
					throw new XException(Constants.LOCATION_EXTERN,
							Constants.LAYER_TECHNICAL,
							Constants.PACKAGE_TECHNICAL_LDAP, "2", params);
				}
			}
		}

		return retVector;
	}

	private void addResultSearch(NamingEnumeration results, Document doc,
			String type, String context, String request) throws XException
	{
		Element resultElement = doc.createElement("Result");
		resultElement.setAttribute(type.toLowerCase(), request);
		resultElement.setAttribute(CONTEXT, context);
		doc.getDocumentElement().appendChild(resultElement);

		Element record = null;
		Element attribute = null;

		SearchResult sr = null;
		NamingEnumeration attrs = null;
		Attribute attr = null;
		String key = null;
		NamingEnumeration values = null;
		String value = null;

		try
		{
			while (results.hasMore())
			{
				sr = (SearchResult) results.next();
				attrs = sr.getAttributes().getAll();

				record = doc.createElement("Record");

				while (attrs.hasMore())
				{
					attr = (Attribute) attrs.next();
					key = attr.getID();
					values = attr.getAll();
					while (values.hasMore())
					{
						value = values.next().toString();
						attribute = doc.createElement(key);
						attribute.appendChild(doc.createTextNode(value));
						record.appendChild(attribute);
					}
				}

				resultElement.appendChild(record);
			}
		}
		catch (NamingException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_LDAP, "0", e);
		}
	}

	private String getContext(Node node)
	{
		NamedNodeMap attributes = node.getAttributes();
		for (int j = 0; j < attributes.getLength(); j++)
		{
			if (attributes.item(j).getNodeName().equals(CONTEXT))
			{ // searched attribute found
				return attributes.item(j).getNodeValue();
			}
		}

		return "";

	}

	private class RequestContainer
	{
		private String mType;
		private String mContext;
		private Object mRequest;

		public RequestContainer(String type, String context, Object request)
		{
			mType = type;
			mContext = context;
			mRequest = request;
		}

		public String getType()
		{
			return mType;
		}

		public String getContext()
		{
			return mContext;
		}
		public Object getRequest()
		{
			return mRequest;
		}

		public String toString()
		{
			return mRequest.toString();
		}
	}
}
