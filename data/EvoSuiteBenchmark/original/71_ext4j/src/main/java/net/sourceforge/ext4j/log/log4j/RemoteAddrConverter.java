/**
 *
 */
package net.sourceforge.ext4j.log.log4j;


import net.sourceforge.ext4j.log.ExecutionContext;

import org.apache.log4j.helpers.FormattingInfo;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author Luc Pezet <lpezet@gmail.com>
 *
 */
public class RemoteAddrConverter extends PatternConverter {

	public RemoteAddrConverter(FormattingInfo pFi) {
		super(pFi);
	}

	protected String convert(LoggingEvent pEvent) {
		return ExecutionContext.getCurrentRequest().getRemoteAddr();
	}

}
