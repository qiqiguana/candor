package net.sf.xisemele.impl;

import java.io.Serializable;
import java.util.Date;

import net.sf.xisemele.api.Result;
import net.sf.xisemele.exception.AttributeNotPermittedException;
import net.sf.xisemele.exception.ElementNotFoundException;
import net.sf.xisemele.exception.InvalidNameException;
import net.sf.xisemele.exception.NotWithinContextException;
import net.sf.xisemele.exception.RemoveRootNotPermittedException;
import net.sf.xisemele.exception.RootDuplicateException;
import net.sf.xisemele.exception.ValueNotPermittedException;
import net.sf.xisemele.exception.WithinContextDuplicateException;
import net.sf.xisemele.exception.WithinContextNotPermittedException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;


/**
 * Definição de interface para abstração da escrita/edição de um documento <code>org.w3c.dom.Document</code>.
 * 
 * @author Carlos Eduardo Coral.
 */
interface WriterEditor extends Serializable {

   /**
    * Retorna instância de <code>org.w3c.dom.Document</code> correspondente ao documento XML.
    * 
    * @return
    *       instância de <code>org.w3c.dom.Document</code> correspondente ao documento XML.
    */
   Document document();
   
   /**
    * Retorna a instância de <code>org.w3c.dom.Node</code> corrente.
    * 
    * @return
    *       instância de <code>org.w3c.dom.Node</code> corrente. 
    */
   Node getCurrent();
   
   /**
    * Retorna instância de {@link Result} correspondente ao documento XML escrito/editado.
    * 
    * @return
    *       instância de {@link Result} correspondente ao document XML escrito/editado.
    */
   Result result();

   /**
    * Verifica se o documento que está sendo escrito/editado contém o elemento correspondente ao <i>path</i> especificado.
    * 
    * @param path
    *       <code>java.lang.String</code> contendo o <i>path</i> que será verificado.
    *       
    * @return
    *       <code>true</code> se o documento contiver o elemento correspondente ao <i>path</i> especificado.
    */
   boolean containsElement(String path);

   /**
    * Inicia um contexto <i>within</i> para o elemento corrente do documento XML.
    * 
    * @throws WithinContextDuplicateException
    *       exceção disparada caso esse método seja invocado duas vezes consecutivas.
    *       
    * @throws WithinContextNotPermittedException 
    *       exceção disparada caso esse método seja invocado para um elemento corrente que já contenha valor.
    */
   void within() throws WithinContextDuplicateException, WithinContextNotPermittedException;

   /**
    * Finaliza o contexto <i>within</i> corrente.
    * 
    * @throws NotWithinContextException
    *       exceção disparada caso não haja contexto <i>within</i> para ser finalizado.
    */
   void endWithin() throws NotWithinContextException;

   /**
    * Adiciona um novo elemento no nível corrente do documento XML.
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome do novo elemento.
    *       
    * @throws RootDuplicateException
    *       exceção disparada quando tentar adicionar mais de um elemento raiz no documento XML.
    *       
    * @throws InvalidNameException
    *       exceção disparada se o nome especificado não for válido.
    */
   void element(String name) throws RootDuplicateException, InvalidNameException;

   /**
    * Adiciona a instância de <code>org.w3c.dom.Element</code> no nível corrente do documento XML.
    * 
    * @param node
    *       instância de <code>org.w3c.dom.Node</code> que será adicionada.
    *       
    * @throws RootDuplicateException
    *       exceção disparada quando tentar adicionar mais de um elemento raiz no documento XML.
    */
   void node(Node node) throws RootDuplicateException;

   /**
    * Atribui o valor especificado para o elemento corrente do documento XML.
    * 
    * @param value
    *       valor que será atribuído ao elemento corrente do documento.
    *       
    * @throws ValueNotPermittedException
    *       exceção disparada caso não seja permitido atribuir valor para o elemento corrente. 
    */
   void value(Object value) throws ValueNotPermittedException;
   
   /**
    * Atribui a data especificada como valor do elemento corrente.
    * 
    * @param value
    *       instância de <code>java.util.Date</code> que será atribuída como valor do elemento corrente.
    *       
    * @param pattern
    *       <code>java.lang.String</code> contendo o padrâo que será aplicado na formatação da data especificada por parâmetro.
    *        
    * @throws ValueNotPermittedException
    *       exceção disparada caso não seja permitido atribuir um valor para o elemento corrente.
    */
   void value(Date date, String pattern) throws ValueNotPermittedException;

   /**
    * Especifica um novo atributo para o elemento corrente do documento XML.
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome que será atribuído ao novo atributo.
    *       
    * @param value
    *       objeto que será atribuído como valor do novo atributo.
    *       
    * @throws AttributeNotPermittedException
    *       exceção disparada caso não seja permitido especificar um atributo para o elemento corrente.
    *       
    * @throws InvalidNameException
    *       exceção disparada se o nome especificado não for válido.
    */
   void attribute(String name, Object value) throws AttributeNotPermittedException, InvalidNameException;
   
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
    * @throws AttributeNotPermittedException
    *       exceção disparada caso não seja permitido especificar um atributo para o elemento corrente.
    *       
    * @throws InvalidNameException
    *       exceção disparada se o nome especificado não for válido.
    */
   void attribute(String name, Date date, String pattern) throws AttributeNotPermittedException, InvalidNameException;

   /**
    * Remove o elemento corrente do documento XML. A partir desse momento, o elemento pai passa a ser o elemento corrente.
    * 
    * <p>
    * Não é permitido remover o elemento raiz do documento XML.
    * </p>
    * 
    * @throws RemoveRootNotPermittedException
    *       exceção disparada se tentar remover o elemento raiz do documento XML.
    */
   void remove() throws RemoveRootNotPermittedException;

   /**
    * Remove o primeiro filho do elemento corrente encontrado com o nome especificado por parâmetro.
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome do elemento filho que deverá ser removido.
    */
   void removeChild(String name);

   /**
    * Remove todos os filhos do elemento corrente encontrados com o nome especificado por parâmetro.
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome dos elementos filhos que deverão ser removidos.
    */
   void removeChildren(String name);

   /**
    * Remove todos os elementos filhos do elemento corrente.
    */
   void removeChildren();

   /**
    * Remove o atributo do elemento corrente com o nome especificado por parâmetro.
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome do atributo que será removido.
    */
   void removeAttribute(String name);

   /**
    * Remove todos os atributos do elemento corrente.
    */
   void removeAttributes();
   
   /**
    * Renomeia o elemento corrente.
    * 
    * @param newName
    *       <code>java.lang.String</code> contendo o novo nome que será atribuído ao elemento corrente.
    *       
    * @throws InvalidNameException
    *       exceção disparada se o nome especificado não for válido.
    */
   void rename(String newName) throws InvalidNameException;

   /**
    * Define o elemento localizado através do <code>path</code> especificado como elemento corrente.
    * 
    * @param path
    *       <code>java.lang.String</code> contendo o caminho completo para o elemento que será definido como elemento corrente.
    *       
    * @throws ElementNotFoundException
    *       exceção disparada se não for localizado um elemento para o <code>path</code> especificado.
    */
   void defineAsCurrent(String path) throws ElementNotFoundException;
}