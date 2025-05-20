package br.com.jnfe.base;

import java.math.BigDecimal;

/**
 * Interface para ICMS estens�o.
 * 
 * @author mauriciofernandesdecastro
 */
public interface ICMSExt extends ICMSST {

	/**
	 * Motivo da desonera��o do ICMS.
	 */
	public char getMotDesICMS();

	/**
	 * Valor da base de c�lculo de ICMS ST cobrado anteriormente po ST.
	 */
	public BigDecimal getVBCSTRet();

	/**
	 * Valor de ICMS ST cobrado anteriormente po ST.
	 */
	public BigDecimal getVICMSSTRet();

	/**
	 * Percentual da base de c�lculo de opera��o pr�pria.
	 */
	public BigDecimal getPBCOp();

	/**
	 * UF para qual � devido o ICMS ST.
	 */
	public String getUFST();

}