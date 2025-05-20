package br.com.jnfe.base;


/**
 * Interface gen�rica para NFe.
 * 
 * <p>
 * M�todos da interface de servi�o <code>NFeMgr</code> referem-se a esta interface
 * ao inv�s da classe concreta, facilitando a adapta��o para uso com outras bibliotecas.
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
	 * N�mero de controle da NFe controlado por este sistema.
	 */
	public long getCNF();
	
	/**
	 * N�mero do documento fiscal NFe (impresso na DANFE).
	 */
	public long getNNF();
	
	/**
	 * Chave da NFe.
	 */
	public String getChNFe();

	/**
	 * Email do destinat�rio.
	 * 
	 * <p>
	 * O campo pode ser utilizado para informar o e-mail de recep��o da NF-e indicada pelo destint�rio.
	 * </p>
	 */
	public String getEmail();
	
	/**
	 * Situa��o da NFe.
	 */
	public char getSitNFe();
	
	/**
	 * Atualiza situa��o da NFe.
	 * 
	 * @param sitNFe
	 */
	public void setSitNFe(char sitNFe);
	
	/**
	 * Verdadeiro se o c�digo num�rico (n�mero aleat�rio gerado pelo emitente para cada NF-e) n�o � zero.
	 */
	public boolean isCodigoNumericoValido();

	/**
	 * Verdadeiro se o n�mero do documento fiscal n�o � zero.
	 */
	public boolean isNumeroDocumentoFiscalValido();
	
}
