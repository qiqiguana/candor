/*
 * Created on Dec 1, 2004
 *
 */
package org.heal.module.oai.heal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.MetadataDAO;
import org.heal.module.oai.provider.DataAccessor;
import org.heal.module.oai.provider.OAIMetadataFormat;
import org.heal.module.oai.provider.OAIRecord;
import org.heal.module.oai.provider.OAIResumptionToken;
import org.heal.module.oai.provider.OAISet;
import org.heal.module.oai.provider.basic.BasicResumptionToken;

/**
 * @author Seth Wright
 *
 */
public class HEALDataAccessor implements DataAccessor {
	private static final boolean debug = false;

	private SimpleDateFormat catalogFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

	private static final int QUERY_RECORD = 0;
	private static final int QUERY_IDENTIFIER = 1;
	private static final int QUERY_SET = 2;
	
	private final HEALProviderConfig config;

	private final DataSource dataSource;
	private Connection conn = null;
	private ResultSet resultSet = null;
	private PreparedStatement stmt = null;
	private long recordsReturned;
	private MetadataDAO metadataDAO = null;
	
	private int queryType = -1;
	private String metadataPrefix = null;
	private Date from = null;
	private Date until = null;
	private String set = null;
	private String nextID = null;
	private boolean hasRows = false;
	private boolean moveForwardOnNext = true;
	//set to true if this query was resumed via a resumptionToken
	//in this case an empty resumptionToken MUST be returned at
	//the end of the query
	private boolean resumedQuery = false;
	private static final String BASE_RECORD_QUERY = "SELECT MetadataID from Metadata WHERE Private=0 AND GlobalID is not null AND CatalogDate is not null";
	private static final String RESUMPTION_QUERY = " AND MetadataID > ?";
	private static final String FROM_QUERY = " AND CatalogDate >= ?";
	private static final String UNTIL_QUERY = " AND CatalogDate <= ?";
	private static final String FROM_UNTIL_QUERY = " AND CatalogDate >= ? AND CatalogDate <= ?";
	private static final String SOURCE_COLLECTION_FILTER_START = " AND (SourceCollection = ?";
	private static final String SOURCE_COLLECTION_FILTER_MIDDLE = " OR SourceCollection = ?";
	private static final String SOURCE_COLLECTION_FILTER_END = ")";
	private static final String BASE_IDENTIFIER_QUERY = "SELECT MetadataID,GlobalID,CatalogDate from Metadata WHERE Private=0 AND GlobalID is not null AND CatalogDate is not null";
	private static final String ORDER_QUERY =" ORDER BY MetadataID";

	public HEALDataAccessor(final HEALProviderConfig config,final DataSource dataSource) {
		this.config = config;		
		this.dataSource = dataSource;
		catalogFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		metadataDAO = new MetadataDAO();
		metadataDAO.setDataSource(this.dataSource);
	}
	
	public OAIResumptionToken parseResumptionToken(final String resumptionToken) {
		//returns null if the token is poorly formatted
		return BasicResumptionToken.parseResumptionToken(resumptionToken,config.getGranularity());
	}
	
	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#openRecordsQuery(java.util.Date, java.util.Date, java.lang.String)
	 */
	public void openRecordsQuery(final String metadataPrefix, final Date from,final Date until,final String set) {
		//right now we don't differentiate on metadataPrefix. the prefixes we do support, we support for
		//all records
		this.metadataPrefix = metadataPrefix;
		this.from = from;
		this.until = until;
		this.set = set;
		nextID = null;
		queryType = HEALDataAccessor.QUERY_RECORD;
		// -1 means that we are doing a new query, not a resumption
		openQuery(BASE_RECORD_QUERY);
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#openRecordsQuery(java.lang.String)
	 */
	public void openRecordsQuery(final String identifier) {
		queryType = HEALDataAccessor.QUERY_RECORD;
		//XXX this is an ugly hack, need to get better aquainted with
		//working with HEAL infrastructure to avoid having to do two queries
		String idPrefix = config.getIDPrefix();
		int prefixIndex = identifier.indexOf(idPrefix);
		if (prefixIndex >= 0) {
			String globalId = identifier.substring(prefixIndex+idPrefix.length());
			try {
				conn = dataSource.getConnection();
				stmt = conn.prepareStatement("SELECT MetadataID from Metadata WHERE Private=0 AND GlobalID=?",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				stmt.setString(1,globalId);
				resultSet = stmt.executeQuery();			
				hasRows = resultSet.next();
				moveForwardOnNext = false;
				if (HEALDataAccessor.debug) {
					System.out.println("*******************************************");
					System.out.println("OpenRecordsQuery (id) ResultSet returned="+hasRows);
					System.out.println("*******************************************");
				}
				recordsReturned = 0;
			} catch (SQLException ex) {
				if (HEALDataAccessor.debug) {
					System.out.println("*******************************************");
					System.out.println("Error occured getting single record query:");
					ex.printStackTrace();
					System.out.println("*******************************************");
				}
				cleanupAfterQuery();
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#getIdentifiersQuery(java.util.Date, java.util.Date, java.lang.String)
	 */
	public void openIdentifiersQuery(final String metadataPrefix,final Date from, final Date until, final String set) {
		this.metadataPrefix = metadataPrefix;
		this.from = from;
		this.until = until;
		this.set = set;
		nextID = null;
		queryType = HEALDataAccessor.QUERY_IDENTIFIER;
		// -1 means that we are doing a new query, not a resumption
		openQuery(BASE_IDENTIFIER_QUERY);
	}

	public void openSetQuery() {
		//does nothing since we don't support sets
		queryType = HEALDataAccessor.QUERY_SET;
	}
  
	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#resumeQuery(org.heal.module.oai.provider.OAIResumptionToken)
	 */
	public boolean resumeRecordQuery(final OAIResumptionToken resumptionToken) {
		resumeQuery(resumptionToken);
		queryType = QUERY_RECORD;
		openQuery(BASE_RECORD_QUERY);
		return true;
	}
	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#resumeQuery(org.heal.module.oai.provider.OAIResumptionToken)
	 */
	public boolean resumeSetQuery(final OAIResumptionToken resumptionToken) {
		//HEALDataAccessor does not support sets.
		return false;
	}
	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#resumeQuery(org.heal.module.oai.provider.OAIResumptionToken)
	 */
	public boolean resumeIdentifierQuery(final OAIResumptionToken resumptionToken) {
		resumeQuery(resumptionToken);
		queryType = QUERY_IDENTIFIER;
		openQuery(BASE_IDENTIFIER_QUERY);
		return true;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#resumeQuery(org.heal.module.oai.provider.OAIResumptionToken)
	 */
	private void resumeQuery(final OAIResumptionToken resumptionToken) {
		resumedQuery = true;
		BasicResumptionToken brt = (BasicResumptionToken) resumptionToken;
		int resumptionID = Integer.parseInt(brt.getNextID());
		metadataPrefix = brt.getMetadataPrefix();
		from = brt.getFrom();
		until = brt.getUntil();
		set = brt.getSet();
		nextID = brt.getNextID();
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#getCurrentIdentifier()
	 */
	public OAIRecord getNextIdentifier() {
		OAIRecord result = null;
		if (resultSet != null && hasRows &&
			(queryType == HEALDataAccessor.QUERY_IDENTIFIER)) {
				try {
					if (!moveForwardOnNext) {
						//we just do this for the first row to see if we have any rows
						//in order to be able to return the appropriate value for
						//hasMoreElements
						moveForwardOnNext = true;
					} else {
						hasRows=resultSet.next();
					}
					if (hasRows) {
						String oaiid = null;
						if (isIdentifierValid(resultSet.getString("GlobalD"))) {
							oaiid = config.getIDPrefix()+resultSet.getString("GlobalID");						
						}
						Date dateDate = catalogFormatter.parse(resultSet.getString("CatalogDate"));
						result = new HEALRecord(oaiid,dateDate,config);			
						if (result.checkIsValid()) {
							recordsReturned++;
						}
					}
				} catch (ParseException ex2) {
					if (HEALDataAccessor.debug) {
						System.out.println("*******************************************");
						System.out.println("Error occured parsing date for next identifier:");
						ex2.printStackTrace();
						System.out.println("*******************************************");
					}
					cleanupAfterQuery();
				} catch (SQLException ex) {
					if (HEALDataAccessor.debug) {
						System.out.println("*******************************************");
						System.out.println("Error occured getting next identifier:");
						ex.printStackTrace();
						System.out.println("*******************************************");
					}
					cleanupAfterQuery();
				}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#getCurrentRecord()
	 */
	public OAIRecord getNextRecord() {
		OAIRecord retval = null;
		if (resultSet != null && hasRows &&
			(queryType == HEALDataAccessor.QUERY_RECORD)) {
			try {
				if (!moveForwardOnNext) {
					//we just do this for the first row to see if we have any rows
					//in order to be able to return the appropriate value for
					//hasMoreElements
					moveForwardOnNext = true;
				} else {
					hasRows=resultSet.next();
				}
				if (hasRows) {
					String metadataId = resultSet.getString("MetadataID");
					CompleteMetadataBean metadata = metadataDAO.getCompleteMetadata(metadataId);
					Date dateStamp = metadata.getCatalogDate();
					String identifier = null;
					if (isIdentifierValid(metadata.getGlobalId())) {
						identifier = config.getIDPrefix()+metadata.getGlobalId();
					}
					retval = new HEALRecord(metadata,config,identifier,dateStamp);
					if (retval.checkIsValid()) {
						recordsReturned++;
					}
				}
			} catch (ParseException ex2) {
				if (HEALDataAccessor.debug) {
					System.out.println("*******************************************");
					System.out.println("Error occured parsing date for next record:");
					ex2.printStackTrace();
					System.out.println("*******************************************");
				}
				cleanupAfterQuery();
			} catch (SQLException ex) {
				System.out.println("*******************************************");
				System.out.println("Error occured getting next record:");
				ex.printStackTrace();
				System.out.println("*******************************************");
				cleanupAfterQuery();
			}
		}
		return retval;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#hasMoreElements()
	 */

	public boolean hasMoreElements() {
		boolean result = false;
		if (resultSet != null && hasRows && 
			(queryType == HEALDataAccessor.QUERY_RECORD ||
			queryType == HEALDataAccessor.QUERY_IDENTIFIER)) {
				try {
					if (moveForwardOnNext) {
						result = !resultSet.isAfterLast();
						if (HEALDataAccessor.debug) {
							System.out.println("*******************************************");
							System.out.println("hasMoreElements !ResultSet.isAfterLast returned="+result);
							System.out.println("*******************************************");
						}
					} else {
						//we know this is true because we just performed
						//the query (moveForwardOnNext == false), 
						// and we have results (hasRows == true)
						result = true;
						if (HEALDataAccessor.debug) {
							System.out.println("*******************************************");
							System.out.println("hasMoreElements moveForwardOnNext was false");
							System.out.println("*******************************************");
						}
					}
				} catch (SQLException ex) {
					if (HEALDataAccessor.debug) {
						System.out.println("*******************************************");
						System.out.println("Error occured checking afterLast:");
						ex.printStackTrace();
						System.out.println("*******************************************");
					}
					cleanupAfterQuery();
				}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#getResumptionToken()
	 */
	public OAIResumptionToken getResumptionToken() {
		try {
			if (queryType != HEALDataAccessor.QUERY_SET) {
				int threshold = config.getResumptionThreshold();
				if ((threshold > 0) && 
					(recordsReturned >= threshold) && 
					!resultSet.isAfterLast()) {
					nextID = Integer.toString(resultSet.getInt("MetadataID"));
					BasicResumptionToken resumptionToken = BasicResumptionToken.generateResumptionToken(metadataPrefix,from,until,set,nextID,config.getGranularity());
					resumptionToken.setCursor(recordsReturned);
					return resumptionToken;			
				}
			}
		} catch (SQLException ex) {
			if (HEALDataAccessor.debug) {
				System.out.println("*******************************************");
				System.out.println("Error occured while creating resumption token:");
				ex.printStackTrace();
				System.out.println("*******************************************");
			}
			cleanupAfterQuery();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#closeQuery()
	 */
	public void closeQuery() {
		//no work to do yet
		cleanupAfterQuery();
	}

	public Date getNow() {
		// TODO HEALDataAccessor.getNow
		return new Date();
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#getMetadataFormats(java.lang.String)
	 */
	public OAIMetadataFormat[] getMetadataFormats(final String identifier) {
		//first check to see if the id exists...
		if (identifier != null) {
			openRecordsQuery(identifier);
			if (!hasMoreElements()) {			
				//can't find the record
				closeQuery();
				return null;			
			}
			closeQuery();
		}
		//if it doesn't exist, return null
		//otherwise, return all formats (we're not selective yet)
		return config.getAllMetadataFormats();
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#validateIdentifier(java.lang.String)
	 * we check to see if it starts correctly and whether or not there is a space or a paren
	 * either of which would be necessary to insert a malicious SQL query
	 */
	public boolean validateIdentifier(final String identifier) {
		return (identifier.startsWith(config.getIDPrefix()) && 
				(identifier.indexOf(' ') == -1) && (identifier.indexOf('(') == -1));
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#validateSet(java.lang.String)
	 */
	public boolean validateSet(final String set) {
		//HEAL does not currently support sets, so there should never be a set parameter to validate
		return false;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#isExpired(org.heal.module.oai.provider.OAIResumptionToken)
	 */
	public boolean isExpired(final OAIResumptionToken resumptionToken) {
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
		return null;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.DataAccessor#setExists(java.lang.String)
	 */
	public boolean setExists(final String set) {
		//we don't support sets
		return false;
	}

	/*
	 * uses the base select statement and a potential resumptionID (should be
	 * -1 if we aren't resuming) to generate and dispatch the sql query
	 */
	private void openQuery(final String baseQuery) {
		try {
			conn = dataSource.getConnection();
			String resumptionString = "";
			int numArguments = 0;
			int resumptionID = -1;
			if (nextID != null) {
				resumptionString = RESUMPTION_QUERY;
				resumptionID = Integer.parseInt(nextID);	
			}
			String finalQuery;
			String[] sourceCollections = config.getAllowedCollections();
			String sourceCollectionFilter = "";
			if (sourceCollections != null) {
				StringBuffer colls = new StringBuffer();
				colls.append(SOURCE_COLLECTION_FILTER_START);
				//we already had one for the first element, so
				//we start with the second
				for (int i=1;i<sourceCollections.length;i++) {
					colls.append(SOURCE_COLLECTION_FILTER_MIDDLE);
				}
				colls.append(SOURCE_COLLECTION_FILTER_END);
				sourceCollectionFilter = colls.toString();
			}
			if (from!=null && until!= null) {
				finalQuery = baseQuery+FROM_UNTIL_QUERY+resumptionString+sourceCollectionFilter+ORDER_QUERY;
				stmt = conn.prepareStatement(finalQuery,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				stmt.setDate(1,new java.sql.Date(from.getTime()));
				stmt.setDate(2,new java.sql.Date(until.getTime()));
				numArguments = 2;
			} else if (from != null) {
				finalQuery = baseQuery+FROM_QUERY+resumptionString+sourceCollectionFilter+ORDER_QUERY;
				stmt = conn.prepareStatement(finalQuery,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				stmt.setDate(1,new java.sql.Date(from.getTime()));
				numArguments = 1;
			} else if (until != null) {
				finalQuery = baseQuery+UNTIL_QUERY+resumptionString+sourceCollectionFilter+ORDER_QUERY;
				stmt = conn.prepareStatement(finalQuery,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				stmt.setDate(1,new java.sql.Date(until.getTime()));
				numArguments = 1;
			} else {
				finalQuery = baseQuery+resumptionString+sourceCollectionFilter+ORDER_QUERY;
				stmt = conn.prepareStatement(finalQuery,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				numArguments = 0;
			}
			if (resumptionID >= 0) {
				numArguments++;
				stmt.setInt(numArguments,resumptionID);
			}
			if (sourceCollections != null) {
				for (int i=0;i<sourceCollections.length;i++) {
					//okay, so we've already added one past the num of args, so
					//we need to add two plus the index here
					numArguments++;
					stmt.setString(numArguments,sourceCollections[i]);
				}
			}
			if (HEALDataAccessor.debug) {
				System.out.println("Executing SQL query ["+finalQuery+"], metadata id="+resumptionID);		
			}
			resultSet = stmt.executeQuery();			
			if (HEALDataAccessor.debug) {
				System.out.println("Query returned");		
			}
			hasRows = resultSet.next();
			if (HEALDataAccessor.debug) {
				System.out.println("hasRows="+hasRows);		
			}
			moveForwardOnNext = false;
			if (HEALDataAccessor.debug) {
				System.out.println("*******************************************");
				System.out.println("openQuery(base) ResultSet returned="+hasRows);
				System.out.println("*******************************************");
			}
			recordsReturned = 0;
		} catch (SQLException ex) {
			if (HEALDataAccessor.debug) {
				System.out.println("*******************************************");
				System.out.println("Error occured opening query:");
				ex.printStackTrace();
				System.out.println("*******************************************");
			}
			cleanupAfterQuery();
		}				
	}

	private boolean isIdentifierValid(String identifier) {
		return !(identifier == null || "null".equalsIgnoreCase(identifier) || "<null>".equalsIgnoreCase(identifier));
	}
	
	private void cleanupAfterQuery() {
		hasRows = false;
		recordsReturned = 0;
		moveForwardOnNext = true;
		nextID = null;
		from = null;
		until = null;
		metadataPrefix = null;
		set = null;
		queryType = -1;
		if (resultSet !=  null) {
			try {
				resultSet.close();
				resultSet = null;				
			} catch (SQLException ex3) {
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException ex2) {
			}
		}
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException ex2) {
			}
		}
	}
}
