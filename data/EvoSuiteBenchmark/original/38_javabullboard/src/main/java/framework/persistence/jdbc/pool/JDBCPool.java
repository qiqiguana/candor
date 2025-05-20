package framework.persistence.jdbc.pool;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

import framework.ApplicationParameters;
import framework.util.StringUtils;
import framework.util.TuningUtils;

/**
 * Abstract JDBC Pool to extends
 * For security and performance reasons, the pool parameters are not 
 * dynamically reloadable.
 * The pool have to be re-created to have the new parameters set.
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.7 $ $Date: 2004/06/17 23:28:51 $
 */
public abstract class JDBCPool implements JDBCPoolConstants
{

  // Logger
  protected static Log log = LogFactory.getLog(JDBCPool.class);
 
  // Pool Name
  protected String poolName;
  protected int debugLevel;
  protected boolean doValidation;
  protected String databaseType;
//  protected String type;
  protected String className;
  protected String url;
  protected String driver;
  protected String username;
  protected String password;
  protected int maxLimit;
  protected int minLimit;
  protected String queryValidation;
  protected String queryUsername;
  protected String queryTimestamp;
  protected boolean isAutocommit;
  protected Map typeMap;
  
  protected JDBCPool()
  {
  }
  
////////////////////////////////////////////////////////////////////////////////
//        PUBLIC METHODS
////////////////////////////////////////////////////////////////////////////////

  public String getPoolName()
  {
    return poolName;
  }

  public void setPoolName(String newPoolName)
  {
    poolName = newPoolName;
  } 

  public int getDebugLevel()
  {
    return debugLevel;
  }

  public boolean doValidation()
  {
    return doValidation;
  }

  public String getDatabaseType()
  {
    return databaseType;
  }

  public String getUrl()
  {
    return url;
  }

  public String getDriver()
  {
    return driver;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

  public int getMaxLimit()
  {
    return maxLimit;
  }

  public int getMinLimit()
  {
    return minLimit;
  }

  public boolean isAutocommit()
  {
    return isAutocommit;
  }

  public Map getTypeMap()
  {
    return typeMap;
  }

  public String getQueryValidation()
  {
    return queryValidation;
  }

  public String getQueryUsername()
  {
    return queryUsername;
  }

  public String getQueryTimestamp()
  {
    return queryTimestamp;
  }

  /**
   * Set connection properties
   */
  public void setConnectionProperties(Connection connection)
  throws Exception
  {
    TuningUtils.startTuning(log, "setConnectionProperties");
    // Setting autocommit incurs the cost of a network round trip to the database server
    if (connection.getAutoCommit()!=isAutocommit()) connection.setAutoCommit(isAutocommit());

    // Set custom type map
    connection.setTypeMap(getTypeMap());
    if (log.isDebugEnabled()) log.debug("setConnectionProperties: typeMap="+connection.getTypeMap());
    TuningUtils.stopTuning(log, "setConnectionProperties");
  }
  
  /**
   * Get a JDBC Connection from the Pool using default connection credentials
   * @return JDBC Connection
   */
  public Connection getConnection()
  throws Exception
  {
    return getConnection(null, null);
  }

  /**
   * Get a JDBC Connection from the Pool
   * @param username Username
   * @param password Password
   * @return JDBC Connection
   */
  public Connection getConnection(String username, String password)
  throws Exception
  {
    TuningUtils.startTuning(log, "getConnection");
    Connection connection = null;
    if (username!=null && password!=null) getInternalConnection(username, password);
    else connection = getInternalConnection();
    if (doValidation()) validateConnection(connection);
    setConnectionProperties(connection);
    TuningUtils.stopTuning(log, "getConnection");
    return connection;
  }

  /**
   * Return a JDBC Connection to the Pool
   * @param connection Connection to be returned
   */
  public void releaseConnection(Connection connection)
  {
    try
    {
      if (connection!=null) connection.close();
      if (log.isDebugEnabled()) log.debug("releaseConnection: "+dumpPoolStatistics());
    }
    catch (Exception e)
    {
      log.error("releaseConnection: Could not release connection !", e);
    }
  }

  /**
   * Pool statistics
   * @return Pool statistics as a String
   */
  public String dumpPoolStatistics()
  {
    StringBuffer sb = new StringBuffer("'");
    sb.append(poolName).append("' pool statistics: ");
    sb.append(countActiveConnections()).append(" actives + ");
    sb.append(countIdleConnections()).append(" idles = ");
    sb.append(getPoolSize()).append(" total connections");
    return sb.toString();
  }

////////////////////////////////////////////////////////////////////////////////
//        PROTECTED METHODS
////////////////////////////////////////////////////////////////////////////////

  /**
   * Initialization
   */
  protected void initialize()
  throws Exception
  {
    String propertyName = null;
    String substitutePropertyName = null;
    Object defaultValue = null;
    Object value = null;
    
    // Debug level
    propertyName = "framework.persistence.jdbc.pool."+poolName+".debugLevel";
    substitutePropertyName = "framework.persistence.jdbc.pool."+DEFAULT_POOL_NAME+".debugLevel";
    defaultValue = DEFAULT_DEBUG_LEVEL;
    value = ApplicationParameters.getWhatYouCan(propertyName, substitutePropertyName, defaultValue);
    debugLevel = ((Integer)value).intValue();
    
    // Do connection validation?
    propertyName = "framework.persistence.jdbc.pool."+poolName+".tuning.doValidation";
    substitutePropertyName = "framework.persistence.jdbc.pool."+DEFAULT_POOL_NAME+".tuning.doValidation";
    defaultValue = DEFAULT_DO_VALIDATION;
    value = ApplicationParameters.getWhatYouCan(propertyName, substitutePropertyName, defaultValue);
    doValidation = ((Boolean)value).booleanValue();

    // Database type
    databaseType = ApplicationParameters.getAsMandatoryString("framework.persistence.jdbc.pool."+poolName+".databaseType");
    url = ApplicationParameters.getAsMandatoryString("framework.persistence.jdbc.pool."+poolName+".url");    
    driver = ApplicationParameters.getAsMandatoryString("framework.persistence.jdbc.pool."+poolName+".driver");
    username = ApplicationParameters.getAsMandatoryString("framework.persistence.jdbc.pool."+poolName+".username");
    password = ApplicationParameters.getAsMandatoryString("framework.persistence.jdbc.pool."+poolName+".password");

    // Max connection limit
    propertyName = "framework.persistence.jdbc.pool."+poolName+".maxLimit";
    substitutePropertyName = "framework.persistence.jdbc.pool."+DEFAULT_POOL_NAME+".maxLimit";
    defaultValue = DEFAULT_MAX_LIMIT;
    value = ApplicationParameters.getWhatYouCan(propertyName, substitutePropertyName, defaultValue);
    maxLimit = ((Integer)value).intValue();

    // Min connection limit
    propertyName = "framework.persistence.jdbc.pool."+poolName+".minLimit";
    substitutePropertyName = "framework.persistence.jdbc.pool."+DEFAULT_POOL_NAME+".minLimit";
    defaultValue = DEFAULT_MIN_LIMIT;
    value = ApplicationParameters.getWhatYouCan(propertyName, substitutePropertyName, defaultValue);
    minLimit = ((Integer)value).intValue();

    // Is connection autocommit?
    propertyName = "framework.persistence.jdbc.pool."+poolName+".isAutocommit";
    substitutePropertyName = "framework.persistence.jdbc.pool."+DEFAULT_POOL_NAME+".isAutocommit";
    defaultValue = DEFAULT_IS_AUTOCOMMIT;
    value = ApplicationParameters.getWhatYouCan(propertyName, substitutePropertyName, defaultValue);
    isAutocommit = ((Boolean)value).booleanValue();

    // Connection type mapping
    List typeMapList = ApplicationParameters.findNodeList("framework.persistence.jdbc.pool."+poolName+".tuning.typeMap");
    Map typeMap = new HashMap();
    Iterator it = typeMapList.iterator();
    while (it.hasNext()) 
    {
      Node node = (Node)it.next();
      String nodeName = ApplicationParameters.getValue(ApplicationParameters.getNode(node, "name"), true);
      String nodeValue = ApplicationParameters.getValue(ApplicationParameters.getNode(node, "value"), false);
      if (StringUtils.exists(nodeName)) typeMap.put(nodeName, nodeValue);
    }

    // Validation query
    propertyName = "framework.persistence.jdbc.pool."+poolName+".query.validation";
    substitutePropertyName = "framework.persistence.jdbc.pool."+DEFAULT_POOL_NAME+".query.validation";
    defaultValue = DEFAULT_QUERY_USERNAME;
    value = ApplicationParameters.getWhatYouCan(propertyName, substitutePropertyName, defaultValue);
    queryValidation = (String)value;

    // Username query
    propertyName = "framework.persistence.jdbc.pool."+poolName+".query.username";
    substitutePropertyName = "framework.persistence.jdbc.pool."+DEFAULT_POOL_NAME+".query.username";
    defaultValue = DEFAULT_QUERY_USERNAME;
    value = ApplicationParameters.getWhatYouCan(propertyName, substitutePropertyName, defaultValue);
    queryUsername = (String)value;

    // Timestamp query
    propertyName = "framework.persistence.jdbc.pool."+poolName+".query.timestamp";
    substitutePropertyName = "framework.persistence.jdbc.pool."+DEFAULT_POOL_NAME+".query.timestamp";
    defaultValue = DEFAULT_QUERY_TIMESTAMP;
    value = ApplicationParameters.getWhatYouCan(propertyName, substitutePropertyName, defaultValue);
    queryTimestamp = (String)value;

    if (log.isDebugEnabled()) 
    {
      StringBuffer sb = new StringBuffer("initialize: ");
      sb.append("PoolName=").append(getPoolName());
      sb.append(", DatabaseType=").append(getDatabaseType());
      sb.append(", PoolClassName=").append(getClass().getName());
      sb.append(", Url=").append(getUrl());
      sb.append(", Username=").append(getUsername());
      sb.append(", Password=").append(getPassword());
      log.debug(sb.toString());
    }

    internalInitialize();
  }

  /**
   * Validate connection
   */
  protected boolean validateConnection(Connection connection)
  throws Exception
  {
    TuningUtils.startTuning(log, "validateConnection");
    Statement stmt = connection.createStatement();
    boolean result = stmt.execute(getQueryValidation());
    TuningUtils.stopTuning(log, "validateConnection");
    return result;
  }

////////////////////////////////////////////////////////////////////////////////

  /**
   * Initialization
   */
  protected abstract void internalInitialize()
  throws Exception;
  
  /**
   * Get a JDBC Connection from the Pool
   * @return JDBC Connection
   */
  protected abstract Connection getInternalConnection()
  throws Exception;

  /**
   * Get a JDBC Connection from the Pool
   * @param username Username
   * @param password Password
   * @return JDBC Connection
   */
  protected abstract Connection getInternalConnection(String username, String password)
  throws Exception;

  /**
   * Set the maximum number of connections in the pool
   */  
  protected abstract void setMaxLimit(int maxLimit) 
  throws Exception;

  /**
   * Set the minimum number of connections in the pool
   */  
  public abstract void setMinLimit(int minLimit)
  throws Exception;

  /**
   * Return the number of active connections
   * @return Number of active connections
   */  
  public abstract int countActiveConnections();

  /**
   * Return the number of idle connections
   * @return Number of idle connections
   */  
  public abstract int countIdleConnections();

  /**
   * Return the number of existing connections in pool
   * @return Pool size
   */  
  public int getPoolSize()
  {
    return countActiveConnections()+countIdleConnections();
  }


}