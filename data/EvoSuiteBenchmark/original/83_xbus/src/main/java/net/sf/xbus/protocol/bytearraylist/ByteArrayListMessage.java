package net.sf.xbus.protocol.bytearraylist;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;

import net.sf.xbus.base.bytearraylist.ByteArrayConverter;
import net.sf.xbus.base.bytearraylist.ByteArrayConverterFactory;
import net.sf.xbus.base.bytearraylist.ByteArrayList;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.bytearrays.XByteArraySupport;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.protocol.ObjectMessage;
import net.sf.xbus.protocol.xml.XMLMessageAbstract;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * <code>ByteArrayListMessage</code
 */
public class ByteArrayListMessage extends XMLMessageAbstract
		implements
			ObjectMessage
{

	private static final String TAG_LINES = "Lines";
	private static final String TAG_RECORD = "Record";
	private static final String TAG_LENGTH = "Length";

	private String mEncodingSystem = null;

	/**
	 * Constructor for ByteArrayListMessage.
	 * 
	 * @param source
	 */
	public ByteArrayListMessage(XBUSSystem source)
	{
		super(source);
		setShortname("ByteArrayListMessage");
		mEncodingSystem = source.getName();
	}

	/**
	 * Constructor for ByteArrayListMessage.
	 * 
	 * @param function
	 * @param source
	 * @param id
	 */
	public ByteArrayListMessage(String function, XBUSSystem source, String id)
	{
		super(function, source, id);
		setShortname("ByteArrayListMessage");
		mEncodingSystem = source.getName();
	}

	/**
	 * Parses the incoming {@link net.sf.xbus.base.bytearraylist.ByteArrayList}into
	 * a XML document.
	 */
	public void setRequestObject(Object obj, XBUSSystem source)
			throws XException
	{
		setRequestDocument(parseList((ByteArrayList) obj, source), source);
	}

	/**
	 * Empty method
	 */
	public void setResponseObject(Object obj, XBUSSystem destination)
	{}

	/**
	 * @return {@link net.sf.xbus.base.bytearraylist.ByteArrayList}
	 */
	public Object getRequestObject(XBUSSystem system) throws XException
	{
		return serializeList(getRequestDocument(system));
	}

	/**
	 * @return always <code>null</code>
	 */
	public Object getResponseObject()
	{
		return null;
	}

	public void setEncodingSystem(XBUSSystem system)
	{
		mEncodingSystem = system.getName();
	}

	private Document parseList(ByteArrayList source, XBUSSystem system)
			throws XException
	{

		/*
		 * 1. Creating the root element
		 */
		DocumentBuilder builder = getDocumentBuilder(system);
		Document doc = builder.newDocument();
		Element root = doc.createElement(mEncodingSystem);
		doc.appendChild(root);

		/*
		 * 2. Getting the interface description
		 */
		String interfaceDescriptionFilename = getInterfaceDescriptionFilename(mEncodingSystem);
		Document interfaceDescription = null;
		try
		{
			interfaceDescription = builder.parse(interfaceDescriptionFilename);
		}
		catch (IOException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_BYTEARRAYLIST, "0", e);
		}
		catch (SAXException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_BYTEARRAYLIST, "0", e);
		}

		/*
		 * 3. Getting the position, length and list of identifiers
		 */
		NodeList lines = interfaceDescription.getElementsByTagName(TAG_LINES);
		if (lines.getLength() != 1)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_BYTEARRAYLIST, "10");
		}

		Node identifierTag = lines.item(0);
		NamedNodeMap attributes = identifierTag.getAttributes();
		int identifierPos = getIdentifierPos(attributes);
		int identifierLength = getIdentifierLength(attributes);

		Hashtable identifiers = getIdentifiers(interfaceDescription);

		// Hashtable recordLengths = computeRecordLengths(interfaceDescription);

		/*
		 * 4. Parsing the list of byte arrays
		 */
		String identifier;
		String identifierName;
		Element recordNode;
		Element fieldNode;
		Node valueNode;
		int pos;
		byte[] record;
		byte[] fieldBytes;
		Field field;
		ByteArrayConverter conv = ByteArrayConverterFactory
				.getConverter(mEncodingSystem);
		Hashtable fields = getFields(interfaceDescription);
		for (Iterator it = (source).iterator(); it.hasNext();)
		{
			record = (byte[]) it.next();
			recordNode = doc.createElement(TAG_RECORD);
			recordNode.setAttribute(TAG_LENGTH, String.valueOf(record.length));
			identifier = conv.byteArrayToString(XByteArraySupport.subArray(
					record, identifierPos, identifierLength));
			identifierName = (String) identifiers.get(identifier);
			List fieldList = (List) fields.get(identifierName);
			pos = 0;
			for (Iterator it1 = fieldList.iterator(); it1.hasNext();)
			{
				field = (Field) it1.next();
				fieldBytes = XByteArraySupport.subArray(record, pos,
						field.length);
				fieldNode = doc.createElement(field.name);
				String value = conv.byteArrayToString(fieldBytes);
				valueNode = doc.createTextNode(value);
				fieldNode.appendChild(valueNode);
				fieldNode
						.setAttribute(TAG_LENGTH, String.valueOf(field.length));
				recordNode.appendChild(fieldNode);
				pos = pos + field.length;
			}
			root.appendChild(recordNode);
		}
		return doc;
	}

	private int getIdentifierPos(NamedNodeMap attributes) throws XException
	{
		int identifierPos;
		Node attribute = attributes.getNamedItem("IdentifierPos");
		if (attribute != null)
		{
			identifierPos = Integer.parseInt(attribute.getNodeValue());
		}
		else
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_BYTEARRAYLIST, "11");
		}
		return identifierPos;
	}

	private int getIdentifierLength(NamedNodeMap attributes) throws XException
	{
		Node attribute;
		int identifierLength;
		attribute = attributes.getNamedItem("IdentifierLength");
		if (attribute != null)
		{
			identifierLength = Integer.parseInt(attribute.getNodeValue());
		}
		else
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_BYTEARRAYLIST, "12");
		}
		return identifierLength;
	}

	private ByteArrayList serializeList(Document doc) throws XException
	{
		ByteArrayList retArrayList = new ByteArrayList();

		ByteArrayConverter conv = ByteArrayConverterFactory
				.getConverter(mEncodingSystem);

		NodeList records = doc.getElementsByTagName(TAG_RECORD);
		Node recordNode;
		NamedNodeMap recordAttributes;
		Attr recordLengthAttr;
		int recordLength;
		byte[] recordArray;

		NodeList fields;
		Node fieldNode;
		NamedNodeMap fieldAttributes;
		Attr fieldLengthAttr;
		int fieldLength;
		Node fieldTextNode;
		String fieldValue;
		byte[] fieldBytes;
		int pos;

		for (int i = 0; i < records.getLength(); i++)
		{
			recordNode = records.item(i);
			recordAttributes = recordNode.getAttributes();
			recordLengthAttr = (Attr) recordAttributes.getNamedItem(TAG_LENGTH);
			recordLength = Integer.parseInt(recordLengthAttr.getNodeValue());
			recordArray = new byte[recordLength];

			pos = 0;

			fields = recordNode.getChildNodes();
			for (int k = 0; k < fields.getLength(); k++)
			{
				fieldNode = fields.item(k);

				if (fieldNode.getNodeType() == Node.ELEMENT_NODE)
				{
					fieldAttributes = fieldNode.getAttributes();
					fieldLengthAttr = (Attr) fieldAttributes
							.getNamedItem(TAG_LENGTH);
					fieldLength = Integer.parseInt(fieldLengthAttr
							.getNodeValue());
					fieldTextNode = fieldNode.getFirstChild();
					fieldValue = fieldTextNode.getNodeValue();
					fieldBytes = conv
							.stringToByteArray(fieldValue, fieldLength);
					for (int m = 0; m < fieldBytes.length; m++)
					{
						recordArray[pos] = fieldBytes[m];
						pos++;
					}
				}
			}
			retArrayList.add(recordArray);
		}
		return retArrayList;
	}

	static private String getInterfaceDescriptionFilename(String system)
			throws XException
	{
		Configuration config = Configuration.getInstance();
		String interfaceDescriptionFilename = config.getValue(
				Constants.CHAPTER_SYSTEM, system, "DescriptionFile");

		return new StringBuffer(Constants.XBUS_ETC).append(
				"InterfaceDescriptions").append(Constants.FILE_SEPERATOR)
				.append(interfaceDescriptionFilename).toString();
	}

	private Hashtable getIdentifiers(Document interfaceDescription)
			throws XException
	{
		Hashtable identifiers = new Hashtable();
		NamedNodeMap attributes = null;
		Node attribute = null;
		NodeList recordTypes = interfaceDescription
				.getElementsByTagName("RecordType");
		if (recordTypes.getLength() < 1)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_BYTEARRAYLIST, "14");
		}
		Node recordType;
		Node nameNode;
		String identifierString;
		String identifierName;
		for (int i = 0; i < recordTypes.getLength(); i++)
		{
			recordType = recordTypes.item(i);
			attributes = recordType.getAttributes();
			attribute = attributes.getNamedItem("Identifier");
			if (attribute != null)
			{
				identifierString = attribute.getNodeValue();
			}
			else
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_BYTEARRAYLIST, "15");
			}

			nameNode = recordType.getFirstChild();
			identifierName = nameNode.getNodeValue();
			identifiers.put(identifierString, identifierName);
		}
		return identifiers;
	}

	private Hashtable getFields(Document interfaceDescription)
			throws XException
	{
		Hashtable fields = new Hashtable();

		NodeList recordtypes = interfaceDescription
				.getElementsByTagName("RecordTypeSpec");

		NamedNodeMap attributes;
		Node attribute;
		String identifierString;
		NodeList fieldNodes;
		Node node;
		Field field = null;
		Node name, length;
		List fieldList;

		for (int i = 0; i < recordtypes.getLength(); i++)
		{
			Node recordtype = recordtypes.item(i);
			attributes = recordtype.getAttributes();
			attribute = attributes.getNamedItem("Name");
			if (attribute != null)
			{
				identifierString = attribute.getNodeValue();
			}
			else
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_BYTEARRAYLIST, "15");
			}

			fieldList = new Vector();
			fieldNodes = recordtype.getChildNodes();
			for (int k = 0; k < fieldNodes.getLength(); k++)
			{
				node = fieldNodes.item(k);
				if (("Field".equals(node.getNodeName()))
						&& (node.getNodeType() == Node.ELEMENT_NODE))
				{
					field = new Field();
					attributes = node.getAttributes();
					name = attributes.getNamedItem("Name");
					if (name != null)
					{
						field.name = name.getNodeValue();
					}
					else
					{
						throw new XException(Constants.LOCATION_INTERN,
								Constants.LAYER_PROTOCOL,
								Constants.PACKAGE_PROTOCOL_BYTEARRAYLIST, "16");
					}
					length = attributes.getNamedItem("Length");
					if (length != null)
					{
						try
						{
							field.length = Integer.parseInt(length
									.getNodeValue());
						}
						catch (NumberFormatException e)
						{
							throw new XException(Constants.LOCATION_EXTERN,
									Constants.LAYER_PROTOCOL,
									Constants.PACKAGE_PROTOCOL_BYTEARRAYLIST,
									"17", e);
						}
					}
					else
					{
						throw new XException(Constants.LOCATION_EXTERN,
								Constants.LAYER_PROTOCOL,
								Constants.PACKAGE_PROTOCOL_BYTEARRAYLIST, "18");
					}
					fieldList.add(field);
				}
			}
			fields.put(identifierString, fieldList);
		}
		return fields;
	}

	protected void synchronizeRequestFields(XBUSSystem system)
	{}

	protected void synchronizeResponseFields(XBUSSystem system)
	{}

	/**
	 * Acts as a container for the information of one field of either an input
	 * or an output parameter.
	 */
	class Field
	{
		public String name = null;
		public int length = 0;
	}
}
