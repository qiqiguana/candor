/*
 * Created on 28.09.2004
 */
package net.sf.xbus.protocol.csv;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.protocol.TextMessage;
import net.sf.xbus.protocol.XMLMessage;
import net.sf.xbus.protocol.xml.XMLMessageAbstract;

import org.w3c.dom.Document;

/**
 * <code>CSVMessage</code> is helping the xBus transporting and converting
 * data structured in CSV format
 * <p>
 * As CSV is not a standardized, the authors have decided to base the
 * implementation on an existing definition (@see <a
 * href="http://www.edoceo.com/utilis/csv-file-format.php"> -&gt; CSV Definition</a>).
 * </p>
 * <p>
 * The entries are internally stored in a DOM tree. Thus <code>CSVMessage</code>
 * extends {@link net.sf.xbus.protocol.xml.XMLMessageAbstract} to use some of
 * its xml features.
 * </p>
 * 
 * @author Duewel, Köppl
 */
public class CSVMessage extends XMLMessageAbstract
		implements
			TextMessage,
			XMLMessage
{

	private XBUSSystem mResponseSystem;

	/**
	 * This constructor stores the <code>source</code>, creates an unique
	 * identifier for the message and initializes the requestTimestamp. It is
	 * used when constructing a new <code>CSVMessage</code> from the data of a
	 * receiver.
	 * 
	 * @param source the source of the data
	 */
	public CSVMessage(XBUSSystem source)
	{
		super(source);
		setShortname("CSVMessage");
	} // CSVMessage(XBUSSystem source)

	/**
	 * This constructor initializes the new <code>CSVMessage</code> with the
	 * given parameters. It is used when constructing a new
	 * <code>CSVMessage</code> by converting it from another
	 * {@link net.sf.xbus.protocol.Message Message}.
	 * 
	 * @param function the function to be executed by the destination system
	 * @param source the source of the data
	 * @param id the message id
	 */
	public CSVMessage(String function, XBUSSystem source, String id)
	{
		super(function, source, id);
		setShortname("CSVMessage");
	} // CSVMessage(String function, XBUSSystem source, String id)

	/**
	 * Returns the request text as String.
	 * 
	 * @param system the name of the process
	 * @return the request text in String format.
	 */
	public String getRequestText(XBUSSystem system) throws XException
	{
		Document doc = getRequestDocument(system);
		String result = null;
		if (doc != null)
			result = serializeRecords(doc, system);
		return result;
	} // getRequestText()

	public void setRequestText(String text, XBUSSystem source)
			throws XException
	{
		Document doc = parseRecords(text, getSource().getName());
		setRequestDocument(doc, source);
	}

	/**
	 * Returns the response text as String.
	 * 
	 * @return the response text as String
	 */
	public String getResponseText() throws XException
	{
		Document doc = getResponseDocument();
		String response = null;
		if (doc != null && mResponseSystem != null)
			response = serializeRecords(doc, mResponseSystem);
		return response;
	} // getResponseText()

	/**
	 * Sets the response data
	 * 
	 * @param text the new response text as String
	 * @param destination the destination process
	 * @throws XException
	 */
	public void setResponseText(String text, XBUSSystem destination)
			throws XException
	{
		setReturncode(Constants.RC_OK);
		setErrorcode(0);
		setErrortext(null);
		if (text == null)
			setResponseDocument(null, destination);
		else
			setResponseDocument(parseRecords(text, destination.getName()),
					destination);
	} // setResponseText(String text)

	/**
	 * Sets the response data by passing a DOM tree
	 * 
	 * @param doc the DOM tree
	 * @param destination the destination process
	 * @throws XException
	 */
	public void setResponseDocument(Document doc, XBUSSystem destination)
			throws XException
	{
		mResponseSystem = destination;

		super.setResponseDocument(doc, destination);
	} // setResponseDocument(Document doc, XBUSSystem destination)

	/**
	 * Parses the Records from CSV format into the internal DOM tree
	 * 
	 * @param text the text which has to be parsed
	 * @return the parsed records as DOM tree
	 */
	private Document parseRecords(String text, String systemName)
			throws XException
	{
		CSVParser parser = CSVParser.getInstance(systemName);
		return parser.parse(text, systemName);
	} // parseRecords(String text)

	/**
	 * Serializes the Records from DOM format into CSV format
	 * 
	 * @param doc the DOM tree which is serialized
	 * @param system the process name
	 * @return the serialized data in CSV format
	 */
	private String serializeRecords(Document doc, XBUSSystem system)
			throws XException
	{
		String records = null;
		// for the result
		if (doc != null)
		{ // There is a document to serialize.
			String sourceType;

			// the interface file type
			if (system != null)
				sourceType = system.getName();
			else
				sourceType = doc.getDocumentElement().getNodeName();

			// Get a serializer object
			CSVSerializer csvSerializer = CSVSerializer.getInstance(sourceType);
			// Serializer is initialized, so let's go.
			records = csvSerializer.serialize(doc);
		} // if (doc != null)
		return records;
	} // serializeRecords(Document doc, XBUSSystem system)

	/**
	 * Empty method.
	 */
	protected void synchronizeRequestFields(XBUSSystem system)
	{
		/*
		 * do nothing
		 */
	} // synchronizeRequestFields()

	/**
	 * Empty method.
	 */
	protected void synchronizeResponseFields(XBUSSystem system)
	{
		setReturncode(Constants.RC_OK);
	} // synchronizeResponseFields()

} // CSVMessage
