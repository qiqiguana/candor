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
public class ProtocolConverter extends ClassicConverter {

	/* (non-Javadoc)
	 * @see ch.qos.logback.core.pattern.Converter#convert(java.lang.Object)
	 */
	@Override
	public String convert(ILoggingEvent pArg0) {
		return RequestInfoExtractorFactory.getExtractorInstance().getProtocol();
	}

}
