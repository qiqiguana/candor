package org.exolab.jms.net.invoke;
public class LoggingCallback__Proxy
    extends org.exolab.jms.net.proxy.Proxy
    implements org.exolab.jms.net.Callback {
    
    private static final java.lang.reflect.Method INVOKE_3f69799313667d6b;
    
    
    public LoggingCallback__Proxy(org.exolab.jms.net.proxy.Delegate delegate) {
        super(delegate);
    }
    
    public void invoke(java.lang.Object arg0) {
        Object[] args = new Object[] {arg0};
        try {
            invoke(INVOKE_3f69799313667d6b, args, 0x3f69799313667d6bL);
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    static {
        try {
            INVOKE_3f69799313667d6b = org.exolab.jms.net.Callback.class.getMethod("invoke", new Class[] {java.lang.Object.class});
        } catch (NoSuchMethodException exception) {
            throw new NoSuchMethodError(exception.getMessage());
        }
    }
    
}
