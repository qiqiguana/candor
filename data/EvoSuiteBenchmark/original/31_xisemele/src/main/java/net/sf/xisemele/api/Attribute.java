package net.sf.xisemele.api;

import java.io.Serializable;

/**
 * Definção de interface para um atributo XML.
 * 
 * @author Carlos Eduardo Coral.
 */
public interface Attribute extends Serializable {

   /**
    * Retorna o nome do atributo.
    * 
    * @return
    *       <code>java.lang.String</code> contendo o nome do atributo.
    */
   String name();
   
   /**
    * Retorna o valor do atributo.
    * 
    * @return
    *       instância de {@link Value} contendo o valor do atributo.
    */
   Value value();
}
