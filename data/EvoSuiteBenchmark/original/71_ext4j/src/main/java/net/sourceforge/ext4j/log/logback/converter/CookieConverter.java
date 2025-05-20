/**
 * 
 */
package net.sourceforge.ext4j.log.logback.converter;

import net.sourceforge.ext4j.log.logback.RequestInfoExtractorFactory;
import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * @author luc
 *
 */
public class CookieConverter extends ClassicConverter {

	private static final String	NA	= "NA";

	/* (non-Javadoc)
	 * @see ch.qos.logback.core.pattern.Converter#convert(java.lang.Object)
	 */
	@Override
	public String convert(ILoggingEvent pEvent) {
		if (pEvent.getArgumentArray() != null && pEvent.getArgumentArray().length > 0) {
			String oCookieName = (String) pEvent.getArgumentArray()[0];
			return RequestInfoExtractorFactory.getExtractorInstance().getCookie(oCookieName);
		}
		return NA;
	}
}
