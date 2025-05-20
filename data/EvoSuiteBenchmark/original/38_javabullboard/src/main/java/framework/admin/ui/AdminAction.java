package framework.admin.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import framework.base.BaseAction;
import framework.ApplicationParameters;
import framework.base.BaseActionServlet;

/**
 * Framework administration actions
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.4 $ $Date: 2004/06/17 23:28:50 $
 */
public class AdminAction extends BaseAction
{

////////////////////////////////////////////////////////////////////////////////
  public void reloadGlobalParameters( ActionMapping mapping,
                                      ActionForm form,
                                      HttpServletRequest request,
                                      HttpServletResponse response)
  throws Exception
  {
    ApplicationParameters.reload();
  }

////////////////////////////////////////////////////////////////////////////////
  public void reloadMessageResources( ActionMapping mapping,
                                      ActionForm form,
                                      HttpServletRequest request,
                                      HttpServletResponse response)
  throws Exception
  {
    if (getServlet() instanceof BaseActionServlet)
    {
      ((BaseActionServlet)getServlet()).reloadMessageResources();
    }
    else
    {
      // This is working but we still need to check for unexpected behaviour
      getServlet().init();
    }
  }

}