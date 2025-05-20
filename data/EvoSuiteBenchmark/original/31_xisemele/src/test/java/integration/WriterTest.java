package integration;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.xisemele.api.Formatter;
import net.sf.xisemele.api.Writer;
import net.sf.xisemele.impl.XisemeleFactory;

import org.junit.Test;

/**
 * Casos de teste de integração da funcionalidade de escrita de XML com a API Xisemele.
 *  
 * @author Carlos Eduardo Coral.
 */
public class WriterTest {

   /**
    * Testa a transformação do documento XML com identação.
    */
   @Test
   public void testIdent() {
      String identedXml = 
         "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
         "<root>\n" +
         "   <a/>\n" +
         "   <b>\n" +
         "      <c/>\n" +
         "   </b>\n" +
         "   <d>\n" +
         "      <e/>\n" +
         "      <f>\n" +
         "         <g/>\n" +
         "      </f>\n" +
         "      <h/>\n" +
         "   </d>\n" +
         "</root>\n";
      
      String oneLineXml = 
         "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
         "<root>" +
            "<a/>" +
            "<b>" +
               "<c/>" +
            "</b>" +
            "<d>" +
               "<e/>" +
               "<f>" +
                  "<g/>" +
               "</f>" +
               "<h/>" +
            "</d>" +
         "</root>";
      
      Writer writer = 
         XisemeleFactory.newXisemele().createWriter("root").within()
            .element("a")
            .element("b").within()
               .element("c")
            .endWithin()
            .element("d").within()
               .element("e")
               .element("f").within()
                  .element("g")
               .endWithin()
               .element("h")
            .endWithin()
         .endWithin();
      
      writer.result()
         .ident(true)
         .identNumber(3)
         .toXML();
      
      assertEquals(identedXml, writer.result().toXML());
      
      writer.result().ident(false);
      
      assertEquals(oneLineXml, writer.result().toXML());
   }
   
   /**
    * Testa a formatação de valores para tipos específicos.
    */
   @Test
   @SuppressWarnings("serial")
   public void testFormatter() {
      Formatter<Long> longFormatter = new Formatter<Long>() {
         public String format(Long value) {
            return "formattedLong[" + value.toString() + "]";
         }
         public Long parse(String text) {
            return new Long(text.substring(17, text.length() - 1));
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

      String expected = 
         "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
         "<root>\n" +
         "  <a>formattedLong[123]</a>\n" +
         "  <b>formattedBoolean[true]</b>\n" +
         "  <c long=\"formattedLong[456]\"/>\n" +
         "  <d boolean=\"formattedBoolean[true]\"/>\n" +
         "</root>\n";
      
      Writer writer = 
         XisemeleFactory.newXisemele()
            .setFormatter(longFormatter)
            .setFormatter(booleanFormatter)
            .createWriter("root").within()
               .element("a", new Long("123"))
               .element("b", Boolean.TRUE)
               .element("c").attribute("long", new Long("456"))
               .element("d").attribute("boolean", Boolean.TRUE)
            .endWithin();
            
      writer.result()
         .ident(true)
         .identNumber(2);
      
      assertEquals(expected, writer.result().toXML());
   }
   
   /**
    * Testa a funcionalidade de escrita de XML e de salvar o documento em arquivo.
    */
   @Test
   public void testToFile() throws Exception {
      String expected = 
         "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
         "<root>" +
            "<a/>" +
            "<b>" +
               "<c attr1=\"value1\" attr2=\"value2\"/>" +
            "</b>" +
            "<d>02/07/2009</d>" +
            "<e>" +
               "<f>" +
                  "<g>value</g>" +
               "</f>" +
            "</e>" +
         "</root>";
      
      Date date = new SimpleDateFormat("dd/MM/yyyy").parse("02/07/2009");
      
      Writer writer = 
         XisemeleFactory.newXisemele().createWriter("root")
         .within()
            .element("a")
            .element("b").within()
               .element("c").attribute("attr1", "value1").attribute("attr2", "value2")
            .endWithin()
            .element("d", date, "dd/MM/yyyy")
            .element("e").within()
               .element("f").within()
                  .element("g", "value")
               .endWithin()
            .endWithin()
         .endWithin();
      
      assertEquals(expected, writer.result().toXML());
      
      File file = File.createTempFile("xisemele-test", ".xml");
      writer.result().toFile(file);
      assertEquals(expected, new BufferedReader(new FileReader(file)).readLine());
      file.delete();
   }
}
