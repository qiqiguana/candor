package net.sf.xisemele.impl;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

/**
 * Casos de teste para a classe {@link DateFormatter}.
 * 
 * @author Carlos Eduardo Coral.
 */
public class DateFormatterTest {
   
   /**
    * Testa os métodos {@link DateFormatter#parse(String)} e {@link DateFormatter#format(Object)}.
    */
   @Test
   public void testParseFormat() {
      DateFormatter formatter = new DateFormatter("yyyyMMdd");
      Date date = (Date) formatter.parse("20090627");
      assertEquals("20090627", formatter.format(date));
   }
}
