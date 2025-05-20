package net.sf.xisemele.api;

import java.io.Serializable;
import java.util.Date;

import net.sf.xisemele.exception.AttributeNotPermittedException;
import net.sf.xisemele.exception.InvalidNameException;
import net.sf.xisemele.exception.NotWithinContextException;
import net.sf.xisemele.exception.RootDuplicateException;
import net.sf.xisemele.exception.WithinContextNotPermittedException;

import org.w3c.dom.Document;

/**
 * Definição de interface de escrita de XML.
 * 
 * @author Carlos Eduardo Coral.
 */
public interface Writer extends Serializable {

   /**
    * Adiciona um novo elemento com o nome especificado por parâmetro no nível corrente do documento XML.
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome que será atribuído ao novo elemento.
    *       
    * @return
    *       instância corrente de {@link Writer} para permitir interface fluente.
    *       
    * @throws RootDuplicateException
    *       exceção disparada quando tentar adicionar mais de um elemento raiz no documento XML.
    *       
    * @throws InvalidNameException
    *       exceção disparada se for especificado um nome inválido.
    */
   Writer element(String name) throws RootDuplicateException, InvalidNameException;
   
   /**
    * Adiciona um novo elemento com o nome especificado por parâmetro no nível corrente do documento XML.
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome que será atribuído ao novo elemento.
    *       
    * @param value
    *       objeto que será atribuído como valor do novo elemento.
    *       
    * @return
    *       instância corrente de {@link Writer} para permitir interface fluente.
    *       
    * @throws RootDuplicateException
    *       exceção disparada quando tentar adicionar mais de um elemento raiz no documento XML.
    *                  
    * @throws InvalidNameException
    *       exceção disparada se for especificado um nome inválido.
    */
   Writer element(String name, Object value) throws RootDuplicateException, InvalidNameException;
   
   /**
    * Adiciona um novo elemento com o nome especificado por parâmetro no nível corrente do documento XML.
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome que será atribuído ao novo elemento.
    *       
    * @param date
    *       instância de <code>java.util.Date</code> que será atribuída como valor do novo elemento.
    *       
    * @param pattern
    *       <code>java.lang.String</code> contendo o padrão que será aplicado na formatação da data especificada
    *       por parâmetro.
    *       
    * @return
    *       instância corrente de {@link Writer} para permitir interface fluente.
    *       
    * @throws RootDuplicateException
    *       exceção disparada quando tentar adicionar mais de um elemento raiz no documento XML.
    *       
    * @throws InvalidNameException
    *       exceção disparada se for especificado um nome inválido.
    */
   Writer element(String name, Date date, String pattern) throws RootDuplicateException, InvalidNameException;
   
   /**
    * Adiciona um novo {@link Element} no nível corrente do documento XML.
    * 
    * @param element
    *       instância de {@link Element} que será adicionada.
    *       
    * @return
    *       instância corrente de {@link Writer} para permitir interface fluente.
    *       
    * @throws RootDuplicateException
    *       exceção disparada quanto tentar adicionar mais de um elemento raiz no documento XML.
    */
   Writer element(Element element) throws RootDuplicateException;
   
   /**
    * Inicia um contexto para inserção de elementos filhos para o último elemento adicionado. Ou seja,
    * os elementos adicionados a partir da chamada desse método serão filhos do último elemento adicionado.
    * 
    * @return
    *       instância corrente de {@link Writer} para permitir interface fluente.
    */
   Writer within() throws WithinContextNotPermittedException;
   
   /**
    * Finaliza um nível da última chamada do método {@link #within()}.
    * 
    * @return
    *       instância corrente de {@link Writer} para permitir interface fluente.
    *       
    * @throws NotWithinContextException
    *       exceção disparada caso o método seja chamado fora de um contexto <i>within</i>.
    */
   Writer endWithin() throws NotWithinContextException;
   
   /**
    * Especifica um novo atributo para o elemento corrente do documento XML.
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome que será atribuído ao novo atributo.
    *       
    * @param value
    *       objeto que será atribuído como valor do novo atributo.
    *       
    * @return
    *       instância corrente de {@link Writer} para permitir interface fluente.
    *       
    * @throws AttributeNotPermittedException
    *       exceção disparada se não for permitido especificar um atributo para o elemento corrente.
    *       
    * @throws InvalidNameException
    *       exceção disparada se for especificado um nome inválido.
    */
   Writer attribute(String name, Object value) throws AttributeNotPermittedException, InvalidNameException;
   
   /**
    * Especifica um novo atributo para o elemento corrente do documento XML.
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome que será atribuído ao novo atributo.
    *       
    * @param date
    *       instância de <code>java.util.Date</code> que será atribuída como valor no novo atributo.
    *       
    * @param pattern
    *       <code>java.lang.String</code> contendo o padrão que será aplicado na formatação da data
    *       especificada por parâmetro.
    *       
    * @return
    *       instância corrente de {@link Writer} para permitir interface fluente.
    *       
    * @throws AttributeNotPermittedException
    *       exceção disparada se não for permitido especificar um atributo para o elemento corrente.
    *       
    * @throws InvalidNameException
    *       exceção disparada se for especificado um nome inválido.
    */
   Writer attribute(String name, Date date, String pattern) throws AttributeNotPermittedException, InvalidNameException;
   
   /**
    * Retorna a instância de {@link Result} correspondente ao documento XML escrito.
    * 
    * @return
    *       instância de {@link Result} correspondente ao documento XML escrito.
    */
   Result result();
   
   /**
    * Retorna instância de <code>org.w3c.dom.Document</code> correspondente ao documento XML criado.
    * 
    * @return
    *       instância de <code>org.w3c.dom.Document</code> correspondente ao documento XML criado.
    */
   Document document();
}
