/*
Copyright 2007 DB-Everywhere
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
 	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.gbshape.dbe.utils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gbshape.dbe.struts.bean.DBDataBean;


public final class SecurityFilter implements javax.servlet.Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

		String requestedUrl = req.getServletPath();
		if (requestedUrl.equals("/dbe.do")){
			chain.doFilter(request, response);
		} else {
			HttpSession session = req.getSession();
	        DBDataBean dataBean = (DBDataBean) session.getAttribute("DBDataBean");
			if(dataBean == null){
				session.setAttribute("error", "SESSION LOST");
				request.getRequestDispatcher("/home.do").forward(request,response);
			} else {
				chain.doFilter(request, response);
			}
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}




}
