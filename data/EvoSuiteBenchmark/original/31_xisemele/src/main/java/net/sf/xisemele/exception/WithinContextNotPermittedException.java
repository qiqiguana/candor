package net.sf.xisemele.exception;

/**
 * Exceção disparada quando tentar iniciar um contexto <i>within</i> para um elemento filho já contém valor. 
 * 
 * @author Carlos Eduardo Coral.
 */
public class WithinContextNotPermittedException extends XisemeleException {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Cria uma nova instância de {@link WithinContextNotPermittedException}.
    * 
    * @param element
    *       <code>java.lang.String</code> contendo o nome do elemento em que se tentou iniciar um contexto <i>within</i>.
    */
   public WithinContextNotPermittedException(final String element) {
      super("net.sf.xisemele.within.context.not.permitted", element);
   }
}
