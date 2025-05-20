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


/**
 * Permite que diferentes implementações escolham entre os modelos
 * de objeto (DOM, AXIOM, etc.) que sejam mais eficientes para 
 * construir uma assinatura.
 * 
 * @author Mauricio Fernandes de Castro
 */
public interface SignatureBuilder<T> {
	
	/**
	 * Prepara e assina um elemento.
	 * 
	 * @param elementToSign
	 * @param parentElement
	 * @param certificate
	 * @param privateKey
	 */
	public void build(T elementToSign, T parentElement, Certificate certificate, PrivateKey privateKey);

}
