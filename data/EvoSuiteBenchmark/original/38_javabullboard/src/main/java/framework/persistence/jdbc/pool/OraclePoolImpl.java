package framework.persistence.jdbc.pool;

import java.sql.Connection;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import oracle.jdbc.pool.OracleConnectionCacheImpl;
import oracle.jdbc.OracleDatabaseMetaData;

/**
 * JDBCPool Oracle pool implementation
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.3 $ $Date: 2004/05/28 00:08:50 $
 */
public class OraclePoolImpl extends JDBCPool
{

  private OracleConnectionCacheImpl occi = null;

  /**
   * Initialization
   */
  protected void internalInitialize()
  throws Exception
  {
    OracleConnectionPoolDataSource opcds = new OracleConnectionPoolDataSource();
    opcds.setURL(getUrl());
    opcds.setUser(getUsername());
    opcds.setPassword(getPassword());
    occi = new OracleConnectionCacheImpl(opcds);
    occi.setCacheScheme(OracleConnectionCacheImpl.DYNAMIC_SCHEME);
/*
    occi.setMaxStatements();
    occi.setStmtCacheSize();
    occi.setExplicitCachingEnabled();
    occi.setImplicitCachingEnabled();
*/
  }

  /**
   * Return the number of active connections
   * @return Number of active connections
   */  
  public int countActiveConnections()
  {
    return occi.getActiveSize();
  }

  /**
   * Return the number of idle connections
   * @return Number of idle connections
   */  
  public int countIdleConnections()
  {
    return occi.getCacheSize()-countActiveConnections();
  }

  /**
   * Set the maximum number of connections in the pool
   */  
  public void setMaxLimit(int maxLimit)
  throws Exception
  {
    occi.setMaxLimit(maxLimit);
  }

  /**
   * Set the minimum number of connections in the pool
   */  
  public void setMinLimit(int minLimit)
  throws Exception
  {
    occi.setMinLimit(minLimit);
  }

  /**
   * Get a JDBC Connection from the Pool
   * @return JDBC Connection
   */
  protected Connection getInternalConnection()
  throws Exception
  {
    Connection connection = occi.getConnection();
    ((OracleDatabaseMetaData)connection.getMetaData()).setGetLobPrecision(false); 
    return connection;
  }
 
  /**
   * Get a JDBC Connection from the Pool
   * @param username Username
   * @param password Password
   * @return JDBC Connection
   */
  protected Connection getInternalConnection(String username, String password)
  throws Exception
  {
    Connection connection = occi.getConnection(username, password);
    ((OracleDatabaseMetaData)connection.getMetaData()).setGetLobPrecision(false); 
    return connection;
  }

}