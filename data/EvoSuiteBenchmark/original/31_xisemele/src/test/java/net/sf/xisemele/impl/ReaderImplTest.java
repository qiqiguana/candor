package net.sf.xisemele.impl;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import net.sf.xisemele.api.Element;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Casos de teste para a classe {@link ReaderImpl}.
 * 
 * @author Carlos Eduardo Coral.
 */
public class ReaderImplTest {

   /**
    * Testa o método {@link ReaderImpl#containsElement(String)}.
    */
   @Test
   public void testContainsElement() {
      Document document = createNiceMock(Document.class);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.containsElement(document, "path")).andReturn(Boolean.TRUE);
      replay(operations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createOperations()).andReturn(operations);
      replay(factory);
      
      ReaderImpl reader = new ReaderImpl(factory, document);
      
      assertTrue(reader.containsElement("path"));
      
      verify(operations, factory);
   }
   
   /**
    * Testa o método {@link ReaderImpl#find(String)}.
    */
   @Test
   public void testFind() {
      Document document = createNiceMock(Document.class);
      
      Node node = createNiceMock(Node.class);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.find(document, "path")).andReturn(node);
      replay(operations);
      
      Element element = createNiceMock(Element.class);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createElement(node)).andReturn(element);
      replay(factory);
      
      ReaderImpl reader = new ReaderImpl(factory, document);
      
      assertSame(element, reader.find("path"));
      
      verify(operations, factory);
   }
   
   /**
    * Testa o método {@link ReaderImpl#root()}.
    */
   @Test
   public void testRoot() {
      org.w3c.dom.Element root = createNiceMock(org.w3c.dom.Element.class);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(root);
      replay(document);
      
      Element element = createNiceMock(Element.class);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createOperations()).andReturn(null);
      expect(factory.createElement(root)).andReturn(element);
      replay(factory);
      
      ReaderImpl reader = new ReaderImpl(factory, document);
      
      assertSame(element, reader.root());
      
      verify(document, factory);
   }
   
   /**
    * Testa o método {@link ReaderImpl#version()}.
    */
   @Test
   public void testVersion() {
      Document document = createStrictMock(Document.class);
      expect(document.getXmlVersion()).andReturn("version");
      replay(document);
      
      ReaderImpl reader = new ReaderImpl(createNiceMock(Factory.class), document);
      
      assertEquals("version", reader.version());
      
      verify(document);
   }
   
   /**
    * Testa o método {@link ReaderImpl#encoding()}.
    */
   @Test
   public void testEncoding() {
      Document document = createStrictMock(Document.class);
      expect(document.getXmlEncoding()).andReturn("encoding");
      replay(document);
      
      ReaderImpl reader = new ReaderImpl(createNiceMock(Factory.class), document);
      
      assertEquals("encoding", reader.encoding());
      
      verify(document);
   }
}
