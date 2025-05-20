package framework.persistence.jdbc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

import framework.persistence.jdbc.Entity;
import framework.persistence.jdbc.Attribute;
import framework.util.jdbc.JDBCUtils;
import framework.util.jdbc.Parameter;

/**
 * EntityHelper is the bridge between an Entity object and the Database:
 * - Insert (create) an entity
 * - Update (store) an entity
 * - Select (load) an entity: Note that view.populate() can do it as well
 * - Delete (remove) an entity
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.5 $ $Date: 2004/06/17 23:28:50 $
 */
public final class EntityHelper extends ComponentHelper
{

  // Logger
  private static Log log = LogFactory.getLog(EntityHelper.class);

  /**
   * Private constructor
   */
  protected EntityHelper()
  {
  }

  /**
   * Get the primary keys as a collection of parameter
   * @param entity The entity object to get the primary keys definition from
   * @param target The map containing the values of the primary keys
   * @return Collection of parameters
   */
  private static Collection getPrimaryKeysAsParameters(Entity entity, Map target)
  {
    // Identify the primary keys as parameters
    // Where clause operator is '=' and case sensitive
    Collection parameters = new ArrayList();
    Collection keys = entity.getPrimaryKeys();
    Iterator it = keys.iterator();
    while (it.hasNext()) 
    {
      Attribute attribute = (Attribute)it.next();
      String name = attribute.getName();
      Object value = target.get(name);
      if (attribute!=null && value!=null)
      {
        String columnName = attribute.getColumnName();
        parameters.add(new Parameter(name, columnName, "=", value, "true"));
      }
      else 
      {
        if (attribute==null) log.warn("getPrimaryKeysAsParameters: Attribute not found "+name);
        if (value==null) log.warn("getPrimaryKeysAsParameters: Primary key "+name+" value is null, parameter is not set");
      }
    }
    return parameters;
  }

  /**
   * Load a row into the target object following the entity definition 
   * and the primary key values contained in the target Map
   * @param connection JDBC connection
   * @param entity The entity object to get the primary keys definition from
   * @param target The map containing the values of the primary keys
   */
  protected static void load(Connection connection, Entity entity, Map target)
  throws Exception
  {
    String objectName = entity.getObjectName();
    String sql = "select * from "+objectName;

    // Identify the primary keys as parameters
    Collection parameters = getPrimaryKeysAsParameters(entity, target);

    // No where clause for the select? (select all rows???) 
    // Well... this is not the purpose of this method!
    if (parameters.isEmpty()) throw new Exception("This select cannot be called without where clause (No primary key provided)");

    // Execute query
    ResultSet rset = JDBCUtils.executeQuery(connection, sql, null, null, parameters);

    // A SQLException is thrown if no data found !
    if (!rset.next()) throw new SQLException("No rows found");
    // A SQLException is thrown if more than one row is returned !
    if (rset.next()) throw new SQLException("Too many rows found");
    rset.previous();

    // Populate the target
    populate(rset, entity.getAttributes(), target);
  }

  /**
   * Insert a row following the entity definition 
   * and the values contained in the target Map
   * @param connection JDBC connection
   * @param entity The entity object to get the primary keys definition from
   * @param target The map containing the values of the primary keys
   * @return the number of inserted rows
   */
  protected static int create(Connection connection, Entity entity, Map target)
  throws Exception
  {
    String objectName = entity.getObjectName();
    StringBuffer columnNames = new StringBuffer();
    StringBuffer binds = new StringBuffer();
    Collection values = new ArrayList();

    // For each attribute value (column value) ...
    Iterator it = target.keySet().iterator();
    while (it.hasNext()) 
    {
      // Get attribute
      String name = (String)it.next();
      Attribute attribute = entity.getAttribute(name);

      // Only process the defined attributes
      if (attribute!=null)
      {
        // Get column name
        String columnName = attribute.getColumnName();

        // Get value
        Object value = target.get(name);
        values.add(value);

        // Construct columns and binds enumeration as a String
        // Blindly add comma (removed below)
        columnNames.append(columnName).append(",");
        binds.append("?").append(",");
      }
    }
    // Remove the last comma
    columnNames.deleteCharAt(columnNames.length()-1);
    binds.deleteCharAt(binds.length()-1);

    // Construct sql
    String sql = "insert into "+objectName+" ("+columnNames+") values ("+binds+")";

    // Execute update
    int result = JDBCUtils.executeUpdate(connection, sql, null, values, null);

    return result;
  }

  /**
   * Update one or more rows following the entity definition 
   * and the primary keys values contained in the target Map
   * @param connection JDBC connection
   * @param entity The entity object to get the primary keys definition from
   * @param target The map containing the values of the primary keys
   * @return the number of updated rows
   */
  protected static int store(Connection connection, Entity entity, Map target)
  throws Exception
  {
    // Identify the primary keys as parameters
    Collection parameters = getPrimaryKeysAsParameters(entity, target);

    // No where clause for the update ? Hmmm... better not execute that!
    if (parameters.isEmpty()) throw new Exception("An update was called without where clause (No primary key provided)");

    int result = store(connection, entity, target, parameters);

    return result;
  }
  
  /**
   * Update one or more rows with the values contained in the target Map 
   * following the entity definition and the parameters provided.
   * @param connection JDBC connection
   * @param entity The entity object to get the primary keys definition from
   * @param target The map containing the values
   * @param parameters Collection of parameters
   * @return the number of updated rows
   */
  protected static int store(Connection connection, Entity entity, Map target, Collection parameters)
  throws Exception
  {
    String objectName = entity.getObjectName();
    StringBuffer columnValues = new StringBuffer();
    Collection values = new ArrayList();

    // For each attribute value (column value) ...
    Iterator it = target.keySet().iterator();
    while (it.hasNext()) 
    {
      // Get attribute
      String name = (String)it.next();
      Attribute attribute = entity.getAttribute(name);
     
      // Only process the defined attributes
      if (attribute!=null)
      {
        // Get column name
        String columnName = attribute.getColumnName();

        // Get value
        Object value = target.get(name);
        values.add(value);

        // Construct columns and binds enumeration as a String
        // Blindly add comma (removed below)
        columnValues.append(columnName).append("=?").append(",");
      }
    }
    // remove the last comma
    columnValues.deleteCharAt(columnValues.length()-1);

    String sql = "update "+objectName+" set "+columnValues;
    int result = JDBCUtils.executeUpdate(connection, sql, null, values, parameters);
    
    return result;
  }

  /**
   * Remove one or more rows following the entity definition 
   * and the primary key values contained in the target Map
   * @param connection JDBC connection
   * @param entity The entity object to get the primary keys definition from
   * @param target The map containing the primary keys values
   * @return the number of removed rows
   */
  protected static int remove(Connection connection, Entity entity, Map target)
  throws Exception
  {
    // Identify the primary keys as parameters
    Collection parameters = getPrimaryKeysAsParameters(entity, target);

    // No where clause for the deletion ? Hmmm... better not execute that!
    if (parameters.isEmpty()) throw new Exception("An update was called without where clause (No primary key provided)");

    int result = remove(connection, entity, parameters);

    return result;
  }

  /**
   * Remove one or more rows following the entity definition 
   * and the parameters provided.
   * @param connection JDBC connection
   * @param entity The entity object to database definitions from
   * @param parameters Collection of parameters
   * @return the number of removed rows
   */
  protected static int remove(Connection connection, Entity entity, Collection parameters)
  throws Exception
  {
    String objectName = entity.getObjectName();
    String sql = "delete from "+objectName;
    int result = JDBCUtils.executeUpdate(connection, sql, null, null, parameters);
    return result;
  }

}