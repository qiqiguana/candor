package net.sourceforge.beanbin.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import net.sourceforge.beanbin.BeanBinException;

public class ReflectUtils {
	public static boolean hasAnnotation(Class clazz, Class annoClass) {
		ReflectionSearch search = new ReflectionSearch(clazz);
		return search.hasAnnotation("@" + annoClass.getName());
	}
	
	public static List<Method> getGetters(Class clazz) throws BeanBinException {
		return getGetterSearch(clazz).getMethods();
	}

	public static ReflectionSearch getGetterSearch(Class clazz) throws BeanBinException {
		ReflectionSearch search = new ReflectionSearch(clazz)
										.methodsThatHave("get*")
										.or()
										.methodsThatHave("is*")
										.and()
										.methodsThatDontHave("@Transient")
										.and()
										.methodsThatDontHave("getClass");
		return search;
	}
	
	public static Class extractGenericType(Type type) throws BeanBinException {
		if(type != null && type instanceof ParameterizedType) {			
			ParameterizedType ptype = (ParameterizedType)type;
			
			Type[] generics = ptype.getActualTypeArguments();
			
			if(generics.length == 1 && generics[0] instanceof Class) {
				return (Class) generics[0];
			} else if(generics.length > 1) {
				throw new BeanBinException("extractGenericType does not support 2 generic parameters");
			}
		}
		
		return null;
	}
}
