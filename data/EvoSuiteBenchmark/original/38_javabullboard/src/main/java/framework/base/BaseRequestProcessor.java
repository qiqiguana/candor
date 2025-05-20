package framework.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.RequestProcessor;

/**
 * Base Request Processor
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.2 $ $Date: 2004/05/07 19:31:42 $
 */
public class BaseRequestProcessor extends RequestProcessor
{

////////////////////////////////////////////////////////////////////////////////
  protected boolean processPreprocess(HttpServletRequest request,
                                      HttpServletResponse response) 
  {
    return (true);
  }
}