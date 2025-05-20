package br.com.jnfe.base.service;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.PrivateKey;
import java.security.cert.Certificate;

import javax.annotation.Resource;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 
 * @author mauriciofernandesdecastro
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/META-INF/spring/jnfe-base-context-test.xml",
		"classpath:/META-INF/spring/jnfe-base-context.xml"})
public class DOMNFeFileReaderTests {
	
	private DOMNFeFileReader nFeFileReader;
	
	@Test
	public void loadAndSign() throws Exception {
		
		securityHandler.setAlias("{e8275963-aa40-438a-9fe9-3a928de76e58}");
		securityHandler.setPassword("associacao".toCharArray());
		securityHandler.setLocation(new ClassPathResource("Associacao.pfx"));
		securityHandler.afterPropertiesSet();
		
		InputStreamSource resource = new ClassPathResource("enviNFe.xml");
		final StreamResult streamResult = (StreamResult) nFeFileReader.loadAndSign(resource.getInputStream(), "infNFe");
		final ByteArrayOutputStream outputStream = (ByteArrayOutputStream) streamResult.getOutputStream();
		// usamos security handler para fazer o trabalho de extrair a chave pública
        securityHandler.handle(null, null, new SecurityCallBack() {
			public void doInSecurityContext(Element arg1, Element arg2, Certificate certificate,
					PrivateKey privateKey) {
				try {
					Document doc = documentBuilderFactory.newDocumentBuilder().parse(new ByteArrayInputStream(outputStream.toByteArray()));
					NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
			        assertTrue(nl.getLength()!=0);
			        DOMValidateContext valContext = new DOMValidateContext(certificate.getPublicKey(), nl.item(0));
			        XMLSignature signature = signatureFactory.unmarshalXMLSignature(valContext);
			        assertTrue(signature.validate(valContext));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}); 
//		System.out.println("Teste da assinatura "+outputStream.toString());
	}
	
	@Resource
	public void setNFeFileReader(DOMNFeFileReader nFeFileReader) {
		this.nFeFileReader = nFeFileReader;
	}
	
	// collabs
	
	private DocumentBuilderFactory documentBuilderFactory;
	private XMLSignatureFactory signatureFactory;
	private Pkcs12SecurityHandlerBean securityHandler;
	
	@javax.annotation.Resource
	public void setDocumentBuilderFactory(DocumentBuilderFactory documentBuilderFactory) {
		this.documentBuilderFactory = documentBuilderFactory;
	}

	@javax.annotation.Resource
	protected void setXMLSignatureFactory(XMLSignatureFactory signatureFactory) {
		this.signatureFactory = signatureFactory;
	}

	@Resource
	public void setSecurityHandler(Pkcs12SecurityHandlerBean securityHandler) {
		this.securityHandler = securityHandler;
	}
	
}
