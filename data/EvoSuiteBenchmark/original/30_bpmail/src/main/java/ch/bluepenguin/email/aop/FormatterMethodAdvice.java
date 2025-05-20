/*
 * Created on 19.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ch.bluepenguin.email.aop;

import java.util.Vector;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Christian
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FormatterMethodAdvice implements MethodInterceptor {

	/* (non-Javadoc)
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("Before: invocation=[" + invocation + "]");
		Object rval = invocation.proceed();
	
		//this will fail, since returntype cannot be changed!
		if(rval instanceof Vector) {
			Vector vals = (Vector)rval;
		   StringBuffer manipulated = new StringBuffer();
		   for(int i=0;i<vals.size();i++){
		   	 manipulated.append(vals.get(i)).append("\n");
		   }
			System.out.println("Invocation will return manipulated value!");
			return manipulated.toString();
		   
		}
		System.out.println("Invocation returned without doing any harm :)");
		return rval;
	}

}
