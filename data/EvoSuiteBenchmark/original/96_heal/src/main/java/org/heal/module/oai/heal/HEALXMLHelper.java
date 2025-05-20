/*
 * Created on Apr 10, 2005
 *
 */
package org.heal.module.oai.heal;

import java.util.Date;

import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.oai.provider.OAIGranularity;
import org.heal.module.oai.provider.oai_dc.OAI_DCHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * @author swright
 *
 */
public class HEALXMLHelper {
	public static final String HEALNS="heal";
	public static final String IMSNS="ims";
	public static final String IMSLOC="http://www.imsproject.org/metadata";
	public static final String HEALLOC="http://www.healcentral.org/xsd/healmd_v1p5";
	public static final String HEALSCHEMA="http://www.healcentral.org/xsd/healmd_v1p5 http://www.healcentral.org/services/schema/HEALmdSchemaXMLv1p5050329.xsd";

	public static void addCustomAbout(CompleteMetadataBean metadataBean, HEALProviderConfig config,Document doc) {
		Element metaElement = doc.createElement(HEALNS+":metametadata");
		metaElement.setAttribute("xmlns:"+HEALNS,HEALLOC);
		metaElement.setAttribute("xmlns:"+IMSNS,IMSLOC);
		metaElement.setAttribute("xmlns:"+OAI_DCHandler.SIMPLEDC_PREFIX,"http://purl.org/dc/elements/1.1/");
		metaElement.setAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance"); 
		metaElement.setAttribute("xsi:schemaLocation",HEALSCHEMA);

		Element catalogEntryElem = doc.createElement(HEALNS+":catalogentry");
		appendTextElem(HEALNS+":catalogue","http://www.healcentral.org",catalogEntryElem,doc);
		appendTextElemWithLangstring(HEALNS+":entry",HEALNS,metadataBean.getGlobalId(),null,catalogEntryElem,doc);	
		metaElement.appendChild(catalogEntryElem);

		Element contributorElem = doc.createElement(HEALNS+":contribute");
		Element roleElem = doc.createElement(HEALNS+":role");
//TODO: make source setting part of the configuration to externalize it
		appendTextElemWithLangstring(HEALNS+":source",HEALNS,"HEALv1.5",null,roleElem,doc);
		//<source><langstring>HEALv1.5</langstring></source>
		appendTextElemWithLangstring(HEALNS+":value",HEALNS,config.getMetametadataRole(),null,roleElem,doc);
		contributorElem.appendChild(roleElem);
		Element centityElem = doc.createElement(HEALNS+":centity");
		Element vCardElem = doc.createElement(HEALNS+":vcard");
		vCardElem.appendChild(doc.createTextNode(config.getHEALVCard()));
		centityElem.appendChild(vCardElem);
		contributorElem.appendChild(centityElem);
		Date contributeDate = metadataBean.getContributeDate();
		if (contributeDate != null) {
			Element dateElem = doc.createElement(HEALNS+":date");
			Element dateTimeElem = doc.createElement(HEALNS+":datetime");
			String dateStr =  OAIGranularity.yearMonthDayHourMinuteSecond.format(contributeDate);
			dateTimeElem.appendChild(doc.createTextNode(dateStr));
			dateElem.appendChild(dateTimeElem);
			contributorElem.appendChild(dateElem); 
		}
		metaElement.appendChild(contributorElem);
		Element extensionElem = doc.createElement(HEALNS+":extension");
		appendTextElem(HEALNS+":healmetadatascheme","HEAL:1.5",extensionElem,doc);
		metaElement.appendChild(extensionElem);
		appendTextElem(HEALNS+":metadatascheme","IMS:1.2",metaElement,doc);
		appendTextElem(HEALNS+":language","en",metaElement,doc);
		doc.appendChild(metaElement);
	}

	public static String getFullNameFromVCard(String vcard) {
		if (vcard != null) {
			int fnIndex = vcard.indexOf("fn:");
			if (fnIndex >=0) {
				int len = vcard.length();
				int stopIndex = fnIndex+3;
				boolean endOfLine = false;
				char here;
				while (stopIndex < len && !endOfLine) {
					here = vcard.charAt(stopIndex);
					if (here == '\n' || here == '\r') {
						endOfLine = true;
					}
					stopIndex++;
				}
				return vcard.substring(fnIndex+3,stopIndex);
			}
		}
		return null;
	}

	public static void appendTextElemWithLangstring(String prefix,String namespace,String value, String language, Element parentElem, Document doc) {
		if (value == null) return;
		String real = value.trim();
		if (real.length() > 0) {
			Element outerElement = doc.createElement(prefix);
			Element langStrElement = doc.createElement(namespace+":langstring");
			if (language != null) {
				langStrElement.setAttribute("lang",language);
			}
			Text textElement = doc.createTextNode(real);
			langStrElement.appendChild(textElement);
			outerElement.appendChild(langStrElement);
			parentElem.appendChild(outerElement);		
		}		
	}

	public static void appendTextElemWithDescriptor(String prefix, String descriptor, String value, Element parentElem, Document doc) {
		if (value == null) return;
		appendTextElem(prefix,descriptor+": "+value,parentElem,doc);
	}
	
	public static void appendTextElem(String prefix,String value,Element parentElem,Document doc) {
		if (value == null) return;
		String real = value.trim();
		if (real.length() > 0) {
			Element outerElement = doc.createElement(prefix);
			Text textElement = doc.createTextNode(real);
			outerElement.appendChild(textElement);
			parentElem.appendChild(outerElement);		
		}
	}
}
