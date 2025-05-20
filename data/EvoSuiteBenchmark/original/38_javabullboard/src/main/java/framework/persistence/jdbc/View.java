package framework.persistence.jdbc;

import java.util.Collection;
import java.util.ArrayList;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

import framework.base.ValueListHandler;
import framework.util.StringUtils;
import framework.persistence.jdbc.ViewHelper;
import framework.persistence.jdbc.Component;
import framework.util.ConvertUtils;
import framework.persistence.jdbc.tools.DbCollectionManager;
import framework.util.jdbc.Parameter;

/**
 * A view is a selection of data from the database
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.7 $ $Date: 2004/06/17 23:28:50 $
 */
public class View extends Component
{

  // Logger
  protected static Log log = LogFactory.getLog(View.class);

  protected Collection tokenValues;
  protected Object bindVariables;
  protected Collection parameters;

  protected boolean retrieveRowCount;
  protected String rowCountMethod;
  protected int startIndex;
  protected int rowsPerPage;
  protected int maxFetchSize;

  public View()
  {
    super();
    this.targetClassName = ViewManager.getInstance().getDefaultTargetClassName();

    this.retrieveRowCount = ValueListHandler.getDefaultRetrieveRowCount();
    this.rowCountMethod = ValueListHandler.getDefaultRowCountMethod();
    this.startIndex = 0;
    this.rowsPerPage = ValueListHandler.getDefaultRowsPerPage();
  }

////////////////////////////////////////////////////////////////////////////////
  public void reset()
  {
    setTokenValues(null);
    setBindVariables(null);
    setParameters(null);
  }

////////////////////////////////////////////////////////////////////////////////
  public String getFullName()
  {
    return ViewManager.getInstance().getFullName(getName(), getPackageName());
  }

////////////////////////////////////////////////////////////////////////////////
  public Collection getTokenValues()
  {
    return tokenValues;
  }

  public void setTokenValues(Collection newTokenValues)
  {
    tokenValues = newTokenValues;
  }

////////////////////////////////////////////////////////////////////////////////
  public Object getBindVariables()
  {
    return bindVariables;
  }

  public void setBindVariables(Object newBindVariables)
  {
    bindVariables = newBindVariables;
  }

////////////////////////////////////////////////////////////////////////////////
  public void addParameter(String name, String operator, Object value)
  {
    addParameter(new Parameter(name, null, operator, value, null));
  }

  public void addParameter(String name, String operator, Object value, Object nextValue)
  {
    addParameter(new Parameter(name, null, operator, value, nextValue));
  }
  
  private void addParameter(Parameter parameter)
  {
    if (getParameters()==null) setParameters(new ArrayList());

    String name = parameter.getName();
    if (StringUtils.exists(name)) 
    {
      Attribute attribute = getAttribute(name);
      if (attribute!=null) parameter.setColumnName(attribute.getColumnName());
    }
    getParameters().add(parameter);
  }

  public void addParameterOrderByAscending(String name)
  {
    addParameter(new Parameter(name, null, Parameter.OPERATOR_ORDER_BY, Parameter.VALUE_ASC, null));
  }

  public void addParameterOrderByDescending(String name)
  {
    addParameter(new Parameter(name, null, Parameter.OPERATOR_ORDER_BY, Parameter.VALUE_DESC, null));
  }

  public Collection getParameters()
  {
    return parameters;
  }

  public void setParameters(Collection newParameters)
  {
    parameters = newParameters;
  }

  public int getMaxFetchSize()
  {
    return maxFetchSize;
  }

  public void setMaxFetchSize(int newMaxFetchSize)
  {
    maxFetchSize = newMaxFetchSize;
  }

  public void setMaxFetchSize(String newMaxFetchSize)
  {
    maxFetchSize = ConvertUtils.convertInt(newMaxFetchSize, ViewManager.getInstance().getDefaultMaxFetchSize());
  }


////////////////////////////////////////////////////////////////////////////////
//        VALUELISTHANDLER
////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////
  public boolean getRetrieveRowCount()
  {
    return retrieveRowCount;
  }

  public boolean hasRetrieveRowCount()
  {
    return retrieveRowCount;
  }

  public void setRetrieveRowCount(String newRetrieveRowCount)
  {
    retrieveRowCount = ConvertUtils.convertBooleanType(newRetrieveRowCount, ValueListHandler.getDefaultRetrieveRowCount());
  }

  public void setRetrieveRowCount(boolean newRetrieveRowCount)
  {
    retrieveRowCount = newRetrieveRowCount;
  }

////////////////////////////////////////////////////////////////////////////////
  public String getRowCountMethod()
  {
    return rowCountMethod;
  }

  public void setRowCountMethod(String newRowCountMethod)
  {
    rowCountMethod = newRowCountMethod;
  }

////////////////////////////////////////////////////////////////////////////////
  public int getStartIndex()
  {
    return startIndex;
  }

  public void setStartIndex(int newStartIndex)
  {
    startIndex = newStartIndex;
  }

////////////////////////////////////////////////////////////////////////////////
  public int getRowsPerPage()
  {
    return rowsPerPage;
  }

  public void setRowsPerPage(String newRowsPerPage)
  {
    rowsPerPage = ConvertUtils.convertInt(newRowsPerPage, ValueListHandler.getDefaultRowsPerPage());
  }

  public void setRowsPerPage(int newRowsPerPage)
  {
    rowsPerPage = newRowsPerPage;
  }


////////////////////////////////////////////////////////////////////////////////
//        PUBLIC METHODS
////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////
  public void populate(Object target)
  throws Exception
  {
    ViewHelper.populate(getConnection(), this, target);
  }

////////////////////////////////////////////////////////////////////////////////
  public Collection getCollection()
  throws Exception
  {
    return getCollection(null);
  }

  public Collection getCollection(Object target)
  throws Exception
  {
    return ViewHelper.getCollection(getConnection(), this, target, null);
  }
  
////////////////////////////////////////////////////////////////////////////////
  public ValueListHandler getValueListHandler()
  throws Exception
  {
    return getValueListHandler(null);
  }

  public ValueListHandler getValueListHandler(Object target)
  throws Exception
  {
    ValueListHandler vlh = new ValueListHandler();
    vlh.setStartIndex(this.getStartIndex());
    vlh.setRowsPerPage(this.getRowsPerPage());
    vlh.setRetrieveRowCount(this.hasRetrieveRowCount());
    vlh.setRowCountMethod(this.getRowCountMethod());
    
    ViewHelper.getCollection(getConnection(), this, target, vlh);

    return vlh;
  }

////////////////////////////////////////////////////////////////////////////////
  protected Collection getAsDbCollection()
  throws Exception
  {
    return getAsDbCollection(null, null, null);
  }

  protected Collection getAsDbCollection(Object target)
  throws Exception
  {
    return getAsDbCollection(null, null, target);
  }

  protected Collection getAsDbCollection(String valueAttributeName, String labelAttributeName)
  throws Exception
  {
    return getAsDbCollection(valueAttributeName, labelAttributeName, null);
  }

  protected Collection getAsDbCollection(String valueAttributeName, String labelAttributeName, Object target)
  throws Exception
  {
    return DbCollectionManager.get(this, valueAttributeName, labelAttributeName, target);
  }

}