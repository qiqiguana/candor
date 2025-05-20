/*
 * Created on Dec 1, 2004
 *
 */
package org.heal.module.oai.provider.basic;

import java.text.ParseException;
import java.util.Date;
import java.util.StringTokenizer;

import org.heal.module.oai.provider.OAIGranularity;
import org.heal.module.oai.provider.OAIResumptionToken;

/**
 * @author Seth Wright
 * This is a default implementation of the OAIResumptionToken
 */
public class BasicResumptionToken implements OAIResumptionToken {
	private static String delimiter = "|";

	/*
	 * If your repository uses the default delimiter for some other purpose
	 * and it may show up in say, the metadataPrefix, or some other field
	 * used to generate the content string, you may wish to change the delimiter.
	 */
	public static void setDelimiter(String newDelimiter) {
		delimiter = newDelimiter;
	}

	public static BasicResumptionToken parseResumptionToken(final String token,final OAIGranularity granularity) {
		if (token == null) return null;
		StringTokenizer tokenizer = new StringTokenizer(token,delimiter);
		if (!tokenizer.hasMoreTokens()) {
			//ACK! invalid token
			return null;
		}		

		long cursor;
		try {
			cursor = Long.parseLong(tokenizer.nextToken());			
		} catch (NumberFormatException ex) {
			return null;
		}

		if (!tokenizer.hasMoreTokens()) {
			//ACK! invalid token
			return null;
		}

		String from = tokenizer.nextToken();
		Date fromDate = null;
		if (!"null".equals(from)) {
			try {
				fromDate = granularity.parse(from);			
			} catch (ParseException ex) {
				return null;
			}
		}
		if (!tokenizer.hasMoreTokens()) {
			//ACK! invalid token
			return null;
		}
		String until = tokenizer.nextToken();
		Date untilDate = null;
		if (!"null".equals(until)) {
			try {
				untilDate = granularity.parse(until);			
			} catch (ParseException ex) {
				return null;
			}
		}
		if (!tokenizer.hasMoreTokens()) {
			//ACK! invalid token
			return null;
		}
		String set = tokenizer.nextToken();
		if (!tokenizer.hasMoreTokens()) {
			//ACK! invalid token
			return null;
		}
		String nextID = tokenizer.nextToken();
		if (!tokenizer.hasMoreTokens()) {
			//ACK! invalid token, we have too many elements
			return null;
		}
		String expires = tokenizer.nextToken();
		Date expirationDate = null;
		if (!"null".equals(expires)) {
			try {
				//expiration dates are in the full 8601 format (spec says so)
				expirationDate = OAIGranularity.yearMonthDayHourMinuteSecond.parse(expires);			
			} catch (ParseException ex) {
				return null;
			}
		}
		if (!tokenizer.hasMoreTokens()) {
			//ACK! invalid token, we have too many elements
			return null;
		}
		long listSize;
		try {
			listSize = Long.parseLong(tokenizer.nextToken());			
		} catch (NumberFormatException ex) {
			return null;
		}

		if (!tokenizer.hasMoreTokens()) {
			//ACK! invalid token, we have too many elements
			return null;
		}

		String metadataPrefix = tokenizer.nextToken();
		if ("null".equals(metadataPrefix)) {
			metadataPrefix = null;
		}
		
		if (tokenizer.hasMoreTokens()) {
			//ACK! invalid token, we have too many elements
			return null;
		}

		return new BasicResumptionToken(metadataPrefix,fromDate,untilDate,set,nextID,expirationDate,listSize,cursor,granularity);
	}

	//generates a resumption token to denote the end of a query that was resumed from a resumption token
	public static BasicResumptionToken generateQueryCompleteToken() {
		return new BasicResumptionToken();
	}

	public static BasicResumptionToken generateResumptionToken(final String metadataPrefix,
															   final Date from, 
															   final Date until,
															   final String set,
															   final String nextID,
															   final OAIGranularity repositoryGranularity)
	{
		return generateResumptionToken(metadataPrefix,from,until,set,nextID,null,0,-1,repositoryGranularity);
	}

	public static BasicResumptionToken generateResumptionToken(final String metadataPrefix,
															   final Date from, 
															   final Date until,
															   final String set,
															   final String nextID,
															   final Date expirationDate,
															   final long listSize,
															   final long cursor,
															   final OAIGranularity repositoryGranularity)			
	{
		return new BasicResumptionToken(metadataPrefix,
										from, 
										until,
										set,
										nextID,
										expirationDate,
										listSize,
										cursor,
										repositoryGranularity);
	}
	
	private long listSize = 0;
	private long cursor = -1;
	private Date expirationDate = null;
	private String content = null;
	private String metadataPrefix = null;
	private Date from = null; 
	private Date until = null;
	private String set = null;
	//this doesn't have to be the same as the cursor, but can be.
	private String nextID = null;
	private boolean completedQuery = false;
	private OAIGranularity granularity = null;
	
	private BasicResumptionToken() {
		this.completedQuery = true;
	}

	private BasicResumptionToken(final String metadataPrefix,
								 final Date from, 
								 final Date until,
								 final String set,
								 final String nextID,
								 final Date expirationDate,
								 final long listSize,
								 final long cursor,
								 final OAIGranularity repositoryGranularity)
	{ 
		this.metadataPrefix = metadataPrefix;
		this.from = from; 
		this.until = until;
		this.set = set;
		//this doesn't have to be the same as the cursor, but can be.
		this.nextID = nextID;
		this.expirationDate = expirationDate;
		this.listSize = listSize;
		this.cursor = cursor;
		this.content = null;
		this.completedQuery = false;
		this.granularity = repositoryGranularity;
	}

	public String getMetadataPrefix() {
		return metadataPrefix;
	}
	
	public Date getFrom() {
		return from;
	}

	public Date getUntil() {
		return until;
	}
	
	public String getSet() {
		return set;
	}
	
	public String getNextID() {
		return nextID;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIResumptionToken#getContent()
	 */
	public String getContent() {
		if (content == null) {
			content= generateContentString();
		}
		return content;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIResumptionToken#getExpirationDate()
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIResumptionToken#getCompleteListSize()
	 */
	public long getCompleteListSize() {
		return listSize;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIResumptionToken#getCursor()
	 */
	public long getCursor() {
		return cursor;
	}
	
	public void setCursor(long cursor) {
		this.cursor = cursor;
	}

	public void setExpirationDate(final Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIResumptionToken#queryCompleted()
	 */
	public boolean queryCompleted() {
		return completedQuery;
	}

	private String generateContentString() 
	{
		StringBuffer contentBuff = new StringBuffer();
		contentBuff.append(cursor);
		contentBuff.append(delimiter);

		if (from != null) {
			contentBuff.append(granularity.format(from));
		} else {
			contentBuff.append("null");
		}
		contentBuff.append(delimiter);
		if (until != null) {
			contentBuff.append(granularity.format(until));
		} else {
			contentBuff.append("null");
		}
		contentBuff.append(delimiter);

		if (set != null) { 
			contentBuff.append(set);
		} else {
			contentBuff.append("null");
		}
		contentBuff.append(delimiter);

		if (nextID != null) { 
			contentBuff.append(nextID);
		} else {
			contentBuff.append("null");
		}
		contentBuff.append(delimiter);

		if (expirationDate != null) { 
			contentBuff.append(granularity.format(expirationDate));
		} else {
			contentBuff.append("null");
		}
		contentBuff.append(delimiter);

		contentBuff.append(listSize);
		contentBuff.append(delimiter);

		if (metadataPrefix != null) { 
			contentBuff.append(metadataPrefix);
		} else {
			contentBuff.append("null");
		}

		return contentBuff.toString();
	}

	public String toString() {
		StringBuffer retval = new StringBuffer();
		retval.append("[metadataPrefix=");
		retval.append(metadataPrefix);
		retval.append("] [from=");
		retval.append(OAIGranularity.yearMonthDayHourMinuteSecond.format(from));
		retval.append("] [until=");
		retval.append(OAIGranularity.yearMonthDayHourMinuteSecond.format(until));
		retval.append("] [set=");
		retval.append(set);
		retval.append("] [nextID=");
		retval.append(nextID);		
		retval.append("] [content=");
		if (content != null) {
			retval.append(content);			
		} else {
			retval.append("null");
		}
		retval.append("] [expirationDate=");
		if (expirationDate != null) {
			//expirationdates are in the full 8601 format
			retval.append(OAIGranularity.yearMonthDayHourMinuteSecond.format(expirationDate));
		} else {
			retval.append("null");
		}
		retval.append("] [listSize=");
		retval.append(listSize);			
		retval.append("] [cursor=");
		retval.append(cursor);			
		retval.append("]");

		return retval.toString();
	}
}
