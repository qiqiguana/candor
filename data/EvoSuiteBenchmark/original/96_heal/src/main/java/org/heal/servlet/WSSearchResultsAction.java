package org.heal.servlet;


import org.heal.module.search.SearchResultBean;
import org.heal.module.search.ShortMetadataResultBean;
import org.heal.util.ParameterMap;
import org.heal.util.ResultsPager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * An {@link Action} that handles the results of MERLOT's SOAP based search services.
 */
 
public class WSSearchResultsAction implements Action {
    
    /**
     *
     * @param servlet
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    
    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        
        SearchResultBean searchResults;
        searchResults = (SearchResultBean) request.getSession().getAttribute("searchResults"); 
		String queryString = request.getQueryString();
        if (queryString == null) {
            queryString = "";
        }

		// If there are no search results, redirect to the appropriate search action.
		// This is here so that, for example, if a user bookmarked a search results page
		// rather than arriving here through a search, the expected page would be generated
		if (searchResults == null) {
            String searchURL = "../search/fedsearch.jsp";
            response.sendRedirect(searchURL);
            return;
        }

		// page specifies the page we are looking at, defaultly page 1
        int displayPage = getParameterAsInteger(request, "page", 1);
		// display specifies how many items are being displayed per page, defaultly 10
        int displayIncrement = getParameterAsInteger(request, "display", 10);


		//**** BEGIN code to calculate displayStart and displayStop
        int displayStart = (displayPage - 1) * displayIncrement;
        int displayStop = 0;
        ShortMetadataResultBean[] allMetadata = null;
        if (searchResults != null) {
            allMetadata = searchResults.getShortRecords();
        }
        if (allMetadata != null) {
            displayStop = displayStart + displayIncrement;
            if (displayStop > allMetadata.length) {
                displayStop = allMetadata.length;
            }
        }
        //**** END code to calculate displayStart and displayStop
        request.setAttribute("displayStart", "" + displayStart);
        request.setAttribute("displayStop", "" + displayStop);
		
        {
        ParameterMap parameters = new ParameterMap(request.getParameterMap());
		// If they're changing the display size, we must reset the page
		parameters.remove("page");

		// Disregarding what "display" value may be in the current parameters
		// map, we put in "5" and construct a new query string to represent
		// the original query string with a "display" of "5"
        parameters.put("display", "5");
        request.setAttribute("displayString5", "wsSearchResults?"+ parameters.toString());

        // Disregarding what "display" value may be in the current parameters
		// map, we put in "10" and construct a new query string to represent
        // the original query string with a "display" of "10"
        parameters.put("display", "10");
        request.setAttribute("displayString10", "wsSearchResults?"+ parameters.toString());

        // Disregarding what "display" value may be in the current parameters
        // map, we put in "25" and construct a new query string to represent
        // the original query string with a "display" of "25"
        parameters.put("display", "25");
        request.setAttribute("displayString25", "wsSearchResults?"+ parameters.toString());

        // Disregarding what "display" value may be in the current parameters
        // map, we put in "25" and construct a new query string to represent
        // the original query string with a "display" of "25"
        parameters.put("display", "50");
        request.setAttribute("displayString50", "wsSearchResults?"+ parameters.toString());

        // The "searchURL" is the original URL with only the search-specific
        // parameters in the query string with the addition of the "display" parameter.
        // This is used in creating the links to different pages of search results.
        parameters = new ParameterMap(request.getParameterMap());
        parameters.remove("page");
        request.setAttribute("searchURL", "wsSearchResults?" +parameters.toString());
        }
				
        request.setAttribute("origURL", "wsSearchResults?" + queryString);
        String pagenb = request.getParameter("page");
        
        ParameterMap parameterv = new ParameterMap(request.getParameterMap());
        // If they're changing the display format, we must reset the page
        parameterv.remove("page");
        // Disregarding what "display" format may be in the current parameters
        // map, we put in "1" and construct a new query string to represent
        // the original query string with a "viewtype" of "1"
        parameterv.put("viewtype", "1");
        parameterv.put("page", pagenb);
        request.setAttribute("view1", "wsSearchResults?"+ parameterv.toString());
        // Disregarding what "display" format may be in the current parameters
        // map, we put in "2" and construct a new query string to represent
        // the original query string with a "viewtype" of "2"
        parameterv.put("viewtype", "2");
        parameterv.put("page", pagenb);
        request.setAttribute("view2", "wsSearchResults?"+ parameterv.toString());
        // Disregarding what "display" value may be in the current parameters
        // map, we put in "3" and construct a new query string to represent
        // the original query string with a "viewtype" of "3"
        parameterv.put("viewtype", "3");
        parameterv.put("page", pagenb);
        request.setAttribute("view3", "wsSearchResults?"+ parameterv.toString());
        // The "searchURL" is the original URL with only the search-specific
        // parameters in the query string with the addition of the "display" parameter.
        // This is used in creating the links to different pages of search results.
        parameterv = new ParameterMap(request.getParameterMap());
        parameterv.remove("page");
        request.setAttribute("searchURL", "wsSearchResults?" +parameterv.toString());				
        if (allMetadata != null) {
            ResultsPager pager = new org.heal.util.ResultsPager(allMetadata.length, displayIncrement, displayPage);
            request.setAttribute("pager", pager);
        }
		String keywords = new String();
		keywords = request.getParameter("keywords");

        // If the original query string is missing a parameter we add
        // the parameter's default value before passing on the query
        String queryStringWithDefaults;
        {
        ParameterMap parameters = new ParameterMap(request.getParameterMap());
        if(!parameters.containsKey("display")) {
            // The default "display" is "10"
            parameters.put("display", "10");
        }
        if(!parameters.containsKey("page")) {
            // The default "page" is "1"
            parameters.put("page", "1");
        }
        queryStringWithDefaults = parameters.toString();
        }
    
        String forwardURL = "/search/WSsearchresults.jsp?" + queryStringWithDefaults;
        RequestDispatcher rd;
        rd = request.getRequestDispatcher(forwardURL);
        rd.forward(request, response);
        return;
    }

    private int getParameterAsInteger(final HttpServletRequest request, final String paramName, final int defaultValue) {
        int ret = defaultValue;
        String parameter = request.getParameter(paramName);
        if (parameter != null && parameter.length() != 0) {
            try {
                ret = Integer.parseInt(parameter);
                } 
            catch(NumberFormatException e) { } // does nothing, so the defaultValue will be returned
        }
        return ret; 
    }

    public boolean actionRequiresLogin() {
        return false;
    }
}
