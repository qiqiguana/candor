package net.sf.xisemele.impl;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Casos de teste para a classe {@link OperationsHelperImpl}.
 * 
 * @author Carlos Eduardo Coral.
 */
public class OperationsHelperImplTest {
   
   /**
    * Testa o método {@link OperationsHelper#children(Node)}.
    */
   @Test
   public void testChildren() {
      Node node0 = createStrictMock(Node.class);
      expect(node0.getNodeType()).andReturn(Node.ELEMENT_NODE);
      replay(node0);
      
      Node node1 = createStrictMock(Node.class);
      expect(node1.getNodeType()).andReturn(Node.TEXT_NODE);
      replay(node1);
      
      Node node2 = createStrictMock(Node.class);
      expect(node2.getNodeType()).andReturn(Node.ELEMENT_NODE);
      replay(node2);
      
      Node node3 = createStrictMock(Node.class);
      expect(node3.getNodeType()).andReturn(Node.TEXT_NODE);
      replay(node3);
      
      NodeList nodeList = createMock(NodeList.class);
      expect(nodeList.getLength()).andReturn(4).anyTimes();
      expect(nodeList.item(0)).andReturn(node0);
      expect(nodeList.item(1)).andReturn(node1);
      expect(nodeList.item(2)).andReturn(node2);
      expect(nodeList.item(3)).andReturn(node3);
      replay(nodeList);
      
      Node node = createStrictMock(Node.class);
      expect(node.getChildNodes()).andReturn(nodeList);
      replay(node);
      
      OperationsHelperImpl operationsHelper = new OperationsHelperImpl();
      assertEquals(Arrays.asList(node0, node2), operationsHelper.children(node));
      
      verify(node0, node1, node2, node3, nodeList, node);
   }
   
   /**
    * Testa o método {@link OperationsHelperImpl#find(Document, String)}.
    */
   @Test
   public void testFind() {
      /* <root>
       *   <nodeA>
       *     <nodeB/>
       *     <nodeC>
       *       <nodeD/>
       *     </nodeC>
       *   </nodeA>
       *   <nodeE>
       *     <nodeF/>
       *   </nodeE>
       * </root>
       */
      
      Node nodeF = createNodeMock("nodeF");
      Node nodeE = createNodeMock("nodeE", nodeF);
      Node nodeD = createNodeMock("nodeD");
      Node nodeC = createNodeMock("nodeC", nodeD);
      Node nodeB = createNodeMock("nodeB");
      Node nodeA = createNodeMock("nodeA", nodeB, nodeC);
      Element root = createElementMock("root", nodeA, nodeE);

//      NodeList nodeList = createMock(NodeList.class);
//      expect(nodeList.getLength()).andReturn(1).anyTimes();
//      expect(nodeList.item(0)).andReturn(root).anyTimes();
//      replay(nodeList);
      
      Document document = createMock(Document.class);
      expect(document.getDocumentElement()).andReturn(root).anyTimes();
      replay(document);
      
      OperationsHelperImpl operationsHelper = new OperationsHelperImpl();
      
      assertSame(root, operationsHelper.find(document, "root"));
      assertSame(nodeA, operationsHelper.find(document, "root/nodeA"));
      assertSame(nodeB, operationsHelper.find(document, "root/nodeA/nodeB"));
      assertSame(nodeC, operationsHelper.find(document, "root/nodeA/nodeC"));
      assertSame(nodeD, operationsHelper.find(document, "root/nodeA/nodeC/nodeD"));
      assertSame(nodeE, operationsHelper.find(document, "root/nodeE"));
      assertSame(nodeF, operationsHelper.find(document, "root/nodeE/nodeF"));
      
      assertNull(operationsHelper.find(document, "nodeA"));
      assertNull(operationsHelper.find(document, "nodeB"));
      assertNull(operationsHelper.find(document, "nodeC"));
      assertNull(operationsHelper.find(document, "root/nodeB"));
      assertNull(operationsHelper.find(document, "root/nodeA/nodeC/nodeF"));
   }

   /**
    * Cria um <i>mock</i> de <code>org.w3c.dom.Node</code> para o nome e filhos especificados por parâmetro.
    * 
    * @param name
    *       <code>java.lang.String</code> que será atribuído ao <i>mock</i> de <code>org.w3c.dom.Node</code>.
    *       
    * @param children
    *       <i>array</i> de <code>org.w3c.dom.Node</code> correspondente aos filhos do <i>mock</i>.
    *       
    * @return
    *       <i>mock</i> de <code>org.w3c.dom.Node</code>.
    */
   private Node createNodeMock(String name, Node... children) {
      NodeList nodeList = createMock(NodeList.class);
      expect(nodeList.getLength()).andReturn(children.length).anyTimes();
      for (int index = 0; index < children.length; index++) {
         expect(nodeList.item(index)).andReturn(children[index]).anyTimes();
      }
      replay(nodeList);
      
      Node node = createMock(Node.class);
      expect(node.getNodeName()).andReturn(name).anyTimes();
      expect(node.getChildNodes()).andReturn(nodeList).anyTimes();
      replay(node);
      
      return node;
   }
   
   /**
    * Cria um <i>mock</i> de <code>org.w3c.dom.Element</code> para o nome e filhos especificados por parâmetro.
    * 
    * @param name
    *       <code>java.lang.String</code> que será atribuído ao <i>mock</i> de <code>org.w3c.dom.Element</code>.
    *       
    * @param children
    *       <i>array</i> de <code>org.w3c.dom.Node</code> correspondente aos filhos do <i>mock</i>.
    *       
    * @return
    *       <i>mock</i> de <code>org.w3c.dom.Element</code>.
    */
   private Element createElementMock(String name, Node... children) {
      NodeList nodeList = createMock(NodeList.class);
      expect(nodeList.getLength()).andReturn(children.length).anyTimes();
      for (int index = 0; index < children.length; index++) {
         expect(nodeList.item(index)).andReturn(children[index]).anyTimes();
      }
      replay(nodeList);
      
      Element element = createMock(Element.class);
      expect(element.getNodeName()).andReturn(name).anyTimes();
      expect(element.getChildNodes()).andReturn(nodeList).anyTimes();
      replay(element);
      
      return element;
   }
   
   /**
    * Testa o método {@link OperationsHelperImpl#nodeWithName(List, String)}.
    */
   @Test
   public void testNodeWithName() {
      Node node0 = createMock(Node.class);
      expect(node0.getNodeName()).andReturn("node0").anyTimes();
      replay(node0);
      
      Node node1 = createMock(Node.class);
      expect(node1.getNodeName()).andReturn("node1").anyTimes();
      replay(node1);
      
      Node node2 = createMock(Node.class);
      expect(node2.getNodeName()).andReturn("node2").anyTimes();
      replay(node2);
      
      List<Node> nodes = Arrays.asList(node0, node1, node2);
      
      OperationsHelperImpl operationsHelper = new OperationsHelperImpl();
      assertSame(node0, operationsHelper.nodeWithName(nodes, "node0"));
      assertSame(node1, operationsHelper.nodeWithName(nodes, "node1"));
      assertSame(node2, operationsHelper.nodeWithName(nodes, "node2"));
      assertNull(operationsHelper.nodeWithName(nodes, "node3"));
      
      verify(node0, node1, node2);
   }
   
   /**
    * Testa o método {@link OperationsHelperImpl#sublistWithName(List, String)}.
    */
   @Test
   public void testSublistWithName() {
      Node node0 = createMock(Node.class);
      expect(node0.getNodeName()).andReturn("nodeA").anyTimes();
      replay(node0);
      
      Node node1 = createMock(Node.class);
      expect(node1.getNodeName()).andReturn("nodeB").anyTimes();
      replay(node1);
      
      Node node2 = createMock(Node.class);
      expect(node2.getNodeName()).andReturn("nodeA").anyTimes();
      replay(node2);
      
      Node node3 = createMock(Node.class);
      expect(node3.getNodeName()).andReturn("nodeB").anyTimes();
      replay(node3);
      
      Node node4 = createMock(Node.class);
      expect(node4.getNodeName()).andReturn("nodeC").anyTimes();
      replay(node4);
      
      List<Node> nodes = Arrays.asList(node0, node1, node2, node3, node4);
      
      OperationsHelperImpl operationsHelper = new OperationsHelperImpl();
      assertEquals(Arrays.asList(node0, node2), operationsHelper.sublistWithName(nodes, "nodeA"));
      assertEquals(Arrays.asList(node1, node3), operationsHelper.sublistWithName(nodes, "nodeB"));
      assertEquals(Arrays.asList(node4), operationsHelper.sublistWithName(nodes, "nodeC"));
      assertEquals(0, operationsHelper.sublistWithName(nodes, "nodeD").size());
      
      verify(node0, node1, node2, node3, node4);
   }
}
