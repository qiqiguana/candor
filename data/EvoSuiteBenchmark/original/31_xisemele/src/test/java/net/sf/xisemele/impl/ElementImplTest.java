package net.sf.xisemele.impl;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import net.sf.xisemele.api.Attribute;
import net.sf.xisemele.api.Element;
import net.sf.xisemele.api.Value;
import net.sf.xisemele.api.ValueList;
import net.sf.xisemele.exception.ChildNotFoundException;
import net.sf.xisemele.exception.ElementIndexOutOfBoundsException;
import net.sf.xisemele.exception.ElementNotFoundException;

import org.junit.Test;
import org.w3c.dom.Node;

/**
 * Casos de teste para a classe {@link ElementImpl}.
 * 
 * @author Carlos Eduardo Coral.
 */
public class ElementImplTest {

   /**
    * Testa o método {@link ElementImpl#attribute(String)}.
    */
   @Test
   public void testAttribute() {
      Node node = createNiceMock(Node.class);

      Value value = createNiceMock(Value.class);

      Operations operations = createStrictMock(Operations.class);
      expect(operations.attributeValue(node, "attrName")).andReturn("attrValue");
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValue("attrValue")).andReturn(value);
      replay(factory);
      
      ElementImpl element = new ElementImpl(factory, node);

      assertSame(value, element.attribute("attrName"));

      verify(factory, operations);
   }

   /**
    * Testa o método {@link ElementImpl#attributes()}.
    */
   @Test
   public void testAttributes() {
      Attribute attr1 = createNiceMock(Attribute.class);
      Attribute attr2 = createNiceMock(Attribute.class);
      Attribute attr3 = createNiceMock(Attribute.class);

      List<Attribute> attrs = Arrays.asList(attr1, attr2, attr3);

      Node node = createNiceMock(Node.class);

      Node node1 = createNiceMock(Node.class);
      Node node2 = createNiceMock(Node.class);
      Node node3 = createNiceMock(Node.class);

      List<Node> nodes = Arrays.asList(node1, node2, node3);

      Operations operations = createStrictMock(Operations.class);
      expect(operations.attributes(node)).andReturn(nodes);
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createAttributes(nodes)).andReturn(attrs);
      replay(factory);
      
      ElementImpl element = new ElementImpl(factory, node);

      assertEquals(attrs, element.attributes());

      verify(factory, operations);
   }

   /**
    * Testa o método {@link ElementImpl#containsAttributes()}
    */
   @Test
   public void testContainsAttributes() {
      Node node = createNiceMock(Node.class);

      Operations operations = createStrictMock(Operations.class);
      expect(operations.containsAttributes(node)).andReturn(Boolean.TRUE);
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createOperations()).andReturn(operations);
      replay(factory);
      
      ElementImpl element = new ElementImpl(factory, node);

      assertEquals(true, element.containsAttributes());

      verify(operations, factory);
   }

   /**
    * Testa o método {@link ElementImpl#containsAttribute(String)}.
    */
   @Test
   public void testContainsAttribute() {
      Node node = createNiceMock(Node.class);

      Operations operations = createStrictMock(Operations.class);
      expect(operations.containsAttribute(node, "attrName")).andReturn(Boolean.TRUE);
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createOperations()).andReturn(operations);
      replay(factory);
      
      ElementImpl element = new ElementImpl(factory, node);

      assertEquals(true, element.containsAttribute("attrName"));

      verify(operations, factory);
   }

   /**
    * Testa o método {@link ElementImpl#child(int)}.
    */
   @Test
   public void testChildForIndex() {
      Node node = createNiceMock(Node.class);

      Node child = createNiceMock(Node.class);

      Element childElement = createNiceMock(Element.class);

      Operations operations = createStrictMock(Operations.class);
      expect(operations.child(node, 3)).andReturn(child);
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createElement(child)).andReturn(childElement);
      replay(factory);
      
      ElementImpl element = new ElementImpl(factory, node);

      assertSame(childElement, element.child(3));

      verify(factory, operations);
   }

   /**
    * Testa o método {@link ElementImpl#child(int)} para a exceção {@link ElementIndexOutOfBoundsException}.
    */
   @Test(expected = ElementIndexOutOfBoundsException.class)
   public void testChildWithElementIndexOutOfBoundsException() {
      Node node = createNiceMock(Node.class);
      expect(node.getNodeName()).andReturn("name");
      replay(node);

      Operations operations = createStrictMock(Operations.class);
      expect(operations.child(node, 3)).andReturn(null);
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createOperations()).andReturn(operations);
      replay(factory);
      
      ElementImpl element = new ElementImpl(factory, node);

      element.child(3);
   }

   /**
    * Testa o método {@link ElementImpl#child(String)}.
    */
   @Test
   public void testChildForName() {
      Node node = createNiceMock(Node.class);

      Node child = createNiceMock(Node.class);
      
      Element elementChild = createNiceMock(Element.class);

      Operations operations = createStrictMock(Operations.class);
      expect(operations.child(node, "name")).andReturn(child);
      replay(operations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createElement(child)).andReturn(elementChild);
      replay(factory);
      
      ElementImpl element = new ElementImpl(factory, node);
      
      assertSame(elementChild, element.child("name"));
      
      verify(factory, operations);
   }
   
   /**
    * Testa o método {@link ElementImpl#child(String)} para a exceção {@link ElementNotFoundException}.
    */
   @Test(expected = ChildNotFoundException.class)
   public void testChildWithElementNotFoundException() {
      Node node = createStrictMock(Node.class);
      expect(node.getNodeName()).andReturn("name");
      replay(node);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.child(node, "childName")).andReturn(null);
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createOperations()).andReturn(operations);
      replay(factory);
      
      ElementImpl element = new ElementImpl(factory, node);
      
      element.child("childName");
   }
   
   /**
    * Testa o método {@link ElementImpl#children()}.
    */
   @Test
   public void testChildren() {
      Node node = createNiceMock(Node.class);
    
      Node child1 = createNiceMock(Node.class);
      Node child2 = createNiceMock(Node.class);
      Node child3 = createNiceMock(Node.class);
      
      List<Node> children = Arrays.asList(child1, child2, child3);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.children(node)).andReturn(children);
      replay(operations);
      
      Element element1 = createNiceMock(Element.class);
      Element element2 = createNiceMock(Element.class);
      Element element3 = createNiceMock(Element.class);
      
      List<Element> elements = Arrays.asList(element1, element2, element3);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createElements(children)).andReturn(elements);
      replay(factory);
      
      ElementImpl element = new ElementImpl(factory, node);

      assertEquals(elements, element.children());
      
      verify(operations, factory);
   }
 
   /**
    * Testa o método {@link ElementImpl#children(String)}.
    */
   @Test
   public void testChildrenWithName() {
      Node node = createNiceMock(Node.class);

      Node child1 = createNiceMock(Node.class);
      Node child2 = createNiceMock(Node.class);
      Node child3 = createNiceMock(Node.class);
      
      List<Node> children = Arrays.asList(child1, child2, child3);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.children(node, "childName")).andReturn(children);
      replay(operations);
      
      Element element1 = createNiceMock(Element.class);
      Element element2 = createNiceMock(Element.class);
      Element element3 = createNiceMock(Element.class);
      
      List<Element> elements = Arrays.asList(element1, element2, element3);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createElements(children)).andReturn(elements);
      replay(factory);
      
      ElementImpl element = new ElementImpl(factory, node);

      assertEquals(elements, element.children("childName"));
      
      verify(operations, factory);
   }
   
   /**
    * Testa o método {@link ElementImpl#childrenValue()}.
    */
   @Test
   public void testChildrenValue() {
      Node node = createNiceMock(Node.class);

      Node child1 = createNiceMock(Node.class);
      Node child2 = createNiceMock(Node.class);
      Node child3 = createNiceMock(Node.class);

      List<Node> children = Arrays.asList(child1, child2, child3);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.children(node)).andReturn(children);
      replay(operations);
      
      ValueList valueList = createNiceMock(ValueList.class);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValueList(children)).andReturn(valueList);
      replay(factory);
      
      ElementImpl element = new ElementImpl(factory, node);
      
      assertSame(valueList, element.childrenValue());
      
      verify(operations, factory);
   }
   
   /**
    * Testa o método {@link ElementImpl#childrenValue(String)}.
    */
   @Test
   public void testChildrenValueForName() {
      Node node = createNiceMock(Node.class);
      
      Node child1 = createNiceMock(Node.class);
      Node child2 = createNiceMock(Node.class);
      Node child3 = createNiceMock(Node.class);

      List<Node> children = Arrays.asList(child1, child2, child3);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.children(node, "childName")).andReturn(children);
      replay(operations);
      
      ValueList valueList = createNiceMock(ValueList.class);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValueList(children)).andReturn(valueList);
      replay(factory);
      
      ElementImpl element = new ElementImpl(factory, node);
      
      assertSame(valueList, element.childrenValue("childName"));
      
      verify(operations, factory);
   }
   
   /**
    * Testa o método {@link ElementImpl#containsChild(String)}.
    */
   @Test
   public void testContainsChild() {
      Node node = createNiceMock(Node.class);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.containsChild(node, "childName")).andReturn(Boolean.TRUE);
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createOperations()).andReturn(operations);
      replay(factory);
      
      ElementImpl element = new ElementImpl(factory, node);
      
      assertTrue(element.containsChild("childName"));
      
      verify(operations, factory);
   }
   
   /**
    * Testa o método {@link ElementImpl#containsChildren()}.
    */
   @Test
   public void testContainsChildren() {
      Node node = createNiceMock(Node.class);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.containsChildren(node)).andReturn(Boolean.TRUE);
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createOperations()).andReturn(operations);
      replay(factory);
      
      ElementImpl element = new ElementImpl(factory, node);
      
      assertTrue(element.containsChildren());
      
      verify(operations, factory);
   }
   
   /**
    * Testa o método {@link ElementImpl#name()}.
    */
   @Test
   public void testName() {
      Node node = createStrictMock(Node.class);
      expect(node.getNodeName()).andReturn("name");
      replay(node);
      
      ElementImpl element = new ElementImpl(createNiceMock(Factory.class), node);
      
      assertEquals("name", element.name());
      
      verify(node);
   }
   
   /**
    * Testa o método {@link ElementImpl#node()}.
    */
   @Test
   public void testNode() {
      Node node = createNiceMock(Node.class);
      
      ElementImpl element = new ElementImpl(createNiceMock(Factory.class), node);
      
      assertSame(node, element.node());
   }
   
   /**
    * Testa o método {@link ElementImpl#numberOfChildren()}.
    */
   @Test
   public void testNumberOfChildren() {
      Node node = createNiceMock(Node.class);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.numberOfChildren(node)).andReturn(3);
      replay(operations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createOperations()).andReturn(operations);
      replay(factory);
      
      ElementImpl element = new ElementImpl(factory, node);
      
      assertEquals(3, element.numberOfChildren());
      
      verify(operations, factory);
   }
   
   /**
    * Testa o método {@link ElementImpl#numberOfChildren(String)}.
    */
   @Test
   public void testNumberOfChildrenForName() {
      Node node = createNiceMock(Node.class);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.numberOfChildren(node, "childName")).andReturn(3);
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createOperations()).andReturn(operations);
      replay(factory);
      
      ElementImpl element = new ElementImpl(factory, node);
      
      assertEquals(3, element.numberOfChildren("childName"));
      
      verify(operations, factory);
   }
 
   /**
    * Testa o método {@link ElementImpl#path()}.
    */
   @Test
   public void testPath() {
      Node node = createNiceMock(Node.class);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.path(node)).andReturn("path");
      replay(operations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createOperations()).andReturn(operations);
      replay(factory);
      
      ElementImpl element = new ElementImpl(factory, node);
      
      assertEquals("path", element.path());
      
      verify(operations, factory);
   }
   
   /**
    * Testa o método {@link ElementImpl#value()}.
    */
   @Test
   public void testValue() {
      Node node1 = createStrictMock(Node.class);
      expect(node1.getTextContent()).andReturn("value");
      replay(node1);
      
      Node node2 = createNiceMock(Node.class);
      
      Operations operations = createStrictMock(Operations.class);
      expect(operations.containsChildren(node1)).andReturn(Boolean.FALSE);
      expect(operations.containsChildren(node2)).andReturn(Boolean.TRUE);
      replay(operations);
      
      Value value1 = createNiceMock(Value.class);
      Value value2 = createNiceMock(Value.class);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createValue("value")).andReturn(value1);
      expect(factory.createOperations()).andReturn(operations);
      expect(factory.createNullValue()).andReturn(value2);
      replay(factory);
      
      ElementImpl element1 = new ElementImpl(factory, node1);
      
      assertSame(value1, element1.value());
      
      ElementImpl element2 = new ElementImpl(factory, node2);
      
      assertSame(value2, element2.value());
      
      verify(node1, operations, factory);
   }
}
