package br.com.jnfe.base;

/**
 * Motivo da desonera��o do ICMS.
 * 
 * @author mauriciofernandesdecastro
 */
public enum MotDesICMS {
	
	/**
	 * N�o aplic�vel.
	 */
	NA(' '),
	/**
	 * T�xi.
	 */
	TAXI('1'),
	/**
	 * Deficiente f�sico.
	 */
	DEFICIENTE_FISICO('2'),
	/**
	 * Produtor agropecu�rio.
	 */
	PRODUTOR_AGROPECUARIO('3'),
	/**
	 * Frotista ou locadora.
	 */
	FROTISTA_OU_LOCADORA('4'),
	/**
	 * Diplom�tico ou consular.
	 */
	DIPLOMATICO('5'),
	/**
	 * Utilit�rios e motocicletas da Amaz�nia ocidental e �reas de livre com�rcio.
	 */
	AREAS_DE_LIVRE_COMERCIO('6'),
	/**
	 * Suframa.
	 */
	SUFRAMA('7'),
	/**
	 * Outros
	 */
	OUTROS('9');
	
	private MotDesICMS(char value) {
		this.value = value;
	}
	
	private char value;
	
	/**
	 * Valor atribu�do ao motivo.
	 */
	public char getValue() {
		return value;
	}

}
