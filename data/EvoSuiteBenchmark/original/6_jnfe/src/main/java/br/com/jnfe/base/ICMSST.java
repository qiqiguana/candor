package br.com.jnfe.base;

import java.math.BigDecimal;

/**
 * Interface para ICMS ST.
 * 
 * @author mauriciofernandesdecastro
 */
public interface ICMSST extends ICMS {

	/**
	 * Modalidade de determinação da BC ST.
	 */
	public char getModBCST();

	/**
	 * Percentual da margem de valor adicionado do ICMS ST.
	 */
	public BigDecimal getPMVAST();

	/**
	 * Percentual de redução da base de cálculo ST.
	 */
	public BigDecimal getPRedBCST();

	/**
	 * Valor da base de cálculo ST.
	 */
	public BigDecimal getVBCST();

	/**
	 * Alíquota do imposto do ICMS ST.
	 */
	public BigDecimal getPICMSST();

	/**
	 * Valor do ICMS ST.
	 */
	public BigDecimal getVICMSST();

}