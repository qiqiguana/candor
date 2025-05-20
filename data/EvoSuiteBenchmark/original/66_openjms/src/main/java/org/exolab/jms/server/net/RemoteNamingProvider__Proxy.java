package org.exolab.jms.server.net;
public class RemoteNamingProvider__Proxy
    extends org.exolab.jms.net.proxy.Proxy
    implements org.codehaus.spice.jndikit.NamingProvider {
    
    private static final java.lang.reflect.Method BIND_45bb8cf0d18ca433;
    private static final java.lang.reflect.Method CREATESUBCONTEXT_3decc306b6b18490;
    private static final java.lang.reflect.Method DESTROYSUBCONTEXT_3decc306c6d2bb07;
    private static final java.lang.reflect.Method GETNAMEPARSER_fffffffff5a0d162;
    private static final java.lang.reflect.Method LIST_c2133cf9556188f5;
    private static final java.lang.reflect.Method LISTBINDINGS_c2133cf95cadb7c5;
    private static final java.lang.reflect.Method LOOKUP_c2133cf950670173;
    private static final java.lang.reflect.Method REBIND_ba44730f19e760fe;
    private static final java.lang.reflect.Method UNBIND_c2133cf91c41be58;
    
    
    public RemoteNamingProvider__Proxy(org.exolab.jms.net.proxy.Delegate delegate) {
        super(delegate);
    }
    
    public void bind(javax.naming.Name arg0, java.lang.String arg1, java.lang.Object arg2)
        throws javax.naming.NamingException, java.lang.Exception { 
        Object[] args = new Object[] {arg0, arg1, arg2};
        try {
            invoke(BIND_45bb8cf0d18ca433, args, 0x45bb8cf0d18ca433L);
        } catch (javax.naming.NamingException exception) {
            throw exception;
        } catch (java.lang.Exception exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public javax.naming.Context createSubcontext(javax.naming.Name arg0)
        throws javax.naming.NamingException, java.lang.Exception { 
        Object[] args = new Object[] {arg0};
        Object result;
        try {
            result = invoke(CREATESUBCONTEXT_3decc306b6b18490, args, 0x3decc306b6b18490L);
        } catch (javax.naming.NamingException exception) {
            throw exception;
        } catch (java.lang.Exception exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return (javax.naming.Context) result;
    }
    
    public void destroySubcontext(javax.naming.Name arg0)
        throws javax.naming.NamingException, java.lang.Exception { 
        Object[] args = new Object[] {arg0};
        try {
            invoke(DESTROYSUBCONTEXT_3decc306c6d2bb07, args, 0x3decc306c6d2bb07L);
        } catch (javax.naming.NamingException exception) {
            throw exception;
        } catch (java.lang.Exception exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public javax.naming.NameParser getNameParser()
        throws javax.naming.NamingException, java.lang.Exception { 
        Object result;
        try {
            result = invoke(GETNAMEPARSER_fffffffff5a0d162, null, 0xfffffffff5a0d162L);
        } catch (javax.naming.NamingException exception) {
            throw exception;
        } catch (java.lang.Exception exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return (javax.naming.NameParser) result;
    }
    
    public javax.naming.NameClassPair[] list(javax.naming.Name arg0)
        throws javax.naming.NamingException, java.lang.Exception { 
        Object[] args = new Object[] {arg0};
        Object result;
        try {
            result = invoke(LIST_c2133cf9556188f5, args, 0xc2133cf9556188f5L);
        } catch (javax.naming.NamingException exception) {
            throw exception;
        } catch (java.lang.Exception exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return (javax.naming.NameClassPair[]) result;
    }
    
    public javax.naming.Binding[] listBindings(javax.naming.Name arg0)
        throws javax.naming.NamingException, java.lang.Exception { 
        Object[] args = new Object[] {arg0};
        Object result;
        try {
            result = invoke(LISTBINDINGS_c2133cf95cadb7c5, args, 0xc2133cf95cadb7c5L);
        } catch (javax.naming.NamingException exception) {
            throw exception;
        } catch (java.lang.Exception exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return (javax.naming.Binding[]) result;
    }
    
    public java.lang.Object lookup(javax.naming.Name arg0)
        throws javax.naming.NamingException, java.lang.Exception { 
        Object[] args = new Object[] {arg0};
        Object result;
        try {
            result = invoke(LOOKUP_c2133cf950670173, args, 0xc2133cf950670173L);
        } catch (javax.naming.NamingException exception) {
            throw exception;
        } catch (java.lang.Exception exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return (java.lang.Object) result;
    }
    
    public void rebind(javax.naming.Name arg0, java.lang.String arg1, java.lang.Object arg2)
        throws javax.naming.NamingException, java.lang.Exception { 
        Object[] args = new Object[] {arg0, arg1, arg2};
        try {
            invoke(REBIND_ba44730f19e760fe, args, 0xba44730f19e760feL);
        } catch (javax.naming.NamingException exception) {
            throw exception;
        } catch (java.lang.Exception exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    public void unbind(javax.naming.Name arg0)
        throws javax.naming.NamingException, java.lang.Exception { 
        Object[] args = new Object[] {arg0};
        try {
            invoke(UNBIND_c2133cf91c41be58, args, 0xc2133cf91c41be58L);
        } catch (javax.naming.NamingException exception) {
            throw exception;
        } catch (java.lang.Exception exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        
    }
    
    static {
        try {
            BIND_45bb8cf0d18ca433 = org.codehaus.spice.jndikit.NamingProvider.class.getMethod("bind", new Class[] {javax.naming.Name.class, java.lang.String.class, java.lang.Object.class});
            CREATESUBCONTEXT_3decc306b6b18490 = org.codehaus.spice.jndikit.NamingProvider.class.getMethod("createSubcontext", new Class[] {javax.naming.Name.class});
            DESTROYSUBCONTEXT_3decc306c6d2bb07 = org.codehaus.spice.jndikit.NamingProvider.class.getMethod("destroySubcontext", new Class[] {javax.naming.Name.class});
            GETNAMEPARSER_fffffffff5a0d162 = org.codehaus.spice.jndikit.NamingProvider.class.getMethod("getNameParser", new Class[] {});
            LIST_c2133cf9556188f5 = org.codehaus.spice.jndikit.NamingProvider.class.getMethod("list", new Class[] {javax.naming.Name.class});
            LISTBINDINGS_c2133cf95cadb7c5 = org.codehaus.spice.jndikit.NamingProvider.class.getMethod("listBindings", new Class[] {javax.naming.Name.class});
            LOOKUP_c2133cf950670173 = org.codehaus.spice.jndikit.NamingProvider.class.getMethod("lookup", new Class[] {javax.naming.Name.class});
            REBIND_ba44730f19e760fe = org.codehaus.spice.jndikit.NamingProvider.class.getMethod("rebind", new Class[] {javax.naming.Name.class, java.lang.String.class, java.lang.Object.class});
            UNBIND_c2133cf91c41be58 = org.codehaus.spice.jndikit.NamingProvider.class.getMethod("unbind", new Class[] {javax.naming.Name.class});
        } catch (NoSuchMethodException exception) {
            throw new NoSuchMethodError(exception.getMessage());
        }
    }
    
}
