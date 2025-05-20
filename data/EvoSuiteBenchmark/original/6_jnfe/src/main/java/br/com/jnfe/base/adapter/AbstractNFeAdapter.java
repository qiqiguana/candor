package br.com.jnfe.base.adapter;

/**
 * Base para adaptadores da NFe.
 * 
 * @author mauriciofernandesdecastro
 */
public abstract class AbstractNFeAdapter {

	/**
	 * URI do namespace para o documento.
	 */
	public String getNamespaceURI() {
		return "http://www.portalfiscal.inf.br/nfe";
	}
	
}
