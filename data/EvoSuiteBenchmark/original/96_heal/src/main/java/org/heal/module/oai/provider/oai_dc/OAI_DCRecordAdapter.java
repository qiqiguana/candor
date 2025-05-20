/*
 * Created on Dec 1, 2004
 *
 */
package org.heal.module.oai.provider.oai_dc;

import org.heal.module.oai.provider.OAIRecordAdapter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author swright
 *
 */
public interface OAI_DCRecordAdapter extends OAIRecordAdapter {	
	//allow the record adapter to add any required namespaces
	public void addRecordAttributes(Element recordElem,Document doc);
	public void addOAIAboutAttributes(Element recordElem,Document doc);

	//now add the elements
	public void appendOAIAboutElements(Element recordElement,Document doc);
	public void appendTitleElements(Element recordElement,Document doc);
	public void appendCreatorElements(Element recordElement,Document doc);
	public void appendSubjectElements(Element recordElement,Document doc);
	public void appendDescriptionElements(Element recordElement,Document doc);
	public void appendPublisherElements(Element recordElement,Document doc);
	public void appendContributorElements(Element recordElement,Document doc);
	public void appendDateElements(Element recordElement,Document doc);
	public void appendTypeElements(Element recordElement,Document doc);
	public void appendFormatElements(Element recordElement,Document doc);
	public void appendIdentifierElements(Element recordElement,Document doc);
	public void appendSourceElements(Element recordElement,Document doc);
	public void appendLanguageElements(Element recordElement,Document doc);
	public void appendRelationElements(Element recordElement,Document doc);
	public void appendCoverageElements(Element recordElement,Document doc);
	public void appendRightsElements(Element recordElement,Document doc);
}
