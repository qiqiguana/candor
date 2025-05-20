package net.sf.xisemele.api;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import net.sf.xisemele.exception.ParseXMLException;
import net.sf.xisemele.impl.XisemeleFactory;

/**
 * Definição de interface principal da API. 
 * 
 * <p>
 * Uma instância de {@link Xisemele} deve ser obtida através da fábrica {@link XisemeleFactory} e configurada para criar 
 * um {@link Reader}, um {@link Writer} ou um {@link Editor} para documentos XML.
 * </p>
 * 
 * @author Carlos Eduardo Coral.
 */
public interface Xisemele extends Serializable {

   /**
    * Atribui um novo {@link Formatter}.
    * 
    * @param formatter
    *       instância de {@link Formatter} que será atribuída.
    *       
    * @return
    *       instância de {@link Xisemele} para permitir interface fluente.
    */
   Xisemele setFormatter(Formatter<?> formatter);

   /**
    * Define o padrão para formatação de datas.
    * 
    * <p>
    * O padrão <i>default</i> de uma instância de {@link Xisemele} para data é <code>MM/dd/yyyy</code>.
    * </p>
    * 
    * @param datePattern
    *       <code>java.lang.String</code> contendo o padrão que será aplicado para datas.
    *       
    * @return
    *       instância de {@link Xisemele} para permitir interface fluente.
    */
   Xisemele setDatePattern(String datePattern);

   /**
    * Cria uma instância de {@link Reader} a partir de <code>java.lang.String</code>
    * contendo o XML que será lido.
    * 
    * @param xml
    *       <code>java.lang.String</code> contendo o XML que será lido pela nova instância de {@link Reader}.
    *       
    * @return
    *       nova instância de {@link Reader} para a <code>java.lang.String</code> especificada por parâmetro.
    *       
    * @throws ParseXMLException
    *       exceção disparada caso ocorra algum erro ao efetuar o <i>parse</i> do XML especificado.
    */
   Reader createReader(String xml) throws ParseXMLException;

   /**
    * Cria uma nova instância de {@link Reader} que irá ler o XML a partir de um arquivo.
    * 
    * @param file
    *       instância de <code>java.io.File</code> correspondente ao arquivo que será lido.
    *       
    * @return
    *       nova instância de {@link Reader} para o <code>java.io.File</code> especificado
    *       por parâmetro.
    *       
    * @throws ParseXMLException
    *       exceção disparada caso ocorra algum erro ao efetuar o <i>parse</i> do XML especificado.
    */
   Reader createReader(File file) throws ParseXMLException;

   /**
    * Cria uma nova instância de {@link Reader} que irá ler o XML a partir de um <code>java.io.InputStream</code>.
    * 
    * @param inputStream
    *       instância de <code>java.io.InputStream</code> correspondente ao XML que será lido.
    *       
    * @return
    *       nova instância de {@link Reader} para o <code>java.io.InputStream</code> especificado
    *       por parâmetro.
    *       
    * @throws ParseXMLException
    *       exceção disparada caso ocorra algum erro ao efetuar o <i>parse</i> do XML especificado.
    */
   Reader createReader(InputStream inputStream) throws ParseXMLException;
   
   /**
    * Cria uma instância de {@link Editor} a partir de <code>java.lang.String</code> contendo o XML que será editado.
    * 
    * <p>
    * A instância de {@link Editor} retornada tem por <i>default</i> o elemento raiz do documento XML definido como elemento
    * corrente de edição.
    * </p>
    * 
    * @param xml
    *       <code>java.lang.String</code> contendo o XML que será editado pela nova instância de {@link Reader}.
    *       
    * @return
    *       nova instância de {@link Editor} para a <code>java.lang.String</code> especificada por parâmetro.
    *       
    * @throws ParseXMLException
    *       exceção disparada caso ocorra algum erro ao efetuar o <i>parse</i> do XML especificado.
    */
   Editor createEditor(String xml) throws ParseXMLException;

   /**
    * Cria uma nova instância de {@link Editor} que irá editar o XML a partir de um arquivo.
    * 
    * <p>
    * A instância de {@link Editor} retornada tem por <i>default</i> o elemento raiz do documento XML definido como elemento
    * corrente de edição.
    * </p>
    * 
    * @param file
    *       instância de <code>java.io.File</code> correspondente ao arquivo que será editado.
    *       
    * @return
    *       nova instância de {@link Editor} para o <code>java.io.File</code> especificado por parâmetro.
    *       
    * @throws ParseXMLException
    *       exceção disparada caso ocorra algum erro ao efetuar o <i>parse</i> do XML especificado.
    */
   Editor createEditor(File file) throws ParseXMLException;

   /**
    * Cria uma nova instância de {@link Editor} que irá editar o XML a partir de um <code>java.io.InputStream</code>.
    * 
    * <p>
    * A instância de {@link Editor} retornada tem por <i>default</i> o elemento raiz do documento XML definido como elemento
    * corrente de edição.
    * </p>
    * 
    * @param inputStream
    *       instância de <code>java.io.InputStream</code> correspondente ao XML que será editado.
    *       
    * @return
    *       nova instância de {@link Editor} para o <code>java.io.InputStream</code> especificado por parâmetro.
    *       
    * @throws ParseXMLException
    *       exceção disparada caso ocorra algum erro ao efetuar o <i>parse</i> do XML especificado.
    */
   Editor createEditor(InputStream inputStream) throws ParseXMLException;   

   /**
    * Cria uma nova instância de {@link Writer} configurada de acordo com o estado da instância de {@link Xisemele} do qual o método
    * foi chamado.
    * 
    * @param rootElement
    *       <code>java.lang.String</code> contendo o nome do elemento raiz do documento XML.
    * 
    * @return
    *       nova instância de {@link Writer}.
    */
   Writer createWriter(String rootElement);
   
   /**
    * Cria uma nova instância de {@link Writer} configurada de acordo com o estado da instância de {@link Xisemele}.
    * 
    * @param rootElement
    *       <code>java.lang.String</code> contendo o nome do elemento raiz do documento XML.
    *             
    * @param xmlVersion
    *       <code>java.lang.String</code> contendo a versão do documento XML.
    *       
    * @return
    *       nova instância de {@link Writer}.
    */
   Writer createWriter(String rootElement, String xmlVersion);
}