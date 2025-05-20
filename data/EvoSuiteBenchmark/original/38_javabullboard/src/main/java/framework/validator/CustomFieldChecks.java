package framework.validator;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.ValidatorUtil;

import org.apache.struts.validator.FieldChecks;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import framework.util.ObjectUtils;
import framework.util.StringUtils;

/**
 * Custom validation rules
 * 
 * @author Jean KON-SUN-TACK
 * @version $Revision: 1.2 $ $Date: 2004/05/09 21:24:18 $
 */
public class CustomFieldChecks extends FieldChecks implements Serializable
{

  /**
   * Check if the name is an existing class name
   * @param bean The bean validation is being performed on.
   * @param va The <code>ValidatorAction</code> that is currently being performed.
   * @param field The <code>Field</code> object associated with the current 
   * field being validated.
   * @param errors The <code>ActionErrors</code> object to add errors to if 
   * any validation errors occur.
   * @param request Current request object.
   * @return true if meets stated requirements, false otherwise.
   */
  public static boolean validateClassName(Object bean,
                                          ValidatorAction va, 
                                          Field field,
                                          ActionErrors errors,
                                          HttpServletRequest request) 
  {  
    String value = null;
    if (isString(bean)) value = (String)bean;
    else value = ValidatorUtil.getValueAsString(bean, field.getProperty());

    if (!ObjectUtils.isClassName(value)) 
    {
      // Default method
      //ActionError error = Resources.getActionError(request, va, field);

      // If the messageKey is defined at form validation definition
      /*Arg arg0 = field.getArg0();
      String messageKey = arg0.getKey();
      ActionError error = new ActionError(messageKey, value);*/

      // The messageKey is defined at rule definition
      String messageKey = va.getMsg();
      ActionError error = new ActionError(messageKey, value);

      errors.add(field.getKey(), error);
      return false;
    } 
    else return true;
  }

  /**
   * Check if the name is an valid Java name
   * @param bean The bean validation is being performed on.
   * @param va The <code>ValidatorAction</code> that is currently being performed.
   * @param field The <code>Field</code> object associated with the current 
   * field being validated.
   * @param errors The <code>ActionErrors</code> object to add errors to if 
   * any validation errors occur.
   * @param request Current request object.
   * @return true if meets stated requirements, false otherwise.
   */
  public static boolean validateJavaName(Object bean,
                                          ValidatorAction va, 
                                          Field field,
                                          ActionErrors errors,
                                          HttpServletRequest request) 
  {  
    String value = null;
    if (isString(bean)) value = (String)bean;
    else value = ValidatorUtil.getValueAsString(bean, field.getProperty());

    if (!StringUtils.isJavaName(value)) 
    {
      String messageKey = va.getMsg();
      ActionError error = new ActionError(messageKey, value);
      errors.add(field.getKey(), error);
      return false;
    } 
    else return true;
  }

  /**
   * Check if the name is an valid Java package name
   * @param bean The bean validation is being performed on.
   * @param va The <code>ValidatorAction</code> that is currently being performed.
   * @param field The <code>Field</code> object associated with the current 
   * field being validated.
   * @param errors The <code>ActionErrors</code> object to add errors to if 
   * any validation errors occur.
   * @param request Current request object.
   * @return true if meets stated requirements, false otherwise.
   */
  public static boolean validateJavaPackageName(Object bean,
                                          ValidatorAction va, 
                                          Field field,
                                          ActionErrors errors,
                                          HttpServletRequest request) 
  {  
    String value = null;
    if (isString(bean)) value = (String)bean;
    else value = ValidatorUtil.getValueAsString(bean, field.getProperty());

    if (!StringUtils.isJavaPackageName(value)) 
    {
      String messageKey = va.getMsg();
      ActionError error = new ActionError(messageKey, value);
      errors.add(field.getKey(), error);
      return false;
    } 
    else return true;
  }

}