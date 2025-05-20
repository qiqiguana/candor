/**
 * 
 */
package net.sourceforge.ext4j.log.log4j;


import net.sourceforge.ext4j.log.Server;

import org.apache.log4j.helpers.PatternParser;

/**
 * @author Luc Pezet <lpezet@gmail.com>
 *
 */
public class ExtrasPatternParser extends PatternParser {

	public ExtrasPatternParser(String pPattern) {
	    super(pPattern);
	}
	
	protected void finalizeConverter(char c) {
		if (c == 's') {
			addConverter(new SimpleStringConverter(formattingInfo, Server.getInstance().getCurrentHost()));
		} else if (c == 'i') {
			addConverter(new SimpleStringConverter(formattingInfo, Server.getInstance().getPublicIP()));
		} else if (c == 'u') {
			addConverter(new RequestURIConverter(formattingInfo));
		} else if (c == 'R') {
			addConverter(new RemoteAddrConverter(formattingInfo));
		} else if (c == 'q') {
			addConverter(new QueryStringConverter(formattingInfo));
		} else {
			super.finalizeConverter(c);
		}
	}

}
