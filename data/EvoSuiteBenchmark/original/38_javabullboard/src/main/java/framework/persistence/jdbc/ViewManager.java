package framework.persistence.jdbc;

import java.sql.Connection;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.CDATASection;

import org.apache.xerces.dom.DocumentImpl;
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

import framework.util.XMLUtils;
import framework.util.ConvertUtils;
import framework.ApplicationParameters;

/**
 * EntityManager is a singleton class in charge of all views lifecycle:
 * - Create views
 * - Write views as files
 * - Read views from files
 * - Validate views
 * - Delete views files
 * - Put views in cache
 * - Remove views from cache
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.8 $ $Date: 2004/06/17 23:28:50 $
 */
public final class ViewManager extends ComponentManager
{

  // Logger
  private static Log log = LogFactory.getLog(ViewManager.class);

  public int getDefaultMaxFetchSize()
  {    
    return ApplicationParameters.getAsInt("framework.persistence.jdbc.view.tuning.defaultMaxFetchSize", 0);
  }

////////////////////////////////////////////////////////////////////////////////
//        SINGLETON
////////////////////////////////////////////////////////////////////////////////

  // Singleton
  private static ViewManager instance;

  /**
   * Return the instance of EntityManager
   * @return EntityManager
   */
  public static ViewManager getInstance()
  {
    if (instance==null) instance = new ViewManager();
    return instance;
  }

  /**
   * Private constructor
   */  
  protected ViewManager()
  {
    super();
  }

////////////////////////////////////////////////////////////////////////////////
//        PUBLIC METHODS
////////////////////////////////////////////////////////////////////////////////

  /**
   * Get the component type
   * @return Component type
   */
  public String getType()
  {
    return "View";
  }

  /**
   * Create a view
   * @param connection JDBC connection
   * @param name Name of the view
   * @param packageName Package name of the view
   * @param comment Comments
   * @param sql SQL Query
   * @return The view
   */
  public Component create( Connection connection, 
                            String name, 
                            String packageName, 
                            String targetClassName,
                            String comment, 
                            String sql)
  throws Exception
  {
    View view = (View)super.create(connection, name, packageName, targetClassName, comment, sql);
    return view;
  }

  /**
   * Dynamically get a view instead reading a view file
   * Manage view cache
   * @param connection JDBC connection
   * @param name Name of the view
   * @param packageName Package name of the view
   * @param comment Comments
   * @param sql SQL Query
   * @return The view
   */
  public View getViewFromQuery(Connection connection, 
                                String name, 
                                String packageName, 
                                String targetClassName,
                                String comment,
                                String sql)
  throws Exception
  {
    // Get from cache
    String key = getFullName(name, packageName);
    Object[] args = new Object[] {connection, name, packageName, targetClassName, comment, sql};
    Class[] parameterTypes = new Class[] {Connection.class, String.class, String.class, String.class, String.class, String.class};
    View view = (View)getOrLoad(key, this, "create", args, parameterTypes);
/*
    View view = (View)getFromCache(key);
    if (view==null) 
    {
      // If not found in cache, create a view
      view = (View)create(connection, name, packageName, targetClassName, comment, sql);
    
      key = view.getFullName();
      putIntoCache(key, view);
    }
*/
    return (View)view.clone();
  }

  /**
   * Check if the view is a valid
   * @param component The view to be checked
   * @param errors Error stack
   * @return true if the view is valid, else false
   */
  public boolean isValidInternal(Component component, Map errors)
  throws Exception
  {
    View view = (View)component;
    boolean result = true;

    return result;    
  }


////////////////////////////////////////////////////////////////////////////////
//        PROTECTED METHODS
////////////////////////////////////////////////////////////////////////////////

  /**
   * Get a new entity instance
   * @return Entity
   */
  protected Component getNew()
  {
    return new View();
  }
  
  /**
   * Get a view as an DOM XML document
   * @param component The view to convert
   * @return The XML Document
   */
  protected Document convertDOM(Component component)
  throws Exception
  {
    View view = (View)component;
    Document doc = new DocumentImpl();
    Element viewElement = doc.createElement(getType().toLowerCase());

    Element sqlQuery = doc.createElement("sqlQuery");
    CDATASection cdataSqlQuery = doc.createCDATASection("\n"+view.getSqlQuery()+"\n");
    sqlQuery.appendChild(cdataSqlQuery);
    viewElement.appendChild(sqlQuery);

    Element vlhElement = doc.createElement("valueListHandler");
    vlhElement.setAttribute("retrieveRowCount", ConvertUtils.convertString(view.hasRetrieveRowCount()));
    vlhElement.setAttribute("rowCountMethod", view.getRowCountMethod());
    vlhElement.setAttribute("rowsPerPage", ConvertUtils.convertString(view.getRowsPerPage()));
    viewElement.appendChild(vlhElement);

    Element tuningElement = doc.createElement("tuning");
//    tuningElement.setAttribute("fetchSize", ConvertUtils.convertString(view.getFetchSize()));
    tuningElement.setAttribute("maxFetchSize", ConvertUtils.convertString(view.getMaxFetchSize()));
//    tuningElement.setAttribute("maxRows", ConvertUtils.convertString(view.getMaxRows()));
    viewElement.appendChild(tuningElement);

    convertDOM(doc, viewElement, view);

    // Debug Info...
    if (log.isDebugEnabled()) log.debug("convertDOM: doc="+XMLUtils.toString(doc));
    
    return doc;
  }


////////////////////////////////////////////////////////////////////////////////
//        GENERATION TOOL METHODS
////////////////////////////////////////////////////////////////////////////////

}