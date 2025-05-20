package org.heal.servlet.upload;

import com.oreilly.servlet.MultipartRequest;
import org.heal.module.catalog.QueueDAO;
import org.heal.module.catalog.QueuedRecordBean;
import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.MetadataDAO;
import org.heal.module.metadata.MetametadataIdentifierBean;
import org.heal.module.upload.UploadServicesBean;
import org.heal.servlet.Action;
import org.heal.util.FileLocator;
import org.heal.util.CommonDAO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;



public class SimpleUploadAction implements Action {
   
    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        RequestDispatcher rd;     
        CompleteMetadataBean completeMetadata = (CompleteMetadataBean)request.getSession().getAttribute("completeMetadata");
        MultipartRequest multiPartReq = (MultipartRequest)request.getSession().getAttribute("requestParameters");
       
        Map parameters = new HashMap();
        for(Enumeration enumeration = multiPartReq.getParameterNames(); enumeration.hasMoreElements();) {
            final String parameterName = (String)enumeration.nextElement();
            final String[] parameterValues = multiPartReq.getParameterValues(parameterName);
            parameters.put(parameterName, parameterValues);
        }
        FileLocator healFileLocator = (FileLocator)servlet.getServletContext().getAttribute("healFileLocator");
        UploadServicesBean uploadServices = (UploadServicesBean)servlet.getServletContext().getAttribute("uploadServices");
        MetadataDAO metadataServices = (MetadataDAO)servlet.getServletContext().getAttribute("MetadataDAO");
        final QueueDAO queueManager = (QueueDAO)servlet.getServletContext().getAttribute("QueueDAO");
        final CommonDAO commonServices = (CommonDAO)servlet.getServletContext().getAttribute("CommonDAO");
        String format = completeMetadata.getFormat();
        String location = completeMetadata.getLocation();
        String converted = healFileLocator.convertLocationToFilePath(location);
        String path = healFileLocator.getUploadFilePath() + File.separator + converted;

        if((format.toLowerCase()).startsWith("Image".toLowerCase())) {
            uploadServices.processImage(completeMetadata, path);
        } else if("Video".equalsIgnoreCase(format) || "Animation".equalsIgnoreCase(format)) {
            uploadServices.processVideo(completeMetadata, path);
        } else if("Audio".equalsIgnoreCase(format)) {
            uploadServices.processAudio(completeMetadata, path);
        }
        try {
            metadataServices.saveCompleteMetadata(completeMetadata);
            // since we need a metadata id for this part...
            MetametadataIdentifierBean mib = new MetametadataIdentifierBean();
            mib.setCatalog("http://www.healcentral.org/");
            mib.setEntry(completeMetadata.getMetadataId());
            mib.setMetadataId(completeMetadata.getMetadataId());
            completeMetadata.getMetametadataIdentifiers().add(mib);
            metadataServices.saveMetametadataIdentifier(mib);
        } catch(SQLException e) {
                throw new ServletException(e);
        }
        QueuedRecordBean temp = new QueuedRecordBean();
        temp.setType(QueueDAO.TYPE_APPROVAL);
        if("yes".equalsIgnoreCase(multiPartReq.getParameter("revision"))) {
            temp.setComments("submitted as a revision");
        }
        temp.setShortMetadata(completeMetadata);
        queueManager.saveQueuedRecord(temp);
        parameters.put("publicationId", new String[]{completeMetadata.getPublicationId()});
        request.getSession().setAttribute("requestParameters", parameters);
        request.setAttribute("submissionId", completeMetadata.getPublicationId());
        rd = request.getRequestDispatcher("../upload2/uploadformSuccess.jsp");
        rd.forward(request, response);        
    }
    public boolean actionRequiresLogin() {
        return true;
    }

    /**
     * Takes information from the parametersMap and, using
     * the request.setAttribute method, places it in the request
     * such that the upload form can easily look it up and display
     * it to the user.  This method is meant to be used in the case
     * that an error occured during the upload processing (such
     * as a missing parameter) and we need the user to resubmit
     * their request.  This method sets the attributes so that
     * the previously set fields in the form can be filled in
     * and the user doesn't have to completely refill the form.
     * Because we are using an encoding type of multipart/form-data
     * to upload the form and file, the request's parameters are
     * not set (we have to read these manually), then we need to
     * set them before we forward the request to another page
     * that wants to use them.
     * If an attribute is not supposed to have multiple settings
     * such as the title or description, the attribute that is
     * set is a String, if the attribute does have multiple
     * settings, such as keywords or organ, then the attribute
     * that is set is a String array (String[]).
     * The parameters that are set are: (* denotes multiple)
     * mediaType
     * browserReq*
     * operatingReq*
     * title
     * description
     * annotated
     * context*
     * specimenType
     * radiographType
     * mriType
     * orientation
     * magnification
     * diseaseProcess
     * diseaseDiagnosis
     * clinicalHistory
     * learnContext
     * inappropriate
     * keywords*
     * organ*
     * copyrightHolder
     * copyrightMessage
     * relatedItem
     * relationType
     * relationDescription
     */
    public static void setupAttributes(HttpServletRequest request, Map parameters) {
        setStringAttribute(request, "title", parameters);
        setStringAttribute(request, "description", parameters);
        setStringAttribute(request, "annotated", parameters);
        setArrayAttribute(request, "context", parameters);
        setStringAttribute(request, "specimenType", parameters);
        setStringAttribute(request, "radiographType", parameters);
        setStringAttribute(request, "mriType", parameters);
        setStringAttribute(request, "orientation", parameters);
        setStringAttribute(request, "magnification", parameters);
        setStringAttribute(request, "diseaseProcess", parameters);
        setStringAttribute(request, "diseaseDiagnosis", parameters);
        setStringAttribute(request, "clinicalHistory", parameters);
        setStringAttribute(request, "learnContext", parameters);
        setStringAttribute(request, "inappropriate", parameters);
        setArrayAttribute(request, "keywords", parameters);
        setStringAttribute(request, "copyrightHolder", parameters);
        setArrayAttribute(request, "contributorInfo", parameters);
        setStringAttribute(request, "copyrightMessage", parameters);
        setStringAttribute(request, "relatedItem", parameters);
        setStringAttribute(request, "relationType", parameters);
        setStringAttribute(request, "relationDescription", parameters);
        setStringAttribute(request, "q_1", parameters);
        setStringAttribute(request, "q_2", parameters);
        setStringAttribute(request, "q_3", parameters);
        setArrayListAttribute(request, "targetUserGroup", parameters);
        setStringAttribute(request, "publicationName", parameters);
        setStringAttribute(request, "creationDate", parameters);
        setStringAttribute(request, "learningResourceType", parameters);
        setStringAttribute(request, "revision", parameters);
        setStringAttribute(request, "publicationId", parameters);
        setStringAttribute(request, "locationfile", parameters);
        setStringAttribute(request, "fileName", parameters);
    }
      /**
     * Looks up the attribute in the parametersMap, takes that setting
     * (if it isn't null) and sets an attribute in the request
     * with the given name and the discovered values.  The value
     * of the attribute is a String[] of all of the values found
     * in the parametersMap that correspond to the given attributeName
     */
      private static void setArrayListAttribute(HttpServletRequest request, String attributeName, Map parameters) {
        String[] stringArray = (String[])parameters.get(attributeName);
        if(stringArray == null) {
            return;
        }
        ArrayList aList = new ArrayList();
        for(int a = 0; a < stringArray.length; a++) {
            aList.add(stringArray[a]);
        }
        request.setAttribute(attributeName, aList);
    }

   /**
     * Looks up the attribute in the parametersMap, takes that setting
     * (if it isn't null) and sets an attribute in the request
     * with the given name and the discovered value.
     */
     private static void setStringAttribute(HttpServletRequest request, String attributeName, Map parameters) {
        String[] temp = (String[])parameters.get(attributeName);
        if(null != temp && temp.length > 0) {
            request.setAttribute(attributeName, temp[0]);
        }
    }

    /**
     * Looks up the attribute in the parametersMap, takes that setting
     * (if it isn't null) and sets an attribute in the request
     * with the given name and the discovered values.  The value
     * of the attribute is a String[] of all of the values found
     * in the parametersMap that correspond to the given attributeName
     */
    private static void setArrayAttribute(HttpServletRequest request, String attributeName, Map parameters) {
        String[] stringArray = (String[])parameters.get(attributeName);
        request.setAttribute(attributeName, stringArray);
    }
}