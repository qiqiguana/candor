package net.sourceforge.beanbin.search.index;

import java.io.Serializable;
import java.util.List;

public class IndexResult implements Serializable {
	private static final long serialVersionUID = 3361625694692670144L;
	private Class clazz;
	private Object key;
	private List<String> values;
	private String property;
	
	public IndexResult(Class clazz, String property, Object key, List<String> values) {
		this.clazz = clazz;
		this.property = property;
		this.key = key;
		this.values = values;
	}
	
	public Class getTargetClass() {
		return clazz;
	}
	
	public String getProperty() {
		return property;
	}
	
	public Object getKey() {
		return key;
	}
	
	public List<String> getValues() {
		return values;
	}
}
