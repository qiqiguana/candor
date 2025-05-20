package org.heal.servlet.admin;



import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.Vector;
import org.heal.module.metadata.MetadataDAO;
import org.heal.module.metadata.TaxonBean;
import org.heal.module.metadata.TaxonPathBean;
import org.heal.module.user.InstructionalLevelBean;
import org.heal.module.user.ProfessionalRoleBean;
import org.heal.module.user.UserBean;
import org.heal.servlet.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.heal.module.user.UserRegistryBean;
import org.heal.util.AuthenticationTools;


public class GenerateReportAction implements Action {
	public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		// doo something
		String type = request.getParameter("type");
    UserRegistryBean userRegistry = (UserRegistryBean) servlet.getServletContext().getAttribute("userRegistry");
    MetadataDAO metadataDao = (MetadataDAO) servlet.getServletContext().getAttribute("MetadataDAO");

		if(!AuthenticationTools.isAdministrator(request)) {
			// The user does not have the access to view this page
			// so we go no further, and redirect them to an access denied page
			// TODO instead of hardcoding page locations, this should be in config files somewhere
			response.sendRedirect("/error/accessDenied.jsp");
			return;
		}
	
		if("sample".equals(type))
		{
			PrintWriter out = response.getWriter();
      out.println("sampleData72");
		}

		if("userCount".equals(type))
		{
			int count = 0;
			try{
				count = userRegistry.getCountOfUsers();
				PrintWriter out = response.getWriter();
				out.println(count);
			}
			catch (SQLException se)
			{
				se.toString();
			}
		}
    
    
		if("getUsers".equals(type))
		{
			try{

				PrintWriter out = response.getWriter();      
				HashMap users = new HashMap();
        //UserRegistryBean userRegistry = (UserRegistryBean)request.getSession().getAttribute("userRegistry");
        Vector userNames = new Vector();
        userNames = userRegistry.getAllUserIds();
        Iterator it = null;
        it = userNames.iterator();
        UserBean userBean = new UserBean();
        out.println("UserId|Email|First Name|Last Name|Institution|City|State|Country|Area of Expertise|Instructional Level|Professional Role");
          for(int a=0;a<userNames.size();a++){
					String userId = null;
          userId = (String) userNames.get(a);
            userBean = userRegistry.getUserFromID(userId);
          if(userBean!=null){
            out.print(userId+"|");          
            out.print(userBean.getEmail()+"|");
            out.print(userBean.getFirstName()+"|");
            out.print(userBean.getLastName()+"|");
            out.print(userBean.getInstitutionName()+"|");
            out.print(userBean.getCity()+"|");
            out.print(userBean.getState()+"|");
            out.print(userBean.getCountry()+"|");
            out.print(userBean.getProfessionalSpecialty()+"|");
            
            InstructionalLevelBean ilb = null;
            ArrayList ilbs = null;
            ilbs = userBean.getInstructionalLevel();
            it = null;
            it = ilbs.iterator();
            while(it.hasNext()){
              ilb = (InstructionalLevelBean) it.next();
              if(it.hasNext())
                out.print(ilb.getInstructionalLevel()+"/");
              else
                out.print(ilb.getInstructionalLevel());
            }           
            out.print("|");
            ProfessionalRoleBean prb = null;
            ArrayList prbs = null;
            prbs = userBean.getProfessionalRole();
            it = null;
            it = prbs.iterator();
            int loopCount = 0;
            while(it.hasNext()){
              prb = (ProfessionalRoleBean) it.next();
              if(it.hasNext())
                out.print(prb.getProfessionalRole()+"/");
              else
                out.print(prb.getProfessionalRole());
            }            
            out.print("\n");    
          }
        }
			}
			catch (SQLException se)
			{
				se.toString();
			}
  
		}

    if("getAreaofExpertise".equals(type)){
      try{
				PrintWriter out = response.getWriter();      
				HashMap users = new HashMap();
        //UserRegistryBean userRegistry = (UserRegistryBean)request.getSession().getAttribute("userRegistry");
        Vector userNames = new Vector();
        userNames = userRegistry.getAreaofExpertiseValues();
        Iterator it = null;
        it = userNames.iterator();
        out.println("Area of Expertise|Count");
          for(int a=0;a<userNames.size();a++){
            out.print(userNames.get(a)+"|");
            out.println(userRegistry.getAreaofExpertiseSummary((String)userNames.get(a)));
          }
        } 
			catch(SQLException se){
				se.printStackTrace();
			}
		}     
    
    
		
		if("detailedViewAccessReport".equals(type))
		{
			try{
				PrintWriter out = response.getWriter();
				BufferedReader fr = new BufferedReader(new FileReader(servlet.getServletContext().getRealPath("/") +"../private/logs/" +"detailedViewAccess.txt"));
				HashMap users = new HashMap();
        //UserRegistryBean userRegistry = (UserRegistryBean)request.getSession().getAttribute("userRegistry");
        UserBean userBean = new UserBean();
        String str = new String();
        int c;
				char ch[] = new char[100];
				//while ((c = (fr.read(ch))) != -1)
        out.println("Email|First Name|Last Name|Instition|City|State|Country|Area of Expertise|Instructional Level|Professional Role|UserId|MetadataId|SessionId|Date");
       
				while ((str=fr.readLine())!=null)
        {
					String userId = null;
          String metadataId = null;
          StringTokenizer st = new StringTokenizer(str,"|");
          if(st!=null&&st.hasMoreTokens())
            userId = st.nextToken();
          if(st!=null&&st.hasMoreTokens())
            metadataId = st.nextToken();            
          ArrayList taxonPathBeans = new ArrayList();  
          try{  
            taxonPathBeans = metadataDao.getTaxonPaths(metadataId);
            userBean = userRegistry.getUserFromID(userId);
          }
          catch (SQLException se)
          {
            se.printStackTrace(); 
          }
          if(userBean!=null){
            out.print(userBean.getEmail()+"|");
            out.print(userBean.getFirstName()+"|");
            out.print(userBean.getLastName()+"|");
            out.print(userBean.getInstitutionName()+"|");
            out.print(userBean.getCity()+"|");
            out.print(userBean.getState()+"|");
            out.print(userBean.getCountry()+"|");
            out.print(userBean.getProfessionalSpecialty()+"|");
            
            InstructionalLevelBean ilb = null;
            ArrayList ilbs = null;
            ilbs = userBean.getInstructionalLevel();
            Iterator it = null;
            it = ilbs.iterator();
            while(it.hasNext()){
              ilb = (InstructionalLevelBean) it.next();
              if(it.hasNext())
                out.print(ilb.getInstructionalLevel()+"/");
              else
                out.print(ilb.getInstructionalLevel());
            }           
            out.print("|");
            ProfessionalRoleBean prb = null;
            ArrayList prbs = null;
            prbs = userBean.getProfessionalRole();
            it = null;
            it = prbs.iterator();
            int loopCount = 0;
            while(it.hasNext()){
              prb = (ProfessionalRoleBean) it.next();
              if(it.hasNext())
                out.print(prb.getProfessionalRole()+"/");
              else
                out.print(prb.getProfessionalRole());
            }

            out.print("|");
            
            TaxonPathBean tpb = null;
            SortedSet taxonPaths = null;
            TaxonBean tb = null;
            Iterator it2 = null;
            it = null;
            boolean firstItem = true;
            it = taxonPathBeans.iterator();
            while(it.hasNext()){
              tpb = (TaxonPathBean)it.next();
              if (tpb.getSource().equalsIgnoreCase("mesh")){
                taxonPaths = tpb.getTaxons();
                it2 = taxonPaths.iterator();
                while(it2.hasNext()){
                  tb = (TaxonBean) it2.next();
                    if(firstItem){
                      out.print(tb.getEntry());
                      firstItem = false;
                    }  
                    else
                      out.print("/"+tb.getEntry());
                }
              }
            }
          }
          out.println("|"+str);
        }
				fr.close();
			}
			catch (FileNotFoundException se){
				se.toString();
      }
    }

		if("detailedViewSurveyReport".equals(type))
		{
			try{
				PrintWriter out = response.getWriter();
				BufferedReader fr = new BufferedReader(new FileReader(servlet.getServletContext().getRealPath("/") +"../private/logs/" +"detailedViewSurvey.txt"));
				//FileReader fr = new FileReader(new File(servlet.getServletContext().getRealPath("/") +"../private/logs/" +"detailedViewAccess.txt"));
				int c;
        UserBean userBean = new UserBean();
        String str = new String();        
				char ch[] = new char[100];
        out.println("Email|First Name|Last Name|Instition|City|State|Country|Area of Expertise|Instructional Level|Professional Role|MetadataId|UserId|Rating|User|Comment");
        
				while ((str=fr.readLine())!=null)
        {
					String userId = null;
          StringTokenizer st = new StringTokenizer(str,"|");
          if(st!=null&&st.hasMoreTokens())
            userId = st.nextToken();
          System.err.println("userId="+userId);  
          try{  
            userBean = userRegistry.getUserFromID(userId);
          }
          
          catch (SQLException se)
          {
            se.printStackTrace(); 
          }
          if(userBean!=null){
            out.print(userBean.getEmail()+"|");
            out.print(userBean.getFirstName()+"|");
            out.print(userBean.getLastName()+"|");
            out.print(userBean.getInstitutionName()+"|");
            out.print(userBean.getCity()+"|");
            out.print(userBean.getState()+"|");
            out.print(userBean.getCountry()+"|");
            out.print(userBean.getProfessionalSpecialty());
            
            InstructionalLevelBean ilb = null;
            ArrayList ilbs = null;
            ilbs = userBean.getInstructionalLevel();
            Iterator it = null;
            it = ilbs.iterator();
            while(it.hasNext()){
              ilb = (InstructionalLevelBean) it.next();
              if(it.hasNext())
                out.print(ilb.getInstructionalLevel()+"/");
              else
                out.print(ilb.getInstructionalLevel());
            }           
            out.print("|");
            ProfessionalRoleBean prb = null;
            ArrayList prbs = null;
            prbs = userBean.getProfessionalRole();
            it = null;
            it = prbs.iterator();
            int loopCount = 0;
            while(it.hasNext()){
              prb = (ProfessionalRoleBean) it.next();
              if(it.hasNext())
                out.print(prb.getProfessionalRole()+"/");
              else
                out.print(prb.getProfessionalRole());
            }            
            
          }
          out.println("|"+str);
					//ch = new char[100];
				}
				fr.close();
			}
			catch (FileNotFoundException se)
			{
				se.toString();
			}
		}		

    if("getUser".equals(type))
    {
      try{
        String email = request.getParameter("email");
        UserBean user = new UserBean();
        
        user = userRegistry.getUserFromEmail(email);
        
        String emailValidationPath = "dev.healcentral.org"+"/healapp/emailValidation?emailId="+user.getUserId()+"&email="+user.getEmail();
        
        PrintWriter out = response.getWriter();
        out.println("Firstname="+user.getFirstName()+"<Br><br>");
        out.println("Validated="+user.isEmailValidated()+"<br><br>");
        out.println("Validation Link = "+emailValidationPath+"<br><br>");
        
      }
      catch(SQLException se)
      {
        se.printStackTrace();
      }
    }


	}
	public boolean actionRequiresLogin() {
		return false;
	}


}
