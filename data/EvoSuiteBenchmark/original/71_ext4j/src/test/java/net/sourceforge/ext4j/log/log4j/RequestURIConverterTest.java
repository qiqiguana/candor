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
import net.sourceforge.ext4j.log.log4j.RequestURIConverter;

import org.apache.log4j.helpers.FormattingInfo;
import org.junit.Test;

/**
 * @author Luc Pezet <lpezet@gmail.com>
 *
 */
public class RequestURIConverterTest {

	
	@Test
	public void convert() throws Exception {
		RequestURIConverter oConverter = new RequestURIConverter(new FormattingInfo());
		HttpServletRequest oRequest = createMock(HttpServletRequest.class);
		ExecutionContext.setCurrentRequest(oRequest);
		String oExpected = "http://www.test.com";
		expect(oRequest.getRequestURI()).andReturn(oExpected);
		replay(oRequest);
		String oActual = oConverter.convert(null);
		verify(oRequest);
		assertEquals(oExpected, oActual);
	}
}
