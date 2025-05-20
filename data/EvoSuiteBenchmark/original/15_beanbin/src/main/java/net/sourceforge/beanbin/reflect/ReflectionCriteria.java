package net.sourceforge.beanbin.reflect;

import java.lang.reflect.Method;
import java.util.List;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.query.Criteria;
import net.sourceforge.beanbin.query.SearchType;

public abstract class ReflectionCriteria extends Criteria {	
	private boolean wildcard;
	private boolean annotation;
	
	public ReflectionCriteria(Class clazz, String term) throws BeanBinException {
		this(clazz, term, SearchType.CONTAINS);
	}
	public ReflectionCriteria(Class clazz, String term, SearchType type) throws BeanBinException {
		this(clazz, clazz.getName(), term, type);
	}
	
	protected ReflectionCriteria(Class clazz, String fieldname, String term, SearchType type) throws BeanBinException {
		super(fieldname, term, type);
		if(hasWildcard(term)) {
			this.wildcard = true;			
		} else {
			this.wildcard = false;
		}
		
		if(term.length() == 0) {
			throw new BeanBinException("Using an empty search term is invalid");
		}
		
		if(term.charAt(0) == '@') {
			annotation = true;
		} else {
			annotation = false;
		}
	}
	
	protected boolean hasWildcard(String term) {
		return term.indexOf("*") != -1;
	}
	
	protected boolean hasWildcard() {
		return this.wildcard;
	}
	
	protected boolean isAnnotation() {
		return annotation;
	}
	
	public abstract List<Method> getResults() throws BeanBinException;
}