package framework.persistence.jdbc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.math.BigDecimal;

import org.apache.log4j.Logger;

import framework.base.ValueListHandler;
import framework.persistence.jdbc.Module;
import framework.persistence.jdbc.View;
import framework.util.ConvertUtils;
import framework.Emp;

/**
 * How to use the JDBC persistence framework
 */
public class EmpModule extends Module
{

  // Logger
  private static Logger log = Logger.getLogger(EmpModule.class);

  public EmpModule(String poolName)
  {
    super(poolName);
  }

  /**
   * Simple example, using an existing View
   */
  public Collection findAllEmps()
  throws Exception
  {
    View view = findView("EmpView");
    Collection result = view.getCollection();
    return result;
  }

  /**
   * How to dynamically create a View
   */
  public Collection findAllEmpNames()
  throws Exception
  {
    String viewName = "DynaEmpView";
    String sql = "select ename from emp";
    View view = getViewFromQuery(viewName, sql);
    Collection result = view.getCollection();
    return result;
  }

  /**
   * How to set a bind variable
   */
  public Emp findEmpById()
  throws Exception
  {
    String viewName = "BindEmpView";
    String sql = "select * from emp where empno=?";
    View view = getViewFromQuery(viewName, sql);
    view.setBindVariables(new BigDecimal(7788));
//    view.setBindVariables(new Double(7788));
    Emp result = new Emp();
    view.populate(result);
    return result;
  }

  /**
   * How to set multiple bind variables 
   * and get the result as a collection of HashMap
   */
  public Collection findEmpByDeptAndComm()
  throws Exception
  {
    String viewName = "MultipleBindEmpView";
    String sql = "select * from emp where deptno=? and comm>?";
    View view = getViewFromQuery(viewName, sql);
    view.setBindVariables(new Object[] {"30", new BigDecimal(0)});
    Collection result = view.getCollection(HashMap.class);
    return result;
  }

  /**
   * How to set multiple bind variables (bis)
   * and get the result as a collection of Emp
   */
  public Collection findEmpByIdAndName()
  throws Exception
  {
    String viewName = "MultipleBindBisEmpView";
    String sql = "select * from emp where empno=? and ename=?";
    View view = getViewFromQuery(viewName, sql);

    Collection binds = new ArrayList();
    binds.add(new BigDecimal(7788));
    binds.add("SCOTT");
    view.setBindVariables(binds);

    Collection result = view.getCollection(Emp.class);
    return result;
  }

  /**
   * How to add a simple parameter
   */
  public Collection findEmpByDept()
  throws Exception
  {
    View view = findView("EmpView");
    view.addParameter("Deptno", "=", "30");
    
    // Here is an equivalent with a raw filter.
    // THIS IS NOT A RECOMMENDED SYNTAX!
    // As you need to directly name the table column, no mapping is used.
    // view.addParameter(null, null, "deptno=30");
    
    Collection result = view.getCollection();
    return result;
  }

  /**
   * How to add a multiple simple parameters
   */
  public Collection findEmpByCommAndName()
  throws Exception
  {
    View view = findView("EmpView");
    view.addParameter("Comm", ">", new BigDecimal(0));
    view.addParameter("Ename", "like", "%AM%", "false");
    Collection result = view.getCollection();
    return result;
  }

  /**
   * How to add a between parameter
   */
  public Collection findEmpByHiredate()
  throws Exception
  {
    View view = findView("EmpView");
    java.sql.Date date1 = ConvertUtils.convertSqlDate("1995-01-01");
    java.sql.Date date2 = ConvertUtils.convertSqlDate("2000-12-31");
    view.addParameter("Hiredate", "between", date1, date2);
    Collection result = view.getCollection();
    return result;
  }

  /**
   * How to add a order by parameter
   */
  public Collection findAllEmpOrderByHiredate()
  throws Exception
  {
    View view = findView("EmpView");
    view.addParameterOrderByDescending("Hiredate");

    // Here is an equivalent.
    // THIS IS NOT A RECOMMENDED SYNTAX!
    // Uselessly complex to write
    // view.addParameter("Hiredate", Parameter.OPERATOR_ORDER_BY, Parameter.VALUE_DESC);

    Collection result = view.getCollection();
    return result;
  }

  /**
   * Simple example, getting a ValueListHandler for page iteration
   */
  public ValueListHandler getEmpVLH1()
  throws Exception
  {
    View view = findView("EmpView");
    ValueListHandler result = view.getValueListHandler();
    return result;
  }

  /**
   * How to set ValueListHandler basic parameters
   */
  public ValueListHandler getEmpVLH2()
  throws Exception
  {
    View view = findView("EmpView");
    view.setStartIndex(3);
    view.setRowsPerPage(5);
    ValueListHandler result = view.getValueListHandler();
    return result;
  }

  /**
   * How to set ValueListHandler advanced parameters
   */
  public ValueListHandler getEmpVLH3()
  throws Exception
  {
    View view = findView("EmpView");
    view.setRetrieveRowCount(true);
    view.setRowCountMethod("sql");

    // To retrieve all rows with a ValueListHandler
    // THIS IS NOT RECOMMENDED!
    // As VLH is normally used for page iteration.
    // view.setRowsPerPage(ValueListHandler.ALL_ROWS); 

    ValueListHandler result = view.getValueListHandler();
    log.info("Row count="+result.getRowCount());
    return result;
  }
  
}