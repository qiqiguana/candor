package net.sf.xisemele.impl;

import java.util.regex.Pattern;

import net.sf.xisemele.exception.InvalidNameException;

/**
 * Implementação de {@link Validations}.
 * 
 * @author Carlos Eduardo Coral.
 */
class ValidationsImpl implements Validations {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;
   
   /**
    * Instância de <code>java.util.regex.Pattern</code> correspondente ao padrão de nomes válidos.
    */
   private final Pattern namePattern = Pattern.compile("[\\:a-zA-Z_][\\:a-zA-Z_\\-\\.0-9]*");
   
   /**
    * Instância de {@link Factory} que será usada para obter novas instâncias de {@link Message}.
    */
   private final Factory factory;

   /**
    * Cria uma nova instância de {@link ValidationsImpl}.
    * 
    * @param factory
    *       instância de {@link Factory} que será usada para obter novas instâncias de {@link Message}.
    */
   public ValidationsImpl(Factory factory) {
      this.factory = factory;
   }

   /**
    * {@inheritDoc}
    */
   public void assertNotNull(Object... parameters) throws NullPointerException {
      for (int index = 1; index < parameters.length; index += 2) {
         if (parameters[index] == null) {
            String message = factory.createMessage().getMessage("net.sf.xisemele.null.pointer.exception", parameters[index - 1]);
            throw new NullPointerException(message);
         }
      }
   }

   /**
    * {@inheritDoc}
    */
   public void assertValidName(String name) throws InvalidNameException {
      if (!namePattern.matcher(name).matches()) {
         throw new InvalidNameException(name);
      }
   }

   /**
    * {@inheritDoc}
    */
   public <T> T getFirstNotNull(T... values) {
      for (T value : values) {
         if (value != null) {
            return value;
         }
      }
      return null;
   }
}
