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
public interface OAIResumptionToken {
	/*
	 * This must contain all of the information required by the DataAccessor
	 * to resume the query at the next element.
	 */
	public String getContent();
	/*
	 * This is an optional argument.  If the return value is null it
	 * is assumed not to have an expiration date and it is not included in
	 * the response
	 */
	public Date getExpirationDate();
	/*
	 * This is also an optional response element.  It must be a positive integer.
	 * A value of zero or less will cause this to not be set in the response.
	 * Note that a value of zero makes no sense in that no resumption token is
	 * needed and DataAccessor.getResumptionToken should have returned null.
	 */
	public long getCompleteListSize();
	/*
	 * An optional element denoting the location in the query of the next element.
	 * This value can be 0 or greater.  A negative return value will cause this
	 * to be suppressed in the response.
	 */
	public long getCursor();
	
	/* 
	 * returns true if this resumption token is generated at the end of a query
	 * that was resumed from a previous resumption token.
	 */
	public boolean queryCompleted();
	
	/*
	 * Since resumptionToken is an exclusive argument, the metadata format
	 * must be encoded in the token but the OAIProvider needs this information
	 * to produce the metadata, so all OAIResumptionTokens must handle this
	 * method
	 */
	 public String getMetadataPrefix();
} 
