/*
 * Created on Mar 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.heal.module.oai.provider.verb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import org.heal.module.oai.provider.DataAccessor;
import org.heal.module.oai.provider.OAIErrorCode;
import org.heal.module.oai.provider.OAIMetadataHandler;
import org.heal.module.oai.provider.OAIProvider;
import org.heal.module.oai.provider.OAIProviderConfig;
import org.heal.module.oai.provider.OAIRecord;
import org.heal.module.oai.provider.ValidatedInput;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GetRecord extends OAIVerb {
	protected GetRecord() {
		super("GetRecord");
	}
	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.verb.OAIVerb#execute(org.heal.module.oai.provider.ValidatedInput, org.heal.module.oai.provider.OAIProviderConfig, org.heal.module.oai.provider.DataAccessor, java.io.PrintWriter)
	 */
	public void execute(
		final ValidatedInput input,
		final OAIProviderConfig config,
		final DataAccessor dataAccessor,
		final PrintWriter writer) throws IOException {
		if (input.identifier == null) {
			writer.println(OAIProvider.getErrorString(OAIErrorCode.BAD_ARGUMENT,"'identifier' is a required argument for GetRecord but was not included the query."));
		} else {
			dataAccessor.openRecordsQuery(input.identifier);
			if (OAIProvider.debug) {
				System.out.println("*******************************************");
				System.out.println("calling get next record...");
				System.out.println("*******************************************");
			}
			OAIRecord record = dataAccessor.getNextRecord();
			if (OAIProvider.debug) {
				System.out.println("*******************************************");
				System.out.println("after calling get next record...");
				System.out.println("*******************************************");
			}
			if (record == null || !record.checkIsValid()) {
				writer.println(OAIProvider.getErrorString(OAIErrorCode.ID_DOES_NOT_EXIST,"No records in this provider matched the provided identifier '"+input.identifier+"'"));
			} else {
				OAIMetadataHandler handler = (OAIMetadataHandler) input.format.getHandler();
				if (!record.supportsMetadataFormat(handler.getFormat())) {
					writer.println(OAIProvider.getErrorString(OAIErrorCode.CANNOT_DISSEMINATE_FORMAT,"The specified metadataPrefix '"+input.format.getPrefix()+"' is not supported by the record with identifier '"+input.identifier+"'"));	
				} else {
					//okay, all the error cases are checked, now output the record
					writer.println("<GetRecord>");
					if (OAIProvider.debug) {
						System.out.println("*******************************************");
						System.out.println("entering write record...");
						System.out.println("*******************************************");
					}

					OAIProvider.writeRecord(record,handler,writer);
					writer.println("</GetRecord>");
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.verb.OAIVerb#validateInput(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.io.PrintWriter)
	 */
	public void validateInput(final ValidatedInput input,final Map parameters) {
		Iterator iter = parameters.keySet().iterator();
		while (iter.hasNext()) {
			String param = (String)iter.next();
			if (!("verb".equalsIgnoreCase(param) || "identifier".equalsIgnoreCase(param) || "metadataPrefix".equalsIgnoreCase(param))) {
				input.errorList.add(getExtraArgumentError(param,((String[])parameters.get(param))[0]));			
			}
		}
		if (parameters.get("identifier") == null) {
			input.errorList.add(getMissingArgumentError("identifier"));			
		}
		if (parameters.get("metadataPrefix") == null) {
			input.errorList.add(getMissingArgumentError("metadataPrefix"));			
		}
	}
}
