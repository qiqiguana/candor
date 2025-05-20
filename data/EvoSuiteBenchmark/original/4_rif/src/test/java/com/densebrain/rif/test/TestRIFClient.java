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

import com.densebrain.rif.client.RIFInvoker;
import com.densebrain.rif.client.RIFManagerFactory;


public class TestRIFClient {
	
	
	public static void main(String[] args) throws Exception {
		String url, val1,val2;
		if (args.length < 3) {
			url = "http://localhost:10002";
			val1 = "test1";
			val2 = "test2";
		} else {
			url = args[0];
			val1 = args[1];
			val2 = args[2];
		}
			
		//Initialize the factory
		System.out.println("Initializing Factory");		
		
		//Retrieve the invoker for the TestInterface
		System.out.println("Getting Invoker");
		RIFInvoker<TestInterface> invoker = RIFManagerFactory.getInstance().getInvoker(url, TestInterface.class);
		
		//Retrieve the dynamically generated proxy
		System.out.println("Getting Impl");
		TestInterface testInterface = invoker.getImpl();
		
		//Execute the "dumbTest" method
		System.out.println("Invoking");
		String echo = testInterface.dumbTest(val1,val2);
		
		//Print the response
		System.out.println("Recieved: " + echo);
		testInterface.voidTest();
		
	}
	
	
}
