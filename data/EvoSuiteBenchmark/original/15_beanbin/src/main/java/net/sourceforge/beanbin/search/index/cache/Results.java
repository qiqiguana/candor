package net.sourceforge.beanbin.search.index.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Results implements Serializable {
	private static final long serialVersionUID = 1606926188924084230L;
	
	private Map<Object, Properties> map;
	
	public Results() {
		this.map = new HashMap<Object, Properties>();
	}
	
	public Properties get(Object key) {
		return this.map.get(key);
	}
	
	public void add(Object key, Properties properties) {
		this.map.put(key, properties);
	}
	
	public Set<Object> getKeys() {
		return map.keySet();
	}
	
	public void clear(Object key, String property) {
		Properties props = map.get(key);
		props.remove(property);
		if(props.getProperties().isEmpty()) {
			map.remove(key);
		}
	}
	
	public void add(Results results) {
		for(Object key : results.getKeys()) {
			Properties props = map.get(key);
			Properties toadd = results.get(key);
			if(props != null) {
				props.add(toadd);
			} else {
				map.put(key, toadd);
			}
		}
	}
}
