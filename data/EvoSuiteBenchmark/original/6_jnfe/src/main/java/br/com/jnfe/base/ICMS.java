package br.com.jnfe.base;

import java.math.BigDecimal;


/**
 * Interface para ICMS.
 * 
 * @author mauriciofernandesdecastro
 */
public interface ICMS {

	/**
	 * C�digo da situa��o tribut�ria.
	 */
	public C_ICMS getC_ICMS();

	/**
	 * Modalidade de determina��o da BC.
	 */
	public char getModBC();

	/**
	 * Al�quota do ICMS.
	 */
	public BigDecimal getPICMS();

	/**
	 * Base de c�lculo do ICMS.
	 */
	public BigDecimal getVBc();

	/**
	 * Base de c�lculo do ICMS.
	 * 
	 * @param vBC
	 */
	public void setVBc(BigDecimal vBC);

	/**
	 * Valor do ICMS.
	 */
	public BigDecimal getVICMS();

	/**
	 * Base de c�lculo do ICMS.
	 * 
	 * @param vICMS
	 */
	public void setVICMS(BigDecimal vICMS);

	/**
	 * Percentual de redu��o da base de c�lculo.
	 */
	public BigDecimal getPRedBC();
	
	/**
	 * Al�quota aplic�vel ao c�lculo do cr�dito.
	 */
	public BigDecimal getPCredSN();

	/**
	 * Valor cr�dito do ICMS que pode ser aproveitado nos termos do
	 * art. 23 da LC 123 (SN).
	 */
	public BigDecimal getVCredICMSSN();

}