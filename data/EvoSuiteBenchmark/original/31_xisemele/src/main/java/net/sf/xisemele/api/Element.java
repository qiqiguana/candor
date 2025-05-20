package net.sf.xisemele.api;

import java.io.Serializable;
import java.util.List;

import net.sf.xisemele.exception.ChildNotFoundException;
import net.sf.xisemele.exception.ElementIndexOutOfBoundsException;

import org.w3c.dom.Node;

/**
 * Definição de interface para um elemento XML.
 * 
 * <p>
 * Um elemento na API Xisemele pode conter {@link #value() valor}, {@link #children() filhos} e {@link #attributes() atributos}. O 
 * conceito de valor é a abstração do texto de determinado nó em um documento XML (<code>Node.getTextContent()</code>). Um determinado
 * elemento que contém valor não poder ter elementos filhos e vice-versa. Veja o seguinte exemplo:
 * 
 * <blockquote><pre>
 * &lt;root&gt;
 *   &lt;elementA&gt;123&lt;/elementA&gt;
 *   &lt;elementB&gt;text value&lt;/elementB&gt;
 * &lt;/root&gt;
 * </pre></blockquote>
 * 
 * Observe que o elemento <b>elementA</b> contém o valor <b>"123"</b> e o elemento <b>elementB</b> o valor <b>"text value"</b>.
 * </p>
 *
 * O conceito de filhos e atributos é equivalente ao da API <b>W3C DOM</b>.
 * 
 * @author Carlos Eduardo Coral.
 */
public interface Element extends Serializable {

   /**
    * Retorna o nome do elemento.
    * 
    * @return
    *       <code>java.lang.String</code> contendo o nome do elemento.
    */
   String name();
   
   /**
    * Retorna instância de {@link Value} correspondente ao valor do elemento XML.
    * 
    * <p>
    * Se o elemento contiver filhos, a instância de {@link Value} retornada será uma
    * implementação de objeto nulo, ou seja, todas as chamadas aos métodos do tipo <code>asX()</code>
    * da instância de {@link Value} irão retornar o valor <code>null</code>.
    * </p>
    * 
    * @return
    *       instância de {@link Value} correspondente ao valor do elemento ou um objeto nulo. O valor
    *       <code>null</code> nunca será retornado.
    */
   Value value();
   
   /**
    * Retorna o <i>path</i> que representa a localização do elemento no documento XML.
    * 
    * <p>
    * O formato do atalho retornado é correspondente ao que pode ser especificado como parâmetro para o método 
    * {@link Reader#find(String) Reader.element(path)}.
    * </p>
    * 
    * @return
    *       <code>java.lang.String</code> contendo o atalho correspondente à localizado do elemento no documento XML.
    */
   String path();
   
   /**
    * Verifica se o elemento contém atributos.
    * 
    * @return
    *       <code>true</code> se o elemento tiver atributos.
    */
   boolean containsAttributes();
   
   /**
    * Verifica se o elemento contém determinado atributo.
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome do atributo
    *       que será verificado.
    *       
    * @return
    *       <code>true</code> se o elemento contiver o atributo especificado
    *       por parâmetro.
    */
   boolean containsAttribute(String name);
   
   /**
    * Retorna o valor de determinado atributo.
    * 
    * <p>
    * Se o atributo não for localizado, a instância de {@link Value} retornada será uma
    * implementação de objeto nulo, ou seja, todas as chamadas aos métodos do tipo <code>asX()</code>
    * da instância de {@link Value} irá retornar o valor <code>null</code>.
    * </p>
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome do atributo
    *       do qual o valor será retornado.
    *          
    * @return
    *       instância de {@link Value} contendo o valor do atributo especificado
    *       por parâmetro ou um objeto nulo. O valor <code>null</code> nunca será retornado.
    */
   Value attribute(String name);
   
   /**
    * Retorna uma lista contendo instâncias de {@link Attribute} correspondentes
    * aos atributos do elemento XML.
    * 
    * @return
    *       lista de {@link Attribute} contendo os atributos do elemento XML. 
    */
   List<Attribute> attributes();
   
   /**
    * Verifica se o elemento contém elementos filhos.
    * 
    * @return
    *       <code>true</code> se o elemento tiver elementos filhos.
    */
   boolean containsChildren();
   
   /**
    * Retorna a quantidade de elementos filhos do elemento XML.
    * 
    * @return
    *       <code>int</code> contendo a quantidade de elementos filhos.
    */
   int numberOfChildren();
   
   /**
    * Retorna a quantidade de elementos filhos do elemento XML com o nome especificado por parâmetro.
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome dos elementos filhos que serão verificados.
    *       
    * @return
    *       <code>int</code> contendo a quantidade de elementos filhos com o nome especificado por parâmetro.
    */
   int numberOfChildren(String name);
 
   /**
    * Retorna o elemento filho localizado na posição <code>index</code> especificada por parâmetro.
    *  
    * @param index
    *       <code>int</code> contendo a posição do elemento filho que deverá ser retornado.
    *       Deve ser especificado um valor entre <code>0</code> e {@link #numberOfChildren()} - 1. 
    *       O valor <code>0</code> correspondente à primeira posição. 
    *       
    * @return
    *       instância de {@link Element} correspondente à posição especificada por parâmetro.
    *       
    * @throws ElementIndexOutOfBoundsException
    *       exceção disparada caso a posição <code>index</code> especificada seja inválida.
    */
   Element child(int index) throws ElementIndexOutOfBoundsException;
   
   /**
    * Verifica se o elemento contém algum filho com o nome especificado por parâmetro.
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome que será verificado.
    *       
    * @return
    *       <code>true</code> se o elemento contiver um filho com o nome especificado.
    */
   boolean containsChild(String name);
   
   /**
    * Retorna o primeiro elemento filho localizado com o nome especificado por parâmetro.
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome do elemento filho que deverá
    *       ser retornado.
    *          
    * @return
    *       instância de {@link Element} que contém o nome especificado ou o valor <code>null</code>, 
    *       caso não encontre nenhum elemento com esse nome. 
    *       
    * @throws ChildNotFoundException
    *       exceção disparada caso não seja encontrado elemento filho com o nome especificado por parâmetro.
    */
   Element child(String name) throws ChildNotFoundException;
   
   /**
    * Retorna uma lista contendo os filhos do elemento XML.
    * 
    * @return
    *       lista de {@link Element} correspondente aos filhos do elemento XML do qual o método
    *       foi chamado. O valor <code>null</code> nunca será retornado.
    */
   List<Element> children();
   
   /**
    * Retorna uma lista contendo os filhos do elemento XML com o nome especificado por parâmetro.
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome dos elementos filhos que deverão ser retornados.
    *       
    * @return
    *       lista de {@link Element} correspondente aos filhos do elemento XML com o nome correspondente ao especificado
    *       por parâmetro. O valor <code>null</code> nunca será retornado.
    */
   List<Element> children(String name);
   
   /**
    * Retorna instância de {@link ValueList} correspondente o valor dos filhos do elemento XML.
    * 
    * @return
    *       instância de {@link ValueList} correspondente ao valor dos filhos do elemento XML do
    *       qual o método foi chamado. O valor <code>null</code> nunca será retornado.
    */
   ValueList childrenValue();
   
   /**
    * Retorna instância de {@link ValueList} correspondente ao valor dos filhos do elemento XML com o nome especificado
    * por parâmetro.
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome dos elementos filhos dos quais os seus valores deverão ser retornados.
    *       
    * @return
    *       instância de {@link ValueList} correspondente ao valor dos filhos do elemento XML com o nome especificado
    *       por parâmetro. O valor <code>null</code> nunca será retornado.
    */
   ValueList childrenValue(String name);
   
   /**
    * Retorna a instância de <code>org.w3c.dom.Node</code> correspondente à instância de {@link Element}. 
    * 
    * @return
    *       instância de <code>org.w3c.dom.Node</code> correspondente.
    */
   Node node();
}
