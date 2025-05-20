package com.densebrain.rif.test;

import java.rmi.RemoteException;

public interface TestInterface {
	public String dumbTest(String testVal, Object testVal2) throws RemoteException;	
	public void voidTest() throws RemoteException;
}
