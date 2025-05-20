package br.com.jnfe.base.service;

import static org.junit.Assert.assertEquals;

import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Element;


/**
 * 
 * @author mauriciofernandesdecastro
 */
public class Pkcs12SecurityHandlerBeanTests {
	
	private SecurityHandler securityHandler;
	
	@Test
	public void handle() {
		SecurityCallBack action = new SecurityCallBack() {
			public void doInSecurityContext(Element sourceElement, Element elementToSign, Certificate certificate, PrivateKey privateKey) {
				assertEquals("CN=NFe - AC Intermediaria 1,OU=Teste Projeto NFe RS,O=Teste Projeto NFe RS,L=Porto Alegre,ST=RS,C=BR", ((X509Certificate) certificate).getIssuerX500Principal().getName());
				assertEquals("RSA", privateKey.getAlgorithm());
				try {
					for (Byte b: ((X509Certificate)certificate).getEncoded()) {
						System.out.print(Byte.toString(b));
						
					}
				} catch (CertificateEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		securityHandler.handle(null, null, action);
	}
	
	@Before
	public void setUp() throws Exception {
		securityHandler = new Pkcs12SecurityHandlerBean("{e8275963-aa40-438a-9fe9-3a928de76e58}", "associacao");
		((Pkcs12SecurityHandlerBean) securityHandler).setLocation(new ClassPathResource("Associacao.pfx"));
		((Pkcs12SecurityHandlerBean) securityHandler).afterPropertiesSet();
	}

}
