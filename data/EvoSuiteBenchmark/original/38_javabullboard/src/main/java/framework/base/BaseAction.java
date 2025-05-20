package framework.base;

import java.util.Collection;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.MessageResources;
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

import framework.ApplicationParameters;
import framework.util.ObjectUtils;
import framework.util.StringUtils;

/**
 * Every action should extends BaseAction (or a subclass) as it defines:
 * - Common form mode constants
 * - Shortcut methods to common forwards
 * - A standard forwarding behaviour
 * 
 * How to use?
 * 1. Create your action extending BaseAction
 * 2. Create your methods returning void:
 *    - Call business methods 
 *    - Call addError(messageKey) to add an error in request
 *    - Do not catch any technical exception
 *    - A business exception should be catched and followed by addError(exception)
 * 
 * How does it work?
 * 1. Method preProcess is called
 * 2. The method defined by the ActionMapping.getParameter() is invoked
 * 3. If an exception is thrown, processException is called
 * 4. Method postProcess is called
 * 5. If an exception is queued, forwardException is called
 * 6. Else if an error is queued, forwardError is called
 * 7. Else forwardSuccess is called
 * 
 * How can I customize this behaviour?
 * 1. Override preProcess (called before your method is invoked) 
 * 2. Override postProcess (called after your method is invoked) 
 * 3. Override processException (called after your method thows an exception) 
 * 4. Have your method return an ActionForward instead of void
 * 5. Override the execute method 
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.8 $ $Date: 2004/06/17 23:28:50 $
 */
public abstract class BaseAction extends DispatchAction
{

  // Logger
  protected static Log log = LogFactory.getLog(BaseAction.class);

  public final static String FORWARD_SUCCESS = "success";
  public final static String FORWARD_ERROR = "error";
  public final static String FORWARD_EXCEPTION = "exception";
  public final static String FORWARD_UNKNOWN = "unknown";

  public static final String MODE_CREATE = "CREATE";
  public static final String MODE_EDIT = "EDIT";
  public static final String MODE_UPDATE = "UPDATE";
  public static final String MODE_DELETE = "DELETE";

////////////////////////////////////////////////////////////////////////////////
  protected ActionForward getForward(ActionMapping mapping, String forwardName)
  {
    return (mapping.findForward(forwardName));
  }

  protected ActionForward getForwardSuccess(ActionMapping mapping)
  {
    return getForward(mapping, FORWARD_SUCCESS);
  }

  protected ActionForward getForwardError(ActionMapping mapping)
  {
    return getForward(mapping, FORWARD_ERROR);
  }

  protected ActionForward getForwardException(ActionMapping mapping)
  {
    return getForward(mapping, FORWARD_EXCEPTION);
  }

  protected ActionForward getForwardUnknown(ActionMapping mapping)
  {
    return getForward(mapping, FORWARD_UNKNOWN);
  }


  /**
   * Return the framework locale object for the user session
   * for given request.
   * If no session is set, or if the session has no locale
   * set, the default locale is returned.
   * @param request The HTTP request we are processing
   */
  protected Locale getLocale(HttpServletRequest request) 
  {
    Locale result = null;
    HttpSession session = request.getSession();
    if (session!=null) result = (Locale)session.getAttribute(Globals.LOCALE_KEY);
    if (result==null) result = ApplicationParameters.getDefaultLocale();
    return result;
  }

  /**
   * Set the framework locale object in the session for this request.
   * If a session context does not exist, one is created.
   * @param request The HTTP request we are processing
   * @param locale The locale to use for this session
   */
  protected void setLocale(HttpServletRequest request, Locale locale) 
  {
    HttpSession session = request.getSession(true);
    session.setAttribute(Globals.LOCALE_KEY, locale);
  }

  /**
   * Get the dispatch parameter from the Action Mapping
   * @param mapping The ActionMapping used to select this instance
   * @param form The optional ActionForm bean for this request (if any)
   * @param request The HTTP request we are processing
   * @param response The resonse we are creating
   * @return The mapping parameter as String 
   */
  protected String getDispatch(ActionMapping mapping,
                               ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response)
  {
    return mapping.getParameter();
  }
  
  /**
   * Get the existing error stack or eventually create one and set it in request
   * An error satck will be created if none exist
   * @param request The HTTP request we are processing
   * @return The error stack ActionErrors
   */
  protected ActionErrors getActionErrors(HttpServletRequest request)
  {
    return getActionErrors(request, true);
  }

  /**
   * Get the existing error stack or eventually create one and set it in request
   * @param request The HTTP request we are processing
   * @param create true if you want to create an error satck if none exist, else false
   * @return The error stack ActionErrors
   */
  protected ActionErrors getActionErrors(HttpServletRequest request, boolean create)
  {
    ActionErrors errors = (ActionErrors)request.getAttribute(Globals.ERROR_KEY); 
    if (errors==null && create) 
    {
      errors = new ActionErrors();
      request.setAttribute(Globals.ERROR_KEY, errors);
    }
    return errors;
  }

  /**
   * Add an error in the error stack and save it in request.
   * Key is ActionErrors.GLOBAL_ERROR
   * No message parameters are provided
   * @param request The HTTP request we are processing
   * @param messageKey The message key
   * @return The error stack ActionErrors 
   */
  protected ActionErrors addError(HttpServletRequest request, String messageKey)
  {
    return addError(request, null, messageKey, null);
  }

  /**
   * Add an error in the error stack and save it in request.
   * Key is ActionErrors.GLOBAL_ERROR
   * @param request The HTTP request we are processing
   * @param messageKey The message key
   * @param params Parameters for the message
   * @return The error stack ActionErrors 
   */
  protected ActionErrors addError(HttpServletRequest request, String messageKey, Object params)
  {
    return addError(request, null, messageKey, params);
  }

  /**
   * Add an error in the error stack and save it in request.
   * Key is ActionErrors.GLOBAL_ERROR
   * No message parameters are provided
   * @param request The HTTP request we are processing
   * @param e The exception: message is used as messageKey
   * @return The error stack ActionErrors 
   */
  protected ActionErrors addError(HttpServletRequest request, Exception e)
  {
    String messageKey = e.getMessage();
    return addError(request, null, messageKey, null);
  }

  /**
   * Add an error in the error stack and save it in request.
   * Key is ActionErrors.GLOBAL_ERROR
   * @param request The HTTP request we are processing
   * @param e The exception: message is used as messageKey
   * @param params Parameters for the message
   * @return The error stack ActionErrors 
   */
  protected ActionErrors addError(HttpServletRequest request, Exception e, Object params)
  {
    String messageKey = e.getMessage();
    return addError(request, null, messageKey, params);
  }

  /**
   * Add an error in the error stack and save it in request.
   * @param request The HTTP request we are processing
   * @param key The key store the message
   * @param e The exception: message is used as messageKey
   * @param params Parameters for the message
   * @return The error stack ActionErrors 
   */
  protected ActionErrors addError(HttpServletRequest request, String key, Exception e, Object params)
  {
    String messageKey = e.getMessage();
    return addError(request, key, messageKey, params);
  }

  /**
   * Add an error in the error stack and save it in request.
   * This is the root addError method
   * @param request The HTTP request we are processing
   * @param key The key store the message
   * @param messageKey The message key
   * @param params Parameters for the message
   * @return The error stack ActionErrors 
   */
  protected ActionErrors addError(HttpServletRequest request, String key, String messageKey, Object params)
  {
    if (!StringUtils.exists(key)) key = ActionErrors.GLOBAL_ERROR;
    Collection parameters = ObjectUtils.toCollection(params);
    ActionError error = new ActionError(messageKey, parameters!=null?parameters.toArray():null);
    ActionErrors errors = getActionErrors(request);
    errors.add(key, error);
    saveErrors(request, errors);

    MessageResources resources = getResources(request);
    String message = resources.getMessage(messageKey, parameters!=null?parameters.toArray():null);
    log.warn(message);
    
    return errors;
  }

  /**
   * Is an non-empty error stack present in request ?
   * @param request The HTTP request we are processing
   * @return true if a non-empty error stack exist in request, else salse
   */
  protected boolean hasErrors(HttpServletRequest request)
  {
    ActionErrors errors = getActionErrors(request, false);
    return (errors!=null && !errors.isEmpty());
  }

  /**
   * Is an exception present in request ?
   * @param request The HTTP request we are processing
   * @return true if an exception exist in request, else false
   */
  protected boolean hasException(HttpServletRequest request)
  {
    return request.getAttribute(Globals.EXCEPTION_KEY)!=null;
  }

  /**
   * Optional extension point for pre-processing.
   * Default method does nothing.
   * To branch to another URI, return an non-null ActionForward.
   * If errors are logged (getErrors() et al),
   * default behaviour will branch to findFailure().
   *
   * @param mapping The ActionMapping used to select this instance
   * @param form The optional ActionForm bean for this request
   * @param request The HTTP request we are processing
   * @param response The resonse we are creating
   */
  protected void preProcess(ActionMapping mapping,
                            ActionForm form,
                            HttpServletRequest request,
                            HttpServletResponse response) 
  throws Exception 
  {
    // override to provide functionality
  }

  /**
   * Optional extension point for post-processing.
   * Default method does nothing.
   * This is called from a finally{} clause,
   * and so is guaranteed to be called after the dispatched mehod.
   *
   * @param mapping The ActionMapping used to select this instance
   * @param form The optional ActionForm bean for this request
   * @param request The HTTP request we are processing
   * @param response The resonse we are creating
   */
  protected void postProcess( ActionMapping mapping,
                              ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response) 
  throws Exception 
  {
    // override to provide functionality
  }

  /**
   * Optional extension point for exception-processing.
   *
   * @param mapping The ActionMapping used to select this instance
   * @param form The optional ActionForm bean for this request
   * @param request The HTTP request we are processing
   * @param response The resonse we are creating
   */
  protected void processException(ActionMapping mapping,
                                  ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response,
                                  Exception e) 
  throws Exception 
  {
    log.fatal("execute: ", e);
    request.setAttribute(Globals.EXCEPTION_KEY, e);
  }

  /**
  * Process the specified HTTP request, and create the corresponding HTTP
  * response (or forward to another web component that will create it).
  * Return an <code>ActionForward</code> instance describing where and how
  * control should be forwarded, or <code>null</code> if the response has
  * already been completed.
  *
  * @param mapping The ActionMapping used to select this instance
  * @param form The optional ActionForm bean for this request (if any)
  * @param request The HTTP request we are processing
  * @param response The HTTP response we are creating
  *
  * @exception Exception if an exception occurs
  */
  public ActionForward execute( ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
  throws Exception 
  {  
    ActionForward result = null;

    // Identify the method name to be dispatched to.
    // dispatchMethod() will call unspecified() if name is null
    String dispatch = getDispatch(mapping, form, request, response);
    if (log.isDebugEnabled()) log.debug("execute: Method to dispatch to '"+dispatch+"'");

    // There are already errors to be displayed: Do not forward to error
    // This is used to avoid undefinitively get error forward
    boolean errorsAlreadyPresent = hasErrors(request);
    if (errorsAlreadyPresent && log.isDebugEnabled()) log.debug("execute: An error stack is already present in request");

    // There is already an exception to be displayed: Do not forward to exception
    // This is used to avoid undefinitively get exception forward
    boolean exceptionAlreadyPresent = hasException(request);
    if (exceptionAlreadyPresent && log.isDebugEnabled()) log.debug("execute: An exception is already present in request");

    // Invoke the named requested method
    try 
    {
      // Check for precondition errors; fail if found
      preProcess(mapping, form, request, response);
      if (log.isDebugEnabled()) log.debug("execute: preProcess done");
      if (!hasErrors(request))
      {
        result = dispatchMethod(mapping, form, request, response, dispatch);
        if (log.isDebugEnabled()) log.debug("execute: dispatchMethod returned "+(result!=null?result.getName():"no result"));
      } 
    }
    catch (Exception e) 
    {
      processException(mapping, form, request, response, e);
      if (log.isDebugEnabled()) log.debug("execute: processException done!");
    }
    finally
    {
      postProcess(mapping, form, request, response);
      if (log.isDebugEnabled()) log.debug("execute: postProcess done");
    }    

    // If errors/exception queued, fail
    if (!exceptionAlreadyPresent && hasException(request)) result = getForwardException(mapping);
    else if (!errorsAlreadyPresent && hasErrors(request)) result = getForwardError(mapping);    

    // If the dispatched method returned a ActionForward, use it.
    // Else use ForwardSuccess
    result = result!=null?result:getForwardSuccess(mapping);
    if (log.isDebugEnabled()) log.debug("execute: Returning "+result);

    return result;
  }

}