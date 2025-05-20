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

    private static RIFImplementationManager instance = new RIFImplementationManager();

    /**
     * Retrieve the singleton RIFImplementationManager instance.
     *
     * @return
     */
    public static RIFImplementationManager getInstance();

    private Hashtable<String, Object> implementationMap = new Hashtable<String, Object>();

    private Hashtable<Object, Map<String, Method>> methodsMap = new Hashtable<Object, Map<String, Method>>();

    private RIFImplementationManager() {
    }

    /**
     * Register an implementation to be served from this JVM
     *
     * @param interfaceClazz - The interface that is served by the passed implementation
     * @param implementation - the implementation of the passed interface
     */
    public void registerImplementation(Class interfaceClazz, Object implementation);

    /**
     * Invoke a method on a registered implementation.  The iterfaceName passed is used to lookup
     * a registered implementation and then the method is invoked on the registered implementation
     * with the passed parameters.
     *
     * @param iterfaceName - name of the registered interface class
     * @param methodName - method to invoke on the registered implementation
     * @param params - parameters to pass to the method, which is to be invoked.
     * @return the return from the method being invoked.
     *
     * @throws RemoteException
     */
    public Object invoke(String iterfaceName, String methodName, Object[] params) throws RemoteException;
}
