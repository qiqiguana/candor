package br.com.jnfe.base.service;

import java.security.cert.Certificate;

import javax.xml.crypto.dsig.keyinfo.KeyInfo;

/**
 * Abstra��o para criar informa��es de chave usadas no NFe.
 * 
 * @author mauriciofernandesdecastro
 */
public interface KeyInfoBuilder {
	
	/**
	 * Cria nova inst�ncia de {@link KeyInfo}.
	 * @param certificate
	 * @return
	 */
	public KeyInfo newKeyInfo(Certificate certificate);

}
