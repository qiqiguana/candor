package org.heal.servlet.upload;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.ContextURLBean;
import org.heal.module.metadata.ContributorBean;
import org.heal.module.metadata.CopyrightBean;
import org.heal.module.metadata.CopyrightHolderBean;
import org.heal.module.metadata.CopyrightTextBean;
import org.heal.module.metadata.FormatBean;
import org.heal.module.metadata.KeywordBean;
import org.heal.module.metadata.MetadataBean;
import org.heal.module.metadata.RelationBean;
import org.heal.module.metadata.RequirementBean;
import org.heal.module.metadata.TargetUserGroupBean;
import org.heal.module.metadata.MetametadataContributorBean;
import org.heal.module.upload.UploadServicesBean;
import org.heal.module.user.UserBean;
import org.heal.servlet.Action;
import org.heal.util.DateTools;
import org.heal.util.FileLocator;
import org.heal.util.VCardBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import org.heal.servlet.upload.AlphanumericFileRenamePolicy;

public class PreviewUploadAction implements Action {
  
  private static final FileRenamePolicy fileRenamePolicy = new AlphanumericFileRenamePolicy();
    private static final Map<String, String> mediaTypes;

    // Initializes the mediaTypes map
    static {
        Map<String, String> temp = new HashMap<String, String>();

        temp.put("pdf", "application/pdf");
        temp.put("ppt", "application/vnd.ms-powerpoint");
        temp.put("pot", "application/vnd.ms-powerpoint");
        temp.put("swf", "application/x-shockwave-flash");
        temp.put("smil", "application/smil");
        temp.put("zip", "application/zip");
        temp.put("doc", "application/msword");
        temp.put("wpd", "application/vnd.wordperfect");
        temp.put("vsd", "application/visio");
        temp.put("xls", "application/vnd.ms-excel");
        temp.put("xlm", "application/vnd.ms-excel");
        temp.put("mpp", "application/vnd.ms-project");
        temp.put("xml", "application/xml");
        temp.put("exe", "application/octet-stream");
        temp.put("js", "application/x-javascript");
        temp.put("wav", "audio/x-wav");
        temp.put("mpg", "audio/mpeg");
        temp.put("rm", "audio/x-pn-realaudio");
        temp.put("ra", "audio/x-pn-realaudio");
        temp.put("ram", "audio/x-pn-realaudio");
        temp.put("rpm", "audio/x-pn-realaudio-plugin");
        temp.put("qt", "audio/qt");
        temp.put("au", "audio/basic");
        temp.put("aif", "audio/x-aiff");
        temp.put("jpg", "Image/jpeg");
        temp.put("gif", "Image/gif");
        temp.put("png", "Image/png");
        temp.put("igs", "model/iges");
        temp.put("iges", "model/iges");
        temp.put("vrml", "model/vrml");
        temp.put("htm", "text/html");
        temp.put("html", "text/html");
        temp.put("sgm", "text/sgml");
        temp.put("sgml", "text/sgml");
        temp.put("txt", "text/plain");
        temp.put("rtf", "text/rtf");
        temp.put("xml", "text/xml");
        temp.put("mpeg", "video/mpeg");
        temp.put("mpg", "video/mpeg");
        temp.put("qt", "video/quicktime");
        temp.put("mov", "video/quicktime");

        mediaTypes = Collections.unmodifiableMap(temp);
    }


  public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException 
  {
    
    RequestDispatcher rd;
        CompleteMetadataBean completeMetadata = new CompleteMetadataBean();
        Vector errorMessages = new Vector();
        FileLocator healFileLocator = (FileLocator)servlet.getServletContext().getAttribute("healFileLocator");
        UserBean validUser = (UserBean)request.getSession().getAttribute("validUser");
        MultipartRequest multiPartReq = processUploadRequest(request, validUser, completeMetadata, healFileLocator, errorMessages);

        if(errorMessages.size() > 0) {
            //setup attributes
            setupAttributes(request, multiPartReq);
            request.setAttribute("errorMessages", errorMessages);
            String forwardURL = "../upload2/uploadform.jsp";
            rd = request.getRequestDispatcher(forwardURL);
            rd.forward(request, response);
            return;
        } else {
            UploadServicesBean uploadServices = (UploadServicesBean)servlet.getServletContext().getAttribute("uploadServices");
            //MetadataDAO metadataServices = (MetadataDAO)servlet.getServletContext().getAttribute("MetadataDAO");
            //final QueueDAO queueManager = (QueueDAO)servlet.getServletContext().getAttribute("QueueDAO");
           // final CommonDAO commonServices = (CommonDAO)servlet.getServletContext().getAttribute("CommonDAO");
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

            final String vCard = "begin:vcard\n" +
                    "n:Health Education Assets Library (HEAL)\n" +
                    "url:http://www.healcentral.org\n" +
                    "email;type=internet:info@healcentral.org \n" +
                    "fn:Health Education Assets Library (HEAL)\n" +
                    "end:vcard";
            MetametadataContributorBean mcb = new MetametadataContributorBean();
            mcb.setDate(new Date());
            mcb.setRole("Creator");
            mcb.setvCard(vCard);
            mcb.setDateDescription("Contributed to HEAL");
            completeMetadata.getMetametadataContributors().add(mcb);
            request.getSession().setAttribute("completeMetadata", completeMetadata);
            request.getSession().setAttribute("requestParameters", multiPartReq);
            response.sendRedirect("../upload2/preview.jsp");

        }  

  }
  public boolean actionRequiresLogin() {
        return true;
    }
  /**
     * Handles all processing of an upload request.  Parses out the upload
     * parameters and file and stores the information in the provided
     * CompleteMetadataBean.  Any errors that occur will result in
     * messages being added to the errorMessages vector.
     * The validUser parameter is required in order to determine where to
     * place the uploaded file.  The healFileLocator parameter is also
     * required for this reason.
     * If the errorMessages vector is empty then the complete metadata
     * bean parameter will be fully filled out and ready for storage
     * in the database.  If an errorMessage does show up, then there
     * was an error in processing and the uploaded file will be deleted
     * before this method returns.  The CompleteMetadataBean will still
     * contain as many parameters as the method was able to process, thus
     * allowing the error form to retain those settings as a convenience
     * to the user.
     * This method returns a HashMap containing all of the parameters and
     * values read from the request.  See the readRequestParameters method
     * for a description of the format of this map.
     */
    public static MultipartRequest processUploadRequest(HttpServletRequest request,
                                                        UserBean validUser,
                                                        CompleteMetadataBean cmb,
                                                        FileLocator healFileLocator,
                                                        Vector errorMessages) {
        String location;
        try {
            String userUploadDir = healFileLocator.getUploadFilePath() + "\\" + validUser.getUserId();

            File directory = new File(userUploadDir);
            if(!directory.exists()) {
                directory.mkdir();
            }

            MultipartRequest multipartReq = new MultipartRequest(request, userUploadDir, (int)healFileLocator.getMaxUploadSize(), fileRenamePolicy);
            String fn = multipartReq.getParameter("locationfile");
            if (fn.equals("File"))
            {
              location = multipartReq.getFilesystemName("fileName");           
              System.err.println("Location = " + location);
              if(location == null) {
                errorMessages.addElement("Unable to process uploaded file.");
              } else {
                location = validUser.getUserId() + "/" + location;
              }              
            }
            else
            {
              location = multipartReq.getParameter("fileName");
            }
            if(multipartReq.getParameter("contributorInfo") == null || multipartReq.getParameter("contributorInfo").length() == 0) {
                errorMessages.addElement("Please include contributor information.");
            }

            if(multipartReq.getParameter("title") == null || multipartReq.getParameter("title").length() == 0) {
                errorMessages.addElement("Please enter a title.");
            }

            if(multipartReq.getParameter("description") == null || multipartReq.getParameter("description").length() == 0) {
                errorMessages.addElement("Please enter a general description.");
            }

            if(multipartReq.getParameter("license") == null || multipartReq.getParameter("license").length() == 0) {
                errorMessages.addElement("Please select usage rights setting.");
            }

            if(multipartReq.getParameter("copyrightHolder") == null || multipartReq.getParameter("copyrightHolder").length() == 0) {
                errorMessages.addElement("Please include copyright holder information.");
            }

            Date testDate = DateTools.parse(multipartReq.getParameter("creationDate"));
            if(null == testDate) {
                errorMessages.addElement("Please provide a date in the format YYYY-MM-DD.");
            } else {
                // This is MS SQL Server-specific
                Calendar testCalendar = Calendar.getInstance();
                testCalendar.setTime(testDate);
                final int year = testCalendar.get(Calendar.YEAR);
                if(year < 1753 || year > 9999) {
                    errorMessages.addElement("The year '" +year+ "' in '" + multipartReq.getParameter("creationDate")+ "' is invalid.  Please provide a year within the range 1753 - 9999.");
                }
            }
            if(null == multipartReq.getParameter("revision")) {
                errorMessages.addElement("Please select whether or not this is a revision");
            }
            //add info to the complete metadata bean...
            storeParameters(multipartReq, validUser, location, healFileLocator, cmb);
            return multipartReq;
        } catch(IOException lEx) {
            errorMessages.addElement("An error occured while reading the submitted file.");
            lEx.printStackTrace();
            return null;
        }
    }

    /**
     * Handles all processing of an upload request.  Parses out the upload
     * parameters and file and stores the information in the provided
     * CompleteMetadataBean.  Any errors that occur will result in
     * messages being added to the errorMessages vector.
     * The validUser parameter is required in order to determine where to
     * place the uploaded file.  The healFileLocator parameter is also
     * required for this reason.
     * If the errorMessages vector is empty then the complete metadata
     * bean parameter will be fully filled out and ready for storage
     * in the database.  If an errorMessage does show up, then there
     * was an error in processing and the uploaded file will be deleted
     * before this method returns.  The CompleteMetadataBean will still
     * contain as many parameters as the method was able to process, thus
     * allowing the error form to retain those settings as a convenience
     * to the user.
     * This method returns a HashMap containing all of the parameters and
     * values read from the request.  See the readRequestParameters method
     * for a description of the format of this map.
     */
    /**
     * Reads the information contained within the parametersMap
     * and stores that information in the CompleteMetadataBean.
     * The values in the parametersMap are ArrayLists keyed off
     * of strings defined in the upload form.  These values,
     * along with the user, location, and file locator are
     * used to completely fill out the complete metadata so
     * that it is ready to be stored in the database.
     * Since this is a new upload, the cmb.metadata.hidden
     * value is set to true (we don't mark things as unhidden
     * until the item is cataloged).
     */
    private static void storeParameters(MultipartRequest multipartReq,
                                        UserBean validUser,
                                        String location,
                                        FileLocator fileLocator,
                                        CompleteMetadataBean cmb) {
        cmb.setLocation(location);
        cmb.setTitle(multipartReq.getParameter("title"));
        cmb.setContributeUserId(validUser.getUserId());
        cmb.setCreationDate(DateTools.parse(multipartReq.getParameter("creationDate")));


        java.util.Date currentTime = new java.util.Date();
        cmb.setContributeDate(currentTime);
        cmb.setAnnotated(multipartReq.getParameter("annotated"));
        cmb.setInappropriate(multipartReq.getParameter("inappropriate"));
        cmb.setPublicationName(multipartReq.getParameter("publicationName"));
        cmb.setPublicationId(multipartReq.getParameter("publicationId"));
        cmb.setSourceCollection("Peer Review Pending");
        cmb.setSubmissionAgreement(multipartReq.getParameter("submissionAgreement"));
        cmb.setDescription(multipartReq.getParameter("description"));
        cmb.setLearningResourceType(multipartReq.getParameter("learningResourceType"));
        cmb.setSpecimenType(multipartReq.getParameter("specimenType"));
        cmb.setRadiographType(multipartReq.getParameter("radiographType"));
        cmb.setOrientation(multipartReq.getParameter("orientation"));
        cmb.setMagnification(multipartReq.getParameter("magnification"));
        cmb.setClinicalHistory(multipartReq.getParameter("clinicalHistory"));
        cmb.setPrivate(true); //we start out hidden until cataloged
        if(location != null) {
            storeFileInfo(location, fileLocator, cmb);
        }
        String resource = multipartReq.getParameter("relatedItem");
        String kind = multipartReq.getParameter("relationType");
        String description = multipartReq.getParameter("relationDescription");
        if(resource != null) {
            RelationBean relation = new RelationBean();
            relation.setResource(resource);
            relation.setKind(kind);
            relation.setDescription(description);
            cmb.addRelation(relation);
        }
        
        String list[];
        String value;

        list = multipartReq.getParameterValues("copyrightHolder");
        if(list != null) {
            int a = list.length;
            for(int iter = 0; iter < a; iter++) {
                value = list[iter];
                CopyrightHolderBean ddb = new CopyrightHolderBean();
                ddb.setVCard(value); //XXX we aren't really storing a VCARD here
                cmb.addCopyrightHolder(ddb);
            }
        }
        list = multipartReq.getParameterValues("contributorInfo");
        if(list != null) {
            int a = list.length;
            for(int iter = 0; iter < a; iter++) {
                VCardBean vCard = new VCardBean();
                value = list[iter];
                ContributorBean cb = new ContributorBean();
                String[] nestedValues = value.split("\\|");
          
                if(nestedValues.length == 7)
                {
                  cb.setRole(trimString(nestedValues[0]));
                  final String lastName = trimString(nestedValues[1]);
                  cb.setLastName(lastName);
                  vCard.setLastName(lastName);
                  final String firstName = trimString(nestedValues[2]);
                  cb.setFirstName(firstName);
                  vCard.setFirstName(firstName);
                  vCard.setMiddleName(trimString(nestedValues[3]));
                  vCard.setTitle(trimString(nestedValues[4]));
                  final String org = trimString(nestedValues[5]);
                  cb.setOrganization(org);
                  vCard.setOrganization(org);
                  final String email = trimString(nestedValues[6]);
                  cb.setEmail(email);
                  vCard.setEmail(email);
                }
                else if(nestedValues.length == 8)
                {
                  cb.setRole(trimString(nestedValues[0]));
                  final String lastName = trimString(nestedValues[1]);
                  cb.setLastName(lastName);
                  vCard.setLastName(lastName);
                  final String firstName = trimString(nestedValues[2]);
                  cb.setFirstName(firstName);
                  vCard.setFirstName(firstName);
                  vCard.setMiddleName(trimString(nestedValues[3]));
                  vCard.setTitle(trimString(nestedValues[4]));
                  final String org = trimString(nestedValues[5]);
                  cb.setOrganization(org);
                  vCard.setOrganization(org);
                  final String email = trimString(nestedValues[6]);
                  cb.setEmail(email);
                  vCard.setEmail(email);
                  vCard.setPhone(trimString(nestedValues[7]));
                
                }
                else {
                    System.err.println("SimpleUploadAction: cannot parse contributor \"" +value+ "\"");
                    continue;
                }               
                String vCardString = vCard.getVCard();
                if(null != vCardString) {
                    cb.setVCard(vCardString);
                } else {
                    cb.setVCard(value); //XXX we aren't really storing a VCARD here
                }
                cmb.addContributor(cb);
            }
        }

        list = multipartReq.getParameterValues("license");
        if(list != null) {
            int a = list.length;
            for(int iter = 0; iter < a; iter++) {
                value = list[iter];
                CopyrightTextBean ddb = new CopyrightTextBean();
                ddb.setCopyrightText(value);
                CopyrightBean cb = new CopyrightBean();
                cb.setCopyrightText(ddb);
                cmb.addCopyright(cb);
            }
        }

        list = multipartReq.getParameterValues("context");
        if(list != null) {
            int a = list.length;
            for(int iter = 0; iter < a; iter++) {
                value = list[iter];
                ContextURLBean ddb = new ContextURLBean();
                ddb.setContextURL(value);
                cmb.addContextURL(ddb);
            }
        }

        list = multipartReq.getParameterValues("targetUserGroup");
        if(list != null) {
            int a = list.length;
            for(int iter = 0; iter < a; iter++) {
                value = list[iter];
                TargetUserGroupBean tugb = new TargetUserGroupBean();
                tugb.setTargetUserGroup(value);
                cmb.addTargetUserGroup(tugb);
            }
        }

        list = multipartReq.getParameterValues("browserReq");
        if(list != null) {
            int a = list.length;
            for(int iter = 0; iter < a; iter++) {
                value = list[iter];
                RequirementBean ddb = new RequirementBean();
                ddb.setRequirementType(RequirementBean.BROWSER);
                ddb.setRequirementName(value);
                cmb.addRequirement(ddb);
            }
        }

        list = multipartReq.getParameterValues("operatingReq");
        if(list != null) {
            int a = list.length;
            for(int iter = 0; iter < a; iter++) {
                value = list[iter];
                RequirementBean ddb = new RequirementBean();
                ddb.setRequirementType(RequirementBean.OS);
                ddb.setRequirementName(value);
                cmb.addRequirement(ddb);
            }
        }

        list = multipartReq.getParameterValues("keywords");
        if(list != null) {
            int a = list.length;
            for(int iter = 0; iter < a; iter++) {
                value = list[iter];
                KeywordBean ddb = new KeywordBean();
                ddb.setKeyword(value);
                cmb.addKeyword(ddb);
            }
        }

        value = location;
        value = getMediaType(value);
        FormatBean ddb = new FormatBean();
        ddb.setFormat(value);
        cmb.addFormat(ddb);
    }

    /**
     * A helper method used to store all the file information
     * in the provided metadata.  The location and fileLocator
     * are used to gain a handle on the File, at which point the
     * size, name, extension, and location are set in the
     * metadata bean.
     */
    private static void storeFileInfo(String location, FileLocator fileLocator, MetadataBean metadata) {
        if(location.startsWith("http")) {
            metadata.setFileName(location);
        } else {

            File content = new File(fileLocator.getUploadFilePath(), location);
            System.err.println(fileLocator.getUploadFilePath());
            System.err.println(location);
            if(content.exists() && content.isFile()) {
                metadata.setLocation(location);
                String name = content.getName();
                metadata.setFileName(name);
                metadata.setFileSize(String.valueOf(content.length()));
            }
        }
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
  
     public static void setupAttributes(HttpServletRequest request, MultipartRequest multiPartReq) {
        setStringAttribute(request, "mediaType", multiPartReq);
        setArrayAttribute(request, "browserReq", multiPartReq);
        setArrayAttribute(request, "operatingReq", multiPartReq);
        setStringAttribute(request, "title", multiPartReq);
        setStringAttribute(request, "description", multiPartReq);
        setStringAttribute(request, "annotated", multiPartReq);
        setArrayAttribute(request, "context", multiPartReq);
        setStringAttribute(request, "specimenType", multiPartReq);
        setStringAttribute(request, "radiographType", multiPartReq);
        setStringAttribute(request, "mriType", multiPartReq);
        setStringAttribute(request, "orientation", multiPartReq);
        setStringAttribute(request, "magnification", multiPartReq);
        setStringAttribute(request, "diseaseProcess", multiPartReq);
        setStringAttribute(request, "diseaseDiagnosis", multiPartReq);
        setStringAttribute(request, "clinicalHistory", multiPartReq);
        setStringAttribute(request, "learnContext", multiPartReq);
        setStringAttribute(request, "inappropriate", multiPartReq);
        setArrayAttribute(request, "keywords", multiPartReq);
        setStringAttribute(request, "copyrightHolder", multiPartReq);
        setArrayAttribute(request, "contributorInfo", multiPartReq);
        setStringAttribute(request, "copyrightMessage", multiPartReq);
        setStringAttribute(request, "relatedItem", multiPartReq);
        setStringAttribute(request, "relationType", multiPartReq);
        setStringAttribute(request, "relationDescription", multiPartReq);
        setStringAttribute(request, "q_1", multiPartReq);
        setStringAttribute(request, "q_2", multiPartReq);
        setStringAttribute(request, "q_3", multiPartReq);
        setStringAttribute(request, "targetUserGroup", multiPartReq);
        setStringAttribute(request, "publicationName", multiPartReq);
        setStringAttribute(request, "creationDate", multiPartReq);
        setStringAttribute(request, "learningResourceType", multiPartReq);
        setStringAttribute(request, "locationfile", multiPartReq);
        setStringAttribute(request, "fileName", multiPartReq);
    }
     /**
     * Looks up the attribute in the parametersMap, takes that setting
     * (if it isn't null) and sets an attribute in the request
     * with the given name and the discovered values.  The value
     * of the attribute is a String[] of all of the values found
     * in the parametersMap that correspond to the given attributeName
     */
    private static void setArrayAttribute(HttpServletRequest request, String attributeName, MultipartRequest multiPartReq) {
        String[] stringArray = multiPartReq.getParameterValues(attributeName);
        if(stringArray != null) {
            request.setAttribute(attributeName, stringArray);
        }
    }
    /**
     * Looks up the attribute in the parametersMap, takes that setting
     * (if it isn't null) and sets an attribute in the request
     * with the given name and the discovered value.
     */
    private static void setStringAttribute(HttpServletRequest request, String attributeName, MultipartRequest multipartReq) {
        String aString = multipartReq.getParameter(attributeName);
        if(aString != null) {
            request.setAttribute(attributeName, aString);
        }
    }
    
  /**
     * Given a complete metadata beans and two hashmaps, looks up the
     * requirements associated with the metadata and separates the
     * requirements into those related to the user's web browser and
     * those related to the user's operating system.  Each entry is
     * added to the hash map as the key, with SELECTED as the value.
     * This is intended for use in jsp pages where a select input is
     * being generated.
     */
     public static void separateRequirements(CompleteMetadataBean cmb, HashMap browserMap, HashMap operatingMap) {
        ArrayList requirements = cmb.getRequirements();
        if(requirements != null) {
            Iterator iter = requirements.iterator();
            while(iter.hasNext()) {
                RequirementBean requirement = (RequirementBean)iter.next();
                String type = requirement.getRequirementType();
                String name = requirement.getRequirementName();
                if(name != null) {
                    if(RequirementBean.BROWSER.equalsIgnoreCase(type)) {
                        browserMap.put(name, "SELECTED");
                    } else if(RequirementBean.OS.equalsIgnoreCase(type)) {
                        operatingMap.put(name, "SELECTED");
                    }
                }
            }
        }
    }

    
    

    /**
     * Determines the media file type based on the file extension (from filename)
     * If filename startsWith "http://" media file Type is web page
     */
    private static String getMediaType(String fileName) {
        int index;
        String fileExtension = null;
        if((index = fileName.lastIndexOf(".")) != -1) {
            fileExtension = fileName.substring(index + 1);
        }
        String mediaType = mediaTypes.get(fileExtension);
        if(mediaType == null) {
            mediaType = "unknown";
        }
        if(("unknown".equals(mediaTypes)) && (fileName.startsWith("http"))) {
            mediaType = "text/htm";
        }
        return mediaType;
    }

    /**
     * Trims any extra whitespace from the beginning and end of a value -- if
     * that results in an empty string, this method returns <code>null</code>.
     *
     * @param value A non-null String.
     * @return A trimmed String or null if the string is empty.
     */
    private final static String trimString(String value) {
        String ret = value.trim();
        return (ret.length() > 0 ? ret : null);
    }
}