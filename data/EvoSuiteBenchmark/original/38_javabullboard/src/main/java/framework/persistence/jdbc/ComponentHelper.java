package framework.persistence.jdbc;

import java.sql.ResultSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

import framework.util.ConvertUtils;
import framework.util.ObjectUtils;
import framework.util.PropertyUtils;
import framework.util.TuningUtils;

/**
 * Common JDBC methods between ViewHelper and EntityHelper
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.4 $ $Date: 2004/06/17 23:28:50 $
 */
public class ComponentHelper
{

  // Logger
  private static Log log = LogFactory.getLog(ComponentHelper.class);

  /**
   * Protected constructor
   */  
  protected ComponentHelper()
  {
  }

  /**
   * Populate the target object with the resultSet content following 
   * the attributes definitions
   * @param resultSet The source resultSet
   * @param attributes The Map of attributes definitions
   * @param target The target object
   */
  protected static void populate(ResultSet resultSet, Map attributes, Object target)
  throws Exception
  {
    if (target instanceof Map) populate(resultSet, attributes, (Map)target);
    else 
    {
      Map properties = new HashMap();
      populate(resultSet, attributes, properties);
//      ObjectUtils.copyProperties(properties, target);
      PropertyUtils.copyProperties(target, properties);
    }
  }

  /**
   * Populate the target map with the resultSet content following 
   * the attributes definitions
   * @param resultSet The source resultSet
   * @param attributes The Map of attributes definitions
   * @param target The target Map
   */
  private static void populate(ResultSet resultSet, Map attributes, Map target)
  throws Exception
  {
    TuningUtils.startTuning(log, "populate");

    Iterator it = attributes.values().iterator();
    while (it.hasNext())
    {
      Attribute attribute = (Attribute)it.next();
      String name = attribute.getName();
      Integer sqlType = attribute.getColumnType();
      String sqlTypeName = attribute.getColumnTypeName();
      Class targetClass = ObjectUtils.forName(attribute.getColumnClassName());

      Object value = resultSet.getObject(attribute.getColumnName());
      if (value!=null)
      {
        Class sourceClass = value.getClass();
        if (log.isDebugEnabled()) log.debug("populate: name="+name+", sqlTypeName="+sqlTypeName+"("+sqlType+"), sourceClass="+sourceClass.getName()+", value="+value+", targetClass="+targetClass.getName());
        if (sourceClass.equals(targetClass)) target.put(name, value);
        else target.put(name, ConvertUtils.convert(ConvertUtils.convertString(value), targetClass));
      }
      else
      {
        if (log.isDebugEnabled()) log.debug("populate: name="+name+", sqlType="+sqlType+", value="+value+", targetClass="+targetClass.getName());
        target.put(name, value);
      }
    }

    TuningUtils.stopTuning(log, "populate");
  }

}