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

import org.w3c.dom.Element;


/**
 * Implementa��es desta interface devem invocar o m�todo 
 * {@link SecurityCallBack#doInSecurityContext(Element, java.security.cert.Certificate, java.security.PrivateKey)} 
 * atrav�s da inst�ncia de <code>SecurityCallBack</code> recebida em 
 * {@link #handle(Element, Element, SecurityCallBack)} para passar 
 * dados protegidos.
 * 
 * @author Mauricio Fernandes de Castro
 */
public interface SecurityHandler {
	
	/**
	 * Passa o elemento a assinar e permite a a��o do 
	 * <code>SecurityCallBack</code>.
	 * 
	 * @param parentElement
	 * @param elementToSign
	 * @param action
	 */
	public void handle(Element parentElement, Element elementToSign, SecurityCallBack action);

}
