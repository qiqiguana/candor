package net.sf.xisemele.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.xisemele.api.Value;
import net.sf.xisemele.api.ValueList;
import net.sf.xisemele.exception.FormatterNotConfiguredException;

/**
 * Implementação de {@link ValueList}.
 * 
 * @author Carlos Eduardo Coral.
 */
class ValueListImpl implements ValueList {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;
   
   /**
    * Lista de {@link Value} que a instância de {@link ValueListImpl} abstrai.
    */
   private final List<Value> values;
   
   /**
    * Cria uma nova instância de {@link ValueListImpl}.
    * 
    * @param values
    *          lista de {@link Value} que será manipulada.
    */
   ValueListImpl(List<Value> values) {
      this.values = values;
   }
   
   /**
    * {@inheritDoc}
    */
   public List<Byte> asByte() {
      List<Byte> list = new ArrayList<Byte>();
      for (Value value : values) {
         list.add(value.asByte());
      }
      return list;
   }
   
   /**
    * {@inheritDoc}
    */
   public List<Short> asShort() {
      List<Short> list = new ArrayList<Short>();
      for (Value value : values) {
         list.add(value.asShort());
      }
      return list;
   }
   
   /**
    * {@inheritDoc}
    */
   public List<Integer> asInteger() {
      List<Integer> list = new ArrayList<Integer>();
      for (Value value : values) {
         list.add(value.asInteger());
      }
      return list;
   }
   
   /**
    * {@inheritDoc}
    */
   public List<Long> asLong() {
      List<Long> list = new ArrayList<Long>();
      for (Value value : values) {
         list.add(value.asLong());
      }
      return list;
   }
   
   /**
    * {@inheritDoc}
    */
   public List<Float> asFloat() {
      List<Float> list = new ArrayList<Float>();
      for (Value value : values) {
         list.add(value.asFloat());
      }
      return list;
   }
   
   /**
    * {@inheritDoc}
    */
   public List<Double> asDouble() {
      List<Double> list = new ArrayList<Double>();
      for (Value value : values) {
         list.add(value.asDouble());
      }
      return list;
   }
   
   /**
    * {@inheritDoc}
    */
   public List<BigInteger> asBigInteger() {
      List<BigInteger> list = new ArrayList<BigInteger>();
      for (Value value : values) {
         list.add(value.asBigInteger());
      }
      return list;
   }
   
   /**
    * {@inheritDoc}
    */
   public List<BigDecimal> asBigDecimal() {
      List<BigDecimal> list = new ArrayList<BigDecimal>();
      for (Value value : values) {
         list.add(value.asBigDecimal());
      }
      return list;
   }
   
   /**
    * {@inheritDoc}
    */
   public List<Boolean> asBoolean() {
      List<Boolean> list = new ArrayList<Boolean>();
      for (Value value : values) {
         list.add(value.asBoolean());
      }
      return list;
   }
   
   /**
    * {@inheritDoc}
    */
   public List<String> asString() {
      List<String> list = new ArrayList<String>();
      for (Value value : values) {
         list.add(value.asString());
      }
      return list;
   }
   
   /**
    * {@inheritDoc}
    */
   public List<Date> asDate() {
      List<Date> list = new ArrayList<Date>();
      for (Value value : values) {
         list.add(value.asDate());
      }
      return list;
   }
   
   /**
    * {@inheritDoc}
    */
   public List<Date> asDate(String pattern) {
      List<Date> list = new ArrayList<Date>();
      for (Value value : values) {
         list.add(value.asDate(pattern));
      }
      return list;
   }
   
   /**
    * {@inheritDoc}
    */
   public <T> List<T> asType(Class<T> type) throws FormatterNotConfiguredException {
      List<T> list = new ArrayList<T>();
      for (Value value : values) {
         list.add(value.asType(type));
      }
      return list;
   }
}
