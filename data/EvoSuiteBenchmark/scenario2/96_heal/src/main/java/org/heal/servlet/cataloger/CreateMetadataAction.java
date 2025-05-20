package org.heal.servlet.cataloger;

import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.MetadataDAO;
import org.heal.module.metadata.TaxonBean;
import org.heal.module.metadata.TaxonPathBean;
import org.heal.servlet.Action;
import org.heal.util.AuthenticationTools;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * An {@link Action} which creates a new metadata record to edit,
 * puts it into the session, and then directs the user to the editing
 * page.
 */
public class CreateMetadataAction implements Action {

    /**
     * @return <code>true</code>
     */
    public boolean actionRequiresLogin() {
        return true;
    }
}
