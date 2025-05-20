package org.heal.servlet;

import org.heal.module.browse.BrowseDAO;
import org.heal.module.browse.BrowseResultsBean;
import org.heal.module.metadata.MetadataDAO;
import org.heal.module.metadata.ShortMetadataBean;
import org.heal.module.metadata.ThumbnailBean;
import org.heal.util.FileLocator;
import org.heal.util.InterfaceUtilitiesBean;
import org.heal.util.ParameterMap;
import org.heal.util.ResultsPager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

/**
 * This {@link Action} handles the browse request
 *
 * @author Jason Varghese
 * @modify Brad Schaefer
 */

public class BrowseAction implements Action {
    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        try {
            BrowseDAO browseServices = (BrowseDAO)servlet.getServletContext().getAttribute("browseDAO");
            String pid = request.getParameter("pid");
            Vector categories;
            Vector trail;
            Vector results;
            BrowseResultsBean browseResultsBean;
            browseResultsBean = (BrowseResultsBean)request.getSession().getAttribute("results");
            MetadataDAO metadataServices = (MetadataDAO)servlet.getServletContext().getAttribute("MetadataDAO");
            if(pid == null || pid.length() < 1) {
                pid = "0";
            }
            if((browseResultsBean != null) && (browseResultsBean.getId().equals(pid))) {
                trail = browseResultsBean.getTrail();
                categories = browseResultsBean.getSubCategories();
                results = browseResultsBean.getResultsId();
            } else {
                trail = browseServices.getTrail(pid);
                categories = browseServices.getChildrenCategories(pid);
                results = browseServices.doBrowse(pid);
                if(trail == null) {
                    trail = new Vector();
                }
                request.getSession().removeAttribute("results");
                browseResultsBean = new BrowseResultsBean();
                browseResultsBean.setTrail(trail);
                browseResultsBean.setId(pid);
                browseResultsBean.setSubCategories(categories);
                browseResultsBean.setResultsId(results);
                request.getSession().setAttribute("results", browseResultsBean);
            }
            
            final int currentSection = getParameterAsInteger(request, "currentSection", 0);
            // display specifies how many items are being displayed per page, defaultly 25
            final int displayIncrement = getParameterAsInteger(request, "display", 25);
            final int previousSection = currentSection - 1;
            int nextSection = currentSection + 1;
            final int lowerbound = currentSection * displayIncrement;
            int upperbound = nextSection * displayIncrement;

            if(upperbound > results.size()) {
                upperbound = results.size();
                nextSection = -1;
            }

            Vector smbs = null;
            if((results != null) && (results.size() > 0)) {
                smbs = new Vector();
                InterfaceUtilitiesBean interfaceUtilities = (InterfaceUtilitiesBean)servlet.getServletContext().getAttribute("interfaceUtilities");
                FileLocator healFileLocator = (FileLocator)servlet.getServletContext().getAttribute("healFileLocator");
                for(int b = lowerbound; b < upperbound; b++) {
                    final String id = (String)results.get(b);
                    if(id != null) {
                        final ShortMetadataBean smb = metadataServices.getShortMetadata(id);
                        String temp = smb.getDescription();
                        if(temp != null && temp.length() > 0) {
                            final int limit = (100 > temp.length() ? temp.length() : 100);
                            smb.setDescription(temp.substring(0, limit));
                        }
                        ThumbnailBean tbean = interfaceUtilities.getThumbnail(smb.getThumbnail(), smb.getFormat(), "../");
                        smb.setThumbnail(tbean);
                        smb.setLocation(healFileLocator.getContentURL(smb.getLocation()));
                        smbs.add(smb);
                    }
                }
            }
            // page specifies the page we are looking at, defaultly page 1
            final int displayPage = getParameterAsInteger(request, "page", 1);
            final String pagenb = String.valueOf(displayPage);
            ResultsPager pager = new ResultsPager(results.size(), displayIncrement, displayPage);
            request.setAttribute("pager", pager);

            //**** BEGIN code to calculate displayStart and displayStop
            int displayStart = (displayPage - 1) * displayIncrement;
            int displayStop = 0;
            if(results != null) {
                displayStop = displayStart + displayIncrement;
                if(displayStop > results.size()) {
                    displayStop = results.size();
                }
            }
            //**** END code to calculate displayStart and displayStop
            request.setAttribute("displayStart", String.valueOf(displayStart));
            request.setAttribute("displayStop", String.valueOf(displayStop));

            {
                ParameterMap parameters = new ParameterMap(request.getParameterMap());
                // If they're changing the display size, we must reset the page
                parameters.remove("page");

                // Disregarding what "display" value may be in the current parameters
                // map, we put in "25" and construct a new query string to represent
                // the original query string with a "display" of "25"
                parameters.put("display", "10");
                parameters.put("page", pagenb);
                request.setAttribute("displayString10", "browse?" + parameters.toString());

                // Disregarding what "display" value may be in the current parameters
                // map, we put in "25" and construct a new query string to represent
                // the original query string with a "display" of "25"
                parameters.put("display", "25");
                parameters.put("page", pagenb);
                request.setAttribute("displayString25", "browse?" + parameters.toString());

                // Disregarding what "display" value may be in the current parameters
                // map, we put in "100" and construct a new query string to represent
                // the original query string with a "display" of "100"
                parameters.put("display", "50");
                parameters.put("page", pagenb);
                request.setAttribute("displayString50", "browse?" + parameters.toString());
            }
            String origURL = "../healapp/browse?" + request.getQueryString();
            request.setAttribute("origURL", origURL);
            request.setAttribute("startNumber", String.valueOf(lowerbound + 1));
            request.setAttribute("smbs", smbs);
            request.setAttribute("trail", trail);
            request.setAttribute("categories", categories);
            request.setAttribute("pid", pid);

            if(previousSection >= 0) {
                request.setAttribute("previousSection", Integer.toString(previousSection));
            }
            if(currentSection >= 0) {
                request.setAttribute("currentSection", Integer.toString(currentSection));
            }
            if(nextSection >= 0) {
                request.setAttribute("nextSection", Integer.toString(nextSection));
            }
            RequestDispatcher rd;
            rd = request.getRequestDispatcher("/browse/browse2.jsp");
            rd.forward(request, response);
        } catch(SQLException ex) {
        }
    }

    private int getParameterAsInteger(final HttpServletRequest request, final String paramName, final int defaultValue) {
        int ret = defaultValue;
        String parameter = request.getParameter(paramName);
        if(parameter != null && parameter.length() != 0) {
            try {
                ret = Integer.parseInt(parameter);
            } catch(NumberFormatException e) {
            } // does nothing, so the defaultValue will be returned
        }
        return ret;
    }

    public boolean actionRequiresLogin() {
        return false;
    }
}
