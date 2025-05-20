package br.com.jnfe.base;

import java.math.BigDecimal;

/**
 * Interface para COFINS.
 * 
 * @author mauriciofernandesdecastro
 */
public interface COFINS {

	/**
	 * C�digo da situa��o tribut�ria.
	 */
	public CST_COFINS getCST();

	/**
	 * Valor da base de c�lculo COFINS.
	 */
	public BigDecimal getCOFINSVBC();

	/**
	 * Valor da base de c�lculo COFINS.
	 * 
	 * @param vBC
	 */
	public void setCOFINSVBC(BigDecimal vBC);

	/**
	 * Al�quota COFINS.
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