package framework.util;

import java.util.Hashtable;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;


public final class TuningUtils 
{

  // Logger
  private static Log log = LogFactory.getLog(TuningUtils.class);

  // Threads Map
  private static Hashtable categoryTimers = new Hashtable();

  protected TuningUtils()
  {
  }

  private static String getKey(String category)
  {
    StringBuffer key = new StringBuffer();
    key.append("[Thread-").append(Thread.currentThread().hashCode()).append("] ");
    key.append(category);
    return key.toString();
  }

  public static void startTuning(String category)
  {
    startTuning(log, category);
  }

  public static void startTuning(Class clazz, String category)
  {
    startTuning(LogFactory.getLog(clazz), category);
  }
  
  public static void startTuning(Log logger, String category)
  {
    if (logger.isInfoEnabled()) 
    {
      String key = getKey(category);
      if (categoryTimers.get(key)!=null) logger.info("Tuning data for key="+key+" has been overwritten!");
      categoryTimers.put(key, new Long(System.currentTimeMillis()));
    }
  }

  public static void stopTuning(String category)
  {
    stopTuning(log, category);
  }

  public static void stopTuning(Class clazz, String category)
  {
    stopTuning(LogFactory.getLog(clazz), category);
  }
  
  public static void stopTuning(Log logger, String category)
  {
    if (logger.isInfoEnabled()) 
    {
      String key = getKey(category);
      long time = System.currentTimeMillis();
      Long start = (Long)categoryTimers.get(key);
      if (start==null) logger.info("Tuning data for key="+key+" has been lost!");
      else
      {
        time -= start.longValue();
        logger.info(category+" TIME="+time+"ms");
        categoryTimers.remove(key);
      }
    }
  }
  
}