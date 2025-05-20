package org.heal.servlet;

import org.heal.module.metadata.MetadataDAO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class ListIMSXMLAction implements Action {
    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        // MetadataServicesBean metadataServices = (MetadataServicesBean) servlet.getServletContext().getAttribute("metadataServices");
        MetadataDAO metadataServices = (MetadataDAO)servlet.getServletContext().getAttribute("MetadataDAO");
        String metadataId = request.getParameter("metadataId");
        try {
            if(metadataId != null && metadataId.length() > 0) {
                PrintWriter out = response.getWriter();
                out.println(metadataServices.getIMSRecord(metadataId));
            }
        } catch(NumberFormatException ex) {
            ex.printStackTrace();
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean actionRequiresLogin() {
        return false;
    }
}
