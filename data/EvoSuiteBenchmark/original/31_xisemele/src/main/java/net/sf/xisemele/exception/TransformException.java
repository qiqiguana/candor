package net.sf.xisemele.exception;

/**
 * Exceção disparada quando ocorrer algum erro ao tentar transformar o documento
 * XML para <code>java.lang.String</code>.
 * 
 * @author Carlos Eduardo Coral.
 */
public class TransformException extends XisemeleException {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;
   
   /**
    * Cria uma nova instância de {@link TransformException}.
    * 
    * @param cause
    *       instância de <code>java.lang.Throwable</code> que originou a exceção.
    */
   public TransformException(Throwable cause) {
      super("net.sf.xisemele.transform.exception", cause);
   }
}
