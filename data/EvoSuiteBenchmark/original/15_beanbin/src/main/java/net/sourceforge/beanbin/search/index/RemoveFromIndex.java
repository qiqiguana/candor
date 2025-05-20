package net.sourceforge.beanbin.search.index;

import java.io.Serializable;

public class RemoveFromIndex implements Serializable {
	private static final long serialVersionUID = 5345761187578130044L;
	
	private Class clazz;
	private Object key;
	
	public RemoveFromIndex(Class clazz, Object key) {
		this.clazz = clazz;
		this.key = key;
	}
	
	public Class getTargetClass() {
		return clazz;
	}
	
	public Object getKey() {
		return key;
	}
}