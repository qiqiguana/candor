package br.com.jnfe.base;

import java.math.BigDecimal;

/**
 * Interface para ICMS estensão.
 * 
 * @author mauriciofernandesdecastro
 */
public interface ICMSExt extends ICMSST {

	/**
	 * Motivo da desoneração do ICMS.
	 */
	public char getMotDesICMS();

	/**
	 * Valor da base de cálculo de ICMS ST cobrado anteriormente po ST.
	 */
	public BigDecimal getVBCSTRet();

	/**
	 * Valor de ICMS ST cobrado anteriormente po ST.
	 */
	public BigDecimal getVICMSSTRet();

	/**
	 * Percentual da base de cálculo de operação própria.
	 */
	public BigDecimal getPBCOp();

	/**
	 * UF para qual é devido o ICMS ST.
	 */
	public String getUFST();

}