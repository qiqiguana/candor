package net.sf.xisemele.impl;

import java.io.Serializable;

import net.sf.xisemele.exception.InvalidNameException;

/**
 * Definição de interface com métodos úteis para validação.
 * 
 * @author Carlos Eduardo Coral.
 */
interface Validations extends Serializable {
   
   /**
    * Verifica se os parâmetros especificados sãos nulos. Se algum dos parâmetros for <code>null</code> será disparada
    * a exceção <code>java.lang.NullPointerException</code> contendo a mensagem correspondente à chave 
    * <code>net.sf.xisemele.null.pointer.exception</code> do arquivo de mensagens.
    * 
    * @param parameters
    *       deve ter tamanho par de tal forma que determinada posição par de número <code>i</code> seja uma <code>java.lang.String</code>
    *       contendo o nome de um parâmetro e a posição <code>i+1</code> seja o valor correspondente que será verificado.
    *       
    * @throws NullPointerException
    *       exceção disparada caso um dos parâmetros especificados seja <code>null</code>.
    */
   void assertNotNull(Object... parameters) throws NullPointerException;

   /**
    * Verifica se o nome especificado é válido.
    * 
    * <p>
    * Um nome é considerado válido se corresponder à seguinte gramática:
    * <blockquote><pre>
    * NameStartChar ::=  ":" | [A-Z] | "_" | [a-z] 
    * NameChar      ::=  NameStartChar | "-" | "." | [0-9]
    * Name          ::=  NameStartChar NameChar*
    * </pre></blockquote></p>
    * 
    * @param name
    *       <code>java.lang.String</code> contendo o nome que será validado.
    *       
    * @throws InvalidNameException
    *       exceção disparada caso o nome especificado não seja válido.
    */
   void assertValidName(String name) throws InvalidNameException;
   
   /**
    * Retorna o primeiro valor não <code>null</code> do <i>array</i> de valores especificado por parâmetro.
    * 
    * @param <T>
    *       tipo dos valores que serão verificados.
    *       
    * @param values
    *       <i>array</i> contendo os valores que serão verificados.
    *       
    * @return
    *       o primeiro valor encontrado na lista especificada que não for <code>null</code> ou o valor <code>null</code> caso
    *       todos sejam <code>null</code>.
    */
   <T> T getFirstNotNull(T... values);
}
