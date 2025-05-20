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
	 * Contribuinte não obrigado à inscrição no cadastro do ICMS.
	 */
	ISENTO('I'),
	/**
	 * Não contribuinte do ICMS.
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
