package org.heal.servlet.search;

import java.io.*;
import java.util.*;
import java.text.*;
import org.heal.servlet.Action;
import org.heal.module.metadata.*;
import org.heal.module.search.*;
import org.heal.module.user.UserBean;
import org.heal.util.*;
import com.ora.jsp.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;

/**
 * @author Grace
 * @version 0.1
 */
 
public class RefineSearchAction implements Action 
{
  public RefineSearchAction()
  {
  }
  public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
  throws IOException, ServletException {        
    String redirectURL = null;
    redirectURL = request.getParameter("pam");
    // System.out.println("URL:"+redirectURL);
    response.sendRedirect(redirectURL);
  }

  public boolean actionRequiresLogin() {
    return false;
  }
}