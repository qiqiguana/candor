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
	 * Valor atribuído ao tipo.
	 */
	public char getValue() {
		return value;
	}
	
	/**
	 * Verdadeiro se é serviço.
	 */
	public boolean isServico() {
		return servico;
	}

}
