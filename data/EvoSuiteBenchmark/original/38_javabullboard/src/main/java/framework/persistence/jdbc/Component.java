package framework.persistence.jdbc;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import java.sql.Connection;

import framework.base.BaseBean;

/**
 * Common attributes between a View and an Entity
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.2 $ $Date: 2004/05/07 19:40:21 $
 */
public abstract class Component extends BaseBean implements Cloneable
{

  protected String name;
  protected String packageName;
  protected String comment;
  protected String sqlQuery;
  protected Map attributes;
  protected Module module;
  protected String targetClassName;
  protected String version;
  protected long timestamp;

  public Component()
  {
    attributes = new HashMap();
  }

  public Object clone()
  {
    try 
    {
      Component result = (Component)super.clone();
      if (getAttributes()!=null) 
      {
//        result.setAttributes(new HashMap());
        Iterator it = getAttributes().values().iterator();
        while (it.hasNext())
        {
          Attribute attribute = (Attribute)it.next();
          result.addAttribute((Attribute)attribute.clone());
        }
      }
      return result;
    }
    catch (CloneNotSupportedException e) 
    {
      // This should not happen, since this class is Cloneable.
      throw new InternalError("Could not clone component");
    }
  }

  public abstract String getFullName();
/*  public String getFullName()
  {
    return "";
  }
*/
////////////////////////////////////////////////////////////////////////////////
  public String getName()
  {
    return name;
  }

  public void setName(String newName)
  {
    name = newName;
  }

////////////////////////////////////////////////////////////////////////////////
  public String getPackageName()
  {
    return packageName;
  }

  public void setPackageName(String newPackageName)
  {
    packageName = newPackageName;
  }

////////////////////////////////////////////////////////////////////////////////
  public String getTargetClassName()
  {
    return targetClassName;
  }

  public void setTargetClassName(String newTargetClassName)
  {
    targetClassName = newTargetClassName;
  }

////////////////////////////////////////////////////////////////////////////////
  public String getComment()
  {
    return comment;
  }

  public void setComment(String newComment)
  {
    comment = newComment;
  }

////////////////////////////////////////////////////////////////////////////////
  public String getSqlQuery()
  {
    return sqlQuery;
  }

  public void setSqlQuery(String newSqlQuery)
  {
    sqlQuery = newSqlQuery;
  }

////////////////////////////////////////////////////////////////////////////////
  public int getAttributesSize()
  {
    return getAttributes().size();
  }

  public Map getAttributes()
  {
    return attributes;
  }

  public Collection getAttributeCollection()
  {
    return getAttributes().values();
  }

  public Attribute getAttribute(String name)
  {
    Attribute attribute = (Attribute)getAttributes().get(name);
    if (attribute==null) log.warn("getAttribute: Attribute not found "+name);
    return attribute;
  }

  public Attribute getAttributeByColumnName(String columnName)
  {
    Attribute attribute = null;
    Iterator it = getAttributeCollection().iterator();
    while (it.hasNext() && attribute==null) 
    {
      Attribute tmp = (Attribute)it.next();
      if (tmp.getColumnName().equals(columnName)) attribute = tmp;
    }
    if (attribute==null) log.warn("getAttributeByColumnName: Attribute not found "+columnName);
    return attribute;
  }


  public void addAttributes(Map newAttributes)
  {
    Iterator it = newAttributes.values().iterator();
    while (it.hasNext()) addAttribute((Attribute)it.next());
  }

  public void addAttribute(Attribute attribute)
  {
    getAttributes().put(attribute.getName(), attribute);
  }

  public void removeAttribute(String name)
  {
    getAttributes().remove(name);
  }

  public void removeAttributes()
  {
    Iterator it = getAttributes().keySet().iterator();
    while (it.hasNext()) removeAttribute((String)it.next());
  }

////////////////////////////////////////////////////////////////////////////////
  public Module getModule()
  {
    return module;
  }

  public void setModule(Module newModule)
  {
    module = newModule;
  }

  public Connection getConnection()
  throws Exception
  {
    if (getModule()!=null) return getModule().getConnection();
    else return null;
  }

  public String getVersion()
  {
    return version;
  }

  public void setVersion(String newVersion)
  {
    version = newVersion;
  }

  public long getTimestamp()
  {
    return timestamp;
  }

  public void setTimestamp(long newTimestamp)
  {
    timestamp = newTimestamp;
  }

}