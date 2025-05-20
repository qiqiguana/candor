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
public class QueryStringConverter extends PatternConverter {

	public QueryStringConverter(FormattingInfo pFi) {
		super(pFi);
	}

	protected String convert(LoggingEvent pEvent) {
		return ExecutionContext.getCurrentRequest().getQueryString();
	}

}
