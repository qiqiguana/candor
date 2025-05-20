package br.com.jnfe.base;

import java.math.BigDecimal;

/**
 * Interface para COFINS.
 * 
 * @author mauriciofernandesdecastro
 */
public interface COFINS {

	/**
	 * Código da situação tributária.
	 */
	public CST_COFINS getCST();

	/**
	 * Valor da base de cálculo COFINS.
	 */
	public BigDecimal getCOFINSVBC();

	/**
	 * Valor da base de cálculo COFINS.
	 * 
	 * @param vBC
	 */
	public void setCOFINSVBC(BigDecimal vBC);

	/**
	 * Alíquota COFINS.
	 */
	public BigDecimal getPCOFINS();

	/**
	 * Valor COFINS.
	 */
	public BigDecimal getVCOFINS();

	/**
	 * Valor COFINS.
	 * 
	 * @param vCOFINS
	 */
	public void setVCOFINS(BigDecimal vCOFINS);

}