package net.sf.xisemele.impl;

import java.util.Date;

import net.sf.xisemele.api.Element;
import net.sf.xisemele.api.Result;
import net.sf.xisemele.api.Writer;
import net.sf.xisemele.exception.AttributeNotPermittedException;
import net.sf.xisemele.exception.InvalidNameException;
import net.sf.xisemele.exception.NotWithinContextException;
import net.sf.xisemele.exception.RootDuplicateException;
import net.sf.xisemele.exception.WithinContextNotPermittedException;

import org.w3c.dom.Document;

/**
 * Implementação de {@link Writer}.
 * 
 * @author Carlos Eduardo Coral.
 */
class WriterImpl implements Writer {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;
   
   /**
    * Instância de {@link WriterEditor} que será usada para escrever determinado documento XML.
    */
   private final WriterEditor writerEditor;

   /**
    * Cria uma nova instância de {@link WriterImpl} para a instância de {@link WriterEditor} especificada por parâmetro.
    * 
    * @param writerEditor
    *       instância de {@link WriterEditor} que será usada para escrever determinado documento XML.
    */
   public WriterImpl(final WriterEditor writerEditor) {
      this.writerEditor = writerEditor;
   }
   
   /**
    * {@inheritDoc}
    */
   public Writer attribute(String name, Object value) throws AttributeNotPermittedException, InvalidNameException {
      writerEditor.attribute(name, value);
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Writer attribute(String name, Date date, String pattern) throws AttributeNotPermittedException, InvalidNameException {
      writerEditor.attribute(name, date, pattern);
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Writer element(String name) throws RootDuplicateException, InvalidNameException {
      writerEditor.element(name);
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Writer element(String name, Object value) throws RootDuplicateException, InvalidNameException {
      writerEditor.element(name);
      writerEditor.value(value);
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Writer element(String name, Date date, String pattern) throws RootDuplicateException, InvalidNameException {
      writerEditor.element(name);
      writerEditor.value(date, pattern);
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Writer element(Element element) throws RootDuplicateException {
      writerEditor.node(element.node());
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Result result() {
      return writerEditor.result();
   }

   /**
    * {@inheritDoc}
    */
   public Document document() {
      return writerEditor.document();
   }

   /**
    * {@inheritDoc}
    */
   public Writer within() throws WithinContextNotPermittedException {
      writerEditor.within();
      return this;
   }
   
   /**
    * {@inheritDoc}
    */
   public Writer endWithin() throws NotWithinContextException {
      writerEditor.endWithin();
      return this;
   }
}
