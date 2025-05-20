package org.heal.servlet.search;

import org.heal.module.search.AdvSearchDAO;
import org.heal.module.search.ParameterBean;
import org.heal.module.search.ParameterNode;
import org.heal.module.search.SearchResultBean;
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

public class AdvSearchAction implements Action 
{
  public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException 
  {
    request.getSession().setAttribute("searchResults", null);
    AdvSearchDAO sd = (AdvSearchDAO)servlet.getServletContext().getAttribute("AdvSearchDAO");
    request.getSession().setAttribute("prekeywords", null);
    SearchResultBean resultBean;
    // searchType is unused for now...
    String searchType = request.getParameter("searchtype");
    ParameterBean param = new ParameterBean();
    String columInfo;
    String keyWord="";
    String relation;
    ParameterNode pam;
    param.setHidden(false);
    String type;
    String input;
    String logic;
    boolean validate=true;
    String keywords = "";
    String[] sourceArray =null;
    String[] publicationNames = null;
    String[] primall = {"all"};
    if(searchType == null)
    {
      searchType ="advanced";
      publicationNames = request.getParameterValues("publicationName");
      if((null != publicationNames) && (!"all".equals(publicationNames[0]))) 
      {
        param.setPublicationNames(publicationNames);
      }
      
      String[] publicationIds = request.getParameterValues("publicationId");
      if((null != publicationIds) && (!"all".equals(publicationIds[0]))) 
      {
        param.setPublicationIds(publicationIds);
        param.setPrimaryArray(primall);
      }
    }
    else if(searchType.equals("collection"))
    {
      sourceArray = request.getParameterValues("source");
      param.setSourceCollection(sourceArray);
      param.setPrimaryArray(primall);
    }
    else
    {
      for(int i = 0; i < 4; i++) 
      {
        type = "type";
        input = "input";
        logic = "logic";
        columInfo = request.getParameter(type + i);
        keyWord = request.getParameter(input + i);
        if(i != 0) 
        {
          relation = request.getParameter(logic + i);
        } 
        else 
        {
          relation = "AND";
        }
        //mapping the terms from the interface to the database tablename and columns
        if(keyWord.length()>1)
        {
          keyWord = keyWord.trim();
          if(columInfo.compareTo("ALL") == 0) 
          {
            columInfo = ("ALL.ALL");
          } 
          else if(columInfo.compareTo("Keyword") == 0) 
          {
            columInfo = ("Keywords.Keyword");
          } 
          else if(columInfo.compareTo("Title") == 0) 
          {
            columInfo = ("Metadata.Title");
          } 
          else if(columInfo.compareTo("metaID") == 0) 
          {
            columInfo = ("Metadata.MetadataID");
          } 
          else if(columInfo.compareTo("Description") == 0) 
          {
            columInfo = ("Metadata.Description");
          } 
          else if(columInfo.compareTo("Contributor") == 0) 
          {
            columInfo = ("Contributors.vCard");
          } 
          else if(columInfo.compareTo("CopyrightHolder") == 0) 
          {
            columInfo = ("CopyrightHolders.vCard");
          }
          
          //added by Zhen 6/12/08 for Tag cloud search.
          else if(columInfo.compareTo("Tags") == 0) 
          {
            columInfo = ("Tags.tag");
          }        
          keyWord=keyWord.toLowerCase(); //switch to lower case
          StringBuffer bf=new StringBuffer();
          char[] origQuery=keyWord.toCharArray();
          int paranthesisCount=0; int pos=0;
          for (int t=0; t<origQuery.length; t++)
          {
            if (origQuery[t]=='(' || origQuery[t]==')')
            {       
              if (origQuery[t]=='(') //if ")(" happens, change to ") and ("
              {
                if(bf.length()>=2 && bf.charAt(bf.length()-2)==')') 
                {
                  bf.append("and "+origQuery[t]+" ");
                  paranthesisCount++;
                }
                else 
                {
                  bf.append(" "+origQuery[t]+" ");
                  paranthesisCount++;
                }
              }
              else if (origQuery[t]==')') //if "( )" happens, remove the segment
              {
                pos=bf.length()-1;
                while (bf.charAt(pos)==' ' && pos>0)//need to get to the last character
                {
                  pos--;
                }
                if(bf.charAt(pos)=='(') 
                {
                  bf.delete(bf.length()-3, bf.length()-1);
                  paranthesisCount--;
                }
                else
                {
                  bf.append(" "+origQuery[t]+" ");
                  paranthesisCount--;
                }          
              }
              else bf.append(" "+origQuery[t]+" ");
            }
            else if (origQuery[t]=='"') //if "...", copy the whole string 
            {
              bf.append(" \" ");
              t++;
              while (i<origQuery.length && origQuery[t]!='"')
              {
                bf.append(origQuery[t]);
                t++;
              }
              if (t==origQuery.length)
              {
                validate=false;
              }
              bf.append(" \" ");
            }
            else if (origQuery[t]=='\'')
            {
              bf.append("\'\'");
            }
            else if (origQuery[t]==' ' && bf.charAt(bf.length()-1)==' ') 
            {        
            }
            else bf.append(origQuery[t]);
          }
          if (paranthesisCount!=0)
          {
            validate=false;
          }
          if (validate)
          {
            pam=new ParameterNode(columInfo, bf.toString(), relation);
            param.addParameters(pam);         
          }
          else
          {
            i=10;
          }			
        }
        else
        {
          
          break;
        }
      }
      String[] imagingArray = request.getParameterValues("imaging");
      if((imagingArray != null) && (imagingArray[0].compareTo("all")) != 0) 
      {
        param.setImaging(imagingArray);    
      }     
      String[] diseaseprocessArray = request.getParameterValues("diseaseProcess");
      if(diseaseprocessArray != null) 
      {
        if (diseaseprocessArray[0].compareTo("all") != 0) 
        {
            param.setDisease(diseaseprocessArray);
        }  
      }
      
      String[] filterStringArray = request.getParameterValues("filters");
      if((filterStringArray != null) && (filterStringArray[0].compareTo("all") != 0 && filterStringArray.length < 8)) 
      {
        param.setFilterArray(filterStringArray);
      } 
      sourceArray = request.getParameterValues("source");
      if((sourceArray != null) && (sourceArray[0].compareTo("all") != 0 )) 
        {
            param.setSourceCollection(sourceArray);
        } 
      String[] primary = {"Health Profession Education","Higher Education"};
      String[] primaryArray=request.getParameterValues("primary");
      if((primaryArray != null))  
      {
        if (primaryArray[0].startsWith("Health")){
            param.setPrimaryArray(primary);
        }
        else
        param.setPrimaryArray(primaryArray);
      }
      
    }
    try 
    {
        resultBean = sd.AdvSearch(param);
        
        for(int i = 0; i < param.size(); i++) 
        {
            ParameterNode tempNode = param.getParameters(i);
            if(i == 0) 
            {
                keywords = tempNode.getKeyWord();
            } 
            else 
            {
                keywords = keywords + " " + tempNode.getRelation() + " " + tempNode.getKeyWord();
            }
        }
        resultBean.setKeywords(keywords);
        request.getSession().setAttribute("searchResults", resultBean);
        //NOTE: PAGE MUST BE THE LAST PARAMETER!
        String queryString = request.getQueryString();
        request.getSession().setAttribute("pams", queryString);
        if(queryString == null || queryString.length() == 0) 
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
    catch(SQLException ex) 
    {
        //throw new SQLException(ex.toString());
    }
  }
  public boolean actionRequiresLogin() 
  {
    return false;
  }
}
