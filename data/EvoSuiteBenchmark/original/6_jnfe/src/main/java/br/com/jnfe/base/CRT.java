package br.com.jnfe.base;

/**
 * Código do regime tributário.
 * 
 * @author mauriciofernandesdecastro
 */
public enum CRT {
	
	/**
	 * Simples nacional.
	 */
	SIMPLES_NACIONAL('1'),
	/**
	 * Simples nacional, excesso de sublimite de receita bruta.
	 */
	SIMPLES_NACIONAL_ESRB('2'),
	/**
	 * Regime normal.
	 */
	NORMAL('3');
	
	private CRT(char value) {
		this.value = value;
	}
	
	private char value;
	
	/**
	 * Valor atribuído ao CRT.
	 */
	public char getValue() {
		return value;
	}

}
