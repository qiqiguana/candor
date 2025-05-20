package org.exolab.jms.net;
public class ExceptionServiceImpl__Proxy
    extends org.exolab.jms.net.proxy.Proxy
    implements org.exolab.jms.net.ExceptionService {
    
    private static final java.lang.reflect.Method THROWERROR_ffffffffddf486fe;
    private static final java.lang.reflect.Method THROWEXCEPTION_ffffffffa450b035;
    private static final java.lang.reflect.Method THROWREMOTEEXCEPTION_635a92df;
    private static final java.lang.reflect.Method THROWRUNTIMEEXCEPTION_7c7dce61;
    private static final java.lang.reflect.Method THROWTHROWABLE_ffffffffc70828c6;
    private static final java.lang.reflect.Method THROWUNDECLAREDERROR_ffffffffab357413;
    private static final java.lang.reflect.Method THROWUNDECLAREDERROR2_441f81df;
    private static final java.lang.reflect.Method THROWUNDECLAREDREMOTEINVOCATIONEXCEPTION_ffffffff871b981c;
    private static final java.lang.reflect.Method THROWUNDECLAREDRUNTIMEEXCEPTION_762e52ec;
    private static final java.lang.reflect.Method THROWUNDECLAREDRUNTIMEEXCEPTION2_5e1b975e;
    
    
    public ExceptionServiceImpl__Proxy(org.exolab.jms.net.proxy.Delegate delegate) {
        super(delegate);
    }
    
    public void throwError()
        throws java.lang.Error { 
        try {
            invoke(THROWERROR_ffffffffddf486fe, null, 0xffffffffddf486feL);
        } catch (java.lang.Error exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public void throwException()
        throws java.lang.Exception { 
        try {
            invoke(THROWEXCEPTION_ffffffffa450b035, null, 0xffffffffa450b035L);
        } catch (java.lang.Exception exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public void throwRemoteException()
        throws java.rmi.RemoteException { 
        try {
            invoke(THROWREMOTEEXCEPTION_635a92df, null, 0x635a92dfL);
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        
    }
    
    public void throwRuntimeException()
        throws java.lang.RuntimeException { 
        try {
            invoke(THROWRUNTIMEEXCEPTION_7c7dce61, null, 0x7c7dce61L);
        } catch (java.lang.RuntimeException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public void throwThrowable()
        throws java.lang.Throwable { 
        try {
            invoke(THROWTHROWABLE_ffffffffc70828c6, null, 0xffffffffc70828c6L);
        } catch (java.lang.Throwable exception) {
            throw exception;
        }
        
    }
    
    public void throwUndeclaredError() {
        try {
            invoke(THROWUNDECLAREDERROR_ffffffffab357413, null, 0xffffffffab357413L);
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public void throwUndeclaredError2()
        throws java.rmi.RemoteException { 
        try {
            invoke(THROWUNDECLAREDERROR2_441f81df, null, 0x441f81dfL);
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        
    }
    
    public void throwUndeclaredRemoteInvocationException() {
        try {
            invoke(THROWUNDECLAREDREMOTEINVOCATIONEXCEPTION_ffffffff871b981c, null, 0xffffffff871b981cL);
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public void throwUndeclaredRuntimeException() {
        try {
            invoke(THROWUNDECLAREDRUNTIMEEXCEPTION_762e52ec, null, 0x762e52ecL);
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public void throwUndeclaredRuntimeException2()
        throws java.rmi.RemoteException { 
        try {
            invoke(THROWUNDECLAREDRUNTIMEEXCEPTION2_5e1b975e, null, 0x5e1b975eL);
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
            THROWERROR_ffffffffddf486fe = org.exolab.jms.net.ExceptionService.class.getMethod("throwError", new Class[] {});
            THROWEXCEPTION_ffffffffa450b035 = org.exolab.jms.net.ExceptionService.class.getMethod("throwException", new Class[] {});
            THROWREMOTEEXCEPTION_635a92df = org.exolab.jms.net.ExceptionService.class.getMethod("throwRemoteException", new Class[] {});
            THROWRUNTIMEEXCEPTION_7c7dce61 = org.exolab.jms.net.ExceptionService.class.getMethod("throwRuntimeException", new Class[] {});
            THROWTHROWABLE_ffffffffc70828c6 = org.exolab.jms.net.ExceptionService.class.getMethod("throwThrowable", new Class[] {});
            THROWUNDECLAREDERROR_ffffffffab357413 = org.exolab.jms.net.ExceptionService.class.getMethod("throwUndeclaredError", new Class[] {});
            THROWUNDECLAREDERROR2_441f81df = org.exolab.jms.net.ExceptionService.class.getMethod("throwUndeclaredError2", new Class[] {});
            THROWUNDECLAREDREMOTEINVOCATIONEXCEPTION_ffffffff871b981c = org.exolab.jms.net.ExceptionService.class.getMethod("throwUndeclaredRemoteInvocationException", new Class[] {});
            THROWUNDECLAREDRUNTIMEEXCEPTION_762e52ec = org.exolab.jms.net.ExceptionService.class.getMethod("throwUndeclaredRuntimeException", new Class[] {});
            THROWUNDECLAREDRUNTIMEEXCEPTION2_5e1b975e = org.exolab.jms.net.ExceptionService.class.getMethod("throwUndeclaredRuntimeException2", new Class[] {});
        } catch (NoSuchMethodException exception) {
            throw new NoSuchMethodError(exception.getMessage());
        }
    }
    
}
