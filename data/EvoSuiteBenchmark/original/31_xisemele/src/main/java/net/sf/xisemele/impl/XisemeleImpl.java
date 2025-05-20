package net.sf.xisemele.impl;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Date;

import net.sf.xisemele.api.Editor;
import net.sf.xisemele.api.Formatter;
import net.sf.xisemele.api.Reader;
import net.sf.xisemele.api.Writer;
import net.sf.xisemele.api.Xisemele;
import net.sf.xisemele.exception.ParseXMLException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Implementação de {@link Xisemele}.
 * 
 * @author Carlos Eduardo Coral.
 */
class XisemeleImpl implements Xisemele {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Instância de {@link Factory} que será usada para criar objetos do Xisemele.
    */
   private final Factory factory;
   
   /**
    * Instância de {@link FormatterProvider} que será usada para configurar e recuperar instâncias de {@link Formatter}.
    */
   private final FormatterProvider formatterProvider;
   
   /**
    * Instância de {@link Validations} usada para verificar os parâmetros dos métodos.
    */
   private final Validations validations;
   
   /**
    * Cria uma nova instância de {@link XisemeleImpl}.
    * 
    * @param factory
    *       instância de {@link Factory} que será usada para criar instâncias de {@link Reader}, {@link Writer} e {@link Editor}.
    * 
    * @param formatterProvider
    *       instância de {@link FormatterProvider} que será usada para configurar e recuperar instâncias de {@link Formatter}.
    */
   public XisemeleImpl(final Factory factory, final FormatterProvider formatterProvider) {
      this.factory = factory;
      this.formatterProvider = formatterProvider;
      this.validations = factory.createValidations();
   }
   
   /**
    * {@inheritDoc}
    */
   public Reader createReader(String xml) throws ParseXMLException {
      try {
         validations.assertNotNull("xml", xml);
         return factory.createReader(factory.createDocumentBuilder().parse(new InputSource(new StringReader(xml))));
      } catch (Exception e) {
         throw new ParseXMLException(e);
      }
   }

   /**
    * {@inheritDoc}
    */
   public Reader createReader(File file) throws ParseXMLException {
      try {
         validations.assertNotNull("file", file);
         return factory.createReader(factory.createDocumentBuilder().parse(file));
      } catch (Exception e) {
         throw new ParseXMLException(e);
      }
   }

   /**
    * {@inheritDoc}
    */
   public Reader createReader(InputStream inputStream) throws ParseXMLException {
      try {
         validations.assertNotNull("inputStream", inputStream);
         return factory.createReader(factory.createDocumentBuilder().parse(inputStream));
      } catch (Exception e) {
         throw new ParseXMLException(e);
      }
   }

   /**
    * {@inheritDoc}
    */
   public Editor createEditor(String xml) throws ParseXMLException {
      try {
         validations.assertNotNull("xml", xml);
         return factory.createEditor(factory.createDocumentBuilder().parse(new InputSource(new StringReader(xml))));
      } catch (Exception e) {
         throw new ParseXMLException(e);
      }
   }
   
   /**
    * {@inheritDoc}
    */
   public Editor createEditor(File file) throws ParseXMLException {
      try {
         validations.assertNotNull("file", file);
         return factory.createEditor(factory.createDocumentBuilder().parse(file));
      } catch (Exception e) {
         throw new ParseXMLException(e);
      }
   }

   /**
    * {@inheritDoc}
    */
   public Editor createEditor(InputStream inputStream) throws ParseXMLException {
      try {
         validations.assertNotNull("inputStream", inputStream);
         return factory.createEditor(factory.createDocumentBuilder().parse(inputStream));
      } catch (Exception e) {
         throw new ParseXMLException(e);
      }
   }

   /**
    * {@inheritDoc}
    */
   public Writer createWriter(String rootElement) {
      validations.assertNotNull("rootElement", rootElement);
      return factory.createWriter(factory.createDocumentBuilder().newDocument(), rootElement);
   }

   /**
    * {@inheritDoc}
    */
   public Writer createWriter(String rootElement, String xmlVersion) {
      validations.assertNotNull("rootElement", rootElement, "xmlVersion", xmlVersion);
      Document document = factory.createDocumentBuilder().newDocument();
      document.setXmlVersion(xmlVersion);
      return factory.createWriter(document, rootElement);
   }

   /**
    * {@inheritDoc}
    */
   public Xisemele setDatePattern(String datePattern) {
      validations.assertNotNull("datePattern", datePattern);
      formatterProvider.setFormatter(Date.class, factory.createDateFormatter(datePattern));
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Xisemele setFormatter(Formatter<?> formatter) {
      validations.assertNotNull("formatter", formatter);
      formatterProvider.setFormatter(formatter.type(), formatter);
      return this;
   }
}
