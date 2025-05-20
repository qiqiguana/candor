package org.heal.servlet.cataloger;

import org.heal.servlet.Action;
import org.heal.module.metadata.RelationBean;
import org.heal.module.metadata.CompleteMetadataBean;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Saves changes to a {@link RelationBean} and acts as
 * an {@link Action} which is called to edit a Relation.  If
 * there isn't an existing Relation which can be found to edit,
 * we assume that a new Relation should be edited.
 */
public class RelationRecordModifier implements Action, MetadataModifier {

    public boolean actionRequiresLogin() {
        return true;
    }
}
