/*
 * Created on Dec 1, 2004
 *
 */
package org.heal.module.oai.heal;

import java.text.ParseException;
import java.util.Date;

import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.oai.provider.OAIMetadataFormat;
import org.heal.module.oai.provider.OAIRecord;
import org.heal.module.oai.provider.OAIRecordAdapter;
import org.heal.module.oai.provider.OAISet;
import org.heal.module.oai.provider.nsdl_dc.NSDL_DCMetadataFormat;
import org.heal.module.oai.provider.oai_dc.OAI_DCMetadataFormat;

/**
 * @author Seth Wright
 */
public class HEALRecord implements OAIRecord {
	private final CompleteMetadataBean metadataBean;
	private final HEALProviderConfig config;
	private final Date dateStamp;
	private final String identifier;
	
	/*
	 * Throws a parse exception if the catalogdate is invalid 
	 */
	public HEALRecord(final CompleteMetadataBean metadataBean,final HEALProviderConfig config,String oaiid,Date dateStamp) throws ParseException {
		this.metadataBean = metadataBean;
		this.config = config;
		this.dateStamp = dateStamp;
		this.identifier = oaiid;
	}

	public HEALRecord(final String oaiid,final Date dateStamp,final HEALProviderConfig config) throws ParseException {
		this(null,config,oaiid,dateStamp);
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIRecord#supportsMetadataFormat(java.lang.String)
	 */
	public boolean supportsMetadataFormat(final OAIMetadataFormat format) {
		//HEAL supports all formats for all records
		boolean result = false;
		//as long as we have only a few formats, not hundreds, this is
		//efficient enough
		String metadataPrefix = format.getPrefix();
		OAIMetadataFormat[] formats = config.getAllMetadataFormats();		
		for (int i=0;i<formats.length && !result;i++) {
			result = formats[i].getPrefix().equals(metadataPrefix);
		}
		return result;
	}

	/* This is where we evaluate whether or not this record is valid to be returned to the
	 * end user in the XML.  For instance, we should return false if the identifier is null
	 * or invalid or whatever future sanity checks need to be put in place.  This filter is
	 * very slow in comparison to the SQL selects, so preferably such checks would be put there
	 * but this is a safeguard.
	 * @see org.heal.module.oai.provider.OAIRecord#checkIsValid()
	 */
	public boolean checkIsValid() {
		if (identifier == null) return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIRecord#getOAIIdentifier()
	 */
	public String getOAIIdentifier() {
		return identifier;		
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIRecord#getDateStamp()
	 */
	public Date getDateStamp() {
		return dateStamp;
	}

	public OAIRecordAdapter getRecordAdapter(final OAIMetadataFormat format) {
		if (metadataBean != null) {
			if (format instanceof OAI_DCMetadataFormat) {
			//pass in the complete metadata
				return new HEALOAIDCRecordAdapter(metadataBean,config);
			} else if (format instanceof NSDL_DCMetadataFormat) {
				return new HEALNSDLQDCRecordAdapter(metadataBean,config);
			}
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIRecord#getSets()
	 */
	public OAISet[] getSets() {
		//we don't support sets yet
		return null;
	}
	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIRecord#isDeleted()
	 */
	public boolean isDeleted() {
		//HEAL doesn't support deleted metadata
		return false;
	}
}
