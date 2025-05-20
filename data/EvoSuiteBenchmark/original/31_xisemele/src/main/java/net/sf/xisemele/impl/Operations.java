package net.sf.xisemele.impl;

import java.io.Serializable;
import java.util.List;

import net.sf.xisemele.exception.ElementNotFoundException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Definição de interface com métodos com operações básicas para a API DOM.
 * 
 * @author Carlos Eduardo Coral.
 */
interface Operations extends Serializable {
   
   /**
    * Verifica se a instância de <code>org.w3c.dom.Node</code> é o elemento raiz de algum documento DOM.
    * 
    * @param node
    *       instância de <code>org.w3c.dom.Node</code> que será verificada.
    *       
    * @return
    *       <code>true</code> se o elemento especificado por parâmetro for raiz de algum documento DOM.
    */
   boolean isRootElement(Node node);

   /**
    * Verifica se a instância de <code>org.w3c.dom.Node</code> contém elementos filhos.
    * 
    * @param node
    *       instância de <code>org.w3c.dom.Node</code> que será verificada.
    *       
    * @return
    *       <code>true</code> se a instância de <code>org.w3c.dom.Node</code> contiver elementos filhos que são do tipo
    *       <code>org.w3c.dom.Node.ELEMENT_NODE</code>.
    */
   boolean containsChildren(Node node);
   
   /**
    * Verifica se a instância de <code>org.w3c.dom.Node</code> contém elemento filho com o nome especificado por parâmetro.
    * 
    * @param node
    *       instância de <code>org.w3c.dom.Node</code> que será verificada.
    *       
    * @param name
    *       <code>java.lang.String</code> contendo o nome do elemento filho que será verificado.
    *       
    * @return
    *       <code>true</code> se a instância de <code>org.w3c.dom.Node</code> contiver filho com o nome especificado.
    */
   boolean containsChild(Node node, String name);
   
   /**
    * Retorna a quantidade de elementos filhos da instância de <code>org.w3c.dom.Node</code> especificada por parâmetro.
    * 
    * @param node
    *       instância de <code>org.w3c.dom.Node</code> que será verificada.
    *       
    * @return
    *       <code>int</code> contendo a quantidade de elementos filhos da instância de <code>org.w3c.dom.Node</code> especificada por 
    *       parâmetro.
    */
   int numberOfChildren(Node node);
   
   /**
    * Retorna a quantidade de elementos filhos da instância de <code>org.w3c.dom.Node</code> com o nome especificado por parâmetro.
    * 
    * @param node
    *       instância de <code>org.w3c.dom.Node</code> que será verificada.
    *       
    * @param name
    *       <code>java.lang.String</code> contendo o nome dos elementos filhos que serão contados.
    *       
    * @return
    *       <code>int</code> contendo a quantidade de elementos filhos da instância de <code>org.w3c.dom.Node</code> com o nome
    *       especificado. 
    */
   int numberOfChildren(Node node, String name);

   /**
    * Retorna instância de <code>org.w3c.dom.Node</code> correspondente ao filho de <code>node</code> na posição <code>index</code>
    * especificada.
    * 
    * @param node
    *       instância de <code>org.w3c.dom.Node</code> do qual o filho localizado na posição <code>index</code> especificada deverá
    *       ser retornado.
    *        
    * @param index
    *       <code>int</code> contendo a posição do elemento filho que deverá ser retornado.
    *       
    * @return
    *       instância de <code>org.w3c.dom.Node</code> correspondente ao elemento filho localizado na posição <code>index</code> ou
    *       o valor <code>null</code> se <code>index</code> for uma posição inválida.
    */
   Node child(Node node, int index);
   
   /**
    * Retorna instância de <code>org.w3c.dom.Node</code> correspondente ao filho de <code>node</code> com o nome especificado por
    * parâmetro.
    * 
    * @param node
    *       instância de <code>org.w3c.dom.Node</code> do qual o filho localizado com o nome especificado deverá ser retornado.
    *        
    * @param name
    *       <code>java.lang.String</code> contendo o nome do elemento filho que deverá ser retornado.
    *       
    * @return
    *       instância de <code>org.w3c.dom.Node</code> correspondente ao elemento filho com o nome especificado ou o valor 
    *       <code>null</code> caso não encontre o elemento filho com esse nome.
    */
   Node child(Node node, String name);
   
   /**
    * Retorna uma instância de <code>java.util.List</code> contendo os elementos filhos de <code>node</code> com o nome
    * especificado por parâmetro.
    * 
    * @param node
    *       instância de <code>org.w3c.dom.Node</code> dos quais os elementos filhos serão retornados.
    *       
    * @param name
    *       <code>java.lang.String</code> contendo o nome dos filhos que deverão ser retornados.
    *       
    * @return
    *       instância de <code>java.util.List</code> contendo os elementos filhos de <code>node</code>. O valor <code>null</code>
    *       nunca será retornado.
    */
   List<Node> children(Node node, String name);
   
   /**
    * Retorna uma instância de <code>java.util.List</code> contendo os elementos filhos de <code>node</code>.
    * 
    * @param node
    *       instância de <code>org.w3c.dom.Node</code> dos quais os elementos filhos serão retornados.
    *       
    * @return
    *       instância de <code>java.util.List</code> contendo os elementos filhos de <code>node</code>. O valor <code>null</code>
    *       nunca será retornado.
    */
   List<Node> children(Node node);
   
   /**
    * Verifica se o elemento XML especificado por parâmetro contém valor.
    * 
    * @param node
    *       instância de <code>org.w3c.dom.Node</code> que será verificada.
    *       
    * @return
    *       <code>true</code> se o elemento especificado contiver valor.
    */
   boolean containsValue(Node node);
   
   /**
    * Verifica se a instância de <code>org.w3c.dom.Node</code> contém atributos.
    * 
    * @param node
    *       instância de <code>org.w3c.dom.Node</code> que será verificada.
    *       
    * @return
    *       <code>true</code> se a instância de <code>org.w3c.dom.Node</code> contiver atributos.
    */
   boolean containsAttributes(Node node);
   
   /**
    * Verifica se a instância de <code>org.w3c.dom.Node</code> contém atributo com o nome especificado por parâmetro.
    * 
    * @param node
    *       instância de <code>org.w3c.dom.Node</code> que será verificada.
    *       
    * @param name
    *       <code>java.lang.String</code> contendo o nome do atributo que será verificado.
    *       
    * @return
    *       <code>true</code> se a instância de <code>org.w3c.dom.Node</code> contiver um atributo com o nome especificado.
    */
   boolean containsAttribute(Node node, String name);
   
   /**
    * Retorna uma instância de <code>java.util.List</code> contendo todos os atributos do elemento especificado por parâmetro.
    * 
    * @param node
    *       instância de <code>org.w3c.dom.Node</code> dos quais todos os atributos serão retornados.
    *       
    * @return
    *       instância de <code>java.util.List</code> contendo todos os atributos do elemento especificado.
    */
   List<Node> attributes(Node node);
   
   /**
    * Retorna instância de <code>java.lang.String</code> correspondente ao valor do atributo do elemento XML com o nome especificado
    * por parâmetro.
    * 
    * @param node
    *       instância de <code>org.w3c.dom.Node</code> cujo determinado atributo será retornado.
    *       
    * @param name
    *       <code>java.lang.String</code> contendo o nome do atributo que deverá ser retornado.
    *       
    * @return
    *       instância de <code>java.lang.String</code> correspondente ao valor do atributo localizado ou o valor <code>null</code> caso
    *       não haja atributo com o nome especificado.
    */
   String attributeValue(Node node, String name);
   
   /**
    * Verifica se a instância de <code>org.w3c.dom.Document</code> contém o elemento localizado através do <code>path</code> especificado.
    *  
    * @param document
    *       instância de <code>org.w3c.dom.Document</code> que será verificado.
    *       
    * @param path
    *       <code>java.lang.String</code> contendo o <code>path</code> para o elemento que será verificado.
    *       
    * @return
    *       <code>true</code> se a instância de <code>org.w3c.dom.Document</code> contendo o elemento para o <code>path</code> especificado.
    */
   boolean containsElement(Document document, String path);
   
   /**
    * Retorna <code>java.lang.String</code> correspondente ao <i>path</i> da instância de <code>org.w3c.dom.Node</code> especificada por
    * parâmetro.
    * 
    * @param node
    *       instância de <code>org.w3c.dom.Node</code> que terá o <i>path</i> correspondente retornado.
    *       
    * @return
    *       <code>java.lang.String</code> contendo o <i>path</i> correspondente à instância de <code>org.w3c.dom.Node</code>
    *       especificada por parâmetro.
    */
   String path(Node node);
   
   /**
    * Retorna a instância de <code>org.w3c.dom.Node</code> correspondente ao elemento localizado em <code>document</code> para
    * o <code>path</code> especificado.
    * 
    * @param document
    *       instância de <code>org.w3c.dom.Document</code> em que determinado elemento deverá ser localizado. 
    *       
    * @param path
    *       <code>java.lang.String</code> contendo o <code>path</code> para o elemento que deverá ser localizado.
    *       
    * @return
    *       instância de <code>org.w3c.dom.Node</code> correspondente ao elemento localizado.
    *       
    * @throws ElementNotFoundException 
    *       exceção disparada caso o elemento não seja encontrado. Para previnir isso, verifique antes se o documento 
    *       {@link #containsElement(Document, String) contém o elemento} para este <code>path</code>. 
    */
   Node find(Document document, String path) throws ElementNotFoundException;
}
