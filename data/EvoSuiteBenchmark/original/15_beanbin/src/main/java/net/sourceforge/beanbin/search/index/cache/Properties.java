package net.sourceforge.beanbin.search.index.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Properties implements Serializable {	
	private static final long serialVersionUID = 6723598173430933698L;
	
	private Map<String, List<String>> values;
	
	public Properties() {
		this.values = new HashMap<String, List<String>>();
	}
	
	public Set<String> getProperties() {
		return values.keySet();
	}
	
	public List<String> getValues(String property) {
		return values.get(property);
	}
	
	public void remove(String property) {
		values.remove(property);
	}
	
	public void add(String property, List<String> values) {
		this.values.put(property, values);
	}
	
	public void add(Properties props) {
		for(String prop : props.getProperties()) {
			values.put(prop, props.getValues(prop));
		}
	}
}
