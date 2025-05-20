/**
 *
 */
package net.sourceforge.ext4j.log.log4j;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.PatternParser;

/**
 * @author Luc Pezet <lpezet@gmail.com>
 *
 */
public class ExtrasPatternLayout extends PatternLayout {

	public ExtrasPatternLayout() {
	}

	/**
	 * @param pPattern
	 */
	public ExtrasPatternLayout(String pPattern) {
		super(pPattern);
	}

	protected PatternParser createPatternParser(String pPattern) {
		return new ExtrasPatternParser(pPattern);
	}

}
