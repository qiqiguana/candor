package framework.persistence.jdbc.pool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

import framework.ApplicationParameters;
import framework.util.StringUtils;
import framework.util.TuningUtils;

/**
 * The JDBCPoolManager is in charge of pool instances creation
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.4 $ $Date: 2004/06/17 23:28:51 $
 */
public class JDBCPoolManager implements JDBCPoolConstants
{

  // Logger
  protected static Log log = LogFactory.getLog(JDBCPoolManager.class);

  // Referenced pools
  private static Map pools = new Hashtable();

  // Static initialization
  static
  {
    try
    {
      initialize();
    }
    catch(Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  // Protected constructor
  protected JDBCPoolManager()
  {
  }

////////////////////////////////////////////////////////////////////////////////
//        PARAMETERS
////////////////////////////////////////////////////////////////////////////////

  public static String getPoolClassName(String poolName)
  {
    String value = ApplicationParameters.getAsString("framework.persistence.jdbc.pool."+poolName+".className");
    String defaultValue = ApplicationParameters.getAsString("framework.persistence.jdbc.pool."+DEFAULT_POOL_NAME+".className", DEFAULT_POOL_CLASSNAME);
    value = value!=null?value:defaultValue;
    return value;
  }

  /**
   * Initialize all declared pools
   * @throws java.lang.Exception
   */
  public static void initialize()
  throws Exception
  {
    Iterator it = getDeclaredPoolNames().iterator();
    while (it.hasNext()) 
    {
      String poolName = (String)it.next();
      getPool(poolName);
    }    
  }

  /**
   * Return the implementation class of the pool
   * @param poolName The pool name
   * @return JDBCPool
   */
  private static JDBCPool getPoolImpl(String poolName)
  throws Exception
  {
    String poolClassName = getPoolClassName(poolName);
    Class clazz = Class.forName(poolClassName);
    JDBCPool result = (JDBCPool)clazz.newInstance();
    return result;
  }


  /**
   * Return the default pool instance
   * @return JDBCPool instance
   */
  public static JDBCPool getPool()
  throws Exception
  {
    return getPool(JDBCPool.DEFAULT_POOL_NAME);
  }
  
  /**
   * Return the pool instance
   * @return JDBCPool instance
   */
  public static JDBCPool getPool(String poolName)
  throws Exception
  {
    JDBCPool pool = (JDBCPool)pools.get(poolName);
    if (pool==null) 
    {
      pool = createPool(poolName);
      pools.put(poolName, pool);
    }
    return pool;
  }

  /**
   * Return the declared pool names as a DbCollection
   * @return Collection of String
   */
  public static Collection getDeclaredPoolNamesAsDbCollection()
  throws Exception
  {
    Collection poolNameList = new ArrayList();
    Collection poolNames = getDeclaredPoolNames();
    Iterator it = poolNames.iterator();
    while (it.hasNext()) 
    {
      String poolName = (String)it.next();
      Map item = new HashMap();
      item.put("Value", poolName);
      item.put("Label", poolName);
      poolNameList.add(item);
    }

    return poolNameList;
  }

  /**
   * Return the declared pool names
   * @return Collection of String
   */
  public static Collection getDeclaredPoolNames()
  throws Exception
  {
    return ApplicationParameters.findChildNodesName("framework/persistence/jdbc/pool");
  }

  /**
   * Creates the requested JDBC pool instance
   * @param poolName The requested pool name
   */  
  protected static JDBCPool createPool(String poolName)
  throws Exception
  {
    if (log.isDebugEnabled()) log.debug("createPool: Creating pool '"+poolName+"'");
    TuningUtils.startTuning(log, "createPool");
    if (!StringUtils.exists(poolName)) throw new RuntimeException("Pool name is null or empty");
    JDBCPool pool = getPoolImpl(poolName);
    pool.setPoolName(poolName);
    pool.initialize();
    TuningUtils.stopTuning(log, "createPool");
    return pool;
  }

  /**
   * Pool statistics
   * @return Pool statistics as a String
   */
  public static String dumpAllStatistics()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("Pool Manager Statistics: \n");

    try
    {
      Iterator it = pools.keySet().iterator();
      while (it.hasNext()) 
      {
        String poolName = (String)it.next();
        sb.append(getPool(poolName).dumpPoolStatistics());
        if (it.hasNext()) sb.append("\n");
      }
    }
    catch(Exception e)
    {
      log.error("dumpAllStatistics: ", e);
      sb.append("Error while gettting Pool Manager Statistics");
    }

    return sb.toString();
  }

}