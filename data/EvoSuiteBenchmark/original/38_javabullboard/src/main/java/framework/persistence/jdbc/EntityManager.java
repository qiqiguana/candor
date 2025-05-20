package framework.persistence.jdbc;

import java.sql.Connection;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.apache.xerces.dom.DocumentImpl;
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

import framework.util.StringUtils;
import framework.util.XMLUtils;
import framework.util.jdbc.JDBCUtils;

/**
 * EntityManager is in charge of all entities lifecycle:
 * - Create entities
 * - Write entities as files
 * - Read entities from files
 * - Validate entities
 * - Delete entities files
 * - Put entities in cache
 * - Remove entities from cache
 * EntityManager is a singleton class, 
 * get an instance with the getInstance() method
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.6 $ $Date: 2004/06/17 23:28:50 $
 */
public final class EntityManager extends ComponentManager
{

  // Logger
  private static Log log = LogFactory.getLog(EntityManager.class);

////////////////////////////////////////////////////////////////////////////////
//        SINGLETON
////////////////////////////////////////////////////////////////////////////////

  // Singleton
  private static EntityManager instance;

  /**
   * Return the instance of EntityManager
   * @return EntityManager
   */
  public static EntityManager getInstance()
  {
    if (instance==null) instance = new EntityManager();
    return instance;
  }

  /**
   * Private constructor
   */  
  private EntityManager()
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
    return "Entity";
  }

  /**
   * Create a entity
   * @param connection JDBC connection
   * @param objectName Name of the database object
   * @param packageName Package name of the component
   * @param comment Comments
   * @return The entity
   */
  public Entity create( Connection connection, 
                        String objectName, 
                        String packageName,
                        String targetClassName,
                        String comment)
  throws Exception
  {
    // Create a standard component
    String sql = "select * from "+objectName;
    String name = StringUtils.getJavaName(objectName);
    Entity entity = (Entity)super.create(connection, name, packageName, targetClassName, comment, sql);

    // Additional settings for entity and its attribute properties...
    entity.setObjectName(objectName);

    // ... get the primary keys of the table
    Map primaryKeys = new HashMap();
    Collection primaryKeyList = JDBCUtils.getTablePrimaryKeys(connection, objectName);
    Iterator itPk = primaryKeyList.iterator();
    while (itPk.hasNext()) 
    {
      Map pk = (Map)itPk.next();
      primaryKeys.put(pk.get("COLUMN_NAME"), pk.get("KEY_SEQ"));
    }

    // ... set the primary keys attribute properties
    Collection columnList = JDBCUtils.getTableColumns(connection, objectName);
    Iterator itCol = columnList.iterator();
    while (itCol.hasNext()) 
    {
      Map col = (Map)itCol.next();

      String columnName = String.valueOf(col.get("COLUMN_NAME"));
      String columnIsNullable = String.valueOf(("YES".equals(String.valueOf(col.get("IS_NULLABLE")))));
      String columnIsPrimaryKey = String.valueOf(primaryKeys.get(columnName)!=null);
      String columnKeySeq = String.valueOf(primaryKeys.get(columnName));
      String attributeName = StringUtils.getJavaName(columnName);

      Attribute attribute = entity.getAttribute(attributeName);
      if (attribute!=null)
      {
        attribute.setNullable(columnIsNullable);
        attribute.setPrimaryKey(columnIsPrimaryKey);
        if (attribute.isPrimaryKey()) attribute.setKeySeq(columnKeySeq);
      }
    }

    return entity;
  }

  /**
   * Check if the entity is a valid
   * @param component The entity to be checked
   * @param errors Error stack
   * @return true if the entity is valid, else false
   */
  public boolean isValidInternal(Component component, Map errors)
  throws Exception
  {
    Entity entity = (Entity)component;
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
    return new Entity();
  }
  
  /**
   * Return the cache
   * @return Map representing the cache
   */
/*  protected Map getCache()
  {
    return entityCache;
  }
*/
  /**
   * Get a entity as an DOM XML document
   * @param component The entity to convert
   * @return The XML Document
   */
  protected Document convertDOM(Component component)
  throws Exception
  {
    Entity entity = (Entity)component;
    Document doc = new DocumentImpl();
    Element entityElement = doc.createElement(getType().toLowerCase());
    convertDOM(doc, entityElement, entity);
    entityElement.setAttribute("objectName", entity.getObjectName());

    // Debug Info...
    if (log.isDebugEnabled()) log.debug("convertDOM: doc="+XMLUtils.toString(doc));
    
    return doc;
  }


}