package net.sf.xbus.technical.as400;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.strings.XStringSupport;
import net.sf.xbus.base.core.trace.Trace;
import net.sf.xbus.base.xbussystem.XBUSSystem;
import net.sf.xbus.base.xml.XMLHelper;
import net.sf.xbus.technical.ObjectSender;
import net.sf.xbus.technical.Sender;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.CharConverter;
import com.ibm.as400.access.ProgramParameter;

/**
 * The <code>AS400ProgramSender</code> calls a program on an AS400 and gives
 * back the result.
 * <p>
 */
public class AS400ProgramSender extends AS400FileBase
		implements
			Sender,
			ObjectSender
{
	public static final String CALLTYPE_SINGLE = "Single";
	public static final String CALLTYPE_MULTI = "Multi";

	private static final String TAG_INPUT = "Input";
	private static final String TAG_OUTPUT = "Output";
	private static final String TAG_FIELD = "Field";

	private static final String ATTRIBUTE_NAME = "Name";
	private static final String ATTRIBUTE_VALUE = "Value";
	private static final String ATTRIBUTE_FORMAT = "Format";
	private static final String ATTRIBUTE_LENGTH = "Length";

	private static final String FORMAT_ALPHA = "alpha";
	private static final String FORMAT_NUM = "num";
	private static final String FORMAT_DATE = "date";

	/**
	 * Interface reference in the xBus
	 */
	private XBUSSystem mDestination = null;

	/**
	 * Declaration of input parameters for program
	 */
	private List mInputFields = null;

	/**
	 * Declaration of output parameters
	 */
	private List mOutputFields = null;

	/**
	 * Length of output parameter string
	 */
	private int mOutputLength = 0;

	/**
	 * Input parameter values lined up in a string
	 */
	private StringBuffer mInputParams;

	/**
	 * Length of counter field for call type "multiple"
	 */
	private int mCountLength;

	/**
	 * Constructs an <code>AS400ProgramSender</code> for a given XBUSSystem
	 * 
	 * @param destination XBUSSystem which consists two informations:<br>
	 *            &nbsp;&nbsp;-The name of the system is used to identify the
	 *            system<br>
	 *            &nbsp;&nbsp;-Additionally an adress might be available.
	 */
	public AS400ProgramSender(XBUSSystem destination)
	{
		mDestination = destination;
	} // constructor AS400ProgramSender(XBUSSystem destination)

	/**
	 * Performs the program call on the AS400. Steps to do this:
	 * <ul>
	 * <li>prepare input parameter</li>
	 * <li>call program with input parameters</li>
	 * <li>retrieve result and fromat output parameters
	 * 
	 * @param function the path of the called program
	 * @param callData <code>org.w3c.Document</code> containing input
	 *            parameter structure and values and output parameter structure
	 * @return <code>org.w3c.Document</code> containing the output data of the
	 *         AS400 program in the specied structure
	 * @exception XException
	 */
	public Object execute(String function, Object callData) throws XException
	{
		// for result
		Document response = null;

		Configuration config = Configuration.getInstance();

		AS400Connection as400Connection = AS400Connection.getInstance(config
				.getValue(Constants.CHAPTER_SYSTEM, mDestination.getName(),
						"AS400Name"));

		int timeout = config.getValueAsIntOptional(Constants.CHAPTER_SYSTEM,
				mDestination.getName(), "ProgramTimeout") * 1000;
		if (timeout == 0)
		{
			timeout = Integer.MAX_VALUE;
		}

		/*
		 * If there is no real AS400 available, then testdata will be used.
		 */
		if (as400Connection.isTestSystem())
		{
			return XMLHelper.parseXML(getTestData(function), null, null);
		}

		// Conect to the iSeries
		AS400 as400 = as400Connection.getSystem();

		// Character converter
		try
		{
			mConverter = new CharConverter(as400.getCcsid());
		}
		catch (UnsupportedEncodingException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "0", e);
		}

		// Determine how to operate
		String callType = config.getValue("AS400Program", function, "CallType");

		if (!CALLTYPE_SINGLE.equals(callType)
				&& !CALLTYPE_MULTI.equals(callType))
		{
			List params = new Vector();
			params.add(callType);
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "39", params);
		} // if (!CALLTYPE_SINGLE.equals(callType) &&
			// !CALLTYPE_MULTI.equals(callType))

		if (CALLTYPE_SINGLE.equals(callType))
		{ // Only one calling step, only one record wil be returned as result
			// Read the name of the programm from the configuration
			String programName = config.getValue("AS400Program", function,
					"Program");

			// Structure for input an (still empty) output parameters
			ProgramParameter[] parameters = getParametersFromDocument(
					(Document) callData, callType, function, 1, 1);

			// the program call itself
			callAS400Program(as400, programName, parameters, timeout);

			// Get the output values.
			List outputList = makeOutputFields(as400, parameters, 1);
			// Place output values in specified XML structure.
			response = getResponseDocument(function, outputList);
		} // then (CALLTYPE_SINGLE.equals(callType))
		else if (CALLTYPE_MULTI.equals(callType))
		{ // Call multiple times to get entire list of output data
			// First call will determine amount of returned records.
			// Next phase is to call (another program) to get each time one
			// record.

			// Read the name of the output count determining program from the
			// configuration
			String programNameAmount = config.getValue("AS400Program",
					function, "ProgramAmount");

			// Read the e
			String programNameData = config.getValue("AS400Program", function,
					"ProgramData");

			/*
			 * First call of the AS400 program to get the amount of rows to be
			 * retrieved.
			 */
			// Structure for input an (still empty) output parameters
			ProgramParameter[] parameters = getParametersFromDocument(
					(Document) callData, callType, function, 1, 1);

			// The first program call itself
			callAS400Program(as400, programNameAmount, parameters, timeout);

			// Number of result records to retrieve in next calling phase
			int recordAmount = getParameterAsInt(as400, parameters, 1);

			/*
			 * Subsequent calls of the AS400 program to get all rows.
			 */
			// for the result records
			List outputVector = new Vector();
			// Loop over all result records - count already known
			for (int row = 0; row < recordAmount; row++)
			{
				// Structure for input an (still empty) output parameters
				parameters = getParametersFromDocument((Document) callData,
						callType, function, 2 + row, 1);

				// The program call itself
				callAS400Program(as400, programNameData, parameters, timeout);

				// Retrieve result record and add it to list
				outputVector.addAll(makeOutputFields(as400, parameters, 1));
			} // for (int row=0; row<recordAmount; row++)
			response = getResponseDocument(function, outputVector);
		} // if (CALLTYPE_MULTI.equals(callType))

		// Done with the server
		as400.disconnectService(AS400.COMMAND);

		return response;
	} // execute(String function, Object callData)

	/**
	 * Gets one output parameter from the parameterlist as a string.
	 */
	private String getParameterAsString(AS400 as400,
			ProgramParameter[] parameters, int position)
	{
		byte[] data = parameters[position].getOutputData();
		return mConverter.byteArrayToString(data);
	}

	/**
	 * Gets one output parameter from the parameterlist as an integer.
	 */
	private int getParameterAsInt(AS400 as400, ProgramParameter[] parameters,
			int position) throws XException
	{
		int response = 0;
		try
		{
			response = Integer.parseInt(getParameterAsString(as400, parameters,
					position));
		}
		catch (NumberFormatException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "0", e);
		}

		return response;
	}

	/**
	 * A list of program parameters is created out of the given
	 * <code>org.w3c.document</code>.
	 */
	private ProgramParameter[] getParametersFromDocument(Document doc,
			String callType, String function, int callNumber, int numOutputRows)
			throws XException
	{
		ProgramParameter[] parameters = null;

		parameters = new ProgramParameter[numOutputRows + 1];

		/*
		 * Filling the input parameter
		 */
		if (callNumber == 1)
		{
			NodeList input = doc.getElementsByTagName(TAG_INPUT);
			if (input.getLength() != 1)
			{
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_AS400, "41");
			}
			mInputFields = getFields(input.item(0).getChildNodes());

			mInputParams = new StringBuffer();
			Field field = null;
			for (Iterator it = mInputFields.iterator(); it.hasNext();)
			{
				field = (Field) it.next();

				if (FORMAT_ALPHA.equals(field.format))
				{
					if (field.length < field.value.length())
					{
						List params = new Vector();
						params.add(field.name);
						params.add(field.value);
						throw new XException(Constants.LOCATION_EXTERN,
								Constants.LAYER_TECHNICAL,
								Constants.PACKAGE_TECHNICAL_AS400, "42", params);
					} // if (field.length < field.value.length())
					mInputParams.append(field.value);
					// Fill in blanks if the value is to short.
					for (int j = 0; j < field.length - field.value.length(); j++)
					{
						mInputParams.append(" ");
					} // for (int j = 0; j < field.length -
						// field.value.length(); j++)
				} // if (FORMAT_ALPHA.equals(field.format))
				else if (FORMAT_NUM.equals(field.format))
				{
					// Test if the value is an integer
					try
					{
						Integer.parseInt(field.value);
					} // try
					catch (NumberFormatException e)
					{
						List params = new Vector();
						params.add(field.name);
						params.add(field.value);
						params.add(field.name);
						throw new XException(Constants.LOCATION_EXTERN,
								Constants.LAYER_TECHNICAL,
								Constants.PACKAGE_TECHNICAL_AS400, "43", params);
					} // catch
					if (field.length < field.value.length())
					{
						List params = new Vector();
						params.add(field.name);
						params.add(field.value);
						throw new XException(Constants.LOCATION_EXTERN,
								Constants.LAYER_TECHNICAL,
								Constants.PACKAGE_TECHNICAL_AS400, "42", params);
					} // if (field.length < field.value.length())
					// Fill in trailing zeros if the value is too short
					for (int j = 0; j < field.length - field.value.length(); j++)
					{
						mInputParams.append("0");
					} // for (int j = 0; j < field.length -
						// field.value.length()

					mInputParams.append(field.value);
				} // if (FORMAT_NUM.equals(field.format))
				else if (FORMAT_DATE.equals(field.format))
				{
					if (field.value.length() != Constants.AS400_CALL_DATE_FORMAT
							.length())
					{
						List params = new Vector();
						params.add(field.name);
						params.add(field.value);
						throw new XException(Constants.LOCATION_EXTERN,
								Constants.LAYER_TECHNICAL,
								Constants.PACKAGE_TECHNICAL_AS400, "45", params);
					}
					mInputParams.append(field.value);
				} // then (FORMAT_DATE.equals(field.format))
				else
				{
					List params = new Vector();
					params.add(field.name);
					params.add(field.format);
					throw new XException(Constants.LOCATION_EXTERN,
							Constants.LAYER_TECHNICAL,
							Constants.PACKAGE_TECHNICAL_AS400, "46", params);
				} // (FORMAT_DATE.equals(field.format))
			} // for (Iterator it = mInputFields.iterator(); it.hasNext();)
		} // if (callNumber==1)
		else if (callNumber == 2)
			mInputParams
					.append(XStringSupport.getNumberString(1, mCountLength));
		else
		{
			mInputParams.delete(mInputParams.lastIndexOf(XStringSupport
					.getNumberString(callNumber - 2, mCountLength)),
					mInputParams.length());
			mInputParams.append(XStringSupport.getNumberString(callNumber - 1,
					mCountLength));
		}

		parameters[0] = new ProgramParameter(mConverter
				.stringToByteArray(mInputParams.toString()));

		/*
		 * Filling the output parameters
		 */
		if (callNumber == 1)
		{
			NodeList output = doc.getElementsByTagName(TAG_OUTPUT);
			if (output.getLength() != 1)
			{
				throw new XException(Constants.LOCATION_EXTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_AS400, "47");
			}
			mOutputFields = getFields(output.item(0).getChildNodes());
		}
		if ((CALLTYPE_MULTI.equals(callType)) && callNumber == 1)
		{
			Configuration config = Configuration.getInstance();
			mCountLength = config.getValueAsInt("AS400Program", function,
					"CounterLength");
			parameters[1] = new ProgramParameter(mCountLength);
		}
		else
		{
			if (callNumber < 3)
			{
				Field field = null;
				for (Iterator it = mOutputFields.iterator(); it.hasNext();)
				{
					field = (Field) it.next();
					mOutputLength = mOutputLength + field.length;
				}
			}
			parameters[1] = new ProgramParameter(mOutputLength);
		}
		return parameters;
	}

	/**
	 * Makes a list of fields out of the output parameters of the given
	 * parameter list.
	 */
	private List makeOutputFields(AS400 as400, ProgramParameter[] parameters,
			int numRows) throws XException
	{
		/*
		 * Since the first parameter is an input parameter, the length of the
		 * parameters array must be larger than numRows
		 */
		if (parameters.length <= numRows)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "66");
		}

		Vector response = new Vector();
		Vector row = null;
		Field field = null;
		int position = 0;
		String data = null;
		for (int i = 1; i <= numRows; i++)
		{
			row = new Vector();
			data = getParameterAsString(as400, parameters, i);

			for (Iterator it = mOutputFields.iterator(); it.hasNext();)
			{
				field = (Field) ((Field) it.next()).clone();
				field.value = data.substring(position, position + field.length)
						.trim();
				if (FORMAT_NUM.equals(field.format))
				{
					try
					{
						field.value = String.valueOf(Integer
								.parseInt(field.value));
					}
					catch (NumberFormatException e)
					{
						throw new XException(Constants.LOCATION_EXTERN,
								Constants.LAYER_TECHNICAL,
								Constants.PACKAGE_TECHNICAL_AS400, "0", e);
					}
				}
				position = position + field.length;
				row.add(field);
			}
			response.add(row);
		}
		return response;
	}

	/**
	 * Creates an <code>org.w3c.Document</code> out of the given list of
	 * output fields
	 */
	private Document getResponseDocument(String function, List outputLists)
			throws XException
	{
		Document response = getTemplateAsDocument();

		Element newFunction = response
				.createElement(Constants.XBUSXMLMESSAGE_FUNCTION);
		newFunction.appendChild(response.createTextNode(function));
		NodeList children = response
				.getElementsByTagName(Constants.XBUSXMLMESSAGE_FUNCTION);
		if (children.getLength() > 0)
		{
			Node oldData = children.item(0);
			Node father = oldData.getParentNode();
			father.replaceChild(newFunction, oldData);
		}

		Element newData = response.createElement(Constants.XBUSXMLMESSAGE_DATA);

		List fields = null;
		Field field = null;
		Element outputNode = null;
		Element fieldNode = null;
		for (Iterator it1 = outputLists.iterator(); it1.hasNext();)
		{
			fields = (List) it1.next();
			outputNode = response.createElement(TAG_OUTPUT);
			newData.appendChild(outputNode);
			outputNode.appendChild(response.createTextNode("\n"));
			for (Iterator it2 = fields.iterator(); it2.hasNext();)
			{
				field = (Field) it2.next();
				fieldNode = response.createElement(TAG_FIELD);
				outputNode.appendChild(fieldNode);
				fieldNode.setAttribute(ATTRIBUTE_NAME, field.name);
				fieldNode.setAttribute(ATTRIBUTE_VALUE, field.value);
				fieldNode.setAttribute(ATTRIBUTE_FORMAT, field.format);
				if (!FORMAT_DATE.equals(field.format))
				{
					fieldNode.setAttribute(ATTRIBUTE_LENGTH, String
							.valueOf(field.length));
				}

				outputNode.appendChild(response.createTextNode("\n"));
			}
		}

		children = response.getElementsByTagName(Constants.XBUSXMLMESSAGE_DATA);
		if (children.getLength() > 0)
		{
			Node oldData = children.item(0);
			Node father = oldData.getParentNode();
			father.replaceChild(newData, oldData);
		}

		return response;
	}

	/**
	 * Makes a list of fields for either input or output parameters.
	 */
	private List getFields(NodeList nodes) throws XException
	{
		Vector fields = new Vector();

		Field field;
		Node node, name, value, length, format;
		NamedNodeMap attributes;
		for (int i = 0; i < nodes.getLength(); i++)
		{
			node = nodes.item(i);

			if ((TAG_FIELD.equals(node.getNodeName()))
					&& (node.getNodeType() == Node.ELEMENT_NODE))
			{
				field = new Field();
				attributes = node.getAttributes();

				name = attributes.getNamedItem(ATTRIBUTE_NAME);
				if (name != null)
				{
					field.name = name.getNodeValue();
				}
				else
				{
					throw new XException(Constants.LOCATION_EXTERN,
							Constants.LAYER_TECHNICAL,
							Constants.PACKAGE_TECHNICAL_AS400, "50");
				}

				value = attributes.getNamedItem(ATTRIBUTE_VALUE);
				if (value != null)
				{
					field.value = value.getNodeValue();
				}
				else
				{
					field.value = null;
				}

				format = attributes.getNamedItem(ATTRIBUTE_FORMAT);
				if (format != null)
				{
					field.format = format.getNodeValue();
				}
				else
				{
					throw new XException(Constants.LOCATION_EXTERN,
							Constants.LAYER_TECHNICAL,
							Constants.PACKAGE_TECHNICAL_AS400, "51");
				}

				if (FORMAT_DATE.equals(field.format))
				{
					field.length = Constants.AS400_CALL_DATE_FORMAT.length();
				}
				else
				{
					length = attributes.getNamedItem(ATTRIBUTE_LENGTH);
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
									Constants.LAYER_TECHNICAL,
									Constants.PACKAGE_TECHNICAL_AS400, "0", e);
						}
					}
					else
					{
						throw new XException(Constants.LOCATION_EXTERN,
								Constants.LAYER_TECHNICAL,
								Constants.PACKAGE_TECHNICAL_AS400, "53");
					}
				}
				fields.add(field);

			}
		}

		return fields;
	}

	/**
	 * Reads test data from a file, when there is no AS/400 available.
	 */
	private String getTestData(String function) throws XException
	{
		Trace.info("Entering AS400ProgramMessage.getTestData");

		String filename;
		if ("StockCheck".equals(function))
		{
			filename = Constants.XBUS_ETC + "AS400Test"
					+ Constants.FILE_SEPERATOR + "StockCheck.xml";
		}
		else if ("OrderCheck".equals(function))
		{
			filename = Constants.XBUS_ETC + "AS400Test"
					+ Constants.FILE_SEPERATOR + "OrderCheck.xml";
		}
		else
		{
			List params = new Vector();
			params.add(function);
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "54", params);
		}

		String line;
		StringBuffer testData = new StringBuffer();

		try
		{
			BufferedReader instream = new BufferedReader(new FileReader(
					new File(filename)));
			while ((line = instream.readLine()) != null)
			{
				testData.append(line);
				testData.append(Constants.LINE_SEPERATOR);
			}
			instream.close();
		}
		catch (IOException e)
		{
			throw new XException(Constants.LOCATION_EXTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_AS400, "0", e);
		}

		Trace.info("Leaving  AS400ProgramMessage.getTestData");
		return testData.toString();
	}

	/**
	 * Acts as a container for the information of one field of either an input
	 * or an output parameter.
	 */
	class Field implements Cloneable
	{
		public String name = null;
		public String value = null;
		public int length = 0;
		public String format = null;

		public Object clone()
		{
			Field field = new Field();
			field.name = this.name;
			field.value = this.value;
			field.length = this.length;
			field.format = this.format;

			return field;
		}
	}

	public String getType()
	{
		return Constants.TYPE_OBJECT;
	}

	/**
	 * Returns a XML string containing an empty AS400ProgramMessage
	 */
	static public String getTemplateAsString() throws XException
	{
		StringBuffer retString = new StringBuffer();

		retString.append("<?xml version=\"1.0\" encoding=\"");
		retString.append(Constants.getXMLEncoding());
		retString.append("\"?>");
		retString.append(Constants.LINE_SEPERATOR);
		retString.append("<!DOCTYPE XBUS_Document SYSTEM \"output.dtd\">");
		// The hard-coded refernce to "output.dtd" is not correct because the
		// interfaces were agreed to
		// use their own specific DTD's (one for input, one for output).
		// As long as the destination system does not check the validity of the
		// output xml file against
		// its DTD, the entry in the file is arbitrary. If it does, an error
		// will be issued becasue the
		// used DTD's contain the names of the fields.
		retString.append(Constants.LINE_SEPERATOR);
		retString.append("<").append(Constants.XBUSXMLMESSAGE_DOCUMENT).append(
				">");
		retString.append(Constants.LINE_SEPERATOR);
		retString.append("<").append(Constants.XBUSXMLMESSAGE_CALL).append(">");
		retString.append(Constants.LINE_SEPERATOR);
		retString.append("<").append(Constants.XBUSXMLMESSAGE_FUNCTION).append(
				" />");
		retString.append(Constants.LINE_SEPERATOR);
		retString.append("</").append(Constants.XBUSXMLMESSAGE_CALL)
				.append(">");
		retString.append(Constants.LINE_SEPERATOR);
		retString.append("<").append(Constants.XBUSXMLMESSAGE_DATA).append(
				" />");
		retString.append(Constants.LINE_SEPERATOR);
		retString.append("</").append(Constants.XBUSXMLMESSAGE_DOCUMENT)
				.append(">");

		return retString.toString();
	}

	/**
	 * Returns a W3C document containing an empty AS400ProgramMessage
	 */
	static public Document getTemplateAsDocument() throws XException
	{
		return XMLHelper.parseXML(getTemplateAsString(), null, null);
	}
}
