package net.sf.xisemele.impl;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import net.sf.xisemele.api.Formatter;
import net.sf.xisemele.exception.FormatterNotConfiguredException;

import org.junit.Test;

/**
 * Casos de teste para a classe {@link ValueImpl}.
 * 
 * @author Carlos Eduardo Coral.
 */
public class ValueImplTest {

   /**
    * Testa o método {@link ValueImpl#asByte()}.
    */
   @Test
   @SuppressWarnings("unchecked")
   public void testAsByte() {
      Formatter formatter = createStrictMock(Formatter.class);
      expect(formatter.parse("10")).andReturn(new Byte("20"));
      replay(formatter);
      
      FormatterProvider provider = createStrictMock(FormatterProvider.class);
      expect(provider.getFormatter(Byte.class)).andReturn(formatter);
      expect(provider.getFormatter(Byte.class)).andReturn(null);
      replay(provider);
      
      ValueImpl value = new ValueImpl(null, provider, "10");
      
      assertEquals(new Byte("20"), value.asByte());
      assertEquals(new Byte("10"), value.asByte());
      
      verify(formatter, provider);
   }
   
   /**
    * Testa o método {@link ValueImpl#asShort()}.
    */
   @Test
   @SuppressWarnings("unchecked")
   public void testAsShort() {
      Formatter formatter = createStrictMock(Formatter.class);
      expect(formatter.parse("10")).andReturn(new Short("20"));
      replay(formatter);
      
      FormatterProvider provider = createStrictMock(FormatterProvider.class);
      expect(provider.getFormatter(Short.class)).andReturn(formatter);
      expect(provider.getFormatter(Short.class)).andReturn(null);
      replay(provider);
      
      ValueImpl value = new ValueImpl(null, provider, "10");
      
      assertEquals(new Short("20"), value.asShort());
      assertEquals(new Short("10"), value.asShort());
      
      verify(formatter, provider);
   }

   /**
    * Testa o método {@link ValueImpl#asInteger()}.
    */
   @Test
   @SuppressWarnings("unchecked")
   public void testAsInteger() {
      Formatter formatter = createStrictMock(Formatter.class);
      expect(formatter.parse("10")).andReturn(new Integer("20"));
      replay(formatter);
      
      FormatterProvider provider = createStrictMock(FormatterProvider.class);
      expect(provider.getFormatter(Integer.class)).andReturn(formatter);
      expect(provider.getFormatter(Integer.class)).andReturn(null);
      replay(provider);
      
      ValueImpl value = new ValueImpl(null, provider, "10");
      
      assertEquals(new Integer("20"), value.asInteger());
      assertEquals(new Integer("10"), value.asInteger());
      
      verify(formatter, provider);
   }
   
   /**
    * Testa o método {@link ValueImpl#asLong()}.
    */
   @Test
   @SuppressWarnings("unchecked")
   public void testAsLong() {
      Formatter formatter = createStrictMock(Formatter.class);
      expect(formatter.parse("10")).andReturn(new Long("20"));
      replay(formatter);
      
      FormatterProvider provider = createStrictMock(FormatterProvider.class);
      expect(provider.getFormatter(Long.class)).andReturn(formatter);
      expect(provider.getFormatter(Long.class)).andReturn(null);
      replay(provider);
      
      ValueImpl value = new ValueImpl(null, provider, "10");
      
      assertEquals(new Long("20"), value.asLong());
      assertEquals(new Long("10"), value.asLong());
      
      verify(formatter, provider);
   }

   /**
    * Testa o método {@link ValueImpl#asFloat()}.
    */
   @Test
   @SuppressWarnings("unchecked")
   public void testAsFloat() {
      Formatter formatter = createStrictMock(Formatter.class);
      expect(formatter.parse("10")).andReturn(new Float("20"));
      replay(formatter);
      
      FormatterProvider provider = createStrictMock(FormatterProvider.class);
      expect(provider.getFormatter(Float.class)).andReturn(formatter);
      expect(provider.getFormatter(Float.class)).andReturn(null);
      replay(provider);
      
      ValueImpl value = new ValueImpl(null, provider, "10");
      
      assertEquals(new Float("20"), value.asFloat());
      assertEquals(new Float("10"), value.asFloat());
      
      verify(formatter, provider);
   }
   
   /**
    * Testa o método {@link ValueImpl#asDouble()}.
    */
   @Test
   @SuppressWarnings("unchecked")
   public void testAsDouble() {
      Formatter formatter = createStrictMock(Formatter.class);
      expect(formatter.parse("10")).andReturn(new Double("20"));
      replay(formatter);
      
      FormatterProvider provider = createStrictMock(FormatterProvider.class);
      expect(provider.getFormatter(Double.class)).andReturn(formatter);
      expect(provider.getFormatter(Double.class)).andReturn(null);
      replay(provider);
      
      ValueImpl value = new ValueImpl(null, provider, "10");
      
      assertEquals(new Double("20"), value.asDouble());
      assertEquals(new Double("10"), value.asDouble());
      
      verify(formatter, provider);
   }
   
   /**
    * Testa o método {@link ValueImpl#asBigInteger()}.
    */
   @Test
   @SuppressWarnings("unchecked")
   public void testAsBigInteger() {
      Formatter formatter = createStrictMock(Formatter.class);
      expect(formatter.parse("10")).andReturn(new BigInteger("20"));
      replay(formatter);
      
      FormatterProvider provider = createStrictMock(FormatterProvider.class);
      expect(provider.getFormatter(BigInteger.class)).andReturn(formatter);
      expect(provider.getFormatter(BigInteger.class)).andReturn(null);
      replay(provider);
      
      ValueImpl value = new ValueImpl(null, provider, "10");
      
      assertEquals(new BigInteger("20"), value.asBigInteger());
      assertEquals(new BigInteger("10"), value.asBigInteger());
      
      verify(formatter, provider);
   }
   
   /**
    * Testa o método {@link ValueImpl#asBigDecimal()}.
    */
   @Test
   @SuppressWarnings("unchecked")
   public void testAsBigDecimal() {
      Formatter formatter = createStrictMock(Formatter.class);
      expect(formatter.parse("10")).andReturn(new BigDecimal("20"));
      replay(formatter);
      
      FormatterProvider provider = createStrictMock(FormatterProvider.class);
      expect(provider.getFormatter(BigDecimal.class)).andReturn(formatter);
      expect(provider.getFormatter(BigDecimal.class)).andReturn(null);
      replay(provider);
      
      ValueImpl value = new ValueImpl(null, provider, "10");
      
      assertEquals(new BigDecimal("20"), value.asBigDecimal());
      assertEquals(new BigDecimal("10"), value.asBigDecimal());
      
      verify(formatter, provider);
   }
   
   /**
    * Testa o método {@link ValueImpl#asBoolean()}.
    */
   @Test
   @SuppressWarnings("unchecked")
   public void testAsBoolean() {
      Formatter formatter = createStrictMock(Formatter.class);
      expect(formatter.parse("ok")).andReturn(Boolean.TRUE);
      replay(formatter);
      
      FormatterProvider provider = createStrictMock(FormatterProvider.class);
      expect(provider.getFormatter(Boolean.class)).andReturn(formatter);
      expect(provider.getFormatter(Boolean.class)).andReturn(null);
      replay(provider);
      
      ValueImpl value = new ValueImpl(null, provider, "ok");
      
      assertEquals(Boolean.TRUE, value.asBoolean());
      
      value = new ValueImpl(null, provider, "true");
      
      assertEquals(Boolean.TRUE, value.asBoolean());
      
      verify(formatter, provider);
   }
 
   /**
    * Testa o método {@link ValueImpl#asString()}.
    */
   @Test
   @SuppressWarnings("unchecked")
   public void testAsString() {
      Formatter formatter = createStrictMock(Formatter.class);
      expect(formatter.parse("original")).andReturn("formatted");
      replay(formatter);
      
      FormatterProvider provider = createStrictMock(FormatterProvider.class);
      expect(provider.getFormatter(String.class)).andReturn(formatter);
      expect(provider.getFormatter(String.class)).andReturn(null);
      replay(provider);
      
      ValueImpl value = new ValueImpl(null, provider, "original");
      
      assertEquals("formatted", value.asString());
      assertEquals("original", value.asString());
      
      verify(formatter, provider);
   }
   
   /**
    * Testa o método {@link ValueImpl#asDate()}
    */
   @Test
   @SuppressWarnings("unchecked")
   public void testAsDate() {
      Date date = new Date();
      
      Formatter formatter = createStrictMock(Formatter.class);
      expect(formatter.parse("date")).andReturn(date);
      replay(formatter);
      
      FormatterProvider provider = createStrictMock(FormatterProvider.class);
      expect(provider.getFormatter(Date.class)).andReturn(formatter);
      expect(provider.getFormatter(Date.class)).andReturn(null);
      replay(provider);
      
      ValueImpl value = new ValueImpl(null, provider, "date");
      
      assertEquals(date, value.asDate());
      
      try {
         value.asDate();
         fail("InternalError expected");
      } catch (InternalError e) {
      }
      
      verify(formatter, provider);
   }
   
   /**
    * Testa o método {@link ValueImpl#asDate(String)}.
    */
   @Test
   @SuppressWarnings("unchecked")
   public void testAsDateForPattern() {
      Date date = new Date();
      
      Formatter formatter = createStrictMock(Formatter.class);
      expect(formatter.parse("date")).andReturn(date);
      replay(formatter);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createDateFormatter("pattern")).andReturn(formatter);
      replay(factory);
      
      ValueImpl value = new ValueImpl(factory, null, "date");
      
      assertEquals(date, value.asDate("pattern"));
      
      verify(formatter, factory);
   }
   
   /**
    * Testa o método {@link ValueImpl#asType(Class)}.
    */
   @Test
   @SuppressWarnings("unchecked")
   public void testAsType() {
      MyType myType = new MyType();
      
      Formatter formatter = createStrictMock(Formatter.class);
      expect(formatter.parse("myType")).andReturn(myType);
      replay(formatter);
      
      FormatterProvider provider = createStrictMock(FormatterProvider.class);
      expect(provider.getFormatter(MyType.class)).andReturn(formatter);
      expect(provider.getFormatter(MyType.class)).andReturn(null);
      replay(provider);
      
      ValueImpl value = new ValueImpl(null, provider, "myType");
      
      assertEquals(myType, value.asType(MyType.class));
      
      try {
         value.asType(MyType.class);
         fail("FormatterNotConfiguredException expected");
      } catch (FormatterNotConfiguredException e) {
      }
      
      verify(formatter, provider);
   }
   
   /**
    * Classe usada para testar o método {@link ValueImpl#asType(Class)}.
    */
   private class MyType {
   }
   
   /**
    * Testa o método {@link ValueImpl#toString()}.
    */
   @Test
   public void testToString() {
      assertEquals("value", new ValueImpl(null, null, "value").toString());
   }
   
   /**
    * Testa o método {@link ValueImpl#equals(Object)}.
    */
   @Test
   public void testEquals() {
      assertEquals(new ValueImpl(null, null, "value"), new ValueImpl(null, null, "value"));
   }
   
   /**
    * Testa o método {@link ValueImpl#hashCode()}.
    */
   @Test
   public void testHashCode() {
      assertEquals(new ValueImpl(null, null, "value"), new ValueImpl(null, null, "value"));
   }
}
