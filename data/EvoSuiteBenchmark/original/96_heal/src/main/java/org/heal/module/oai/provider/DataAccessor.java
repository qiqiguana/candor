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
public interface DataAccessor {

	public void openRecordsQuery(final String metadataPrefix,final Date from, final Date until, final String set);
	public void openRecordsQuery(final String identifier);
	public void openIdentifiersQuery(final String metadataPrefix,final Date from, final Date until, final String set);
	public void openSetQuery();

	/* resumptionToken is an exclusive parameter that encodes all query information 
	 * so no other parameters are required to resume the query
	 * These method return whether or not the query was successfully resumed.  
	 * It will return false in the case that the query denoted by the resumption
	 * token cannot be resumed (i.e. too many changes to the repository, etc).  In this
	 * case, the OAIProvider will issue a badResumptionToken error.
	 */
	public boolean resumeRecordQuery(final OAIResumptionToken resumptionToken);
	public boolean resumeSetQuery(final OAIResumptionToken resumptionToken);
	public boolean resumeIdentifierQuery(final OAIResumptionToken resumptionToken);

	/* This method returns an OAIRecord object, but is only required to return
	 * valid values for getOAIIdentifier, getSets[], and getDatestamp()
	 * Returns null if there are no more identifiers to be had
	 */
	public OAIRecord getNextIdentifier();

	/* Returns a full fledged record, supporting all methods
	 * Returns null if there are no records to be read
	 */
	public OAIRecord getNextRecord();

	/* returns true as long as there are more elements, including the current
	 * record.
	 * This will only return false
	 */
	public boolean hasMoreElements();

	/* Used for iterating over all of the possible sets
	 */
	public OAISet getNextSet();

	/* The data accessor will be queried for a resumption token on each iteration over a query, except
	 * the getRecord query for a particular identifier.  The rules on what and when to return a token are
	 * as follows:
	 * null should be returned if 
	 * 				a) the end of the list has been reached or 
	 * 				b) the provider should continue processing the list (i.e. it's okay to 
	 * 					ask for another element)
	 * 
	 * A token should be returned if
	 * 				a) the provider should stop processing the list AND there still exist 
	 * 				   	records/lists/identifiers to be processed
	 * 				b) the end of the list has been reached AND the query was resumed via a 
	 * 					resumption.  in this case the resumption token should not contain either
	 * 					an expiration date, or content (token.getContent() and token.getExpirationDate()
	 * 					MUST return null)
	 * For additional details, see the spec:  http://www.openarchives.org/OAI/2.0/openarchivesprotocol.htm#FlowControl
	 */
	public OAIResumptionToken getResumptionToken();

	/* Performs any cleanup necessary when the provider is finished working with
	 * this query.  This is called for every request, so it must execute cleanly
	 * even in the case that no query was actually opened.
	 */
	public void closeQuery();

	//return the repository's current time, used in the response date field
	public Date getNow();

	/* the query for all formats supported is in the OAIProviderConfig
	 * but the query for what a particular identifier supports is based
	 * on back-end data and therefore is listed here.
	 * This should return an ArrayList of OAIMetadataFormat objects
	 * 
	 * MUST RETURN NULL IF THE ID DOES NOT EXIST
	 * MUST RETURN AN EMPTY LIST IF THE ID EXISTS BUT NO FORMATS ARE SUPPORTED
	 */
	public OAIMetadataFormat[] getMetadataFormats(final String identifier);
	
	/* returns true if the format of the identifier conforms to that of this repository
	 * false if it doesn't.  This DOES NOT check to see if the identifier actually exists.
	 */
	public boolean validateIdentifier(final String identifier);

	/* returns true if the format of the set conforms to that of this repository
	 * false if it doesn't.  This DOES NOT check to see if the set actually exists.
	 */
	public boolean validateSet(final String set);

	/* Validates whether or not the set actually exists
	 */
	public boolean setExists(final String set);

	/*
	 * Parses the resumption token and returns a resumptionToken object
	 * Returns null if the string is not parseable or has bad arguments.
	 */
	public OAIResumptionToken parseResumptionToken(final String resumptionToken);

	/*
	 * Returns true if the resumption token has expired and is therefore invalid,
	 * false otherwise.
	 */
	public boolean isExpired(final OAIResumptionToken resumptionToken);
}
