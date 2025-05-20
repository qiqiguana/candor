/**
 * 
 */
package net.sourceforge.ext4j.log.logback;

/**
 * @author luc
 *
 */
public interface IRequestInfoExtractor {
	
	public String getMethod();
	
	public String getRemoteAddr();
	
	public String getProtocol();
	
	public String getRequestURL();
	
	public String getRequestURI();
	
	public String getCookie(String pName);

}
