package net.sf.xisemele.impl;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;

import net.sf.xisemele.api.Editor;
import net.sf.xisemele.api.Formatter;
import net.sf.xisemele.api.Reader;
import net.sf.xisemele.api.Writer;
import net.sf.xisemele.exception.ParseXMLException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Casos de teste para a classe {@link XisemeleImpl}.
 * 
 * @author Carlos Eduardo Coral.
 */
public class XisemeleImplTest {

   /**
    * Testa o método {@link XisemeleImpl#createReader(String)}.
    */
   @Test
   public void testCreateReaderForXML() throws Exception {
      Document document = createNiceMock(Document.class);
      
      DocumentBuilder builder = org.easymock.classextension.EasyMock.createStrictMock(DocumentBuilder.class);
      org.easymock.classextension.EasyMock
         .expect(builder.parse((InputSource) org.easymock.classextension.EasyMock.notNull())).andReturn(document);
      org.easymock.classextension.EasyMock.replay(builder);
      
      Reader reader = createNiceMock(Reader.class);
      
      Validations validations = createStrictMock(Validations.class);
      validations.assertNotNull("xml", "xml");
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createDocumentBuilder()).andReturn(builder);
      expect(factory.createReader(document)).andReturn(reader);
      replay(factory);
      
      XisemeleImpl xisemele = new XisemeleImpl(factory, createNiceMock(FormatterProvider.class));
      
      assertSame(reader, xisemele.createReader("xml"));
      
      org.easymock.classextension.EasyMock.verify(builder);
      verify(factory, validations);
   }
   
   /**
    * Testa se dispara a exceção {@link ParseXMLException} ao chamar o método {@link XisemeleImpl#createReader(String)}.
    */
   @Test(expected = ParseXMLException.class)
   public void testCreateReaderForXMLWithParseXMLException() throws Exception {
      DocumentBuilder builder = org.easymock.classextension.EasyMock.createStrictMock(DocumentBuilder.class);
      org.easymock.classextension.EasyMock.expect(
            builder.parse((InputSource) org.easymock.classextension.EasyMock.notNull())).andThrow(new IOException());
      org.easymock.classextension.EasyMock.replay(builder);
      
      Validations validations = createStrictMock(Validations.class);
      validations.assertNotNull("xml", "xml");
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createDocumentBuilder()).andReturn(builder);
      replay(factory);
      
      XisemeleImpl xisemele = new XisemeleImpl(factory, createNiceMock(FormatterProvider.class));
      
      xisemele.createReader("xml");
   }
   
   /**
    * Testa o método {@link XisemeleImpl#createReader(File)}.
    */
   @Test
   public void testCreateReaderForFile() throws Exception  {
      File file = org.easymock.classextension.EasyMock.createNiceMock(File.class);
      
      Document document = createNiceMock(Document.class);
      
      DocumentBuilder builder = org.easymock.classextension.EasyMock.createStrictMock(DocumentBuilder.class);
      org.easymock.classextension.EasyMock.expect(builder.parse(file)).andReturn(document);
      org.easymock.classextension.EasyMock.replay(builder);
      
      Reader reader = createNiceMock(Reader.class);
      
      Validations validations = createStrictMock(Validations.class);
      validations.assertNotNull("file", file);
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createDocumentBuilder()).andReturn(builder);
      expect(factory.createReader(document)).andReturn(reader);
      replay(factory);
      
      XisemeleImpl xisemele = new XisemeleImpl(factory, createNiceMock(FormatterProvider.class));
      
      assertSame(reader, xisemele.createReader(file));
      
      org.easymock.classextension.EasyMock.verify(builder);
      verify(factory, validations);
   }

   /**
    * Testa se dispara a exceção {@link ParseXMLException} ao chamar o método {@link XisemeleImpl#createReader(File)}.
    */
   @Test(expected = ParseXMLException.class)
   public void testCreateReaderForFileWithParseXMLException() throws Exception {
      File file = org.easymock.classextension.EasyMock.createNiceMock(File.class);
      
      DocumentBuilder builder = org.easymock.classextension.EasyMock.createStrictMock(DocumentBuilder.class);
      org.easymock.classextension.EasyMock.expect(builder.parse(file)).andThrow(new IOException());
      org.easymock.classextension.EasyMock.replay(builder);
      
      Validations validations = createStrictMock(Validations.class);
      validations.assertNotNull("file", file);
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createDocumentBuilder()).andReturn(builder);
      replay(factory);

      XisemeleImpl xisemele = new XisemeleImpl(factory, createNiceMock(FormatterProvider.class));
      
      xisemele.createReader(file);
   }
   
   /**
    * Testa o método {@link XisemeleImpl#createReader(InputStream)}.
    */
   @Test
   public void testCreateReaderForInputStream() throws Exception {
      InputStream input = org.easymock.classextension.EasyMock.createNiceMock(InputStream.class);
      
      Document document = createNiceMock(Document.class);
      
      DocumentBuilder builder = org.easymock.classextension.EasyMock.createStrictMock(DocumentBuilder.class);
      org.easymock.classextension.EasyMock.expect(builder.parse(input)).andReturn(document);
      org.easymock.classextension.EasyMock.replay(builder);
      
      Reader reader = createNiceMock(Reader.class);
      
      Validations validations = createStrictMock(Validations.class);
      validations.assertNotNull("inputStream", input);
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createDocumentBuilder()).andReturn(builder);
      expect(factory.createReader(document)).andReturn(reader);
      replay(factory);
      
      XisemeleImpl xisemele = new XisemeleImpl(factory, createNiceMock(FormatterProvider.class));
      
      assertSame(reader, xisemele.createReader(input));
      
      org.easymock.classextension.EasyMock.verify(builder);
      verify(factory, validations);
   }
   
   /**
    * Testa se dispara a exceção {@link ParseXMLException} ao chamar o método {@link XisemeleImpl#createReader(InputStream)}.
    */
   @Test(expected = ParseXMLException.class)
   public void testCreateReaderForInputStreamWithParseXMLException() throws Exception {
      InputStream input = org.easymock.classextension.EasyMock.createNiceMock(InputStream.class);
      
      DocumentBuilder builder = org.easymock.classextension.EasyMock.createStrictMock(DocumentBuilder.class);
      org.easymock.classextension.EasyMock.expect(builder.parse(input)).andThrow(new IOException());
      org.easymock.classextension.EasyMock.replay(builder);
      
      Validations validations = createStrictMock(Validations.class);
      validations.assertNotNull("inputStream", input);
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createDocumentBuilder()).andReturn(builder);
      replay(factory);
      
      XisemeleImpl xisemele = new XisemeleImpl(factory, createNiceMock(FormatterProvider.class));
      
      xisemele.createReader(input);  
   }
   
   /**
    * Testa o método {@link XisemeleImpl#createEditor(String)}.
    */
   @Test
   public void testCreateEditorForXML() throws Exception {
      Document document = createNiceMock(Document.class);
      
      DocumentBuilder builder = org.easymock.classextension.EasyMock.createStrictMock(DocumentBuilder.class);
      org.easymock.classextension.EasyMock.expect(
            builder.parse((InputSource) org.easymock.classextension.EasyMock.notNull())).andReturn(document);
      org.easymock.classextension.EasyMock.replay(builder);
      
      Editor editor = createNiceMock(Editor.class);
      
      Validations validations = createStrictMock(Validations.class);
      validations.assertNotNull("xml", "xml");
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createDocumentBuilder()).andReturn(builder);
      expect(factory.createEditor(document)).andReturn(editor);
      replay(factory);
      
      XisemeleImpl xisemele = new XisemeleImpl(factory, createNiceMock(FormatterProvider.class));
      
      assertSame(editor, xisemele.createEditor("xml"));
      
      org.easymock.classextension.EasyMock.verify(builder);
      verify(factory, validations);      
   }
   
   /**
    * Testa se dispara a exceção {@link ParseXMLException} ao chamar o método {@link XisemeleImpl#createEditor(String)}.
    */
   @Test(expected = ParseXMLException.class)
   public void testCreateEditorForXMLWithParseXMLException() throws Exception {
      DocumentBuilder builder = org.easymock.classextension.EasyMock.createStrictMock(DocumentBuilder.class);
      org.easymock.classextension.EasyMock.expect(
            builder.parse((InputSource) org.easymock.classextension.EasyMock.notNull())).andThrow(new IOException());
      org.easymock.classextension.EasyMock.replay(builder);
      
      Validations validations = createStrictMock(Validations.class);
      validations.assertNotNull("xml", "xml");
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createDocumentBuilder()).andReturn(builder);
      replay(factory);
      
      XisemeleImpl xisemele = new XisemeleImpl(factory, createNiceMock(FormatterProvider.class));
      
      xisemele.createEditor("xml");
   }
   
   /**
    * Testa o método {@link XisemeleImpl#createEditor(File)}.
    */
   @Test
   public void testCreateEditorForFile() throws Exception {
      File file = org.easymock.classextension.EasyMock.createNiceMock(File.class);
      
      Document document = createNiceMock(Document.class);
      
      DocumentBuilder builder = org.easymock.classextension.EasyMock.createStrictMock(DocumentBuilder.class);
      org.easymock.classextension.EasyMock.expect(builder.parse(file)).andReturn(document);
      org.easymock.classextension.EasyMock.replay(builder);
      
      Editor editor = createNiceMock(Editor.class);
      
      Validations validations = createStrictMock(Validations.class);
      validations.assertNotNull("file", file);
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createDocumentBuilder()).andReturn(builder);
      expect(factory.createEditor(document)).andReturn(editor);
      replay(factory);
      
      XisemeleImpl xisemele = new XisemeleImpl(factory, createNiceMock(FormatterProvider.class));
      
      assertSame(editor, xisemele.createEditor(file));
      
      org.easymock.classextension.EasyMock.verify(builder);
      verify(factory, validations);      
   }
   
   /**
    * Testa se dispara a exceção {@link ParseXMLException} ao chamar o método {@link XisemeleImpl#createEditor(File)}.
    * @throws Exception
    */
   @Test(expected = ParseXMLException.class)
   public void testCreateEditorForFileWithParseXMLException() throws Exception {
      File file = org.easymock.classextension.EasyMock.createNiceMock(File.class);
      
      DocumentBuilder builder = org.easymock.classextension.EasyMock.createStrictMock(DocumentBuilder.class);
      org.easymock.classextension.EasyMock.expect(builder.parse(file)).andThrow(new IOException());
      org.easymock.classextension.EasyMock.replay(builder);
      
      Validations validations = createStrictMock(Validations.class);
      validations.assertNotNull("file", file);
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createDocumentBuilder()).andReturn(builder);
      replay(factory);
      
      XisemeleImpl xisemele = new XisemeleImpl(factory, createNiceMock(FormatterProvider.class));
      
      xisemele.createEditor(file);
   }
   
   /**
    * Testa o método {@link XisemeleImpl#createEditor(InputStream)}.
    */
   @Test
   public void testCreateEditorForInputStream() throws Exception {
      InputStream input = org.easymock.classextension.EasyMock.createNiceMock(InputStream.class);
      
      Document document = createNiceMock(Document.class);
      
      DocumentBuilder builder = org.easymock.classextension.EasyMock.createStrictMock(DocumentBuilder.class);
      org.easymock.classextension.EasyMock.expect(builder.parse(input)).andReturn(document);
      org.easymock.classextension.EasyMock.replay(builder);
      
      Editor editor = createNiceMock(Editor.class);
      
      Validations validations = createStrictMock(Validations.class);
      validations.assertNotNull("inputStream", input);
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createDocumentBuilder()).andReturn(builder);
      expect(factory.createEditor(document)).andReturn(editor);
      replay(factory);
      
      XisemeleImpl xisemele = new XisemeleImpl(factory, createNiceMock(FormatterProvider.class));
      
      assertSame(editor, xisemele.createEditor(input));
      
      org.easymock.classextension.EasyMock.verify(builder);
      verify(factory, validations);      
   }

   /**
    * Testa se dispara a exceção {@link ParseXMLException} ao chamar o método {@link XisemeleImpl#createEditor(InputStream)}.
    */
   @Test(expected = ParseXMLException.class)
   public void testCreateEditorForInputStreamWithParseXMLException() throws Exception {
      InputStream input = org.easymock.classextension.EasyMock.createNiceMock(InputStream.class);
      
      DocumentBuilder builder = org.easymock.classextension.EasyMock.createStrictMock(DocumentBuilder.class);
      org.easymock.classextension.EasyMock.expect(builder.parse(input)).andThrow(new IOException());
      org.easymock.classextension.EasyMock.replay(builder);
      
      Validations validations = createStrictMock(Validations.class);
      validations.assertNotNull("inputStream", input);
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createDocumentBuilder()).andReturn(builder);
      replay(factory);
      
      XisemeleImpl xisemele = new XisemeleImpl(factory, createNiceMock(FormatterProvider.class));
      
      xisemele.createEditor(input);
   }
   
   /**
    * Testa o método {@link XisemeleImpl#createWriter(String)}.
    */
   @Test
   public void testCreateWriter() {
      Document document = createNiceMock(Document.class);
      
      DocumentBuilder builder = org.easymock.classextension.EasyMock.createStrictMock(DocumentBuilder.class);
      org.easymock.classextension.EasyMock.expect(builder.newDocument()).andReturn(document);
      org.easymock.classextension.EasyMock.replay(builder);
      
      Writer writer = createNiceMock(Writer.class);
      
      Validations validations = createStrictMock(Validations.class);
      validations.assertNotNull("rootElement", "root");
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createDocumentBuilder()).andReturn(builder);
      expect(factory.createWriter(document, "root")).andReturn(writer);
      replay(factory);
      
      XisemeleImpl xisemele = new XisemeleImpl(factory, createNiceMock(FormatterProvider.class));
      
      assertSame(writer, xisemele.createWriter("root"));
      
      org.easymock.classextension.EasyMock.verify(builder);
      verify(factory, validations);
   }
   
   /**
    * Testa o método {@link XisemeleImpl#createWriter(String, String)}.
    */
   @Test
   public void testCreateWriterForXmlVersion() {
      Document document = createStrictMock(Document.class);
      document.setXmlVersion("1.0");
      replay(document);
      
      DocumentBuilder builder = org.easymock.classextension.EasyMock.createStrictMock(DocumentBuilder.class);
      org.easymock.classextension.EasyMock.expect(builder.newDocument()).andReturn(document);
      org.easymock.classextension.EasyMock.replay(builder);
      
      Writer writer = createNiceMock(Writer.class);
      
      Validations validations = createStrictMock(Validations.class);
      validations.assertNotNull("rootElement", "root", "xmlVersion", "1.0");
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createDocumentBuilder()).andReturn(builder);
      expect(factory.createWriter(document, "root")).andReturn(writer);
      replay(factory);
      
      XisemeleImpl xisemele = new XisemeleImpl(factory, createNiceMock(FormatterProvider.class));
      
      assertSame(writer, xisemele.createWriter("root", "1.0"));
      
      org.easymock.classextension.EasyMock.verify(builder);
      verify(document, factory, validations);
   }
   
   /**
    * Testa o método {@link XisemeleImpl#setDatePattern(String)}.
    */
   @Test
   @SuppressWarnings("unchecked")
   public void testSetDatePattern() {
      Formatter formatter = createNiceMock(Formatter.class);

      Validations validations = createStrictMock(Validations.class);
      validations.assertNotNull("datePattern", "pattern");
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createDateFormatter("pattern")).andReturn(formatter);
      replay(factory);
      
      FormatterProvider provider = createStrictMock(FormatterProvider.class);
      provider.setFormatter(Date.class, formatter);
      replay(provider);

      XisemeleImpl xisemele = new XisemeleImpl(factory, provider);
      
      assertSame(xisemele, xisemele.setDatePattern("pattern"));
      
      verify(factory, provider);
   }
   
   /**
    * Testa o método {@link XisemeleImpl#setFormatter(Formatter)}.
    */
   @Test
   @SuppressWarnings("unchecked")
   public void testSetFormatter() {
      Formatter formatter = createStrictMock(Formatter.class);
      expect(formatter.type()).andReturn((Class) Integer.class);
      replay(formatter);
      
      Validations validations = createStrictMock(Validations.class);
      validations.assertNotNull("formatter", formatter);
      replay(validations);

      Factory factory = createStrictMock(Factory.class);
      expect(factory.createValidations()).andReturn(validations);
      replay(factory);
      
      FormatterProvider provider = createStrictMock(FormatterProvider.class);
      provider.setFormatter(Integer.class, formatter);
      replay(provider);
      
      XisemeleImpl xisemele = new XisemeleImpl(factory, provider);
      
      assertSame(xisemele, xisemele.setFormatter(formatter));
      
      verify(formatter, provider, validations, factory);
   }
}
