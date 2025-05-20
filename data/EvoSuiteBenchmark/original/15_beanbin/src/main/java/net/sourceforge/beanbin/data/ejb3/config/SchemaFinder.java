package net.sourceforge.beanbin.data.ejb3.config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.data.EntityUtils;
import net.sourceforge.beanbin.reflect.ReflectUtils;
import net.sourceforge.beanbin.reflect.Resolver;

public class SchemaFinder {	
	private List<Class> classes;

	public SchemaFinder(Class clazz) throws BeanBinException {
		this.classes = new ArrayList<Class>();		
		addClass(clazz);
	}
	
	public List<Class> getSchema() {
		return classes;
	}
	
	private void addClass(Class clazz) throws BeanBinException {
		if(getSchema().contains(clazz)) {
			return;
		}
		
		getSchema().add(clazz);		
		
		for(Method getter : ReflectUtils.getGetters(clazz)) {
			Class returnType = getter.getReturnType();
			if(!returnType.isPrimitive()) {
				if(EntityUtils.isEntity(returnType)) {
					addClass(returnType);
				} else if(EntityUtils.isCollection(returnType)) {
					returnType = ReflectUtils.extractGenericType(getter.getGenericReturnType());
					if(returnType != null) {
						addClass(returnType);	
					}
				}
			}
		}
		
		if(EntityUtils.isAbstract(clazz)) {
			Resolver resolve = new Resolver();
			List<Class> impls = resolve.findImplementations(clazz);
			for(Class impl : impls) {
				addClass(impl);
			}	
		}
		
		if(EntityUtils.isEntity(clazz.getSuperclass())) {
			addClass(clazz.getSuperclass());
		}
	}
}
