package framework.base;

import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import org.apache.log4j.Logger;
import org.apache.log4j.NDC;

import junit.framework.TestSuite;
import junit.framework.TestCase;
import junit.framework.Test;

// import framework.GlobalParameters;
import framework.base.BaseCacheManager;

public class BaseCacheManagerTest extends TestCase
{
  // Logger
  protected static Logger log = Logger.getLogger(BaseCacheManagerTest.class);

  private CacheManagerTest cache = new CacheManagerTest();

  // Required constructor - takes method name to test as parameter
  public BaseCacheManagerTest(String name) 
  {
    super(name);
  }

  // This method is called before running each test
  protected void setUp() 
  {
  }

////////////////////////////////////////////////////////////////////////////////
  public void testLoad() throws Exception 
  {
    NDC.push("testLoad");

    Random rnd = new Random();
    int count = cache.isCacheSizeLimited()?cache.getCacheSizeLimit()+10:30;
    for (int i = 0; i < count; i++) 
    {
      cache.get(Integer.toString(i));
      log.info(i + ": cache.get(" + i + ")");
      Thread.sleep(i);
    }

    log.info("Size is "+cache.getCacheSize()+", expected is "+cache.getCacheSizeLimit());
    if (!cache.isCacheSizeLimited()) assertTrue(cache.getCacheSize()==count);
    else assertTrue(cache.getCacheSize()==cache.getCacheSizeLimit());

    log.info("Hit Percentage "+cache.getCacheHitPercentage()+", expected is 0");
    assertTrue(cache.getCacheHitPercentage()==0);

    NDC.remove();
  }

////////////////////////////////////////////////////////////////////////////////
  public void testReuse() 
  throws Exception 
  {
    NDC.push("testReuse");

    log.info("LOADING...");
    cache.get("1");
    cache.get("2");
    cache.get("3");
    log.info(cache.dumpCache());

    log.info("REUSING...");
    cache.get("1");
    cache.get("2");
    log.info(cache.dumpCache());

    log.info("Hit Percentage "+cache.getCacheHitPercentage()+", expected is 40");
    assertEquals(cache.getCacheHitPercentage(), 40);

    NDC.remove();
  }

////////////////////////////////////////////////////////////////////////////////
  public void testIdleTimeout() 
  throws Exception 
  {
    NDC.push("testTimeToLive");

    log.info("LOADING...");
    cache.get("1");
    cache.get("2");
    cache.get("3");
    log.info(cache.dumpCache());

    log.info("SLEEPING FOR "+(cache.getCacheTimeToLive()/1000+1)+"s...");
    Thread.sleep((cache.getCacheTimeToLive()+1000));

    log.info("REUSING...");
    cache.get("1");
    cache.get("2");
    log.info(cache.dumpCache());

    log.info("Size is "+cache.getCacheSize()+", expected is 2");
    assertEquals(cache.getCacheSize(), 2);

    log.info("Hit Percentage "+cache.getCacheHitPercentage()+", expected is 0");
    assertEquals(cache.getCacheHitPercentage(), 0);

    NDC.remove();
  }

////////////////////////////////////////////////////////////////////////////////
  public void testTimeToLive() 
  throws Exception 
  {
    NDC.push("testTimeToLive");

    log.info("LOADING...");
    cache.get("1");
    cache.get("2");
    cache.get("3");
    log.info(cache.dumpCache());

    log.info("LOOP FOR "+(cache.getCacheTimeToLive()/1000-1)+"s...");
    for(int i=0; i<((cache.getCacheTimeToLive()/1000)-1); i++)
    {
      log.info("SLEEPING FOR 1s...");
      Thread.sleep(1000);
      cache.get("2");
    }
    log.info(cache.dumpCache());

    log.info("SLEEPING FOR 2 MORE seconds...");
    Thread.sleep(2000);
    log.info(cache.dumpCache());

    log.info("Size is "+cache.getCacheSize()+", expected is 0");
    assertTrue(cache.getCacheSize()==0);

    NDC.remove();
  }
  
////////////////////////////////////////////////////////////////////////////////
  // Add tests to run in order of running into the suite
  public static Test suite() 
  {
/*
    // Shortcut to automatically add all testXXX methods
    return new TestSuite(BaseCacheManagerTest.class);
*/

//    TestSuite testSuite = new ActiveTestSuite();
    TestSuite testSuite = new TestSuite();
    testSuite.addTest(new BaseCacheManagerTest("testLoad"));
    testSuite.addTest(new BaseCacheManagerTest("testReuse"));
    testSuite.addTest(new BaseCacheManagerTest("testIdleTimeout"));
    testSuite.addTest(new BaseCacheManagerTest("testTimeToLive"));
    log.info("countTestCases=" + testSuite.countTestCases());

    return testSuite;
  }

////////////////////////////////////////////////////////////////////////////////
  public static void main(String[] args)
  {
    // Run the tests
    junit.textui.TestRunner.run(suite());
  }

////////////////////////////////////////////////////////////////////////////////
//        INNER CLASS CACHEMANAGERTEST
////////////////////////////////////////////////////////////////////////////////

  protected class CacheManagerTest extends BaseCacheManager
  {

    protected String getType()
    {
      return "CacheManagerTest";
    }
  
    protected Object load(Object key)
    {
      Map obj = new HashMap();
      obj.put(key, key);
      return obj;
    }

    public Object get(Object key)
    throws Exception
    {
      return getOrLoad(key);
    }

  }

}