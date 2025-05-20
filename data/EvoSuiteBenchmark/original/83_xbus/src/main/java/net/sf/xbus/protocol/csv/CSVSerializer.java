package net.sf.xbus.protocol.csv;

import java.util.Hashtable;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.xml.XDomSupport;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <code>CSVSerializer</code> serves for serializing records stored in a DOM
 * tree according to a CSV type message
 * <p>
 * The DOM tree structure is described in the
 * {@link net.sf.xbus.protocol.csv.CSVParser CSVParser} documentation.
 * </p>
 * <ul>
 * <li>The serialization result is a String.</li>
 * <li>Every record is serialized into one line</li>
 * <li>The fields are separated by the FieldSeparator which is set in the
 * configuration. (default value in xbus.conf, overwritting possible for every
 * process</li>
 * <li>The QuoteChar is surrounding fields which contain the QuoteChar
 * character, a line break, a FieldSeparator or spaces. Which character is used
 * is indicated in xbus.conf of in the process configuration. </li>
 * <li>The HasHeader configuration key indicates if an header should be written
 * into the CSV file</li>
 * <li>If AlwaysQuote is set true in the configuration, all fields are
 * surrounded by the QuoteChar</li>
 * </ul>
 * 
 * @author Stephan Düwel, Wolfgang Köppl
 */
public class CSVSerializer
{

	private static Hashtable instances = new Hashtable();
	private static final Object classLock = CSVSerializer.class;

	private String fieldSeparator;
	private String quoteChar;
	private String quotePair;
	private boolean alwaysQuote;
	private boolean hasHeader;
	private String lineSeparator;

	/**
	 * @param system the name of the process
	 * @throws XException
	 */
	public CSVSerializer(String system) throws XException
	{
		readConfig(system);
	}

	/**
	 * @param sourceType the name of the process
	 * @return an instance of the CSVSerializer class
	 */
	protected static CSVSerializer getInstance(String sourceType)
			throws XException
	{
		synchronized (classLock)
		{
			if (sourceType == null || sourceType.length() == 0)
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_CSV, "1");

			CSVSerializer instance = (CSVSerializer) instances.get(Thread
					.currentThread().getName()
					+ sourceType);
			if (instance == null)
			{ // no suitable instance available yet
				instance = new CSVSerializer(sourceType);
				instances.put(Thread.currentThread().getName() + sourceType,
						instance);
			} // if (instance==null)
			return instance;
		}
	}

	/**
	 * @return Returns the lineSeparator.
	 */
	public String getLineSeparator()
	{
		return lineSeparator;
	}

	/**
	 * Serializes a DOM tree into a CSV message.
	 * 
	 * @param doc The information that is parsed into CSV format. Has to be a
	 *            DOM tree.
	 * @return The String of the serialized document.
	 */
	protected String serialize(Document doc) throws XException
	{
		String result = "";
		// delete all the gaps(spaces) between the tags
		try
		{
			XDomSupport.deleteWhitespaceTextInElementNodesAndComments(doc);
		}
		catch (Exception e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL, Constants.PACKAGE_PROTOCOL_CSV,
					"3");
		}

		// do we want to write out a header?
		if (hasHeader)
		{
			result = returnSerializedHeader(doc);
		}// if(hasHeder)

		Node recordsNode = doc.getElementsByTagName("Records").item(0);
		NodeList recordsList = recordsNode.getChildNodes();

		// go through all records and process every single of them
		for (int i = 0; i < recordsList.getLength(); i++)
		{
			result += serializeOneLine(recordsList.item(i).getChildNodes());
		}
		return result;
	}

	/**
	 * Returns the serialized header line. The headers for the fields are the
	 * tag names inside the <Header>-Tags
	 * 
	 * @param doc The document containing the header information.
	 * @return The header String
	 * @throws XException
	 */
	private String returnSerializedHeader(Document doc) throws XException
	{
		String header = null;
		Node headerNode = doc.getElementsByTagName("Header").item(0);
		if (headerNode != null)
		{
			NodeList headerElements = headerNode.getChildNodes();
			header = serializeOneLine(headerElements);
		}
		else
		{
			Node firstRecord = doc.getElementsByTagName("Records").item(0)
					.getFirstChild();
			NodeList fields = firstRecord.getChildNodes();
			StringBuffer line = new StringBuffer();
			for (int i = 0; i < fields.getLength(); i++)
			{
				String fieldName = fields.item(i).getNodeName();
				// doubleQuote all quoteChars
				if (fieldName.indexOf(quoteChar) >= 0)
					fieldName = fieldName.replaceAll(quoteChar, quotePair);

				// do we always quote or have quote, fieldSeparator, lineBreak
				// or
				// space in the field
				if (alwaysQuote || fieldName.indexOf(quoteChar) >= 0
						|| fieldName.indexOf(lineSeparator) >= 0
						|| fieldName.indexOf(fieldSeparator) >= 0)
				{
					line.append(quoteChar);
					line.append(fieldName);
					line.append(quoteChar);
				} // then (alwaysQuote || fieldName.indexOf(quoteChar) >= 0 ||
					// fieldName.indexOf(lineSeparator) >= 0 ||
					// fieldName.indexOf(fieldSeparator) >= 0)
				else
					line.append(fieldName);

				// last field?
				if (i < fields.getLength() - 1)
					line.append(fieldSeparator);
				else
					line.append(lineSeparator);
			} // for (int i=0; i<fields.getLength(); i++)
			if (line.length() > 0)
				header = line.toString();
		} // else (headerNode != null)
		if (header == null)
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL, Constants.PACKAGE_PROTOCOL_CSV,
					"2");
		return header;
	} // returnSerializedHeader(Document doc)

	/**
	 * Parses one set of fields (one record or one header) into a CSV entry
	 * 
	 * @param lineElements The elements of the entry. Every node in the list is
	 *            one field in the entry.
	 * @return The String of the serialized entry in CSV format.
	 */
	private String serializeOneLine(NodeList lineElements)
	{
		StringBuffer text = new StringBuffer();
		for (int i = 0; i < lineElements.getLength(); i++)
		{
			Node currentNode = lineElements.item(i);
			StringBuffer currentText = new StringBuffer();
			NodeList textNodes = currentNode.getChildNodes();
			for (int j = 0; j < textNodes.getLength(); j++)
			{
				currentText.append(textNodes.item(j).getNodeValue());
			}

			String fieldValue = currentText.toString();
			// doubleQuote all quoteChars
			if (fieldValue.indexOf(quoteChar) >= 0)
			{
				fieldValue = fieldValue.replaceAll(quoteChar, quotePair);
			}

			// do we always quote or have quote, fieldSeparator, lineBreak or
			// space in the field
			if (alwaysQuote || fieldValue.indexOf(quoteChar) >= 0
					|| fieldValue.indexOf(lineSeparator) >= 0
					|| fieldValue.indexOf(fieldSeparator) >= 0
			/* || currentText.indexOf(" ") >= 0 */)
			{
				text.append(quoteChar);
				text.append(fieldValue);
				text.append(quoteChar);
			}
			else
			{
				text.append(currentText);
			}
			// last field?
			if (i < lineElements.getLength() - 1)
			{
				text.append(fieldSeparator);
			}
			else
			{
				text.append(lineSeparator);
			}
		}
		return text.toString();
	}

	/**
	 * Reads out the configuration entries for the Serialization. Defaults
	 * values are set as MessageDefaults_CSVMessage_Key in xbus.conf. These can
	 * be overwritten for every process.
	 * 
	 * @param sectionName The name of the process.
	 * @throws XException
	 */
	private void readConfig(String sectionName) throws XException
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

		if (quoteChar == null || fieldSeparator == null
				|| quoteChar.equals(fieldSeparator))
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL, Constants.PACKAGE_PROTOCOL_CSV,
					"15");

		quotePair = quoteChar + quoteChar;

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

		// get configuration if all fields should be quoted
		if ((tempVariable = config.getValueOptional(Constants.CHAPTER_SYSTEM,
				sectionName, Constants.KEY_CSV_ALWAYS_QUOTE)) == null)
		{
			alwaysQuote = xBusConfig.getValueAsBoolean("MessageDefaults",
					"CSVMessage", Constants.KEY_CSV_ALWAYS_QUOTE);
		}
		else if (tempVariable.equalsIgnoreCase(Constants.CONFIGURATION_TRUE))
		{
			alwaysQuote = true;
		}
		else
		{
			alwaysQuote = false;
		}

		// set line separator
		lineSeparator = Constants.LINE_SEPERATOR;
		String platform = config.getValueOptional(Constants.CHAPTER_SYSTEM,
				sectionName, "Platform");
		if (platform != null)
		{
			lineSeparator = Constants.getLineSeperator(platform);
		}
	}

}
