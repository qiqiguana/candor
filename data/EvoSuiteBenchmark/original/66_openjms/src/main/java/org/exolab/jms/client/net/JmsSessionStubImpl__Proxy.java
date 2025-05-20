package org.exolab.jms.client.net;
public class JmsSessionStubImpl__Proxy
    extends org.exolab.jms.net.proxy.Proxy
    implements org.exolab.jms.server.ServerSession, org.exolab.jms.client.JmsMessageListener {
    
    private static final java.lang.reflect.Method ACKNOWLEDGEMESSAGE_470cf01925e61066;
    private static final java.lang.reflect.Method BROWSE_ffccae6c832070ed;
    private static final java.lang.reflect.Method CLOSE_28eb0055;
    private static final java.lang.reflect.Method CLOSECONSUMER_32c67c058993a3;
    private static final java.lang.reflect.Method COMMIT_6dac11818271f49a;
    private static final java.lang.reflect.Method COMMIT_ffffffff8271f49a;
    private static final java.lang.reflect.Method CREATEBROWSER_1723829a3ad04c9;
    private static final java.lang.reflect.Method CREATECONSUMER_d25fc17d386e8757;
    private static final java.lang.reflect.Method CREATEDURABLECONSUMER_45ff3606f45ec510;
    private static final java.lang.reflect.Method END_918915b92d4f5576;
    private static final java.lang.reflect.Method FORGET_6e777da999113c40;
    private static final java.lang.reflect.Method GETRESOURCEMANAGERID_23f8ced8;
    private static final java.lang.reflect.Method GETTRANSACTIONTIMEOUT_ffffffff8dc0054f;
    private static final java.lang.reflect.Method ONMESSAGE_88da14d096fb4afd;
    private static final java.lang.reflect.Method ONMESSAGEAVAILABLE_56ab00e8;
    private static final java.lang.reflect.Method PREPARE_6e777da9c07e16d1;
    private static final java.lang.reflect.Method RECEIVE_1ade15f5;
    private static final java.lang.reflect.Method RECEIVENOWAIT_32c67c0ab6d48f;
    private static final java.lang.reflect.Method RECOVER_fffe6810e623cb29;
    private static final java.lang.reflect.Method RECOVER_6dc9f0c9;
    private static final java.lang.reflect.Method ROLLBACK_6e777da9ddca2349;
    private static final java.lang.reflect.Method ROLLBACK_ffffffffddca2349;
    private static final java.lang.reflect.Method SEND_7725eb2f2d7b1225;
    private static final java.lang.reflect.Method SEND_3ec5a5e2d7b1225;
    private static final java.lang.reflect.Method SETASYNCHRONOUS_3e9aa542f94ac67;
    private static final java.lang.reflect.Method SETMESSAGELISTENER_e24ed802b65740b4;
    private static final java.lang.reflect.Method SETTRANSACTIONTIMEOUT_197ef194140b4;
    private static final java.lang.reflect.Method START_918915b92bc419cf;
    private static final java.lang.reflect.Method START_2bc419cf;
    private static final java.lang.reflect.Method STOP_2d78d5af;
    private static final java.lang.reflect.Method UNSUBSCRIBE_473e36650f8af77c;
    
    
    public JmsSessionStubImpl__Proxy(org.exolab.jms.net.proxy.Delegate delegate) {
        super(delegate);
    }
    
    public void acknowledgeMessage(long arg0, java.lang.String arg1)
        throws javax.jms.JMSException { 
        Object[] args = new Object[] {new java.lang.Long(arg0), arg1};
        try {
            invoke(ACKNOWLEDGEMESSAGE_470cf01925e61066, args, 0x470cf01925e61066L);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public java.util.List browse(long arg0, int arg1)
        throws javax.jms.JMSException { 
        Object[] args = new Object[] {new java.lang.Long(arg0), new java.lang.Integer(arg1)};
        Object result;
        try {
            result = invoke(BROWSE_ffccae6c832070ed, args, 0xffccae6c832070edL);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return (java.util.List) result;
    }
    
    public void close()
        throws javax.jms.JMSException { 
        try {
            invoke(CLOSE_28eb0055, null, 0x28eb0055L);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public void closeConsumer(long arg0)
        throws javax.jms.JMSException { 
        Object[] args = new Object[] {new java.lang.Long(arg0)};
        try {
            invoke(CLOSECONSUMER_32c67c058993a3, args, 0x32c67c058993a3L);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public void commit(javax.transaction.xa.Xid arg0, boolean arg1)
        throws javax.transaction.xa.XAException { 
        Object[] args = new Object[] {arg0, new java.lang.Boolean(arg1)};
        try {
            invoke(COMMIT_6dac11818271f49a, args, 0x6dac11818271f49aL);
        } catch (javax.transaction.xa.XAException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public void commit()
        throws javax.jms.JMSException { 
        try {
            invoke(COMMIT_ffffffff8271f49a, null, 0xffffffff8271f49aL);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public long createBrowser(org.exolab.jms.client.JmsQueue arg0, java.lang.String arg1)
        throws javax.jms.JMSException { 
        Object[] args = new Object[] {arg0, arg1};
        Object result;
        try {
            result = invoke(CREATEBROWSER_1723829a3ad04c9, args, 0x1723829a3ad04c9L);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return ((java.lang.Long) result).longValue();
    }
    
    public long createConsumer(org.exolab.jms.client.JmsDestination arg0, java.lang.String arg1, boolean arg2)
        throws javax.jms.JMSException { 
        Object[] args = new Object[] {arg0, arg1, new java.lang.Boolean(arg2)};
        Object result;
        try {
            result = invoke(CREATECONSUMER_d25fc17d386e8757, args, 0xd25fc17d386e8757L);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return ((java.lang.Long) result).longValue();
    }
    
    public long createDurableConsumer(org.exolab.jms.client.JmsTopic arg0, java.lang.String arg1, java.lang.String arg2, boolean arg3)
        throws javax.jms.JMSException { 
        Object[] args = new Object[] {arg0, arg1, arg2, new java.lang.Boolean(arg3)};
        Object result;
        try {
            result = invoke(CREATEDURABLECONSUMER_45ff3606f45ec510, args, 0x45ff3606f45ec510L);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return ((java.lang.Long) result).longValue();
    }
    
    public void end(javax.transaction.xa.Xid arg0, int arg1)
        throws javax.transaction.xa.XAException { 
        Object[] args = new Object[] {arg0, new java.lang.Integer(arg1)};
        try {
            invoke(END_918915b92d4f5576, args, 0x918915b92d4f5576L);
        } catch (javax.transaction.xa.XAException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public void forget(javax.transaction.xa.Xid arg0)
        throws javax.transaction.xa.XAException { 
        Object[] args = new Object[] {arg0};
        try {
            invoke(FORGET_6e777da999113c40, args, 0x6e777da999113c40L);
        } catch (javax.transaction.xa.XAException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public java.lang.String getResourceManagerId()
        throws javax.transaction.xa.XAException { 
        Object result;
        try {
            result = invoke(GETRESOURCEMANAGERID_23f8ced8, null, 0x23f8ced8L);
        } catch (javax.transaction.xa.XAException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return (java.lang.String) result;
    }
    
    public int getTransactionTimeout()
        throws javax.transaction.xa.XAException { 
        Object result;
        try {
            result = invoke(GETTRANSACTIONTIMEOUT_ffffffff8dc0054f, null, 0xffffffff8dc0054fL);
        } catch (javax.transaction.xa.XAException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return ((java.lang.Integer) result).intValue();
    }
    
    public boolean onMessage(org.exolab.jms.message.MessageImpl arg0)
        throws java.rmi.RemoteException { 
        Object[] args = new Object[] {arg0};
        Object result;
        try {
            result = invoke(ONMESSAGE_88da14d096fb4afd, args, 0x88da14d096fb4afdL);
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        return ((java.lang.Boolean) result).booleanValue();
    }
    
    public void onMessageAvailable()
        throws java.rmi.RemoteException { 
        try {
            invoke(ONMESSAGEAVAILABLE_56ab00e8, null, 0x56ab00e8L);
        } catch (java.rmi.RemoteException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new java.rmi.RemoteException(exception.getMessage(), exception);
        }
        
    }
    
    public int prepare(javax.transaction.xa.Xid arg0)
        throws javax.transaction.xa.XAException { 
        Object[] args = new Object[] {arg0};
        Object result;
        try {
            result = invoke(PREPARE_6e777da9c07e16d1, args, 0x6e777da9c07e16d1L);
        } catch (javax.transaction.xa.XAException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return ((java.lang.Integer) result).intValue();
    }
    
    public org.exolab.jms.message.MessageImpl receive(long arg0, long arg1)
        throws javax.jms.JMSException { 
        Object[] args = new Object[] {new java.lang.Long(arg0), new java.lang.Long(arg1)};
        Object result;
        try {
            result = invoke(RECEIVE_1ade15f5, args, 0x1ade15f5L);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return (org.exolab.jms.message.MessageImpl) result;
    }
    
    public org.exolab.jms.message.MessageImpl receiveNoWait(long arg0)
        throws javax.jms.JMSException { 
        Object[] args = new Object[] {new java.lang.Long(arg0)};
        Object result;
        try {
            result = invoke(RECEIVENOWAIT_32c67c0ab6d48f, args, 0x32c67c0ab6d48fL);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return (org.exolab.jms.message.MessageImpl) result;
    }
    
    public javax.transaction.xa.Xid[] recover(int arg0)
        throws javax.transaction.xa.XAException { 
        Object[] args = new Object[] {new java.lang.Integer(arg0)};
        Object result;
        try {
            result = invoke(RECOVER_fffe6810e623cb29, args, 0xfffe6810e623cb29L);
        } catch (javax.transaction.xa.XAException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return (javax.transaction.xa.Xid[]) result;
    }
    
    public void recover()
        throws javax.jms.JMSException { 
        try {
            invoke(RECOVER_6dc9f0c9, null, 0x6dc9f0c9L);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public void rollback(javax.transaction.xa.Xid arg0)
        throws javax.transaction.xa.XAException { 
        Object[] args = new Object[] {arg0};
        try {
            invoke(ROLLBACK_6e777da9ddca2349, args, 0x6e777da9ddca2349L);
        } catch (javax.transaction.xa.XAException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public void rollback()
        throws javax.jms.JMSException { 
        try {
            invoke(ROLLBACK_ffffffffddca2349, null, 0xffffffffddca2349L);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public void send(org.exolab.jms.message.MessageImpl arg0)
        throws javax.jms.JMSException { 
        Object[] args = new Object[] {arg0};
        try {
            invoke(SEND_7725eb2f2d7b1225, args, 0x7725eb2f2d7b1225L);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public void send(java.util.List arg0)
        throws javax.jms.JMSException { 
        Object[] args = new Object[] {arg0};
        try {
            invoke(SEND_3ec5a5e2d7b1225, args, 0x3ec5a5e2d7b1225L);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public void setAsynchronous(long arg0, boolean arg1)
        throws javax.jms.JMSException { 
        Object[] args = new Object[] {new java.lang.Long(arg0), new java.lang.Boolean(arg1)};
        try {
            invoke(SETASYNCHRONOUS_3e9aa542f94ac67, args, 0x3e9aa542f94ac67L);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public void setMessageListener(org.exolab.jms.client.JmsMessageListener arg0) {
        Object[] args = new Object[] {arg0};
        try {
            invoke(SETMESSAGELISTENER_e24ed802b65740b4, args, 0xe24ed802b65740b4L);
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public boolean setTransactionTimeout(int arg0)
        throws javax.transaction.xa.XAException { 
        Object[] args = new Object[] {new java.lang.Integer(arg0)};
        Object result;
        try {
            result = invoke(SETTRANSACTIONTIMEOUT_197ef194140b4, args, 0x197ef194140b4L);
        } catch (javax.transaction.xa.XAException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return ((java.lang.Boolean) result).booleanValue();
    }
    
    public void start(javax.transaction.xa.Xid arg0, int arg1)
        throws javax.transaction.xa.XAException { 
        Object[] args = new Object[] {arg0, new java.lang.Integer(arg1)};
        try {
            invoke(START_918915b92bc419cf, args, 0x918915b92bc419cfL);
        } catch (javax.transaction.xa.XAException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public void start()
        throws javax.jms.JMSException { 
        try {
            invoke(START_2bc419cf, null, 0x2bc419cfL);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public void stop()
        throws javax.jms.JMSException { 
        try {
            invoke(STOP_2d78d5af, null, 0x2d78d5afL);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public void unsubscribe(java.lang.String arg0)
        throws javax.jms.JMSException { 
        Object[] args = new Object[] {arg0};
        try {
            invoke(UNSUBSCRIBE_473e36650f8af77c, args, 0x473e36650f8af77cL);
        } catch (javax.jms.JMSException exception) {
            throw exception;
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    static {
        try {
            ACKNOWLEDGEMESSAGE_470cf01925e61066 = org.exolab.jms.server.ServerSession.class.getMethod("acknowledgeMessage", new Class[] {long.class, java.lang.String.class});
            BROWSE_ffccae6c832070ed = org.exolab.jms.server.ServerSession.class.getMethod("browse", new Class[] {long.class, int.class});
            CLOSE_28eb0055 = org.exolab.jms.server.ServerSession.class.getMethod("close", new Class[] {});
            CLOSECONSUMER_32c67c058993a3 = org.exolab.jms.server.ServerSession.class.getMethod("closeConsumer", new Class[] {long.class});
            COMMIT_6dac11818271f49a = org.exolab.jms.server.ServerSession.class.getMethod("commit", new Class[] {javax.transaction.xa.Xid.class, boolean.class});
            COMMIT_ffffffff8271f49a = org.exolab.jms.server.ServerSession.class.getMethod("commit", new Class[] {});
            CREATEBROWSER_1723829a3ad04c9 = org.exolab.jms.server.ServerSession.class.getMethod("createBrowser", new Class[] {org.exolab.jms.client.JmsQueue.class, java.lang.String.class});
            CREATECONSUMER_d25fc17d386e8757 = org.exolab.jms.server.ServerSession.class.getMethod("createConsumer", new Class[] {org.exolab.jms.client.JmsDestination.class, java.lang.String.class, boolean.class});
            CREATEDURABLECONSUMER_45ff3606f45ec510 = org.exolab.jms.server.ServerSession.class.getMethod("createDurableConsumer", new Class[] {org.exolab.jms.client.JmsTopic.class, java.lang.String.class, java.lang.String.class, boolean.class});
            END_918915b92d4f5576 = org.exolab.jms.server.ServerSession.class.getMethod("end", new Class[] {javax.transaction.xa.Xid.class, int.class});
            FORGET_6e777da999113c40 = org.exolab.jms.server.ServerSession.class.getMethod("forget", new Class[] {javax.transaction.xa.Xid.class});
            GETRESOURCEMANAGERID_23f8ced8 = org.exolab.jms.server.ServerSession.class.getMethod("getResourceManagerId", new Class[] {});
            GETTRANSACTIONTIMEOUT_ffffffff8dc0054f = org.exolab.jms.server.ServerSession.class.getMethod("getTransactionTimeout", new Class[] {});
            ONMESSAGE_88da14d096fb4afd = org.exolab.jms.client.JmsMessageListener.class.getMethod("onMessage", new Class[] {org.exolab.jms.message.MessageImpl.class});
            ONMESSAGEAVAILABLE_56ab00e8 = org.exolab.jms.client.JmsMessageListener.class.getMethod("onMessageAvailable", new Class[] {});
            PREPARE_6e777da9c07e16d1 = org.exolab.jms.server.ServerSession.class.getMethod("prepare", new Class[] {javax.transaction.xa.Xid.class});
            RECEIVE_1ade15f5 = org.exolab.jms.server.ServerSession.class.getMethod("receive", new Class[] {long.class, long.class});
            RECEIVENOWAIT_32c67c0ab6d48f = org.exolab.jms.server.ServerSession.class.getMethod("receiveNoWait", new Class[] {long.class});
            RECOVER_fffe6810e623cb29 = org.exolab.jms.server.ServerSession.class.getMethod("recover", new Class[] {int.class});
            RECOVER_6dc9f0c9 = org.exolab.jms.server.ServerSession.class.getMethod("recover", new Class[] {});
            ROLLBACK_6e777da9ddca2349 = org.exolab.jms.server.ServerSession.class.getMethod("rollback", new Class[] {javax.transaction.xa.Xid.class});
            ROLLBACK_ffffffffddca2349 = org.exolab.jms.server.ServerSession.class.getMethod("rollback", new Class[] {});
            SEND_7725eb2f2d7b1225 = org.exolab.jms.server.ServerSession.class.getMethod("send", new Class[] {org.exolab.jms.message.MessageImpl.class});
            SEND_3ec5a5e2d7b1225 = org.exolab.jms.server.ServerSession.class.getMethod("send", new Class[] {java.util.List.class});
            SETASYNCHRONOUS_3e9aa542f94ac67 = org.exolab.jms.server.ServerSession.class.getMethod("setAsynchronous", new Class[] {long.class, boolean.class});
            SETMESSAGELISTENER_e24ed802b65740b4 = org.exolab.jms.server.ServerSession.class.getMethod("setMessageListener", new Class[] {org.exolab.jms.client.JmsMessageListener.class});
            SETTRANSACTIONTIMEOUT_197ef194140b4 = org.exolab.jms.server.ServerSession.class.getMethod("setTransactionTimeout", new Class[] {int.class});
            START_918915b92bc419cf = org.exolab.jms.server.ServerSession.class.getMethod("start", new Class[] {javax.transaction.xa.Xid.class, int.class});
            START_2bc419cf = org.exolab.jms.server.ServerSession.class.getMethod("start", new Class[] {});
            STOP_2d78d5af = org.exolab.jms.server.ServerSession.class.getMethod("stop", new Class[] {});
            UNSUBSCRIBE_473e36650f8af77c = org.exolab.jms.server.ServerSession.class.getMethod("unsubscribe", new Class[] {java.lang.String.class});
        } catch (NoSuchMethodException exception) {
            throw new NoSuchMethodError(exception.getMessage());
        }
    }
    
}
