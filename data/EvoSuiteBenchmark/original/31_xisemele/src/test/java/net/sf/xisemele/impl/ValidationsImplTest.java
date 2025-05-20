package net.sf.xisemele.impl;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Casos de teste para a classe {@link ValidationsImpl}.
 * 
 * @author Carlos Eduardo Coral.
 */
public class ValidationsImplTest {

   /**
    * Testa o método {@link ValidationsImpl#assertNotNull(Object...)}.
    */
   @Test
   public void testAssertNotNull() {
      Message message = createStrictMock(Message.class);
      expect(message.getMessage("net.sf.xisemele.null.pointer.exception", "parameter")).andReturn("message");
      replay(message);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createMessage()).andReturn(message);
      replay(factory);
      
      ValidationsImpl validations = new ValidationsImpl(factory);
      try {
         validations.assertNotNull("value", new Object(), "parameter", null);
         fail("Deveria ter disparado NullPointerException");
      } catch (NullPointerException e) {
         assertEquals("message", e.getMessage());
      }
      
      verify(message, factory);
   }
   
   /**
    * Testa o método {@link ValidationsImpl#assertValidName(String)}.
    */
   @Test
   public void testAssertValidName() {
      ValidationsImpl validations = new ValidationsImpl(createNiceMock(Factory.class));

      validations.assertValidName("a123-a");
      validations.assertValidName(":a");
      validations.assertValidName("_a-c3");
      validations.assertValidName("__test-name");
      
      assertInvalidName(validations, "");
      assertInvalidName(validations, " ");
      assertInvalidName(validations, "1a");
      assertInvalidName(validations, " a");
      assertInvalidName(validations, ".a");
      assertInvalidName(validations, "-a");
      assertInvalidName(validations, "+a");
      assertInvalidName(validations, "@a");
      assertInvalidName(validations, "$a");
      assertInvalidName(validations, "*a");
      assertInvalidName(validations, "(a");
      assertInvalidName(validations, ")a");
      assertInvalidName(validations, "!a");
      assertInvalidName(validations, "\"a");
      assertInvalidName(validations, "'a");
      assertInvalidName(validations, ";a");
      assertInvalidName(validations, "{a");
      assertInvalidName(validations, "}a");
      assertInvalidName(validations, "[a");
      assertInvalidName(validations, "]a");
      assertInvalidName(validations, "/a");
      assertInvalidName(validations, "~a");
      assertInvalidName(validations, "^a");
      assertInvalidName(validations, "'a");
      assertInvalidName(validations, "`a");
   }
   
   /**
    * Verifica se a instância de {@link Validations} acusa o nome especificado como inválido.
    */
   private void assertInvalidName(Validations validations, String name) {
      try {
         validations.assertValidName(name);
         fail("O nome '" + name + "' deveria ser inválido");
      } catch (Exception e) {
      }
   }
   
   /**
    * Testa o método {@link ValidationsImpl#getFirstNotNull(Object...)}.
    */
   @Test
   public void testGetFirstNotNull() {
      ValidationsImpl validations = new ValidationsImpl(null);
      
      assertSame("a", validations.getFirstNotNull("a"));
      assertSame("a", validations.getFirstNotNull(null, "a"));
      assertSame("a", validations.getFirstNotNull(null, "a", "b"));
      assertSame("a", validations.getFirstNotNull(null, null, "a"));
      assertSame("a", validations.getFirstNotNull(null, null, null, "a"));
   }
}
