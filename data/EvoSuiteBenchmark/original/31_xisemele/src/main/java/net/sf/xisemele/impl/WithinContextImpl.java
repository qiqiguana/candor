package net.sf.xisemele.impl;

/**
 * Implementação de {@link WithinContext}.
 * 
 * @author Carlos Eduardo Coral.
 */
class WithinContextImpl implements WithinContext {

   /**
    * Serial version.
    */
   private static final long serialVersionUID = 1L;
   
   /**
    * Indica se a instância de {@link WithinContextImpl} está habilitada ou não. 
    */
   private boolean enabled;
   
   /**
    * {@inheritDoc}
    */
   public boolean enabled() {
      return enabled;
   }

   /**
    * {@inheritDoc}
    */
   public void start() {
      enabled = true;
   }

   /**
    * {@inheritDoc}
    */
   public void stop() {
      enabled = false;
   }
}
