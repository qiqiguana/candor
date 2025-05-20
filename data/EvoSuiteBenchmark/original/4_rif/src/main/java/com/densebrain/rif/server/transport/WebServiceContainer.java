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

package com.densebrain.rif.server.transport;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.rpc.receivers.RPCMessageReceiver;
import org.apache.axis2.transport.http.turnup.SimpleHTTPServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * WebServiceContainer is a wrapper around the Axis SimpleHTTPServer
 * 
 * It wraps a simple http server which is hosting Axis2 Web services
 * and provides simple functionality for creating and managing services.
 * 
 * @author Jonathan Glanz
 *
 */
public class WebServiceContainer extends SimpleHTTPServer {
	
	private static final Log logger = LogFactory.getLog(WebServiceContainer.class);
	
	/**
	 * Initialize a new container as an embedded server
	 * 
	 * @param port - port for the http server to listen on
	 * @return
	 * @throws ServerException
	 */
	public static WebServiceContainer newInstance(String hostName, int port, String contextPath) throws RemoteException{
		
		try {
			ConfigurationContext configurationContext = 
				ConfigurationContextFactory.createConfigurationContextFromFileSystem(null,null);
		
			WebServiceContainer container = new WebServiceContainer(configurationContext, contextPath, hostName, port);
			return container;
		} catch (AxisFault af) {
			throw af;
		}
	}
	
	/**
	 * Create a new WebServiceContainer with the passed configurationContext, this is only used
	 * for embedding the container in an existing servlet container.
	 * 
	 * @param configurationContext
	 * @return
	 * @throws RemoteException
	 */
	public static WebServiceContainer newInstance(ConfigurationContext configurationContext) throws RemoteException{
		
		WebServiceContainer container = new WebServiceContainer(configurationContext);
		return container;
		
	}
	
	
	ConfigurationContext configurationContext;
	String contextPath;
	String hostName;
	int port;
	List<WebServiceDescriptor> descriptorList;
	
	public WebServiceContainer(ConfigurationContext configurationContext) {
		super();
		this.configurationContext = configurationContext;
		
		descriptorList = new LinkedList<WebServiceDescriptor>();
	}
	
	public WebServiceContainer(ConfigurationContext configurationContext, String contextPath, String hostName, int port) throws AxisFault, RemoteException {
		super(contextPath, configurationContext, port);
		try {
			this.configurationContext = configurationContext;
			this.contextPath = contextPath;
			this.hostName = hostName;
			this.port = port;
			
			if (hostName == null) {
				hostName = InetAddress.getLocalHost().getHostName();
			}
			
			descriptorList = new LinkedList<WebServiceDescriptor>();
		} catch (Exception e) {
			throw new RemoteException("Unable to initialize container", e);
		}
	}
	
	public void configureService(Class serviceClazz, String targetNamespace, String typesNamespace) throws RemoteException {
		WebServiceDescriptor descriptor = new WebServiceDescriptor(serviceClazz, targetNamespace, typesNamespace);
		configureService(descriptor);
	}
	
	public void configureService(WebServiceDescriptor descriptor) throws RemoteException {
		try {
			AxisService service =
				AxisService.createService(
						descriptor.getServiceClazz().getName(), 
						configurationContext.getAxisConfiguration(),
						RPCMessageReceiver.class, 
						descriptor.getTargetNamespace(),
						descriptor.getTypesNamespace());
			configurationContext.getAxisConfiguration().addService(service);
			
			if (!descriptorList.contains(descriptor))
				descriptorList.add(descriptor);
		} catch (Exception e) {
			throw new RemoteException("Unable to add service", e);
		}
	}
	
	public WebServiceContainer restartContainer() throws RemoteException {
		//logger.debug("Restarting web service container");
		stopContainer();
		try {
			//configurationContext = 
			//	ConfigurationContextFactory.createConfigurationContextFromFileSystem(null,null);
			
			//super.configurationContext = configurationContext;
			
			for (int i = 0; i < descriptorList.size();i++) {
				WebServiceDescriptor descriptor = descriptorList.get(i);
				configurationContext.getAxisConfiguration().removeService(descriptor.getServiceClazz().getSimpleName());
				configureService(descriptor);
			}
			
			startContainer();
			//logger.debug("Restarted web service container");
		} catch (AxisFault af) {
			//logger.error("Error occured while restarting WebServiceContainer", af);
			throw new RemoteException("Error occured while restarting WebServiceContainer", af);
		}
		return this;
	}
	
	public void startContainer() throws RemoteException {
		try {
			if (port > 0) 
				super.start();
		} catch (AxisFault af) {
			throw new RemoteException("Unable to start WebServiceContainer: " + af.getMessage(), af);
		}
	}
	
	public void stopContainer() throws RemoteException {
		if (port > 0)
			super.stop();		
	}
	
	
	/**
     * replyToEPR
     * If the user has given host address paramter then it gets the high priority and
     * ERP will be creatd using that
     * N:B - hostAddress should be a complte url (http://www.myApp.com/ws)
     *
     * @param serviceName
     * @param ip
     * @return an EndpointReference
     * @see org.apache.axis2.transport.TransportListener#getEPRForService(String,String)
     */
    public EndpointReference getEPRForService(String serviceName, String ip) throws AxisFault {
        return new EndpointReference(
        		"http://" + hostName + ':' + port + contextPath + '/' + serviceName);
    	
    }
    
    
    
}
