package br.com.jnfe.base;

/**
 * Tipo de detalhe.
 * 
 * @author mauriciofernandesdecastro
 */
public enum TpDet {
	
	/**
	 * Sujeito a ICMS, IPI e/ou II.
	 */
	PRODUTO('P'),
	/**
	 * Sujeito a ISSQN
	 */
	SERVICO('S', true);
	
	private TpDet(char value) {
		this(value, false);
	}
	
	private TpDet(char value, boolean servico) {
		this.value = value;
		this.servico = servico;
	}
	
	private char value;
	private boolean servico;
	
	/**
	 * Valor atribu�do ao tipo.
	 */
	public char getValue() {
		return value;
	}
	
	/**
	 * Verdadeiro se � servi�o.
	 */
	public boolean isServico() {
		return servico;
	}

}
