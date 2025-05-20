package net.sf.xisemele.impl;

import java.io.Serializable;

import net.sf.xisemele.api.Formatter;

/**
 * Definição de interface para um provedor de {@link Formatter}.
 *  
 * @author Carlos Eduardo Coral.
 */
interface FormatterProvider extends Serializable {

   /**
    * Associa uma instância de {@link Formatter} a um determinado tipo.
    * 
    * @param type
    *       <code>java.lang.Class</code> contendo o tipo do qual a instância de {@link Formatter} será associada.
    *       
    * @param formatter
    *       instância de {@link Formatter} que será associada ao tipo especificado por parâmetro.
    */
   void setFormatter(Class<?> type, Formatter<?> formatter);
   
   /**
    * Retorna a instância de {@link Formatter} associada com o tipo especificado por parâmetro.
    * 
    * @param type
    *       <code>java.lang.Class</code> contendo o tipo do qual se deseja obter a instância de {@link Formatter} correspondente.
    *       
    * @return
    *       instância de {@link Formatter} correspondente ao tipo especificado ou o valor <code>null</code>, caso não encontre.
    */
   <T> Formatter<T> getFormatter(Class<?> type);
}
