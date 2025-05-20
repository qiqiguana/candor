/*
 * Created on Dec 1, 2004
 *
 */
package org.heal.module.oai.provider;

import java.util.Date;

/**
 * @author Seth Wright
 * 
 */
public interface OAIProviderConfig {
	/* All of the metadata formats supported by the server.
	 * This should at the least contain the oai_dc format.
	 * 
	 * MUST RETURN AN EMPTY LIST IF NO FORMATS ARE SUPPORTED...but why would you want this?
	 */
	public OAIMetadataFormat[] getAllMetadataFormats();
	
	public OAIMetadataFormat getMetadataFormat(final String prefix);
	// the following information is used in the Identify response
	 
	//The publicly advertised name of the repository
	public String getRepositoryName();
	
	//used in the oai-identifier portion of the description element in Identify responses
	//These values should return null if the repository does not support the 
	//oai-identifier schema in the Identify query
	public String getRepositoryIdentifier();
    public String getIdentifierDelimiter();
    public String getSampleIdentifier();
	
	/* the base url to use for this provider.
	 * From the spec:
	 * URLs for GET requests have keyword arguments appended to the base URL, separated from it by a question mark [?]. For example, the URL of a GetRecord request to a repository with base URL that is http://an.oa.org/OAI-script might be:
	 * http://an.oa.org/OAI-script?verb=GetRecord&identifier=oai%3AarXiv.org%3Ahep-th%2F9901001&metadataPrefix=oai_dc
	 * 
	 * Of course an HTTP POST would be done to the base URL as such:
	 * POST http://an.oa.org/OAI-script HTTP/1.0
	 * Content-Length: 82
	 * Content-Type: application/x-www-form-urlencoded
	 * 
	 * verb=GetRecord&identifier=oai%3AarXiv.org%3Ahep-th%2F9901001&metadataPrefix=oai_dc
	 */
	public String getBaseURL();

	//array list of strings, must have at least one entry
	public String[] getAdminEmails();

	/* The earliest datestamp that can be queried.
	 */
	public Date getEarliestDatestamp();

	/* From the spec:
	 * If a record is no longer available then it is said to be deleted. Repositories must declare one of three levels of support for deleted records in the deletedRecord element of the Identify response:
     * no - the repository does not maintain information about deletions. A repository that indicates this level of support must not reveal a deleted status in any response.
     * persistent - the repository maintains information about deletions with no time limit. A repository that indicates this level of support must persistently keep track of the full history of deletions and consistently reveal the status of a deleted record over time.
     * transient - the repository does not guarantee that a list of deletions is maintained persistently or consistently. A repository that indicates this level of support may reveal a deleted status for records.
     * 
	 * So, this must return one of three strings, "no", "persistent", and "transient"	 
	 */
	public String getDeletedRecord();

	/* from the OAI spec:
	 * All repositories must support YYYY-MM-DD. 
	 * A repository that supports YYYY-MM-DDThh:mm:ssZ should 
	 * indicate so in the Identify response.
	 */
	public OAIGranularity getGranularity();

	/* What sort of compression encodings this repository supports
	 * From the spec:
	 * Repositories must support the HTTP identity encoding.
	 * Repositories should express the encodings they support in addition to identity by including compression elements in the Identify response.
	 * 
	 * NOTE: A response of null implies that this repository only supports "identity" encoding.
	 * There is no need to return "identity" as that is implicit and absolutely required by the spec.
	 */
	public String[] getCompressions();

	/* array list of strings, null if to be omitted
	 * these strings can be just plain text descriptions, or
	 * an XML encoded description, but any XML should contain the appropriate
	 * schema and namespace settings in the element.  An example from the spec:
	 * <oai-identifier 
     *   xmlns="http://www.openarchives.org/OAI/2.0/oai-identifier"
     *   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     *   xsi:schemaLocation=
     *       "http://www.openarchives.org/OAI/2.0/oai-identifier
     *   http://www.openarchives.org/OAI/2.0/oai-identifier.xsd">
     *   <scheme>oai</scheme>
     *   <repositoryIdentifier>lcoa1.loc.gov</repositoryIdentifier>
     *   <delimiter>:</delimiter>
     *   <sampleIdentifier>oai:lcoa1.loc.gov:loc.music/musdi.002</sampleIdentifier>
     *  </oai-identifier>
     * 
	 */
	public String[] getDescriptions();

}
