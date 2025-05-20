/*
 * Created on Dec 1, 2004
 *
 */
package org.heal.module.oai.provider;

/**
 * @author Seth Wright
 *
 */
public interface OAISet {
/* from spec:
 *    <element name="setSpec" type="oai:setSpecType"/>
 *    <element name="setName" type="string"/>
 *    <element name="setDescription" type="oai:descriptionType" 
 *             minOccurs="0" maxOccurs="unbounded"/>
 */
	public String getSpec();
	public String getName();
	public String[] getDescriptions();
}
