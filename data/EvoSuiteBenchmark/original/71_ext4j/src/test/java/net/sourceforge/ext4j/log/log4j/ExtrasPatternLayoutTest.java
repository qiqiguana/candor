/**
 *
 */
package net.sourceforge.ext4j.log.log4j;

import static org.junit.Assert.assertEquals;
import net.sourceforge.ext4j.log.log4j.ExtrasPatternLayout;
import net.sourceforge.ext4j.log.log4j.ExtrasPatternParser;

import org.apache.log4j.helpers.PatternParser;
import org.junit.Test;

/**
 * @author Luc Pezet <lpezet@gmail.com>
 *
 */
public class ExtrasPatternLayoutTest {

	@Test
	public void createPatternParser() throws Exception {
		ExtrasPatternLayout oLayout = new ExtrasPatternLayout();
		PatternParser oActual = oLayout.createPatternParser("%s");
		assertEquals(ExtrasPatternParser.class.getName(), oActual.getClass().getName());

		oLayout = new ExtrasPatternLayout("%s");
		oActual = oLayout.createPatternParser("%s");
		assertEquals(ExtrasPatternParser.class.getName(), oActual.getClass().getName());
	}
}
