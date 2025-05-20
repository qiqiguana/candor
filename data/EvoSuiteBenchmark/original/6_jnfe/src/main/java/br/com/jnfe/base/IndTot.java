package br.com.jnfe.base;

/**
 * Indica se valor do Item (vProd) entra no valor total da NF-e (vProd).
 * 
 * @author mauriciofernandesdecastro
 */
public enum IndTot {
	
	/**
	 * O valor do item não soma no total da nota.
	 */
	NAO_SOMA(0),
	/**
	 * O valor do item soma no total da nota.
	 */
	SOMA(1);
	
	private IndTot(int value) {
		this.value = value;
	}
	
	private int value;
	
	/**
	 * Valor atribuído ao indicador.
	 */
	public int getValue() {
		return value;
	}

}
