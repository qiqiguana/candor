package br.com.jnfe.base;

import java.math.BigDecimal;

/**
 * Interface para ICMS ST.
 * 
 * @author mauriciofernandesdecastro
 */
public interface ICMSST extends ICMS {

	/**
	 * Modalidade de determina��o da BC ST.
	 */
	public char getModBCST();

	/**
	 * Percentual da margem de valor adicionado do ICMS ST.
	 */
	public BigDecimal getPMVAST();

	/**
	 * Percentual de redu��o da base de c�lculo ST.
	 */
	public BigDecimal getPRedBCST();

	/**
	 * Valor da base de c�lculo ST.
	 */
	public BigDecimal getVBCST();

	/**
	 * Al�quota do imposto do ICMS ST.
	 */
	public BigDecimal getPICMSST();

	/**
	 * Valor do ICMS ST.
	 */
	public BigDecimal getVICMSST();

}