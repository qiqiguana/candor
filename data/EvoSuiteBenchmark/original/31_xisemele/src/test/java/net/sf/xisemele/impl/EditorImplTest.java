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
 * Casos de teste para a classe {@link EditorImpl}.
 * 
 * @author Carlos Eduardo Coral.
 */
public class EditorImplTest {

   /**
    * Testa os métodos {@link EditorImpl#attribute(String, Object)} e {@link EditorImpl#attribute(String, Date, String)}.
    */
   @Test
   public void testAttributes() {
      Date date = new Date();
      Object value = new Object();
      
      WriterEditor writerEditor = createStrictMock(WriterEditor.class);
      writerEditor.attribute("elementName", value);
      writerEditor.attribute("elemenName", date, "pattern");
      replay(writerEditor);
      
      EditorImpl editor = new EditorImpl(writerEditor);
      assertSame(editor, editor.attribute("elementName", value));
      assertSame(editor, editor.attribute("elemenName", date, "pattern"));
      
      verify(writerEditor);
   }
   
   /**
    * Testa o método {@link EditorImpl#containsElement(String)}.
    */
   @Test
   public void testContainsElement() {
      WriterEditor writerEditor = createStrictMock(WriterEditor.class);
      expect(writerEditor.containsElement("path")).andReturn(Boolean.TRUE);
      replay(writerEditor);
      
      EditorImpl editor = new EditorImpl(writerEditor);
      assertTrue(editor.containsElement("path"));
      
      verify(writerEditor);
   }
   
   /**
    * Testa o método {@link EditorImpl#defineAsCurrent(String)}.
    */
   @Test
   public void testDefineAsCurrent() {
      WriterEditor writerEditor = createStrictMock(WriterEditor.class);
      writerEditor.defineAsCurrent("path");
      replay(writerEditor);
      
      EditorImpl editor = new EditorImpl(writerEditor);
      assertSame(editor, editor.defineAsCurrent("path"));
      
      verify(writerEditor);
   }
   
   /**
    * Testa os métodos {@link EditorImpl#element(String)}, {@link EditorImpl#element(String, Object)},
    * {@link EditorImpl#element(String, Date, String)} e {@link EditorImpl#element(Element)}.
    */
   @Test
   public void testElements() {
      Object value = new Object();
      Date date = new Date();
      
      Node node = createNiceMock(Node.class);
      
      Element element = createNiceMock(Element.class);
      expect(element.node()).andReturn(node);
      replay(element);
      
      WriterEditor writerEditor = createStrictMock(WriterEditor.class);
      writerEditor.element("elementA");
      writerEditor.element("elementB");
      writerEditor.value(value);
      writerEditor.element("elementC");
      writerEditor.value(date, "pattern");
      writerEditor.node(node);
      replay(writerEditor);
      
      EditorImpl editor = new EditorImpl(writerEditor);
      assertSame(editor, editor.element("elementA"));
      assertSame(editor, editor.element("elementB", value));
      assertSame(editor, editor.element("elementC", date, "pattern"));
      assertSame(editor, editor.element(element));
      
      verify(writerEditor);
   }
   
   /**
    * Testa os métodos {@link EditorImpl#within()} e {@link EditorImpl#endWithin()}.
    */
   @Test
   public void testWithin() {
      WriterEditor writerEditor = createStrictMock(WriterEditor.class);
      writerEditor.within();
      writerEditor.endWithin();
      replay(writerEditor);

      EditorImpl editor = new EditorImpl(writerEditor);
      assertSame(editor, editor.within());
      assertSame(editor, editor.endWithin());
      
      verify(writerEditor);
   }
   
   /**
    * Testa o método {@link EditorImpl#document()}.
    */
   @Test
   public void testDocument() {
      Document document = createNiceMock(Document.class);
      
      WriterEditor writerEditor = createStrictMock(WriterEditor.class);
      expect(writerEditor.document()).andReturn(document);
      replay(writerEditor);

      EditorImpl editor = new EditorImpl(writerEditor);
      assertSame(document, editor.document());
      
      verify(writerEditor);
   }

   /**
    * Testa os métodos {@link EditorImpl#remove()}, {@link EditorImpl#removeAttribute(String)}, {@link EditorImpl#removeAttributes()},
    * {@link EditorImpl#removeChild(String)}, {@link EditorImpl#removeChildren()} e {@link EditorImpl#removeChildren(String)}.
    */
   @Test
   public void testRemove() {
      WriterEditor writerEditor = createStrictMock(WriterEditor.class);
      writerEditor.remove();
      writerEditor.removeAttribute("attributeName");
      writerEditor.removeAttributes();
      writerEditor.removeChild("childName");
      writerEditor.removeChildren();
      writerEditor.removeChildren("childrenName");
      replay(writerEditor);

      EditorImpl editor = new EditorImpl(writerEditor);
      assertSame(editor, editor.remove());
      assertSame(editor, editor.removeAttribute("attributeName"));
      assertSame(editor, editor.removeAttributes());
      assertSame(editor, editor.removeChild("childName"));
      assertSame(editor, editor.removeChildren());
      assertSame(editor, editor.removeChildren("childrenName"));
      
      verify(writerEditor);
   }
   
   /**
    * Testa o método {@link EditorImpl#rename(String)}.
    */
   @Test
   public void testRename() {
      WriterEditor writerEditor = createStrictMock(WriterEditor.class);
      writerEditor.rename("newName");
      replay(writerEditor);
      
      EditorImpl editor = new EditorImpl(writerEditor);
      assertSame(editor, editor.rename("newName"));
      
      verify(writerEditor);
   }
   
   /**
    * Testa os métodos {@link EditorImpl#value(Object)} e {@link EditorImpl#value(Date, String)}.
    */
   @Test
   public void testValue() {
      Object value = new Object();
      Date date = new Date();
      
      WriterEditor writerEditor = createStrictMock(WriterEditor.class);
      writerEditor.value(value);
      writerEditor.value(date, "pattern");
      replay(writerEditor);

      EditorImpl editor = new EditorImpl(writerEditor);
      assertSame(editor, editor.value(value));
      assertSame(editor, editor.value(date, "pattern"));
      
      verify(writerEditor);
   }
 
   /**
    * Testa o método {@link EditorImpl#result()}.
    */
   @Test
   public void testResult() {
      Result result = createNiceMock(Result.class);
      
      WriterEditor writerEditor = createStrictMock(WriterEditor.class);
      expect(writerEditor.result()).andReturn(result);
      replay(writerEditor);
      
      EditorImpl editor = new EditorImpl(writerEditor);
      assertSame(result, editor.result());
      
      verify(writerEditor);
   }
}
