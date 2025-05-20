package framework.base;

import java.util.Map;

import org.w3c.dom.Document;
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

import framework.util.PropertyUtils;
import framework.util.XMLUtils;

/**
 * Every model object should extends BaseBean (or a subclass) as it defines:
 * - a String conversion method (useful for debugging)
 * - a XML conversion method
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.5 $ $Date: 2004/06/17 23:28:50 $
 */
public abstract class BaseBean
{

  // Logger
  protected static Log log = LogFactory.getLog(BaseBean.class);

  /**
   * Convert a class into a String
   * This is useful for debug information
   * WARNING!! In some cases, this method can cause a stack overflow:
   * Example: 
   *    MyBean extends BaseBean.
   *    The bean myBean has a public getName() method.
   *    1- myBean.getName() calls a myBean.toString().
   *    2- myBean.toString() calls PropertyUtils.describe().
   *    3- PropertyUtils.describe() calls myBean.getName()
   *    => ... You're f...ed up !
   * Workarounds:
   * - Do not call toString() in getters
   * - Implements a custom describe() method (Manually create the Map)
   * - Create the associated beanInfo (Removing the unwanted properties)
   * @return The class in String format
   */
  public String toString()
  {
    String result = null;
    try
    {
      result = String.valueOf(describe());
    }
    catch(Exception e)
    {
      log.warn(this.getClass().getName()+".toString ", e);
    }
    return result;
  }

  /**
   * Return the entire set of properties for which the bean
   * provides a read method. This map contains the unconverted property
   * values for all properties for which a read method is provided
   * (i.e. where the <code>getReadMethod()</code> returns non-null).
   * @exception Exception
   * @return Map describing the bean
   */
  public Map describe()
  throws Exception
  {
    return PropertyUtils.describe(this);
  }

  /**
   * Convert the bean into an XML element
   * The hashcode is used as ID
   * @return XML Document representing the bean
   */
  public Document toXML()
  {
    return toXML(null);
  }

  /**
   * Convert the bean into an XML element
   * @param beanId The object ID
   * @return XML Document representing the bean
   */
  public Document toXML(String beanId)
  {
    Document result = null;
    try
    {
      result = XMLUtils.convertDOM(this, beanId);
    }
    catch(Exception e)
    {
      log.warn(this.getClass().getName()+".toXML ", e);
    }
    return result;
  }

}