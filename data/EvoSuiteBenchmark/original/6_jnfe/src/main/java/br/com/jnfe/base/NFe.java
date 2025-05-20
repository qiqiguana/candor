package br.com.jnfe.base;


/**
 * Interface genérica para NFe.
 * 
 * <p>
 * Métodos da interface de serviço <code>NFeMgr</code> referem-se a esta interface
 * ao invés da classe concreta, facilitando a adaptação para uso com outras bibliotecas.
 *</p> 
 *
 * @author mauriciofernandesdecastro
 */
public interface NFe {
	
	/**
	 * Conveniente para recuperar o tipo de ambiente.
	 */
	public char getTpAmb();
	
	/**
	 * Número de controle da NFe controlado por este sistema.
	 */
	public long getCNF();
	
	/**
	 * Número do documento fiscal NFe (impresso na DANFE).
	 */
	public long getNNF();
	
	/**
	 * Chave da NFe.
	 */
	public String getChNFe();

	/**
	 * Email do destinatário.
	 * 
	 * <p>
	 * O campo pode ser utilizado para informar o e-mail de recepção da NF-e indicada pelo destintário.
	 * </p>
	 */
	public String getEmail();
	
	/**
	 * Situação da NFe.
	 */
	public char getSitNFe();
	
	/**
	 * Atualiza situação da NFe.
	 * 
	 * @param sitNFe
	 */
	public void setSitNFe(char sitNFe);
	
	/**
	 * Verdadeiro se o código numérico (número aleatório gerado pelo emitente para cada NF-e) não é zero.
	 */
	public boolean isCodigoNumericoValido();

	/**
	 * Verdadeiro se o número do documento fiscal não é zero.
	 */
	public boolean isNumeroDocumentoFiscalValido();
	
}
