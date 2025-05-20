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
package com.densebrain.rif.test;

import com.densebrain.rif.server.RIFImplementationManager;
import com.densebrain.rif.server.RIFServer;

public class TestRIFServer {
	
	public static void main(String[] args) throws Exception {
		int port = 10002;
		if (args.length > 0)
			port = Integer.parseInt(args[0]);
		
		//Register all implementations - this can be done at any point even once the server is running
		RIFImplementationManager.getInstance().registerImplementation(TestInterface.class, new TestImpl());
		
		//Initialize the server
		RIFServer server = new RIFServer(port);
		
		//Start the server
		server.start();
		
		//Display message - Server is ready to recieve requests now
		System.out.println("Started...press <ENTER> to stop");
		
		//Wait for <ENTER> to be pressed
		System.in.read();
		
		//Stop the server
		server.stop();
	}
}
