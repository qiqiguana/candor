package framework.persistence.jdbc.tools.ui;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.DynaActionForm;

import framework.base.BaseAction;
import framework.util.jdbc.JDBCUtils;
import framework.persistence.jdbc.Module;
import framework.persistence.jdbc.EntityManager;
import framework.persistence.jdbc.Entity;
import framework.persistence.jdbc.Attribute;
import framework.persistence.jdbc.tools.ToolsModule;
import framework.util.StringUtils;
import framework.util.ConvertUtils;
import framework.persistence.jdbc.tools.DbCollectionManager;
import framework.persistence.jdbc.pool.JDBCPoolManager;

/**
 * Entity actions
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.5 $ $Date: 2004/05/09 21:16:36 $
 */
public class EntityAction extends BaseAction
{  

  public static final String KEY_ENTITY = "framework.persistence.jdbc.tools.ui.entity";
  public static final String KEY_ENTITY_LIST = "framework.persistence.jdbc.tools.ui.entity.list";
  public static final String KEY_ENTITY_FULLNAME_PATTERN = "framework.persistence.jdbc.tools.ui.entity.fullNamePattern";
  public static final String KEY_POOL_NAME_LIST = "framework.persistence.jdbc.tools.ui.entity.poolNameList";
  public static final String KEY_SCHEMA_LIST = "framework.persistence.jdbc.tools.ui.entity.schemaList";
  public static final String KEY_TABLE_TYPE_LIST = "framework.persistence.jdbc.tools.ui.entity.tableTypeList";

  public static final String KEY_MODE = "framework.persistence.jdbc.tools.ui.entity.mode";

////////////////////////////////////////////////////////////////////////////////
  public void list(ActionMapping mapping,
                            ActionForm form,
                            HttpServletRequest request,
                            HttpServletResponse response)
  throws Exception
  {
    Collection result =  null;
    DynaActionForm entityListForm = (DynaActionForm)form;
    String fullNamePattern = (String)entityListForm.get("fullName");

    // Try to get the list from session...
    result = (Collection)request.getSession().getAttribute(KEY_ENTITY_LIST);
    boolean hasToList = result==null || ConvertUtils.convertBooleanType(request.getParameter("refresh"), false);

    // Could not get the list from session, search on file system
    if (hasToList)
    {
      result = EntityManager.getInstance().listFiles(fullNamePattern);
      request.getSession().setAttribute(KEY_ENTITY_FULLNAME_PATTERN, fullNamePattern);
      request.getSession().setAttribute(KEY_ENTITY_LIST, result);
    }

    request.setAttribute(KEY_ENTITY_LIST, result);
  }

////////////////////////////////////////////////////////////////////////////////
  public void create(ActionMapping mapping,
                              ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
  throws Exception
  {
    DynaActionForm entityForm = (DynaActionForm)form;
    entityForm.initialize(mapping);// reset(mapping, request);
    request.getSession().removeAttribute(KEY_ENTITY);   
    request.getSession().setAttribute(KEY_MODE, MODE_CREATE);
  }

////////////////////////////////////////////////////////////////////////////////
  public void select(ActionMapping mapping,
                              ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
  throws Exception
  {
    DynaActionForm entityForm = (DynaActionForm)form;
    String schema = (String)entityForm.get("schema");
    String type = (String)entityForm.get("type");
    String poolName = (String)entityForm.get("poolName");

    type = StringUtils.exists(type)?type:"TABLE";
    entityForm.set("type", type);

    Collection poolNameList = (Collection)request.getSession().getAttribute(KEY_POOL_NAME_LIST);
    Collection schemaList = (Collection)request.getSession().getAttribute(KEY_SCHEMA_LIST+" - "+poolName);
    Collection typeList = (Collection)request.getSession().getAttribute(KEY_TABLE_TYPE_LIST+" - "+poolName);
    Collection objectNameList = null;

    // Get the pool names
    if (poolNameList==null)
    {
      poolNameList = JDBCPoolManager.getDeclaredPoolNamesAsDbCollection();
      request.getSession().setAttribute(KEY_POOL_NAME_LIST, poolNameList);      
    }

    Module module = new ToolsModule(poolName);
    try
    {
      module.connect();

      // If no schema selected, use the connection schema
      schema = StringUtils.exists(schema)?schema:module.getConnection().getMetaData().getUserName();
      entityForm.set("schema", schema);

      // Get the schemas
      if (schemaList==null)
      {
        Collection tmpSchemaList = JDBCUtils.getDatabaseSchemas(module.getConnection());
        schemaList = DbCollectionManager.get(tmpSchemaList, "TABLE_SCHEM", "TABLE_SCHEM");
        request.getSession().setAttribute(KEY_SCHEMA_LIST+" - "+poolName, schemaList);
      }
  
      // Get the table types
      if (typeList==null)
      {
        Collection tmpTypeList = JDBCUtils.getTableTypes(module.getConnection());
        typeList = DbCollectionManager.get(tmpTypeList, "TABLE_TYPE", "TABLE_TYPE");
        request.getSession().setAttribute(KEY_TABLE_TYPE_LIST+" - "+poolName, typeList);
      }

      // Get the object names  
      Collection tmpObjectNameList = JDBCUtils.getDatabaseTables(module.getConnection(), null, schema, null, new String[] {type});
      objectNameList = DbCollectionManager.get(tmpObjectNameList, "TABLE_NAME", "TABLE_NAME");
    }
    catch(Exception e)
    {
      addError(request, "error.framework.persistence.jdbc.entity.select.exception", new Object[] {module.getPoolName(), e});
    }
    finally
    {
      module.disconnect();
    }

    Collection emptyList = new ArrayList();
    request.setAttribute("poolNameList", poolNameList!=null?poolNameList:emptyList);
    request.setAttribute("schemaList", schemaList!=null?schemaList:emptyList);
    request.setAttribute("typeList", typeList!=null?typeList:emptyList);
    request.setAttribute("objectNameList", objectNameList!=null?objectNameList:emptyList);
    request.getSession().setAttribute(KEY_MODE, MODE_CREATE);   
  }

////////////////////////////////////////////////////////////////////////////////
  public void attributes(ActionMapping mapping,
                                  ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response)
  throws Exception
  {    
    DynaActionForm entityForm = (DynaActionForm)form;
    String objectName = (String)entityForm.get("objectName");
    String packageName = (String)entityForm.get("packageName");
    String targetClassName = (String)entityForm.get("targetClassName");
    String comment = (String)entityForm.get("comment");
    String poolName = (String)entityForm.get("poolName");

    // We create the entity :
    // - if none found (CREATE)
    // - if object name has changed (EDIT then select another Object)
    Entity entity = (Entity)request.getSession().getAttribute(KEY_ENTITY);
    boolean hasToUpdate = entity==null || !objectName.equals(entity.getObjectName());
    if (hasToUpdate)
    {
      targetClassName = StringUtils.exists(targetClassName)?targetClassName:EntityManager.getInstance().getDefaultTargetClassName();
      Module module = new ToolsModule(poolName);
      try
      {
        module.connect();
        entity = (Entity)EntityManager.getInstance().create(module.getConnection(), objectName, packageName, targetClassName, comment);  
        request.getSession().setAttribute(KEY_ENTITY, entity);
        copyProperties(entity, entityForm);
      }
      catch(Exception e)
      {
        addError(request, "error.framework.persistence.jdbc.entity.describe.exception", e);
      }
      finally
      {
        module.disconnect();
      }
    }
  }

////////////////////////////////////////////////////////////////////////////////
// entity => entityForm
  private void copyProperties(Entity entity, DynaActionForm viewForm)
  throws Exception
  {
    viewForm.set("name", entity.getName());
    viewForm.set("packageName", entity.getPackageName());
    viewForm.set("targetClassName", entity.getTargetClassName());
    viewForm.set("objectName", entity.getObjectName());
    viewForm.set("comment", entity.getComment());
    viewForm.set("attributes", (Attribute[])entity.getAttributeCollection().toArray(new Attribute[entity.getAttributesSize()]));
    viewForm.set("oldAttributes", (Attribute[])((Entity)entity.clone()).getAttributeCollection().toArray(new Attribute[entity.getAttributesSize()]));
  }
  
////////////////////////////////////////////////////////////////////////////////
  public void name(ActionMapping mapping,
                            ActionForm form,
                            HttpServletRequest request,
                            HttpServletResponse response)
  throws Exception
  {
    DynaActionForm entityForm = (DynaActionForm)form;
    Attribute[] attributes = (Attribute[])entityForm.get("attributes");
    Map duplicates = EntityManager.getInstance().validateAttributeNameUniqueness(attributes);
    Iterator it = duplicates.keySet().iterator();
    while (it.hasNext()) 
    {
      String name = (String)it.next();
      addError(request, "error.framework.persistence.jdbc.attribute.name.unique", name);
    }
  }

////////////////////////////////////////////////////////////////////////////////
  public void confirm(ActionMapping mapping,
                              ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
  throws Exception
  {
    DynaActionForm entityForm = (DynaActionForm)form;
    String name = (String)entityForm.get("name");
    String packageName = (String)entityForm.get("packageName");
    String targetClassName = (String)entityForm.get("targetClassName");
    boolean isCreateMode = MODE_CREATE.equals(request.getSession().getAttribute(KEY_MODE));
    
    if (isCreateMode)
    {
      try
      {
        Entity entity = (Entity)EntityManager.getInstance().read(name, packageName);
        if (entity!=null) 
        {
          addError(request, "error.framework.persistence.jdbc.entity.alreadyExist", entity.getFullName());
        }
      }
      catch(FileNotFoundException fnfe)
      {
        // Ok, no valid component found under the requested name
      }
    }
  }

////////////////////////////////////////////////////////////////////////////////
  public void write(ActionMapping mapping,
                              ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
  throws Exception
  {
    DynaActionForm entityForm = (DynaActionForm)form;
    Entity entity = (Entity)request.getSession().getAttribute(KEY_ENTITY);
    copyProperties(entityForm, entity);
    EntityManager.getInstance().writeFile(entity);
    request.getSession().removeAttribute(KEY_ENTITY);   
  }

////////////////////////////////////////////////////////////////////////////////
//  entityForm => entity
  private void copyProperties(DynaActionForm entityForm, Entity entity)
  throws Exception
  {
    BeanUtils.copyProperties(entity, entityForm);

    Attribute[] attributes = (Attribute[])entityForm.get("attributes");
    Attribute[] oldAttributes = (Attribute[])entityForm.get("oldAttributes");

    int nbAttributes = entity.getAttributesSize();
    for (int i=0; i<nbAttributes; i++)
    {
      String name = attributes[i].getName();
      String oldName = oldAttributes[i].getName();
      String columnClassName = attributes[i].getColumnClassName();
      String oldColumnClassName = oldAttributes[i].getColumnClassName();
      
      // Attribute name has been changed changed
      if (!oldName.equals(name))
      {
        Attribute attribute = entity.getAttribute(oldName);
        attribute.setName(name);
        entity.removeAttribute(oldName);
        entity.addAttribute(attribute);
      }
      
      // Set column class name
      entity.getAttribute(name).setColumnClassName(columnClassName);
    }
  }

////////////////////////////////////////////////////////////////////////////////
  public void edit(ActionMapping mapping,
                            ActionForm form,
                            HttpServletRequest request,
                            HttpServletResponse response)
  throws Exception
  {
    DynaActionForm entityForm = (DynaActionForm)form;
    String name = (String)entityForm.get("name");
    String packageName = (String)entityForm.get("packageName");
    Entity entity = (Entity)EntityManager.getInstance().read(name, packageName);
    copyProperties(entity, entityForm);
    request.getSession().setAttribute(KEY_ENTITY, entity);
    request.getSession().setAttribute(KEY_MODE, MODE_EDIT);
  }

////////////////////////////////////////////////////////////////////////////////
  public void dump(ActionMapping mapping,
                            ActionForm form,
                            HttpServletRequest request,
                            HttpServletResponse response)
  throws Exception
  {
    DynaActionForm entityForm = (DynaActionForm)form;
    String name = (String)entityForm.get("name");
    String packageName = (String)entityForm.get("packageName");
    String entityDump = EntityManager.getInstance().dump(name, packageName);
    request.setAttribute("entity", entityDump);
  }

////////////////////////////////////////////////////////////////////////////////
  public void remove(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
  throws Exception
  {
    DynaActionForm entityForm = (DynaActionForm)form;
    String name = (String)entityForm.get("name");
    String packageName = (String)entityForm.get("packageName");
    EntityManager.getInstance().deleteFile(name, packageName);
  }
  
}