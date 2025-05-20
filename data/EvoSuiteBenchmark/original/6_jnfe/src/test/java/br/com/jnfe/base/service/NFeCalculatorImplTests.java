package br.com.jnfe.base.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.junit.Before;
import org.junit.Test;

import br.com.jnfe.base.COFINS;
import br.com.jnfe.base.CST_COFINS;
import br.com.jnfe.base.CST_IPI;
import br.com.jnfe.base.CST_PIS;
import br.com.jnfe.base.C_ICMS;
import br.com.jnfe.base.ICMS;
import br.com.jnfe.base.IPI;
import br.com.jnfe.base.ModBC;
import br.com.jnfe.base.PIS;

/**
 * 
 * @author mauriciofernandesdecastro
 */
public class NFeCalculatorImplTests {
	
	@Test
	public void icms() {
		
		ICMS icms = new ICMS() {
			
			public char getModBC() { return ModBC.MARGEM.getValue(); }
			
			public C_ICMS getC_ICMS() { return null; }
			
			public BigDecimal getVICMS() { return taxValue; }
			
			public void setVICMS(BigDecimal vICMS) { taxValue = vICMS; }
			
			public void setVBc(BigDecimal vBC) { }
			
			public BigDecimal getVCredICMSSN() { return null; }
			
			public BigDecimal getVBc() { return BigDecimal.TEN; }
			
			public BigDecimal getPICMS() { return new BigDecimal(12); }
			
			public BigDecimal getPRedBC() { return null; }
			
			public BigDecimal getPCredSN() { return null; }
			
		};
		assertEquals("1,2000", new DecimalFormat("0.0000").format(nFeCalculator.calculate(icms)));
		
	}
	
	@Test
	public void ipi() {
		
		IPI ipi = new IPI() {
			
			//getCSTGrupo() { return null; }
			
			//getModBC() { return 0; }
			
			public CST_IPI getCST() { return null; }
			
			public BigDecimal getVIPI() { return taxValue; }
			
			public void setVIPI(BigDecimal vIPI) { taxValue = vIPI; }
			
			public void setIPIVBC(BigDecimal vBC) { }
			
			public BigDecimal getIPIVBC() { return BigDecimal.TEN; }
			
			public BigDecimal getPIPI() { return new BigDecimal(5); }
			
			//getPRedBC() { return null; }
			
			//getPCredSN() { return null; }

			public String getCSelo() { return null; }
			
		};
		assertEquals("0,5000", new DecimalFormat("0.0000").format(nFeCalculator.calculate(ipi)));
		
	}
	
	@Test
	public void pis() {
		
		PIS pis = new PIS() {
			
			//getCSTGrupo() { return null; }
			
			//getModBC() { return 0; }
			
			public CST_PIS getCST() { return null; }
			
			public BigDecimal getVPIS() { return taxValue; }
			
			public void setVPIS(BigDecimal vPIS) { taxValue = vPIS; }
			
			public void setPISVBC(BigDecimal vBC) { }
			
			public BigDecimal getPISVBC() { return BigDecimal.TEN; }
			
			public BigDecimal getPPIS() { return new BigDecimal(0.8); }
			
			//getPRedBC() { return null; }
			
			//getPCredSN() { return null; }

		};
		assertEquals("0,0800", new DecimalFormat("0.0000").format(nFeCalculator.calculate(pis)));
		
	}
	
	@Test
	public void cofins() {
		
		COFINS pis = new COFINS() {
			
			//getCSTGrupo() { return null; }
			
			//getModBC() { return 0; }
			
			public CST_COFINS getCST() { return null; }
			
			public BigDecimal getVCOFINS() { return taxValue; }
			
			public void setVCOFINS(BigDecimal vCOFINS) { taxValue = vCOFINS; }
			
			public void setCOFINSVBC(BigDecimal vBC) { }
			
			public BigDecimal getCOFINSVBC() { return BigDecimal.TEN; }
			
			public BigDecimal getPCOFINS() { return new BigDecimal(3.2); }
			
			//getPRedBC() { return null; }
			
			//getPCredSN() { return null; }

		};
		assertEquals("0,3200", new DecimalFormat("0.0000").format(nFeCalculator.calculate(pis)));
		
	}
	
	// locals
	
	private NFeCalculator nFeCalculator;
	private BigDecimal taxValue;
		
	@Before
	public void setUp() {
		nFeCalculator = new NFeCalculatorImpl();
	}

}
