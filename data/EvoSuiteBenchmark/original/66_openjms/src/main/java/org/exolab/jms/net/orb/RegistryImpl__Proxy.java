package org.exolab.jms.net.orb;
public class RegistryImpl__Proxy
    extends org.exolab.jms.net.proxy.Proxy
    implements org.exolab.jms.net.registry.Registry {
    
    private static final java.lang.reflect.Method BIND_2f505ef6a1c998a5;
    private static final java.lang.reflect.Method LOOKUP_b8c1c99a88dad31a;
    private static final java.lang.reflect.Method UNBIND_473e36656c0482ce;
    
    
    public RegistryImpl__Proxy(org.exolab.jms.net.proxy.Delegate delegate) {
        super(delegate);
    }
    
    public void bind(java.lang.String arg0, org.exolab.jms.net.proxy.Proxy arg1)
        throws java.rmi.AccessException, java.rmi.AlreadyBoundException, java.rmi.RemoteException { 
        Object[] args = new Object[] {arg0, arg1};
        try {
            invoke(BIND_2f505ef6a1c998a5, args, 0x2f505ef6a1c998a5L);
        } catch (java.rmi.AccessException exception) {
            throw exception;
        } catch (java.rmi.AlreadyBoundException exception) {
            throw exception;
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        
    }
    
    public org.exolab.jms.net.proxy.Proxy lookup(java.lang.String arg0)
        throws java.rmi.NotBoundException, java.rmi.RemoteException { 
        Object[] args = new Object[] {arg0};
        Object result;
        try {
            result = invoke(LOOKUP_b8c1c99a88dad31a, args, 0xb8c1c99a88dad31aL);
        } catch (java.rmi.NotBoundException exception) {
            throw exception;
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        return (org.exolab.jms.net.proxy.Proxy) result;
    }
    
    public void unbind(java.lang.String arg0)
        throws java.rmi.AccessException, java.rmi.NotBoundException, java.rmi.RemoteException { 
        Object[] args = new Object[] {arg0};
        try {
            invoke(UNBIND_473e36656c0482ce, args, 0x473e36656c0482ceL);
        } catch (java.rmi.AccessException exception) {
            throw exception;
        } catch (java.rmi.NotBoundException exception) {
            throw exception;
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        
    }
    
    static {
        try {
            BIND_2f505ef6a1c998a5 = org.exolab.jms.net.registry.Registry.class.getMethod("bind", new Class[] {java.lang.String.class, org.exolab.jms.net.proxy.Proxy.class});
            LOOKUP_b8c1c99a88dad31a = org.exolab.jms.net.registry.Registry.class.getMethod("lookup", new Class[] {java.lang.String.class});
            UNBIND_473e36656c0482ce = org.exolab.jms.net.registry.Registry.class.getMethod("unbind", new Class[] {java.lang.String.class});
        } catch (NoSuchMethodException exception) {
            throw new NoSuchMethodError(exception.getMessage());
        }
    }
    
}
