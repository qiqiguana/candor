package net.sf.xisemele.exception;

import java.io.File;

/**
 * Exceção disparada quando ocorrer algum erro com leitura ou escrita de arquivos. 
 * 
 * @author Carlos Eduardo Coral.
 *
 */
public class XisemeleIOException extends XisemeleException {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Cria uma nova instância de {@link XisemeleIOException}.
    * 
    * @param file
    *       instância de <code>java.io.File</code> em que ocorreu o erro.
    * 
    * @param cause
    *       instância de <code>java.lang.Throwable</code> que originou a exceção.
    */
   public XisemeleIOException(File file, Throwable cause) {
      super("net.sf.xisemele.io.exception", cause, file.getName());
   }
}
