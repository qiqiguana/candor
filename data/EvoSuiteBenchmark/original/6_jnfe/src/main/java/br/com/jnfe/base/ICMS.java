package br.com.jnfe.base;

import java.math.BigDecimal;


/**
 * Interface para ICMS.
 * 
 * @author mauriciofernandesdecastro
 */
public interface ICMS {

	/**
	 * Código da situação tributária.
	 */
	public C_ICMS getC_ICMS();

	/**
	 * Modalidade de determinação da BC.
	 */
	public char getModBC();

	/**
	 * Alíquota do ICMS.
	 */
	public BigDecimal getPICMS();

	/**
	 * Base de cálculo do ICMS.
	 */
	public BigDecimal getVBc();

	/**
	 * Base de cálculo do ICMS.
	 * 
	 * @param vBC
	 */
	public void setVBc(BigDecimal vBC);

	/**
	 * Valor do ICMS.
	 */
	public BigDecimal getVICMS();

	/**
	 * Base de cálculo do ICMS.
	 * 
	 * @param vICMS
	 */
	public void setVICMS(BigDecimal vICMS);

	/**
	 * Percentual de redução da base de cálculo.
	 */
	public BigDecimal getPRedBC();
	
	/**
	 * Alíquota aplicável ao cálculo do crédito.
	 */
	public BigDecimal getPCredSN();

	/**
	 * Valor crédito do ICMS que pode ser aproveitado nos termos do
	 * art. 23 da LC 123 (SN).
	 */
	public BigDecimal getVCredICMSSN();

}