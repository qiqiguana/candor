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

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.rmi.RemoteException;

import org.apache.bcel.Constants;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.BasicType;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.FieldGen;
import org.apache.bcel.generic.InstructionConstants;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.Type;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.densebrain.rif.client.service.RIFService;
import com.densebrain.rif.client.service.types.Invoke;
import com.densebrain.rif.client.service.types.InvokeResponse;
import com.densebrain.rif.util.ObjectUtility;

/**
 * The RIFInvoker is the class that actually builds the dynamic 
 * interface proxies and invokes the calls between the host and 
 * the client.
 * 
 *  Sample Code:
 *  
 *  <code>
 *  RIFInvoker invoker = RIFManagerFactory.getInvoker(YourRemoteInterface.class);
 *  YourRemoteInterface yourRemoteInterface = (YourRemoteInterface) invoker.getImpl();
 *    
 *  or
 *  
 *  YourRemoteInterface yourRemoteInterface = (YourRemoteInterface) 
 *  	RIFManagerFactory.getImpl(YourRemoteInterface.class);
 *  </code>
 * 
 * @author Jonathan Glanz
 *
 */
public class RIFInvoker<I extends Object> implements Constants {
	
	private static final Log log = LogFactory.getLog(RIFInvoker.class);
	
	private RIFManager manager;
	private Class interfaceClazz;
	private I impl;
	
	/**
	 * The default constructor accepts the manager that it is invoking for as 
	 * well as the interface that it will proxy for and then it builds a dynamically
	 * generated proxy to the interface leveraging the implmentation provided by
	 * the server.
	 * 
	 * @param manager - The RIFManager that it will use to invoke the remote service
	 * @param interfaceClazz - The interface that it will proxy to the server
	 * @throws RemoteException - If there is any issue building the proxy then an exception is raised.
	 */
	protected RIFInvoker(RIFManager manager, Class interfaceClazz) throws RemoteException {
		this.manager = manager;
		this.interfaceClazz = interfaceClazz;
		
		buildImpl();
	}
	
	/**
	 * The buiuldImpl() method is the method that actually examines the interface that this 
	 * invoker will proxy for and builds the dynamic implmentation of the interface.
	 * 
	 * Then it instantiates the newly created class and the new instance is available through getImpl()
	 * 
	 * @throws RemoteException
	 */
	private void buildImpl() throws RemoteException {
		if (interfaceClazz == null) throw new RemoteException("Interface class can not be null");
		if (!interfaceClazz.isInterface()) throw new RemoteException("Interface class must be an interface");
		
		Method[] methods = interfaceClazz.getMethods();
		Method method;
		for (int i = 0; i < methods.length;i++) {
			method = methods[i];
			
			checkRemoteExceptionDeclared(method);
		}
		
		String implName = interfaceClazz.getName() + "RIFImpl";
		ClassGen cg = new ClassGen(implName, "java.lang.Object",
				"<generated>", ACC_PUBLIC | ACC_SUPER, new String[] { interfaceClazz.getName() });
		
		ConstantPoolGen cp = cg.getConstantPool(); // cg creates constant pool		
		InstructionList il;
		InstructionFactory instructionFactory = new InstructionFactory(cg, cp);
		
		//Create Invoker Field
		FieldGen field;
	    field = new FieldGen(ACC_PRIVATE, new ObjectType(RIFInvoker.class.getName()), "invoker", cp);
	    cg.addField(field.getField());
	    
	    
	    //Create Constructor
	    il = new InstructionList();
	    MethodGen methodGen = new MethodGen(ACC_PUBLIC, Type.VOID, new Type[] { new ObjectType(RIFInvoker.class.getName()) }, new String[] { "invoker" }, "<init>", implName, il, cp);

	    il.append(InstructionFactory.createLoad(Type.OBJECT, 0));
	    il.append(instructionFactory.createInvoke("java.lang.Object", "<init>", Type.VOID, Type.NO_ARGS, Constants.INVOKESPECIAL));
	    il.append(InstructionFactory.createLoad(Type.OBJECT, 0));
	    il.append(InstructionFactory.createLoad(Type.OBJECT, 1));
	    il.append(instructionFactory.createFieldAccess(implName, "invoker", new ObjectType(RIFInvoker.class.getName()), Constants.PUTFIELD));
	    il.append(InstructionFactory.createReturn(Type.VOID));
	    methodGen.setMaxStack();
	    methodGen.setMaxLocals();
	    cg.addMethod(methodGen.getMethod());
	    il.dispose();
	    
	    //Create methods
	    for (int i = 0; i < methods.length;i++) {
	    	method = methods[i];
	    	
	    	Class[] paramClassTypes = method.getParameterTypes();
	    	String[] paramNames = new String[paramClassTypes.length];
	    	for (int j = 0; j < paramClassTypes.length;j++) {
	    		paramNames[j] = "arg" + j;
	    	}
	    	
	    	Type[] paramTypes = new Type[paramClassTypes.length];
	    	int paramCount = paramTypes.length;
	    	for (int j = 0; j < paramTypes.length;j++) {
	    		Class paramClassType = paramClassTypes[j];
	    		paramTypes[j] = Type.getType(paramClassType);
	    	}
	    	
	    	Class returnTypeClazz = method.getReturnType();
	    	Type returnType = (method.getReturnType() == null) ? Type.VOID : Type.getType(returnTypeClazz);
	    	
	    	
	    	
	    	il = new InstructionList();
		    methodGen = new MethodGen(ACC_PUBLIC, returnType, paramTypes, paramNames, method.getName(), implName, il, cp);

		    il.append(new org.apache.bcel.generic.PUSH(cp, paramCount));
		    il.append(instructionFactory.createNewArray(Type.OBJECT, (short) 1));
		    for (int j = 0; j < paramCount;j++) {
		    	il.append(InstructionConstants.DUP);
			    il.append(new org.apache.bcel.generic.PUSH(cp, j));
			    il.append(InstructionFactory.createLoad(Type.OBJECT, j+1));
			    il.append(InstructionConstants.AASTORE);
		    }
		    il.append(InstructionFactory.createStore(Type.OBJECT, paramCount+1));
		    
		    il.append(InstructionFactory.createLoad(Type.OBJECT, 0));
		    il.append(instructionFactory.createFieldAccess(
		    		implName, 
		    		"invoker", 
		    		new ObjectType(RIFInvoker.class.getName()), 
		    		Constants.GETFIELD));
		    
		    il.append(new org.apache.bcel.generic.PUSH(cp, method.getName()));
		    il.append(InstructionFactory.createLoad(Type.OBJECT, paramCount+1));
		    il.append(instructionFactory.createInvoke(
		    		RIFInvoker.class.getName(), 
		    		"invoke", 
		    		Type.OBJECT, 
		    		new Type[] { Type.STRING, new ArrayType(Type.OBJECT, 1) }, 
		    		Constants.INVOKEVIRTUAL));
		    
		    //il.append(instructionFactory.createCheckCast(Type.STRING));
		    
		    if (!Type.VOID.equals(returnType)) {
		    	if (returnTypeClazz != null && returnTypeClazz.isPrimitive()) {
		    		//il.append(InstructionFactory.createLoad(Type.OBJECT, 1));
				    il.append(instructionFactory.createInvoke("java.lang.Object", "toString", Type.STRING, Type.NO_ARGS, Constants.INVOKEVIRTUAL));
				    if (Boolean.TYPE.equals(returnTypeClazz)) {
				    	il.append(instructionFactory.createInvoke("java.lang.Boolean", "valueOf", new ObjectType("java.lang.Boolean"), new Type[] { Type.STRING }, Constants.INVOKESTATIC));
				    	il.append(instructionFactory.createInvoke("java.lang.Boolean", "booleanValue", Type.BOOLEAN, Type.NO_ARGS, Constants.INVOKEVIRTUAL));
				    } else if (Long.TYPE.equals(returnTypeClazz)) 
				    	il.append(instructionFactory.createInvoke("java.lang.Long", "parseLong", Type.LONG, new Type[] { Type.STRING }, Constants.INVOKESTATIC));
				    else if (Integer.TYPE.equals(returnTypeClazz)) 
				    	il.append(instructionFactory.createInvoke("java.lang.Integer", "parseInt", Type.INT, new Type[] { Type.STRING }, Constants.INVOKESTATIC));
				    else if (Short.TYPE.equals(returnTypeClazz)) 
				    	il.append(instructionFactory.createInvoke("java.lang.Short", "parseShort", Type.SHORT, new Type[] { Type.STRING }, Constants.INVOKESTATIC));
				    else if (Float.TYPE.equals(returnTypeClazz)) 
				    	il.append(instructionFactory.createInvoke("java.lang.Float", "parseFloat", Type.FLOAT, new Type[] { Type.STRING }, Constants.INVOKESTATIC));
				    else if (Double.TYPE.equals(returnTypeClazz))
				    	il.append(instructionFactory.createInvoke("java.lang.Double", "parseDouble", Type.DOUBLE, new Type[] { Type.STRING }, Constants.INVOKESTATIC));
				    else if (Byte.TYPE.equals(returnTypeClazz)) 
				    	il.append(instructionFactory.createInvoke("java.lang.Byte", "parseByte", Type.INT, new Type[] { Type.STRING }, Constants.INVOKESTATIC));
				    else if (Character.TYPE.equals(returnTypeClazz)) 
				    	throw new IllegalArgumentException("Return type char is not currently supported");
		    	} else {
			    	il.append(instructionFactory.createCast(Type.OBJECT, returnType));			    	
		    	}
		    }
		    il.append(InstructionFactory.createReturn(returnType));
		    
		    methodGen.setMaxStack();
		    methodGen.setMaxLocals();
		    cg.addMethod(methodGen.getMethod());
		    il.dispose();
	    }
	    
	    manager.getClassLoader().registerClass(implName, cg.getJavaClass().getBytes());
	    
	    try {
	    	Class implClass = Class.forName(implName, true, manager.getClassLoader());
	    	Constructor constructor = implClass.getConstructor(new Class[] { RIFInvoker.class });
	    	impl = (I) constructor.newInstance(new Object[] { this });
	    } catch (Exception e) {
	    	log.error("Error while instantiating " + implName + " for " + interfaceClazz.getName(), e);
	    	throw new RemoteException("Error while instantiating " + implName + " for " + interfaceClazz.getName(), e);
	    }
	}	

	
	/**
	 * Retrieves the instance of the interface implmentation that was dynamically generated
	 * 
	 * @return - the implementation of the interface that this class is implementing
	 */
	public I getImpl() {
		return impl;
	}
	
	public Object invoke(String methodName, Object[] params) throws RemoteException {
		RIFService service = manager.getService();
		
		Invoke invoke = new Invoke();
		invoke.setClassName(interfaceClazz.getName());
		invoke.setMethodName(methodName);
		try {
			invoke.setSerializedParams(ObjectUtility.encodeBytes(ObjectUtility.serializeObject(params)));
		} catch (IOException ioe) {
			throw new RemoteException("Unable to serialize parameters", ioe);
		}
		InvokeResponse invokeResponse = service.invoke(invoke);
		String serializedResponse = invokeResponse.get_return();
		try {
			return ObjectUtility.deserializeObjectBase64Encoded(serializedResponse);
		} catch (IOException ioe) {
			throw new RemoteException("Unable to deserialize return value: " + ioe.getMessage(), ioe);
		}
	}
	
	private void checkRemoteExceptionDeclared(Method method) throws RemoteException{
		Class[] exceptionTypes = method.getExceptionTypes();
		boolean exists = false;
		for (int i = 0; i < exceptionTypes.length;i++) {
			if (RemoteException.class.equals(exceptionTypes[i])) {
				exists = true;
				break;
			}
		}
		
		if (!exists) throw new RemoteException("Method " + method.getName() + " does not declare RemoteException as a throwable");
	}
}
