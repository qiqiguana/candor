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

package br.com.jnfe.base;

import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import br.com.jnfe.base.util.SecurityUtils;

/**
 * Atualiza as propriedades do sistema para establecer a "keyStore"
 * usada para o transmissão das mensagens dos web services NFe.
 * 
 * @author Mauricio Fernandes de Castro
 */
public class TransportKeyStoreBean implements InitializingBean {
	
	private final static String DEFAULT_KEYSTORE_URI  = "file:#{ systemProperties['user.home'] }/jnfe.pfx";
	private final static String DEFAULT_KEYSTORE_TYPE = "pkcs12";
	
	private String keyStoreUri;
	private String keyStoreType;
	private String keyStorePassword;
	private String trustStoreType;
	private String trustStore;
	private String trustStorePassword;

	/**
	 * A URI para a keystore usada para autenticar o transporte.
	 * 
	 * @param keyStoreUri
	 */
	public void setKeyStoreUri(String keyStoreUri) {
		this.keyStoreUri = keyStoreUri;
	}

	/**
	 * O tipo de keystore empregado para o transporte.
	 * 
	 * @param keyStoreType
	 */
	public void setKeyStoreType(String keyStoreType) {
		this.keyStoreType = keyStoreType;
	}

	/**
	 * A senha para acesso ao keystore empregado para o transporte.
	 * 
	 * @param keyStorePassword
	 */
	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}
	
	/**
	 * Localização do aramzém seguro.
	 * 
	 * @param trustStore
	 */
	public void setTrustStore(String trustStore) {
		this.trustStore = trustStore;
	}
	
	/**
	 * Tipo do armazém do armazém seguro (cacerts).
	 * 
	 * @param trustStoreType
	 */
	public void setTrustStoreType(String trustStoreType) {
		this.trustStoreType = trustStoreType;
	}
	
	/**
	 * Senah do armazém seguro (cacerts).
	 * 
	 * @param trustStorePassword
	 */
	public void setTrustStorePassword(String trustStorePassword) {
		this.trustStorePassword = trustStorePassword;
	}

	public void afterPropertiesSet() throws Exception {
		if (keyStoreType==null || keyStoreType.length()==0) {
			logger.warn("Using default keyStoreType.");
			setKeyStoreType(DEFAULT_KEYSTORE_TYPE);
		}
		System.setProperty("javax.net.ssl.keyStoreType", keyStoreType);
		if (keyStoreUri==null || keyStoreUri.length()==0) {
			logger.warn("Using default keyStoreUri.");
			setKeyStoreUri(DEFAULT_KEYSTORE_URI);
		}
		System.setProperty("javax.net.ssl.keyStore", keyStoreUri);
		// TODO proteger senha da keystore de transporte com MD5 ou SHA
		if (keyStorePassword!=null && keyStorePassword.length()>0) {
			System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
		}
		if (trustStore!=null) {
			System.setProperty("javax.net.ssl.trustStore", trustStore);
		}
		if (trustStoreType!=null) {
			System.setProperty("javax.net.ssl.trustStoreType", trustStoreType);
		}
		if (trustStorePassword!=null) {
			System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
		}
		logger.info("Propriedades de transporte: {}", toString());
	}
	
    /**
     * toString
     * @return String
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
        buffer.append("javax.net.ssl.keyStore").append("='").append(System.getProperty("javax.net.ssl.keyStore")).append("' ");
        buffer.append("javax.net.ssl.keyStoreType").append("='").append(System.getProperty("javax.net.ssl.keyStoreType")).append("' ");
        buffer.append("javax.net.ssl.trustStoreType").append("='").append(System.getProperty("javax.net.ssl.trustStoreType")).append("' ");
        buffer.append("javax.net.ssl.trustStore").append("='").append(System.getProperty("javax.net.ssl.trustStore")).append("' ");
        buffer.append("]");
      
        return buffer.toString();
    }

	
	/**
	 * Abre o  armazém seguro (cacerts).
	 * 
	 * @throws Exception 
	 */
	public KeyStore openTransportStore() throws Exception {
    	return SecurityUtils.openStore(keyStoreType, keyStoreUri, keyStorePassword.toCharArray());
	}
	
	/**
	 * Abre o gerenciador de chaves do armazém de transporte.
	 * 
	 * @throws Exception 
	 */
	public KeyManagerFactory openTransportKeyManagerFactory() throws Exception {
    	KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
    	kmf.init(openTransportStore(), keyStorePassword.toCharArray());
    	return kmf;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(TransportKeyStoreBean.class);

}
