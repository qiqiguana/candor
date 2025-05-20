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

import java.net.InetAddress;
import java.rmi.RemoteException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.densebrain.rif.server.transport.WebServiceContainer;

/**
 * The micro-http server that listens for WebService requests
 * and approiately marshalls them to enable RIF to work.
 * 
 * @author Jonathan Glanz
 *
 */
public class RIFServer {
	
	/**
	 * The context path to register the RIFService against on the embeded Axis2 server
	 */
	public static final String CONTEXT_PATH = "/rif/services";
	
	private static final Log logger = LogFactory.getLog(RIFServer.class);
	
	private WebServiceContainer container;
	
	/**
	 * Default constructor, which instantiates the RIFServer on the passed port.  At current
	 * the RIFServer will listen on ALL local network interfaces.
	 * 
	 * @param port - to listen on
	 */
	public RIFServer(int port) {
		try {
			container = WebServiceContainer.newInstance(InetAddress.getLocalHost().getHostName(), port, CONTEXT_PATH);
			container.configureService(RIFService.class, RIFService.TARGET_NAMESPACE, RIFService.TYPES_NAMESPACE);
			logger.info("Started RIF Server on port: " + port);
			logger.info("RIF WSDL Available at: http://localhost:" + port + "/rif/services/RIFService?wsdl");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Start the RIFServer
	 * 
	 * @throws RemoteException
	 */
	public void start() throws RemoteException {
		container.startContainer();
	}
	
	/**
	 * Stop the RIFServer
	 * 
	 * @throws RemoteException
	 */
	public void stop() throws RemoteException {
		container.stopContainer();
	}
}
