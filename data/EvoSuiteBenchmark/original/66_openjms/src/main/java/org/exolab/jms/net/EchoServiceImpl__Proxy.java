package org.exolab.jms.net;
public class EchoServiceImpl__Proxy
    extends org.exolab.jms.net.proxy.Proxy
    implements org.exolab.jms.net.EchoService {
    
    private static final java.lang.reflect.Method ECHOBOOLEAN_fc2493d7f2615325;
    private static final java.lang.reflect.Method ECHOBYTE_2e61083d232c8b;
    private static final java.lang.reflect.Method ECHOCHAR_2e93563d23ad03;
    private static final java.lang.reflect.Method ECHODOUBLE_4f08842ef07370c9;
    private static final java.lang.reflect.Method ECHOFLOAT_fa2fdda3fa5d9c25;
    private static final java.lang.reflect.Method ECHOINT_fffe6810982d392b;
    private static final java.lang.reflect.Method ECHOLONG_32c67c3d342573;
    private static final java.lang.reflect.Method ECHOOBJECT_3f6979934dd39919;
    private static final java.lang.reflect.Method ECHOSHORT_f97a7b83f9d3d465;
    
    
    public EchoServiceImpl__Proxy(org.exolab.jms.net.proxy.Delegate delegate) {
        super(delegate);
    }
    
    public boolean echoBoolean(boolean arg0) {
        Object[] args = new Object[] {new java.lang.Boolean(arg0)};
        Object result;
        try {
            result = invoke(ECHOBOOLEAN_fc2493d7f2615325, args, 0xfc2493d7f2615325L);
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return ((java.lang.Boolean) result).booleanValue();
    }
    
    public byte echoByte(byte arg0) {
        Object[] args = new Object[] {new java.lang.Byte(arg0)};
        Object result;
        try {
            result = invoke(ECHOBYTE_2e61083d232c8b, args, 0x2e61083d232c8bL);
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return ((java.lang.Byte) result).byteValue();
    }
    
    public char echoChar(char arg0) {
        Object[] args = new Object[] {new java.lang.Character(arg0)};
        Object result;
        try {
            result = invoke(ECHOCHAR_2e93563d23ad03, args, 0x2e93563d23ad03L);
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return ((java.lang.Character) result).charValue();
    }
    
    public double echoDouble(double arg0) {
        Object[] args = new Object[] {new java.lang.Double(arg0)};
        Object result;
        try {
            result = invoke(ECHODOUBLE_4f08842ef07370c9, args, 0x4f08842ef07370c9L);
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return ((java.lang.Double) result).doubleValue();
    }
    
    public float echoFloat(float arg0) {
        Object[] args = new Object[] {new java.lang.Float(arg0)};
        Object result;
        try {
            result = invoke(ECHOFLOAT_fa2fdda3fa5d9c25, args, 0xfa2fdda3fa5d9c25L);
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return ((java.lang.Float) result).floatValue();
    }
    
    public int echoInt(int arg0) {
        Object[] args = new Object[] {new java.lang.Integer(arg0)};
        Object result;
        try {
            result = invoke(ECHOINT_fffe6810982d392b, args, 0xfffe6810982d392bL);
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return ((java.lang.Integer) result).intValue();
    }
    
    public long echoLong(long arg0) {
        Object[] args = new Object[] {new java.lang.Long(arg0)};
        Object result;
        try {
            result = invoke(ECHOLONG_32c67c3d342573, args, 0x32c67c3d342573L);
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return ((java.lang.Long) result).longValue();
    }
    
    public java.lang.Object echoObject(java.lang.Object arg0) {
        Object[] args = new Object[] {arg0};
        Object result;
        try {
            result = invoke(ECHOOBJECT_3f6979934dd39919, args, 0x3f6979934dd39919L);
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return (java.lang.Object) result;
    }
    
    public short echoShort(short arg0) {
        Object[] args = new Object[] {new java.lang.Short(arg0)};
        Object result;
        try {
            result = invoke(ECHOSHORT_f97a7b83f9d3d465, args, 0xf97a7b83f9d3d465L);
        } catch (org.exolab.jms.net.proxy.RemoteInvocationException exception) {
            throw exception;
        } catch (java.lang.Throwable exception) {
            throw new org.exolab.jms.net.proxy.RemoteInvocationException(exception);
        }
        return ((java.lang.Short) result).shortValue();
    }
    
    static {
        try {
            ECHOBOOLEAN_fc2493d7f2615325 = org.exolab.jms.net.EchoService.class.getMethod("echoBoolean", new Class[] {boolean.class});
            ECHOBYTE_2e61083d232c8b = org.exolab.jms.net.EchoService.class.getMethod("echoByte", new Class[] {byte.class});
            ECHOCHAR_2e93563d23ad03 = org.exolab.jms.net.EchoService.class.getMethod("echoChar", new Class[] {char.class});
            ECHODOUBLE_4f08842ef07370c9 = org.exolab.jms.net.EchoService.class.getMethod("echoDouble", new Class[] {double.class});
            ECHOFLOAT_fa2fdda3fa5d9c25 = org.exolab.jms.net.EchoService.class.getMethod("echoFloat", new Class[] {float.class});
            ECHOINT_fffe6810982d392b = org.exolab.jms.net.EchoService.class.getMethod("echoInt", new Class[] {int.class});
            ECHOLONG_32c67c3d342573 = org.exolab.jms.net.EchoService.class.getMethod("echoLong", new Class[] {long.class});
            ECHOOBJECT_3f6979934dd39919 = org.exolab.jms.net.EchoService.class.getMethod("echoObject", new Class[] {java.lang.Object.class});
            ECHOSHORT_f97a7b83f9d3d465 = org.exolab.jms.net.EchoService.class.getMethod("echoShort", new Class[] {short.class});
        } catch (NoSuchMethodException exception) {
            throw new NoSuchMethodError(exception.getMessage());
        }
    }
    
}
