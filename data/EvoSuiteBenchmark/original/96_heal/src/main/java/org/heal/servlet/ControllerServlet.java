package org.heal.servlet;

import com.ora.jsp.sql.DataSourceWrapper;

import java.sql.SQLException;
import org.heal.module.admin.AdministrationDAO;
import org.heal.module.browse.BrowseDAO;
import org.heal.module.catalog.QueueDAO;
import org.heal.module.download.DownloadDAO;
import org.heal.module.metadata.MetadataDAO;
import org.heal.module.notice.NotificationServicesBean;
import org.heal.module.search.AdvSearchDAO;
import org.heal.module.search.ReSearchDAO;
// import org.heal.module.search.ConceptMappingDAO;
import org.heal.module.search.SimpleSearchDAO;
import org.heal.module.upload.UploadServicesBean;
import org.heal.module.user.UserRegistryBean;
import org.heal.servlet.admin.AdminAction;
import org.heal.servlet.admin.GenerateReportAction;
import org.heal.servlet.admin.LinkCheckerAction;
import org.heal.servlet.registration.ModifyRegistrationAction;
import org.heal.servlet.registration.RegistrationAction;
import org.heal.servlet.registration.UpdateRegistrationAction;
import org.heal.servlet.approver.ModifyApprovalQueueEntryAction;
import org.heal.servlet.approver.ShowApprovalQueueAction;
import org.heal.servlet.cataloger.CancelEditMetadataAction;
import org.heal.servlet.cataloger.CreateMetadataAction;
import org.heal.servlet.cataloger.EditMetadataAction;
import org.heal.servlet.cataloger.ModifyCatalogQueueEntryAction;
import org.heal.servlet.cataloger.ModifyMetadataAction;
import org.heal.servlet.cataloger.SaveControlledVocabularyMultipleAction;
import org.heal.servlet.cataloger.ShowCatalogQueueAction;
import org.heal.servlet.search.AdvSearchAction;
import org.heal.servlet.search.EditAdvSearchAction;
import org.heal.servlet.search.ResearchAction;
import org.heal.servlet.search.RefineSearchAction;
import org.heal.servlet.search.SearchResultsAction;
import org.heal.servlet.search.SimpleSearchAction;
import org.heal.servlet.search.SortResultsAction;
import org.heal.servlet.search.VarSearchAction;
import org.heal.servlet.upload.ShowUploadFormAction;
import org.heal.servlet.upload.SimpleUploadAction;
import org.heal.servlet.upload.PreviewUploadAction;
import org.heal.util.CommonDAO;
import org.heal.util.FileLocator;
import org.heal.util.InterfaceUtilitiesBean;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Hashtable;

import org.heal.servlet.userreview.SubmitUserReviewAction;
import org.heal.servlet.userreview.UserReviewDAO;
import org.heal.servlet.userreview.WriteUserReviewAction;
import org.heal.servlet.userreview.ShowUserReviewAction;
import org.heal.servlet.userreview.ShowAllUserReviewAction;
import org.heal.servlet.userreview.ShowTop10Action;
import org.heal.servlet.userreview.ApproveUserReviewAction;

import org.heal.servlet.tagcloud.TagCloudDAO;
import org.heal.servlet.tagcloud.InputTagsFormAction;
import org.heal.servlet.tagcloud.SubmitTagsAction;
import org.heal.servlet.tagcloud.ShowTopTagsAction;
/**
 * This class acts as a controller for the HEAL project.  It
 * provides support functionality for initialization and cleanup of
 * major application level components such as database connectivity.
 *
 * @author Seth Wright
 * @version 0.1
 *          Modify by Grace: Added DAO instead of MetadataDB,
 *          Added SimpleSearchAction,ResearchAction,SortResultsAction,
 */

public class ControllerServlet extends HttpServlet {
    Hashtable actions = null;

    /**
     * Creates the application scope objects used by
     * JSP pages in this application.
     */
    public void init() throws ServletException {
        DataSource ds;

        ServletConfig config = getServletConfig();
        String jdbcDriverClassName = config.getInitParameter("jdbcDriverClassName");
        String jdbcURL = config.getInitParameter("jdbcURL");
        String jdbcUser = config.getInitParameter("jdbcUser");
        String jdbcPassword = config.getInitParameter("jdbcPassword");
        String uploadPath = config.getInitParameter("healUploadPath");
        String thumbnailPath = config.getInitParameter("healThumbnailPath");
        String contentPath = config.getInitParameter("healContentPath");
        String packagePath = config.getInitParameter("healPackagePath");
        String basePath = config.getInitParameter("healBaseFilePath");
        String baseURL = config.getInitParameter("healBaseURL");
        String healAddress = config.getInitParameter("healEmailAddress");
        String healMailHost = config.getInitParameter("healMailHost");
        String healMailUser = config.getInitParameter("healMailUser");
        String healMailPassword = config.getInitParameter("healMailPassword");
        String smtpServer = config.getInitParameter("smtpServer");
        String xmlFeedback = config.getInitParameter("xmlFeedback");

        if(jdbcURL == null) {
            jdbcURL = "jdbc:odbc:heal";
        }
        if(jdbcDriverClassName == null) {
            jdbcDriverClassName = "sun.jdbc.odbc.JdbcOdbcDriver";
        }
        if(jdbcUser == null) {
            jdbcUser = "heal";
        }
        if(jdbcPassword == null) {
            jdbcPassword = "heal123";
        }
        if(uploadPath == null) {
            uploadPath = FileLocator.DEFAULT_UPLOAD_DIRECTORY;
        }
        if(thumbnailPath == null) {
            thumbnailPath = FileLocator.DEFAULT_THUMBNAIL_DIRECTORY;
        }
        if(contentPath == null) {
            contentPath = FileLocator.DEFAULT_CONTENT_DIRECTORY;
        }
        if(packagePath == null) {
            packagePath = FileLocator.DEFAULT_PACKAGE_DIRECTORY;
        }
        InternetAddress healInternetAddress;
        try {
            if(healAddress != null) {
                healInternetAddress = new InternetAddress(healAddress);
            } else {
                healInternetAddress = null;
            }
        } catch(AddressException ex) {
            ex.printStackTrace();
            throw new ServletException("Unable to parse the configured heal " +
                    "address.  The Notification system " +
                    "cannot function without a properly " +
                    "configured heal email address.  The " +
                    "error encountered was:" + ex.toString());
        }
        ServletContext context = getServletContext();
        try {
            // use the DataSourceWrapper from the O'Reilly JSP library
            ds = new DataSourceWrapper(jdbcDriverClassName, jdbcURL, jdbcUser, jdbcPassword);
        } catch(Exception e) {
            throw new ServletException("Unable to initialize HEAL application" +
                    " due to an inability to open a " +
                    " connection to the database.  Reason:" +
                    e.toString());
        }
        if(ds == null) {
            throw new ServletException("Unable to initialize HEAL application" +
                    " due to an inability to open a " +
                    " connection to the database.");
        }
        //The translator between URLs and actual file locations
        FileLocator fileLocator = new FileLocator();
        fileLocator.setBaseFilePath(basePath);
        fileLocator.setServerBaseURL(baseURL);
        fileLocator.setUploadDirectory(uploadPath);
        fileLocator.setThumbnailDirectory(thumbnailPath);
        fileLocator.setContentDirectory(contentPath);
        fileLocator.setPackageDirectory(packagePath);
        fileLocator.generateFullDirectories();
        context.setAttribute("healFileLocator", fileLocator);
        //if the directories don't exist, we need to try and create them.
        String dirString = fileLocator.getUploadFilePath();
        File tempDirectory;
        boolean success;
        tempDirectory = new File(dirString);
        if(!tempDirectory.exists()) {
            success = tempDirectory.mkdirs();
            if(!success) {
                throw new ServletException("Unable to initialize HEAL " +
                        "application due to an inability " +
                        "to create the upload " +
                        "directory: " + dirString);
            }
        } else if(!tempDirectory.isDirectory()) {
            throw new ServletException("Unable to initialize HEAL " +
                    "application due to the fact that the " +
                    "upload path exists, but is not a " +
                    "directory: " + dirString);
        }
        //if the directories don't exist, we need to try and create them.
        dirString = fileLocator.getThumbnailFilePath();
        tempDirectory = new File(dirString);
        if(!tempDirectory.exists()) {
            success = tempDirectory.mkdirs();
            if(!success) {
                throw new ServletException("Unable to initialize HEAL " +
                        "application due to an inability " +
                        "to create the thumbnail " +
                        "directory: " + dirString);
            }
        } else if(!tempDirectory.isDirectory()) {
            throw new ServletException("Unable to initialize HEAL " +
                    "application due to the fact that the " +
                    "thumbnail path exists, but is not a " +
                    "directory: " + dirString);
        }
        dirString = fileLocator.getContentFilePath();
        tempDirectory = new File(dirString);
        if(!tempDirectory.exists()) {
            success = tempDirectory.mkdirs();
            if(!success) {
                throw new ServletException("Unable to initialize HEAL " +
                        "application due to an inability " +
                        "to create the content " +
                        "directory: " + dirString);
            }
        } else if(!tempDirectory.isDirectory()) {
            throw new ServletException("Unable to initialize HEAL " +
                    "application due to the fact that the " +
                    "content path exists, but is not a " +
                    "directory: " + dirString);
        }
        dirString = fileLocator.getPackageFilePath();
        tempDirectory = new File(dirString);
        if(!tempDirectory.exists()) {
            success = tempDirectory.mkdirs();
            if(!success) {
                throw new ServletException("Unable to initialize HEAL " +
                        "application due to an inability " +
                        "to create the package " +
                        "directory: " + dirString);
            }
        } else if(!tempDirectory.isDirectory()) {
            throw new ServletException("Unable to initialize HEAL " +
                    "application due to the fact that the " +
                    "package path exists, but is not a " +
                    "directory: " + dirString);
        }
        //put the user registry in the application scope
        UserRegistryBean userReg = new UserRegistryBean();
        userReg.setDataSource(ds);
        context.setAttribute("userRegistry", userReg);

        //put the Common dao in the application scope
        CommonDAO comDao = new CommonDAO();
        comDao.setDataSource(ds);
        context.setAttribute("CommonDAO", comDao);

        //put the Metadata dao in the application scope
        MetadataDAO metaDao = new MetadataDAO();
        metaDao.setDataSource(ds);
        metaDao.setFileLocator(fileLocator);
        context.setAttribute("MetadataDAO", metaDao);
        try{
        context.setAttribute("resourceCount3",metaDao.getCountOfResources());
        }
        catch (SQLException se){
          se.printStackTrace();
        }
        //put the simple search dao in the application scope
        SimpleSearchDAO sDao = new SimpleSearchDAO();
        sDao.setDataSource(ds);
        context.setAttribute("SimpleSearchDAO", sDao);

        //put the edit search dao in the application scope
        ReSearchDAO Dao = new ReSearchDAO();
        Dao.setDataSource(ds);
        context.setAttribute("ReSearchDAO", Dao);

        //put the advanced search dao in the application scope
        AdvSearchDAO aDao = new AdvSearchDAO();
        aDao.setDataSource(ds);
        context.setAttribute("AdvSearchDAO", aDao);

        UploadServicesBean uploadServices = new UploadServicesBean();
        uploadServices.setFileLocator(fileLocator);
        context.setAttribute("uploadServices", uploadServices);

        DownloadDAO downloadServices = new DownloadDAO();
        downloadServices.setFileLocator(fileLocator);
        downloadServices.setMetadataDAO(metaDao);
        downloadServices.setDataSource(ds);
        context.setAttribute("downloadDAO", downloadServices);

        QueueDAO queueDao = new QueueDAO();
        queueDao.setDataSource(ds);
        context.setAttribute("QueueDAO", queueDao);

        BrowseDAO browseServices = new BrowseDAO();
        browseServices.setDataSource(ds);
        context.setAttribute("browseDAO", browseServices);
        
/*        ConceptMappingDAO cpDAO = new ConceptMappingDAO();
        cpDAO.setDataSource(ds);
        context.setAttribute("ConceptMappingDAO", cpDAO);
*/		
        //added by Zhen 2/20/08
        UserReviewDAO userReviewDao = new UserReviewDAO();
        userReviewDao.setDataSource(ds);
	context.setAttribute("UserReviewDAO", userReviewDao);

        //added by Zhen 5/20/08
        TagCloudDAO tagCloudDao = new TagCloudDAO();
        tagCloudDao.setDataSource(ds);
	context.setAttribute("TagCloudDAO", tagCloudDao);
        
        //now put the notification services bean in the application scope
        NotificationServicesBean notificationServices = new NotificationServicesBean();
        notificationServices.setUserRegistry(userReg);
        notificationServices.setMailhost(healMailHost);
        notificationServices.setHealAddress(healInternetAddress);
        notificationServices.setMailUser(healMailUser);
        notificationServices.setMailPassword(healMailPassword);
        context.setAttribute("notificationServices", notificationServices);

        AdministrationDAO adminDao = new AdministrationDAO();
        adminDao.setDataSource(ds);
        adminDao.setUserRegistry(userReg);
        adminDao.setFileLocator(fileLocator);
        context.setAttribute("administrationDAO", adminDao);

        InterfaceUtilitiesBean interfaceUtil = new InterfaceUtilitiesBean();
        interfaceUtil.init(fileLocator);
        context.setAttribute("interfaceUtilities", interfaceUtil);

        actions = new Hashtable();
        actions.put("/browse", new BrowseAction());
        actions.put("/preupload", new PreviewUploadAction());
        actions.put("/upload", new SimpleUploadAction());
        actions.put("/showUploadForm", new ShowUploadFormAction());
        actions.put("/wsSearch", new WSSimpleSearchAction());
        actions.put("/search", new SimpleSearchAction());
        actions.put("/advsearch", new AdvSearchAction());
        actions.put("/editadvs", new EditAdvSearchAction());
        actions.put("/research", new ResearchAction());
        actions.put("/refinesearch", new RefineSearchAction());
        actions.put("/varsearch", new VarSearchAction());
        actions.put("/sortResults", new SortResultsAction());
        actions.put("/createRegistration", new RegistrationAction());
        actions.put("/modifyRegistration", new ModifyRegistrationAction());
        actions.put("/updateRegistration", new UpdateRegistrationAction());
        actions.put("/download", new DownloadAction());
        actions.put("/listIMSXML", new ListIMSXMLAction());
        actions.put("/showMetadata", new ShowMetadataAction());
        actions.put("/viewDownloadFolder", new ViewDownloadFolderAction());
        actions.put("/addToDownloadFolder", new AddToDownloadAction());
        actions.put("/removeFromDownloadFolder", new RemoveFromDownloadAction());
        actions.put("/wsSearchResults", new WSSearchResultsAction());
        actions.put("/searchResults", new SearchResultsAction());
        actions.put("/showRegistrationInfo", new ShowRegistrationInfoAction());
        actions.put("/authenticate", new AuthenticateAction());
        actions.put("/logout", new LogoutAction());
        actions.put("/submitPeerReviewSurvey", new SubmitPeerReviewSurveyAction());
        actions.put("/sendPasswordRemind", new EmailReminderAction());
        actions.put("/showApprovalQueue", new ShowApprovalQueueAction());
        actions.put("/modifyApprovalQueueEntry", new ModifyApprovalQueueEntryAction());
        actions.put("/showCatalogQueue", new ShowCatalogQueueAction());
        actions.put("/modifyCatalogQueueEntry", new ModifyCatalogQueueEntryAction());
        actions.put("/editMetadata", new EditMetadataAction());
        actions.put("/modifyMetadata", new ModifyMetadataAction());
        actions.put("/cancelEditMetadata", new CancelEditMetadataAction());
        actions.put("/createMetadata", new CreateMetadataAction());
        actions.put("/emailFeedback", new EmailFeedbackAction(smtpServer, xmlFeedback));
        actions.put("/modifyRegistration", new ModifyRegistrationAction());
        actions.put("/emailValidation", new EmailValidationAction());
        actions.put("/modifyEmail", new ModifyEmailAction());
        actions.put("/submitDetailedViewSurvey", new SubmitDetailedViewSurveyAction());
        actions.put("/saveControlledVocabMulti", new SaveControlledVocabularyMultipleAction());
        actions.put("/generateReport", new GenerateReportAction());
        actions.put("/admin", new AdminAction());
        actions.put("/linkCheck", new LinkCheckerAction());

	// added by Zhen 2/18/08
        actions.put("/writeUserReview", new WriteUserReviewAction());
        actions.put("/submitUserReview", new SubmitUserReviewAction());
        actions.put("/showUserReview", new ShowUserReviewAction());        
        actions.put("/showAllUserReview", new ShowAllUserReviewAction());        
        actions.put("/approveUserReview", new ApproveUserReviewAction());
        actions.put("/showTop10", new ShowTop10Action());
        
        // added by Zhen 5/20/08
        actions.put("/inputTagsForm", new InputTagsFormAction());
        actions.put("/submitTags", new SubmitTagsAction());
        actions.put("/showTopTags", new ShowTopTagsAction());        
    }

    /**
     * Removes the UserRegistryBean servlet context attributes.
     * The bean and it's data source are free to be garbage collected.
     */
    public void destroy() {
        ServletContext context = getServletContext();
        /* pull the beans out of the app. scope so that they can get
         * garbage collected and thus the DataSource, too and from that
         * we will release our DB connection.  It's a long winding road to
         * play nice, but it's the right thing to do.
        */
        context.removeAttribute("userRegistry");
        context.removeAttribute("uploadServices");
        context.removeAttribute("downloadDAO");
        context.removeAttribute("approvalQueueDAO");
        context.removeAttribute("catalogQueueDAO");
        context.removeAttribute("notificationServices");
        context.removeAttribute("administrationDAO");
        context.removeAttribute("healFileLocator");
        context.removeAttribute("interfaceUtilities");
        context.removeAttribute("browseDAO");
        context.removeAttribute("SimpleSearchDAO");
        context.removeAttribute("ReSearchDAO");
        context.removeAttribute("AdvSearchDAO");
        context.removeAttribute("MetadataDAO");
        context.removeAttribute("CommonDAO");
//        context.removeAttribute("ConceptMappingDAO");
        context.removeAttribute("UserReviewDAO");
        context.removeAttribute("TagCloudDAO");
    }

    /**
     * Refer all get requests to the central processing doPost method.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * The doPost method is the central processing unit of the HEAL
     * application.  It verifies that the user is logged in (if necessary to
     * access the request portion), forwards the user to a login page (if
     * necessary), or forwards the request to the requested jsp.
     * The action to perform is determined by the request url (by getting
     * the path info from the HttpServletRequest parameter.  If the path info
     * is empty the main.jsp is executed.  If it is unrecognized as an action
     * then an error page is returned.  XXXTBD.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String actionString = request.getPathInfo();
        if(actionString == null || actionString.length() == 0) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return;
        }
        Action action = (Action)actions.get(actionString);
        if(action == null) {
            response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
            return;
        }
        /*
         * If the user is not logged in for an action which requires
         * the user to be, we make them login first,
         * setting the original url, so that the app can forward the user to
         * the requested page once they actually do log in.
        */
        if(action.actionRequiresLogin() && !isLoggedIn(request)) {
            doForwardToLogin(request, response);
            return;
        }
        action.perform(this, request, response);
    }

    /**
     * Returns true if the user is currently logged in, or false otherwise.
     */
    private boolean isLoggedIn(HttpServletRequest request) {
        boolean isLoggedIn = false;
        HttpSession session = request.getSession();
        if(session.getAttribute("validUser") != null) {
            isLoggedIn = true;
        }
        return isLoggedIn;
    }

    private void doForwardToLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String originalURL = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        if(queryString != null) {
            originalURL += "?" + queryString;
        }
        String loginURL = "/user/login.jsp" + "?origURL=" +
                URLEncoder.encode(originalURL, "UTF-8") +
                "&errorMsg=" + URLEncoder.encode("Please log in first", "UTF-8");
        forward(loginURL, request, response);
    }

    /**
     * forwards a request to the given relative url
     */
    private void forward(String url, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }
}
