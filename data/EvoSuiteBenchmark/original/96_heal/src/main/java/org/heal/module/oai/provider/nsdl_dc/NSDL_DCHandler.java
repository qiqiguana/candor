/*
 * Created on Dec 1, 2004
 *
 */
package org.heal.module.oai.provider.nsdl_dc;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.heal.module.oai.provider.OAIMetadataFormat;
import org.heal.module.oai.provider.OAIMetadataHandler;
import org.heal.module.oai.provider.OAIRecordAdapter;
import org.heal.module.oai.provider.oai_dc.OAI_DCHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Seth Wright
 *
 */
public class NSDL_DCHandler implements OAIMetadataHandler {
	private final OAIMetadataFormat metadataFormat;
	public static final String QDC_NS = "dc";
	public static final String QDCT_NS = "dct";

	public NSDL_DCHandler(final OAIMetadataFormat format) {
		metadataFormat = format;
	}

	public OAIMetadataFormat getFormat() {
		return metadataFormat;
	}

	/* (non-Javadoc)
	 * @see org.heal.module.oai.provider.OAIMetadataHandler#writeRecord(org.heal.module.oai.provider.OAIRecord, java.io.OutputStream)
	 */
	public void writeMetadata(final OAIRecordAdapter record, final PrintWriter writer)
	throws IOException 
	{
		NSDL_QDCRecordAdapter recordAdapter = (NSDL_QDCRecordAdapter) record;
		try {
			//create a new Document using JAXP
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();

			Element recordElem;
			recordElem = doc.createElement(metadataFormat.getPrefix()+":"+NSDL_DCHandler.QDC_NS);
			System.out.println("printed recordElem");
			recordElem.setAttribute("xmlns:"+metadataFormat.getPrefix(),metadataFormat.getNamespace());

//include the dublin core...
			recordElem.setAttribute("xmlns:"+NSDL_DCHandler.QDC_NS,
									"http://purl.org/dc/elements/1.1/");
			recordElem.setAttribute("xmlns:"+NSDL_DCHandler.QDCT_NS,
									"http://purl.org/dc/terms/");

			recordElem.setAttribute("xmlns:xsi",
									"http://www.w3.org/2001/XMLSchema-instance"); 
            
			recordElem.setAttribute("xsi:schemaLocation",metadataFormat.getSchemaLocation());

			//allow the recordAdapter to add any namespaces, etc necessary.
			recordAdapter.addRecordAttributes(recordElem,doc);
			recordAdapter.appendIdentifierElements(recordElem,doc);
			recordAdapter.appendTitleElements(recordElem,doc);
			recordAdapter.appendSubjectElements(recordElem,doc);
			recordAdapter.appendEducationLevelElements(recordElem, doc);
			recordAdapter.appendInteractivityTypeElement(recordElem,doc);
			recordAdapter.appendInteractivityLevelElement(recordElem,doc);
			recordAdapter.appendTypicalLearningTimeElement(recordElem,doc);
			recordAdapter.appendContributorElements(recordElem, doc);
			recordAdapter.appendCoverageElements(recordElem, doc);
			recordAdapter.appendCreateorElements(recordElem, doc);
			recordAdapter.appendDateElements(recordElem, doc);
			recordAdapter.appendDescriptionElements(recordElem, doc);
			recordAdapter.appendFormatElements(recordElem, doc);
			recordAdapter.appendLanguageElements(recordElem, doc);
			recordAdapter.appendPublisherElements(recordElem, doc);
			recordAdapter.appendRelationElements(recordElem, doc);
			recordAdapter.appendRightsElements(recordElem, doc);
			recordAdapter.appendSourceElements(recordElem, doc);
			recordAdapter.appendTypeElements(recordElem, doc);
			recordAdapter.appendDCTAccessibilityElements(recordElem, doc);
			recordAdapter.appendDCTAccrualMethodElements(recordElem, doc);
			recordAdapter.appendDCTAccrualPeriodicityElements(recordElem, doc);
			recordAdapter.appendDCTAccrualPolicyElements(recordElem, doc);
			recordAdapter.appendDCTAudienceElements(recordElem, doc);
			recordAdapter.appendDCTMediatorElements(recordElem, doc);
			recordAdapter.appendDCTEducationLevelElements(recordElem, doc);
			recordAdapter.appendDCTSpatialElements(recordElem, doc);
			recordAdapter.appendDCTTemporalElements(recordElem, doc);
			recordAdapter.appendDCTAvailableElements(recordElem, doc);
			recordAdapter.appendDCTCreatedElements(recordElem, doc);
			recordAdapter.appendDCTDateAcceptedElements(recordElem, doc);
			recordAdapter.appendDCTDateCopyrightedElements(recordElem, doc);
			recordAdapter.appendDCTDateSubmittedElements(recordElem, doc);
			recordAdapter.appendDCTIssuedElements(recordElem, doc);
			recordAdapter.appendDCTModifiedElements(recordElem, doc);
			recordAdapter.appendDCTValidElements(recordElem, doc);
			recordAdapter.appendDCTAbstractElements(recordElem, doc);
			recordAdapter.appendDCTTableOfContentsElements(recordElem, doc);
			recordAdapter.appendDCTExtentElements(recordElem, doc);
			recordAdapter.appendDCTMediumElements(recordElem, doc);
			recordAdapter.appendDCTBibliograhicCitationElements(recordElem, doc);
			recordAdapter.appendDCTInstructionalMethodElements(recordElem, doc);
			recordAdapter.appendDCTProvenanceElements(recordElem, doc);
			recordAdapter.appendDCTConformsToElements(recordElem, doc);
			recordAdapter.appendDCTIsFormatOfElements(recordElem, doc);
			recordAdapter.appendDCTHasFormatElements(recordElem, doc);
			recordAdapter.appendDCTIsPartOfElements(recordElem, doc);
			recordAdapter.appendDCTHasPartElements(recordElem, doc);
			recordAdapter.appendDCTIsReferencedByElements(recordElem, doc);
			recordAdapter.appendDCTReferencesElements(recordElem, doc);
			recordAdapter.appendDCTIsReplacedByElements(recordElem, doc);
			recordAdapter.appendDCTReplacesElements(recordElem, doc);
			recordAdapter.appendDCTIsRequiredByElements(recordElem, doc);
			recordAdapter.appendDCTRequiresElements(recordElem, doc);
			recordAdapter.appendDCTIsVersionOfElements(recordElem, doc);
			recordAdapter.appendDCTHasVersionElements(recordElem, doc);
			recordAdapter.appendDCTAccessRightsElements(recordElem, doc);
			recordAdapter.appendDCTLicenseElements(recordElem, doc);
			recordAdapter.appendDCTRightsHolderElements(recordElem, doc);
			recordAdapter.appendDCTAlternativeElements(recordElem, doc);

			doc.appendChild(recordElem);

			//now that we have the document built, we should write it out.
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			StringWriter swriter = new StringWriter();
			transformer.transform(new DOMSource(doc),new StreamResult(swriter));

			///This is ugly...we need to strip off the initial <?xml ?> line
			String output = swriter.toString();
			int questIndex = output.indexOf("?>");		
			if (questIndex > 0) {
				writer.print(output.substring(questIndex+2));
			} else {
				writer.print(output);
			}
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			throw new IOException(pce.toString());
		} catch (TransformerException ex) {
			ex.printStackTrace();
			throw new IOException(ex.toString());
		}
	}	

	public void writeCustomAbout(final OAIRecordAdapter record, final PrintWriter writer) 
	throws IOException {
		NSDL_QDCRecordAdapter recordAdapter = (NSDL_QDCRecordAdapter) record;
		try {
			//create a new Document using JAXP
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();

			record.addCustomAbout(doc);
			
			//now that we have the document built, we should write it out.
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			StringWriter swriter = new StringWriter();
			transformer.transform(new DOMSource(doc),new StreamResult(swriter));
			
			///This is ugly...we need to strip off the initial <?xml ?> line
			String output = swriter.toString();
			int questIndex = output.indexOf("?>");		
			if (questIndex > 0) {
				writer.print(output.substring(questIndex+2));
			} else {
				writer.print(output);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IOException(ex.toString());
		}
	}
	
	public void writeOAIAbout(final OAIRecordAdapter record, final PrintWriter writer)
	throws IOException 
	{
		NSDL_QDCRecordAdapter recordAdapter = (NSDL_QDCRecordAdapter) record;
		try {
			//create a new Document using JAXP
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();
			Element recordElem;
			recordElem = doc.createElement(metadataFormat.getPrefix()+":dc");
			recordElem.setAttribute("xmlns:"+metadataFormat.getPrefix(),metadataFormat.getNamespace());

//include the dublin core...
			recordElem.setAttribute("xmlns:"+OAI_DCHandler.SIMPLEDC_PREFIX,
									"http://purl.org/dc/elements/1.1/");
		 	    
			recordElem.setAttribute("xmlns:xsi",
									"http://www.w3.org/2001/XMLSchema-instance"); 
            
			recordElem.setAttribute("xsi:schemaLocation",metadataFormat.getSchema());

			//allow the recordAdapter to add any namespaces, etc necessary.
			recordAdapter.addOAIAboutAttributes(recordElem,doc);
			recordAdapter.appendOAIAboutElements(recordElem,doc);

			doc.appendChild(recordElem);

			//now that we have the document built, we should write it out.
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			StringWriter swriter = new StringWriter();
			transformer.transform(new DOMSource(doc),new StreamResult(swriter));

			///This is ugly...we need to strip off the initial <?xml ?> line
			String output = swriter.toString();
			int questIndex = output.indexOf("?>");		
			if (questIndex > 0) {
				writer.print(output.substring(questIndex+2));
			} else {
				writer.print(output);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IOException(ex.toString());
		}
	}	

		/*
		<nsdl_dc:nsdl_dc schemaVersion="1.02.010" xsi:schemaLocation="http://ns.nsdl.org/nsdl_dc_v1.02/ http://ns.nsdl.org/schemas/nsdl_dc/nsdl_dc_v1.02.xsd">
		<!-- nsdl_dc specific fields -->
		<dc:identifier xsi:type="dct:ISBN">ISBN</dc:identifier>
		<dc:identifier xsi:type="dct:ISSN">ISSN</dc:identifier>
		<dc:subject xsi:type="nsdl_dc:GEM">Biological and life sciences</dc:subject>
		<dct:educationLevel xsi:type="nsdl_dc:NSDLEdLevel">Grades Pre-K to 12</dct:educationLevel>
		<ieee:interactivityType>interactivityType</ieee:interactivityType>
		<ieee:interactivityLevel>interactivityLevel</ieee:interactivityLevel>
		<ieee:typicalLearningTime>typicalLearningTime</ieee:typicalLearningTime>
		<!-- simple DC elements, alphabetically -->
		<dc:contributor>contributor (dc element)</dc:contributor>
		<dc:coverage>coverage (dc element)</dc:coverage>
		<dc:creator>creator (dc element)</dc:creator>
		<dc:date>date (dc element)</dc:date>
		<dc:description>description (dc element)</dc:description>
		<dc:format>format (dc element)</dc:format>
		<dc:identifier>http://dublincore.org/</dc:identifier>
		<dc:language>language (dc element)</dc:language>
		<dc:publisher>publisher (dc element)</dc:publisher>
		<dc:relation>relation (dc element)</dc:relation>
		<dc:rights>rights (dc element)</dc:rights>
		<dc:source>source (dc element)</dc:source>
		<dc:subject>subject (dc element)</dc:subject>
		<dc:title>title (dc element)</dc:title>
		<dc:type>type (dc element)</dc:type>
		<!--
		 qualified DC dcterms elements and refinements, alphabetically by element 
		-->
		<!-- accessibility element -->
		<dct:accessibility>accessibility (dcterms element)</dct:accessibility>
		<!-- accrualMethod element -->
		<dct:accrualMethod>accrualMethod (dcterms element)</dct:accrualMethod>
		<!-- accrualPeriodicity element -->
		<dct:accrualPeriodicity>accuralPeriodicity (dcterms element)</dct:accrualPeriodicity>
		<!-- accrualPolicy element -->
		<dct:accrualPolicy>accuralPolicy (dcterms element)</dct:accrualPolicy>
		<!-- audience element and its refinements -->
		<dct:audience>audience (dcterms element)</dct:audience>
		<dct:mediator>mediator (refines audience)</dct:mediator>
		<dct:educationLevel>educationLevel (refines audience)</dct:educationLevel>
		<!-- coverage refinements -->
		<dct:spatial>spatial (refines coverage)</dct:spatial>
		<dct:temporal>temporal (refines coverage)</dct:temporal>
		<!-- date refinements -->
		<dct:available>available (refines date)</dct:available>
		<dct:created>created (refines date)</dct:created>
		<dct:dateAccepted>dateAccepted (refines date)</dct:dateAccepted>
		<dct:dateCopyrighted>dateCopyrighted (refines date)</dct:dateCopyrighted>
		<dct:dateSubmitted>dateSubmitted (refines date)</dct:dateSubmitted>
		<dct:issued>issued (refines date)</dct:issued>
		<dct:modified>modified (refines date)</dct:modified>
		<dct:valid>valid (refines date)</dct:valid>
		<!-- description refinements -->
		<dct:abstract>abstract (refines description)</dct:abstract>
		<dct:tableOfContents>tableOfContents (refines description)</dct:tableOfContents>
		<!-- format refinements -->
		<dct:extent>extent (refines format)</dct:extent>
		<dct:medium>medium (refines format)</dct:medium>
		<!-- identifier refinements -->
		<dct:bibliographicCitation>bibliographicCitation (refines identifier)</dct:bibliographicCitation>
		<!-- instructionalMethod element -->
		<dct:instructionalMethod>instructionalMethod (dcterms element)</dct:instructionalMethod>
		<!-- provenance element -->
		<dct:provenance>provenance (dcterms element)</dct:provenance>
		<!-- relation refinements -->
		<dct:conformsTo>conformsTo (refines relation)</dct:conformsTo>
		<dct:isFormatOf>isFormatOf (refines relation)</dct:isFormatOf>
		<dct:hasFormat>hasFormat (refines relation)</dct:hasFormat>
		<dct:isPartOf>isPartOf (refines relation)</dct:isPartOf>
		<dct:hasPart>hasPart (refines relation)</dct:hasPart>
		<dct:isReferencedBy>isReferencedBy (refines relation)</dct:isReferencedBy>
		<dct:references>references (refines relation)</dct:references>
		<dct:isReplacedBy>isReplacedBy (refines relation)</dct:isReplacedBy>
		<dct:replaces>replaces (refines relation)</dct:replaces>
		<dct:isRequiredBy>isRequiredBy (refines relation)</dct:isRequiredBy>
		<dct:requires>requires (refines relation)</dct:requires>
		<dct:isVersionOf>isVersionOf (refines relation)</dct:isVersionOf>
		<dct:hasVersion>hasVersion (refines relation)</dct:hasVersion>
		<!-- rights refinements -->
		<dct:accessRights>accessRights (refines rights)</dct:accessRights>
		<dct:license>license (refines rights)</dct:license>
		<!-- rightsHolder element -->
		<dct:rightsHolder>rightsHolder (dcterms element)</dct:rightsHolder>
		<!-- title refinements -->
		<dct:alternative>alternative (refines title)</dct:alternative>
		-
			<!--
		 QDC dcterms encoding schemes, applied to elements and their refinements 
		-->
		<!-- coverage encoding schemes -->
		<dc:coverage xsi:type="dct:Box">Box encoding scheme for dc coverage</dc:coverage>
		<dc:coverage xsi:type="dct:ISO3166">ISO3166 encoding scheme for dc coverage</dc:coverage>
		<dc:coverage xsi:type="dct:Point">Point encoding scheme for dc coverage</dc:coverage>
		<dc:coverage xsi:type="dct:TGN">TGN encoding scheme for dc coverage</dc:coverage>
		<dc:coverage xsi:type="dct:ISO8601">ISO8601 encoding scheme for dc coverage</dc:coverage>
		<dc:coverage xsi:type="dct:Period">Period encoding scheme for dc coverage</dc:coverage>
		<dc:coverage xsi:type="dct:W3CDTF">2004-11</dc:coverage>
		-
			<!--
		 spatial (refinement of coverage) encoding schemes 
		-->
		<dct:spatial xsi:type="dct:Box">Box encoding scheme for dcterms spatial</dct:spatial>
		<dct:spatial xsi:type="dct:ISO3166">ISO3166 encoding scheme for dcterms spatial</dct:spatial>
		<dct:spatial xsi:type="dct:Point">Point encoding scheme for dcterms spatial</dct:spatial>
		<dct:spatial xsi:type="dct:TGN">TGN encoding scheme for dcterms spatial</dct:spatial>
		-
			<!--
		 temporal (refinement of coverage) encoding schemes 
		-->
		<dct:temporal xsi:type="dct:ISO8601">ISO8601 encoding scheme dcterms temporal</dct:temporal>
		<dct:temporal xsi:type="dct:Period">Period encoding scheme for dcterms temporal</dct:temporal>
		<dct:temporal xsi:type="dct:W3CDTF">2004</dct:temporal>
		<!-- date encoding schemes -->
		<dc:date xsi:type="dct:ISO8601">ISO8601 encoding scheme for dc date</dc:date>
		<dc:date xsi:type="dct:Period">Period encoding scheme for dc date</dc:date>
		<dc:date xsi:type="dct:W3CDTF">1901-01-01</dc:date>
		<dct:available xsi:type="dct:ISO8601">ISO8601 encoding scheme for dcterms available</dct:available>
		<dct:available xsi:type="dct:Period">Period encoding scheme for dcterms available</dct:available>
		<dct:available xsi:type="dct:W3CDTF">1902-02-02</dct:available>
		<dct:created xsi:type="dct:ISO8601">ISO8601 encoding scheme for dcterms created</dct:created>
		<dct:created xsi:type="dct:Period">Period encoding scheme for dcterms created</dct:created>
		<dct:created xsi:type="dct:W3CDTF">1903-03-03</dct:created>
		<dct:dateAccepted xsi:type="dct:ISO8601">ISO8601 encoding scheme for dcterms dateAccepted</dct:dateAccepted>
		<dct:dateAccepted xsi:type="dct:Period">Period encoding scheme for dcterms dateAccepted</dct:dateAccepted>
		<dct:dateAccepted xsi:type="dct:W3CDTF">1904-04-04</dct:dateAccepted>
		<dct:dateCopyrighted xsi:type="dct:ISO8601">
			ISO8601 encoding scheme for dcterms dateCopyrighted
		</dct:dateCopyrighted>
		<dct:dateCopyrighted xsi:type="dct:Period">Period encoding scheme for dcterms dateCopyrighted</dct:dateCopyrighted>
		<dct:dateCopyrighted xsi:type="dct:W3CDTF">1905-05-05</dct:dateCopyrighted>
		<dct:dateSubmitted xsi:type="dct:ISO8601">ISO8601 encoding scheme for dcterms dateSubmitted</dct:dateSubmitted>
		<dct:dateSubmitted xsi:type="dct:Period">Period encoding scheme for dcterms dateSubmitted</dct:dateSubmitted>
		<dct:dateSubmitted xsi:type="dct:W3CDTF">1906-06-06</dct:dateSubmitted>
		<dct:issued xsi:type="dct:ISO8601">ISO8601 encoding scheme for dcterms issued</dct:issued>
		<dct:issued xsi:type="dct:Period">Period encoding scheme for dcterms issued</dct:issued>
		<dct:issued xsi:type="dct:W3CDTF">1907-07-07</dct:issued>
		<dct:modified xsi:type="dct:ISO8601">ISO8601 encoding scheme for dcterms modified</dct:modified>
		<dct:modified xsi:type="dct:Period">Period encoding scheme for dcterms modified</dct:modified>
		<dct:modified xsi:type="dct:W3CDTF">1908-08-08</dct:modified>
		<dct:valid xsi:type="dct:ISO8601">ISO8601 encoding scheme for dcterms valid</dct:valid>
		<dct:valid xsi:type="dct:Period">Period encoding scheme for dcterms valid</dct:valid>
		<dct:valid xsi:type="dct:W3CDTF">1909-09-09</dct:valid>
		<!-- format encoding schemes -->
		<dc:format xsi:type="dct:IMT">text/xml</dc:format>
		<!-- identifier encoding schemes -->
		<dc:identifier xsi:type="dct:URI">http://purl.org/dc/elements/1.1/identifier</dc:identifier>
		<!-- language encoding schemes -->
		<dc:language xsi:type="dct:ISO639-2">ISO639-2 encoding scheme for dc language</dc:language>
		<dc:language xsi:type="dct:RFC3066">en</dc:language>
		<!-- relation encoding schemes -->
		<dc:relation xsi:type="dct:URI">http://purl.org/dc/elements/1.1/relation</dc:relation>
		<dct:conformsTo xsi:type="dct:URI">http://purl.org/dc/terms/conformsTo</dct:conformsTo>
		<dct:isFormatOf xsi:type="dct:URI">http://purl.org/dc/terms/isFormatOf</dct:isFormatOf>
		<dct:hasFormat xsi:type="dct:URI">http://purl.org/dc/terms/hasFormat</dct:hasFormat>
		<dct:isPartOf xsi:type="dct:URI">http://purl.org/dc/terms/isPartOf</dct:isPartOf>
		<dct:hasPart xsi:type="dct:URI">http://purl.org/dc/terms/hasPart</dct:hasPart>
		<dct:isReferencedBy xsi:type="dct:URI">http://purl.org/dc/terms/isReferencedBy</dct:isReferencedBy>
		<dct:references xsi:type="dct:URI">http://purl.org/dc/terms/references</dct:references>
		<dct:isReplacedBy xsi:type="dct:URI">http://purl.org/dc/terms/isReplacedBy</dct:isReplacedBy>
		<dct:replaces xsi:type="dct:URI">http://purl.org/dc/terms/replaces</dct:replaces>
		<dct:isRequiredBy xsi:type="dct:URI">http://purl.org/dc/terms/isRequiredBy</dct:isRequiredBy>
		<dct:requires xsi:type="dct:URI">http://purl.org/dc/terms/requires</dct:requires>
		<dct:isVersionOf xsi:type="dct:URI">http://purl.org/dc/terms/isVersionOf</dct:isVersionOf>
		<dct:hasVersion xsi:type="dct:URI">http://purl.org/dc/terms/hasVersion</dct:hasVersion>
		<!-- source encoding schemes -->
		<dc:source xsi:type="dct:URI">http://purl.org/dc/elements/1.1/source</dc:source>
		<!-- subject encoding schemes -->
		<dc:subject xsi:type="dct:DDC">DDC encoding scheme for dc subject</dc:subject>
		<dc:subject xsi:type="dct:LCC">LCC encoding scheme for dc subject</dc:subject>
		<dc:subject xsi:type="dct:LCSH">LCSH encoding scheme for dc subject</dc:subject>
		<dc:subject xsi:type="dct:MESH">MESH encoding scheme for dc subject</dc:subject>
		<dc:subject xsi:type="dct:UDC">UDC encoding scheme for dc subject</dc:subject>
		<!-- type encoding schemes -->
		<dc:type xsi:type="dct:DCMIType">PhysicalObject</dc:type>
		</nsdl_dc:nsdl_dc>
		*/
}
