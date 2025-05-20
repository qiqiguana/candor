package net.sf.xisemele.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.xisemele.api.Formatter;

/**
 * Implementação de {@link FormatterProvider}.
 * 
 * @author Carlos Eduardo Coral.
 */
class FormatterProviderImpl implements FormatterProvider {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Mapa contendo os tipos e as instâncias de {@link Formatter} correspondentes.
    */
   private Map<Class<?>, Formatter<?>> formatters = new HashMap<Class<?>, Formatter<?>>();

   /**
    * {@inheritDoc}
    */
   @SuppressWarnings("unchecked")
   public <T> Formatter<T> getFormatter(Class<?> type) {
      return (Formatter<T>) formatters.get(type);
   }

   /**
    * {@inheritDoc}
    */
   public void setFormatter(Class<?> type, Formatter<?> formatter) {
      formatters.put(type, formatter);
   }
}
