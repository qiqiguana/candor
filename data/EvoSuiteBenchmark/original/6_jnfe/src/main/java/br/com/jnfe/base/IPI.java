package br.com.jnfe.base;

import java.math.BigDecimal;

/**
 * Interface para IPI.
 * 
 * @author mauriciofernandesdecastro
 */
public interface IPI {

	/**
	 * Código da situação tributária.
	 */
	public CST_IPI getCST();

	/**
	 * Valor da base de cálculo do IPI.
	 */
	public BigDecimal getIPIVBC();

	/**
	 * Base de cálculo do IPI.
	 */
	public void setIPIVBC(BigDecimal vBC);

	/**
	 * Alíquota do IPI.
	 */
	public BigDecimal getPIPI();
	
	/**
	 * Valor do IPI.
	 */
	public BigDecimal getVIPI();

	/**
	 * Valor do IPI.
	 */
	public void setVIPI(BigDecimal vIPI);

	/**
	 * Código do selo de controle IPI
	 */
	public String getCSelo();

}