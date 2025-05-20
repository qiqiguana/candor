package net.sourceforge.beanbin.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sourceforge.beanbin.BeanBinException;
import net.sourceforge.beanbin.query.Conditional;
import net.sourceforge.beanbin.query.Criteria;
import net.sourceforge.beanbin.query.Query;
import net.sourceforge.beanbin.query.SearchType;
import net.sourceforge.beanbin.search.WildcardSearch;

/**
 * ReflectionSearch is meant to work as an arguement accumulator in order to 
 * build up criteria then when getMethods() or getProperties() is called the 
 * built up {@link Query} is executed.
 * <br><br>
 * For example:
 * <blockquote>
 * 	<code> ReflectionSearch search = new ReflectionSearch(SomeClass.class).methodsThatHave("@SomeAnno").and().methodsThatDontHave("set*");<br>
 * 		// no search as occured yet...<br>
 * 	List&lt;Method&gt; props = search.getMethods(); // query is executed..</code><br>
 * </blockquote>
 * 
 * The exception to this is the hasAnnotation(term) method.  That method is meant to act on the Class iteself
 * and well create the {@link Query} and execute it immediately.
 * <br><br>
 * To improve performance a cacheing system will be used.  The class is {@link ReflectionShelf} and is a singleton.
 * @author Brian Gorman
 *
 */
public class ReflectionSearch {
	private Class clazz;
	private Query query;

	public ReflectionSearch(Class clazz) {
		this.clazz = clazz;
		this.query = new Query();
	}
	
	/**
	 * Searches the annotations assossiated with the clazz
	 * @param term
	 * @return
	 */
	public boolean hasAnnotation(String term) {
		for(Annotation anno : clazz.getAnnotations()) {
			String name = anno.annotationType().getName();
			String className = name.substring(name.lastIndexOf(".") + 1);
			String termName = term.toString().substring(1);
			
			if(hasWildcard(termName)) {
				WildcardSearch wild = new WildcardSearch(termName);
				if(wild.doesMatch(name)) {
					return true;
				} else if(wild.doesMatch(className)) {
					return true;
				}
			} else {
				if(name.equals(termName)) {
					return true;
				} else if(className.equals(termName)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean hasWildcard(String term) {
		return term.indexOf("*") != -1;
	}
	
	/**
	 * 
	 * @param term
	 * @return
	 * @throws BeanBinException 
	 */
	public ReflectionSearch methodsThatHave(String term) throws BeanBinException {
		query.add(new MethodReflectionCriteria(clazz, term, SearchType.EQUALS));
		return this;
	}
	
	public ReflectionSearch methodsThatDontHave(String term) throws BeanBinException {
		query.add(new MethodReflectionCriteria(clazz, term, SearchType.DOESNOTEQUAL));
		return this;
	}
	
	public ReflectionSearch and() {
		query.setNextConditional(Conditional.AND);
		return this;
	}
	
	public ReflectionSearch or() {
		query.setNextConditional(Conditional.OR);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public List<Method> getMethods() throws BeanBinException {
		List<Method> list = (List<Method>)ReflectionShelf.getInstance().get(query);
		if(list == null) {
			list = new ArrayList<Method>();
			for(Criteria criteria : query.getCriterias()) {
				if(criteria instanceof ReflectionCriteria) {
					ReflectionCriteria crit = (ReflectionCriteria)criteria;
					Conditional cond = crit.getPreviousCondition();
					if(cond != null) {
						if(cond == Conditional.AND) {
							intersect(list, crit.getResults());
						} else {
							union(list, crit.getResults());							
						}
					} else {
						list = crit.getResults();	
					}
				}
			}
			ReflectionShelf.getInstance().put(query, list);
		}
		
		return list;
	}
	
	protected void intersect(List<Method> master, List<Method> toadd) {
		for(int i = 0; i < master.size(); ++i) {
			if(!toadd.contains(master.get(i))) {
				master.remove(i--);
			}
		}
	}
	
	protected void union(List<Method> master, List<Method> toadd) {
		for(Method method : toadd) {
			if(!master.contains(method)) {
				master.add(method);
			}
		}
	}
}
