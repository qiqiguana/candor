package net.sf.xisemele.impl;

import java.io.Serializable;

/**
 * Definição de interface para as mensagem da implementação de Xisemele.
 * 
 * @author Carlos Eduardo Coral.
 */
interface Message extends Serializable {

   /**
    * Retorna a mensagem associada com determinada chave formatada de acordo com os parâmetros especificados..
    * 
    * @param key
    *       <code>java.lang.String</code> contendo a chave da qual a mensagem associada deverá ser retornada.
    *       
    * @param arguments 
    *       <i>array</i> contendo os objetos que serão usados para formatar a mensagem.
    *       
    * @return
    *       <code>java.lang.String</code> contendo a mensagem associada com a chave especificada por parâmetro.
    */
   String getMessage(String key, Object... arguments);
}
