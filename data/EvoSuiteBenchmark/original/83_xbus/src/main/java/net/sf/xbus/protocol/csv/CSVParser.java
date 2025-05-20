/*
 * Created on 04.10.2004
 */

package net.sf.xbus.protocol.csv;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.xml.XDomSupport;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <p>
 * The <code>CSVParser</code> class contains all methods necessary for parsing
 * CSV format into an XML representation.
 * </p>
 * <p>
 * This parser supports all CSV format content which satisfies the rules
 * indicated in <a href="http://www.edoceo.com/utilis/csv-file-format.php">this
 * CSV definition </a>.
 * </p>
 * <p>
 * The parsing result is a DOM tree with the following structure:
 * </p>
 * <p>
 * &lt;InterfaceName&gt; <dir>&lt;+ <i>Header </i>+&gt; <dir>&lt;+ <i>heading
 * </i>+&gt;+ <b>FieldValue </b>+&lt;/+ <i>heading </i>+&gt; <br>
 * ... </dir> &lt;/+ <i>Header </i>+&gt; </dir> <dir>&lt;Records&gt; <dir>&lt;+
 * <i>Record </i>+&gt; <dir>&lt;+ <i>FieldName </i>+&gt;+ <b>FieldValue
 * </b>+&lt;/+ <i>FieldName </i>+&gt; <br>
 * ... </dir> &lt;/+ <i>Record </i>+&gt; <br>
 * ... </dir> &lt;/Records&gt; </dir> &lt;/InterfaceName&gt;
 * </p>
 * <p>
 * The header is optional. For every entry a Record tag structure is created.
 * </p>
 * <p>
 * The information for the names and values of the DOM entities are taken out of
 * the CSV source text and the optional Description file. A description file
 * must be valid (InterfaceSpecCSV.dtd).
 * </p>
 * <p>
 * The following table gives an overview, how the Header information and the
 * names of the entry tags are selected:
 * </p>
 * 
 * <table border="1">
 * <tr>
 * <td colspan="2"><b>CSV file </b></td>
 * <td colspan="5"><b>Description file </b></td>
 * <td colspan="2"><b>Result </b></td>
 * </tr>
 * <tr  align="center">
 * <td>has header</td>
 * <td>does not contain header</td>
 * <td>No description file</td>
 * <td>does not contain any information</td>
 * <td>contains only Header information</td>
 * <td>contains only Records information</td>
 * <td>contains Header and Records information</td>
 * <td><b>Header information taken from: </b></td>
 * <td><b>Tag names of entries taken from: </b></td>
 * </tr>
 * <tr align="center">
 * <td>X</td>
 * <td>&nbsp;</td>
 * <td>X</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>CSV header</td>
 * <td>CSV header</td>
 * </tr>
 * 
 * <tr align="center">
 * <td>X</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>X</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>CSV header</td>
 * <td>CSV header</td>
 * </tr>
 * 
 * <tr align="center">
 * <td>X</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>X</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>Description file</td>
 * <td>Header information in description file</td>
 * </tr>
 * 
 * <tr align="center">
 * <td>X</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>X</td>
 * <td>&nbsp;</td>
 * <td>CSV header</td>
 * <td>Records information in description file</td>
 * </tr>
 * 
 * <tr align="center">
 * <td>X</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>X</td>
 * <td>Description file</td>
 * <td>Records information in description file</td>
 * </tr>
 * 
 * <tr align="center">
 * <td>&nbsp;</td>
 * <td>X</td>
 * <td>X</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>No header</td>
 * <td>Tag name = &quot;field&quot;</td>
 * </tr>
 * 
 * <tr align="center">
 * <td>&nbsp;</td>
 * <td>X</td>
 * <td>&nbsp;</td>
 * <td>X</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>No header</td>
 * <td>Tag name = &quot;field&quot;</td>
 * </tr>
 * 
 * <tr align="center">
 * <td>&nbsp;</td>
 * <td>X</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>X</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>Description file</td>
 * <td>Header information in description file</td>
 * </tr>
 * 
 * <tr align="center">
 * <td>&nbsp;</td>
 * <td>X</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>X</td>
 * <td>&nbsp;</td>
 * <td>No header</td>
 * <td>Records information in description file</td>
 * </tr>
 * 
 * <tr align="center">
 * <td>&nbsp;</td>
 * <td>X</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>&nbsp;</td>
 * <td>X</td>
 * <td>Description file</td>
 * <td>Records information in description file</td>
 * </tr>* </table>
 */
public class CSVParser
{
	private static Hashtable instances = new Hashtable();

	private static final Object classLock = CSVParser.class;

	private String fieldSeparator;

	private String quoteChar;

	private boolean hasHeader;

	private String lineSeparator;

	private Document descriptionTree = null;

	private Vector fieldNames = null;

	/**
	 * @param quoteChar
	 *            The quoteChar to set.
	 */
	public void setQuoteChar(String quoteChar)
	{
		this.quoteChar = quoteChar;
	}

	/**
	 * @return Returns the quoteChar.
	 */
	public String getQuoteChar()
	{
		return quoteChar;
	}

	/**
	 * @return Returns the hasHeader.
	 */
	public boolean isHasHeader()
	{
		return hasHeader;
	}

	/**
	 * @param hasHeader
	 *            The hasHeader to set.
	 */
	public void setHasHeader(boolean hasHeader)
	{
		this.hasHeader = hasHeader;
	}

	/**
	 * @param fieldSeparator
	 *            The fieldSeparator to set.
	 */
	public void setFieldSeparator(String fieldSeparator)
	{
		this.fieldSeparator = fieldSeparator;
	}

	/**
	 * @return Returns the fieldSeparator.
	 */
	public String getFieldSeparator()
	{
		return fieldSeparator;
	}

	/**
	 * @param lineSeparator
	 *            The lineSeparator to set.
	 */
	public void setLineSeparator(String lineSeparator)
	{
		this.lineSeparator = lineSeparator;
	}

	/**
	 * @return Returns the lineSeparator.
	 */
	public String getLineSeparator()
	{
		return lineSeparator;
	}

	/**
	 * @return Returns the descriptionTree.
	 */
	public Document getDescriptionTree()
	{
		return descriptionTree;
	}

	/**
	 * @param descriptionTree
	 *            The descriptionTree to set.
	 */
	public void setDescriptionTree(Document descriptionTree)
	{
		this.descriptionTree = descriptionTree;
	}

	/**
	 * @return Returns the headerArray.
	 */
	public Vector getFieldNames()
	{
		return fieldNames;
	}

	/**
	 * @param fieldNames
	 */
	public void setFieldNames(Vector fieldNames)
	{
		this.fieldNames = fieldNames;
	}

	/**
	 * Adds a value to the field names. The field names are the names of the
	 * tags of an entry. If the passed name is not a valid tagname,
	 * &quot;field&quot; is added to the list of names instead of the passed
	 * name. Namespace tag names are also not allowed and replaced bz
	 * &quot;field&quot;
	 * 
	 * @param fieldName
	 *            The field name to add
	 * @throws XException
	 *             If parser configuration is not valid.
	 */
	private void addFieldName(String fieldName) throws XException
	{
		if (getFieldNames() == null)
		{
			setFieldNames(new Vector());
		}
		try
		{
			DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.newDocument().createElement(fieldName);
		}
		catch (DOMException e)
		{ // if fieldName is not a valid tag name
			fieldName = "Field";
		}
		catch (ParserConfigurationException parserException)
		{
			Vector error = new Vector();
			error.add(parserException.toString());
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL, Constants.PACKAGE_PROTOCOL_CSV,
					"10");
		}
		// we do not allow : otherwise namespace declaration would be necessary
		if (fieldName.indexOf(":") >= 0)
		{
			fieldName = "Field";
		}
		getFieldNames().add(fieldName);
	}

	/**
	 * Constructor of the parser. Starts the initialization which loads the
	 * configuration values.
	 * 
	 * @param system
	 *            the name of the process
	 * @throws XException
	 */
	public CSVParser(String system) throws XException
	{
		initialize(system);
	}

	/**
	 * Returns an instance of <code>CSVParser</code>
	 * 
	 * @param sourceType
	 *            The name of the process
	 * @return An instance of the CSVSerializer class
	 */
	protected static CSVParser getInstance(String sourceType) throws XException
	{
		synchronized (classLock)
		{
			if (sourceType == null || sourceType.length() == 0)
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_CSV, "1");

			CSVParser instance = (CSVParser) instances.get(Thread
					.currentThread().getName()
					+ sourceType);
			if (instance == null)
			{ // no suitable instance available yet
				instance = new CSVParser(sourceType);
				instances.put(Thread.currentThread().getName() + sourceType,
						instance);
			} // if (instance==null)
			return instance;
		}
	}

	/**
	 * Initializes a CSVParser object by reading and setting the configuration.
	 * Parameters of CSVparser are: fieldSeparator, quoteChar, hasHeader,
	 * lineSeparator and the description file.
	 * 
	 * @param sectionName
	 *            The name of the process to read the values in the
	 *            configuration file from.
	 * @throws XException
	 */
	private void initialize(String sectionName) throws XException
	{
		Configuration config = Configuration.getInstance();
		Configuration xBusConfig = Configuration.getInstance("xbus");
		String tempVariable = null;

		// get Field Separator
		if ((fieldSeparator = config.getValueOptional(Constants.CHAPTER_SYSTEM,
				sectionName, Constants.KEY_CSV_FIELD_SEPARATOR)) == null)
		{
			fieldSeparator = xBusConfig.getValue("MessageDefaults",
					"CSVMessage", Constants.KEY_CSV_FIELD_SEPARATOR);
		}

		// get Quote Character
		if ((quoteChar = config.getValueOptional(Constants.CHAPTER_SYSTEM,
				sectionName, Constants.KEY_CSV_QUOTE_CHAR)) == null)
		{
			quoteChar = xBusConfig.getValue("MessageDefaults", "CSVMessage",
					Constants.KEY_CSV_QUOTE_CHAR);
		}

		// get configuration entry if containing header
		if ((tempVariable = config.getValueOptional(Constants.CHAPTER_SYSTEM,
				sectionName, Constants.KEY_CSV_HAS_HEADER)) == null)
		{
			hasHeader = xBusConfig.getValueAsBoolean("MessageDefaults",
					"CSVMessage", Constants.KEY_CSV_HAS_HEADER);
		}
		else if (tempVariable.equalsIgnoreCase(Constants.CONFIGURATION_TRUE))
		{
			hasHeader = true;
		}
		else
		{
			hasHeader = false;
		}

		// set line separator
		lineSeparator = Constants.LINE_SEPERATOR;
		String platform = config.getValueOptional(Constants.CHAPTER_SYSTEM,
				sectionName, "Platform");
		if (platform != null)
		{
			lineSeparator = Constants.getLineSeperator(platform);
		}

		if ((tempVariable = config.getValueOptional(Constants.CHAPTER_SYSTEM,
				sectionName, Constants.KEY_CSV_DESCRIPTION_FILE)) == null)
		{
			setDescriptionTree(null);
		}
		else
		{
			String fileName = Constants.XBUS_ETC + "InterfaceDescriptions"
					+ Constants.FILE_SEPERATOR + tempVariable;
			File descriptionFile = new File(fileName);
			if (!descriptionFile.exists())
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_CSV, "7");
			}
			try
			{
				DocumentBuilder builder = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder();

				Document doc = builder.parse(descriptionFile);
				XDomSupport.deleteWhitespaceTextInElementNodesAndComments(doc);
				setDescriptionTree(doc);
			}
			catch (Exception e)
			{
				Vector errors = new Vector();
				errors.add(e.toString());
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_CSV, "8", errors);
			}
		}

	}

	/**
	 * Parses the passed CSV format text into a DOM tree and returns it.
	 * 
	 * @param text
	 *            The source text to parse. Has to be CSV format.
	 * @param systemName
	 *            The name of the process - used as root tag
	 * @return The parsed DOM tree.
	 * @throws XException
	 */
	public Document parse(String text, String systemName) throws XException
	{
		DocumentBuilder builder = null;
		try
		{
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		}
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL, Constants.PACKAGE_PROTOCOL_CSV,
					"13");
		}
		Document resultDocument = builder.newDocument();

		// create root node <i>systemName</i>
		Element root = resultDocument.createElement(systemName);
		resultDocument.appendChild(root);

		// separate the entries
		Vector lines = returnSeparatedEntries(text);
		int lineCounter = 0;

		boolean useHeaderAsTagName = true;
		if ((searchTagInDescriptionFile("Records")) && (fieldNames == null))
		{
			writeFieldNamesOutOfDescriptionFile();
			useHeaderAsTagName = false;
		}
		if (searchTagInDescriptionFile("Header"))
		{
			writeHeaderInformation(returnHeaderOfDescriptionFile(),
					resultDocument, root, (useHeaderAsTagName && (fieldNames == null)));
		}
		else if (isHasHeader())
		{// only hasHeader
			Vector headerVector = parseOneLine(lines.get(lineCounter));
			writeHeaderInformation(headerVector, resultDocument, root,
					useHeaderAsTagName);
		}// isHasHeader()

		// first Line is not content - skip!
		if (isHasHeader())
		{
			lineCounter++;
		}

		Element records = resultDocument.createElement("Records");
		root.appendChild(records);

		// go through the entries and parse every single one of them
		for (; lineCounter < lines.size(); lineCounter++)
		{
			Vector lineValues = parseOneLine(lines.get(lineCounter));
			writeOneRecord(lineValues, resultDocument, records);
		}

		return resultDocument;
	}

	/**
	 * Writes the header information into the DOM tree.
	 * 
	 * @param contentVector
	 *            The values of the header.
	 * @param document
	 *            The result document
	 * @param root
	 *            The root node of the document (usually systemName)
	 * @param writeFieldNames
	 *            If the header names should also be used as field tag names.
	 * @throws XException
	 */
	private void writeHeaderInformation(Vector contentVector,
			Document document, Node root, boolean writeFieldNames)
			throws XException
	{
		Element header = document.createElement("Header");
		root.appendChild(header);
		Enumeration e = contentVector.elements();
		while (e.hasMoreElements())
		{
			String element = getStringOutOfObject(e.nextElement());
			Element elem = document.createElement("Heading");
			Node textElem = document.createTextNode(element);
			header.appendChild(elem);
			elem.appendChild(textElem);
			if (writeFieldNames)
			{
				addFieldName(element);
			}
		}
	}

	/**
	 * Separates the text into CSV entries by using the fieldSeparator as
	 * indicator and sticking together again entries which consist of more than
	 * one line
	 * 
	 * @param sourceText
	 *            The text to separate.
	 * @return A Vector containing the entries.
	 * @throws XException
	 *             If last entry does not end correctly before end of text.
	 *             Usually missing quoteChar.
	 */
	private Vector returnSeparatedEntries(String sourceText) throws XException
	{
		Vector entries = new Vector();
		int lineTokenizer = 0;

		// get rid of empty lines(just spaces) in the beginning and the end
		sourceText = sourceText.trim();
		String[] lines = sourceText.split(getLineSeparator());
		while (lineTokenizer < lines.length)
		{
			// are we lucky and the line does not contain any line break in a
			// field
			// an odd number of quoteChars indicates that line is not a complete
			// entry
			if ((countOccurencesOfString(getQuoteChar(), lines[lineTokenizer]) % 2) == 0)
			{
				entries.add(lines[lineTokenizer++]);
				continue;
			}

			// we have a line break in the field!
			int startLine = lineTokenizer;
			// run until we get a line without a line break in a field
			try
			{
				while (((countOccurencesOfString(quoteChar,
						lines[++lineTokenizer])) % 2) == 0)
					;
			}
			catch (ArrayIndexOutOfBoundsException e)
			{
				Vector error = new Vector();
				error.add(Integer.toString(lineTokenizer));
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_CSV, "4", error);
			}

			String buildEntry = "";
			for (int i = startLine; i <= lineTokenizer; i++)
			{
				// add lineSeparator inside text(lost when spliting)
				if (i != startLine)
				{
					buildEntry += getLineSeparator();
				}
				buildEntry += lines[i];
			}
			entries.add(buildEntry);
			lineTokenizer++;
		}
		return entries;
	}

	/**
	 * Parses one entry into the fields and returns them as Vector.
	 * 
	 * @param lineText
	 *            The source text to parse.
	 * @return The parsed fields as Vector.
	 * @throws XException
	 */
	private Vector parseOneLine(Object lineText) throws XException
	{
		Vector returnVector = new Vector();
		int fieldTokenizer = 0;

		// add a space at the end to successfully parse empty fields at the end
		// of line
		// a; b; c; would be interpreted as 3 fields otherwise
		String[] fields = (getStringOutOfObject(lineText) + " ")
				.split(getFieldSeparator());
		while (fieldTokenizer < fields.length)
		{
			if ((countOccurencesOfString(getQuoteChar(), fields[fieldTokenizer]) % 2) == 0)
			{
				returnVector.add(fields[fieldTokenizer++]);
				continue;
			}

			// we have a fieldSeparator in the field!
			int startField = fieldTokenizer;
			// run until we get a field without a fieldSeparator
			try
			{
				while (((countOccurencesOfString(getQuoteChar(),
						fields[++fieldTokenizer])) % 2) == 0)
					;
			}
			catch (ArrayIndexOutOfBoundsException e)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_CSV, "4");
			}
			String buildEntry = "";
			for (int i = startField; i <= fieldTokenizer; i++)
			{
				// add lineSeparator inside text(lost when spliting)
				if (i != startField)
				{
					buildEntry += getFieldSeparator();
				}
				buildEntry += fields[i];
			}
			returnVector.add(buildEntry);
			fieldTokenizer++;
		}
		returnVector = cleanFields(returnVector);
		return returnVector;
	}

	/**
	 * Parses one record into a document.
	 * 
	 * @param values
	 *            The entries of the record.
	 * @param document
	 *            The document where the nodes have to be integrated.
	 * @param recordsElement
	 *            The root element of the entry fields.
	 * @throws XException
	 *             If the entry does not have the correct number of fields
	 */
	private void writeOneRecord(Vector values, Document document,
			Node recordsElement) throws XException
	{
		Element record = document.createElement("Record");
		recordsElement.appendChild(record);
		// we require same number of fields for every entry
		// could be changed if necessary like done for RecordTypeMessage

		// do we have no field declaration (out of header or description file)?
		if (getFieldNames() == null)
		{
			// first entry defines the number of fields
			for (int i = 0; i < values.size(); i++)
			{
				addFieldName("Field");
			}
		}
		// does the entry have the correct number of fields?
		if (values.size() != getFieldNames().size())
		{
			Vector error = new Vector();
			error.add(values.toString());
			error.add(Integer.toString(values.size()));
			error.add(Integer.toString(getFieldNames().size()));
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL, Constants.PACKAGE_PROTOCOL_CSV,
					"12", error);
		}
		// create node for every field
		for (int i = 0; i < values.size(); i++)
		{
			Element elem = document
					.createElement(getStringOutOfObject(getFieldNames().get(i)));
			Node textElem = document.createTextNode(getStringOutOfObject(values
					.get(i)));
			record.appendChild(elem);
			elem.appendChild(textElem);
		}

	}

	/**
	 * Fetches the field tag names out of the description file
	 * 
	 * @throws XException
	 */
	private void writeFieldNamesOutOfDescriptionFile() throws XException
	{
		Document descriptionTree = getDescriptionTree();
		// get Records Node
		NodeList nodeList = descriptionTree.getElementsByTagName("Records");
		// get Record nodes
		nodeList = nodeList.item(0).getChildNodes(); // get record node
		nodeList = nodeList.item(0).getChildNodes(); // get field nodes
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			addFieldName(nodeList.item(i).getAttributes().getNamedItem("Name")
					.getNodeValue());
		}
	}

	/**
	 * Reads the header out of the description file and returns it als Vector.
	 * 
	 * @return The Header fields as Vector.
	 * @throws XException
	 */
	private Vector returnHeaderOfDescriptionFile() throws XException
	{
		Vector returnVector = new Vector();
		NodeList nodeList = getDescriptionTree().getElementsByTagName("Header");
		Node fileHeaderRoot = null;
		try
		{
			fileHeaderRoot = nodeList.item(0);
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL, Constants.PACKAGE_PROTOCOL_CSV,
					"9");
		}
		nodeList = fileHeaderRoot.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			try
			{
				returnVector.add(nodeList.item(i).getAttributes().getNamedItem(
						"Name").getNodeValue());
			}
			catch (DOMException e)
			{
				Vector error = new Vector();
				error.add(e.toString());
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_CSV, "11", error);
			}

		}
		return returnVector;
	}

	/**
	 * Returns the number of occurence of a pattern in a String.
	 * 
	 * @param pattern
	 *            The search pattern.
	 * @param baseString
	 *            The String to search in.
	 * @return The number of occurences.
	 */
	private int countOccurencesOfString(String pattern, String baseString)
	{
		int count = 0;
		int currentIndex = 0;
		while ((currentIndex = baseString.indexOf(pattern, currentIndex)) != -1)
		{
			currentIndex += pattern.length();
			count++;
		}
		return count;
	}

	/**
	 * Cleans the fields from all the unnecessary dust and tests if unallowed
	 * characters exist. Removes leading and trailing white spaces, removes
	 * quoting of fields and replaces double quoteChars with a single.
	 * 
	 * @param sourceVector
	 *            The Vector containing the dirty fields.
	 * @return A Vector with the cleaned entries.
	 * @throws XException
	 *             If unallowed characters exist in fields.
	 */
	private Vector cleanFields(Vector sourceVector) throws XException
	{
		Vector resultVector = new Vector();
		Enumeration e = sourceVector.elements();
		while (e.hasMoreElements())
		{
			String fieldString = getStringOutOfObject(e.nextElement());

			fieldString = fieldString.trim();

			// is this a quoted field
			if (fieldString.indexOf(getQuoteChar()) == 0)
			{
				// is there also a quote in the end of the string
				if (fieldString.lastIndexOf(getQuoteChar()) != fieldString
						.length()
						- getQuoteChar().length())
				{
					Vector error = new Vector();
					error.add(fieldString);
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_CSV, "14", error);
				}
				fieldString = fieldString.substring(getQuoteChar().length(),
						fieldString.length() - getQuoteChar().length());
				fieldString = fieldString.replaceAll(getQuoteChar()
						+ getQuoteChar(), getQuoteChar());
			}
			else
			{
				// are unallowed values in field even it has not been quoted?
				if (fieldString.indexOf(getLineSeparator()) >= 0
						|| fieldString.indexOf(getQuoteChar()) >= 0)
				{
					Vector error = new Vector();
					error.add(fieldString);
					throw new XException(Constants.LOCATION_INTERN,
							Constants.LAYER_PROTOCOL,
							Constants.PACKAGE_PROTOCOL_CSV, "6", error);
				}
			}
			resultVector.add(fieldString);
		}
		return resultVector;
	}

	/**
	 * Converts a passed object into a String.
	 * 
	 * @param content
	 *            The object to convert
	 * @return The converted String representation.
	 * @throws XException
	 *             If passed object is not a String.
	 */
	private String getStringOutOfObject(Object content) throws XException
	{
		if (!(content instanceof String))
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL, Constants.PACKAGE_PROTOCOL_CSV,
					"5");
		}
		return (String) content;
	}

	/**
	 * Searches for a tag with the name of the passed parameter.
	 * 
	 * @param tag
	 *            The tag name to search for
	 * @return true if tag name found
	 * @return false if tag name not found
	 */
	private boolean searchTagInDescriptionFile(String tag)
	{
		if (getDescriptionTree() == null
				|| (getDescriptionTree().getElementsByTagName(tag)).getLength() == 0)
		{
			return false;
		}
		return true;
	}
}
