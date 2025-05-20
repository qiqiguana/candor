package net.sf.xisemele.impl;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;

import net.sf.xisemele.api.Formatter;
import net.sf.xisemele.api.Result;
import net.sf.xisemele.exception.AttributeNotPermittedException;
import net.sf.xisemele.exception.NotWithinContextException;
import net.sf.xisemele.exception.RemoveRootNotPermittedException;
import net.sf.xisemele.exception.RootDuplicateException;
import net.sf.xisemele.exception.ValueNotPermittedException;
import net.sf.xisemele.exception.WithinContextDuplicateException;
import net.sf.xisemele.exception.WithinContextNotPermittedException;

import org.junit.Test;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Casos de testes para a classe {@link WriterEditorImpl}.
 * 
 * @author Carlos Eduardo Coral.
 */
public class WriterEditorImplTest {

   /**
    * Testa o método {@link WriterEditorImpl#document()}.
    */
   @Test
   public void testDocument() {
      Element element = createStrictMock(Element.class);
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      expect(element.getOwnerDocument()).andReturn(document);
      replay(element, document);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(null);
      expect(factory.createOperations()).andReturn(null);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);
      
      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      assertSame(document, writerEditor.document());
      
      verify(element, document, factory);
   }
   
   /**
    * Testa o método {@link WriterEditorImpl#result()}.
    */
   @Test
   public void testResult() {
      Element element = createNiceMock(Element.class);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);
      
      Result result = createNiceMock(Result.class);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(null);
      expect(factory.createOperations()).andReturn(null);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(result);
      replay(factory);
      
      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      assertSame(result, writerEditor.result());
      
      verify(document, factory);
   }
   
   /**
    * Testa o método {@link WriterEditorImpl#getCurrent()}.
    */
   @Test
   public void testGetCurrent() {
      Element element = createNiceMock(Element.class);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(null);
      expect(factory.createOperations()).andReturn(null);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);
      
      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      assertSame(element, writerEditor.getCurrent());
      
      verify(document, factory);
   }
   
   /**
    * Testa o método {@link WriterEditorImpl#containsElement(String)}.
    */
   @Test
   public void testContainsElement() {
      Element element = createStrictMock(Element.class);
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      expect(element.getOwnerDocument()).andReturn(document);
      replay(element, document);

      Operations operations = createStrictMock(Operations.class);
      expect(operations.containsElement(document, "path")).andReturn(Boolean.TRUE);
      replay(operations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(null);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);
      
      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      assertTrue(writerEditor.containsElement("path"));
      
      verify(element, document, operations, factory);
   }
   
   /**
    * Testa o método {@link WriterEditorImpl#within()}.
    */
   @Test
   public void testWithin() {
      Element element = createNiceMock(Element.class);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);
      
      WithinContext withinContext = createStrictMock(WithinContext.class);
      expect(withinContext.enabled()).andReturn(Boolean.FALSE);
      withinContext.start();
      replay(withinContext);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.containsValue(element)).andReturn(Boolean.FALSE);
      replay(operations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(withinContext);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);
      
      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.within();
      
      verify(document, withinContext, operations, factory);
   }
   
   /**
    * Testa se dispara a exceção {@link WithinContextDuplicateException} ao chamar o método {@link WriterEditorImpl#within()}.
    */
   @Test(expected = WithinContextDuplicateException.class)
   public void testWithinWithWithinContextDuplicateException() {
      Element element = createNiceMock(Element.class);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);
      
      WithinContext withinContext = createStrictMock(WithinContext.class);
      expect(withinContext.enabled()).andReturn(Boolean.TRUE);
      replay(withinContext);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(withinContext);
      expect(factory.createOperations()).andReturn(null);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.within();
   }
 
   /**
    * Testa se dispara a exceção {@link WithinContextNotPermittedException} ao chamar o método {@link WriterEditorImpl#within()}.
    */
   @Test(expected = WithinContextNotPermittedException.class)
   public void testWithinWithWithinContextNotPermittedException() {
      Element element = createStrictMock(Element.class);
      expect(element.getNodeName()).andReturn("nodeName");
      replay(element);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);
      
      WithinContext withinContext = createStrictMock(WithinContext.class);
      expect(withinContext.enabled()).andReturn(Boolean.FALSE);
      replay(withinContext);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.containsValue(element)).andReturn(Boolean.TRUE);
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(withinContext);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.within();
   }
 
   /**
    * Testa o método {@link WriterEditorImpl#endWithin()}.
    */
   @Test
   public void testEndWithin() {
      Element element = createNiceMock(Element.class);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);
      
      WithinContext withinContext = createStrictMock(WithinContext.class);
      expect(withinContext.enabled()).andReturn(Boolean.TRUE);
      withinContext.stop();
      replay(withinContext);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(withinContext);
      expect(factory.createOperations()).andReturn(null);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.endWithin();
      
      verify(document, withinContext, factory);
   }
   
   /**
    * Testa se dispara a exceção {@link NotWithinContextException} ao chamar o método {@link WriterEditorImpl#endWithin()}.
    */
   @Test(expected = NotWithinContextException.class)
   public void testEndWithinWithNotWithinContextException() {
      Element element = createNiceMock(Element.class);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);
      
      WithinContext withinContext = createStrictMock(WithinContext.class);
      expect(withinContext.enabled()).andReturn(Boolean.FALSE);
      replay(withinContext);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.isRootElement(element)).andReturn(Boolean.TRUE);
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(withinContext);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.endWithin();
   }
   
   /**
    * Testa o método {@link WriterEditorImpl#endWithin()} para o caso em que o elemento pai do element corrente passa ser o
    * elemento corrente.
    */
   @Test
   public void testEndWithinForParent() {
      Element parent = createNiceMock(Element.class);
      
      Element element = createStrictMock(Element.class);
      expect(element.getParentNode()).andReturn(parent);
      replay(element);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);
      
      WithinContext withinContext = createStrictMock(WithinContext.class);
      expect(withinContext.enabled()).andReturn(Boolean.FALSE);
      replay(withinContext);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.isRootElement(element)).andReturn(Boolean.FALSE);
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(withinContext);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.endWithin();
      assertSame(parent, writerEditor.getCurrent());
      
      verify(element, document, withinContext, operations, factory);
   }
   
   /**
    * Testa o método {@link WriterEditorImpl#element(String)}.
    */
   @Test
   public void testElement() {
      Element newElement = createNiceMock(Element.class);
      
      Element parent = createStrictMock(Element.class);
      expect(parent.appendChild(newElement)).andReturn(null);
      replay(parent);
      
      Element element = createStrictMock(Element.class);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      expect(document.createElement("newElement")).andReturn(newElement);
      replay(document);
      
      expect(element.getOwnerDocument()).andReturn(document);
      expect(element.getParentNode()).andReturn(parent);
      replay(element);
      
      WithinContext withinContext = createStrictMock(WithinContext.class);
      expect(withinContext.enabled()).andReturn(Boolean.FALSE);
      replay(withinContext);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.isRootElement(element)).andReturn(Boolean.FALSE);
      replay(operations);

      Validations validations = createStrictMock(Validations.class);
      validations.assertValidName("newElement");
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(withinContext);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.element("newElement");
      assertSame(newElement, writerEditor.getCurrent());
      
      verify(parent, element, document, withinContext, operations, validations, factory);
   }
   
   /**
    * Testa o método {@link WriterEditorImpl#element(String)} no caso em que o contexto <i>within</i> está habilitado.
    */
   @Test
   public void testElementWithWithinContextEnabled() {
      Element newElement = createNiceMock(Element.class);
      
      Element element = createStrictMock(Element.class);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      expect(document.createElement("newElement")).andReturn(newElement);
      replay(document);
      
      expect(element.getOwnerDocument()).andReturn(document);
      expect(element.appendChild(newElement)).andReturn(null);
      replay(element);
      
      WithinContext withinContext = createStrictMock(WithinContext.class);
      expect(withinContext.enabled()).andReturn(Boolean.TRUE);
      withinContext.stop();
      replay(withinContext);

      Validations validations = createStrictMock(Validations.class);
      validations.assertValidName("newElement");
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(withinContext);
      expect(factory.createOperations()).andReturn(null);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.element("newElement");
      assertSame(newElement, writerEditor.getCurrent());
      
      verify(element, document, withinContext, validations, factory);
   }
   
   /**
    * Testa se dispara a exceção {@link RootDuplicateException} ao chamar o método {@link WriterEditorImpl#element(String)}.
    */
   @Test(expected = RootDuplicateException.class)
   public void testElementWithRootDuplicateException() {
      Element newElement = createStrictMock(Element.class);
      expect(newElement.getNodeName()).andReturn("newElement");
      replay(newElement);
      
      Element element = createStrictMock(Element.class);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      expect(document.createElement("newElement")).andReturn(newElement);
      replay(document);
      
      expect(element.getOwnerDocument()).andReturn(document);
      expect(element.getNodeName()).andReturn("element");
      replay(element);
      
      WithinContext withinContext = createStrictMock(WithinContext.class);
      expect(withinContext.enabled()).andReturn(Boolean.FALSE);
      replay(withinContext);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.isRootElement(element)).andReturn(Boolean.TRUE);
      replay(operations);

      Validations validations = createStrictMock(Validations.class);
      validations.assertValidName("newElement");
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(withinContext);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.element("newElement");
   }
   
   /**
    * Testa o método {@link WriterEditorImpl#node(org.w3c.dom.Node)}.
    */
   @Test
   public void testNode() {
      Element newElement = createNiceMock(Element.class);
      
      Element parent = createStrictMock(Element.class);
      expect(parent.appendChild(newElement)).andReturn(null);
      replay(parent);
      
      Element element = createStrictMock(Element.class);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      expect(document.importNode(newElement, true)).andReturn(newElement);
      replay(document);
      
      expect(element.getOwnerDocument()).andReturn(document);
      expect(element.getParentNode()).andReturn(parent);
      replay(element);
      
      WithinContext withinContext = createStrictMock(WithinContext.class);
      expect(withinContext.enabled()).andReturn(Boolean.FALSE);
      replay(withinContext);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.isRootElement(element)).andReturn(Boolean.FALSE);
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(withinContext);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.node(newElement);
      assertSame(newElement, writerEditor.getCurrent());
      
      verify(parent, element, document, withinContext, operations, factory);
   }
   
   /**
    * Testa o método {@link WriterEditorImpl#node(org.w3c.dom.Node)} no caso em que o contexto <i>within</i> está habilitado.
    */
   @Test
   public void testNodeWithWithinContextEnabled() {
      Element newElement = createNiceMock(Element.class);
      
      Element element = createStrictMock(Element.class);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      expect(document.importNode(newElement, true)).andReturn(newElement);
      replay(document);
      
      expect(element.getOwnerDocument()).andReturn(document);
      expect(element.appendChild(newElement)).andReturn(null);
      replay(element);
      
      WithinContext withinContext = createStrictMock(WithinContext.class);
      expect(withinContext.enabled()).andReturn(Boolean.TRUE);
      withinContext.stop();
      replay(withinContext);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(withinContext);
      expect(factory.createOperations()).andReturn(null);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.node(newElement);
      assertSame(newElement, writerEditor.getCurrent());
      
      verify(element, document, withinContext, factory);
   }
   
   /**
    * Testa se dispara a exceção {@link RootDuplicateException} ao chamar o método {@link WriterEditorImpl#node(org.w3c.dom.Node)}.
    */
   @Test(expected = RootDuplicateException.class)
   public void testNodeWithRootDuplicateException() {
      Element newElement = createStrictMock(Element.class);
      expect(newElement.getNodeName()).andReturn("newElement");
      replay(newElement);
      
      Element element = createStrictMock(Element.class);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      expect(document.importNode(newElement, true)).andReturn(newElement);
      expect(document.createElement("newElement")).andReturn(newElement);
      replay(document);
      
      expect(element.getOwnerDocument()).andReturn(document);
      expect(element.getNodeName()).andReturn("element");
      replay(element);
      
      WithinContext withinContext = createStrictMock(WithinContext.class);
      expect(withinContext.enabled()).andReturn(Boolean.FALSE);
      replay(withinContext);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.isRootElement(element)).andReturn(Boolean.TRUE);
      replay(operations);

      Validations validations = createStrictMock(Validations.class);
      validations.assertValidName("newElement");
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(withinContext);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.node(newElement);
   }  
   
   /**
    * Testa o método {@link WriterEditorImpl#value(Object)}.
    */
   @Test
   @SuppressWarnings("unchecked")
   public void testValue() {
      Element element = createStrictMock(Element.class);
      element.setTextContent("formatted");
      replay(element);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.containsChildren(element)).andReturn(Boolean.FALSE);
      replay(operations);
      
      Formatter formatter = createStrictMock(Formatter.class);
      expect(formatter.format("value")).andReturn("formatted");
      replay(formatter);
      
      FormatterProvider formatterProvider = createStrictMock(FormatterProvider.class);
      expect(formatterProvider.getFormatter(String.class)).andReturn(formatter);
      replay(formatterProvider);

      WithinContext withinContext = createStrictMock(WithinContext.class);
      expect(withinContext.enabled()).andReturn(Boolean.FALSE);
      replay(withinContext);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(withinContext);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, formatterProvider, document);
      writerEditor.value("value");
      
      verify(element, document, operations, formatter, formatterProvider, factory);
   }
   
   /**
    * Testa se dispara a exceção {@link ValueNotPermittedException} ao chamar o método {@link WriterEditorImpl#value(Object)} para
    * um elemento que já contenha elementos filhos.
    */
   @Test(expected = ValueNotPermittedException.class)
   public void testValueWithValueNotPermittedExceptionCase1() {
      Element element = createStrictMock(Element.class);
      expect(element.getNodeName()).andReturn("element");
      replay(element);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.containsChildren(element)).andReturn(Boolean.TRUE);
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(null);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.value("value");
   }
   
   /**
    * Testa se dispara a exceção {@link ValueNotPermittedException} ao chamar o método {@link WriterEditorImpl#value(Object)} para
    * um contexto <i>within</i> iniciado.
    */
   @Test(expected = ValueNotPermittedException.class)
   public void testValueWithValueNotPermittedExceptionCase2() {
      Element element = createStrictMock(Element.class);
      expect(element.getNodeName()).andReturn("element");
      replay(element);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);

      WithinContext withinContext = createStrictMock(WithinContext.class);
      expect(withinContext.enabled()).andReturn(Boolean.TRUE);
      replay(withinContext);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(withinContext);
      expect(factory.createOperations()).andReturn(createNiceMock(Operations.class));
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.value("value");
   }
   
   /**
    * Testa o método {@link WriterEditorImpl#value(Object)} no caso de especificar o valor <code>null</code> por parâmetro.
    */
   @Test
   public void testValueNull() {
      Element element = createNiceMock(Element.class);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.containsChildren(element)).andReturn(Boolean.FALSE);
      replay(operations);

      WithinContext withinContext = createStrictMock(WithinContext.class);
      expect(withinContext.enabled()).andReturn(Boolean.FALSE);
      replay(withinContext);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(withinContext);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.value(null);
      
      verify(document, operations, factory);
   }
   
   /**
    * Testa o método {@link WriterEditorImpl#value(Date, String)}.
    */
   @Test
   @SuppressWarnings("unchecked")
   public void testValueForDate() {
      Element element = createStrictMock(Element.class);
      element.setTextContent("formatted");
      replay(element);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.containsChildren(element)).andReturn(Boolean.FALSE);
      replay(operations);
      
      Date date = new Date();
      
      Formatter formatter = createStrictMock(Formatter.class);
      expect(formatter.format(date)).andReturn("formatted");
      replay(formatter);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(null);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      expect(factory.createDateFormatter("pattern")).andReturn(formatter);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.value(date, "pattern");
      
      verify(element, document, operations, formatter, factory);
   }
 
   /**
    * Testa se dispara a exceção {@link ValueNotPermittedException} ao chamar o método {@link WriterEditorImpl#value(Date, String)}.
    */
   @Test(expected = ValueNotPermittedException.class)
   public void testValueForDateWithValueNotPermittedException() {
      Element element = createStrictMock(Element.class);
      expect(element.getNodeName()).andReturn("element");
      replay(element);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.containsChildren(element)).andReturn(Boolean.TRUE);
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(null);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.value(new Date(), "pattern");
   }
   
   /**
    * Testa o método {@link WriterEditorImpl#attribute(String, Object)}.
    */
   @Test
   @SuppressWarnings("unchecked")
   public void testAttribute() {
      Attr attribute = createStrictMock(Attr.class);
      attribute.setNodeValue("formattedValue");
      replay(attribute);
      
      NamedNodeMap namedNodeMap = createStrictMock(NamedNodeMap.class);
      expect(namedNodeMap.setNamedItem(attribute)).andReturn(null);
      replay(namedNodeMap);
      
      Element element = createStrictMock(Element.class);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      expect(document.createAttribute("attribute")).andReturn(attribute);
      replay(document);
      
      expect(element.getOwnerDocument()).andReturn(document);
      expect(element.getAttributes()).andReturn(namedNodeMap);
      replay(element);
      
      Formatter formatter = createStrictMock(Formatter.class);
      expect(formatter.format("value")).andReturn("formattedValue");
      replay(formatter);
      
      FormatterProvider provider = createStrictMock(FormatterProvider.class);
      expect(provider.getFormatter(String.class)).andReturn(formatter);
      replay(provider);
      
      WithinContext withinContext = createStrictMock(WithinContext.class);
      expect(withinContext.enabled()).andReturn(Boolean.FALSE);
      replay(withinContext);

      Validations validations = createStrictMock(Validations.class);
      validations.assertValidName("attribute");
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(withinContext);
      expect(factory.createOperations()).andReturn(null);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, provider, document);
      writerEditor.attribute("attribute", "value");
      
      verify(attribute, namedNodeMap, element, document, formatter, provider, withinContext, validations, factory);
   }
   
   /**
    * Testa se dispara a exceção {@link AttributeNotPermittedException} ao chamar o método 
    * {@link WriterEditorImpl#attribute(String, Object)};
    */
   @Test(expected = AttributeNotPermittedException.class)
   public void testAttributeWithAttributeNotPermittedException() {
      Element element = createNiceMock(Element.class);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);
      
      WithinContext withinContext = createStrictMock(WithinContext.class);
      expect(withinContext.enabled()).andReturn(Boolean.TRUE);
      replay(withinContext);

      Validations validations = createStrictMock(Validations.class);
      validations.assertValidName("attribute");
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(withinContext);
      expect(factory.createOperations()).andReturn(null);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.attribute("attribute", "value");
   }
   
   /**
    * Testa o método {@link WriterEditorImpl#attribute(String, Date, String)}.
    */
   @Test
   @SuppressWarnings("unchecked")
   public void testAttributeForDate() {
      Attr attribute = createStrictMock(Attr.class);
      attribute.setNodeValue("formattedValue");
      replay(attribute);
      
      NamedNodeMap namedNodeMap = createStrictMock(NamedNodeMap.class);
      expect(namedNodeMap.setNamedItem(attribute)).andReturn(null);
      replay(namedNodeMap);
      
      Element element = createStrictMock(Element.class);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      expect(document.createAttribute("attribute")).andReturn(attribute);
      replay(document);
      
      expect(element.getOwnerDocument()).andReturn(document);
      expect(element.getAttributes()).andReturn(namedNodeMap);
      replay(element);

      Date date = new Date();
      
      Formatter formatter = createStrictMock(Formatter.class);
      expect(formatter.format(date)).andReturn("formattedValue");
      replay(formatter);
      
      WithinContext withinContext = createStrictMock(WithinContext.class);
      expect(withinContext.enabled()).andReturn(Boolean.FALSE);
      replay(withinContext);

      Validations validations = createStrictMock(Validations.class);
      validations.assertValidName("attribute");
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(withinContext);
      expect(factory.createOperations()).andReturn(null);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createResult(document)).andReturn(null);
      expect(factory.createDateFormatter("pattern")).andReturn(formatter);
      replay(factory);
      
      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.attribute("attribute", date, "pattern");
      
      verify(attribute, namedNodeMap, document, element, formatter, validations, factory, withinContext);
   }
   
   /**
    * Testa se dispara a exceção {@link AttributeNotPermittedException} ao chamar o método 
    * {@link WriterEditorImpl#attribute(String, Date, String)}.
    */
   @Test(expected = AttributeNotPermittedException.class)
   public void testeAttributeForDateWithAttributeNotPermittedException() {
      Element element = createNiceMock(Element.class);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);
      
      WithinContext withinContext = createStrictMock(WithinContext.class);
      expect(withinContext.enabled()).andReturn(Boolean.TRUE);
      replay(withinContext);

      Validations validations = createStrictMock(Validations.class);
      validations.assertValidName("attribute");
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(withinContext);
      expect(factory.createOperations()).andReturn(null);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.attribute("attribute", new Date(), "pattern");
   }
   
   /**
    * Testa o método {@link WriterEditorImpl#remove()}.
    */
   @Test
   public void testRemove() {
      Element parent = createStrictMock(Element.class);
      
      Element element = createStrictMock(Element.class);
      expect(element.getParentNode()).andReturn(parent);
      replay(element);
      
      expect(parent.removeChild(element)).andReturn(null);
      replay(parent);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.isRootElement(element)).andReturn(Boolean.FALSE);
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(null);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.remove();
      assertSame(parent, writerEditor.getCurrent());
    
      verify(parent, element, document, operations, factory);
   }
   
   /**
    * Testa se dispara a exceção {@link RemoveRootNotPermittedException} ao chamar o método {@link WriterEditorImpl#remove()}.
    */
   @Test(expected = RemoveRootNotPermittedException.class)
   public void testRemoveWithRemoveRootNotPermittedException() {
      Element element = createStrictMock(Element.class);
      expect(element.getNodeName()).andReturn("element");
      replay(element);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.isRootElement(element)).andReturn(Boolean.TRUE);
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(null);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.remove();
   }
   
   /**
    * Testa o método {@link WriterEditorImpl#removeChild(String)}.
    */
   @Test
   public void testRemoveChildForName() {
      Element child = createNiceMock(Element.class);
      
      Element element = createStrictMock(Element.class);
      expect(element.removeChild(child)).andReturn(null);
      replay(element);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.child(element, "childName")).andReturn(child);
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(null);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.removeChild("childName");
      
      verify(element, document, operations, factory);
   }
   
   /**
    * Testa o método {@link WriterEditorImpl#removeChildren(String)}.
    */
   @Test
   public void testRemoveChildrenForName() {
      Element child1 = createNiceMock(Element.class);
      Element child2 = createNiceMock(Element.class);
      Element child3 = createNiceMock(Element.class);
      
      Element element = createStrictMock(Element.class);
      expect(element.removeChild(child1)).andReturn(null);
      expect(element.removeChild(child2)).andReturn(null);
      expect(element.removeChild(child3)).andReturn(null);
      replay(element);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.children(element, "childName")).andReturn(Arrays.<Node>asList(child1, child2, child3));
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(null);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.removeChildren("childName");
      
      verify(element, document, operations, factory);
   }
   
   /**
    * Testa o método {@link WriterEditorImpl#removeChildren()}.
    */
   @Test
   public void testRemoveChildren() {
      Element child1 = createNiceMock(Element.class);
      Element child2 = createNiceMock(Element.class);
      Element child3 = createNiceMock(Element.class);
      
      Element element = createStrictMock(Element.class);
      expect(element.removeChild(child1)).andReturn(null);
      expect(element.removeChild(child2)).andReturn(null);
      expect(element.removeChild(child3)).andReturn(null);
      replay(element);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.children(element)).andReturn(Arrays.<Node>asList(child1, child2, child3));
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(null);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.removeChildren();
      
      verify(element, document, operations, factory);
   }
   
   /**
    * Testa o método {@link WriterEditorImpl#removeAttribute(String)}.
    */
   @Test
   public void testRemoveAttribute() {
      NamedNodeMap namedNodeMap = createStrictMock(NamedNodeMap.class);
      expect(namedNodeMap.removeNamedItem("attribute")).andReturn(null);
      replay(namedNodeMap);
      
      Element element = createStrictMock(Element.class);
      expect(element.getAttributes()).andReturn(namedNodeMap);
      replay(element);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(null);
      expect(factory.createOperations()).andReturn(null);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.removeAttribute("attribute");
      
      verify(namedNodeMap, element, document, factory);
   }
   
   /**
    * Testa o método {@link WriterEditorImpl#removeAttributes()}.
    */
   @Test
   public void testRemoveAttributes() {
      Node attr1 = createStrictMock(Node.class);
      expect(attr1.getNodeName()).andReturn("attr1");
      replay(attr1);
      
      Node attr2 = createStrictMock(Node.class);
      expect(attr2.getNodeName()).andReturn("attr2");
      replay(attr2);
      
      Node attr3 = createStrictMock(Node.class);
      expect(attr3.getNodeName()).andReturn("attr3");
      replay(attr3);
      
      NamedNodeMap namedNodeMap = createStrictMock(NamedNodeMap.class);
      expect(namedNodeMap.removeNamedItem("attr1")).andReturn(null);
      expect(namedNodeMap.removeNamedItem("attr2")).andReturn(null);
      expect(namedNodeMap.removeNamedItem("attr3")).andReturn(null);
      replay(namedNodeMap);
      
      Element element = createStrictMock(Element.class);
      expect(element.getAttributes()).andReturn(namedNodeMap);
      replay(element);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.attributes(element)).andReturn(Arrays.asList(attr1, attr2, attr3));
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(null);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.removeAttributes();
      
      verify(attr1, attr2, attr3, namedNodeMap, element, document, operations, factory);
   }
   
   /**
    * Testa o método {@link WriterEditorImpl#rename(String)}.
    */
   @Test
   public void testRename() {
      Element element = createStrictMock(Element.class);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      expect(document.renameNode(element, null, "newName")).andReturn(element);
      replay(document);
      
      expect(element.getOwnerDocument()).andReturn(document);
      replay(element);

      Validations validations = createStrictMock(Validations.class);
      validations.assertValidName("newName");
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(null);
      expect(factory.createOperations()).andReturn(null);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);
      
      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.rename("newName");
      
      verify(element, document, validations, factory);
   }
   
   /**
    * Testa o método {@link WriterEditorImpl#defineAsCurrent(String)}.
    */
   @Test
   public void testDefineAsCurrent() {
      Element element = createStrictMock(Element.class);
      
      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(element);
      replay(document);
      
      expect(element.getOwnerDocument()).andReturn(document);
      replay(element);
      
      Element newCurrent = createNiceMock(Element.class);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.find(document, "path")).andReturn(newCurrent);
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createWithinContext()).andReturn(null);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValidations()).andReturn(null);
      expect(factory.createResult(document)).andReturn(null);
      replay(factory);

      WriterEditorImpl writerEditor = new WriterEditorImpl(factory, null, document);
      writerEditor.defineAsCurrent("path");
      
      verify(document, element, operations, factory);
   }
}
