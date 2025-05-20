package net.sourceforge.beanbin.codegen;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.util.List;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.annotations.blob.In;
import net.sourceforge.beanbin.annotations.blob.Out;
import net.sourceforge.beanbin.data.EntityUtils;
import net.sourceforge.beanbin.reflect.ReflectionSearch;

import org.hibernate.Hibernate;

public class BlobClassTemplate {
	private Object entity;

	public BlobClassTemplate(Object entity) {
		this.entity = entity;
	}
	
	protected Blob makeBlob(String getterName) throws BeanBinException {		
		try {
			return Hibernate.createBlob(getInputStream(getBlobObject(getterName)));
		} catch (IOException e) {
			throw new BeanBinException("BlobClassTemplate.makeBlob: " + e.getMessage(), e);
		}
	}
	
	protected void setBlob(String getterName, Blob sqlblob) throws BeanBinException {
		try {
			Method getter = getGetter(getterName);
			Class<?> clazz = getter.getReturnType();
			Object blob = null;
			if(hasInAndOutAnnotations(clazz)) {
				blob = clazz.newInstance();
				Object[] args = {sqlblob.getBinaryStream()};
				getInMethod(clazz).invoke(blob, args);				
			} else {
				ObjectInputStream is = new ObjectInputStream(sqlblob.getBinaryStream());
				blob = is.readObject();
			}
			Class[] args = {clazz};
			String setterName = EntityUtils.getSetterName(getter);
			Method setter = entity.getClass().getMethod(setterName, args);
			Object[] realargs = {blob};
			setter.invoke(entity, realargs);
		} catch(Exception e) {
			throw new BeanBinException("BlobClassTemplate.setBlob: " + e.getMessage(), e);
		}
	}
	
	protected InputStream getInputStream(Object blob) throws BeanBinException {
		try {
			if(hasInAndOutAnnotations(blob.getClass())) {
				Method out = getOutMethod(blob.getClass());			
				Object obj = out.invoke(blob, new Object[0]);
				if(obj instanceof InputStream) {
					return (InputStream)obj;
				} else {
					throw new BeanBinException("The method " + out.getName() + " needs to return an instance of InputStream if it is to be serialized and initialized explicitly.");
				}
			} else {
				return serialize(blob);
			}
		} catch(Exception e) {
			throw new BeanBinException("BlobClassTemplate.getInputStream: " + e.getMessage(), e);
		}
	}
	
	private boolean hasInAndOutAnnotations(Class clazz) throws BeanBinException {
		return getInAndOutMethods(clazz).size() == 2;
	}

	private List<Method> getInAndOutMethods(Class clazz) throws BeanBinException {
		List<Method> methods = new ReflectionSearch(clazz)
								   .methodsThatHave("@net.sourceforge.beanbin.annotations.blob.In")
								   .or()
								   .methodsThatHave("@net.sourceforge.beanbin.annotations.blob.Out")								   
								   .getMethods();
		return methods;
	}
	
	private Method getOutMethod(Class clazz) throws BeanBinException {
		List<Method> methods = getInAndOutMethods(clazz);
		for(Method getter : methods) {
			for(Annotation anno : getter.getAnnotations()) {
				if(anno instanceof Out) {
					return getter;
				}
			}
		}
		
		throw new BeanBinException("Method with an @Out annotation was not found.");
	}
	
	private Method getInMethod(Class clazz) throws BeanBinException {
		List<Method> methods = getInAndOutMethods(clazz);
		for(Method getter : methods) {
			for(Annotation anno : getter.getAnnotations()) {
				if(anno instanceof In) {
					return getter;
				}
			}
		}
		
		throw new BeanBinException("Method with an @In annotation was not found.");
	}

	private InputStream serialize(Object blob) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream is = new ObjectOutputStream(out);
		is.writeObject(blob);
		return new ByteArrayInputStream(out.toByteArray());
	}
	
	protected Object getBlobObject(String getterName) throws BeanBinException {
		try {
			Method getter = getGetter(getterName);			
			return getter.invoke(entity, new Object[0]);
		} catch (Exception e) {
			throw new BeanBinException("BlobClassTemplate.getBlobObject: " + e.getMessage(), e);
		}
	}

	private Method getGetter(String getterName) throws NoSuchMethodException {
		Method getter = entity.getClass().getMethod(getterName, new Class[0]);
		return getter;
	}
}