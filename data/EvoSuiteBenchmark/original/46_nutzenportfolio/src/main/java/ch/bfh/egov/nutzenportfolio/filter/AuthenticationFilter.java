/**
 * Nutzenportfolio
 * Copyright (C) 2006 Kompetenzzentrum E-Business, Simon Bergamin

 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package ch.bfh.egov.nutzenportfolio.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ch.bfh.egov.nutzenportfolio.Constants;

/**
 * Struts-Filter zur Authentifizierung von Benutzern
 * 
 * @author Kompetenzzentrum E-Business, Simon Bergamin
 */
public class AuthenticationFilter implements Filter {
	
	private Log logger = LogFactory.getLog(this.getClass());

	/**
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {}

	/**
   * Prüft, ob ein Benutzer eingeloggt ist.
   * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(
			ServletRequest servletRequest,
			ServletResponse servletResponse,
			FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request;
		HttpServletResponse response;
		String url;
		try {
		  request = (HttpServletRequest) servletRequest;
		  response = (HttpServletResponse) servletResponse;
		} catch (ClassCastException cce) {
		  logger.fatal("Kein HTTP Request erhalten!");
		  return;
		}
		
		// Benutzer eingeloggt?
		HttpSession session = request.getSession();
		Integer mandantId = null;
		try {
			mandantId = (Integer) session.getAttribute(Constants.MANDANT_ID);
		} catch (ClassCastException cce) {}
		if (mandantId == null) { 
		  url = request.getContextPath() + Constants.LOGIN_ACTION;     
		  response.sendRedirect(url);
		  logger.warn("Unerlaubter Zugriff blockiert. Benutzer muss angemeldet sein!");
		  return;
		}
		chain.doFilter(servletRequest,servletResponse);
	}

	/**
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {}

}

