/**
 * 
 */
package net.sourceforge.ext4j.log.logback;

/**
 * @author luc
 *
 */
public class StubRequestInfoExtractor implements IRequestInfoExtractor {

	private static final String	STUB_IMPLEMENTATION	= "_SI_";

	/* (non-Javadoc)
	 * @see net.sourceforge.ext4j.log.logback.IRequestInfoExtractor#getMethod()
	 */
	public String getMethod() {
		return STUB_IMPLEMENTATION;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.ext4j.log.logback.IRequestInfoExtractor#getRemoteAddr()
	 */
	public String getRemoteAddr() {
		return STUB_IMPLEMENTATION;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.ext4j.log.logback.IRequestInfoExtractor#getProtocol()
	 */
	public String getProtocol() {
		return STUB_IMPLEMENTATION;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.ext4j.log.logback.IRequestInfoExtractor#getRequestURL()
	 */
	public String getRequestURL() {
		return STUB_IMPLEMENTATION;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.ext4j.log.logback.IRequestInfoExtractor#getCookie(java.lang.String)
	 */
	public String getCookie(String pName) {
		return STUB_IMPLEMENTATION;
	}
	
	public String getRequestURI() {
		return STUB_IMPLEMENTATION;
	}

}
