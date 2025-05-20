package org.heal.servlet;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * An {@link Action} that stores information submitted in the Detailed View survey
 * into a delimited text file.
 */
public class SubmitDetailedViewSurveyAction extends DelimitedFileWriterAction {
	public SubmitDetailedViewSurveyAction() { }

	public String getLogFilename() {
		return "DetailedViewSurvey.txt";
	}

	public String getSuccessPage() {
		return "../feedback/mail_logged_response.html";
	}

	public List getData(HttpServletRequest request) {
		List ret = new ArrayList();

		ret.add(getFormParameter(request, "metadataId"));
		ret.add(getFormParameter(request, "userId"));
		ret.add(getFormParameter(request, "rating"));
		ret.add(getFormParameter(request, "use"));
		ret.add(getFormParameter(request, "comments"));

		return ret;
	}

	public boolean actionRequiresLogin() {
		return false;
	}
}
