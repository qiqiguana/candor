package net.sf.xisemele.exception;

/**
 * Exceção disparada quando tentar acessar um elemento XML para um índice inválido. 
 * 
 * @author Carlos Eduardo Coral.
 */
public class ElementIndexOutOfBoundsException extends XisemeleException {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Cria uma nova instância de {@link ElementIndexOutOfBoundsException}.
    * 
    * @param parentElement
    *       <code>java.lang.String</code> contendo o nome do elemento pai do qual
    *       se tentou acessar um elemento filho em uma posição inválida.
    *       
    * @param index
    *       <code>int</code> contendo o índice inválido.
    */
   public ElementIndexOutOfBoundsException(String parentElement, int index) {
      super("net.sf.xisemele.child.index", parentElement, index);
   }
}
