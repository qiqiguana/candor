package br.com.jnfe.base;

import java.math.BigDecimal;

/**
 * Interface para IPI.
 * 
 * @author mauriciofernandesdecastro
 */
public interface IPI {

	/**
	 * C�digo da situa��o tribut�ria.
	 */
	public CST_IPI getCST();

	/**
	 * Valor da base de c�lculo do IPI.
	 */
	public BigDecimal getIPIVBC();

	/**
	 * Base de c�lculo do IPI.
	 */
	public void setIPIVBC(BigDecimal vBC);

	/**
	 * Al�quota do IPI.
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
	 * C�digo do selo de controle IPI
	 */
	public String getCSelo();

}