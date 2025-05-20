package framework.base;

import java.util.Collection;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

import framework.base.BaseBean;
import framework.ApplicationParameters;

/**
 * Page Iterator Implementation
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.5 $ $Date: 2004/06/17 23:28:50 $
 */
public class ValueListHandler extends BaseBean
{

  // Logger
  protected static Log log = LogFactory.getLog(ValueListHandler.class);

  public final static int ALL_ROWS = -1;

  public final static String ROWCOUNT_METHOD_JAVA = "java";
  public final static String ROWCOUNT_METHOD_SQL = "sql";

  protected Collection resultList;
  protected int startIndex;
  protected int rowsPerPage;
  protected boolean advanced;
  protected boolean retrieveRowCount;
  protected int rowCount;
  protected int nextIndex;
  protected boolean nextPage;
  protected String rowCountMethod;

////////////////////////////////////////////////////////////////////////////////
  public ValueListHandler()
  {
    super();
    init();
  }
/*
  public ValueListHandler(String rowsPerPageProperty, 
                          String rowCountProperty, 
                          String rowCountMethodProperty)
  {
    super();
    init();
    this.rowsPerPage = GlobalParameters.getAsInt(rowsPerPageProperty, getDefaultRowsPerPage());
    this.hasRetrieveRowCount = GlobalParameters.getAsBoolean(rowCountProperty, getDefaultRetrieveRowCount());
    this.rowCountMethod = GlobalParameters.getAsString(rowCountMethodProperty, getDefaultRowCountMethod());
  }
*/
  private void init()
  {
    this.advanced = false;
    this.rowCount = 0;
    this.nextIndex = 0;
    this.nextPage = false;
    this.retrieveRowCount = getDefaultRetrieveRowCount();
    this.rowCountMethod = getDefaultRowCountMethod();
    this.startIndex = 0;
    this.rowsPerPage = getDefaultRowsPerPage();
  }

////////////////////////////////////////////////////////////////////////////////
//        PARAMETERS
////////////////////////////////////////////////////////////////////////////////
/*
  public static int getMaxRows()
  {
    return GlobalParameters.getAsInt("framework.base.vlh.default.maxRows", 100);
  }
*/
  public static int getDefaultRowsPerPage()
  {
    return ApplicationParameters.getAsInt("framework.base.valueListHandler.defaultRowsPerPage", 10);
  }

  public static boolean getDefaultRetrieveRowCount()
  {
    return ApplicationParameters.getAsBooleanType("framework.base.valueListHandler.defaultRetrieveRowCount", false);
  }

  public static String getDefaultRowCountMethod()
  {
    return ApplicationParameters.getAsString("framework.base.valueListHandler.defaultRowCountMethod", ROWCOUNT_METHOD_JAVA);
  }


////////////////////////////////////////////////////////////////////////////////
//        PUBLIC METHOD
////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////
  public void setNextIndex(int value)
  {
    nextIndex = value;
  }

  public void setNextPage(boolean value)
  {
    nextPage = value;
  }

////////////////////////////////////////////////////////////////////////////////
  public int getStartIndex()
  {
    return startIndex;
  }

  public void setStartIndex(int value)
  {
    startIndex = value;
  }

////////////////////////////////////////////////////////////////////////////////
  public int getPreviousIndex()
  {
    int temp = startIndex - rowsPerPage;
    return (temp<0?0:temp);
  }

  public int getNextIndex()
  {
    int temp = startIndex + rowsPerPage;
    if (!hasRetrieveRowCount())
      return nextIndex;
    else  
      return (temp>=rowCount?rowCount:temp);
  }

  public boolean hasPreviousPage()
  {
    return startIndex >= rowsPerPage;
  }

  public boolean hasNextPage()
  {
    if (!hasRetrieveRowCount())
      return nextPage;
    else
      return startIndex+resultList.size()+1 < rowCount ;
  }

////////////////////////////////////////////////////////////////////////////////
  public Collection getResultList()
  {
    return resultList;
  }

  public void setResultList(Collection value)
  {
    resultList = value;
  }

  public boolean isEmpty()
  {
    return (resultList==null || resultList.isEmpty());
  }

  public int getPageCount()
  throws Exception
  {
    if (!hasRetrieveRowCount()) log.warn("getPageCount: was called but hasRetrieveRowCount is set to false");
    return (int)Math.ceil((double)rowCount / rowsPerPage);
  }

////////////////////////////////////////////////////////////////////////////////
  public boolean isAdvanced()
  {
    return advanced;
  }

  public void setAdvanced(boolean value)
  {
    advanced = value;
  }

////////////////////////////////////////////////////////////////////////////////
  public int getRowsPerPage()
  {
    return rowsPerPage;
  }

  public void setRowsPerPage(int value)
  {
    rowsPerPage = value;
  }

////////////////////////////////////////////////////////////////////////////////
  public void setRowCount(int value)
  {
    rowCount = value;
  }

  public int getRowCount()
  {
    return rowCount;
  }

////////////////////////////////////////////////////////////////////////////////
  public boolean hasRetrieveRowCount()
  {
    return retrieveRowCount;
  }

  public void setRetrieveRowCount(boolean newRetrieveRowCount)
  {
    retrieveRowCount = newRetrieveRowCount;
  }

  public String getRowCountMethod()
  {
    return rowCountMethod;
  }

  public void setRowCountMethod(String newRowCountMethod)
  {
    rowCountMethod = newRowCountMethod;
  }

}