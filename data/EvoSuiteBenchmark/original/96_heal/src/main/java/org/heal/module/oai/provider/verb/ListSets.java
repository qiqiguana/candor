/*
 * Created on Mar 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.heal.module.oai.provider.verb;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import org.heal.module.oai.provider.DataAccessor;
import org.heal.module.oai.provider.OAIErrorCode;
import org.heal.module.oai.provider.OAIProvider;
import org.heal.module.oai.provider.OAIProviderConfig;
import org.heal.module.oai.provider.OAIResumptionToken;
import org.heal.module.oai.provider.OAISet;
import org.heal.module.oai.provider.ValidatedInput;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ListSets extends OAIVerb {
	protected ListSets() {
		super("ListSets");
	}
	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.verb.OAIVerb#execute(org.heal.module.oai.provider.ValidatedInput, org.heal.module.oai.provider.OAIProviderConfig, org.heal.module.oai.provider.DataAccessor, java.io.PrintWriter)
	 */
	public void execute(final ValidatedInput input,final OAIProviderConfig config,final DataAccessor dataAccessor,final PrintWriter writer) {
		if (input.resumptionToken != null) {
			if (!dataAccessor.resumeSetQuery(input.resumptionToken)) {
				writer.println(OAIProvider.getErrorString(OAIErrorCode.BAD_RESUMPTION_TOKEN,"While the provided resumption token is of a valid format, the query could not be resumed.  One possible reason is that the repository has changed significantly since the time the token was issued."));
			}
		} else {
			dataAccessor.openSetQuery();
		}
		if (!dataAccessor.hasMoreElements()) {
			writer.println(OAIProvider.getErrorString(OAIErrorCode.NO_SET_HEIRARCHY,"This repository does not support sets"));
		} else {
			writer.println("<ListSets>");			
			OAIResumptionToken token = null;
			while(token == null && dataAccessor.hasMoreElements()) {
				OAISet set = dataAccessor.getNextSet();
				writer.println("<set>");
				writer.print("<setSpec>");
				writer.print(set.getSpec());
				writer.println("</setSpec>");
				writer.print("<setName>");
				writer.print(set.getName());
				writer.println("</setName>");
				OAIProvider.writeStrings(set.getDescriptions(),"setDescription",writer);
				writer.println("</set>");
				token = dataAccessor.getResumptionToken();
			}
			if (token != null) {
				OAIProvider.writeResumptionToken(token,writer);				
			}
			writer.println("</ListSets>");
		}
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.verb.OAIVerb#validateInput(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.io.PrintWriter)
	 */
	public void validateInput(final ValidatedInput input,final Map parameters) {
		Iterator iter = parameters.keySet().iterator();
		while (iter.hasNext()) {
			String param = (String)iter.next();
			if (!("verb".equalsIgnoreCase(param) || "resumptionToken".equalsIgnoreCase(param))) {
				input.errorList.add(getExtraArgumentError(param,((String[])parameters.get(param))[0]));			
			}
		}
	}

}
