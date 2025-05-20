package net.sourceforge.beanbin.reflect;

import java.util.HashMap;
import java.util.Map;

import net.sourceforge.beanbin.query.Query;

public class ReflectionShelf {
	private static ReflectionShelf shelf;
	
	private Map<Query, Object> results;
	
	private ReflectionShelf() {
		this.results = new HashMap<Query, Object>();		
	}
	
	public Object get(Query query) {
		return results.get(query);
	}
	
	public void put(Query query, Object obj) {
		results.put(query, obj);
	}
	
	public static ReflectionShelf getInstance() {
		if(shelf == null) {
			shelf = new ReflectionShelf();
		}
		
		return shelf;
	}
}
