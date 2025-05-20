package net.sf.xisemele.impl;

import net.sf.xisemele.api.Element;
import net.sf.xisemele.api.Reader;
import net.sf.xisemele.exception.ElementNotFoundException;

import org.w3c.dom.Document;

/**
 * Implementação de {@link Reader}.
 * 
 * @author Carlos Eduardo Coral.
 */
class ReaderImpl implements Reader {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Instância de <code>org.w3c.dom.Document</code> que será lida.
    */
   private final Document document;

   /**
    * Instância de {@link Factory} usada para obter novas instâncias de {@link Element}.
    */
   private final Factory factory;
   
   /**
    * Instância de {@link Operations} usada para manipular elementos da API DOM.
    */
   private final Operations operations;

   /**
    * Cria uma nova instância de {@link ReaderImpl}.
    * 
    * @param factory
    *       instância de {@link Factory} que será usada para obter novas instâncias de {@link Element}.
    *       
    * @param document
    *       instância de <code>org.w3c.dom.Document</code> que será lida.
    */
   public ReaderImpl(final Factory factory, final Document document) {
      this.document = document;
      this.factory = factory;
      this.operations = factory.createOperations();
   }
   
   /**
    * {@inheritDoc}
    */
   public boolean containsElement(String path) {
      return operations.containsElement(document, path);
   }

   /**
    * {@inheritDoc}
    */
   public Element find(String path) throws ElementNotFoundException {
      return factory.createElement(operations.find(document, path));
   }

   /**
    * {@inheritDoc}
    */
   public Element root() {
      return factory.createElement(document.getDocumentElement());
   }

   /**
    * {@inheritDoc}
    */
   public String version() {
      return document.getXmlVersion();
   }
   
   /**
    * {@inheritDoc}
    */
   public String encoding() {
      return document.getXmlEncoding();
   }
}
