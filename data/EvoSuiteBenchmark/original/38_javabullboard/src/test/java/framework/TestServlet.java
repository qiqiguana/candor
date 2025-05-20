package framework;
import java.io.PrintWriter;
import java.io.IOException;
import framework.util.FileUtils;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet 
{
  private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

  public void init(ServletConfig config) throws ServletException
  {
    super.init(config);
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    try {
    String root = getServletContext().getRealPath("/");
    root += "/WEB-INF/";
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head><title>TestServlet</title></head>");
    out.println("<body>");
    out.println("<p>"+root+"</p>");
    Collection result = FileUtils.listFiles(root, null, false);
    Iterator it = result.iterator();
    while (it.hasNext()) out.println("<p>"+it.next()+"</p>");
    out.println("</body></html>");
    out.close();
    } catch (Exception e) 
    {
      e.printStackTrace();
    }
  }
}