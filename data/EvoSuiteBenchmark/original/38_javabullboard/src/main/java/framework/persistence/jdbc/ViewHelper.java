package framework.persistence.jdbc;

import java.util.Collection;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

import framework.base.ValueListHandler;
import framework.persistence.jdbc.View;
import framework.util.StringUtils;
import framework.util.ObjectUtils;
import framework.util.jdbc.JDBCUtils;
import framework.util.TuningUtils;


/**
 * ViewHelper is a utility class to load data from Database
 * - Select one (populate) object
 * - Select multiple (getCollection) objects
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.6 $ $Date: 2004/06/17 23:28:50 $
 */
public final class ViewHelper extends ComponentHelper
{

  // Logger
  private static Log log = LogFactory.getLog(ViewHelper.class);
  
  /**
   * Private constructor
   */  
  protected ViewHelper()
  {
  }

////////////////////////////////////////////////////////////////////////////////
//        PROTECTED METHODS
////////////////////////////////////////////////////////////////////////////////

  /**
   * Get the maximum number rows to fetch defined by the pool of the module of the view
   * @param view The executed view
   * @return int Maximum number of rows fetch size
   */
/*  protected static int getMaxRows(View view)
  throws Exception
  {
    return view.getModule().getJDBCPool().getMaxRows();
  }
*/
  /**
   * Get the maximum fetch size defined by the pool of the module of the view
   * @param view The executed view
   * @return int Maximum fetch size
   */
/*  protected static int getMaxFetchSize(View view)
  throws Exception
  {
    return view.getModule().getJDBCPool().getMaxFetchSize();
  }
*/
  /**
   * Populate the target object with the query result of the view
   * @param connection JDBC connection
   * @param view View to execute
   * @param target Object to populate
   */
  protected static void populate(Connection connection, View view, Object target)
  throws Exception
  {
    // Get query parameters
    String sql = view.getSqlQuery();
    Collection tokenValues = view.getTokenValues();
    Object bindVariables = view.getBindVariables();
    Collection parameters = view.getParameters();

    // Execute query
    ResultSet rset = JDBCUtils.executeQuery(connection, sql, tokenValues, bindVariables, parameters);
    
    // A SQLException is thrown if no data found !
    if (!rset.next()) throw new SQLException("No rows found");

    // Populate the target
    populate(rset, view.getAttributes(), target);

    // A SQLException is thrown if more than one row is returned !
    if (rset.next()) throw new SQLException("Too many rows found");
  }
 
  /**
   * Get a collection of target object with the query result of the view
   * @param connection JDBC connection
   * @param view View to execute
   * @param target Object to populate
   * @param vlh Optional ValueListHandler for page by page iteration
   * @return Collection of target object
   */
  protected static Collection getCollection(Connection connection, 
                                            View view,
                                            Object target, 
                                            ValueListHandler vlh)
  throws Exception
  {

///////////////////////////////////
//        INITIALIZE             //
///////////////////////////////////

    // Tuning parameters
    TuningUtils.startTuning(log, "getCollection - total");
    long start = 0;
    long end = 0;

    // Page iterator parameters
    boolean hasVLH = (vlh!=null);
    boolean hasWindow = (hasVLH && vlh.getRowsPerPage()!=ValueListHandler.ALL_ROWS);
    boolean hasRetrieveRowCount = (hasVLH && vlh.hasRetrieveRowCount());
    boolean isSQLRowCountMethod = (hasVLH && ValueListHandler.ROWCOUNT_METHOD_SQL.equalsIgnoreCase(vlh.getRowCountMethod()));
    int rowCount = 0;
    if (log.isDebugEnabled()) log.debug("getCollection: hasVLH="+hasVLH+", hasRetrieveRowCount="+hasRetrieveRowCount+", isSQLRowCountMethod="+isSQLRowCountMethod);

///////////////////////////////////
//        EXECUTE QUERY          //
///////////////////////////////////

    // Get query parameters
    String sql = view.getSqlQuery();
    Collection tokenValues = view.getTokenValues();
    Object bindVariables = view.getBindVariables();
    Collection parameters = view.getParameters();
    int scrollType = hasVLH?ResultSet.TYPE_SCROLL_INSENSITIVE:ResultSet.TYPE_FORWARD_ONLY;
//    int scrollType = ResultSet.TYPE_FORWARD_ONLY;

    // Execute query
    if (log.isDebugEnabled()) log.debug("getCollection: requested resultset type="+JDBCUtils.getResultSetType(scrollType));
    TuningUtils.startTuning(log, "getCollection - executeQuery");
    ResultSet resultSet = JDBCUtils.executeQuery(connection, sql, tokenValues, bindVariables, parameters, scrollType);
    int originalFetchSize = resultSet.getFetchSize();
    TuningUtils.stopTuning(log, "getCollection - executeQuery");
    if (log.isDebugEnabled()) log.debug("getCollection: resultSet.getType()="+JDBCUtils.getResultSetType(resultSet.getType()));

///////////////////////////////////
//        POSITIONING            //
///////////////////////////////////

    // Positioning to the start index
    if (hasVLH)
    {
      TuningUtils.startTuning(log, "getCollection - positioning");

      // Get the start index (always positive)
      int iStart = vlh.getStartIndex()<0?0:vlh.getStartIndex();

      // Try to optimize the fetch size
      if (iStart>resultSet.getFetchSize() && iStart<=view.getMaxFetchSize()) resultSet.setFetchSize(iStart);

      // Go to the start index
      // An attempt to position the cursor beyond the first/last row in the result set 
      // leaves the cursor before the first row or after the last row. 
      if (resultSet.getType()!=ResultSet.TYPE_FORWARD_ONLY)
      {
        if (log.isDebugEnabled()) log.debug("getCollection: Positioning by absolute to index="+iStart);
        // HAHAHA !! ABSOLUTELY STUPID !!! 
        // absolute(0) throws java.sql.SQLException: absolute: Invalid cursor operation.
        if (iStart!=0) resultSet.absolute(iStart);
        if (resultSet.isAfterLast()) 
        {
          log.warn("getCollection: startIndex="+vlh.getStartIndex()+" is out of range");
          resultSet.last();
          rowCount += resultSet.getRow();
          resultSet.afterLast();
        }
        else rowCount += resultSet.getRow();
      }
      else
      {
        if (log.isDebugEnabled()) log.debug("getCollection: Positioning manually to index="+iStart);
        boolean hasNext = true;
        while(iStart-->0 && hasNext) 
        {
          hasNext = resultSet.next();
          rowCount++;
        }
        if (!hasNext && iStart>0) log.warn("getCollection: startIndex("+vlh.getStartIndex()+") out of range");
      }

      TuningUtils.stopTuning(log, "getCollection - positioning");
      
      // Restore original fetch size after positioning
      resultSet.setFetchSize(originalFetchSize);
    }

///////////////////////////////////
//        FETCHING ROWS          //
///////////////////////////////////

    // Get the number of rows to fetch
    // and try to optimize fetch size
    int iCount = 0;
    if (hasWindow) 
    {
      iCount = vlh.getRowsPerPage();
      resultSet.setFetchSize(iCount);
      if (log.isDebugEnabled()) log.debug("getCollection: Window size set to "+iCount);
    }
    else resultSet.setFetchSize(view.getMaxFetchSize());

    // Acquire target class
    Class factory = null;
    if (target==null) 
    {
      // Get view defined target
      if (StringUtils.exists(view.getTargetClassName())) factory = ObjectUtils.forName(view.getTargetClassName());
      // Get default target class : java.util.HashMap
      else factory = ObjectUtils.forName(ViewManager.getInstance().getDefaultTargetClassName());
    }
    else factory = (target instanceof Class)?(Class)target:target.getClass();

    // Use ArrayList to maintain ResultSet sequence
    Collection resultList = new ArrayList();

    // Scroll to each record and populate target
    TuningUtils.startTuning(log, "getCollection - fetching");
    boolean hasNext = resultSet.next();
    while (hasNext && (hasWindow?(iCount-->0):true)) 
    {
      rowCount++;
      Object bean = factory.newInstance();
      populate(resultSet, view.getAttributes(), bean);
      resultList.add(bean);
      hasNext = resultSet.next();
    }
    if (hasNext) rowCount++; // BUG FIX: We have called resultSet.next() but did not count it
    TuningUtils.stopTuning(log, "getCollection - fetching");

    // Restore original fetch size after fetching rows
    resultSet.setFetchSize(originalFetchSize);

///////////////////////////////////
//        ROW COUNT              //
///////////////////////////////////

    // Get the total numbers of line (if hasLineCount set to true)
    if (hasRetrieveRowCount)
    {
      TuningUtils.startTuning(log, "getCollection - counting rows");
      if (isSQLRowCountMethod)
      {
        // Count row using SQL
        if (log.isDebugEnabled()) log.debug("getCollection: Counting rows by executing count()...");
        rowCount = JDBCUtils.getRowCountFromQuery(connection, sql, tokenValues, bindVariables, parameters);
      }
      else
      {
        resultSet.setFetchSize(view.getMaxFetchSize());
        if (resultSet.getType()!=ResultSet.TYPE_FORWARD_ONLY)
        {
          // Count row last()
          if (log.isDebugEnabled()) log.debug("getCollection: Counting rows by scrolling to last");
          resultSet.last();
          rowCount = resultSet.getRow();
        }
        else
        {
          // Count row using manual counter
          if (log.isDebugEnabled()) log.debug("getCollection: Counting rows by using manual counter...");
          while (resultSet.next()) rowCount++;
        }
      }
      TuningUtils.stopTuning(log, "getCollection - counting rows");
      if (log.isDebugEnabled()) log.debug("getCollection: rowCount="+rowCount);
    }

///////////////////////////////////
//        FINALIZE               //
///////////////////////////////////

    TuningUtils.startTuning(log, "getCollection - closing resultset");
    resultSet.close();
    TuningUtils.stopTuning(log, "getCollection - closing resultset");

    if (hasRetrieveRowCount) vlh.setRowCount(rowCount);
    if (hasVLH) vlh.setResultList(resultList);

    TuningUtils.stopTuning(log, "getCollection - total");

    return (resultList);
  } 
  
}