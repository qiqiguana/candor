package net.sf.xisemele.impl;

import static org.easymock.EasyMock.*;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.w3c.dom.Document;

/**
 * Casos de teste para a classe {@link ResultImpl}.
 * 
 * @author Carlos Eduardo Coral.
 */
public class ResultImplTest {

   /**
    * Testa o método {@link ResultImpl#toXML()}.
    */
   @Test
   public void testToXML() throws Exception {
      Document document = createStrictMock(Document.class);
      document.setXmlStandalone(true);
      expect(document.getXmlEncoding()).andReturn(null);
      replay(document);
      
      DOMSource domSource = org.easymock.classextension.EasyMock.createNiceMock(DOMSource.class);
      StringWriter stringWriter = org.easymock.classextension.EasyMock.createNiceMock(StringWriter.class);
      StreamResult streamResult = org.easymock.classextension.EasyMock.createNiceMock(StreamResult.class);
      
      Transformer transformer = org.easymock.classextension.EasyMock.createStrictMock(Transformer.class);
      transformer.setOutputProperty(OutputKeys.ENCODING, "encoding");
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
      transformer.transform(domSource, streamResult);
      org.easymock.classextension.EasyMock.replay(transformer);
      
      Validations validations = createStrictMock(Validations.class);
      expect(validations.getFirstNotNull(null, "UTF-8")).andReturn("UTF-8");
      validations.assertNotNull("encoding", "encoding");
      validations.assertNotNull("ident", true);
      validations.assertNotNull("identNumber", 4);
      replay(validations);
      
      Factory factory = createStrictMock(Factory.class);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createStringWriter()).andReturn(stringWriter);
      expect(factory.createTransformer()).andReturn(transformer);
      expect(factory.createDOMSource(document)).andReturn(domSource);
      expect(factory.createStreamResult(stringWriter)).andReturn(streamResult);
      replay(factory);
      
      ResultImpl result = new ResultImpl(factory, document);
      
      result.encoding("encoding");
      result.ident(true);
      result.identNumber(4);
      result.toXML();
      
      org.easymock.classextension.EasyMock.verify(transformer);
      verify(document, factory, validations);
   }
   
   /**
    * Testa os métodos {@link ResultImpl#toFile(File)} e {@link ResultImpl#toFile(String)}.
    */
   @Test
   public void testToFile() throws Exception {
      Document document = createStrictMock(Document.class);
      document.setXmlStandalone(true);
      expect(document.getXmlEncoding()).andReturn(null);
      replay(document);
      
      DOMSource domSource = org.easymock.classextension.EasyMock.createNiceMock(DOMSource.class);
      StreamResult streamResult = org.easymock.classextension.EasyMock.createNiceMock(StreamResult.class);
      File file = org.easymock.classextension.EasyMock.createNiceMock(File.class);
      
      PrintWriter printWriter = org.easymock.classextension.EasyMock.createNiceMock(PrintWriter.class);
      printWriter.close();
      printWriter.close();
      org.easymock.classextension.EasyMock.replay(printWriter);
      
      Transformer transformer = org.easymock.classextension.EasyMock.createMock(Transformer.class);
      transformer.setOutputProperty(OutputKeys.ENCODING, "encoding");
      expectLastCall().times(2);
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      expectLastCall().times(2);
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
      expectLastCall().times(2);
      transformer.transform(domSource, streamResult);
      expectLastCall().times(2);
      org.easymock.classextension.EasyMock.replay(transformer);
      
      Validations validations = createStrictMock(Validations.class);
      expect(validations.getFirstNotNull(null, "UTF-8")).andReturn("UTF-8");
      validations.assertNotNull("encoding", "encoding");
      validations.assertNotNull("ident", true);
      validations.assertNotNull("identNumber", 4);
      validations.assertNotNull("file", file);
      validations.assertNotNull("fileName", "fileName");
      replay(validations);
      
      Factory factory = createMock(Factory.class);
      expect(factory.createValidations()).andReturn(validations);
      expect(factory.createPrintWriter(file)).andReturn(printWriter);
      expect(factory.createPrintWriter((File) notNull())).andReturn(printWriter);
      expect(factory.createTransformer()).andReturn(transformer).times(2);
      expect(factory.createDOMSource(document)).andReturn(domSource).times(2);
      expect(factory.createStreamResult(printWriter)).andReturn(streamResult).times(2);
      replay(factory);
      
      ResultImpl result = new ResultImpl(factory, document);
      
      result.encoding("encoding");
      result.ident(true);
      result.identNumber(4);
      result.toFile(file);
      result.toFile("fileName");
      
      org.easymock.classextension.EasyMock.verify(printWriter, transformer);
      verify(factory, validations);
   }
}
