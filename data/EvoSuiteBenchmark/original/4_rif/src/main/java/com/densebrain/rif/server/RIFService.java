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
 * 
 * @author Jonathan Glanz
 *
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
