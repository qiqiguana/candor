package br.com.jnfe.base.adapter;

import javax.xml.transform.Source;

/**
 * Estratégia para adaptar a requisição.
 * 
 * @author mauriciofernandesdecastro
 */
public interface RequestAdapter {
	
	/**
	 * Cria cabeçalho.
	 */
	public String newCabec();

	/**
	 * Cria requisição.
	 */
	public Source newRequest(String servicoRemoto, String qualificadorAbreviado, String content);
	
}
