package net.sf.xisemele.exception;

/**
 * Exceção disparada quando tentar finalizar um contexto <i>within</i> sem ter nenhum
 * contexto <i>within</i> iniciado. 
 * 
 * @author Carlos Eduardo Coral.
 */
public class NotWithinContextException extends XisemeleException {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Cria uma nova instância de {@link NotWithinContextException}.
    */
   public NotWithinContextException() {
      super("net.sf.xisemele.not.within.context");
   }
}
