/*
 * Created on Mar 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.heal.module.oai.provider;

import java.util.HashMap;

/**
 * 
 * badArgument  	The request includes illegal arguments, is missing required arguments, includes a repeated argument, or values for arguments have an illegal syntax
 * badResumptionToken 	The value of the resumptionToken argument is invalid or expired. 
 * badVerb 	Value of the verb argument is not a legal OAI-PMH verb, the verb argument is missing, or the verb argument is repeated. 
 * cannotDisseminateFormat 	The metadata format identified by the value given for the metadataPrefix argument is not supported by the item or by the repository.
 * idDoesNotExist 	The value of the identifier argument is unknown or illegal in this repository.
 * noRecordsMatch 	The combination of the values of the from, until, set and metadataPrefix arguments results in an empty list. 
 * noMetadataFormats 	There are no metadata formats available for the specified item. 
 * noSetHierarchy 	The repository does not support sets. 
 * @author Seth Wright
*/

public class OAIErrorCode {
	private static final HashMap errorCodes = new HashMap();
	public static final OAIErrorCode BAD_ARGUMENT = new OAIErrorCode("badArgument");
	public static final OAIErrorCode BAD_RESUMPTION_TOKEN = new OAIErrorCode("badResumptionToken");
	public static final OAIErrorCode BAD_VERB = new OAIErrorCode("badVerb");
	public static final OAIErrorCode CANNOT_DISSEMINATE_FORMAT = new OAIErrorCode("cannotDisseminateFormat");
	public static final OAIErrorCode ID_DOES_NOT_EXIST = new OAIErrorCode("idDoesNotExist");
	public static final OAIErrorCode NO_RECORDS_MATCH = new OAIErrorCode("noRecordsMatch");
	public static final OAIErrorCode NO_METADATA_FORMATS = new OAIErrorCode("noMetadataFormats");
	public static final OAIErrorCode NO_SET_HEIRARCHY = new OAIErrorCode("noSetHierarchy");
	
	private final String name;
	private OAIErrorCode(final String name) {
		this.name = name;
		errorCodes.put(this.name,this);
	}
	public static OAIErrorCode getErrorCode(final String name) {
		return (OAIErrorCode)errorCodes.get(name);
	}
	public String getName() {
		return name;
	}
}
