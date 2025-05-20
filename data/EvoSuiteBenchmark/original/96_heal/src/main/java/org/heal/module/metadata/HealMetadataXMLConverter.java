package org.heal.module.metadata;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.heal.util.DateTools;
import org.heal.util.FileLocator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeMap;

public class HealMetadataXMLConverter {
	//public static final String IMSNAMESPACETAG = "imsmd";
	public static final String IMSNAMESPACETAG = null;

	/**
	 * Given a metadata bean and document, generates a complete IMS metadata
	 * representation of the provided bean using the document to create the
	 * elements.  The elemprefix is intended to allow the element names to
	 * begin with namespace information, so each element IMS element will
	 * be created with that prefix as follows: "prefix:elementName".  So,
	 * the prefix should correspond to the prefix specified in an xmlns
	 * attribute that specifies the IMS metadata namespace definition.
	 * If no prefix is specified, then no prefix (or colon) is appended.
	 */
	public static Element metadataToElement(CompleteMetadataBean metaInfo,
											FileLocator fileLocator,
											String elemPrefix,
											Document doc) {
		Element recordElem, metametadataElem, generalElem, lifecycleElem;
		Element technicalElem, educationalElem, classificationElem;
		Element rightsElem;

		String nsPrefix;
		if(elemPrefix == null) {
			nsPrefix = "";
		} else {
			nsPrefix = elemPrefix + ":";
		}

		recordElem = doc.createElement(nsPrefix + "record");

		metametadataElem = createMetametadataElem(metaInfo, nsPrefix, doc);
		recordElem.appendChild(metametadataElem);

		generalElem = createGeneralElem(metaInfo, nsPrefix, doc);
		recordElem.appendChild(generalElem);

		lifecycleElem = createLifecycleElem(metaInfo, nsPrefix, doc);
		recordElem.appendChild(lifecycleElem);

		technicalElem = createTechnicalElem(metaInfo,
			  fileLocator,
			  nsPrefix, doc);
		recordElem.appendChild(technicalElem);

		educationalElem = createEducationalElem(metaInfo, nsPrefix, doc);
		recordElem.appendChild(educationalElem);

		rightsElem = createRightsElem(metaInfo, nsPrefix, doc);
		recordElem.appendChild(rightsElem);

		appendRelations(metaInfo, recordElem, nsPrefix, doc);

		classificationElem = createClassificationElem(metaInfo,
			  nsPrefix,
			  doc);
		recordElem.appendChild(classificationElem);

		return recordElem;
	}

	/**
	 * Takes a metadata bean and generates the IMS XML output for that
	 * bean.
	 */
	public static void metadataToIMSXML(CompleteMetadataBean metaInfo,
										FileLocator fileLocator,
										OutputStream out)
		  throws IOException {
		try {
			//create a new Document using JAXP
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();

			Element recordElem = metadataToElement(metaInfo,
				  fileLocator,
				  IMSNAMESPACETAG,
				  doc);

			//recordElem.setAttribute("xmlns:"+IMSNAMESPACETAG,
			//"http://www.imsproject.org/metadata");


			recordElem.setAttribute("xmlns",
				  "http://www.healcentral.org/xsd/healmd_v1p5");

			recordElem.setAttribute("xmlns:xsi",
				  "http://www.w3.org/2001/XMLSchema-instance");

			recordElem.setAttribute("xsi:schemaLocation",
				  "http://www.healcentral.org/services/schema/HEALmdSchemaXMLv1p5.xsd");


			doc.appendChild(recordElem);

			//now that we have the document built, we should write it out.
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.transform(new DOMSource(doc), new StreamResult(out));
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new IOException(ex.toString());
		}
	}

	/**
	 * Creates a custom heal representation of the metadata.  This metadata
	 * contains the id, title, description, filename, fileextension, and
	 * filesize.
	 */
	public static void shortMetadataToXML(ShortMetadataBean metaInfo,
										  OutputStream out)
		  throws IOException {
		String id = metaInfo.getGlobalId();
		String title = metaInfo.getTitle();
		String description = metaInfo.getDescription();
		String fileName = metaInfo.getFileName();
		String fileExtension = metaInfo.getFileExtension();
		String fileSize = metaInfo.getFileSize();

		//String nsPrefix = IMSNAMESPACETAG+":";
		String nsPrefix = "";


		try {
			//create a new Document using JAXP
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();

			Element fileNameElem, fileExtensionElem, fileSizeElem, titleElem;
			Element titleStringElem, descriptionElem, descriptionStringElem;
			Element extensionElem, catalogEntryElem, technicalElem, generalElem;
			Element recordElem, metaElem, metaschemeElem, languageElem;

			//make the general element and sub elements
			titleElem = doc.createElement(nsPrefix + "title");
			titleStringElem = doc.createElement(nsPrefix + "langstring");
			titleStringElem.setAttribute("xml:lang", "en-US");
			titleStringElem.appendChild(doc.createTextNode(title));
			titleElem.appendChild(titleStringElem);

			descriptionElem = doc.createElement(nsPrefix + "description");
			descriptionStringElem = doc.createElement(nsPrefix + "langstring");
			descriptionStringElem.setAttribute("xml:lang", "en-US");
			descriptionStringElem.appendChild(doc.createTextNode(description));
			descriptionElem.appendChild(titleStringElem);

			catalogEntryElem = createCatalogEntryElem(id, nsPrefix, doc);

			metaElem = doc.createElement(nsPrefix + "metametadata");
			metaElem.appendChild(catalogEntryElem);
			metaschemeElem = doc.createElement(nsPrefix + "metadatascheme");
			metaschemeElem.appendChild(doc.createTextNode("IMS:1.1"));
			metaElem.appendChild(metaschemeElem);
			languageElem = doc.createElement(nsPrefix + "language");
			languageElem.appendChild(doc.createTextNode("en-US"));
			metaElem.appendChild(languageElem);

			catalogEntryElem = createCatalogEntryElem(id, nsPrefix, doc);

			generalElem = doc.createElement(nsPrefix + "general");
			generalElem.appendChild(catalogEntryElem);
			generalElem.appendChild(titleElem);
			generalElem.appendChild(descriptionElem);

			//make the technical portion
			fileNameElem = doc.createElement("filename");
			fileNameElem.appendChild(doc.createTextNode(fileName));
			fileExtensionElem = doc.createElement("fileextension");
			fileExtensionElem.appendChild(doc.createTextNode(fileExtension));
			fileSizeElem = doc.createElement("size");
			fileSizeElem.appendChild(doc.createTextNode(fileSize));
			extensionElem = doc.createElement(nsPrefix + "extension");
			extensionElem.appendChild(fileNameElem);
			extensionElem.appendChild(fileExtensionElem);
			technicalElem = doc.createElement(nsPrefix + "technical");
			technicalElem.appendChild(extensionElem);
			technicalElem.appendChild(fileSizeElem);

			recordElem = doc.createElement(nsPrefix + "record");
			recordElem.setAttribute("xmlns:" + IMSNAMESPACETAG,
				  "http://www.imsproject.org/metadata");

			recordElem.appendChild(metaElem);
			recordElem.appendChild(generalElem);
			recordElem.appendChild(technicalElem);

			doc.appendChild(recordElem);

			//now that we have the document built, we should write it out.
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.transform(new DOMSource(doc), new StreamResult(out));
		} catch(Exception ex) {
			throw new IOException(ex.toString());
		}
	}

	private static Element createCatalogEntryElem(String entry,
												  String nsPrefix,
												  Document doc) {
		//make the catalogentry portion of the general elem
		Element catalogueElem = doc.createElement(nsPrefix + "catalogue");
		catalogueElem.appendChild(doc.createTextNode("HEAL.org"));

		Element entryElem = createElementWithLang(nsPrefix + "entry",
			  "en-US",
			  entry,
			  nsPrefix,
			  doc);
		Element catalogEntryElem = doc.createElement(nsPrefix + "catalogentry");
		catalogEntryElem.appendChild(catalogueElem);
		catalogEntryElem.appendChild(entryElem);
		return catalogEntryElem;
	}

	/**
	 * Generates the metametadata element which contains the catalog entry
	 * which contains the global id, the metadata scheme and version, the
	 * language, and the contributors.
	 */
	private static Element createMetametadataElem(CompleteMetadataBean cmb,
												  String nsPrefix,
												  Document doc) {
		Element metaElem, catalogElem, schemeElem, languageElem;
		if(cmb == null || doc == null) {
			return null;
		}
		MetadataBean metadata = cmb.getMetadata();
//	String metadataId = metadata.getMetadataId();
		String globalId = metadata.getGlobalId();

		metaElem = doc.createElement(nsPrefix + "metametadata");
		catalogElem = createCatalogEntryElem(globalId, nsPrefix, doc);
		metaElem.appendChild(catalogElem);
		appendContributors(cmb, metaElem, nsPrefix, doc);
		schemeElem = doc.createElement(nsPrefix + "metadatascheme");
		schemeElem.appendChild(doc.createTextNode("IMS:1.1"));
		metaElem.appendChild(schemeElem);
		languageElem = doc.createElement(nsPrefix + "language");
		languageElem.appendChild(doc.createTextNode("en-US"));
		metaElem.appendChild(languageElem);
		return metaElem;
	}

	/**
	 * Given an element, appends the contributors specified in the complete
	 * metadata bean to the given element.
	 */
	private static void appendContributors(CompleteMetadataBean cmb,
										   Element parentElem,
										   String nsPrefix,
										   Document doc) {
		if(cmb == null || parentElem == null || doc == null) {
			return;
		}
		TreeMap contributors = cmb.getContributors();
		String contributeDate = null;
		String role = null;
		String vCard = null;
		ContributorBean contributor = null;
		Element dateElem, roleElem, contributorElem, vCardElem;
		ArrayList contribList;
		Iterator listIterator;

		contributeDate = DateTools.format(cmb.getContributeDate());
		if(contributors != null) {
			Iterator contributorIterator = contributors.keySet().iterator();
			while(contributorIterator.hasNext()) {
				role = (String)contributorIterator.next();
				contribList = (ArrayList)contributors.get(role);
				listIterator = contribList.iterator();
				contributorElem = doc.createElement(nsPrefix + "contribute");
				roleElem = createElementWithLang(nsPrefix + "role",
					  null,
					  role,
					  nsPrefix,
					  doc);
				contributorElem.appendChild(roleElem);

				while(listIterator.hasNext()) {
					contributor = (ContributorBean)listIterator.next();
					vCard = contributor.getVCard();
					vCardElem = createVCardElem(vCard, nsPrefix, doc);
					contributorElem.appendChild(vCardElem);
				}

				if(contributeDate != null) {
					dateElem = createDateElem(contributeDate, nsPrefix, doc);
					contributorElem.appendChild(dateElem);
				}
				parentElem.appendChild(contributorElem);
			}
		}
	}

	/**
	 * Generates a vcard element that adheres to the specified
	 * IMS hierarchy (centity containing a vcard).
	 */
	private static Element createVCardElem(String vCard,
										   String nsPrefix,
										   Document doc) {
		if(vCard == null || doc == null) {
			return null;
		}
		Element centityElem = doc.createElement(nsPrefix + "centity");
		Element vCardElem = doc.createElement(nsPrefix + "vcard");
		vCardElem.appendChild(doc.createTextNode(vCard));
		centityElem.appendChild(vCardElem);
		return centityElem;
	}

	/**
	 * Given a date string, creates a date and datetime element that adheres
	 * to the IMS's specifications for dates.
	 */
	private static Element createDateElem(String date,
										  String nsPrefix,
										  Document doc) {
		if(date == null || doc == null) {
			return null;
		}
		Element dateElem = doc.createElement(nsPrefix + "date");
		Element dateTimeElem = doc.createElement(nsPrefix + "datetime");
		dateTimeElem.appendChild(doc.createTextNode(date));
		dateElem.appendChild(dateTimeElem);
		return dateElem;
	}


	private static Element createGeneralElem(CompleteMetadataBean cmb,
											 String nsPrefix,
											 Document doc) {
		if(cmb == null || doc == null) {
			return null;
		}
		Element generalElem, catalogElem, descriptionElem, languageElem;
//	Element keywordsElem, titleElem, extensionElem;
		Element titleElem, extensionElem;

		MetadataBean metadata = cmb.getMetadata();

		generalElem = doc.createElement(nsPrefix + "general");

		if(metadata == null) {
			return generalElem;
		}

//	String metadataId = metadata.getMetadataId();
		String title = metadata.getTitle();
		String globalId = metadata.getGlobalId();
		String description = metadata.getDescription();
		//ArrayList keywords = cmb.getKeywords();

		titleElem = createElementWithLang(nsPrefix + "title",
			  null,
			  title,
			  nsPrefix,
			  doc);
		generalElem.appendChild(titleElem);
		catalogElem = createCatalogEntryElem(globalId, nsPrefix, doc);
		generalElem.appendChild(catalogElem);
		languageElem = doc.createElement(nsPrefix + "language");
		languageElem.appendChild(doc.createTextNode("en-US"));
		generalElem.appendChild(languageElem);
		descriptionElem = createElementWithLang(nsPrefix + "description",
			  null,
			  description,
			  nsPrefix,
			  doc);
		generalElem.appendChild(descriptionElem);
		//appendKeywordList(generalElem,keywords,nsPrefix,doc);

		extensionElem = createGeneralExtensionElem(cmb, nsPrefix, doc);
		generalElem.appendChild(extensionElem);

		return generalElem;
	}

	private static Element createGeneralExtensionElem(CompleteMetadataBean cmb,
													  String nsPrefix,
													  Document doc) {
		if(cmb == null || doc == null) {
			return null;
		}
		Element extensionElem;

		MetadataBean meta = cmb.getMetadata();

		extensionElem = doc.createElement(nsPrefix + "extension");

		if(meta == null) {
			return extensionElem;
		}

		extensionElem.appendChild(createTextElement("specimentype",
			  meta.getSpecimenType(),
			  doc));
		extensionElem.appendChild(createTextElement("radiographtype",
			  meta.getRadiographType(),
			  doc));
		extensionElem.appendChild(createTextElement("orientation",
			  meta.getOrientation(),
			  doc));
		extensionElem.appendChild(createTextElement("magnification",
			  meta.getMagnification(),
			  doc));
		extensionElem.appendChild(createTextElement("annotated",
			  booleanToYesNo(meta.isAnnotated()),
			  doc));

		for(Iterator iter = cmb.getDiseaseDiagnoses().iterator(); iter.hasNext();) {
			DiseaseDiagnosisBean value = (DiseaseDiagnosisBean)iter.next();
			extensionElem.appendChild(createTextElement("diseaseprocess",
				  value.getDiseaseDiagnosis(), doc));
		}

		extensionElem.appendChild(createTextElement("clinicalhistory",
			  meta.getClinicalHistory(),
			  doc));
		appendDiseaseDiagnosesList(extensionElem,
			  cmb.getDiseaseDiagnoses(),
			  doc);

		appendContextURLList(extensionElem, cmb.getContextURLs(), doc);

		extensionElem.appendChild(createTextElement("inappropriate",
			  booleanToYesNo(meta.isInappropriate()),
			  doc));
		return extensionElem;
	}

	private static Element createLifecycleElem(CompleteMetadataBean cmb,
											   String nsPrefix,
											   Document doc) {
		if(cmb == null || doc == null) {
			return null;
		}
		Element lifecycleElem;

		lifecycleElem = doc.createElement(nsPrefix + "lifecycle");
		appendContributors(cmb, lifecycleElem, nsPrefix, doc);

		return lifecycleElem;

	}

	private static Element createTechnicalElem(CompleteMetadataBean cmb,
											   FileLocator fileLocator,
											   String nsPrefix,
											   Document doc) {
		if(cmb == null || doc == null) {
			return null;
		}
		Element technicalElem, extensionElem, locationElem;
		Element durationElem;
		MetadataBean meta = cmb.getMetadata();

		technicalElem = doc.createElement(nsPrefix + "technical");
		extensionElem = createTechnicalExtensionElem(cmb, nsPrefix, doc);
		technicalElem.appendChild(extensionElem);
		if(meta != null) {
			technicalElem.appendChild(createTextElement(nsPrefix + "size",
				  meta.getFileSize(),
				  doc));
			String location = fileLocator.getContentURL(meta.getLocation());
			locationElem = doc.createElement(nsPrefix + "location");
			locationElem.setAttribute("type", "URI");
			locationElem.appendChild(doc.createTextNode(location));
			technicalElem.appendChild(locationElem);
		}
		appendRequirementsList(technicalElem,
			  cmb.getRequirements(),
			  nsPrefix,
			  doc);

		String duration = meta.getDuration();
		durationElem = doc.createElement(nsPrefix + "duration");
		if(duration != null) {
			try {
				long durationLong = Long.parseLong(duration);
				durationElem.appendChild(createDateTimeElem(durationLong,
					  nsPrefix,
					  doc));
			} catch(NumberFormatException ex) {
				//XXX ignore for now??  Should I output an error, log one?
			}
		}
		technicalElem.appendChild(durationElem);

		return technicalElem;
	}

	/**
	 * Creates an element of the form:
	 * <datetime>yyyy-mm-ddThh:mm:ss</datetime>
	 * The string is meant to be a duration, not a date, so a parameter of
	 * 180 will get a value of 0000-00-00T00:03:00 (3 minutes).
	 */
	private static Element createDateTimeElem(long seconds,
											  String nsPrefix,
											  Document doc) {
		long sec = seconds % 60;
		long minutes = seconds / 60;
		long min = minutes % 60;
		long hours = minutes / 60;
		String dtString = "0000-00-00T" + hours + min + sec;
		Element dateTimeElem = doc.createElement(nsPrefix + "datetime");
		dateTimeElem.appendChild(doc.createTextNode(dtString));
		return dateTimeElem;
	}


	/**
	 * Creates a technical exstension element of the following form:
	 * If the CompleteMetadataBean's metadata property is not null, you get:
	 * <extension>
	 * <filename>name</filename>      (OR <filename /> if filename is null)
	 * <fileextension>extension</fileextension>
	 * (OR <fileextension /> if fileextension is null)
	 * <filewidth>width</filewidth> (OR <filewidth /> if filewidth is null)
	 * <fileheight>height</fileheight>
	 * (OR <fileheight /> if fileheight is null)
	 * </extension>
	 * <p/>
	 * if the metadata property is null, you get:
	 * <extension />
	 */
	private static Element createTechnicalExtensionElem(CompleteMetadataBean cmb,
														String nsPrefix,
														Document doc) {
		if(cmb == null || doc == null) {
			return null;
		}
		Element extensionElem = doc.createElement(nsPrefix + "extension");
		MetadataBean meta = cmb.getMetadata();
		if(meta != null) {
			extensionElem.appendChild(createTextElement("filename",
				  meta.getFileName(),
				  doc));
			extensionElem.appendChild(createTextElement("fileextension",
				  meta.getFileExtension(),
				  doc));
			extensionElem.appendChild(createTextElement("filewidth",
				  meta.getFileWidth(),
				  doc));
			extensionElem.appendChild(createTextElement("fileheight",
				  meta.getFileHeight(),
				  doc));
		}
		return extensionElem;
	}

	private static Element createEducationalElem(CompleteMetadataBean cmb,
												 String nsPrefix,
												 Document doc) {
		if(cmb == null || doc == null) {
			return null;
		}
		Element educationalElem, learningElem;
		educationalElem = doc.createElement(nsPrefix + "educational");
		Iterator iter = cmb.getTargetUserGroups().iterator();
		while(iter.hasNext()) {
			learningElem = createElementWithLang(nsPrefix + "learningcontext",
				  "en",
				  ((TargetUserGroupBean)iter.next()).getTargetUserGroup(),
				  nsPrefix,
				  doc);
			educationalElem.appendChild(learningElem);
		}

		return educationalElem;
	}

	private static Element createRightsElem(CompleteMetadataBean cmb,
											String nsPrefix,
											Document doc) {
		if(cmb == null || doc == null) {
			return null;
		}
		Element rightsElem, hasCopyElem, extensionElem;
		ArrayList copyrights, copyrightHolders;
		String hasCopyrights;
		rightsElem = doc.createElement(nsPrefix + "rights");
		copyrights = cmb.getCopyrights();
		copyrightHolders = cmb.getCopyrightHolders();
		if((copyrights == null ||
			  copyrights.size() == 0) &&
			  (copyrightHolders == null ||
			  copyrightHolders.size() == 0)) {
			hasCopyrights = "no";
		} else {
			hasCopyrights = "yes";
		}
		hasCopyElem = createElementWithLang(nsPrefix + "copyrightorotherrestrictions",
			  null,
			  hasCopyrights,
			  nsPrefix,
			  doc);
		rightsElem.appendChild(hasCopyElem);

		extensionElem = createRightsExtension(copyrights,
			  copyrightHolders,
			  nsPrefix,
			  doc);
		rightsElem.appendChild(extensionElem);
		return rightsElem;
	}

	private static Element createRightsExtension(ArrayList copyrights,
												 ArrayList copyrightHolders,
												 String nsPrefix,
												 Document doc) {
		if(doc == null) {
			return null;
		}
		Element extensionElem, copyrightElem, holderElem;
		String holderString, copyrightString;
		CopyrightBean copyright;
		CopyrightTextBean copyrightText;
		CopyrightHolderBean holder;

		extensionElem = doc.createElement(nsPrefix + "extension");

		if(copyrightHolders != null) {
			Iterator holderIterator = copyrightHolders.iterator();
			while(holderIterator.hasNext()) {
				holder = (CopyrightHolderBean)holderIterator.next();
				holderString = holder.getVCard();
				holderElem = createVCardElem(holderString, nsPrefix, doc);
				copyrightElem = doc.createElement("copyrightholder");
				copyrightElem.appendChild(holderElem);
				extensionElem.appendChild(copyrightElem);
			}
		} else {
			copyrightElem = doc.createElement("copyrightholder");
			extensionElem.appendChild(copyrightElem);
		}

		if(copyrights != null) {
			Iterator copyrightIterator = copyrights.iterator();
			while(copyrightIterator.hasNext()) {
				copyright = (CopyrightBean)copyrightIterator.next();
				copyrightText = copyright.getCopyrightText();
				if(copyrightText != null) {
					copyrightString = copyrightText.getCopyrightText();
					copyrightElem = createElementWithLang("copyrightdescription",
						  null,
						  copyrightString,
						  nsPrefix,
						  doc);
					extensionElem.appendChild(copyrightElem);
				}
			}
		} else {
			copyrightElem = doc.createElement("copyrightdescription");
			extensionElem.appendChild(copyrightElem);
		}
		return extensionElem;
	}

	private static void appendRelations(CompleteMetadataBean cmb,
										Element parentElem,
										String nsPrefix,
										Document doc) {
		if(cmb == null || parentElem == null || doc == null) {
			return;
		}
		ArrayList relations = cmb.getRelations();
		Iterator relationIterator = relations.iterator();
		RelationBean relation;
		Element relationElem;
		Element resourceElem;
		String kind;
		String description;
		String resource;

		while(relationIterator.hasNext()) {
			relation = (RelationBean)relationIterator.next();
			relationElem = doc.createElement(nsPrefix + "relation");
			kind = relation.getKind();
			if(kind != null) {
				relationElem.appendChild(createElementWithLang(nsPrefix + "kind",
					  null,
					  kind,
					  nsPrefix,
					  doc));
			}
			description = relation.getDescription();
			resource = relation.getResource();
			resourceElem = doc.createElement(nsPrefix + "resource");
			if(description != null) {
				resourceElem.appendChild(createElementWithLang(nsPrefix + "description",
					  null,
					  description,
					  nsPrefix,
					  doc));
			}
			if(resource != null) {
				resourceElem.appendChild(createCatalogEntryElem(resource,
					  nsPrefix,
					  doc));
			}
			relationElem.appendChild(resourceElem);
			parentElem.appendChild(relationElem);
		}
	}

	private static Element createClassificationElem(CompleteMetadataBean cmb,
													String nsPrefix,
													Document doc) {
		if(cmb == null || doc == null) {
			return null;
		}
		ArrayList paths;
		SortedSet taxons;
		TaxonPathBean taxonPath;
		TaxonBean taxon;
		String source, id, entry;
		Element classificationElem, taxonElem, pathElem, entryElem;
		Iterator pathIterator, taxonIterator;
		classificationElem = doc.createElement(nsPrefix + "classification");
		paths = cmb.getTaxonPaths();
		if(paths != null) {
			pathIterator = paths.iterator();
			while(pathIterator.hasNext()) {
				taxonPath = (TaxonPathBean)pathIterator.next();
				source = taxonPath.getSource();
				pathElem = doc.createElement(nsPrefix + "taxonpath");
				pathElem.appendChild(createTextElement(nsPrefix + "source",
					  source,
					  doc));
				taxons = taxonPath.getTaxons();
				if(taxons != null) {
					taxonIterator = taxons.iterator();
					while(taxonIterator.hasNext()) {
						taxon = (TaxonBean)taxonIterator.next();
						id = taxon.getId();
						entry = taxon.getEntry();
						taxonElem = doc.createElement(nsPrefix + "taxon");
						taxonElem.appendChild(createTextElement(nsPrefix + "id",
							  id,
							  doc));
						entryElem = createElementWithLang(nsPrefix + "entry",
							  null,
							  entry,
							  nsPrefix,
							  doc);
						taxonElem.appendChild(entryElem);
						pathElem.appendChild(taxonElem);
					}
				}
				classificationElem.appendChild(pathElem);
			}
		}
		ArrayList keywords = cmb.getKeywords();
		appendKeywordList(classificationElem, keywords, nsPrefix, doc);
		return classificationElem;
	}

	/**
	 * Creates an Element of the form:
	 * <elementName>
	 * <langstring xml:lang="languageAttr">textvalue</langstring>
	 * </elementName>
	 */
	private static Element createElementWithLang(String elementName,
												 String languageAttr,
												 String textvalue,
												 String nsPrefix,
												 Document doc) {
		if(elementName == null) {
			return null;
		}
		Element retval = doc.createElement(elementName);
		if(textvalue != null) {
			retval.appendChild(createLangstring(textvalue,
				  languageAttr,
				  nsPrefix,
				  doc));
		}
		return retval;
	}

	/**
	 * Creates an element with the given name and text value.  If the value
	 * is null, then an empty element will be created:
	 * <elementName>elementValue</elementName>
	 * OR
	 * <elementName />
	 */
	private static Element createTextElement(String elementName,
											 String elementValue,
											 Document doc) {
		Element retval = doc.createElement(elementName);
		if(elementValue != null) {
			retval.appendChild(doc.createTextNode(elementValue));
		}
		return retval;
	}

	/**
	 * Creates an element with the given name and text value.  If the value
	 * is null, then an empty element will be created:
	 * <elementName>elementValue(1)</elementName>
	 * <elementName>elementValue(2)</elementName>
	 * <elementName>elementValue(3)</elementName>
	 * ...
	 * <elementName>elementValue(n)</elementName>
	 * OR
	 * <elementName />
	 */
	private static void appendElementList(Element parentElem,
										  String elementName,
										  ArrayList elementValues,
										  Document doc) {
		if(parentElem == null) {
			return;
		}
		if(elementValues == null) {
			parentElem.appendChild(doc.createElement(elementName));
		} else {
			Iterator valueIterator = elementValues.iterator();
			String value = null;
			while(valueIterator.hasNext()) {
				value = (String)valueIterator.next();
				parentElem.appendChild(createTextElement(elementName,
					  value,
					  doc));
			}
		}
	}

	/**
	 * Creates an element with the given name and text value.  If the value
	 * is null, then an empty element will be created:
	 * <contextURL>elementValue(1)</contextURL>
	 * <contextURL>elementValue(2)</contextURL>
	 * <contextURL>elementValue(3)</contextURL>
	 * ...
	 * <contextURL>elementValue(n)</contextURL>
	 * OR
	 * <contextURL />
	 */
	private static void appendContextURLList(Element parentElem,
											 ArrayList elementValues,
											 Document doc) {
		if(parentElem == null) {
			return;
		}
		if(elementValues == null) {
			parentElem.appendChild(doc.createElement("contextURL"));
		} else {
			Iterator valueIterator = elementValues.iterator();
			ContextURLBean value = null;
			while(valueIterator.hasNext()) {
				value = (ContextURLBean)valueIterator.next();
				parentElem.appendChild(createTextElement("contextURL",
					  value.getContextURL(),
					  doc));
			}
		}
	}

	/**
	 * Creates an element with the given name and text value.  If the value
	 * is null, then an empty element will be created:
	 * <format>elementValue(1)</format>
	 * <format>elementValue(2)</format>
	 * <format>elementValue(3)</format>
	 * ...
	 * <format>elementValue(n)</format>
	 * OR
	 * <format />
	 */
	private static void appendFormatList(Element parentElem,
										 ArrayList elementValues,
										 String nsPrefix,
										 Document doc) {
		if(parentElem == null) {
			return;
		}
		if(elementValues == null) {
			parentElem.appendChild(doc.createElement(nsPrefix + "format"));
		} else {
			Iterator valueIterator = elementValues.iterator();
			FormatBean value = null;
			while(valueIterator.hasNext()) {
				value = (FormatBean)valueIterator.next();
				parentElem.appendChild(createTextElement(nsPrefix + "format",
					  value.getFormat(),
					  doc));
			}
		}
	}

	/**
	 * Creates an element with the given name and text value.  If the value
	 * is null, then an empty element will be created:
	 * <keyword>elementValue(1)</keyword>
	 * <keyword>elementValue(2)</keyword>
	 * <keyword>elementValue(3)</keyword>
	 * ...
	 * <keyword>elementValue(n)</keyword>
	 * OR
	 * <keyword />
	 */
	private static void appendKeywordList(Element parentElem,
										  ArrayList elementValues,
										  String nsPrefix,
										  Document doc) {
		if(parentElem == null) {
			return;
		}
		if(elementValues == null) {
			parentElem.appendChild(doc.createElement(nsPrefix + "keyword"));
		} else {
			Iterator valueIterator = elementValues.iterator();
			KeywordBean value = null;
			while(valueIterator.hasNext()) {
				value = (KeywordBean)valueIterator.next();
				parentElem.appendChild(createTextElement(nsPrefix + "keyword",
					  value.getKeyword(),
					  doc));
			}
		}
	}

	/**
	 * Creates an element with the given name and text value.  If the value
	 * is null, then an empty element will be created:
	 * <requirements>elementValue(1)</requirements>
	 * <requirements>elementValue(2)</requirements>
	 * <requirements>elementValue(3)</requirements>
	 * ...
	 * <requirements>elementValue(n)</requirements>
	 * OR
	 * <requirements />
	 */
	private static void appendRequirementsList(Element parentElem,
											   ArrayList elementValues,
											   String nsPrefix,
											   Document doc) {
		if(parentElem == null) {
			return;
		}
		if(elementValues == null) {
			parentElem.appendChild(doc.createElement(nsPrefix + "requirements"));
		} else {
			Iterator valueIterator = elementValues.iterator();
			RequirementBean value = null;
			Element requirementElem, typeElem, nameElem;
			while(valueIterator.hasNext()) {
				value = (RequirementBean)valueIterator.next();
				requirementElem = doc.createElement(nsPrefix + "requirements");
				typeElem = createElementWithLang(nsPrefix + "type",
					  null,
					  value.getRequirementType(),
					  nsPrefix,
					  doc);
				requirementElem.appendChild(typeElem);
				nameElem = createElementWithLang(nsPrefix + "name",
					  null,
					  value.getRequirementName(),
					  nsPrefix,
					  doc);
				requirementElem.appendChild(nameElem);
				parentElem.appendChild(requirementElem);
			}
		}
	}

	/**
	 * Creates an element with the given name and text value.  If the value
	 * is null, then an empty element will be created:
	 * <diseasediagnosis>elementValue(1)</diseasediagnosis>
	 * <diseasediagnosis>elementValue(2)</diseasediagnosis>
	 * <diseasediagnosis>elementValue(3)</diseasediagnosis>
	 * ...
	 * <diseasediagnosis>elementValue(n)</diseasediagnosis>
	 * OR
	 * <diseasediagnosis />
	 */
	private static void appendDiseaseDiagnosesList(Element parentElem,
												   ArrayList elementValues,
												   Document doc) {
		if(parentElem == null) {
			return;
		}
		if(elementValues == null) {
			parentElem.appendChild(doc.createElement("diseasediagnosis"));
		} else {
			Iterator valueIterator = elementValues.iterator();
			DiseaseDiagnosisBean value = null;
			while(valueIterator.hasNext()) {
				value = (DiseaseDiagnosisBean)valueIterator.next();
				parentElem.appendChild(createTextElement("diseasediagnosis",
					  value.getDiseaseDiagnosis(),
					  doc));
			}
		}
	}

	/**
	 * Creates an element with the given name and boolean value.
	 * Example:
	 * <elementName>true</elementName>
	 * OR
	 * <elementName>false</elementName>
	 */
	private static Element createBooleanElement(String elementName,
												boolean elementValue,
												Document doc) {
		Element retval = doc.createElement(elementName);
		if(elementValue) {
			retval.appendChild(doc.createTextNode("true"));
		} else {
			retval.appendChild(doc.createTextNode("false"));
		}
		return retval;
	}

	/**
	 * Creates an Element of the form:
	 * <elementName>
	 * <langstring xml:lang="languageAttr">textvalue(1)</langstring>
	 * <langstring xml:lang="languageAttr">textvalue(2)</langstring>
	 * <langstring xml:lang="languageAttr">textvalue(3)</langstring>
	 * ...
	 * <langstring xml:lang="languageAttr">textvalue(n)</langstring>
	 * </elementName>
	 */
	private static Element createElementWithLangList(String elementName,
													 String languageAttr,
													 ArrayList textvalues,
													 String nsPrefix,
													 Document doc) {
		if(elementName == null) {
			return null;
		}
		Element retval = doc.createElement(elementName);
		if(textvalues == null) {
			return retval;
		}
		Iterator valuesIterator = textvalues.iterator();
		String value;
		while(valuesIterator.hasNext()) {
			value = (String)valuesIterator.next();
			retval.appendChild(createLangstring(value,
				  languageAttr,
				  nsPrefix,
				  doc));
		}
		return retval;
	}

	/**
	 * Creates an Element of the form:
	 * <langstring xml:lang="languageAttr">textvalue</langstring>
	 */
	private static Element createLangstring(String textvalue,
											String languageAttr,
											String nsPrefix,
											Document doc) {
		Element returnValue = doc.createElement(nsPrefix + "langstring");
		if(languageAttr != null) {
			returnValue.setAttribute("xml:lang", languageAttr);
		}
		if(textvalue != null) {
			returnValue.appendChild(doc.createTextNode(textvalue));
		}
		return returnValue;
	}

	public static String getPointerListXML(ArrayList ids)
		  throws IOException {
		if(ids == null) {
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			//create a new Document using JAXP
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();
			Element listElem = doc.createElement("metadatalist");
			Element catalogueElem = doc.createElement("catalogue");
			catalogueElem.appendChild(doc.createTextNode("HEAL.org"));
			listElem.appendChild(catalogueElem);

			Iterator idIter = ids.iterator();
			Element entryElem;
			Element langstringElem;
			while(idIter.hasNext()) {
				String id = (String)idIter.next();
				entryElem = doc.createElement("entry");
				langstringElem = doc.createElement("langstring");
				langstringElem.appendChild(doc.createTextNode(id));
				entryElem.appendChild(langstringElem);
				listElem.appendChild(entryElem);
			}

			doc.appendChild(listElem);

			//now that we have the document built, we should write it out.
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.transform(new DOMSource(doc), new StreamResult(out));
		} catch(Exception ex) {
			throw new IOException(ex.toString());
		}
		return out.toString();
	}

	/**
	 * Creates the manifest element for the given metadata, including the
	 * metadata element (hard coded settings),
	 * organizations element (currently empty), and the dynamically
	 * generated resources element.
	 * No attributes are set on the element, but it is assumed that the
	 * namespace for the IMS metadata tags is "imsmd" (specified in
	 * org.heal.module.metadata.HealMetadataXMLConverter.IMSNAMESPACETAG).
	 * This is consistent with all of the manifest examples provided by IMS.
	 */
	private static Element createManifestElement(CompleteMetadataBean[] metaInfo,
												 FileLocator fileLocator,
												 Document doc) {
		Element metametadataElem, organizationsElem, resourcesElem;
		Element manifestElem;

		//manifestElem = doc.createElement("manifest");
		manifestElem = doc.createElement("imscp:manifest");

		metametadataElem = createManifestMetadataElem(doc);
		manifestElem.appendChild(metametadataElem);

		/* since this is a dynamically generated manifest for
		 * a package constituted of potentially many disparate pieces of
		 * content, we can't really provide any insight as to the
		 * organization of the content, so we leave the organizations
		 * element empty
		 */
		organizationsElem = doc.createElement("organizations");
		manifestElem.appendChild(organizationsElem);

		resourcesElem = createManifestResourcesElem(metaInfo,
			  fileLocator,
			  doc);
		manifestElem.appendChild(resourcesElem);

		return manifestElem;
	}

	/**
	 * Creates a parent resources element containing multiple resource
	 * elements (one for each metadata).  The resource elements contain
	 * the content's metadata and the path to the file within the package.
	 * No dependency information is generated because this information is not
	 * encoded in the database.
	 */
	private static Element createManifestResourcesElem(CompleteMetadataBean[] metaInfo,
													   FileLocator fileLocator,
													   Document doc) {
		Element resourcesElem = doc.createElement("resources");
		Element resourceElem;
		for(int i = 0; i < metaInfo.length; i++) {
			resourceElem = createManifestResourceElem("RESOURCE" + i, metaInfo[i],
				  fileLocator,
				  doc);
			resourcesElem.appendChild(resourceElem);
		}
		return resourcesElem;
	}

	/**
	 * Given an identifier, metadata bean and dom document, creates a resource
	 * element for inclusion in the manifest.  The identifier is used to
	 * give a name to this element.  Usually this will just be something
	 * along the lines of "RESOURCEi" where i is a unique number in this
	 * manifest.
	 * The created element has a name of "resource" with an identifier
	 * of, well, "identifier".  The included elements are a file entry
	 * to specify the location of the file in the package and the
	 * content's metadata.  No dependency information is supplied.
	 * Also the resource tag contains a "type" attribute that is hardcoded
	 * to be "webcontent" since a) that is all we serve, and b) the IMS v1.1
	 * content packaging specification only defines that setting.
	 */
	private static Element createManifestResourceElem(String identifier,
													  CompleteMetadataBean metadata,
													  FileLocator fileLocator,
													  Document doc) {

		Element resourceElem, metadataElem, fileElem;
		resourceElem = doc.createElement("resource");
		resourceElem.setAttribute("identifier", identifier);
		resourceElem.setAttribute("type", "webcontent");
		String nsprefix = HealMetadataXMLConverter.IMSNAMESPACETAG;
		metadataElem = HealMetadataXMLConverter.metadataToElement(metadata,
			  fileLocator,
			  nsprefix,
			  doc);
		MetadataBean meta = metadata.getMetadata();
		String localpath = fileLocator.getContentURL(meta.getLocation());
		fileElem = doc.createElement("file");
		fileElem.setAttribute("href", localpath);
		resourceElem.appendChild(metadataElem);

		return resourceElem;
	}

	/**
	 * Creates an element of the form:
	 * <metadata>
	 * <schema>IMS Content</schema>
	 * <schemaversion>1.1</schemaversion>
	 * <imsmd:record>
	 * <imsmd:general>
	 * <imsmd:title>
	 * <imsmd:langstring xml:lang="en-US">Dynamically Generated HEAL IMS Content Package</imsmd:langstring>
	 * </imsmd:title>
	 * </imsmd:general>
	 * </imsmd:record>
	 * </metadata>
	 */
	private static Element createManifestMetadataElem(Document doc) {
		Element schemaElem, schemaversionElem, recordElem;
		Element generalElem, titleElem;
		//Element metadataElem = doc.createElement("metadata");
		Element metadataElem = doc.createElement("imscp:metadata");
		//schemaElem = doc.createElement("schema");
		schemaElem = doc.createElement("imscp:schema");
		schemaElem.appendChild(doc.createTextNode("IMS Content"));
		//schemaversionElem = doc.createElement("schemaversion");
		schemaversionElem = doc.createElement("imscp:schemaversion");

		schemaversionElem.appendChild(doc.createTextNode("1.1"));
		//recordElem = doc.createElement(HealMetadataXMLConverter.IMSNAMESPACETAG+":record");
		//generalElem = doc.createElement(HealMetadataXMLConverter.IMSNAMESPACETAG+":general");
		//titleElem = createManifestElementWithLang(HealMetadataXMLConverter.IMSNAMESPACETAG+":title",
		//					  "en-US",
		//					  "Dynamically Generated HEAL IMS Content Package",
		//					  doc);

		recordElem = doc.createElement("record");
		generalElem = doc.createElement("general");
		titleElem = createManifestElementWithLang("title",
			  "en-US",
			  "Dynamically Generated HEAL IMS Content Package",
			  doc);

		generalElem.appendChild(titleElem);
		recordElem.appendChild(generalElem);
		metadataElem.appendChild(schemaElem);
		metadataElem.appendChild(schemaversionElem);
		metadataElem.appendChild(recordElem);
		return metadataElem;
	}

	/**
	 * Creates an Element of the form:
	 * <elementName>
	 * <langstring xml:lang="languageAttr">textvalue</langstring>
	 * </elementName>
	 */
	private static Element createManifestElementWithLang(String elementName,
														 String languageAttr,
														 String textvalue,
														 Document doc) {
		Element retval = doc.createElement(elementName);
		retval.appendChild(createManifestLangstring(textvalue,
			  languageAttr,
			  doc));
		return retval;
	}

	/**
	 * Creates an Element of the form:
	 * <langstring xml:lang="languageAttr">textvalue</langstring>
	 */
	private static Element createManifestLangstring(String textvalue,
													String languageAttr,
													Document doc) {
		//Element returnValue = doc.createElement(HealMetadataXMLConverter.IMSNAMESPACETAG+":langstring");
		Element returnValue = doc.createElement("langstring");

		if(languageAttr != null) {
			returnValue.setAttribute("xml:lang", languageAttr);
		}
		returnValue.appendChild(doc.createTextNode(textvalue));
		return returnValue;
	}


	/**
	 * Given an array of metadata beans, creates a manifest file for an IMS
	 * package containing the given metadata and content.  The XML manifest
	 * is then written to the provided output stream.
	 */
	public static void getManifestIMSXML(CompleteMetadataBean[] metaInfo,
										 FileLocator fileLocator,
										 OutputStream out)
		  throws IOException {
		try {
			//create a new Document using JAXP
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();

			Element manifestElem = createManifestElement(metaInfo,
				  fileLocator,
				  doc);

			manifestElem.setAttribute("identifier", "MANIFEST1");
			manifestElem.setAttribute("xmlns:imscp", "http://www.imsproject.org/xsd/ims_cp_rootv1p1");
			//manifestElem.setAttribute("xmlns:"+HealMetadataXMLConverter.IMSNAMESPACETAG,
			//	      "http://www.imsproject.org/xsd/ims_md_rootv1p1");

			manifestElem.setAttribute("xmlns",
				  "http://www.healcentral.org/xsd/healmd_v1p5");

			manifestElem.setAttribute("xmlns:xsi", "http://www.w3.org/2000/10/XMLSchema-instance");
			//manifestElem.setAttribute("xsi:schemaLocation","http://www.imsproject.org/xsd/ims_cp_rootv1p1 http://www.imsproject.org/xsd/ims_cp_rootv1p1.xsd http://www.imsproject.org/xsd/ims_md_rootv1p1 http://www.imsproject.org/xsd/ims_md_rootv1p1.xsd");
			manifestElem.setAttribute("xsi:schemaLocation", "http://www.imsproject.org/xsd/ims_cp_rootv1p1 http://www.imsproject.org/xsd/ims_cp_rootv1p1.xsd http://www.healcentral.org/services/schema/HEALmdSchemaXMLv1p5 http://www.healcentral.org/services/schema/HEALmdSchemaXMLv1p5.xsd");


			doc.appendChild(manifestElem);

			//now that we have the document built, we should write it out.
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.transform(new DOMSource(doc), new StreamResult(out));
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new IOException(ex.toString());
		}
	}

	/**
	 * if bool is true, "yes" is returned - otherwise "no" is returned
	 */
	private static String booleanToYesNo(boolean bool) {
		return (bool ? "yes" : "no");
	}

}
