package net.sf.xisemele.exception;

/**
 * Exceção disparada quando o usuário solicitar uma conversão de valor para um tipo específico
 * e não for localizado um {@link net.sf.xisemele.api.Formatter} correspondente para esse tipo.
 *  
 * @author Carlos Eduardo Coral.
 */
public class FormatterNotConfiguredException extends XisemeleException {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Cria uma nova instância de {@link FormatterNotConfiguredException}.
    * 
    * @param type
    *       <code>java.lang.Class</code> para o tipo do qual um {@link net.sf.xisemele.api.Formatter}
    *       correspondente não foi localizado. 
    */
   public FormatterNotConfiguredException(Class<?> type) {
      super("net.sf.xisemele.formatter.not.configured", type.getName());
   }
}
