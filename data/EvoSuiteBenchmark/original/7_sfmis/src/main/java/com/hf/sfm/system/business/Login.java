package com.hf.sfm.system.business;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import com.hf.sfm.util.DaoFactory;
import com.hf.sfm.util.HibernateSessionFactory;

public class Login extends HttpServlet {
	

	/**
	 * Constructor of the object.
	 */
	public Login() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	@SuppressWarnings("deprecation")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        String rtn="0";
        String roleInfo="";
        DaoFactory dao = new DaoFactory();
		String account = request.getParameter("account");
		String psw = request.getParameter("psw");
		Session se = HibernateSessionFactory.currentSession();
		String sql = "select account,groupid,name,state from a_worker A left join a_person_info B on A.personid=B.personid where account=? and password=?";
		List user = se.createSQLQuery(sql).setParameter(0, account).setParameter(1,dao.encrypt(psw)).list();
		if(user.size()>0){
			for(Iterator it = user.iterator();it.hasNext();){
				Object[] obj = (Object[]) it.next();
				if(obj[3].equals("1")){
					rtn="2";
					break;
				}else{
					roleInfo="{userAccount:'"+obj[0]+"',"+
					         "groupid:'"+obj[1]+"',"+
					         "username:'"+obj[2]+"'}";
					rtn="1";
				}
			}
		}
		HttpSession session = request.getSession();
		session.setAttribute("rtn", rtn);
		session.setAttribute("roleInfo", roleInfo);
		response.sendRedirect("sfmindex.jsp");
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
