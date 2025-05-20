/*
 * Created on Dec 1, 2004
 *
 */
package org.heal.module.oai.heal;

import java.io.IOException;
import java.io.PrintWriter;

import org.heal.module.oai.provider.OAIMetadataFormat;
import org.heal.module.oai.provider.OAIMetadataHandler;
import org.heal.module.oai.provider.OAIRecordAdapter;

/**
 * @author Seth Wright
 *
 */
public class HEALHandler implements OAIMetadataHandler {
	private final OAIMetadataFormat metadataFormat;

	public HEALHandler(final OAIMetadataFormat format) {
		metadataFormat = format;
	}

	public OAIMetadataFormat getFormat() {
		return metadataFormat;
	}
		
	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIMetadataHandler#writeMetadata(org.heal.module.oai.provider.OAIRecord, java.io.PrintWriter)
	 */
	public void writeMetadata(final OAIRecordAdapter record, final PrintWriter writer) throws IOException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIMetadataHandler#writeAbout(org.heal.module.oai.provider.OAIRecord, java.io.PrintWriter)
	 */
	public void writeAbout(final OAIRecordAdapter record, final PrintWriter writer) throws IOException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIMetadataHandler#writeOAIAbout(org.heal.module.oai.provider.OAIRecordAdapter, java.io.PrintWriter)
	 */
	public void writeOAIAbout(OAIRecordAdapter record, PrintWriter writer) throws IOException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIMetadataHandler#writeCustomAbout(org.heal.module.oai.provider.OAIRecordAdapter, java.io.PrintWriter)
	 */
	public void writeCustomAbout(OAIRecordAdapter record, PrintWriter writer) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
