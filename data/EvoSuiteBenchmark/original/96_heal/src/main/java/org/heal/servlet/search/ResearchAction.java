package org.heal.servlet.search;

import java.io.*;
import java.util.*;
import org.heal.module.search.*;
import org.heal.servlet.Action;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

/**
 * @author Grace
 * @version 0.1
 */
 
public class ResearchAction implements Action 
{
  public ResearchAction()
  {
  }
  public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException 
  {
    List metaIDs = null;
    String metaids=null;
    
    SearchResultBean resultBean = new SearchResultBean(); 
    ReSearchDAO sd = (ReSearchDAO) servlet.getServletContext().getAttribute("ReSearchDAO");
    String checker = request.getParameter("check");  
    String keywordString = request.getParameter("keywords");  
    String searchtype = request.getParameter("selecttype");
   // String pagenb = request.getParameter("page");
    if(checker != null)
    { 
      metaIDs=(List)request.getSession().getAttribute("metaids");
     // System.out.print("step2:"+metaIDs);
      metaids=sd.makeInString(metaIDs);
      /* Tracking previous search words
      String prewords = request.getParameter("prekeywords");
      
      String prekeywords =(String)request.getSession().getAttribute("prekeywords");
      if(prekeywords != null) 
      {     
          prekeywords = prekeywords+" AND "+prewords;       
      }
      else 
      {
        prekeywords =prewords;
      }
      request.getSession().setAttribute("prekeywords",prekeywords);  
      */
    }
    else 
    {
      metaids = "no";
    //  request.getSession().setAttribute("prekeywords",null); 
    }    
    if (keywordString == null) 
    {
      keywordString = "%";
    }
    else if (keywordString != null && keywordString.length() == 0) 
    {
      keywordString = "%";
    }
    else 
    {
      //control apostrophes search in database
      char ch=39;     
      String nch="''";
      String och=null;
      och=Character.toString(ch);
      keywordString=keywordString.toLowerCase();
      keywordString=keywordString.replaceAll(och, nch);
      keywordString=keywordString.replace('"','%'); 
      keywordString=keywordString.replaceAll(" and "," "); 
      keywordString=keywordString.replaceAll(" or "," ");   
    }
    request.getSession().setAttribute("searchResults", null);
    String[] filterStringArray = request.getParameterValues("filters");        
    if ((filterStringArray != null) && (filterStringArray[0].equals("all")))
      filterStringArray = null;             
    try 
    {
      if (keywordString != null) 
      {
        resultBean = sd.editSearch(metaids,keywordString,filterStringArray,searchtype,false); //filter hidden 
       // System.out.print("\nID"+metaids);
       List keys = new ArrayList();
       String keywords = "";
       StringTokenizer tokenizer = new StringTokenizer(keywordString," ");
       while (tokenizer.hasMoreTokens()) 
       {
         keys.add(tokenizer.nextToken());
       }
       for (int i=0; i<keys.size(); i++)
       {
         if(i == 0)
          {
            keywords =(String)keys.get(0);
          }
          else 
          {
            if(searchtype.equals("All"))
            {
              keywords = keywords + " AND "+ (String)keys.get(i);  
            }
            if(searchtype.equals("Any"))
            {
              keywords = keywords + " OR "+ (String)keys.get(i); 
            }
            if(searchtype.equals("Exact"))
            {
              keywords = keywords + " "+ (String)keys.get(i);    
            }            
          }
        }
        resultBean.setKeywords(keywords);
        request.getSession().setAttribute("searchResults", resultBean);
      }
       
      if (resultBean == null) System.err.println("rb is null");
      //NOTE: PAGE MUST BE THE LAST PARAMETER!
      String queryString = request.getQueryString();        
      if (queryString == null || queryString.length() == 0) 
      {
        queryString = "page=1";
      } 
      else 
      {
        queryString = queryString + "&page=1";
      }
      String redirectURL = "searchResults?" + queryString;
      response.sendRedirect(redirectURL);
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
