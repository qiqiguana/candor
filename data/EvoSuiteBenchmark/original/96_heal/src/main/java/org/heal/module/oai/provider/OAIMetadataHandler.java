/*
 * Created on Dec 1, 2004
 *
 */
package org.heal.module.oai.provider;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Seth Wright
 *
 */
public interface OAIMetadataHandler {
	//gets the prefix that identifies this type of metadata, e.g. oai_dc, or nsdl_dc
	public OAIMetadataFormat getFormat();
	public void writeMetadata(final OAIRecordAdapter record, final PrintWriter writer) throws IOException;
	public void writeOAIAbout(final OAIRecordAdapter record, final PrintWriter writer) throws IOException;
	public void writeCustomAbout(final OAIRecordAdapter record, final PrintWriter writer) throws IOException;
}
