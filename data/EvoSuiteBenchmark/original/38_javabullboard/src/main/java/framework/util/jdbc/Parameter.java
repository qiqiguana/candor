package framework.util.jdbc;

import framework.base.BaseBean;

/**
 * Bean representing a JDBC parameter
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.5 $ $Date: 2004/06/02 20:34:37 $
 */
public class Parameter extends BaseBean
{

  public static final String OPERATOR_ORDER_BY = "ORDER BY";
  public static final String VALUE_ASC = "ASC";
  public static final String VALUE_DESC = "DESC";

  protected String name;
  protected String operator;
  protected Object value;
  protected Object nextValue;
  protected String columnName;

  public Parameter()
  {
  }
  
  public Parameter(String name, String columnName, String operator, Object value, Object nextValue)
  {
    this.name = name;
    this.columnName = columnName;
    this.operator = operator;
    this.value = value;
    this.nextValue = nextValue;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String newName)
  {
    name = newName;
  }

  public String getOperator()
  {
    return operator;
  }

  public void setOperator(String newOperator)
  {
    operator = newOperator;
  }

  public Object getValue()
  {
    return value;
  }

  public void setValue(Object newValue)
  {
    value = newValue;
  }

  public Object getNextValue()
  {
    return nextValue;
  }

  public void setNextValue(Object newNextValue)
  {
    nextValue = newNextValue;
  }

  public String getColumnName()
  {
    return columnName;
  }

  public void setColumnName(String newColumnName)
  {
    columnName = newColumnName;
  }

  /**
   * Check for a valid parameter
   * @return true if the parameter is valid, else false
   */  
  protected boolean isValid()
  {
    return JDBCUtils.isValidParameter(this);
  }

}