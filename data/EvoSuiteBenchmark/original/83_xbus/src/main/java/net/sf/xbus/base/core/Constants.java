package net.sf.xbus.base.core;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import net.sf.xbus.base.core.config.Configuration;

/**
 * Definition of various constants, used by all classes.
 */
public class Constants
{

	/**
	 * Reads the encoding for XML data in the configuration and returns it. When
	 * no encoding is found, the value UTF-8 is returned.
	 * 
	 * @return encoding for XML data
	 * @throws XException
	 *             when something goes wrong
	 */
	public static final String getXMLEncoding() throws XException
	{
		String encoding = Configuration.getInstance().getValueOptional(
				CHAPTER_BASE, "XML", "Encoding");
		if (encoding != null)
		{
			return encoding;
		}
		else
		{
			encoding = Configuration.getInstance().getValueOptional(
					CHAPTER_BASE, "Encoding", "XML");
			if (encoding != null)
			{
				return encoding;
			}
			else
			{
				return "UTF-8";
			}
		}
	}

	public static final String RC_OK = "RC_OK";

	public static final String RC_NOK = "RC_NOK";

	public static final String CONFIGURATION_TRUE = "true";

	public static final String CONFIGURATION_FALSE = "false";

	public static final String TYPE_TEXT = "Text";

	public static final String TYPE_OBJECT = "Object";

	public static final String TYPE_BINARY = "Binary";

	public static final String TYPE_XML = "XML";

	public static final int IFCONTENTCLASS_BYTEARRAYLIST = 0;

	public static final int IFCONTENTCLASS_STRING = 1;

	public static final String XBUSXMLMESSAGE_DOCUMENT = "XBUS_Document";

	public static final String XBUSXMLMESSAGE_CALL = "XBUS_Call";

	public static final String XBUSXMLMESSAGE_DATA = "XBUS_Data";

	public static final String XBUSXMLMESSAGE_ID = "Id";

	public static final String XBUSXMLMESSAGE_FUNCTION = "Function";

	public static final String XBUSXMLMESSAGE_SOURCE = "Source";

	public static final String XBUSXMLMESSAGE_ADDRESS = "Address";

	public static final String XBUSXMLMESSAGE_TIMESTAMP = "Timestamp";

	public static final String XBUSXMLMESSAGE_RETURNCODE = "Returncode";

	public static final String XBUSXMLMESSAGE_ERRORCODE = "Errorcode";

	public static final String XBUSXMLMESSAGE_ERRORTEXT = "Errortext";

	public static final String LINE_SEPERATOR = System
			.getProperty("line.separator");

	public static final byte NEWLINE = 10;

	public static final byte CARRIAGE_RETURN = 13;

	/**
	 * <code>getLineSeperator</code> returns the line break representation for
	 * a specified platform.
	 * 
	 * @param platform
	 *            the platform, for which the line break is needed
	 */
	public static String getLineSeperator(String platform) throws XException
	{
		String result = null;
		if (platform.equals("Unix"))
			result = "\n";
		else if (platform.equals("Windows"))
			result = "\r\n";
		else
		{
			List params = new Vector();
			params.add(platform);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_COREBASE,
					Constants.PACKAGE_COREBASE_COREBASE, "20", params);
		} // else
		return result;
	} // getLineSeperator(String platform)

	public static final String FILE_SEPERATOR = File.separator;

	public static final String XBUS_HOME = System.getProperty("xbus.home");

	public static final String XBUS_ETC = XBUS_HOME + FILE_SEPERATOR + "etc"
			+ FILE_SEPERATOR;

	public static final String XBUS_LOG = XBUS_HOME + FILE_SEPERATOR + "log"
			+ FILE_SEPERATOR;

	public static final String XBUS_PLUGIN_ETC = XBUS_HOME + FILE_SEPERATOR
			+ "plugin" + FILE_SEPERATOR + "etc" + FILE_SEPERATOR;

	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss,SSS";

	public static final String FILE_NAME_DATE_FORMAT = ".yyyyMMddHHmmssSSS";

	public static final String AS400_DATE_FORMAT = "yyyyMMdd";

	// must be up to 10 characters long
	public static final String AS400_CALL_DATE_FORMAT = "ddMMyyyy";

	private static SimpleDateFormat dateFormat = null;

	/**
	 * Constructs and gets a SimpleDateFormat using the default pattern using
	 * the given {@link #DATE_FORMAT}
	 * 
	 * @return SimpleDateFormat
	 */
	public static final SimpleDateFormat getDateFormat()
	{
		if (dateFormat == null)
		{
			dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
		}
		return dateFormat;
	}

	/**
	 * Constructs a SimpleDateFormat using the default pattern using the given
	 * {@link #FILE_NAME_DATE_FORMAT} and gets it into a String
	 * 
	 * @return act.Date as String
	 */
	public static final String getDateAsString()
	{
		return new SimpleDateFormat(Constants.FILE_NAME_DATE_FORMAT)
				.format(new Date());
	}

	/**
	 * Constructs a SimpleDateFormat using the default pattern using the given
	 * {@link #AS400_DATE_FORMAT} and gets it into a String
	 * 
	 * @return act.Date as String must be up to 10 characters long
	 */
	public static final String getAS400DateFormat()
	{
		return new SimpleDateFormat(Constants.AS400_DATE_FORMAT)
				.format(new Date());
	}

	public static final String QUEUE_DUMP_DELIMITER = "|||||nextMessage|||||";

	// Configuration
	public static final String CHAPTER_SYSTEM = "System";

	public static final String CHAPTER_BASE = "Base";

	public static final String KEY_FILENAME = "Filename";

	public static final String KEY_SEND_RESOL = "ConflictResolution";

	public static final String KEY_ENCODING = "Encoding";

	public static final String KEY_RECEIVE_RESOL = "FinalResolution";

	public static final String KEY_CSV_HAS_HEADER = "HasHeader";

	public static final String KEY_CSV_QUOTE_CHAR = "QuoteChar";

	public static final String KEY_CSV_FIELD_SEPARATOR = "FieldSeparator";

	public static final String KEY_CSV_ALWAYS_QUOTE = "AlwaysQuote";

	public static final String KEY_CSV_DESCRIPTION_FILE = "DescriptionFile";

	// FileSender
	public static final String SYS_ENCODING = System
			.getProperty("file.encoding");

	public static final String WRITE_APPEND = "Append";

	public static final String WRITE_OVERWRITE = "Overwrite";

	public static final String WRITE_ERROR = "Error";

	public static final String WRITE_RENAME = "Rename";

	public static final int BLKSIZ = 32768;

	public static final String TEMP_SUFFIX = "xTmp";

	public static final String BACKUP_SUFFIX = ".save";

	// FileReceiver
	public static final String READ_PRESERVE = "Preserve";

	public static final String READ_RENAME = "Rename";

	public static final String READ_DELETE = "Delete";

	public static final String READ_DELETEFILE = "DeleteFile";

	public static final String READ_DELETEMEMBER = "DeleteMember";

	public static final String READ_CALLPROGRAM = "CallProgram";

	public static final String RENAME_SUFFIX = ".back";

	public static final String READ_ERROR = "Error";

	public static final String READ_IGNORE = "Ignore";

	public static final String READ_PROCESS = "Process";

	// XException - Location
	public static final String LOCATION_INTERN = "I";

	public static final String LOCATION_EXTERN = "E";

	// XException - Layer
	public static final String LAYER_COREBASE = "00";

	public static final String LAYER_TECHNICAL = "01";

	public static final String LAYER_PROTOCOL = "02";

	public static final String LAYER_APPLICATION = "03";

	public static final String LAYER_BASE = "04";

	public static final String LAYER_ADMIN = "05";

	public static final String LAYER_BOOTSTRAP = "06";

	// XException - Technical Package
	public static final String PACKAGE_TECHNICAL_TECHNICAL = "000";

	public static final String PACKAGE_TECHNICAL_FILE = "001";

	public static final String PACKAGE_TECHNICAL_AS400 = "002";

	public static final String PACKAGE_TECHNICAL_DATABASE = "003";

	public static final String PACKAGE_TECHNICAL_HTTP = "004";

	public static final String PACKAGE_TECHNICAL_MQ = "005";

	public static final String PACKAGE_TECHNICAL_JAVA = "006";

	public static final String PACKAGE_TECHNICAL_MAIL = "007";

	public static final String PACKAGE_TECHNICAL_SOCKET = "008";

	public static final String PACKAGE_TECHNICAL_MISC = "009";

	public static final String PACKAGE_TECHNICAL_FTP = "010";

	public static final String PACKAGE_TECHNICAL_LDAP = "011";

	// XException - Protocol Package
	public static final String PACKAGE_PROTOCOL_PROTOCOL = "000";

	public static final String PACKAGE_PROTOCOL_AS400 = "001";

	public static final String PACKAGE_PROTOCOL_BYTEARRAYLIST = "002";

	public static final String PACKAGE_PROTOCOL_RECORDS = "003";

	public static final String PACKAGE_PROTOCOL_SIMPLEOBJECT = "004";

	public static final String PACKAGE_PROTOCOL_SIMPLETEXT = "005";

	public static final String PACKAGE_PROTOCOL_SOAP = "006";

	public static final String PACKAGE_PROTOCOL_XML = "007";

	public static final String PACKAGE_PROTOCOL_SIMPLE = "008";

	public static final String PACKAGE_PROTOCOL_CSV = "009";

	// XException - Application Package
	public static final String PACKAGE_APPLICATION_ROUTER = "001";

	public static final String PACKAGE_APPLICATION_ADAPTER = "002";

	public static final String PACKAGE_APPLICATION_APPLICATIONFACTORY = "003";

	// XException - Base Package
	public static final String PACKAGE_BASE_NOTIFYERROR = "001";

	public static final String PACKAGE_BASE_JOURNAL = "002";

	public static final String PACKAGE_BASE_XBUSSYSTEM = "003";

	public static final String PACKAGE_BASE_DELETEDMESSAGESTORE = "004";

	// XException - Admin Package
	public static final String PACKAGE_ADMIN_ADMIN = "000";

	public static final String PACKAGE_ADMIN_JMX = "001";

	public static final String PACKAGE_ADMIN_HTML = "002";

	public static final String PACKAGE_ADMIN_SOAP = "003";

	// XException - Core Base Package
	public static final String PACKAGE_COREBASE_COREBASE = "000";

	public static final String PACKAGE_COREBASE_CONFIG = "001";

	public static final String PACKAGE_COREBASE_TRACE = "002";

	public static final String PACKAGE_COREBASE_TIMEOUTCALL = "003";

	public static final String PACKAGE_COREBASE_XML = "004";

	public static final String PACKAGE_COREBASE_STRINGS = "005";

	public static final String PACKAGE_COREBASE_ARITHMETIC = "006";

	public static final String PACKAGE_COREBASE_REFLECTION = "007";

	public static final String PACKAGE_COREBASE_BYTEARRAYS = "008";

	// XException - Bootstrap Package
	public static final String PACKAGE_BOOTSTRAP_BOOTSTRAP = "000";

	// XException for Examples
	public static final String LAYER_SAMPLE = "99";

	public static final String PACKAGE_SAMPLE_SAMPLE = "888";

	// XException for TestDriver
	public static final String LAYER_TESTDRIVER = "99";

	public static final String PACKAGE_TESTDRIVER_TESTDRIVER = "999";

	public static final String POSTPROCESSING_PERSYSTEM = "perSystem";

	public static final String POSTPROCESSING_FINAL = "final";

}
