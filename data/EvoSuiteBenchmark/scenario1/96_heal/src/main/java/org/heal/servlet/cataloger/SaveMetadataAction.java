package org.heal.servlet.cataloger;

import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.MetadataDAO;
import org.heal.module.metadata.ContextURLBean;
import org.heal.module.metadata.CopyrightHolderBean;
import org.heal.module.metadata.ContributorBean;
import org.heal.module.metadata.TaxonPathBean;
import org.heal.module.metadata.TaxonBean;
import org.heal.module.metadata.RequirementBean;
import org.heal.module.metadata.RelationBean;
import org.heal.module.metadata.MetametadataIdentifierBean;
import org.heal.module.metadata.MetametadataContributorBean;
import org.heal.module.catalog.QueueDAO;
import org.heal.servlet.Action;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * An {@link org.heal.servlet.Action Action} used to save metadata
 * records.
 */
public class SaveMetadataAction implements Action {

    public boolean actionRequiresLogin() {
        return true;
    }
}
