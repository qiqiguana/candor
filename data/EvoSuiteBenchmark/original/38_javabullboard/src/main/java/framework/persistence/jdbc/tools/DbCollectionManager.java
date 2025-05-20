package framework.persistence.jdbc.tools;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

import framework.persistence.jdbc.View;
import framework.persistence.jdbc.Attribute;
import framework.base.BaseCacheManager;
import framework.ApplicationParameters;
import framework.persistence.jdbc.tools.ToolsModule;
import framework.util.TuningUtils;

/**
 * Retrieve (and eventually cache) collections of Value-Label from the database
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.7 $ $Date: 2004/06/17 23:28:51 $
 */
public class DbCollectionManager extends BaseCacheManager
{

  // Logger
  private static Log log = LogFactory.getLog(DbCollectionManager.class);

////////////////////////////////////////////////////////////////////////////////
//        SINGLETON
////////////////////////////////////////////////////////////////////////////////

  // Singleton
  private static DbCollectionManager instance;

  /**
   * Return the instance of DbCollectionUtils
   * @return DbCollectionUtils
   */
  protected static DbCollectionManager getInstance()
  {
    if (instance==null) instance = new DbCollectionManager();
    return instance;
  }

  /**
   * Private constructor
   */  
  protected DbCollectionManager()
  {
    super();
  }

////////////////////////////////////////////////////////////////////////////////
//        PARAMETERS
////////////////////////////////////////////////////////////////////////////////

  public static String getValueColumnName()
  {
    return ApplicationParameters.getAsString("framework.persistence.jdbc.tools.dbcollection.valueColumnName", "Value");
  }

  public static String getLabelColumnName()
  {
    return ApplicationParameters.getAsString("framework.persistence.jdbc.tools.dbcollection.labelColumnName", "Label");
  }

////////////////////////////////////////////////////////////////////////////////
//        PUBLIC METHODS
////////////////////////////////////////////////////////////////////////////////

  /**
   * Prints the most recently used list of cached objects
   */
  public static String dump()
  {
    return getInstance().dumpCache();
  }
  
  /**
   * Get a ValueLabel Collection
   * The result is cached under the fullname of the view
   * The value attribute name is defined by getValueColumnName()
   * The label attribute name is defined by getLabelColumnName()
   * The target class is defined by the View
   * @param name Name of the view
   * @param packageName Package Name of the view
   * @return Collection of target
   */
  public static Collection get(String name, String packageName)
  throws Exception
  {
    return get(name, packageName, getValueColumnName(), getLabelColumnName());
  }

  /**
   * Get a ValueLabel Collection
   * The result is cached under the fullname of the view
   * The target class is defined by the View
   * @param name Name of the view
   * @param packageName Package Name of the view
   * @param valueAttributeName The attribute name to use as the Value
   * @param labelAttributeName The attribute name to use as the Label
   * @return Collection of target
   */
  public static Collection get( String name, 
                                String packageName, 
                                String valueAttributeName,
                                String labelAttributeName)
  throws Exception
  {
    return get(name, packageName, valueAttributeName, labelAttributeName, null);
  }
  
  /**
   * Get a ValueLabel Collection
   * The result is cached under the fullname of the view
   * @param name Name of the view
   * @param packageName Package Name of the view
   * @param valueAttributeName The attribute name to use as the Value
   * @param labelAttributeName The attribute name to use as the Label
   * @param target Object to populate
   * @return Collection of target
   */
  public static Collection get( String name, 
                                String packageName, 
                                String valueAttributeName,
                                String labelAttributeName,
                                Object target)
  throws Exception
  {
    Collection result = null;
    ToolsModule module = new ToolsModule();
    module.connect();
    View view = module.findAnyView(name, packageName);
    result = getAndCache(view, valueAttributeName, labelAttributeName, target);
    module.disconnect();
    return result;
  }

  /**
   * Get a ValueLabel Collection
   * The result is cached under the fullname of the view.
   * Be aware that view won't be executed twice and 
   * the parameters taken into account even if they have changed 
   * as the result is cached once and for all.
   * @param view The view to execute (view's parameter has already been set)
   * @param valueAttributeName The attribute name to use as the Value
   * @param labelAttributeName The attribute name to use as the Label
   * @param target Object to populate
   * @return Collection of target
   */
  public static Collection getAndCache( View view, 
                                        String valueAttributeName,
                                        String labelAttributeName,
                                        Object target)
  throws Exception
  {
    String identifier = view.getFullName();
    Object[] args = new Object[] {view, valueAttributeName, labelAttributeName, target};
    Class[] parameterTypes = new Class[] {View.class, String.class, String.class, Object.class};
    return (Collection)getInstance().getOrLoad(identifier, getInstance(), "get", args, parameterTypes);

/*  
    String identifier = view.getFullName();
    Collection result = (Collection)getInstance().getFromCache(identifier);
    if (result!=null) return result;
    else
    {
      result = get(view, valueAttributeName, labelAttributeName, target);
      getInstance().putIntoCache(identifier, result);
    }
    return result;
*/
  }

  /**
   * Get a ValueLabel Collection
   * The result is NOT cached as the parameters may change
   * The value attribute name is defined by getValueColumnName()
   * The label attribute name is defined by getLabelColumnName()
   * The target class is defined by the View
   * @param view The view to execute (view's parameter has already been set)
   * @return Collection of target 
   */
  public static Collection get(View view)
  throws Exception
  {
    return get(view, getValueColumnName(), getLabelColumnName());
  }

  /**
   * Get a ValueLabel Collection
   * The result is NOT cached as the parameters may change
   * The target class is defined by the View
   * @param view The view to execute (view's parameter has already been set)
   * @param valueAttributeName The attribute name to use as the Value
   * @param labelAttributeName The attribute name to use as the Label
   * @return Collection of target 
   */
  public static Collection get( View view, 
                                String valueAttributeName,
                                String labelAttributeName)
  throws Exception
  {
    return get(view, valueAttributeName, labelAttributeName, null);
  }
  
  /**
   * Get a ValueLabel Collection
   * The result is NOT cached as the parameters may change
   * @param view The view to execute (view's parameter has already been set)
   * @param valueAttributeName The attribute name to use as the Value
   * @param labelAttributeName The attribute name to use as the Label
   * @param target Object to populate
   * @return Collection of target 
   */
  public static Collection get( View view, 
                                String valueAttributeName,
                                String labelAttributeName,
                                Object target)
  throws Exception
  {
    Collection result = null;

    Attribute valueAttribute = view.getAttribute(valueAttributeName);
    valueAttribute.setName(getValueColumnName());

    Attribute labelAttribute = view.getAttribute(labelAttributeName);
    labelAttribute.setName(getLabelColumnName());

    view.removeAttributes();
    view.addAttribute(valueAttribute);
    view.addAttribute(labelAttribute);

    result = view.getCollection(target);
    return result;
  }

  /**
   * Get a ValueLabel Collection from another Collection of Map
   * @param valueKey The map key to use as the Value
   * @param labelKey The map key to use as the Label
   * @return Collection of Map
   */  
  public static Collection get( Collection collection, 
                                String valueKey,
                                String labelKey)
  throws Exception
  {
    TuningUtils.startTuning(log, "get");
    String valueColumnName = getValueColumnName();
    String labelColumnName = getLabelColumnName();
    if (collection==null) return null;
    Collection result = new ArrayList(collection.size());
    Iterator it = collection.iterator();
    while (it.hasNext()) 
    {
      Map out = new HashMap();
      Map in = (Map)it.next();
      out.put(valueColumnName, in.get(valueKey));
      out.put(labelColumnName, in.get(labelKey));
      result.add(out);
    }
    TuningUtils.stopTuning(log, "get");
    return result;
  }

////////////////////////////////////////////////////////////////////////////////
//        PROTECTED METHODS
////////////////////////////////////////////////////////////////////////////////

  /**
   * Get the Object cache type
   * @return Object cache type
   */
  protected String getType()
  {
    return "DbCollection";
  }

}