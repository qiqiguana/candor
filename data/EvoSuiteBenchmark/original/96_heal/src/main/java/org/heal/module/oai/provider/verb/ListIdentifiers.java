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
import org.heal.module.oai.provider.OAIMetadataHandler;
import org.heal.module.oai.provider.OAIProvider;
import org.heal.module.oai.provider.OAIProviderConfig;
import org.heal.module.oai.provider.OAIRecord;
import org.heal.module.oai.provider.OAIResumptionToken;
import org.heal.module.oai.provider.ValidatedInput;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ListIdentifiers extends OAIVerb {
	protected ListIdentifiers() {
		super("ListIdentifiers");
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.verb.OAIVerb#execute(org.heal.module.oai.provider.ValidatedInput, org.heal.module.oai.provider.OAIProviderConfig, org.heal.module.oai.provider.DataAccessor, java.io.PrintWriter)
	 */
	public void execute(final ValidatedInput input,final OAIProviderConfig config,final DataAccessor dataAccessor,final PrintWriter writer) {
		String metadataPrefix;
		if (input.resumptionToken != null) {
			if (!dataAccessor.resumeIdentifierQuery(input.resumptionToken)) {
				writer.println(OAIProvider.getErrorString(OAIErrorCode.BAD_RESUMPTION_TOKEN,"While the provided resumption token is of a valid format, the query could not be resumed.  One possible reason is that the repository has changed significantly since the time the token was issued."));
			}
		} else {
			dataAccessor.openIdentifiersQuery(input.format.getPrefix(),input.from,input.until,input.set);
		}
		if (!dataAccessor.hasMoreElements()) {
			//query returned no records
			writer.println(OAIProvider.getErrorString(OAIErrorCode.NO_RECORDS_MATCH,"No records in this provider matched the ListRecords query parameters"));
		} else {
			OAIRecord record = null;
			writer.println("<ListIdentifiers>");
			//the metadataPrefix should have already been validated so we won't perform the check again
			OAIMetadataHandler handler = input.format.getHandler();
			OAIResumptionToken token = null;
			while(token == null && dataAccessor.hasMoreElements()) {
				record = dataAccessor.getNextIdentifier();
				if (record != null && record.checkIsValid()) {
					OAIProvider.writeRecordHeader(record,writer);
				}
				token = dataAccessor.getResumptionToken();
			}
			OAIProvider.writeResumptionToken(token,writer);
			writer.println("</ListIdentifiers>");			
		}		
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.verb.OAIVerb#validateInput(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.io.PrintWriter)
	 */
	public void validateInput(final ValidatedInput input,final Map parameters) {
		Iterator iter = parameters.keySet().iterator();
		while (iter.hasNext()) {
			String param = (String)iter.next();
			if (!("verb".equalsIgnoreCase(param) || "metadataPrefix".equalsIgnoreCase(param) || "from".equalsIgnoreCase(param) || "until".equalsIgnoreCase(param)  || "resumptionToken".equalsIgnoreCase(param))) {
				input.errorList.add(getExtraArgumentError(param,((String[])parameters.get(param))[0]));
			}
		}
		if (parameters.get("metadataPrefix") == null && parameters.get("resumptionToken") == null) {
			input.errorList.add(getMissingArgumentError("metadataPrefix"));
		}
	}

}
