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

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

/**
 * Uma inst�ncia de <code>SecurityHandler</code> que utiliza propriedades
 * pr�-configuradas.
 * 
 * @author Mauricio Fernandes de Castro
 */
public class SimpleSecurityHandlerBean extends AbstractSecurityHandlerBean {

	/**
	 * Construtor vazio.
	 */
	public SimpleSecurityHandlerBean() {
	}

	/**
	 * Construtor.
	 * 
	 * @param alias
	 * @param password
	 */
	public SimpleSecurityHandlerBean(String alias, String password) {
		this.alias = alias;
		this.password = password.toCharArray();
	}

	public void handle(Element parentElement, Element elementToSign, SecurityCallBack action) {
		PrivateKey privateKey = null;
		Certificate certificate = null;
		try {
			if (getKeyStore() != null) {
				logger.debug("Recuperando credenciais de armaz�m tipo {}.", getKeyStore().getType());
				if (getKeyStore().containsAlias(alias)) {
					privateKey = (PrivateKey) getKeyStore().getKey(alias, password);
					logger.debug("Chave particular recuperada no formato: {}.", privateKey.getFormat());
					certificate = getKeyStore().getCertificate(alias);
					logger.debug("Certificado recuperado: {}.", ((X509Certificate) certificate).getSubjectDN());
				} else {
					throw new IllegalArgumentException(
							"Armaz�m configurado pelo bean 'keyStore' n�o cont�m o certificado '"
									+ alias
									+ "'. "
									+ "Tente outro 'alias' ou reconfigure jnfe-core-context.xml para evitar a cria��o do bean 'keyStore', "
									+ "for�ando o sistema a usar o armaz�m principal.");
				}
			} else {
				logger.debug("Recuperando credenciais da primeira chave do armaz�m principal em {}.",
								System.getProperty("javax.net.ssl.keyStore"));
				KeyStore ksKeys = KeyStore.getInstance(System.getProperty("javax.net.ssl.keyStoreType"));
				ksKeys.load(new FileInputStream(System
						.getProperty("javax.net.ssl.keyStore")), System
						.getProperty("javax.net.ssl.keyStorePassword")
						.toCharArray());
				Enumeration<String> aliases = ksKeys.aliases();
				if (aliases.hasMoreElements()) {
					String transportAlias = aliases.nextElement();
					certificate = ksKeys.getCertificate(transportAlias);
					logger.debug("Certificado: {}.", ((X509Certificate) certificate).getSubjectDN());
					privateKey = (PrivateKey) ksKeys.getKey(transportAlias, password);
				} else {
					throw new IllegalArgumentException("Armaz�m principal n�o cont�m um certificado v�lido.");
				}
			}
		} catch (Exception e) {
			logger.error("Imposs�vel recuperar credenciais", e);
		}
		action.doInSecurityContext(parentElement, elementToSign, certificate, privateKey);
	}

	/**
	 * Assegura valores padr�o para apelido e senha.
	 */
	public void afterPropertiesSet() throws Exception {
		if (alias == null) {
			alias = "teste";
			logger.warn("Utilzando apelido 'teste' para localizar chave particular no armaz�m de clientes");
		}
		if (password == null) {
			password = "teste".toCharArray();
			logger.warn("Utilzando senha 'teste' para abrir chave particular no armaz�m de clientes");
		}
	}

	private String alias;
	private char[] password;

	/**
	 * Apelido dado � chave particular no armaz�m de clientes.
	 * 
	 * @param alias
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * Senha da chave particular no armaz�m de clientes.
	 * 
	 * @param alias
	 */
	public void setPassword(char[] password) {
		this.password = password;
	}

	protected static final Logger logger = LoggerFactory.getLogger(SimpleSecurityHandlerBean.class);

}
