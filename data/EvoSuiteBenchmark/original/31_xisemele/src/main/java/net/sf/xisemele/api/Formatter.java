package net.sf.xisemele.api;

import java.io.Serializable;

/**
 * Definição de interface para formatadores para tipos específicos.
 * 
 * @author Carlos Eduardo Coral.
 */
public interface Formatter<T> extends Serializable {

   /**
    * Deve retornar o <code>java.lang.Class</code> correspondente
    * ao tipo que a instância de {@link Formatter} está associada.
    * 
    * @return
    *       <code>java.lang.Class</code> correspondente ao tipo do formatador.
    */
   Class<T> type();
   
   /**
    * Deve retornar <code>java.lang.String</code> correspondente a formatação
    * do objeto especificado por parâmetro.
    * 
    * @param value
    *       objeto que será formatado.
    *       
    * @return
    *       <code>java.lang.String</code> correspondente à formatação do
    *       objeto especificado por parâmetro.
    */
   String format(T value);
   
   /**
    * Deve fazer o processo inverso de {@link #format(Object)}. Ou seja, deve
    * retornar uma nova instância do tipo definido em {@link #type()} 
    * correspondente à <i>string</i> especificada por parâmetro.
    * 
    * @param text
    *       <code>java.lang.String</code> que será convertida para o tipo específico.
    *       
    * @return
    *       nova instância do tipo T correspondente à <i>string</i> especificada por parâmetro.
    */
   T parse(String text);
}
