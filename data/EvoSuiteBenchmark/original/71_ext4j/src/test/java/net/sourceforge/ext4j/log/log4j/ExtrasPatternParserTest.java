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
import net.sourceforge.ext4j.log.Server;
import net.sourceforge.ext4j.log.log4j.ExtrasPatternParser;

import org.apache.log4j.helpers.PatternConverter;
import org.junit.Test;

/**
 * @author Luc Pezet <lpezet@gmail.com>
 *
 */
public class ExtrasPatternParserTest {

	
	@Test
	public void parseCurrentHost() throws Exception {
		String oExpected = "SuperMachine";
		ExtrasPatternParser oParser = new ExtrasPatternParser("%s");
		Server.getInstance().setCurrentHost(oExpected);
		PatternConverter oCnv = oParser.parse();
		StringBuffer oBuf = new StringBuffer();
		oCnv.format(oBuf, null);
		assertEquals(oExpected, oBuf.toString());
	}
	
	@Test
	public void parsePublicIP() throws Exception {
		String oExpected = "210.210.210.210";
		ExtrasPatternParser oParser = new ExtrasPatternParser("%i");
		Server.getInstance().setPublicIP(oExpected);
		PatternConverter oCnv = oParser.parse();
		StringBuffer oBuf = new StringBuffer();
		oCnv.format(oBuf, null);
		assertEquals(oExpected, oBuf.toString());
	}
		
	@Test
	public void parseRemoteAddr() throws Exception {
		ExtrasPatternParser oParser = new ExtrasPatternParser("%R");
		HttpServletRequest oRequest = createMock(HttpServletRequest.class);
		ExecutionContext.setCurrentRequest(oRequest);
		String oExpected = "10.10.10.10";
		expect(oRequest.getRemoteAddr()).andReturn(oExpected);
		replay(oRequest);
		PatternConverter oCnv = oParser.parse();
		StringBuffer oBuf = new StringBuffer();
		oCnv.format(oBuf, null);
		verify(oRequest);
		assertEquals(oExpected, oBuf.toString());
	}
	
	@Test
	public void parseRequestURI() throws Exception {
		ExtrasPatternParser oParser = new ExtrasPatternParser("%u");
		HttpServletRequest oRequest = createMock(HttpServletRequest.class);
		ExecutionContext.setCurrentRequest(oRequest);
		String oExpected = "http://www.test.com/test";
		expect(oRequest.getRequestURI()).andReturn(oExpected);
		replay(oRequest);
		PatternConverter oCnv = oParser.parse();
		StringBuffer oBuf = new StringBuffer();
		oCnv.format(oBuf, null);
		verify(oRequest);
		assertEquals(oExpected, oBuf.toString());
	}
	
	@Test
	public void parseQueryString() throws Exception {
		ExtrasPatternParser oParser = new ExtrasPatternParser("%q");
		HttpServletRequest oRequest = createMock(HttpServletRequest.class);
		ExecutionContext.setCurrentRequest(oRequest);
		String oExpected = "hello=world&byebye=world";
		expect(oRequest.getQueryString()).andReturn(oExpected);
		replay(oRequest);
		PatternConverter oCnv = oParser.parse();
		StringBuffer oBuf = new StringBuffer();
		oCnv.format(oBuf, null);
		verify(oRequest);
		assertEquals(oExpected, oBuf.toString());
	}
}
