/*
 * Created on Dec 1, 2004
 *
 */
package org.heal.module.oai.heal;

import org.heal.module.oai.provider.OAIMetadataFormat;
import org.heal.module.oai.provider.OAIMetadataHandler;

/**
 * @author Seth Wright
 *
 */
public class HEALMetadataFormat implements OAIMetadataFormat {
	private String prefix = null;
	
	public void setPrefix(final String prefix) {
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
		return "http://www.healcentral.org/services/schema/HEALmdSchemaXMLv1p5.xsd";
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIMetadataFormat#getNamespace()
	 */
	public String getNamespace() {
		return "http://www.healcentral.org/xsd/healmd_v1p5";
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIMetadataFormat#getHandler()
	 */
	public OAIMetadataHandler getHandler() {
		return new HEALHandler(this);
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIMetadataFormat#getSchemaLocation()
	 */
	public String getSchemaLocation() {
		return "http://www.healcentral.org/services/schema/ http://www.healcentral.org/services/schema/HEALmdSchemaXMLv1p5.xsd";
	}

}
