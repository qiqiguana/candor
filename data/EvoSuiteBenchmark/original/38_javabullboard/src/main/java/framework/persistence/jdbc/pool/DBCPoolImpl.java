package framework.persistence.jdbc.pool;

import java.sql.Connection;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;
import org.apache.commons.dbcp.cpdsadapter.DriverAdapterCPDS;
import org.apache.commons.dbcp.datasources.SharedPoolDataSource;


/**
 * JDBCPool DBCP (Jakarta DataBase Common Pool) Implementation
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.4 $ $Date: 2004/06/17 23:28:50 $
 */
public class DBCPoolImpl extends JDBCPool
{

  // Logger
  protected static Log log = LogFactory.getLog(framework.persistence.jdbc.pool.DBCPoolImpl.class);

  // Datasource
//  private BasicDataSource ds = null;
  private SharedPoolDataSource ds = new SharedPoolDataSource();

  /**
   * Prrotected Constructor
   */
  protected DBCPoolImpl()
  {
  }

  /**
   * Initialization
   */
  protected void internalInitialize()
  throws Exception
  {
    DriverAdapterCPDS cpds = new DriverAdapterCPDS();
    cpds.setDriver(getDriver());
    cpds.setUrl(getUrl());
    cpds.setUser(getUsername());
    cpds.setPassword(getPassword());
    ds.setConnectionPoolDataSource(cpds);

/*
    ds = new BasicDataSource();
    ds.setDriverClassName(getDriver());
    ds.setUrl(getUrl());
    ds.setUsername(getUsername());
    ds.setPassword(getPassword());
*/
/*
    ds.setMaxOpenPreparedStatements();
    ds.setPoolPreparedStatements();
    ds.setValidationQuery();
*/
  }

  /**
   * Return the number of active connections
   * @return Number of active connections
   */  
  public int countActiveConnections()
  {
    return ds.getNumActive();
  }

  /**
   * Return the number of idle connections
   * @return Number of idle connections
   */  
  public int countIdleConnections()
  {
    return ds.getNumIdle();
  }

  /**
   * Set the maximum number of connections in the pool
   */  
  public void setMaxLimit(int maxLimit)
  throws Exception 
  {
    ds.setMaxActive(maxLimit);
  }

  /**
   * Set the minimum number of connections in the pool
   */  
  public void setMinLimit(int minLimit)
  throws Exception
  {
    log.warn("setMinLimit is not supported in DBCPool !!??");
    //ds.setMinIdle(minLimit);
  }

  /**
   * Get a JDBC Connection from the Pool
   * @return JDBC Connection
   */
  protected Connection getInternalConnection()
  throws Exception
  {
    return ds.getConnection();
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
    return ds.getConnection(username, password);
  }

}