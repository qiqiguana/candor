package net.sf.xisemele.exception;

/**
 * Exceção disparada quando tentar adicionar um atributo em um contexto <i>within</i> que não tenha sido
 * adicionado um elemento ainda.
 * 
 * @author Carlos Eduardo Coral.
 */
public class AttributeNotPermittedException extends XisemeleException {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Cria uma nova instância de {@link AttributeNotPermittedException}.
    */
   public AttributeNotPermittedException() {
      super("net.sf.xisemele.attribute.not.permitted");
   }
}
