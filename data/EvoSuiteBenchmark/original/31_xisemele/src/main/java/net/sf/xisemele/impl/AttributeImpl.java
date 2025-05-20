package net.sf.xisemele.impl;

import net.sf.xisemele.api.Attribute;
import net.sf.xisemele.api.Value;

/**
 * Implementação de {@link Attribute}.
 * 
 * @author Carlos Eduardo Coral.
 */
class AttributeImpl implements Attribute {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Nome do atributo.
    */
   private final String name;
   
   /**
    * instância de {@link Value} correspondente ao valor do atributo.
    */
   private final Value value;
   
   /**
    * Cria uma nova instância de {@link AttributeImpl}.
    * 
    * @param name
    *          <code>java.lang.String</code> contendo o nome do atributo.
    *          
    * @param value
    *          instância de {@link Value} contendo o valor do atributo.
    */
   public AttributeImpl(String name, Value value) {
      this.name = name;
      this.value = value;
   }

   /**
    * {@inheritDoc}
    */
   public String name() {
      return name;
   }

   /**
    * {@inheritDoc}
    */
   public Value value() {
      return value;
   }
   
   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o) {
      if (o instanceof AttributeImpl) {
         AttributeImpl attr = (AttributeImpl) o;
         return name.equals(attr.name) && value.equals(attr.value);
      }
      return false;
   }
   
   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode() {
      return name.hashCode() + value.hashCode();
   }
}
