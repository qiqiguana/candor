package org.heal.servlet.cataloger;

import org.heal.servlet.Action;
import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.CopyrightHolderBean;

import javax.servlet.ServletRequest;
import java.util.Iterator;

/**
 * Saves changes to a {@link CopyrightHolderBean}.
 */
public class CopyrightHolderRecordModifier implements MetadataModifier {
	private static Action NEXT_ACTION = new EditMetadataAction();

	public void updateMetadata(CompleteMetadataBean cmb, ServletRequest request) {
		final String copyrightHolderId = request.getParameter("copyrightHolderId");
		final String vCard = request.getParameter("vCard");

		CopyrightHolderBean chb = null;
		int lowestCopyrightHolderId = 0;
		for(Iterator iter = cmb.getCopyrightHolders().iterator(); iter.hasNext();) {
			CopyrightHolderBean temp = (CopyrightHolderBean)iter.next();
			String idStr = temp.getCopyrightHolderId();
			int id = Integer.parseInt(idStr);
			if(idStr.equals(copyrightHolderId)) {
				chb = temp;
				break;
			}
			// We must find the lowest contributor id for cases
			// where this is a new contributor bean, and we must
			// generate a unique id for it (which we define to be
			// negative numbers for contributor beans not in the
			// database yet).
			if(id < lowestCopyrightHolderId) {
				lowestCopyrightHolderId = id;
			}
		}
		if(null == chb) {
			// No existing contributor bean was found, so we must
			// create one with a unique id and add it to the
			// CompleteMetadataBean
			chb = new CopyrightHolderBean();
			chb.setCopyrightHolderId(String.valueOf(lowestCopyrightHolderId - 1));
			// The role must be set before adding a contributor since
			// it uses the role in order to put a contributor into a
			// TreeMap
			cmb.addCopyrightHolder(chb);
		}
		chb.setVCard(vCard);
	}

	public Action getNextAction(ServletRequest request) {
		return NEXT_ACTION;
	}

}
