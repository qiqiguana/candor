package framework.base;

import java.util.Enumeration;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;

/**
 * Framework Action servlet
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.3 $ $Date: 2004/05/06 18:59:47 $
 */
public class BaseActionServlet extends ActionServlet
{

  /**
   * Reloads the message resources files
   * @exception ServletException
   */
  public void reloadMessageResources()
  throws ServletException
  {
    ModuleConfig moduleConfig = initModuleConfig("", config);
    initModuleMessageResources(moduleConfig);
    Enumeration names = getServletConfig().getInitParameterNames();
    while (names.hasMoreElements()) 
    {
      String name = (String) names.nextElement();
      if (!name.startsWith("config/")) continue;
      String prefix = name.substring(6);
      moduleConfig = initModuleConfig(prefix, getServletConfig().getInitParameter(name));
      initModuleMessageResources(moduleConfig);
    }
  }

}