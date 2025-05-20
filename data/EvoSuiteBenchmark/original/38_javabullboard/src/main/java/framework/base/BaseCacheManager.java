package framework.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;
import java.util.Timer;
import java.util.LinkedList;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;
import org.apache.commons.beanutils.MethodUtils;

import framework.ApplicationParameters;
import framework.util.StringUtils;
import org.apache.log4j.NDC;

/**
 * Extends BaseCacheManager to have your class behave as a Cache.
 * 
 * How to use?
 * 1. Create a class extending BaseCacheManager
 * 2. Override the getType() method to tell what kind of object you are caching
 * 3. Override the load(key) method to create a new Object
 * 
 * How does it work?
 * 1. When you need an object, call getOrLoad(key)
 * 2. If the object already in cache, it is returned
 *    else the load(key) is called and the new Object is put in cache
 *    
 * How can I customize this behaviour?
 * 1. Set your cache parameters:
 *    - framework.cache.<MyObjectType>.cacheSizeLimit // int
 *      If a cacheSizeLimit is set (>0), a LRU rule is applied to the cache.
 *      Default is no limit.
 *    - framework.cache.<MyObjectType>.timeToLive // in milliseconds
 *      If a timeToLive is set (>0), any object in the cache won't remain 
 *      more than timeToLive in the cache. Default is live forever.
 *    - framework.cache.<MyObjectType>.idleTimeout // in milliseconds
 *      If an idleTimeout is set (>0), any object idle for more than idleTimeout 
 *      will be removed from the cache. Default is never timeout.
 *    - framework.cache.<MyObjectType>.timerInterval // in milliseconds
 *      The timerInterval is used to check object timeouts in the cache. 
 *      Default is 1mn.
 * 2. If you need more than one parameter to create your object 
 *    and can't use load(key), instead of calling getOrLoad(key), 
 *    use getOrLoad(key, object, methodName, parameters, parameterTypes).
 *    This will dynamically call methodName from your object instance with 
 *    the parameters values and parameterTypes. 
 *    So that you can call any method to load a new object in the cache.
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.8 $ $Date: 2004/06/17 23:28:50 $
 */
public abstract class BaseCacheManager 
{

  // Logger
  protected static Log log = LogFactory.getLog(BaseCacheManager.class);

  // Cache size limit and timeouts.
  // All timeouts are in milliseconds.
  public static final int CACHE_SIZE_UNLIMITED = 0;
  public static final long CACHE_LIVE_FOREVER = 0;
  public static final long CACHE_NEVER_TIMEOUT = 0;
  public static final long CACHE_DEFAULT_TIMER_INTERVAL = 1 * 60 * 1000;  // 1mn

  // The cache
  protected Map cache = null;
  protected LinkedList lruList = null;
  protected boolean isLRU = false;
  protected Timer timer;
  protected long cacheHits = 0;
  protected long cacheMisses = 0;

  protected BaseCacheManager()
  {
    cache = new HashMap();
    
    // A size limit has been set, use LRU
    if (isCacheSizeLimited()) 
    {
      lruList = new LinkedList();
      isLRU = true;
    }
    // else cache all !
    else isLRU = false;

    // If a timeout is used, launch timer
    if (isCacheTimeToLiveUsed() || isCacheIdleTimeoutUsed()) initializeTimer();
  }

////////////////////////////////////////////////////////////////////////////////
//        PARAMETERS
////////////////////////////////////////////////////////////////////////////////

  public int getCacheSizeLimit()
  {
    return ApplicationParameters.getAsInt("framework.cache."+getType().toLowerCase()+".cacheSizeLimit", CACHE_SIZE_UNLIMITED);
  }

  public long getCacheTimeToLive()
  {
    return ApplicationParameters.getAsLongType("framework.cache."+getType().toLowerCase()+".timeToLive", CACHE_LIVE_FOREVER);
  }

  public long getCacheIdleTimeout()
  {
    return ApplicationParameters.getAsLongType("framework.cache."+getType().toLowerCase()+".idleTimeout", CACHE_NEVER_TIMEOUT);
  }

  public long getCacheTimerInterval()
  {
    return ApplicationParameters.getAsLongType("framework.cache."+getType().toLowerCase()+".timerInterval", CACHE_DEFAULT_TIMER_INTERVAL);
  }

  /**
   * Is size limit used ?
   */
  public boolean isCacheSizeLimited() 
  {
    return getCacheSizeLimit()>CACHE_SIZE_UNLIMITED;
  }

  /**
   * Is time to live used ?
   */
  public boolean isCacheTimeToLiveUsed() 
  {
    return getCacheTimeToLive()>CACHE_LIVE_FOREVER;
  }

  /**
   * Is idle timeout set ?
   */
  public boolean isCacheIdleTimeoutUsed() 
  {
    return getCacheIdleTimeout()>CACHE_NEVER_TIMEOUT;
  }

////////////////////////////////////////////////////////////////////////////////
//        PRIVATE METHODS
////////////////////////////////////////////////////////////////////////////////

  /**
   * Launch the TimerTask to remove expired items
   */
  private void initializeTimer() 
  {
    if (log.isDebugEnabled()) log.debug("initializeTimer() started");
    if (timer!=null) timer.cancel();
    timer = new Timer(true);
    timer.schedule(
      new TimerTask() 
      {
        public void run() 
        {
          NDC.push("TimerTask");

          // Look for expired items
          long now = System.currentTimeMillis();
          ArrayList keysToRemove = new ArrayList();
          try
          {
            Iterator itr = cache.keySet().iterator();
            while (itr.hasNext()) 
            {
              Object key = itr.next();
              CachedObject cobj = (CachedObject)cache.get(key);
              if (cobj == null || cobj.hasExpired(now)) keysToRemove.add(key);
            }
  
            // Remove expired items
            Iterator it = keysToRemove.iterator();
            while (it.hasNext()) 
            {
              Object key = it.next();
              if (log.isDebugEnabled()) log.debug("TimerTask: Removing "+key);
              removeFromCache(key);
            }
          }
          catch(java.util.ConcurrentModificationException e)
          {
            // Ignorable.  This is just a timer cleaning up.
            // It will catchup on cleaning next time it runs.
              if (log.isDebugEnabled()) log.warn("Ignorable ConcurrentModificationException");
          }
          NDC.remove();
        }
      },
      0,
      getCacheTimerInterval()
    );
  }

  /**
   * Return an object from cache, null if not found
   * @param key Unique identifier of the object   
   * @return The requested object, null if not found
   */
  private Object getFromCache(Object key)
  {
    Object result = cache.get(key);
    if (result!=null) 
    {
      if (log.isDebugEnabled()) log.debug("getFromCache: "+getType()+" Getting "+key+": It's a hit");
      return ((CachedObject)result).getCachedData(key);
    }
    else 
    {
      if (log.isDebugEnabled()) log.debug("getOrLoad: "+getType()+" Getting "+key+": It's a miss ...");
      return null;
    }
  }

  /**
   * Put an object into cache.
   * If the given key already maps to an existing object, it will be overwritten 
   * and the existing object is returned.
   * You may want to check the return value for null-ness to make sure you
   * are not overwriting a previously cached object.  May be you can use a
   * different key for your object if you do not intend to overwrite.
   * @param key Unique identifier of the object   
   * @param object The object
  */
  private synchronized Object putIntoCache(Object key, Object object)
  {
    Object result = cache.get(key);
    if (result!=null) log.warn("putIntoCache: "+getType()+" "+key+" has been overwritten in cache");
    else if (log.isDebugEnabled()) log.debug("putIntoCache: "+getType()+" "+key+" put into cache");
    cache.put(key, new CachedObject(object));
    if (isLRU) lruList.addFirst(key);
    return result;
  }

  /**
   * Remove an object from cache
   * @param key Unique identifier of the object   
   * @return true if the object has been remove, false if the object is not found
   */
  private boolean removeFromCache(Object key)
  {
    if (log.isDebugEnabled()) log.debug("putIntoCache: "+getType()+" "+key+" removed from cache");
    Object result = cache.remove(key);
    if (isLRU) lruList.remove(key);
    return result!=null;
  }

////////////////////////////////////////////////////////////////////////////////
//        PROTECTED METHODS
////////////////////////////////////////////////////////////////////////////////

  /**
   * Get the component type
   * @return Component type
   */
  protected abstract String getType();

  /**
   * Finalize any remaining tasks
   */
  protected void finalize() 
  throws Exception
  {
    clearCache();
    if (timer!=null) timer.cancel();
    if (log.isDebugEnabled()) log.debug("finalize: "+getType()+" finalized");
  }

  /**
   * Load the object to be cached.
   * Overrides this method to create the object to be cached.
   * If the 
   * @param key Unique identifier of the object   
   * @return the object to be cached
   */
  protected Object load(Object key)
  throws Exception
  {
    throw new Exception("You have to override the load() method !");
  }

  /**
   * Load the object to be cached
   * @param object The object on which method has to be invoke
   * @param methodName The method name to invoke
   * @param parameters The parameters to pass to the method
   * @param parameterTypes The parameter types of the arguments
   * @return the object to be cached
   */
  protected Object load(Object object, String methodName, Object[] parameters, Class[] parameterTypes)
  throws Exception
  {
    if (parameterTypes!=null) return MethodUtils.invokeExactMethod(object, methodName, parameters, parameterTypes);
    else return MethodUtils.invokeExactMethod(object, methodName, parameters);
  }

  /**
   * Return a object from cache, 
   * if not found, call the load method and cache the result
   * @param key Unique identifier of the object   
   * @return The Object
   */
  protected Object getOrLoad(Object key) 
  throws Exception 
  {    
    return getOrLoad(key, null, null, null, null);
  }

  /**
   * Return a object from cache, 
   * if not found, call the load method and cache the result
   * Parameter types are optionals but if you omit to precise them (by passing null)
   * all arguments must exist (none can be null)
   * @param key Unique identifier of the object   
   * @param object The object instance on which method has to be invoked
   * @param methodName The method name to invoke
   * @param parameters The parameters to pass to the method
   * @param parameterTypes The parameter types of the arguments
   * @return The Object
   */
  protected Object getOrLoad(Object key, Object object, String methodName, Object[] parameters, Class[] parameterTypes) 
  throws Exception
  {
    boolean isDynamicLoad  = (object!=null && StringUtils.exists(methodName) && parameters!=null);

    Object result = getFromCache(key);
    if (result!=null) 
    {
      cacheHits++;
      if (isLRU)
      {
        synchronized(cache) 
        {
          lruList.remove(key);
          lruList.addFirst(key);
        }
      }
    }
    else
    {
      cacheMisses++;
      synchronized(cache) 
      { 
        if (isLRU && getCacheSize()>=getCacheSizeLimit()) removeFromCache(lruList.getLast());
        result = isDynamicLoad?load(object, methodName, parameters, parameterTypes):load(key);
        putIntoCache(key, result);
      }
    }
    return result;
  }

////////////////////////////////////////////////////////////////////////////////
//        PUBLIC METHODS
////////////////////////////////////////////////////////////////////////////////

  /**
   * Clear cache
   */
  public void clearCache()
  {
    cache.clear();
    cacheHits = 0;
    cacheMisses = 0;    
  }

  /**
   * Gets the hit percentage of the cache
   * @return the hit percentage of the cache
   */
  public int getCacheHitPercentage() 
  {
    return (int)((cacheHits*100) / (cacheHits+cacheMisses));
  }

  /**
   * Return the current cache size
   * @return the current cache size
   */
  public int getCacheSize() 
  {
    return cache.size();
  }

  /**
   * Prints cache
   */
  public String dumpCache()
  {
    StringBuffer result = new StringBuffer();
    result.append("<!---------------- ");
    result.append(getType());
    result.append(" (hits=");
    result.append(getCacheHitPercentage());
    result.append("%) ---------------->");

    Iterator it = cache.keySet().iterator();
    while (it.hasNext()) 
    {
      Object key = it.next();
      CachedObject cobj = (CachedObject)cache.get(key);
      result.append("\n<!-- ");
      result.append("key=").append(key);
      result.append(", whenCached=").append(cobj.whenCached());
      result.append(", whenLastAccessed=").append(cobj.whenLastAccessed());
      result.append(", howManyTimesAccessed=").append(cobj.howManyTimesAccessed());
//      result.append(", object=").append(cobj.getCachedData(key));
      result.append(" -->");
    }
    result.append(dumpCacheLRUList());
    return result.toString();
  }

  /**
   * Prints the most recently used list of cached objects
   */
  public String dumpCacheLRUList()
  {
    StringBuffer result = new StringBuffer("\n<!-- LRUList=");
    result.append(lruList);
    result.append(" -->");
    return result.toString();
  }


////////////////////////////////////////////////////////////////////////////////
//        INNER CLASS CACHEDOBJECT
////////////////////////////////////////////////////////////////////////////////

  /**
   * A cached object, needed to store attributes such as the last time
   * it was accessed.
   */
  protected class CachedObject 
  {
    Object cachedData;
    long timeCached;
    long timeAccessedLast;
    int numberOfAccesses;
    long objectTimeToLive;
    long objectIdleTimeout;

    CachedObject(Object cachedData) 
    {
      long now = System.currentTimeMillis();
      this.cachedData = cachedData;
      timeCached = now;
      timeAccessedLast = now;
      ++numberOfAccesses;
      objectTimeToLive = getCacheTimeToLive();
      objectIdleTimeout = getCacheIdleTimeout();
    }

    public long whenCached() 
    {
      return timeCached;
    }
  
    public long whenLastAccessed() 
    {
      return timeAccessedLast;
    }
    
    public int howManyTimesAccessed() 
    {
      return numberOfAccesses;
    }

    /**
     * Get the cached data from the CachedObject
     * @param key The key of the cached object
     * @return Object The cached data
     */
    Object getCachedData(Object key) 
    {
      // If the item has expired, remove it from the cache and return null
      long now = System.currentTimeMillis();      
      if (hasExpired(now)) 
      {
        cachedData = null;
        removeFromCache(key);
        return null;
      }
      timeAccessedLast = now;
      ++numberOfAccesses;
      return cachedData;
    }

    /**
     * Has the object expired ?
     * - Time to live has passed
     * - Idle time has passed
     * @param now The currentTimeMillis
     * @return true if the object has expired, else false
     */
    boolean hasExpired(long now) 
    {
      if (isCacheTimeToLiveUsed()) return (now>timeCached+objectTimeToLive);
      else if (isCacheIdleTimeoutUsed()) return (now>timeAccessedLast+objectIdleTimeout);
      else return false;
    }
  }

}