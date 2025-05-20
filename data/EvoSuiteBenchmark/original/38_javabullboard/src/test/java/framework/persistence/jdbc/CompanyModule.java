package framework.persistence.jdbc;

import java.util.Collection;
import java.util.Iterator;

import framework.base.ValueListHandler;
import framework.persistence.jdbc.Module;
import framework.persistence.jdbc.View;

public class CompanyModule extends Module
{

////////////////////////////////////////////////////////////////////////////////
//  CONSTRUCTORS
////////////////////////////////////////////////////////////////////////////////

  /**
   * Default constructor
   */
  public CompanyModule()
  {
    super();
  }

  /**
   * Multi-Database constructor
   */
  public CompanyModule(String poolName)
  {
    super(poolName);
  }

////////////////////////////////////////////////////////////////////////////////
//  PUBLIC METHODS
////////////////////////////////////////////////////////////////////////////////

  public Collection findCompanyByParameters()
  throws Exception
  {
    String viewName = "CompanyView";
    View view = findView(viewName);

    // Set bind variables
// METHOD 1:
//    view.setBindVariables("FR");
// METHOD 2:
//    view.setBindVariables(new Object[] {"FR"});
// METHOD 3:
//    Collection binds = new ArrayList();
//    binds.add("FR");
//    view.setBindVariables(binds);

    // Set parameters
// METHOD 1: (Raw filter, not recommended)
//    view.addParameter(null, null, "COM_COU_CODE='FR'");
// METHOD 2: (Standard filters)
    // Operators <, =, >, <=, >=
//    view.addParameter("ComId", ">", new BigDecimal(5000));
    // Operator LIKE
    view.addParameter("ComName", "like", "%acn%", "false");
    // Operator BETWEEN
//    java.sql.Date date1 = ConvertUtils.convertSqlDate("2002-09-01");
//    java.sql.Date date2 = ConvertUtils.convertSqlDate("2002-10-02");
//    or
//    java.sql.Timestamp date1 = ConvertUtils.convertTimestamp("2002-09-01");
//    java.sql.Timestamp date2 = ConvertUtils.convertTimestamp("2002-10-02");
//    Do not use java.util.Date!
//    view.addParameter("ComCreationDate", "between", date1, date2);
    // Operator ORDER BY
//    view.addParameterOrderByDescending("ComCreationDate");
//    view.addParameter("ComCreationDate", "ORDER BY", "ASC"); // Not recommended
    // Operator GROUP BY
//    view.addParameterGroupBy("ComName"); // Not recommended
//    view.addParameter("ComName", "GROUP BY", null); // Not recommended

    // Get result
// METHOD 1: Get the whole query result as Collection
//    Collection result = view.getCollection(HashMap.class);
// METHOD 2: Get the query result as ValueListHandler for page iteration
      // Setting ValueListHandler start index
//    view.setStartIndex(20);
      // Overrides ValueListHandler XML definition
    view.setRowsPerPage(ValueListHandler.ALL_ROWS); // Not recommended, VLH is normally used for page iteration.
    view.setRetrieveRowCount(true);
    view.setRowCountMethod("sql");
    ValueListHandler vlh = view.getValueListHandler();
    Collection result = vlh.getResultList();
    System.out.println("Row count="+vlh.getRowCount());

    return result;
  }

////////////////////////////////////////////////////////////////////////////////
//  SAMPLE MAIN FOR DEBUGGING
////////////////////////////////////////////////////////////////////////////////
  public static void main(String[] args)
  {

    CompanyModule module = new CompanyModule();

    try
    {
      module.connect();
      Collection result = module.findCompanyByParameters();
      
      if (result.isEmpty()) System.out.println("No results");
      Iterator it = result.iterator();
      while(it.hasNext()) System.out.println(it.next());
      System.out.println("********* Main OK");
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      module.disconnect();
    }
  }

}