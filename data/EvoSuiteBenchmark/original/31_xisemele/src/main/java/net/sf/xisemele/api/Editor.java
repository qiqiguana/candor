package net.sf.xisemele.api;

import java.io.Serializable;
import java.util.Date;

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

/**
 * Definição de interface de edição de documentos XML.
 *  
 * @author Carlos Eduardo Coral.
 */
public interface Editor extends Serializable {

   /**
    * Verifica se há um elemento correspondente ao <code>path</code> especificado por parâmetro.
    * 
    * <p>
    * Veja a sintaxe para <i>path</i> no método {@link Reader#find(String)}.
    * </p>
    * 
    * @param path
    *       <code>java.lang.String</code> correspondente ao elemento que será verificado.
    *       
    * @return
    *       <code>true</code> se existir um elemento para o <code>path</code> especificado.
    */
   boolean containsElement(String path);
   
   /**
    * Define o elemento correspondente ao <code>path</code> especificado por parâmetro como o elemento corrente.
    * 
    * @param path
    *       <code>java.lang.String</code> contendo o <code>path</code> do elemento que deverá ser definido como elemento corrente. 
    *       Veja a sintaxe correta para o <code>path</code> na documentação do método {@link Reader#find(String)}.
    *       
    * @return
    *       instância corrente de {@link Editor} para permitir interface fluente.
    *       
    * @throws ElementNotFoundException
    *       exceção disparada caso o elemento especificado em <code>path</code> não seja localizado.
    */
   Editor defineAsCurrent(String path) throws ElementNotFoundException;
   
   /**
    * Remove o elemento corrente do documento XML. A partir desse momento, o elemento pai torna-se o elemento 
    * corrente.
    * 
    * @return
    *       instância corrente de {@link Editor} para permitir interface fluente.
    */
   Editor remove() throws RemoveRootNotPermittedException;
   
   /**
    * Remove o primeiro filho do elemento corrente com o nome especificado por parâmetro.
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome do elemento filho que deverá ser removido.
    *       
    * @return
    *       instância corrente de {@link Editor} para permitir interface fluente.
    */
   Editor removeChild(String name);
   
   /**
    * Remove todos os filhos do elemento corrente com o nome especificado por parâmetro.
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome dos elementos filhos que deverão ser removidos.
    *       
    * @return
    *       instância corrente de {@link Editor} para permitir interface fluente.
    */
   Editor removeChildren(String name);
   
   /**
    * Remove todos os filhos do elemento corrente.
    * 
    * @return
    *       instância corrente de {@link Editor} para permitir interface fluente.
    */
   Editor removeChildren();
   
   /**
    * Remove o atributo do elemento corrente com o nome especificado por parâmetro.
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome do atributo que deverá ser removido.
    *       
    * @return
    *       instância corrente de {@link Editor} para permitir interface fluente.
    */
   Editor removeAttribute(String name);
   
   /**
    * Remove todos os atributos do elemento corrente.
    * 
    * @return
    *       instância corrente de {@link Editor} para permitir interface fluente.
    */
   Editor removeAttributes();
   
   /**
    * Renomeia o elemento corrente.
    * 
    * @param newName
    *       <code>java.lang.String</code> contendo o novo nome que será atribuído ao elemento.
    *       
    * @throws InvalidNameException
    *       exceção disparada se for especificado um nome inválido.
    *             
    * @return
    *       instância corrente de {@link Editor} para permitir interface fluente.
    */
   Editor rename(String newName) throws InvalidNameException;
   
   /**
    * Atribui um valor para o elemento corrente.
    * 
    * @param value
    *       instância de <code>java.lang.Object</code> correspondente ao valor que será atribuído ao elemento corrente.
    *       
    * @return
    *       instância corrente de {@link Editor} para permitir interface fluente.
    *       
    * @throws ValueNotPermittedException
    *       exceção disparada caso não seja permitido atribuir um valor para o elemento corrente.
    */
   Editor value(Object value) throws ValueNotPermittedException;
   
   /**
    * Atribui a data especificada como valor do elemento corrente.
    * 
    * @param value
    *       instância de <code>java.util.Date</code> que será atribuída como valor do elemento corrente.
    *       
    * @param pattern
    *       <code>java.lang.String</code> contendo o padrâo que será aplicado na formatação da data especificada por parâmetro.
    *        
    * @return
    *       instância corrente de {@link Editor} para permitir interface fluente.
    *       
    * @throws ValueNotPermittedException
    *       exceção disparada caso não seja permitido atribuir um valor para o elemento corrente.
    */
   Editor value(Date value, String pattern) throws ValueNotPermittedException;

   /**
    * Inicia um contexto para inserção de elementos filhos para o último elemento adicionado. Ou seja,
    * os elementos adicionados a partir da chamada desse método serão filhos do último elemento adicionado.
    * 
    * @return
    *       instância corrente de {@link Editor} para permitir interface fluente.
    *       
    * @throws WithinContextDuplicateException
    *       exceção disparada caso esse método seja invocado duas vezes consecutivas.
    *       
    * @throws WithinContextNotPermittedException 
    *       exceção disparada caso esse método seja invocado para um elemento corrente que já contenha valor.
    */
   Editor within() throws WithinContextDuplicateException, WithinContextNotPermittedException;

   /**
    * Finaliza um nível da última chamada do método {@link #within()}.
    * 
    * @return
    *       instância corrente de {@link Editor} para permitir interface fluente.
    *       
    * @throws NotWithinContextException
    *       exceção disparada caso o método seja chamado fora de um contexto <i>within</i>.
    */
   Editor endWithin() throws NotWithinContextException;

   /**
    * Adiciona um novo elemento com o nome especificado por parâmetro no nível corrente do documento XML.
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome que será atribuído ao novo elemento.
    *       
    * @return
    *       instância corrente de {@link Editor} para permitir interface fluente.
    *       
    * @throws RootDuplicateException
    *       exceção disparada quando tentar adicionar mais de um elemento raiz no documento XML.
    *       
    * @throws InvalidNameException
    *       exceção disparada se for especificado um nome inválido.      
    */
   Editor element(String name) throws RootDuplicateException, InvalidNameException;
   
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
    *       instância corrente de {@link Editor} para permitir interface fluente.
    *       
    * @throws RootDuplicateException
    *       exceção disparada quando tentar adicionar mais de um elemento raiz no documento XML.
    *       
    * @throws InvalidNameException
    *       exceção disparada se for especificado um nome inválido.
    */
   Editor element(String name, Object value) throws RootDuplicateException, InvalidNameException;
   
   /**
    * Adiciona um novo elemento com o nome especificado por parâmetro no nível corrente do documento XML.
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome que será atribuído ao novo elemento.
    *       
    * @param value
    *       instância de <code>java.util.Date</code> que será atribuída como valor do novo elemento.
    *       
    * @param pattern
    *       <code>java.lang.String</code> contendo o padrão que será aplicado na formatação da data especificada
    *       por parâmetro.
    *       
    * @return
    *       instância corrente de {@link Editor} para permitir interface fluente.
    *       
    * @throws RootDuplicateException
    *       exceção disparada quando tentar adicionar mais de um elemento raiz no documento XML.
    *       
    * @throws InvalidNameException
    *       exceção disparada se for especificado um nome inválido.
    */
   Editor element(String name, Date value, String pattern) throws RootDuplicateException, InvalidNameException;
   
   /**
    * Adiciona um novo {@link Element} no nível corrente do documento XML.
    * 
    * @param element
    *       instância de {@link Element} que será adicionada.
    *       
    * @return
    *       instância corrente de {@link Editor} para permitir interface fluente.
    *       
    * @throws RootDuplicateException
    *       exceção disparada quanto tentar adicionar mais de um elemento raiz no documento XML.
    */
   Editor element(Element element) throws RootDuplicateException;
   
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
    *       instância corrente de {@link Editor} para permitir interface fluente.
    *       
    * @throws AttributeNotPermittedException
    *       exceção disparada caso não seja permitido especificar um atributo para o elemento corrente.
    *       
    * @throws InvalidNameException
    *       exceção disparada se for especificado um nome inválido.
    */
   Editor attribute(String name, Object value) throws AttributeNotPermittedException, InvalidNameException;
   
   /**
    * Especifica um novo atributo para o elemento corrente do documento XML.
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome que será atribuído ao novo atributo.
    *       
    * @param value
    *       instância de <code>java.util.Date</code> que será atribuída como valor no novo atributo.
    *       
    * @param pattern
    *       <code>java.lang.String</code> contendo o padrão que será aplicado na formatação da data
    *       especificada por parâmetro.
    *       
    * @return
    *       instância corrente de {@link Editor} para permitir interface fluente.
    *      
    * @throws AttributeNotPermittedException
    *       exceção disparada caso não seja permitido especificar um atributo para o elemento corrente.
    *       
    * @throws InvalidNameException
    *       exceção disparada se for especificado um nome inválido.
    */
   Editor attribute(String name, Date value, String pattern) throws AttributeNotPermittedException, InvalidNameException;
   
   /**
    * Retorna instância de {@link Result} correspondente ao documento XML editado.
    * 
    * @return
    *       instância de {@link Result} correspondente ao documento XML editado.
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
