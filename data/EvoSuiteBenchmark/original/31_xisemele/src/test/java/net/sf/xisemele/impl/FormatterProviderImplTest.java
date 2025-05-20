package net.sf.xisemele.impl;

import static org.easymock.classextension.EasyMock.*;
import static org.junit.Assert.*;

import net.sf.xisemele.api.Formatter;

import org.junit.Test;

/**
 * Casos de teste para a classe {@link FormatterProviderImpl}.
 *  
 * @author Carlos Eduardo Coral.
 */
public class FormatterProviderImplTest {

   /**
    * Testa o comportamento de {@link FormatterProviderImpl}.
    */
   @Test
   @SuppressWarnings("unchecked")
   public void test() {
      Formatter formatter1 = createNiceMock(Formatter.class);
      Formatter formatter2 = createNiceMock(Formatter.class);
      Formatter formatter3 = createNiceMock(Formatter.class);
      
      FormatterProviderImpl provider = new FormatterProviderImpl();
      provider.setFormatter(Integer.class, formatter1);
      provider.setFormatter(Long.class, formatter2);
      provider.setFormatter(Double.class, formatter3);
      
      assertSame(formatter1, provider.getFormatter(Integer.class));
      assertSame(formatter2, provider.getFormatter(Long.class));
      assertSame(formatter3, provider.getFormatter(Double.class));
      
      assertNull(provider.getFormatter(String.class));
   }
}
