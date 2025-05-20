/*
 * Copyright (c) 2006, Densebrain, Inc
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice, 
 *   	this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice, 
 *   	this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *   * Neither the name of the Densebrain, Inc nor the names of its contributors 
 *   	may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS 
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */

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
 *
 */
public class RIFManagerFactory {
	
	private static RIFManagerFactory instance = new RIFManagerFactory();
	
	/**
	 * Initialize's the factory for use; the url passed in is the URL of the 
	 * RIFServer with no context path: i.e. http://&lt;hostname&gt;:&lt;port&gt;
	 * 
	 * @param url - URL of the RIFServer in the format http://&lt;hostname&gt;:&lt;port&gt;
	 * @throws RemoteException
	 */
	public static RIFManagerFactory getInstance() throws RemoteException {
		return instance;
	}
	
	/**
	 * Retrieve the RIFManager that is being used for this JVM, its a Singleton
	 * @return RIFManager for the domain
	 */
	public RIFManager getManager(String url) throws RemoteException {
		RIFManager manager = managerMap.get(url);
		if (manager == null) {
			synchronized(this) {
				manager = managerMap.get(url);
				if (manager == null) {
					manager = new RIFManager(url + "/rif/services/RIFService");
					managerMap.put(url, manager);
				}
			}
		}
		return manager;
	}
	
	/**
	 * Get a RIFInvoker for a specific interface. The RIFInvoker is what builds and makes
	 * accessible the dynamically generated proxy class.
	 * 
	 * @param interfaceClazz - the interface that the invoker will proxy for.
	 * @return - RIUFInvoker that is proxying for the provided interface.
	 * @throws RemoteException
	 */
	public RIFInvoker getInvoker(String url, Class interfaceClazz) throws RemoteException {
		return getManager(url).getInvoker(interfaceClazz);
	}
	
	/**
	 * Retrieve the dynamically generated proxy directly instead of first requesting 
	 * the RIFInvoker.
	 * 
	 * @param interfaceClazz
	 * @return
	 * @throws RemoteException
	 */
	public Object getImpl(String url, Class interfaceClazz) throws RemoteException {
		return getInvoker(url, interfaceClazz).getImpl();
	}
	
	private Hashtable<String, RIFManager> managerMap = new Hashtable<String, RIFManager>();
	
	private RIFManagerFactory() {
		
	}
	
	
}
