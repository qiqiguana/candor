package org.heal.servlet.cataloger;

import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.RequirementBean;
import org.heal.servlet.Action;

import javax.servlet.ServletRequest;
import java.util.Iterator;

/**
 * Saves changes to a {@link RequirementBean}.
 */
public class RequirementRecordModifier implements MetadataModifier {
	private static Action NEXT_ACTION = new EditMetadataAction();

	public void updateMetadata(CompleteMetadataBean cmb, ServletRequest request) {
		final String requirementId = request.getParameter("requirementId");
		final String type = request.getParameter("requirementType");
		final String name = request.getParameter("requirementName");
                final String otherplatform = request.getParameter("otherPlatform");
                final String description = request.getParameter("description");
                final String duration = request.getParameter("duration");
		int lowestRequirementId = 0;
		RequirementBean requirement = null;
		for(Iterator iter = cmb.getRequirements().iterator(); iter.hasNext();) {
			RequirementBean temp = (RequirementBean)iter.next();
			final int tempId = Integer.parseInt(temp.getRequirementId());
			if(requirementId.equals(temp.getRequirementId())) {
				requirement = temp;
				break;
			}
			if(tempId < lowestRequirementId) {
				lowestRequirementId = tempId;
			}
		}
		if(null == requirement) {
			requirement = new RequirementBean();
			requirement.setRequirementId(String.valueOf(lowestRequirementId - 1));
                        cmb.addRequirement(requirement);
		}
		requirement.setRequirementType(type);
		requirement.setRequirementName(name);
                requirement.setOtherPlatform(otherplatform);
                requirement.setDescription(description);;
                requirement.setDuration(duration);
	    
	}

	public Action getNextAction(ServletRequest request) {
		return NEXT_ACTION;
	}
}
