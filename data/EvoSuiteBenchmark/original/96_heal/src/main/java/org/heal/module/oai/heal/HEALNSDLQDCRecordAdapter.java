/*
 * Created on Apr 10, 2005
 *
 */
package org.heal.module.oai.heal;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.ContextURLBean;
import org.heal.module.metadata.ContributorBean;
import org.heal.module.metadata.CopyrightBean;
import org.heal.module.metadata.CopyrightHolderBean;
import org.heal.module.metadata.CopyrightTextBean;
import org.heal.module.metadata.DiseaseDiagnosisBean;
import org.heal.module.metadata.FormatBean;
import org.heal.module.metadata.KeywordBean;
import org.heal.module.metadata.RelationBean;
import org.heal.module.metadata.RequirementBean;
import org.heal.module.metadata.TaxonBean;
import org.heal.module.metadata.TaxonPathBean;
import org.heal.module.oai.provider.OAIGranularity;
import org.heal.module.oai.provider.nsdl_dc.NSDL_DCHandler;
import org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter;
import org.heal.util.FileLocator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author swright
 *
 */
public class HEALNSDLQDCRecordAdapter implements NSDL_QDCRecordAdapter {

	private final CompleteMetadataBean metadataBean;
	private final HEALProviderConfig config;
	
	public HEALNSDLQDCRecordAdapter(final CompleteMetadataBean metadataBean,final HEALProviderConfig config) {
		this.metadataBean = metadataBean;
		this.config = config;
	}

	public boolean hasOAIAbout() {
		return false;
	}
	
	public boolean hasCustomAbout() {
		return true;
	}
	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#hasNSDLAbout()
	 */
	public boolean hasNSDLAbout() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#addRecordAttributes(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void addRecordAttributes(Element recordElem, Document doc) {
		recordElem.setAttribute("xmlns:"+HEALXMLHelper.HEALNS,HEALXMLHelper.HEALLOC);
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIRecordAdapter#addCustomAbout(org.w3c.dom.Document)
	 */
	public void addCustomAbout(Document doc) {
		HEALXMLHelper.addCustomAbout(metadataBean,config,doc);
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#addOAIAboutAttributes(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void addOAIAboutAttributes(Element recordElem, Document doc) {
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendOAIAboutElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendOAIAboutElements(Element recordElement, Document doc) {
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendIdentifierElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendIdentifierElements(Element recordElement, Document doc) {
		String location = metadataBean.getLocation();
		if (location != null && !location.startsWith("http://")) {
			location = config.getContentPrefix()+'/'+location;
		}		
		HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+":identifier",location,recordElement,doc);
		HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+":identifier",metadataBean.getGlobalId(),recordElement,doc);
/*
XXX TODO: fill in catalog and entry
		General CatalogEntry Catalogue = Location in the Metadata table; the default is http://www.healcentral.org; but some resources have other URLs, such as http://medstat.med.utah.edu/kw or http://www.nlm.nih.gov/medlineplus
		General CatalogEntry Entry = GlobalID from the Metadata table; this isn't technically accurate, but we haven't used IDs from the local catalogs
*/
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendTitleElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendTitleElements(Element recordElement, Document doc) {
		HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+":title",metadataBean.getTitle(),recordElement,doc);		
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendSubjectElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendSubjectElements(Element recordElement, Document doc) {
		Collection taxonpaths = metadataBean.getTaxonPaths();
		Iterator iter = taxonpaths.iterator();
		TaxonPathBean tpb;
		TaxonBean tb;
		while (iter.hasNext()) {
			tpb = (TaxonPathBean) iter.next();
			HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+":subject",tpb.getSource(),recordElement,doc);
			Collection taxons = tpb.getTaxons();
			Iterator titer = taxons.iterator();
			while (titer.hasNext()) {
				tb = (TaxonBean)titer.next();
				HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+":subject",tb.getId(),recordElement,doc);					
				HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+":subject",tb.getEntry(),recordElement,doc);					
			}
		}
		Collection keywords = metadataBean.getKeywords();
		iter = keywords.iterator();
		KeywordBean kb;
		while (iter.hasNext()) {
			kb = (KeywordBean)iter.next();
			HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+":subject",kb.getKeyword(),recordElement,doc);
		}
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendEducationLevelElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendEducationLevelElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub
		//context
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendInteractivityTypeElement(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendInteractivityTypeElement(Element recordElement,
			Document doc) {
		// TODO Auto-generated method stub
		//interactivity type
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendInteractivityLevelElement(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendInteractivityLevelElement(Element recordElement,
			Document doc) {
		// TODO Auto-generated method stub
		//interactivity level
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendTypicalLearningTimeElement(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendTypicalLearningTimeElement(Element recordElement,
			Document doc) {
		// TODO Auto-generated method stub
		//typical learning time
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendContributorElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendContributorElements(Element recordElement, Document doc) {
		Collection contributors = metadataBean.getContributorList();
		Iterator iter = contributors.iterator();
		ContributorBean contributor;
		String role,type = null;
		while (iter.hasNext()) {
			contributor = (ContributorBean)iter.next();
			role = contributor.getRole();
			type = null;			 
			
			if ("primary".equals(role)) {
				type = ":creator";
			} else if ("secondary".equals(role)) {
				type = ":contributor";
			} else if ("Content Provider".equals(role)) {
				type = ":publisher";
			}			
			HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+type,"Role: "+role,recordElement,doc);
			HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+type,contributor.getVCard(),recordElement,doc);
		}
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendCoverageElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendCoverageElements(Element recordElement, Document doc) {
		HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+":coverage",config.getCoverage(),recordElement,doc);
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendCreateorElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendCreateorElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub
		//lifecycle contribute role
		//if role is primary, then also lifecycle contribute centity
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDateElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDateElements(Element recordElement, Document doc) {
		Date contributeDate = metadataBean.getContributeDate();
		if (contributeDate != null) {
			String dateStr = OAIGranularity.yearMonthDayHourMinuteSecond.format(contributeDate);
			HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+":date",dateStr,recordElement,doc);
		}
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDescriptionElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDescriptionElements(Element recordElement, Document doc) {
		HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+":description",metadataBean.getDescription(),recordElement,doc);
		HEALXMLHelper.appendTextElemWithDescriptor(NSDL_DCHandler.QDC_NS+":description","Specimen Type",metadataBean.getSpecimenType(),recordElement,doc);
		HEALXMLHelper.appendTextElemWithDescriptor(NSDL_DCHandler.QDC_NS+":description","Orientation",metadataBean.getOrientation(),recordElement,doc);
		HEALXMLHelper.appendTextElemWithDescriptor(NSDL_DCHandler.QDC_NS+":description","Magnification",metadataBean.getMagnification(),recordElement,doc);
		HEALXMLHelper.appendTextElemWithDescriptor(NSDL_DCHandler.QDC_NS+":description","Annotated",metadataBean.getAnnotated(),recordElement,doc);
		Collection diagnoses = metadataBean.getDiseaseDiagnoses();
		if (diagnoses != null) {
			DiseaseDiagnosisBean value = null;
			Iterator iter = diagnoses.iterator();
			while (iter.hasNext()) {
				value = (DiseaseDiagnosisBean) iter.next();
				HEALXMLHelper.appendTextElemWithDescriptor(NSDL_DCHandler.QDC_NS+":description","Disease diagnosis",value.getDiseaseDiagnosis(),recordElement,doc);
			}
		}

		HEALXMLHelper.appendTextElemWithDescriptor(NSDL_DCHandler.QDC_NS+":description","Clinical history",metadataBean.getClinicalHistory(),recordElement,doc);
		HEALXMLHelper.appendTextElemWithDescriptor(NSDL_DCHandler.QDC_NS+":description","Radiograph type", metadataBean.getRadiographType(),recordElement,doc);
		
		Collection urls = metadataBean.getContextURLs();
		if (urls != null) {
			Iterator iter = urls.iterator();
			ContextURLBean value = null;
			while (iter.hasNext()) {
				value = (ContextURLBean) iter.next();
				HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+":description",value.getContextURL(),recordElement,doc);				
				HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+":description",value.getContextURLDescription(),recordElement,doc);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendFormatElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendFormatElements(Element recordElement, Document doc) {
		HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+":format",metadataBean.getFileExtension(),recordElement,doc);
		String width = metadataBean.getFileWidth();
		String height = metadataBean.getFileHeight();
		if (width != null && !"0".equals(width) && height != null && !"0".equals(height)) {
			HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+":format",width+" x "+height+" pixels",recordElement,doc);
		}
		String fileSize = metadataBean.getFileSize();
		if (fileSize != null && !"0".equals(fileSize)) {
			HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+":format",fileSize+" bytes",recordElement,doc);					
		}
		String duration = metadataBean.getDuration();
		if (duration != null && !"0".equals(duration)) {
			HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+":format",duration+" seconds",recordElement,doc);
		}
		Collection list = metadataBean.getRequirements();
		Iterator iter = list.iterator();
		RequirementBean rb;
		while (iter.hasNext()) {
			rb = (RequirementBean)iter.next();
			HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+":format",rb.getRequirementType()+": "+rb.getRequirementName(),recordElement,doc);
		}
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendLanguageElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendLanguageElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendPublisherElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendPublisherElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendRelationElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendRelationElements(Element recordElement, Document doc) {
		Collection relations = metadataBean.getRelations();
		Iterator iter = relations.iterator();
		RelationBean rb;
		while (iter.hasNext()) {
			rb = (RelationBean)iter.next();
			HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+":relation",rb.getKind(),recordElement,doc);
			HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+":relation",rb.getDescription(),recordElement,doc);
			//XXX TODO this next line needs to have the relation lookup tweaked to include the GlobalID of
			//the resource...and to lookup the resource's catalog entries and catalog
//			HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+":relation",rb.getResource(),recordElement,doc);
		}
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendRightsElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendRightsElements(Element recordElement, Document doc) {
		Collection col = metadataBean.getCopyrights();
		Iterator iter = col.iterator();
		CopyrightBean cb;
		CopyrightTextBean ctb;
		while (iter.hasNext()) {
			cb = (CopyrightBean) iter.next();
			ctb = cb.getCopyrightText();
			HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+":rights",ctb.getCopyrightText(),recordElement,doc);
		}

		col = metadataBean.getCopyrightHolders();
		iter = col.iterator();
		CopyrightHolderBean chb;
		String fullName;
		while (iter.hasNext()) {
			chb = (CopyrightHolderBean) iter.next();
			fullName = HEALXMLHelper.getFullNameFromVCard(chb.getVCard());
			HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+":rights",fullName,recordElement,doc);
		}
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendSourceElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendSourceElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendTypeElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendTypeElements(Element recordElement, Document doc) {
		Collection formats = metadataBean.getFormats();
		Iterator iter = formats.iterator();
		FormatBean fb;
		while (iter.hasNext()) {
			fb = (FormatBean)iter.next();
			HEALXMLHelper.appendTextElem(NSDL_DCHandler.QDC_NS+":type",fb.getFormat(),recordElement,doc);
		}
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTAccessibilityElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTAccessibilityElements(Element recordElement,
			Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTAccrualMethodElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTAccrualMethodElements(Element recordElement,
			Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTAccrualPeriodicityElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTAccrualPeriodicityElements(Element recordElement,
			Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTAccrualPolicyElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTAccrualPolicyElements(Element recordElement,
			Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTAudienceElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTAudienceElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTMediatorElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTMediatorElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTEducationLevelElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTEducationLevelElements(Element recordElement,
			Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTSpatialElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTSpatialElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTTemporalElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTTemporalElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTAvailableElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTAvailableElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTCreatedElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTCreatedElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTDateAcceptedElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTDateAcceptedElements(Element recordElement,
			Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTDateCopyrightedElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTDateCopyrightedElements(Element recordElement,
			Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTDateSubmittedElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTDateSubmittedElements(Element recordElement,
			Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTIssuedElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTIssuedElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTModifiedElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTModifiedElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTValidElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTValidElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTAbstractElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTAbstractElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTTableOfContentsElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTTableOfContentsElements(Element recordElement,
			Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTExtentElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTExtentElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTMediumElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTMediumElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTBibliograhicCitationElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTBibliograhicCitationElements(Element recordElement,
			Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTInstructionalMethodElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTInstructionalMethodElements(Element recordElement,
			Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTProvenanceElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTProvenanceElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTConformsToElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTConformsToElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTIsFormatOfElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTIsFormatOfElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTHasFormatElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTHasFormatElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTIsPartOfElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTIsPartOfElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTHasPartElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTHasPartElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTIsReferencedByElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTIsReferencedByElements(Element recordElement,
			Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTReferencesElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTReferencesElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTIsReplacedByElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTIsReplacedByElements(Element recordElement,
			Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTReplacesElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTReplacesElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTIsRequiredByElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTIsRequiredByElements(Element recordElement,
			Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTRequiresElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTRequiresElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTIsVersionOfElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTIsVersionOfElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTHasVersionElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTHasVersionElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTAccessRightsElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTAccessRightsElements(Element recordElement,
			Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTLicenseElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTLicenseElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTRightsHolderElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTRightsHolderElements(Element recordElement,
			Document doc) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.nsdl_dc.NSDL_QDCRecordAdapter#appendDCTAlternativeElements(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void appendDCTAlternativeElements(Element recordElement, Document doc) {
		// TODO Auto-generated method stub

	}
}
