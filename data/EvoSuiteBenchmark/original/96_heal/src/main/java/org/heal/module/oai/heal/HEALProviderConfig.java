/*
 * Created on Dec 1, 2004
 * 
 */
package org.heal.module.oai.heal;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import org.heal.module.oai.provider.OAIGranularity;
import org.heal.module.oai.provider.OAIMetadataFormat;
import org.heal.module.oai.provider.OAIProviderConfig;
import org.heal.util.FileLocator;

/**
 * @author Seth Wright
 *
 */
public class HEALProviderConfig implements OAIProviderConfig {
	private static final boolean debug = true;

	private final OAIMetadataFormat[] metadataFormats;
	private final HashMap formatMap = new HashMap();
	private Date earliest;
	private String idPrefix = "oai:org.heal:";
	private int resumptionThreshold = 5;
	private String coverage = "health-sciences";
	private String metametadataRole = "primary";
	private OAIGranularity granularity = OAIGranularity.yearMonthDayHourMinuteSecond;
	private String[] adminEmails = {"info@healcentral.org"};
	private String[] descriptions = {"The Health Education Assets Library (HEAL) is a digital library of freely accessible, web-based multimedia teaching materials that meet the needs of today's health sciences educators and learners. The HEAL collection currently contains a number of collections of multimedia resources for health sciences undergraduate and professional education, as well as resources for patient and consumer health."};
	private String healVCard = "begin:vcard\nn:Health Education Assets Library (HEAL)\nurl:http://www.healcentral.org\nemail;type=internet:info@healcentral.org\nfn:Health Education Assets Library (HEAL)\nend:vcard";
	private String[] allowedCollections = {"Peer Review Pending","HEAL Reviewed Collection"};
	private String repositoryName = "Health Education Assets Library";
	private String baseURL = "http://www.healcentral.org/heal/oaiprovider";
	private String contentPrefix = "http://www.healcentral.org/content";

	public HEALProviderConfig(String idPrefix,
							  int resumptionThreshold,
							  String coverage,
							  String metametadataRole,
							  String granularity,
							  String[] descriptions,
							  String[] adminEmails,
							  String healVCard,
							  String[] allowedCollections,
							  String repositoryName,
							  String baseURL,
							  OAIMetadataFormat[] formats,
							  String contentPrefix) {
		metadataFormats = formats;
		for (int i=0;i<formats.length;i++) {
			formatMap.put(formats[i].getPrefix(),formats[i]);
		}
		if (idPrefix != null) {
			this.idPrefix = idPrefix;
		}
		this.resumptionThreshold = resumptionThreshold;
		if (coverage != null) {
			this.coverage = coverage;
		}
		if (metametadataRole != null) {
			this.metametadataRole = metametadataRole;			
		}
		if (granularity != null) {
			this.granularity = OAIGranularity.getGranularity(granularity);
			if (this.granularity == null) {
				throw new IllegalArgumentException("'"+granularity + "' is not a valid granularity setting.");
			}
		}
		if (descriptions != null) {
			this.descriptions = descriptions;
		}
		if (adminEmails != null) {
			this.adminEmails = adminEmails;
		}
		if (healVCard != null) {
			this.healVCard = healVCard;
		}
		if (repositoryName != null) {
			this.repositoryName = repositoryName;
		}
		if (baseURL != null) {
			this.baseURL = baseURL;
		}
		try {
			earliest = OAIGranularity.yearMonthDayHourMinuteSecond.parse("2000-09-01T0:00:00Z");
		} catch (ParseException ex) {
			//DISPLAY CONFIG ERROR SOMEHOW
			//default to earliest java date available, January 1, 1970
			earliest = new Date(0);
		}
		this.contentPrefix = contentPrefix;
	}

	/* Constructs a date from the given string using the granularity
	 * of this provider
	 * Returns null if the parsing fails
	 */
	public Date getDateFromString(String dateStr) {
		Date result = null;
		try {
			result = granularity.parse(dateStr);		
		} catch (ParseException ex) {
			if (HEALProviderConfig.debug) {
				System.out.println("*******************************************");
				System.out.println("Error occured getting date:");
				ex.printStackTrace();
				System.out.println("*******************************************");
			}
		}
		return result;
	}
	
	/* Constructs a date from the given string using the granularity
	 * of this provider
	 * Returns null if an error occurs
	 */
	public String getStringFromDate(Date dateDate) {
		return granularity.format(dateDate);		
	}

	//These two are for the HEALDataAccessor
	/*
	 * returns the maximum number of records the provider should return before
	 * issuing a resumption tokens.
	 * a return value of -1 means that the provider doesn't stop until the
	 * end of the query has been reached and no resumption tokens will be
	 * given.
	 */
	public int getResumptionThreshold() {
		return resumptionThreshold;		
	}

	/* The prefix tacked onto the beginning of the GlobalIDs to 
	 * create the OAI identifier
	 */
	public String getIDPrefix() {
		return idPrefix;
	}	
	
	public String[] getAllowedCollections() {
		return allowedCollections;
	}
	//These are for the HEALOAIDCAdapter
	public String getHEALVCard() {
		return healVCard;
	}
	public String getMetametadataRole() {
		return metametadataRole;
	}
	public String getCoverage() {
		return coverage;	
	}
	
	/* All of the metadata formats supported by the server.
	 * This should at the least contain the oai_dc format.
	 * This returns an ArrayList of string identifiers.
	 */
	public OAIMetadataFormat[] getAllMetadataFormats() {
		return metadataFormats;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIProviderConfig#getRepositoryName()
	 */
	public String getRepositoryName() {
		return repositoryName;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIProviderConfig#getBaseURL()
	 */
	public String getBaseURL() {
		return baseURL;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIProviderConfig#getAdminEmails()
	 */
	public String[] getAdminEmails() {
		return adminEmails;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIProviderConfig#getEarliestDatestamp()
	 */
	public Date getEarliestDatestamp() {
		//HEAL didn't receive funding until September of 2000...
		//this should be configurable in a config file.
		return earliest;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIProviderConfig#getDeletedRecord()
	 */
	public String getDeletedRecord() {
		return "no";
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIProviderConfig#getGranularity()
	 */
	public OAIGranularity getGranularity() {
		return granularity;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIProviderConfig#getCompressions()
	 */
	public String[] getCompressions() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIProviderConfig#getDescriptions()
	 */
	public String[] getDescriptions() {
		return descriptions;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIProviderConfig#getMetadataFormat(java.lang.String)
	 */
	public OAIMetadataFormat getMetadataFormat(final String prefix) {
		return (OAIMetadataFormat)formatMap.get(prefix);
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIProviderConfig#getRepositoryIdentifier()
	 */
	public String getRepositoryIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIProviderConfig#getIdentifierDelimiter()
	 */
	public String getIdentifierDelimiter() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIProviderConfig#getSampleIdentifier()
	 */
	public String getSampleIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getContentPrefix() {
		return contentPrefix;
	}
	
	//Below are the HEAL specific configuration options:
}
