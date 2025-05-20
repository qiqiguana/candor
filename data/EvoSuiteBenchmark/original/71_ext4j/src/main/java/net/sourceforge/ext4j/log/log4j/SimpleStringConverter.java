/**
 * 
 */
package net.sourceforge.ext4j.log.log4j;

import org.apache.log4j.helpers.FormattingInfo;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author Luc Pezet <lpezet@gmail.com>
 *
 */
public class SimpleStringConverter extends PatternConverter {
	
	private String mValue;

	public SimpleStringConverter(FormattingInfo pFormattingInfo, String pValue) {
		super(pFormattingInfo);
		mValue = pValue;
	}

	/* (non-Javadoc)
	 * @see org.apache.log4j.helpers.PatternConverter#convert(org.apache.log4j.spi.LoggingEvent)
	 */
	protected String convert(LoggingEvent pEvent) {
		return mValue;
	}

}
