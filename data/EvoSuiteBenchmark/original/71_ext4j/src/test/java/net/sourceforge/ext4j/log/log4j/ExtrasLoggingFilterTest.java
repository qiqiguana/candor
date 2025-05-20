/**
 *
 */
package net.sourceforge.ext4j.log.log4j;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.ext4j.log.ExecutionContext;
import net.sourceforge.ext4j.log.ExtrasLoggingFilter;
import net.sourceforge.ext4j.log.Server;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Test;

/**
 * @author Luc Pezet <lpezet@gmail.com>
 *
 */
public class ExtrasLoggingFilterTest {

	@Test
	public void init() throws Exception {
		ExtrasLoggingFilter oFilter = new ExtrasLoggingFilter();
		FilterConfig oConfig = createMock(FilterConfig.class);
		String oExpected = "test/1.2.3";
		ServletContext oServletContext = createMock(ServletContext.class);
		expect(oConfig.getServletContext()).andReturn(oServletContext);
		expect(oServletContext.getServerInfo()).andReturn(oExpected);
		replay(oConfig);
		replay(oServletContext);
		oFilter.init(oConfig);
		assertEquals(oExpected, Server.getInstance().getInfo());
		verify(oConfig);
		verify(oServletContext);
	}

	@Test
	public void doFilter() throws Exception {
		ExtrasLoggingFilter oFilter = new ExtrasLoggingFilter();
		ServletRequest oRequest = createMock(HttpServletRequest.class);
		ServletResponse oResponse = createMock(HttpServletResponse.class);
		FilterChain oChain = createMock(FilterChain.class);
		ExecutionContext.setCurrentRequest(null);
		oFilter.doFilter(oRequest, oResponse, oChain);
		assertNull(ExecutionContext.getCurrentRequest());
	}
}
