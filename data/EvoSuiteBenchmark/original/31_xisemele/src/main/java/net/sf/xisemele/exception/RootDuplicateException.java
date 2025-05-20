package net.sf.xisemele.exception;

/**
 * Exceção disparada quando tentar adicionar mais de um elemento raiz em um
 * documento XML.
 * 
 * @author Carlos Eduardo Coral.
 */
public class RootDuplicateException extends XisemeleException {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Cria uma nova instância {@link RootDuplicateException}.
    * 
    * @param rootName 
    *       <code>java.lang.String</code> contendo o nome do elemento raiz do
    *       documento XML.
    *       
    * @param duplicateRootName
    *       <code>java.lang.String</code> contendo o nome do elemento
    *       que tentou ser inserido como raiz duplicada.
    */
   public RootDuplicateException(String rootName, String duplicateRootName) {
      super("net.sf.xisemele.root.duplicate", duplicateRootName, rootName);
   }
}
