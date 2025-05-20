/*
 *   CVS $Id: ResponseHeaderFilter.java,v 1.1 2006/12/18 21:40:22 grodecki Exp $
 * 
 *   ====================================================================
 *                 Open Source Health Records Exchange
 *   ====================================================================
 *
 *   Copyright (C) 2005, 2006 Browsersoft Inc.
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License, version 2, 
 *   as published by the Free Software Foundation.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   The GNU General Public License is available at
 *   http://www.fsf.org/licensing/licenses/gpl.html
 *
 *   Email: info@openhre.org
 *   Web:   http://www.openhre.org
 */

package com.browsersoft.servlet.util;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * This class was inspired by:
 * <pre>
 * http://issues.apache.org/bugzilla/show_bug.cgi?id=27122
 * and
 * http://www.jspinsider.com/content/dev/afessh/another-filter-every-site-should-have.html
 * </pre>
 * @author don@browsersoft.com
 *
 */
public class ResponseHeaderFilter implements Filter {
  FilterConfig fc;
  public void doFilter(ServletRequest req,
                       ServletResponse res,
                       FilterChain chain)
                       throws IOException,
                              ServletException {
	System.out.println("ResponseHeaderFilter:doFilter() called");
    HttpServletResponse response =
      (HttpServletResponse) res;
    // set the provided HTTP response parameters
    for (Enumeration e=fc.getInitParameterNames();
        e.hasMoreElements();) {
      String headerName = (String)e.nextElement();
      System.out.println("Setting Response Header "+headerName
    		  		+" to "+fc.getInitParameter(headerName));
      response.setHeader(headerName, // not addHeader()
                 fc.getInitParameter(headerName));
    }
    // pass the request/response on
    chain.doFilter(req, response);
  }
  public void init(FilterConfig filterConfig) {
	System.out.println("ResponseHeaderFilter:init() called with "+filterConfig);
    this.fc = filterConfig;
  }
  public void destroy() {
	System.out.println("ResponseHeaderFilter:destroy() called");
    this.fc = null;
  }
}

