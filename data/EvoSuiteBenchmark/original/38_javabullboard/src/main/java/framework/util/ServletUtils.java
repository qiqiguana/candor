package framework.util;

import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.tiles.ComponentConstants;
import org.apache.struts.tiles.ComponentContext;

import framework.ApplicationParameters;

/**
 * Prints some useful debug information for JSP
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.8 $ $Date: 2004/06/17 23:28:51 $
 */
public final class ServletUtils 
{

  // Logger
  private static Log log = LogFactory.getLog(ServletUtils.class);

  /**
   * Protected constructor
   */
  protected ServletUtils()
  {
  }

////////////////////////////////////////////////////////////////////////////////
//  SESSION MANGEMENT METHODS
////////////////////////////////////////////////////////////////////////////////



////////////////////////////////////////////////////////////////////////////////
//  DEBUG METHODS
////////////////////////////////////////////////////////////////////////////////
  /**
   * Prints some request properties:
   * - QueryString
   * - RequestURI
   * - RequestURL
   * @param request The HttpServletRequest
   * @return Debug infos as String format
   */
  public static String showRequestProperties(HttpServletRequest request)
  {
    StringBuffer sb = new StringBuffer("\n<!---------------- REQUEST PROPERTIES ---------------->");
    sb.append("\n<!-- request.hashCode()=").append(request.hashCode()).append(" -->");
    sb.append("\n<!-- request.getPathInfo()=").append(request.getPathInfo()).append(" -->");
    sb.append("\n<!-- request.getPathTranslated()=").append(request.getPathTranslated()).append(" -->");
    sb.append("\n<!-- request.getProtocol()=").append(request.getProtocol()).append(" -->");
    sb.append("\n<!-- request.getAuthType()=").append(request.getAuthType()).append(" -->");
    sb.append("\n<!-- request.getCharacterEncoding()=").append(request.getCharacterEncoding()).append(" -->");
    sb.append("\n<!-- request.getContentType()=").append(request.getContentType()).append(" -->");
    sb.append("\n<!-- request.getContextPath()=").append(request.getContextPath()).append(" -->");
    sb.append("\n<!-- request.getMethod()=").append(request.getMethod()).append(" -->");
    sb.append("\n<!-- request.getRemoteAddr()=").append(request.getRemoteAddr()).append(" -->");
    sb.append("\n<!-- request.getRemoteHost()=").append(request.getRemoteHost()).append(" -->");
    sb.append("\n<!-- request.getRemoteUser()=").append(request.getRemoteUser()).append(" -->");
    sb.append("\n<!-- request.getRequestedSessionId()=").append(request.getRequestedSessionId()).append(" -->");
    sb.append("\n<!-- request.getScheme()=").append(request.getScheme()).append(" -->");
    sb.append("\n<!-- request.getServerName()=").append(request.getServerName()).append(" -->");
    sb.append("\n<!-- request.getServerPort()=").append(request.getServerPort()).append(" -->");
    sb.append("\n<!-- request.getServletPath()=").append(request.getServletPath()).append(" -->");
    sb.append("\n<!-- request.getQueryString()=").append(request.getQueryString()).append(" -->");
    sb.append("\n<!-- request.getRequestURI()=").append(request.getRequestURI()).append(" -->");
    sb.append("\n<!-- request.getRequestURL()=").append(request.getRequestURL()).append(" -->");
    return sb.toString();
  }

  /**
   * Prints cookies
   * @param request The HttpServletRequest
   * @return Debug infos as String format
   */
  public static String showCookies(HttpServletRequest request)
  {
    StringBuffer sb = new StringBuffer("\n<!---------------- COOKIES ---------------->");
    Cookie[] cookies = request.getCookies();
    for (int i=0; cookies!=null && i<cookies.length; i++) 
    {
      sb.append("\n<!-- ").append(i).append(" -->");     
      sb.append("\n<!-- cookie.getName()=").append(cookies[i].getName()).append(" -->");
      sb.append("\n<!-- cookie.getComment()=").append(cookies[i].getComment()).append(" -->");
      sb.append("\n<!-- cookie.getDomain()=").append(cookies[i].getDomain()).append(" -->");
      sb.append("\n<!-- cookie.getMaxAge()=").append(cookies[i].getMaxAge()).append(" -->");
      sb.append("\n<!-- cookie.getPath()=").append(cookies[i].getPath()).append(" -->");
      sb.append("\n<!-- cookie.getSecure()=").append(cookies[i].getSecure()).append(" -->");
      sb.append("\n<!-- cookie.getValue()=").append(cookies[i].getValue()).append(" -->");
      sb.append("\n<!-- cookie.getVersion()=").append(cookies[i].getVersion()).append(" -->");
    }
    return sb.toString();
  }

  /**
   * Prints the GlobalParameters values
   * @return Debug infos as String format
   */
  public static String showGlobalParameters()
  {
    StringBuffer sb = new StringBuffer("\n<!---------------- PROPERTIES ---------------->");
    sb.append("\n<!-- ").append(ApplicationParameters.twoString()).append(" -->");
    return sb.toString();
  }

  /**
   * Prints all attrbutes in the following scopes:
   * - PAGE
   * - REQUEST
   * - SESSION
   * - APPLICATION
   * @param pageContext The PageContext
   * @return Debug infos as String format
   */
  public static String showPageContext(PageContext pageContext)
  {
    int[] scopes = {PageContext.PAGE_SCOPE, PageContext.REQUEST_SCOPE, PageContext.SESSION_SCOPE, PageContext.APPLICATION_SCOPE};
    String[] scopeNames = {"PAGE", "REQUEST", "SESSION", "APPLICATION"};
    StringBuffer sb = new StringBuffer("");

    for (int i=0; i<scopes.length; i++)
    {
      sb.append("\n\n<!---------------- PAGECONTEXT: "+scopeNames[i]+" ---------------->");
      Enumeration enume = pageContext.getAttributeNamesInScope(scopes[i]);
      while (enume.hasMoreElements())
      {
        String name = (String)enume.nextElement();
        Object value = pageContext.getAttribute(name, scopes[i]);
        sb.append(showNameValueType(name, value));
      }
    }

    return sb.toString();
  }

  /**
   * Prints the request parameters
   * @param request The HttpServletRequest
   * @return Debug infos as String format
   */
  public static String showRequestParameters(HttpServletRequest request)
  {
    StringBuffer sb = new StringBuffer("\n<!---------------- REQUEST PARAMETERS ---------------->");
    Enumeration enume   = request.getParameterNames();
    while (enume.hasMoreElements())
    {
      String name = (String)enume.nextElement();
      String[] values = request.getParameterValues(name);
      for(int i=0; i<values.length; i++) 
      {
        Object value = values[i];
        sb.append(showNameValueType(name, value));
      }
    }
    return sb.toString();
  }
/*
  public static String showRequestAttributes(HttpServletRequest request)
  {
    StringBuffer sb = new StringBuffer("\n<!---------------- REQUEST ATTRIBUTES ---------------->");
    Enumeration enume   = request.getAttributeNames();
    while (enume.hasMoreElements())
    {
      String name = (String)enume.nextElement();
      sb.append("\n<!-- name="+name+" \tvalue="+request.getAttribute(name)+" -->");
    }
    return sb.toString();
  }

  public static String showSessionAttributes(HttpSession session)
  {
    StringBuffer sb = new StringBuffer("\n<!---------------- SESSION ATTRIBUTES ---------------->");
    Enumeration enume   = session.getAttributeNames();
    while (enume.hasMoreElements())
    {
      String name = (String)enume.nextElement();
      sb.append("\n<!-- name="+name+" \tvalue="+session.getAttribute(name)+" -->");
    }
    return sb.toString();
  }
*/

  /**
   * Prints the tiles attributes
   * @param pageContext The PageContext
   * @return Debug infos as String format
   */
  public static String showTilesScope(PageContext pageContext) 
  {
    ComponentContext compContext = (ComponentContext)pageContext.getAttribute(ComponentConstants.COMPONENT_CONTEXT, PageContext.REQUEST_SCOPE);
    StringBuffer sb = new StringBuffer("\n\n<!---------------- TILE ATTRIBUTES ---------------->");

    if (compContext!=null)
    { 
      Iterator iter = compContext.getAttributeNames();
      while (iter.hasNext())
      {
        String name = (String)iter.next();
        Object value = compContext.getAttribute(name);
        sb.append(showNameValueType(name, value));
      }
    } 

    return sb.toString();
  }

  /**
   * Prints the attribute type
   * @param name Name of the attribute
   * @param value The attribute itself
   * @return Debug infos as String format
   */
  private static String showNameValueType(String name, Object value) 
  {
    StringBuffer sb = new StringBuffer();
    if (value!=null)
    {
      sb.append("\n<!-- ").append(name).append("=").append(value).append(" (").append(value.getClass().getName()).append(") -->");
    }
    else
    {
      sb.append("\n<!-- ").append(name).append("=").append(value).append(" -->");
    }
    return sb.toString();
 }

}