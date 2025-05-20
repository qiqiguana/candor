package br.com.jnfe.base.service;

import java.security.cert.Certificate;

import javax.xml.crypto.dsig.keyinfo.KeyInfo;

/**
 * Abstração para criar informações de chave usadas no NFe.
 * 
 * @author mauriciofernandesdecastro
 */
public interface KeyInfoBuilder {
	
	/**
	 * Cria nova instância de {@link KeyInfo}.
	 * @param certificate
	 * @return
	 */
	public KeyInfo newKeyInfo(Certificate certificate);

}
