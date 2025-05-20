package net.sf.xbus.base.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


/**
 * <code>XMLHelper</code> offers some support for treating XML structures.
 * 
 * @author Stefan Fleckenstein
 */
public class XMLHelper
{
	private static final String DOUBLELINEFEED = "\n\n";

	private static final String DOUBLELINESEPARATOR = Constants.LINE_SEPERATOR
			+ Constants.LINE_SEPERATOR;

	/**
	 * <code>getTagAttribute</code> gets the text of the attribute named
	 * <code>attr</code> in the first occurence of the <code>tag</code> in
	 * the given <code>org.w3c.dom.Document</code>.
	 * 
	 * @param doc
	 *            the document in which to search
	 * @param tag
	 *            the tag to serch for
	 * @param attr
	 *            the attribute name to search for
	 * @return the attribute value or <code>null</code> if the no
	 *         <code>tag</code> node exists or if the first <code>tag</code>
	 *         node does not contain the attribute <code>attr</code>.
	 */
	static public String getTagAttribute(Document doc, String tag, String attr)
	{
		String retText = null;
		// for the result

		NodeList children = doc.getElementsByTagName(tag);
		// all nodes with the specified tag
		Node currentTag;
		if (children.getLength() > 0)
		{ // some nodes with searched tag found
			currentTag = children.item(0);
			// first occurence of searched tag
			retText = getAttribute(currentTag, attr);
		} // if (children.getLength() > 0)
		return retText;
	} // getTagAttribute(Document doc, String tag, String attr)

	/**
	 * Returns the value of the attribute.
	 * 
	 * @param node
	 *            the node which shall contain the attribute
	 * @param attr
	 *            the attribute name to search for
	 * @return the attribute value or <code>null</code> if the node does not
	 *         contain the attribute <code>attr</code>.
	 */
	public static String getAttribute(Node node, String attr)
	{
		String retText = null;

		NamedNodeMap attributes = node.getAttributes();
		// its attributes
		boolean textFound = false;
		// for terminating search loop
		for (int j = 0; (!textFound) && (j < attributes.getLength()); j++)
		{ // loop over attributes
			if (attributes.item(j).getNodeName().equals(attr))
			{ // searched attribute found
				retText = attributes.item(j).getNodeValue();
				// return its value
				textFound = true;
			} // if (attributes.item(j).getNodeName().equals(attr))
		} // for (int j = 0;(!textFound) && (j < attributes.getLength()); j++)

		return retText;
	}

	/**
	 * <code>getTagCDATA</code> gets the content of the first CDATA section in
	 * the first occurence of the <code>tag</code> in the given
	 * <code>org.w3c.dom.Document</code>.
	 * <p>
	 * 
	 * @param doc
	 *            the document in which to search
	 * @param tag
	 *            the tag to serch for
	 * @return the CDATA section value or <code>null</code> if the no
	 *         <code>tag</code> node exists or if the first <code>tag</code>
	 *         node does not contain a CDATA section labelled with
	 *         <code>tag</code> exists.
	 */
	static public String getTagCDATA(Document doc, String tag)
	{
		String retText = null;
		// for the result

		NodeList children = doc.getElementsByTagName(tag);
		// all nodes with the specified tag
		Node currentTag;
		if (children.getLength() > 0)
		{ // some nodes with searched tag found
			currentTag = children.item(0);
			// first occurence of searched tag
			NodeList grandchildren = currentTag.getChildNodes();
			// its child nodes
			boolean textFound = false;
			// for terminating search loop
			for (int j = 0; (!textFound) && j < grandchildren.getLength(); j++)
			{ // loop over child nodes
				if (grandchildren.item(j).getNodeType() == Node.CDATA_SECTION_NODE)
				{ // CDATA section found
					retText = grandchildren.item(j).getNodeValue();
					// return its value
					textFound = true;
				} // if
				// (grandchildren.item(j).getNodeType()==Node.CDATA_SECTION_NODE)
			} // for (int j = 0;(!textFound) && j < grandchildren.getLength();
			// j++)
		} // if (children.getLength() > 0)

		/*
		 * Workaround, since the Serializer disturbs the linefeeds of the CDATA
		 */
		if (!DOUBLELINESEPARATOR.equals(DOUBLELINEFEED))
		{
			retText = retText.replaceAll(DOUBLELINEFEED,
					Constants.LINE_SEPERATOR);
		} // if (!DOUBLELINESEPARATOR.equals(DOUBLELINEFEED))
		return retText;
	} // getTagCDATA(Document doc, String tag)

	/**
	 * Gets the text of the first occurence of the <code>tag</code> in the
	 * given <code>org.w3c.dom.Document</code>. Exactly spoken, it retrieves
	 * the value of the first text node of that occurence.
	 * 
	 * @param doc
	 *            the document in which to search
	 * @param tag
	 *            the tag to serch for
	 * @return the text value or <code>null</code> if no <code>tag</code>
	 *         node exists of if the first one does nor contain text.
	 */
	static public String getTagText(Document doc, String tag)
	{
		String retText = null;
		// for the result

		NodeList children = doc.getElementsByTagName(tag);
		// all nodes with the specified tag
		Node foundTag;
		if (children.getLength() > 0)
		{ // some nodes with searched tag found
			foundTag = children.item(0);
			// first occurence of searched tag
			retText = getNodeText(foundTag);
		} // if (children.getLength() > 0)
		return retText;
	} // getTagText(Document doc, String tag)

	public static String getNodeText(Node tag)
	{
		String retText = null;
		NodeList children = tag.getChildNodes();
		boolean textFound = false;
		for (int j = 0; (!textFound) && j < children.getLength(); j++)
		{
			if (children.item(j).getNodeType() == Node.TEXT_NODE)
			{
				retText = children.item(j).getNodeValue();
				textFound = true;
			}
		}
		return retText;
	}

	/**
	 * Gets the name/value list composed by the child element nodes of the first
	 * occurence of the <code>tag</code> in the given
	 * <code>org.w3c.dom.Document</code>. The mentioned name is the tag of
	 * the child node and the value in the name/value pair just the node value.
	 * Child nodes other than element nodes are ignored. The value is extracted
	 * from the first child node of the element node - if this one is a text
	 * node. All other child nodes are ignored.
	 * 
	 * @param doc
	 *            the document in which to search
	 * @param tag
	 *            the tag to serch for
	 * @return the text value or <code>null</code> if no <code>tag</code>
	 *         node exists of if the first one does nor contain text.
	 * @throws XException
	 *             if one of the examined element nodes for a name/value pair
	 *             has no child or another node than a text node as first child
	 */
	static public Hashtable getTagTextList(Document doc, String tag)
			throws XException
	{
		Hashtable retTable = null;
		// for return object

		NodeList children = doc.getElementsByTagName(tag);
		// all nodes with the specified tag
		Node foundTag;
		if (children.getLength() > 0)
		{ // some nodes with searched tag found
			retTable = new Hashtable();
			foundTag = children.item(0);
			// first occurence of searched tag

			NodeList grandchildren = foundTag.getChildNodes();
			// its child nodes

			for (int j = 0; j < grandchildren.getLength(); j++)
			{ // loop over child nodes
				Node grand = grandchildren.item(j);
				if (grand.getNodeType() == Node.ELEMENT_NODE)
				{ // element node found
					Node grand2 = grandchildren.item(j).getFirstChild();
					if ((grand2 != null)
							&& (grand2.getNodeType() == Node.TEXT_NODE))
					{ // text node found to read the value
						String nodeName = grandchildren.item(j).getNodeName();
						String nodeValue = grand2.getNodeValue();
						retTable.put(nodeName, nodeValue);
					} // then ((grand2 != null) && (grand2.getNodeType() ==
					// Node.TEXT_NODE))
					else
					{ // no child or one of wrong node type
						throw new XException(Constants.LOCATION_INTERN,
								Constants.LAYER_COREBASE,
								Constants.PACKAGE_COREBASE_XML, "1");
					} // else ((grand2 != null) && (grand2.getNodeType() ==
					// Node.TEXT_NODE))
				} // if (grand.getNodeType() == Node.ELEMENT_NODE)
			} // for (int j = 0; j < grandchildren.getLength(); j++)
		} // if (children.getLength() > 0)
		return retTable;
	} // getTagTextList(Document doc, String tag)

	/**
	 * <code>setTagTextList</code> stotes a list of name/value pairs in the
	 * first occurence of the <code>tag</code> in the given
	 * <code>org.w3c.dom.Document</code>. Each pair makes up an element node
	 * with the given name as tag and the given value as node value (in a text
	 * node). If the <code>tag</code> doesn't exist, nothing happens. If
	 * <code>tag</code> exists, its previous content is replaced by the list
	 * of text nodes.
	 * 
	 * @param doc
	 *            the <code>org.w3c.dom.Document</code> containing the tag
	 * @param tag
	 *            name of the tag that should be replaced
	 * @param entries
	 *            contains the data for the list of text nodes. Both keys and
	 *            elements must be of type <code>String</code>. The keys are
	 *            treated as tag names, the elements are treated as values for
	 *            the text nodes.
	 */
	static public void setTagTextList(Document doc, String tag,
			Hashtable entries)
	{
		NodeList children = doc.getElementsByTagName(tag);
		// all nodes with the specified tag
		if (children.getLength() > 0)
		{ // some nodes with searched tag found
			Element newElement = doc.createElement(tag);
			// The old tag node will just be replaced.
			for (Enumeration e = entries.keys(); e.hasMoreElements();)
			{ // loop over name/value pairs
				String key = (String) e.nextElement();
				Element child = doc.createElement(key);
				// the element node from the name ...
				child
						.appendChild(doc.createTextNode((String) entries
								.get(key)));
				// ... plus the text node for the value
				newElement.appendChild(child);
				// to the tag node
			} // for (Enumeration enum = entries.keys();
			// enum.hasMoreElements();)

			Node oldTag = children.item(0);
			// Replace the first tag occurence ...
			Node father = oldTag.getParentNode();
			// ... in its parent node ...
			father.replaceChild(newElement, oldTag);
			// by the newly constructed node containg the name/value pairs
		} // if (children.getLength() > 0)
	} // setTagTextList(Document doc, String tag, Hashtable entries)

	/**
	 * <code>setTagText</code> sets the <code>text</code> in the first
	 * occurence of the <code>tag</code> in the given
	 * <code>org.w3c.dom.Document</code>. If the <code>tag</code> doesn't
	 * exist, nothing happens, otherwise the corresponding node is completely
	 * replaced.
	 */
	static public void setTagText(Document doc, String tag, String text)
	{
		NodeList children = doc.getElementsByTagName(tag);
		// all nodes with the specified tag
		if (children.getLength() > 0)
		{ // some nodes with searched tag found
			Element newElement = doc.createElement(tag);
			// The old tag node will just be replaced.
			newElement.appendChild(doc.createTextNode(text));
			// the value to store
			Node oldTag = children.item(0);
			// Replace the first tag occurence ...
			Node father = oldTag.getParentNode();
			// ... in its parent node ...
			father.replaceChild(newElement, oldTag);
			// by the newly constructed node
		} // if (children.getLength() > 0)
	} // setTagText(Document doc, String tag, String text)

	/**
	 * <code>setTagCDATA</code> sets the <code>text</code> in the first
	 * occurence of the <code>tag</code> in the given
	 * <code>org.w3c.dom.Document</code> as CDATA section. If the
	 * <code>tag</code> doesn't exist, nothing happens, otherwise the
	 * corresponding node is completely replaced.
	 */
	static public void setTagCDATA(Document doc, String tag, String text)
	{
		NodeList children = doc.getElementsByTagName(tag);
		// all nodes with the specified tag
		if (children.getLength() > 0)
		{ // some nodes with searched tag found
			Element newElement = doc.createElement(tag);
			// The old tag node will just be replaced.
			newElement.appendChild(doc.createCDATASection(text));
			// the value to store
			Node oldTag = children.item(0);
			// Replace the first tag occurence ...
			Node father = oldTag.getParentNode();
			// ... in its parent node ...
			father.replaceChild(newElement, oldTag);
			// by the newly constructed node
		} // if (children.getLength() > 0)

	} // setTagCDATA(Document doc, String tag, String text)

	/**
	 * Deletes the first occurance of the <code>tag</code> in the given
	 * <code>org.w3c.dom.Document</code>.
	 * <p>
	 * Does nothing if the <code>tag</code> doesn't exist.
	 */
	static public void deleteTag(Document doc, String tag)
	{
		NodeList children = doc.getElementsByTagName(tag);
		// all nodes with the specified tag
		Node foundTag;
		if (children.getLength() > 0)
		{
			foundTag = children.item(0);
			// the first tag occurence ...
			Node father = foundTag.getParentNode();
			// its parent node
			father.removeChild(foundTag);
		} // if (children.getLength() > 0)
	} // deleteTag(Document doc, String tag)

	/**
	 * Parses the given XML string.
	 */
	static public Document parseXML(String xml, String messageName,
			String system) throws XException
	{
		Document retDocument = null;

		if (messageName == null)
		{
			messageName = "Default";
		}

		if ((xml != null) && (xml.length() > 0))
		{
			try
			{
				ByteArrayInputStream xmlStream = new ByteArrayInputStream(xml
						.getBytes(Constants.getXMLEncoding()));
				InputSource inputSource = new InputSource(xmlStream);
				String systemString = new StringBuffer().append(
						Constants.XBUS_ETC).append("dtd").append(
						Constants.FILE_SEPERATOR).toString();

				String systemId = new File(systemString).toURL().toString();
				inputSource.setSystemId(systemId);

				DOMParser parser = new DOMParser();

				parser.setFeature("http://xml.org/sax/features/validation",
						getValidating(messageName, system));
				parser.setFeature("http://xml.org/sax/features/namespaces",
						getNamespaceAware(messageName, system));
				String schema = getXMLSchema(messageName, system);
				if (schema != null)
				{
					parser.setFeature("http://xml.org/sax/features/namespaces",
							true);
					parser.setFeature("http://xml.org/sax/features/validation",
							true);
					parser.setFeature(
							"http://apache.org/xml/features/validation/schema",
							true);
					parser
							.setProperty(
									"http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation",
									new StringBuffer().append(
											Constants.XBUS_ETC)
											.append("Schema").append(
													Constants.FILE_SEPERATOR)
											.append(schema).toString());
				}
				parser.setErrorHandler(new XParserErrorHandler());

				parser.parse(inputSource);
				retDocument = parser.getDocument();
			}
			catch (Exception e)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_COREBASE,
						Constants.PACKAGE_COREBASE_XML, "0", e);
			}
		}
		return retDocument;
	}

	/**
	 * Returns a <code>DocumentBuilder</code> needed for parsing. For better
	 * performance, <code>DocumentBuilders</code> are cached per thread. Due
	 * to the message type the parser may be instructed
	 * <ul>
	 * <li>to ignore whitespace in elements,</li>
	 * <li>to ignore comments,</li>
	 * <li>to validate the xml document against its DTD.</li>
	 * </ul>
	 * Configuration keys control these options.
	 * 
	 * @return a {@link javax.xml.parsers.DocumentBuilder DocumentBuilder}
	 *         object for xml parsing
	 * @throws XException
	 *             in case that any problem with instantiating the
	 *             <code>DocumentBuilder</code> object arises
	 */
	static public DocumentBuilder getDocumentBuilder(String messageName,
			String system) throws XException
	{
		DocumentBuilder docBuilder;

		if (messageName == null)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_COREBASE, Constants.PACKAGE_COREBASE_XML,
					"2");
		}

		try
		// to cast to XException
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory
					.setIgnoringComments(getIgnoringComments(messageName,
							system));
			factory
					.setIgnoringElementContentWhitespace(getIgnoringElementContentWhitespace(
							messageName, system));
			factory.setValidating(getValidating(messageName, system));
			factory.setNamespaceAware(getNamespaceAware(messageName, system));

			docBuilder = factory.newDocumentBuilder();
			docBuilder.setErrorHandler(new XParserErrorHandler());
		} // try
		catch (ParserConfigurationException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_COREBASE, Constants.PACKAGE_COREBASE_XML,
					"0", e);
		} // catch
		return docBuilder;
	} // getDocumentBuilder()

	private static boolean getIgnoringComments(String messageName, String system)
			throws XException
	{
		Configuration standConf = Configuration.getInstance();
		Configuration xbusConf = Configuration.getInstance("xbus");

		boolean setIgnoringComments = xbusConf.getValueAsBoolean(
				"ParserSettings", messageName, "IgnoringComments");
		if (system != null)
		{
			setIgnoringComments = setIgnoringComments
					|| standConf.getValueAsBooleanOptional(
							Constants.CHAPTER_SYSTEM, system,
							"IgnoringComments");
		}
		return setIgnoringComments;
	}

	private static boolean getIgnoringElementContentWhitespace(
			String messageName, String system) throws XException
	{
		Configuration standConf = Configuration.getInstance();
		Configuration xbusConf = Configuration.getInstance("xbus");

		boolean setIgnoringElementContentWhitespace = xbusConf
				.getValueAsBoolean("ParserSettings", messageName,
						"IgnoringElementContentWhitespace");
		if (system != null)
		{
			setIgnoringElementContentWhitespace = setIgnoringElementContentWhitespace
					|| standConf.getValueAsBooleanOptional(
							Constants.CHAPTER_SYSTEM, system,
							"IgnoringElementContentWhitespace");
		}
		return setIgnoringElementContentWhitespace;
	}

	private static boolean getValidating(String messageName, String system)
			throws XException
	{
		Configuration standConf = Configuration.getInstance();
		Configuration xbusConf = Configuration.getInstance("xbus");

		boolean setValidating = xbusConf.getValueAsBoolean("ParserSettings",
				messageName, "XMLValidating");
		if (system != null)
		{
			setValidating = setValidating
					|| standConf.getValueAsBooleanOptional(
							Constants.CHAPTER_SYSTEM, system, "XMLValidating");
		}
		return setValidating;
	}

	private static String getXMLSchema(String messageName, String system)
			throws XException
	{
		Configuration standConf = Configuration.getInstance();
		Configuration xbusConf = Configuration.getInstance("xbus");

		String schema = null;

		if (system != null)
		{
			schema = standConf.getValueOptional(Constants.CHAPTER_SYSTEM,
					system, "XMLSchema");
		}
		if (schema == null)
		{
			schema = xbusConf.getValueOptional("ParserSettings", messageName,
					"XMLSchema");
		}
		return schema;
	}

	private static boolean getNamespaceAware(String messageName, String system)
			throws XException
	{
		Configuration standConf = Configuration.getInstance();
		Configuration xbusConf = Configuration.getInstance("xbus");

		boolean setNamespaceAware = xbusConf.getValueAsBoolean(
				"ParserSettings", messageName, "NamespaceAware");
		if (system != null)
		{
			setNamespaceAware = setNamespaceAware
					|| standConf.getValueAsBooleanOptional(
							Constants.CHAPTER_SYSTEM, system, "NamespaceAware");
		}
		return setNamespaceAware;
	}

	/**
	 * Serializes a XML document.
	 * 
	 * @param doc
	 *            the given <code>org.w3c.dom.Document</code> to be serialized
	 * @param systemID
	 *            reference to the DTD of the XML document
	 * @return the content of the XML document in its string representation
	 */
	static public String serializeXML(Document doc, String systemID)
			throws XException
	{
		String xmlData = null;

		if (doc != null)
		{
			Transformer serializer = getSerializer(systemID);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();

			try
			{
				serializer.transform(new DOMSource(doc), new StreamResult(
						outStream));
				xmlData = outStream.toString(Constants.getXMLEncoding());
				outStream.close();

				// The serializer contains a bug and replaces DOS line breaks
				// "\r\n" by "\r\r\n" within any element text.
				// This is corrected in the follwing instruction.
				xmlData = xmlData.replaceAll("\r\r\n", "\r\n");
			}
			catch (TransformerException e)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_COREBASE,
						Constants.PACKAGE_COREBASE_XML, "0", e);
			}
			catch (IOException e)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_COREBASE,
						Constants.PACKAGE_COREBASE_XML, "0", e);
			}
		}
		return xmlData;
	}

	/**
	 * Returns a <code>Serializer</code> needed for serializing XML documents.
	 */
	static private Transformer getSerializer(String systemID) throws XException
	{
		setTransformerProperties();

		Transformer serializer;
		TransformerFactory tfactory = TransformerFactory.newInstance();
		// This creates a transformer that does a simple identity transform,
		// and thus can be used for all intents and purposes as a serializer.
		try
		{
			serializer = tfactory.newTransformer();
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.setOutputProperty(OutputKeys.METHOD, "xml");
			serializer.setOutputProperty(OutputKeys.ENCODING, Constants
					.getXMLEncoding());
			if ((systemID != null) && (systemID.length() > 0))
			{
				serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,
						systemID);
			}
		}
		catch (Throwable e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_COREBASE, Constants.PACKAGE_COREBASE_XML,
					"0", e);
		}
		return serializer;
	}

	/**
	 * 
	 */
	public static void setTransformerProperties() throws XException
	{
		if (System.getProperty("java.version").startsWith("1.5"))
		{
			System
					.setProperty("javax.xml.transform.TransformerFactory",
							"com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");
		}

		String transformerProperty = Configuration.getInstance()
				.getValueOptional(Constants.CHAPTER_BASE, "XML",
						"TransformerFactory");
		if (transformerProperty != null)
		{
			System.setProperty("javax.xml.transform.TransformerFactory",
					transformerProperty);
		}
	}
} // XMLHelper
