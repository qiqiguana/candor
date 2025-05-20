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
 * @author Jonathan Glanz
 *
 */
public class RIFImplementationManager {
	private static RIFImplementationManager instance = new RIFImplementationManager();
	
	/**
	 * Retrieve the singleton RIFImplementationManager instance.
	 * 
	 * @return
	 */
	public static RIFImplementationManager getInstance() {
		return instance;
	}
	
	private Hashtable<String, Object> implementationMap = new Hashtable<String, Object>();
	private Hashtable<Object, Map<String, Method>> methodsMap = new Hashtable<Object, Map<String, Method>>();
	private RIFImplementationManager() { }
	
	/**
	 * Register an implementation to be served from this JVM
	 * 
	 * @param interfaceClazz - The interface that is served by the passed implementation
	 * @param implementation - the implementation of the passed interface
	 */
	public void registerImplementation(Class interfaceClazz, Object implementation) {
		implementationMap.put(interfaceClazz.getName(), implementation);
	}
	
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
	public Object invoke(String iterfaceName, String methodName, Object[] params) throws RemoteException {
		Object impl = implementationMap.get(iterfaceName);
		if (impl == null) throw new RemoteException("Not registered: " + iterfaceName);
		
		Map<String, Method> methodMap = methodsMap.get(impl);
		if (methodMap == null) {
			synchronized (this) {
				methodMap = methodsMap.get(impl);
				if (methodMap == null) {
					methodMap = new Hashtable<String, Method>();
					Class clazz = impl.getClass();
					Method[] methods = clazz.getMethods();
					
					for (int i = 0; i < methods.length;i++) {
						Method method = methods[i];
						methodMap.put(method.getName(), method);
					}
					
					methodsMap.put(impl, methodMap);
				}
			}
		}
		
		Method method = methodMap.get(methodName);
		if (method == null) throw new IllegalArgumentException("Unknown method " + methodName + " on " + iterfaceName);
		
		try {
			return method.invoke(impl, params);
		} catch (Exception e) {
			throw new RemoteException("Error occured while invoking " + iterfaceName + "." + methodName + ": " + e.getMessage(), e);
		}
	}
}
