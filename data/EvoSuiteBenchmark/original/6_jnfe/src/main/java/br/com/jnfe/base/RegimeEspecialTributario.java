package br.com.jnfe.base;

/**
 * Define o regime especial tributário conforme ABRASF.
 * 
 * @author mauriciofernandesdecastro
 */
public enum RegimeEspecialTributario {
	
	NA('0'),
	MICROEMPRESA_MUNICIPAL('1'),
	ESTIMATIVA('2'),
	SOCIEDADE_PROFISSIONAIS('3'),
	COOPERATIVA('4'),
	MICROEMPRESARIO_INDIVIDUAL('5'),
	MICROEMPRESA_PEQUENO_PORTE('6');
	
	private RegimeEspecialTributario(char value) {
		this.value = value;
	}
	
	private char value;
	
	public char getValue() {
		return value;
	}
}
