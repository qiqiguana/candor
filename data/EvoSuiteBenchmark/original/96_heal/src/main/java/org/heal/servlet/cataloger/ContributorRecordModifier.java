package org.heal.servlet.cataloger;

import org.heal.servlet.Action;
import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.ContributorBean;
import org.heal.util.DateTools;

import javax.servlet.ServletRequest;
import java.util.Iterator;

/**
 * Saves changes to a {@link ContributorBean}.
 */
public class ContributorRecordModifier implements MetadataModifier {
	private static Action NEXT_ACTION = new EditMetadataAction();

	public void updateMetadata(CompleteMetadataBean cmb, ServletRequest request) {
            final String contributorId = request.getParameter("contributorId");
            final String role = request.getParameter("role");
            final String vCard = request.getParameter("vCard");
            final String contributeDate = request.getParameter("contributeDate");
            final String contributeDateDescription = request.getParameter("contributeDateDescription");
	    final String version = request.getParameter("version");
	    final String status = request.getParameter("status");

        ContributorBean cb = null;
		int lowestContributorId = 0;
		for(Iterator iter = cmb.getContributorList().iterator(); iter.hasNext();) {
			ContributorBean temp = (ContributorBean)iter.next();
			String idStr = temp.getContributorId();
			int id = Integer.parseInt(idStr);
			if(idStr.equals(contributorId)) {
				cb = temp;
				break;
			}
			// We must find the lowest contributor id for cases
			// where this is a new contributor bean, and we must
			// generate a unique id for it (which we define to be
			// negative numbers for contributor beans not in the
			// database yet).
			if(id < lowestContributorId) {
				lowestContributorId = id;
			}
		}
		if(null == cb) {
			// No existing contributor bean was found, so we must
			// create one with a unique id and add it to the
			// CompleteMetadataBean
			cb = new ContributorBean();
			cb.setContributorId(String.valueOf(lowestContributorId - 1));
			// The role must be set before adding a contributor since
			// it uses the role in order to put a contributor into a
			// TreeMap
			cb.setRole(role);
			cb.setVCard(vCard);
            cb.setDate(DateTools.parse(contributeDate));
            cb.setDateDescription(contributeDateDescription);
            cb.setVersion(version);
            cb.setStatus(status);
            cmb.addContributor(cb);
		} else {
            cb.setDate(DateTools.parse(contributeDate));
            cb.setDateDescription(contributeDateDescription);
            cb.setRole(role);
            cb.setVCard(vCard);
            cb.setVersion(version);
            cb.setStatus(status);
		}
	}

	public Action getNextAction(ServletRequest request) {
		return NEXT_ACTION;
	}
}
