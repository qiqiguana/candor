/* Copyright 2005 I Serv Consultoria Empresarial Ltda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.jnfe.base.service;

import java.security.PrivateKey;
import java.security.cert.Certificate;

import javax.annotation.Resource;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dom.DOMStructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Implementação da interface <code>SignatureHandler</code> usando DOM.
 * 
 * @author Mauricio Fernandes de Castro
 */
public class DOMNFeSignatureHandler implements SignatureHandler {
	
	/**
	 * Assina com o auxílio de securityHandler e signatureBuilder.
	 * 
	 * @param sourceToSign
	 * @param tagNameToSign
	 * 
	 * @throws Exception
	 */
	public void sign(XMLStructure sourceStructure, String tagNameToSign) throws Exception {
		Element sourceElement = (Element) ((DOMStructure) sourceStructure).getNode();
		sourceElement.normalize();
		logger.debug("Pronto para extrair {} para assinatura de {}.", tagNameToSign, sourceElement);
		NodeList elementsToSign = sourceElement.getElementsByTagName(tagNameToSign);
		if (elementsToSign!=null && elementsToSign.getLength()>0) {
			for (int i = 0; i < elementsToSign.getLength(); i++) {
				Element elementToSign = (Element) elementsToSign.item(i);
				Element parentElement = (Element) elementToSign.getParentNode();
				logger.debug("Pronto para invocar 'securityHandler' para assinar {} #{}.", elementToSign.getTagName(), i);
				securityHandler.handle(parentElement, elementToSign , new SecurityCallBack() {
					public void doInSecurityContext(Element parentElement, Element elementToSign, Certificate certificate, PrivateKey privateKey) {
						signatureBuilder.build(elementToSign, parentElement, certificate, privateKey);
					}
				});
			}
		}
		else {
			logger.warn("Não foi encontrada a tag {} para assinar.", tagNameToSign);
		}
	}
	
	// collabs
	
	private SecurityHandler securityHandler;
	private SignatureBuilder<Element> signatureBuilder;
	
	@Resource
	public void setSecurityHandler(SecurityHandler securityHandler) {
		this.securityHandler = securityHandler;
	}
	
	@Resource
	public void setSignatureBuilder(SignatureBuilder<Element> signatureBuilder) {
		this.signatureBuilder = signatureBuilder;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(DOMNFeSignatureHandler.class);

}
