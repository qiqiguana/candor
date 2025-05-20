package net.sf.xisemele.impl;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.sf.xisemele.api.Attribute;
import net.sf.xisemele.api.Editor;
import net.sf.xisemele.api.Element;
import net.sf.xisemele.api.Formatter;
import net.sf.xisemele.api.Reader;
import net.sf.xisemele.api.Result;
import net.sf.xisemele.api.Value;
import net.sf.xisemele.api.ValueList;
import net.sf.xisemele.api.Writer;
import net.sf.xisemele.exception.XisemeleIOException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;

/**
 * Implementação de {@link Factory}.
 * 
 * @author Carlos Eduardo Coral.
 */
class FactoryImpl implements Factory {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Instância de {@link FormatterProvider} que será atribuída aos objetos criados.
    */
   private final FormatterProvider formatterProvider;

   /**
    * Cria uma nova instância de {@link FactoryImpl}.
    * 
    * @param formatterProvider
    *       instância de {@link FormatterProvider} que será delegada aos objetos criados na fábrica.
    */
   public FactoryImpl(final FormatterProvider formatterProvider) {
      this.formatterProvider = formatterProvider;
   }
   
   /**
    * {@inheritDoc}
    */
   public List<Attribute> createAttributes(List<Node> nodes) {
      List<Attribute> attributes = new ArrayList<Attribute>();
      for (Node node : nodes) {
         attributes.add(new AttributeImpl(node.getNodeName(), new ValueImpl(this, formatterProvider, node.getNodeValue())));
      }
      return attributes;
   }

   /**
    * {@inheritDoc}
    */
   public DOMSource createDOMSource(Document document) {
      return new DOMSource(document);
   }

   /**
    * {@inheritDoc}
    */
   public Formatter<Date> createDateFormatter(String datePattern) {
      return new DateFormatter(datePattern);
   }
   
   /**
    * {@inheritDoc}.
    */
   public Result createResult(Document document) {
      return new ResultImpl(this, document);
   }

   /**
    * {@inheritDoc}
    */
   public DocumentBuilder createDocumentBuilder() {
      DocumentBuilderFactory factory = new DocumentBuilderFactoryImpl();
      factory.setNamespaceAware(true);
      factory.setIgnoringComments(true);
      factory.setValidating(false);
      try {
         DocumentBuilder builder = factory.newDocumentBuilder();
         builder.setErrorHandler(new NullErrorHandler());
         return builder;
      } catch (ParserConfigurationException e) {
         throw new InternalError(e.getMessage());
      }
   }

   /**
    * {@inheritDoc}
    */
   public Editor createEditor(Document document) {
      return new EditorImpl(new WriterEditorImpl(this, formatterProvider, document));
   }

   /**
    * {@inheritDoc}
    */
   public Element createElement(Node node) {
      return new ElementImpl(this, node);
   }

   /**
    * {@inheritDoc}
    */
   public List<Element> createElements(List<Node> nodes) {
      ArrayList<Element> elements = new ArrayList<Element>();
      for (Node node : nodes) {
         elements.add(createElement(node));
      }
      return elements;
   }

   /**
    * {@inheritDoc}
    */
   public Message createMessage() {
      return new MessageImpl();
   }

   /**
    * {@inheritDoc}
    */
   public Value createNullValue() {
      return NullValue.INSTANCE;
   }

   /**
    * {@inheritDoc}
    */
   public PrintWriter createPrintWriter(File file) throws XisemeleIOException {
      try {
         return new PrintWriter(file);
      } catch (Exception e) {
         throw new XisemeleIOException(file, e);
      }
   }

   /**
    * {@inheritDoc}
    */
   public Reader createReader(Document document) {
      return new ReaderImpl(this, document);
   }

   /**
    * {@inheritDoc}
    */
   public StreamResult createStreamResult(java.io.Writer writer) {
      return new StreamResult(writer);
   }

   /**
    * {@inheritDoc}
    */
   public StringWriter createStringWriter() {
      return new StringWriter();
   }

   /**
    * {@inheritDoc}
    */
   public Transformer createTransformer() throws TransformerConfigurationException, TransformerFactoryConfigurationError {
      return TransformerFactory.newInstance().newTransformer();
   }

   /**
    * {@inheritDoc}
    */
   public Value createValue(String value) {
      return new ValueImpl(this, formatterProvider, value);
   }

   /**
    * {@inheritDoc}
    */
   public ValueList createValueList(List<Node> nodes) {
      ArrayList<Value> values = new ArrayList<Value>();
      for (Node node : nodes) {
         values.add(createValue(node.getTextContent()));
      }
      return new ValueListImpl(values);
   }

   /**
    * {@inheritDoc}
    */
   public Writer createWriter(Document document, String rootElement) {
      return new WriterImpl(new WriterEditorImpl(this, formatterProvider, document, rootElement));
   }

   /**
    * {@inheritDoc}
    */
   public WithinContext createWithinContext() {
      return new WithinContextImpl();
   }

   /**
    * {@inheritDoc}
    */
   public Operations createOperations() {
      return new OperationsImpl(new OperationsHelperImpl());
   }

   /**
    * {@inheritDoc}
    */
   public Validations createValidations() {
      return new ValidationsImpl(this);
   }
}
