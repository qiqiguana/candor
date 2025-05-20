/**
 * 
 */
package net.sourceforge.ext4j.log.log4j;

import static org.junit.Assert.assertEquals;

import net.sourceforge.ext4j.log.log4j.SimpleStringConverter;

import org.apache.log4j.helpers.FormattingInfo;
import org.junit.Test;

/**
 * @author Luc Pezet <lpezet@gmail.com>
 *
 */
public class SimpleStringConverterTest {

	@Test
	public void convert() throws Exception {
		String oExpected = "HelloWorld!";
		SimpleStringConverter oConverter = new SimpleStringConverter(new FormattingInfo(), oExpected);
		assertEquals(oExpected, oConverter.convert(null));
	}
}
