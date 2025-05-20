package org.heal.servlet.search;

import java.io.*;
import org.heal.servlet.Action;
import org.heal.module.search.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

/**
 * @author Grace
 * @version 0.1
 */

public class VarSearchAction implements Action {
    public VarSearchAction() {
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
            throws IOException, ServletException {
        SimpleSearchDAO sd = (SimpleSearchDAO)servlet.getServletContext().getAttribute("SimpleSearchDAO");

        String prek = request.getParameter("prekeywords");
        String[] keywordArray = request.getParameterValues("keywords");
        ParameterBean param = new ParameterBean();
        ParameterNode pam = new ParameterNode();
        String keywords = "";
        boolean stype = true;
        if(keywordArray == null) {
            keywordArray[0] = "%";
            //System.out.print(keywordString);
        }
        String relation = null;
        String columInfo = "ALL.ALL"; //dummy variable
        param.setHidden(false);

        //mapping the terms from the interface to the database tablename and columns
        String key = null;

        for(int i = 0; i < keywordArray.length; i++) {
            key = keywordArray[i];
            key = key.toLowerCase(); //switch to lower case
            key = key.replaceAll("\'", "\'\'");
            if(i == 0) {
                relation = "AND";
            } else {
                relation = "OR";
            }

            pam = new ParameterNode(columInfo, key, relation, stype);
            param.addParameters(pam);
        }
        for(int i = 0; i < param.size(); i++) {
            ParameterNode tempNode = param.getParameters(i);
            if(i == 0) {
                keywords = tempNode.getKeyWord();
            } else {
                keywords = keywords + " " + tempNode.getRelation() + " " + tempNode.getKeyWord();
            }
        }
        request.getSession().setAttribute("variants", keywords);
        request.getSession().setAttribute("searchResults", null);
        SearchResultBean resultBean = new SearchResultBean();
        try {
            if(keywordArray != null) {
                resultBean = sd.simpleSearch(param,"check");
                resultBean.setKeywords(prek);
                System.out.print("*****" + prek);
                request.getSession().setAttribute("searchResults", resultBean);
            }
            if(resultBean == null) System.err.println("rb is null");
            //NOTE: PAGE MUST BE THE LAST PARAMETER!
            String queryString = request.getQueryString();
            if(queryString == null || queryString.length() == 0) {
                queryString = "page=1";
            } else {
                queryString = queryString + "&page=1";
            }
            String redirectURL = "searchResults?" + queryString;
            response.sendRedirect(redirectURL);
        } catch(SQLException ex) {
            //throw new SQLException(ex.toString());
        }
    }

    /**
     *
     * @return
     */
    public boolean actionRequiresLogin() {
        return false;
    }
}

