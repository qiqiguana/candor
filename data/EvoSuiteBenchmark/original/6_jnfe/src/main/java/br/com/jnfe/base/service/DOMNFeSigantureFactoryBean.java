package br.com.jnfe.base.service;

import java.security.Provider;

import javax.xml.crypto.dsig.XMLSignatureFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Cria instâncias de <code>XMLSignatureFactory</code>.
 * 
 * @author mauriciofernandesdecastro
 */
public class DOMNFeSigantureFactoryBean implements InitializingBean, FactoryBean<XMLSignatureFactory> {

	public static final String DEFAULT_PROVIDER_CLASS_NAME = "org.jcp.xml.dsig.internal.dom.XMLDSigRI";
	public static final String DEFAULT_PROVIDER_NAME = "jsr105Provider";
	
	private String providerClassName = "";
	private String providerName = "";
    
	/**
	 * O nome do provedor JCA.
	 */
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	
	/**
	 * A classe do provedor JCA.
	 */
	public String getProviderClassName() {
		return providerClassName;
	}
	public void setProviderClassName(String providerClassName) {
		this.providerClassName = providerClassName;
	}
	
	
	
    /**
     * Prepara a criação de instâncias de <code>XMLSignatureFactory</code>.
     */
	public void afterPropertiesSet() throws Exception {
		if (getProviderName().isEmpty()) {
			setProviderName(DEFAULT_PROVIDER_NAME);
			logger.warn("PRovider name não definido, usando {}", getProviderName());
		}
		if (getProviderClassName().isEmpty()) {
			setProviderClassName(DEFAULT_PROVIDER_CLASS_NAME);
			logger.warn("PRovider name não definido, usando {}", getProviderClassName());
		}
		providerName = System.getProperty(getProviderName(), getProviderClassName());
	}
	
	public XMLSignatureFactory getObject() throws Exception {
		logger.debug("Usando o provider com nome {}.", providerName);
		Provider provider = (Provider) Class.forName(providerName).newInstance();
		XMLSignatureFactory xmlSignatureFactory = XMLSignatureFactory.getInstance("DOM", provider);
		logger.debug("A instância de XMLSignatureFactory é {}.", xmlSignatureFactory);
		return xmlSignatureFactory;
	}
	
	public Class<?> getObjectType() {
		return XMLSignatureFactory.class;
	}
	
	public boolean isSingleton() {
		return false;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(DOMNFeSigantureFactoryBean.class);

}
