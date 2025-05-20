package br.com.jnfe.base.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.crypto.dom.DOMStructure;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Lê um arquivo do sistema e assina.
 * 
 * @author mauriciofernandesdecastro
 */
public class DOMNFeFileReader {
	
	/**
	 * Lê um arquivo e assina.
	 * 
	 * @param filePath
	 * @param tagToSign
	 * 
	 * @throws Exception
	 */
	public Result loadAndSign(String filePath, String tagToSign) throws Exception {
		InputStreamSource resource = new ClassPathResource(filePath);
		return loadAndSign(resource.getInputStream(), tagToSign);
	}
	
	/**
	 * Lê um arquivo e assina.
	 * 
	 * @param inputStream
	 * @param tagToSign
	 * 
	 * @throws Exception
	 */
	public Result loadAndSign(InputStream inputStream, String tagToSign) throws Exception {
		DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
		Document document = builder.parse(inputStream);
		
		Element root = document.getDocumentElement();
		Element parent = (Element) document.getElementsByTagName("NFe").item(0);
		
		signatureHandler.sign(new DOMStructure(parent), tagToSign);
		
		OutputStream outputResult = new ByteArrayOutputStream();
		StreamResult streamResult = new StreamResult(outputResult);
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.transform(new DOMSource(root), streamResult);
		} catch (TransformerException e) {
			throw new IllegalArgumentException("Impossível transformar assinatura, ", e);
		}
		return streamResult;
	}
	
	// collabs
	
	private DocumentBuilderFactory documentBuilderFactory;
	private SignatureHandler signatureHandler;
	
	@javax.annotation.Resource
	public void setDocumentBuilderFactory(DocumentBuilderFactory documentBuilderFactory) {
		this.documentBuilderFactory = documentBuilderFactory;
	}
	
	@javax.annotation.Resource
	public void setSignatureHandler(SignatureHandler signatureHandler) {
		this.signatureHandler = signatureHandler;
	}

}
