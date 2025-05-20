package org.heal.servlet.admin;
import java.io.IOException;
import java.util.Vector;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.heal.servlet.*;
import org.heal.util.CommonDAO;

public class AdminAction implements Action {
	public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
    String action = "";
    String metadataId="";
    action = request.getParameter("action");
    metadataId = request.getParameter("metadataId");
    CommonDAO common = (CommonDAO)servlet.getServletContext().getAttribute("CommonDAO");
    if (common==null)
      System.err.println("common null");
    common.deleteMetadata(metadataId);
    RequestDispatcher rd;
    Vector message = new Vector();
    message.addElement("Item deleted.  To delete another item please enter metadataID and press submit.");
    request.setAttribute("messages",message);
    rd = request.getRequestDispatcher("../admins/delete.jsp");
    rd.forward(request, response);
    return;
    }

    public boolean actionRequiresLogin() {
      return false;
	}
}
