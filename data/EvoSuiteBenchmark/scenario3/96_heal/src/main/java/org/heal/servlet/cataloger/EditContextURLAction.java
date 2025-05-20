package org.heal.servlet.cataloger;

import org.heal.servlet.Action;
import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.ContextURLBean;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.Iterator;

/**
 * An {@link Action} which is called to edit a Context URL.  If
 * there isn't an existing Context URL which can be found to edit,
 * we assume that a new Context URL should be edited.
 */
public class EditContextURLAction implements Action {

    /**
     * @return <code>true</code>
     */
    public boolean actionRequiresLogin();
}
