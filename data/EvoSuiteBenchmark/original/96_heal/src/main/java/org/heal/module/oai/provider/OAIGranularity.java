/*
 * Created on Mar 19, 2005
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.heal.module.oai.provider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * @author swright
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OAIGranularity {
	private static final HashMap granularities = new HashMap();
	public static final OAIGranularity yearMonthDay = new OAIGranularity("YYYY-MM-DD","yyyy-MM-dd");
	public static final OAIGranularity yearMonthDayHourMinuteSecond = new OAIGranularity("YYYY-MM-DDThh:mm:ssZ","yyyy-MM-dd'T'HH:mm:ss'Z'");
	private final String display;
	private final SimpleDateFormat dateFormatter;

	protected OAIGranularity(final String display,final String javaFormat) {
		this.display = display;
		dateFormatter = new SimpleDateFormat(javaFormat);
		dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		granularities.put(display,this);
	}
	
	public static OAIGranularity getGranularity(String display) {
		return (OAIGranularity)granularities.get(display);
	}

	public String getDisplay() {
		return display;
	}
	
	public Date parse(String str) throws ParseException {
		return dateFormatter.parse(str);
	}
	public String format(Date aDate) {
		return dateFormatter.format(aDate);
	}
}
