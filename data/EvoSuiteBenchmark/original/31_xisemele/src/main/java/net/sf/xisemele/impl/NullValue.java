package net.sf.xisemele.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import net.sf.xisemele.api.Value;
import net.sf.xisemele.exception.FormatterNotConfiguredException;

/**
 * Implementação de objeto nulo para a interface {@link Value}.
 * 
 * @author Carlos Eduardo Coral.
 */
class NullValue implements Value {

   /**
    * Única instância de {@link NullValue}.
    */
   public static final NullValue INSTANCE = new NullValue();
   
   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Retorna o valor <code>null</code>.
    * 
    * @return
    *       valor <code>null</code>.
    */
   public BigDecimal asBigDecimal() {
      return null;
   }

   /**
    * Retorna o valor <code>null</code>.
    * 
    * @return
    *       valor <code>null</code>.
    */
   public BigInteger asBigInteger() {
      return null;
   }

   /**
    * Retorna o valor <code>null</code>.
    * 
    * @return
    *       valor <code>null</code>.
    */
   public Boolean asBoolean() {
      return null;
   }

   /**
    * Retorna o valor <code>null</code>.
    * 
    * @return
    *       valor <code>null</code>.
    */
   public Byte asByte() {
      return null;
   }

   /**
    * Retorna o valor <code>null</code>.
    * 
    * @return
    *       valor <code>null</code>.
    */
   public Date asDate() {
      return null;
   }
   
   /**
    * Retorna o valor <code>null</code>.
    * @param pattern
    *       não é utilizado nessa implementação.
    * @return
    *       valor <code>null</code>.
    */
   public Date asDate(String pattern) {
      return null;
   }

   /**
    * Retorna o valor <code>null</code>.
    * 
    * @return
    *       valor <code>null</code>.
    */
   public Double asDouble() {
      return null;
   }

   /**
    * Retorna o valor <code>null</code>.
    * 
    * @return
    *       valor <code>null</code>.
    */
   public Float asFloat() {
      return null;
   }

   /**
    * Retorna o valor <code>null</code>.
    * 
    * @return
    *       valor <code>null</code>.
    */
   public Integer asInteger() {
      return null;
   }

   /**
    * Retorna o valor <code>null</code>.
    * 
    * @return
    *       valor <code>null</code>.
    */
   public Long asLong() {
      return null;
   }

   /**
    * Retorna o valor <code>null</code>.
    * 
    * @return
    *       valor <code>null</code>.
    */
   public Short asShort() {
      return null;
   }

   /**
    * Retorna o valor <code>null</code>.
    * 
    * @return
    *       valor <code>null</code>.
    */
   public String asString() {
      return null;
   }
   
   /**
    * Retorna o valor <code>null</code>.
    *
    * @param type
    *       não é utilizado nessa implementação.
    * 
    * @return
    *       valor <code>null</code>.
    */
   public <T> T asType(Class<T> type) throws FormatterNotConfiguredException {
      return null;
   }
   
   /**
    * Retorna o valor <code>null</code>.
    * @return
    *       <code>null</code>.
    */
   @Override
   public String toString() {
      return null;
   }
   
   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o) {
      return o instanceof NullValue;
   }
   
   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode() {
      return 0;
   }

   /**
    * Construtora <i>default</i> privada.
    */
   private NullValue() {
   }
}
