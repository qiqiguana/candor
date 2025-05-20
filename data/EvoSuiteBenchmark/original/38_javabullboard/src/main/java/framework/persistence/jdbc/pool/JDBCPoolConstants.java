package framework.persistence.jdbc.pool;

/**
 * Pool constants
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.2 $ $Date: 2004/05/09 21:12:06 $
 */
public interface JDBCPoolConstants 
{
  // Available pool types
  public static final String ORACLE_POOL_TYPE = "oracle";
  public static final String DBCP_POOL_TYPE = "dbcp";

  public static final String DEFAULT_POOL_NAME = "default";
  public static final String DEFAULT_POOL_CLASSNAME = "framework.persistence.jdbc.pool.DBCPoolImpl";  
  public static final String DEFAULT_POOL_TYPE = DBCP_POOL_TYPE;
  public static final Integer DEFAULT_DEBUG_LEVEL = new Integer(0);
  public static final Boolean DEFAULT_DO_VALIDATION = new Boolean(false);
  public static final Integer DEFAULT_MAX_ROWS = new Integer(0);
  public static final Integer DEFAULT_MAX_FETCH_SIZE = new Integer(100);
  public static final Boolean DEFAULT_IS_AUTOCOMMIT = new Boolean(false);
  public static final String DEFAULT_QUERY_TIMESTAMP = "select current_timestamp() as 'current_timestamp'";
  public static final String DEFAULT_QUERY_USERNAME = "select current_user() as 'current_user'";
  public static final String DEFAULT_QUERY_VALIDATION = "select current_user() as 'current_user'";
  public static final Integer DEFAULT_MAX_LIMIT = new Integer(0);
  public static final Integer DEFAULT_MIN_LIMIT = new Integer(0);

}