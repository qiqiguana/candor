package framework.persistence.jdbc.tools.ui;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.DynaActionForm;

import framework.base.ValueListHandler;
import framework.base.BaseAction;
import framework.persistence.jdbc.Module;
import framework.persistence.jdbc.ViewManager;
import framework.persistence.jdbc.View;
import framework.persistence.jdbc.Attribute;
import framework.persistence.jdbc.tools.ToolsModule;
import framework.persistence.jdbc.pool.JDBCPoolManager;
import framework.util.jdbc.JDBCUtils;
import framework.util.StringUtils;
import framework.util.ConvertUtils;

/**
 * View actions
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.6 $ $Date: 2004/05/28 00:09:55 $
 */
public class ViewAction extends BaseAction
{  

  public static final String KEY_VIEW = "framework.persistence.jdbc.tools.ui.view";
  public static final String KEY_VIEW_LIST = "framework.persistence.jdbc.tools.ui.view.list";
  public static final String KEY_VIEW_FULLNAME_PATTERN = "framework.persistence.jdbc.tools.ui.view.fullNamePattern";
  public static final String KEY_POOL_NAME_LIST = "framework.persistence.jdbc.tools.ui.view.poolNameList";

  public static final String KEY_MODE = "framework.persistence.jdbc.tools.ui.view.mode";

////////////////////////////////////////////////////////////////////////////////
  public void list(ActionMapping mapping,
                            ActionForm form,
                            HttpServletRequest request,
                            HttpServletResponse response)
  throws Exception
  {
    Collection result =  null;
    DynaActionForm viewListForm = (DynaActionForm)form;
    String fullNamePattern = (String)viewListForm.get("fullName");

    // Try to get the list from session...
    result = (Collection)request.getSession().getAttribute(KEY_VIEW_LIST);
    boolean hasToList = result==null || ConvertUtils.convertBooleanType(request.getParameter("refresh"), false);

    // Could not get the list from session, search on file system
    if (hasToList)
    {
      result = ViewManager.getInstance().listFiles(fullNamePattern);
      request.getSession().setAttribute(KEY_VIEW_FULLNAME_PATTERN, fullNamePattern);
      request.getSession().setAttribute(KEY_VIEW_LIST, result);
    }

    request.setAttribute(KEY_VIEW_LIST, result);
  }

////////////////////////////////////////////////////////////////////////////////
  public void create(ActionMapping mapping,
                              ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
  throws Exception
  {
    DynaActionForm viewForm = (DynaActionForm)form;
    viewForm.initialize(mapping);// reset(mapping, request);
    request.getSession().removeAttribute(KEY_VIEW);
    request.getSession().setAttribute(KEY_MODE, MODE_CREATE);
  }

////////////////////////////////////////////////////////////////////////////////
  public void query( ActionMapping mapping,
                              ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
  throws Exception
  {
    Collection poolNameList = (Collection)request.getSession().getAttribute(KEY_POOL_NAME_LIST);
    if (poolNameList==null)
    {
      poolNameList = JDBCPoolManager.getDeclaredPoolNamesAsDbCollection();
      request.getSession().setAttribute(KEY_POOL_NAME_LIST, poolNameList);      
    }
  }

////////////////////////////////////////////////////////////////////////////////
  public void attributes(ActionMapping mapping,
                                  ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response)
  throws Exception
  {
    DynaActionForm viewForm = (DynaActionForm)form;
    String name = (String)viewForm.get("name");
    String packageName = (String)viewForm.get("packageName");
    String targetClassName = (String)viewForm.get("targetClassName");
    String comment = (String)viewForm.get("comment");
    String sqlQuery = (String)viewForm.get("sqlQuery");
    String poolName = (String)viewForm.get("poolName");

    // We create the view :
    // - if none found (CREATE)
    // - if sql query has changed (EDIT then update the query)
    boolean hasToUpdate = true;
    View oldView = (View)request.getSession().getAttribute(KEY_VIEW);
    if (oldView!=null)
    {
      String oldSqlQuery = oldView.getSqlQuery();
      String tmpSqlQuery = StringUtils.reformat(sqlQuery);
      String tmpOldSqlQuery = StringUtils.reformat(oldSqlQuery);
      hasToUpdate = !tmpSqlQuery.equals(tmpOldSqlQuery);
    }

    if (hasToUpdate)
    {
      name = StringUtils.exists(name)?name:ViewManager.getInstance().getType();
      targetClassName = StringUtils.exists(targetClassName)?targetClassName:ViewManager.getInstance().getDefaultTargetClassName();
      Module module = new ToolsModule(poolName);
      try
      {
        module.connect();
        View newView = (View)ViewManager.getInstance().create(module.getConnection(), name, packageName, targetClassName, comment, sqlQuery);

        // Keep the old attribute definitions for the new view (EDIT then update the query)
        if (oldView!=null)
        {
          Iterator it = oldView.getAttributeCollection().iterator();
          while (it.hasNext()) 
          {
            Attribute oldAttribute = (Attribute)it.next();
            Attribute newAttribute = newView.getAttributeByColumnName(oldAttribute.getColumnName());
            boolean isAttributeAlreadyDefined = newAttribute!=null;
            if (isAttributeAlreadyDefined)
            {
              newView.removeAttribute(newAttribute.getName());
              newView.addAttribute(oldAttribute);
            }
          }
        }
        request.getSession().setAttribute(KEY_VIEW, newView);
        copyProperties(newView, viewForm);
      }
      catch(Exception e)
      {
        addError(request, "error.framework.persistence.jdbc.view.query.exception", e);
      }
      finally
      {
        module.disconnect();
      }
    }    
  }

////////////////////////////////////////////////////////////////////////////////
// view => viewForm
  private void copyProperties(View view, DynaActionForm viewForm)
  throws Exception
  {
//BeanUtils.copyProperty(viewForm, view);
    viewForm.set("name", view.getName());
    viewForm.set("packageName", view.getPackageName());
    viewForm.set("targetClassName", view.getTargetClassName());
    viewForm.set("comment", view.getComment());
    viewForm.set("sqlQuery", view.getSqlQuery());
    viewForm.set("attributes", (Attribute[])view.getAttributeCollection().toArray(new Attribute[view.getAttributesSize()]));
    viewForm.set("oldAttributes", (Attribute[])((View)view.clone()).getAttributeCollection().toArray(new Attribute[view.getAttributesSize()]));
  }

////////////////////////////////////////////////////////////////////////////////
  public void name(ActionMapping mapping,
                            ActionForm form,
                            HttpServletRequest request,
                            HttpServletResponse response)
  throws Exception
  {
    DynaActionForm viewForm = (DynaActionForm)form;
    Attribute[] attributes = (Attribute[])viewForm.get("attributes");
    Map duplicates = ViewManager.getInstance().validateAttributeNameUniqueness(attributes);
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
    DynaActionForm viewForm = (DynaActionForm)form;
    String name = (String)viewForm.get("name");
    String packageName = (String)viewForm.get("packageName");
    String targetClassName = (String)viewForm.get("targetClassName");
    boolean isCreateMode = MODE_CREATE.equals(request.getSession().getAttribute(KEY_MODE));
    
    if (isCreateMode)
    {
      try
      {
        View view = (View)ViewManager.getInstance().read(name, packageName);
        if (view!=null) 
        {
          addError(request, "error.framework.persistence.jdbc.view.alreadyExist", view.getFullName());
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
    DynaActionForm viewForm = (DynaActionForm)form;
    View view = (View)request.getSession().getAttribute(KEY_VIEW);
    copyProperties(viewForm, view);
    ViewManager.getInstance().writeFile(view);
    request.getSession().removeAttribute(KEY_VIEW);
  }

////////////////////////////////////////////////////////////////////////////////
//  viewForm => view
  private void copyProperties(DynaActionForm viewForm, View view)
  throws Exception
  {
/*
    ObjectUtils.copyProperties(viewForm, view);
    BeanUtils.copyProperties(view, viewForm);
    PropertyUtils.copyProperties(viewForm, view);
*/
    view.setName((String)viewForm.get("name"));
    view.setPackageName((String)viewForm.get("packageName"));
    view.setComment((String)viewForm.get("comment"));
    view.setSqlQuery((String)viewForm.get("sqlQuery"));

    Attribute[] attributes = (Attribute[])viewForm.get("attributes");
    Attribute[] oldAttributes = (Attribute[])viewForm.get("oldAttributes");

    int nbAttributes = view.getAttributesSize();
    for (int i=0; i<nbAttributes; i++)
    {
      String name = attributes[i].getName();
      String oldName = oldAttributes[i].getName();
      String columnClassName = attributes[i].getColumnClassName();
      String oldColumnClassName = oldAttributes[i].getColumnClassName();
      
      // Attribute name has been changed changed
      if (!oldName.equals(name))
      {
        Attribute attribute = view.getAttribute(oldName);
        attribute.setName(name);
        view.removeAttribute(oldName);
        view.addAttribute(attribute);
      }
      
      // Set column class name
      view.getAttribute(name).setColumnClassName(columnClassName);
    }
  }

////////////////////////////////////////////////////////////////////////////////
  public void edit(ActionMapping mapping,
                            ActionForm form,
                            HttpServletRequest request,
                            HttpServletResponse response)
  throws Exception
  {
    DynaActionForm viewForm = (DynaActionForm)form;
    String name = (String)viewForm.get("name");
    String packageName = (String)viewForm.get("packageName");
    View view = (View)ViewManager.getInstance().read(name, packageName);
    copyProperties(view, viewForm);
    request.getSession().setAttribute(KEY_VIEW, view);
    request.getSession().setAttribute(KEY_MODE, MODE_EDIT);
  }

////////////////////////////////////////////////////////////////////////////////
  public void dump(ActionMapping mapping,
                            ActionForm form,
                            HttpServletRequest request,
                            HttpServletResponse response)
  throws Exception
  {
    DynaActionForm viewForm = (DynaActionForm)form;
    String name = (String)viewForm.get("name");
    String packageName = (String)viewForm.get("packageName");
    String viewDump = ViewManager.getInstance().dump(name, packageName);
    request.setAttribute("view", viewDump);
  }

////////////////////////////////////////////////////////////////////////////////
  public void remove(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
  throws Exception
  {
    DynaActionForm viewForm = (DynaActionForm)form;
    String name = (String)viewForm.get("name");
    String packageName = (String)viewForm.get("packageName");
    ViewManager.getInstance().deleteFile(name, packageName);
  }

////////////////////////////////////////////////////////////////////////////////
  public void test(ActionMapping mapping,
                            ActionForm form,
                            HttpServletRequest request,
                            HttpServletResponse response)
  throws Exception
  {
    DynaActionForm viewTestForm = (DynaActionForm)form;
    String name = (String)viewTestForm.get("name");
    String packageName = (String)viewTestForm.get("packageName");
    String poolName = (String)viewTestForm.get("poolName");

    Collection poolNameList = JDBCPoolManager.getDeclaredPoolNamesAsDbCollection();

    // Read view file
    View view = (View)ViewManager.getInstance().read(name, packageName);

    // Populate form
    copyProperties(view, viewTestForm);

    // Set view in session
    request.getSession().setAttribute(KEY_VIEW, view);

    // Retrieve connection information
    Module module = new ToolsModule(poolName);
    Map keywords = new HashMap();
    try
    {
      module.connect();

      Map metaData = JDBCUtils.getConnectionMetaData(module.getConnection());
      request.setAttribute("metaData", metaData);
      String databaseType = StringUtils.valueOf(metaData.get("databaseProductName"));
      keywords = JDBCUtils.getDatabaseKeywords(databaseType);
    }
    catch(Exception e)
    {
      addError(request, "error.framework.persistence.jdbc.entity.connect.exception", new Object[] {module.getPoolName(), e});
    }
    finally
    {
      module.disconnect();
    }

    String sqlQuery = StringUtils.prettyPrint(keywords, view.getSqlQuery(), "<b>", "</b>", "toUpperCase");
    sqlQuery = StringUtils.replace(sqlQuery, "?", "<input type='text' name='bindVariables' size='5'/>");
    request.setAttribute("sqlQuery", sqlQuery);
    request.setAttribute("poolNameList", poolNameList);
  }

////////////////////////////////////////////////////////////////////////////////
  public void testRun(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
  throws Exception
  {
    DynaActionForm viewTestForm = (DynaActionForm)form;
    String poolName = (String)viewTestForm.get("poolName");
    String[] bindVariables = (String[])viewTestForm.get("bindVariables");

    // Get the view from session
    View view = (View)request.getSession().getAttribute(KEY_VIEW);

    // Populate form
    copyProperties(view, viewTestForm);

    // Get query result
    ValueListHandler result = null;
    Module module = new ToolsModule(poolName);
    try
    {
      module.connect();
      view.setModule(module);
      view.reset();
      view.setBindVariables(bindVariables);
      result = view.getValueListHandler();
    }
    catch(Exception e)
    {
      addError(request, "error.framework.persistence.jdbc.view.testRun.exception", e.getMessage());
    }
    finally
    {
      module.disconnect();
    }

    request.setAttribute("result", result);
  }

/*
////////////////////////////////////////////////////////////////////////////////
  public void reload(ActionMapping mapping,
                          ActionForm form,
                          HttpServletRequest request,
                          HttpServletResponse response,
                          ActionErrors errors,
                          Module module)
  throws Exception
  {
    DynaActionForm viewForm = (DynaActionForm)form;
    String name = (String)viewForm.get("name");
    String packageName = (String)viewForm.get("packageName");
    ViewManager.reloadView(name, packageName);
  }
*/

}