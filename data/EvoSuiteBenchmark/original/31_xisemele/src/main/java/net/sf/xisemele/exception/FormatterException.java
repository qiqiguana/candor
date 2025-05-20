package net.sf.xisemele.exception;

/**
 * Exceção disparada quando ocorrer algum erro ao tentar converter um texto
 * para um tipo específico ou para quando efetuar o processo inverso de formatar
 * um tipo específico para texto.  
 * 
 * @author Carlos Eduardo Coral. 
 */
public class FormatterException extends XisemeleException {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Cria uma nova instância de {@link FormatterException} para um valor e para o
    * tipo específico do qual ocorreu erro ao tentar converter.
    * 
    * @param type
    *       <code>java.lang.Class</code> correspondente ao tipo que ocorreu erro
    *       ao tentar converter.
    *       
    * @param value
    *       <code>java.lang.String</code> contendo o valor que tentou ser convertido.
    */
   public FormatterException(Class<?> type, String value) {
      super("net.sf.xisemele.formatter.exception", type.getName(), value);
   }
   
   /**
    * Cria uma nova instância de {@link FormatterException} para um valor, para o
    * tipo específico do qual ocorreu o erro ao tentar converter e para uma instânci
    * de <code>java.lang.Throwable</code> que originou a exceção.
    * 
    * @param type
    *       <code>java.lang.Class</code> correspondente ao tipo que ocorreu erro
    *       ao tentar converter.
    *       
    * @param value
    *       <code>java.lang.String</code> contendo o valor que tentou ser convertido.
    *       
    * @param cause
    *       <code>java.lang.Throwable</code> que originou a exceção.     
    */
   public FormatterException(Class<?> type, String value, Throwable cause) {
      super("net.sf.xisemele.formatter.exception", cause, type.getName(), value);
   }
}
