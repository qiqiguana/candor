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
import org.heal.module.oai.provider.OAIMetadataFormat;
import org.heal.module.oai.provider.OAIProvider;
import org.heal.module.oai.provider.OAIProviderConfig;
import org.heal.module.oai.provider.ValidatedInput;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ListMetadataFormats extends OAIVerb {
	protected ListMetadataFormats() {
		super("ListMetadataFormats");
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.verb.OAIVerb#execute(org.heal.module.oai.provider.ValidatedInput, org.heal.module.oai.provider.OAIProviderConfig, org.heal.module.oai.provider.DataAccessor, java.io.PrintWriter)
	 */
	public void execute(final ValidatedInput input,final OAIProviderConfig config,final DataAccessor dataAccessor,final PrintWriter writer) {
			OAIMetadataFormat[] formats = null;
			if (input.identifier != null) {
				formats = dataAccessor.getMetadataFormats(input.identifier);
				if (formats == null) {
					writer.println(OAIProvider.getErrorString(OAIErrorCode.ID_DOES_NOT_EXIST,input.identifier+" has the structure of a valid identifier, but it maps to no known item"));
					return;
				} else if (formats.length == 0) {
					writer.println(OAIProvider.getErrorString(OAIErrorCode.NO_METADATA_FORMATS,input.identifier+" is not available from this provider in any metadata format."));
					return;
				}
			} else {
				formats = config.getAllMetadataFormats();
				if (formats == null || formats.length == 0) {
					writer.println(OAIProvider.getErrorString(OAIErrorCode.NO_METADATA_FORMATS,"This provider is not configured to provide metadata in any format.  This is a configuration error, please contact the administrator."));
					return;
				}
			}
			//formats cannot be null or empty at this point
			writer.println("<ListMetadataFormats>");
			for (int i=0;i<formats.length;i++) {
				writer.print("<metadataFormat>");
				writer.print("<metadataPrefix>");
				writer.print(formats[i].getPrefix());
				writer.println("</metadataPrefix>");
				writer.print("<schema>");
				writer.print(formats[i].getSchema());
				writer.println("</schema>");
				writer.print("<metadataNamespace>");
				writer.print(formats[i].getNamespace());
				writer.println("</metadataNamespace>");
				writer.print("</metadataFormat>");
			}
			writer.println("</ListMetadataFormats>");
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.verb.OAIVerb#validateInput(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.io.PrintWriter)
	 */
	public void validateInput(final ValidatedInput input,final Map parameters) {
		Iterator iter = parameters.keySet().iterator();
		while (iter.hasNext()) {
			String param = (String)iter.next();
			if (!("verb".equalsIgnoreCase(param) || "identifier".equalsIgnoreCase(param))) {
				input.errorList.add(getExtraArgumentError(param,((String[])parameters.get(param))[0]));			
			}
		}
	}
}
