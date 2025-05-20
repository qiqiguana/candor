package framework.persistence.jdbc;

import java.sql.Connection;
import java.util.Map;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

import framework.persistence.jdbc.ViewManager;
import framework.persistence.jdbc.EntityManager;
import framework.persistence.jdbc.View;
import framework.persistence.jdbc.Entity;
import framework.persistence.jdbc.pool.JDBCPoolManager;
import framework.persistence.jdbc.pool.JDBCPool;
import framework.util.StringUtils;

/**
 * Every DAO should extends Module as it provide:
 * - transaction management methods
 * - retrieving views
 * - retrieving entities
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.4 $ $Date: 2004/06/17 23:28:50 $
 */
public class Module
{

  // Logger
  private static Log log = LogFactory.getLog(Module.class);

  // Pool name
  protected String poolName;
  protected Map views;
  protected Map entities;

  // Connection
  protected Connection connection;

  /**
   * Protected constructor
   */
  protected Module()
  {
    this.poolName = JDBCPool.DEFAULT_POOL_NAME;
  }

  protected Module(String poolName)
  {
    boolean isPoolNameProvided = StringUtils.exists(poolName);
    this.poolName = isPoolNameProvided?poolName:JDBCPool.DEFAULT_POOL_NAME;
  }

  protected void initalize()
  {
/*
    Try to get Module from ModuleManager
    if module not found
    {
      Try to load file this.getClass().getName()+".xml"
      If file exists
      {
        If xmlPoolName exists
        {
          this.poolName = xmlPoolName;
          this.views.putAll(xmlViews)
          this.entities.putAll(xmlEntities)
        }
      }
    }
*/
  }

////////////////////////////////////////////////////////////////////////////////
  public String getPoolName()
  {
    return poolName;
  }

  private JDBCPool getJDBCPool()
  throws Exception
  {
    if (!StringUtils.exists(poolName)) throw new Exception("The poolName has not been set. Check your module constructors: MyModule() and MyModule(String poolName)");
    return JDBCPoolManager.getPool(poolName);
  }

////////////////////////////////////////////////////////////////////////////////
/*  public Map getConnectionMetaData()
  throws Exception
  {
    return JDBCHelper.getConnectionMetaData(getConnection());
  }
*/
////////////////////////////////////////////////////////////////////////////////
  public boolean isConnected()
  throws Exception
  {
    return !(connection==null || connection.isClosed());
  }

  public Connection getConnection()
  throws Exception
  {
    if (!isConnected()) throw new Exception("Module is not connected. Call myModule.connect().");
    return connection;
  }

  public void connect()
  throws Exception
  {
    connect(null, null);
  }

  public void connect(String username, String password)
  throws Exception
  {
    if (isConnected()) 
    {
      log.warn("connect: This module was already connected. Realeasing the internal connection...");
      disconnect();
    }
    connection = getJDBCPool().getConnection(username, password);
  }

  public void disconnect()
  {
    try
    {
      getJDBCPool().releaseConnection(connection);
    }
    catch(Exception e)
    {
      log.error("disconnect: ", e);
    }
  }

////////////////////////////////////////////////////////////////////////////////
  public void commit()
  throws Exception
  {
    getConnection().commit();
    log.info("commit: Connection committed !");
  }

  public void rollback()
  throws Exception
  {
    getConnection().rollback();
    log.info("rollback: Connection rollbacked !");
  }

////////////////////////////////////////////////////////////////////////////////
  protected View findView(String name)
  throws Exception
  {
    return findAnyView(name, this.getClass().getPackage().getName());
  }

////////////////////////////////////////////////////////////////////////////////
  protected View findAnyView(String name, String packageName)
  throws Exception
  {
    View view = (View)ViewManager.getInstance().find(name, packageName);
    view.setModule(this);
    return view;
  }

////////////////////////////////////////////////////////////////////////////////
  protected View getViewFromQuery(String name, String sql)
  throws Exception
  {
    View view = ViewManager.getInstance().getViewFromQuery( getConnection(), 
                                              name, 
                                              this.getClass().getPackage().getName(), 
                                              null,
                                              null,
                                              sql);
    view.setModule(this);
    return view;
  }

////////////////////////////////////////////////////////////////////////////////
  protected Entity findEntity(String name)
  throws Exception
  {
    Entity entity = (Entity)EntityManager.getInstance().find(name, this.getClass().getPackage().getName());
    entity.setModule(this);
    return entity;
  }

}