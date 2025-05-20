package net.sf.xbus.protocol.records;

import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import net.sf.xbus.base.bytearraylist.ByteArrayConverter;
import net.sf.xbus.base.bytearraylist.ByteArrayConverterFactory;
import net.sf.xbus.base.bytearraylist.ByteArrayList;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.bytearrays.XByteArraySupport;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.strings.CardinalityStrings;
import net.sf.xbus.base.xml.XDomSupport;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The class <code>RecordTypeParser</code> implements parsing records from a
 * string or a list of byte arrays.
 * <p>
 * The parsed structure is described by a xml file - in particular the contained
 * record types, their order within the string and how to identify them during
 * parsing. Such describing files are of document type
 * <code>InterfaceSpec</code> declared in "InterfaceSpec.dtd".
 * </p>
 * <p>
 * The parsing result is a DOM tree with the following structure:
 * </p>
 * <p>
 * &lt;+ <i>InterfaceName </i>+&gt; <dir>&lt;+ <i>HeaderName </i>+&gt;
 * <dir>&lt;+ <i>FieldName </i>+&gt;+ <b>FieldValue </b>+&lt;/+ <i>FieldName
 * </i>+&gt; <br>
 * ... </dir> &lt;/+ <i>HeaderName </i>+&gt; <br>
 * &lt;Records&gt; <dir>&lt;RecordGroup&gt; <dir>&lt;+ <i>RecordType </i>+&gt;
 * <dir>&lt;+ <i>FieldName </i>+&gt;+ <b>FieldValue </b>+&lt;/+ <i>FieldName
 * </i>+&gt; <br>
 * ... </dir> &lt;/+ <i>RecordType </i>+&gt; <br>
 * ... </dir> &lt;/RecordGroup&gt; <br>
 * ... </dir> &lt;/Records&gt; <br>
 * &lt;+ <i>TrailerName </i>+&gt; <dir>&lt;+ <i>FieldName </i>+&gt;+
 * <b>FieldValue </b>+&lt;/+ <i>FieldName </i>+&gt; <br>
 * ... </dir> &lt;/+ <i>TrailerName </i>+&gt; </dir> &lt;/+ <i>InterfaceName
 * </i>+&gt;
 * </p>
 * <p>
 * Header and trailer are optional. <br>
 * The &lt;RecordGroup&gt; structure is only used if records of different types
 * are grouped together in the string (RecordOrder=Structured for the
 * &lt;Lines&gt; specification in the structure description). Otherwise records
 * are listed immediately below the &lt;Records&gt; level.
 * </p>
 * <p>
 * There is no DTD for the parsing result since the used tags depend on the
 * document structure of the parsed file (tags indicated by +'s above). But it
 * can be derived from the structure description taking the name attributes for
 * interface type, header, record types, fields and trailer as tags.
 * </p>
 * <p>
 * The "ordinary" working mode is parsing records from a string. But due to
 * special problems with some character encoding and line breaks within record
 * fields, byte-array-oriented parsing is available, too. In this case each
 * record is stored in its own byte array.
 * </p>
 * 
 * @author Stephan Düwel
 */
public class RecordTypeParser extends RecordTypeManipulator
{

	// //////////////////////
	// Static fields
	// //////////////////////

	/**
	 * <code>instances</code> stores the instances by thread, the interface
	 * name and the representation type (<code>String</code> or
	 * {@link net.sf.xbus.base.bytearraylist.ByteArrayList}).
	 */
	private static Hashtable instances = new Hashtable();
	private static final Object classLock = RecordTypeParser.class;

	// //////////////////////
	// Member fields
	// //////////////////////

	/**
	 * Flag if record lines are cut off after the last non-whitespace character.
	 */
	private boolean lineCutting = false;

	// //////////////////////
	// Constructors
	// //////////////////////

	/**
	 * Just the standard constructor.
	 */
	private RecordTypeParser() throws XException
	{
		super();
	} // RecordTypeParser()

	// //////////////////////
	// Static methods
	// //////////////////////

	/**
	 * <code>getInstance()</code> retrieves the an instance of
	 * <code>RecordTypeParser</code>.
	 * 
	 * @param sourceType the name for the parsed structure as it is derived from
	 *            a
	 *            {@link net.sf.xbus.protocol.records.RecordTypeMessage RecordTypeMessage}
	 * @param interfaceContentClass Working mode: sting or byte array list
	 * @throws XException in case of missing <code>sourceType</code>
	 */
	public static RecordTypeParser getInstance(String sourceType,
			int interfaceContentClass) throws XException
	{
		synchronized (classLock)
		{
			if (sourceType == null || sourceType.length() == 0)
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "98");

			RecordTypeParser instance = (RecordTypeParser) instances.get(Thread
					.currentThread().getName()
					+ sourceType + interfaceContentClass);
			if (instance == null)
			{ // no suitable instance available yet
				instance = new RecordTypeParser();
				instances.put(Thread.currentThread().getName() + sourceType
						+ interfaceContentClass, instance);
			} // if (instance==null)
			return instance;
		} // synchronized (classLock)
	} // getInstance(String sourceType, int interfaceContentClass)

	/**
	 * <code>getByteArrayConverter</code> retrieves a byte array converter
	 * object which is convenient for the interface from which the records are
	 * to parse.
	 * 
	 * @return an appropriate converter object
	 */
	protected ByteArrayConverter getByteArrayConverter() throws XException
	{
		// Get the byte the byte array converter for the interface specified
		// in the underlying message.
		return ByteArrayConverterFactory.getConverter(sourceType);
	} // getByteArrayConverter()

	// /////////////////////////////////////////
	// Parsing the contents
	// /////////////////////////////////////////

	/**
	 * <code>parse</code> parses a string due to the record type definition.
	 * 
	 * @param interfaceContent the string to parse
	 * @param resultDoc The parsing result - an empty DOM tree which will be
	 *            filled.
	 * @throws XException in case that the interface structure description is
	 *             not loaded or in case of an unexpected value or end of
	 *             string/byte array/byte array list
	 */
	public void parse(Object interfaceContent, Document resultDoc)
			throws XException
	{
		if (interfaceStructure == null)
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "99");

		// May ending white spaces in the record lines be ommitted?
		lineCutting = Configuration.getInstance().getValueAsBooleanOptional(
				"System", sourceType, "LineCutting");

		// The root element has the content type as xml tag.
		Element root = resultDoc.createElement(contentType);
		resultDoc.appendChild(root);

		// Parsing position
		int stringPos = 0;
		// Position in string or in the complete byte array list (byte count)

		int line[] =
		{0};
		// Index of list element in a byte array list, array for call by
		// reference

		try
		// for casting to XException
		{
			// Determine the length of content to parse ...
			if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING)
				// ... as number of characters for a parsed string
				interfaceContentLength = ((String) interfaceContent).length();
			else if (interfaceContentClass == Constants.IFCONTENTCLASS_BYTEARRAYLIST)
				// ... as number of bytes in case of a parsed byte array list
				interfaceContentLength = ((ByteArrayList) interfaceContent)
						.length();
			else
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "100");
		} // try
		catch (ClassCastException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		} // catch (ClassCastException e)

		Element[] headerNode =
		{null};
		// for building up the header node (array for call by reference)
		if (headerSpec != null)
			stringPos = parseHeader(interfaceContent, stringPos, line,
					resultDoc, headerNode);
		if (headerNode.length == 1 && headerNode[0] != null
				&& headerNode[0].hasChildNodes())
			// header successfully parsed
			root.appendChild(headerNode[0]);

		Element[] recordsNode =
		{null};
		// for building up the records section (array for call by reference)
		stringPos = parseRecords(interfaceContent, stringPos, line, resultDoc,
				recordsNode);
		if (recordsNode.length == 1 && recordsNode[0] != null
				&& recordsNode[0].hasChildNodes())
			// records successfully parsed
			root.appendChild(recordsNode[0]);

		Element[] trailerNode =
		{null};
		// For building up the trailer node (array for call by reference)
		if (trailerSpec != null)
			stringPos = parseTrailer(interfaceContent, stringPos, line,
					resultDoc, trailerNode);
		if (trailerNode.length == 1 && trailerNode[0] != null
				&& trailerNode[0].hasChildNodes())
			// trailer successfully parsed
			root.appendChild(trailerNode[0]);
	} // parse(String interfaceContent, Document resultDoc)

	/**
	 * <code>parseHeader</code> parses the header.
	 * 
	 * @param interfaceContent the (whole) string to parse
	 * @param stringPos the position to start parsing from in the string or in
	 *            the complete byte array list (byte count)
	 * @param line index of the byte array to parse, array for call by
	 *            reference, because it is augmented, ignored when parsing a
	 *            string
	 * @param doc the total result document - necessary for creating nodes
	 * @param headerNode the resulting header node (array to have call by
	 *            reference)
	 * @return the string or byte array position to start the next part's
	 *         parsing from
	 * @throws XException in case of an unexpected value or end of string
	 */
	private int parseHeader(Object interfaceContent, int stringPos, int[] line,
			Document doc, Element[] headerNode) throws XException
	{
		if (stringPos < interfaceContentLength)
		{ // There is some content to parse
			headerNode[0] = doc.createElement(headerSpec.getAttribute("Name"));
			// The header owns its specific name to use as tag.

			// The contained fields and groups
			stringPos = parseFieldsAndGroups(interfaceContent, stringPos, line,
					doc, headerNode[0], headerSpec);

			// Jump over line break if necessary
			if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING
					&& ((String) interfaceContent).startsWith(
							Constants.LINE_SEPERATOR, stringPos))
				stringPos += Constants.LINE_SEPERATOR.length();
			// Next line (Value is always incremented because in case of string
			// parsing it is ignoed anyway.)
			line[0]++;
		} // if (stringPos<interfaceContent.length())
		return stringPos;
	} // parseHeader(Object interfaceContent, int stringPos, int[] line,
	// Document doc, Element[] headerNode)

	/**
	 * <code>parseRecords</code> parses the record types.
	 * 
	 * @param interfaceContent the (whole) string to parse
	 * @param stringPos the position to start parsing from in the string or in
	 *            the complete byte array list (byte count)
	 * @param line index of the byte array to parse, array for call by
	 *            reference, because it is augmented, ignored when parsing a
	 *            string
	 * @param doc the total result document - necessary for creating nodes
	 * @param recordsNode the resulting Record Types node (array to have call by
	 *            reference)
	 * @return the string or byte array position to start the next part's
	 *         parsing from
	 * @throws XException in case of an unexpected value or end of string
	 */
	private int parseRecords(Object interfaceContent, int stringPos,
			int[] line, Document doc, Element[] recordsNode) throws XException
	{
		// Determine end of record parsing
		int recordsSectionEnd = interfaceContentLength;

		if (trailerSpec != null)
			// If there is a trailer, record parsing must already end before the
			// string end.
			recordsSectionEnd -= RecordTypeDescriptionChecker
					.computeRecordLength(trailerSpec, groupsSpec);

		if (stringPos < recordsSectionEnd)
			// something to parse left
			recordsNode[0] = doc.createElement("Records");

		Element insertionNode = recordsNode[0];
		// node to insert the records below - <Records> or <RecordType>
		Element recordType = null;
		int recordCount = 0;
		// counter for subsequent records of the same type
		Element nextRecord = null;
		// To create record nodes.
		// The last created is used to determine the next record type.
		while (stringPos < recordsSectionEnd)
		{ // loop over content string
			// Determining the next record type really drives the loop.
			Element nextRecordType = determineNextRecordType(interfaceContent,
					stringPos, line, recordType, recordCount, nextRecord);

			if (recordType == null
					|| !XDomSupport.getTrimedNodeText(nextRecordType).equals(
							XDomSupport.getTrimedNodeText(recordType)))
			{ // First record type or record type change.
				recordCount = 0;
				recordType = nextRecordType;
			} // if (recordType==null ||
			// !XDomSupport.getTrimedElementValue(nextRecordType).equals(XDomSupport.getTrimedElementValue(recordType)))

			if (recOrder.equals("Structured")
					&& getRecordTypePositionInLines(nextRecordType) == 0)
			{ // first record in a group
				insertionNode = doc.createElement("RecordGroup");
				recordsNode[0].appendChild(insertionNode);
			} // if (recOrder.equals("Structured") && nextRecordTypePos==0)

			String nextRecordTypeName = XDomSupport
					.getTrimedNodeText(nextRecordType);
			nextRecord = doc.createElement(nextRecordTypeName);
			// the record node

			// Search for the record type specification.
			List nextRecordTypeSpecCandidates = XDomSupport
					.getChildNodesByAttrValue(recordTypesSpec, "Name",
							nextRecordTypeName, "RecordTypeSpec");
			if (nextRecordTypeSpecCandidates.size() != 1)
			{
				List params = new Vector();
				params.add(nextRecordTypeName);
				params.add(new Integer(line[0] + 1));
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "102", params);
			} // if (nextRecordTypeSpecCandidates.size() != 1)

			// Parse the record content: the fields
			stringPos = parseFieldsAndGroups(interfaceContent, stringPos, line,
					doc, nextRecord, (Node) nextRecordTypeSpecCandidates.get(0));
			if (nextRecord.hasChildNodes())
			{ // some content found
				insertionNode.appendChild(nextRecord);
				// insert record into tree
				recordCount++;
			} // if (nextRecord.hasChildNodes())

			// Jump over line break if necessary
			if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING
					&& ((String) interfaceContent).startsWith(
							Constants.LINE_SEPERATOR, stringPos))
				stringPos += Constants.LINE_SEPERATOR.length();
			// Next line (Value is always incremented because in case of string
			// parsing it is ignored anyway.)
			line[0]++;
		} // while (stringPos<recordsSectionEnd)
		return stringPos;
	} // parseRecords(Object interfaceContent, int stringPos, int[] line,
	// Document doc, Element[] recordsNode)

	/**
	 * <code>parseTrailer</code> parses the trailer.
	 * 
	 * @param interfaceContent the (whole) string to parse
	 * @param stringPos the position to start parsing from in the string or in
	 *            the complete byte array list (byte count)
	 * @param line index of the byte array to parse, array for call by
	 *            reference, because it is augmented, ignored when parsing a
	 *            string
	 * @param doc the total result document - necessary for creating nodes
	 * @param trailerNode the resulting header node (array to have call by
	 *            reference)
	 * @return the string or byte array position to start the next part's
	 *         parsing from
	 * @throws XException in case of an unexpected value or end of string
	 */
	private int parseTrailer(Object interfaceContent, int stringPos,
			int[] line, Document doc, Element[] trailerNode) throws XException
	{
		if (stringPos < interfaceContentLength)
		{ // There is some content to parse
			trailerNode[0] = doc
					.createElement(trailerSpec.getAttribute("Name"));
			// The trailer has its specific name to use as tag.

			// The contained fields and groups
			stringPos = parseFieldsAndGroups(interfaceContent, stringPos, line,
					doc, trailerNode[0], trailerSpec);

			// Jump over line break if necessary
			if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING
					&& ((String) interfaceContent).startsWith(
							Constants.LINE_SEPERATOR, stringPos))
				stringPos += Constants.LINE_SEPERATOR.length();
			// Next line (Value is always incremented because in case of string
			// parsing it is ignored anyway.)
			line[0]++;
		} // if (stringPos<interfaceContent.length())
		return stringPos;
	} // parseTrailer(Object interfaceContent, int stringPos, int[] line,
	// Document doc, Element[] trailerNode)

	/**
	 * <code>parseFieldsAndGroups</code> parses the fields and group
	 * references within a record type - including header and trailer.
	 * 
	 * @param interfaceContent the (whole) string to parse
	 * @param stringPos the position to start parsing from in the string or in
	 *            the complete byte array list (byte count)
	 * @param line index of the byte array to parse, array for call by
	 *            reference, because it is augmented, ignored when parsing a
	 *            string
	 * @param doc the total result document - necessary for creating nodes
	 * @param parent the record node in the result DOM tree to attach the field
	 *            nodes to
	 * @param parentSpec the record type node in the structure descrition to get
	 *            the field specs from
	 * @return the string or byte array position to start the next part's
	 *         parsing from
	 * @throws XException in case of an unexpected value or end of string
	 */
	private int parseFieldsAndGroups(Object interfaceContent, int stringPos,
			int[] line, Document doc, Node parent, Node parentSpec)
			throws XException
	{
		if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING)
			stringPos = parseFieldsAndGroupsSingleByteArray(interfaceContent,
					stringPos, line, doc, parent, parentSpec);
		else
			stringPos = parseFieldsAndGroupsSingleByteArray(
					((ByteArrayList) interfaceContent).get(line[0]), stringPos,
					line, doc, parent, parentSpec);
		return stringPos;
	} // parseFieldsAndGroups(Object interfaceContent, int stringPos, int[]
	// line, Document doc, Node parent, Node parentSpec)

	/**
	 * @param interfaceContent
	 * @param stringPos
	 * @param line
	 * @param doc
	 * @param lastRecord
	 * @param parentSpec
	 * @return
	 */
	private int parseFieldsAndGroupsSingleByteArray(Object interfaceContent,
			int stringPos, int[] line, Document doc, Node parent,
			Node parentSpec) throws XException
	{
		int lineEnd = 0;

		// Calculate line length
		if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING)
		{
			lineEnd = ((String) interfaceContent).indexOf(
					Constants.LINE_SEPERATOR, stringPos);
			if (lineEnd == -1)
				lineEnd = interfaceContentLength;
		} // then (interfaceContentClass == Constants.IFCONTENTCLASS_STRING)
		else
			lineEnd = stringPos + ((byte[]) interfaceContent).length;

		NodeList fieldSpecs = parentSpec.getChildNodes();
		int linePos[] =
		{0};
		// position in the actual byte array, ignored during string parsing
		for (int i = 0; i < fieldSpecs.getLength(); i++)
		{ // Loop over all fields and group references
			Element fieldSpec = (Element) fieldSpecs.item(i);
			LinkedList fields = new LinkedList();
			// For the result of field parsing (multiple fields in case of a
			// group)
			if (fieldSpec.getNodeName().equals("Field"))

				// a single field
				stringPos = parseField(interfaceContent, stringPos, line,
						lineEnd, linePos, doc, fields, fieldSpec);

			else if (fieldSpec.getNodeName().equals("Group"))

				// a field group
				stringPos = parseGroup(interfaceContent, stringPos, line,
						lineEnd, linePos, doc, fields, fieldSpec);

			// Now append the result to the record node.
			for (int j = 0; j < fields.size(); j++)
				parent.appendChild((Node) fields.get(j));
		} // for (int i=0; i<fieldSpecs.getLength(); i++)
		return stringPos;
	}

	/**
	 * <code>parseGroup</code> parses a group when a group reference was
	 * found.
	 * 
	 * @param interfaceContent the (whole) string to parse
	 * @param stringPos the position to start parsing from in the string or in
	 *            the complete byte array list (byte count)
	 * @param line index of the byte array to parse, array for call by
	 *            reference, because it is augmented, ignored when parsing a
	 *            string
	 * @param lineEnd the ending position of the current line
	 * @param linePos position in the actual byte array, ignored when parsing a
	 *            string
	 * @param doc the total result document - necessary for creating nodes
	 * @param fields the parsing result - similar to
	 *            {@link #parseField(Object, int, int[], int, int[], Document, LinkedList, Element) parseField})
	 * @param groupRef the found group reference
	 * @return the string or byte array position to start the next part's
	 *         parsing from
	 * @throws XException in case of an unexpected value or end of string
	 */
	private int parseGroup(Object interfaceContent, int stringPos, int[] line,
			int lineEnd, int[] linePos, Document doc, LinkedList fields,
			Element groupRef) throws XException
	{
		try
		// for casting to XException
		{ // Get the specification of included fields
			NodeList fieldSpecs = RecordTypeDescriptionChecker.getGroupSpec(
					groupRef.getAttribute("Name"), groupsSpec).getChildNodes();
			// fields in the group
			for (int i = 0; i < fieldSpecs.getLength(); i++)
			{ // Loop over all group fields
				Element fieldSpec = (Element) fieldSpecs.item(i);
				LinkedList field = new LinkedList();
				// For the result of field parsing (list for call by reference)

				// the single field
				stringPos = parseField(interfaceContent, stringPos, line,
						lineEnd, linePos, doc, field, fieldSpec);

				if (field.size() == 1)
					fields.add(field.get(0));
			} // for (int i=0; i<fieldSpecs.getLength(); i++)
		} // try
		catch (ClassCastException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		} // catch
		return stringPos;
	} // parseGroup(Object interfaceContent, int stringPos, int[] line, int
	// lineEnd, int[] linePos, Document doc, LinkedList fields, Element
	// groupRef)

	/**
	 * <code>parseField</code> parses a single field.
	 * 
	 * @param interfaceContent the (whole) string to parse
	 * @param stringPos the position to start parsing from in the string or in
	 *            the complete byte array list (byte count)
	 * @param line index of the byte array to parse, array for call by
	 *            reference, because it is augmented, ignored when parsing a
	 *            string
	 * @param lineEnd the ending position of the current line
	 * @param linePos position in the actual byte array, ignored when parsing a
	 *            string
	 * @param doc the total result document - necessary for creating nodes
	 * @param fields the parsing result (list for call by reference - similar to
	 *            {@link #parseGroup(Object, int, int[], int, int[], Document, LinkedList, Element) parseGroup})
	 * @param fieldSpec the field specification as DOM node
	 * @return the string or byte array position to start the next part's
	 *         parsing from
	 * @throws XException in case of an unexpected value or end of string
	 */
	private int parseField(Object interfaceContent, int stringPos, int[] line,
			int lineEnd, int[] linePos, Document doc, LinkedList fields,
			Element fieldSpec) throws XException
	{
		try
		// for casting to XException
		{
			int length = Integer.parseInt(fieldSpec.getAttribute("Length"));
			// How many characters?
			if (stringPos + length > lineEnd)
			{
				if (lineCutting)
					length = lineEnd - stringPos;
				else
				{
					List params = new Vector();
					params.add(((Element) fieldSpec.getParentNode())
							.getAttribute("Name"));
					params.add(fieldSpec.getAttribute("Name"));
					params.add(new Integer(line[0] + 1));
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "104", params);
				} // else (lineCutting)
			} // if (stringPos + length > lineEnd)

			// Extract the value ...
			String value = null;
			if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING)
				// ... from string
				value = ((String) interfaceContent).substring(stringPos,
						stringPos + length);
			else
				// ... from byte array
				value = extractSubString((byte[]) interfaceContent, linePos[0],
						length);

			if (fieldSpec.getAttribute("Format").equals("blank"))
			{ // A filler may only contain blanks.
				boolean onlyBlanks = true;
				for (int i = 0; i < value.length() && onlyBlanks; i++)
					onlyBlanks = (value.charAt(i) == ' ');
				if (!onlyBlanks)
				{
					List params = new Vector();
					params.add(((Element) fieldSpec.getParentNode())
							.getAttribute("Name"));
					params.add(fieldSpec.getAttribute("Name"));
					params.add(new Integer(line[0] + 1));
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "105", params);
				} // if (!onlyBlanks)
			} // then (fieldSpec.getAttribute("Format").equals("blank"))
			else
			{ // not just a blank filler
				if (fieldSpec.getAttribute("Format").equals("const")
						&& !value.equals(fieldSpec.getAttribute("Value")))
				{ // constant fields must have the specified value
					List params = new Vector();
					params.add(((Element) fieldSpec.getParentNode())
							.getAttribute("Name"));
					params.add(fieldSpec.getAttribute("Name"));
					params.add(new Integer(line[0] + 1));
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "106", params);
				} // then (fieldSpec.getAttribute("Format").equals("const") &&
				// !value.equals(fieldSpec.getAttribute("Value")))
				else if (fieldSpec.getAttribute("Format").equals("num"))
				{ // Numeric fields must contain a number
					// First character may be '+' or '-'
					value = value.trim();
					// blanks permitted
					if (value.length() > 0)
					{ // First look for a leading sign
						String valueWithoutSign = value;
						String sign = "";
						if (value.charAt(0) == '+' || value.charAt(0) == '-')
						{
							valueWithoutSign = value.substring(1);
							sign = value.substring(0, 1).trim();
						} // if (value.charAt(0)=='+' || value.charAt(0)=='-')

						// Is an decimal point allowed, if yes which one?
						String decimalPoint = fieldSpec
								.getAttribute("DecimalPoint");
						int decPointPos = -1;
						char decimalP = '.';
						if (decimalPoint != null && decimalPoint.length() > 0)
						{ // decimal point expected
							// which character - '.' or ','?
							if (decimalPoint.equals("comma"))
								decimalP = ',';
							else if (!decimalPoint.equals("dot"))
							{
								List params = new Vector();
								params
										.add(((Element) fieldSpec
												.getParentNode())
												.getAttribute("Name"));
								params.add(fieldSpec.getAttribute("Name"));
								throw new XException(Constants.LOCATION_INTERN,
										Constants.LAYER_PROTOCOL,
										Constants.PACKAGE_PROTOCOL_RECORDS,
										"107", params);
							} // if (!decimalPoint.equals("dot"))
							// decimal point position
							decPointPos = valueWithoutSign.indexOf(decimalP);
						} // if (decimalPoint!=null &&
							// decimalPoint.length()>0)

						// Check digits
						int zeroBegin = -1;
						int zeroEnd = valueWithoutSign.length();
						boolean onlyZeros = true;
						if (decPointPos > -1)
						{ // Check digits before decimal point
							for (int i = 0; i < decPointPos; i++)
							{
								if (!Character.isDigit(valueWithoutSign
										.charAt(i)))
								{
									List params = new Vector();
									params.add(((Element) fieldSpec
											.getParentNode())
											.getAttribute("Name"));
									params.add(fieldSpec.getAttribute("Name"));
									params.add(new Integer(line[0] + 1));
									params.add(value);
									throw new XException(
											Constants.LOCATION_INTERN,
											Constants.LAYER_PROTOCOL,
											Constants.PACKAGE_PROTOCOL_RECORDS,
											"108", params);
								} // if
								// (!Character.isDigit(valueWithoutSign.charAt(i)))
								if (onlyZeros
										&& valueWithoutSign.charAt(i) == '0')
									zeroBegin = i;
								else
									onlyZeros = false;
							} // for (int i=0; i<decPointPos; i++)

							// Check digits behind decimal point
							onlyZeros = true;
							for (int i = valueWithoutSign.length() - 1; i > decPointPos; i--)
							{
								if (!Character.isDigit(valueWithoutSign
										.charAt(i)))
								{
									List params = new Vector();
									params.add(((Element) fieldSpec
											.getParentNode())
											.getAttribute("Name"));
									params.add(fieldSpec.getAttribute("Name"));
									params.add(new Integer(line[0] + 1));
									throw new XException(
											Constants.LOCATION_INTERN,
											Constants.LAYER_PROTOCOL,
											Constants.PACKAGE_PROTOCOL_RECORDS,
											"108", params);
								} // if
								// (!Character.isDigit(valueWithoutSign.charAt(i)))
								if (onlyZeros
										&& valueWithoutSign.charAt(i) == '0')
									zeroEnd = i;
								else
									onlyZeros = false;
							} // for (int i=valueWithoutSign.length()-1;
							// i>decPointPos; i--)
						} // then (decPointPos>-1)

						else
							// no decimal point
							for (int i = 0; i < valueWithoutSign.length(); i++)
							{
								if (!Character.isDigit(valueWithoutSign
										.charAt(i)))
								{
									List params = new Vector();
									params.add(((Element) fieldSpec
											.getParentNode())
											.getAttribute("Name"));
									params.add(fieldSpec.getAttribute("Name"));
									params.add(new Integer(line[0] + 1));
									throw new XException(
											Constants.LOCATION_INTERN,
											Constants.LAYER_PROTOCOL,
											Constants.PACKAGE_PROTOCOL_RECORDS,
											"108", params);
								} // if
								// (!Character.isDigit(valueWithoutSign.charAt(i)))
								if (onlyZeros
										&& valueWithoutSign.charAt(i) == '0')
									zeroBegin = i;
								else
									onlyZeros = false;
							} // for (int i=0; i<valueWithoutSign.length();
								// i++)

						if (zeroEnd < valueWithoutSign.length())
							valueWithoutSign = valueWithoutSign.substring(0,
									zeroEnd);
						if (zeroBegin > -1)
							valueWithoutSign = valueWithoutSign
									.substring(zeroBegin + 1);
						if (valueWithoutSign.length() == 0
								|| valueWithoutSign.length() == 1
								&& valueWithoutSign.charAt(0) == decimalP)
							value = "0";
						else
							value = sign + valueWithoutSign;
					} // if (value.length()>0)
				} // then (fieldSpec.getAttribute("Format").equals("num"))
				else if (fieldSpec.getAttribute("Format").equals("date"))
				{
					value = value.trim();
					if (value.length() > 0)
					{ // a date given
						try
						{
							SimpleDateFormat sdf = new SimpleDateFormat(
									fieldSpec.getAttribute("DateFormat"));
							sdf.parse(value);
						} // try
						catch (Exception e)
						{
							throw new XException(Constants.LOCATION_INTERN,
									Constants.LAYER_PROTOCOL,
									Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
						} // catch
					} // if (value.trim().length()>0)
				} // if (fieldSpec.getAttribute("Format").equals("date"))

				// Everthing ok, thus create a node.
				Element field = doc.createElement(fieldSpec
						.getAttribute("Name"));
				field.appendChild(doc.createTextNode(value));
				fields.add(field);
			} // else (fieldSpec.getAttribute("Format").equals("blank"))

			// Update position in actual byte array
			linePos[0] += length;

			// New global position
			return stringPos + length;
		} // try
		catch (NumberFormatException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		} // catch (NumberFormatException e)
	} // parseField(Object interfaceContent, int stringPos, int[] line, int
	// lineEnd, int[] linePos, Document doc, LinkedList fields, Element
	// fieldSpec)

	/**
	 * <code>determineNextRecordType</code> computes the nexet record type due
	 * to the actual position in the parsed string, the interface structure
	 * description, the last record type and the last record.
	 * 
	 * @param interfaceContent interfaceContent the (whole) string to parse
	 * @param stringPos the position to start parsing the next record from in
	 *            the string or in the complete byte array list (byte count)
	 * @param line index of the next byte array to parse, array for call by
	 *            reference, because it is augmented, ignored when parsing a
	 *            string
	 * @param recordType the last record type as its declaration node in the
	 *            interface structure description -<code>null</code> if no
	 *            record was parsed before
	 * @param recordCount the number of subsequent records of the last type
	 * @param lastRecord the last parsed record as node in the result DOM tree -
	 *            <code>null</code> if no record was parsed before
	 * @return the next record type as declaration node in the lines section of
	 *         the interface structure description
	 */
	private Element determineNextRecordType(Object interfaceContent,
			int stringPos, int[] line, Element recordType, int recordCount,
			Element lastRecord) throws XException
	{
		Element nextRecordType = null;
		if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING)
			nextRecordType = determineNextRecordTypeSingleByteArray(
					interfaceContent, stringPos, line, recordType, recordCount,
					lastRecord);
		else
			nextRecordType = determineNextRecordTypeSingleByteArray(
					((ByteArrayList) interfaceContent).get(line[0]), stringPos,
					line, recordType, recordCount, lastRecord);
		return nextRecordType;
	} // determineNextRecordType(Object interfaceContent, int stringPos, int[]
	// line, Element recordType, int recordCount, Element lastRecord)

	/**
	 * @param interfaceContent
	 * @param stringPos
	 * @param line
	 * @param recordType
	 * @param recordCount
	 * @param lastRecord
	 * @return
	 */
	private Element determineNextRecordTypeSingleByteArray(
			Object interfaceContent, int stringPos, int[] line,
			Element recordType, int recordCount, Element lastRecord)
			throws XException
	{
		Element nextRecordType = null;
		// for the result
		try
		// for casting to XException
		{
			if (recIdentMethod.equals("TypeIdentifier"))
			{ // With a type identifier the next record type is simply derived
				// from the interface content, just read the correct substring
				// to get its id.
				int idPos = Integer.parseInt(linesSpec
						.getAttribute("IdentifierPos"));
				int idLength = Integer.parseInt(linesSpec
						.getAttribute("IdentifierLength"));
				// Get the declaration by the id.
				if (interfaceContentClass == Constants.IFCONTENTCLASS_STRING)
					// ... from string
					nextRecordType = getRecordTypeById(((String) interfaceContent)
							.substring(stringPos + idPos, stringPos + idPos
									+ idLength));
				else
					// ... from byte array
					nextRecordType = getRecordTypeById(extractSubString(
							(byte[]) interfaceContent, idPos, idPos + idLength));
			} // if (recIdentMethod.equals("TypeIdentifier")
			else if (recordType == null)
				// First record.
				if (recOrder.equals("Structured"))
					// Get the first record type of the first record group
					nextRecordType = (Element) linesSpec.getFirstChild()
							.getFirstChild();
				else
					// Just get the first record type - there are no record
					// groups.
					nextRecordType = (Element) linesSpec.getFirstChild();
			else
			{ // Not the first record and no type identifier.
				boolean lookAtNext = true;
				// Still searching?
				nextRecordType = recordType;
				// Start with the last record type.
				while (lookAtNext && nextRecordType != null)
				{ // loop until found or end of record type list reached
					String card = nextRecordType.getAttribute("Occurrences");
					int cardNum = -1;
					int cardLow = -1;
					int cardHigh = -1;

					if (CardinalityStrings.isCardinalityInterval(card, true))
					{ // The cardinality is specified as an interval.
						cardLow = CardinalityStrings.getCardinalityLow(card,
								true);
						cardHigh = CardinalityStrings.getCardinalityHigh(card,
								true);
						if (recordCount < cardLow)
							// minimal cardinality not yet reached, thus take
							// this record type.
							lookAtNext = false;
						else
						{ // minimal cardinality already reached before
							if (cardHigh > recordCount)
							{ // maximal cardinality not yet reached
								if (linesSpec.getChildNodes().getLength() == 1)
								{ // only one record type
									lookAtNext = false;
								} // then
								// (linesSpec.getChildNodes().getLength()==1)
								else
								{
									// Look for the existance indicator.
									String existanceIndField = nextRecordType
											.getAttribute("ExistIndicatorField");
									String existanceIndValue = nextRecordType
											.getAttribute("ExistIndicatorValue");
									// Find the field in the last record.
									NodeList existInds = lastRecord
											.getElementsByTagName(existanceIndField);
									if (existInds.getLength() != 1)
									{
										List params = new Vector();
										params
												.add(XDomSupport
														.getTrimedNodeText(nextRecordType));
										params.add(existanceIndField);
										params.add(XDomSupport
												.getTrimedNodeText(recordType));
										throw new XException(
												Constants.LOCATION_INTERN,
												Constants.LAYER_PROTOCOL,
												Constants.PACKAGE_PROTOCOL_RECORDS,
												"113", params);
									} // if (existInds.getLength() != 1)
									// The existance indicator is set in the
									// last record, the next record type is
									// found.
									if (existanceIndValue.equals(XDomSupport
											.getTrimedNodeText(existInds
													.item(0))))
										lookAtNext = false;
								} // else
								// (linesSpec.getChildNodes().getLength()==1)
							} // if (cardHigh>recordCount)
						} // else (cardLow<recordCount)
					} // then (isCardinalityInterval(card,true))
					else
					{ // Cardinality is just a single value.
						cardNum = Integer.parseInt(card);
						// Cardinality already reached?
						if (cardNum < recordCount)
							lookAtNext = false;
					} // else (isCardinalityInterval(card,true))

					if (lookAtNext)
					{ // Still searching
						nextRecordType = (Element) nextRecordType
								.getNextSibling();
						recordCount = 0;
					} // if (lookAtNext)
				} // while (lookAtNext && nextRecordType!=null)

				if (lookAtNext)
					// At the end of record type list next record type not
					// found.
					if (recOrder.equals("Structured"))
						// In case of grouped records, just a new group starts.
						nextRecordType = (Element) linesSpec.getFirstChild()
								.getFirstChild();
					// Take the first record type of the first record group
					// because there is only one
					// (recIdentMethod!="TypeIdentifier").
					else
					{
						List params = new Vector();
						params.add(XDomSupport.getTrimedNodeText(recordType));
						params.add(new Integer(line[0] + 1));
						throw new XException(Constants.LOCATION_INTERN,
								Constants.LAYER_PROTOCOL,
								Constants.PACKAGE_PROTOCOL_RECORDS, "114",
								params);
					}
			} // else (recordType==null)
		} // try
		catch (IndexOutOfBoundsException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		} // catch (IndexOutOfBoundsException e)
		catch (NumberFormatException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		} // catch (NumberFormatException e)
		catch (ClassCastException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		} // catch (ClassCastException e)
		return nextRecordType;
	}

	/**
	 * @param inRecord
	 * @param doc
	 */
	public int parseSingleRecord(byte[] inRecord, Document doc,
			Element[] recordType, int recordCount) throws XException
	{
		int line[] =
		{0};
		Element lastRecord = null;
		if (doc.hasChildNodes())
			lastRecord = doc.getDocumentElement();

		Element nextRecordType = determineNextRecordTypeSingleByteArray(
				inRecord, 0, line, recordType[0], recordCount, lastRecord);

		if (recordType[0] == null
				|| !XDomSupport.getTrimedNodeText(nextRecordType).equals(
						XDomSupport.getTrimedNodeText(recordType[0])))
		{ // First record type or record type change.
			recordCount = 0;
			recordType[0] = nextRecordType;
		} // if (recordType==null ||
		// !XDomSupport.getTrimedElementValue(nextRecordType).equals(XDomSupport.getTrimedElementValue(recordType)))

		String nextRecordTypeName = XDomSupport
				.getTrimedNodeText(nextRecordType);
		Element nextRecord = doc.createElement(nextRecordTypeName);
		// the record node

		// Search for the record type specification.
		List nextRecordTypeSpecCandidates = XDomSupport
				.getChildNodesByAttrValue(recordTypesSpec, "Name",
						nextRecordTypeName, "RecordTypeSpec");
		if (nextRecordTypeSpecCandidates.size() != 1)
		{
			List params = new Vector();
			params.add(nextRecordTypeName);
			params.add(new Integer(line[0] + 1));
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "102", params);
		} // if (nextRecordTypeSpecCandidates.size() != 1)

		// Parse the record content: the fields
		parseFieldsAndGroupsSingleByteArray(inRecord, 0, line, doc, nextRecord,
				(Node) nextRecordTypeSpecCandidates.get(0));
		if (nextRecord.hasChildNodes())
		{ // some content found
			if (doc.hasChildNodes())
				doc.removeChild(doc.getFirstChild());
			doc.appendChild(nextRecord);
			// insert record into tree
			recordCount++;
		} // if (nextRecord.hasChildNodes())

		return recordCount;
	}

	/**
	 * <code>extractSubString</code> extracts a field value as string from a
	 * specific position in a byte array. The extraction is done in two steps:
	 * <ol>
	 * <li>Extraction of bytes due to position and length</li>
	 * <li>Conversion to string by the preloaded byte array converter</li>
	 * </ol>
	 * 
	 * @param array the byte array to extract the field value from
	 * @param pos the field starting position in the array (byte count)
	 * @param length the field length (byte count)
	 * @return the field value converted to a string
	 * @throws XException in case of unpossible <code>pos</code> or
	 *             <code>length</code> values
	 */
	protected String extractSubString(byte[] array, int pos, int length)
			throws XException
	{
		byte[] subArr = XByteArraySupport.subArray(array, pos, length);
		return byteArrayConverter.byteArrayToString(subArr);
	} // extractSubString(byte[] array, int pos, int length)

} // RecordTypeParser
