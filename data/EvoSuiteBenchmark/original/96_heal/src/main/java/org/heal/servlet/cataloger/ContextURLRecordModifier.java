package org.heal.servlet.cataloger;

import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.ContextURLBean;
import org.heal.servlet.Action;

import javax.servlet.ServletRequest;
import java.util.Iterator;

/**
 * Saves changes to a {@link ContextURLBean}.
 */
public class ContextURLRecordModifier implements MetadataModifier {
	private static Action NEXT_ACTION = new EditMetadataAction();

	public void updateMetadata(CompleteMetadataBean cmb, ServletRequest request) {
		final String contextURLId = request.getParameter("contextURLId");
		final String contextURL = request.getParameter("contextURL");
		final String contextURLDescription = request.getParameter("description");

		ContextURLBean cub = null;
		int lowestContextURLId = 0;
		for(Iterator iter = cmb.getContextURLs().iterator(); iter.hasNext();) {
			ContextURLBean temp = (ContextURLBean)iter.next();
			String idStr = temp.getContextURLId();
			int id = Integer.parseInt(idStr);
			if(idStr.equals(contextURLId)) {
				cub = temp;
				break;
			}
			// We must find the lowest context url id for cases
			// where this is a new context url bean, and we must
			// generate a unique id for it (which we define to be
			// negative numbers for context url beans not in the
			// database yet).
			if(id < lowestContextURLId) {
				lowestContextURLId = id;
			}
		}
		if(null == cub) {
			// No existing context url bean was found, so we must
			// create one with a unique id and add it to the
			// CompleteMetadataBean
			cub = new ContextURLBean();
			cub.setContextURLId(String.valueOf(lowestContextURLId - 1));
			cmb.getContextURLs().add(cub);
		}
		cub.setContextURL(contextURL);
		cub.setContextURLDescription(contextURLDescription);
	}

	public Action getNextAction(ServletRequest request) {
		return NEXT_ACTION;
	}
}
