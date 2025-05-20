package org.heal.servlet.tagcloud;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.StringTokenizer;
import org.heal.servlet.*;

public class SubmitTagsAction implements Action {

    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        String taglist;
        String metadataId;
        String userId;
        String nextToken, nextToken2;
        
        TagBean tagEntry = new TagBean();
        TagCloudDAO tagCloudRegistry = (TagCloudDAO) servlet.getServletContext().getAttribute("TagCloudDAO");

        taglist = request.getParameter("taglist");
        metadataId = request.getParameter("metadataId");
        userId = request.getParameter("userId");
  
        // separate the tag list into individual tokens.
        StringTokenizer tags = new StringTokenizer(taglist, ",");
        while (tags.hasMoreTokens()) {
            nextToken = tags.nextToken().trim();
            StringTokenizer tags2 = new StringTokenizer(nextToken, ";");
            while (tags2.hasMoreTokens()){
                nextToken2 = tags2.nextToken().trim();                
                if (nextToken2.length() != 0){
                    tagEntry.setTag(nextToken2);
                    tagEntry.setMetadataId(metadataId);
                    tagEntry.setUserId(userId);

                    try {
                        // saves the bean into database.
                        tagCloudRegistry.saveTagEntry(tagEntry);
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        response.sendRedirect("../tagcloud/tagsubmit_response.jsp?metadataId=" + metadataId);
    } 

    // login is required to view this page.
    public boolean actionRequiresLogin() {
        return true;
    }
}

