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
	private static Action NEXT_ACTION = new EditMetadataAction();

	public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// We skip the usual authentication-checking stuff since this
		// already should've been done by ModifyMetadataAction
		final CompleteMetadataBean cmb = (CompleteMetadataBean)request.getSession().getAttribute("metadata");
		final String selectedId = request.getParameter("selectedId");

		// This shouldn't be necessary, but it won't hurt to check
		if(null == cmb || null == selectedId) {
			response.sendRedirect("/error/accessDenied.jsp");
			return;
		}

        RelationBean rbToEdit = findRelationBean(cmb.getRelations(), selectedId);
		if(null == rbToEdit) {
			rbToEdit = new RelationBean();
		}
		request.setAttribute("RelationBean", rbToEdit);

		RequestDispatcher rd = request.getRequestDispatcher("/catalog/editRelation.jsp");
		rd.forward(request, response);
	}

	/**
	 * @return <code>true</code>
	 */
	public boolean actionRequiresLogin() {
		return true;
	}

	public void updateMetadata(CompleteMetadataBean cmb, ServletRequest request) {
		final String relationId = request.getParameter("relationId");
		final String resource = request.getParameter("resource");
		final String kind = request.getParameter("kind");
		final String description = request.getParameter("description");
                final String catalogue = request.getParameter("catalogue");
                final String entry = request.getParameter("entry");

		RelationBean rb = findRelationBean(cmb.getRelations(), relationId);
		if(null == rb) {
			int lowestRelationId = 0;
			for(Iterator iter = cmb.getRelations().iterator(); iter.hasNext();) {
				final RelationBean temp = (RelationBean)iter.next();
				final int tempId = Integer.parseInt(temp.getRelationId());
                            if(tempId < lowestRelationId) {
					lowestRelationId = tempId;
				}
			}
			rb = new RelationBean();
			rb.setRelationId(String.valueOf(lowestRelationId - 1));
                        cmb.addRelation(rb);
		}
		rb.setResource(resource);
		rb.setKind(kind);
		rb.setDescription(description);
                rb.setCatalogue(catalogue);
                rb.setEntry(entry);
                
	    
	}

	public Action getNextAction(ServletRequest request) {
		return NEXT_ACTION;
	}

	private RelationBean findRelationBean(final List relations, final String relationId) {
		RelationBean ret = null;
		for(Iterator iter = relations.iterator(); iter.hasNext();) {
			RelationBean temp = (RelationBean)iter.next();
            if( (null == relationId && null == temp.getRelationId()) ||
				  (null != relationId && relationId.equals(temp.getRelationId()))) {
				ret = temp;
			}
		}
		return ret;
	}
}
