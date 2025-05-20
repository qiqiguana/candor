package net.sf.xisemele.exception;

/**
 * Exceção disparada caso ocorra algum erro ao efetuar o <i>parse</i> de um documento XML. 
 * 
 * @author Carlos Eduardo Coral. 
 */
public class ParseXMLException extends XisemeleException {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Cria uma nova instância de {@link ParseXMLException}.
    * 
    * @param cause
    *       instância de <code>java.lang.Throwable</code> que originou a exceção.
    */
   public ParseXMLException(Throwable cause) {
      super("net.sf.xisemele.parse.xml.exception", cause);
   }
}
