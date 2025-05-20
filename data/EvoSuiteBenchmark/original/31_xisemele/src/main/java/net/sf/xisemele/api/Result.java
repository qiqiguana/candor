package net.sf.xisemele.api;

import java.io.File;
import java.io.Serializable;

import net.sf.xisemele.exception.TransformException;
import net.sf.xisemele.exception.XisemeleIOException;

/**
 * Definição de interface para a transformação da escrita/edição de determinado documento XML para outro formato.
 * 
 * @author Carlos Eduardo Coral.
 */
public interface Result extends Serializable {

   /**
    * Especifica o <i>encoding</i> que será aplicado na transformação.
    * 
    * @param encoding
    *       <code>java.lang.String</code> contendo o <i>encoding</i> que será aplicado na transformação.
    *       
    * @return
    *       instância corrente de {@link Result} para permitir interface fluente.
    */
   Result encoding(String encoding);
   
   /**
    * Especifica se o XML será identado ou não.
    * 
    * @param ident
    *       <code>true</code> indicando que o XML será identado.
    *       
    * @return
    *       instância corrente de {@link Result} para permitir interface fluente.
    */
   Result ident(boolean ident);
   
   /**
    * Especifica a quantidade de espaços que será aplicada na identação do XML.
    * 
    * <p>
    * O valor especificado será considerado para a transformação somente se tiver sido especificado o valor <code>true</code>
    * para o método {@link Result#ident(boolean)}.
    * </p>
    * 
    * @param identNumber
    *       <code>int</code> indicando a quantidade de espaços.
    *       
    * @return
    *       instância corrente de {@link Result} para permitir interface fluente.
    */
   Result identNumber(int identNumber);
   
   /**
    * Transforma o XML para o arquivo do qual o nome é especificado por parâmetro.
    * 
    * @param fileName
    *       <code>java.lang.String</code> contendo o nome do arquivo que será gravado.
    *       
    * @return
    *       instância corrente de {@link Result} para permitir interface fluente.
    *       
    * @throws XisemeleIOException
    *       exceção disparada caso ocorra algum erro ao escrever o arquivo.
    */
   Result toFile(String fileName) throws XisemeleIOException;
   
   /**
    * Transforma o XML para o arquivo especificado por parâmetro.
    * 
    * @param file
    *       instância de <code>java.io.File</code> correspondente ao arquivo que será gravado.
    *       
    * @return
    *       instância corrente de {@link Result} para permitir interface fluente.
    *       
    * @throws XisemeleIOException
    *       exceção disparada caso ocorra algum erro ao escrever o arquivo.
    */
   Result toFile(File file) throws XisemeleIOException;
   
   /**
    * Transforma o XML para <code>java.lang.String</code>.
    * 
    * @return
    *       <code>java.lang.String</code> correspondente ao XML transformado.
    *       
    * @throws TransformException
    *       exceção disparada caso ocorra algum erro ao transformar o XML.
    */
   String toXML() throws TransformException;
}
