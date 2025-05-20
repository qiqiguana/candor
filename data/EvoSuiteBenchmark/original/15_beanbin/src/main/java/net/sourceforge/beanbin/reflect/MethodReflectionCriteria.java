package net.sourceforge.beanbin.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.query.SearchType;
import net.sourceforge.beanbin.search.WildcardSearch;

public class MethodReflectionCriteria extends ReflectionCriteria {
	private static final long serialVersionUID = -293584882622008707L;
	
	private Class clazz;

	public MethodReflectionCriteria(Class clazz, String term, SearchType type) throws BeanBinException {
		super(clazz, clazz.getName() + "-method", term, type);
		this.clazz = clazz;
	}

	public List<Method> getResults() throws BeanBinException {
		List<Method> list = new ArrayList<Method>();
		for(Method method : clazz.getMethods()) {
			if(getType() == SearchType.EQUALS) {
				addIfEquals(list, method);
			} else if(getType() == SearchType.CONTAINS) {
				addIfContains(list, method);
			} else if(getType() == SearchType.DOESNOTEQUAL) {
				addIfDoesNotEqual(list, method);
			} else {
				throw new BeanBinException("SearchType: " + getType() + " is invalid for MethodReflectionCriteria!");
			}
		}
		return list;
	}

	private void addIfEquals(List<Method> list, Method method) {
		if(isAnnotation()) {
			addForAnnotation(list, method, (String)getTerm());
		} else {
			if(add(method.getName(), getTerm().toString())) {
				list.add(method);
			}
		}
	}

	private void addForAnnotation(List<Method> list, Method method, String term) {
		for(Annotation anno : method.getAnnotations()) {
			String name = anno.annotationType().getName();
			String className = name.substring(name.lastIndexOf(".") + 1);
			String termName = term.toString().substring(1);
			
			if(add(name, termName) || add(className, termName)) {
				list.add(method);
			}
		}
	}
	
	private void addIfContains(List<Method> list, Method method) {
		if(isAnnotation()) {
			String term = getTerm().toString().substring(1);
			addForAnnotation(list, method, "@*" + term + "*");
		} else if(hasWildcard()){
			WildcardSearch wild = new WildcardSearch("*" + getTerm().toString() + "*");
			if(wild.doesMatch(method.getName())) {
				list.add(method);
			}
		} else {
			if(method.getName().indexOf((String)getTerm()) != -1) {
				list.add(method);
			}	
		}
	}
	
	private void addIfDoesNotEqual(List<Method> list, Method method) {
		if(isAnnotation()) {
			if(method.getAnnotations().length == 0) {
				list.add(method);
			} else {
				boolean add = true;
				for(Annotation anno : method.getAnnotations()) {
					String name = anno.annotationType().getName();
					String className = name.substring(name.lastIndexOf(".") + 1);
					String termName = getTerm().toString().substring(1);
					
					if(add(name, termName) || add(className, termName)) {
						add = false;
						break;
					}
				}
				if(add) {
					list.add(method);
				}
			}		
		} else {
			if(!add(method.getName(), getTerm().toString())) {
				list.add(method);
			}
		}
	}
	
	private boolean add(String value, String term) {
		if(hasWildcard(term)) {
			WildcardSearch wild = new WildcardSearch(term);
			if(wild.doesMatch(value)) {
				return true;
			}			
		} else {
			if(value.equals(term)) {
				return true;
			}
		}
		return false;
	}
}
