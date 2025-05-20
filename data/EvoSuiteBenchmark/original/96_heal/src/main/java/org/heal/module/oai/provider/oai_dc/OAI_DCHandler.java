/*
 * Created on Dec 1, 2004
 *
 */
package org.heal.module.oai.provider.oai_dc;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.heal.module.oai.provider.OAIMetadataFormat;
import org.heal.module.oai.provider.OAIMetadataHandler;
import org.heal.module.oai.provider.OAIRecordAdapter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Seth Wright
 *
 */
public class OAI_DCHandler implements OAIMetadataHandler {
	public static final String SIMPLEDC_PREFIX = "dc";
	private final OAIMetadataFormat metadataFormat;

	public OAI_DCHandler(final OAIMetadataFormat format) {
		this.metadataFormat = format;
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
		OAI_DCRecordAdapter recordAdapter = (OAI_DCRecordAdapter) record;
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
            
			recordElem.setAttribute("xsi:schemaLocation",metadataFormat.getSchemaLocation());

			//allow the recordAdapter to add any namespaces, etc necessary.
			recordAdapter.addRecordAttributes(recordElem,doc);
			recordAdapter.appendTitleElements(recordElem,doc);
			recordAdapter.appendCreatorElements(recordElem,doc);
			recordAdapter.appendSubjectElements(recordElem,doc);
			recordAdapter.appendDescriptionElements(recordElem,doc);
			recordAdapter.appendPublisherElements(recordElem,doc);
			recordAdapter.appendContributorElements(recordElem,doc);
			recordAdapter.appendDateElements(recordElem,doc);
			recordAdapter.appendTypeElements(recordElem,doc);
			recordAdapter.appendFormatElements(recordElem,doc);
			recordAdapter.appendIdentifierElements(recordElem,doc);
			recordAdapter.appendSourceElements(recordElem,doc);
			recordAdapter.appendLanguageElements(recordElem,doc);
			recordAdapter.appendRelationElements(recordElem,doc);
			recordAdapter.appendCoverageElements(recordElem,doc);
			recordAdapter.appendRightsElements(recordElem,doc);

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
		} catch (ParserConfigurationException e) {
			StringWriter swriter = new StringWriter();
			PrintWriter exwriter = new PrintWriter(swriter);
			e.printStackTrace(exwriter);
			throw new IOException(exwriter.toString());
		} catch (TransformerConfigurationException e) {
			StringWriter swriter = new StringWriter();
			PrintWriter exwriter = new PrintWriter(swriter);
			e.printStackTrace(exwriter);
			throw new IOException(exwriter.toString());
		} catch (TransformerException e) {
			StringWriter swriter = new StringWriter();
			PrintWriter exwriter = new PrintWriter(swriter);
			e.printStackTrace(exwriter);
			throw new IOException(exwriter.toString());
		}
	}	

	public void writeCustomAbout(final OAIRecordAdapter record, final PrintWriter writer) 
	throws IOException {
		OAI_DCRecordAdapter recordAdapter = (OAI_DCRecordAdapter) record;
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
		OAI_DCRecordAdapter recordAdapter = (OAI_DCRecordAdapter) record;
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
}
