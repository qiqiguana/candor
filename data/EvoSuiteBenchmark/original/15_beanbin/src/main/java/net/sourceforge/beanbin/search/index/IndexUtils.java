package net.sourceforge.beanbin.search.index;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.data.EntityUtils;
import net.sourceforge.beanbin.reflect.ReflectionSearch;

public class IndexUtils {
	public static List<String> getIndexProperties(Class clazz) throws BeanBinException {
		List<String> list = new ArrayList<String>();
		for(Method getter : getIndexGetters(clazz)) {
			list.add(EntityUtils.getProperty(getter));
		}
		return list;
	}
	
	public static boolean hasSettableIndexes(Class clazz) throws BeanBinException {
		return !getSettableIndexSetters(clazz).isEmpty();
	}
	
	public static List<String> getSettableIndexProperties(Class clazz) throws BeanBinException {
		List<String> indexProperties = new ArrayList<String>();
		for(Method setter : getSettableIndexSetters(clazz)) {
			indexProperties.add(EntityUtils.getProperty(setter));
		}
		
		return indexProperties;
	}
	
	public static List<Method> getSettableIndexSetters(Class clazz) throws BeanBinException {
		List<Method> list = new ArrayList<Method>();
		for(Method getter : IndexUtils.getIndexGetters(clazz)) {
			Method setter = EntityUtils.getSetter(getter);
			if(getter.getReturnType() == List.class && setter != null) {				
				list.add(setter);
			}
		}
		
		return list;
	}
	
	public static List<Method> getIndexGetters(Class clazz) throws BeanBinException {
		return new ReflectionSearch(clazz).methodsThatHave("@net.sourceforge.beanbin.annotations.IndexSearch").getMethods();		
	}
	
	public static boolean hasAnIndexSearch(Class clazz) throws BeanBinException {
		return !getIndexGetters(clazz).isEmpty();
	}
}
