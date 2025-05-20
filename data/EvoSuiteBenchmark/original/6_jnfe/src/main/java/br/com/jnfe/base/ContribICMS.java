package br.com.jnfe.base;

/**
 * Tipo de contribuinte do ICMS.
 * 
 * @author mauriciofernandesdecastro
 */
public enum ContribICMS {
	
	/**
	 * Contribuinte do ICMS.
	 */
	ICMS('C'),
	/**
	 * Contribuinte n�o obrigado � inscri��o no cadastro do ICMS.
	 */
	ISENTO('I'),
	/**
	 * N�o contribuinte do ICMS.
	 */
	NAO_CONTRIBUINTE('N');
	
	private ContribICMS(char value) {
		this.value = value;
	}

	private char value;
	
	public char getValue() {
		return value;
	}
}
