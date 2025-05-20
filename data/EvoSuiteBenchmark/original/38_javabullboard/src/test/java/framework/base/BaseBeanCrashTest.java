package framework.base;

import framework.base.BaseBean;

/**
 * This Test is used to show how to get a Stack OverFlow 
 * using reflection (BaseBean.toString() use reflection)
 * One solution to avoid having getTest invoked by reflection
 * is to declare it as a protected method
 */
public class BaseBeanCrashTest extends BaseBean
{
  public BaseBeanCrashTest()
  {
  }

  public String crashTest()
//  protected String crashTest()
  {
    return this.toString();
  }

////////////////////////////////////////////////////////////////////////////////
//        TEST
////////////////////////////////////////////////////////////////////////////////

  public static void main(String[] args)
  {
    BaseBeanCrashTest test = new BaseBeanCrashTest();
    System.out.println(test.crashTest());
  }
}