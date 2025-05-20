package framework.persistence.jdbc;

import framework.Emp;
import framework.base.ValueListHandler;
import framework.persistence.jdbc.pool.JDBCPoolManager;
import java.util.Collection;
import java.util.Iterator;
import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.extensions.ActiveTestSuite;

import org.apache.log4j.Logger;

// import framework.GlobalParameters;

public class EmpModuleTest extends TestCase
{

  // Logger
  private static Logger log = Logger.getLogger(EmpModuleTest.class);
  
  private static final boolean isPrintEnabled = false;

  // Pool Name
  private static String poolName = "javabb";

  public EmpModuleTest()
  {
  }

  // Required constructor - takes method name to test as parameter
  public EmpModuleTest(String name) 
  {
    super(name);
  }

  protected void tearDown()
  throws Exception
  {
    super.tearDown();
    System.out.println(JDBCPoolManager.dumpAllStatistics());
  }

////////////////////////////////////////////////////////////////////////////////
  public static void print(Collection results)
  {
    if (isPrintEnabled)
    {
      if (results.isEmpty()) log.info("No results");
      Iterator it = results.iterator();
      while(it.hasNext()) log.info(it.next());
    }
  }

  public static void print(ValueListHandler vlh)
  {
    if (isPrintEnabled) print(vlh.getResultList());
  }  

  public static void print(Emp emp)
  {
    if (isPrintEnabled) log.info(String.valueOf(emp));
  }  

////////////////////////////////////////////////////////////////////////////////
  public static void testEmpModule()
  throws Exception
  {
    EmpModule module = new EmpModule(poolName);

    try
    {
      module.connect();

      print(module.findAllEmps());
      print(module.findAllEmpNames());
      print(module.findEmpById());
      print(module.findEmpByDeptAndComm());
      print(module.findEmpByIdAndName());
      print(module.findEmpByDept());
      print(module.findEmpByCommAndName());
      print(module.findEmpByHiredate());
      print(module.findAllEmpOrderByHiredate());
      print(module.getEmpVLH1());
      print(module.getEmpVLH2());
      print(module.getEmpVLH3());

      log.info("********* test OK");
    }
    finally
    {
      module.disconnect();
    }
  }


  // Add tests to run in order of running into the suite
  public static Test suite() 
  {
    int nbThreads = 100;
    TestSuite suite1 = new ActiveTestSuite();
    for (int i=0; i<nbThreads; i++) suite1.addTest(new EmpModuleTest("testEmpModule"));
    return suite1;
  }

  /**
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    junit.textui.TestRunner.run( suite() );
/*
    try 
    {
      testEmpModule();
    } 
    catch (Exception e) 
    {
      e.printStackTrace();
    }
    finally 
    {
    }
*/
  }
}