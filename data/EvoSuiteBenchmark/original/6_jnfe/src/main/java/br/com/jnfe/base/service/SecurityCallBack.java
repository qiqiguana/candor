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

import org.w3c.dom.Element;


/**
 * Utilizado para assinatura, colabora com os manipuladores de seguran�a 
 * para evitar a exposi��o direta do certificado e da chave particular.
 * 
 * <p>
 * No caso de uso mais frequente, esta interface � implenentada por uma
 * classe an�nima passada como par�metro pelo manipulador, como no 
 * exemplo:</p>
 * 
 * <pre>
 * //... c�digo do chamador 
 * 
 * 		securityHandler.handle(elementToSign, <b>new SecurityCallBack()</b> {
 * 			public void doInSecurityContext(
 * 					Element sourceElement, 
 * 					Element elementToSign, 
 * 					Certificate certificate, 
 * 					PrivateKey privateKey) {
 * 				// c�digo que combina propriedades expostas pelo
 * 				// chamador com o certificado ou chave particular
 * 			}
 * 		});
 * </pre>
 * 
 * @author Mauricio Fernandes de Castro
 * @see {@link SecurityHandler}
 */
public interface SecurityCallBack {

	/**
	 * Repassa as credenciais recebidas do manipulador ao chamador,
	 * junto com o elemento a ser assinado.
	 * 
	 * @param parentElement
	 * @param elementToSign
	 * @param certificate
	 * @param privateKey
	 */
	public void doInSecurityContext(Element parentElement, Element elementToSign, Certificate certificate, PrivateKey privateKey);
	
}
