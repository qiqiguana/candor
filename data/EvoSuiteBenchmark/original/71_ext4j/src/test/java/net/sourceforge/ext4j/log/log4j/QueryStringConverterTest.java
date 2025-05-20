/**
 * 
 */
package net.sourceforge.ext4j.log.log4j;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.ext4j.log.ExecutionContext;
import net.sourceforge.ext4j.log.log4j.QueryStringConverter;

import org.apache.log4j.helpers.FormattingInfo;
import org.junit.Test;

/**
 * @author Luc Pezet <lpezet@gmail.com>
 *
 */
public class QueryStringConverterTest {
	
	@Test	
	public void convert() throws Exception {
		FormattingInfo oFI = new FormattingInfo();
		QueryStringConverter oConverter = new QueryStringConverter(oFI);
		HttpServletRequest oRequest = createMock(HttpServletRequest.class);
		ExecutionContext.setCurrentRequest(oRequest);
		String oExpected = "hello=world&time=2PM";
		expect(oRequest.getQueryString()).andReturn(oExpected);
		replay(oRequest);
		String oActual = oConverter.convert(null);
		verify(oRequest);
		assertEquals(oExpected, oActual);
	}
}
