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
import org.heal.module.oai.provider.OAIProvider;
import org.heal.module.oai.provider.OAIProviderConfig;
import org.heal.module.oai.provider.ValidatedInput;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Identify extends OAIVerb {
	protected Identify(){
		super("Identify");
	}
	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.verb.OAIVerb#execute(org.heal.module.oai.provider.ValidatedInput)
	 */
	public void execute(final ValidatedInput input,final OAIProviderConfig config,final DataAccessor dataAccessor,final PrintWriter writer) {
		//supported protocol version is: "2.0", get everything else from the config
		writer.println("<Identify>");
		OAIProvider.writeString("repositoryName",config.getRepositoryName(),writer);
		OAIProvider.writeString("baseURL",config.getBaseURL(),writer);
		writer.println("<protocolVersion>2.0</protocolVersion>");
		String[] adminEmails = config.getAdminEmails();
		OAIProvider.writeStrings(adminEmails,"adminEmail",writer);
		OAIProvider.writeString("earliestDatestamp",config.getGranularity().format(config.getEarliestDatestamp()),writer);
		OAIProvider.writeString("deletedRecord",config.getDeletedRecord(),writer);
		OAIProvider.writeString("granularity",config.getGranularity().getDisplay(),writer);
		OAIProvider.writeStrings(config.getCompressions(),"compression",writer);
	    if (config.getRepositoryIdentifier() != null) {
	    	writer.write("<description>");
	    	writeOAIIdentifier(config,writer);
	    	writer.write("</description>");
	    }
		writer.println("</Identify>");
	
	}

	private void writeOAIIdentifier(OAIProviderConfig config,PrintWriter writer) {
	    writer.println("<oai-identifier xmlns=\"http://www.openarchives.org/OAI/2.0/oai-identifier\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.openarchives.org/OAI/2.0/oai-identifier http://www.openarchives.org/OAI/2.0/oai-identifier.xsd\">");
	    writer.println("<scheme>oai</scheme>");
	    OAIProvider.writeString("repositoryIdentifier",config.getRepositoryIdentifier(),writer);
	    OAIProvider.writeString("delimiter",config.getIdentifierDelimiter(),writer);
	    OAIProvider.writeString("sampleIdentifier",config.getSampleIdentifier(),writer);
	    writer.println("</oai-identifier>");
	}
	
	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.verb.OAIVerb#validateInput(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.io.PrintWriter)
	 */
	public void validateInput(ValidatedInput input,final Map parameters) {
		Iterator iter = parameters.keySet().iterator();
		while (iter.hasNext()) {
			String param = (String)iter.next();
			if (!"verb".equalsIgnoreCase(param)) {
				input.errorList.add(getExtraArgumentError(param,((String[])parameters.get(param))[0]));
			}
		}
	}

}
