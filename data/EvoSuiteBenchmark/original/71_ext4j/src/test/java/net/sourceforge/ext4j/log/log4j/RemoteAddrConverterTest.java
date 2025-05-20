/**
 * 
 */
package net.sourceforge.ext4j.log.log4j;

import javax.servlet.http.HttpServletRequest;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

import net.sourceforge.ext4j.log.ExecutionContext;
import net.sourceforge.ext4j.log.log4j.RemoteAddrConverter;

import org.apache.log4j.helpers.FormattingInfo;
import org.junit.Test;

/**
 * @author Luc Pezet <lpezet@gmail.com>
 *
 */
public class RemoteAddrConverterTest {

	@Test	
	public void convert() throws Exception {
		FormattingInfo oFI = new FormattingInfo();
		RemoteAddrConverter oConverter = new RemoteAddrConverter(oFI);
		HttpServletRequest oRequest = createMock(HttpServletRequest.class);
		ExecutionContext.setCurrentRequest(oRequest);
		String oExpected = "10.10.10.10";
		expect(oRequest.getRemoteAddr()).andReturn(oExpected);
		replay(oRequest);
		String oActual = oConverter.convert(null);
		verify(oRequest);
		assertEquals(oExpected, oActual);
	}
}
