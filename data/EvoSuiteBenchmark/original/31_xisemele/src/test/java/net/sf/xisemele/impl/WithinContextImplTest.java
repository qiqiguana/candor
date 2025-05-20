package net.sf.xisemele.impl;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Caso de teste para a classe {@link WithinContextImpl}.
 * 
 * @author Carlos Eduardo Coral.
 */
public class WithinContextImplTest {

   /**
    * Testa o comportamento de {@link WithinContextImpl}.
    */
   @Test
   public void test() {
      WithinContextImpl withinContext = new WithinContextImpl();

      assertFalse(withinContext.enabled());
      withinContext.start();
      assertTrue(withinContext.enabled());
      withinContext.stop();
      assertFalse(withinContext.enabled());
   }
}
