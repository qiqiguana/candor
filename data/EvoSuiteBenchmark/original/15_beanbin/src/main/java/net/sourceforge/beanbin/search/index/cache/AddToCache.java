package net.sourceforge.beanbin.search.index.cache;

import java.io.Serializable;
import java.util.List;

public class AddToCache implements Serializable {
	private static final long serialVersionUID = -1447662019272196581L;
	
	private Class clazz;
	private List<Object> keys;
	
	public AddToCache(Class clazz, List<Object> keys) {
		this.clazz = clazz;
		this.keys = keys;
	}

	public Class getTargetClass() {
		return clazz;
	}
	
	public List<Object> getKeys() {
		return keys;
	}
}
