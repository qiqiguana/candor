package br.com.jnfe.base.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.jnfe.base.COFINS;
import br.com.jnfe.base.ICMS;
import br.com.jnfe.base.ICMSExt;
import br.com.jnfe.base.ICMSST;
import br.com.jnfe.base.IPI;
import br.com.jnfe.base.ModBC;
import br.com.jnfe.base.PIS;

/**
 * Implementação padrão para cálculo de impostos.
 * 
 * @author mauriciofernandesdecastro
 */
public class NFeCalculatorImpl implements NFeCalculator {

	public BigDecimal calculate(ICMS icms) {
		BigDecimal taxValue = icms.getVICMS();
		validate(icms.getPICMS(), icms.getVBc());

		if (taxValue==null) {
			if (icms.getModBC()==ModBC.MARGEM.getValue()) {
				taxValue = internalCalculate(icms.getVBc(), icms.getPICMS());
				logger.debug("Valor calculado do ICMS: {}.", taxValue);
				return taxValue;
			}
			throw new UnsupportedOperationException("Modo de determinação da base de cálculo ainda não suportada.");
		}
		logger.warn("Valor não recalculado do ICMS: {}.", taxValue);
		
		return taxValue;
	}

	public BigDecimal calculate(ICMSST icms) {
		BigDecimal taxValue = calculate((ICMS) icms);
		// TODO implantar calculo ICMS ST.
		return taxValue;
	}

	public BigDecimal calculate(ICMSExt icms) {
		BigDecimal taxValue = calculate((ICMS) icms);
		// TODO implantar calculo ICMS ext.
		return taxValue;
	}

	public BigDecimal calculate(IPI ipi) {
		BigDecimal taxValue = ipi.getVIPI();
		validate(ipi.getPIPI(), ipi.getIPIVBC());

		if (taxValue==null) {
			taxValue = internalCalculate(ipi.getIPIVBC(), ipi.getPIPI());
			logger.debug("Valor calculado do IPI: {}.", taxValue);
			return taxValue;
		}
		logger.warn("Valor não recalculado do IPI: {}.", taxValue);
		
		return taxValue;
	}

	public BigDecimal calculate(PIS pis) {
		BigDecimal taxValue = pis.getVPIS();
		validate(pis.getPPIS(), pis.getPISVBC());

		if (taxValue==null) {
			taxValue = internalCalculate(pis.getPISVBC(), pis.getPPIS());
			logger.debug("Valor calculado do PIS: {}.", taxValue);
			return taxValue;
		}
		logger.warn("Valor não recalculado do PIS: {}.", taxValue);
		
		return taxValue;
	}

	public BigDecimal calculate(COFINS cofins) {
		BigDecimal taxValue = cofins.getVCOFINS();
		validate(cofins.getPCOFINS(), cofins.getCOFINSVBC());

		if (taxValue==null) {
			taxValue = internalCalculate(cofins.getCOFINSVBC(), cofins.getPCOFINS());
			logger.debug("Valor calculado do CONFINS: {}.", taxValue);
			return taxValue;
		}
		logger.warn("Valor não recalculado do CONFINS: {}.", taxValue);
		
		return taxValue;
	}
	
	/**
	 * Lança exceção sempre que algum dos parâmetros for nulo.
	 * 
	 * @param aliquota
	 * @param vBC
	 */
	protected void validate(BigDecimal aliquota, BigDecimal vBC) {
		if (aliquota==null) {
			throw new IllegalArgumentException("Valor da alíquota não pode ser nulo");
		}
		if (vBC==null) {
			throw new IllegalArgumentException("Valor da base de cálculo não pode ser nulo");
		}
	}
	
	/**
	 * Calcula o imposto como um percentual da base.
	 * 
	 * @param vBC valor da base de cálculo
	 * @param p alíquota
	 */
	protected BigDecimal internalCalculate(BigDecimal vBC, BigDecimal p) {
		return vBC.multiply(p).divide(new BigDecimal(100));
	}
	
	private static final Logger logger = LoggerFactory.getLogger(NFeCalculatorImpl.class);

}
