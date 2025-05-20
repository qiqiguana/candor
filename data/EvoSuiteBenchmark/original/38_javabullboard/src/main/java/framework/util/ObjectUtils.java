package framework.util;

import java.util.Map;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;
import java.beans.PropertyDescriptor;
import java.net.URL;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;


/**
 * Object utility class
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.6 $ $Date: 2004/06/17 23:28:51 $
 */
public final class ObjectUtils
{

  // Logger
  private static Log log = LogFactory.getLog(ObjectUtils.class);

  /**
   * Protected constructor
   */
  protected ObjectUtils()
  {
  }
  
  /**
   * Return the class name
   * @param  clazz Class
   * @return The class name
   */
  public static String getClassName(Class clazz)
  {
    String fullName = clazz.getName();
    return fullName.substring(fullName.lastIndexOf('.')+1);
  }

  /**
   * Check if the name is an existing class name
   * @param  className Name to test
   * @return true if the name is a existing class name, else false
   */
  public static boolean isClassName(String className)
  {
    try
    {
      Class.forName(className);
      return true;
    }
    catch(Exception e)
    {
      return false;
    }
  }

  /**
   * Get Class from name
   * @param className Name to test
   * @return Class
   */
  public static Class forName(String className)
  throws Exception
  {
    Class result = null;
    try
    {
      result = Class.forName(className);
    }
    catch(Exception e)
    {
      result = null;
      log.error("Error getting class "+className, e);
      throw e;
    }
    return result;
  }

  /**
   * Return the full directory to the class
   * @param  clazz Class
   * @return The class name
   */
  public static String getFullDirectory(Class clazz)
  {
    URL classURL = clazz.getResource(getClassName(clazz)+".class");
    String classDirectory = classURL.getFile();
    return classDirectory;
  }

  /**
   * Copy properties from Origin bean to Destination bean
   * @param destination The target Object
   * @param origin The source Object
   */
  public static void copyProperties(Object origin, Object destination)
  {
    try
    {
      Map properties = PropertyUtils.describe(origin);
      copyProperties(properties, destination);
    }
    catch(Exception e)
    {
      log.error("copyProperties:", e);
    }
  }

  /**
   * Copy properties into the Destination bean
   * @param destination The target Object
   * @param properties The source properties
   */
  public static void copyProperties(Map properties, Object destination)
  {
    if ((properties == null) || (destination == null)) return;

    if (properties.size()<=0)
    {
      if (log.isDebugEnabled()) log.debug("copyProperties: No properties to be set...");
      return;
    }

    // Loop through the property name/value pairs to be set
    Iterator names = properties.keySet().iterator();
    while (names.hasNext())
    {
      // Identify the property name and value(s) to be assigned
      String name = (String)names.next();
      try
      {
        PropertyDescriptor descriptor = PropertyUtils.getPropertyDescriptor(destination, name);
        if (descriptor!=null)
        {
          Class clazz = descriptor.getPropertyType();
          Object value = properties.get(name);
          // Skip null values
          if (value==null)
          {
            if (log.isDebugEnabled()) log.debug("copyProperties: Property \tname="+name+" is null. Skipping...");
          }
          else
          {
            // Set the property
            if (log.isDebugEnabled()) log.debug("copyProperties: Setting name="+name+", \tvalue="+value+", \tclazz="+clazz.getName());
            PropertyUtils.setProperty(destination, name, value);
          }

        } // End if
        else 
        {
          if (log.isDebugEnabled()) log.debug("copyProperties: Could not process "+name+": No accessor found. Skipping...");
        }
      } // End try
      catch(Exception e)
      {
        if (log.isDebugEnabled()) log.debug("copyProperties: Could not process "+name+": "+e+". Skipping...");
      }
    } // End wile

  }

  /**
   * Determines if the value of the two objects are different.  If both objects 
   * are null, they are considered to be the same.  If one object is null and 
   * the other has a value, they are different.  If both objects have a value 
   * and the values are compared to determine if the objects are the same 
   * or different 
   * @param firstValue An Object
   * @param secondValue Another Object
   * @return true if values are differents, else false
   */
  public static boolean valuesAreDifferent(Object firstValue, Object secondValue) 
  { 
    if ((firstValue==null) && (secondValue==null)) return false;
    else if (firstValue!=null) return !firstValue.equals(secondValue);
    else return true;
  } 

  /**
   * Control if the target Class is a primitive 
   * @param  clazz Class to control
   * @return boolean : true if primitive 
   */
  public static boolean isPrimitive(Class clazz)
  {
    if (!clazz.isArray()) 
    {
      return (clazz.isPrimitive());
    } 
    else 
    {
      Class clazzz = clazz.getComponentType();
      return isPrimitive(clazzz);
    }
  } 

  /**
   * Control if the target Class match destination Class 
   * @param  clazz Class to compare
   * @param  target Class to be compared to
   * @return boolean : true if match 
   */
  public static boolean isMatching(Class clazz, Class target)
  {
  
    if (!clazz.isArray()) 
    {
      return (clazz == target);
    } 
    else 
    {
      Class clazzz = clazz.getComponentType();
      return isMatching(clazzz, target);
    }
  }  

  /**
   * Convert an Object to a Collection:
   * - If the Object is already a Collection, return the Collection
   * - If the Object is an array, convert it to a Collection
   * - Else we consider the value as a single Object and create a Collection
   *   containing this object
   * @param value Object ro convert
   * @return The result Collection
   */
  public static Collection toCollection(Object value)
  {   
    if (value==null) return null;

    Collection result = new ArrayList();
    if (value instanceof Collection)
    {
      if (log.isDebugEnabled()) log.debug("toCollection: Value is a already a Collection");
      result.addAll((Collection)value);
    }
    else if (value.getClass().isArray()) 
    {
      if (log.isDebugEnabled()) log.debug("toCollection: Value is an array");
      result.addAll(Arrays.asList((Object[])value));
    }
    else 
    {
      if (log.isDebugEnabled()) log.debug("toCollection: Value is considered as a single value");
      result.add(value);
    }

    return result;
  }

  /**
  * Try to get a Class with 3 alternatives
  */
  public Class getTargetClass(Object classDefinedTarget, String definitionDefinedTargetClassName, String defaultTargetClassName)
  throws Exception
  {
    Class factory = null;
    if (classDefinedTarget==null) 
    {
      String className = StringUtils.exists(definitionDefinedTargetClassName)?definitionDefinedTargetClassName:defaultTargetClassName;
      factory = ObjectUtils.forName(className);
    }
    else factory = (classDefinedTarget instanceof Class)?(Class)classDefinedTarget:classDefinedTarget.getClass();
    return factory;
  }

}
