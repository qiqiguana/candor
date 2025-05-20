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

    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

    /**
     * Since this {@link Action} creates <em>new</em> metadata records, we
     * must clear the ids from an existing {@link CompleteMetadataBean} that
     * this new record is based on.
     *
     * @param cmb
     */
    private static void clearIds(CompleteMetadataBean cmb) throws ServletException;

    private static void clearIdsFromList(List objects) throws ServletException;

    /**
     * @return <code>true</code>
     */
    public boolean actionRequiresLogin();
}
