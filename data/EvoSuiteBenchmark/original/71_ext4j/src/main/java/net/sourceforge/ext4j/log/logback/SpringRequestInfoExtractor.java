/**
 * 
 */
package net.sourceforge.ext4j.log.logback;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author luc
 *
 */
public class SpringRequestInfoExtractor implements IRequestInfoExtractor {
	
	public SpringRequestInfoExtractor() {
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.ext4j.log.logback.IRequestInfoExtrator#getMethod()
	 */
	public String getMethod() {
		HttpServletRequest oRequest = getCurrentRequest();
		return (oRequest != null) ? oRequest.getMethod() : "NA";
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.ext4j.log.logback.IRequestInfoExtrator#getRemoteAddr()
	 */
	public String getRemoteAddr() {
		HttpServletRequest oRequest = getCurrentRequest();
		return (oRequest != null) ? oRequest.getRemoteAddr() : "NA";
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.ext4j.log.logback.IRequestInfoExtrator#getProtocol()
	 */
	public String getProtocol() {
		HttpServletRequest oRequest = getCurrentRequest();
		return (oRequest != null) ? oRequest.getProtocol() : "NA";
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.ext4j.log.logback.IRequestInfoExtrator#getRequestURL()
	 */
	public String getRequestURL() {
		HttpServletRequest oRequest = getCurrentRequest();
		return (oRequest != null) ? oRequest.getRequestURL().toString() : "NA";
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.ext4j.log.logback.IRequestInfoExtrator#getCookie(java.lang.String)
	 */
	public String getCookie(String pName) {
		HttpServletRequest oRequest = getCurrentRequest();
		if (oRequest == null || oRequest.getCookies() == null) return "NA";
		for (Cookie c : oRequest.getCookies()) {
			if (c.getName().equalsIgnoreCase(pName)) return c.getValue();
		}
		return "-";
	}

	public String getRequestURI() {
		HttpServletRequest oRequest = getCurrentRequest();
		return (oRequest != null) ? oRequest.getRequestURI() : "NA";
	}
	
	private HttpServletRequest getCurrentRequest() {
		ServletRequestAttributes oAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return oAttributes.getRequest();
	}

}
