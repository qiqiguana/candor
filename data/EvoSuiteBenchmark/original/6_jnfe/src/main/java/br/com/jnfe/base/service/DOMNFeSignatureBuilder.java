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

import java.io.InputStreamReader;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;

import org.jcp.xml.dsig.internal.dom.DOMReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.w3c.dom.Element;


/**
 * Implementação para <code>NFeSignatureBuilder</code> com a tag a assinar
 * representada por um elemento DOM.
 * 
 * <p>
 * Esta implementação difere da especificação WS Security 1.1 para 
 * implementar o subconjunto definido no manual de integração do contribuinte,
 * versão 3.0 de Março de 2009, conforme abaixo.
 * </p>
 * 
 * <table border="1">
 * <tr><td>#</td><td>Campo</td><td>Ele</td><td>Pai</td><td>Tipo</td><td>Ocor.</td>
 * <td>Descrição/Observação</td></tr>
 * <tr><td>XS01</td><td>Signature</td><td>Raiz</td><td>-</td><td>-</td><td>-</td><td>-
 * <tr><td>XS02</td><td>SignedInfo</td><td>G</td><td>XS01</td><td>-</td><td>1-1</td>
 * <td>Grupo da Informação da assinatura</td></tr>
 * <tr><td>XS03</td><td>Canonicalization Method</td><td>G</td><td>XS02</td><td>-</td><td>1-1</td>
 * <td>Grupo do Método de Canonicalização</td></tr>
 * <tr><td>XS04</td><td>Algorithm</td><td>A</td><td>XS03</td><td>C</td><td>1-1</td>
 * <td>Atributo Algorithm de CanonicalizationMethod: http://www.w3.org/TR/2001/REC-xml-c14n-20010315</td></tr>
 * <tr><td>XS05</td><td>SignatureMethod</td><td>G</td><td>XS02</td><td>-</td><td>1-1</td>
 * <td>Grupo do Método de Assinatura</td></tr>
 * <tr><td>XS06</td><td>Algorithm</td><td>A</td><td>XS05</td><td>C</td><td>1-1</td>
 * <td>Atributo Algorithm de SignedInfo: http://www.w3.org/2000/09/xmldsig#rsa-sha1</td></tr>
 * <tr><td>XS07</td><td>Reference</td><td>G</td><td>XS02</td><td>-</td><td>1-1</td>
 * <td>Grupo do Método de Reference</td></tr>
 * <tr><td>XS08</td><td>URI</td><td>A</td><td>XS07</td><td>C</td><td>1-1</td>
 * <td>Atributo URI da tag Reference</td></tr>
 * <tr><td>XS10</td><td>Transforms</td><td>G</td><td>XS07</td><td>-</td><td>1-1</td>
 * <td>Grupo do algorithm de Transform</td></tr>
 * <tr><td>XS11</td><td>unique_Transf_Alg</td><td>RC</td><td>XS10</td><td>-</td><td>1-1</td>
 * <td>Regra para o atributo Algorithm do Transform ser ï¿½nico.</td></tr>
 * <tr><td>XS12</td><td>Transform</td><td>G</td><td>XS10</td><td>-</td><td>2-2</td>
 * <td>Grupo de Transform</td></tr>
 * <tr><td>XS13</td><td>Algorithm</td><td>A</td><td>XS12</td><td>C</td><td>1-1</td>
 * <td>Atributos vï¿½lidos Algorithm do Transform: http://www.w3.org/TR/2001/REC-xml-c14n-20010315
 *     http://www.w3.org/2000/09/xmldsig#envelopedsignature</td></tr>
 * <tr><td>XS14</td><td>XPath</td><td>E</td><td>XS12</td><td>C</td><td>0-N</td>
 * <td>XPath</td></tr>
 * <tr><td>XS15</td><td>DigestMethod</td><td>G</td><td>XS07</td><td>-</td><td>1-1</td>
 * <td>Grupo do Método de DigestMethod</td></tr>
 * <tr><td>XS16</td><td>Algorithm</td><td>A</td><td>XS15</td><td>C</td><td>1-1</td>
 * <td>Atributo Algorithm de DigestMethod: http://www.w3.org/2000/09/xmldsig#sha1</td></tr>
 * <tr><td>XS17</td><td>DigestValue</td><td>E</td><td>XS07</td><td>C</td><td>1</td>
 * <td>Digest Value (Hash SHA-1 ï¿½ Base64)</td></tr>
 * <tr><td>XS18</td><td>SignatureValue</td><td>G</td><td>XS01</td><td>-</td><td>1-1</td>
 * <td>Grupo do Signature Value</td></tr>
 * <tr><td>XS19</td><td>KeyInfo</td><td>G</td><td>XS01</td><td>-</td><td>1-1</td>
 * <td>Grupo do KeyInfo</td></tr>
 * <tr><td>XS20</td><td>X509Data</td><td>G</td><td>XS19</td><td>-</td><td>1-1</td>
 * <td>Grupo X509</td></tr>
 * <tr><td>XS21</td><td>X509Certificate</td><td>E</td><td>XS20</td><td>C</td><td>1-1</td>
 * <td>Certificado Digital x509 em Base64</td></tr>
 * </table>
 * 
 * @author Mauricio Fernandes de Castro
 */
public class DOMNFeSignatureBuilder implements SignatureBuilder<Element>, InitializingBean {

	public static final String CANONICALIZATION_METHOD = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
	public static final String C14N_TRANSFORMATION_METHOD = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
	
	private List<Transform> transformList;
	private DigestMethod digestMethod;

	/**
	 * Construtor vazio.
	 */
	public DOMNFeSignatureBuilder() {
	}
	
	/**
	 * O método <i>digest</i>.
	 */
	public DigestMethod getDigestMethod() {
		return digestMethod;
	}
	public void setDigestMethod(DigestMethod digestMethod) {
		this.digestMethod = digestMethod;
	}
	
	/**
	 * Implementa <code>InitializingBean</code> para garantir que haja <code>signatureFactory</code>, <code>transformList</code>
	 * e <code>digestMethod</code> disponíveis para assinatura. 
	 */
	public void afterPropertiesSet() throws Exception {
		if (signatureFactory==null) {
//			signatureFactory = newSignatureFactory();
		}
		if (transformList==null) {
			transformList = newTransformList();
		}
		if (logger.isDebugEnabled()) {
			StringBuilder debugMsg = new StringBuilder("Lista de transformações inclui algorítimos: ");
			for (Transform transform: transformList) {
				debugMsg.append(transform.getAlgorithm()).append(" ");
			}
			logger.debug(debugMsg.toString());
		}
		if (digestMethod==null) {
			digestMethod = signatureFactory.newDigestMethod(DigestMethod.SHA1, null);
		}
		logger.debug("Algorítimo do método digest é {}", digestMethod.getAlgorithm());
	}
		
//	/**
//	 * Cria nova <code>XMLSignatureFactory</code> usando os padrões deste bean.
//     *
//	 * @throws InstantiationException
//	 * @throws IllegalAccessException
//	 * @throws ClassNotFoundException
//	 */
//	protected XMLSignatureFactory newSignatureFactory() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
//		String providerName = System.getProperty(PROVIDER_NAME, PROVIDER_CLASS_NAME);
//		return XMLSignatureFactory
//		.getInstance("DOM", (Provider) Class.forName(providerName)
//				.newInstance());
//	}
//	
	/**
	 * Cria nova lista de <code>Transform</code> usando os padrões deste bean.
	 * 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchAlgorithmException 
	 */
	protected List<Transform> newTransformList() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		List<Transform> transformList = new ArrayList<Transform>();
		TransformParameterSpec tps = null;
		transformList.add(signatureFactory.newTransform(Transform.ENVELOPED, tps));
		transformList.add(signatureFactory.newTransform(C14N_TRANSFORMATION_METHOD, tps));
		return transformList;
	}
	
	/**
	 * Prepara e assina um elemento.
	 * 
	 * @param elementToSign
	 * @param parentElement
	 * @param certificate
	 * @param privateKey
	 */
	public void build(Element elementToSign, Element parentElement, Certificate certificate, PrivateKey privateKey) {
		try {
			List<Reference> refList = newReferenceList(elementToSign);
			SignedInfo signedInfo = newSignedInfo(refList);
			KeyInfo keyInfo = keyInfoBuilder.newKeyInfo(certificate);
			DOMSignContext dsc = new DOMSignContext(privateKey, parentElement);
			XMLSignature signature = signatureFactory.newXMLSignature(signedInfo, keyInfo);
			signature.sign(dsc);
			logger.debug(" Primeiro digest value encontrado é {}.", ((DOMReference) signature.getSignedInfo().getReferences().get(0)).getDigestValue());
			logger.debug(" Primeiro digest value encontrado é {}.", ((DOMReference) signature.getSignedInfo().getReferences().get(0)).getHere());
			InputStreamReader isr = 
			    new InputStreamReader(signature.getSignedInfo().getCanonicalizedData());
			char[] cbuf = new char[1024];
			while (isr.read(cbuf, 0, 1024) != -1) {
			    System.out.print(cbuf);
			}
			System.out.println();
			logger.debug("Elemento <{}> assinado e inserido em <{}>.", elementToSign.getTagName(), parentElement.getTagName());
		} catch (Exception e) {
			throw new IllegalArgumentException("Impossível construir assinatura, ", e);
		}
	}
		
	/**
	 * Cria a lista de referências.
	 * 
	 * @param elementToSign
	 */
	protected List<Reference> newReferenceList(Element elementToSign) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		String URI = new StringBuilder("#").append(elementToSign.getAttribute("Id")).toString();
		Reference ref = newReference(URI);
		ArrayList<Reference> refList = new ArrayList<Reference>();
		refList.add(ref);
		return refList;
	}
	
	/**
	 * Cria uma referência.
	 * 
	 * @param URI
	 */
	protected Reference newReference(String URI) {
		Reference reference = signatureFactory.newReference(URI, digestMethod, transformList, null, null);
		logger.debug("Elemento <Reference URI='{}'.../>[{}] preparado para assinatura.", URI, reference.getDereferencedData());
//		logger.debug("Elemento <Reference URI='{}'.../>[{}] preparado para assinatura.", URI, reference.getCalculatedDigestValue());
		return reference;
	}

	/**
	 * Cria elemento a assinar.
	 * 
	 * @param refList
	 */
	protected SignedInfo newSignedInfo(List<Reference> refList) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
		CanonicalizationMethod canonicalMethod = signatureFactory
		    .newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null);
		SignatureMethod sm = signatureFactory.newSignatureMethod(SignatureMethod.RSA_SHA1, null);
		SignedInfo signedInfo = signatureFactory.newSignedInfo(canonicalMethod, sm, refList);
		logger.debug("Elemento <SignedInfo .../> preparado para assinatura.");
		return signedInfo;
	}
	
	// collabs
	
	private XMLSignatureFactory signatureFactory;
	private KeyInfoBuilder keyInfoBuilder;

	@Resource
	protected void setXMLSignatureFactory(XMLSignatureFactory signatureFactory) {
		this.signatureFactory = signatureFactory;
	}
	
	@Resource
	public void setKeyInfoBuilder(KeyInfoBuilder keyInfoBuilder) {
		this.keyInfoBuilder = keyInfoBuilder;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(DOMNFeSignatureBuilder.class);
	
}
