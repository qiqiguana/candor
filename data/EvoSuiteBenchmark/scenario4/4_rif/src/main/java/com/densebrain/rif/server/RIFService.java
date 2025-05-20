package com.densebrain.rif.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import org.apache.axis2.util.Base64;

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
     * Types namespace for the web service
     */
    public static final String TYPES_NAMESPACE = "http://densebrain.com/rif/client/service/types";

    /**
     * Target namespace for the web service
     */
    public static final String TARGET_NAMESPACE = "http://densebrain.com/rif/client/service";

    /**
     * The invoke ws method, which marshalls the call to the RIFImplementationManager
     *
     * @param interfaceName - the registered interface to invoke the method on
     * @param methodName - method to invoke on the registered interface
     * @param serializedParams - serialized parameters to reconstruct and pass to the RIFImplementationManager
     * @return
     * @throws RemoteException
     */
    public String invoke(String interfaceName, String methodName, String serializedParams) throws RemoteException;
}
