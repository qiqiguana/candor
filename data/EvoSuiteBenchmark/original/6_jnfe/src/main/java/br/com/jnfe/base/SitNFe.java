package br.com.jnfe.base;

/**
 * Situação da NFe.
 * 
 * @author mauriciofernandesdecastro
 */
public enum SitNFe {
	
	/**
	 * Antes de enviar, esta é a situação.
	 */
	NAO_ENVIADA('N', false, true),
	/**
	 * Ainda não há resposta.
	 */
	PENDENTE('P', false, true),
	/**
	 * Autorizada, DANFE não emitida.
	 */
	AUTORIZADA('A', true, false),
	/**
	 * Autorizada, DANFE emitida.
	 */
	COM_DANFE('D', true, false),
	/**
	 * Rejeitada.
	 */
	REJEITADA('R', false, false),
	/**
	 * Denegada.
	 */
	DENEGADA('D', false, false), 
	/**
	 * Emitida e cancelada.
	 */
	CANCELADA('X', false, false),
	/**
	 * Outras situações não atribuídas a uma NFe.
	 */
	INDEFINIDA('I', false, false);
	
	private SitNFe(char value, boolean usar, boolean reenviar) {
		this.value  = value;
		this.usar = usar;
		this.reenviar = reenviar;
	}
	
	private char value;
	private boolean usar;
	private boolean reenviar;
	
	public char getValue() {
		return value;
	}
	
	public boolean isUsar() {
		return usar;
	}
	
	public boolean isReenviar() {
		return reenviar;
	}

}
