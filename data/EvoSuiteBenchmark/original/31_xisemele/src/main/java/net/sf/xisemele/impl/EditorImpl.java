package net.sf.xisemele.impl;

import java.util.Date;

import net.sf.xisemele.api.Editor;
import net.sf.xisemele.api.Element;
import net.sf.xisemele.api.Result;
import net.sf.xisemele.exception.AttributeNotPermittedException;
import net.sf.xisemele.exception.ElementNotFoundException;
import net.sf.xisemele.exception.NotWithinContextException;
import net.sf.xisemele.exception.RemoveRootNotPermittedException;
import net.sf.xisemele.exception.RootDuplicateException;
import net.sf.xisemele.exception.ValueNotPermittedException;
import net.sf.xisemele.exception.WithinContextDuplicateException;
import net.sf.xisemele.exception.WithinContextNotPermittedException;

import org.w3c.dom.Document;

/**
 * Implementação de {@link Editor}.
 * 
 * @author Carlos Eduardo Coral.
 */
class EditorImpl implements Editor {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Instância de {@link WriterEditor} usada para editar determinado documento XML.
    */
   private final WriterEditor writerEditor;
   
   /**
    * Cria uma nova instância de {@link EditorImpl} para a instância de {@link WriterEditor} especificada por parâmetro.
    * 
    * @param writerEditor
    *       instância de {@link WriterEditor} que será usada para editar determinado documento XML.
    */
   EditorImpl(final WriterEditor writerEditor) {
      this.writerEditor = writerEditor;
   }
   
   /**
    * {@inheritDoc}
    */
   public Editor attribute(String name, Object value) throws AttributeNotPermittedException {
      writerEditor.attribute(name, value);
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Editor attribute(String name, Date value, String pattern) throws AttributeNotPermittedException {
      writerEditor.attribute(name, value, pattern);
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public boolean containsElement(String path) {
      return writerEditor.containsElement(path);
   }

   /**
    * {@inheritDoc}
    */
   public Editor defineAsCurrent(String path) throws ElementNotFoundException {
      writerEditor.defineAsCurrent(path);
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Editor element(String name) throws RootDuplicateException {
      writerEditor.element(name);
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Editor element(String name, Object value) throws RootDuplicateException {
      writerEditor.element(name);
      writerEditor.value(value);
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Editor element(String name, Date value, String pattern) throws RootDuplicateException {
      writerEditor.element(name);
      writerEditor.value(value, pattern);
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Editor element(Element element) throws RootDuplicateException {
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
   public Editor remove() throws RemoveRootNotPermittedException {
      writerEditor.remove();
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Editor removeAttribute(String name) {
      writerEditor.removeAttribute(name);
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Editor removeAttributes() {
      writerEditor.removeAttributes();
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Editor removeChild(String name) {
      writerEditor.removeChild(name);
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Editor removeChildren(String name) {
      writerEditor.removeChildren(name);
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Editor removeChildren() {
      writerEditor.removeChildren();
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Editor rename(String newName) {
      writerEditor.rename(newName);
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Editor value(Object value) throws ValueNotPermittedException {
      writerEditor.value(value);
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Editor value(Date value, String pattern) throws ValueNotPermittedException {
      writerEditor.value(value, pattern);
      return this;
   }

   /**
    * {@inheritDoc}
    */
   public Editor within() throws WithinContextDuplicateException, WithinContextNotPermittedException {
      writerEditor.within();
      return this;
   }
   
   /**
    * {@inheritDoc}
    */
   public Editor endWithin() throws NotWithinContextException {
      writerEditor.endWithin();
      return this;
   }
}
