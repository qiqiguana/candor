package net.sf.xisemele.impl;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Implementação de {@link Message}.
 * 
 * @author Carlos Eduardo Coral.
 */
class MessageImpl implements Message {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * {@inheritDoc}
    */
   public String getMessage(String key, Object... arguments) {
      ResourceBundle resourceBundle = ResourceBundle.getBundle("messages"); 
      return MessageFormat.format(resourceBundle.getString(key), arguments);
   }
}
