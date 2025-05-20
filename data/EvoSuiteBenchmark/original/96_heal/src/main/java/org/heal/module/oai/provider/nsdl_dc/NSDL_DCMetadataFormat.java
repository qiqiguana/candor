/*
 * Created on Dec 1, 2004
 *
 */
package org.heal.module.oai.provider.nsdl_dc;

import org.heal.module.oai.provider.OAIMetadataFormat;
import org.heal.module.oai.provider.OAIMetadataHandler;

/**
 * @author Seth Wright
 *
 */
public class NSDL_DCMetadataFormat implements OAIMetadataFormat {
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
		return "http://ns.nsdl.org/schemas/nsdl_dc/nsdl_dc_v1.02.xsd";
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIMetadataFormat#getNamespace()
	 */
	public String getNamespace() {
		return "http://ns.nsdl.org/schemas/nsdl_dc/";
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIMetadataFormat#getHandler()
	 */
	public OAIMetadataHandler getHandler() {
		return new NSDL_DCHandler(this);
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIMetadataFormat#getSchemaLocation()
	 */
	public String getSchemaLocation() {
		return "http://ns.nsdl.org/schemas/nsdl_dc/ http://ns.nsdl.org/schemas/nsdl_dc/nsdl_dc_v1.02.xsd";
	}
}
