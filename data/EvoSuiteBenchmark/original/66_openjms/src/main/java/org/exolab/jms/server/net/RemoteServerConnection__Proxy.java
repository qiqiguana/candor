package org.exolab.jms.server.net;
public class RemoteServerConnection__Proxy
    extends org.exolab.jms.net.proxy.Proxy
    implements org.exolab.jms.server.ServerConnection, org.exolab.jms.net.connector.CallerListener {
    
    private static final java.lang.reflect.Method CLOSE_67ce6237;
    private static final java.lang.reflect.Method CREATESESSION_fc250438b312c798;
    private static final java.lang.reflect.Method DISCONNECTED_97a2a68e6a379ecd;
    private static final java.lang.reflect.Method GETCLIENTID_ffffffff949daba2;
    private static final java.lang.reflect.Method GETCONNECTIONID_ffffffffb1fcf328;
    private static final java.lang.reflect.Method SETCLIENTID_b8c1c99acedc6b67;
    
    private static final org.exolab.jms.net.proxy.ThrowableAdapter JMSEXCEPTIONADAPTER_ffffffffb2d3554d = new org.exolab.jms.client.net.JMSExceptionAdapter();
    
    public RemoteServerConnection__Proxy(org.exolab.jms.net.proxy.Delegate delegate) {
        super(delegate);
    }
    
    public void close()
        throws javax.jms.JMSException { 
        try {
            invoke(CLOSE_67ce6237, null, 0x67ce6237L);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            javax.jms.JMSException error = (javax.jms.JMSException) JMSEXCEPTIONADAPTER_ffffffffb2d3554d.adapt(exception);
            throw error;
        }
        
    }
    
    public org.exolab.jms.server.ServerSession createSession(int arg0, boolean arg1)
        throws javax.jms.JMSException { 
        Object[] args = new Object[] {new java.lang.Integer(arg0), new java.lang.Boolean(arg1)};
        Object result;
        try {
            result = invoke(CREATESESSION_fc250438b312c798, args, 0xfc250438b312c798L);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            javax.jms.JMSException error = (javax.jms.JMSException) JMSEXCEPTIONADAPTER_ffffffffb2d3554d.adapt(exception);
            throw error;
        }
        return (org.exolab.jms.server.ServerSession) result;
    }
    
    public void disconnected(org.exolab.jms.net.connector.Caller arg0) {
        Object[] args = new Object[] {arg0};
        try {
            invoke(DISCONNECTED_97a2a68e6a379ecd, args, 0x97a2a68e6a379ecdL);
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public java.lang.String getClientID()
        throws javax.jms.JMSException { 
        Object result;
        try {
            result = invoke(GETCLIENTID_ffffffff949daba2, null, 0xffffffff949daba2L);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            javax.jms.JMSException error = (javax.jms.JMSException) JMSEXCEPTIONADAPTER_ffffffffb2d3554d.adapt(exception);
            throw error;
        }
        return (java.lang.String) result;
    }
    
    public long getConnectionId()
        throws javax.jms.JMSException { 
        Object result;
        try {
            result = invoke(GETCONNECTIONID_ffffffffb1fcf328, null, 0xffffffffb1fcf328L);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            javax.jms.JMSException error = (javax.jms.JMSException) JMSEXCEPTIONADAPTER_ffffffffb2d3554d.adapt(exception);
            throw error;
        }
        return ((java.lang.Long) result).longValue();
    }
    
    public void setClientID(java.lang.String arg0)
        throws javax.jms.JMSException { 
        Object[] args = new Object[] {arg0};
        try {
            invoke(SETCLIENTID_b8c1c99acedc6b67, args, 0xb8c1c99acedc6b67L);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            javax.jms.JMSException error = (javax.jms.JMSException) JMSEXCEPTIONADAPTER_ffffffffb2d3554d.adapt(exception);
            throw error;
        }
        
    }
    
    static {
        try {
            CLOSE_67ce6237 = org.exolab.jms.server.ServerConnection.class.getMethod("close", new Class[] {});
            CREATESESSION_fc250438b312c798 = org.exolab.jms.server.ServerConnection.class.getMethod("createSession", new Class[] {int.class, boolean.class});
            DISCONNECTED_97a2a68e6a379ecd = org.exolab.jms.net.connector.CallerListener.class.getMethod("disconnected", new Class[] {org.exolab.jms.net.connector.Caller.class});
            GETCLIENTID_ffffffff949daba2 = org.exolab.jms.server.ServerConnection.class.getMethod("getClientID", new Class[] {});
            GETCONNECTIONID_ffffffffb1fcf328 = org.exolab.jms.server.ServerConnection.class.getMethod("getConnectionId", new Class[] {});
            SETCLIENTID_b8c1c99acedc6b67 = org.exolab.jms.server.ServerConnection.class.getMethod("setClientID", new Class[] {java.lang.String.class});
        } catch (NoSuchMethodException exception) {
            throw new NoSuchMethodError(exception.getMessage());
        }
    }
    
}
