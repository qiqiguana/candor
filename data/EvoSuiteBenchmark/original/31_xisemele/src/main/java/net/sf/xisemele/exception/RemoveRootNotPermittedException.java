package net.sf.xisemele.exception;

/**
 * Exceção disparada quando tentar remover o elemento raiz de algum documento XML.
 * 
 * @author Carlos Eduardo Coral.
 */
public class RemoveRootNotPermittedException extends XisemeleException {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Cria uma instância de {@link RemoveRootNotPermittedException} para o elemento cujo o nome é especificado por parâmetro.
    * 
    * @param elementName
    *       <code>java.lang.String</code> contendo o nome do elemento que originou a exceção.
    */
   public RemoveRootNotPermittedException(String elementName) {
      super("net.sf.xisemele.remove.root.not.permitted", elementName);
   }
}
