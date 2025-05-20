/*
 * Created on Dec 13, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.heal.module.oai.test;

import java.util.Date;

import org.heal.module.oai.provider.DataAccessor;
import org.heal.module.oai.provider.OAIMetadataFormat;
import org.heal.module.oai.provider.OAIProviderConfig;
import org.heal.module.oai.provider.OAIRecord;
import org.heal.module.oai.provider.OAIResumptionToken;
import org.heal.module.oai.provider.OAISet;
import org.heal.module.oai.provider.basic.BasicResumptionToken;
import org.heal.module.oai.provider.basic.BasicSet;

/**
 * @author swright
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PseudoDataAccessor implements DataAccessor {
	//TODO break out this test data to an xml file or something
	public static final String IDPREFIX = "oai:org.heal:";
	private static final int QUERY_RECORD = 0;
	private static final int QUERY_IDENTIFIER = 1;
	private static final int QUERY_SET = 2;
	
	OAIProviderConfig config = null;

	private int queryType = -1;
	private String metadataPrefix = null;
	private Date from = null;
	private Date until = null;
	private String set = null;
	private String nextID = null;
	private boolean queryResumed = false;

	private BasicSet[] sets = null;
	private String[] set1desc = {"Desc1, Line1","Desc2, Line2"}; 
	private String[] set2desc = {"Desc2, Line2","Desc3, Line3"}; 
	private String[] set3desc = {"Desc3, Line3","Desc4, Line4"}; 
	private String[] set4desc = {"Desc4, Line4","Desc5, Line5"}; 

	private int index = -1;
	
	public PseudoDataAccessor(OAIProviderConfig config) {
		this.config = config;
		sets = new BasicSet[4];
		sets[0] = new BasicSet("Set1","Namespace1",set1desc);
		sets[1] = new BasicSet("Set2","Namespace1",set2desc);
		sets[2] = new BasicSet("Set3","Namespace1",set3desc);
		sets[3] = new BasicSet("Set4","Namespace1",set4desc);
	}

	public OAIResumptionToken parseResumptionToken(String resumptionToken) {
		//returns null if the token is poorly formatted
		return BasicResumptionToken.parseResumptionToken(resumptionToken,config.getGranularity());
	}
	
	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#openRecordsQuery(java.util.Date, java.util.Date, java.lang.String)
	 */
	public void openRecordsQuery(String metadataPrefix, Date from, Date until, String set) {
		// TODO HEALDataAccessor.openRecordsQuery(from,until,set)
		queryType = QUERY_RECORD;

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#openRecordsQuery(java.lang.String)
	 */
	public void openRecordsQuery(String identifier) {
		// TODO HEALDataAccessor.openRecordsQuery(identifier)
		queryType = QUERY_RECORD;

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#getIdentifiersQuery(java.util.Date, java.util.Date, java.lang.String)
	 */
	public void openIdentifiersQuery(String metadataPrefix,Date from, Date until, String set) {
		// TODO HEALDataAccessor.openIdentifiersQuery
		queryType = QUERY_IDENTIFIER;

	}

	public void openSetQuery() {
		queryType = QUERY_SET;
		index = 0;
	}

	
	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#resumeRecordQuery(org.heal.module.oai.provider.OAIResumptionToken)
	 */
	public boolean resumeRecordQuery(OAIResumptionToken resumptionToken) {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#resumeSetQuery(org.heal.module.oai.provider.OAIResumptionToken)
	 */
	public boolean resumeSetQuery(OAIResumptionToken resumptionToken) {
		BasicResumptionToken token = (BasicResumptionToken) resumptionToken;
		queryType = QUERY_SET;
		index = (int)token.getCursor();
		queryResumed = true;
		return true;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#resumeIdentifierQuery(org.heal.module.oai.provider.OAIResumptionToken)
	 */
	public boolean resumeIdentifierQuery(OAIResumptionToken resumptionToken) {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#getCurrentIdentifier()
	 */
	public OAIRecord getNextIdentifier() {
		// TODO HEALDataAccessor.getCurrentIdentifier
		return null;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#getCurrentRecord()
	 */
	public OAIRecord getNextRecord() {
		// TODO HEALDataAccessor.getCurrentRecord
		return null;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#hasMoreElements()
	 */
	public boolean hasMoreElements() {
		// TODO HEALDataAccessor.hasMoreElements
		if (queryType == QUERY_SET) {
			return (index >= 0 && index < sets.length); 
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#getResumptionToken()
	 */
	public OAIResumptionToken getResumptionToken() {
		String queryTypeStr = null;
		switch (queryType) {
			case QUERY_IDENTIFIER:
				queryTypeStr = "ListIdentifiers";
				break;
			case QUERY_RECORD:
				queryTypeStr = "ListRecords";
				break;
			case QUERY_SET:
				queryTypeStr = "ListSets";
				if (index == 2) {
					break;  //send a resumption token at the second set
				}
				if (index >= sets.length) {
					if (queryResumed) {
						return BasicResumptionToken.generateQueryCompleteToken();
					}
				}
				//don't create a resumptionToken because we're at the end
				return null;
		}
		BasicResumptionToken resumptionToken = BasicResumptionToken.generateResumptionToken(metadataPrefix,
																							from,
																							until,
																							set,
																							nextID,
																							config.getGranularity());
		resumptionToken.setCursor(index);
		resumptionToken.setExpirationDate(new Date(new Date().getTime()-1000));
		return resumptionToken;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#closeQuery()
	 */
	public void closeQuery() {
		index = -1;
	}

	public Date getNow() {
		return new Date();
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#getMetadataFormats(java.lang.String)
	 */
	public OAIMetadataFormat[] getMetadataFormats(String identifier) {
		//first check to see if the id exists...
		//if it doesn't exist, return null
		//otherwise, return all formats (we're not selective yet)
		return config.getAllMetadataFormats();
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#validateIdentifier(java.lang.String)
	 */
	public boolean validateIdentifier(String identifier) {
		return identifier.startsWith(IDPREFIX);
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#validateSet(java.lang.String)
	 */
	public boolean validateSet(String set) {
		//for now any set is a valid set - change this for testing...
		return true;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#isExpired(org.heal.module.oai.provider.OAIResumptionToken)
	 */
	public boolean isExpired(OAIResumptionToken resumptionToken) {
		Date resumptionDate = resumptionToken.getExpirationDate();
		if (resumptionDate != null) {
			if (getNow().getTime() < resumptionDate.getTime()) {
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#getNextSet()
	 */
	public OAISet getNextSet() {
		OAISet retval = null;
		if (index >= 0 && index <sets.length) {			
			retval = sets[index];
			index++;
		}
		return retval;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#setExists(java.lang.String)
	 */
	public boolean setExists(String set) {
		for (int i=0;i<sets.length;i++) {
			if (sets[i].equals(set)) {
				return true;
			}
		}
		return false;
	}

}
