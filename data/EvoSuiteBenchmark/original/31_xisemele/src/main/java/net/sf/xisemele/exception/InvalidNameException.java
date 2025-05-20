package net.sf.xisemele.exception;

/**
 * Exceção disparada quando tentar atribuir algum nome inválido para um determinado elemento ou atributo. 
 * 
 * @author Carlos Eduardo Coral.
 */
public class InvalidNameException extends XisemeleException {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Cria uma nova instância de {@link InvalidNameException} para a <code>java.lang.String</code> contendo o nome
    * que não passou na validação.
    * 
    * @param invalidName
    *       <code>java.lang.String</code> contendo o nome que originou a exceção.
    */
   public InvalidNameException(String invalidName) {
      super("net.sf.xisemele.invalid.name", invalidName);
   }
}
