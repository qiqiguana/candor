package net.sf.xbus.protocol.records;

import javax.xml.parsers.DocumentBuilder;

import net.sf.xbus.base.bytearraylist.ByteArrayList;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.protocol.ObjectMessage;
import net.sf.xbus.protocol.TextMessage;
import net.sf.xbus.protocol.XMLMessage;
import net.sf.xbus.protocol.xml.XMLMessageAbstract;

import org.w3c.dom.Document;

/**
 * <code>RecordTypeMessage</code> is used in the xBus to transport and convert
 * file contents structured by record types.
 * <p>
 * The structure is described by a xml file - in particular the contained record
 * types, their order within the string and how to identify them during parsing.
 * Such describing files are of document type <code>InterfaceSpec</code>
 * declared in "InterfaceSpec.dtd".
 * </p>
 * <p>
 * The records are internally stored in a DOM tree. Thus
 * <code>RecordTypeMessage</code> extends
 * {@link net.sf.xbus.protocol.xml.XMLMessageAbstract}to use some of xml its
 * features.
 * </p>
 * 
 * @author Stephan Düwel
 */
public class RecordTypeMessage extends XMLMessageAbstract
		implements
			TextMessage,
			ObjectMessage,
			XMLMessage
{

	private XBUSSystem mResponseSystem;

	/**
	 * This constructor stores the <code>source</code>, creates an unique
	 * identifier for the message and initializes the requestTimestamp. It is
	 * used when constructing a new <code>RecordTypeMessage</code> from the
	 * data of a receiver.
	 * 
	 * @param source the source of the data
	 */
	public RecordTypeMessage(XBUSSystem source)
	{
		super(source);
		setShortname("RecordTypeMessage");
	} // RecordTypeMessage(XBUSSystem source)

	/**
	 * This constructor initializes the new <code>RecordTypeMessage</code>
	 * with the given parameters. It is used when constructing a new
	 * <code>RecordTypeMessage</code> by converting it from another
	 * {@link net.sf.xbus.protocol.Message Message}.
	 * 
	 * @param function the function to be executed by the destination system
	 * @param source the source of the data
	 * @param id the message id
	 */
	public RecordTypeMessage(String function, XBUSSystem source, String id)
	{
		super(function, source, id);
		setShortname("RecordTypeMessage");
	} // RecordTypeMessage(String function, XBUSSystem source, String id)

	/**
	 * <code>setRequestText</code> sets the text of the incoming message.
	 * After parsing due to the interface file and record type specification,
	 * the information is stored in a
	 * {@link org.w3c.dom.Document org.w3c.dom.Document}. This operation is
	 * used to work with strings.
	 * 
	 * @see #setRequestObject(Object, net.sf.xbus.base.xbussystem.XBUSSystem)
	 * @param text the data string
	 * @throws XException in case that the specifed string cannot be parsed
	 */
	public void setRequestText(String text, XBUSSystem source)
			throws XException
	{
		if (text == null)
			setRequestDocument(null, source);
		else
			setRequestDocument(parseRecordsFromString(text, source), source);
	} // setRequestText(String text)

	/**
	 * Gets the text of the incoming message. It is the result of the
	 * serialization of a {@link org.w3c.dom.Document org.w3c.dom.Document}due
	 * to the interface file and record type specification. This operation is
	 * used to work with strings.
	 * 
	 * @see #getRequestObject(XBUSSystem)
	 * @param system reference to the system for which the message is meant -
	 *            used in finding specifications for serialising the message
	 *            content
	 * @return the message text as string
	 * @throws XException in case that the content cannot be serialised
	 */
	public String getRequestText(XBUSSystem system) throws XException
	{
		Document doc = getRequestDocument(system);
		String result = null;
		if (doc != null)
			result = serializeRecordsToString(doc, system);
		return result;
	} // getRequestText()

	/**
	 * <code>setResponseText</code> sets the text of the incoming message.
	 * After parsing due to the interface file and record type specification,
	 * the information is stored in a
	 * {@link org.w3c.dom.Document org.w3c.dom.Document}. This operation is
	 * used to work with strings.
	 * 
	 * @see #setResponseText(String, XBUSSystem)
	 * @param text the data string
	 * @throws XException in case that the specifed string cannot be parsed
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
			setResponseDocument(parseRecordsFromString(text, destination),
					destination);
	} // setResponseText(String text)

	/**
	 * Gets the text of the response message. It is the result of the
	 * serialization of a {@link org.w3c.dom.Document org.w3c.dom.Document}due
	 * to the interface file and record type specification. This operation is
	 * used to work with strings.
	 * 
	 * @see #getResponseText()
	 * @return the message text as string
	 * @throws XException in case that the content cannot be serialised
	 */
	public String getResponseText() throws XException
	{
		Document doc = getResponseDocument();
		String response = null;
		if (doc != null && mResponseSystem != null)
			response = serializeRecordsToString(doc, mResponseSystem);
		return response;
	} // getResponseText()

	/**
	 * <code>setRequestObject</code> sets the text of the incoming message.
	 * After parsing due to the interface file and record type specification,
	 * the information is stored in a
	 * {@link org.w3c.dom.Document org.w3c.dom.Document}. This operation is
	 * used to work with byte array lists.
	 * 
	 * @see #setRequestObject(Object, net.sf.xbus.base.xbussystem.XBUSSystem)
	 * @param obj the data in form of a
	 *            {@link net.sf.xbus.base.bytearraylist.ByteArrayList}
	 * @throws XException in case that the specifed string cannot be parsed
	 */
	public void setRequestObject(Object obj, XBUSSystem source)
			throws XException
	{
		if (obj == null)
			setRequestDocument(null, source);
		else
		{
			try
			// for casting to XException
			{
				ByteArrayList bal = (ByteArrayList) obj;
				if (bal.length() == 0)
					setRequestDocument(null, source);
				else
					setRequestDocument(parseRecordsFromByteArrayList(bal,
							source), source);
			} // try
			catch (ClassCastException e)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
			} // catch
		} // else (obj==null)
	} // setRequestObject(Object obj)

	/**
	 * Gets the text of the incoming message as a
	 * {@link net.sf.xbus.base.bytearraylist.ByteArrayList}. It is the result
	 * of the serialization of a
	 * {@link org.w3c.dom.Document org.w3c.dom.Document}due to the interface
	 * file and record type specification. This operation is used to work with
	 * byte array lists.
	 * 
	 * @see #getRequestText(XBUSSystem)
	 * @param system reference to the system for which the message is meant -
	 *            used in finding specifications for serialising the message
	 *            content
	 * @return the message text as
	 *         {@link net.sf.xbus.base.bytearraylist.ByteArrayList}
	 * @throws XException in case that the content cannot be serialised
	 */
	public Object getRequestObject(XBUSSystem system) throws XException
	{
		Document doc = getRequestDocument(system);
		Object result = null;
		if (doc != null)
			result = serializeRecordsToByteArrayList(doc, system);
		return result;
	} // getRequestObject()

	/**
	 * <code>setResponseObject</code> sets the text of the incoming message.
	 * After parsing due to the interface file and record type specification,
	 * the information is stored in a
	 * {@link org.w3c.dom.Document org.w3c.dom.Document}. This operation is
	 * used to work with byte array lists.
	 * 
	 * @see #setResponseText(String, XBUSSystem)
	 * @param obj the data in form of a
	 *            {@link net.sf.xbus.base.bytearraylist.ByteArrayList}
	 * @throws XException in case that the specifed string cannot be parsed
	 */
	public void setResponseObject(Object obj, XBUSSystem destination)
			throws XException
	{
		setReturncode(Constants.RC_OK);
		setErrorcode(0);
		setErrortext(null);
		if (obj == null)
			setResponseDocument(null, destination);
		else
		{
			try
			// for casting to XException
			{
				ByteArrayList bal = (ByteArrayList) obj;
				if (bal.length() == 0)
					setRequestDocument(null, destination);
				else
					setResponseDocument(parseRecordsFromByteArrayList(bal,
							destination), destination);
			} // try
			catch (ClassCastException e)
			{
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_PROTOCOL,
						Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
			} // catch
		} // else (obj==null)
	} // setResponseObject(Object obj)

	/**
	 * Gets the text of the response message as a
	 * {@link net.sf.xbus.base.bytearraylist.ByteArrayList}. It is the result
	 * of the serialization of a
	 * {@link org.w3c.dom.Document org.w3c.dom.Document}due to the interface
	 * file and record type specification. This operation is used to work with
	 * byte array lists.
	 * 
	 * @see #getResponseText()
	 * @return the message text as
	 *         {@link net.sf.xbus.base.bytearraylist.ByteArrayList}
	 * @throws XException in case that the content cannot be serialised
	 */
	public Object getResponseObject() throws XException
	{
		Document doc = getResponseDocument();
		Object response = null;
		if (doc != null && mResponseSystem != null)
			response = serializeRecordsToByteArrayList(doc, mResponseSystem);
		return response;
	} // getResponseObject()

	public void setResponseDocument(Document doc, XBUSSystem destination)
			throws XException
	{
		mResponseSystem = destination;

		super.setResponseDocument(doc, destination);
	}

	/**
	 * Gets the text of the message as xml document. It is the result of the
	 * standard serialization of a
	 * {@link org.w3c.dom.Document org.w3c.dom.Document}.
	 * 
	 * @param system reference to the system for which the message is meant -
	 *            used in finding specifications for serialising the message
	 *            content
	 * @return the serialised document in XML format
	 * @throws XException in case that the content cannot be serialised
	 */
	public String getRequestTextAsXML(XBUSSystem system) throws XException
	{
		return super.getRequestText(system);
	} // getRequestTextAsXML()

	/**
	 * Empty method.
	 */
	protected void synchronizeRequestFields(XBUSSystem system)
	{} // synchronizeRequestFields()

	/**
	 * Empty method.
	 */
	protected void synchronizeResponseFields(XBUSSystem system)
	{
		setReturncode(Constants.RC_OK);
	} // synchronizeResponseFields()

	/**
	 * Parses the given string due to the interface file and record type
	 * specification. This operation is used to work with strings.
	 * 
	 * @param interfaceContent the string to parse
	 * @return a DOM tree, <code>null</code> if <code>interfaceContent</code>
	 *         is empty
	 * @throws XException in case of a parser error
	 */
	private Document parseRecordsFromString(String interfaceContent,
			XBUSSystem system) throws XException
	{
		Document retDocument = null;
		// for the result
		if (interfaceContent != null)
		{ // There is something to parse
			retDocument = parseRecords(interfaceContent,
					Constants.IFCONTENTCLASS_STRING, system);
		} // if ((interfaceContent != null) && (interfaceContent.length() >
			// 0))
		return retDocument;
	} // parseRecordsFromString(String interfaceContent)

	/**
	 * <code>parseRecordsFromByteArrayList</code> parses the given bytearray
	 * due to the interface file and record type specification.
	 * 
	 * @param interfaceContent the <code>ByteArrayList</code> to parse
	 * @return a DOM tree, <code>null</code> if <code>interfaceContent</code>
	 *         is empty
	 * @throws XException in case of a parser error
	 */
	private Document parseRecordsFromByteArrayList(
			ByteArrayList interfaceContent, XBUSSystem system)
			throws XException
	{
		Document retDocument = null;
		// for the result
		if (interfaceContent != null)
		{ // There is something to parse
			retDocument = parseRecords(interfaceContent,
					Constants.IFCONTENTCLASS_BYTEARRAYLIST, system);
		} // if ((interfaceContent != null) && (interfaceContent.length() >
			// 0))
		return retDocument;
	} // parseRecordsFromByteArrayList(ByteArrayList interfaceContent)

	/**
	 * <code>parseRecords</code> is the parsing kernel common for working
	 * modes "string" and "byte array list".
	 * 
	 * @param interfaceContent the data to parse
	 * @param interfaceContentClass indicator for working mode -
	 *            {@link java.lang.String}or
	 *            {@link net.sf.xbus.base.bytearraylist.ByteArrayList}
	 * @return a DOM tree, <code>null</code> if <code>interfaceContent</code>
	 *         is empty
	 * @throws XException in case of a parser error
	 */
	private Document parseRecords(Object interfaceContent,
			int interfaceContentClass, XBUSSystem system) throws XException
	{
		Document retDocument;
		DocumentBuilder builder = getDocumentBuilder(system);
		retDocument = builder.newDocument();
		// for the parsed content
		RecordTypeParser rtParser = RecordTypeParser.getInstance(getSource()
				.getName(), interfaceContentClass);
		// Get a parser object

		if (rtParser.getSourceType() == null
				|| !rtParser.getSourceType().equals(getSource().getName())
				|| rtParser.getInterfaceContentClass() != interfaceContentClass)
		{ // Parser is not yet initialised for the correct interface file
			// type
			rtParser.initialize(getSource().getName(), interfaceContentClass,
					builder);
		} // if (rtParser.getSourceType()==null ||
		// !rtParser.getSourceType().equals(getSource().getName()))

		// Now the parser is initialised, let's go.
		rtParser.parse(interfaceContent, retDocument);
		return retDocument;
	} // parseRecords(String interfaceContent)

	/**
	 * <code>serializeRecordsToString</code> translates the dom tree for the
	 * parsed records to a string conforming to the interface file and record
	 * type specification. This operation is used to work with strings.
	 * 
	 * @param doc the DOM tree
	 * @param system reference to the system for which the message is meant -
	 *            used in finding specifications for serialising the message
	 *            content
	 * @return the serialised data as string, <code>null</code> for an empty
	 *         <code>doc</code>
	 */
	private String serializeRecordsToString(Document doc, XBUSSystem system)
			throws XException
	{
		String records = null;
		// for the result
		try
		{
			records = (String) serializeRecords(doc, system,
					Constants.IFCONTENTCLASS_STRING);
		} // try
		catch (ClassCastException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		} // catch
		return records;
	} // serializeRecordsToString(Document doc)

	/**
	 * <code>serializeRecordsToByteArrayList</code> translates the dom tree
	 * for the parsed records to a byte array list conforming to the interface
	 * file and record type specification. This operation is used to work with
	 * byte array lists.
	 * 
	 * @param doc the DOM tree
	 * @param system reference to the system for which the message is meant -
	 *            used in finding specifications for serialising the message
	 *            content
	 * @return the serialised data as
	 *         {@link net.sf.xbus.base.bytearraylist.ByteArrayList},
	 *         <code>null</code> for an empty <code>doc</code>
	 */
	private ByteArrayList serializeRecordsToByteArrayList(Document doc,
			XBUSSystem system) throws XException
	{
		ByteArrayList records = null;
		try
		{
			records = (ByteArrayList) serializeRecords(doc, system,
					Constants.IFCONTENTCLASS_BYTEARRAYLIST);
		} // try
		catch (ClassCastException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_PROTOCOL,
					Constants.PACKAGE_PROTOCOL_RECORDS, "0", e);
		} // catch
		return records;
	} // serializeRecordsToByteArrayList(Document doc)

	/**
	 * <code>serializeRecords</code> is the serializing kernel common for
	 * working modes "string" and "byte array list".
	 * 
	 * @param doc the DOM tree to serialize
	 * @param system reference to the system for which the message is meant -
	 *            used in finding specifications for serialising the message
	 *            content
	 * @param interfaceContentClass indicator for working mode -
	 *            {@link java.lang.String}or
	 *            {@link net.sf.xbus.base.bytearraylist.ByteArrayList}
	 * @return a DOM tree, <code>null</code> if <code>interfaceContent</code>
	 *         is empty
	 * @throws XException in case of a parser error
	 */
	private Object serializeRecords(Document doc, XBUSSystem system,
			int interfaceContentClass) throws XException
	{
		Object records = null;
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
			RecordTypeSerializer rtSerializer = RecordTypeSerializer
					.getInstance(sourceType, interfaceContentClass);
			if (rtSerializer.getSourceType() == null
					|| !rtSerializer.getSourceType().equals(sourceType)
					|| rtSerializer.getInterfaceContentClass() != interfaceContentClass)
			{ // Serializer is not yet initialised for the correct interface
				// file type
				DocumentBuilder builder = getDocumentBuilder(system);
				rtSerializer.initialize(sourceType, interfaceContentClass,
						builder);
			} // if (rtSerializer.getSourceType()==null ||
			// !rtSerializer.getSourceType().equals(contentType))
			// Serializer is initialized, so let's go.
			records = rtSerializer.serialize(doc);
		} // if (doc != null)

		return records;
	} // serializeRecords(Document doc, int interfaceContentClass)

} // RecordTypeMessage
