package net.sf.xisemele.impl;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;

import net.sf.xisemele.api.Result;
import net.sf.xisemele.exception.TransformException;
import net.sf.xisemele.exception.XisemeleIOException;

import org.w3c.dom.Document;

/**
 * Implementação de {@link Result}.
 * 
 * @author Carlos Eduardo Coral.
 */
class ResultImpl implements Result {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Instância de {@link Factory} usada para criar objetos necessários para transformação.
    */
   private final Factory factory;
   
   /**
    * Instância de {@link Validations} usada para verificação de parâmetros.
    */
   private final Validations validations;
   
   /**
    * Instância de <code>org.w3c.dom.Document</code> que será transformada.
    */
   private final Document document;
   
   /**
    * Encoding que será aplicado na transformação.
    */
   private String encoding;
   
   /**
    * Indica se será aplicada a identação ou não. 
    */
   private boolean ident;
   
   /**
    * Quantidade de espaços que será aplicada na identação.
    */
   private int identNumber;
   
   /**
    * Cria uma nova instância de {@link ResultImpl}.
    * 
    * @param factory
    *       instância de {@link Factory} usada para criar objetos necessários para a transformação do XML.
    *       
    * @param document
    *       instância de <code>org.w3c.dom.Document</code> que será transformada.
    */
   public ResultImpl(final Factory factory, final Document document) {
      this.factory = factory;
      this.validations = factory.createValidations();
      this.document = document;
      document.setXmlStandalone(true);
      encoding = validations.getFirstNotNull(document.getXmlEncoding(), "UTF-8");
      ident = false;
      identNumber = 2;
   }
   
   /**
    * {@inheritDoc}
    */
   public Result encoding(String encoding) {
      validations.assertNotNull("encoding", encoding);
      this.encoding = encoding;
      return this;
   }
   
   /**
    * {@inheritDoc}
    * FIXME Remover TEXT_NODE antes de aplicar a identação, para que não mantenha a identação anterior.
    */
   public Result ident(boolean ident) {
      validations.assertNotNull("ident", ident);
      this.ident = ident;
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Result identNumber(int identNumber) {
      validations.assertNotNull("identNumber", identNumber);
      this.identNumber = identNumber;
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Result toFile(String fileName) throws XisemeleIOException {
      validations.assertNotNull("fileName", fileName);
      PrintWriter writer = factory.createPrintWriter(new File(fileName));
      transform(writer);
      writer.close();
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Result toFile(File file) throws XisemeleIOException {
      validations.assertNotNull("file", file);
      PrintWriter writer = factory.createPrintWriter(file);
      transform(writer);
      writer.close();
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public String toXML() throws TransformException {
      StringWriter writer = factory.createStringWriter();
      transform(writer);
      return writer.toString();
   }
   
   /**
    * Transforma o documento XML para a instância de <code>java.io.Writer</code> especificada por parâmetro.
    * 
    * @param writer
    *       instância de <code>java.io.Writer</i> em que o XML transformado será escrito.
    */
   private void transform(Writer writer) {
      try {
         Transformer transformer = factory.createTransformer();
         transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
         transformer.setOutputProperty(OutputKeys.INDENT, ident ? "yes" : "no");
         transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(identNumber));
         transformer.transform(factory.createDOMSource(document), factory.createStreamResult(writer));
      } catch (TransformerException e) {
         throw new TransformException(e);
      }     
   }
}
