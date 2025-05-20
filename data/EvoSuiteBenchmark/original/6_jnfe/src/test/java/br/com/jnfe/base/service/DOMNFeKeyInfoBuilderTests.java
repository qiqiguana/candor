package br.com.jnfe.base.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.X509Certificate;

import javax.annotation.Resource;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
 * @author mauriciofernandesdecastro
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/META-INF/spring/jnfe-base-context-test.xml",
		"classpath:/META-INF/spring/jnfe-base-context.xml"})
public class DOMNFeKeyInfoBuilderTests {
	
	@Test
	public void newKeyInfo() throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException, MarshalException, TransformerException, UnsupportedEncodingException, IOException {
		assertTrue(keyInfoBuilder instanceof KeyInfoBuilder);
		X509Certificate certificate = securityHandlerBean.getCertificate();
		KeyInfo keyInfo = keyInfoBuilder.newKeyInfo(certificate);
		Element root = document.getDocumentElement();
		keyInfo.marshal(new DOMStructure(root), null);
		Element keyInfoElement = (Element) root.getElementsByTagName("KeyInfo").item(0);
		assertNotNull(keyInfoElement);
		Element x509DataElement = (Element) keyInfoElement.getElementsByTagName("X509Data").item(0);
		assertNotNull(x509DataElement);
		Element x509CertificateElement = (Element) x509DataElement.getElementsByTagName("X509Certificate").item(0);
		assertNotNull(x509CertificateElement);
		char[] cert = x509CertificateElement.getTextContent().toCharArray();
		// este arquivo foi peviamente gerado usando keytool para extrair uma representação
		// do certificado em Associacao.pfx
		ClassPathResource testCertificate = new ClassPathResource("associacao.cert.txt");
		InputStreamReader in = new InputStreamReader(testCertificate.getInputStream(), "UTF-8");
		int i, j = 0;
		boolean success = false;
		while((i=in.read())!=-1 && j!=cert.length) {
			success = true;
			if (cert[j++]!=(char) i) {
				j = 0;
				success = false;
			}
		}
		assertTrue(success);
		
	}
	
	@Before
	public void setUp() throws Exception {
		// Criamos um documento para ligar a KeyInfo
	    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
	    DOMImplementation di = documentBuilder.getDOMImplementation();
	    document = di.createDocument("http://www.w3.org/2000/09/xmldsig#", "Signature", null);

	    securityHandlerBean.setAlias("{e8275963-aa40-438a-9fe9-3a928de76e58}");
	    securityHandlerBean.setPassword("associacao".toCharArray());
	    securityHandlerBean.setLocation(new ClassPathResource("Associacao.pfx"));
	    securityHandlerBean.afterPropertiesSet();
	}
	
	private Document document;
	
	private DocumentBuilderFactory documentBuilderFactory;
	private DOMNFeKeyInfoBuilder keyInfoBuilder;
	private Pkcs12SecurityHandlerBean securityHandlerBean;
	
	@Resource
	public void setDocumentBuilderFactory(DocumentBuilderFactory documentBuilderFactory) {
		this.documentBuilderFactory = documentBuilderFactory;
	}
	
	@Resource
	public void setDomnFeKeyInfoBuilder(DOMNFeKeyInfoBuilder keyInfoBuilder) {
		this.keyInfoBuilder = keyInfoBuilder;
	}
	
	@Resource
	public void setSecurityHandlerBean(Pkcs12SecurityHandlerBean securityHandlerBean) {
		this.securityHandlerBean = securityHandlerBean;
	}

}
