package com.densebrain.rif.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.axis2.util.Base64;

public class ObjectUtility {
	private ObjectUtility() {}
	
	public static byte[] serializeObject(Object o) throws IOException {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(o);
			
		} finally {
		
			try {
				oos.close();
			} catch (Exception e) { }
			try {
				baos.close();
			} catch (Exception e) { }
			
		}
		
		return baos.toByteArray();
	}
	
	public static String encodeBytes(byte[] bytes) {
		return Base64.encode(bytes);
	}
	
	public static Object deserializeObjectBase64Encoded(String s) throws IOException {
		return deserializeObject(decodeString(s));
	}
	
	public static Object deserializeObject(byte[] bytes) throws IOException {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		Object o = null;
		try {
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			o = ois.readObject();
			
		} catch (ClassNotFoundException cnfe) {
			throw new IOException("Class Not Found: " + cnfe.getMessage());	
		} finally {
			try {
				ois.close();
			} catch (Exception e) { }
			try {
				bais.close();
			} catch (Exception e) { }
			
		}
		return o;
	}
	
	public static byte[] decodeString(String s) {
		return Base64.decode(s);
	}

}
