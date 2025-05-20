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
	
	private RIFService service;
	private RIFClassLoader classLoader;
	
	protected RIFManager(String url) throws RemoteException {
		try {
			service = new RIFServiceStub(url);
			classLoader = new RIFClassLoader();
		} catch (Exception e) {
			throw new RemoteException("Unable to initialize manager: " + e.getMessage(), e);
		}
	}
	
	private Hashtable<Class, RIFInvoker> invokerMap = new Hashtable<Class, RIFInvoker>();
	
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
			synchronized(this) {
				invoker = invokerMap.get(interfaceClazz);
				if (invoker == null) {
					invoker = new RIFInvoker(this, interfaceClazz);
					invokerMap.put(interfaceClazz, invoker);
				}
			}
		}
		
		return invoker;
	}
	
	/**
	 * Retrieve the RIFClassLoader for isntantiating the dynamic proxy classes
	 * @return
	 */
	protected RIFClassLoader getClassLoader() {
		return classLoader;
	}

	/**
	 * retrieve the web service for invoking the methods on the proxy remotely
	 * @return
	 */
	public RIFService getService() {
		return service;
	}
	
	
}
