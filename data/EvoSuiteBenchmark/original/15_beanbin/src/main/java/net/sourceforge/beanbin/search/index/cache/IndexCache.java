package net.sourceforge.beanbin.search.index.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.beanbin.BeanBinException;

public class IndexCache implements Serializable {
	private static final long serialVersionUID = 3635156889400399154L;
	
	private Map<Class, Results> map;
	
	public IndexCache() {
		this.map = new HashMap<Class, Results>();
	}
	
	public List<String> getValues(Class clazz, Object key, String property) throws BeanBinException {
		Results results = map.get(clazz);
		if(results != null) {
			Properties props = results.get(key);
			if(props != null) {
				List<String> values = props.getValues(property);
				clear(clazz, key, property);
				return values;
			}
		}
		
		return null;
	}
	
	public Set<Class> getClasses() {
		return map.keySet();
	}
	
	public Results get(Class clazz) {
		return map.get(clazz);
	}
	
	public void add(Class clazz, Results results) {
		map.put(clazz, results);
	}
	
	private void clear(Class clazz, Object key, String property) {
		Results results = map.get(clazz);
		if(results != null) {
			results.clear(key, property);
		}
	}
	
	public void addCache(IndexCache cache) {
		for(Class clazz : cache.getClasses()) {
			Results results = map.get(clazz);
			Results toadd = cache.get(clazz);
			if(results != null) {
				results.add(toadd);
			} else {
				map.put(clazz, toadd);
			}
		}
	}
}
