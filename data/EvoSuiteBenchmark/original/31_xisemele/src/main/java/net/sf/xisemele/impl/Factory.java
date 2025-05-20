package net.sf.xisemele.impl;

import java.io.File;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
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

/**
 * Definição de interface para fábrica de objetos que poderão ser criados pela API Xisemele. 
 * 
 * @author Carlos Eduardo Coral.
 */
interface Factory extends Serializable {

   /**
    * Retorna uma nova instância de {@link Value} para a <code>java.lang.String</code> especificada por parâmetro.
    * 
    * @param value
    *       instância de <code>java.lang.String</code> que será atribuída a nova instância de {@link Value} retornada.
    *       
    * @return
    *       nova instância de {@link Value} correspondente à <code>java.lang.String</code> especificada por parâmetro ou a 
    *       instância de {@link NullValue} caso seja especificado o valor <code>null</code> por parâmetro.
    */
   Value createValue(String value);
   
   /**
    * Retorna uma nova instância da implementação de objeto nulo de {@link Value}.
    * 
    * @return
    *       nova instância de {@link Value} correspondente a um objeto nulo.
    */
   Value createNullValue();
   
   /**
    * Retorna lista de {@link Attribute} correspondente às instâncias de <code>org.w3c.dom.Node</code> especificada por parâmetro.
    * 
    * @param nodes
    *       lista de <code>org.w3c.dom.Node</code> correspondente aos atributos que deverão ser retornados em instância de 
    *       {@link Attribute}. 
    *       
    * @return
    *       lista de {@link Attribute} correspondente às instâncias de <code>org.w3c.dom.Node</code> especificadas por parâmetro.
    */
   List<Attribute> createAttributes(List<Node> nodes);
   
   /**
    * Retorna uma nova instância de {@link Element} para a instância de <code>org.w3c.dom.Node</code> especificada por parâmetro.
    * 
    * @param node
    *       instância de <code>org.w3c.dom.Node</code> que será atribuída à nova instância de {@link Element}.
    *       
    * @return
    *       nova instância de {@link Element} correspondente à instância de <code>org.w3c.dom.Node</code> especificada por parâmetro.
    */
   Element createElement(Node node);
   
   /**
    * Retorna uma instância de <code>java.util.List</code> contendo instâncias de {@link Element} para às instâncias de 
    * <code>org.w3c.dom.Node</code> especificadas por parâmetro. 
    * 
    * @param nodes
    *       lista contendo as instâncias de <code>org.w3c.dom.Node</code> que serão atribuídas às novas instâncias de {@link Element}. 
    *       
    * @return
    *       lista contendo as novas instâncias de {@link Element} correspondentes às instâncias de <code>org.w3c.dom.Node</code> 
    *       especificadas por parâmetro.
    */
   List<Element> createElements(List<Node> nodes);

   /**
    * Retorna uma nova instância de {@link ValueList} correspondente às instâncias de <code>org.w3c.dom.Node</code> especificadas
    * por parâmetro.
    * 
    * @param nodes
    *       lista de <code>org.w3c.dom.Node</code> da qual a nova instância de {@link ValueList} correspondente será retornada.
    *       
    * @return
    *       instância de {@link ValueList} correspondente às instâncias de <code>org.w3c.dom.Node</code> especificadas por parâmetro.
    */
   ValueList createValueList(List<Node> nodes);
   
   /**
    * Cria uma nova instância de {@link Reader} para a instância de <code>org.w3c.dom.Document</code> especificada por parâmetro.
    * 
    * @param document
    *       instância de <code>org.w3c.dom.Document</code> que será atribuída a nova instância de {@link Reader}.
    *       
    * @return
    *       nova instância de {@link Reader} correspondente à nova instância de <code>org.w3c.dom.Document</code> especificada por 
    *       parâmetro.
    */
   Reader createReader(Document document);
   
   /**
    * Cria uma nova instância de {@link Editor} para a instância de <code>org.w3c.dom.Document</code> especificada por parâmetro.
    * 
    * @param document
    *       instância de <code>org.w3c.dom.Document</code> que será atribuída a nova instância de {@link Editor}.
    *       
    * @return
    *       nova instância de {@link Editor} correspondente à nova instância de <code>org.w3c.dom.Document</code> especificada por
    *       parâmetro.
    */
   Editor createEditor(Document document);
   
   /**
    * Cria uma nova instância de {@link Writer} para a instância de <code>org.w3c.dom.Document</code> especificada por parâmetro.
    * 
    * @param document
    *       instância de <code>org.w3c.dom.Document</code> que será atribuída a nova instância de {@link Writer}.
    *       
    * @param rootElement
    *       <code>java.lang.String</code> contendo o nome do elemento raiz.
    *       
    * @return
    *       nova instância de {@link Writer} correspondente á nova instância de <code>org.w3c.dom.Document</code> especificada por
    *       parâmetro.
    */
   Writer createWriter(Document document, String rootElement);
   
   /**
    * Cria uma nova instância de {@link Formatter} para formatação de datas.
    * 
    * @param datePattern
    *       <code>java.lang.String</code> contendo o padrão que será aplicado na formatação de datas.
    *       
    * @return
    *       instância de {@link Formatter} para formatação de datas.
    */
   Formatter<Date> createDateFormatter(String datePattern);
   
   /**
    * Cria uma nova instância de {@link Result} para a instância de <code>org.w3c.dom.Document</code>.
    * 
    * @param document
    *       instância de <code>org.w3c.dom.Document</code> que será atribuída a nova instância de {@link Result}.
    *       
    * @return
    *       nova instância de {@link Result}.
    */
   Result createResult(Document document);
   
   /**
    * Cria uma nova instância de <code>java.io.StringWriter</code>.
    * 
    * @return
    *       nova instância de <code>java.io.StringWriter</code>.
    */
   StringWriter createStringWriter();
   
   /**
    * Cria uma nova instância de <code>java.io.PrintWriter</code>.
    * 
    * @param file
    *       instância de <code>java.io.File</code> que será referenciada pela nova instância de 
    *       <code>java.io.PrintWriter</code>.
    *       
    * @return
    *       nova instância de <code>java.io.PrintWriter</code>.
    *       
    * @throws XisemeleIOException
    *       exceção disparada caso ocorra algum erro ao criar a instância de <code>java.io.PrintWriter</code>.
    */
   PrintWriter createPrintWriter(File file) throws XisemeleIOException;

   /**
    * Cria uma nova instância de <code>javax.xml.parsers.DocumentBuilder</code>.
    *  
    * @return
    *       nova instância de <code>javax.xml.parsers.DocumentBuilder</code>.
    */
   DocumentBuilder createDocumentBuilder();
   
   /**
    * Cria uma nova instância de <code>javax.xml.transform.Transformer</code>.
    * 
    * @return
    *       nova instância de <code>javax.xml.transform.Transformer</code>.
    */
   Transformer createTransformer() throws TransformerConfigurationException, TransformerFactoryConfigurationError;
   
   /**
    * Cria uma nova instância de <code>javax.xml.transform.dom.DOMSource</code> para a instância de 
    * <code>org.w3c.dom.Document</code> especificada por parâmetro.
    * 
    * @param document
    *       instância de <code>org.w3c.dom.Document</code> que será atribuída a nova instância de 
    *       <code>javax.xml.transform.dom.DOMSource</code>.
    *       
    * @return
    *       nova instância de <code>javax.xml.transform.dom.DOMSource</code>.
    */
   DOMSource createDOMSource(Document document);
   
   /**
    * Cria uma nova instância de <code>javax.xml.transform.stream.StreamResult</code> para a instância de 
    * <code>java.io.Writer</code> especificada por parâmetro.
    *  
    * @param writer
    *       instância de <code>java.io.Writer</code> que será atribuída a nova instância de 
    *       <code>javax.xml.transform.stream.StreamResult</code>.
    *       
    * @return
    *       nova instância de <code>javax.xml.transform.stream.StreamResult</code>.
    */
   StreamResult createStreamResult(java.io.Writer writer);
   
   /**
    * Cria uma nova instância de {@link Message}.
    * 
    * @return
    *       nova instância de {@link Message}.
    */
   Message createMessage();

   /**
    * Cria uma nova instância de {@link WithinContext}.
    * 
    * @return
    *       nova instância de {@link WithinContext}.
    */
   WithinContext createWithinContext();
   
   /**
    * Cria uma nova instância de {@link Operations}.
    * 
    * @return
    *       nova instância de {@link Operations}.
    */
   Operations createOperations();
   
   /**
    * Cria uma nova instância de {@link Validations}.
    * 
    * @return
    *       nova instância de {@link Validations}.
    */
   Validations createValidations();
 }
