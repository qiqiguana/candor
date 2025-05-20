package net.sf.xisemele.impl;

import java.util.List;

import net.sf.xisemele.api.Attribute;
import net.sf.xisemele.api.Element;
import net.sf.xisemele.api.Value;
import net.sf.xisemele.api.ValueList;
import net.sf.xisemele.exception.ChildNotFoundException;
import net.sf.xisemele.exception.ElementIndexOutOfBoundsException;

import org.w3c.dom.Node;

/**
 * Implementação de {@link Element}.
 * 
 * @author Carlos Eduardo Coral.
 */
class ElementImpl implements Element {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;
   
   /**
    * Instância de {@link Factory} que será usada para criar os objetos retornados pela instância de {@link ElementImpl}.
    */
   private final Factory factory;
   
   /**
    * Instância de {@link Operations} que será usada para manipular elementos da API DOM.
    */
   private final Operations operations;
   
   /**
    * Instância de <code>org.w3c.dom.Node</code> representada pela instância de {@link ElementImpl}.
    */
   private final Node node;

   /**
    * Cria uma nova instância de {@link ElementImpl}.
    * 
    * @param factory
    *       instância de {@link Factory} que será usada para criar os objetos retornados pela instância de {@link ElementImpl}.
    * 
    * @param node
    *       instância de <code>org.w3c.dom.Node</code> que a instância de {@link ElementImpl} irá representar.
    */
   public ElementImpl(final Factory factory, final Node node) {
      this.factory = factory;
      this.operations = factory.createOperations();
      this.node = node;
   }
   
   /**
    * {@inheritDoc}
    */
   public Value attribute(String name) {
      return factory.createValue(operations.attributeValue(node, name));
   }

   /**
    * {@inheritDoc}
    */
   public List<Attribute> attributes() {
      return factory.createAttributes(operations.attributes(node));
   }

   /**
    * {@inheritDoc}
    */
   public Element child(int index) throws ElementIndexOutOfBoundsException {
      Node child = operations.child(node, index);
      if (child == null) {
         throw new ElementIndexOutOfBoundsException(node.getNodeName(), index);
      }
      return factory.createElement(child);
   }

   /**
    * {@inheritDoc}
    */
   public Element child(String name) throws ChildNotFoundException {
      Node child = operations.child(node, name);
      if (child == null) {
         throw new ChildNotFoundException(node.getNodeName(), name);
      }
      return factory.createElement(child);
   }

   /**
    * {@inheritDoc}
    */
   public List<Element> children() {
      return factory.createElements(operations.children(node));
   }

   /**
    * {@inheritDoc}
    */
   public List<Element> children(String name) {
      return factory.createElements(operations.children(node, name));
   }

   /**
    * {@inheritDoc}
    */
   public ValueList childrenValue() {
      return factory.createValueList(operations.children(node));
   }

   /**
    * {@inheritDoc}
    */
   public ValueList childrenValue(String name) {
      return factory.createValueList(operations.children(node, name));
   }

   /**
    * {@inheritDoc}
    */
   public boolean containsAttribute(String name) {
      return operations.containsAttribute(node, name);
   }

   /**
    * {@inheritDoc}
    */
   public boolean containsAttributes() {
      return operations.containsAttributes(node);
   }

   /**
    * {@inheritDoc}
    */
   public boolean containsChild(String name) {
      return operations.containsChild(node, name);
   }

   /**
    * {@inheritDoc}
    */
   public boolean containsChildren() {
      return operations.containsChildren(node);
   }

   /**
    * {@inheritDoc}
    */
   public String name() {
      return node.getNodeName();
   }

   /**
    * {@inheritDoc}
    */
   public Node node() {
      return node;
   }

   /**
    * {@inheritDoc}
    */
   public int numberOfChildren() {
      return operations.numberOfChildren(node);
   }

   /**
    * {@inheritDoc}
    */
   public int numberOfChildren(String name) {
      return operations.numberOfChildren(node, name);
   }

   /**
    * {@inheritDoc}
    */
   public String path() {
      return operations.path(node);
   }

   /**
    * {@inheritDoc}
    */
   public Value value() {
      if (operations.containsChildren(node)) {
         return factory.createNullValue();
      } else {
         return factory.createValue(node.getTextContent());
      }
   }
}
