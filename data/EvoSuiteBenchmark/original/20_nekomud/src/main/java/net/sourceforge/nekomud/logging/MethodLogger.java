/*
 * MethodLogger.java
 * Copyright 2008-2009 Patrick Meade
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sourceforge.nekomud.logging;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MethodLogger is a logging aspect that logs entry and exit of method calls,
 * the arguments passed to the method (if any), and the result of the method
 * call (if any). This logging aspect is generally used for debugging purposes
 * only, as it tends to be very noisy even with narrow pointcuts.
 * @author patrick
 */
public class MethodLogger
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Log the entry to a method call.
	 * @param joinPoint JoinPoint object representing the method that is about
	 *                  to be called.
	 */
	public void logMethodEntry(JoinPoint joinPoint)
	{
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		StringBuffer logBuffer = new StringBuffer("Entering [");
		logBuffer.append(className);
		logBuffer.append(".");
		logBuffer.append(methodName);
		logBuffer.append("]: args = {");
		Object[] args = joinPoint.getArgs();
		if(args != null) {
			if(args.length > 0) {
				logBuffer.append(args[0]);
			}
			for(int i=1; i<args.length; i++) {
				logBuffer.append(", ");
				logBuffer.append(args[i]);
			}
		}
		logBuffer.append("}");
		logger.trace(logBuffer.toString());
	}

	/**
	 * Log the exit from a method call.
	 * @param joinPoint JoinPoint object representing the method that has
	 *                  finished executing. 
	 * @param result the result returning from the method call
	 */
	public void logMethodExit(JoinPoint joinPoint, Object result)
    {
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		StringBuffer logBuffer = new StringBuffer("Exiting [");
		logBuffer.append(className);
		logBuffer.append(".");
		logBuffer.append(methodName);
		logBuffer.append("]: result = ");
		logBuffer.append(result);
		logger.trace(logBuffer.toString());
    }
}
