package net.sf.xisemele.impl;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Date;

import net.sf.xisemele.api.Element;
import net.sf.xisemele.api.Result;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Casos de teste para a classe {@link WriterImpl}.
 * 
 * @author Carlos Eduardo Coral.
 */
public class WriterImplTest {

   /**
    * Testa os métodos {@link WriterImpl#attribute(String, Object)} e {@link WriterImpl#attribute(String, Date, String)}.
    */
   @Test
   public void testAttributes() {
      Object value = new Object();
      Date date = new Date();
      
      WriterEditor writerEditor = createStrictMock(WriterEditor.class);
      writerEditor.attribute("attributeA", value);
      writerEditor.attribute("attributeB", date, "pattern");
      replay(writerEditor);
      
      WriterImpl writer = new WriterImpl(writerEditor);
      writer.attribute("attributeA", value);
      writer.attribute("attributeB", date, "pattern");
      
      verify(writerEditor);
   }
   
   /**
    * Testa os métodos {@link WriterImpl#element(Element)}, {@link WriterImpl#element(String)}, {@link WriterImpl#element(String, Object)}
    * e {@link WriterImpl#element(String, Date, String)}.
    */
   @Test
   public void testElements() {
      Object value = new Object();
      Date date = new Date();
      
      Node node = createNiceMock(Node.class);
      
      Element element = createStrictMock(Element.class);
      expect(element.node()).andReturn(node);
      replay(element);

      WriterEditor writerEditor = createStrictMock(WriterEditor.class);
      writerEditor.node(node);
      writerEditor.element("elementA");
      writerEditor.element("elementB");
      writerEditor.value(value);
      writerEditor.element("elementC");
      writerEditor.value(date, "pattern");
      replay(writerEditor);
      
      WriterImpl writer = new WriterImpl(writerEditor);
      writer.element(element);
      writer.element("elementA");
      writer.element("elementB", value);
      writer.element("elementC", date, "pattern");
      
      verify(writerEditor);
   }
 
   /**
    * Testa o método {@link WriterImpl#document()}.
    */
   @Test
   public void testDocument() {
      Document document = createNiceMock(Document.class);
      
      WriterEditor writerEditor = createStrictMock(WriterEditor.class);
      expect(writerEditor.document()).andReturn(document);
      replay(writerEditor);
      
      WriterImpl writer = new WriterImpl(writerEditor);
      assertSame(document, writer.document());
      
      verify(writerEditor);
   }

   /**
    * Testa os métodos {@link WriterImpl#within()} e {@link WriterImpl#endWithin()}.
    */
   @Test
   public void testWithin() {
      WriterEditor writerEditor = createStrictMock(WriterEditor.class);
      writerEditor.within();
      writerEditor.endWithin();
      replay(writerEditor);
      
      WriterImpl writer = new WriterImpl(writerEditor);
      writer.within();
      writer.endWithin();
      
      verify(writerEditor);
   }
   
   /**
    * Testa o método {@link WriterImpl#result()}.
    */
   @Test
   public void testResult() {
      Result result = createNiceMock(Result.class);
      
      WriterEditor writerEditor = createStrictMock(WriterEditor.class);
      expect(writerEditor.result()).andReturn(result);
      replay(writerEditor);
      
      WriterImpl editor = new WriterImpl(writerEditor);
      assertSame(result, editor.result());
      
      verify(writerEditor);
   }
}
