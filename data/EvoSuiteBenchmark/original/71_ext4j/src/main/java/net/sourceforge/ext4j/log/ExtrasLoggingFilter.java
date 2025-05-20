/**
 *
 */
package net.sourceforge.ext4j.log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.sourceforge.ext4j.log.filter.IInitLogger;



/**
 * @author Luc Pezet <lpezet@gmail.com>
 *
 */
public class ExtrasLoggingFilter implements Filter {

	private List<IInitLogger> mInitLoggers = new ArrayList<IInitLogger>();

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest pRequest, ServletResponse pResponse, FilterChain pFilterChain) throws IOException, ServletException {
		ExecutionContext.setCurrentRequest((HttpServletRequest) pRequest);
		try {
			pFilterChain.doFilter(pRequest, pResponse);
		} finally {
			ExecutionContext.setCurrentRequest(null);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig pConfig) throws ServletException {
		Server.getInstance().setInfo(pConfig.getServletContext().getServerInfo());
		try {
			for (IInitLogger oInit : mInitLoggers) {
				oInit.init(pConfig);
			}
		} catch (ServletException e) {
			throw e;
		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

}
