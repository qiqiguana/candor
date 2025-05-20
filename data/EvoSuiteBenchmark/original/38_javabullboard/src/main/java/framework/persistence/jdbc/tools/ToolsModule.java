package framework.persistence.jdbc.tools;

import framework.persistence.jdbc.Module;
import framework.persistence.jdbc.View;

/**
 * A simple module used for framework needs
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.2 $ $Date: 2004/05/09 21:15:44 $
 */
public final class ToolsModule extends Module
{

  public ToolsModule()
  {
    super();
  }

  public ToolsModule(String poolName)
  {
    super(poolName);
  }

////////////////////////////////////////////////////////////////////////////////
  public View findAnyView(String name, String packageName)
  throws Exception
  {
    return super.findAnyView(name, packageName);
  }

}