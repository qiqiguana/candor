package net.sf.xisemele.impl;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.sf.xisemele.exception.ElementNotFoundException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Casos de teste para a classe {@link OperationsImpl}.
 * 
 * @author Carlos Eduardo Coral.
 */
public class OperationsImplTest {

   /**
    * Testa o método {@link OperationsImpl#attributeValue(org.w3c.dom.Node, String)}.
    */
   @Test
   public void testAttributeValue() {
      Node attr = createStrictMock(Node.class);
      expect(attr.getNodeValue()).andReturn("attrValue");
      replay(attr);
      
      NamedNodeMap namedNodeMap = createStrictMock(NamedNodeMap.class);
      expect(namedNodeMap.getNamedItem("attrName")).andReturn(attr);
      replay(namedNodeMap);
      
      Node node = createStrictMock(Node.class);
      expect(node.getAttributes()).andReturn(namedNodeMap);
      replay(node);
      
      OperationsImpl operations = new OperationsImpl(null);
      assertEquals("attrValue", operations.attributeValue(node, "attrName"));
      
      verify(attr, namedNodeMap, node);
   }
   
   /**
    * Testa o método {@link OperationsImpl#attributes(Node)}.
    */
   @Test
   public void testAttributes() {
      Node attr1 = createNiceMock(Node.class);
      Node attr2 = createNiceMock(Node.class);
      Node attr3 = createNiceMock(Node.class);
      
      NamedNodeMap namedNodeMap = createMock(NamedNodeMap.class);
      expect(namedNodeMap.getLength()).andReturn(3).times(4);
      expect(namedNodeMap.item(0)).andReturn(attr1);
      expect(namedNodeMap.item(1)).andReturn(attr2);
      expect(namedNodeMap.item(2)).andReturn(attr3);
      replay(namedNodeMap);
      
      Node node = createStrictMock(Node.class);
      expect(node.getAttributes()).andReturn(namedNodeMap);
      replay(node);
      
      OperationsImpl operations = new OperationsImpl(null);
      assertEquals(Arrays.asList(attr1, attr2, attr3), operations.attributes(node));
      
      verify(namedNodeMap, node);
   }
 
   /**
    * Testa o método {@link OperationsImpl#child(Node, int)}.
    */
   @Test
   public void testChildForIndex() {
      Node child1 = createNiceMock(Node.class);
      Node child2 = createNiceMock(Node.class);
      
      Node node = createNiceMock(Node.class);
      
      OperationsHelper operationsHelper = createStrictMock(OperationsHelper.class);
      expect(operationsHelper.children(node)).andReturn(Arrays.asList(child1, child2)).times(4);
      replay(operationsHelper);
      
      OperationsImpl operations = new OperationsImpl(operationsHelper);
      assertNull(operations.child(node, -1));
      assertSame(child1, operations.child(node, 0));
      assertSame(child2, operations.child(node, 1));
      assertNull(operations.child(node, 2));
      
      verify(operationsHelper);
   }
   
   /**
    * Testa o método {@link OperationsImpl#child(Node, String)}.
    */
   @Test
   public void testChildForName() {
      Node child1 = createNiceMock(Node.class);
      Node child2 = createNiceMock(Node.class);
      Node child3 = createNiceMock(Node.class);

      Node node = createNiceMock(Node.class);
      
      List<Node> children = Arrays.asList(child1, child2, child3);
      
      OperationsHelper operationsHelper = createMock(OperationsHelper.class);
      expect(operationsHelper.children(node)).andReturn(children).times(5);
      expect(operationsHelper.nodeWithName(children, "child0")).andReturn(null);
      expect(operationsHelper.nodeWithName(children, "child1")).andReturn(child1);
      expect(operationsHelper.nodeWithName(children, "child2")).andReturn(child2);
      expect(operationsHelper.nodeWithName(children, "child3")).andReturn(child3);
      expect(operationsHelper.nodeWithName(children, "child4")).andReturn(null);
      replay(operationsHelper);
      
      OperationsImpl operations = new OperationsImpl(operationsHelper);
      assertNull(operations.child(node, "child0"));
      assertSame(child1, operations.child(node, "child1"));
      assertSame(child2, operations.child(node, "child2"));
      assertSame(child3, operations.child(node, "child3"));
      assertNull(operations.child(node, "child4"));
      
      verify(operationsHelper);
   }
   
   /**
    * Testa o método {@link OperationsImpl#children(Node, String)}.
    */
   @Test
   public void testChildrenForName() {
      Node child1 = createNiceMock(Node.class);
      Node child2 = createNiceMock(Node.class);
      Node child3 = createNiceMock(Node.class);
      Node child4 = createNiceMock(Node.class);
      Node child5 = createNiceMock(Node.class);
      
      Node node = createNiceMock(Node.class);
      
      List<Node> children = Arrays.asList(child1, child2, child3, child4, child5);
      
      OperationsHelper operationsHelper = createStrictMock(OperationsHelper.class);
      expect(operationsHelper.children(node)).andReturn(children);
      expect(operationsHelper.sublistWithName(children, "childB")).andReturn(Arrays.asList(child2, child3, child4));
      replay(operationsHelper);
      
      OperationsImpl operations = new OperationsImpl(operationsHelper);
      assertEquals(Arrays.asList(child2, child3, child4), operations.children(node, "childB"));
      
      verify(operationsHelper);
   }
   
   /**
    * Testa o método {@link OperationsImpl#children(Node)}.
    */
   @Test
   public void testChildren() {
      Node child1 = createNiceMock(Node.class);
      Node child2 = createNiceMock(Node.class);
      Node child3 = createNiceMock(Node.class);
      
      Node node = createNiceMock(Node.class);
      
      OperationsHelper operationsHelper = createStrictMock(OperationsHelper.class);
      expect(operationsHelper.children(node)).andReturn(Arrays.asList(child1, child2, child3));
      replay(operationsHelper);
      
      OperationsImpl operations = new OperationsImpl(operationsHelper);
      assertEquals(Arrays.asList(child1, child2, child3), operations.children(node));
      
      verify(operationsHelper);
   }
   
   /**
    * Testa o método {@link OperationsImpl#containsAttribute(Node, String)}.
    */
   @Test
   public void testContainsAttribute() {
      Node attribute = createNiceMock(Node.class);
      
      NamedNodeMap namedNodeMap = createStrictMock(NamedNodeMap.class);
      expect(namedNodeMap.getNamedItem("attribute1")).andReturn(attribute);
      expect(namedNodeMap.getNamedItem("attribute2")).andReturn(null);
      replay(namedNodeMap);
      
      Node node = createStrictMock(Node.class);
      expect(node.getAttributes()).andReturn(namedNodeMap).times(2);
      replay(node);
      
      OperationsImpl operations = new OperationsImpl(null);
      assertTrue(operations.containsAttribute(node, "attribute1"));
      assertFalse(operations.containsAttribute(node, "attribute2"));
      
      verify(namedNodeMap, node);
   }
   
   /**
    * Testa o método {@link OperationsImpl#containsAttributes(Node)}.
    */
   @Test
   public void testContainsAttributes() {
      NamedNodeMap namedNodeMap = createStrictMock(NamedNodeMap.class);
      expect(namedNodeMap.getLength()).andReturn(3);
      expect(namedNodeMap.getLength()).andReturn(0);
      replay(namedNodeMap);
      
      Node node = createStrictMock(Node.class);
      expect(node.getAttributes()).andReturn(namedNodeMap).times(2);
      replay(node);
      
      OperationsImpl operations = new OperationsImpl(null);
      assertTrue(operations.containsAttributes(node));
      assertFalse(operations.containsAttributes(node));
      
      verify(namedNodeMap, node);
   }
   
   /**
    * Testa o método {@link OperationsImpl#containsChild(Node, String)}.
    */
   @Test
   public void testContainsChild() {
      Node child1 = createNiceMock(Node.class);
      Node child2 = createNiceMock(Node.class);
      Node child3 = createNiceMock(Node.class);
    
      Node node = createNiceMock(Node.class);
      
      List<Node> children = Arrays.asList(child1, child2, child3);
      
      OperationsHelper operationsHelper = createMock(OperationsHelper.class);
      expect(operationsHelper.children(node)).andReturn(children).times(4);
      expect(operationsHelper.nodeWithName(children, "child1")).andReturn(child1);
      expect(operationsHelper.nodeWithName(children, "child2")).andReturn(child2);
      expect(operationsHelper.nodeWithName(children, "child3")).andReturn(child3);
      expect(operationsHelper.nodeWithName(children, "child4")).andReturn(null);
      replay(operationsHelper);
      
      OperationsImpl operations = new OperationsImpl(operationsHelper);
      
      assertTrue(operations.containsChild(node, "child1"));
      assertTrue(operations.containsChild(node, "child2"));
      assertTrue(operations.containsChild(node, "child3"));
      assertFalse(operations.containsChild(node, "child4"));
      
      verify(operationsHelper);
   }
   
   /**
    * Testa o método {@link OperationsImpl#containsChildren(Node)}.
    */
   @Test
   public void testContainsChildren() {
      Node child = createNiceMock(Node.class);
      
      Node node = createNiceMock(Node.class);
      
      OperationsHelper operationsHelper = createStrictMock(OperationsHelper.class);
      expect(operationsHelper.children(node)).andReturn(Arrays.asList(child));
      expect(operationsHelper.children(node)).andReturn(Collections.<Node>emptyList());
      replay(operationsHelper);
      
      OperationsImpl operations = new OperationsImpl(operationsHelper);
      assertTrue(operations.containsChildren(node));
      assertFalse(operations.containsChildren(node));
      
      verify(operationsHelper);
   }
   
   /**
    * Testa o método {@link OperationsImpl#containsElement(Document, String)}.
    */
   @Test
   public void testContainsElement() {
      Node node = createNiceMock(Node.class);
      Document document = createNiceMock(Document.class);
      
      OperationsHelper operationsHelper = createStrictMock(OperationsHelper.class);
      expect(operationsHelper.find(document, "path")).andReturn(node);
      expect(operationsHelper.find(document, "path")).andReturn(null);
      replay(operationsHelper);
      
      OperationsImpl operations = new OperationsImpl(operationsHelper);
      assertTrue(operations.containsElement(document, "path"));
      assertFalse(operations.containsElement(document, "path"));
    
      verify(operationsHelper);
   }
   
   /**
    * Testa o método {@link OperationsImpl#containsValue(Node)}.
    */
   @Test
   public void testContainsValue() {
      Node node = createStrictMock(Node.class);
      expect(node.getTextContent()).andReturn("abc");
      expect(node.getTextContent()).andReturn(" \t  \n\n    \n \t   \n");
      replay(node);
      
      Node child = createNiceMock(Node.class);
      
      OperationsHelper operationsHelper = createStrictMock(OperationsHelper.class);
      expect(operationsHelper.children(node)).andReturn(Arrays.asList(child));
      expect(operationsHelper.children(node)).andReturn(Collections.<Node>emptyList()).times(2);
      replay(operationsHelper);
      
      OperationsImpl operations = new OperationsImpl(operationsHelper);
      assertFalse(operations.containsValue(node));
      assertTrue(operations.containsValue(node));
      assertFalse(operations.containsValue(node));
      
      verify(node, operationsHelper);
   }
   
   /**
    * Testa o método {@link OperationsImpl#find(Document, String)}.
    */
   @Test
   public void testFind() {
      Node node = createNiceMock(Node.class);
      Document document = createNiceMock(Document.class);
      
      OperationsHelper operationsHelper = createStrictMock(OperationsHelper.class);
      expect(operationsHelper.find(document, "path")).andReturn(node);
      replay(operationsHelper);
      
      OperationsImpl operations = new OperationsImpl(operationsHelper);
      assertSame(node, operations.find(document, "path"));
      
      verify(operationsHelper);
   }

   /**
    * Testa se dispara a exceção {@link ElementNotFoundException} ao chamar o método {@link OperationsImpl#find(Document, String)}.
    */
   @Test(expected = ElementNotFoundException.class)
   public void testFindWithElementNotFoundException() {
      Document document = createNiceMock(Document.class);
      
      OperationsHelper operationsHelper = createStrictMock(OperationsHelper.class);
      expect(operationsHelper.find(document, "path")).andReturn(null);
      replay(operationsHelper);
      
      new OperationsImpl(operationsHelper).find(document, "path");
   }
   
   /**
    * Testa o método {@link OperationsImpl#isRootElement(Node)}.
    */
   @Test
   public void testIsRootElement() {
      Element parent = createStrictMock(Element.class);

      Document document = createStrictMock(Document.class);
      expect(document.getDocumentElement()).andReturn(parent).times(2);
      replay(document);
      
      expect(parent.getOwnerDocument()).andReturn(document);
      replay(parent);
      
      Node node = createStrictMock(Node.class);
      expect(node.getOwnerDocument()).andReturn(document);
      replay(node);
      
      OperationsImpl operations = new OperationsImpl(null);
      assertTrue(operations.isRootElement(parent));
      assertFalse(operations.isRootElement(node));
      
      verify(parent, document, node);
   }
   
   /**
    * Testa o método {@link OperationsImpl#numberOfChildren(Node)}.
    */
   @Test
   @SuppressWarnings("unchecked")
   public void testNumberOfChildren() {
      List<Node> children = createStrictMock(List.class);
      expect(children.size()).andReturn(3);
      expect(children.size()).andReturn(1);
      expect(children.size()).andReturn(0);
      replay(children);
      
      Node node = createNiceMock(Node.class);
      
      OperationsHelper operationsHelper = createStrictMock(OperationsHelper.class);
      expect(operationsHelper.children(node)).andReturn(children).times(3);
      replay(operationsHelper);
      
      OperationsImpl operations = new OperationsImpl(operationsHelper);
      assertEquals(3, operations.numberOfChildren(node));
      assertEquals(1, operations.numberOfChildren(node));
      assertEquals(0, operations.numberOfChildren(node));
      
      verify(children, operationsHelper);
   }
   
   /**
    * Testa o método {@link OperationsImpl#numberOfChildren(Node, String)}.
    */
   @Test
   @SuppressWarnings("unchecked")
   public void testNumberOfChildrenForName() {
      List<Node> children = createStrictMock(List.class);
      expect(children.size()).andReturn(3);
      expect(children.size()).andReturn(1);
      expect(children.size()).andReturn(0);
      replay(children);
      
      Node node = createNiceMock(Node.class);
      
      OperationsHelper operationsHelper = createMock(OperationsHelper.class);
      expect(operationsHelper.children(node)).andReturn(children).times(3);
      expect(operationsHelper.sublistWithName(children, "child1")).andReturn(children);
      expect(operationsHelper.sublistWithName(children, "child2")).andReturn(children);
      expect(operationsHelper.sublistWithName(children, "child3")).andReturn(children);
      replay(operationsHelper);
      
      OperationsImpl operations = new OperationsImpl(operationsHelper);
      assertEquals(3, operations.numberOfChildren(node, "child1"));
      assertEquals(1, operations.numberOfChildren(node, "child2"));
      assertEquals(0, operations.numberOfChildren(node, "child3"));
      
      verify(children, operationsHelper);
   }
   
   /**
    * Testa o método {@link OperationsImpl#path(Node)}.
    */
   @Test
   public void testPath() {
      Node node1 = createMock(Node.class);
      expect(node1.getNodeName()).andReturn("node1").times(3);
      expect(node1.getParentNode()).andReturn(null).times(3);
      expect(node1.getNodeType()).andReturn(Node.ELEMENT_NODE).times(2);
      replay(node1);
      
      Node node2 = createMock(Node.class);
      expect(node2.getNodeName()).andReturn("node2").times(2);
      expect(node2.getParentNode()).andReturn(node1).times(2);
      expect(node2.getNodeType()).andReturn(Node.ELEMENT_NODE);
      replay(node2);
      
      Node node3 = createMock(Node.class);
      expect(node3.getNodeName()).andReturn("node3");
      expect(node3.getParentNode()).andReturn(node2);
      replay(node3);
      
      OperationsImpl operations = new OperationsImpl(null);
      
      assertEquals("node1/node2/node3", operations.path(node3));
      assertEquals("node1/node2", operations.path(node2));
      assertEquals("node1", operations.path(node1));
      
      verify(node1, node2, node3);
   }
}
