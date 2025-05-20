package framework;

import framework.util.TuningUtils;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Document;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;
import org.apache.xpath.XPathAPI;

import framework.util.XMLUtils;
import framework.util.StringUtils;
import framework.util.ConvertUtils;

/**
 * This class is used to access the parameters:
 * - framework parameters
 * - application parameters
 * 
 * This file must be named <b>ApplicationParameters.xml</b> and placed at the root 
 * of the appication classpath. Mostly under WEB-INF/classes for web applications.
 * 
 * It is important to have parameters centralized into a single XML file:
 * - simple to update
 * - simple to reload
 * 
 * Copying the value of a parameter in a static property is generally a bad idea:
 * Ex: 
 * DO NOT WRITE
 *    static int initPoolSize = GlobalParameters.getAsInt("/mysql/initPoolSize");
 * BUT
 *    public static int getInitPoolSize()
 *    {
 *      return GlobalParameters.getAsInt("/mysql/initPoolSize");
 *    }
 * Because, in the first case, the parameter file reload won't work.
 * 
 * The XML document is hold in memory but it is still slower to access a node 
 * through XPath and convert it than directly accessing a static value.
 * To avoid performance issue, have parameter values as local variables 
 * for example (especially for loops).
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.1 $ $Date: 2004/06/17 23:27:18 $
 */
public final class ApplicationParameters 
{

  // Logger
  private static Log log = LogFactory.getLog(framework.ApplicationParameters.class);
  
  // Parameters
  private static final String DEFAULT_PARAMETER_FILE = "ApplicationParameters.xml";
  private static final String FRAMEWORK_APPLICATION_RUN_MODE_PRODUCTION = "production";
  private static final String FRAMEWORK_APPLICATION_RUN_MODE_INTEGRATION = "integration";
  private static final String FRAMEWORK_APPLICATION_RUN_MODE_DEVELOPMENT = "development";

  private static String parameterFile = DEFAULT_PARAMETER_FILE;
  private static Document parameterDocument = null;

  // Application mandatory parameters
  public static String FRAMEWORK_APPLICATION_NAME = null;
  public static String FRAMEWORK_APPLICATION_VERSION = null;
  public static String FRAMEWORK_APPLICATION_BASE_DIRECTORY = null;
  public static String FRAMEWORK_APPLICATION_WEB_PATH = null;
  public static String FRAMEWORK_APPLICATION_WEB_DIRECTORY = null;
  public static String FRAMEWORK_APPLICATION_SOURCE_PATH = null;
  public static String FRAMEWORK_APPLICATION_SOURCE_DIRECTORY = null;
  public static String FRAMEWORK_APPLICATION_URL = null;
  public static String FRAMEWORK_APPLICATION_RUN_MODE = null;

  /*
   * Static Initialization
   */
  static
  {
    reload();
  }

  /**
   * Convert the parameters into a String.
   * Replace <!-- --> by <COMMENTS> </COMMENTS> to prevent interpretation 
   * from browers
   * @return The string representing all parameters
   */
  public static String twoString()
  {
    try
    {
      String result = XMLUtils.toString(parameterDocument);
      result = StringUtils.replace(result, "<!--", "<COMMENTS>");
      result = StringUtils.replace(result, "-->", "</COMMENTS>");
      return result;
    }
    catch(Exception e)
    {
      log.error("twoString", e);
      return null;
    }
  }

////////////////////////////////////////////////////////////////////////////////
//        PUBLIC LOAD / RELOAD METHODS
////////////////////////////////////////////////////////////////////////////////

  /**
   * Reload the parameters
   */
  public static void reload()
  {
    load(parameterFile);
  }

  /**
   * Load the parameters
   * The parameters file must be placed under WEB-INF/classes
   * @param pParameterFile The name of the parameter file to load
   */
  public static void load(String pParameterFile)
  {
    parameterFile = pParameterFile;
   
    try 
    {
      // Load the file
      ClassLoader cl = framework.ApplicationParameters.class.getClassLoader();
      InputStream is = cl.getResourceAsStream(parameterFile);
      if (is==null) throw new RuntimeException(parameterFile+" could not be found!");

      // Parse the document
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      parameterDocument = db.parse(is);

      // Get the application mandatory parameters
      FRAMEWORK_APPLICATION_NAME = getAsMandatoryString("framework/mandatory/application/name");
      FRAMEWORK_APPLICATION_VERSION = getAsMandatoryString("framework/mandatory/application/version");
      FRAMEWORK_APPLICATION_BASE_DIRECTORY = getAsMandatoryString("framework/mandatory/application/baseDirectory");
      FRAMEWORK_APPLICATION_WEB_PATH = getAsMandatoryString("framework/mandatory/application/webPath");
      FRAMEWORK_APPLICATION_WEB_DIRECTORY = FRAMEWORK_APPLICATION_BASE_DIRECTORY+FRAMEWORK_APPLICATION_WEB_PATH;
      FRAMEWORK_APPLICATION_SOURCE_PATH = getAsMandatoryString("framework/mandatory/application/sourcePath");
      FRAMEWORK_APPLICATION_SOURCE_DIRECTORY = FRAMEWORK_APPLICATION_BASE_DIRECTORY+FRAMEWORK_APPLICATION_SOURCE_PATH;
      FRAMEWORK_APPLICATION_URL = getAsMandatoryString("framework/mandatory/application/url");
      FRAMEWORK_APPLICATION_RUN_MODE = getAsMandatoryString("framework/mandatory/application/runMode");

      // Set system properties
      setSystemProperties();

      log.info("load: "+FRAMEWORK_APPLICATION_NAME+" "+FRAMEWORK_APPLICATION_VERSION+" initialized !");
    }
    catch (Exception e)
    {
      throw new RuntimeException("load: Error while loading parameter file '"+pParameterFile+"': "+e.getMessage());
    }
  }


////////////////////////////////////////////////////////////////////////////////
//        PUBLIC SETTERS METHODS
////////////////////////////////////////////////////////////////////////////////

  /**
   * Finds a node based on an XPath expression and sets the text contents of it.
   * Takes a context node and an XPath expression. 
   * The matching node gets a text node appending containing the contents 
   * of the value string. 
   * The node matching the XPath expression is returned.
   * @param contextNode Context node on which to evaluate the xpath  
   * @param parameterName The XPath expression                            
   * @param value The text to add to the matched node
   * @return The matched node 
   **/
  public static void set(Node contextNode, String parameterName, String value)
  throws Exception
  {
    Node targetNode = getNode(contextNode, parameterName, true);

    // Remove all of the current contents
    NodeList children = targetNode.getChildNodes();
    for(int index=0; index<children.getLength(); index++) 
    {
      targetNode.removeChild( children.item( index ) );
    }

    // Add in the new value
    targetNode.appendChild(parameterDocument.createTextNode(value));        
  }

  public static void set(String parameterName, String value)
  throws Exception
  {
    set(null, parameterName, value);
  }
  

////////////////////////////////////////////////////////////////////////////////
//        PUBLIC GETTERS METHODS
////////////////////////////////////////////////////////////////////////////////

  /**
   * Is development mode?
   * @return true if application runs in development mode
   */
  public static boolean isDevelopmentMode()
  {
    return FRAMEWORK_APPLICATION_RUN_MODE_DEVELOPMENT.equalsIgnoreCase(FRAMEWORK_APPLICATION_RUN_MODE);
  }

  /**
   * Is integration mode?
   * @return true if application runs in integration mode
   */
  public static boolean isIntegrationMode()
  {
    return FRAMEWORK_APPLICATION_RUN_MODE_INTEGRATION.equalsIgnoreCase(FRAMEWORK_APPLICATION_RUN_MODE);
  }

  /**
   * Is production mode?
   * @return true if application runs in production mode
   */
  public static boolean isProductionMode()
  {
    return FRAMEWORK_APPLICATION_RUN_MODE_PRODUCTION.equalsIgnoreCase(FRAMEWORK_APPLICATION_RUN_MODE);
  }

  /**
   * Get the default locale
   * @return The locale in the properties if defined, else the system's locale 
   */
  public static Locale getDefaultLocale()
  {
      String language = getAsString("/framework/default/locale/language");
      String country = getAsString("/framework/default/locale/country");
      if (language!=null && country!=null) return new Locale(language, country);
      else return Locale.getDefault();
  }

////////////////////////////////////////////////////////////////////////////////
// BOOLEAN / BOOLEAN TYPE
////////////////////////////////////////////////////////////////////////////////

  /**
   * Get a boolean property value
   * @param propertyName The property name
   * @return The property value, null if property's not found
   */
  public static Boolean getAsBoolean(String propertyName)
  {
    return getAsBoolean(propertyName, null);
  }

  /**
   * Get a boolean property value
   * @param propertyName The property name
   * @return The property value, defaultValue if property's not found
   */
  public static Boolean getAsBoolean(String propertyName, Boolean defaultValue)
  {
    String result = getAsString(propertyName, null);
    if (result==null) return defaultValue; // Workaround for Boolean.valueOf(null) => false
    return ConvertUtils.convertBoolean(result, defaultValue);      
  }

  /**
   * Get a boolean property value
   * Throw exception if not found
   * @param propertyName The property name
   * @return The property value
   */
  public static Boolean getAsMandatoryBoolean(String propertyName)
  throws Exception
  {
    String result = getAsMandatoryString(propertyName);
    return ConvertUtils.convertBoolean(result);
  }

  /**
   * Get a boolean property value
   * @param propertyName The property name
   * @return The property value, defaultValue if property's not found
   */
  public static boolean getAsBooleanType(String propertyName, boolean defaultValue)
  {
    Boolean result = getAsBoolean(propertyName);
    if (result==null) return defaultValue;
    else return result.booleanValue();
  }

  /**
   * Get a boolean property value
   * Throw exception if not found
   * @param propertyName The property name
   * @return The property value
   */
  public static boolean getAsMandatoryBooleanType(String propertyName)
  throws Exception
  {
    Boolean result = getAsMandatoryBoolean(propertyName);
    return result.booleanValue();
  }

////////////////////////////////////////////////////////////////////////////////
// INTEGER / INT
////////////////////////////////////////////////////////////////////////////////

  /**
   * Get a Integer property value
   * @param propertyName The property name
   * @return The property value, null if property's not found
   */
  public static Integer getAsInteger(String propertyName)
  {
    return getAsInteger(propertyName, null);
  }

  /**
   * Get a Integer property value
   * @param propertyName The property name
   * @return The property value, defaultValue if property's not found
   */
  public static Integer getAsInteger(String propertyName, Integer defaultValue)
  {
    String result = getAsString(propertyName, null);
    return ConvertUtils.convertInteger(result, defaultValue);
  }

  /**
   * Get a Integer property value
   * Throw exception if not found
   * @param propertyName The property name
   * @return The property value
   */
  public static Integer getAsMandatoryInteger(String propertyName)
  throws Exception
  {
    String result = getAsMandatoryString(propertyName);
    return ConvertUtils.convertInteger(result);
  }

  /**
   * Get a int property value
   * @param propertyName The property name
   * @return The property value, defaultValue if property's not found
   */
  public static int getAsInt(String propertyName, int defaultValue)
  {
    Integer result = getAsInteger(propertyName);
    if (result==null) return defaultValue;
    else return result.intValue();
  }

  /**
   * Get a int property value
   * Throws Exception if not found
   * @param propertyName The property name
   * @return The property value
   */
  public static int getAsMandatoryInt(String propertyName)
  throws Exception
  {
    Integer result = getAsMandatoryInteger(propertyName);
    return result.intValue();
  }

////////////////////////////////////////////////////////////////////////////////
// LONG / LONG TYPE
////////////////////////////////////////////////////////////////////////////////

  /**
   * Get a Long property value
   * @param propertyName The property name
   * @return The property value, null if property's not found
   */
  public static Long getAsLong(String propertyName)
  {
    return getAsLong(propertyName, null);
  }

  /**
   * Get a Long property value
   * @param propertyName The property name
   * @return The property value, defaultValue if property's not found
   */
  public static Long getAsLong(String propertyName, Long defaultValue)
  {
    String result = getAsString(propertyName, null);
    return ConvertUtils.convertLong(result, defaultValue);
  }

  /**
   * Get a Long property value
   * Throw exception if not found
   * @param propertyName The property name
   * @return The property value
   */
  public static Long getAsMandatoryLong(String propertyName)
  throws Exception
  {
    String result = getAsMandatoryString(propertyName);
    return ConvertUtils.convertLong(result);
  }

  /**
   * Get a long property value
   * @param propertyName The property name
   * @return The property value, defaultValue if property's not found
   */
  public static long getAsLongType(String propertyName, long defaultValue)
  {
    Long result = getAsLong(propertyName);
    if (result==null) return defaultValue;
    else return result.longValue();
  }

  /**
   * Get a long property value
   * Throws Exception if not found
   * @param propertyName The property name
   * @return The property value
   */
  public static long getAsMandatoryLongType(String propertyName)
  throws Exception
  {
    Long result = getAsMandatoryLong(propertyName);
    return result.longValue();
  }


////////////////////////////////////////////////////////////////////////////////
// STRING
////////////////////////////////////////////////////////////////////////////////

  /**
   * Get a String property value
   * @param propertyName The property name
   * @return The property value, null if property's not found
   */
  public static String getAsString(String propertyName)
  {
    return getAsString(propertyName, null);
  }

  /**
   * Get a String property value
   * @param propertyName The property name
   * @param defaultValue The default value to use if property's not found
   * @return The property value, defaultValue if property's not found
   */
  public static String getAsString(String propertyName, String defaultValue)
  {
    String result = null;
    try
    {
      result = getAsString(propertyName, defaultValue, false);
    }
    catch(Exception e)
    {
      // This should never happens because the property is not mandatory
      log.error("getAsString: Oops!", e);
      result = defaultValue;
    }
    return result;
  }
  
  /**
   * Get a String property value
   * Throw Exception if not found
   * @param propertyName The property name
   * @return The property value
   */
  public static String getAsMandatoryString(String propertyName)
  throws Exception
  {
    return getAsString(propertyName, null, true);
  }

  /**
   * Get a String property value
   * @param propertyName The property name
   * @param propertyName A substitute property name if the previous one has not been found
   * @param defaultValue Finally the default value tu use if nothing was found
   * @return The property value, null if property's not found
   */
  public static Object getWhatYouCan( String propertyName, 
                                      String substitutePropertyName, 
                                      Object defaultValue)
  throws Exception
  {
    if (defaultValue==null) throw new Exception("Must provide a default value");
    String result = getAsString(propertyName, null);
    if (result==null) result = getAsString(substitutePropertyName, null);    
    if (result!=null) return ConvertUtils.convert(result, defaultValue.getClass());
    else return defaultValue;    
  }

////////////////////////////////////////////////////////////////////////////////
// NODES
////////////////////////////////////////////////////////////////////////////////

  /**
   * Return a Collection of Nodes witch names starts with parameterName
   * @param parameterName
   * @return Collection of Node
   */
  public static List findNodeList(Node contextNode, String parameterName)
  throws Exception
  {
    TuningUtils.startTuning(log, "findNodeList");
    if (!StringUtils.exists(parameterName)) throw new Exception("findNodes called with empty parameterName");

    List result = new ArrayList();
    if (contextNode==null) contextNode = getRootNode();

    // Keep properties compatibility
    parameterName = parameterName.replace('.', '/');
    
    NodeList nl = XPathAPI.selectNodeList(contextNode, parameterName);
    for (int i=0; i<nl.getLength(); i++) result.add(nl.item(i));
    TuningUtils.stopTuning(log, "findNodeList");
    return result;
  }

  public static List findNodeList(String parameterName)
  throws Exception
  {
    return findNodeList(null, parameterName);
  }

  /**
   * Return a parameter witch name matches parameterName
   * THIS IS THE REAL ROOT METHOD !!!!
   * @param parameterName
   * @return Node
   */
  public static Node getNode(Node contextNode, String parameterName, boolean isMandatory)
  throws Exception
  {
    TuningUtils.startTuning(log, "getNode");
    if (!StringUtils.exists(parameterName)) throw new Exception("findNode called with empty parameterName");
    if (contextNode==null) contextNode = getRootNode();

    // Keep properties compatibility
    parameterName = parameterName.replace('.', '/');
    if (log.isDebugEnabled()) log.debug("getNode: Looking for node "+parameterName);  
    Node node = XPathAPI.selectSingleNode(contextNode, parameterName);
    if (node==null && isMandatory) throw new Exception("Could not find mandatory parameter "+parameterName);
    TuningUtils.stopTuning(log, "getNode");
    return node;
  }

  public static Node getNode(String parameterName, boolean isMandatory)
  throws Exception
  {
    return getNode(null, parameterName, isMandatory);
  }

  public static Node getNode(Node contextNode, String parameterName)
  throws Exception
  {
    return getNode(contextNode, parameterName, false);
  }

  public static Node getNode(String parameterName)
  throws Exception
  {
    return getNode(null, parameterName, false);
  }

  /**
   * Return a List of parameters witch names starts with parameterName
   * @param contextNode The context node
   * @param parameterName The parameter name
   * @return List of String
   */
  public static List findNodeValueList(Node contextNode, String parameterName)
  throws Exception
  {
    List result = new ArrayList();
    
    Iterator it = findNodeList(contextNode, parameterName).iterator();
    while (it.hasNext()) 
    {
      Node node = (Node)it.next();
      result.add(getValue(node, true));
    }

    return result;
  }

  public static List findNodeValueList(String parameterName)
  throws Exception
  {
    return findNodeValueList(null, parameterName);
  }

  /**
   * Returns the Element Nodes names under the provided path
   * @param parameterName The path to search child nodes
   * @return Collection of String
   */
  public static List findChildNodesName(String parameterName)
  throws Exception
  {
    List result = new ArrayList();

    Node poolRoot = getNode(parameterName, true);
    NodeList nl = poolRoot.getChildNodes();
    for (int i = 0; i < nl.getLength(); i++) 
    {
      Node node = nl.item(i);
      if (node.getNodeType()==Node.ELEMENT_NODE)
      {
        result.add(node.getNodeName());
      }
    }

    return result;
  }

  /**
   * Get system properties
   **/
  private static Properties getSystemProperties()
  throws Exception
  {
    return System.getProperties();
  }

////////////////////////////////////////////////////////////////////////////////
//        PRIVATE METHODS
////////////////////////////////////////////////////////////////////////////////

  /**
   * Get the framework root Node
   * @return Node representing the framework root
   */
  private static Node getRootNode()
  throws Exception
  {
    //return parameterDocument.getDocumentElement().getFirstChild();
    return XPathAPI.selectSingleNode(parameterDocument.getDocumentElement(), "/applicationParameters");  
  }


  /**
   * Get a String parameter value
   * THIS IS THE ROOT GETTER METHOD !
   * @param parameterName The parameter name
   * @param defaultValue The default value to use if parameter's not found
   * @param isMandatory is a mandatory parameter?
   * @return The parameter value, defaultValue if parameter's not found or Exception if mandatory
   */
  private static String getAsString(String parameterName, String defaultValue, boolean isMandatory)
  throws Exception
  {
    String result = null;

    Node node = getNode(parameterName, isMandatory);
    result = getValue(node, true);
    if (result==null) 
    {
      if (isMandatory) throw new Exception("Mandatory parameter "+parameterName+" is empty");
      else
      {
        if (log.isDebugEnabled()) log.debug("getAsString: Parameter "+parameterName+" not found or empty, using default value: "+defaultValue);
        result = defaultValue;
      }
    }
    return result;
  }

  /**
   * Returns the contents of all immediate child text nodes, can strip whitespace
   * <p>
   * Takes a node as input and merges all its immediate text nodes into a
   * string.  If the strip whitespace flag is set, whitespace at the beggining
   * and end of each merged text node will be removed
   *
   * @param node node to extract text contents of
   * @param doTrim flag to set whitespace removal
   * @return string containing text contents of the node 
   **/
  public static String getValue(Node node, boolean doTrim)
  {
    if (node==null) return null;
    
    StringBuffer contents = new StringBuffer();
    NodeList childNodes = node.getChildNodes();
    for (int i=0; i<childNodes.getLength(); i++)
    {
      if (childNodes.item(i).getNodeType()==Node.TEXT_NODE) 
      {
        contents.append(childNodes.item(i).getNodeValue());
      }
    }
    String result = contents.toString();
//    result = result.length()>0?result:null;
    result = doTrim?StringUtils.trimEmptyToNull(result):result;
    return result;
  } 

  /**
   * Set system properties as specified in the parameters file
   * Empty property names will not bet processed
   * Empty values ("") are considered as valids values
   **/
  private static void setSystemProperties()
  throws Exception
  {
    List result = findNodeList("framework/system/setProperty");
    Iterator it = result.iterator();
    while (it.hasNext()) 
    {
      Node node = (Node)it.next();
      String name = getValue(getNode(node, "name"), true);
      String value = getValue(getNode(node, "value"), false);
      if (StringUtils.exists(name)) System.setProperty(name, value);
    }    
  }
    
}