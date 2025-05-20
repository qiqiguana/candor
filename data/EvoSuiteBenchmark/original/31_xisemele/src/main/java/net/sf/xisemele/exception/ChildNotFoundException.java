package net.sf.xisemele.exception;

/**
 * Exceção dispara para quando não encontrar determinado filho para um elemento XML. 
 *  
 * @author Carlos Eduardo Coral.
 */
public class ChildNotFoundException extends XisemeleException {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Cria uma nova instância de {@link ElementNotFoundException} para o nome do elemento filho que não foi encontrado.
    * 
    * @param nodeName
    *       <code>java.lang.String</code> contendo o nome do elemento do qual determinado elemento filho não foi encontrado.
    * 
    * @param childName
    *       <code>java.lang.String</code> contendo o nome do elemento filho que não foi encontrado.
    */
   public ChildNotFoundException(String nodeName, String childName) {
      super("net.sf.xisemele.child.not.found", childName);
   }
}
