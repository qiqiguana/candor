package net.sf.xisemele.impl;

import java.io.Serializable;

/**
 * Definição de interface para contexto <i>within</i>.
 * 
 * @author Carlos Eduardo Coral.
 */
interface WithinContext extends Serializable {

   /**
    * Indica se o contexto <i>within</i> está ativado ou não.
    * 
    * @return
    *       <code>true</code> se o contexto <i>within</i> estiver ativado.
    */
   boolean enabled();
   
   /**
    * Inicia o contexto <i>within</i>.
    */
   void start();
   
   /**
    * Finaliza o contexto <i>within</i>.
    */
   void stop();
}
