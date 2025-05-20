package com.densebrain.rif.client;

import java.rmi.RemoteException;
import java.util.Hashtable;
import com.densebrain.rif.client.service.RIFService;
import com.densebrain.rif.client.service.RIFServiceStub;

/**
 * The RIFManager is responsible for keeping track of the client side web service annd caching
 * RIFInvoker's to cut down on processing time for secondary RIFInvoker requests.  It keeps an
 * internal mapping of interfaces to RIFInvokers.
 *
 * @author Jonathan Glanz
 * @copyright Desnbrain, Inc @ 2006
 */
public class RIFManager {

    /**
     * Retrieve an invoker by interface name, if one does not exist then create a new RIFInvoker.
     * When the RIFInvoker is instantiate it build the dynamic runtime proxy.
     *
     * @param interfaceClazz - The interface to proxy against the server for, MUST be an interface
     * @return - RIFInvoker ready to proxy for the given interface
     * @throws RemoteException
     */
    public RIFInvoker getInvoker(Class interfaceClazz) throws RemoteException {
        RIFInvoker invoker = invokerMap.get(interfaceClazz);
        if (invoker == null) {
            synchronized (this) {
                invoker = invokerMap.get(interfaceClazz);
                if (invoker == null) {
                    invoker = new RIFInvoker(this, interfaceClazz);
                    invokerMap.put(interfaceClazz, invoker);
                }
            }
        }
        return invoker;
    }
}
