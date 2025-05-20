package br.com.jnfe.base;

import java.math.BigDecimal;


/**
 * Interface para PIS.
 * 
 * @author mauriciofernandesdecastro
 */
public interface PIS {

	/**
	 * Código da situação tributária.
	 */
	public CST_PIS getCST();

	/**
	 * Valor da base de cálculo do PIS.
	 */
	public BigDecimal getPISVBC();

	/**
	 * Base de cálculo do PIS.
	 * 
	 * @param vBC
	 */
	public void setPISVBC(BigDecimal vBC);

	/**
	 * Alíquota do PIS.
	 */
	public BigDecimal getPPIS();

	/**
	 * Valor do PIS.
	 */
	public BigDecimal getVPIS();

	/**
	 * Valor do PIS.
	 * 
	 * @param vPIS
	 */
	public void setVPIS(BigDecimal vPIS);

}