package net.sf.xbus.protocol.records;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;

import net.sf.xbus.base.bytearraylist.ByteArrayConverter;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.xml.XDomSupport;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * <code>RecordTypeManipulator</code> contains all things common to the
 * {@link net.sf.xbus.protocol.records.RecordTypeParser RecordTypeParser}and
 * {@link net.sf.xbus.protocol.records.RecordTypeSerializer RecordTypeSerializer}.
 * The fields concerning the interface structure description are as well shared
 * with
 * {@link net.sf.xbus.protocol.records.RecordTypeTestDataGenerator RecordTypeTestDataGenerator}.
 * 
 * @author Stephan Düwel
 */
abstract class RecordTypeManipulator
{

	// //////////////////////
	// Object bound fields
	// //////////////////////

	/**
	 * <code>sourceType</code> contains the name for the parsed structure as
	 * it is derived from a
	 * {@link net.sf.xbus.protocol.records.RecordTypeMessage RecordTypeMessage}.
	 * <code>RecordTypeManipulator</code> does not garuantee the consistency
	 * of <code>sourceType</code>,{@link #contentType}and
	 * {@link #interfaceStructure}.
	 */
	protected String sourceType;

	/**
	 * <code>contentType</code> contains the name for the parsed structure as
	 * it is derived from a {@link #interfaceStructure}.
	 * <code>RecordTypeManipulator</code> does not garuantee the consistency
	 * of <code>contentType</code> and {@link #sourceType}
	 */
	protected String contentType;

	/**
	 * <code>interfaceStructure</code> holds the structure description used
	 * for parsing/serializing. But <code>RecordTypeManipulator</code> does
	 * not load the structure description itself from a file nor does it
	 * garuantee the consistency of {@link #sourceType}with
	 * <code>interfaceStructure</code>.
	 */
	protected Document interfaceStructure;

	/**
	 * <code>headerSpec</code> is a shortcut into the
	 * {@link #interfaceStructure}for the file/string header.
	 */
	protected Element headerSpec;

	/**
	 * <code>linesSpec</code> is a shortcut into the
	 * {@link #interfaceStructure}for the lines specification.
	 */
	protected Element linesSpec;

	/**
	 * <code>recordTypesSpec</code> is a shortcut into the
	 * {@link #interfaceStructure}for the specification section of record
	 * types.
	 */
	protected Node recordTypesSpec;

	/**
	 * <code>trailerSpec</code> is a shortcut into the
	 * {@link #interfaceStructure}for the file/string trailer.
	 */
	protected Element trailerSpec;

	/**
	 * <code>groupsSpec</code> is a shortcut into the
	 * {@link #interfaceStructure}for the specification section of field
	 * groups.
	 */
	protected Node groupsSpec;

	/**
	 * <code>recIdentMethod</code> indicates how the records with the parsed
	 * string are identified during parsing. Possible values are:
	 * <ul>
	 * <li>"TypeIdentifier" - The records are identified by a contained id
	 * field.</li>
	 * <li>"RecordOrder" - The record type is determined by the line order.
	 * Works only with <code>recOrder</code> ="Ordered" / "Structured".</li>
	 * </ul>
	 */
	protected String recIdentMethod;

	/**
	 * <code>recOrder</code> determines in which order the differnt record
	 * types occurr in the parsed string. Possible values are:
	 * <ul>
	 * <li>"Arbitrary" - The records are in arbitrary order. Works only with
	 * <code>recIdentMethod</code> ="TypeIdentifier".</li>
	 * <li>"Ordered" - The records are orderd due to the record type order in
	 * this list. Record types - despite of the first one - may be optional.
	 * Together with <code>recIdentMethod</code> ="RecordOrder" all optional
	 * record types must have an existance indicator.</li>
	 * <li>"Structured" - Records of different types belong together and this
	 * is expressed by their order. Groups of records appear repeatedly. Each
	 * group repects the record type order in this list. Record types - despite
	 * of the first one - may be optional. Together with
	 * <code>recIdentMethod</code> ="RecordOrder" all optional record types
	 * must have an existance indicator.</li>
	 * </ul>
	 */
	protected String recOrder;

	/**
	 * Is the checking of the structrure description activated?
	 */
	protected boolean checkingActivated;

	/**
	 * The <code>RecordTypeManipulator</code> is able to treat interface file
	 * contents as strings or as byte array lists.
	 * <code>interfaceContentClass</code> contains the information which of
	 * these media is used.
	 */
	protected int interfaceContentClass;

	/**
	 * For converting between byte arrays and strings. Necessary because
	 * internal format for transformations is always string.
	 */
	ByteArrayConverter byteArrayConverter;

	/**
	 * Length of interface file in sense of <br>
	 * (a) characters if the content is represented as string <br>
	 * (b) bytes if the content is represented as byte array list
	 */
	protected int interfaceContentLength;

	// //////////////////////
	// Constructors
	// //////////////////////

	/**
	 * Just the standard constructor.
	 */
	protected RecordTypeManipulator() throws XException
	{
		sourceType = null;
		contentType = null;
		interfaceContentClass = Constants.IFCONTENTCLASS_BYTEARRAYLIST;
		// The more complicated case. It will work well even if pure string
		// handling is sufficient.
		interfaceStructure = null;
		headerSpec = null;
		linesSpec = null;
		recordTypesSpec = null;
		trailerSpec = null;
		groupsSpec = null;
		recIdentMethod = null;
		recOrder = null;
		checkingActivated = Configuration.getInstance("xbus")
				.getValueAsBoolean("ParserSettings", "RecordTypeMessage",
						"CheckingActivated");
		// Configuration entry: Should the interface description be checked?
	} // RecordTypeManipulator()

	// //////////////////////
	// Object bound methods
	// //////////////////////

	/**
	 * <code>getSourceType</code> retrieves the name for the parsed structure
	 * as it is derived from a
	 * {@link net.sf.xbus.protocol.records.RecordTypeMessage RecordTypeMessage}.
	 * <code>RecordTypeManipulator</code> does not garuantee the consistency
	 * of <code>sourceType</code>,{@link #contentType}and
	 * {@link #interfaceStructure}.
	 */
	public String getSourceType()
	{
		return sourceType;
	} // getSourceType()

	/**
	 * <code>getInterfaceContentClass</code> retrieves the mode (string or
	 * byte array list) in which the <code>RecordTypeManipulator</code> is
	 * working in.
	 */
	public int getInterfaceContentClass()
	{
		return interfaceContentClass;
	} // getInterfaceContentClass()

	/**
	 * <source>getInterfaceDescription </source> extracts the name of the
	 * interface description file.
	 * 
	 * @param system the origin of the message
	 * @return name of the interface description file
	 * @throws XException in case that the interface file name can not be
	 *             retrieved
	 */
	protected String getInterfaceDescription(String system) throws XException
	{
		Configuration config = Configuration.getInstance();
		String fileName = config.getValue("System", system, "DescriptionFile");
		return Constants.XBUS_ETC + "InterfaceDescriptions"
				+ Constants.FILE_SEPERATOR + fileName;
	} // getInterfaceDescription(XBUSSystem source)

	/**
	 * <code>initialize</code> sets the name for the parsed structure and the
	 * structure description used for parsing/serializing. The structure
	 * description is checked if this option is activated in the configuration.
	 * <code>RecordTypeManipulator</code> does not load the structure
	 * description itself from a file nor does it garuantee the consistency of
	 * {@link #sourceType}with {@link #interfaceStructure}.
	 * 
	 * @param system the name for the parsed structure as it is derived from a
	 *            {@link net.sf.xbus.protocol.records.RecordTypeMessage RecordTypeMessage}
	 * @param ifContentClass the mode (string or byte array list) in which the
	 *            <code>RecordTypeManipulator</code> is working in
	 * @param builder ???
	 * @throws XException in case of an illegal structure description. In this
	 *             case the description is not set!
	 */
	public void initialize(String system, int ifContentClass,
			DocumentBuilder builder) throws XException
	{
		String interfaceDescription = getInterfaceDescription(system);
		// The path of the interface description file (xml)
		if (interfaceDescription == null || interfaceDescription.length() == 0)
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "91");
		else
		{
			interfaceStructure = null;
			// For the interface structure parsed from a xml file
			try
			// for casting to XException
			{
				interfaceStructure = builder.parse(interfaceDescription);
				// The record type structure description we have as DOM tree.
			} // try
			catch (IOException e)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
			} // catch (IOException e)
			catch (SAXException e)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
			} // catch (SAXException e)
			// Initialize the parser/serialiser with the interface file type as
			// name
			// and as structure description.
			// This may induce a check on the description file. Some of the
			// checks may be done by validating the document against its DTD.
			// "builder.isValidating()" indicates whether the used parser does
			// such a check.

			try
			// try-catch block for casting standard exceptions to XException
			{
				DocumentType docType = interfaceStructure.getDoctype();
				if (docType != null
						&& !docType.getName().equals("InterfaceSpec"))
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "67");
				if (system == null || system.length() == 0)
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "68");
				// content type as derived from a RecordTypeMessage

				Element root = interfaceStructure.getDocumentElement();
				contentType = root.getAttribute("Name");
				// content type as specified in the structure description

				NodeList toplevel = root.getChildNodes();
				int topLevelCount = toplevel.getLength();

				if (topLevelCount < 2)
					// Extension if no prior validating:
					// At least there must be the lines and the record type
					// specs section.
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "69");

				int pos = 0;
				// Counting the top level nodes.

				headerSpec = (Element) toplevel.item(pos);
				// The header is optional.
				if (headerSpec.getNodeName().equals("Header"))
					pos++;
				else
					headerSpec = null;

				linesSpec = (Element) toplevel.item(pos);
				// The lines section is mandatory but a quick check does not
				// harm.
				if (linesSpec.getNodeName().equals("Lines"))
					pos++;
				else
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "70");

				// How to identify the record types
				recIdentMethod = linesSpec.getAttribute("RecordIdentification");
				recOrder = linesSpec.getAttribute("RecordOrder");

				if (pos < topLevelCount)
				{ // Some top level nodes left.
					recordTypesSpec = toplevel.item(pos);
					// The record type specifications are mandatory but a quick
					// ckeck does not harm
					if (recordTypesSpec.getNodeName().equals("RecordTypes"))
						pos++;
					else
						throw new XException(Constants.LOCATION_INTERN,
								Constants.LAYER_PROTOCOL,
								Constants.PACKAGE_PROTOCOL_RECORDS, "71");
				} // then (pos<topLevelCount) - before trying record type
				// specifications section
				else
					// The record type specifications are missing.
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "69");

				if (pos < topLevelCount)
				{ // Some top level nodes left.
					trailerSpec = (Element) toplevel.item(pos);
					// The trailer is optional.
					if (trailerSpec.getNodeName().equals("Trailer"))
						pos++;
					else
						trailerSpec = null;

					if (pos < topLevelCount)
					{
						groupsSpec = toplevel.item(pos);
						// Field groups may be defined or not.
						if (!groupsSpec.getNodeName().equals("Groups"))
						{ // Unexpected tag.
							List params = new Vector();
							params.add(groupsSpec.getNodeName());
							params.add("Groups");
							throw new XException(Constants.LOCATION_INTERN,
									Constants.LAYER_PROTOCOL,
									Constants.PACKAGE_PROTOCOL_RECORDS, "85",
									params);
						} // if (!groupsSpec.getNodeName().equals("Groups"))
					} // if (pos<topLevelCount) - before trying groups section
				} // if (pos<topLevelCount) - before trying trailer

				if (checkingActivated)
				{ // Check structure description for consistency refering to
					// the
					// single parts.
					RecordTypeDescriptionChecker checker = RecordTypeDescriptionChecker
							.getInstance();

					if (headerSpec != null)
						checker.checkHeaderSpecification(headerSpec,
								groupsSpec, builder.isValidating());

					Object[] checkInfos = checker.checkLinesSpecification(
							linesSpec, builder.isValidating());
					// record type names and existance indicator declarations
					// stored for
					// further consistency checks

					// Check the record type specifications.
					// In particular mark in <checkInfos> all specified record
					// types
					// and all existance indicators pointing to an existing
					// field.
					checker.checkRecordTypesSpecification(recordTypesSpec,
							groupsSpec, checkInfos, builder.isValidating());

					// Everthing in <checkInfos> found?
					for (int i = 0; i < ((String[]) checkInfos[0]).length; i++)
						if (((String[]) checkInfos[0])[i] != null)
						{ // <null> indicates that the declared record type is
							// specified.
							List params = new Vector();
							params.add(((String[]) checkInfos[0])[i]);
							throw new XException(Constants.LOCATION_INTERN,
									Constants.LAYER_PROTOCOL,
									Constants.PACKAGE_PROTOCOL_RECORDS, "74",
									params);
						} // if (((String[]) checkInfos[0])[i] != null)

					// Existance indicators as second checkInfos component
					// are optional: not used if
					// <recIdentMethod>=="TypeIdentifier".
					if (checkInfos[1] != null)
					{ // <checkInfos[1]> contains all existance indicators
						// which
						// point from any record type to any other.
						for (int i = 0; i < ((TreeMap[]) checkInfos[1]).length; i++)
						{ // loop over all record types in the role of a
							// preceding
							// record containg an existance indicator for a
							// suceeding
							// record type
							TreeMap existanceIndicators = ((TreeMap[]) checkInfos[1])[i];
							// the existance indicators from one specific record
							// type to others
							if (existanceIndicators != null)
							{ // some existance indicators depending on fields
								// on
								// this record type
								// Look if all of them point to existing fields.
								Iterator it = existanceIndicators.values()
										.iterator();
								while (it.hasNext())
								{ // loop over all existance indicators for
									// each
									// record type
									String value = (String) it.next();
									if (!value.equals("Found"))
									{ // "Found" indicates that the respective
										// field specification exists.
										List params = new Vector();
										params.add(value);
										throw new XException(
												Constants.LOCATION_INTERN,
												Constants.LAYER_PROTOCOL,
												Constants.PACKAGE_PROTOCOL_RECORDS,
												"75", params);
									} // if (!value.equals("Found"))
								} // while (it.hasNext())
							} // if (existanceIndicators!=null)
						} // for (int i=0;
							// i<((TreeMap[])checkInfos[1]).length;
						// i++)
					} // if (checkInfos[1]!=null)

					if (trailerSpec != null)
						checker.checkTrailerSpecification(trailerSpec,
								groupsSpec, builder.isValidating());

					if (groupsSpec != null)
						checker.checkGroupsSpecification(groupsSpec, builder
								.isValidating());
				} // if (checkingActivated)

				// Everything checked - if desired - thus store the structure
				// information
				sourceType = system;

				// the working mode details (string / byte array list)
				interfaceContentClass = ifContentClass;
				byteArrayConverter = getByteArrayConverter();
			} // try
			catch (Exception e) // i.e. DOMException, ClassCastException
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
			} // catch
		} // else (interfaceDescription==null ||
		// interfaceDescription.length()==0)
	} // initialize(String system, int ifContentClass, DocumentBuilder
		// builder)

	/**
	 * <code>getByteArrayConverter</code> retrieves a byte array converter
	 * (conversion between strings and byte arrays) according to the specific
	 * interface needs. Its is abstract because subclasses use different ways of
	 * determining the correct interface name.
	 * 
	 * @return ByteArrayConverter for string-byte array conversion in the
	 *         specific interface situation
	 */
	abstract protected ByteArrayConverter getByteArrayConverter()
			throws XException;

	/**
	 * <code>getRecordTypePositionInLines</code> retrieves the position of a
	 * record type in the lines section.
	 * 
	 * @param recordType as node in the lines section
	 * @return the position of the record type in the lines section (starting at
	 *         0) - relative to the record group if {@link #recOrder}
	 *         =="Structured"
	 * @throws XException in case that the record type cannot be found
	 */
	protected int getRecordTypePositionInLines(Element recordType)
			throws XException
	{
		int pos = -1;
		// for the position
		NodeList recordTypes = recordType.getParentNode().getChildNodes();
		// Complicated to serve for both situations - with and without record
		// groups
		for (int i = 0; i < recordTypes.getLength() && pos == -1; i++)
			// loop over record type declarations below the same parent node
			if (XDomSupport.getTrimedNodeText(recordTypes.item(i)).equals(
					XDomSupport.getTrimedNodeText(recordType)))
				// Found
				pos = i;
		if (pos == -1)
		{
			List params = new Vector();
			params.add(XDomSupport.getTrimedNodeText(recordType));
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "77", params);
		}
		return pos;
	} // getPositionInLines(Element recordType)

	/**
	 * <code>getRecordTypeByID</code> retrieves the record type declaration
	 * within the lines section by the type identifier.
	 * 
	 * @param searchedId the type identifier
	 * @return the record type declaration node in the lines section as as
	 *         {@link org.w3c.dom.Element}
	 * @throws XException in case that the record type cannot be found
	 */
	protected Element getRecordTypeById(String searchedId) throws XException
	{
		Node recordGroup = linesSpec;
		if (recOrder.equals("Structured"))
			// Go to record group level
			recordGroup = linesSpec.getFirstChild();
		Element result = null;
		try
		// for casting to XException
		{
			while (recordGroup != null && result == null)
			{ // loop over record groups
				NodeList recordTypes = recordGroup.getChildNodes();
				// record types in this group
				for (int i = 0; i < recordTypes.getLength() && result == null; i++)
				{ // loop over record types in the group
					result = (Element) recordTypes.item(i);
					String id = result.getAttribute("Identifier");
					if (id.length() > 0)
					{ // type id specified as single value
						if (!id.equals(searchedId))
							// not the right one
							result = null;
					} // then (id.length()>0)
					else
					{ // type id specified as interval
						String idLo = result.getAttribute("IdentifierLow");
						String idHi = result.getAttribute("IdentifierHigh");
						if (idLo.length() == 0 || idHi.length() == 0
								|| idLo.compareTo(searchedId) > 0
								|| idHi.compareTo(searchedId) < 0)
							// searched id not comtained in interval
							result = null;
					} // else (id.length()>0)
				} // for (int i=0; i<recordTypes.getLength()&&result==null;
					// i++)

				if (recordGroup.getNodeName().equals("RecordGroup"))
					// Next record group
					recordGroup = recordGroup.getNextSibling();
				else
					// If there are nor groups, just finish the record group
					// loop
					recordGroup = null;
			} // while (recordGroup!=null && result==null)
		} // try
		catch (ClassCastException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		} // catch
		if (result == null)
		{
			List params = new Vector();
			params.add(searchedId);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "79", params);
		}
		return result;
	} // getRecordTypePosById(String searchedId)

	/**
	 * <code>getRecordTypeSpec</code> retrieves the record type specification
	 * from the record types section to a record type name.
	 * 
	 * @param recordType the record type name
	 * @return the record type specification as {@link org.w3c.dom.Node node}in
	 *         the interface structure description
	 * @throws XException in case that the record type specification cannot be
	 *             found
	 */
	protected Node getRecordTypeSpec(String recordType) throws XException
	{
		try
		// for casting to XException
		{
			List candidates = XDomSupport.getChildNodesByAttrValue(
					recordTypesSpec, "Name", recordType, "RecordTypeSpec");
			if (candidates.size() != 1)
			{
				List params = new Vector();
				params.add(recordType);
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "80", params);
			} // if (candidates.size() != 1)
			return (Node) candidates.get(0);
		} // try
		catch (ClassCastException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		} // catch
	} // getRecordTypeSpec(String recordType)

	/**
	 * <code>getFieldPos</code> determines the position of a field within the
	 * surrounding record type - including header and trailer.
	 * 
	 * @param fieldSpec the field as {@link org.w3c.dom.Element elementnode}
	 * @param parentSpec the specification of the surrounding record type
	 * @return the field position within the record type
	 * @throws XException in case of problems with the description file
	 */
	protected int getFieldPos(Element fieldSpec, Node parentSpec)
			throws XException
	{
		int pos = 0;
		// for the result
		boolean searching = true;
		try
		// for casting to XException
		{
			String fieldName = fieldSpec.getAttribute("Name");
			if (fieldName == null || fieldName.length() == 0)
			{
				List params = new Vector();
				params.add("Name");
				params.add(((Element) fieldSpec.getParentNode())
						.getAttribute("Name"));
				params.add(fieldSpec.toString());
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "82", params);
			} // if (fieldName == null || fieldName.length() == 0)
			NodeList fieldSpecs = parentSpec.getChildNodes();
			for (int i = 0; i < fieldSpecs.getLength() && searching; i++)
			{ // loop over fields and group references within record type
				Element searchedFieldSpec = (Element) fieldSpecs.item(i);
				if (searchedFieldSpec.getNodeName().equals("Field"))
					// a single field, compare its name
					if (fieldName
							.equals(searchedFieldSpec.getAttribute("Name")))
						searching = false;
					else
						pos++;
				else if (searchedFieldSpec.getNodeName().equals("Group"))
				{ // an entire field group
					NodeList groupFields = RecordTypeDescriptionChecker
							.getGroupSpec(
									searchedFieldSpec.getAttribute("Name"),
									groupsSpec).getChildNodes();
					for (int j = 0; j < groupFields.getLength(); j++)
					{ // loop over group fields
						searchedFieldSpec = (Element) groupFields.item(j);
						// now a single field, compare its name
						if (fieldName.equals(searchedFieldSpec
								.getAttribute("Name")))
							searching = false;
						else
							pos++;
					} // for (int j=0; j<groupFields.getLength(); j++)
				} // if (searchedFieldSpec.getNodeName().equals("Group"))
			} // for (int i=0; i<fieldSpecs.getLength()&&searching; i++)
		} // try
		catch (ClassCastException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		} // catch
		if (searching)
		{ // Field not found
			List params = new Vector();
			params.add(((Element) fieldSpec.getParentNode())
					.getAttribute("Name"));
			params.add(fieldSpec.getAttribute("Name"));
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "84", params);
		}
		return pos;
	} // getFieldPos(Element fieldSpec, Node parentSpec)

	/**
	 * <code>getTypeIdentifier</code> retrieves the type identifier field.
	 * 
	 * @param idPosString the identifier position in the record line - in sense
	 *            of char counting if the interface contant is treated as
	 *            string; in sense of byte counting if a byte array list is used
	 *            instead
	 * @param recordTypeSpec the record type specification to search in
	 * @return the name of the type identifier field - null if it could not be
	 *         found
	 * @throws XException in case of problems with the description file or the
	 *             identifier position
	 */
	protected String getTypeIdentifier(String idPosString, Node recordTypeSpec)
			throws XException
	{
		String result = null;
		try
		// for casting to XException
		{ // the identifier position
			int idPos = Integer.parseInt(idPosString);
			// the field specifications
			NodeList fields = recordTypeSpec.getChildNodes();
			int i = 0;
			int[] pos =
			{0};
			// the search position - array for call by reference to search in
			// groups
			for (; result == null && i < fields.getLength() && pos[0] <= idPos; i++)
			{ // loop over fields until identifier position is reached
				Element fieldSpec = (Element) fields.item(i);
				if (fieldSpec.getNodeName().equals("Field"))
					// a single field
					if (pos[0] == idPos)
						// identifier position reached
						result = fieldSpec.getAttribute("Name");
					else
						// Go further.
						pos[0] += Integer.parseInt(fieldSpec
								.getAttribute("Length"));
				else if (fieldSpec.getNodeName().equals("Group"))
					// an entire field group
					result = getTypeIdentifierFromGroup(idPos, fieldSpec, pos);
				else
				{
					List params = new Vector();
					params.add(fieldSpec.getNodeName());
					params.add("Field or Group");
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "85", params);
				} // else (fieldSpec.getNodeName().equals("Group"))
			} // for (; i<fields.getLength()&&pos[0]<=idPos; i++)
		} // try
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
		return result;
	} // getTypeIdentifier(String idPosInString, Node recordTypeSpec)

	/**
	 * <code>getTypeIdentifierFromGroup</code> searches the type identifier
	 * field within a field group.
	 * 
	 * @param idPos the identifier position in the record line - in sense of
	 *            char counting if the interface contant is treated as string;
	 *            in sense of byte counting if a byte array list is used instead
	 * @param groupRef the group reference
	 * @param pos the actual search position, will be augmented - array for call
	 *            by reference
	 * @return the name of the type identifier field - null if it could not be
	 *         found
	 * @throws XException in case of problems with the description file
	 */
	private String getTypeIdentifierFromGroup(int idPos, Element groupRef,
			int[] pos) throws XException
	{
		try
		// for casting to XException
		{ // the group fields
			NodeList fieldSpecs = RecordTypeDescriptionChecker.getGroupSpec(
					groupRef.getAttribute("Name"), groupsSpec).getChildNodes();
			// fields in the group
			for (int i = 0; i < fieldSpecs.getLength() && pos[0] <= idPos; i++)
			{ // Loop over group fields until identifier position is reached
				Element fieldSpec = (Element) fieldSpecs.item(i);
				// identifier position reached
				if (pos[0] == idPos)
					fieldSpec.getAttribute("Name");
				else
					// Go further.
					pos[0] += Integer
							.parseInt(fieldSpec.getAttribute("Length"));
			} // for (int i=0; i<fieldSpecs.getLength(); i++)
		} // try
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
		} // catch
		return null;
	} // getTypeIdentifierFromGroup(int idPos, Element groupRef, int[] pos)

} // RecordTypeManipulator
