package org.exolab.jms.net.invoke;
public class ExportServiceImpl__Proxy
    extends org.exolab.jms.net.proxy.Proxy
    implements org.exolab.jms.net.invoke.ExportService {
    
    private static final java.lang.reflect.Method EXPORTOBJECTTO_ffffffff8b457cef;
    
    
    public ExportServiceImpl__Proxy(org.exolab.jms.net.proxy.Delegate delegate) {
        super(delegate);
    }
    
    public org.exolab.jms.net.proxy.Proxy exportObjectTo()
        throws java.rmi.RemoteException { 
        Object result;
        try {
            result = invoke(EXPORTOBJECTTO_ffffffff8b457cef, null, 0xffffffff8b457cefL);
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        return (org.exolab.jms.net.proxy.Proxy) result;
    }
    
    static {
        try {
            EXPORTOBJECTTO_ffffffff8b457cef = org.exolab.jms.net.invoke.ExportService.class.getMethod("exportObjectTo", new Class[] {});
        } catch (NoSuchMethodException exception) {
            throw new NoSuchMethodError(exception.getMessage());
        }
    }
    
}
