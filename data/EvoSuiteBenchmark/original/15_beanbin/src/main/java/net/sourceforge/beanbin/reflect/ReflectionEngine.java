package net.sourceforge.beanbin.reflect;

import java.lang.reflect.Method;
import java.util.List;

/**
 * This class is meant to do all the reflection logic against a given
 * class and a search term.  It also serves as a cacheing mechanism in order
 * to improve performance.
 * 
 * Search Term Syntax:
 * 		
 * 
 * @author Brian Gorman
 *
 */
public class ReflectionEngine {
//	private map that acts as the cache..
	
	/**
	 * @param clazz
	 * @param term
	 * @return matching methods
	 */
	public List<Method> getMethodsThatHave(Class clazz, String term) {
		return null;
	}
}
