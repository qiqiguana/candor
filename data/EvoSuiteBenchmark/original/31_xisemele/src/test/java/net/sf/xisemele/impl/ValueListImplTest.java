package net.sf.xisemele.impl;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;

import net.sf.xisemele.api.Value;
import net.sf.xisemele.impl.ValueListImpl;

import org.junit.Test;


/**
 * Casos de teste para a classe {@link ValueListImpl}.
 * 
 * @author Carlos Eduardo Coral.
 */
public class ValueListImplTest {

   /**
    * Testa o método {@link ValueListImpl#asByte()}. 
    */
   @Test
   public void testAsByte() {
      Value value1 = createMock(Value.class);
      expect(value1.asByte()).andReturn(new Byte("10"));
      Value value2 = createMock(Value.class);
      expect(value2.asByte()).andReturn(new Byte("20"));
      Value value3 = createMock(Value.class);
      expect(value3.asByte()).andReturn(new Byte("30"));
      Value value4 = createMock(Value.class);
      expect(value4.asByte()).andReturn(new Byte("40"));
      
      replay(value1);
      replay(value2);
      replay(value3);
      replay(value4);
      
      assertEquals(Arrays.asList(new Byte("10"), new Byte("20"), new Byte("30"), new Byte("40")), 
            new ValueListImpl(Arrays.asList(value1, value2, value3, value4)).asByte());
      
      verify(value1);
      verify(value2);
      verify(value3);
      verify(value4);
   }
   
   /**
    * Testa o método {@link ValueListImpl#asShort()}. 
    */
   @Test
   public void testAsShort() {
      Value value1 = createMock(Value.class);
      expect(value1.asShort()).andReturn(new Short("10"));
      Value value2 = createMock(Value.class);
      expect(value2.asShort()).andReturn(new Short("20"));
      Value value3 = createMock(Value.class);
      expect(value3.asShort()).andReturn(new Short("30"));
      Value value4 = createMock(Value.class);
      expect(value4.asShort()).andReturn(new Short("40"));
      
      replay(value1);
      replay(value2);
      replay(value3);
      replay(value4);
      
      assertEquals(Arrays.asList(new Short("10"), new Short("20"), new Short("30"), new Short("40")), 
            new ValueListImpl(Arrays.asList(value1, value2, value3, value4)).asShort());
      
      verify(value1);
      verify(value2);
      verify(value3);
      verify(value4);
   }
   
   /**
    * Testa o método {@link ValueListImpl#asInteger()}. 
    */
   @Test
   public void testAsInteger() {
      Value value1 = createMock(Value.class);
      expect(value1.asInteger()).andReturn(new Integer("10"));
      Value value2 = createMock(Value.class);
      expect(value2.asInteger()).andReturn(new Integer("20"));
      Value value3 = createMock(Value.class);
      expect(value3.asInteger()).andReturn(new Integer("30"));
      Value value4 = createMock(Value.class);
      expect(value4.asInteger()).andReturn(new Integer("40"));
      
      replay(value1);
      replay(value2);
      replay(value3);
      replay(value4);
      
      assertEquals(Arrays.asList(new Integer("10"), new Integer("20"), new Integer("30"), new Integer("40")), 
            new ValueListImpl(Arrays.asList(value1, value2, value3, value4)).asInteger());
      
      verify(value1);
      verify(value2);
      verify(value3);
      verify(value4);
   }
   
   /**
    * Testa o método {@link ValueListImpl#asLong()}. 
    */
   @Test
   public void testAsLong() {
      Value value1 = createMock(Value.class);
      expect(value1.asLong()).andReturn(new Long("10"));
      Value value2 = createMock(Value.class);
      expect(value2.asLong()).andReturn(new Long("20"));
      Value value3 = createMock(Value.class);
      expect(value3.asLong()).andReturn(new Long("30"));
      Value value4 = createMock(Value.class);
      expect(value4.asLong()).andReturn(new Long("40"));
      
      replay(value1);
      replay(value2);
      replay(value3);
      replay(value4);
      
      assertEquals(Arrays.asList(new Long("10"), new Long("20"), new Long("30"), new Long("40")), 
            new ValueListImpl(Arrays.asList(value1, value2, value3, value4)).asLong());
      
      verify(value1);
      verify(value2);
      verify(value3);
      verify(value4);
   }
   
   /**
    * Testa o método {@link ValueListImpl#asFloat()}. 
    */
   @Test
   public void testAsFloat() {
      Value value1 = createMock(Value.class);
      expect(value1.asFloat()).andReturn(new Float("10"));
      Value value2 = createMock(Value.class);
      expect(value2.asFloat()).andReturn(new Float("20"));
      Value value3 = createMock(Value.class);
      expect(value3.asFloat()).andReturn(new Float("30"));
      Value value4 = createMock(Value.class);
      expect(value4.asFloat()).andReturn(new Float("40"));
      
      replay(value1);
      replay(value2);
      replay(value3);
      replay(value4);
      
      assertEquals(Arrays.asList(new Float("10"), new Float("20"), new Float("30"), new Float("40")), 
            new ValueListImpl(Arrays.asList(value1, value2, value3, value4)).asFloat());
      
      verify(value1);
      verify(value2);
      verify(value3);
      verify(value4);
   }
   
   /**
    * Testa o método {@link ValueListImpl#asDouble()}. 
    */
   @Test
   public void testAsDouble() {
      Value value1 = createMock(Value.class);
      expect(value1.asDouble()).andReturn(new Double("10"));
      Value value2 = createMock(Value.class);
      expect(value2.asDouble()).andReturn(new Double("20"));
      Value value3 = createMock(Value.class);
      expect(value3.asDouble()).andReturn(new Double("30"));
      Value value4 = createMock(Value.class);
      expect(value4.asDouble()).andReturn(new Double("40"));
      
      replay(value1);
      replay(value2);
      replay(value3);
      replay(value4);
      
      assertEquals(Arrays.asList(new Double("10"), new Double("20"), new Double("30"), new Double("40")), 
            new ValueListImpl(Arrays.asList(value1, value2, value3, value4)).asDouble());
      
      verify(value1);
      verify(value2);
      verify(value3);
      verify(value4);
   }
   
   /**
    * Testa o método {@link ValueListImpl#asBigInteger()}. 
    */
   @Test
   public void testAsBigInteger() {
      Value value1 = createMock(Value.class);
      expect(value1.asBigInteger()).andReturn(new BigInteger("10"));
      Value value2 = createMock(Value.class);
      expect(value2.asBigInteger()).andReturn(new BigInteger("20"));
      Value value3 = createMock(Value.class);
      expect(value3.asBigInteger()).andReturn(new BigInteger("30"));
      Value value4 = createMock(Value.class);
      expect(value4.asBigInteger()).andReturn(new BigInteger("40"));
      
      replay(value1);
      replay(value2);
      replay(value3);
      replay(value4);
      
      assertEquals(Arrays.asList(new BigInteger("10"), new BigInteger("20"), new BigInteger("30"), new BigInteger("40")), 
            new ValueListImpl(Arrays.asList(value1, value2, value3, value4)).asBigInteger());
      
      verify(value1);
      verify(value2);
      verify(value3);
      verify(value4);
   }
   
   /**
    * Testa o método {@link ValueListImpl#asBigDecimal()}. 
    */
   @Test
   public void testAsBigDecimal() {
      Value value1 = createMock(Value.class);
      expect(value1.asBigDecimal()).andReturn(new BigDecimal("10"));
      Value value2 = createMock(Value.class);
      expect(value2.asBigDecimal()).andReturn(new BigDecimal("20"));
      Value value3 = createMock(Value.class);
      expect(value3.asBigDecimal()).andReturn(new BigDecimal("30"));
      Value value4 = createMock(Value.class);
      expect(value4.asBigDecimal()).andReturn(new BigDecimal("40"));
      
      replay(value1);
      replay(value2);
      replay(value3);
      replay(value4);
      
      assertEquals(Arrays.asList(new BigDecimal("10"), new BigDecimal("20"), new BigDecimal("30"), new BigDecimal("40")), 
            new ValueListImpl(Arrays.asList(value1, value2, value3, value4)).asBigDecimal());
      
      verify(value1);
      verify(value2);
      verify(value3);
      verify(value4);
   }
   
   /**
    * Testa o método {@link ValueListImpl#asBoolean()}. 
    */
   @Test
   public void testAsBoolean() {
      Value value1 = createMock(Value.class);
      expect(value1.asBoolean()).andReturn(Boolean.TRUE);
      Value value2 = createMock(Value.class);
      expect(value2.asBoolean()).andReturn(Boolean.FALSE);
      Value value3 = createMock(Value.class);
      expect(value3.asBoolean()).andReturn(Boolean.FALSE);
      Value value4 = createMock(Value.class);
      expect(value4.asBoolean()).andReturn(Boolean.TRUE);
      
      replay(value1);
      replay(value2);
      replay(value3);
      replay(value4);
      
      assertEquals(Arrays.asList(Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE), 
            new ValueListImpl(Arrays.asList(value1, value2, value3, value4)).asBoolean());
      
      verify(value1);
      verify(value2);
      verify(value3);
      verify(value4);
   }
   
   /**
    * Testa o método {@link ValueListImpl#asString()}. 
    */
   @Test
   public void testAsString() {
      Value value1 = createMock(Value.class);
      expect(value1.asString()).andReturn("one");
      Value value2 = createMock(Value.class);
      expect(value2.asString()).andReturn("two");
      Value value3 = createMock(Value.class);
      expect(value3.asString()).andReturn("three");
      Value value4 = createMock(Value.class);
      expect(value4.asString()).andReturn("four");
      
      replay(value1);
      replay(value2);
      replay(value3);
      replay(value4);
      
      assertEquals(Arrays.asList("one", "two", "three", "four"), 
            new ValueListImpl(Arrays.asList(value1, value2, value3, value4)).asString());
      
      verify(value1);
      verify(value2);
      verify(value3);
      verify(value4);
   }
   
   /**
    * Testa o método {@link ValueListImpl#asDate()}. 
    */
   @Test
   public void testAsDate() {
      Date date1 = new Date();
      Date date2 = new Date();
      Date date3 = new Date();
      Date date4 = new Date();
      
      Value value1 = createMock(Value.class);
      expect(value1.asDate()).andReturn(date1);
      Value value2 = createMock(Value.class);
      expect(value2.asDate()).andReturn(date2);
      Value value3 = createMock(Value.class);
      expect(value3.asDate()).andReturn(date3);
      Value value4 = createMock(Value.class);
      expect(value4.asDate()).andReturn(date4);
      
      replay(value1);
      replay(value2);
      replay(value3);
      replay(value4);
      
      assertEquals(Arrays.asList(date1, date2, date3, date4), 
            new ValueListImpl(Arrays.asList(value1, value2, value3, value4)).asDate());
      
      verify(value1);
      verify(value2);
      verify(value3);
      verify(value4);
   }

   /**
    * Testa o método {@link ValueListImpl#asType(Class)}. 
    */
   @Test
   public void testAsType() {
      Value value1 = createMock(Value.class);
      expect(value1.asType(String.class)).andReturn("one");
      Value value2 = createMock(Value.class);
      expect(value2.asType(String.class)).andReturn("two");
      Value value3 = createMock(Value.class);
      expect(value3.asType(String.class)).andReturn("three");
      Value value4 = createMock(Value.class);
      expect(value4.asType(String.class)).andReturn("four");
      
      replay(value1);
      replay(value2);
      replay(value3);
      replay(value4);
      
      assertEquals(Arrays.asList("one", "two", "three", "four"), 
            new ValueListImpl(Arrays.asList(value1, value2, value3, value4)).asType(String.class));
      
      verify(value1);
      verify(value2);
      verify(value3);
      verify(value4);
   }
}

