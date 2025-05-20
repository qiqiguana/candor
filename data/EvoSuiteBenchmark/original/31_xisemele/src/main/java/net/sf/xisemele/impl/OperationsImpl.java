package net.sf.xisemele.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.xisemele.exception.ElementNotFoundException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Implementação de {@link Operations}.
 * 
 * @author Carlos Eduardo Coral.
 */
class OperationsImpl implements Operations {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;
   
   /**
    * Instância de {@link OperationsHelper} que será usada para manipular objetos da API DOM.
    */
   private final OperationsHelper operationsHelper;

   /**
    * Cria uma nova instância de {@link OperationsImpl}.
    * 
    * @param operationsHelper
    *       instância de {@link OperationsHelper} que será usada para manipular objetos da API DOM.
    */
   public OperationsImpl(final OperationsHelper operationsHelper) {
      this.operationsHelper = operationsHelper;
   }

   /**
    * {@inheritDoc}
    */
   public String attributeValue(Node node, String name) {
      return node.getAttributes().getNamedItem(name).getNodeValue();
   }

   /**
    * {@inheritDoc}
    */
   public List<Node> attributes(Node node) {
      ArrayList<Node> attributes = new ArrayList<Node>();
      NamedNodeMap namedNodeMap = node.getAttributes();
      for (int index = 0; index < namedNodeMap.getLength(); index++) {
         attributes.add(namedNodeMap.item(index));
      }
      return attributes;
   }

   /**
    * {@inheritDoc}
    */
   public Node child(Node node, int index) {
      List<Node> children = operationsHelper.children(node);
      if (index >= 0 && index < children.size()) {
         return children.get(index);
      }
      return null;
   }

   /**
    * {@inheritDoc}
    */
   public Node child(Node node, String name) {
      return operationsHelper.nodeWithName(operationsHelper.children(node), name);
   }

   /**
    * {@inheritDoc}
    */
   public List<Node> children(Node node, String name) {
      return operationsHelper.sublistWithName(operationsHelper.children(node), name);
   }

   /**
    * {@inheritDoc}
    */
   public List<Node> children(Node node) {
      return operationsHelper.children(node);
   }

   /**
    * {@inheritDoc}
    */
   public boolean containsAttribute(Node node, String name) {
      return node.getAttributes().getNamedItem(name) != null;
   }

   /**
    * {@inheritDoc}
    */
   public boolean containsAttributes(Node node) {
      return node.getAttributes().getLength() > 0;
   }

   /**
    * {@inheritDoc}
    */
   public boolean containsChild(Node node, String name) {
      return operationsHelper.nodeWithName(operationsHelper.children(node), name) != null;
   }

   /**
    * {@inheritDoc}
    */
   public boolean containsChildren(Node node) {
      return operationsHelper.children(node).size() > 0;
   }

   /**
    * {@inheritDoc}
    */
   public boolean containsElement(Document document, String path) {
      return operationsHelper.find(document, path) != null;
   }

   /**
    * {@inheritDoc}
    */
   public boolean containsValue(Node node) {
      return !(containsChildren(node) || node.getTextContent().trim().length() == 0);
   }

   /**
    * {@inheritDoc}
    */
   public Node find(Document document, String path) throws ElementNotFoundException {
      Node node = operationsHelper.find(document, path);
      if (node == null) {
         throw new ElementNotFoundException(path);
      }
      return node;
   }

   /**
    * {@inheritDoc}
    */
   public boolean isRootElement(Node node) {
      return node.getOwnerDocument().getDocumentElement() == node;
   }

   /**
    * {@inheritDoc}
    */
   public int numberOfChildren(Node node) {
      return operationsHelper.children(node).size();
   }

   /**
    * {@inheritDoc}
    */
   public int numberOfChildren(Node node, String name) {
      return operationsHelper.sublistWithName(operationsHelper.children(node), name).size();
   }

   /**
    * {@inheritDoc}
    */
   public String path(Node node) {
      StringBuilder path = new StringBuilder(node.getNodeName());
      Node parent = node.getParentNode();
      while (parent != null && parent.getNodeType() == Node.ELEMENT_NODE) {
         path.insert(0, '/').insert(0, parent.getNodeName());
         parent = parent.getParentNode();
      }
      return path.toString();
   }
}
