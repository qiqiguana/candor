package org.heal.servlet;

import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.FormatBean;
import org.heal.module.metadata.MetadataDAO;
import org.heal.module.metadata.RequirementBean;
import org.heal.module.metadata.ThumbnailBean;
import org.heal.module.metadata.TargetUserGroupBean;
import org.heal.util.FileLocator;
import org.heal.util.InterfaceUtilitiesBean;
import org.heal.servlet.userreview.*;
import org.heal.servlet.tagcloud.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class ShowMetadataAction implements Action {
    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        final MetadataDAO metadataServices = (MetadataDAO)servlet.getServletContext().getAttribute("MetadataDAO");
        final InterfaceUtilitiesBean interfaceUtilities = (InterfaceUtilitiesBean)servlet.getServletContext().getAttribute("interfaceUtilities");
        final FileLocator healFileLocator = (FileLocator)servlet.getServletContext().getAttribute("healFileLocator");
        CompleteMetadataBean completeMetadata;

        final String metadataId = request.getParameter("metadataId");

        final DetailedViewAccessAction dtvaa = new DetailedViewAccessAction();
        dtvaa.perform(servlet, request, response);

        //added by Zhen 2/24/08
        final UserReviewDAO userReviewRegistry = (UserReviewDAO)servlet.getServletContext().getAttribute("UserReviewDAO");

        //UserReviewBean userReviewEntry;
        ArrayList userReviewArray = new ArrayList();
        int userRatingAVG = 0;
        
        //added by Zhen 5/21/08
        final TagCloudDAO tagRegistry = (TagCloudDAO)servlet.getServletContext().getAttribute("TagCloudDAO");
        ArrayList tagArray = new ArrayList();
				
        try {
            userReviewArray = userReviewRegistry.getUserReview(metadataId, "approved", "reviewDate");	
            userRatingAVG = userReviewRegistry.getUserRatingAVG(metadataId);	
            tagArray = tagRegistry.getTags(metadataId);
            completeMetadata = metadataServices.getCompleteMetadata(metadataId);

            String format = completeMetadata.getFormat();
            if(format == null) {
                format = "unknown";
                FormatBean ddb = new FormatBean();
                ddb.setFormat(format);
                ArrayList formats = new ArrayList();
                formats.add(ddb);
                completeMetadata.setFormats(formats);
            }

            ThumbnailBean thumbnail = interfaceUtilities.getThumbnail(completeMetadata.getThumbnail(), format, "../");
            completeMetadata.setThumbnail(thumbnail);

            if(completeMetadata.isCataloged()) {
                completeMetadata.setLocation(healFileLocator.getContentURL(completeMetadata.getLocation()));
            } else {
                completeMetadata.setLocation(healFileLocator.getUploadURL(completeMetadata.getLocation()));
            }

            completeMetadata.setFileSize(NumberFormat.getInstance().format(Long.parseLong(completeMetadata.getFileSize())));

            // Seperates requirements into List<String>s that are easy to
            // display in the interface
            final List<String> osRequirements = new ArrayList<String>();
            final List<String> browserRequirements = new ArrayList<String>();
            final Iterator iter = completeMetadata.getRequirements().iterator();
            while(iter.hasNext()) {
                RequirementBean requirement = (RequirementBean)iter.next();
                final String type = requirement.getRequirementType();
                final String name = requirement.getRequirementName();
                if("Operating System".equalsIgnoreCase(type)) {
                    if(!"None".equalsIgnoreCase(name)) {
                        osRequirements.add(name);
                    }
                } else if("Web Browser".equalsIgnoreCase(type)) {
                    if(!"Any".equalsIgnoreCase(name)) {
                        browserRequirements.add(name);
                    }
                }
            }
            request.setAttribute("osRequirements", osRequirements);
            request.setAttribute("browserRequirements", browserRequirements);
        } catch(SQLException e) {
            throw new ServletException(e);
        }

        // Target User Groups are put into alphabetical order
        Collections.sort(completeMetadata.getTargetUserGroups(), new Comparator() {
            public int compare(Object o, Object o1) {
                TargetUserGroupBean tug1 = (TargetUserGroupBean)o;
                TargetUserGroupBean tug2 = (TargetUserGroupBean)o1;
                return tug1.getTargetUserGroup().compareTo(tug2.getTargetUserGroup());
            }
        });

        String origURL = "../healapp/showMetadata?" + request.getQueryString();
        request.setAttribute("origURL", origURL);
        request.setAttribute("completeMetadata", completeMetadata);
        request.setAttribute("userReviewArray", userReviewArray);
        request.setAttribute("userRatingAVG", userRatingAVG);
        request.setAttribute("tagArray", tagArray);
        
        RequestDispatcher rd;
        rd = request.getRequestDispatcher("/metadata/detail.jsp");
        rd.forward(request, response);
    }

    public boolean actionRequiresLogin() {
        return false;
    }
}
