package com.densebrain.rif.client;

import java.rmi.RemoteException;
import java.util.Hashtable;

/**
 * Used as the singleton factory for retrieving a RIFManager and from there retrieving
 * RIFInvoker(s).  The initialize method MUST be called before using the Factory.
 *
 * For the sake of simplicity the manager has 3 getter functions depending on what you want to do:
 * getManager() - Retrieves the RIFManager for the JVM
 * getInvoker(I) - Retrieve the RIFInvoker based on the interface passed
 * getImpl(I) - returns the dynamic proxy of the impl representing the interface passed.
 *
 * @author Jonathan Glanz
 */
public class RIFManagerFactory {

    /**
     * Initialize's the factory for use; the url passed in is the URL of the
     * RIFServer with no context path: i.e. http://&lt;hostname&gt;:&lt;port&gt;
     *
     * @param url - URL of the RIFServer in the format http://&lt;hostname&gt;:&lt;port&gt;
     * @throws RemoteException
     */
    public static RIFManagerFactory getInstance() throws RemoteException;
}
