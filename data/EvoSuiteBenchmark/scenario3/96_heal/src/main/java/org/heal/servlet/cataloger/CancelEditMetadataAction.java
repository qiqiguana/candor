package org.heal.servlet.cataloger;

import org.heal.servlet.Action;
import org.heal.util.AuthenticationTools;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;

/**
 * A simple {@link Action} which clears the session variable used
 * for editing metadata.
 */
public class CancelEditMetadataAction implements Action {

    /**
     * @return <code>false</code>
     */
    public boolean actionRequiresLogin();
}
