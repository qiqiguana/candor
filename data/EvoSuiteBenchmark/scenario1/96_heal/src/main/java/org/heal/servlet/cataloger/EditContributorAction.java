package org.heal.servlet.cataloger;

import org.heal.servlet.Action;
import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.ContributorBean;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.Iterator;

/**
 * An {@link Action} which is called to edit a Contributor.  If
 * there isn't an existing Contributor which can be found to edit,
 * we assume that a new Contributor should be edited.
 */
public class EditContributorAction implements Action {

    public boolean actionRequiresLogin() {
        return true;
    }
}
