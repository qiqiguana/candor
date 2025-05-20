package com.densebrain.rif.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import org.apache.axis2.util.Base64;

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
    public Object invoke(String iterfaceName, String methodName, Object[] params) throws RemoteException;

    /**
     * Retrieve the singleton RIFImplementationManager instance.
     *
     * @return
     */
    public static RIFImplementationManager getInstance();
}

/**
 * This is the WebService, which is hosted on Axis2 that marshalls the remote calls
 * between the client RIFInvoker and the Server RIFImplementationManager.
 *
 * @see com.densebrain.rif.client.RIFInvoker
 * @see com.densebrain.rif.server.RIFImplementationManager
 * @author Jonathan Glanz
 */
public class RIFService {

    /**
     * The invoke ws method, which marshalls the call to the RIFImplementationManager
     *
     * @param interfaceName - the registered interface to invoke the method on
     * @param methodName - method to invoke on the registered interface
     * @param serializedParams - serialized parameters to reconstruct and pass to the RIFImplementationManager
     * @return
     * @throws RemoteException
     */
    public String invoke(String interfaceName, String methodName, String serializedParams) throws RemoteException {
        byte[] paramBytes = Base64.decode(serializedParams);
        Object[] params;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(paramBytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            params = (Object[]) ois.readObject();
            ois.close();
            bais.close();
        } catch (Exception e) {
            throw new RemoteException("Unable to deserialize parameters: " + e.getMessage(), e);
        }
        Object result = RIFImplementationManager.getInstance().invoke(interfaceName, methodName, params);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(result);
            oos.close();
            byte[] resultBytes = baos.toByteArray();
            return Base64.encode(resultBytes);
        } catch (Exception e) {
            throw new RemoteException("Unable to serialize result: " + e.getMessage());
        }
    }
}
