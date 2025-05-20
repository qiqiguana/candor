package net.sf.xisemele.impl;

import java.util.Date;

import net.sf.xisemele.api.Formatter;
import net.sf.xisemele.api.Result;
import net.sf.xisemele.exception.AttributeNotPermittedException;
import net.sf.xisemele.exception.ElementNotFoundException;
import net.sf.xisemele.exception.InvalidNameException;
import net.sf.xisemele.exception.NotWithinContextException;
import net.sf.xisemele.exception.RemoveRootNotPermittedException;
import net.sf.xisemele.exception.RootDuplicateException;
import net.sf.xisemele.exception.ValueNotPermittedException;
import net.sf.xisemele.exception.WithinContextDuplicateException;
import net.sf.xisemele.exception.WithinContextNotPermittedException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Implementação de {@link WriterEditor}.
 * 
 * @author Carlos Eduardo Coral.
 */
class WriterEditorImpl implements WriterEditor {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Instância de {@link Factory} que será usada para criar objetos do Xisemele.
    */
   private final Factory factory;
   
   /**
    * Instância de {@link FormatterProvider} que será usada para obter uma instância de {@link Formatter} para determinado tipo.
    */
   private final FormatterProvider formatterProvider;
   
   /**
    * Instância de {@link Operations} usada para tratar objetos da API DOM.
    */
   private final Operations operations;
   
   /**
    * Instância de {@link Validations} usada para verificar os parâmetros de entrada.
    */
   private final Validations validations;
   
   /**
    * Instância de {@link WithinContext} que será usada para gerenciar o contexto <i>within</i>.
    */
   private final WithinContext withinContext;
   
   /**
    * Instância de {@link Result} correspondente ao documento XML escrito/editado.
    */
   private final Result result;
   
   /**
    * Instância de <code>org.w3c.dom.Node</code> corrente.
    */
   private Node node;

   /**
    * Cria uma nova instância de {@link WriterEditorImpl}.
    * 
    * @param factory
    *       instância de {@link Factory} que será usada para criar objetos do Xisemele.
    * 
    * @param provider
    *       instância de {@link FormatterProvider} que será usada para obter uma instância de {@link Formatter} para determinado
    *       tipo.
    * 
    * @param document
    *       instância de <code>org.w3c.dom.Document</code> correspondente ao novo XML que será escrito.
    *       
    * @param rootName
    *       <code>java.lang.String</code> contendo o nome que será atribuído ao elemento raiz do documento XML.
    */
   public WriterEditorImpl(final Factory factory, 
                           final FormatterProvider provider,
                           final Document document, 
                           final String rootName) {
      this.factory = factory;
      this.formatterProvider = provider;
      this.withinContext = factory.createWithinContext();
      this.operations = factory.createOperations();
      this.validations = factory.createValidations();
      this.result = factory.createResult(document);
      node = document.createElement(rootName);
      document.appendChild(node);
   }

   /**
    * Cria uma nova instância de {@link WriterEditorImpl}.
    * 
    * @param factory
    *       instância de {@link Factory} que será usada para criar objetos do Xisemele.
    * 
    * @param provider
    *       instância de {@link FormatterProvider} que será usada para obter uma instância de {@link Formatter} para determinado tipo.
    *       
    * @param document
    *       instância de <code>org.w3c.dom.Document</code> correspondente ao XML que será escrito/editado.
    */
   public WriterEditorImpl(final Factory factory,
                           final FormatterProvider provider, 
                           final Document document) {
      this.factory = factory;
      this.formatterProvider = provider;
      this.withinContext = factory.createWithinContext();
      this.operations = factory.createOperations();
      this.validations = factory.createValidations();
      this.result = factory.createResult(document);
      node = document.getDocumentElement();
   }

   /**
    * {@inheritDoc}
    */
   public Document document() {
      return node.getOwnerDocument();
   }

   /**
    * {@inheritDoc}
    */
   public Node getCurrent() {
      return node;
   }

   /**
    * {@inheritDoc}
    */
   public Result result() {
      return result;
   }

   /**
    * {@inheritDoc}
    */
   public boolean containsElement(String path) {
      return operations.containsElement(document(), path);
   }

   /**
    * {@inheritDoc}
    */
   public void within() throws WithinContextDuplicateException, WithinContextNotPermittedException {
      if (withinContext.enabled()) {
         throw new WithinContextDuplicateException();
      } else if (operations.containsValue(node)) {
         throw new WithinContextNotPermittedException(node.getNodeName());
      }
      withinContext.start();
   }
   
   /**
    * {@inheritDoc}
    */
   public void endWithin() throws NotWithinContextException {
      if (withinContext.enabled()) {
         withinContext.stop();
      } else if (operations.isRootElement(node)) {
         throw new NotWithinContextException();
      } else {
         node = node.getParentNode();
      }
   }
   
   /**
    * {@inheritDoc}
    */
   public void element(final String name) throws RootDuplicateException, InvalidNameException {
      validations.assertValidName(name);
      append(document().createElement(name));
   }

   /**
    * {@inheritDoc}
    */
   public void node(final Node node) throws RootDuplicateException {
      append(document().importNode(node, true));
   }
   
   /**
    * {@inheritDoc}
    */
   public void value(final Object value) throws ValueNotPermittedException {
      if (operations.containsChildren(node) || withinContext.enabled()) {
         throw new ValueNotPermittedException(node.getNodeName());
      } else if (value != null) {
         node.setTextContent(formatValue(value));
      }
   }
   
   /**
    * {@inheritDoc}
    */
   public void value(final Date date, final String pattern) throws ValueNotPermittedException {
      if (operations.containsChildren(node)) {
         throw new ValueNotPermittedException(node.getNodeName());
      } else {
         node.setTextContent(factory.createDateFormatter(pattern).format(date));
      }
   }
   
   /**
    * {@inheritDoc}
    */
   public void attribute(final String name, final Object value) throws AttributeNotPermittedException, InvalidNameException {
      validations.assertValidName(name);
      if (withinContext.enabled()) {
         throw new AttributeNotPermittedException();
      } 
      // FIXME Não adicionar atributo se o valor for null.
      attribute(name, formatValue(value));
   }
   
   /**
    * {@inheritDoc}
    */
   public void attribute(final String name, final Date value, final String pattern) 
      throws AttributeNotPermittedException, InvalidNameException {
      validations.assertValidName(name);
      if (withinContext.enabled()) {
         throw new AttributeNotPermittedException();
      }
      attribute(name, factory.createDateFormatter(pattern).format(value));
   }

   /**
    * {@inheritDoc}
    */
   public void remove() throws RemoveRootNotPermittedException {
      if (operations.isRootElement(node)) {
         throw new RemoveRootNotPermittedException(node.getNodeName());
      } else {
         Node toRemove = node;
         node = node.getParentNode();
         node.removeChild(toRemove);
      }
   }
   
   /**
    * {@inheritDoc}
    */
   public void removeChild(final String name) {
      Node child = operations.child(node, name);
      if (child != null) {
         node.removeChild(child);
      }
   }
   
   /**
    * {@inheritDoc}
    */
   public void removeChildren(final String name) {
      for (Node child : operations.children(node, name)) {
         node.removeChild(child);
      }
   }
   
   /**
    * {@inheritDoc}
    */
   public void removeChildren() {
      for (Node child : operations.children(node)) {
         node.removeChild(child);
      }
   }
   
   /**
    * {@inheritDoc}
    */
   public void removeAttribute(final String name) {
      node.getAttributes().removeNamedItem(name);
   }
   
   /**
    * {@inheritDoc}
    */
   public void removeAttributes() {
      NamedNodeMap attributes = node.getAttributes();
      for (Node attribute : operations.attributes(node)) {
         attributes.removeNamedItem(attribute.getNodeName());
      }
   }
   
   /**
    * {@inheritDoc}
    */
   public void rename(String newName) throws InvalidNameException {
      validations.assertValidName(newName);
      node = document().renameNode(node, null, newName);
   }

   /**
    * {@inheritDoc}
    */
   public void defineAsCurrent(final String path) throws ElementNotFoundException {
      node = operations.find(document(), path);
   }

   /**
    * Adiciona a instância de <code>org.w3c.dom.Node</code> no nível corrente do documento XML.
    * 
    * @param newElement
    *       instância de <code>org.w3c.dom.Node</code> que será adicionada.
    *       
    * @return
    *       instância corrente de {@link WriterEditorImpl}.
    *       
    * @throws RootDuplicateException
    *       exceção disparada quando tentar adicionar mais de um elemento raiz no documento XML.
    */
   private void append(Node newElement) throws RootDuplicateException {
      if (withinContext.enabled()) {
         node.appendChild(newElement);
         withinContext.stop();
      } else if (operations.isRootElement(node)) {
         throw new RootDuplicateException(node.getNodeName(), newElement.getNodeName());
      } else {
         node.getParentNode().appendChild(newElement);
      }
      node = newElement;
   }
   
   /**
    * Adiciona o atributo com o nome e valor especificados para o elemento corrente que está sendo lido/editado.
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome do atributo que será adicionado.
    *       
    * @param formattedValue
    *       <code>java.lang.String</code> contendo o valor formatado que será atribuído ao novo atributo.
    */
   private void attribute(final String name, String formattedValue) {
      Node attribute = document().createAttribute(name);
      attribute.setNodeValue(formattedValue);
      node.getAttributes().setNamedItem(attribute);
   }
   
   /**
    * Formata para <code>java.lang.String</code> o valor especificado por parâmetro.
    * 
    * @param value
    *       objeto que será formatado para <code>java.lang.String</code>.
    *       
    * @return
    *       valor formatado.
    */
   @SuppressWarnings("unchecked")
   private String formatValue(Object value) {
      Formatter<Object> formatter = formatterProvider.getFormatter((Class<Object>) value.getClass());
      if (formatter != null) {
         return formatter.format(value);
      }
      return value.toString();
   }
}
