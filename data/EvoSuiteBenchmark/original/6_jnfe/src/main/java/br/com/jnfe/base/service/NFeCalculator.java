package br.com.jnfe.base.service;

import java.math.BigDecimal;

import br.com.jnfe.base.COFINS;
import br.com.jnfe.base.ICMS;
import br.com.jnfe.base.ICMSExt;
import br.com.jnfe.base.ICMSST;
import br.com.jnfe.base.IPI;
import br.com.jnfe.base.PIS;

/**
 * Interface para cálculo de impostos.
 * 
 * @author mauriciofernandesdecastro
 */
public interface NFeCalculator {
	
	/**
	 * Calcula ICMS.
	 * 
	 * @param icms
	 */
	public BigDecimal calculate(ICMS icms);

	/**
	 * Calcula ICMS por substituição tributária.
	 * 
	 * @param icms
	 */
	public BigDecimal calculate(ICMSST icms);

	/**
	 * Calcula ICMS que requer campos estendidos.
	 * 
	 * @param icms
	 */
	public BigDecimal calculate(ICMSExt icms);

	/**
	 * Calcula IPI.
	 * 
	 * @param ipi
	 */
	public BigDecimal calculate(IPI ipi);

	/**
	 * Calcula PIS.
	 * 
	 * @param pis
	 */
	public BigDecimal calculate(PIS pis);

	/**
	 * Calcula COFINS.
	 * 
	 * @param cofins
	 */
	public BigDecimal calculate(COFINS cofins);

}
