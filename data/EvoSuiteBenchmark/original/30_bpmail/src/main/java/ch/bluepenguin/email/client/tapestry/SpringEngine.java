/*
 * Created on 11.01.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ch.bluepenguin.email.client.tapestry;

import java.util.Map;

import org.apache.tapestry.engine.BaseEngine;
import org.apache.tapestry.request.RequestContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author Christian
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SpringEngine extends BaseEngine {
	private static final String APPLICATION_CONTEXT_KEY = "appContext";

	protected void setupForRequest(RequestContext context) {
		super.setupForRequest(context); 
		//		 insert ApplicationContext in global, if not there
		Map global = (Map) getGlobal();
		ApplicationContext ac = (ApplicationContext) global
				.get(APPLICATION_CONTEXT_KEY);
		if (ac == null) {
			ac = WebApplicationContextUtils.getWebApplicationContext(context
					.getServlet().getServletContext());
			global.put(APPLICATION_CONTEXT_KEY, ac);
		}
	}
}