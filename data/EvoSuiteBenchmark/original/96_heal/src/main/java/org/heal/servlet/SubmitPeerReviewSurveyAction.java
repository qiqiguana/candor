package org.heal.servlet;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ArrayList;

/**
 * Comments here!
 *
 * @version 1.0
 * @author Brad Schaefer (<A HREF="mailto:schaefer@lib.med.utah.edu">schaefer@lib.med.utah.edu</A>) 
 */
public class SubmitPeerReviewSurveyAction extends DelimitedFileWriterAction {
    public boolean actionRequiresLogin() { return false; }

    /**
     * @return The name of the file to write the delimited data to.  This file
     *      need not include path information -- it will show up in the /private/logs/
     *      folder as specified in {@link org.heal.servlet.DelimitedFileWriterAction}.
     */
    public String getLogFilename() {
        return "PeerReviewSurvey.txt";
    }

    /**
     * @return A <code>String</code> representation of the page to forward
     *      to upon a successful form submission.
     */
    public String getSuccessPage() {
        return "/peerReview/surveySuccess.html";
    }

    public List getData(HttpServletRequest request) {
        List ret = new ArrayList();

        ret.add(getFormParameter(request, "firstName"));
        ret.add(getFormParameter(request, "lastName"));
        ret.add(getFormParameter(request, "affiliation"));
        ret.add(getFormParameter(request, "address1"));
        ret.add(getFormParameter(request, "address2"));
        ret.add(getFormParameter(request, "city"));
        ret.add(getFormParameter(request, "state"));
        ret.add(getFormParameter(request, "postalCode"));
        ret.add(getFormParameter(request, "country"));
        ret.add(getFormParameter(request, "email"));
        ret.add(getFormParameter(request, "phoneNumber"));
        ret.add(getFormParameter(request, "faxNumber"));
        ret.add(getFormParameter(request, "discipline"));
        ret.add(getFormParameter(request, "teachingExperience"));
        ret.add(getFormParameter(request, "experienceLevel"));
        ret.add(getFormParameter(request, "attendedGeaMeeting"));
        ret.add(getFormParameter(request, "attendedPreviousSOL"));
        ret.add(getFormParameter(request, "willAttendSOL"));
        ret.add(getFormParameter(request, "willAttendIAMSE"));
        ret.add(getFormParameter(request, "willAttendAAMC"));

        return ret;
    }

}
