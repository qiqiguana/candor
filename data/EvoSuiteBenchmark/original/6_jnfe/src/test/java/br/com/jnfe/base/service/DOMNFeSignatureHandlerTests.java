package br.com.jnfe.base.service;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * 
 * @author Mauricio Fernandes de Castro
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/META-INF/spring/jnfe-base-context-test.xml",
		"classpath:/META-INF/spring/jnfe-base-context.xml"})
public class DOMNFeSignatureHandlerTests {
	
	@Test
	public void signNFe() throws Exception {
		InputStreamSource resource = new ClassPathResource("NFe.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(resource.getInputStream());
		signatureHandler.sign(new DOMStructure(document.getDocumentElement()), "infNFe");
		
		Node signature = document.getDocumentElement().getElementsByTagName("Signature").item(0);
		Node signedInfo = ((Element) signature).getElementsByTagName("SignedInfo").item(0);
		Node reference = ((Element) signedInfo).getElementsByTagName("Reference").item(0);
		assertEquals("#NFe41000276489525000102550010000000010000000010", reference.getAttributes().getNamedItem("URI").getTextContent());
	}
	
	// collabs
	
	private SignatureHandler signatureHandler;
	
	@Resource
	public void setSignatureHandler(SignatureHandler signatureHandler) {
		this.signatureHandler = signatureHandler;
	}

}
