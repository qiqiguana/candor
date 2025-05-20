/*
 * Created on Dec 1, 2004
 *
 */
package org.heal.module.oai.provider;

/**
 * @author Seth Wright
 *
 */
public interface OAIMetadataFormat {
	/* From the spec:
	 *   <complexType name="metadataFormatType">
	 *     <sequence>
	 *       <element name="metadataPrefix" type="oai:metadataPrefixType"/>
	 *       <element name="schema" type="anyURI"/>
	 *       <element name="metadataNamespace" type="anyURI"/>
	 *     </sequence>
	 *   </complexType>
	 */
	public void setPrefix(final String prefix);
	public String getPrefix();
	//this is used for the metadata's <schema> field
	public String getSchema();
	//this is used in generating the XML to use for the schemaLocation tag.
	public String getSchemaLocation();
	public String getNamespace();
	public OAIMetadataHandler getHandler();
}
