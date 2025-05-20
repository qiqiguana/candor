package net.sf.xbus.protocol.records;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import net.sf.xbus.base.bytearraylist.ByteArrayConverter;
import net.sf.xbus.base.bytearraylist.ByteArrayConverterFactory;
import net.sf.xbus.base.bytearraylist.ByteArrayList;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xml.XDomSupport;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <code>RecordTypeSerializer</code> serves for serializing records stored in
 * a DOM tree according to a record type structure.
 * <p>
 * The DOM tree structure is described in the
 * {@link net.sf.xbus.protocol.records.RecordTypeParser RecordTypeParser}
 * documentation.
 * </p>
 * <p>
 * The serialization result is a String. <br>
 * The string structure is described by a xml file - in particular the contained
 * record types. Such describing files are of document type
 * <code>InterfaceSpec</code> declared in "InterfaceSpec.dtd". The structure
 * of the internal DOM tree to serialise is described on the
 * {@link net.sf.xbus.protocol.records.RecordTypeParser RecordTypeParser}
 * documentation page.
 * </p>
 * 
 * @author Lars Meßner, Stehan Düwel
 */
public class RecordTypeSerializer extends RecordTypeManipulator
{

	// //////////////////////
	// Static fields
	// //////////////////////

	/**
	 * <code>instances</code> stores the instances by thread and the interface
	 * type.
	 */
	private static Hashtable instances = new Hashtable();
	private static final Object classLock = RecordTypeSerializer.class;

	// //////////////////////
	// Constructors
	// //////////////////////

	/**
	 * Just the standard constructor.
	 */
	private RecordTypeSerializer() throws XException
	{
		super();
	} // RecordTypeParser()

	// //////////////////////
	// Static methods
	// //////////////////////

	/**
	 * <code>getInstance()</code> retrieves the an instance of
	 * <code>RecordTypeSerializer</code>.
	 * 
	 * @param sourceType the name for the parsed structure as it is derived from
	 *            the DOM tree with the interface content
	 * @throws XException in case of missing <code>sourceType</code>
	 */
	public static RecordTypeSerializer getInstance(String sourceType,
			int interfaceContentClass) throws XException
	{
		synchronized (classLock)
		{
			if (sourceType == null || sourceType.length() == 0)
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "68");

			RecordTypeSerializer instance = (RecordTypeSerializer) instances
					.get(Thread.currentThread().getName() + sourceType
							+ interfaceContentClass);
			if (instance == null)
			{ // no suitable instance available yet
				instance = new RecordTypeSerializer();
				instances.put(Thread.currentThread().getName() + sourceType
						+ interfaceContentClass, instance);
			} // if (instance==null)
			return instance;
		}
	} // getInstance(String sourceType)

	/**
	 * Method getByteArrayConverter.
	 * 
	 * @return ByteArrayConverter
	 */
	protected ByteArrayConverter getByteArrayConverter() throws XException
	{
		return ByteArrayConverterFactory.getConverter(sourceType);
	}

	// /////////////////////////////////////////
	// Serializing the contents
	// /////////////////////////////////////////

	/**
	 * <code>serialize</code> serializes the specified DOM tree due to the
	 * record type definition.
	 * 
	 * @param docContent the DOM tree with the interface content
	 * @return the serialized string
	 * @throws XException in case of an unexpected value
	 */
	public Object serialize(Document docContent) throws XException
	{
		if (interfaceStructure == null)
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "99");

		// The root element has the content type as xml tag.
		Element root = docContent.getDocumentElement();
		if (contentType == null || !contentType.equals(root.getNodeName()))
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "120");

		Node node = root.getFirstChild();
		Object result = null;
		if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING)
		{
			result = "";
		}
		else if (interfaceContentClass == Constants.IFCONTENTCLASS_BYTEARRAYLIST)
		{
			result = new ByteArrayList();
		}
		else
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "100");
		}

		if (node == null)
		{
			return result;
		}

		if (!node.getNodeName().equals("Records"))
		{ // not the records section - thus a header
			if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING)
				result = serializeHeader(node) + Constants.LINE_SEPERATOR;
			else
				((ByteArrayList) result).add((byte[]) serializeHeader(node));
			// Go to the records section
			node = node.getNextSibling();
		} // if (!node.getNodeName().equals("Records"))

		// Now the records section
		if (node == null || !node.getNodeName().equals("Records"))
		{
			List params = new Vector();
			params.add("Records expected");
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "122", params);
		} // if (node == null || !node.getNodeName().equals("Records"))
		if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING)
			result = result + (String) serializeRecords(node);
		else
			((ByteArrayList) result)
					.addAll((ByteArrayList) serializeRecords(node));

		// Go to trailer or end
		node = node.getNextSibling();
		if (node != null)
		{ // trailer found
			if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING)
				result = result + (String) serializeTrailer(node);
			else
				((ByteArrayList) result).add((byte[]) serializeTrailer(node));
			// Go to end
			node = node.getNextSibling();
		} // if (node!=null)
		else if (result != null
				&& interfaceContentClass == Constants.IFCONTENTCLASS_STRING
				&& ((String) result).length() > 0)
			// no trailer
			// Cut off the line separator after the last record.
			result = ((String) result).substring(0, ((String) result).length()
					- Constants.LINE_SEPERATOR.length());

		// Must be the end.
		if (node != null)
		{
			List params = new Vector();
			params.add(node.getNodeName());
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "123", params);
		} // if (node != null)

		return result;
	} // serialize(Document docContent)

	/**
	 * <code>serializeHeader</code> parses the header.
	 * 
	 * @param headerNode the header in the content DOM tree
	 * @return the serialized header as {@link java.lang.String string}
	 * @throws XException in case of an unexpected value
	 */
	private Object serializeHeader(Node headerNode) throws XException
	{ // Easy: no record type identification necessary
		// Jump directly to contained fields.
		return serializeFieldsAndGroups(headerNode, headerSpec);
	} // serializeHeader(Node headerNode)

	/**
	 * <code>serializeRecords</code> serializes the records section.
	 * 
	 * @param recordsNode
	 * @return String
	 */
	private Object serializeRecords(Node recordsNode) throws XException
	{
		String tag = recordsNode.getNodeName();
		if (tag == null || !tag.equals("Records"))
		{
			List params = new Vector();
			params.add("Records expected");
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "122", params);
		} // if (tag == null || !tag.equals("Records"))
		Object result = null;
		if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING)
			result = "";
		else
			result = new ByteArrayList();
		Node recordGroup = recordsNode.getFirstChild();
		while (recordGroup != null)
		{ // Loop over the record groups or just a dummy loop
			// Two possibilities: directly first record or first record group
			Node record = recordGroup;
			if (recordGroup.getNodeName().equals("RecordGroup"))
				// In case of a record group go one level deeper to the first
				// record of the group
				record = recordGroup.getFirstChild();
			while (record != null)
			{ // loop over the records in the group - or all if no record
				// groups are present
				// The record type is identified by the xml tag.
				// Get it and jum to the contained fields.
				if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING)
					result = result
							+ (String) serializeFieldsAndGroups(record,
									getRecordTypeSpec(record.getNodeName()))
							+ Constants.LINE_SEPERATOR;
				else
					((ByteArrayList) result)
							.add((byte[]) serializeFieldsAndGroups(record,
									getRecordTypeSpec(record.getNodeName())));
				// Go to next record
				record = record.getNextSibling();
			} // while (record!=null)
			if (recordGroup.getNodeName().equals("RecordGroup"))
			{ // Go to next record group
				recordGroup = recordGroup.getNextSibling();
			} // if (recordGroup.getNodeName().equals("RecordGroup"))
			else
				// no record groups, simply end the loop
				recordGroup = null;
		} // while (recordGroup!=null)
		return result;
	} // serializeRecords(Node recordsNode)

	/**
	 * <code>serializeTrailer</code> parses the header.
	 * 
	 * @param trailerNode the trailer in the content DOM tree
	 * @return the serialized trailer as {@link java.lang.String string}
	 * @throws XException in case of an unexpected value
	 */
	private Object serializeTrailer(Node trailerNode) throws XException
	{ // Easy: no record type identification necessary
		// Jump directly to contained fields.
		return serializeFieldsAndGroups(trailerNode, trailerSpec);
	} // serializeTrailer(Node trailerNode)

	/**
	 * <code>serializeFieldsAndGroups</code> serializes the fields and group
	 * references within a record type - including header and trailer.
	 * 
	 * @param recordNode the record node in the result DOM tree
	 * @param recordType the record type specification node in the structure
	 *            descrition to get the field specs from
	 * @return the serialized records as {@link java.lang.String string}
	 * @throws XException in case of an unexpected value
	 */
	private Object serializeFieldsAndGroups(Node recordNode, Node recordType)
			throws XException
	{ // To avoid permanent string manipulations, a StringBuffer is used.
		int length = RecordTypeDescriptionChecker.computeRecordLength(
				recordType, groupsSpec);
		// Length for the buffer.
		int pos = 0;
		// Position in the buffer to put the next field.
		Object buffer = null;
		if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING)
			buffer = new StringBuffer(length);
		else
			buffer = new byte[length];

		try
		// for casting to XException
		{
			NodeList fieldSpecs = recordType.getChildNodes();
			// The next available field node in the content DOM tree.
			// array for call by reference
			Node[] fieldNode = new Node[1];
			// the first field
			fieldNode[0] = recordNode.getFirstChild();

			for (int i = 0; i < fieldSpecs.getLength(); i++)
			{ // Loop over all fields and group references in the sturcture
				// description
				Element fieldSpec = (Element) fieldSpecs.item(i);
				if (fieldSpec.getNodeName().equals("Field"))

					// a single field
					pos = serializeField(fieldSpec, buffer, pos, fieldNode);
				// May have moved the actual field node in the content DOM tree

				else if (fieldSpec.getNodeName().equals("Group"))

					// a field group
					pos = serializeGroup(fieldSpec, buffer, pos, fieldNode);
				// May have moved the actual field node in the content DOM tree

			} // for (int i=0; i<fieldSpecs.getLength(); i++)

			// All record fields in the content DOM tree should be serialized.
			if (fieldNode[0] != null)
			{
				List params = new Vector();
				params.add(fieldNode[0].getNodeName());
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "125", params);
			}
		} // try
		catch (ClassCastException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		} // catch
		if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING)
			return new String((StringBuffer) buffer);
		else
			return buffer;
	} // serializeFieldsAndGroups(Node recordNode, Node recordType)

	/**
	 * <code>serializeGroup</code> serializes the fields of a field group
	 * defined in the interface structure description.
	 * 
	 * @param groupRef the group reference in the structure description
	 * @param buffer the buffer to fill with the serialized field contents
	 * @param pos the position in the buffer to start the filling
	 * @param fieldNode the next available field node in the content DOM tree -
	 *            array for call by reference
	 * @return the buffer position to start the next filling at
	 * @throws XException in case of an unexpected value
	 */
	private int serializeGroup(Element groupRef, Object buffer, int pos,
			Node[] fieldNode) throws XException
	{
		try
		// for casting to XException
		{
			NodeList fieldSpecs = RecordTypeDescriptionChecker.getGroupSpec(
					groupRef.getAttribute("Name"), groupsSpec).getChildNodes();
			// fields in the group
			for (int i = 0; i < fieldSpecs.getLength(); i++)
				pos = serializeField((Element) fieldSpecs.item(i), buffer, pos,
						fieldNode);
		} // try
		catch (ClassCastException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		} // catch
		return pos;
	} // serializeGroup(Element groupRef,StringBuffer buffer,int pos,Node[]
	// fieldNode)

	/**
	 * <code>serializeField</code> serializes the fields of a field group
	 * defined in the interface structure description.
	 * 
	 * @param fieldSpec the field specification in the structure description
	 * @param buffer the buffer to fill with the serialized field contents
	 * @param pos the position in the buffer to start the filling
	 * @param fieldNode the next available field node in the content DOM tree -
	 *            array for call by reference
	 * @return the buffer position to start the next filling at
	 * @throws XException in case of an unexpected value
	 */
	private int serializeField(Element fieldSpec, Object buffer, int pos,
			Node[] fieldNode) throws XException
	{
		try
		// for casting to XException
		{
			String fieldFormat = fieldSpec.getAttribute("Format");
			if (fieldFormat == null || fieldFormat.length() == 0)
			{
				List params = new Vector();
				params.add("Format");
				params.add(((Element) fieldSpec.getParentNode())
						.getAttribute("Name"));
				params.add(fieldSpec.toString());
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "82", params);
			}

			// How many characters?
			int length = Integer.parseInt(fieldSpec.getAttribute("Length"));

			if (fieldFormat.equals("blank"))
			{ // only filler - no corresponding node in the content DOM tree
				if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING)
					for (int i = 0; i < length; i++)
						((StringBuffer) buffer).insert(pos + i, ' ');
				else
					fillByteArray((byte[]) buffer, pos, length, "");
			} // then (fieldFormat.equals("blank"))
			else
			{ // Not just filler
				String fieldName = fieldSpec.getAttribute("Name");
				String nodeName = null;
				if (fieldNode[0] != null)
					nodeName = fieldNode[0].getNodeName();
				if (fieldName == null)
				{
					List params = new Vector();
					params.add("Name");
					params.add(((Element) fieldSpec.getParentNode())
							.getAttribute("Name"));
					params.add(fieldSpec.toString());
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "82", params);
				} // if (fieldName == null)

				// The field value may be stored in the content DOM tree or
				// a standard value may be used.
				String value = "";
				// standard for alphanumeric fields
				if (fieldFormat.equals("num"))
					value = "0";
				// standard for numeric fields

				if (fieldName.equals(nodeName))
				{ // The value is given in the content DOM tree.
					value = XDomSupport.getNodeText(fieldNode[0]);
					// Set the next available field in the content DOM tree one
					// further
					fieldNode[0] = fieldNode[0].getNextSibling();
				} // if (fieldName.equals(nodeName))

				// Now a value - read from the content DOM tree or standard - is
				// given.
				if (fieldFormat.equals("const"))
				{
					String constValue = fieldSpec.getAttribute("Value");
					// value specified in the interface description
					byte[] constValueBytes = null;
					if (interfaceContentClass == Constants.IFCONTENTCLASS_BYTEARRAYLIST)
					{
						constValueBytes = byteArrayConverter
								.stringToByteArray(constValue);
					} // if (interfaceContentClass ==
					// Constants.IFCONTENTCLASS_BYTEARRAYLIST)
					if (!constValue.equals(value))
					{ // The interface description must specify a value of
						// correct
						// length.
						// If the content DOM tree has a value, they must be
						// identical (after triming).
						List params = new Vector();
						params.add(((Element) fieldSpec.getParentNode())
								.getAttribute("Name"));
						params.add(fieldSpec.getAttribute("Name"));
						params.add("?");
						throw new XException(Constants.LOCATION_INTERN,
								Constants.LAYER_PROTOCOL,
								Constants.PACKAGE_PROTOCOL_RECORDS, "106",
								params);
					} // if (constValue == null || constValueLength != length
						// ||
					// value.length() > 0 && !constTrimed.equals(value))

					if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING)
						((StringBuffer) buffer).insert(pos, constValue);
					else
						for (int i = 0; i < length; i++)
							((byte[]) buffer)[pos + i] = constValueBytes[i];
				} // then (fieldFormat.equals("const"))
				else if (fieldFormat.equals("alpha"))
				{
					int valueLength = value.length();
					byte[] valueBytes = null;
					if (interfaceContentClass == Constants.IFCONTENTCLASS_BYTEARRAYLIST)
					{
						valueBytes = byteArrayConverter
								.stringToByteArray(value);
						valueLength = valueBytes.length;
					}
					if (length < valueLength)
					{
						value = value.trim();
						valueLength = value.length();
						if (interfaceContentClass == Constants.IFCONTENTCLASS_BYTEARRAYLIST)
						{
							valueBytes = byteArrayConverter
									.stringToByteArray(value);
							valueLength = valueBytes.length;
						}
						if (length < valueLength)
						{
							if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING)
							{
								List params = new Vector();
								params
										.add(((Element) fieldSpec
												.getParentNode())
												.getAttribute("Name"));
								params.add(fieldSpec.getAttribute("Name"));
								params.add(value);
								throw new XException(Constants.LOCATION_INTERN,
										Constants.LAYER_PROTOCOL,
										Constants.PACKAGE_PROTOCOL_RECORDS,
										"131", params);
							} // if (interfaceContentClass ==
							// Constants.IFCONTENTCLASS_STRING)
							else
							{
								valueBytes = byteArrayConverter
										.stringToByteArray(value, length);
								valueLength = valueBytes.length;
							}
						}
					}
					if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING)
					{
						((StringBuffer) buffer).insert(pos, value);
						// Fill in blanks if the value is to short.
						for (int i = 0; i < length - value.length(); i++)
							((StringBuffer) buffer).insert(pos + value.length()
									+ i, ' ');
					}
					else
						fillByteArray((byte[]) buffer, pos, length, value);
				} // then (fieldFormat.equals("alpha"))
				else if (fieldFormat.equals("num"))
				{ // First look for a leading sign
					int start = 0;
					if (value.length() > 0)
					{
						char sign = value.charAt(0);
						if (sign == '+' || sign == '-')
						{
							if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING)
							{
								((StringBuffer) buffer).insert(pos, sign);
								start = 1;
							}
							else
							{
								byte[] signBytes = byteArrayConverter
										.stringToByteArray("" + sign);
								start = signBytes.length;
								for (int i = 0; i < start; i++)
									((byte[]) buffer)[pos + i] = signBytes[i];
							}
							value = value.substring(1).trim();
						} // if (sign=='+' || sign=='-')
					} // if (value.length()>0)

					// Look for a decimal point
					String decimalPoint = fieldSpec
							.getAttribute("DecimalPoint");
					int decPointPos = -1;
					if (decimalPoint != null && decimalPoint.length() > 0)
					{
						char decimalP = '.';
						if (decimalPoint.equals("comma"))
							decimalP = ',';
						else if (!decimalPoint.equals("dot"))
						{
							List params = new Vector();
							params.add(((Element) fieldSpec.getParentNode())
									.getAttribute("Name"));
							params.add(fieldSpec.getAttribute("Name"));
							throw new XException(Constants.LOCATION_INTERN,
									Constants.LAYER_PROTOCOL,
									Constants.PACKAGE_PROTOCOL_RECORDS, "107",
									params);
						} // if (!decimalPoint.equals("dot"))
						decPointPos = value.indexOf(decimalP);
					} // if (decimalPoint!=null && decimalPoint.length()>0)

					// Check digits
					int zeroBegin = -1;
					int zeroEnd = value.length();
					boolean onlyZeros = true;
					if (decPointPos > -1)
					{ // Check digits before decimal point
						for (int i = 0; i < decPointPos; i++)
						{
							if (!Character.isDigit(value.charAt(i)))
							{
								List params = new Vector();
								params
										.add(((Element) fieldSpec
												.getParentNode())
												.getAttribute("Name"));
								params.add(fieldSpec.getAttribute("Name"));
								params.add("?");
								throw new XException(Constants.LOCATION_INTERN,
										Constants.LAYER_PROTOCOL,
										Constants.PACKAGE_PROTOCOL_RECORDS,
										"108", params);
							} // if
							// (!Character.isDigit(valueWithoutSign.charAt(i)))
							if (onlyZeros && value.charAt(i) == '0')
								zeroBegin = i;
							else
								onlyZeros = false;
						} // for (int i=0; i<decPointPos; i++)
						// Check digits behind decimal point
						onlyZeros = true;
						for (int i = value.length() - 1; i > decPointPos; i--)
						{
							if (!Character.isDigit(value.charAt(i)))
							{
								List params = new Vector();
								params
										.add(((Element) fieldSpec
												.getParentNode())
												.getAttribute("Name"));
								params.add(fieldSpec.getAttribute("Name"));
								params.add("?");
								throw new XException(Constants.LOCATION_INTERN,
										Constants.LAYER_PROTOCOL,
										Constants.PACKAGE_PROTOCOL_RECORDS,
										"108", params);
							} // if
							// (!Character.isDigit(valueWithoutSign.charAt(i)))
							if (onlyZeros && value.charAt(i) == '0')
								zeroEnd = i;
							else
								onlyZeros = false;
						} // for (int i=valueWithoutSign.length()-1;
						// i>decPointPos; i--)
					} // then (decPointPos>-1)
					else
						// no decimal point
						for (int i = 0; i < value.length(); i++)
						{
							if (!Character.isDigit(value.charAt(i)))
							{
								List params = new Vector();
								params
										.add(((Element) fieldSpec
												.getParentNode())
												.getAttribute("Name"));
								params.add(fieldSpec.getAttribute("Name"));
								params.add("?");
								params.add(value);
								throw new XException(Constants.LOCATION_INTERN,
										Constants.LAYER_PROTOCOL,
										Constants.PACKAGE_PROTOCOL_RECORDS,
										"108", params);
							} // if
							// (!Character.isDigit(valueWithoutSign.charAt(i)))
							if (onlyZeros && value.charAt(i) == '0')
								zeroBegin = i;
							else
								onlyZeros = false;
						} // for (int i=0; i<valueWithoutSign.length(); i++)

					String valueWithoutSignZeros = value.substring(
							zeroBegin + 1, zeroEnd);
					int valueWithoutSignZerosLength = valueWithoutSignZeros
							.length();
					byte[] valueWithoutSignZerosBytes = null;
					if (interfaceContentClass == Constants.IFCONTENTCLASS_BYTEARRAYLIST)
					{
						valueWithoutSignZerosBytes = byteArrayConverter
								.stringToByteArray(value);
						valueWithoutSignZerosLength = valueWithoutSignZerosBytes.length;
						if (valueWithoutSignZerosLength > length - start)
						{ // Too long - even if zeros are deleted.
							List params = new Vector();
							params.add(((Element) fieldSpec.getParentNode())
									.getAttribute("Name"));
							params.add(fieldSpec.getAttribute("Name"));
							params.add(value);
							throw new XException(Constants.LOCATION_INTERN,
									Constants.LAYER_PROTOCOL,
									Constants.PACKAGE_PROTOCOL_RECORDS, "131",
									params);
						} // if (valueWithoutSignZerosLength > length - start)
						byte[] zero = byteArrayConverter.stringToByteArray("0");
						if (decPointPos > -1)
						{
							int offset = pos + start;
							for (int i = 0; i < valueWithoutSignZerosLength; i++)
								((byte[]) buffer)[offset + i] = valueWithoutSignZerosBytes[i];
							offset += valueWithoutSignZerosLength;
							for (int i = 0; i < (length - start - valueWithoutSignZerosLength)
									/ zero.length; i++)
							{
								for (int j = 0; j < zero.length; j++)
									((byte[]) buffer)[offset + j] = zero[j];
								offset += zero.length;
							}
							for (int i = offset; i < length; i++)
								((byte[]) buffer)[i] = 0;
						}
						else
						{
							int offset = pos + start;
							for (int i = 0; i < (length - start - valueWithoutSignZerosLength)
									/ zero.length; i++)
							{
								for (int j = 0; j < zero.length; j++)
									((byte[]) buffer)[offset + j] = zero[j];
								offset += zero.length;
							}
							for (int i = 0; i < valueWithoutSignZerosLength; i++)
								((byte[]) buffer)[offset + i] = valueWithoutSignZerosBytes[i];
							offset += valueWithoutSignZerosLength;
							for (int i = offset; i < length; i++)
								((byte[]) buffer)[i] = 0;
						}
					}
					else
					{
						if (zeroEnd - zeroBegin - 1 > length - start)
						{
							List params = new Vector();
							params.add(((Element) fieldSpec.getParentNode())
									.getAttribute("Name"));
							params.add(fieldSpec.getAttribute("Name"));
							params.add(value);
							throw new XException(Constants.LOCATION_INTERN,
									Constants.LAYER_PROTOCOL,
									Constants.PACKAGE_PROTOCOL_RECORDS, "131",
									params);
						} // if (zeroEnd - zeroBegin - 1 > length - start)
						if (length - start + zeroBegin + 1 < value.length())
							// Delete zeros at the end after the decimal point
							// but only to that extend that the necessary length
							// is achieved by delelting leading zeros.
							value = value.substring(0, length - start
									+ zeroBegin + 1);
						if (zeroBegin > -1)
							// Delete zeros at beginning
							value = value.substring(zeroBegin + 1);
						// Fill in zeros at beginning if necessary
						for (int i = 0; i < length - value.length() - start; i++)
							((StringBuffer) buffer)
									.insert(pos + start + i, '0');

						// Put value into buffer - sign is already there.
						((StringBuffer) buffer).insert(pos + length
								- value.length(), value);
					}
				} // (fieldFormat.equals("num"))
				else if (fieldFormat.equals("date"))
				{
					if (value.length() == 0)
						// Blanks allowed.
						if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING)
							for (int i = 0; i < length; i++)
								((StringBuffer) buffer).insert(pos + i, ' ');
						else
							fillByteArray((byte[]) buffer, pos, length, "");
					else
					{
						int valueLength = value.length();
						byte[] valueBytes = null;
						if (interfaceContentClass == Constants.IFCONTENTCLASS_BYTEARRAYLIST)
						{
							valueBytes = byteArrayConverter
									.stringToByteArray(value);
							valueLength = valueBytes.length;
						}
						if (length != valueLength)
						{ // Otherwise length must be correct.
							List params = new Vector();
							params.add(((Element) fieldSpec.getParentNode())
									.getAttribute("Name"));
							params.add(fieldSpec.getAttribute("Name"));
							params.add(value);
							throw new XException(Constants.LOCATION_INTERN,
									Constants.LAYER_PROTOCOL,
									Constants.PACKAGE_PROTOCOL_RECORDS, "138",
									params);
						}
						if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING)
							((StringBuffer) buffer).insert(pos, value);
						else
							for (int i = 0; i < length; i++)
								((byte[]) buffer)[pos + i] = valueBytes[i];
					}
				} // then (fieldFormat.equals("date"))
				else
				{
					List params = new Vector();
					params.add(((Element) fieldSpec.getParentNode())
							.getAttribute("Name"));
					params.add(fieldFormat);
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "139", params);
				} // else (fieldFormat.equals("date"))
			} // else (fieldFormat.equals("blank"))

			return pos + length;
		} // try
		catch (NumberFormatException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		} // catch
	} // serializeField(Element fieldSpec,StringBuffer buffer,int pos,Node[]
	// fieldNode)

	/**
	 * Method fillByteArray.
	 * 
	 * @param b
	 * @param pos
	 * @param length
	 * @param constValue
	 */
	private void fillByteArray(byte[] arr, int pos, int length, String value)
			throws XException
	{
		byte[] fill = byteArrayConverter.stringToByteArray(value, length);
		for (int i = 0; i < length; i++)
			arr[pos + i] = fill[i];
	}

	/**
	 * Serializes one record.
	 * 
	 * @param doc
	 * @return a byte array containing one record
	 */
	public byte[] serializeSingleRecord(Document doc) throws XException
	{
		Element record = doc.getDocumentElement();
		return (byte[]) serializeFieldsAndGroups(record,
				getRecordTypeSpec(record.getNodeName()));
	}

} // RecordTypeSerializer
