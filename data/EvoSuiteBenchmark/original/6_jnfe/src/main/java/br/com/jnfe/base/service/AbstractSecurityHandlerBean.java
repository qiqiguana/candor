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

import java.security.KeyStore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;


/**
 * Base para implementações de <code>SecurityHandler</code> que 
 * colaboram com uma <code>KeyStore</code>.
 * 
 * @author Mauricio Fernandes de Castro
 */
public abstract class AbstractSecurityHandlerBean implements SecurityHandler, InitializingBean {
	
	private KeyStore keyStore;
	private Resource location;
	
	/**
	 * O armazém de chaves (keystore).
	 */
	protected KeyStore getKeyStore() {
		return keyStore;
	}
	
	public void setKeyStore(KeyStore keyStore) {
		this.keyStore = keyStore;
	}
	
	/**
	 * A localização do armazém, requerida se o armazém não é fornecido.
	 */
	public Resource getLocation() {
		return location;
	}
	public void setLocation(Resource location) {
		this.location = location;
	}
	
	protected static final Logger logger = LoggerFactory.getLogger(AbstractSecurityHandlerBean.class);
	
}
