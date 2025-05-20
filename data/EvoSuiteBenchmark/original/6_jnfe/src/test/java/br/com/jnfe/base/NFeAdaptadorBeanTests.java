package br.com.jnfe.base;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.jnfe.base.adapter.AbstractNFeAdaptadorBean;


/**
 * 
 * @author mauriciofernandesdecastro
 */
public class NFeAdaptadorBeanTests {
	
	private AbstractNFeAdaptadorBean nFeAdaptadorBean;

	@Test
	public void converte() {
		assertEquals(2, nFeAdaptadorBean.convertePosPeso(43, 43));
		assertEquals(3, nFeAdaptadorBean.convertePosPeso(42, 43));
		assertEquals(8, nFeAdaptadorBean.convertePosPeso(37, 43));
		assertEquals(9, nFeAdaptadorBean.convertePosPeso(36, 43));
		assertEquals(2, nFeAdaptadorBean.convertePosPeso(35, 43));
		assertEquals(3, nFeAdaptadorBean.convertePosPeso(34, 43));
		assertEquals(4, nFeAdaptadorBean.convertePosPeso(1, 43));
		assertEquals(6, nFeAdaptadorBean.convertePosPeso(7, 43));
		assertEquals(3, nFeAdaptadorBean.convertePosPeso(10, 43));
	}

	@Test
	public void soma() {
		String chNFe = "5206043300991100250655012000000780026730161";
		assertEquals(43, chNFe.length());
		assertEquals(644, nFeAdaptadorBean.calculaSomaDV(chNFe));
	}
	
	public void calcula() {
		String chNFe = "5206043300991100250655012000000780026730161";
		assertEquals(5, nFeAdaptadorBean.calculaDV(chNFe));
	}
	
	@Before
	public void setUp() {
		nFeAdaptadorBean = new AbstractNFeAdaptadorBean() {
		};
	}

}
