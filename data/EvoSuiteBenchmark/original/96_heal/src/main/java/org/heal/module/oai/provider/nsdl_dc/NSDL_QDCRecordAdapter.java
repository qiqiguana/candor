/*
 * Created on Mar 27, 2005
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.heal.module.oai.provider.nsdl_dc;

import org.heal.module.oai.provider.OAIRecordAdapter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author swright
 *
 */
public interface NSDL_QDCRecordAdapter extends OAIRecordAdapter {
	public boolean hasNSDLAbout();
	
	public void addRecordAttributes(Element recordElem, Document doc);
	public void addOAIAboutAttributes(Element recordElem,Document doc);

	//now add the elements
	public void appendOAIAboutElements(Element recordElement,Document doc);
	public void appendIdentifierElements(Element recordElement,Document doc);
	public void appendTitleElements(Element recordElement,Document doc);
	public void appendSubjectElements(Element recordElement,Document doc);
	public void appendEducationLevelElements(Element recordElement, Document doc);
	public void appendInteractivityTypeElement(Element recordElement, Document doc);
	public void appendInteractivityLevelElement(Element recordElement, Document doc);
	public void appendTypicalLearningTimeElement(Element recordElement, Document doc);
	public void appendContributorElements(Element recordElement, Document doc);
	public void appendCoverageElements(Element recordElement, Document doc);
	public void appendCreateorElements(Element recordElement, Document doc);
	public void appendDateElements(Element recordElement, Document doc);
	public void appendDescriptionElements(Element recordElement, Document doc);
	public void appendFormatElements(Element recordElement, Document doc);
	public void appendLanguageElements(Element recordElement, Document doc);
	public void appendPublisherElements(Element recordElement, Document doc);
	public void appendRelationElements(Element recordElement, Document doc);
	public void appendRightsElements(Element recordElement, Document doc);
	public void appendSourceElements(Element recordElement, Document doc);
	public void appendTypeElements(Element recordElement, Document doc);
	public void appendDCTAccessibilityElements(Element recordElement, Document doc);
	public void appendDCTAccrualMethodElements(Element recordElement, Document doc);
	public void appendDCTAccrualPeriodicityElements(Element recordElement, Document doc);
	public void appendDCTAccrualPolicyElements(Element recordElement, Document doc);
	public void appendDCTAudienceElements(Element recordElement, Document doc);
	public void appendDCTMediatorElements(Element recordElement, Document doc);
	public void appendDCTEducationLevelElements(Element recordElement, Document doc);
	public void appendDCTSpatialElements(Element recordElement, Document doc);
	public void appendDCTTemporalElements(Element recordElement, Document doc);
	public void appendDCTAvailableElements(Element recordElement, Document doc);
	public void appendDCTCreatedElements(Element recordElement, Document doc);
	public void appendDCTDateAcceptedElements(Element recordElement, Document doc);
	public void appendDCTDateCopyrightedElements(Element recordElement, Document doc);
	public void appendDCTDateSubmittedElements(Element recordElement, Document doc);
	public void appendDCTIssuedElements(Element recordElement, Document doc);
	public void appendDCTModifiedElements(Element recordElement, Document doc);
	public void appendDCTValidElements(Element recordElement, Document doc);
	public void appendDCTAbstractElements(Element recordElement, Document doc);
	public void appendDCTTableOfContentsElements(Element recordElement, Document doc);
	public void appendDCTExtentElements(Element recordElement, Document doc);
	public void appendDCTMediumElements(Element recordElement, Document doc);
	public void appendDCTBibliograhicCitationElements(Element recordElement, Document doc);
	public void appendDCTInstructionalMethodElements(Element recordElement, Document doc);
	public void appendDCTProvenanceElements(Element recordElement, Document doc);
	public void appendDCTConformsToElements(Element recordElement, Document doc);
	public void appendDCTIsFormatOfElements(Element recordElement, Document doc);
	public void appendDCTHasFormatElements(Element recordElement, Document doc);
	public void appendDCTIsPartOfElements(Element recordElement, Document doc);
	public void appendDCTHasPartElements(Element recordElement, Document doc);
	public void appendDCTIsReferencedByElements(Element recordElement, Document doc);
	public void appendDCTReferencesElements(Element recordElement, Document doc);
	public void appendDCTIsReplacedByElements(Element recordElement, Document doc);
	public void appendDCTReplacesElements(Element recordElement, Document doc);
	public void appendDCTIsRequiredByElements(Element recordElement, Document doc);
	public void appendDCTRequiresElements(Element recordElement, Document doc);
	public void appendDCTIsVersionOfElements(Element recordElement, Document doc);
	public void appendDCTHasVersionElements(Element recordElement, Document doc);
	public void appendDCTAccessRightsElements(Element recordElement, Document doc);
	public void appendDCTLicenseElements(Element recordElement, Document doc);
	public void appendDCTRightsHolderElements(Element recordElement, Document doc);
	public void appendDCTAlternativeElements(Element recordElement, Document doc);
}
