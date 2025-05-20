package org.exolab.jms.server.net;
public class JmsAdminConnectionImpl__Proxy
    extends org.exolab.jms.net.proxy.Proxy
    implements org.exolab.jms.server.net.RemoteJmsAdminConnectionIfc {
    
    private static final java.lang.reflect.Method ADDDESTINATION_53b35631133ddfbd;
    private static final java.lang.reflect.Method ADDDURABLECONSUMER_1f857200;
    private static final java.lang.reflect.Method ADDUSER_677b0b1c;
    private static final java.lang.reflect.Method CHANGEPASSWORD_ffffffffeb75297b;
    private static final java.lang.reflect.Method DESTINATIONEXISTS_473e36655bd4c91a;
    private static final java.lang.reflect.Method DURABLECONSUMEREXISTS_473e36650276dcbd;
    private static final java.lang.reflect.Method GETALLDESTINATIONS_ffffffff8510a80b;
    private static final java.lang.reflect.Method GETALLUSERS_fffffffff246e266;
    private static final java.lang.reflect.Method GETDURABLECONSUMERMESSAGECOUNT_fffffffffb019274;
    private static final java.lang.reflect.Method GETDURABLECONSUMERS_473e3665586e8a23;
    private static final java.lang.reflect.Method GETQUEUEMESSAGECOUNT_473e3665563b6094;
    private static final java.lang.reflect.Method ISCONNECTED_b8c1c99aff469fef;
    private static final java.lang.reflect.Method PURGEMESSAGES_31debc0;
    private static final java.lang.reflect.Method REMOVEDESTINATION_b8c1c99aff8293fa;
    private static final java.lang.reflect.Method REMOVEDURABLECONSUMER_473e36657d849ddd;
    private static final java.lang.reflect.Method REMOVEUSER_b8c1c99a9d8add7f;
    private static final java.lang.reflect.Method STOPSERVER_71de0f49;
    private static final java.lang.reflect.Method UNREGISTERCONSUMER_b8c1c99a8cfc2b42;
    
    
    public JmsAdminConnectionImpl__Proxy(org.exolab.jms.net.proxy.Delegate delegate) {
        super(delegate);
    }
    
    public boolean addDestination(java.lang.String arg0, java.lang.Boolean arg1)
        throws java.rmi.RemoteException { 
        Object[] args = new Object[] {arg0, arg1};
        Object result;
        try {
            result = invoke(ADDDESTINATION_53b35631133ddfbd, args, 0x53b35631133ddfbdL);
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        return ((java.lang.Boolean) result).booleanValue();
    }
    
    public boolean addDurableConsumer(java.lang.String arg0, java.lang.String arg1)
        throws java.rmi.RemoteException { 
        Object[] args = new Object[] {arg0, arg1};
        Object result;
        try {
            result = invoke(ADDDURABLECONSUMER_1f857200, args, 0x1f857200L);
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        return ((java.lang.Boolean) result).booleanValue();
    }
    
    public boolean addUser(java.lang.String arg0, java.lang.String arg1)
        throws java.rmi.RemoteException { 
        Object[] args = new Object[] {arg0, arg1};
        Object result;
        try {
            result = invoke(ADDUSER_677b0b1c, args, 0x677b0b1cL);
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        return ((java.lang.Boolean) result).booleanValue();
    }
    
    public boolean changePassword(java.lang.String arg0, java.lang.String arg1)
        throws java.rmi.RemoteException { 
        Object[] args = new Object[] {arg0, arg1};
        Object result;
        try {
            result = invoke(CHANGEPASSWORD_ffffffffeb75297b, args, 0xffffffffeb75297bL);
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        return ((java.lang.Boolean) result).booleanValue();
    }
    
    public boolean destinationExists(java.lang.String arg0)
        throws java.rmi.RemoteException { 
        Object[] args = new Object[] {arg0};
        Object result;
        try {
            result = invoke(DESTINATIONEXISTS_473e36655bd4c91a, args, 0x473e36655bd4c91aL);
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        return ((java.lang.Boolean) result).booleanValue();
    }
    
    public boolean durableConsumerExists(java.lang.String arg0)
        throws java.rmi.RemoteException { 
        Object[] args = new Object[] {arg0};
        Object result;
        try {
            result = invoke(DURABLECONSUMEREXISTS_473e36650276dcbd, args, 0x473e36650276dcbdL);
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        return ((java.lang.Boolean) result).booleanValue();
    }
    
    public java.util.Vector getAllDestinations()
        throws java.rmi.RemoteException { 
        Object result;
        try {
            result = invoke(GETALLDESTINATIONS_ffffffff8510a80b, null, 0xffffffff8510a80bL);
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        return (java.util.Vector) result;
    }
    
    public java.util.Vector getAllUsers()
        throws java.rmi.RemoteException { 
        Object result;
        try {
            result = invoke(GETALLUSERS_fffffffff246e266, null, 0xfffffffff246e266L);
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        return (java.util.Vector) result;
    }
    
    public int getDurableConsumerMessageCount(java.lang.String arg0, java.lang.String arg1)
        throws java.rmi.RemoteException { 
        Object[] args = new Object[] {arg0, arg1};
        Object result;
        try {
            result = invoke(GETDURABLECONSUMERMESSAGECOUNT_fffffffffb019274, args, 0xfffffffffb019274L);
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        return ((java.lang.Integer) result).intValue();
    }
    
    public java.util.Vector getDurableConsumers(java.lang.String arg0)
        throws java.rmi.RemoteException { 
        Object[] args = new Object[] {arg0};
        Object result;
        try {
            result = invoke(GETDURABLECONSUMERS_473e3665586e8a23, args, 0x473e3665586e8a23L);
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        return (java.util.Vector) result;
    }
    
    public int getQueueMessageCount(java.lang.String arg0)
        throws java.rmi.RemoteException { 
        Object[] args = new Object[] {arg0};
        Object result;
        try {
            result = invoke(GETQUEUEMESSAGECOUNT_473e3665563b6094, args, 0x473e3665563b6094L);
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        return ((java.lang.Integer) result).intValue();
    }
    
    public boolean isConnected(java.lang.String arg0)
        throws java.rmi.RemoteException { 
        Object[] args = new Object[] {arg0};
        Object result;
        try {
            result = invoke(ISCONNECTED_b8c1c99aff469fef, args, 0xb8c1c99aff469fefL);
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        return ((java.lang.Boolean) result).booleanValue();
    }
    
    public int purgeMessages()
        throws java.rmi.RemoteException { 
        Object result;
        try {
            result = invoke(PURGEMESSAGES_31debc0, null, 0x31debc0L);
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        return ((java.lang.Integer) result).intValue();
    }
    
    public boolean removeDestination(java.lang.String arg0)
        throws java.rmi.RemoteException { 
        Object[] args = new Object[] {arg0};
        Object result;
        try {
            result = invoke(REMOVEDESTINATION_b8c1c99aff8293fa, args, 0xb8c1c99aff8293faL);
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        return ((java.lang.Boolean) result).booleanValue();
    }
    
    public boolean removeDurableConsumer(java.lang.String arg0)
        throws java.rmi.RemoteException { 
        Object[] args = new Object[] {arg0};
        Object result;
        try {
            result = invoke(REMOVEDURABLECONSUMER_473e36657d849ddd, args, 0x473e36657d849dddL);
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        return ((java.lang.Boolean) result).booleanValue();
    }
    
    public boolean removeUser(java.lang.String arg0)
        throws java.rmi.RemoteException { 
        Object[] args = new Object[] {arg0};
        Object result;
        try {
            result = invoke(REMOVEUSER_b8c1c99a9d8add7f, args, 0xb8c1c99a9d8add7fL);
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        return ((java.lang.Boolean) result).booleanValue();
    }
    
    public void stopServer()
        throws java.rmi.RemoteException { 
        try {
            invoke(STOPSERVER_71de0f49, null, 0x71de0f49L);
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        
    }
    
    public boolean unregisterConsumer(java.lang.String arg0)
        throws java.rmi.RemoteException { 
        Object[] args = new Object[] {arg0};
        Object result;
        try {
            result = invoke(UNREGISTERCONSUMER_b8c1c99a8cfc2b42, args, 0xb8c1c99a8cfc2b42L);
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        return ((java.lang.Boolean) result).booleanValue();
    }
    
    static {
        try {
            ADDDESTINATION_53b35631133ddfbd = org.exolab.jms.server.net.RemoteJmsAdminConnectionIfc.class.getMethod("addDestination", new Class[] {java.lang.String.class, java.lang.Boolean.class});
            ADDDURABLECONSUMER_1f857200 = org.exolab.jms.server.net.RemoteJmsAdminConnectionIfc.class.getMethod("addDurableConsumer", new Class[] {java.lang.String.class, java.lang.String.class});
            ADDUSER_677b0b1c = org.exolab.jms.server.net.RemoteJmsAdminConnectionIfc.class.getMethod("addUser", new Class[] {java.lang.String.class, java.lang.String.class});
            CHANGEPASSWORD_ffffffffeb75297b = org.exolab.jms.server.net.RemoteJmsAdminConnectionIfc.class.getMethod("changePassword", new Class[] {java.lang.String.class, java.lang.String.class});
            DESTINATIONEXISTS_473e36655bd4c91a = org.exolab.jms.server.net.RemoteJmsAdminConnectionIfc.class.getMethod("destinationExists", new Class[] {java.lang.String.class});
            DURABLECONSUMEREXISTS_473e36650276dcbd = org.exolab.jms.server.net.RemoteJmsAdminConnectionIfc.class.getMethod("durableConsumerExists", new Class[] {java.lang.String.class});
            GETALLDESTINATIONS_ffffffff8510a80b = org.exolab.jms.server.net.RemoteJmsAdminConnectionIfc.class.getMethod("getAllDestinations", new Class[] {});
            GETALLUSERS_fffffffff246e266 = org.exolab.jms.server.net.RemoteJmsAdminConnectionIfc.class.getMethod("getAllUsers", new Class[] {});
            GETDURABLECONSUMERMESSAGECOUNT_fffffffffb019274 = org.exolab.jms.server.net.RemoteJmsAdminConnectionIfc.class.getMethod("getDurableConsumerMessageCount", new Class[] {java.lang.String.class, java.lang.String.class});
            GETDURABLECONSUMERS_473e3665586e8a23 = org.exolab.jms.server.net.RemoteJmsAdminConnectionIfc.class.getMethod("getDurableConsumers", new Class[] {java.lang.String.class});
            GETQUEUEMESSAGECOUNT_473e3665563b6094 = org.exolab.jms.server.net.RemoteJmsAdminConnectionIfc.class.getMethod("getQueueMessageCount", new Class[] {java.lang.String.class});
            ISCONNECTED_b8c1c99aff469fef = org.exolab.jms.server.net.RemoteJmsAdminConnectionIfc.class.getMethod("isConnected", new Class[] {java.lang.String.class});
            PURGEMESSAGES_31debc0 = org.exolab.jms.server.net.RemoteJmsAdminConnectionIfc.class.getMethod("purgeMessages", new Class[] {});
            REMOVEDESTINATION_b8c1c99aff8293fa = org.exolab.jms.server.net.RemoteJmsAdminConnectionIfc.class.getMethod("removeDestination", new Class[] {java.lang.String.class});
            REMOVEDURABLECONSUMER_473e36657d849ddd = org.exolab.jms.server.net.RemoteJmsAdminConnectionIfc.class.getMethod("removeDurableConsumer", new Class[] {java.lang.String.class});
            REMOVEUSER_b8c1c99a9d8add7f = org.exolab.jms.server.net.RemoteJmsAdminConnectionIfc.class.getMethod("removeUser", new Class[] {java.lang.String.class});
            STOPSERVER_71de0f49 = org.exolab.jms.server.net.RemoteJmsAdminConnectionIfc.class.getMethod("stopServer", new Class[] {});
            UNREGISTERCONSUMER_b8c1c99a8cfc2b42 = org.exolab.jms.server.net.RemoteJmsAdminConnectionIfc.class.getMethod("unregisterConsumer", new Class[] {java.lang.String.class});
        } catch (NoSuchMethodException exception) {
            throw new NoSuchMethodError(exception.getMessage());
        }
    }
    
}
