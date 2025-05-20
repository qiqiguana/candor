package org.heal.servlet.search;

import java.io.*;
import java.util.*;
import org.heal.servlet.Action;
import org.heal.module.search.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

/**
 * @author Grace 
 * @version 0.1
 */
 
public class SortResultsAction implements Action
{
  public SortResultsAction()
  {
  }
  public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException 
  {   
    String metaids=null;
    ReSearchDAO sd = (ReSearchDAO) servlet.getServletContext().getAttribute("ReSearchDAO");
    List metaIDs=(List)request.getSession().getAttribute("metaids");
    String queryString = request.getQueryString();
    String kwds = request.getParameter("keywords");
    String redirectURL=null;
    //System.out.print("metaIDs"+metaIDs);
    if(metaIDs != null) 
    {      
      metaids=sd.makeInString(metaIDs);
      request.getSession().setAttribute("sortString", metaids);  
      //System.out.print(metaids);  
    }
    else {
      metaids = (String)request.getSession().getAttribute("sortString");
    //  System.out.print(metaids);  
    }
    String sortterm = request.getParameter("sortby");
    SearchResultBean resultBean = new SearchResultBean();         
    try 
    {
    //  if(sortterm.equals("Relevance"))
    //  {  
    //    redirectURL = "http://157.142.98.192/healnew/search/relevance.html";
    //    response.sendRedirect(redirectURL);
    //  }
    //  else 
    //  {
      resultBean = sd.sortResult(sortterm,metaids); //filter hidden 
      resultBean.setKeywords(kwds);
      request.getSession().setAttribute("searchResults", resultBean);   
      if (resultBean == null) System.err.println("rb is null");
      if (queryString == null || queryString.length() == 0) 
      {
        queryString = "page=1";
      } 
     // else 
     // {
     //   queryString = queryString + "&page=1";
    //  }
      redirectURL = "searchResults?" + queryString;
      response.sendRedirect(redirectURL);    
     // }
    } 
    catch (SQLException ex) 
    {
       //throw new SQLException(ex.toString());
    }
  }
   public boolean actionRequiresLogin() 
   {
     return false;
   }
}

