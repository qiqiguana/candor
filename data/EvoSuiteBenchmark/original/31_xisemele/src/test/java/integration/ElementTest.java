package integration;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import net.sf.xisemele.api.Element;
import net.sf.xisemele.api.Reader;
import net.sf.xisemele.impl.XisemeleFactory;

/**
 * Casos de teste de integração da instância de {@link Element} na leitura de determinado documento XML.
 * 
 * @author Carlos Eduardo Coral.
 */
public class ElementTest {
   
   /**
    * Testa o método {@link Element#name()};
    */
   @Test
   public void testName() {
      String xml = 
         "<root>" +
            "<a/>" +
         "</root>";
      
      Reader reader = XisemeleFactory.newXisemele().createReader(xml);
      
      assertEquals("root", reader.root().name());
      assertEquals("a", reader.find("root/a").name());
   }
   
   /**
    * Testa o método {@link Element#value()}.
    */
   @Test
   public void testValue() {
      String xml =
         "<root>" +
            "<a>123</a>" +
            "<b>true</b>" +
            "<c>value</c>" +
         "</root>";
      
      Reader reader = XisemeleFactory.newXisemele().createReader(xml);
      
      assertEquals(new Integer("123"), reader.find("root/a").value().asInteger());
      assertEquals(Boolean.TRUE, reader.find("root/b").value().asBoolean());
      assertEquals("value", reader.find("root/c").value().asString());
   }
   
   /**
    * Testa os métodos de leitura de atributos.
    */
   @Test
   public void testAttributes() {
      String xml =
         "<root>" +
            "<a attr=\"value\"/>" +
            "<b attr1=\"value1\" attr2=\"value2\"/>" +
         "</root>";
      
      Reader reader = XisemeleFactory.newXisemele().createReader(xml);
      
      assertFalse(reader.root().containsAttributes());
      
      assertTrue(reader.find("root/a").containsAttributes());
      assertTrue(reader.find("root/a").containsAttribute("attr"));
      assertFalse(reader.find("root/a").containsAttribute("other"));
      assertTrue(reader.find("root/b").containsAttributes());
      assertTrue(reader.find("root/b").containsAttribute("attr1"));
      assertTrue(reader.find("root/b").containsAttribute("attr2"));
      
      assertEquals("value", reader.find("root/a").attribute("attr").asString());
      assertEquals("value1", reader.find("root/b").attribute("attr1").asString());
      assertEquals("value2", reader.find("root/b").attribute("attr2").asString());
   }
   
   /**
    * Testa os métodos de leitura de elementos filhos.
    */
   @Test
   public void testChildren() {
      String xml =
         "<root>" +
            "<a>" +
               "<b/>" +
               "<c>value1</c>" +
               "<c>value2</c>" +
            "</a>" +
         "</root>";
      
      Reader reader = XisemeleFactory.newXisemele().createReader(xml);
      
      assertTrue(reader.find("root/a").containsChildren());
      assertTrue(reader.find("root/a").containsChild("b"));
      assertTrue(reader.find("root/a").containsChild("c"));
      assertFalse(reader.find("root/a/b").containsChildren());
      
      assertEquals(3, reader.find("root/a").numberOfChildren());
      assertEquals(2, reader.find("root/a").numberOfChildren("c"));
      
      assertEquals("b", reader.find("root/a").child(0).name());
      assertEquals("c", reader.find("root/a").child(1).name());
      assertEquals("c", reader.find("root/a").child(2).name());
      
      assertEquals("b", reader.find("root/a").child("b").name());
      assertEquals("value1", reader.find("root/a").child("c").value().asString());
      
      List<Element> children = reader.find("root/a").children();
      assertEquals("b", children.get(0).name());
      assertEquals("c", children.get(1).name());
      assertEquals("c", children.get(2).name());
      
      children = reader.find("root/a").children("c");
      assertEquals("value1", children.get(0).value().asString());
      assertEquals("value2", children.get(1).value().asString());
      
      assertEquals(Arrays.asList("", "value1", "value2"), reader.find("root/a").childrenValue().asString());
      assertEquals(Arrays.asList("value1", "value2"), reader.find("root/a").childrenValue("c").asString());
   }
   
   /**
    * Testa o método {@link Element#path()}.
    */
   @Test
   public void testPath() {
      String xml = 
         "<root>" +
            "<a/>" +
            "<b>" +
               "<c/>" +
            "</b>" +
            "<d>" +
               "<e>" +
                  "<f>" +
                     "<g>" +
                        "<h/>" +
                     "</g>" +
                  "</f>" +
               "</e>" +
            "</d>" +
         "</root>";
      
      Reader reader = XisemeleFactory.newXisemele().createReader(xml);
      
      assertEquals("root/a", reader.find("root/a").path());
      assertEquals("root/b", reader.find("root/b").path());
      assertEquals("root/b/c", reader.find("root/b/c").path());
      assertEquals("root/d", reader.find("root/d").path());
      assertEquals("root/d/e", reader.find("root/d/e").path());
      assertEquals("root/d/e/f", reader.find("root/d/e/f").path());
      assertEquals("root/d/e/f/g", reader.find("root/d/e/f/g").path());
      assertEquals("root/d/e/f/g/h", reader.find("root/d/e/f/g/h").path());
   }
}
