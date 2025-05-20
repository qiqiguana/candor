package br.com.jnfe.base;

import java.math.BigDecimal;


/**
 * Interface para PIS.
 * 
 * @author mauriciofernandesdecastro
 */
public interface PIS {

	/**
	 * C�digo da situa��o tribut�ria.
	 */
	public CST_PIS getCST();

	/**
	 * Valor da base de c�lculo do PIS.
	 */
	public BigDecimal getPISVBC();

	/**
	 * Base de c�lculo do PIS.
	 * 
	 * @param vBC
	 */
	public void setPISVBC(BigDecimal vBC);

	/**
	 * Al�quota do PIS.
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