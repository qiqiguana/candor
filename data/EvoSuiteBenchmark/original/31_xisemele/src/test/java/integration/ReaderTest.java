package integration;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import net.sf.xisemele.api.Element;
import net.sf.xisemele.api.Formatter;
import net.sf.xisemele.api.Reader;
import net.sf.xisemele.impl.XisemeleFactory;

import org.junit.Test;

/**
 * Casos de teste de integração da funcionalidade de leitura de XML da API Xisemele.
 *  
 * @author Carlos Eduardo Coral.
 */
public class ReaderTest {

   /**
    * Testa a leitura de valores formatados.
    */
   @Test
   @SuppressWarnings("serial")
   public void testFormatter() {
      Formatter<Long> longFormatter = new Formatter<Long>() {
         public String format(Long value) {
            return "formattedLong[" + value.toString() + "]";
         }
         public Long parse(String text) {
            return new Long(text.substring(14, text.length() - 1));
         }
         public Class<Long> type() {
            return Long.class;
         }
      };
      
      Formatter<Boolean> booleanFormatter = new Formatter<Boolean>() {
         public String format(Boolean value) {
            return "formattedBoolean[" + value.toString() + "]";
         }
         public Boolean parse(String text) {
            return new Boolean(text.substring(17, text.length() - 1));
         }
         public Class<Boolean> type() {
            return Boolean.class;
         }
      };
      
      Formatter<MyType> myTypeFormatter = new Formatter<MyType>() {
         public String format(MyType value) {
            return "myType[" + value.value1 + ',' + value.value2 + "]";
         }
         public MyType parse(String text) {
            String value1 = text.substring(7, text.indexOf(','));
            String value2 = text.substring(text.indexOf(',') + 1, text.length() - 1);
            return new MyType(value1, value2);
         }
         public Class<MyType> type() {
            return MyType.class;
         }
      };

      String xml = 
         "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
         "<root>\n" +
         "  <a>\n" +
         "     <child>formattedLong[123]</child>\n" +
         "     <child>formattedLong[456]</child>\n" +
         "     <child>formattedLong[789]</child>\n" +
         "  </a>\n" +
         "  <b>formattedBoolean[true]</b>\n" +
         "  <c long=\"formattedLong[456]\"/>\n" +
         "  <d boolean=\"formattedBoolean[true]\"/>\n" +
         "  <e myType=\"myType[abc,def]\">myType[ghi,jkl]</e>" +
         "</root>\n";
  
      Reader reader =
         XisemeleFactory.newXisemele()
            .setFormatter(longFormatter)
            .setFormatter(booleanFormatter)
            .setFormatter(myTypeFormatter)
            .createReader(xml);
      
      assertEquals(Arrays.asList(new Long("123"), new Long("456"), new Long("789")), reader.find("root/a").childrenValue().asLong());
      assertEquals(Boolean.TRUE, reader.find("root/b").value().asBoolean());
      
      assertEquals(new Long("456"), reader.find("root/c").attribute("long").asLong());
      assertEquals(Boolean.TRUE, reader.find("root/d").attribute("boolean").asBoolean());
      
      assertEquals(new MyType("abc", "def"), reader.find("root/e").attribute("myType").asType(MyType.class));
      assertEquals(new MyType("ghi", "jkl"), reader.find("root/e").value().asType(MyType.class));
   }
   
   /**
    * Classe para teste de formatação de valores. 
    */
   class MyType {
      String value1;
      String value2;
      MyType(String value1, String value2) {
         this.value1 = value1;
         this.value2 = value2;
      }
      @Override
      public boolean equals(Object other) {
         if (other instanceof MyType) {
            MyType myType = (MyType) other;
            return value1.equals(myType.value1) && value2.equals(myType.value2);
         }
         return false;
      }
   }
   
   /**
    * Testa a funcionalidade de leitura para o métodos {@link Reader#containsElement(String)} e {@link Reader#find(String)}.
    */
   @Test
   public void testContainsElementAndFind() {
      String xml = 
         "<root>" +
         "  <a>value</a>" +
         "  <b>" +
         "     <c>1</c>" +
         "     <c>2</c>" +
         "     <c>3</c>" +
         "  </b>" +
         "  <d>" +
         "     <e>" +
         "        <f attr=\"50\"/>" +
         "        <g>true</g>" +
         "        <g>false</g>" +
         "        <g>true</g>" +
         "     </e>" +
         "  </d>" +
         "</root>";
      
      Reader reader = XisemeleFactory.newXisemele().createReader(xml);
      
      assertTrue(reader.containsElement("root"));
      assertTrue(reader.containsElement("root/a"));
      assertTrue(reader.containsElement("root/b"));
      assertTrue(reader.containsElement("root/b/c"));
      assertTrue(reader.containsElement("root/d"));
      assertTrue(reader.containsElement("root/d/e"));
      assertTrue(reader.containsElement("root/d/e/f"));
      assertTrue(reader.containsElement("root/d/e/g"));
      
      assertEquals("a", reader.find("root/a").name());
      assertEquals("c", reader.find("root/b/c").name());
      assertEquals("b", reader.find("root/b").name());
      assertEquals("e", reader.find("root/d/e").name());
      assertEquals("f", reader.find("root/d/e/f").name());
      assertEquals("g", reader.find("root/d/e/g").name());
   }
 
   /**
    * Testa os métodos que localiza os filhos de determinados elementos.
    */
   @Test
   public void testChildren() {
      String xml = 
         "<root>" +
         "  <a>" +
         "     <b>value1</b>" +
         "     <b>value2</b>" +
         "     <b>value3</b>" +
         "  </a>" +
         "  <c>" +
         "     <d>value4</d>" +
         "     <e>value5</e>" +
         "  </c>" +
         "</root>";
      
      Reader reader = XisemeleFactory.newXisemele().createReader(xml);
      
      assertEquals("a", reader.root().child("a").name());
      assertEquals("a", reader.root().child(0).name());
      assertEquals("c", reader.root().child("c").name());
      assertEquals("c", reader.root().child(1).name());
      
      List<Element> children = reader.root().child("a").children();
      assertEquals(3, children.size());
      assertEquals("value1", children.get(0).value().asString());
      assertEquals("value2", children.get(1).value().asString());
      assertEquals("value3", children.get(2).value().asString());
      
      children = reader.root().child("a").children("b");
      assertEquals(3, children.size());
      assertEquals("value1", children.get(0).value().asString());
      assertEquals("value2", children.get(1).value().asString());
      assertEquals("value3", children.get(2).value().asString());
      
      assertEquals("value4", reader.root().child("c").child("d").value().asString());
      assertEquals("value5", reader.root().child("c").child("e").value().asString());
   }
}
