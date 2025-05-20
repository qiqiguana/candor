package br.com.jnfe.base.adapter;

import javax.xml.transform.Source;

/**
 * Estrat�gia para adaptar a requisi��o.
 * 
 * @author mauriciofernandesdecastro
 */
public interface RequestAdapter {
	
	/**
	 * Cria cabe�alho.
	 */
	public String newCabec();

	/**
	 * Cria requisi��o.
	 */
	public Source newRequest(String servicoRemoto, String qualificadorAbreviado, String content);
	
}
