package net.sf.xisemele.exception;

/**
 * Exceção disparada quando tentar acessar um elemento para um atalho inválido. 
 *  
 * @author Carlos Eduardo Coral.
 */
public class ElementNotFoundException extends XisemeleException {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Cria uma nova instância de {@link ElementNotFoundException} para um
    * atalho que originou a exceção.
    * 
    * @param path
    *       <code>java.lang.String</code> contendo o atalho inválido, que 
    *       originou a exceção.
    */
   public ElementNotFoundException(String path) {
      super("net.sf.xisemele.element.not.found", path);
   }
}
