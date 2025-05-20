/*
 * Created on Dec 1, 2004
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
import org.heal.module.oai.provider.OAIRecordAdapter;
import org.heal.module.oai.provider.oai_dc.OAI_DCHandler;
import org.heal.module.oai.provider.oai_dc.OAI_DCRecordAdapter;
import org.heal.util.FileLocator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * @author Seth Wright
 *
 */
public class HEALOAIDCRecordAdapter implements OAI_DCRecordAdapter, OAIRecordAdapter {

	private final CompleteMetadataBean metadataBean;
	private final HEALProviderConfig config;
	
	public HEALOAIDCRecordAdapter(final CompleteMetadataBean metadataBean,final HEALProviderConfig config) {
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
	 * @see org.heal.module.oai.provider.oai_dc.OAI_DCRecordAdapter#addAttributes(org.w3c.dom.Element)
	 */
	public void addRecordAttributes(Element recordElement,Document doc) {
		recordElement.setAttribute("xmlns:"+HEALXMLHelper.HEALNS,HEALXMLHelper.HEALLOC);  		
	}

	public void addCustomAbout(Document doc) {
		HEALXMLHelper.addCustomAbout(metadataBean,config,doc);
	}
	
	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.oai_dc.OAI_DCRecordAdapter#addAttributes(org.w3c.dom.Element)
	 */
	public void addOAIAboutAttributes(Element recordElement,Document doc) {		
	}


	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAI_DCRecordAdapter#appendAboutElements(org.heal.module.oai.provider.OAIRecord, org.w3c.dom.Element)
	 */
	public void appendOAIAboutElements(Element recordElement, Document doc) {
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAI_DCRecordAdapter#appendTitleElements(org.heal.module.oai.provider.OAIRecord, org.w3c.dom.Element)
	 */
	public void appendTitleElements(Element recordElement, Document doc) {
		HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+":title",metadataBean.getTitle(),recordElement,doc);
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAI_DCRecordAdapter#appendSubjectElements(org.heal.module.oai.provider.OAIRecord, org.w3c.dom.Element)
	 */
	public void appendSubjectElements(Element recordElement, Document doc) {
		Collection taxonpaths = metadataBean.getTaxonPaths();
		Iterator iter = taxonpaths.iterator();
		TaxonPathBean tpb;
		TaxonBean tb;
		while (iter.hasNext()) {
			tpb = (TaxonPathBean) iter.next();
			HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+":subject",tpb.getSource(),recordElement,doc);
			Collection taxons = tpb.getTaxons();
			Iterator taxonIter = taxons.iterator();
			while (taxonIter.hasNext()) {
				tb = (TaxonBean)taxonIter.next();
				HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+":subject",tb.getId(),recordElement,doc);					
				HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+":subject",tb.getEntry(),recordElement,doc);					
			}
		}
		Collection keywords = metadataBean.getKeywords();
		iter = keywords.iterator();
		KeywordBean kb;
		while (iter.hasNext()) {
			kb = (KeywordBean)iter.next();
			HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+":subject",kb.getKeyword(),recordElement,doc);
		}
	}

	
	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAI_DCRecordAdapter#appendDescrpitionElements(org.heal.module.oai.provider.OAIRecord, org.w3c.dom.Element)
	 */
	public void appendDescriptionElements(Element recordElement, Document doc) {
		HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+":description",metadataBean.getDescription(),recordElement,doc);
		HEALXMLHelper.appendTextElemWithDescriptor(OAI_DCHandler.SIMPLEDC_PREFIX+":description","Specimen Type",metadataBean.getSpecimenType(),recordElement,doc);
		HEALXMLHelper.appendTextElemWithDescriptor(OAI_DCHandler.SIMPLEDC_PREFIX+":description","Orientation",metadataBean.getOrientation(),recordElement,doc);
		HEALXMLHelper.appendTextElemWithDescriptor(OAI_DCHandler.SIMPLEDC_PREFIX+":description","Magnification",metadataBean.getMagnification(),recordElement,doc);
		HEALXMLHelper.appendTextElemWithDescriptor(OAI_DCHandler.SIMPLEDC_PREFIX+":description","Annotated",metadataBean.getAnnotated(),recordElement,doc);
		Collection diagnoses = metadataBean.getDiseaseDiagnoses();
		if (diagnoses != null) {
			DiseaseDiagnosisBean value = null;
			Iterator iter = diagnoses.iterator();
			while (iter.hasNext()) {
				value = (DiseaseDiagnosisBean) iter.next();
				HEALXMLHelper.appendTextElemWithDescriptor(OAI_DCHandler.SIMPLEDC_PREFIX+":description","Disease diagnosis",value.getDiseaseDiagnosis(),recordElement,doc);
			}
		}

		HEALXMLHelper.appendTextElemWithDescriptor(OAI_DCHandler.SIMPLEDC_PREFIX+":description","Clinical history",metadataBean.getClinicalHistory(),recordElement,doc);
		HEALXMLHelper.appendTextElemWithDescriptor(OAI_DCHandler.SIMPLEDC_PREFIX+":description","Radiograph type", metadataBean.getRadiographType(),recordElement,doc);
		
		Collection urls = metadataBean.getContextURLs();
		if (urls != null) {
			Iterator iter = urls.iterator();
			ContextURLBean value = null;
			while (iter.hasNext()) {
				value = (ContextURLBean) iter.next();
				HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+":description",value.getContextURL(),recordElement,doc);				
				HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+":description",value.getContextURLDescription(),recordElement,doc);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAI_DCRecordAdapter#appendPublisherElements(org.heal.module.oai.provider.OAIRecord, org.w3c.dom.Element)
	 */
	public void appendPublisherElements(Element recordElement, Document doc) {
		//XXX we don't do anything here because we already appended them in
		//the appendContributorElements method
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAI_DCRecordAdapter#appendCreatorElements(org.heal.module.oai.provider.OAIRecord, org.w3c.dom.Element)
	 */
	public void appendCreatorElements(Element recordElement, Document doc) {
		//XXX we don't do anything here because we already appended them in
		//the appendContributorElements method
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAI_DCRecordAdapter#appendContributorElements(org.heal.module.oai.provider.OAIRecord, org.w3c.dom.Element)
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
			HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+type,"Role: "+role,recordElement,doc);
			HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+type,contributor.getVCard(),recordElement,doc);
		}
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAI_DCRecordAdapter#appendDateElements(org.heal.module.oai.provider.OAIRecord, org.w3c.dom.Element)
	 */
	public void appendDateElements(Element recordElement, Document doc) {
		Date contributeDate = metadataBean.getContributeDate();
		if (contributeDate != null) {
			String dateStr = OAIGranularity.yearMonthDayHourMinuteSecond.format(contributeDate);
			HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+":date",dateStr,recordElement,doc);
		}
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAI_DCRecordAdapter#appendTypeElements(org.heal.module.oai.provider.OAIRecord, org.w3c.dom.Element)
	 */
	public void appendTypeElements(Element recordElement, Document doc) {
		Collection formats = metadataBean.getFormats();
		Iterator iter = formats.iterator();
		FormatBean fb;
		while (iter.hasNext()) {
			fb = (FormatBean)iter.next();
			HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+":type",fb.getFormat(),recordElement,doc);
		}
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAI_DCRecordAdapter#appendFormatElements(org.heal.module.oai.provider.OAIRecord, org.w3c.dom.Element)
	 */
	public void appendFormatElements(Element recordElement, Document doc) {
		HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+":format",metadataBean.getFileExtension(),recordElement,doc);
		String dimensions = "";
		String width = metadataBean.getFileWidth();
		String height = metadataBean.getFileHeight();
		if (width != null && !"0".equals(width) && height != null && !"0".equals(height)) {
			HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+":format",width+" x "+height+" pixels",recordElement,doc);			
		}
		String fileSize = metadataBean.getFileSize();
		if (fileSize != null && !"0".equals(fileSize)) {
			HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+":format",fileSize+" bytes",recordElement,doc);					
		}
		String duration = metadataBean.getDuration();
		if (duration != null && !"0".equals(duration)) {
			HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+":format",duration+" seconds",recordElement,doc);
		}
		Collection list = metadataBean.getRequirements();
		Iterator iter = list.iterator();
		RequirementBean rb;
		while (iter.hasNext()) {
			rb = (RequirementBean)iter.next();
			HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+":format",rb.getRequirementType()+": "+rb.getRequirementName(),recordElement,doc);
		}
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAI_DCRecordAdapter#appendIdentifierElements(org.heal.module.oai.provider.OAIRecord, org.w3c.dom.Element)
	 */
	public void appendIdentifierElements(Element recordElement, Document doc) {		
		String location = metadataBean.getLocation();
		if (location != null && !location.startsWith("http://")) {
			location = config.getContentPrefix()+'/'+location;
		}		
		HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+":identifier",location,recordElement,doc);
		HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+":identifier",metadataBean.getGlobalId(),recordElement,doc);
/*
		General CatalogEntry Catalogue = Location in the Metadata table; the default is http://www.healcentral.org; but some resources                                         have other URLs, such as http://medstat.med.utah.edu/kw or http://www.nlm.nih.gov/medlineplus
		General CatalogEntry Entry = GlobalID from the Metadata table; this isn't technically accurate, but we haven't used IDs from the local catalogs
*/
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAI_DCRecordAdapter#appendSourceElements(org.heal.module.oai.provider.OAIRecord, org.w3c.dom.Element)
	 */
	public void appendSourceElements(Element recordElement, Document doc) {
		//Do nothing here because these elements are appended in
		//the relation portion
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAI_DCRecordAdapter#appendLanguageElements(org.heal.module.oai.provider.OAIRecord, org.w3c.dom.Element)
	 */
	public void appendLanguageElements(Element recordElement, Document doc) {
		//we don't have any language entries yet
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAI_DCRecordAdapter#appendRelationElements(org.heal.module.oai.provider.OAIRecord, org.w3c.dom.Element)
	 */
	public void appendRelationElements(Element recordElement, Document doc) {
		Collection relations = metadataBean.getRelations();
		Iterator iter = relations.iterator();
		RelationBean rb;
		while (iter.hasNext()) {
			rb = (RelationBean)iter.next();
			HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+":relation",rb.getKind(),recordElement,doc);			
			HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+":relation",rb.getDescription(),recordElement,doc);
			//XXX TODO this next line needs to have the relation lookup tweaked to include the GlobalID of
			//the resource...and to lookup the resource's catalog entries and catalog
//			HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+":relation",rb.getResource(),recordElement,doc);				
		}
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAI_DCRecordAdapter#appendCoverageElements(org.heal.module.oai.provider.OAIRecord, org.w3c.dom.Element)
	 */
	public void appendCoverageElements(Element recordElement, Document doc) {
		HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+":coverage",config.getCoverage(),recordElement,doc);
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAI_DCRecordAdapter#appendRightsElements(org.heal.module.oai.provider.OAIRecord, org.w3c.dom.Element)
	 */
	public void appendRightsElements(Element recordElement, Document doc) {
		Collection col = metadataBean.getCopyrights();
		Iterator iter = col.iterator();
		CopyrightBean cb;
		CopyrightTextBean ctb;
		while (iter.hasNext()) {
			cb = (CopyrightBean) iter.next();
			ctb = cb.getCopyrightText();
			HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+":rights",ctb.getCopyrightText(),recordElement,doc);
		}

		col = metadataBean.getCopyrightHolders();
		iter = col.iterator();
		CopyrightHolderBean chb;
		String fullName;
		while (iter.hasNext()) {
			chb = (CopyrightHolderBean) iter.next();
			fullName = HEALXMLHelper.getFullNameFromVCard(chb.getVCard());
			HEALXMLHelper.appendTextElem(OAI_DCHandler.SIMPLEDC_PREFIX+":rights",fullName,recordElement,doc);
		}
	}
}
