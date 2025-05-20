package org.exolab.jms.net;
public class KillServiceImpl__Proxy
    extends org.exolab.jms.net.proxy.Proxy
    implements org.exolab.jms.net.KillService {
    
    private static final java.lang.reflect.Method KILL_32c67c66249f3f;
    
    
    public KillServiceImpl__Proxy(org.exolab.jms.net.proxy.Delegate delegate) {
        super(delegate);
    }
    
    public void kill(long arg0) {
        Object[] args = new Object[] {new java.lang.Long(arg0)};
        try {
            invoke(KILL_32c67c66249f3f, args, 0x32c67c66249f3fL);
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    static {
        try {
            KILL_32c67c66249f3f = org.exolab.jms.net.KillService.class.getMethod("kill", new Class[] {long.class});
        } catch (NoSuchMethodException exception) {
            throw new NoSuchMethodError(exception.getMessage());
        }
    }
    
}
