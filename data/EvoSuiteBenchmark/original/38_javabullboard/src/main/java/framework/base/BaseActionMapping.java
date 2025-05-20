package framework.base;

import org.apache.struts.action.ActionMapping;

/**
 * Framework Action mapping
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.2 $ $Date: 2004/05/06 18:59:47 $
 */
public class BaseActionMapping extends ActionMapping
{
  protected String resourcePrefix;
  protected String formActionPath;

  public String getResourcePrefix()
  {
    return resourcePrefix;
  }

  public void setResourcePrefix(String newResourcePrefix)
  {
    resourcePrefix = newResourcePrefix;
  }

  public String getFormActionPath()
  {
    return formActionPath;
  }

  public void setFormActionPath(String newFormActionPath)
  {
    formActionPath = newFormActionPath;
  }



}