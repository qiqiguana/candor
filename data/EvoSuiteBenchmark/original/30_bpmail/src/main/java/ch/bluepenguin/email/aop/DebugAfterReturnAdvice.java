/*
 * Created on 18.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ch.bluepenguin.email.aop;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;


/**
 * @author Christian
 * 
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DebugAfterReturnAdvice implements AfterReturningAdvice {

	private String locale;
	/* (non-Javadoc)
	 * @see org.springframework.aop.AfterReturningAdvice#afterReturning(java.lang.Object, java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 */
	public void afterReturning(Object returnValue, Method m, Object[] args,
			Object target) throws Throwable {
		System.out.println(">>> method " + m.getName() + " of class " + m.getDeclaringClass().getName() +" called ");
		if(args != null) {
			for(int i =0; i<args.length; i++) {
				System.out.println("     arg [" + i+ "] type: " +args[i].getClass().getName());
				System.out.println("     arg [" + i+ "] val : " +args[i]);
			}
		}
		System.out.println("<<< method " + m.getName() + " returnvalue: " + returnValue);
		
	}

	/**
	 * @return Returns the locale.
	 */
	public String getLocale() {
		return locale;
	}
	/**
	 * @param locale The locale to set.
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}
}
