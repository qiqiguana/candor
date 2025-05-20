/**
 *
 */
package net.sourceforge.ext4j.log;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Luc Pezet <lpezet@gmail.com>
 *
 */
public class ExecutionContext {

	private static ThreadLocal<HttpServletRequest> mCurrentRequest = new ThreadLocal<HttpServletRequest>();

	public static HttpServletRequest getCurrentRequest() {
		return mCurrentRequest.get();
	}

	public static void setCurrentRequest(HttpServletRequest pRequest) {
		mCurrentRequest.set(pRequest);
	}



}
