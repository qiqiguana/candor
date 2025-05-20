package framework.persistence.jdbc;

import framework.base.BaseBean;
import framework.util.ConvertUtils;

/**
 * The bean representing a Database column / Java object mapping
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.2 $ $Date: 2004/05/07 19:39:36 $
 */
public class Attribute extends BaseBean implements Cloneable
{
  protected String name;
  protected String columnClassName;
  protected String columnType;
  protected String columnTypeName;
  protected String precision;
  protected String scale;
  protected String columnName;

  protected boolean nullable;
  protected boolean primaryKey;
  protected int keySeq;

  public Attribute()
  {
  }

  public Object clone()
  {
    try 
    {
      return super.clone();
    }
    catch (CloneNotSupportedException e) 
    {
      //This should not happen, since this class is Cloneable.
      throw new InternalError("Could not clone class Attribute");
    }
  }

  public String getName()
  {
    return name;
  }

  public void setName(String newName)
  {
    name = newName;
  }

  public String getColumnClassName()
  {
    return columnClassName;
  }

  public void setColumnClassName(String newColumnClassName)
  {
    columnClassName = newColumnClassName;
  }

  public Integer getColumnType()
  {
    return ConvertUtils.convertInteger(columnType);
  }

  public void setColumnType(String newColumnType)
  {
    columnType = newColumnType;
  }

  public String getColumnTypeName()
  {
    return columnTypeName;
  }

  public void setColumnTypeName(String newColumnTypeName)
  {
    columnTypeName = newColumnTypeName;
  }
/*
  public Integer getScale()
  {
    return ConvertUtils.convertInteger(scale);
  }
*/
  public String getScale()
  {
    return scale;
  }

  public void setScale(String newScale)
  {
    scale = newScale;
  }
/*
  public Integer getPrecision()
  {
    return ConvertUtils.convertInteger(precision);
  }
*/
  public String getPrecision()
  {
    return precision;
  }

  public void setPrecision(String newPrecision)
  {
    precision = newPrecision;
  }

  public String getColumnName()
  {
    return columnName;
  }

  public void setColumnName(String newColumnName)
  {
    columnName = newColumnName;
  }

  public boolean isNullable()
  {
    return nullable;
  }

  public void setNullable(String newNullable)
  {
    nullable = ConvertUtils.convertBooleanType(newNullable, true);
  }

  public void setNotNull(String newNotNull)
  {
    nullable = !ConvertUtils.convertBooleanType(newNotNull, false);
  }

  public boolean isPrimaryKey()
  {
    return primaryKey;
  }

  public void setPrimaryKey(String newPrimaryKey)
  {
    primaryKey = ConvertUtils.convertBooleanType(newPrimaryKey, false);
  }
/*
  public void setPrimaryKey(boolean newPrimaryKey)
  {
    primaryKey = newPrimaryKey;
  }
*/
  public int getKeySeq()
  {
    return keySeq;
  }

  public void setKeySeq(String newKeySeq)
  {
    keySeq = ConvertUtils.convertInt(newKeySeq, 0);
  }
}