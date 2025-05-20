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

import java.io.FileNotFoundException;
import java.security.KeyStore;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.X509Certificate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

/**
 * Uma inst�ncia de <code>SecurityHandler</code> que utiliza propriedades
 * pr�-configuradas.
 * 
 * <p>
 * A responsablidade deste bean � extrair o certificado e a chave particular de um 
 * armaz�m tipo pkcs12 definido atrav�s de {@link #getLocation()} e repass�-los ao callback
 * recebido na chamada do m�todo {@link #handle(Element, Element, SecurityCallBack)}.
 * </p>
 * 
 * @author Mauricio Fernandes de Castro
 */
public class Pkcs12SecurityHandlerBean extends AbstractSecurityHandlerBean {

	/**
	 * Construtor vazio.
	 */
	public Pkcs12SecurityHandlerBean() {
	}

	/**
	 * Construtor.
	 * 
	 * @param alias
	 * @param password
	 */
	public Pkcs12SecurityHandlerBean(String alias, String password) {
		this.alias = alias;
		this.password = password.toCharArray();
	}

	public void handle(Element sourceElement, Element elementToSign, SecurityCallBack action) {
		try {
			KeyStore.PrivateKeyEntry pkEntry = unlockPkEntry();
			PrivateKey privateKey = pkEntry.getPrivateKey(); //(PrivateKey) getKeyStore().getKey(alias, password);
			java.security.cert.Certificate certificate = pkEntry.getCertificate();
			action.doInSecurityContext(sourceElement, elementToSign, certificate, privateKey);
		} catch (Exception e) {
			throw new IllegalArgumentException("Imposs�vel recuperar credenciais", e);
		}
	}
	
	/**
	 * Conveniente para recuperar certificado.
	 * 
	 * @throws KeyStoreException 
	 * @throws UnrecoverableEntryException 
	 * @throws NoSuchAlgorithmException 
	 */
	public X509Certificate getCertificate() throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException {
		return (X509Certificate) unlockPkEntry().getCertificate();
	}
	
	/**
	 * Conveniente para desbloquear o chave particular.
	 * 
	 * @throws KeyStoreException 
	 * @throws UnrecoverableEntryException 
	 * @throws NoSuchAlgorithmException 
	 */
	private KeyStore.PrivateKeyEntry unlockPkEntry() throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException {
		KeyStore keystore = getKeyStore();
		logger.debug("Pronto para recuperar a entrada {} no aramz�m '{}'.", keystore, alias);
		KeyStore.PrivateKeyEntry pkEntry = (PrivateKeyEntry) keystore.getEntry(alias, new KeyStore.PasswordProtection(password));
		logger.debug("Recuperada a entrada usando o alias: '{}'.", alias);
		return pkEntry;
	}
	
	/**
	 * Assegura valores padr�o para apelido e senha antes de abrir o armaz�m.
	 */
	public void afterPropertiesSet() throws Exception {
		if (alias != null && !alias.isEmpty()) {
			if (password != null && password.length>0) {
				loadKeyStore();
			}
			else {
				logger.warn("Senha n�o inicializada, armaz�m n�o ser� aberto agora.");
			}
		}
		else {
			logger.warn("Alias (apelido) n�o inicializado, armaz�m n�o ser� aberto agora.");
		}
	}
	
	/**
	 * Abre o armazem usando valores definidos pelas propriedades deste bean.
	 * 
	 */
	public void loadKeyStore() {
		try {
			KeyStore ksKeys = KeyStore.getInstance("PKCS12");
			ksKeys.load(getLocation().getInputStream(), password);
			super.setKeyStore(ksKeys);
			logger.info("Aberto armaz�m {} localizado em {}.", ksKeys, getLocation());
			if (!getKeyStore().isKeyEntry(alias)) {
				logger.warn("N�o existe chave particular para o alias '{}' em {}.", alias, getLocation());
				throw new RuntimeException("N�o existe chave particular no armaz�m designado.");
			}
		} catch (FileNotFoundException e) {
			logger.warn("Armaz�m n�o localizado em {}.", getLocation());
		} catch (Exception e) {
			logger.warn("Erro ao abrir armaz�m localizado em {}.", getLocation());
			throw new RuntimeException("Erro ao abrir armaz�m, ", e);
		}
		
	}
	
	/**
	 * Lan�a um erro na tentativa de estabelecer armaz�m sem que seja atrav�s das propriedades deste bean.
	 */
	@Override
	public void setKeyStore(KeyStore keyStore) {
		throw new IllegalArgumentException("Utilize alias, password e location para estabelecer um armaz�m.");
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

	protected static final Logger logger = LoggerFactory.getLogger(Pkcs12SecurityHandlerBean.class);

}
