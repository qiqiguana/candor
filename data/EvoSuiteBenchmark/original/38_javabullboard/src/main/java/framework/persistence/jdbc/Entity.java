package framework.persistence.jdbc;

import java.util.Map;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

import framework.persistence.jdbc.EntityHelper;

/**
 * Bean representing a Database object (Table or View)
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.4 $ $Date: 2004/06/17 23:28:50 $
 */
public class Entity extends Component
{

  // Logger
  protected static Log log = LogFactory.getLog(Entity.class);

  protected String objectName;
  protected Collection primaryKeys;

  public Entity()
  {
    super();
    init();
  }

  private void init()
  {
    primaryKeys = new ArrayList();
    this.targetClassName = EntityManager.getInstance().getDefaultTargetClassName();
  }

////////////////////////////////////////////////////////////////////////////////
  public String getFullName()
  {
    return EntityManager.getInstance().getFullName(getName(), getPackageName());
  }

////////////////////////////////////////////////////////////////////////////////
  public String getObjectName()
  {
    return objectName;
  }

  public void setObjectName(String newObjectName)
  {
    objectName = newObjectName;
  }

////////////////////////////////////////////////////////////////////////////////
  public void addAttribute(Attribute attribute)
  {
    attributes.put(attribute.getName(), attribute);
    if (attribute.isPrimaryKey()) 
    {
      int keySeq = attribute.getKeySeq()-1;
      while (getPrimaryKeys().size()<keySeq) ((List)getPrimaryKeys()).add(0, null);
      ((List)getPrimaryKeys()).add(keySeq, attribute);
    }
  }

  public void removeAttribute(String name)
  {
    getPrimaryKeys().remove(getAttribute(name));
    attributes.remove(name);
  }

////////////////////////////////////////////////////////////////////////////////
  public Collection getPrimaryKeys()
  {
//    if (primaryKeys.isEmpty()) log.warn("getPrimaryKeys: The entity "+getName()+" has no primary key");
    return primaryKeys;
  }

  public void setPrimaryKeys(Collection newPrimaryKeys)
  {
    primaryKeys = newPrimaryKeys;
  }

////////////////////////////////////////////////////////////////////////////////
  public void load(Map target)
  throws Exception
  {
    EntityHelper.load(getConnection(), this, target);
  } 

////////////////////////////////////////////////////////////////////////////////
  public int create(Map target)
  throws Exception
  {
    return EntityHelper.create(getConnection(), this, target);
  }

////////////////////////////////////////////////////////////////////////////////
  public int store(Map target)
  throws Exception
  {
    return EntityHelper.store(getConnection(), this, target);
  }

////////////////////////////////////////////////////////////////////////////////
  public int remove(Map target)
  throws Exception
  {
    return EntityHelper.remove(getConnection(), this, target);
  }

  public int remove(Collection parameters)
  throws Exception
  {
    return EntityHelper.remove(getConnection(), this, parameters);
  }

}