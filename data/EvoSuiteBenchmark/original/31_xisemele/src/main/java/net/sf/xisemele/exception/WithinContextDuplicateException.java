package net.sf.xisemele.exception;

/**
 * Exceção disparada quando tentar invocar o método <code>within()</code> duas vezes consecutivas.
 * 
 * @author Carlos Eduardo Coral.
 */
public class WithinContextDuplicateException extends XisemeleException {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Cria uma nova instância de {@link WithinContextDuplicateException}.
    */
   public WithinContextDuplicateException() {
      super("net.sf.xisemele.within.context.duplicate");
   }
}
