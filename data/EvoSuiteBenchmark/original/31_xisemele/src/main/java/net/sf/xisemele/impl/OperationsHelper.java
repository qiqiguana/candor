package net.sf.xisemele.impl;

import java.io.Serializable;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Definição de interface com métodos auxiliares para a implementação de {@link Operations}.
 * 
 * @author Carlos Eduardo Coral.
 */
interface OperationsHelper extends Serializable {

   /**
    * Retorna as instâncias de <code>org.w3c.dom.Node</code> filhas da instância de <code>org.w3c.dom.Node</code> especificada
    * por parâmetro. Serão retornadas apenas as instâncias filhas de <code>node</code> que são do tipo Node.ELEMENT_NODE. 
    * 
    * @param node
    *       instância de <code>org.w3c.dom.Node</code> do quais os filhos serão retornados.
    *       
    * @return
    *       instância de <code>java.util.List</code> contendo as instâncias de <code>org.w3c.dom.Node</code> filhas de 
    *       <code>node</code>.
    */
   List<Node> children(Node node);
   
   /**
    * Retorna o nome da lista especificada que contiver o nome especificado por parâmetro.
    * 
    * @param nodes
    *       lista de <code>org.w3c.dom.Node</code> da qual determinado elemento será retornado.
    *       
    * @param name
    *       <code>java.lang.String</code> contendo o nome da instância de <code>org.w3c.dom.Node</code> que deverá ser retornada.
    *       
    * @return
    *       instância de <code>org.w3c.dom.Node</code> correspondente ao nome especificado por parâmetro ou o valor <code>null</code> 
    *       caso não encontre nenhuma instância de <code>org.w3c.dom.Node</code> com esse nome.
    */
   Node nodeWithName(List<Node> nodes, String name);
   
   /**
    * Retorna uma <i>sublist</i> contendo instâncias de <code>org.w3c.dom.Node</code>, retiradas da lista <code>nodes</code>, contendo o nome 
    * especificado por parâmetro.
    *   
    * @param nodes
    *       lista de <code>org.w3c.dom.Node</code> da qual uma <i>sublist</i> será retornada.
    *       
    * @param name
    *       <code>java.lang.String</code> contendo o nome das instâncias de <code>org.w3c.dom.Node</code> que deverão ser retornadas.
    *       
    * @return
    *       lista de <code>org.w3c.dom.Node</code> correspondente aos elementos de <code>nodes</code> contendo o nome
    *       especificado por parâmetro.
    */
   List<Node> sublistWithName(List<Node> nodes, String name);
   
   /**
    * Localiza a instância de <code>org.w3c.dom.Node</code> na instância de <code>org.w3c.dom.Document</code> correspondente ao
    * <code>path</code> especificado.
    * 
    * @param document
    *       instância de <code>org.w3c.dom.Document</code> do qual determinado elemento deverá ser localizado.
    *       
    * @param path
    *       <code>java.lang.String</code> contendo o <i>path</i> do elemento que deverá ser localizado.
    *       
    * @return
    *       instância de <code>org.w3c.dom.Node</code> correspondente ao elemento localizado ou o valor <code>null</code>, caso o contrário.
    */
   Node find(Document document, String path);
}
