package org.exolab.jms.server.net;
public class RemoteServerConnectionFactory__Proxy
    extends org.exolab.jms.net.proxy.Proxy
    implements org.exolab.jms.server.ServerConnectionFactory {
    
    private static final java.lang.reflect.Method CREATECONNECTION_b8c1c99ab9c5108e;
    
    private static final org.exolab.jms.net.proxy.ThrowableAdapter JMSEXCEPTIONADAPTER_ffffffffb2d3554d = new org.exolab.jms.client.net.JMSExceptionAdapter();
    
    public RemoteServerConnectionFactory__Proxy(org.exolab.jms.net.proxy.Delegate delegate) {
        super(delegate);
    }
    
    public org.exolab.jms.server.ServerConnection createConnection(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2)
        throws javax.jms.JMSException { 
        Object[] args = new Object[] {arg0, arg1, arg2};
        Object result;
        try {
            result = invoke(CREATECONNECTION_b8c1c99ab9c5108e, args, 0xb8c1c99ab9c5108eL);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            javax.jms.JMSException error = (javax.jms.JMSException) JMSEXCEPTIONADAPTER_ffffffffb2d3554d.adapt(exception);
            throw error;
        }
        return (org.exolab.jms.server.ServerConnection) result;
    }
    
    static {
        try {
            CREATECONNECTION_b8c1c99ab9c5108e = org.exolab.jms.server.ServerConnectionFactory.class.getMethod("createConnection", new Class[] {java.lang.String.class, java.lang.String.class, java.lang.String.class});
        } catch (NoSuchMethodException exception) {
            throw new NoSuchMethodError(exception.getMessage());
        }
    }
    
}
