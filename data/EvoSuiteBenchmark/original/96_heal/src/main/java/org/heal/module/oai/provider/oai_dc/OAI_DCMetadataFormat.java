/*
 * Created on Dec 1, 2004
 *
 */
package org.heal.module.oai.provider.oai_dc;

import org.heal.module.oai.provider.OAIMetadataFormat;
import org.heal.module.oai.provider.OAIMetadataHandler;

/**
 * @author Seth Wright
 *
 */
public class OAI_DCMetadataFormat implements OAIMetadataFormat {
	private String prefix = null;
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIMetadataFormat#getPrefix()
	 */
	public String getPrefix() {
		return prefix;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIMetadataFormat#getSchema()
	 */
	public String getSchema() {
		return "http://www.openarchives.org/OAI/2.0/oai_dc.xsd";
	}

	public String getSchemaLocation() {
		return "http://www.openarchives.org/OAI/2.0/oai_dc/ http://www.openarchives.org/OAI/2.0/oai_dc.xsd";
	}
	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIMetadataFormat#getNamespace()
	 */
	public String getNamespace() {
		return "http://www.openarchives.org/OAI/2.0/oai_dc/";
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIMetadataFormat#getHandler()
	 */
	public OAIMetadataHandler getHandler() {
		return new OAI_DCHandler(this);
	}

}
