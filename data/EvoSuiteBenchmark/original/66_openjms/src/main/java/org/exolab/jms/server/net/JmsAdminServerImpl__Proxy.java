package org.exolab.jms.server.net;
public class JmsAdminServerImpl__Proxy
    extends org.exolab.jms.net.proxy.Proxy
    implements org.exolab.jms.server.net.RemoteJmsAdminServerIfc {
    
    private static final java.lang.reflect.Method CREATECONNECTION_ffffffffbc44bf71;
    
    
    public JmsAdminServerImpl__Proxy(org.exolab.jms.net.proxy.Delegate delegate) {
        super(delegate);
    }
    
    public org.exolab.jms.server.net.RemoteJmsAdminConnectionIfc createConnection(java.lang.String arg0, java.lang.String arg1)
        throws javax.jms.JMSException, java.rmi.RemoteException { 
        Object[] args = new Object[] {arg0, arg1};
        Object result;
        try {
            result = invoke(CREATECONNECTION_ffffffffbc44bf71, args, 0xffffffffbc44bf71L);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        return (org.exolab.jms.server.net.RemoteJmsAdminConnectionIfc) result;
    }
    
    static {
        try {
            CREATECONNECTION_ffffffffbc44bf71 = org.exolab.jms.server.net.RemoteJmsAdminServerIfc.class.getMethod("createConnection", new Class[] {java.lang.String.class, java.lang.String.class});
        } catch (NoSuchMethodException exception) {
            throw new NoSuchMethodError(exception.getMessage());
        }
    }
    
}
