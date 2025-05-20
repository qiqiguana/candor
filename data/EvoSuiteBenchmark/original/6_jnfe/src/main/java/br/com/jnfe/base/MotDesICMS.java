package br.com.jnfe.base;

/**
 * Motivo da desoneração do ICMS.
 * 
 * @author mauriciofernandesdecastro
 */
public enum MotDesICMS {
	
	/**
	 * Não aplicável.
	 */
	NA(' '),
	/**
	 * Táxi.
	 */
	TAXI('1'),
	/**
	 * Deficiente físico.
	 */
	DEFICIENTE_FISICO('2'),
	/**
	 * Produtor agropecuário.
	 */
	PRODUTOR_AGROPECUARIO('3'),
	/**
	 * Frotista ou locadora.
	 */
	FROTISTA_OU_LOCADORA('4'),
	/**
	 * Diplomático ou consular.
	 */
	DIPLOMATICO('5'),
	/**
	 * Utilitários e motocicletas da Amazônia ocidental e áreas de livre comércio.
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
	 * Valor atribuído ao motivo.
	 */
	public char getValue() {
		return value;
	}

}
