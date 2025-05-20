package br.com.jnfe.base.service;

import java.security.cert.Certificate;
import java.util.Collections;

import javax.annotation.Resource;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementação padrão da interface <code>KeyInfoBuilder</code>.
 *  
 * @author mauriciofernandesdecastro
 */
public class DOMNFeKeyInfoBuilder implements KeyInfoBuilder {

	public KeyInfo newKeyInfo(Certificate certificate) {
		KeyInfoFactory kif = signatureFactory.getKeyInfoFactory();
		X509Data x509Data = kif.newX509Data(Collections.singletonList(certificate));
		KeyInfo keyInfo = kif.newKeyInfo(Collections.singletonList(x509Data));
		logger.debug("Elemento <KeyInfo ...> preparado para assinatura.");
		return keyInfo;
	}

	// collabs
	
	private XMLSignatureFactory signatureFactory;

	@Resource
	protected void setXMLSignatureFactory(XMLSignatureFactory signatureFactory) {
		this.signatureFactory = signatureFactory;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(DOMNFeKeyInfoBuilder.class);
	
}
