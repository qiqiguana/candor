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

import java.util.Hashtable;

/**
 * The RIFClassLoader is used to load the dynamically generated classes as
 * well as enabling easy class registration.
 * 
 * @author Jonathan Glanz
 *
 */
public class RIFClassLoader extends ClassLoader {
	
	private Hashtable<String, Class> classMap = new Hashtable<String, Class>();
	
	/**
	 * Initializes with the current thread's context classloader as its parent
	 *
	 */
	protected RIFClassLoader() {
		super(Thread.currentThread().getContextClassLoader());
	}
	
	/**
	 * Finds a class based on the passed in className
	 */
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		return classMap.get(name);
	}
	
	/**
	 * Registers a dynamically generated class
	 * 
	 * @param name - name of the dynamic proxy to register
	 * @param data - the byte[] representing the class data
	 */
	protected void registerClass(String name, byte[] data) {
		Class clazz = defineClass(name, data, 0, data.length);
		classMap.put(name, clazz);
	}

}
