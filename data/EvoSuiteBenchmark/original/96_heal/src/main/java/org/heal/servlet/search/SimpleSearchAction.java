package org.heal.servlet.search;

import org.heal.module.search.ParameterBean;
import org.heal.module.search.ParameterNode;
import org.heal.module.search.SearchResultBean;
//import org.heal.module.search.ConceptMappingBean;
import org.heal.module.search.SimpleSearchDAO;
//import org.heal.module.search.ConceptMappingDAO;
import org.heal.servlet.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Grace/Julie
 * @version 0.1
 */
 
public class SimpleSearchAction implements Action
{
  public SimpleSearchAction()
  {
  }
  /**
   * 
   * @param servlet
   * @param request
   * @param response
   * @throws IOException
   * @throws ServletException
   */
  public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException 
  {
    SimpleSearchDAO sd = (SimpleSearchDAO) servlet.getServletContext().getAttribute("SimpleSearchDAO"); 
   // ConceptMappingDAO cd = (ConceptMappingDAO) servlet.getServletContext().getAttribute("ConceptMappingDAO");
    request.getSession().setAttribute("prekeywords",null); 
    String consumer = request.getParameter("consumer");
    String keywordString = request.getParameter("keywords");
    String peir = request.getParameter("peir");
    String searchType = request.getParameter("searchtype");
    String keywords ="";
    ParameterBean param=new ParameterBean(keywordString); 
    if (peir == null){
       param.setUsageRights(null);; 
    }
    else {
        param.setUsageRights("PEIR");;
    }
    request.getSession().setAttribute("searchResults", null); 
   // request.getSession().setAttribute("conceptMapping", null);
    SearchResultBean resultBean = new SearchResultBean();
   // ConceptMappingBean cmb = new ConceptMappingBean();
    try 
    {
      if (keywordString != null) 
      {
        resultBean = sd.simpleSearch(param,consumer);
      //  cmb = cd.ConceptMapping(keywordString);     
        for (int i =0; i<param.size(); i++)
        {
          ParameterNode tempNode=param.getParameters(i);
          if(i == 0)
          {
            keywords = tempNode.getKeyWord();
          }
          else 
          {
            keywords = keywords + " " + tempNode.getRelation()+" "+ tempNode.getKeyWord();
          }
        }
        resultBean.setKeywords(keywords);  
        request.getSession().setAttribute("searchResults", resultBean);
       // request.getSession().setAttribute("conceptMapping", cmb);
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
  /**
   * 
   * @return 
   */
  public boolean actionRequiresLogin() 
  {
    return false;
  }
}