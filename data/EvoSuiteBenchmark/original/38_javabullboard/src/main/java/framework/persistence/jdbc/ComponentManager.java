package framework.persistence.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.net.URL;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.CDATASection;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.xmlrules.DigesterLoader;
import org.apache.struts.action.ActionError;
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

import framework.ApplicationParameters;
import framework.util.StringUtils;
import framework.util.FileUtils;
import framework.util.ConvertUtils;
import framework.util.XMLUtils;
import framework.util.ObjectUtils;
import framework.base.BaseCacheManager;
import framework.util.jdbc.JDBCUtils;
import framework.util.jdbc.Parameter;

/**
 * Common functions between ViewManager and EntityManager
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.7 $ $Date: 2004/06/17 23:28:50 $
 */
public abstract class ComponentManager extends BaseCacheManager
{

  // Logger
  protected static Log log = LogFactory.getLog(ComponentManager.class);

  /**
   * Protected constructor
   */
  protected ComponentManager()
  {
  }

////////////////////////////////////////////////////////////////////////////////
//        PARAMETERS
////////////////////////////////////////////////////////////////////////////////

  public String getDefaultTargetClassName()
  {
    return ApplicationParameters.getAsString("framework.persistence.jdbc."+getType().toLowerCase()+".defaultTargetClassName", "java.util.HashMap");
  }

  public boolean isRepositoryUsed()
  {
    return ApplicationParameters.getAsBooleanType("framework.persistence.jdbc."+getType().toLowerCase()+".repository.use", false);
  }

  public String getRepositoryPath()
  throws Exception
  {
    return ApplicationParameters.getAsMandatoryString("framework.persistence.jdbc."+getType().toLowerCase()+".repository.path");
  }

  public String getRepositoryDirectory()
  throws Exception
  {
    return ApplicationParameters.FRAMEWORK_APPLICATION_BASE_DIRECTORY+getRepositoryPath();
  }

  public String getFileExtension()
  {
    return ApplicationParameters.getAsString("framework.persistence.jdbc."+getType().toLowerCase()+".fileExtension", "xml");
  }

  public String getRulesFilePath()
  throws Exception
  {
    return ApplicationParameters.getAsMandatoryString("framework.persistence.jdbc."+getType().toLowerCase()+".rulesFilePath");  
  }

  public URL getRulesFileURL()
  throws Exception
  {  
    String fullPathToRuleFile = ApplicationParameters.FRAMEWORK_APPLICATION_BASE_DIRECTORY+getRulesFilePath();
    return FileUtils.getFileURL(fullPathToRuleFile);
  }

  public String getRootDirectory()
  throws Exception
  {
    String result = null;

    if (isRepositoryUsed()) 
    {
      // If component repository used, check the directory existence
      if (!FileUtils.checkDirectory(getRepositoryDirectory()))
      {
        FileUtils.createDirectory(getRepositoryDirectory());
      }
      result = getRepositoryDirectory();
    }
    else result = ApplicationParameters.FRAMEWORK_APPLICATION_SOURCE_DIRECTORY;

    return result;
  }


////////////////////////////////////////////////////////////////////////////////
//        PUBLIC METHODS
////////////////////////////////////////////////////////////////////////////////

  /**
   * Get the component type
   * @return Component type
   */
  public abstract String getType();

  /**
   * Return the full name of a component from its name and package
   * @param name Name of the component
   * @param packageName Package name of the component
   * @return The component full name
   */
  public String getFullName(String name, String packageName)
  {
    String fullName = StringUtils.exists(packageName)?packageName+"."+name:name;
    return fullName;
  }

////////////////////////////////////////////////////////////////////////////////
  public Map validateAttributeNameUniqueness(Object attributes)
  {
    Collection atts = ObjectUtils.toCollection(attributes);
    Map duplicates = new HashMap();
    Map namesMap = new HashMap();
    Iterator it = atts.iterator();
    while (it.hasNext()) 
    {
      Attribute attribute = (Attribute)it.next();

      // Check attributes name uniqueness
      String name = attribute.getName();
      if (namesMap.get(name)!=null) duplicates.put(name, attribute);
      namesMap.put(name, name);
    }

    return duplicates;
  }

  /**
   * Return the list of valid components files
   * Search will start from the rootDirectory
   * @param pattern Optional pattern to filter result list
   * @return Collection of component
   */
  public Collection listFiles(String pattern)
  throws Exception
  {
    return listFiles(null, pattern);
  }
  
  /**
   * Return the list of valid components files
   * @param path The path where to search component files
   * @param pattern Optional pattern to filter result list
   * @return Collection of component
   */
  public Collection listFiles(String path, String pattern)
  throws Exception
  {
    if (!StringUtils.exists(path)) 
    {
      path = getRootDirectory();
      if (log.isDebugEnabled()) log.debug("listFiles: path is not provided, using rootDirectory "+path);
    }
    Collection result = new ArrayList();
    pattern = StringUtils.exists(pattern)?pattern:".*";
    String filePattern = getFileExtension()+"$";
    Collection files = FileUtils.listFiles(path, filePattern, false);
    Iterator it = files.iterator();
    while (it.hasNext()) 
    {
      File file = (File)it.next();
      String filePath = file.getPath();
      Component component = readFile(filePath, null);
      if (component!=null && StringUtils.matchPattern(pattern, component.getFullName(), false)) result.add(component);
    }

    return result;
  }

/*
  public Collection listFiles(String path, String pattern)
  throws Exception
  {
    Collection result = new ArrayList();
    File root = new File(path);

    File[] files = root.listFiles();
    for (int i=0; i<files.length; i++)
    {
      String filePath = files[i].getPath();
      if (FileUtils.checkDirectory(filePath)) result.addAll(listFiles(filePath, pattern));
      boolean matchPattern = true;
      if (StringUtils.exists(pattern)) matchPattern = StringUtils.matchPattern("^.*"+pattern.toUpperCase()+".*$", filePath.toUpperCase(), true);    //filePath.toUpperCase().indexOf(pattern.toUpperCase())>=0;
      boolean isXMLFile = filePath.toUpperCase().endsWith("."+getFileExtension().toUpperCase());
      if (isXMLFile && matchPattern)
      {
        Component component = readFile(filePath, null);
        if (component!=null) result.add(component);
      }
    }

    return result;
  }
*/  
  /**
   * Get a component with its name and package
   * Manage component cache
   * @param name Name of the component
   * @param packageName Package name of the component
   * @return The component
   */
  public Component find(String name, String packageName)
  throws Exception
  {
    String key = getFullName(name, packageName);
    Object[] args = new Object[] {name, packageName};
    Class[] parameterTypes = new Class[] {String.class, String.class};
    Component component = (Component)getOrLoad(key, this, "read", args, parameterTypes);
/*
    // Get from cache
    String key = getFullName(name, packageName);
    Component component = (Component)getFromCache(key);
    if (component==null)
    {
      // If not found in cache, read the xml file
      component = read(name, packageName);

      key = component.getFullName();
      putIntoCache(key, component);
    }
*/
    return (Component)component.clone();
  }

  /**
   * Read a component file
   * @param name Name of the component
   * @param packageName Package name of the component
   * @return The component
   */
  public Component read(String name, String packageName)
  throws Exception
  {
    String fileDirectory = getFileDirectory(name, packageName);
    return readFile(fileDirectory, null);
  }

  /**
   * Get a component as String
   * @param name Name of the component
   * @param packageName Package name of the component
   * @return The component in String format
   */
  public String dump(String name, String packageName)
  throws Exception
  {
    Component component = read(name, packageName);
    Document doc = convertDOM(component);
    return XMLUtils.toString(doc);
  }

  /**
   * Write a component as file
   * @param component Component to be written
   */
  public void writeFile(Component component)
  throws Exception
  {
    String fileDirectory = getFileDirectory(component.getName(), component.getPackageName());
    Document doc = convertDOM(component);
    XMLUtils.writeDOMFile(doc, fileDirectory);
    if (log.isDebugEnabled()) log.debug("writeFile: "+getType()+" file "+fileDirectory+" written successfully");
  }

  /**
   * Delete a component file
   * @param name Name of the component
   * @param packageName Package name of the component
   */
  public void deleteFile(String name, String packageName)
  throws Exception
  {
    String fileDirectory = getFileDirectory(name, packageName);
    boolean result = FileUtils.deleteFile(fileDirectory);
    if (!result) throw new Exception("Could not delete component file "+fileDirectory);
    if (log.isDebugEnabled()) log.debug("deleteFile: "+getType()+" file "+fileDirectory+" has been deleted");
  }


////////////////////////////////////////////////////////////////////////////////
//        PROTECTED METHODS
////////////////////////////////////////////////////////////////////////////////

  /**
   * Get a new component instance
   * @return Component
   */
  protected abstract Component getNew();
   
  /**
   * Get a component as an DOM XML document
   * @param component The component to convert
   * @return The XML Document
   */
  protected abstract Document convertDOM(Component component)
  throws Exception;
  

  /**
   * Convert component into an XML content and add it to the root element
   * @param doc The XML document
   * @param rootElement The root element
   * @param component The component to convert
   */
  protected void convertDOM(Document doc, Element rootElement, Component component)
  {
    rootElement.setAttribute("name", component.getName());
    rootElement.setAttribute("packageName", component.getPackageName());
    rootElement.setAttribute("targetClassName", component.getTargetClassName());
    rootElement.setAttribute("version", component.getVersion());
    rootElement.setAttribute("timestamp", String.valueOf(component.getTimestamp()));

    Element comment = doc.createElement("comment");
    CDATASection cdataComment = doc.createCDATASection(component.getComment());
    comment.appendChild(cdataComment);
    rootElement.appendChild(comment);

    Iterator it = component.getAttributeCollection().iterator();
    while (it.hasNext())
    {
      Attribute attributeObject = (Attribute)it.next();
      Element attribute = convertDOM(doc, attributeObject);
      rootElement.appendChild(attribute);
    }
    doc.appendChild(rootElement);

  }

  /**
   * Eventually add an error, if the error stack exists
   * else simply log the error
   * This method is designed to work with AND without Struts (batch processing)
   * @param errors Map of ActionError
   * @param errorMessage The error message
   * @param messageKey The message key
   * @param params The message parameters
   */
  protected void addError(Map errors, String errorMessage, String messageKey, Object params)
  {
    // Error stack exists, add error
    if (errors!=null)
    {
      Collection parameters = ObjectUtils.toCollection(params);
      errors.put(errorMessage, new ActionError(messageKey, parameters!=null?parameters.toArray():null));    
    }
    // else log the error
    else log.error(errorMessage);
  }  

  /**
   * Check if the component is a valid
   * @param component The component to be checked
   * @param errors Error stack
   * @return true if the component is valid, else false
   */
  protected boolean isValid(Component component, Map errors)
  throws Exception
  {
    boolean result = true;

    // Check full name    
    result = result && isValidFullName(component.getName(), component.getPackageName(), errors);

    // Check target class name
    result = result && isValidTargetClassName(component.getTargetClassName(), errors);

    // Check view attributes
    result = result && isValidAttributes((Attribute[])component.getAttributeCollection().toArray(new Attribute[component.getAttributesSize()]), errors);

    // Check view attributes
    result = result && isValidInternal(component, errors);

    return result;    
  }

  /**
   * Check the specific type properties of a component
   * @param component The component to be checked
   * @param errors Error stack
   * @return true if the component is valid, else false
   */
  protected abstract boolean isValidInternal(Component component, Map errors)
  throws Exception;

  /**
   * Create a component
   * @param connection JDBC connection
   * @param name Name of the component
   * @param packageName Package name of the component
   * @param comment Comments
   * @param sql SQL Query
   * @return The component
   */
  protected Component create( Connection connection, 
                              String name, 
                              String packageName, 
                              String targetClassName,
                              String comment, 
                              String sql)
  throws Exception
  {
    // Replace bind variables by null values
    String tempSql = JDBCUtils.replaceBindVariablesWithNull(sql);

    // Dummy parameter to avoid retrieving rows
    Collection dummyParameters = new ArrayList();
    dummyParameters.add(new Parameter (null, null, null, "1=2", null));

    // Get target class name
//    targetClassName = (targetClassName==null || targetClassName.length()<=0)?getDefaultTargetClassName():targetClassName;

    // Create the component from the resultset
    Component component = getNew();
    component.setName(name);
    component.setPackageName(packageName);
    component.setTargetClassName(targetClassName);
    component.setVersion(ApplicationParameters.FRAMEWORK_APPLICATION_VERSION);
    component.setTimestamp(System.currentTimeMillis());
    
    component.setComment(comment);
    component.setSqlQuery(sql);

    // Excecute query and add attributes with standard properties
    ResultSet rset = JDBCUtils.executeQuery(connection, tempSql, null, null, dummyParameters);
    ResultSetMetaData metadata = rset.getMetaData();
    Map typeMap = connection.getTypeMap();
    addAttributes(component, metadata, typeMap);
    rset.close();

    // Validate component
    Map errors = new HashMap();
    boolean valid = isValid(component, errors);
    if (!valid) 
    {
      String errMsg = "";
      Iterator it = errors.keySet().iterator();
      while (it.hasNext()) errMsg += it.next();    
      throw new Exception(component.getFullName()+" is not a valid component: "+errMsg);
    }
    
    if (log.isDebugEnabled()) log.debug("create: "+getType()+" "+component.getFullName()+" created successfully");
    return component;
  }

  /**
   * Add attributes to the component following the content of the resultSet
   * @param component Component to add the attributes to
   * @param metadata The meta data to get the attributes from
   * @param typeMap The type mapping
   */
  protected void addAttributes(Component component, ResultSetMetaData metadata, Map typeMap)
  throws Exception
  {
    Collection attributes = new ArrayList();
    for (int i=1; i<=metadata.getColumnCount(); i++)
    {
      try
      {
        Attribute attribute = new Attribute();
        attribute.setName(StringUtils.getJavaName(metadata.getColumnName(i)));
        attribute.setColumnName(metadata.getColumnName(i));
        attribute.setColumnType(String.valueOf(metadata.getColumnType(i)));
        attribute.setScale(String.valueOf(metadata.getScale(i)));
        attribute.setNullable(ResultSetMetaData.columnNullable==metadata.isNullable(i)?"true":"false");        

        // Set column class name
        // Use custom type map if defined
        String columnTypeName = metadata.getColumnTypeName(i);
        String mappedColumnClassName = (String)typeMap.get(columnTypeName);
        if (mappedColumnClassName==null) mappedColumnClassName = metadata.getColumnClassName(i);
        attribute.setColumnTypeName(columnTypeName);
        attribute.setColumnClassName(mappedColumnClassName);

        try
        {
          attribute.setPrecision(String.valueOf(metadata.getPrecision(i)));
        }
        catch(NumberFormatException blobException)
        {
          attribute.setPrecision("-1");
          log.warn("addAttributes: I bet this column is a blob: "+metadata.getColumnName(i), blobException);
        }
        component.addAttribute(attribute);
      }
      catch(Exception e)
      {
        log.error("addAttributes: Could not create attribute for column "+metadata.getColumnName(i), e);
      }
    }    
  }

  /**
   * Synchronize the component attributes with the database
   * @param component Component to synchronize the attributes
   */
/*  protected void synchronizeAttributes(Connection connection, Component component)
  throws Exception
  {
//    Component view = (View)ViewManager.getInstance().create(connection, name, packageName, targetClassName, comment, sqlQuery);

    Iterator it = component.getAttributeCollection().iterator();
    while (it.hasNext()) 
    {
      Attribute attribute = (Attribute)it.next();
      Attribute referenceAttribute = reference.getAttributeByColumnName(attribute.getColumnName());
      boolean isAttributeAlreadyDefined = referenceAttribute!=null;
      if (!isAttributeAlreadyDefined)
      {
        view.removeAttribute(attribute.getName());
        view.addAttribute(oldAttribute);
      }
    }

  }
*/
  /**
   * Check if the name and package name of the component are valids
   * @param name Name of the component
   * @param packageName Package name  of the component
   * @param errors Error stack
   * @return true if all the attributes are valid, else false
   */
  protected boolean isValidFullName(String name, String packageName, Map errors)
  {
    boolean result = true;
    String errorMessage = null;
    String messageKey = null;

    // Check component name
    if (!StringUtils.isJavaName(name)) 
    {
      errorMessage = "isFullNameValid: The "+getType()+" name is not a valid Java name: name="+name;
      messageKey = "error.framework.persistence.jdbc."+getType().trim().toLowerCase()+".name.invalid";
      addError(errors, errorMessage, messageKey, name);
      result = false;
    }

    // Check component package name
    if (!StringUtils.isJavaPackageName(packageName))
    {
      errorMessage = "isFullNameValid: The "+getType()+" package name is not a valid Java name: packageName="+packageName;
      messageKey = "error.framework.persistence.jdbc."+getType().trim().toLowerCase()+".packageName.invalid";
      addError(errors, errorMessage, messageKey, packageName);
      result = false;
    }

    return result;
  }

  /**
   * Check if the target class name is valid
   * @param targetClassName Name target class
   * @param errors Map of errors
   * @return true if the target is a valid class, else false
   */
  protected boolean isValidTargetClassName(String targetClassName, Map errors)
  {
    boolean result = true;
    String errorMessage = null;
    String messageKey = null;

    // Check target class name
    if (StringUtils.exists(targetClassName) && !ObjectUtils.isClassName(targetClassName))
    {
      errorMessage = "isValidTargetClassName: The target class name cannot be instanciate: targetClassName="+targetClassName;
      messageKey = "error.framework.persistence.jdbc."+getType().trim().toLowerCase()+".targetClassName.invalid";
      addError(errors, errorMessage, messageKey, targetClassName);
      result = false;
    }

    return result;
  }

  /**
   * Check if the attributes are valids
   * @param attributes The attributes to be checked
   * @param errors Error stack
   * @return true if all the attributes are valid, else false
   */
  protected boolean isValidAttributes(Attribute[] attributes, Map errors)
  {
    boolean result = true;
    String errorMessage = null;
    String messageKey = null;

    Map namesMap = new HashMap();
    for (int i=0; i<attributes.length; i++) 
    {
      // Check attribute name
      String name = attributes[i].getName();
      if (!StringUtils.isJavaName(name)) 
      {
        errorMessage = "isAttributesValid: The attribute name is not a valid Java name: name="+name;
        messageKey = "error.framework.persistence.jdbc.attribute.name.invalid";
        addError(errors, errorMessage, messageKey, name);
        result = false;
      }

      // Check attribute name uniqueness
      if (namesMap.get(name)!=null) 
      {
        errorMessage = "isAttributesValid: The attribute name is not unique: name="+name;
        messageKey = "error.framework.persistence.jdbc.attribute.name.unique";
        addError(errors, errorMessage, messageKey, name);
        result = false;
      }
      namesMap.put(name, name);

      // Check column classes name
      String columnClassName = attributes[i].getColumnClassName();
      if (!ObjectUtils.isClassName(columnClassName))
      {
        errorMessage = "isAttributesValid: The column class name cannot be instanciate: columnClassName="+columnClassName;
        messageKey = "error.framework.persistence.jdbc.attribute.columnClassName.invalid";
        addError(errors, errorMessage, messageKey, columnClassName);
        result = false;
      }
    }

    return result;
  }


////////////////////////////////////////////////////////////////////////////////
//        PRIVATE METHODS
////////////////////////////////////////////////////////////////////////////////

  /**
   * Get the full path to a component from its name and package
   * @param name Name of the component
   * @param packageName Package name of the component
   * @return The full path to the component
   */
  private String getFileDirectory(String name, String packageName)
  throws Exception
  {
    String fileDirectory = getRootDirectory()+File.separator;

    if (isRepositoryUsed())
    {
      // If the repository is used, the package name is part of the file name
      // Ex: name=hello, packageName=framework.persistence.jdbc.test
      //     => framework.persistence.jdbc.test.hello.xml
      String fullName = getFullName(name, packageName);
      fileDirectory += fullName;
    }
    else
    {
      // If the repository is NOT used, the package name is a path (as Java classes)
      // Ex: name=hello, packageName=framework.persistence.jdbc.test
      //     => framework/persistence/jdbc/test/hello.xml
      packageName = StringUtils.exists(packageName)?packageName.replace('.', File.separatorChar):"";
      String filePath = packageName+File.separator+name;
      fileDirectory += filePath;
    }
    return fileDirectory+"."+getFileExtension();
  }
  
  /**
   * Read a component file
   * @param fileDirectory Full path to the component file
   * @param rulesFileDirectory Full path to the component rules file
   * @return The component, null if the component is not valid
   */
  protected Component readFile(String fileDirectory, String rulesFileDirectory)
  throws Exception
  {
    Component result = null;
    
    // Get the component file
    FileInputStream input = new FileInputStream(fileDirectory);
//    File input = new File(fileDirectory);

    // Get the rule file
    URL rulesFileURL = rulesFileDirectory==null?getRulesFileURL():FileUtils.getFileURL(rulesFileDirectory);

    // Load digester
    Digester digester = DigesterLoader.createDigester(rulesFileURL);

    // Parse and validate the component file
    Component component = null;
    Map errors = new HashMap();
    String errorMessage = null;
    String messageKey = null;
    
    boolean isValid = true;
    try
    {
      component = (Component)digester.parse(input);
      if (component==null)
      {
        errorMessage = "The file "+fileDirectory+" is not a "+getType();
        messageKey = "error.framework.persistence.jdbc.component.isNot";
        addError(errors, errorMessage, messageKey, new Object[] {fileDirectory, getType()});
        isValid = false;
      }
      else isValid = isValid(fileDirectory, component, errors);
    }
    catch(Exception e)
    {
      errorMessage = "The "+getType()+" file "+fileDirectory+" could not be parsed: "+e;
      messageKey = "error.framework.persistence.jdbc."+getType().trim().toLowerCase()+".parseException";
      addError(errors, errorMessage, messageKey, e.getMessage());
      isValid = false;
    }
    
    // Consolidate errors
    if (!isValid) 
    {
      String msg = "";
      Iterator it = errors.keySet().iterator();
      while(it.hasNext())  msg += "\n"+it.next();
      log.error("readFile: "+fileDirectory+" is not a valid "+getType()+" because of the following errors: "+msg);
      result = null;
    }
    else result = component;

    input.close();
    return result;
  }

  /**
   * Convert an attribute into an XML element
   * @param doc The XML document
   * @param attribute The attribute to convert
   * @return XML element
   */
  private Element convertDOM(Document doc, Attribute attributeObject)
  {
    Element attribute = doc.createElement("attribute");

    Element name = doc.createElement("name");
    name.appendChild(doc.createTextNode(attributeObject.getName()));
    attribute.appendChild(name);

    Element columnClassName = doc.createElement("columnClassName");
    columnClassName.appendChild(doc.createTextNode(attributeObject.getColumnClassName()));
    attribute.appendChild(columnClassName);

    Element columnName = doc.createElement("columnName");
    columnName.appendChild(doc.createTextNode(attributeObject.getColumnName()));
    attribute.appendChild(columnName);
      
    Element columnType = doc.createElement("columnType");
    columnType.appendChild(doc.createTextNode(ConvertUtils.convertString(attributeObject.getColumnType())));
    attribute.appendChild(columnType);
      
    Element columnTypeName = doc.createElement("columnTypeName");
    columnTypeName.appendChild(doc.createTextNode(attributeObject.getColumnTypeName()));
    attribute.appendChild(columnTypeName);
      
    Element precision = doc.createElement("precision");
    precision.appendChild(doc.createTextNode(ConvertUtils.convertString(attributeObject.getPrecision())));
    attribute.appendChild(precision);
      
    Element scale = doc.createElement("scale");
    scale.appendChild(doc.createTextNode(ConvertUtils.convertString(attributeObject.getScale())));
    attribute.appendChild(scale);

    Element nullable = doc.createElement("isNullable");
    nullable.appendChild(doc.createTextNode(ConvertUtils.convertString(attributeObject.isNullable())));
    attribute.appendChild(nullable);

    Element isPrimaryKey = doc.createElement("isPrimaryKey");
    isPrimaryKey.appendChild(doc.createTextNode(ConvertUtils.convertString(attributeObject.isPrimaryKey())));
    attribute.appendChild(isPrimaryKey);

    Element keySeq = doc.createElement("keySeq");
    keySeq.appendChild(doc.createTextNode(ConvertUtils.convertString(attributeObject.getKeySeq())));
    attribute.appendChild(keySeq);

    return attribute;
  }

  /**
   * Check if the file is a valid component
   * @param fileDirectory The full path to the component
   * @param component The component to be checked
   * @param errors Error stack
   * @return true if the component is valid, else false
   */
  private boolean isValid(String fileDirectory, Component component, Map errors)
  throws Exception
  {
    boolean result = true;
    String errorMessage = null;
    String messageKey = null;

    if (component==null) throw new Exception("Component is null");
    
    // We cannot compare fileDirectories (String) 
    // because of separator char difference between operating systems
    // We'd better compare Files
    File expectedFile = new File(getFileDirectory(component.getName(), component.getPackageName()));
    File providedFile = new File(fileDirectory);
    if (log.isDebugEnabled()) log.debug("isValid: expectedFileURL="+expectedFile+", providedFile="+providedFile+", equals?="+expectedFile.equals(expectedFile));

    // Check paths
    if (!expectedFile.equals(providedFile))
    {
      errorMessage = "isValid: "+getType()+" name or package do not match the file physical location: name="+component.getName()+", packageName="+component.getPackageName()+", fileDirectory="+fileDirectory;
      messageKey = "error.framework.persistence.jdbc."+getType().trim().toLowerCase()+".nameOrPackage.inconsistent";
      addError(errors, errorMessage, messageKey, new Object[] {component.getName(), component.getPackageName(), fileDirectory});
      result = false;       
    }

    // Call specific type validation method
    result = result && isValid(component, errors);
    
    return result;
  }


////////////////////////////////////////////////////////////////////////////////
//        CACHE MANAGEMENT
////////////////////////////////////////////////////////////////////////////////

  /**
   * Return the cache
   * @return Map representing the cache
   */
//  protected abstract Map getCache();

  /**
   * Return a component from cache, null if not found
   * @param name Name of the component
   * @param packageName Package name of the component
   * @return The requested component, null if not found
   */
/*  protected Component getFromCache(String name, String packageName)
  {
    String fullName = getFullName(name, packageName);
    Component result = (Component)getCache().get(fullName);
    if (result!=null && log.isDebugEnabled()) log.debug("getFromCache: "+getType()+" "+fullName+" found in cache");
    return result;
  }
*/
  /**
   * Put a component into cache
   * @param component The component
   */
/*  protected void putIntoCache(Component component)
  {
    String fullName = component.getFullName();
    if (getCache().get(fullName)!=null) log.warn("putIntoCache: "+getType()+" "+fullName+" has been overwritten in cache");
    getCache().put(fullName, component);
    if (log.isDebugEnabled()) log.debug("putIntoCache: "+getType()+" "+fullName+" put in cache");
  }
*/
  /**
   * Read a component file and load it in cache
   * @param name Name of the component
   * @param packageName Package name of the component
   */
/*  protected void reloadInCache(String name, String packageName)
  throws Exception
  {
    Component component = read(name, packageName);
    putIntoCache(component);
  }
*/    
}