/**
 * 
 */
package net.sourceforge.ext4j.log.logback;

import net.sourceforge.ext4j.log.logback.converter.CookieConverter;
import net.sourceforge.ext4j.log.logback.converter.ProtocolConverter;
import net.sourceforge.ext4j.log.logback.converter.RemoteAddrConverter;
import net.sourceforge.ext4j.log.logback.converter.RequestMethodConverter;
import net.sourceforge.ext4j.log.logback.converter.RequestURLConverter;
import ch.qos.logback.classic.PatternLayout;

/**
 * @author luc
 *
 */
public class ExtPatternLayout extends PatternLayout {
	
	public ExtPatternLayout() {
		super();
		initConverters();
	}

	private void initConverters() {
		getDefaultConverterMap().put("a", RemoteAddrConverter.class.getName());
		getDefaultConverterMap().put("remoteIP", RemoteAddrConverter.class.getName());
		
		getDefaultConverterMap().put("H", ProtocolConverter.class.getName());
		getDefaultConverterMap().put("protocol", ProtocolConverter.class.getName());
		
		getDefaultConverterMap().put("m", RequestMethodConverter.class.getName());
		getDefaultConverterMap().put("requestMethod", RequestMethodConverter.class.getName());
		
		getDefaultConverterMap().put("r", RequestURLConverter.class.getName());
		getDefaultConverterMap().put("requestURL", RequestURLConverter.class.getName());
		
		getDefaultConverterMap().put("u", RequestURLConverter.class.getName());
		getDefaultConverterMap().put("requestURI", RequestURLConverter.class.getName());
		
		getDefaultConverterMap().put("reqCookie", CookieConverter.class.getName());
		getDefaultConverterMap().put("cookie", CookieConverter.class.getName());
	}
}
