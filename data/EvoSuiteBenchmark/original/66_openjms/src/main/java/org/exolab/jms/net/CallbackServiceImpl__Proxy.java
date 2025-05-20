package org.exolab.jms.net;
public class CallbackServiceImpl__Proxy
    extends org.exolab.jms.net.proxy.Proxy
    implements org.exolab.jms.net.CallbackService {
    
    private static final java.lang.reflect.Method ADDCALLBACK_55dc9778e86c3dbc;
    private static final java.lang.reflect.Method GETCALLBACKS_ffffffff912ba068;
    private static final java.lang.reflect.Method INVOKE_c096866ceab7b5c2;
    private static final java.lang.reflect.Method REMOVECALLBACK_55dc9778c5774953;
    
    
    public CallbackServiceImpl__Proxy(org.exolab.jms.net.proxy.Delegate delegate) {
        super(delegate);
    }
    
    public void addCallback(org.exolab.jms.net.Callback arg0) {
        Object[] args = new Object[] {arg0};
        try {
            invoke(ADDCALLBACK_55dc9778e86c3dbc, args, 0x55dc9778e86c3dbcL);
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public java.util.List getCallbacks() {
        Object result;
        try {
            result = invoke(GETCALLBACKS_ffffffff912ba068, null, 0xffffffff912ba068L);
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return (java.util.List) result;
    }
    
    public void invoke(java.lang.Object arg0) {
        Object[] args = new Object[] {arg0};
        try {
            invoke(INVOKE_c096866ceab7b5c2, args, 0xc096866ceab7b5c2L);
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public void removeCallback(org.exolab.jms.net.Callback arg0) {
        Object[] args = new Object[] {arg0};
        try {
            invoke(REMOVECALLBACK_55dc9778c5774953, args, 0x55dc9778c5774953L);
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    static {
        try {
            ADDCALLBACK_55dc9778e86c3dbc = org.exolab.jms.net.CallbackService.class.getMethod("addCallback", new Class[] {org.exolab.jms.net.Callback.class});
            GETCALLBACKS_ffffffff912ba068 = org.exolab.jms.net.CallbackService.class.getMethod("getCallbacks", new Class[] {});
            INVOKE_c096866ceab7b5c2 = org.exolab.jms.net.CallbackService.class.getMethod("invoke", new Class[] {java.lang.Object.class});
            REMOVECALLBACK_55dc9778c5774953 = org.exolab.jms.net.CallbackService.class.getMethod("removeCallback", new Class[] {org.exolab.jms.net.Callback.class});
        } catch (NoSuchMethodException exception) {
            throw new NoSuchMethodError(exception.getMessage());
        }
    }
    
}
