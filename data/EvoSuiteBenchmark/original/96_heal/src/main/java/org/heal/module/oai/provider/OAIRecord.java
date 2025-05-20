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
public interface OAIRecord {
	
	/* This method returns true if the record is valid and should be returned 
	 * to the requester.  If this is false, then it should not be presented
	 * to the caller (i.e. it should be skipped over and not included in the
	 * XML results).
	 */
	public boolean checkIsValid();
	
	/* Returns the identifier used by OAI in GetRecord requests
	 */
	public String getOAIIdentifier();

	/* Returns the date stamp used to depict the record's creation or update 
	 * dates to be returned in the OAI record header.
	 */	
	public Date getDateStamp();

	/* Returns true if this record is available in the given format.
	 * False if it is not.
	 */
	public boolean supportsMetadataFormat(final OAIMetadataFormat format);

	/* getRecordAdapter returns null if the metadataPrefix is not supported
	 * by this record
	 */
	public OAIRecordAdapter getRecordAdapter(final OAIMetadataFormat format);
	
	/* The list of Sets to which this record belongs.
	 * Returns null or empty list if the record does not belong to any
	 * sets
	 */
	public OAISet[] getSets();

	/* For repositories that support deleted metadata harvesting
	 * this gives the status of the current record.
	 */
	public boolean isDeleted();
}
