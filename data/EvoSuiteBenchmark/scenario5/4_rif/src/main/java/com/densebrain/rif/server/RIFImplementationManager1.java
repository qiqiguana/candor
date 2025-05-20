package com.densebrain.rif.server;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Map;

/**
 * Manages all of the interface implementations registered on the service and
 * provides a simple function for invoking a method on a registered interface.
 *
 * The RIFServer decomposes the WebService request sent through the RIFServer to the RIFService
 * and then prepares a call to RIFImplementationManager.invoke to call the actual function.
 *
 * To register an implementation:
 * <code>
 *
 * RIFImplementationManager.getInstance().registerImplementation(YourInterface.class, new YourImpl());
 *
 * </code>
 *
 * @author Jonathan Glanz
 */
public class RIFImplementationManager {

    /**
     * Invoke a method on a registered implementation.  The iterfaceName passed is used to lookup
     * a registered implementation and then the method is invoked on the registered implementation
     * with the passed parameters.
     *
     * @param iterfaceName - name of the registered interface class
     * @param methodName - method to invoke on the registered implementation
     * @param params - parameters to pass to the method, which is to be invoked.
     * @return the return from the method being invoked.
     * @throws RemoteException
     */
    public Object invoke(String iterfaceName, String methodName, Object[] params) throws RemoteException {
        Object impl = implementationMap.get(iterfaceName);
        if (impl == null)
            throw new RemoteException("Not registered: " + iterfaceName);
        Map<String, Method> methodMap = methodsMap.get(impl);
        if (methodMap == null) {
            synchronized (this) {
                methodMap = methodsMap.get(impl);
                if (methodMap == null) {
                    methodMap = new Hashtable<String, Method>();
                    Class clazz = impl.getClass();
                    Method[] methods = clazz.getMethods();
                    for (int i = 0; i < methods.length; i++) {
                        Method method = methods[i];
                        methodMap.put(method.getName(), method);
                    }
                    methodsMap.put(impl, methodMap);
                }
            }
        }
        Method method = methodMap.get(methodName);
        if (method == null)
            throw new IllegalArgumentException("Unknown method " + methodName + " on " + iterfaceName);
        try {
            return method.invoke(impl, params);
        } catch (Exception e) {
            throw new RemoteException("Error occured while invoking " + iterfaceName + "." + methodName + ": " + e.getMessage(), e);
        }
    }
}
