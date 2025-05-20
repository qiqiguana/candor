package net.sf.xbus.protocol.records;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.strings.CardinalityStrings;
import net.sf.xbus.base.xml.XDomSupport;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <code>RecordTypeDescriptionChecker</code> checks the xml description of a
 * record type interface. This description explains the structure of the used
 * interface files.
 * 
 * @author Stephan Düwel
 */
class RecordTypeDescriptionChecker
{

	/**
	 * <code>instances</code> stores the instances of the checker by thread.
	 */
	private static Hashtable instances = new Hashtable();

	/**
	 * <code>getInstance</code> returns a
	 * <code>RecordTypeDescriptionChecker</code> to work with.
	 * 
	 * @return a checker instance
	 */
	public static RecordTypeDescriptionChecker getInstance()
	{ // Instances are stored by thread.
		RecordTypeDescriptionChecker instance = (RecordTypeDescriptionChecker) instances
				.get(Thread.currentThread().getName());
		if (instance == null)
		{ // No checker initialised yet
			instance = new RecordTypeDescriptionChecker();
			instances.put(Thread.currentThread().getName(), instance);
		} // if (instance==null)
		return instance;
	} // getInstance()

	// ///////////////////////////////////////////////////
	// Checking the structrue description top level
	// ///////////////////////////////////////////////////

	/**
	 * <code>checkHeaderSpecification</code> checks the consistency of the
	 * file header desription.
	 * 
	 * @param headerSpec
	 *            the structure description for the header
	 * @param groupsSpec
	 *            the list of field group specifications to check group
	 *            references
	 * @param validated
	 *            Is the xml already validated?
	 * @throws XException -
	 *             in case of a inconsistency
	 */
	public void checkHeaderSpecification(Element headerSpec, Node groupsSpec,
			boolean validated) throws XException
	{
		if (!validated && headerSpec.getAttribute("Name").length() == 0)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "1");
		}
		// Now the single fields in the header
		checkFieldsAndGroupRefs(headerSpec, groupsSpec, null, validated);
	} // checkHeaderSpecification(Element headerSpec, Node groupsSpec, boolean

	// validated)

	/**
	 * <code>checkLinesSpecification</code> checks the consistency of the
	 * lines section. The lines section lists all record types, declares their
	 * order in the file and specifies how to identify them in the file.
	 * Furthermore is fills the <code>recordTypeNames</code> and
	 * <code>existanceIndicators</code> arrays to prepare checking the
	 * consistency + of the record type declarations and the specifications
	 * afterwards.
	 * 
	 * @param linesSpec
	 *            the lines section of the structure description
	 * @param validated
	 *            Is the xml already validated?
	 * @return an array with 2 elements, the first is the array of all record
	 *         type names, the second is an array of existance indicators (<code>null</code>
	 *         if record identification method is "TypeIdentifier")
	 * @throws XException
	 *             in case of an inconsistency
	 */
	public Object[] checkLinesSpecification(Element linesSpec, boolean validated)
			throws XException
	{
		// In which order to find the records
		String recOrder = linesSpec.getAttribute("RecordOrder");

		if (!validated)
		{ // Errors in the xml?
			if (recOrder.length() == 0)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "2");
			}
			if (!recOrder.equals("Arbitrary") && !recOrder.equals("Ordered")
					&& !recOrder.equals("Structured"))
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "3");
			}
		} // if (!validated)

		// How to identify the records
		String recIdentMethod = linesSpec.getAttribute("RecordIdentification");

		if (!validated)
		{ // Errors in the xml?
			if (recIdentMethod.length() == 0)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "4");
			}
			if (!recIdentMethod.equals("TypeIdentifier")
					&& !recIdentMethod.equals("RecordOrder"))
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "5");
			}
		} // if (!validated)

		if (recOrder.equals("Arbitrary")
				&& !recIdentMethod.equals("TypeIdentifier"))
		{
			// forbidden combination
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "6");
		}
		// the record type declarations or ...
		// ... the list of record groups, if <recOrder>="Structured"
		NodeList recordTypes = linesSpec.getChildNodes();
		int numOfRecordTypes = recordTypes.getLength();
		// only ok if <recOrder>!="Structured" because otherwise this computes
		// the number of record groups!!!!!!!!!

		if (recordTypes.getLength() == 0)
		{
			// Extension if no prior DTD validating
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "7");
		}
		// Loop will run over record types, but the identification of record
		// record groups above is important, as well.
		// If <recOrder>!="Structured", the parent node is just the Lines node.
		Node recordGroup = linesSpec;

		// Now some corrections to the setting so far if record groups must be
		// treated.
		if (recOrder.equals("Structured"))
		{
			if (!recIdentMethod.equals("TypeIdentifier")
					&& recordTypes.getLength() > 1)
			{
				// Several record groups are only allowed if the record types
				// can
				// be identiifed by type identifiers.
				// Attention: <recordTypes> contains the record groups - not the
				// types!
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "8");
			}
			numOfRecordTypes = 0;
			// For counting the record types a loop over the record groups is
			// necessary.
			for (int i = 0; i < recordTypes.getLength(); i++)
			{ // loop over record groups
				if (!recordTypes.item(i).getNodeName().equals("RecordGroup"))
				{
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "9");
				}
				numOfRecordTypes += recordTypes.item(i).getChildNodes()
						.getLength();

				// the number of records in that group
			} // for (int i=0; i<recordTypes.getLength(); i++)

			recordGroup = recordTypes.item(0);
			// for loop over record groups
			recordTypes = recordGroup.getChildNodes();
			// the record types one level deeper now, thus really to record
			// types
		} // if (recOrder.equals("Structured"))

		TreeMap[] existanceIndicators = null;
		// for record types which's occurrence depends on a certain value in the
		// record before

		if (recIdentMethod.equals("TypeIdentifier"))
		{ // type identification by type identifier is easy but needs some
			// values
			// where to find the identifier within the records
			String idPos = linesSpec.getAttribute("IdentifierPos");
			// Where does the identifier start in the record string?
			if (idPos.length() == 0)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "10");
			}
			String idLength = linesSpec.getAttribute("IdentifierLength");
			// How long is the type identifier?
			if (idLength.length() == 0)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "11");
			}
			try
			{ // The position must be a positive integer.
				int num = Integer.parseInt(idPos);
				if (num < 0)
				{
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "12");
				}
				// The length as well must be positive integer.
				// Length 0 is permitted for having a simple handling of files
				// with
				// only one record type.
				num = Integer.parseInt(idLength);
				if (num < 0)
				{
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "13");
				}
			} // try
			catch (ClassCastException e)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
			} // catch
		} // then (recIdentMethod.equals("TypeIdentifier"))
		else
		// Without type identifiers everything gets more complicated ...
		// ... but the primary value are already set.
		{ // Indicators for successors may be important.
			existanceIndicators = new TreeMap[numOfRecordTypes];
			for (int i = 0; i < existanceIndicators.length; i++)
				existanceIndicators[i] = null;
			// Will be filled when going through the single record type
			// declarations.
		} // else (recIdentMethod.equals("TypeIdentifier"))

		String[] recordTypeNames = new String[numOfRecordTypes];
		// Storing the names for consistency check gainst the record type
		// specification section.
		LinkedList typeIds = new LinkedList();
		LinkedList typeIdIntervals = new LinkedList();
		// Record type identifier may be single values or intervals.
		// They are stored to check their unique reference to a record type.
		int recordTypesCount = recordTypes.getLength();
		// the number of record types in the current record group or at all
		// (wihtout grouping) - at least if no bad nodes are included in a
		// errorprone specification
		int recordTypesBefore = 0;
		// number of record type declarations checked in other record groups
		// before - used as offset to datermine the position in the record type
		// array
		while (recordGroup != null)
		{ // loop over record groups
			// - only one loop if <recOrder>!="Structured" because of missing
			// record groups
			for (int i = 0; i < recordTypesCount; i++)
			{ // Loop over all record type declarations of the record group
				Node rType = recordTypes.item(i);

				// Extension if no DTD validating
				if (rType.getNodeType() != Node.ELEMENT_NODE
						|| !rType.getNodeName().equals("RecordType"))
				{
					List params = new Vector();
					params.add(rType.getNodeName());
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "15", params);
				}
				// Check the consistency of this single record type declaration.
				// If its occurrence depends on values in the prececeeding
				// record
				// the information about this value is stored in
				// <dependenceOnPredecessor>.
				String[] dependenceOnPredecessor = checkRecordTypeDeclaration(
						rType, recordTypesCount, recordTypesBefore + i,
						recIdentMethod, recOrder, recordTypeNames, typeIds,
						typeIdIntervals, existanceIndicators);

				if (existanceIndicators != null
						&& dependenceOnPredecessor != null)
				{ // Existance records are expected in general and an
					// existance
					// indicator is defined for the last cheched record type.
					// To ensure that the identification via existance
					// indicators
					// is possible, the information about them is analysed the
					// other
					// way round. Originally the information was attached as
					// <dependenceOnPredecessor> to the dependent record type.
					// Now it will be attached as <existanceIndicators> to its
					// possible predecessors.
					String occurrences = ((Element) recordTypes.item(i))
							.getAttribute("Occurrences");
					// Cardinality of the record type.
					boolean isOptional = CardinalityStrings
							.isCardinalityInterval(occurrences, true)
							&& CardinalityStrings.getCardinalityLow(
									occurrences, true) == 0;
					for (int j = i - 1; j > -1 && isOptional; j++)
					{ // Backwards loop to the beginning of the record types
						// starting at the predecessor of the examined record
						// type.
						// As long as a record type is optional, its predecessor
						// is
						// added to the list of possible predecessors for the
						// originally
						// checked record type.
						// Stops at the first record type because this one may
						// not be optional.

						// Now information attached to the predecessor.
						if (existanceIndicators[j] == null)
						{ // The predecessor does not yet have identified
							// successor
							// which depend on an existnace indicator.
							existanceIndicators[j] = new TreeMap();
							existanceIndicators[j].put(
									dependenceOnPredecessor[0],
									dependenceOnPredecessor[1]);
						} // then (existanceIndicators[j]==null)
						else
						{ // The predecessor already has information about
							// successors.
							// Existance indicator value for the existance
							// indicator
							// field stated in the examined record type, but
							// used for
							// another record type !?
							String value = (String) existanceIndicators[j]
									.get(dependenceOnPredecessor[0]);
							if (value != null)
								// Existance indicator values in the same field
								// for different record types.
								if (value.length() != dependenceOnPredecessor[1]
										.length())
								{
									throw new XException(
											Constants.LOCATION_INTERN,
											Constants.LAYER_PROTOCOL,
											Constants.PACKAGE_PROTOCOL_RECORDS,
											"16");
								}
								else
									// No other use of the existance indicator
									// field yet.
									// Store the new value.
									existanceIndicators[j].put(
											dependenceOnPredecessor[0],
											dependenceOnPredecessor[1]);
						} // else (existanceIndicators[j]==null)
						occurrences = ((Element) recordTypes.item(j))
								.getAttribute("Occurrences");
						// Cardinality of the last checked record type
						isOptional = CardinalityStrings.isCardinalityInterval(
								occurrences, true)
								&& CardinalityStrings.getCardinalityLow(
										occurrences, true) == 0;
					} // for (int j=i-1; j>-1 && isOptional; j++)
				} // if (existanceIndicators!=null &&
				// dependenceOnPredecessor!=null)
			} // for (int i=0; i<recordTypes.getLength(); i++)

			recordTypesBefore += recordTypesCount;
			// Correct the number of checked record types in the so far
			// examined record groups

			if (recordGroup.getNodeName().equals("RecordGroup"))
			{ // record groups really present
				recordGroup = recordGroup.getNextSibling();
				// Choose next record group
				if (recordGroup != null)
				{ // there is a next record group
					recordTypes = recordGroup.getChildNodes();
					recordTypesCount = recordTypes.getLength();
					// Next check the record types in the new group
				} // if (recordGroup != null)
			} // if (recordGroup.getNodeName().equals("RecordGroup"))
			else
				// no real record group loop but record declarations directly
				// in lines section, terminate loop by setting ...
				recordGroup = null;
		} // while (recordGroup!=null) - record group loop

		// Check record type names for uniqueness.
		for (int i = 0; i < recordTypeNames.length; i++)
			for (int j = i + 1; j < recordTypeNames.length; j++)
				if (recordTypeNames[i].equals(recordTypeNames[j]))
				{
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "17");
				}
		// Checks on the type identifier
		// First check their length
		// If the record type identification is not done by type identifiers,
		// the two type id lists are empty.
		int identifierLength = 0;
		if (typeIds.size() > 0)
			// Some single value id's
			identifierLength = ((String) typeIds.get(0)).length();
		else if (typeIdIntervals.size() > 0)
			// No single value id but some id intervals
			identifierLength = ((String[]) typeIdIntervals.get(0))[0].length();
		// All id's must have the same length.
		// First the single value id's
		for (int i = 1; i < typeIds.size(); i++)
			if (((String) typeIds.get(0)).length() != identifierLength)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "18");
			}
		// Second the id intervals
		for (int i = 1; i < typeIdIntervals.size(); i++)
			// Attention: two values!
			if (((String[]) typeIdIntervals.get(0))[0].length() != identifierLength
					|| ((String[]) typeIdIntervals.get(0))[1].length() != identifierLength)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "18");
			}
		// Second, check the id uniqueness in sense of identification of only
		// one
		// record type
		if (!typeIdsAreUnique(typeIds, typeIdIntervals))
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "20");
		}

		// Return the found record type names and existance indicators.
		Object[] checkInfos = new Object[2];
		checkInfos[0] = recordTypeNames;
		checkInfos[1] = existanceIndicators;
		return checkInfos;
	} // checkLinesSpecification(Element linesSpec, boolean validated)

	/**
	 * <code>checkRecordTypesSpecification</code> checks the section of record
	 * type specifications for consistency. In particular it checks for
	 * existance of the record types and fields named in the lines section.
	 * 
	 * @param recordTypesSpec
	 *            the record type specifications section
	 * @param groupsSpec
	 *            the list of field group specifications to check group
	 *            references
	 * @param checkInfos
	 *            record type names and existance indicators found in the lines
	 *            section
	 * @param validated
	 *            Is the xml already validated?
	 * @throws XException
	 *             in case of an inconsistency
	 */
	public void checkRecordTypesSpecification(Node recordTypesSpec,
			Node groupsSpec, Object[] checkInfos, boolean validated)
			throws XException
	{
		try
		// for casting to XException
		{
			NodeList recordTypes = recordTypesSpec.getChildNodes();
			// Here we have no record groups!
			for (int i = 0; i < recordTypes.getLength(); i++)
			{ // loop over record type specifications
				Element recordType = (Element) recordTypes.item(i);

				// Extension if no prior validating
				if (!recordType.getNodeName().equals("RecordTypeSpec"))
				{
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "21");
				}
				String recTypeName = recordType.getAttribute("Name");
				// Extension if no prior validating
				if (recTypeName == null
						|| !XDomSupport.isValidTagName(recTypeName))
				{
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "22");
				}
				// Search record type name in the name array from
				// the record type (Lines) section.
				boolean nameSearched = true;
				// Still searching?
				int pos = 0;
				for (; pos < ((String[]) checkInfos[0]).length && nameSearched; pos++)
					// loop over record type names from the lines section
					if (recTypeName.equals(((String[]) checkInfos[0])[pos]))
					{ // name found
						((String[]) checkInfos[0])[pos] = null;
						// deleted for avoiding more hits
						nameSearched = false;
						// search loop ended for this record type
					} // if
				// (recTypeName.equals(((String[])checkInfos[0])[pos]))
				if (nameSearched)
				{
					// Record type not declared in lines section
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "23");
				}
				// Now the single fields in the record type
				TreeMap existanceIndicators = null;
				// With the found position in the checkInfos array
				// the correct existance indicator map is retrieved ...
				if (checkInfos[1] != null)
					// Because pos was augmented once to much in the for loop,
					// now
					// use pos-1.
					existanceIndicators = ((TreeMap[]) checkInfos[1])[pos - 1];
				// ... and feeded to field checking
				checkFieldsAndGroupRefs(recordType, groupsSpec,
						existanceIndicators, validated);
			} // for (int i=0; i<recordTypes.getLength(); i++)
		} // try
		catch (ClassCastException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		} // catch
	} // checkRecordTypesSpecification(Node recordTypesSpec, Node groupsSpec,

	// Object[] checkInfos, boolean validated)

	/**
	 * <code>checkTrailerSpecification</code> checks the consistency of the
	 * file trailer desription.
	 * 
	 * @param trailerSpec
	 *            the structure description for the trailer
	 * @param groupsSpec
	 *            the list of field group specifications to check group
	 *            references
	 * @param validated
	 *            Is the xml already validated?
	 * @throws XException
	 *             in case of a inconsistency
	 */
	public void checkTrailerSpecification(Element trailerSpec, Node groupsSpec,
			boolean validated) throws XException
	{
		// Extension if no prior DTD validating
		if (trailerSpec.getAttribute("Name").length() == 0)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "1");
		}
		// Now the single fields in the header
		checkFieldsAndGroupRefs(trailerSpec, groupsSpec, null, validated);
	} // checkTrailerSpecification(Element trailerSpec, Node groupsSpec,

	// boolean validated)

	/**
	 * <code>checkGroupsSpecification</code> checks the consistency of each
	 * field group specification.
	 * 
	 * @param groupsSpec
	 *            the list of field group specifications
	 * @param validated
	 *            Is the xml already validated?
	 * @throws XException
	 *             in case of an inconsistency
	 */
	public void checkGroupsSpecification(Node groupsSpec, boolean validated)
			throws XException
	{
		NodeList groups = groupsSpec.getChildNodes();

		if (!validated)
		{ // Extension if no prior DTD validating
			try
			// for casting to XException
			{
				for (int i = 0; i < groups.getLength(); i++)
					// consistency check on each single group specification
					// (redundant to validating against DTD)
					checkGroupSpecification((Element) groups.item(i));
			} // try
			catch (ClassCastException e)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
			} // catch
		} // if (!validated)

		// Check for the uniqueness of group names
		for (int i = 0; i < groups.getLength() - 1; i++)
		{
			String groupName = ((Element) groups.item(i)).getAttribute("Name");
			for (int j = i + 1; j < groups.getLength(); j++)
				if (groupName.equals(((Element) groups.item(j))
						.getAttribute("Name")))
				{
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "27");
				}
		} // for (int i=0; i<groups.getLength()-1; i++)
	} // checkGroupsSpecification(Node groupsSpec, boolean validated)

	// //////////////////////////////////////////////
	// Structrue checking on some deeper level
	// //////////////////////////////////////////////

	/**
	 * <code>checkRecordTypeDeclaration</code> checks a record type
	 * declaration for consistency. Furthermore is fills the
	 * <code>recordTypeNames</code> and <code>existanceIndicators</code> to
	 * check consistency with the record type specifications afterwards.
	 * 
	 * @param recTypeDecl
	 *            the DOM node of the declaration
	 * @param recordTypesCount
	 *            the number of record types in the actual record group or the
	 *            whole file (wihtout grouping)
	 * @param pos
	 *            the position in the list of declarations (starts at 0)
	 * @param recIdentMethod
	 *            the method to identify record types
	 * @param recOrder
	 *            the order of records in the file
	 * @param recordTypeNames
	 *            array of record type names, filled at position
	 *            <code>pos</code> right here
	 * @param typeIds
	 *            the list of single-value type id's, a found id will be added
	 * @param typeIdIntervals
	 *            the list of type id intervals, a found interval will be added
	 * @param existanceIndicators
	 *            array of existance indicator maps, potentially filled with
	 *            self reference at position <code>pos</code> right here
	 * @return field/value pair for an existance indicator in the record type
	 * @throws XException
	 *             in case of an inconsistency
	 */
	public String[] checkRecordTypeDeclaration(Node recTypeDecl,
			int recordTypesCount, int pos, String recIdentMethod,
			String recOrder, String[] recordTypeNames, LinkedList typeIds,
			LinkedList typeIdIntervals, TreeMap[] existanceIndicators)
			throws XException
	{
		String[] precedingIndicator = null;
		// for returning exstistance indicator information for predecessors

		try
		// for casting to XException
		{
			if (recIdentMethod.equals("TypeIdentifier"))
			{ // Record types are identified by an id
				String id = ((Element) recTypeDecl).getAttribute("Identifier");
				// a single-value id
				String[] idLoHi = new String[2];
				idLoHi[0] = ((Element) recTypeDecl)
						.getAttribute("IdentifierLow");
				idLoHi[1] = ((Element) recTypeDecl)
						.getAttribute("IdentifierHigh");
				// an id interval
				if (id.length() == 0)
				{ // No single-value id
					if (idLoHi[0].length() == 0 || idLoHi[1].length() == 0)
					{
						// Something must be specified: single value or
						// interval.
						throw new XException(Constants.LOCATION_INTERN,
								Constants.LAYER_PROTOCOL,
								Constants.PACKAGE_PROTOCOL_RECORDS, "28");
					}
					typeIdIntervals.add(idLoHi);
				} // then (id.length()==0)
				else
				{ // Single-value id found
					if (idLoHi[0].length() > 0 || idLoHi[1].length() > 0)
					{
						// Any interval specification conflicts.
						throw new XException(Constants.LOCATION_INTERN,
								Constants.LAYER_PROTOCOL,
								Constants.PACKAGE_PROTOCOL_RECORDS, "28");
					}
					typeIds.add(id);
				} // else (id.length()==0)
			} // if (recIdentMethod.equals("TypeIdentifier"))

			recordTypeNames[pos] = XDomSupport.getTrimedNodeText(recTypeDecl);
			// the record type name
			if (!XDomSupport.isValidTagName(recordTypeNames[pos]))
			{
				// It must be a legal xml tag name.
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "30");
			}
			// Now look for record identification features, in particular the
			// cardinality
			// and existance indicator
			if (recOrder.equals("Ordered") || recOrder.equals("Structured"))
			{ // Records are listed in the record type order or in groups.
				// The cardinality must be specified.
				String occurrences = ((Element) recTypeDecl)
						.getAttribute("Occurrences");
				if (occurrences.length() == 0)
				{
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "31");
				}
				if (!CardinalityStrings.isCardinalityValid(occurrences))
				{
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "32");
				}

				// The first record in a record group must have cardinality 1.
				if (recOrder.equals("Structured") && pos == 0)
				{
					try
					{
						int occurrNum = Integer.parseInt(occurrences);
						if (occurrNum != 1)
						{
							throw new XException(Constants.LOCATION_INTERN,
									Constants.LAYER_PROTOCOL,
									Constants.PACKAGE_PROTOCOL_RECORDS, "33");
						}
					} // try
					catch (NumberFormatException e)
					{
						throw new XException(Constants.LOCATION_INTERN,
								Constants.LAYER_PROTOCOL,
								Constants.PACKAGE_PROTOCOL_RECORDS, "33");
					} // catch
				} // if (recOrder.equals("Structured") && pos==0)

				// If the record type is not given by an id but the cardinality
				// is specified as an
				// interval and there are also other record types, there must be
				// an existance indicator
				// refering the preceding record.
				if (recordTypesCount > 1
						&& !recIdentMethod.equals("TypeIdentifier")
						&& CardinalityStrings.isCardinalityInterval(
								occurrences, true))
				{
					String existIndField = ((Element) recTypeDecl)
							.getAttribute("ExistIndicatorField");
					// Indicator field specified by name
					if (existIndField.length() == 0)
					{
						throw new XException(Constants.LOCATION_INTERN,
								Constants.LAYER_PROTOCOL,
								Constants.PACKAGE_PROTOCOL_RECORDS, "35");
					}
					String existIndValue = ((Element) recTypeDecl)
							.getAttribute("ExistIndicatorValue");
					if (existIndValue.length() == 0)
					{
						throw new XException(Constants.LOCATION_INTERN,
								Constants.LAYER_PROTOCOL,
								Constants.PACKAGE_PROTOCOL_RECORDS, "36");
					}
					precedingIndicator = new String[2];
					precedingIndicator[0] = existIndField;
					precedingIndicator[1] = existIndValue;
					// If the record type may occurr more than once, it may be
					// its
					// predecessor itself.
					if (CardinalityStrings
							.getCardinalityHigh(occurrences, true) > 1)
					{
						existanceIndicators[pos] = new TreeMap();
						existanceIndicators[pos].put(precedingIndicator[0],
								precedingIndicator[1]);
					} // if (getCardinalityHigh(occurrences,true)>1)
				} // if (!recIdentMethod.equals("TypeIdentifier") &&
				// isCardinalityInterval(occurrences,true))
			} // if (recOrder.equals("Ordered") ||
			// recOrder.equals("Structured"))
		} // try
		catch (ClassCastException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		} // catch
		return precedingIndicator;
		// Group starting record type found?
	} // checkRecordTypeDeclaration(Node recTypeDecl, int pos, String

	// recIdentMethod, String recOrder, String[] recordTypeNames, LinkedList
	// typeIds, LinkedList typeIdIntervals, TreeMap[] existanceIndicators)

	/**
	 * <code>checkFieldsAndGroupRefs</code> checks the fields and group
	 * references within an record type (including header and trailer) for
	 * consistency.
	 * 
	 * @param parent
	 *            the record type specification node
	 * @param groupsSpec
	 *            the list of field group specifications
	 * @param existanceIndicators
	 *            existance indicator map for the surrounding record type,
	 *            references to fields will be marked
	 * @param validated
	 *            Is the xml already validated?
	 * @throws XException
	 *             in case of an inconsistency
	 */
	private void checkFieldsAndGroupRefs(Node parent, Node groupsSpec,
			TreeMap existanceIndicators, boolean validated) throws XException
	{
		try
		// try-catch block for casting standard exceptions to XException
		{
			NodeList fieldSpecs = parent.getChildNodes();
			for (int i = 0; i < fieldSpecs.getLength(); i++)
			{
				Element fieldSpec = (Element) fieldSpecs.item(i);
				// Two alternatives: field or group
				if (fieldSpec.getNodeName().equals("Field"))
					checkFieldSpecification(fieldSpec, existanceIndicators,
							validated);
				else if (fieldSpec.getNodeName().equals("Group"))
					checkGroupReference(fieldSpec, groupsSpec,
							existanceIndicators);
				else
				// Extension if no prior DTD validating
				{
					List params = new Vector();
					params.add(fieldSpec.getNodeName());
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "38", params);
				}
			} // for (int i=0; i<fieldSpecs.getLength(); i++)
		} // try
		catch (ClassCastException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		} // catch
	} // checkFieldsAndGroupRefs(Node parent, Node groupsSpec, TreeMap

	// existanceIndicators)

	/**
	 * <code>checkGroupReference</code> checks the consistency of a reference
	 * to a field group.
	 * 
	 * @param groupRef
	 *            the DOM node for the group reference
	 * @param groupsSpec
	 *            the list of field group specifications to check group
	 *            references
	 * @param existanceIndicators
	 *            existance indicator map for the surrounding record type,
	 *            references to group fields will be marked
	 * @throws XException
	 *             in case of an inconsistency
	 */
	public void checkGroupReference(Element groupRef, Node groupsSpec,
			TreeMap existanceIndicators) throws XException
	{
		try
		{
			String groupName = groupRef.getAttribute("Name");

			// Extension if no prior DTD validating
			if (groupName.length() == 0)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "40");
			}
			// Search for the group specification.
			if (groupsSpec == null)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "41");
			}
			List groups = XDomSupport.getChildNodesByAttrValue(groupsSpec,
					"Name", groupName, "GroupSpec");
			if (groups == null || groups.size() == 0)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "42");
			}
			if (groups.size() > 1)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "43");
			}
			// Group specification found

			// some existance indicators to search for
			if (existanceIndicators != null)
			{
				NodeList fields = ((Node) groups.get(0)).getChildNodes();
				for (int i = 0; i < fields.getLength(); i++)
				{
					String name = ((Element) fields.item(i))
							.getAttribute("Name");
					String value = (String) existanceIndicators.get(name);
					if (value != null)
					{
						if (value.equals("Found"))
						{
							// Will always occurr if a record type contains more
							// than one instance of
							// a group. Thus, existance indicators in group
							// should not occurr.
							throw new XException(Constants.LOCATION_INTERN,
									Constants.LAYER_PROTOCOL,
									Constants.PACKAGE_PROTOCOL_RECORDS, "44");
						}
						int length = Integer
								.parseInt(((Element) fields.item(i))
										.getAttribute("Length"));
						if (value.length() != length)
						{
							throw new XException(Constants.LOCATION_INTERN,
									Constants.LAYER_PROTOCOL,
									Constants.PACKAGE_PROTOCOL_RECORDS, "45");
						}
						// Mark as found.
						existanceIndicators.put(name, "Found");
					} // if (value!=null)
				} // for (int i=0; i<fields.getLength(); i++)
			} // if (existanceIndicators!=null)
		} // try
		catch (ClassCastException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		}
		catch (NumberFormatException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		}
	} // checkGroupReference(Element groupRef, Node groupsSpec, TreeMap

	// existanceIndicators)

	/**
	 * <code>checkFieldSpecification</code> checks the consistency of a field
	 * specification.
	 * 
	 * @param fieldSpec
	 *            the DOM node for the field specification
	 * @param existanceIndicators
	 *            existance indicator map for the surrounding record type,
	 *            references to field will be marked
	 * @param validated
	 *            Is the xml already validated?
	 * @throws XException
	 *             in case of an inconsistency
	 */
	private void checkFieldSpecification(Element fieldSpec,
			TreeMap existanceIndicators, boolean validated) throws XException
	{
		try
		{ // Field name
			String name = fieldSpec.getAttribute("Name");
			// Field length
			String length = fieldSpec.getAttribute("Length");
			// Field format
			String format = fieldSpec.getAttribute("Format");

			if (!validated)
			{ // Extension if no prior DTD validating
				if (name.length() == 0)
				{
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "48");
				}
				if (length.length() == 0)
				{
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "49");
				}
				if (format.length() == 0)
				{
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "50");
				}
			} // if (!validated)

			// For dates further format infoamtion is mandatory.
			if (format.equals("date")
					&& fieldSpec.getAttribute("DateFormat").length() == 0)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "51");
			}

			// The length must be an strictly positive integer
			int lengthNum = Integer.parseInt(length);
			if (lengthNum < 1)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "52");
			}
			// Constant values must be bound to a value.
			if (format.equals("const"))
			{
				String value = fieldSpec.getAttribute("Value");
				if (value.length() == 0)
				{
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "53");
				}
				else if (value.length() != lengthNum)
				{
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "54");
				}
			} // if (fieldSpec.getAttribute("Format").equals("const"))

			// some existance indicators to search for
			if (existanceIndicators != null)
			{
				String value = (String) existanceIndicators.get(name);
				if (value != null)
				{ // existance indicator defined for this field
					if (value.equals("Found"))
					{
						// Already found for other field
						throw new XException(Constants.LOCATION_INTERN,
								Constants.LAYER_PROTOCOL,
								Constants.PACKAGE_PROTOCOL_RECORDS, "44");
					}
					if (value.length() != lengthNum)
					{
						throw new XException(Constants.LOCATION_INTERN,
								Constants.LAYER_PROTOCOL,
								Constants.PACKAGE_PROTOCOL_RECORDS, "45");
					}
					// Mark as found.
					existanceIndicators.put(name, "Found");
				} // if (value!=null)
			} // if (existanceIndicators!=null)
		} // try
		catch (NumberFormatException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		} // catch
		catch (ClassCastException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		} // catch
	} // checkFieldSpecification(Element fieldSpec, TreeMap

	// existanceIndicators, boolean validated)

	/**
	 * <code>checkGroupSpecification</code> checks the consistency of a single
	 * field group specification. Extension if no prior DTD validating because
	 * otherwise completely redundant.
	 * 
	 * @param groupSpec
	 *            the group specification to check as DOM node
	 * @throws XException
	 *             in case of an inconsistency
	 */
	public void checkGroupSpecification(Element groupSpec) throws XException
	{
		if (groupSpec.getAttribute("Name").length() == 0)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "59");
		}
		try
		// for casting to XException
		{
			NodeList fieldSpecs = groupSpec.getChildNodes();
			// fields in the group
			for (int i = 0; i < fieldSpecs.getLength(); i++)
			{ // Loop over all group fields
				if (!((Element) fieldSpecs.item(i)).getNodeName().equals(
						"Field"))
				{
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "60");
				}
			} // for (int i=0; i<fieldSpecs.getLength(); i++)
		} // try
		catch (ClassCastException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		} // catch
	} // checkGroupSpecification(Node groupSpec)

	// ///////////////////////
	// Auxiliary functions
	// ///////////////////////

	/**
	 * <code>typeIdsAreUnique</code> checks if each declared record type id
	 * refers uniquely to only one record type.
	 * 
	 * @param typeIds
	 *            single-value id's as found in the record type declarations
	 * @param typeIdIntervals
	 *            intervals of id's (used as counters) as found in the record
	 *            type declarations
	 * @return <code>true</code> if all id's are unique, false if an id refers
	 *         to two record types
	 */
	public boolean typeIdsAreUnique(LinkedList typeIds,
			LinkedList typeIdIntervals)
	{
		boolean result = true;
		for (int i = 0; i < typeIds.size() && result; i++)
		{ // Loop over all single-value id's
			for (int j = i + 1; j < typeIds.size() && result; j++)
				// Loop over rest of single-value id's
				result = !((String) typeIds.get(i)).equals((typeIds.get(j)));
			// Repeated id?
			for (int j = 0; j < typeIdIntervals.size() && result; j++)
				// Loop over all id intervals
				result = (((String) typeIds.get(i))
						.compareTo(((String[]) typeIdIntervals.get(j))[0]) < 0)
						|| (((String) typeIds.get(i))
								.compareTo(((String[]) typeIdIntervals.get(j))[1]) > 0);
			// Or member of one of the intervals?
		} // for (int i=0; i<typeIds.size() && result; i++)
		for (int i = 0; i < typeIdIntervals.size() && result; i++)
			// Loop over all id intervals
			for (int j = i + 1; j < typeIdIntervals.size() && result; j++)
				// Loop over rest of intervals
				result = (((String[]) typeIdIntervals.get(i))[0]
						.compareTo(((String[]) typeIdIntervals.get(j))[1]) > 0)
						|| (((String[]) typeIdIntervals.get(i))[1]
								.compareTo(((String[]) typeIdIntervals.get(j))[0]) < 0);
		// Do the two intervals intersect?
		return result;
	} // typeIdsAreUnique(LinkedList typeIds, LinkedList typeIdIntervals)

	/**
	 * <code>computeRecordLength</code> calculates the string length for a
	 * record type - including header and trailer.
	 * 
	 * @param spec
	 *            the record type specification
	 * @param groupsSpec
	 *            the list of group specifications
	 * @return the total length including filler
	 * @throws XException
	 *             in case of a mal-specified length
	 */
	protected static int computeRecordLength(Node spec, Node groupsSpec)
			throws XException
	{
		int fieldLengthSum = 0;
		// for the result
		NodeList fieldSpecs = spec.getChildNodes();
		try
		// to cast to XException
		{
			for (int i = 0; i < fieldSpecs.getLength(); i++)
			{
				Element fieldSpec = (Element) fieldSpecs.item(i);
				if (fieldSpec.getNodeName().equals("Field"))
					fieldLengthSum += Integer.parseInt(fieldSpec
							.getAttribute("Length"));
				else if (fieldSpec.getNodeName().equals("Group"))
					fieldLengthSum += computeGroupLength(fieldSpec, groupsSpec);
			} // for (int i=0; i<fieldSpecs.getLength(); i++)
		}
		catch (NumberFormatException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		}
		return fieldLengthSum;
	} // computeRecordLength(Element spec)

	/**
	 * <code>computeGroupLength</code> calculates the string length for a
	 * field group.
	 * 
	 * @param groupRef
	 *            a refernce to the group
	 * @param groupsSpec
	 *            the list of group specifications
	 * @return the total length including filler
	 * @throws XException
	 *             in case of a mal-specified length or missing group
	 *             specification
	 */
	protected static int computeGroupLength(Element groupRef, Node groupsSpec)
			throws XException
	{
		int groupLengthSum = 0;
		// for the result
		NodeList fieldSpecs = getGroupSpec(groupRef.getAttribute("Name"),
				groupsSpec).getChildNodes();
		// the group specification
		try
		// for casting to XException
		{
			for (int i = 0; i < fieldSpecs.getLength(); i++)
			{
				Element fieldSpec = (Element) fieldSpecs.item(i);
				if (fieldSpec.getNodeName().equals("Field"))
					groupLengthSum += Integer.parseInt(fieldSpec
							.getAttribute("Length"));
				else
				{
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_RECORDS, "63");
				}
			} // for (int i=0; i<fieldSpecs.getLength(); i++)
		} // try
		catch (NumberFormatException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		} // catch
		return groupLengthSum;
	} // computeGroupLength(Element groupRef, Node groupsSpec)

	/**
	 * <code>getGroupSpec</code> selects the group specification node by the
	 * group name.
	 * 
	 * @param groupName
	 *            the name of the field group
	 * @param groupsSpec
	 *            the list of group specifications
	 * @return the group specification node
	 * @throws XException
	 *             in case that the group specification cannot be retrieved
	 */
	protected static Node getGroupSpec(String groupName, Node groupsSpec)
			throws XException
	{
		Node result = null;
		try
		// for casting to XException
		{
			List groups = XDomSupport.getChildNodesByAttrValue(groupsSpec,
					"Name", groupName, "GroupSpec");
			if (groups.size() != 1)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "65");
			}
			result = (Node) groups.get(0);
		} // try
		catch (ClassCastException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		} // catch
		return result;
	} // getGroupSpec(String groupName, Node groupsSpec)

} // RecordTypeDescriptionChecker
