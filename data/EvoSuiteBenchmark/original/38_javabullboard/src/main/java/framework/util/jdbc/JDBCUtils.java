package framework.util.jdbc;

import java.sql.Types;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;
import java.sql.ResultSetMetaData;
import java.math.BigDecimal;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

//import javax.sql.rowset.CachedRowSet;
//import oracle.jdbc.OraclePreparedStatement;

import framework.util.ConvertUtils;
import framework.util.StringUtils;
import framework.util.ObjectUtils;

/**
 * JDBC utility class
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.10 $ $Date: 2004/06/17 23:28:51 $
 */
public final class JDBCUtils 
{

  // Logger
  private static Log log = LogFactory.getLog(JDBCUtils.class);

  // Keywords cache
  private static Map databaseKeywordsCache = new HashMap();

  // Default SQL keywords file
  private static final String DEFAULT_KEYWORD = "default";
  
  // Token delimiters
  private static final String TOKEN_START = "/*";
  private static final String TOKEN_END   = "*/";

  // Available CachedRowSet implementations
  private static final String CACHEDROWSET_ORACLE = "oracle";
  private static final String CACHEDROWSET_SUN = "sun";

  /**
   * Protected constructor
   */
  protected JDBCUtils()
  {
  }

////////////////////////////////////////////////////////////////////////////////
//        PUBLIC METHODS
////////////////////////////////////////////////////////////////////////////////

  /**
   * Get connection meta data
   * @param connection JDBC connection
   * @return Connection information as Map
   */ 
  public static Map getConnectionMetaData(Connection connection)
  throws Exception 
  {
    Map result = new HashMap();
    DatabaseMetaData md = connection.getMetaData();
    result.put("databaseProductName", md.getDatabaseProductName());
    result.put("databaseProductVersion", md.getDatabaseProductVersion());
    result.put("driverName", md.getDriverName());
    result.put("driverVersion", md.getDriverVersion());
    result.put("URL", md.getURL());
    result.put("userName", md.getUserName());
    return result;
  } 

  /**
   * Get the database schemas
   * @param connection JDBC connection
   * @return Collection of Map
   */
  public static Collection getDatabaseSchemas(Connection connection)
  throws Exception 
  {
    DatabaseMetaData md = connection.getMetaData();
    ResultSet resultSet = md.getSchemas();
    Collection result = getResultSetAsCollectionOfMap(resultSet);    
    resultSet.close();
    return result;
  } 

  /**
   * Get the different table types
   * @param connection JDBC connection
   * @return Collection of Map
   */
  public static Collection getTableTypes(Connection connection)
  throws Exception 
  {
    DatabaseMetaData md = connection.getMetaData();
    ResultSet resultSet = md.getTableTypes();
    Collection result = getResultSetAsCollectionOfMap(resultSet);    
    resultSet.close();
    return result;
  } 

  /**
   * Get tables meta data
   * Catalog is not used.
   * Schema pattern is the connection user name.
   * Types are TABLE and VIEW.
   * @param connection JDBC connection
   * @param tableNamePattern Table name, or null
   * @return Collection of Map
   */ 
  public static Collection getDatabaseTables(Connection connection, String tableNamePattern)
  throws Exception 
  {
    String schemaPattern = connection.getMetaData().getUserName();
    String[] types = new String[] {"TABLE", "VIEW"};
    return getDatabaseTables(connection, null, schemaPattern, tableNamePattern, types);
  } 

  /**
   * Get tables meta data
   * @param connection JDBC connection
   * @param catalog "" retrieves tables without a catalog; 
   *  null means catalog is not used in the search
   * @param schemaPattern "" retrieves tables without a schema; 
   *  null means schema is not used in the search
   * @param tableNamePattern Table name, or null
   * @param types "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", 
   *  "LOCAL TEMPORARY", "ALIAS", "SYNONYM" or null
   * @return Collection of Map
   */ 
  public static Collection getDatabaseTables( Connection connection, 
                                              String catalog, 
                                              String schemaPattern, 
                                              String tableNamePattern, 
                                              String[] types)
  throws Exception 
  {
    DatabaseMetaData md = connection.getMetaData();
    ResultSet resultSet = md.getTables(catalog, schemaPattern, tableNamePattern, types);
    Collection result = getResultSetAsCollectionOfMap(resultSet);    
    resultSet.close();
    return result;
  } 

  /**
   * Get the primary keys of a table
   * @param connection JDBC connection
   * @param tableNamePattern Table name, or null
   * @return Collection of Map
   */
  public static Collection getTablePrimaryKeys(Connection connection, String tableNamePattern)
  throws Exception 
  {
    String schemaPattern = connection.getMetaData().getUserName();
    return getTablePrimaryKeys(connection, null, schemaPattern, tableNamePattern);
  } 

  /**
   * Get the primary keys of a table
   * @param connection JDBC connection
   * @param catalog "" retrieves tables without a catalog; 
   *  null means catalog is not used in the search
   * @param schemaPattern "" retrieves tables without a schema; 
   *  null means schema is not used in the search
   * @param tableNamePattern Table name, or null
   * @return Collection of Map
   */
  public static Collection getTablePrimaryKeys( Connection connection, 
                                                String catalog, 
                                                String schemaPattern, 
                                                String tableNamePattern)
  throws Exception 
  {
    DatabaseMetaData md = connection.getMetaData();
    ResultSet resultSet = md.getPrimaryKeys(catalog, schemaPattern, tableNamePattern);
    Collection result = getResultSetAsCollectionOfMap(resultSet);
    resultSet.close();
    return result;    
  } 


  /**
   * Get the primary keys of a table
   * @param connection JDBC connection
   * @param tableNamePattern Table name, or null
   * @return Collection of Map
   */
  public static Collection getTableColumns(Connection connection, String tableNamePattern)
  throws Exception 
  {
    String schemaPattern = connection.getMetaData().getUserName();
    return getTableColumns(connection, null, schemaPattern, tableNamePattern, null);
  } 

  /**
   * Get the primary keys of a table
   * @param connection JDBC connection
   * @param catalog "" retrieves tables without a catalog; 
   *  null means catalog is not used in the search
   * @param schemaPattern "" retrieves tables without a schema; 
   *  null means schema is not used in the search
   * @param tableNamePattern Table name, or null
   * @param columnNamePattern Column name, or null
   * @return Collection of Map
   */
  public static Collection getTableColumns( Connection connection, 
                                            String catalog, 
                                            String schemaPattern, 
                                            String tableNamePattern,
                                            String columnNamePattern)
  throws Exception 
  {
    DatabaseMetaData md = connection.getMetaData();
    ResultSet resultSet = md.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);
    Collection result = getResultSetAsCollectionOfMap(resultSet);
    resultSet.close();
    return result;    
  } 

  /**
   * Retrieve the content of a resultset as a collection of Map
   * @param resultSet The resultset
   * @return Collection of Map
   */
  public static Collection getResultSetAsCollectionOfMap(ResultSet resultSet)
  throws Exception
  {
    Collection result = new ArrayList();
    ResultSetMetaData md = resultSet.getMetaData();
    int nbColumns = md.getColumnCount();
    while (resultSet.next()) 
    {
      Map row = new HashMap();
      for (int i = 1; i <= nbColumns; i++) row.put(md.getColumnName(i), resultSet.getObject(i));
      result.add(row);
    }
    return result;
  }

  /**
   * Retrieve the first column of the first row of a resultset
   * @param resultSet The resultset
   * @return Object
   */
  public static Object getFirstColumnOfFirstRow(ResultSet resultSet)
  throws Exception
  {
    Object result = null;
    resultSet.next();
    result = resultSet.getObject(1);
    return result;
  }
  


  /**
   * Get connection Non SQL92 keywords
   * @param connection JDBC connection
   * @return Map of Strings
   */ 
  public static Map getDatabaseNonSQL92Keywords(Connection connection)
  throws Exception 
  {
    Map result = new HashMap();
    List keywords = StringUtils.stringToList(connection.getMetaData().getSQLKeywords(), ",");
    Iterator it = keywords.iterator();
    while (it.hasNext())
    {
      String word = ((String)it.next()).toUpperCase();
      result.put(word, word);
    }
    return result;
  } 

  /**
   * Get default sql keywords
   * @return Map of Strings
   */ 
  public static Map getDatabaseKeywords()
  throws Exception 
  {    
    return getDatabaseKeywords(DEFAULT_KEYWORD);
  }
  
  /**
   * Get database keywords
   * @param databaseType The database type (oracle, mysql ...etc.)
   * @return Map of Strings
   */ 
  public static Map getDatabaseKeywords(String databaseType)
  throws Exception 
  {
    Map result = (Map)databaseKeywordsCache.get(databaseType);
    if (result==null) 
    {
      String keywordFilename = databaseType.toLowerCase();
      result = StringUtils.getKeywords(keywordFilename);
      databaseKeywordsCache.put(databaseType, result);
    }    
    return result;
  } 

  /**
   * Get next value from a database sequence 
   * @param connection JDBC connection
   * @param sequenceName Name of the sequence
   * @return Sequence next value as BigDecimal
   */ 
  public static synchronized BigDecimal getNextVal(Connection connection, String sequenceName)
  throws Exception 
  {
    BigDecimal result = null;
    String sql = "select "+sequenceName+".nextval from dual";
    
    ResultSet resultSet = executeQuery(connection, sql, null, null, null);
    if (resultSet.next()) result = resultSet.getBigDecimal(1);
    resultSet.close();

    if (result!=null) return result;
    else throw new SQLException("The sequence "+sequenceName+" could not be found");
  } 

  /**
   * Execute count(*) on a query : "select count(*) from (<subquery>) counter"
   * This syntax is common to Oracle and MySQL
   * To be validated with other databases
   * @param connection JDBC connection
   * @param sql The SQL query
   * @return The number of rows in the query
   */ 
  public static int getRowCountFromQuery(Connection connection, 
                                        String sql,
                                        Collection tokenValues,
                                        Object bindVariables,
                                        Collection parameters)
  throws Exception 
  {
    int result = 0;
    String sqlCount = "select count(1) from ("+sql+") counter";
    
    ResultSet resultSet = executeQuery(connection, sqlCount, tokenValues, bindVariables, parameters);
    resultSet.next();
    result = resultSet.getInt(1);
    resultSet.close();

    return result;
  } 

  /**
   * Count the number of rows in a ResultSet
   * This resultset MUST BE SCROLLABLE
   * @param rset The resultset
   * @return Number of rows in the resultset
   */ 
  public static int getRowCountFromResultSet(ResultSet rset)
  throws Exception 
  {
    int result = 0;
    rset.last();
    result = rset.getRow();
    rset.beforeFirst();
    return result;
  } 

  /**
   * Replace bind variables by null
   * This is useful to test a query without setting values in bind variables
   * @param sqlQuery SQL query
   * @return The result SQL query
   */
  public static String replaceBindVariablesWithNull(String sqlQuery)
  {
    return StringUtils.replace(sqlQuery, "?", "null");
  }

  /**
   * Retrieve the resultSet type as a String instead of an int
   * @param resultSetType the int representing ResultSet type
   * @return String representing the resultSet type instead of the ID
   */  
  public static String getResultSetType(int resultSetType)
  throws SQLException
  {
    String result = String.valueOf(resultSetType);
    if (resultSetType==ResultSet.TYPE_FORWARD_ONLY) result += "(TYPE_FORWARD_ONLY)";
    else if (resultSetType==ResultSet.TYPE_SCROLL_INSENSITIVE) result += "(TYPE_SCROLL_INSENSITIVE)";
    else if (resultSetType==ResultSet.TYPE_SCROLL_SENSITIVE) result += "(TYPE_SCROLL_SENSITIVE)";
    else result += "(Unknown)";
    return result;
  }

////////////////////////////////////////////////////////////////////////////////
//        PROTECTED METHODS
////////////////////////////////////////////////////////////////////////////////

  /**
   * Check for a valid parameter
   * @param parameter Parameter to be checked
   * @return true if the parameter is valid, else false
   */  
  protected static boolean isValidParameter(Parameter parameter)
  {
    boolean result = true;
    if (parameter==null) result = false;
/*    else if (!StringUtils.exists(parameter.getName()) && StringUtils.exists(parameter.getColumnName()))
    {
      log.warn("isValidParameter: Name cannot be null if column name is provided ="+parameter);
      result = false;
    }
*/    else if (StringUtils.exists(parameter.getName()) && !StringUtils.exists(parameter.getColumnName()))
    {
      log.warn("isValidParameter: Column name cannot be null if name is provided ="+parameter);
      result = false;
    }
    else if (!StringUtils.exists(parameter.getColumnName()) && StringUtils.exists(parameter.getOperator()))
    {
      log.warn("isValidParameter: Column name cannot be null if operator is provided ="+parameter);
      result = false;
    }
    if (StringUtils.exists(parameter.getColumnName()) && !StringUtils.exists(parameter.getOperator()))
    {
      log.warn("isValidParameter: Operator cannot be null if column name is provided ="+parameter);
      result = false;
    }
    if (!StringUtils.exists(parameter.getColumnName()) && !StringUtils.exists(parameter.getOperator()) && parameter.getValue()==null)
    {
      log.warn("isValidParameter: Value cannot be null if column name and operator is provided ="+parameter);
      result = false;
    }
    return result;
  }

  /**
   * Execute a query
   * @param connection JDBC connection
   * @param sql SQL query
   * @param tokenValues Collection of tokens
   * @param bindVariables Collection/Array/Object of bind variables
   * @param parameters Collection of parameters
   * @return The ResultSet
   */
  public static ResultSet executeQuery(Connection connection, 
                                        String sql, 
                                        Collection tokenValues, 
                                        Object bindVariables, 
                                        Collection parameters)
  throws Exception 
  {
    return executeQuery(connection, sql, tokenValues, bindVariables, parameters, ResultSet.TYPE_FORWARD_ONLY);
  }
  
  public static ResultSet executeQuery(Connection connection, 
                                        String sql, 
                                        Collection tokenValues, 
                                        Object bindVariables, 
                                        Collection parameters,
                                        int scrollType)
  throws Exception 
  {
    PreparedStatement statement = prepareStatement(connection, sql, tokenValues, bindVariables, parameters, scrollType);
    ResultSet resultSet = statement.executeQuery();
    return resultSet;
  }

  /**
   * Execute an update
   * @param connection JDBC connection
   * @param sql SQL query
   * @param tokenValues Collection of tokens
   * @param bindVariables Collection/Array/Object of bind variables
   * @param parameters Collection of parameters
   * @return The number of rows processed
   */
  public static int executeUpdate(Connection connection, 
                                  String sql, 
                                  Collection tokenValues, 
                                  Object bindVariables, 
                                  Collection parameters)
  throws Exception 
  {
    return executeUpdate(connection, sql, tokenValues, bindVariables, parameters, ResultSet.TYPE_FORWARD_ONLY);
  }
  
  public static int executeUpdate(Connection connection, 
                                  String sql, 
                                  Collection tokenValues, 
                                  Object bindVariables, 
                                  Collection parameters,
                                  int scrollType)
  throws Exception 
  {
    PreparedStatement statement = prepareStatement(connection, sql, tokenValues, bindVariables, parameters, scrollType);
    int result = statement.executeUpdate();
    return result;
  }

  /**
   * Populate the target object with the resultSet content following 
   * the attributes definitions
   * @param resultSet The source resultSet
   * @param attributes The Map of attributes definitions
   * @param target The target object
   */
/*  protected static void populate(ResultSet resultSet, Map attributes, Object target)
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
*/

////////////////////////////////////////////////////////////////////////////////
//        PRIVATE METHODS
////////////////////////////////////////////////////////////////////////////////

  private static String placeParameter(String in, String stringParameter)
  {
    String tempSql = in.toUpperCase();
    int iWhere = tempSql.lastIndexOf("WHERE");
    if (iWhere<0)
    {
      int iGroupBy = tempSql.lastIndexOf("GROUP BY");
      if (iGroupBy<0)
      {
        int iHaving = tempSql.lastIndexOf("HAVING");
        if (iHaving<0)
        {
          int iOrderBy = tempSql.lastIndexOf("ORDER BY");
          if (iOrderBy<0)
          {
            return in+" where "+stringParameter;
          }
          else return in.substring(0, iOrderBy)+"where "+stringParameter+in.substring(iOrderBy-1);
        }
        else return in.substring(0, iHaving)+"where "+stringParameter+in.substring(iHaving-1);
      }
      else return in.substring(0, iGroupBy)+"where "+stringParameter+in.substring(iGroupBy-1);
    }
    else return in.substring(0, iWhere)+"where "+stringParameter+" and "+in.substring(iWhere+"WHERE".length());
  }

  /**
   * Build a standard parameter
   * GROUP BY, HAVING and ORDER BY clauses cannot be build with this method
   * @param columnName Column name to filter
   * @param operator Operator of filter
   * @param value Filter value
   * @param nextValue Next value if operator is BETWEEN, isCaseSensitive if operator is LIKE
   * @param bindVariables The collection of bind variables
   * @return The result SQL query
   */  
  private static String buildParameter( String columnName, 
                                        String operator, 
                                        Object value, 
                                        Object nextValue, 
                                        Collection bindVariables)
  throws Exception
  {
    if (log.isDebugEnabled()) log.debug("buildParameter: columnName="+columnName+", operator="+operator+", value="+value+", nextValue="+nextValue);
    StringBuffer out = new StringBuffer();
    
    if (!StringUtils.exists(columnName) || !StringUtils.exists(operator))
    {
      out.append(value);
    }
    else
    {
      if (value instanceof String)
      {
        boolean isCaseSensitive = ConvertUtils.convertBooleanType(ConvertUtils.convertString(nextValue), false);
        // Escape quotes in value
        String stringValue = StringUtils.replace(ConvertUtils.convertString(value),"'","''");
        stringValue = "'"+stringValue+"'";
        if (isCaseSensitive) out.append(columnName).append(" ").append(operator).append(" ").append(stringValue);
        else out.append(" and upper(").append(columnName).append(") ").append(operator).append(" ").append(stringValue.toUpperCase());
      }
      else if ((value instanceof java.sql.Date) || (value instanceof java.sql.Timestamp) || (value instanceof java.sql.Time))
      {
        out.append(columnName).append(" ").append(operator).append(" ?");
        bindVariables.add(value);
        if ("BETWEEN".equalsIgnoreCase(operator)) 
        { 
          out.append(" and ?"); 
          bindVariables.add(nextValue); 
        }
      }
      else
      {
        out.append(columnName).append(" ").append(operator).append(" ").append(value);
        if ("BETWEEN".equalsIgnoreCase(operator)) out.append(" and ").append(nextValue);
      }
    }
    return out.toString();
  }

  /**
   * Add parameters to a SQL query
   * @param sql SQL query
   * @param parameters Collection of parameters
   * @param useWrapping true to wrap the original query with the parameters, 
   *    false to add the parameters
   * @return The result SQL query
   */  
  private static String addParameters(String sql, 
                                      Collection parameters, 
                                      Collection bindVariables)
  throws Exception
  {
    return addParameters(sql, parameters, bindVariables, true);
  }
  
  private static String addParameters(String sql, 
                                      Collection parameters, 
                                      Collection bindVariables,
                                      boolean useWrapping)
  throws Exception
  {
    if (parameters==null || parameters.size()<=0)
    {
      if (log.isDebugEnabled()) log.debug("addParameters: No parameters to add");
      return sql;
    }
/*    
    boolean hasWhereClause = sql.toUpperCase().indexOf("WHERE")>=0;
    String resultSql = sql+(hasWhereClause?"":" where 1=1 ");
*/
    String resultSql = sql;
    String parameterSql = "";
    String orderByColumn = null;
    String orderByDirection = null;
    boolean needWhereKeyword = useWrapping;

    Iterator it = parameters.iterator();
    while (it.hasNext()) 
    {
      Parameter parameter = (Parameter)it.next();
      if (parameter.isValid())
      {
        // Get column name
        String columnName = parameter.getColumnName()!=null?parameter.getColumnName():parameter.getName();
        if (columnName!=null) columnName = columnName.trim().toUpperCase();

        // Get operator
        String operator = parameter.getOperator();
        if (operator!=null) operator = operator.trim().toUpperCase();

        // Get value
        Object value = parameter.getValue();

        // Get next value
        Object nextValue = parameter.getNextValue();
  
        // Get ORDER BY clause
        if (Parameter.OPERATOR_ORDER_BY.equalsIgnoreCase(operator)) 
        {
          orderByColumn = columnName;
          if (value!=null) 
          {
            String stringValue = ConvertUtils.convertString(value).trim().toUpperCase();
            orderByDirection = Parameter.VALUE_ASC.equals(stringValue)?Parameter.VALUE_ASC:Parameter.VALUE_DESC;
          }
        }
        // Get STANDARD WHERE clause
        else 
        {
          parameterSql += (needWhereKeyword?"\nwhere ":"\nand ");
          parameterSql += buildParameter(columnName, operator, value, nextValue, bindVariables);
          needWhereKeyword = false;
        }
      }
    }

    if (useWrapping) resultSql = "select * from \n(\n"+resultSql+"\n) qrslt ";
    resultSql += parameterSql;

    // Set order by clause
    if (orderByColumn!=null) resultSql += " "+Parameter.OPERATOR_ORDER_BY+" "+orderByColumn+" "+orderByDirection;

    return resultSql;
  }

  /**
   * Replace tokens in the sql query
   * A token is a array of two Objects: new Object[]  {tokenName, tokenValue}
   * Constants TOKEN_START and TOKEN_END are the token delimiters
   * @param sqlQuery SQL query where to replace tokens
   * @param tokenValues Collection of tokens
   * @return The result SQL query
   */
  private static String replaceTokens(String sqlQuery, Collection tokenValues)
  {
    return StringUtils.replaceTokens(sqlQuery, tokenValues, TOKEN_START, TOKEN_END);
  }

  /**
   * Binds varibles in the preparedStatement
   * @param preparedStatement The prepared statement
   * @param bindVariables Collection of bind variables
   */
  private static void setBindVariables(PreparedStatement preparedStatement, Collection bindVariables)
  throws SQLException
  {
    if (bindVariables==null || bindVariables.isEmpty()) 
    {
      if (log.isDebugEnabled()) log.debug("setBindVariables: No variables to bind (null or empty list received)");
    }
    else
    {
      int i = 0;
      Iterator it = bindVariables.iterator();
      while (it.hasNext())
      {
        Object bindVariable = it.next();
        if (bindVariable!=null) 
        {
          i++;
          if (log.isDebugEnabled()) log.debug("setInternalBindVariables: bindVariables["+i+"]="+bindVariable+" ("+bindVariable.getClass().getName()+")");
          preparedStatement.setObject(i, bindVariable);
        }
        else 
        {
// MAYBE USELESS, CHECK PREPAREDSTATEMENT SOURCE CODE
          i++;
          if (log.isDebugEnabled()) log.debug("setInternalBindVariables: bindVariables["+i+"]=null");
          preparedStatement.setNull(i, Types.VARCHAR);
        }
      }
    }
  }

  /**
   * Get a prepared statement ready with:
   * 1 - token values replaced
   * 2 - parameters added
   * 3 - bind variables set
   * @param Connection JDBC connection
   * @param sql SQL query
   * @param tokenValues Collection of tokens to be replaced in query
   * @param bindVariables Collection/Array/Object of bind variables to be set
   * @param parameters Collection of parameters to be added to the query
   * @return PreparedStatement
   */  
  private static PreparedStatement prepareStatement(Connection connection, 
                                                  String sql, 
                                                  Collection tokenValues,
                                                  Object bindVariables,
                                                  Collection parameters,
                                                  int scrollType)
  throws Exception
  {
    if (connection==null) throw new Exception("Connection is null!");

    if (log.isDebugEnabled()) log.debug("prepareStatement: original sql="+sql);

//TuningUtils.startTuning("prepareStatement");

    sql = replaceTokens(sql, tokenValues);
    if (log.isDebugEnabled()) log.debug("prepareStatement: after token processing sql="+sql);

    Collection binds = ObjectUtils.toCollection(bindVariables);
    if (binds==null) binds = new ArrayList();

    sql = addParameters(sql, parameters, binds);
    if (log.isDebugEnabled()) log.debug("prepareStatement: after parameters processing sql="+sql);

    PreparedStatement statement = connection.prepareStatement(sql, scrollType, ResultSet.CONCUR_READ_ONLY);

    setBindVariables(statement, binds);

//TuningUtils.stopTuning("prepareStatement");

    return statement;
  }

  /**
   * Populate the target map with the resultSet content following 
   * the attributes definitions
   * @param resultSet The source resultSet
   * @param attributes The Map of attributes definitions
   * @param target The target Map
   */
/*  
  private static void populate(ResultSet resultSet, Map attributes, Map target)
  throws Exception
  {
    TuningUtils.startTuning(log, "populate");

    Iterator it = attributes.values().iterator();
    while (it.hasNext())
    {
      Attribute attribute = (Attribute)it.next();
      String name = attribute.getName();
      String sqlType = attribute.getColumnTypeName();
      Class targetClass = ObjectUtils.forName(attribute.getColumnClassName());

      Object value = resultSet.getObject(attribute.getColumnName());
      if (value!=null)
      {
        Class sourceClass = value.getClass();
        if (log.isDebugEnabled()) log.debug("populate: name="+name+", sqlType="+sqlType+", sourceClass="+sourceClass.getName()+", value="+value+", targetClass="+targetClass.getName());
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
*/
}
