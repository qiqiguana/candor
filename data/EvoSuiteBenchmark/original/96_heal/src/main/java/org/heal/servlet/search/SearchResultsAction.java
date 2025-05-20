package org.heal.servlet.search;

import org.heal.module.metadata.MetadataDAO;
import org.heal.module.metadata.ShortMetadataBean;
import org.heal.module.metadata.ThumbnailBean;
import org.heal.module.search.SearchResultBean;
import org.heal.module.search.ShortMetadataResultBean;
import org.heal.util.FileLocator;
import org.heal.util.InterfaceUtilitiesBean;
import org.heal.util.ParameterMap;
import org.heal.util.ResultsPager;
import org.heal.servlet.Action;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;

public class SearchResultsAction implements Action 
{
  public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException 
  {
    SearchResultBean searchResults;
    searchResults = (SearchResultBean) request.getSession().getAttribute("searchResults");
    String searchType = request.getParameter("searchtype");      
    String queryString = request.getQueryString();
    String pagenb = request.getParameter("page");
    if (queryString == null) 
    {
      queryString = "";
    }
    // If there are no search results, redirect to the appropriate search action.
    // This is here so that, for example, if a user bookmarked a search results page
    // rather than arriving here through a search, the expected page would be generated
    if (searchResults == null) 
    {
      String searchURL = null;           
      if ("simple".equalsIgnoreCase(searchType)) 
      {
        searchURL = "search?" + queryString;//substring("action=display&showpage=Searchresults&".length());;
      }
      else
      {
        searchURL = "advsearch?" + queryString;
      } 
      
      response.sendRedirect(searchURL);
      return;
    }
    // page specifies the page we are looking at, defaultly page 1
    int displayPage = getParameterAsInteger(request, "page", 1);
    // display specifies how many items are being displayed per page, defaultly 25
    int displayIncrement = getParameterAsInteger(request, "display", 25);
    //**** BEGIN code to calculate displayStart and displayStop
    int displayStart = (displayPage - 1) * displayIncrement;
    int displayStop = 0;
    ShortMetadataResultBean[] allMetadata = null;
    if (searchResults != null) 
    {
      allMetadata = searchResults.getShortRecords();
    }
    if (allMetadata != null) 
    {
      displayStop = displayStart + displayIncrement;
      if (displayStop > allMetadata.length) 
      {
        displayStop = allMetadata.length;
      }
    }
    //**** END code to calculate displayStart and displayStop
    request.setAttribute("displayStart", "" + displayStart);
    request.setAttribute("displayStop", "" + displayStop);
    ParameterMap parameters = new ParameterMap(request.getParameterMap());
    // If they're changing the display size, we must reset the page
    parameters.remove("page");
    // Disregarding what "display" value may be in the current parameters
    // map, we put in "10" and construct a new query string to represent
    // the original query string with a "display" of "10"
    parameters.put("display", "10");
    parameters.put("page", pagenb);
    request.setAttribute("displayString10", "searchResults?"+ parameters.toString());
    // Disregarding what "display" value may be in the current parameters
    // map, we put in "25" and construct a new query string to represent
    // the original query string with a "display" of "25"
    parameters.put("display", "25");
    parameters.put("page", pagenb);
    request.setAttribute("displayString25", "searchResults?"+ parameters.toString());
    // Disregarding what "display" value may be in the current parameters
    // map, we put in "50" and construct a new query string to represent
    // the original query string with a "display" of "50"
    parameters.put("display", "50");
    parameters.put("page", pagenb);
    request.setAttribute("displayString50", "searchResults?"+ parameters.toString());
    // The "searchURL" is the original URL with only the search-specific
    // parameters in the query string with the addition of the "display" parameter.
    // This is used in creating the links to different pages of search results.
    parameters = new ParameterMap(request.getParameterMap());
    parameters.remove("page");
    request.setAttribute("searchURL", "searchResults?" +parameters.toString());
    ParameterMap parameterv = new ParameterMap(request.getParameterMap());
    // If they're changing the display format, we must reset the page
    parameterv.remove("page");
    // Disregarding what "display" format may be in the current parameters
    // map, we put in "1" and construct a new query string to represent
    // the original query string with a "viewtype" of "1"
    parameterv.put("viewtype", "1");
    parameterv.put("page", pagenb);
    request.setAttribute("view1", "searchResults?"+ parameterv.toString());
    // Disregarding what "display" format may be in the current parameters
    // map, we put in "2" and construct a new query string to represent
    // the original query string with a "viewtype" of "2"
    parameterv.put("viewtype", "2");
    parameterv.put("page", pagenb);
    request.setAttribute("view2", "searchResults?"+ parameterv.toString());
    // Disregarding what "display" value may be in the current parameters
    // map, we put in "3" and construct a new query string to represent
    // the original query string with a "viewtype" of "3"
    parameterv.put("viewtype", "3");
    parameterv.put("page", pagenb);
    request.setAttribute("view3", "searchResults?"+ parameterv.toString());
    // The "searchURL" is the original URL with only the search-specific
    // parameters in the query string with the addition of the "display" parameter.
    // This is used in creating the links to different pages of search results.
    parameterv = new ParameterMap(request.getParameterMap());
    parameterv.remove("page");
    request.setAttribute("searchURL", "searchResults?" +parameterv.toString());
    ParameterMap parametert = new ParameterMap(request.getParameterMap());
    // If they're sorting the display result, we must reset the page
    parametert.remove("page");
    // Disregarding what "sort" value may be in the current parameters
    // map, we put in "sortFormat" and construct a new query string to represent
    // the original query string with a "sort" by "Format"
    parametert.put("sortby", "Title");
    parametert.put("page", pagenb);
    request.setAttribute("sortTitle", "sortResults?"+ parametert.toString());
    parametert.put("sortby", "Format");
    parametert.put("page", pagenb);
    request.setAttribute("sortFormat", "sortResults?"+ parametert.toString());
    // Disregarding what "sort" value may be in the current parameters
    // map, we put in "sortScollect" and construct a new query string to represent
    // the original query string with a "sort" by "Source Collection"
    parametert.put("sortby", "SourceCollection");
    parametert.put("page", pagenb);
    request.setAttribute("sortScollect", "sortResults?"+ parametert.toString());
    // Disregarding what "sort" value may be in the current parameters
    // map, we put in "sortSize" and construct a new query string to represent
    // the original query string with a "sort" by "File Size"
    parametert.put("sortby", "FileSize");
    parametert.put("page", pagenb);
    request.setAttribute("sortSize", "sortResults?"+ parametert.toString());
    // The "searchURL" is the original URL with only the search-specific
    // parameters in the query string with the addition of the "display" parameter.
    // This is used in creating the links to different pages of search results.
    parametert = new ParameterMap(request.getParameterMap());
    parametert.remove("page");
    request.setAttribute("searchURL", "searchResults?" +parametert.toString());
    request.setAttribute("origURL", "searchResults?" + queryString);
    if (allMetadata != null) 
    {
      ResultsPager pager = new org.heal.util.ResultsPager(allMetadata.length, displayIncrement, displayPage);
      request.setAttribute("pager", pager);
    }
    String metadataId;
    ShortMetadataBean currentMeta;
    MetadataDAO metadataServices = (MetadataDAO) servlet.getServletContext().getAttribute("MetadataDAO");
    InterfaceUtilitiesBean interfaceUtilities = (InterfaceUtilitiesBean) servlet.getServletContext().getAttribute("interfaceUtilities");
    FileLocator healFileLocator = (FileLocator) servlet.getServletContext().getAttribute("healFileLocator");
    for (int i = displayStart; i < displayStop; i++) {
      metadataId = allMetadata[i].getMetadataId();
      currentMeta = allMetadata[i].getShortMetadata();
      if (currentMeta == null) 
      {
        try 
        {
          currentMeta = metadataServices.getShortMetadata(metadataId);
        } 
        catch (SQLException e) 
        {
          System.err.println(e);
        }
        String format = currentMeta.getFormat();
          if (format == null) 
        {
          format = "unknown";
        }
        
        ThumbnailBean thumbnail = interfaceUtilities.getThumbnail(currentMeta.getThumbnail(), format, "../");
        currentMeta.setThumbnail(thumbnail);//}
        currentMeta.setLocation(healFileLocator.getContentURL(currentMeta.getLocation()));
        currentMeta.setDescription(interfaceUtilities.getAbbreviatedString(currentMeta.getDescription(), 100));
        currentMeta.setFileSize(NumberFormat.getInstance().format(Long.parseLong(currentMeta.getFileSize())));
        allMetadata[i].setShortMetadata(currentMeta);
      }
    }
    // If the original query string is missing a parameter we add
    // the parameter's default value before passing on the query
    String queryStringWithDefaults;
    ParameterMap parameter = new ParameterMap(request.getParameterMap());
    if(!parameter.containsKey("display")) 
    {
      // The default "display" is "25"
      parameter.put("display", "25");
    }
    if(!parameter.containsKey("page")) 
    {
      // The default "page" is "1"
      parameter.put("page", "1");
    }
    if(!parameter.containsKey("viewtype")) 
    {
      // The default "view" is "1"
      parameter.put("viewtype", "1");
    }
    if(!parameter.containsKey("sortby")) 
    {
      // The default "sort" is "Title"
      parameter.put("sortby", "Title");
    }
    queryStringWithDefaults = parameter.toString();
    String forwardURL = null;
    forwardURL = "/search/searchresults.jsp?" + queryStringWithDefaults;
    RequestDispatcher rd;
    rd = request.getRequestDispatcher(forwardURL);
    rd.forward(request, response);
    return;
  }
  private int getParameterAsInteger(final HttpServletRequest request, final String paramName, final int defaultValue) 
  {
    int ret = defaultValue;
    String parameter = request.getParameter(paramName);
    if (parameter != null && parameter.length() != 0) 
    {
      try 
      {
        ret = Integer.parseInt(parameter);
      } 
      catch(NumberFormatException e) { } // does nothing, so the defaultValue will be returned
    }
    return ret;
  }
  public boolean actionRequiresLogin() 
  {
    return false;
  }
}