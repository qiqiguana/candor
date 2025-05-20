package net.sf.xisemele.exception;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Classe base para todas as exceções do <code>Xisemele</code>.
 * 
 * @author Carlos Eduardo Coral.
 */
public class XisemeleException extends RuntimeException {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;
   
   /**
    * Cria uma nova instância de {@link XisemeleException}.
    * 
    * @param keyException
    *       <code>java.lang.String</code> contendo a chave que deverá ser usada para
    *       recuperar a mensagem correspondente no arquivo de mensagens.
    *       
    * @param cause
    *       instância de <code>java.lang.Throwable</code> que originou a exceção.
    *       
    * @param arguments
    *       <i>array</i> de valores que deverá ser usado para formatar a mensagem da exceção.
    */
   public XisemeleException(String keyException, Throwable cause, Object... arguments) {
      super(formatMessage(keyException, arguments), cause);
   }
   
   /**
    * Cria uma nova instância de {@link XisemeleException}.
    * 
    * @param keyException
    *       <code>java.lang.String</code> contendo a chave que deverá ser usada para
    *       recuperar a mensagem correspondente no arquivo de mensagens.
    *       
    * @param arguments
    *       <i>array</i> de valores que deverá ser usado para formatar a mensagem da exceção.
    */
   public XisemeleException(String keyException, Object... arguments) {
      super(formatMessage(keyException, arguments));
   }
   
   /**
    * Retorna a mensagem correspondente à chave <code>key</code> formatada de acordo com
    * os valores especificados por parâmetro.
    * 
    * @param key
    *       <code>java.lang.String</code> contendo a chave que será utilizada para recuperar
    *       a mensagem do arquivo de mensagens.
    *       
    * @param arguments
    *       <i>array</i> de valores que será usado para formatar a mensagem recuperada.
    *       
    * @return
    *       <code>java.lang.String</code> contendo a mensagem formatada.
    */
   private static String formatMessage(final String key, final Object... arguments) {
      ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
      return MessageFormat.format(resourceBundle.getString(key), arguments);
   }
}
