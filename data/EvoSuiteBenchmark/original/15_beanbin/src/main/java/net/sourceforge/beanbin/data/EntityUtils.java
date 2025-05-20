package net.sourceforge.beanbin.data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.reflect.ReflectUtils;
import net.sourceforge.beanbin.reflect.ReflectionSearch;

public class EntityUtils {
	public static Object getId(Object entity) throws BeanBinException {
		try {
			return getIdGetter(entity.getClass()).invoke(entity, new Object[0]);
		} catch (Exception e) {
			throw new BeanBinException("EntityUtils exception: " + e.getMessage(), e);
		} 
	}
	
	public static String getIdProperty(Class clazz) throws BeanBinException {
		return getProperty(getIdGetter(clazz));
	}
	
	public static String getProperty(Method method) {		
		String prop = method.getName();
		if(prop.startsWith("get") || prop.startsWith("set") || prop.startsWith("has")) {
			prop = prop.substring(3);
		} else if(prop.startsWith("is")) {
			prop = prop.substring(2);			
		}
		
		prop = prop.substring(0, 1).toLowerCase() + prop.substring(1);
		return prop;		
	}
	
	public static Method getMethod(Class clazz, String property) throws BeanBinException {
		property = property.substring(0, 1).toUpperCase() + property.substring(1);
		List<Method> list = ReflectUtils.getGetterSearch(clazz).and().methodsThatHave("get" + property).getMethods();
		if(list.size() != 1) {
			throw new BeanBinException("Could not determine getter Method from the property: " + property);
		}
		
		return list.get(0);
	}
	
	public static List<Method> getBlobGetters(Class clazz) throws BeanBinException {
		List<Method> list = new ArrayList<Method>();
		ReflectionSearch search = new ReflectionSearch(clazz)
									.methodsThatHave("get*")
									.and()
									.methodsThatDontHave("getClass");
		
		for(Method getter : search.getMethods()) {
			if(isBlob(getter.getReturnType())) {
				list.add(getter);
			}
		}
		return list;
	}
	
	public static boolean hasBlobs(Class clazz) throws BeanBinException {
		return !getBlobGetters(clazz).isEmpty();
	}
	
	public static boolean isBlob(Class clazz) {
		return new ReflectionSearch(clazz).hasAnnotation("@net.sourceforge.beanbin.annotations.blob.Blob");
	}
	
	public static List<Method> getSubEntityMethods(Class clazz) throws BeanBinException {
		List<Method> list = new ArrayList<Method>();
		for(Method getter : ReflectUtils.getGetters(clazz)) {
			Class returnType = getter.getReturnType();
			if(!returnType.isPrimitive()) {
				if(EntityUtils.isEntity(returnType)) {
					list.add(getter);
				} else if(EntityUtils.isCollection(returnType)) {
					returnType = ReflectUtils.extractGenericType(getter.getGenericReturnType());
					if(returnType != null && EntityUtils.isEntity(returnType)) {
						list.add(getter);
					}
				}
			}
		}
		return list;
	}
	
	public static Method getIdGetter(Class clazz) throws BeanBinException {
		ReflectionSearch search = new ReflectionSearch(clazz).methodsThatHave("@Id");
		List<Method> list = search.getMethods();
		if(list.size() != 1) {
			throw new BeanBinException("You can only have one method with the @Id annotation");
		}
		Method method = list.get(0);
		return method;
	}
	
	public static boolean isEntity(Class clazz) {
		return ReflectUtils.hasAnnotation(clazz, Entity.class);
	}
	
	public static boolean isAbstract(Class clazz) {
		return ReflectUtils.hasAnnotation(clazz, Inheritance.class);
	}
	
	public static boolean isCollection(Class<?> clazz) {
		try {
			clazz.asSubclass(Collection.class);
			return true;
		} catch(ClassCastException e) {
			return false;
		}
	}
	
	public static void mergeEntities(Object master, Object tomerge) throws BeanBinException {
		try {
			for(Method getter : getGettersWithoutId(master.getClass())) {
				Object masterobj = getter.invoke(master, new Object[0]);
				Object tomergeobj = getter.invoke(tomerge, new Object[0]);
				
				if(masterobj != null && !isEntity(masterobj.getClass())) {
					if(!masterobj.equals(tomergeobj)) {
						merge(master, getter, tomergeobj);
					}
				} else if(masterobj == null && tomergeobj != null && !isEntity(tomergeobj.getClass())) {
					merge(master, getter, tomergeobj);
				}
			}
		} catch(Exception e) {
			throw new BeanBinException("EntityUtls.mergeEntities: " + e.getMessage(), e);
		}
	}

	private static void merge(Object master, Method getter, Object tomergeobj) throws BeanBinException, IllegalAccessException, InvocationTargetException {
		Method setter = getSetter(getter);
		Object[] args = {tomergeobj};
		setter.invoke(master, args);
	}
	
	public static Method getSetter(Method getter) throws BeanBinException {
		String setterName = getSetterName(getter);
		Class[] params = {getter.getReturnType()};
		try {
			return getter.getDeclaringClass().getMethod(setterName, params);
		} catch (NoSuchMethodException e) {
			return null;
		} catch (Exception e) {
			throw new BeanBinException("EntityUtils.getSetter: " + e.getMessage(), e);
		}
	}

	public static String getSetterName(Method getter) {
		String prop = getProperty(getter);
		String setterName = "set" + prop.substring(0, 1).toUpperCase() + prop.substring(1);
		return setterName;
	}

	public static List<Method> getGettersWithoutId(Class clazz) throws BeanBinException {
		return ReflectUtils.getGetterSearch(clazz).and().methodsThatDontHave("@Id").getMethods();
	}

}
