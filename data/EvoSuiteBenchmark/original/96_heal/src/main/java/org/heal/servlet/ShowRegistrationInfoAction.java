package org.heal.servlet;

import java.util.ArrayList;
import java.util.Iterator;
import org.heal.module.user.InstructionalLevelBean;
import org.heal.module.user.ProfessionalRoleBean;
import org.heal.module.user.UserBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowRegistrationInfoAction implements Action {
    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
				String type = new String();
				type = request.getParameter("type");		
        RequestDispatcher rd;
        UserBean user = (UserBean)request.getSession().getAttribute("validUser");
        //request.setAttribute("validUser", user);
				setUserAttributes(request,user);
				//System.out.println("type="+type);

				
				if("modify".equals(type) || (!user.isLoginModified()))
					rd = request.getRequestDispatcher("/user/modifyLogin.jsp");
				else
					rd = request.getRequestDispatcher("/user/update.jsp");
        rd.forward(request, response);
        return;
    }
		
		
		public void setUserAttributes(HttpServletRequest request, UserBean user)
		{
			ArrayList ilb = new ArrayList();
			ArrayList instructionalLevel=null;
			ArrayList professionalRole=null;
			
			
			instructionalLevel = user.getInstructionalLevel();
			Iterator it = instructionalLevel.iterator();
			
			while(it.hasNext())
			{
				InstructionalLevelBean instructionalLevelBean = (InstructionalLevelBean)it.next();
				ilb.add(instructionalLevelBean.getInstructionalLevel());
			}
			
			ArrayList prb = new ArrayList();
			professionalRole = user.getProfessionalRole();			
			it = professionalRole.iterator();			
			while(it.hasNext())
			{
				ProfessionalRoleBean professionalRoleBean = (ProfessionalRoleBean)it.next();
				String pr = professionalRoleBean.getProfessionalRole();
				if ((!"Faculty Member/Instructor/Lecturer".equalsIgnoreCase(pr)) && (!"Course Director".equalsIgnoreCase(pr)) && (!"Residency Program Director".equalsIgnoreCase(pr)) && (!"Instructional Developer".equalsIgnoreCase(pr))&& (!"Dean or Other Administrator".equalsIgnoreCase(pr)) && (!"Student or Resident".equalsIgnoreCase(pr)) && (!"Librarian".equalsIgnoreCase(pr))){ 
					request.setAttribute("professionalRoleOtherText",professionalRoleBean.getProfessionalRole());
					prb.add("Other");
				}
				else
					prb.add(professionalRoleBean.getProfessionalRole());

			}
			request.setAttribute("validUser", user);
			request.setAttribute("prb",prb);
			request.setAttribute("ilb",ilb);
		}

    public boolean actionRequiresLogin() {
        return true;
    }
}
