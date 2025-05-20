package net.sf.xisemele.impl;

import java.io.Serializable;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Implementação nula de <code>org.xml.sax.ErrorHandler</code>.
 * 
 * @author Carlos Eduardo Coral.
 */
class NullErrorHandler implements ErrorHandler, Serializable {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * {@inheritDoc}
    */
   public void error(SAXParseException exception) throws SAXException {
   }

   /**
    * {@inheritDoc}
    */
   public void fatalError(SAXParseException exception) throws SAXException {
   }

   /**
    * {@inheritDoc}
    */
   public void warning(SAXParseException exception) throws SAXException {
   }
}
