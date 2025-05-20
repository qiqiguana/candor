package net.sf.xisemele.exception;

/**
 * Exceção disparada quando tentar atribuir um valor para um elemento que já contenha elementos filhos.
 * 
 * @author Carlos Eduardo Coral.
 */
public class ValueNotPermittedException extends XisemeleException {

   /**
    * Serial version. 
    */
   private static final long serialVersionUID = 1L;

   /**
    * Cria uma nova instância de {@link ValueNotPermittedException} para o elemento cujo o nome é especificado por parâmetro.
    * 
    * @param elementName
    *       <code>java.lang.String</code> contendo o nome do elemento que originou a exceção.
    */
   public ValueNotPermittedException(String elementName) {
      super("net.sf.xisemele.value.not.permitted", elementName);
   }
}
